package com.backdoored.hacks.misc;

import com.backdoored.utils.NoStackTraceThrowable;
import com.backdoored.DrmManager;
import net.minecraftforge.fml.common.FMLLog;
import com.backdoored.Backdoored;
import java.nio.charset.StandardCharsets;
import com.google.common.hash.Hashing;
import net.minecraft.client.gui.GuiMultiplayer;
import net.minecraft.realms.RealmsBridge;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.multiplayer.WorldClient;
import com.backdoored.gui.CategoriesInit;
import com.backdoored.hacks.BaseHack;

public class Disconnect extends BaseHack
{
    public Disconnect() {
        super("Disconenct", CategoriesInit.MISC, "Allows you to bind a key to disconnecting");
    }
    
    public void onEnabled() {
        this.setEnabled(false);
        Disconnect.mc.field_71441_e.func_72882_A();
        Disconnect.mc.func_71403_a((WorldClient)null);
        if (Disconnect.mc.func_71387_A()) {
            Disconnect.mc.func_147108_a((GuiScreen)new GuiMainMenu());
        }
        else if (Disconnect.mc.func_181540_al()) {
            final RealmsBridge realmsbridge = new RealmsBridge();
            realmsbridge.switchToRealms((GuiScreen)new GuiMainMenu());
        }
        else {
            Disconnect.mc.func_147108_a((GuiScreen)new GuiMultiplayer((GuiScreen)new GuiMainMenu()));
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
}
