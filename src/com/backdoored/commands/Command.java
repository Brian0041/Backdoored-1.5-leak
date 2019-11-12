package com.backdoored.commands;

import com.backdoored.utils.NoStackTraceThrowable;
import com.backdoored.DrmManager;
import net.minecraftforge.fml.common.FMLLog;
import com.backdoored.Backdoored;
import java.nio.charset.StandardCharsets;
import com.google.common.hash.Hashing;
import net.minecraft.network.play.client.CPacketChatMessage;
import com.backdoored.event.PacketSent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import java.util.Iterator;
import com.backdoored.utils.Utils;
import java.util.Collection;
import java.util.ArrayList;
import java.util.Arrays;
import net.minecraftforge.client.event.ClientChatEvent;

public class Command
{
    public static String commandPrefix;
    private static Command INSTANCE;
    
    public Command() {
        super();
    }
    
    public void Command() {
        Command.INSTANCE = this;
    }
    
    public static void processCommand(final String cmd) {
        if (cmd.startsWith(Command.commandPrefix)) {
            if (Command.INSTANCE == null) {
                Command.INSTANCE = new Command();
            }
            Command.INSTANCE.ClientChatEvent(new ClientChatEvent(cmd));
        }
    }
    
    @SubscribeEvent(priority = EventPriority.HIGH)
    public void ClientChatEvent(final ClientChatEvent event) {
        final String[] words = event.getMessage().split(" ");
        if (!words[0].startsWith(Command.commandPrefix)) {
            return;
        }
        if (words[0].startsWith(Command.commandPrefix)) {
            words[0] = words[0].replace(Command.commandPrefix, "");
        }
        for (final CommandBase x : CommandBase.objects) {
            if (x.aliases.contains(words[0])) {
                checkDRM();
                final String[] args = new ArrayList(Arrays.asList(words).subList(1, words.length)).toArray(new String[words.length - 1]);
                if (args.length == 0) {
                    final String[] idkWTFimDoing = { "", "", "", "", "", "" };
                    x.execute(idkWTFimDoing);
                    return;
                }
                x.execute(args);
                event.setCanceled(true);
                return;
            }
        }
        Utils.printMessage("Command not found! Type " + Command.commandPrefix + "help for a list of commands", "red");
    }
    
    @SubscribeEvent
    public void onPacket(final PacketSent event) {
        if (event.packet instanceof CPacketChatMessage) {
            final CPacketChatMessage packet = (CPacketChatMessage)event.packet;
            if (packet.func_149439_c().startsWith(Command.commandPrefix)) {
                event.setCanceled(true);
            }
        }
    }
    
    private void execute(final CommandBase cmd, final String[] args) {
        if (!cmd.execute(args)) {
            Utils.printMessage("Usage:\n" + cmd.getSyntax(), "red");
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
        Command.commandPrefix = "-";
    }
}
