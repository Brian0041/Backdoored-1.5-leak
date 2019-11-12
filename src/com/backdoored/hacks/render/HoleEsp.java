package com.backdoored.hacks.render;

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.util.math.AxisAlignedBB;
import com.backdoored.utils.RenderUtils;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraft.init.Blocks;
import java.util.Iterator;
import com.backdoored.gui.CategoriesInit;
import net.minecraft.util.math.BlockPos;
import java.util.ArrayList;
import com.backdoored.setting.Setting;
import com.backdoored.hacks.BaseHack;

public class HoleEsp extends BaseHack
{
    private static Setting holeRadius;
    private static Setting holeHeight;
    private static Setting onlyBedrock;
    private static Setting maxY;
    private ArrayList<BlockPos> holes;
    
    public HoleEsp() {
        super("Hole ESP", CategoriesInit.RENDER, "See holes to camp in during pvp");
        HoleEsp.holeRadius = new Setting("Hole Radius", this, 10, 1, 50);
        HoleEsp.holeHeight = new Setting("Hole height 3", this, false);
        HoleEsp.onlyBedrock = new Setting("Only bedrock or obby", this, true);
        HoleEsp.maxY = new Setting("Max Y", this, 125, 0, 125);
    }
    
    public void onUpdate() {
        if (!this.getEnabled()) {
            return;
        }
        this.holes = new ArrayList<BlockPos>();
        final Iterable<BlockPos> potentials = (Iterable<BlockPos>)BlockPos.func_177980_a(HoleEsp.mc.field_71439_g.func_180425_c().func_177982_a(-HoleEsp.holeRadius.getValInt(), -HoleEsp.holeRadius.getValInt(), -HoleEsp.holeRadius.getValInt()), HoleEsp.mc.field_71439_g.func_180425_c().func_177982_a(HoleEsp.holeRadius.getValInt(), HoleEsp.holeRadius.getValInt(), HoleEsp.holeRadius.getValInt()));
        for (final BlockPos pos : potentials) {
            if (isHole(pos, HoleEsp.holeHeight.getValBoolean())) {
                this.holes.add(pos);
            }
        }
    }
    
    public static boolean isHole(final BlockPos pos) {
        return isHole(pos, false);
    }
    
    public static boolean isHole(final BlockPos pos, final boolean holeheight) {
        if (pos.func_177956_o() > HoleEsp.maxY.getValInt()) {
            return false;
        }
        final boolean isSolid = !HoleEsp.mc.field_71441_e.func_180495_p(pos).func_185904_a().func_76230_c() && !HoleEsp.mc.field_71441_e.func_180495_p(pos.func_177982_a(0, 1, 0)).func_185904_a().func_76230_c() && (!HoleEsp.mc.field_71441_e.func_180495_p(pos.func_177982_a(0, 2, 0)).func_185904_a().func_76230_c() || !holeheight) && HoleEsp.mc.field_71441_e.func_180495_p(pos.func_177982_a(0, -1, 0)).func_185904_a().func_76220_a() && HoleEsp.mc.field_71441_e.func_180495_p(pos.func_177982_a(1, 0, 0)).func_185904_a().func_76220_a() && HoleEsp.mc.field_71441_e.func_180495_p(pos.func_177982_a(0, 0, 1)).func_185904_a().func_76220_a() && HoleEsp.mc.field_71441_e.func_180495_p(pos.func_177982_a(-1, 0, 0)).func_185904_a().func_76220_a() && HoleEsp.mc.field_71441_e.func_180495_p(pos.func_177982_a(0, 0, -1)).func_185904_a().func_76220_a();
        if (!HoleEsp.onlyBedrock.getValBoolean()) {
            return isSolid;
        }
        final boolean isBedrock = (HoleEsp.mc.field_71441_e.func_180495_p(pos.func_177982_a(0, -1, 0)).func_177230_c().equals(Blocks.field_150357_h) || HoleEsp.mc.field_71441_e.func_180495_p(pos.func_177982_a(0, -1, 0)).func_177230_c().equals(Blocks.field_150343_Z)) && (HoleEsp.mc.field_71441_e.func_180495_p(pos.func_177982_a(1, 0, 0)).func_177230_c().equals(Blocks.field_150357_h) || HoleEsp.mc.field_71441_e.func_180495_p(pos.func_177982_a(1, 0, 0)).func_177230_c().equals(Blocks.field_150343_Z)) && (HoleEsp.mc.field_71441_e.func_180495_p(pos.func_177982_a(0, 0, 1)).func_177230_c().equals(Blocks.field_150357_h) || HoleEsp.mc.field_71441_e.func_180495_p(pos.func_177982_a(0, 0, 1)).func_177230_c().equals(Blocks.field_150343_Z)) && (HoleEsp.mc.field_71441_e.func_180495_p(pos.func_177982_a(-1, 0, 0)).func_177230_c().equals(Blocks.field_150357_h) || HoleEsp.mc.field_71441_e.func_180495_p(pos.func_177982_a(-1, 0, 0)).func_177230_c().equals(Blocks.field_150343_Z)) && (HoleEsp.mc.field_71441_e.func_180495_p(pos.func_177982_a(0, 0, -1)).func_177230_c().equals(Blocks.field_150357_h) || HoleEsp.mc.field_71441_e.func_180495_p(pos.func_177982_a(0, 0, -1)).func_177230_c().equals(Blocks.field_150343_Z));
        return isBedrock && isSolid;
    }
    
    @SubscribeEvent
    public void onRenderWorld(final RenderWorldLastEvent event) {
        if (!this.getEnabled()) {
            return;
        }
        RenderUtils.glStart(255.0f, 0.0f, 255.0f, 1.0f);
        if (this.holes != null) {
            for (final BlockPos hole : this.holes) {
                final AxisAlignedBB bb = RenderUtils.getBoundingBox(hole);
                RenderUtils.drawOutlinedBox(bb);
            }
        }
        RenderUtils.glEnd();
    }
}
