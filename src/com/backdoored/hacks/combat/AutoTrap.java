package com.backdoored.hacks.combat;

import java.util.Iterator;
import net.minecraft.util.math.BlockPos;
import net.minecraft.entity.Entity;
import com.backdoored.utils.FriendUtils;
import net.minecraft.entity.player.EntityPlayer;
import com.backdoored.utils.Utils;
import com.backdoored.utils.WorldUtils;
import net.minecraft.item.Item;
import net.minecraft.init.Blocks;
import com.backdoored.gui.CategoriesInit;
import com.backdoored.setting.Setting;
import com.backdoored.hacks.BaseHack;

public class AutoTrap extends BaseHack
{
    private Setting range;
    private Setting delay;
    private int blocksThisTick;
    private boolean placingBlocks;
    
    public AutoTrap() {
        super("Auto Trap", CategoriesInit.COMBAT, "Trap nearby players");
        this.blocksThisTick = 0;
        this.placingBlocks = false;
        this.range = new Setting("Range", this, 8.0, 0.0, 15.0);
        this.delay = new Setting("Tick delay", this, 5, 0, 10);
    }
    
    public void onEnabled() {
        this.placingBlocks = true;
    }
    
    public void onDisabled() {
        this.placingBlocks = false;
    }
    
    public void onUpdate() {
        if (!this.placingBlocks) {
            return;
        }
        if (this.blocksThisTick % this.delay.getValInt() != 0) {
            ++this.blocksThisTick;
            return;
        }
        final int oldSlot = AutoTrap.mc.field_71439_g.field_71071_by.field_70461_c;
        final int newSlot = WorldUtils.findItem(Item.func_150898_a(Blocks.field_150343_Z));
        if (newSlot == -1) {
            this.setEnabled(this.placingBlocks = false);
            Utils.printMessage("Obsidian was not found in your hotbar!", "red");
            return;
        }
        AutoTrap.mc.field_71439_g.field_71071_by.field_70461_c = newSlot;
        for (final EntityPlayer player : AutoTrap.mc.field_71441_e.field_73010_i) {
            if (FriendUtils.isFriend(player)) {
                continue;
            }
            if (AutoTrap.mc.field_71439_g.func_70032_d((Entity)player) > this.range.getValDouble() || player == AutoTrap.mc.field_71439_g || FriendUtils.isFriend(player)) {
                continue;
            }
            final BlockPos[] array;
            final BlockPos[] blocks = array = new BlockPos[] { WorldUtils.getRelativeBlockPos(player, 1, 0, 1), WorldUtils.getRelativeBlockPos(player, -1, 0, -1), WorldUtils.getRelativeBlockPos(player, 1, 0, -1), WorldUtils.getRelativeBlockPos(player, -1, 0, 1), WorldUtils.getRelativeBlockPos(player, 1, 1, 1), WorldUtils.getRelativeBlockPos(player, -1, 1, -1), WorldUtils.getRelativeBlockPos(player, 1, 1, -1), WorldUtils.getRelativeBlockPos(player, -1, 1, 1), WorldUtils.getRelativeBlockPos(player, 1, 1, 0), WorldUtils.getRelativeBlockPos(player, -1, 1, 0), WorldUtils.getRelativeBlockPos(player, 0, 1, 1), WorldUtils.getRelativeBlockPos(player, 0, 1, -1), WorldUtils.getRelativeBlockPos(player, 0, 2, 1), WorldUtils.getRelativeBlockPos(player, 0, 2, 0) };
            for (final BlockPos pos : array) {
                if (AutoTrap.mc.field_71441_e.func_180495_p(pos).func_185904_a().func_76222_j()) {
                    WorldUtils.placeBlockMainHand(pos);
                    ++this.blocksThisTick;
                    return;
                }
            }
        }
        AutoTrap.mc.field_71439_g.field_71071_by.field_70461_c = oldSlot;
    }
}
