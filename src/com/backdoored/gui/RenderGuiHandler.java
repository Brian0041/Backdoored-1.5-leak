package com.backdoored.gui;

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import java.util.Iterator;
import java.util.Objects;
import com.backdoored.setting.Setting;
import com.backdoored.setting.SettingsManager;
import com.backdoored.hacks.BaseHack;
import com.backdoored.Globals;
import org.lwjgl.input.Mouse;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import java.util.ArrayList;

public class RenderGuiHandler
{
    private static Button prevButton;
    private static ArrayList<Category> invertedCatsList;
    private Category catToRemap;
    
    public RenderGuiHandler() {
        super();
    }
    
    @SubscribeEvent
    public void RenderGuiHandler(final RenderGameOverlayEvent event) {
        if (Mouse.isButtonDown(0) || Mouse.isButtonDown(1)) {
            for (final Category x : Globals.invertedCategories()) {
                if (GuiNotif.mouseX > x.button.x && GuiNotif.mouseY > x.button.y && GuiNotif.mouseX < x.button.x + x.width && GuiNotif.mouseY < x.button.y + x.height) {
                    this.setRemap(x);
                    break;
                }
                for (final BaseHack y : Globals.hacks) {
                    if (y.category != x) {
                        continue;
                    }
                    if (GuiNotif.mouseX > y.button.x && GuiNotif.mouseY > y.button.y && GuiNotif.mouseX < y.button.x + y.button.field_146294_l && GuiNotif.mouseY < y.button.y + y.button.field_146295_m) {
                        this.setRemap(x);
                        break;
                    }
                    for (final Setting z : SettingsManager.getSettingsByMod(y)) {
                        if (GuiNotif.mouseX > z.button.x && GuiNotif.mouseY > z.button.y && GuiNotif.mouseX < z.button.x + z.button.field_146294_l && GuiNotif.mouseY < z.button.y + z.button.field_146295_m) {
                            this.setRemap(x);
                            break;
                        }
                    }
                }
            }
        }
        this.remapCat();
        for (final BaseHack x2 : Globals.hacks) {
            if (x2.button.rightClickToggled) {
                for (final Setting y2 : Objects.requireNonNull(SettingsManager.getSettingsByMod(x2))) {
                    y2.button.isVisible = !y2.button.isVisible;
                }
                for (final Setting q : SettingsManager.getSettings()) {
                    if (q.hack != x2) {
                        q.button.isVisible = false;
                    }
                }
                x2.button.rightClickToggled = false;
            }
        }
        for (final Category x : Globals.invertedCategories()) {
            if (Mouse.isButtonDown(0) && GuiNotif.mouseX > x.button.x && GuiNotif.mouseY > x.button.y && GuiNotif.mouseX < x.button.x + x.width && GuiNotif.mouseY < x.button.y + x.height && !x.mouseHeld) {
                x.StartMouseX = GuiNotif.mouseX - x.button.x;
                x.StartMouseY = GuiNotif.mouseY - x.button.y;
                x.mouseHeld = true;
            }
            if (x.mouseHeld) {
                x.button.x = GuiNotif.mouseX - x.StartMouseX;
                x.button.y = GuiNotif.mouseY - x.StartMouseY;
            }
            if (!Mouse.isButtonDown(0)) {
                x.mouseHeld = false;
            }
            if (x.mouseHeld && Mouse.isButtonDown(0)) {
                break;
            }
        }
        for (final Category x : Globals.invertedCategories()) {
            if (!x.button.rightClickToggled) {
                for (final BaseHack y : Globals.hacks) {
                    if (y.category == x) {
                        y.button.isVisible = false;
                        for (final Setting setting : SettingsManager.getSettingsByMod(y)) {
                            setting.button.isVisible = false;
                        }
                    }
                }
            }
            else {
                for (final BaseHack y : Globals.hacks) {
                    if (y.category == x) {
                        y.button.isVisible = true;
                    }
                }
            }
            for (final BaseHack y : Globals.hacks) {
                if (y.category != x) {
                    continue;
                }
                y.button.x = x.button.x;
                if (RenderGuiHandler.prevButton != null) {
                    y.button.y = RenderGuiHandler.prevButton.y + RenderGuiHandler.prevButton.field_146295_m;
                }
                else {
                    y.button.y = x.button.y + x.button.field_146295_m;
                }
                RenderGuiHandler.prevButton = y.button;
            }
            RenderGuiHandler.prevButton = null;
        }
    }
    
    private void remapCat() {
        if (this.catToRemap != null) {
            Globals.categories.remove(this.catToRemap);
            Globals.categories.add(this.catToRemap);
        }
    }
    
    private void setRemap(final Category cat) {
        this.catToRemap = cat;
    }
}
