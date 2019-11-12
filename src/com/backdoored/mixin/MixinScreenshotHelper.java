package com.backdoored.mixin;

import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.common.MinecraftForge;
import com.backdoored.event.TakeScreenshot;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.client.shader.Framebuffer;
import javax.annotation.Nullable;
import java.io.File;
import net.minecraft.util.ScreenShotHelper;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ ScreenShotHelper.class })
public class MixinScreenshotHelper
{
    public MixinScreenshotHelper() {
        super();
    }
    
    @Redirect(method = { "Lnet/minecraft/util/ScreenShotHelper;saveScreenshot(Ljava/io/File;IILnet/minecraft/client/shader/Framebuffer;)Lnet/minecraft/util/text/ITextComponent;" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/util/ScreenShotHelper;saveScreenshot(Ljava/io/File;Ljava/lang/String;IILnet/minecraft/client/shader/Framebuffer;)Lnet/minecraft/util/text/ITextComponent;"))
    private static ITextComponent saveScreenshot(final File gameDirectory, @Nullable final String screenshotName, final int width, final int height, final Framebuffer buffer) {
        final TakeScreenshot event = new TakeScreenshot(gameDirectory, screenshotName, width, height, buffer);
        MinecraftForge.EVENT_BUS.post((Event)event);
        if (!event.isCanceled()) {
            return ScreenShotHelper.func_148259_a(gameDirectory, (String)null, width, height, buffer);
        }
        return event.result;
    }
}
