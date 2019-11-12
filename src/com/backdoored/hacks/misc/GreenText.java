package com.backdoored.hacks.misc;

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.client.event.ClientChatEvent;
import com.backdoored.gui.CategoriesInit;
import com.backdoored.hacks.BaseHack;

public class GreenText extends BaseHack
{
    public GreenText() {
        super("GreenText", CategoriesInit.MISC, "Prepend text with >");
    }
    
    @SubscribeEvent
    public void ClientChatEvent(final ClientChatEvent event) {
        if (!this.getEnabled() || event.getMessage().charAt(0) == '/' || event.getMessage().charAt(0) == '!') {
            return;
        }
        event.setMessage(">" + event.getMessage());
    }
}
