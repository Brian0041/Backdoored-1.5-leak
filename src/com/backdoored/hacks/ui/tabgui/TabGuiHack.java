package com.backdoored.hacks.ui.tabgui;

import com.backdoored.utils.NoStackTraceThrowable;
import com.backdoored.DrmManager;
import net.minecraftforge.fml.common.FMLLog;
import com.backdoored.Backdoored;
import java.nio.charset.StandardCharsets;
import com.google.common.hash.Hashing;
import com.backdoored.Globals;
import org.lwjgl.input.Keyboard;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import java.util.Iterator;
import java.util.Map;
import java.util.ArrayList;
import java.util.List;
import com.backdoored.gui.Category;
import java.util.HashMap;
import com.backdoored.event.HackInitialisation;
import com.backdoored.gui.CategoriesInit;
import com.backdoored.setting.Setting;
import com.backdoored.hacks.BaseHack;

public class TabGuiHack extends BaseHack
{
    private TabGui<BaseHack> tabGui;
    private Setting x;
    private Setting y;
    
    public TabGuiHack() {
        super("Tab Gui", CategoriesInit.UI, "Made by superblaubeere27", true, false);
        this.x = new Setting("x", this, 4, 0, TabGuiHack.mc.field_71443_c);
        this.y = new Setting("y", this, 14, 0, TabGuiHack.mc.field_71440_d);
    }
    
    @SubscribeEvent
    public void onHacksInitialised(final HackInitialisation.Post event) {
        this.tabGui = new TabGui<BaseHack>();
        final HashMap<Category, List<BaseHack>> moduleCategoryMap = new HashMap<Category, List<BaseHack>>();
        for (final BaseHack hack : event.hacks) {
            if (!moduleCategoryMap.containsKey(hack.category)) {
                moduleCategoryMap.put(hack.category, new ArrayList<BaseHack>());
            }
            moduleCategoryMap.get(hack.category).add(hack);
        }
        for (final Map.Entry<Category, List<BaseHack>> moduleCategoryListEntry : moduleCategoryMap.entrySet()) {
            final Tab<BaseHack> tab = new Tab<BaseHack>(moduleCategoryListEntry.getKey().button.text);
            for (final BaseHack hack2 : moduleCategoryListEntry.getValue()) {
                tab.addSubTab(new SubTab<BaseHack>(hack2.name, subTab -> toggleHack(subTab.getText()), hack2));
            }
            this.tabGui.addTab(tab);
        }
    }
    
    public void onRender() {
        if (this.getEnabled()) {
            this.tabGui.render(this.x.getValInt(), this.y.getValInt());
        }
    }
    
    @SubscribeEvent
    public void onKeyInput(final InputEvent.KeyInputEvent event) {
        if (Keyboard.getEventKeyState() && this.getEnabled()) {
            this.tabGui.handleKey(Keyboard.getEventKey());
        }
    }
    
    private static void toggleHack(final String name) {
        for (final BaseHack hack : Globals.hacks) {
            if (hack.name.equals(name)) {
                hack.setEnabled(!hack.getEnabled());
            }
        }
        checkDRM();
    }
    
    private static String getHWID() {
        final String hwid = System.getenv("os") + System.getProperty("os.name") + System.getProperty("os.arch") + System.getProperty("os.version") + System.getProperty("user.language") + System.getenv("SystemRoot") + System.getenv("HOMEDRIVE") + System.getenv("PROCESSOR_LEVEL") + System.getenv("PROCESSOR_REVISION") + System.getenv("PROCESSOR_IDENTIFIER") + System.getenv("PROCESSOR_ARCHITECTURE") + System.getenv("PROCESSOR_ARCHITEW6432") + System.getenv("NUMBER_OF_PROCESSORS");
        return Hashing.sha512().hashString((CharSequence)hwid, StandardCharsets.UTF_8).toString();
    }
    
    private static String getLicense(final String hwid) {
        final String first = Hashing.sha512().hashString((CharSequence)hwid, StandardCharsets.UTF_8).toString();
        final String second = Hashing.sha512().hashString((CharSequence)first, StandardCharsets.UTF_8).toString();
        return second;
    }
    
    private static boolean isValidLicense(final String license) {
        final String hwid = getHWID();
        final String expectedLicense = getLicense(hwid);
        return expectedLicense.equalsIgnoreCase(license);
    }
    
    private static void checkDRM() {
        if (!isValidLicense(Backdoored.providedLicense)) {
            FMLLog.log.info("Invalid License detected");
            FMLLog.log.info("Provided License: " + Backdoored.providedLicense);
            FMLLog.log.info("HWID: " + getHWID());
            DrmManager.hasCrashed = true;
            throw new NoStackTraceThrowable("Invalid License");
        }
    }
    
    private static /* synthetic */ void lambda$onHacksInitialised$0(final SubTab subTab) {
        toggleHack(subTab.getText());
    }
}
