package com.backdoored;

import com.backdoored.event.ServerTick;
import net.minecraft.network.play.server.SPacketTimeUpdate;
import com.backdoored.event.PacketRecieved;
import net.minecraft.client.gui.GuiScreen;
import com.backdoored.subguis.MainMenuGui;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import com.backdoored.commands.Command;
import com.backdoored.commands.ViewInventory;
import com.backdoored.commands.Import;
import com.backdoored.commands.FakePlayer;
import com.backdoored.commands.Bind;
import com.backdoored.commands.Load;
import com.backdoored.commands.NomadBase;
import com.backdoored.commands.Save;
import com.backdoored.commands.Toggle;
import com.backdoored.commands.Spectate;
import com.backdoored.commands.Prefix;
import com.backdoored.commands.Help;
import com.backdoored.commands.Friend;
import com.backdoored.commands.FakeMsg;
import java.util.Set;
import com.backdoored.api.classloading.SimpleClassLoader;
import com.backdoored.hacks.ui.tabgui.TabGuiHack;
import com.backdoored.hacks.ui.GuiBind;
import com.backdoored.hacks.ui.hud.Hud;
import com.backdoored.hacks.combat.WebAura;
import com.backdoored.hacks.misc.VisualRange;
import com.backdoored.hacks.exploit.VanishDetector;
import com.backdoored.hacks.player.Twerk;
import com.backdoored.hacks.ui.Swing;
import com.backdoored.hacks.combat.Surround;
import com.backdoored.hacks.combat.StrengthPotDetect;
import com.backdoored.hacks.movement.Speed;
import com.backdoored.hacks.player.Spammer;
import com.backdoored.hacks.exploit.SoundCoordLogger;
import com.backdoored.hacks.misc.SkinDerp;
import com.backdoored.hacks.client.ShulkerPreview;
import com.backdoored.hacks.client.ShulkerFaker;
import com.backdoored.hacks.exploit.ServerCrasher;
import com.backdoored.hacks.exploit.SecretClose;
import com.backdoored.hacks.player.Scaffold;
import com.backdoored.hacks.player.RotationLock;
import com.backdoored.hacks.misc.ReloadSoundSystem;
import com.backdoored.hacks.render.RainbowEnchant;
import com.backdoored.hacks.exploit.PingSpoof;
import com.backdoored.hacks.movement.PullDown;
import com.backdoored.hacks.exploit.Popbob;
import com.backdoored.hacks.movement.Phase;
import com.backdoored.hacks.exploit.PacketMine;
import com.backdoored.hacks.movement.PacketFly;
import com.backdoored.hacks.exploit.OffhandLag;
import com.backdoored.hacks.client.Notifications;
import com.backdoored.hacks.exploit.NoSwing;
import com.backdoored.hacks.render.NoRender;
import com.backdoored.hacks.render.NoHands;
import com.backdoored.hacks.render.NoFog;
import com.backdoored.hacks.player.NoBreakDelay;
import com.backdoored.hacks.client.MsgOnToggle;
import com.backdoored.hacks.render.MobOwner;
import com.backdoored.hacks.combat.MiddleClickInvSee;
import com.backdoored.hacks.client.MapTooltip;
import com.backdoored.hacks.client.LogoutSpot;
import com.backdoored.hacks.player.LiquidInteract;
import com.backdoored.hacks.ui.latematt.LateMattGui;
import com.backdoored.hacks.combat.KillAura;
import com.backdoored.hacks.player.InstantPortalReuse;
import com.backdoored.hacks.combat.InvisDetect;
import com.backdoored.hacks.player.InvPreview;
import com.backdoored.hacks.client.InfiniteChatLength;
import com.backdoored.hacks.combat.HopperAura;
import com.backdoored.hacks.render.HoleEsp;
import com.backdoored.hacks.render.HitboxEsp;
import com.backdoored.hacks.exploit.HausemasterFinder;
import com.backdoored.hacks.movement.GuiMove;
import com.backdoored.hacks.misc.GreenText;
import com.backdoored.hacks.misc.GrabCoords;
import com.backdoored.hacks.exploit.FootSpam;
import com.backdoored.hacks.player.FastPlace;
import com.backdoored.hacks.movement.FastIce;
import com.backdoored.hacks.player.FakeRotation;
import com.backdoored.hacks.player.FakeItem;
import com.backdoored.hacks.exploit.FakeCreative;
import com.backdoored.hacks.client.ExtraTab;
import com.backdoored.hacks.movement.ElytraFlight;
import com.backdoored.hacks.misc.Disconnect;
import com.backdoored.hacks.client.DebugCrosshair;
import com.backdoored.hacks.exploit.CoordTpExploit;
import com.backdoored.hacks.ui.Coordinates;
import com.backdoored.hacks.player.ConstantQMain;
import com.backdoored.hacks.render.ChestESP;
import com.backdoored.hacks.client.ChatTimestamps;
import com.backdoored.hacks.player.ChatModifier;
import com.backdoored.hacks.client.ChatFilter;
import com.backdoored.hacks.chatbot.ChatBot;
import com.backdoored.hacks.misc.ChatAppend;
import com.backdoored.hacks.client.CameraClip;
import com.backdoored.hacks.combat.BowSpam;
import com.backdoored.hacks.movement.BoatFly;
import com.backdoored.hacks.client.BetterSign;
import com.backdoored.hacks.client.BetterScreenshot;
import com.backdoored.hacks.client.BetterHighlightBox;
import com.backdoored.hacks.player.AutoWither;
import com.backdoored.hacks.combat.AutoTrap;
import com.backdoored.hacks.combat.AutoTotem;
import com.backdoored.hacks.combat.ArmorAlert;
import com.backdoored.hacks.misc.AutoReply;
import com.backdoored.hacks.combat.AutoEz;
import com.backdoored.hacks.combat.AutoCrystal;
import com.backdoored.hacks.combat.Auto32k;
import com.backdoored.hacks.render.AntiOverlay;
import com.backdoored.hacks.client.AntiFOV;
import com.backdoored.hacks.combat.AntiDeathScreen;
import com.backdoored.hacks.player.AntiBedTrap;
import com.backdoored.hacks.chatbot.Announcer;
import net.minecraftforge.fml.common.eventhandler.Event;
import com.backdoored.event.HackInitialisation;
import org.lwjgl.opengl.Display;
import com.backdoored.utils.CapeUtils;
import com.backdoored.hacks.BaseHack;
import com.backdoored.config.ConfigJsonManager;
import com.backdoored.config.SetConfigs;
import com.backdoored.gui.OptionsHandler;
import com.backdoored.gui.RenderGuiHandler;
import net.minecraftforge.common.MinecraftForge;
import com.backdoored.gui.CategoriesInit;
import com.backdoored.utils.FagtionUtils;
import com.backdoored.utils.FriendUtils;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import com.backdoored.utils.NoStackTraceThrowable;
import java.io.File;
import net.minecraftforge.fml.common.FMLLog;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import java.io.IOException;
import java.io.Reader;
import java.io.BufferedReader;
import java.io.FileReader;
import net.minecraftforge.fml.common.Mod;

