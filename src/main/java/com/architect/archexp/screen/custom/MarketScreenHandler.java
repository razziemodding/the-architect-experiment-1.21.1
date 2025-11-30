package com.architect.archexp.screen.custom;

import com.architect.archexp.TheArchitectExperiment;
import com.architect.archexp.recipe.MarketRecipe;
import com.architect.archexp.recipe.MarketRecipeInput;
import com.architect.archexp.recipe.ModRecipes;
import com.architect.archexp.screen.ModScreens;
import com.architect.archexp.util.ModTags;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.RecipeEntry;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.screen.slot.Slot;
import net.minecraft.world.World;

import java.util.Optional;

public class MarketScreenHandler extends ScreenHandler { //
    private final Inventory inventory;
    private final ScreenHandlerContext context;

    // slots
    private final int INPUT = 0;
    private final int CURRENCY = 1;
    private final int OUTPUT = 2;
    private final int SLOT_ONE_X = 31;
    private final int SLOT_TWO_X = 67;
    private final int OUTPUT_X = 129;
    private final int SLOT_Y = 35;

    private final World world;
    private RecipeEntry<MarketRecipe> currentRecipe;
    public MarketScreenHandler(int syncId, PlayerInventory playerInventory) {
        this(syncId, playerInventory, new SimpleInventory(3), ScreenHandlerContext.EMPTY);
    }

    public MarketScreenHandler(int syncId, PlayerInventory playerInventory, Inventory inventory, ScreenHandlerContext context) {
        super(ModScreens.MARKETPLACE_SCREEN_HANDLER, syncId);
        checkSize(inventory, 3);
        this.inventory = inventory;
        this.context = context;
        this.world = playerInventory.player.getWorld();

        // input
        this.addSlot(new Slot(inventory, INPUT, SLOT_ONE_X, SLOT_Y) {
            @Override
            public void markDirty() {
                super.markDirty();
                updateResult();
            }
        });

        // currency (diamonds)
        this.addSlot(new Slot(inventory, CURRENCY, SLOT_TWO_X, SLOT_Y) {
            @Override
            public void markDirty() {
                super.markDirty();
                updateResult();
            }
        });

        // no insert
        this.addSlot(new Slot(inventory, OUTPUT, OUTPUT_X, SLOT_Y) {
            @Override
            public boolean canInsert(ItemStack stack) {
                return false;
            }

            @Override
            public void onTakeItem(PlayerEntity player, ItemStack stack) {
                craftItem();
                super.onTakeItem(player, stack);
            }
        });

        addPlayerInventory(playerInventory);
        addPlayerHotbar(playerInventory);

        // initialize/update output
        updateResult();
    }

    private void updateResult() {
        if (world == null) return;

        Optional<RecipeEntry<MarketRecipe>> match = getCurrentRecipe();
        this.currentRecipe = match.orElse(null);

        if (this.currentRecipe == null) {
            setStack(OUTPUT, ItemStack.EMPTY);
            sendContentUpdates();
            return;
        }
        if (!this.inventory.getStack(INPUT).isIn(ModTags.Items.MARKETPLACE_TRADABLE)) {
            setStack(OUTPUT, ItemStack.EMPTY);
            sendContentUpdates();
            return;
        } else {
            if (this.inventory.getStack(INPUT).isIn(ModTags.Items.CROPS) &&
                    (this.inventory.getStack(CURRENCY).getCount() < currentRecipe.value().getCurrencyCost(this.inventory.getStack(INPUT))) ||
                     this.inventory.getStack(INPUT).getCount() < currentRecipe.value().getInputCost(this.inventory.getStack(INPUT))) {
                setStack(OUTPUT, ItemStack.EMPTY);
                sendContentUpdates();
                return;
            } else if (this.inventory.getStack(INPUT).isIn(ModTags.Items.MARKET_FIRST_INPUT_VALID_EXTR) &&
                        this.inventory.getStack(CURRENCY).getCount() < currentRecipe.value().getCurrencyCost(this.inventory.getStack(INPUT))) {
                setStack(OUTPUT, ItemStack.EMPTY);
                sendContentUpdates();
                return;
            } else if (TheArchitectExperiment.MarketMap.containsKey(this.inventory.getStack(INPUT).getItem()) &&
                    (this.inventory.getStack(INPUT).getCount() < TheArchitectExperiment.MarketMap.getOrDefault(this.inventory.getStack(INPUT).getItem(), null) ||
                    this.inventory.getStack(CURRENCY).getCount() < TheArchitectExperiment.MarketCost.getOrDefault(this.inventory.getStack(INPUT).getItem(), null))) {
                setStack(OUTPUT, ItemStack.EMPTY);
                sendContentUpdates();
                return;
            }
        }

        // get output stack
        ItemStack result = this.currentRecipe.value().getResult(world.getRegistryManager()).copy();
        setStack(OUTPUT, result);
        sendContentUpdates();
    }

