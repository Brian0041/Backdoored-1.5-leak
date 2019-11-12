package com.backdoored.hacks.combat;

import net.minecraft.entity.Entity;
import net.minecraft.util.math.AxisAlignedBB;
import com.backdoored.utils.WorldUtils;
import net.minecraft.init.Blocks;
import com.backdoored.gui.CategoriesInit;
import net.minecraft.util.math.BlockPos;
import com.backdoored.hacks.BaseHack;

public class Surround extends BaseHack
{
    private BlockPos lastPos;
    
    public Surround() {
        super("Surround", CategoriesInit.COMBAT, "Surrounds your feet with obsidian.");
        this.lastPos = new BlockPos(0, -100, 0);
    }
    
    public void onUpdate() {
        if (!this.getEnabled()) {
            return;
        }
        final int prevSlot = Surround.mc.field_71439_g.field_71071_by.field_70461_c;
        final int obbyIndex = WorldUtils.findBlock(Blocks.field_150343_Z);
        if (obbyIndex != -1) {
            final BlockPos playerPos = new BlockPos(Surround.mc.field_71439_g.func_174791_d());
            if (playerPos.equals((Object)this.lastPos)) {
                final BlockPos[] array;
                final BlockPos[] positions = array = new BlockPos[] { playerPos.func_177982_a(0, -1, 1), playerPos.func_177982_a(1, -1, 0), playerPos.func_177982_a(0, -1, -1), playerPos.func_177982_a(-1, -1, 0), playerPos.func_177982_a(0, 0, 1), playerPos.func_177982_a(1, 0, 0), playerPos.func_177982_a(0, 0, -1), playerPos.func_177982_a(-1, 0, 0) };
                for (final BlockPos pos : array) {
                    if (Surround.mc.field_71441_e.func_180495_p(pos).func_185904_a().func_76222_j() && Surround.mc.field_71441_e.func_72839_b((Entity)null, new AxisAlignedBB(pos)).isEmpty()) {
                        Surround.mc.field_71439_g.field_71071_by.field_70461_c = obbyIndex;
                        WorldUtils.placeBlockMainHand(pos);
                    }
                }
                Surround.mc.field_71439_g.field_71071_by.field_70461_c = prevSlot;
            }
            else {
                this.setEnabled(false);
            }
        }
    }
    
    public void onEnabled() {
        this.lastPos = new BlockPos(Surround.mc.field_71439_g.func_174791_d());
    }
}
