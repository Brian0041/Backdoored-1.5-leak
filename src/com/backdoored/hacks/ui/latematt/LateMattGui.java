package com.backdoored.hacks.ui.latematt;

import net.minecraft.client.gui.GuiScreen;
import com.backdoored.gui.CategoriesInit;
import com.backdoored.hacks.BaseHack;

public class LateMattGui extends BaseHack
{
    private static LateMattGui INSTANCE;
    
    public LateMattGui() {
        super("LateMattGui", "Thanks latematt", CategoriesInit.UI);
        LateMattGui.INSTANCE = this;
    }
    
    public void onEnabled() {
        LateMattGui.mc.func_147108_a((GuiScreen)new GuiClick());
    }
    
    public void onDisabled() {
        LateMattGui.mc.func_147108_a((GuiScreen)null);
    }
    
    public static void onGuiClosed() {
        if (LateMattGui.INSTANCE != null) {
            LateMattGui.INSTANCE.setEnabled(false);
        }
    }
}
