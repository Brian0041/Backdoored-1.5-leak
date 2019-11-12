package com.backdoored.hacks.client;

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.eventhandler.Event;
import com.backdoored.event.InfiniteChat;
import com.backdoored.gui.CategoriesInit;
import com.backdoored.hacks.BaseHack;

public class InfiniteChatLength extends BaseHack
{
    public InfiniteChatLength() {
        super("InfiniteChatLength", CategoriesInit.CLIENT, "Have infinite scrolling chat");
    }
    
    @SubscribeEvent
    public void shouldInfiniteChat(final InfiniteChat event) {
        if (this.getEnabled()) {
            event.setResult(Event.Result.ALLOW);
        }
        else {
            event.setResult(Event.Result.DENY);
        }
    }
}
