package com.backdoored.hacks.ui.cookiegui;

import java.util.Iterator;
import java.util.HashMap;
import com.backdoored.utils.RenderUtils;
import java.util.Map;
import java.util.List;
import com.backdoored.gui.Category;
import org.lwjgl.opengl.GL11;
import com.backdoored.Globals;
import java.io.IOException;
import net.minecraft.util.math.Vec2f;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.Minecraft;
import com.backdoored.hacks.BaseHack;
import java.awt.Color;
import net.minecraft.client.gui.GuiScreen;

public class CookieGuiImpl extends GuiScreen
{
    public static final Color TITLE_BACKGROUND;
    public static final Color BACKGROUND;
    public static final Color ENABLED_BACKGROUND;
    public static final Color BORDER;
    private BaseHack apiHack;
    private static Minecraft mc;
    public static ScaledResolution sr;
    private Vec2f lastClicked;
    
    CookieGuiImpl(final BaseHack apiHack) {
        super();
        this.lastClicked = null;
        this.apiHack = apiHack;
    }
    
    protected void func_73864_a(final int mouseX, final int mouseY, final int mouseButton) throws IOException {
        if (mouseButton == 0) {
            this.lastClicked = new Vec2f((float)mouseX, (float)mouseY);
        }
    }
    
    public void func_73863_a(final int mouseX, final int mouseY, final float partialTicks) {
        final HashMap<Category, List<BaseHack>> categoryMap = Globals.getCategoryMap();
        GL11.glLineWidth(1.0f);
        int width = 0;
        for (final Category category : categoryMap.keySet()) {
            int thisWidth = CookieGuiImpl.mc.field_71466_p.func_78256_a(category.button.text) + 4;
            if (thisWidth > width) {
                width = thisWidth;
            }
            for (final BaseHack hack : categoryMap.get(category)) {
                thisWidth = CookieGuiImpl.mc.field_71466_p.func_78256_a(hack.name) + 4;
                if (thisWidth > width) {
                    width = thisWidth;
                }
            }
        }
        for (final Map.Entry<Category, List<BaseHack>> entry : categoryMap.entrySet()) {
            final Category category2 = entry.getKey();
            final int x = category2.button.x;
            final int y = category2.button.y;
            RenderUtils.drawRect(7, x, y, width + 6 + x, CookieGuiImpl.mc.field_71466_p.field_78288_b + 6 + y, CookieGuiImpl.TITLE_BACKGROUND.getRGB());
            RenderUtils.drawRect(2, x, y, width + 6 + x, CookieGuiImpl.mc.field_71466_p.field_78288_b + 6 + y, CookieGuiImpl.BORDER.getRGB());
            CookieGuiImpl.mc.field_71466_p.func_78276_b(entry.getKey().button.text, x + 3, y + 3, Color.WHITE.getRGB());
            int yOffset = 6 + CookieGuiImpl.mc.field_71466_p.field_78288_b;
            if (!category2.button.isVisible) {
                return;
            }
            for (final BaseHack hack2 : entry.getValue()) {
                final int thisY = y + yOffset;
                Color background = CookieGuiImpl.BACKGROUND;
                if (hack2.getEnabled()) {
                    background = CookieGuiImpl.ENABLED_BACKGROUND;
                }
                RenderUtils.drawRect(7, x, thisY, width + 6 + x, CookieGuiImpl.mc.field_71466_p.field_78288_b + 6 + thisY, background.getRGB());
                RenderUtils.drawRect(2, x, thisY, width + 6 + x, CookieGuiImpl.mc.field_71466_p.field_78288_b + 6 + thisY, CookieGuiImpl.BORDER.getRGB());
                CookieGuiImpl.mc.field_71466_p.func_78276_b(hack2.name, x + 3, thisY + 3, Color.WHITE.getRGB());
                if (this.lastClicked != null && this.lastClicked.field_189982_i > x && this.lastClicked.field_189982_i < width + 6 + x && this.lastClicked.field_189983_j > thisY && this.lastClicked.field_189983_j < CookieGuiImpl.mc.field_71466_p.field_78288_b + 6 + thisY) {
                    hack2.setEnabled(!hack2.getEnabled());
                    this.lastClicked = null;
                }
                yOffset += 6 + CookieGuiImpl.mc.field_71466_p.field_78288_b;
            }
        }
    }
    
    public boolean func_73868_f() {
        return false;
    }
    
    public void func_146281_b() {
        this.apiHack.setEnabled(false);
    }
    
    static {
        TITLE_BACKGROUND = new Color(164, 56, 55);
        BACKGROUND = new Color(128, 128, 128, 127);
        ENABLED_BACKGROUND = new Color(38, 164, 78, 255);
        BORDER = new Color(0, 0, 0, 255);
        CookieGuiImpl.mc = Globals.mc;
        CookieGuiImpl.sr = new ScaledResolution(CookieGuiImpl.mc);
    }
}
