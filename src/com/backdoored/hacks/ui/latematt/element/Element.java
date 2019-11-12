package com.backdoored.hacks.ui.latematt.element;

public abstract class Element
{
    private float x;
    private float y;
    private float width;
    private float height;
    
    public Element(final float x, final float y, final float width, final float height) {
        super();
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
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
    
    public abstract void drawElement(final int p0, final int p1);
    
    public abstract void keyPressed(final int p0);
    
    public abstract void mouseClicked(final int p0, final int p1, final int p2);
    
    public abstract void onGuiClosed();
    
    public boolean isOverElement(final int mouseX, final int mouseY) {
        return mouseX > this.getX() && mouseY > this.getY() && mouseX < this.getX() + this.getWidth() && mouseY < this.getY() + this.getHeight();
    }
}
