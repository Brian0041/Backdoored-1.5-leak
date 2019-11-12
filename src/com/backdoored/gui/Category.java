package com.backdoored.gui;

import com.backdoored.Globals;

public class Category
{
    public int x;
    public int y;
    public int width;
    public int height;
    public int listPlaces;
    public Button button;
    boolean mouseHeld;
    int StartMouseX;
    int StartMouseY;
    
    public Category(final int x, final int y, final String title) {
        super();
        this.width = 100;
        this.height = 20;
        this.listPlaces = 1;
        this.mouseHeld = false;
        this.StartMouseX = 0;
        this.StartMouseY = 0;
        this.x = x;
        this.y = y;
        this.button = new Button(x, y, this.width, this.height, title, false, true, new float[] { 1.0f, 0.0f, 0.0f, 1.0f });
        Globals.categories.add(this);
    }
}
