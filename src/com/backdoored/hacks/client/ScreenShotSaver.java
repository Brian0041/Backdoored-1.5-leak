package com.backdoored.hacks.client;

import java.util.Date;
import net.minecraftforge.fml.common.FMLLog;
import net.minecraft.util.text.ITextComponent;
import com.backdoored.utils.Utils;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.event.ClickEvent;
import net.minecraft.util.text.TextComponentString;
import java.awt.image.RenderedImage;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import net.minecraft.client.renderer.OpenGlHelper;
import java.io.File;
import net.minecraft.client.shader.Framebuffer;
import java.text.SimpleDateFormat;

class ScreenShotSaver implements Runnable
{
    private static final SimpleDateFormat DATE_FORMAT;
    private int width;
    private int height;
    private int[] pixels;
    private Framebuffer frameBuffer;
    private File screenshotDir;
    
    ScreenShotSaver(final int width, final int height, final int[] pixels, final Framebuffer frameBuffer, final File screenshotDir) {
        super();
        this.width = width;
        this.height = height;
        this.pixels = pixels;
        this.frameBuffer = frameBuffer;
        this.screenshotDir = screenshotDir;
    }
    
    @Override
    public void run() {
        wtf(this.pixels, this.width, this.height);
        BufferedImage bufferedimage = null;
        try {
            if (OpenGlHelper.func_148822_b()) {
                bufferedimage = new BufferedImage(this.frameBuffer.field_147621_c, this.frameBuffer.field_147618_d, 1);
                int j;
                for (int k = j = this.frameBuffer.field_147620_b - this.frameBuffer.field_147618_d; k < this.frameBuffer.field_147620_b; ++k) {
                    for (int l = 0; l < this.frameBuffer.field_147621_c; ++l) {
                        bufferedimage.setRGB(l, k - j, this.pixels[k * this.frameBuffer.field_147622_a + l]);
                    }
                }
            }
            else {
                bufferedimage = new BufferedImage(this.width, this.height, 1);
                bufferedimage.setRGB(0, 0, this.width, this.height, this.pixels, 0, this.width);
            }
            final File file = getTimestampedPNGFileForDirectory(this.screenshotDir);
            ImageIO.write(bufferedimage, "png", file);
            final ITextComponent textComponent = (ITextComponent)new TextComponentString(file.getName());
            textComponent.func_150256_b().func_150241_a(new ClickEvent(ClickEvent.Action.OPEN_FILE, file.getAbsolutePath()));
            textComponent.func_150256_b().func_150228_d(Boolean.valueOf(true));
            Utils.printMessage((ITextComponent)new TextComponentTranslation("screenshot.success", new Object[] { textComponent }));
        }
        catch (Exception e) {
            FMLLog.log.info("Failed to save screenshot");
            e.printStackTrace();
            Utils.printMessage((ITextComponent)new TextComponentTranslation("screenshot.failure", new Object[] { e.getMessage() }));
        }
    }
    
    private static void wtf(final int[] idfk1, final int idfk2, final int idfk3) {
        final int[] penis = new int[idfk2];
        for (int i = idfk3 / 2, j = 0; j < i; ++j) {
            System.arraycopy(idfk1, j * idfk2, penis, 0, idfk2);
            System.arraycopy(idfk1, (idfk3 - 1 - j) * idfk2, idfk1, j * idfk2, idfk2);
            System.arraycopy(penis, 0, idfk1, (idfk3 - 1 - j) * idfk2, idfk2);
        }
    }
    
    private static File getTimestampedPNGFileForDirectory(final File gameDirectory) {
        final String s = ScreenShotSaver.DATE_FORMAT.format(new Date());
        int i = 1;
        File file1;
        while (true) {
            file1 = new File(gameDirectory, s + ((i == 1) ? "" : ("_" + i)) + ".png");
            if (!file1.exists()) {
                break;
            }
            ++i;
        }
        return file1;
    }
    
    static {
        DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd_HH.mm.ss");
    }
}
