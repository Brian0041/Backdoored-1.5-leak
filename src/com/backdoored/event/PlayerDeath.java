package com.backdoored.event;

import net.minecraft.entity.player.EntityPlayer;

public class PlayerDeath extends BackdooredEvent
{
    private EntityPlayer player;
    
    public PlayerDeath(final EntityPlayer player) {
        super();
        this.player = player;
    }
    
    public EntityPlayer getPlayer() {
        return this.player;
    }
}
