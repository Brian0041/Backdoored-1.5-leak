package com.backdoored.hacks.ui.cookiegui;

import java.util.Iterator;
import com.backdoored.hacks.BaseHack;
import com.backdoored.Globals;
import java.util.ArrayList;
import com.backdoored.hacks.gui.cookiegui.Button;
import java.util.List;
import com.backdoored.gui.Category;

public class Frame
{
    private String title;
    private Category category;
    private int x;
    private int y;
    private int width;
    private int height;
    private List<Button> buttons;
    
    public Frame(final Category category) {
        super();
        this.buttons = new ArrayList<Button>();
        this.title = category.button.text;
        this.category = category;
    }
    
    private void fillBtnList() {
        int largestWidth = 0;
        int totalHeight = 0;
        for (final BaseHack hack : Globals.hacks) {
            if (hack.category == this.category) {
                final Button btn = new Button(hack);
                totalHeight += btn.height + 1;
                final int textWidth = Globals.mc.field_71466_p.func_78256_a(btn.getText());
                if (textWidth > largestWidth) {
                    largestWidth = textWidth;
                }
                this.buttons.add(btn);
            }
        }
        this.height = Math.min(CookieGuiImpl.sr.func_78328_b(), totalHeight);
        this.width = largestWidth + 15;
    }
    
    public static void draw() {
    }
}
