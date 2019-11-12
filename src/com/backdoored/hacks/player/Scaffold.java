package com.backdoored.hacks.player;

import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.living.LivingEvent;
import java.util.Iterator;
import java.util.ArrayList;
import com.backdoored.utils.WorldUtils;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import com.backdoored.utils.Utils;
import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import com.backdoored.setting.Setting;
import com.backdoored.gui.CategoriesInit;
import com.backdoored.hacks.BaseHack;

public class Scaffold extends BaseHack
{
    public Scaffold() {
        super("Scaffold", CategoriesInit.PLAYER, "Automatically bridges for you");
        new Setting("Radius", this, 0, 0, 2);
        new Setting("Down", this, true);
        new Setting("Tower", this, true);
    }
    
    public void onUpdate() {
        if (!this.getEnabled()) {
            return;
        }
        final int oldSlot = Scaffold.mc.field_71439_g.field_71071_by.field_70461_c;
        int newSlot = -1;
        for (int i = 0; i < 9; ++i) {
            if (Scaffold.mc.field_71439_g.field_71071_by.func_70301_a(i) != ItemStack.field_190927_a && Scaffold.mc.field_71439_g.field_71071_by.func_70301_a(i).func_77973_b() instanceof ItemBlock && Block.func_149634_a(Scaffold.mc.field_71439_g.field_71071_by.func_70301_a(i).func_77973_b()).func_176223_P().func_185913_b()) {
                newSlot = i;
                break;
            }
        }
        if (newSlot == -1) {
            Utils.printMessage("No blocks found in hotbar!", "red");
            this.setEnabled(false);
            return;
        }
        Scaffold.mc.field_71439_g.field_71071_by.field_70461_c = newSlot;
        if (this.getSetting("Radius").getValInt() != 0 && this.getSetting("Down").getValBoolean()) {
            this.getSetting("Radius").setVal(0);
        }
        if (Scaffold.mc.field_71474_y.field_151444_V.func_151470_d() && this.getSetting("Down").getValBoolean()) {
            final float yaw = (float)Math.toRadians(Scaffold.mc.field_71439_g.field_70177_z);
            if (Scaffold.mc.field_71474_y.field_74351_w.func_151470_d()) {
                Scaffold.mc.field_71439_g.field_70159_w = -MathHelper.func_76126_a(yaw) * 0.03f;
                Scaffold.mc.field_71439_g.field_70179_y = MathHelper.func_76134_b(yaw) * 0.03f;
            }
            if (Scaffold.mc.field_71474_y.field_74368_y.func_151470_d()) {
                Scaffold.mc.field_71439_g.field_70159_w = MathHelper.func_76126_a(yaw) * 0.03f;
                Scaffold.mc.field_71439_g.field_70179_y = -MathHelper.func_76134_b(yaw) * 0.03f;
            }
            if (Scaffold.mc.field_71474_y.field_74370_x.func_151470_d()) {
                Scaffold.mc.field_71439_g.field_70159_w = MathHelper.func_76134_b(yaw) * 0.03f;
                Scaffold.mc.field_71439_g.field_70179_y = MathHelper.func_76126_a(yaw) * 0.03f;
            }
            if (Scaffold.mc.field_71474_y.field_74366_z.func_151470_d()) {
                Scaffold.mc.field_71439_g.field_70159_w = -MathHelper.func_76134_b(yaw) * 0.03f;
                Scaffold.mc.field_71439_g.field_70179_y = -MathHelper.func_76126_a(yaw) * 0.03f;
            }
            final BlockPos under = new BlockPos(Scaffold.mc.field_71439_g.field_70165_t, Scaffold.mc.field_71439_g.field_70163_u - 2.0, Scaffold.mc.field_71439_g.field_70161_v);
            if (Scaffold.mc.field_71441_e.func_180495_p(under).func_185904_a().func_76222_j()) {
                WorldUtils.placeBlockMainHand(under);
            }
            if (Math.abs(Scaffold.mc.field_71439_g.field_70159_w) > 0.03 && Scaffold.mc.field_71441_e.func_180495_p(new BlockPos(under.func_177958_n() + Scaffold.mc.field_71439_g.field_70159_w / Math.abs(Scaffold.mc.field_71439_g.field_70159_w), (double)(under.func_177956_o() - 1), (double)under.func_177952_p())).func_185904_a().func_76222_j()) {
                WorldUtils.placeBlockMainHand(new BlockPos(under.func_177958_n() + Scaffold.mc.field_71439_g.field_70159_w / Math.abs(Scaffold.mc.field_71439_g.field_70159_w), (double)(under.func_177956_o() - 1), (double)under.func_177952_p()));
            }
            else if (Math.abs(Scaffold.mc.field_71439_g.field_70179_y) > 0.03 && Scaffold.mc.field_71441_e.func_180495_p(new BlockPos((double)under.func_177958_n(), (double)(under.func_177956_o() - 1), under.func_177952_p() + Scaffold.mc.field_71439_g.field_70179_y / Math.abs(Scaffold.mc.field_71439_g.field_70179_y))).func_185904_a().func_76222_j()) {
                WorldUtils.placeBlockMainHand(new BlockPos((double)under.func_177958_n(), (double)(under.func_177956_o() - 1), under.func_177952_p() + Scaffold.mc.field_71439_g.field_70179_y / Math.abs(Scaffold.mc.field_71439_g.field_70179_y)));
            }
            Scaffold.mc.field_71439_g.field_71071_by.field_70461_c = oldSlot;
            return;
        }
        if (this.getSetting("Radius").getValInt() == 0) {
            final BlockPos under2 = new BlockPos(Scaffold.mc.field_71439_g.field_70165_t, Scaffold.mc.field_71439_g.field_70163_u - 1.0, Scaffold.mc.field_71439_g.field_70161_v);
            if (Scaffold.mc.field_71441_e.func_180495_p(under2).func_185904_a().func_76222_j()) {
                WorldUtils.placeBlockMainHand(under2);
            }
            if (Math.abs(Scaffold.mc.field_71439_g.field_70159_w) > 0.033 && Scaffold.mc.field_71441_e.func_180495_p(new BlockPos(under2.func_177958_n() + Scaffold.mc.field_71439_g.field_70159_w / Math.abs(Scaffold.mc.field_71439_g.field_70159_w), (double)under2.func_177956_o(), (double)under2.func_177952_p())).func_185904_a().func_76222_j()) {
                WorldUtils.placeBlockMainHand(new BlockPos(under2.func_177958_n() + Scaffold.mc.field_71439_g.field_70159_w / Math.abs(Scaffold.mc.field_71439_g.field_70159_w), (double)under2.func_177956_o(), (double)under2.func_177952_p()));
            }
            else if (Math.abs(Scaffold.mc.field_71439_g.field_70179_y) > 0.033 && Scaffold.mc.field_71441_e.func_180495_p(new BlockPos((double)under2.func_177958_n(), (double)under2.func_177956_o(), under2.func_177952_p() + Scaffold.mc.field_71439_g.field_70179_y / Math.abs(Scaffold.mc.field_71439_g.field_70179_y))).func_185904_a().func_76222_j()) {
                WorldUtils.placeBlockMainHand(new BlockPos((double)under2.func_177958_n(), (double)under2.func_177956_o(), under2.func_177952_p() + Scaffold.mc.field_71439_g.field_70179_y / Math.abs(Scaffold.mc.field_71439_g.field_70179_y)));
            }
            Scaffold.mc.field_71439_g.field_71071_by.field_70461_c = oldSlot;
            return;
        }
        final ArrayList<BlockPos> blocks = new ArrayList<BlockPos>();
        for (int x = -this.getSetting("Radius").getValInt(); x <= this.getSetting("Radius").getValInt(); ++x) {
            for (int z = -this.getSetting("Radius").getValInt(); z <= this.getSetting("Radius").getValInt(); ++z) {
                blocks.add(new BlockPos(Scaffold.mc.field_71439_g.field_70165_t + x, Scaffold.mc.field_71439_g.field_70163_u - 1.0, Scaffold.mc.field_71439_g.field_70161_v + z));
            }
        }
        for (final BlockPos x2 : blocks) {
            if (Scaffold.mc.field_71441_e.func_180495_p(x2).func_185904_a().func_76222_j()) {
                WorldUtils.placeBlockMainHand(x2);
            }
        }
        Scaffold.mc.field_71439_g.field_71071_by.field_70461_c = oldSlot;
    }
    
