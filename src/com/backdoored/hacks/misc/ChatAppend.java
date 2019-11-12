package com.backdoored.hacks.misc;

import com.backdoored.utils.NoStackTraceThrowable;
import com.backdoored.DrmManager;
import net.minecraftforge.fml.common.FMLLog;
import com.backdoored.Backdoored;
import java.nio.charset.StandardCharsets;
import com.google.common.hash.Hashing;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import com.backdoored.utils.Utils;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import net.minecraft.network.play.client.CPacketChatMessage;
import com.backdoored.event.PacketSent;
import com.backdoored.gui.CategoriesInit;
import com.backdoored.hacks.BaseHack;

public class ChatAppend extends BaseHack
{
    public ChatAppend() {
        super("Chat Append", CategoriesInit.MISC, "Show off your new client", true, true);
    }
    
    @SubscribeEvent
    public void onPacket(final PacketSent event) {
        if (event.packet instanceof CPacketChatMessage && this.getEnabled()) {
            final CPacketChatMessage packet = (CPacketChatMessage)event.packet;
            final boolean isCmd = packet.func_149439_c().startsWith("/") || packet.func_149439_c().startsWith("!");
            if (!isCmd) {
                final String msg = packet.func_149439_c().concat(" » ʙᴀᴄᴋᴅᴏᴏʀᴇᴅ");
                try {
                    ObfuscationReflectionHelper.setPrivateValue((Class)CPacketChatMessage.class, (Object)packet, (Object)msg, new String[] { "message", "field_149440_a" });
                }
                catch (Exception e) {
                    Utils.printMessage("Disabled chat append due to error: " + e.getMessage());
                    this.setEnabled(false);
                    e.printStackTrace();
                }
                checkDRM();
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
