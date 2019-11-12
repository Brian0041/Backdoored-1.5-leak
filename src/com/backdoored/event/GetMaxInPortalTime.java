package com.backdoored.event;

import net.minecraft.entity.Entity;

public class GetMaxInPortalTime extends BackdooredEvent
{
    public Entity entity;
    public int maxInPortalTime;
    
    public GetMaxInPortalTime(final Entity entity, final int maxInPortalTime) {
        super();
        this.entity = entity;
        this.maxInPortalTime = maxInPortalTime;
    }
}