@Mod(modid = "backdoored", version = "1.5", clientSideOnly = true)
public class Backdoored
{
    public static final String MODID = "backdoored";
    public static final String VERSION = "1.5";
    public static String providedLicense;
    public static String lastChat;
    
    public Backdoored() {
        super();
    }
    
    public static boolean getDevMode() {
        try {
            final BufferedReader inoptions = new BufferedReader(new FileReader("Backdoored/options.txt"));
            String read;
            while ((read = inoptions.readLine()) != null) {
                if (read.equals("dev.enable.debugger")) {
                    return true;
                }
            }
            inoptions.close();
            return false;
        }
        catch (IOException e) {
            return false;
        }
    }
    
    @Mod.EventHandler
    public void preInit(final FMLPreInitializationEvent event) {
        FMLLog.log.info("\n$$$$$$$\\                      $$\\             $$\\                                               $$\\ \n$$  __$$\\                     $$ |            $$ |                                              $$ |\n$$ |  $$ | $$$$$$\\   $$$$$$$\\ $$ |  $$\\  $$$$$$$ | $$$$$$\\   $$$$$$\\   $$$$$$\\   $$$$$$\\   $$$$$$$ |\n$$$$$$$\\ | \\____$$\\ $$  _____|$$ | $$  |$$  __$$ |$$  __$$\\ $$  __$$\\ $$  __$$\\ $$  __$$\\ $$  __$$ |\n$$  __$$\\  $$$$$$$ |$$ /      $$$$$$  / $$ /  $$ |$$ /  $$ |$$ /  $$ |$$ |  \\__|$$$$$$$$ |$$ /  $$ |\n$$ |  $$ |$$  __$$ |$$ |      $$  _$$<  $$ |  $$ |$$ |  $$ |$$ |  $$ |$$ |      $$   ____|$$ |  $$ |\n$$$$$$$  |\\$$$$$$$ |\\$$$$$$$\\ $$ | \\$$\\ \\$$$$$$$ |\\$$$$$$  |\\$$$$$$  |$$ |      \\$$$$$$$\\ \\$$$$$$$ |\n\\_______/  \\_______| \\_______|\\__|  \\__| \\_______| \\______/  \\______/ \\__|       \\_______| \\_______|\n");
        FMLLog.log.info("Loading backdoored...");
        DiscordPresence.start();
        final File dir = new File("Backdoored");
        if (!dir.exists()) {
            dir.mkdir();
        }
        if (!DrmManager.loadLicense()) {
            throw new NoStackTraceThrowable("Couldnt load license, invalid drm");
        }
    }
    
