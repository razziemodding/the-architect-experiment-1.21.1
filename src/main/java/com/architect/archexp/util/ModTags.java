package com.architect.archexp.util;

import com.architect.archexp.TheArchitectExperiment;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

public class ModTags {;

    public static class Items {
        public static final TagKey<Item> SOUL = createTag("soul");

        public static final TagKey<Item> MARKET_FIRST_INPUT_VALID_EXTR = createTag("market_first_input_valid_extr");



        private static TagKey<Item> createTag(String name) {
            return TagKey.of(RegistryKeys.ITEM, Identifier.of(TheArchitectExperiment.MOD_ID, name));
        }
    }
}
