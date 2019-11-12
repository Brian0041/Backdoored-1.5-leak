package com.backdoored.extensions;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderGlobal;

public class RenderGlobalWrapper extends RenderGlobal
{
    public RenderGlobalWrapper(final Minecraft mcIn) {
        super(mcIn);
    }
}
