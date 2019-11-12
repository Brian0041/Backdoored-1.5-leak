package com.backdoored.hacks.misc;

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.util.text.ChatType;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import com.backdoored.setting.Setting;
import com.backdoored.gui.CategoriesInit;
import com.backdoored.hacks.BaseHack;

public class AutoReply extends BaseHack
{
    private final String FILE_PATH = "Ebic/autoreplies.txt";
    private String[] replies;
    
    public AutoReply() {
        super("Auto Reply", CategoriesInit.MISC, "Tell those scrubs whos boss");
        this.replies = new String[0];
        new Setting("Text", this, "Shut up scrub", new String[] { "Shut up scrub" });
    }
    
    @SubscribeEvent
    public void onClientChat(final ClientChatReceivedEvent event) {
        if (!this.getEnabled() || !event.getType().equals((Object)ChatType.CHAT)) {
            return;
        }
        System.out.println("Message recieved: " + event.getMessage().func_150260_c());
        final String[] msg = event.getMessage().func_150260_c().split(" whispers: ");
        AutoReply.mc.field_71439_g.func_71165_d("/r " + msg[0] + " " + this.getSetting("Text").getValString());
    }
}
