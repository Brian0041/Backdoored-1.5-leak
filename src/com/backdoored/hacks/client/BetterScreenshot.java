package com.backdoored.hacks.client;

import net.minecraft.util.text.ITextComponent;
import net.minecraft.client.renderer.GlStateManager;
import org.lwjgl.opengl.GL11;
import org.lwjgl.BufferUtils;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.shader.Framebuffer;
import java.io.File;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.util.text.TextComponentString;
import com.backdoored.event.TakeScreenshot;
import com.backdoored.gui.CategoriesInit;
import java.nio.IntBuffer;
import com.backdoored.hacks.BaseHack;

public class BetterScreenshot extends BaseHack
{
    private IntBuffer pixelBuffer;
    private int[] pixels;
    
    public BetterScreenshot() {
        super("Better Screenshot", "Asyncronous Screenshots", CategoriesInit.CLIENT);
    }
    
    @SubscribeEvent
    public void onScreenshot(final TakeScreenshot event) {
        if (this.getEnabled()) {
            event.setCanceled(true);
            this.saveScreenshot(event.gameDirectory, event.width, event.height, event.buffer);
            event.result = (ITextComponent)new TextComponentString("Creating screenshot...");
        }
    }
    
    public void saveScreenshot(final File gameDirectory, int width, int height, final Framebuffer buffer) {
        final File ssFolder = new File(gameDirectory, "screenshots");
        ssFolder.mkdir();
        if (OpenGlHelper.func_148822_b()) {
            width = buffer.field_147622_a;
            height = buffer.field_147620_b;
        }
        final int resolution = width * height;
        if (this.pixelBuffer == null || this.pixelBuffer.capacity() < resolution) {
            this.pixelBuffer = BufferUtils.createIntBuffer(resolution);
            this.pixels = new int[resolution];
        }
        GL11.glPixelStorei(3333, 1);
        GL11.glPixelStorei(3317, 1);
        this.pixelBuffer.clear();
        if (OpenGlHelper.func_148822_b()) {
            GlStateManager.func_179144_i(buffer.field_147617_g);
            GL11.glGetTexImage(3553, 0, 32993, 33639, this.pixelBuffer);
        }
        else {
            GL11.glReadPixels(0, 0, width, height, 32993, 33639, this.pixelBuffer);
        }
        this.pixelBuffer.get(this.pixels);
        new Thread(new ScreenShotSaver(width, height, this.pixels, BetterScreenshot.mc.func_147110_a(), ssFolder), "Screenshot creation thread").start();
    }
}
