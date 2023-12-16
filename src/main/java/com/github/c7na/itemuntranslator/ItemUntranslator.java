package com.github.c7na.itemuntranslator;

import cpw.mods.fml.common.Mod;

@Mod(modid = Tags.MODID)
public class ItemUntranslator {

    public static final Object getTooltipLock = new Object();
    public static Thread getTooltipThread;

    public static boolean shouldUntranslate() {
        Thread current = Thread.currentThread();
        return current.equals(getTooltipThread);
    }
}
