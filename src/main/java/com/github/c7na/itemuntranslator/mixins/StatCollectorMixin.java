package com.github.c7na.itemuntranslator.mixins;

import net.minecraft.util.StatCollector;
import net.minecraft.util.StringTranslate;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import com.github.c7na.itemuntranslator.ItemUntranslator;

import cpw.mods.fml.common.registry.LanguageRegistry;

@Mixin(StatCollector.class)
public class StatCollectorMixin {

    @Shadow
    private static StringTranslate localizedName;
    @Shadow
    private static StringTranslate fallbackTranslator;

    @Overwrite
    public static String translateToLocal(String key) {
        if (ItemUntranslator.shouldUntranslate()) {
            return tryTranslateKey(key);
        } else {
            return localizedName.translateKey(key);
        }
    }

    @Overwrite
    public static String translateToLocalFormatted(String key, Object... params) {
        if (ItemUntranslator.shouldUntranslate()) {
            return String.format(tryTranslateKey(key), params);
        } else {
            return localizedName.translateKeyFormat(key, params);
        }
    }

    private static String tryTranslateKey(String key) {
        String name = LanguageRegistry.instance()
            .getStringLocalization(key, "en_US");
        if (name == null || name.isEmpty() || name.equals(key)) {
            name = fallbackTranslator.translateKey(key);
        }
        if (name == null || name.isEmpty() || name.equals(key)) {
            name = localizedName.translateKey(key);
        }
        return name == null ? key : name;
    }

    @Overwrite
    public static boolean canTranslate(String key) {
        if (ItemUntranslator.shouldUntranslate()) {
            return fallbackTranslator.containsTranslateKey(key);
        } else {
            return localizedName.containsTranslateKey(key);
        }
    }
}
