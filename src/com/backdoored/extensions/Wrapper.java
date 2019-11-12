package com.backdoored.extensions;

import com.backdoored.Globals;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.gui.FontRenderer;

public class Wrapper
{
    public static final FontRenderer fontRenderer;
    public static final Class<GlStateManager> GlStateManager;
    public static final RenderGlobalWrapper RenderGlobalWrapper;
    
    public Wrapper() {
        super();
    }
    
    static {
        fontRenderer = Globals.mc.field_71466_p;
        GlStateManager = GlStateManager.class;
        RenderGlobalWrapper = new RenderGlobalWrapper(Globals.mc);
    }
}
