package com.backdoored.event;

import net.minecraft.entity.Entity;
import net.minecraftforge.fml.common.eventhandler.Cancelable;

@Cancelable
public class SetRotation extends BackdooredEvent
{
    public Entity entity;
    public float yaw;
    public float pitch;
    
    public SetRotation(final Entity entity, final float yaw, final float pitch) {
        super();
        this.yaw = yaw;
        this.pitch = pitch;
    }
}
