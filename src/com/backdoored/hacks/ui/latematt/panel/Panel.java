package com.backdoored.hacks.ui.latematt.panel;

import java.util.Iterator;
import org.lwjgl.input.Mouse;
import net.minecraft.client.gui.Gui;
import com.backdoored.hacks.ui.latematt.element.Element;
import java.util.ArrayList;
import com.backdoored.gui.Category;
import net.minecraft.client.Minecraft;

public class Panel
{
    private static Minecraft mc;
    private float x;
    private float y;
    private float width;
    private float height;
    private float openheight;
    private boolean dragging;
    private boolean open;
    private float dragX;
    private float dragY;
    private Category type;
    private ArrayList<Element> elements;
    
    public Panel(final Category type, final ArrayList<Element> elements, final float x, final float y, final float width, final float height) {
        super();
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.openheight = height;
        this.type = type;
        this.elements = elements;
    }
    
    public float getX() {
        return this.x;
    }
    
    public void setX(final float x) {
        this.x = x;
    }
    
    public float getY() {
        return this.y;
    }
    
    public void setY(final float y) {
        this.y = y;
    }
    
    public float getWidth() {
        return this.width;
    }
    
    public void setWidth(final float width) {
        this.width = width;
    }
    
    public float getHeight() {
        return this.height;
    }
    
    public void setHeight(final float height) {
        this.height = height;
    }
    
    public float getOpenHeight() {
        return this.openheight;
    }
    
    public void setOpenHeight(final float openheight) {
        this.openheight = openheight;
    }
    
    public boolean isDragging() {
        return this.dragging;
    }
    
    public void setDragging(final boolean dragging) {
        this.dragging = dragging;
    }
    
    public boolean isOpen() {
        return this.open;
    }
    
    public void setOpen(final boolean open) {
        this.open = open;
    }
    
    public Category getType() {
        return this.type;
    }
    
    public void setType(final Category type) {
        this.type = type;
    }
    
    public ArrayList<Element> getElements() {
        return this.elements;
    }
    
    public void setElements(final ArrayList<Element> elements) {
        this.elements = elements;
    }
    
    public void drawPanel(final int mouseX, final int mouseY) {
        Gui.func_73734_a((int)this.getX(), (int)this.getY(), (int)this.getX() + (int)this.getWidth(), (int)this.getY() + (int)this.getHeight(), 1426063360);
        Panel.mc.field_71466_p.func_175063_a(this.type.button.text, this.getX() + this.getWidth() / 2.0f - Panel.mc.field_71466_p.func_78256_a(this.type.button.text) / 2, this.getY() + 3.0f, -1);
        if (this.isOpen()) {
            Gui.func_73734_a((int)this.getX() + 2, (int)this.getY() + (int)this.getOpenHeight() - 1, (int)this.getX() + (int)this.getWidth() - 2, (int)this.getY() + (int)this.getOpenHeight(), 1426063360);
        }
        if (this.isDragging()) {
            this.x = mouseX + this.dragX;
            this.y = mouseY + this.dragY;
            if (!Mouse.isButtonDown(0)) {
                this.setDragging(false);
            }
        }
        if (this.isOpen()) {
            float y = this.getOpenHeight();
            for (final Element element : this.elements) {
                element.drawElement(mouseX, mouseY);
                element.setX(this.getX() + 2.0f);
                element.setY(this.getY() + y + 2.0f);
                y += 13.0f;
            }
            this.setHeight(y + 3.0f);
        }
        else {
            this.setHeight(this.getOpenHeight());
        }
    }
    
    public void keyPressed(final int key) {
        if (this.isOpen()) {
            for (final Element element : this.elements) {
                element.keyPressed(key);
            }
        }
    }
    
    public void mouseClicked(final int mouseX, final int mouseY, final int mouseButton) {
        if (this.isOverPanel(mouseX, mouseY)) {
            if (mouseButton == 0) {
                this.dragX = this.getX() - mouseX;
                this.dragY = this.getY() - mouseY;
                this.dragging = true;
            }
            else if (mouseButton == 1) {
                this.open = !this.open;
            }
        }
        if (this.isOpen()) {
            for (final Element element : this.elements) {
                element.mouseClicked(mouseX, mouseY, mouseButton);
            }
        }
    }
    
    public boolean isOverPanel(final int mouseX, final int mouseY) {
        return mouseX > this.getX() && mouseY > this.getY() && mouseX < this.getX() + this.getWidth() && mouseY < this.getY() + this.getOpenHeight();
    }
    
    public void onGuiClosed() {
        this.dragging = false;
    }
    
    static {
        Panel.mc = Minecraft.func_71410_x();
    }
}
