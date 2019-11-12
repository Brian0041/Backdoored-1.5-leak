package com.backdoored.hacks.ui.latematt;

import java.io.IOException;
import java.util.Iterator;
import com.backdoored.hacks.ui.latematt.element.elements.ElementButton;
import com.backdoored.hacks.BaseHack;
import com.backdoored.Globals;
import com.backdoored.hacks.ui.latematt.element.Element;
import com.backdoored.gui.Category;
import com.backdoored.gui.CategoriesInit;
import com.backdoored.hacks.ui.latematt.panel.Panel;
import java.util.ArrayList;
import net.minecraft.client.gui.GuiScreen;

public class GuiClick extends GuiScreen
{
    private final ArrayList<Panel> panels;
    
    public GuiClick() {
        super();
        float x = 4.0f;
        float y = 4.0f;
        this.panels = new ArrayList<Panel>();
        for (final Category type : CategoriesInit.getAll()) {
            y = 18.0f;
            final ArrayList<Element> elements = new ArrayList<Element>();
            for (final BaseHack mod : Globals.hacks) {
                if (mod.category == type) {
                    elements.add(new ElementButton(mod, x + 2.0f, y + 2.0f, 96.0f, 12.0f));
                    y += 13.0f;
                }
            }
            this.panels.add(new Panel(type, elements, x, 4.0f, 100.0f, 14.0f));
            x += 102.0f;
        }
    }
    
    public boolean func_73868_f() {
        return false;
    }
    
    protected void func_73864_a(final int mouseX, final int mouseY, final int mouseButton) throws IOException {
        super.func_73864_a(mouseX, mouseY, mouseButton);
        for (final Panel panel : this.panels) {
            panel.mouseClicked(mouseX, mouseY, mouseButton);
        }
    }
    
    public void func_73863_a(final int mouseX, final int mouseY, final float partialTicks) {
        super.func_73863_a(mouseX, mouseY, partialTicks);
        for (final Panel panel : this.panels) {
            panel.drawPanel(mouseX, mouseY);
        }
    }
    
    protected void func_73869_a(final char typedChar, final int keyCode) throws IOException {
        super.func_73869_a(typedChar, keyCode);
        for (final Panel panel : this.panels) {
            panel.keyPressed(keyCode);
        }
    }
    
    public void func_146281_b() {
        LateMattGui.onGuiClosed();
    }
}
