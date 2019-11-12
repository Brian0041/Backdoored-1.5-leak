package com.backdoored.hacks.gui.cookiegui;

import com.backdoored.hacks.BaseHack;
import java.awt.Color;

public class Button
{
    public static Color ENABLED;
    public static Color DISABLED;
    public int width;
    public int height;
    public int x;
    public int y;
    private BaseHack hack;
    
    public Button(final BaseHack hack) {
        super();
        this.hack = hack;
    }
    
    public void updatePos(final int x, final int y) {
        this.x = x;
        this.y = y;
    }
    
    public boolean isEnabled() {
        return this.hack.getEnabled();
    }
    
    public void toggle() {
        this.hack.setEnabled(!this.hack.getEnabled());
    }
    
    public String getText() {
        return this.hack.name;
    }
    
    public Color getColour() {
        return this.hack.getEnabled() ? Button.ENABLED : Button.DISABLED;
    }
    
    static {
        Button.ENABLED = Color.GREEN;
        Button.DISABLED = Color.GRAY;
    }
}
