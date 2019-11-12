package com.backdoored.hacks.ui.hud;

import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import com.backdoored.gui.CategoriesInit;
import com.backdoored.setting.Setting;
import com.backdoored.hacks.BaseHack;

public class Hud extends BaseHack
{
    public static Hud INSTANCE;
    private HudNotif hudNotif;
    private Setting[] settings;
    
    public Hud() {
        super("Hud", CategoriesInit.UI, "Hud Overlay", true, false);
        this.hudNotif = new HudNotif();
        this.settings = new Setting[] { new Setting("Icon", this, true), new Setting("Watermark", this, true), new Setting("Module List", this, true), new Setting("Mod Align", this, "Top right", new String[] { "Top right", "Custom", "Top left", "Bot right", "Bot left" }), new Setting("Custom-x", this, 2, 0, Hud.mc.field_71443_c + 100), new Setting("Custom-y", this, 2, 0, Hud.mc.field_71440_d + 100), new Setting("Mod Order", this, "Length", new String[] { "Length", "Alphabetical" }), new Setting("Rainbow Colour", this, false) };
        Hud.INSTANCE = this;
    }
    
    @SubscribeEvent(priority = EventPriority.LOWEST)
    @Override
    public void RenderHud(final RenderGameOverlayEvent.Post event) {
        if (event.getType() != RenderGameOverlayEvent.ElementType.EXPERIENCE) {
            return;
        }
        if (this.getEnabled()) {
            this.hudNotif.render((RenderGameOverlayEvent)event, this.settings, this);
        }
    }
}
