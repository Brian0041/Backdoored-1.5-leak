package com.backdoored.hacks.ui.csgogui;

import java.awt.Color;
import com.backdoored.Globals;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.gui.Gui;

public class CsgoGuiImpl extends Gui
{
    private static ScaledResolution sr;
    
    public CsgoGuiImpl() {
        super();
    }
    
    public static void draw() {
        CsgoGuiImpl.sr = new ScaledResolution(Globals.mc);
        final int sf = CsgoGuiImpl.sr.func_78325_e();
        func_73734_a(50 / sf, 50 / sf, 50 / sf, 50 / sf, Color.BLACK.getRGB());
    }
}
