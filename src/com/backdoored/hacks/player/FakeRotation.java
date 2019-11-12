package com.backdoored.hacks.player;

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import com.backdoored.utils.Utils;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import net.minecraft.network.play.client.CPacketPlayer;
import com.backdoored.event.PacketSent;
import com.backdoored.gui.CategoriesInit;
import com.backdoored.hacks.BaseHack;

public class FakeRotation extends BaseHack
{
    public FakeRotation() {
        super("Fake Rotation", CategoriesInit.PLAYER, "Fake your rotation");
    }
    
    @SubscribeEvent
    public void onPacketSent(final PacketSent event) {
        if (!this.getEnabled()) {
            return;
        }
        if (event.packet instanceof CPacketPlayer) {
            try {
                ObfuscationReflectionHelper.setPrivateValue((Class)CPacketPlayer.class, (Object)event.packet, (Object)(-90), new String[] { "pitch", "field_149473_f" });
            }
            catch (Exception e) {
                Utils.printMessage("Disabled fake rotation due to error: " + e.toString(), "red");
                e.printStackTrace();
            }
        }
    }
}
