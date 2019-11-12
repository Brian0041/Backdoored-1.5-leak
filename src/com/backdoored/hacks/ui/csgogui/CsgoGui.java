package com.backdoored.hacks.ui.csgogui;

import com.backdoored.gui.CategoriesInit;
import com.backdoored.hacks.BaseHack;

public class CsgoGui extends BaseHack
{
    public CsgoGui() {
        super("Csgo Gui", CategoriesInit.UI, "Thx to swoopae#2037");
    }
    
    public void onUpdate() {
        if (this.getEnabled()) {
            CsgoGuiImpl.draw();
        }
    }
}