    @Mod.EventHandler
    public void postInit(final FMLInitializationEvent event) {
        FriendUtils.read();
        FagtionUtils.loadFagtions();
        new CategoriesInit();
        MinecraftForge.EVENT_BUS.register((Object)new RenderGuiHandler());
        MinecraftForge.EVENT_BUS.register((Object)new OptionsHandler());
        MinecraftForge.EVENT_BUS.register((Object)new SetConfigs());
        MinecraftForge.EVENT_BUS.register((Object)this);
        this.initHacks();
        this.initCommands();
        ConfigJsonManager.read();
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            Globals.hacks.forEach(BaseHack::onDestroy);
            FriendUtils.save();
            ConfigJsonManager.save();
            return;
        }));
        new CapeUtils();
        setTitle();
    }
    
    public static void setTitle() {
        Display.setTitle("Backdoored 1.5");
    }
    
    private void initHacks() {
        MinecraftForge.EVENT_BUS.post((Event)new HackInitialisation.Pre());
        final Class[] hacks = { Announcer.class, AntiBedTrap.class, AntiDeathScreen.class, AntiFOV.class, AntiOverlay.class, Auto32k.class, AutoCrystal.class, AutoEz.class, AutoReply.class, ArmorAlert.class, AutoTotem.class, AutoTrap.class, AutoWither.class, BetterHighlightBox.class, BetterScreenshot.class, BetterSign.class, BoatFly.class, BowSpam.class, CameraClip.class, ChatAppend.class, ChatBot.class, ChatFilter.class, ChatModifier.class, ChatTimestamps.class, ChestESP.class, ConstantQMain.class, Coordinates.class, CoordTpExploit.class, DebugCrosshair.class, Disconnect.class, ElytraFlight.class, ExtraTab.class, FakeCreative.class, FakeItem.class, FakeRotation.class, FastIce.class, FastPlace.class, FootSpam.class, GrabCoords.class, GreenText.class, GuiMove.class, HausemasterFinder.class, HitboxEsp.class, HoleEsp.class, HopperAura.class, InfiniteChatLength.class, InvPreview.class, InvisDetect.class, InstantPortalReuse.class, KillAura.class, LateMattGui.class, LiquidInteract.class, LogoutSpot.class, MapTooltip.class, MiddleClickInvSee.class, MobOwner.class, MsgOnToggle.class, NoBreakDelay.class, NoFog.class, NoHands.class, NoRender.class, NoSwing.class, Notifications.class, OffhandLag.class, PacketFly.class, PacketMine.class, Phase.class, Popbob.class, PullDown.class, PingSpoof.class, RainbowEnchant.class, ReloadSoundSystem.class, RotationLock.class, Scaffold.class, SecretClose.class, ServerCrasher.class, ShulkerFaker.class, ShulkerPreview.class, SkinDerp.class, SoundCoordLogger.class, Spammer.class, Speed.class, StrengthPotDetect.class, Surround.class, Swing.class, Twerk.class, VanishDetector.class, VisualRange.class, WebAura.class, Hud.class, GuiBind.class, TabGuiHack.class };
        final Set<Class> erroredClasses = new SimpleClassLoader().build(hacks).initialise().getErroredClasses();
        FMLLog.log.info("Backdoored tried to load " + hacks.length + " hacks, out of which " + erroredClasses.size() + " failed");
        FMLLog.log.info("Failed hacks: " + erroredClasses.toString());
        MinecraftForge.EVENT_BUS.post((Event)new HackInitialisation.Post(Globals.hacks));
        FMLLog.log.info("Backdoored startup finished ");
    }
    
    private void initCommands() {
        final Class[] cmds = { FakeMsg.class, Friend.class, Help.class, Prefix.class, Spectate.class, Toggle.class, Save.class, NomadBase.class, Load.class, Bind.class, FakePlayer.class, Import.class, ViewInventory.class };
        final Set<Class> erroredClasses = new SimpleClassLoader().build(cmds).initialise().getErroredClasses();
        FMLLog.log.info("Backdoored tried to load " + cmds.length + " commands, out of which " + erroredClasses.size() + " failed");
        FMLLog.log.info("Failed commands: " + erroredClasses.toString());
        final Command command = new Command();
        MinecraftForge.EVENT_BUS.register((Object)command);
    }
    
    @SubscribeEvent
    public void onChatRecieved(final ClientChatReceivedEvent event) {
        Backdoored.lastChat = event.getMessage().func_150260_c();
    }
    
    @SubscribeEvent
    public void onGuiOpened(final GuiOpenEvent event) {
        if (event.getGui() instanceof GuiMainMenu) {
            event.setGui((GuiScreen)new MainMenuGui());
        }
    }
    
    @SubscribeEvent
    public void onPacket(final PacketRecieved event) {
        if (event.packet instanceof SPacketTimeUpdate) {
            MinecraftForge.EVENT_BUS.post((Event)new ServerTick());
        }
    }
    
    private static /* synthetic */ void lambda$postInit$0() {
        Globals.hacks.forEach(BaseHack::onDestroy);
        FriendUtils.save();
        ConfigJsonManager.save();
    }
    
    static {
        Backdoored.providedLicense = "";
        Backdoored.lastChat = "";
    }
}
