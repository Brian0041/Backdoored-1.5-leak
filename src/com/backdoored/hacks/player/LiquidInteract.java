package com.backdoored.hacks.player;

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import com.backdoored.event.CanLiquidCollide;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import net.minecraft.block.material.Material;
import com.backdoored.gui.CategoriesInit;
import com.backdoored.hacks.BaseHack;

public class LiquidInteract extends BaseHack
{
    public LiquidInteract() {
        super("Liquid Interact", "Allows raytracing to hit liquids", CategoriesInit.PLAYER);
    }
    
    public void onEnabled() {
        this.setReplacable(true);
    }
    
    public void onDisabled() {
        this.setReplacable(false);
    }
    
    private void setReplacable(final boolean replacable) {
        final Material[] array;
        final Material[] mats = array = new Material[] { Material.field_151586_h, Material.field_151587_i };
        for (final Material mat : array) {
            ObfuscationReflectionHelper.setPrivateValue((Class)Material.class, (Object)mat, (Object)replacable, new String[] { "replaceable", "field_76239_H" });
        }
    }
    
    @SubscribeEvent
    public void onCanLiquidCollide(final CanLiquidCollide event) {
        if (this.getEnabled()) {
            event.cir.setReturnValue((Object)Boolean.TRUE);
        }
    }
}
