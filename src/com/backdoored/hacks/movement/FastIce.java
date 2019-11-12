package com.backdoored.hacks.movement;

import net.minecraft.init.Blocks;
import com.backdoored.gui.CategoriesInit;
import com.backdoored.setting.Setting;
import net.minecraft.block.Block;
import com.backdoored.hacks.BaseHack;

public class FastIce extends BaseHack
{
    private static final Block[] ices;
    private static Setting speed;
    
    public FastIce() {
        super("Fast Ice", CategoriesInit.MOVEMENT, "Make ice very slippery");
        FastIce.speed = new Setting("Speed (0.6 is best)", this, 0.6, 0.0, 1.0);
    }
    
    public void onEnabled() {
        final float speedVal = (float)FastIce.speed.getValDouble();
        final float slipperiness = Math.abs(speedVal - 1.0f);
        setIces(slipperiness);
    }
    
    public void onDisabled() {
        setIces(0.98f);
    }
    
    private static void setIces(final float slipperiness) {
        for (final Block ice : FastIce.ices) {
            ice.setDefaultSlipperiness(ice.field_149765_K = slipperiness);
        }
    }
    
    static {
        ices = new Block[] { Blocks.field_150432_aD, Blocks.field_150403_cj, Blocks.field_185778_de };
    }
}
