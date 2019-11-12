package com.backdoored.hacks.chatbot;

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.network.play.server.SPacketChat;
import com.backdoored.event.PacketRecieved;
import com.backdoored.utils.Utils;
import com.backdoored.gui.CategoriesInit;
import com.backdoored.hacks.BaseHack;

public class ChatBot extends BaseHack
{
    private ChatBotScriptHandler handler;
    private long lastSendMessage;
    
    public ChatBot() {
        super("Chat Bot", "Scriptable chat bot", CategoriesInit.CHATBOT);
        this.lastSendMessage = 0L;
    }
    
    public void onEnabled() {
        try {
            this.handler = new ChatBotScriptHandler();
        }
        catch (Exception e) {
            this.setEnabled(false);
            Utils.printMessage("Failed to initialise chatbot script: " + e.getMessage(), "red");
            e.printStackTrace();
        }
    }
    
    @SubscribeEvent
    public void onPacketReceived(final PacketRecieved event) {
        if (this.getEnabled() && event.packet instanceof SPacketChat && System.currentTimeMillis() - this.lastSendMessage > 5000L) {
            final SPacketChat packet = (SPacketChat)event.packet;
            this.handleChat(packet.func_148915_c().func_150260_c(), packet.func_192590_c().name());
            this.lastSendMessage = System.currentTimeMillis();
        }
    }
    
    private void handleChat(final String msg, final String type) {
        if (ChatBot.mc.field_71439_g == null || msg.startsWith("<" + ChatBot.mc.field_71439_g.func_70005_c_()) || msg.startsWith("<" + ChatBot.mc.field_71439_g.getDisplayNameString())) {
            return;
        }
        try {
            if (this.handler == null) {
                this.handler = new ChatBotScriptHandler();
            }
            final String output = this.handler.onChatRecieved(msg, type);
            if (output != null) {
                ChatBot.mc.field_71439_g.func_71165_d(output);
            }
        }
        catch (Exception e) {
            this.setEnabled(false);
            Utils.printMessage("Failure while invoking chatbot script: " + e.getMessage(), "red");
            e.printStackTrace();
        }
    }
}
