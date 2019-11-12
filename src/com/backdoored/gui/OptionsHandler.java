package com.backdoored.gui;

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import java.util.Iterator;
import com.backdoored.utils.Utils;
import org.lwjgl.input.Mouse;
import org.lwjgl.input.Keyboard;
import java.util.Objects;
import com.backdoored.setting.SettingsManager;
import com.backdoored.setting.Setting;
import java.util.ArrayList;
import com.backdoored.hacks.BaseHack;
import com.backdoored.Globals;
import net.minecraftforge.client.event.RenderGameOverlayEvent;

public class OptionsHandler
{
    private static Button prevButton;
    
    public OptionsHandler() {
        super();
    }
    
    @SubscribeEvent
    public void optionsHandler(final RenderGameOverlayEvent event) {
        for (final BaseHack x : Globals.hacks) {
            for (final Setting y : Objects.requireNonNull(SettingsManager.getSettingsByMod(x))) {
                final Button button = y.button;
                final Button parentButton = x.button;
                if (y.isBind()) {
                    if (Keyboard.getEventKeyState() && !button.leftClickToggled && Keyboard.getKeyName(Keyboard.getEventKey()).equals(y.getValString()) && !y.held && Globals.mc.field_71415_G) {
                        if (!Keyboard.getKeyName(Keyboard.getEventKey()).equals("NONE")) {
                            for (final BaseHack z : Globals.hacks) {
                                if (parentButton.text.equals(z.button.text)) {
                                    z.setEnabled(!z.getEnabled());
                                }
                            }
                            y.held = true;
                        }
                    }
                    else if (!Keyboard.getEventKeyState()) {
                        y.held = false;
                    }
                    if (button.leftClickToggled) {
                        button.text = "Bind: ...";
                        if (Keyboard.getEventKeyState()) {
                            y.setVal(Keyboard.getKeyName(Keyboard.getEventKey()));
                            button.text = "Bind: " + y.getValString();
                            button.leftClickToggled = false;
                            parentButton.leftClickToggled = false;
                            y.held = true;
                        }
                        if (button.rightClickToggled) {
                            y.setVal("NONE");
                            button.text = "Bind: " + y.getValString();
                            button.leftClickToggled = false;
                        }
                    }
                    else {
                        button.text = "Bind: " + y.getValString();
                    }
                    if (button.rightClickToggled) {
                        button.rightClickToggled = false;
                    }
                }
                if (y.isMode()) {
                    if (button.leftClickToggled) {
                        int z2 = 0;
                        while (z2 < y.getOptions().length) {
                            if (y.getOptions()[z2].equals(y.getValString())) {
                                if (z2 == 0) {
                                    y.setVal(y.getOptions()[y.getOptions().length - 1]);
                                    break;
                                }
                                y.setVal(y.getOptions()[z2 - 1]);
                                break;
                            }
                            else {
                                ++z2;
                            }
                        }
                        button.leftClickToggled = false;
                    }
                    button.text = y.getName() + ": " + y.getValString();
                }
                if (y.isToggle()) {
                    if (button.leftClickToggled) {
                        y.setVal(!y.getValBoolean());
                        button.leftClickToggled = false;
                    }
                    button.text = y.getName() + ": " + y.getValString();
                }
                if (y.isSliderD()) {
                    if (button.leftClickToggled) {
                        y.held = true;
                        button.leftClickToggled = false;
                    }
                    if (!Mouse.isButtonDown(0)) {
                        y.held = false;
                    }
                    if (y.held && GuiNotif.mouseX >= button.x && GuiNotif.mouseX <= button.x + button.field_146294_l) {
                        y.setVal(Utils.roundDouble((GuiNotif.mouseX - button.x) / (double)button.field_146294_l * y.getMax() - y.getMin() + y.getMin(), 2));
                    }
                    button.text = y.getName() + ": " + y.getValString();
                }
                if (y.isSliderINT()) {
                    if (button.leftClickToggled) {
                        y.held = true;
                        button.leftClickToggled = false;
                    }
                    if (!Mouse.isButtonDown(0)) {
                        y.held = false;
                    }
                    if (y.held && GuiNotif.mouseX >= button.x && GuiNotif.mouseX <= button.x + button.field_146294_l) {
                        y.setVal((int)Utils.roundDouble((GuiNotif.mouseX - button.x) / (double)button.field_146294_l * y.getMax() - y.getMin() + y.getMin(), 0));
                    }
                    button.text = y.getName() + ": " + y.getValString();
                }
                button.x = parentButton.x + parentButton.field_146294_l;
                if (OptionsHandler.prevButton != null) {
                    button.y = OptionsHandler.prevButton.y + OptionsHandler.prevButton.field_146295_m;
                }
                else {
                    button.y = parentButton.y;
                }
                OptionsHandler.prevButton = button;
            }
            OptionsHandler.prevButton = null;
        }
    }
}
