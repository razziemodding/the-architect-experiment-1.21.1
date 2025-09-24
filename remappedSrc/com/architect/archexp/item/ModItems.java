package com.architect.archexp.item;

import com.architect.archexp.TheArchitectExperiment;
import com.architect.archexp.item.custom.HealthAmuletItem;
import com.architect.archexp.item.custom.MightAmuletItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;

import java.util.function.Function;

public class ModItems {
    //amulets
    public static final Item HEALTH_AMULET = registerItem("health_amulet", setting -> new HealthAmuletItem(setting.rarity(Rarity.RARE).maxCount(1)));
    public static final Item MIGHT_AMULET = registerItem("might_amulet", setting -> new MightAmuletItem(setting.rarity(Rarity.RARE).maxCount(1).useCooldown(5f)));

    //registry
    private static Item registerItem(String name, Function<Item.Settings, Item> function) {
        return Registry.register(Registries.ITEM, Identifier.of(TheArchitectExperiment.MOD_ID, name),
                function.apply(new Item.Settings().registryKey(RegistryKey.of(RegistryKeys.ITEM, Identifier.of(TheArchitectExperiment.MOD_ID, name)))));
    }

    public static void registerModItems() {
        TheArchitectExperiment.LOGGER.info("The Architect gives you items.");
    }
}
