package com.backdoored.hacks.ui.cookiegui;

import net.minecraft.client.gui.GuiScreen;
import com.backdoored.gui.CategoriesInit;
import com.backdoored.hacks.BaseHack;

public class CookieGui extends BaseHack
{
    public CookieGui() {
        super("Gui 2", CategoriesInit.UI, "New ui");
    }
    
    public void onEnabled() {
        CookieGui.mc.func_147108_a((GuiScreen)new CookieGuiImpl(this));
    }
    
    public void onDisabled() {
        CookieGui.mc.func_147108_a((GuiScreen)null);
    }
}
