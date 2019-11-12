package com.backdoored.hacks.client;

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.client.event.EntityViewRenderEvent;
import com.backdoored.gui.CategoriesInit;
import com.backdoored.setting.Setting;
import com.backdoored.hacks.BaseHack;

public class AntiFOV extends BaseHack
{
    private Setting maxFOV;
    
    public AntiFOV() {
        super("Anti FOV", CategoriesInit.CLIENT, "Cap your FOV");
        this.maxFOV = new Setting("Max FOV", this, 125, 0, 360);
    }
    
    @SubscribeEvent
    public void onRenderFOV(final EntityViewRenderEvent.FOVModifier event) {
        if (this.getEnabled()) {
            event.setFOV(Math.min(event.getFOV(), (float)this.maxFOV.getValInt()));
        }
    }
}
