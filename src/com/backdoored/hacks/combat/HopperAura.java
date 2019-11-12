package com.backdoored.hacks.combat;

import net.minecraft.util.math.BlockPos;
import java.util.Iterator;
import net.minecraft.network.Packet;
import net.minecraft.util.EnumFacing;
import net.minecraft.network.play.client.CPacketPlayerDigging;
import com.backdoored.utils.WorldUtils;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import net.minecraft.tileentity.TileEntityHopper;
import java.util.List;
import com.backdoored.setting.Setting;
import com.backdoored.gui.CategoriesInit;
import java.util.ArrayList;
import com.backdoored.hacks.BaseHack;

public class HopperAura extends BaseHack
{
    private ArrayList<String> s;
    private int[] picks;
    
    public HopperAura() {
        super("HopperAura", CategoriesInit.COMBAT, "Peform Actions on nearby hoppers");
        this.s = new ArrayList<String>() {
            final /* synthetic */ HopperAura this$0;
            
            HopperAura$1() {
                this.this$0 = this$0;
                super();
            }
        };
        this.picks = new int[] { 278, 285, 274, 270, 257 };
        new Setting("Distance", this, 5.0, 1.0, 10.0);
        new Setting("LockRotations", this, true);
    }
    
    public void onUpdate() {
        if (!this.getEnabled()) {
            return;
        }
        final List<TileEntity> hoppers = (List<TileEntity>)HopperAura.mc.field_71441_e.field_147482_g.stream().filter(p -> p instanceof TileEntityHopper).collect(Collectors.toList());
        if (hoppers.size() > 0) {
            for (final TileEntity hopper : hoppers) {
                final BlockPos hopperPos = hopper.func_174877_v();
                if (HopperAura.mc.field_71439_g.func_70011_f((double)hopperPos.func_177958_n(), (double)hopperPos.func_177956_o(), (double)hopperPos.func_177952_p()) <= this.getSetting("Distance").getValDouble()) {
                    for (final int x : this.picks) {
                        final int slot = WorldUtils.findItem(Item.func_150899_d(x));
                        if (slot != -1) {
                            HopperAura.mc.field_71439_g.field_71071_by.field_70461_c = slot;
                            if (this.getSetting("LockRotations").getValBoolean()) {
                                WorldUtils.lookAtBlock(hopperPos);
                            }
                            HopperAura.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.START_DESTROY_BLOCK, hopper.func_174877_v(), EnumFacing.UP));
                            HopperAura.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.STOP_DESTROY_BLOCK, hopper.func_174877_v(), EnumFacing.UP));
                            return;
                        }
                    }
                }
            }
        }
    }
    
    public void onEnabled() {
    }
    
    public void onDisabled() {
    }
    
    private static /* synthetic */ boolean lambda$onUpdate$0(final TileEntity p) {
        return p instanceof TileEntityHopper;
    }
}
