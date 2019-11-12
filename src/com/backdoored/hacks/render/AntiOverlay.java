package com.backdoored.hacks.render;

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.client.event.RenderBlockOverlayEvent;
import com.backdoored.gui.CategoriesInit;
import com.backdoored.setting.Setting;
import com.backdoored.hacks.BaseHack;

public class AntiOverlay extends BaseHack
{
    public Setting allowFire;
    public Setting allowBlocks;
    public Setting allowWater;
    
    public AntiOverlay() {
        super("Anti Overlay", CategoriesInit.RENDER, "Prevents Overlay");
        this.allowFire = new Setting("Fire", this, true);
        this.allowBlocks = new Setting("Blocks", this, true);
        this.allowWater = new Setting("Water", this, true);
    }
    
    @SubscribeEvent
    public void renderBlockOverlay(final RenderBlockOverlayEvent event) {
        if (!this.getEnabled()) {
            return;
        }
        boolean shouldRender = false;
        switch (event.getOverlayType()) {
            case FIRE: {
                if (this.allowFire.getValBoolean()) {
                    shouldRender = true;
                    break;
                }
                break;
            }
            case BLOCK: {
                if (this.allowBlocks.getValBoolean()) {
                    shouldRender = true;
                    break;
                }
                break;
            }
            case WATER: {
                if (this.allowWater.getValBoolean()) {
                    shouldRender = true;
                    break;
                }
                break;
            }
        }
        event.setCanceled(shouldRender);
    }
}
