package com.backdoored.hacks.client;

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import com.backdoored.event.RenderTab;
import com.backdoored.gui.CategoriesInit;
import com.backdoored.hacks.BaseHack;

public class ExtraTab extends BaseHack
{
    public static ExtraTab INSTANCE;
    
    public ExtraTab() {
        super("Extra Tab", CategoriesInit.CLIENT, "Display full tab menu");
        ExtraTab.INSTANCE = this;
    }
    
    @SubscribeEvent
    public void onTabRender(final RenderTab event) {
        if (this.getEnabled()) {
            event.size = event.players.size();
        }
    }
}
