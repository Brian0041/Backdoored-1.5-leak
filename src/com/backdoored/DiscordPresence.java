package com.backdoored;

import net.minecraft.client.multiplayer.ServerData;
import club.minnced.discord.rpc.DiscordEventHandlers;
import net.minecraftforge.fml.common.FMLLog;
import club.minnced.discord.rpc.DiscordRichPresence;
import club.minnced.discord.rpc.DiscordRPC;

public class DiscordPresence
{
    private static final String APP_ID = "608333956575920159";
    private static final DiscordRPC rpc;
    private static DiscordRichPresence presence;
    private static boolean hasStarted;
    
    public DiscordPresence() {
        super();
    }
    
    public static boolean start() {
        FMLLog.log.info("Starting Discord RPC");
        if (DiscordPresence.hasStarted) {
            return false;
        }
        DiscordPresence.hasStarted = true;
        final DiscordEventHandlers handlers = new DiscordEventHandlers();
        handlers.disconnected = ((var1, var2) -> System.out.println("Discord RPC disconnected, var1: " + String.valueOf(var1) + ", var2: " + var2));
        DiscordPresence.rpc.Discord_Initialize("608333956575920159", handlers, true, "");
        DiscordPresence.presence.startTimestamp = System.currentTimeMillis() / 1000L;
        DiscordPresence.presence.details = "Main Menu";
        DiscordPresence.presence.state = "discord.gg/ncQkFKU";
        DiscordPresence.presence.largeImageKey = "backdoored_logo";
        DiscordPresence.rpc.Discord_UpdatePresence(DiscordPresence.presence);
        String details;
        String state;
        int players;
        int maxPlayers;
        ServerData svr;
        String[] popInfo;
        int players2;
        int maxPlayers2;
        new Thread(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    DiscordPresence.rpc.Discord_RunCallbacks();
                    details = "";
                    state = "";
                    players = 0;
                    maxPlayers = 0;
                    if (Globals.mc.func_71387_A()) {
                        details = "Singleplayer";
                    }
                    else if (Globals.mc.func_147104_D() != null) {
                        svr = Globals.mc.func_147104_D();
                        if (!svr.field_78845_b.equals("")) {
                            details = "Multiplayer";
                            state = svr.field_78845_b;
                            if (svr.field_78846_c != null) {
                                popInfo = svr.field_78846_c.split("/");
                                if (popInfo.length > 2) {
                                    players2 = Integer.valueOf(popInfo[0]);
                                    maxPlayers2 = Integer.valueOf(popInfo[1]);
                                }
                            }
                            if (state.contains("2b2t.org")) {
                                try {
                                    if (Backdoored.lastChat.startsWith("Position in queue: ")) {
                                        state = state + " " + Integer.parseInt(Backdoored.lastChat.substring(19)) + " in queue";
                                    }
                                }
                                catch (Throwable e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }
                    else {
                        details = "Main Menu";
                        state = "discord.gg/ncQkFKU";
                    }
                    if (!details.equals(DiscordPresence.presence.details) || !state.equals(DiscordPresence.presence.state)) {
                        DiscordPresence.presence.startTimestamp = System.currentTimeMillis() / 1000L;
                    }
                    DiscordPresence.presence.details = details;
                    DiscordPresence.presence.state = state;
                    DiscordPresence.rpc.Discord_UpdatePresence(DiscordPresence.presence);
                }
                catch (Exception e2) {
                    e2.printStackTrace();
                }
                try {
                    Thread.sleep(5000L);
                }
                catch (InterruptedException e3) {
                    e3.printStackTrace();
                }
            }
            return;
        }, "Discord-RPC-Callback-Handler").start();
        FMLLog.log.info("Discord RPC initialised succesfully");
        return true;
    }
    
    private static /* synthetic */ void lambda$start$1() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                DiscordPresence.rpc.Discord_RunCallbacks();
                String details = "";
                String state = "";
                int players = 0;
                int maxPlayers = 0;
                if (Globals.mc.func_71387_A()) {
                    details = "Singleplayer";
                }
                else if (Globals.mc.func_147104_D() != null) {
                    final ServerData svr = Globals.mc.func_147104_D();
                    if (!svr.field_78845_b.equals("")) {
                        details = "Multiplayer";
                        state = svr.field_78845_b;
                        if (svr.field_78846_c != null) {
                            final String[] popInfo = svr.field_78846_c.split("/");
                            if (popInfo.length > 2) {
                                players = Integer.valueOf(popInfo[0]);
                                maxPlayers = Integer.valueOf(popInfo[1]);
                            }
                        }
                        if (state.contains("2b2t.org")) {
                            try {
                                if (Backdoored.lastChat.startsWith("Position in queue: ")) {
                                    state = state + " " + Integer.parseInt(Backdoored.lastChat.substring(19)) + " in queue";
                                }
                            }
                            catch (Throwable e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
                else {
                    details = "Main Menu";
                    state = "discord.gg/ncQkFKU";
                }
                if (!details.equals(DiscordPresence.presence.details) || !state.equals(DiscordPresence.presence.state)) {
                    DiscordPresence.presence.startTimestamp = System.currentTimeMillis() / 1000L;
                }
                DiscordPresence.presence.details = details;
                DiscordPresence.presence.state = state;
                DiscordPresence.rpc.Discord_UpdatePresence(DiscordPresence.presence);
            }
            catch (Exception e2) {
                e2.printStackTrace();
            }
            try {
                Thread.sleep(5000L);
            }
            catch (InterruptedException e3) {
                e3.printStackTrace();
            }
        }
    }
    
    private static /* synthetic */ void lambda$start$0(final int var1, final String var2) {
        System.out.println("Discord RPC disconnected, var1: " + String.valueOf(var1) + ", var2: " + var2);
    }
    
    static {
        rpc = DiscordRPC.INSTANCE;
        DiscordPresence.presence = new DiscordRichPresence();
        DiscordPresence.hasStarted = false;
    }
}
