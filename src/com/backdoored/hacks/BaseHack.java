package com.backdoored.hacks;

import com.backdoored.utils.NoStackTraceThrowable;
import com.backdoored.DrmManager;
import net.minecraftforge.fml.common.FMLLog;
import java.nio.charset.StandardCharsets;
import com.google.common.hash.Hashing;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import com.backdoored.event.HackDisabled;
import com.backdoored.Backdoored;
import com.backdoored.utils.Utils;
import net.minecraftforge.fml.common.eventhandler.Event;
import com.backdoored.event.HackEnabled;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import java.util.Iterator;
import java.util.Objects;
import com.backdoored.setting.SettingsManager;
import java.util.ArrayList;
import com.backdoored.setting.Setting;
import net.minecraftforge.common.MinecraftForge;
import com.backdoored.Globals;
import net.minecraft.client.Minecraft;
import com.backdoored.gui.Button;
import com.backdoored.gui.Category;

public class BaseHack
{
    public final String name;
    public final Category category;
    public final String description;
    public final int placeOnList;
    public Button button;
    protected static final Minecraft mc;
    private boolean setEnabledQueue;
    
    public BaseHack(final String name, final String description, final Category category) {
        this(name, category, description, false, true);
    }
    
    public BaseHack(final String name, final Category category, final String description) {
        this(name, category, description, false, true);
    }
    
    public BaseHack(final String name, final Category category, final String description, final boolean defaultOn, final boolean defaultIsVisible) {
        super();
        this.name = name;
        this.category = category;
        this.description = description;
        this.setEnabled(defaultOn);
        this.placeOnList = category.listPlaces;
        ++category.listPlaces;
        this.button = new Button(category.x, category.y + category.height * this.placeOnList, category.width, category.height, name, true, true, new float[] { 0.2f, 0.0f, 0.9f, 1.0f });
        Globals.hacks.add(this);
        Globals.hacksMap.put(name, this);
        MinecraftForge.EVENT_BUS.register((Object)this);
        final Setting isVisible = new Setting("Is Visible", this, defaultIsVisible);
        final Setting bind = new Setting("Bind", this);
    }
    
    public void setEnabled(final boolean enabled) {
        this.setEnabledQueue = enabled;
    }
    
    public static void setEnabled(final String hack, final boolean enabled) {
        Globals.hacksMap.get(hack).setEnabledQueue = enabled;
    }
    
    public boolean getEnabled() {
        return this.button.leftClickToggled;
    }
    
    public static boolean getEnabled(final String hack) {
        return Globals.hacksMap.get(hack).button.leftClickToggled;
    }
    
    protected void onUpdate() {
    }
    
    protected void onRender() {
    }
    
    protected void onEnabled() {
    }
    
    protected void onDisabled() {
        checkDRM();
    }
    
    public void onDestroy() {
        MinecraftForge.EVENT_BUS.unregister((Object)this);
    }
    
    protected Setting getSetting(final String name) {
        for (final Setting setting : Objects.requireNonNull(SettingsManager.getSettingsByMod(this))) {
            if (setting.getName().trim().equalsIgnoreCase(name.trim())) {
                return setting;
            }
        }
        throw new RuntimeException("Setting \"" + name + "\" could not be found");
    }
    
    @SubscribeEvent
    public void onClientTick(final TickEvent.ClientTickEvent event) {
        if (BaseHack.mc.field_71441_e == null) {
            return;
        }
        if (this.setEnabledQueue != this.getEnabled()) {
            this.button.leftClickToggled = this.setEnabledQueue;
            if (this.setEnabledQueue) {
                try {
                    this.onEnabled();
                    final HackEnabled enabledEvent = new HackEnabled(Globals.hacksMap.get(this.name));
                    MinecraftForge.EVENT_BUS.post((Event)enabledEvent);
                }
                catch (Throwable t) {
                    this.setEnabled(false);
                    Utils.printMessage("Disabled '" + this.name + "' due to error while enabling: " + t.getMessage(), "red");
                    t.printStackTrace();
                }
            }
            else {
                Backdoored.setTitle();
                try {
                    this.onDisabled();
                    final HackDisabled disabledEvent = new HackDisabled(Globals.hacksMap.get(this.name));
                    MinecraftForge.EVENT_BUS.post((Event)disabledEvent);
                }
                catch (Throwable t) {
                    Utils.printMessage("Disabled '" + this.name + "' due to error while disabling: " + t.getMessage(), "red");
                    t.printStackTrace();
                }
            }
        }
        try {
            this.onUpdate();
        }
        catch (Throwable t) {
            this.setEnabled(false);
            Utils.printMessage("Disabled '" + this.name + "' due to error while ticking: " + t.getMessage(), "red");
            t.printStackTrace();
        }
    }
    
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void RenderHud(final RenderGameOverlayEvent.Post event) {
        if (event.getType() == RenderGameOverlayEvent.ElementType.EXPERIENCE) {
            try {
                this.onRender();
            }
            catch (Throwable t) {
                this.setEnabled(false);
                Utils.printMessage("Disabled '" + this.name + "' due to error while rendering: " + t.getMessage(), "red");
                t.printStackTrace();
            }
        }
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
    
    static {
        mc = Globals.mc;
    }
}
