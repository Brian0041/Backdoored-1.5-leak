package com.backdoored.event;

import net.minecraft.client.renderer.culling.ICamera;
import net.minecraft.entity.Entity;

public class RenderOutline extends BackdooredEvent
{
    public Entity target;
    public Entity viewer;
    public ICamera camera;
    
    public RenderOutline(final Entity target, final Entity viewer, final ICamera camera) {
        super();
        this.target = target;
        this.viewer = viewer;
        this.camera = camera;
    }
}
