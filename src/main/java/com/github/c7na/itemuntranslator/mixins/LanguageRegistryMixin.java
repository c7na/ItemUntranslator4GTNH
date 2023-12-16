package com.github.c7na.itemuntranslator.mixins;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import com.github.c7na.itemuntranslator.ItemUntranslator;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.registry.LanguageRegistry;

@Mixin(LanguageRegistry.class)
public class LanguageRegistryMixin {

    @Overwrite(remap = false)
    public String getStringLocalization(String key) {
        LanguageRegistry registry = LanguageRegistry.class.cast(this);
        if (ItemUntranslator.shouldUntranslate()) {
            return registry.getStringLocalization(key, "en_US");
        } else {
            return registry.getStringLocalization(
                key,
                FMLCommonHandler.instance()
                    .getCurrentLanguage());
        }
    }
}
