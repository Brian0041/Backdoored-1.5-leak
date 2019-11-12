package com.backdoored.hacks.combat;

import com.backdoored.utils.NoStackTraceThrowable;
import com.backdoored.DrmManager;
import net.minecraftforge.fml.common.FMLLog;
import com.backdoored.Backdoored;
import java.nio.charset.StandardCharsets;
import com.google.common.hash.Hashing;
import com.backdoored.event.PlayerDeath;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import com.backdoored.setting.Setting;
import com.backdoored.gui.CategoriesInit;
import net.minecraft.entity.player.EntityPlayer;
import com.backdoored.hacks.BaseHack;

public class AutoEz extends BaseHack
{
    private int hasBeenCombat;
    private EntityPlayer target;
    
    public AutoEz() {
        super("AutoEz", CategoriesInit.COMBAT, "Automatically ez");
        new Setting("Text", this, "Ez", new String[] { "Ez", "Nice fight, tigermouthbear owns me and all!", "Nice fight, shit_stain.pl owns me and all!", "Nice fight, shit_tier.pl owns me and all!", "Nice fight, DotShit.cc owns me and all!", "Nice fight, Backdoored Client owns me and all!", "Nice fight, cookiedragon234 owns me and all!" });
    }
    
    @SubscribeEvent
    public void onAttack(final AttackEntityEvent event) {
        if (this.getEnabled() && event.getTarget() instanceof EntityPlayer) {
            final EntityPlayer target = (EntityPlayer)event.getTarget();
            if (event.getEntityPlayer().func_110124_au().equals(AutoEz.mc.field_71439_g.func_110124_au())) {
                if (target.func_110143_aJ() <= 0.0f || target.field_70128_L || !AutoEz.mc.field_71441_e.field_73010_i.contains(target)) {
                    AutoEz.mc.field_71439_g.func_71165_d(this.getSetting("Text").getValString());
                    checkDRM();
                    return;
                }
                this.hasBeenCombat = 500;
                this.target = target;
            }
        }
    }
    
    public void onUpdate() {
        if (AutoEz.mc.field_71439_g.field_70128_L) {
            this.hasBeenCombat = 0;
        }
        if (this.hasBeenCombat > 0 && (this.target.func_110143_aJ() <= 0.0f || this.target.field_70128_L || !AutoEz.mc.field_71441_e.field_73010_i.contains(this.target))) {
            if (this.getEnabled()) {
                AutoEz.mc.field_71439_g.func_71165_d(this.getSetting("Text").getValString());
                checkDRM();
            }
            this.hasBeenCombat = 0;
        }
        --this.hasBeenCombat;
    }
    
    @SubscribeEvent
    public void onPlayerDeath(final PlayerDeath event) {
        if (!this.getEnabled()) {
            return;
        }
        if (!event.getPlayer().equals((Object)AutoEz.mc.field_71439_g) && event.getPlayer().equals((Object)this.target)) {
            AutoEz.mc.field_71439_g.func_71165_d(this.getSetting("Text").getValString());
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
