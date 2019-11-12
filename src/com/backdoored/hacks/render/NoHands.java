package com.backdoored.hacks.render;

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.util.EnumHand;
import net.minecraftforge.client.event.RenderSpecificHandEvent;
import com.backdoored.gui.CategoriesInit;
import com.backdoored.setting.Setting;
import com.backdoored.hacks.BaseHack;

public class NoHands extends BaseHack
{
    private Setting blacklist;
    
    public NoHands() {
        super("No Hands", "Dont render your hands", CategoriesInit.RENDER);
        this.blacklist = new Setting("Blacklist", this, "No Hands", new String[] { "No Hands", "No Left", "No Right" });
    }
    
    @SubscribeEvent
    public void onRenderHand(final RenderSpecificHandEvent event) {
        if (this.getEnabled()) {
            if (this.blacklist.getValString().equals("No Hands")) {
                event.setCanceled(true);
            }
            if (this.blacklist.getValString().equals("No Left") && event.getHand() == EnumHand.OFF_HAND) {
                event.setCanceled(true);
            }
            if (this.blacklist.getValString().equals("No Right") && event.getHand() == EnumHand.MAIN_HAND) {
                event.setCanceled(true);
            }
        }
    }
}
