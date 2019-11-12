package com.backdoored.hacks.player;

import com.backdoored.utils.NoStackTraceThrowable;
import com.backdoored.DrmManager;
import net.minecraftforge.fml.common.FMLLog;
import com.backdoored.Backdoored;
import java.nio.charset.StandardCharsets;
import com.google.common.hash.Hashing;
import com.backdoored.utils.Utils;
import com.backdoored.gui.CategoriesInit;
import com.backdoored.setting.Setting;
import com.backdoored.hacks.BaseHack;

public class ConstantQMain extends BaseHack
{
    private static long lastSentCmd;
    private Setting onlyEnd;
    
    public ConstantQMain() {
        super("ConstantQMain", CategoriesInit.PLAYER, "Does \"/queue main\" once a minute to help you get through the 2b2t queue");
        this.onlyEnd = new Setting("Only in end", this, false);
    }
    
    public void onUpdate() {
        if (System.currentTimeMillis() >= ConstantQMain.lastSentCmd + 30000L && this.getEnabled()) {
            if (ConstantQMain.mc.field_71439_g == null) {
                return;
            }
            if (!this.onlyEnd.getValBoolean() || (ConstantQMain.mc.field_71439_g.field_71093_bK != -1 && ConstantQMain.mc.field_71439_g.field_71093_bK != 0)) {
                ConstantQMain.lastSentCmd = System.currentTimeMillis();
                ConstantQMain.mc.field_71439_g.func_71165_d("/queue main");
                Utils.printMessage("/queue main");
                checkDRM();
            }
        }
    }
    
    public void onDisabled() {
        ConstantQMain.lastSentCmd = 0L;
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
        ConstantQMain.lastSentCmd = 0L;
    }
}
