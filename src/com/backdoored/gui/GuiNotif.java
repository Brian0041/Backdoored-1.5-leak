package com.backdoored.gui;

import java.io.IOException;
import net.minecraft.util.ResourceLocation;
import java.util.Iterator;
import org.lwjgl.opengl.GL11;
import com.backdoored.setting.Setting;
import com.backdoored.setting.SettingsManager;
import com.backdoored.hacks.BaseHack;
import com.backdoored.Globals;
import net.minecraft.client.gui.GuiScreen;

public class GuiNotif extends GuiScreen
{
    public static int mouseX;
    public static int mouseY;
    
    public GuiNotif() {
        super();
    }
    
    public void func_73866_w_() {
        super.func_73866_w_();
        this.field_146291_p = true;
    }
    
    public void func_73863_a(final int mouseX, final int mouseY, final float partialTicks) {
        GuiNotif.mouseX = mouseX;
        GuiNotif.mouseY = mouseY;
        for (final Category c : Globals.categories) {
            c.button.drawButton(mouseX, mouseY);
            for (final BaseHack h : Globals.hacks) {
                if (h.category == c && h.button.isVisible) {
                    h.button.drawButton(mouseX, mouseY);
                    for (final Setting s : SettingsManager.getSettingsByMod(h)) {
                        if (s.button.isVisible) {
                            s.button.drawButton(mouseX, mouseY);
                        }
                        if (s.isSlider() && s.button.isVisible) {
                            Globals.mc.field_71446_o.func_110577_a(Button.white);
                            GL11.glPushAttrib(1048575);
                            GL11.glPushMatrix();
                            GL11.glTranslatef(0.0f, 0.0f, 0.0f);
                            this.func_73729_b(s.button.x + 1, s.button.y + s.button.field_146295_m - 2, 0, 0, (int)((s.button.field_146294_l - 2) * (s.getValInt() / (s.getMax() - s.getMin()))), 1);
                            GL11.glPopMatrix();
                            GL11.glPopAttrib();
                        }
                    }
                }
            }
        }
        this.renderTooltip(mouseX, mouseY);
        super.func_73863_a(mouseX, mouseY, partialTicks);
    }
    
    private void renderTooltip(final int mouseX, final int mouseY) {
        final Button btn = this.findHoveredBtn(mouseX, mouseY);
        if (btn != null) {
            final BaseHack hack = this.btnToHack(btn);
            if (hack != null) {
                Globals.mc.field_71446_o.func_110577_a(new ResourceLocation("backdoored", "textures/white.png"));
                GL11.glPushAttrib(1048575);
                GL11.glPushMatrix();
                GL11.glTranslatef(0.0f, 0.0f, 0.0f);
                GL11.glColor4f(255.0f, 255.0f, 255.0f, 1.0f);
                final int recWidth = this.field_146289_q.func_78256_a(hack.description) + 1;
                final int recHeight = this.field_146289_q.field_78288_b + 1;
                this.func_73729_b(mouseX + 5, mouseY + 5, recWidth, recHeight, recWidth, recHeight);
                GL11.glPopMatrix();
                GL11.glPopAttrib();
                this.field_146289_q.func_78276_b(hack.description, mouseX + 6, mouseY + 6, 0);
            }
        }
    }
    
    protected void func_73864_a(final int mouseX, final int mouseY, final int mouseId) throws IOException {
        for (final Button x : Button.Objects) {
            if (!x.isVisible) {
                continue;
            }
            if (mouseX >= x.x && mouseX <= x.x + x.field_146294_l && mouseY >= x.y && mouseY <= x.y + x.field_146295_m && mouseId == 0) {
                for (final BaseHack y : Globals.hacks) {
                    if (y.name.equals(x.text)) {
                        y.setEnabled(!y.getEnabled());
                        return;
                    }
                }
                x.leftClickToggled = !x.leftClickToggled;
            }
            else {
                if (mouseX < x.x || mouseX > x.x + x.field_146294_l || mouseY < x.y || mouseY > x.y + x.field_146295_m || mouseId != 1) {
                    continue;
                }
                x.rightClickToggled = !x.rightClickToggled;
            }
        }
        super.func_73864_a(mouseX, mouseY, mouseId);
    }
    
    private BaseHack btnToHack(final Button btn) {
        for (final BaseHack y : Globals.hacks) {
            if (y.name.equals(btn.text)) {
                return y;
            }
        }
        return null;
    }
    
    private Button findHoveredBtn(final int mouseX, final int mouseY) {
        for (final Button x : Button.Objects) {
            if (!x.isVisible) {
                continue;
            }
            if (mouseX >= x.x && mouseX <= x.x + x.field_146294_l && mouseY >= x.y && mouseY <= x.y + x.field_146295_m) {
                return x;
            }
        }
        return null;
    }
    
    public boolean func_73868_f() {
        return false;
    }
    
    static {
        GuiNotif.mouseX = 0;
        GuiNotif.mouseY = 0;
    }
}
