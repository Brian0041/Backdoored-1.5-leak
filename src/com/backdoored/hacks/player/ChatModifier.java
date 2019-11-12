package com.backdoored.hacks.player;

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import com.backdoored.utils.Utils;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import net.minecraft.network.play.client.CPacketChatMessage;
import com.backdoored.event.PacketSent;
import com.backdoored.setting.Setting;
import com.backdoored.utils.chat.Fancy;
import com.backdoored.utils.chat.Disabled;
import com.backdoored.utils.chat.L33t;
import com.backdoored.utils.chat.JustLearntEngrish;
import com.backdoored.utils.chat.Chav;
import com.backdoored.utils.chat.Reverse;
import com.backdoored.utils.chat.Emphasize;
import com.backdoored.gui.CategoriesInit;
import com.backdoored.utils.chat.Mutator;
import com.backdoored.hacks.BaseHack;

public class ChatModifier extends BaseHack
{
    private Mutator[] mutators;
    
    public ChatModifier() {
        super("Chat Modifier", CategoriesInit.PLAYER, "Modify your chat messages");
        this.mutators = new Mutator[] { new Emphasize(), new Reverse(), new Chav(), new JustLearntEngrish(), new L33t(), new Disabled(), new Fancy() };
        new Setting("Emphasize", this, false);
        new Setting("Reverse", this, false);
        new Setting("Chav", this, false);
        new Setting("JustLearntEngrish", this, false);
        new Setting("L33t", this, false);
        new Setting("Disabled", this, false);
        new Setting("Fancy", this, false);
    }
    
    @SubscribeEvent
    public void onPacket(final PacketSent event) {
        if (event.packet instanceof CPacketChatMessage) {
            System.out.println("Was packet");
            if (this.getEnabled()) {
                final CPacketChatMessage packet = (CPacketChatMessage)event.packet;
                String msg = packet.func_149439_c();
                final boolean isCmd = msg.startsWith("/") || msg.startsWith("!");
                if (!isCmd) {
                    for (final Mutator mutator : this.mutators) {
                        try {
                            if (this.getSetting(mutator.getName()).getValBoolean()) {
                                msg = mutator.mutate(msg);
                            }
                        }
                        catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
                try {
                    ObfuscationReflectionHelper.setPrivateValue((Class)CPacketChatMessage.class, (Object)packet, (Object)msg, new String[] { "message", "field_149440_a" });
                }
                catch (Exception e2) {
                    Utils.printMessage("Disabled chat modifier due to error: " + e2.getMessage());
                    this.setEnabled(false);
                    e2.printStackTrace();
                }
            }
        }
    }
}
