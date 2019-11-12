package com.backdoored.hacks.ui;

import com.backdoored.utils.NoStackTraceThrowable;
import com.backdoored.DrmManager;
import net.minecraftforge.fml.common.FMLLog;
import com.backdoored.Backdoored;
import java.nio.charset.StandardCharsets;
import com.google.common.hash.Hashing;
import com.backdoored.gui.CategoriesInit;
import com.backdoored.hacks.ui.swing.SwingImpl;
import com.backdoored.setting.Setting;
import com.backdoored.hacks.BaseHack;

public class Swing extends BaseHack
{
    public static Setting chat;
    private boolean isClosed;
    private SwingImpl swingImp;
    
    public Swing() {
        super("Swing", CategoriesInit.UI, "Popup console");
        this.isClosed = false;
        Swing.chat = new Setting("Show Chat", this, true);
    }
    
    public void onEnabled() {
        if (this.swingImp == null || this.isClosed) {
            this.swingImp = new SwingImpl();
        }
        this.isClosed = false;
        checkDRM();
    }
    
    public void onDisabled() {
        this.swingImp.close();
        this.isClosed = true;
        checkDRM();
    }
    
    public void onUpdate() {
        if (this.swingImp != null) {
            this.swingImp.onUpdate();
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
}