    @SubscribeEvent
    public void onEntityUpdate(final LivingEvent.LivingUpdateEvent event) {
        if (!this.getEnabled() || !this.getSetting("Down").getValBoolean()) {
            return;
        }
        if (event.getEntityLiving() instanceof EntityPlayer) {
            final EntityPlayer player = (EntityPlayer)event.getEntityLiving();
            if (player.func_70093_af()) {
                if (Math.abs(Scaffold.mc.field_71439_g.field_70165_t) - (int)Math.abs(Scaffold.mc.field_71439_g.field_70165_t) < 0.1 || Math.abs(Scaffold.mc.field_71439_g.field_70165_t) - (int)Math.abs(Scaffold.mc.field_71439_g.field_70165_t) > 0.9) {
                    Scaffold.mc.field_71439_g.field_70165_t = (double)Math.round(Math.abs(Scaffold.mc.field_71439_g.field_70165_t) - (int)Math.abs(Scaffold.mc.field_71439_g.field_70165_t));
                }
                if (Math.abs(Scaffold.mc.field_71439_g.field_70161_v) - (int)Math.abs(Scaffold.mc.field_71439_g.field_70161_v) < 0.1 || Math.abs(Scaffold.mc.field_71439_g.field_70161_v) - (int)Math.abs(Scaffold.mc.field_71439_g.field_70161_v) > 0.9) {
                    Scaffold.mc.field_71439_g.field_70161_v = (double)Math.round(Math.abs(Scaffold.mc.field_71439_g.field_70161_v) - (int)Math.abs(Scaffold.mc.field_71439_g.field_70161_v));
                }
            }
        }
    }
    
    @SubscribeEvent
    public void onJump(final LivingEvent.LivingJumpEvent event) {
        if (this.getEnabled() && this.getSetting("Tower").getValBoolean()) {
            final EntityPlayerSP field_71439_g = Scaffold.mc.field_71439_g;
            field_71439_g.field_70181_x += 0.1;
        }
    }
}
