package com.backdoored.hacks.client;

import com.backdoored.event.HackDisabled;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import com.backdoored.utils.Utils;
import com.backdoored.event.HackEnabled;
import com.backdoored.gui.CategoriesInit;
import com.backdoored.hacks.BaseHack;

public class MsgOnToggle extends BaseHack
{
    public MsgOnToggle() {
        super("MsgOnToggle", CategoriesInit.CLIENT, "Sends message to chat on module toggle", true, true);
    }
    
    @SubscribeEvent
    public void onHackEnabled(final HackEnabled event) {
        if (this.getEnabled()) {
            Utils.printMessage(event.hack.name + " was enabled", "red");
        }
    }
    
    @SubscribeEvent
    public void onHackDisabled(final HackDisabled event) {
        if (this.getEnabled()) {
            Utils.printMessage(event.hack.name + " was disabled", "red");
        }
    }
}
