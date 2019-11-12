package com.backdoored.hacks.ui;

import net.minecraft.client.gui.GuiScreen;
import com.backdoored.gui.GuiNotif;
import com.backdoored.setting.SettingsManager;
import com.backdoored.setting.Setting;
import org.lwjgl.input.Keyboard;
import com.backdoored.gui.CategoriesInit;
import com.backdoored.hacks.BaseHack;

public class GuiBind extends BaseHack
{
    public GuiBind() {
        super("GuiBind", CategoriesInit.UI, "GuiBind", false, false);
        if (this.getSetting("Bind").getValString().equals("NONE")) {
            this.getSetting("Bind").setVal(Keyboard.getKeyName(39));
            SettingsManager.getSettingsByMod(this).get(1).button.text = Keyboard.getKeyName(39);
        }
    }
    
    public void onEnabled() {
        GuiBind.mc.func_147108_a((GuiScreen)new GuiNotif());
        this.setEnabled(false);
    }
}
