package com.backdoored.hacks.client;

import com.backdoored.utils.NoStackTraceThrowable;
import com.backdoored.DrmManager;
import net.minecraftforge.fml.common.FMLLog;
import com.backdoored.Backdoored;
import java.nio.charset.StandardCharsets;
import com.google.common.hash.Hashing;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.network.play.server.SPacketChat;
import com.backdoored.event.PacketRecieved;
import com.backdoored.utils.TrayUtils;
import net.minecraft.entity.player.EntityPlayer;
import com.backdoored.setting.Setting;
import com.backdoored.gui.CategoriesInit;
import com.backdoored.hacks.BaseHack;

public class Notifications extends BaseHack
{
    public static Notifications INSTANCE;
    
    public Notifications() {
        super("Notifications", CategoriesInit.CLIENT, "Toast Notifications");
        new Setting("Visual Range", this, true);
        new Setting("Queue", this, true);
        Notifications.INSTANCE = this;
    }
    
    public void visualRangeTrigger(final EntityPlayer player) {
        if (!this.getEnabled() || !this.getSetting("Visual Range").getValBoolean()) {
            return;
        }
        TrayUtils.sendMessage("Visual Range", player.getDisplayNameString() + " entered your visual range");
        checkDRM();
    }
    
    @SubscribeEvent
    public void onPacket(final PacketRecieved event) {
        if (this.getEnabled() && this.getSetting("Queue").getValBoolean() && event.packet instanceof SPacketChat) {
            final SPacketChat packet = (SPacketChat)event.packet;
            final String msg = packet.func_148915_c().func_150260_c().toLowerCase();
            if (msg.startsWith("connecting to")) {
                TrayUtils.sendMessage(packet.func_148915_c().func_150260_c());
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
}
