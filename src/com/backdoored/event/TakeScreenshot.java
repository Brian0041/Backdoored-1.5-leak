package com.backdoored.event;

import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.client.shader.Framebuffer;
import java.io.File;
import net.minecraftforge.fml.common.eventhandler.Cancelable;

@Cancelable
public class TakeScreenshot extends BackdooredEvent
{
    public File gameDirectory;
    public String screenshotName;
    public int width;
    public int height;
    public Framebuffer buffer;
    public ITextComponent result;
    
    public TakeScreenshot(final File gameDirectory, final String screenshotName, final int width, final int height, final Framebuffer buffer) {
        super();
        this.gameDirectory = gameDirectory;
        this.screenshotName = screenshotName;
        this.width = width;
        this.height = height;
        this.buffer = buffer;
        this.result = (ITextComponent)new TextComponentString("Screenshot meant to be here?");
    }
}
