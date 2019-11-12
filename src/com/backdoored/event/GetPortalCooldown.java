package com.backdoored.event;

import net.minecraft.entity.player.EntityPlayer;

public class GetPortalCooldown extends BackdooredEvent
{
    public int cooldown;
    public EntityPlayer player;
    
    public GetPortalCooldown(final int cooldown, final EntityPlayer player) {
        super();
        this.cooldown = cooldown;
        this.player = player;
    }
}
