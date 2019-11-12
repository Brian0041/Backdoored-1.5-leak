package com.backdoored.hacks.combat;

import net.minecraft.network.play.client.CPacketPlayerTryUseItem;
import net.minecraft.network.Packet;
import net.minecraft.util.math.BlockPos;
import net.minecraft.network.play.client.CPacketPlayerDigging;
import net.minecraft.item.ItemBow;
import com.backdoored.setting.Setting;
import com.backdoored.gui.CategoriesInit;
import com.backdoored.hacks.BaseHack;

public class BowSpam extends BaseHack
{
    public long lastFired;
    
    public BowSpam() {
        super("Bow Spam", CategoriesInit.COMBAT, "Spam your bow");
        this.lastFired = 0L;
        final Setting delay = new Setting("Delay", this, 0.2, 0.0, 2.0);
    }
    
    public void onUpdate() {
        if (!this.getEnabled()) {
            return;
        }
        if (BowSpam.mc.field_71439_g.field_71071_by.func_70448_g().func_77973_b() instanceof ItemBow && BowSpam.mc.field_71439_g.func_184587_cr() && BowSpam.mc.field_71439_g.func_184612_cw() >= 3) {
            BowSpam.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.field_177992_a, BowSpam.mc.field_71439_g.func_174811_aO()));
            BowSpam.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketPlayerTryUseItem(BowSpam.mc.field_71439_g.func_184600_cs()));
            BowSpam.mc.field_71439_g.func_184597_cx();
        }
    }
}
