package com.backdoored.hacks.player;

import com.backdoored.event.GetMaxInPortalTime;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import com.backdoored.event.GetPortalCooldown;
import com.backdoored.gui.CategoriesInit;
import com.backdoored.setting.Setting;
import com.backdoored.hacks.BaseHack;

public class InstantPortalReuse extends BaseHack
{
    private Setting cooldown;
    private Setting waitTime;
    
    public InstantPortalReuse() {
        super("Instant Portal", "ez pz", CategoriesInit.PLAYER);
        this.cooldown = new Setting("Cooldown", this, 2, 0, 10);
        this.waitTime = new Setting("Wait Time", this, 2, 0, 80);
    }
    
    @SubscribeEvent
    public void getPortalCooldown(final GetPortalCooldown event) {
        if (this.getEnabled() && (event.player == null || event.player.func_110124_au().equals(InstantPortalReuse.mc.field_71439_g.func_110124_au()))) {
            event.cooldown = this.cooldown.getValInt();
        }
    }
    
    @SubscribeEvent
    public void getMaxInPortalTime(final GetMaxInPortalTime event) {
        if (this.getEnabled() && (event.entity == null || event.entity.func_110124_au().equals(InstantPortalReuse.mc.field_71439_g.func_110124_au()))) {
            event.maxInPortalTime = this.waitTime.getValInt();
        }
    }
}
