package com.backdoored.hacks.movement;

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.client.event.PlayerSPPushOutOfBlocksEvent;
import com.backdoored.event.PacketSent;
import com.backdoored.gui.CategoriesInit;
import com.backdoored.hacks.BaseHack;

public class Phase extends BaseHack
{
    public Phase() {
        super("Phase", CategoriesInit.MOVEMENT, "Phase through blocks(experimental)");
    }
    
    public void onUpdate() {
        if (!this.getEnabled()) {
            return;
        }
        Phase.mc.field_71439_g.field_70145_X = true;
    }
    
    @SubscribeEvent
    public void onPacketSent(final PacketSent event) {
        if (!this.getEnabled()) {
            return;
        }
        if (event.packet instanceof PlayerSPPushOutOfBlocksEvent) {
            event.setCanceled(true);
        }
    }
    
    @SubscribeEvent
    public void onPlayerPushOut(final PlayerSPPushOutOfBlocksEvent event) {
        if (!this.getEnabled()) {
            return;
        }
        event.setCanceled(true);
    }
}
