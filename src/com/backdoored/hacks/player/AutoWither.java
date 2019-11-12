package com.backdoored.hacks.player;

import net.minecraft.util.EnumFacing;
import com.backdoored.Globals;
import com.backdoored.utils.Utils;
import com.backdoored.utils.WorldUtils;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import com.backdoored.event.ServerTick;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.init.Blocks;
import com.backdoored.gui.CategoriesInit;
import net.minecraft.util.math.BlockPos;
import net.minecraft.item.Item;
import com.backdoored.hacks.BaseHack;

public class AutoWither extends BaseHack
{
    private Item SOUL_SAND;
    private Item WITHER_SKULL;
    private BlockPos basePos;
    private int stage;
    
    public AutoWither() {
        super("Auto Wither", CategoriesInit.PLAYER, "1 tick withers");
        this.SOUL_SAND = new ItemStack(Blocks.field_150425_aM).func_77973_b();
        this.WITHER_SKULL = new ItemStack((Block)Blocks.field_150465_bP).func_77973_b();
        this.basePos = new BlockPos(0, 0, 0);
        this.stage = -1;
    }
    
    public void onEnabled() {
        ++this.stage;
        this.stage0();
        ++this.stage;
    }
    
    @SubscribeEvent
    public void onServerTick(final ServerTick event) {
        if (!this.getEnabled() || this.stage > 1) {
            this.stage = -1;
            this.setEnabled(false);
            return;
        }
        if (this.stage == 0) {
            this.stage0();
        }
        if (this.stage == 1) {
            this.stage1();
            this.stage = -1;
            this.setEnabled(false);
            return;
        }
        ++this.stage;
    }
    
    private boolean stage0() {
        if (AutoWither.mc.field_71476_x == null || AutoWither.mc.field_71476_x.field_178784_b == null) {
            this.basePos = AutoWither.mc.field_71439_g.func_180425_c().func_177982_a(2, 0, 0);
        }
        else {
            this.basePos = AutoWither.mc.field_71476_x.func_178782_a().func_177972_a(AutoWither.mc.field_71476_x.field_178784_b);
        }
        final int soulSandI = WorldUtils.findItem(this.SOUL_SAND);
        final int skullI = this.getSkull();
        if (skullI == -1 || soulSandI == -1) {
            final String missing = (skullI == -1) ? "Wither Skull" : "Soul Sand";
            Utils.printMessage(missing + " was not found in your hotbar!", "red");
            this.setEnabled(false);
            return false;
        }
        AutoWither.mc.field_71439_g.field_71071_by.field_70461_c = WorldUtils.findItem(this.SOUL_SAND);
        WorldUtils.placeBlockMainHand(this.basePos);
        if (this.isX()) {
            WorldUtils.placeBlockMainHand(this.basePos.func_177982_a(0, 1, 0));
            WorldUtils.placeBlockMainHand(this.basePos.func_177982_a(1, 1, 0));
            WorldUtils.placeBlockMainHand(this.basePos.func_177982_a(-1, 1, 0));
        }
        else {
            WorldUtils.placeBlockMainHand(this.basePos.func_177982_a(0, 1, 0));
            WorldUtils.placeBlockMainHand(this.basePos.func_177982_a(0, 1, 1));
            WorldUtils.placeBlockMainHand(this.basePos.func_177982_a(0, 1, -1));
        }
        return true;
    }
    
    private boolean stage1() {
        final int skullI = this.getSkull();
        if (skullI != -1) {
            AutoWither.mc.field_71439_g.field_71071_by.field_70461_c = skullI;
            if (this.isX()) {
                WorldUtils.placeBlockMainHand(this.basePos.func_177982_a(0, 2, 0));
                WorldUtils.placeBlockMainHand(this.basePos.func_177982_a(1, 2, 0));
                WorldUtils.placeBlockMainHand(this.basePos.func_177982_a(-1, 2, 0));
            }
            else {
                WorldUtils.placeBlockMainHand(this.basePos.func_177982_a(0, 2, 0));
                WorldUtils.placeBlockMainHand(this.basePos.func_177982_a(0, 2, 1));
                WorldUtils.placeBlockMainHand(this.basePos.func_177982_a(0, 2, -1));
            }
            return true;
        }
        return false;
    }
    
    private int getSkull() {
        for (int i = 0; i < 9; ++i) {
            final ItemStack stack = Globals.mc.field_71439_g.field_71071_by.func_70301_a(i);
            if (stack.func_77973_b().func_77653_i(stack).equals("Wither Skeleton Skull")) {
                return i;
            }
        }
        return -1;
    }
    
    private boolean isX() {
        final EnumFacing facing = AutoWither.mc.field_71439_g.func_174811_aO();
        return facing != EnumFacing.EAST && facing != EnumFacing.WEST;
    }
}