    private void craftItem() {
        if (this.currentRecipe == null) return;

        ItemStack produced = this.currentRecipe.value().getResult(world.getRegistryManager()).copy();
        if (produced.isEmpty()) return;

        if (!canInsertItemIntoOutputSlot(produced) || !canInsertAmountIntoOutputSlot(produced.getCount())) {
            return;
        }

        int inputCost = this.currentRecipe.value().getInputCost(this.inventory.getStack(INPUT));
        int currencyCost = this.currentRecipe.value().getCurrencyCost(this.inventory.getStack(INPUT));

        ItemStack input = inventory.getStack(INPUT);
        ItemStack currency = inventory.getStack(CURRENCY);

        if (input.isEmpty() || currency.isEmpty()) return;
        if (input.getCount() < inputCost || currency.getCount() < currencyCost) return;


        removeStack(INPUT, inputCost);
        removeStack(CURRENCY, currencyCost);

        // merge crafted result into output slot
        ItemStack output = inventory.getStack(OUTPUT);
        if (output.isEmpty()) {
            setStack(OUTPUT, produced);
        } else if (/*ItemStack.canCombine(output, produced)*/ output.isOf(produced.getItem())) {
            output.increment(produced.getCount());
            setStack(OUTPUT, output);
        } else {
            // shouldnt happen
            return;
        }

        // re calcularte
        updateResult();
        sendContentUpdates();
    }

    @Override
    public void onClosed(PlayerEntity player) {
        super.onClosed(player);
        player.getInventory().offerOrDrop(inventory.removeStack(INPUT));
        player.getInventory().offerOrDrop(inventory.removeStack(CURRENCY));
    }

    private ItemStack removeStack(int slot, int count) {
        ItemStack removed = this.inventory.removeStack(slot, count);
        if (!removed.isEmpty()) {
            sendContentUpdates();
        }
        return removed;
    }

    private void setStack(int slot, ItemStack stack) {
        this.inventory.setStack(slot, stack);
        sendContentUpdates();
    }

    private boolean canInsertItemIntoOutputSlot(ItemStack out) {
        ItemStack current = inventory.getStack(OUTPUT);
        return current.isEmpty() || current.getItem() == out.getItem();
    }

    private Optional<RecipeEntry<MarketRecipe>> getCurrentRecipe() {
        ItemStack in = inventory.getStack(INPUT);
        ItemStack cur = inventory.getStack(CURRENCY);
        if (in.isEmpty() || cur.isEmpty()) return Optional.empty();

        MarketRecipeInput input = new MarketRecipeInput(in, cur);
        return world.getRecipeManager().getFirstMatch(ModRecipes.MARKET_RECIPE_TYPE, input, world);
    }

    private boolean canInsertAmountIntoOutputSlot(int count) {
        ItemStack current = inventory.getStack(OUTPUT);
        int max = current.isEmpty() ? producedMax(count) : current.getMaxCount();
        int have = current.isEmpty() ? 0 : current.getCount();
        return have + count <= max;
    }

    private int producedMax(int defaultMax) {
        Optional<RecipeEntry<MarketRecipe>> match = getCurrentRecipe();
        Ingredient slot1;
        if (match.isPresent()) {
            slot1 = match.get().value().input();
            if (TheArchitectExperiment.MarketMap.containsKey(slot1) && TheArchitectExperiment.MarketMap.get(slot1) == inventory.getStack(INPUT).getCount())
                defaultMax = TheArchitectExperiment.MarketMap.get(slot1);
        } else if (inventory.getStack(INPUT).isIn(ModTags.Items.MARKET_FIRST_INPUT_VALID_EXTR)) {
            defaultMax = 1;
        } else defaultMax = 0;

        return defaultMax;
    }

    @Override
    public ItemStack quickMove(PlayerEntity player, int invSlot) {
        ItemStack newStack = ItemStack.EMPTY;
        Slot slot = this.slots.get(invSlot);
        if (slot != null && slot.hasStack()) {
            ItemStack stack = slot.getStack();
            newStack = stack.copy();

            if (invSlot == OUTPUT) {
                // Move crafted output to player inventory
                if (!this.insertItem(stack, 3, 39, true)) return ItemStack.EMPTY;
                slot.onQuickTransfer(stack, newStack);
            } else if (invSlot == INPUT || invSlot == CURRENCY) {
                // Move input/currency back to player inventory
                if (!this.insertItem(stack, 3, 39, false)) return ItemStack.EMPTY;
            } else {
                // From player inventory/hotbar -> try INPUT then CURRENCY
                if (!this.insertItem(stack, INPUT, INPUT + 1, false)) {
                    if (!this.insertItem(stack, CURRENCY, CURRENCY + 1, false)) {
                        return ItemStack.EMPTY;
                    }
                }
            }

            if (stack.isEmpty()) {
                slot.setStack(ItemStack.EMPTY);
            } else {
                slot.markDirty();
            }

            if (stack.getCount() == newStack.getCount()) {
                return ItemStack.EMPTY;
            }
            slot.onTakeItem(player, stack);
        }
        return newStack;
    }

    

    @Override
    public boolean canUse(PlayerEntity player) {
        return this.inventory.canPlayerUse(player);
    }

    private void addPlayerInventory(PlayerInventory playerInventory) {
        for (int row = 0; row < 3; ++row) {
            for (int col = 0; col < 9; ++col) {
                this.addSlot(new Slot(playerInventory, col + row * 9 + 9, 8 + col * 18, 84 + row * 18));
            }
        }
    }

    private void addPlayerHotbar(PlayerInventory playerInventory) {
        for (int i = 0; i < 9; ++i) {
            this.addSlot(new Slot(playerInventory, i, 8 + i * 18, 142));
        }
    }
}