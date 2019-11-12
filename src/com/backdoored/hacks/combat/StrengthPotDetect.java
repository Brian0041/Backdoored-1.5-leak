package com.backdoored.hacks.combat;

import com.backdoored.utils.NoStackTraceThrowable;
import com.backdoored.DrmManager;
import net.minecraftforge.fml.common.FMLLog;
import com.backdoored.Backdoored;
import java.nio.charset.StandardCharsets;
import com.google.common.hash.Hashing;
import java.util.Iterator;
import com.backdoored.utils.Utils;
import net.minecraft.init.MobEffects;
import java.util.Map;
import java.util.Collections;
import java.util.WeakHashMap;
import com.backdoored.gui.CategoriesInit;
import net.minecraft.entity.player.EntityPlayer;
import java.util.Set;
import com.backdoored.hacks.BaseHack;

public class StrengthPotDetect extends BaseHack
{
    private Set<EntityPlayer> strengthedPlayers;
    
    public StrengthPotDetect() {
        super("Strength Pot Detect", CategoriesInit.COMBAT, "Detect when enemies strength pot");
        this.strengthedPlayers = Collections.newSetFromMap(new WeakHashMap<EntityPlayer, Boolean>());
    }
    
    public void onUpdate() {
        if (!this.getEnabled()) {
            return;
        }
        for (final EntityPlayer player : StrengthPotDetect.mc.field_71441_e.field_73010_i) {
            if (player.equals((Object)StrengthPotDetect.mc.field_71439_g)) {
                continue;
            }
            if (player.func_70644_a(MobEffects.field_76420_g) && !this.strengthedPlayers.contains(player)) {
                Utils.printMessage("Player '" + player.getDisplayNameString() + "' has strenth potted", "yellow");
                this.strengthedPlayers.add(player);
                checkDRM();
            }
            if (!this.strengthedPlayers.contains(player) || player.func_70644_a(MobEffects.field_76420_g)) {
                continue;
            }
            Utils.printMessage("Player '" + player.getDisplayNameString() + "' no longer has strength", "green");
            this.strengthedPlayers.remove(player);
            checkDRM();
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
