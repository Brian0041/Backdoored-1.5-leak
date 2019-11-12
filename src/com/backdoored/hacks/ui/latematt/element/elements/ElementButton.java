package com.backdoored.hacks.ui.latematt.element.elements;

import net.minecraft.client.gui.Gui;
import com.backdoored.hacks.BaseHack;
import net.minecraft.client.Minecraft;
import com.backdoored.hacks.ui.latematt.element.Element;

public class ElementButton extends Element
{
    private static Minecraft mc;
    private final BaseHack mod;
    
    public ElementButton(final BaseHack mod, final float x, final float y, final float width, final float height) {
        super(x, y, width, height);
        this.mod = mod;
    }
    
    @Override
    public void drawElement(final int mouseX, final int mouseY) {
        Gui.func_73734_a((int)this.getX(), (int)this.getY(), (int)this.getX() + (int)this.getWidth(), (int)this.getY() + (int)this.getHeight(), this.mod.getEnabled() ? -1728053248 : 855638016);
        ElementButton.mc.field_71466_p.func_175063_a(this.mod.name, this.getX() + this.getWidth() / 2.0f - ElementButton.mc.field_71466_p.func_78256_a(this.mod.name) / 2, this.getY() + 2.0f, -1);
    }
    
    @Override
    public void keyPressed(final int key) {
    }
    
    @Override
    public void mouseClicked(final int mouseX, final int mouseY, final int mouseButton) {
        if (this.isOverElement(mouseX, mouseY) && mouseButton == 0) {
            this.mod.setEnabled(!this.mod.getEnabled());
        }
    }
    
    public BaseHack getMod() {
        return this.mod;
    }
    
    @Override
    public void onGuiClosed() {
    }
    
    static {
        ElementButton.mc = Minecraft.func_71410_x();
    }
}
