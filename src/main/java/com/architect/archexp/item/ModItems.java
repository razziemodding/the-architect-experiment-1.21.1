package com.architect.archexp.item;

import com.architect.archexp.TheArchitectExperiment;
import com.architect.archexp.item.custom.*;
import com.architect.archexp.util.ModComponents;
import net.minecraft.item.Item;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.item.ToolMaterials;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;

public class ModItems {
    //amulets
    public static final Item HEALTH_AMULET = registerItem("health_amulet", new HealthAmuletItem(new Item.Settings().rarity(Rarity.RARE).maxCount(1)));
    public static final Item MIGHT_AMULET = registerItem("might_amulet", new MightAmuletItem(new Item.Settings()
            .rarity(Rarity.RARE)
            .maxCount(1)
            .component(ModComponents.ITEM_DELAY, 0)
            .component(ModComponents.WAIT_FOR_DELAY, false)));
    public static final Item SOUL_AMULET = registerItem("soul_amulet", new SoulAmuletItem(new Item.Settings()
            .rarity(Rarity.RARE)
            .maxCount(1)
            .component(ModComponents.SOUL_AMULET_ACTIVE, false)));
    public static final Item BOLT_AMULET = registerItem("bolt_amulet", new BoltAmuletItem(new Item.Settings().rarity(Rarity.RARE).maxCount(1)));
    public static final Item WIND_AMULET = registerItem("wind_amulet", new WindAmuletItem(new Item.Settings().rarity(Rarity.RARE).maxCount(1)));

    //weapons
    public static final Item SKELETAL_SCALE = registerItem("skeletal_scale", new SwordItem(ToolMaterials.STONE,
            new Item.Settings().attributeModifiers(SwordItem.createAttributeModifiers(ToolMaterials.IRON, 3, -2)).rarity(Rarity.EPIC).maxCount(1)));

    //steamhappy

    public static final Item STEAM_HAPPY = registerItem("steam_happy", new Item(new Item.Settings().rarity(Rarity.EPIC).maxCount(64)));


    //registry
    private static Item registerItem(String name, Item item) {
        return Registry.register(Registries.ITEM, Identifier.of(TheArchitectExperiment.MOD_ID, name), item);
    }

    public static void registerModItems() {
        TheArchitectExperiment.LOGGER.info("The Architect gives you material.");
    }
}
