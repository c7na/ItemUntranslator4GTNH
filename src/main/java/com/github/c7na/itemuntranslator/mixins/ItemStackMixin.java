package com.github.c7na.itemuntranslator.mixins;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.github.c7na.itemuntranslator.ItemUntranslator;

@Mixin(ItemStack.class)
public class ItemStackMixin {

    private static int recursion = 0;

    @Inject(method = "getTooltip", at = @At("RETURN"))
    public void getTooltip(EntityPlayer player, boolean showAdvancedItemTooltips,
        CallbackInfoReturnable<List<String>> cir) {
        ItemStack stack = ItemStack.class.cast(this);
        List<String> arraylist = cir.getReturnValue();

        String englishName;
        synchronized (ItemUntranslator.getTooltipLock) {
            try {
                if (ItemUntranslator.getTooltipThread == null) {
                    ItemUntranslator.getTooltipThread = Thread.currentThread();
                } else {
                    recursion++;
                }
                englishName = stack.getDisplayName();
            } finally {
                if (recursion == 0) {
                    ItemUntranslator.getTooltipThread = null;
                } else {
                    recursion--;
                }

            }
        }
        if (englishName != null && !englishName.equals(arraylist.get(0))) {
            arraylist.add(1, englishName);
        }
    }
}
