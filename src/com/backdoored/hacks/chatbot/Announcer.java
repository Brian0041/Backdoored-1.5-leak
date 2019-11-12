package com.backdoored.hacks.chatbot;

import com.backdoored.event.PlayerLeave;
import com.backdoored.event.PlayerJoin;
import com.backdoored.event.HackDisabled;
import com.backdoored.event.HackEnabled;
import net.minecraftforge.client.event.ScreenshotEvent;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraft.entity.EntityLivingBase;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemBlock;
import net.minecraft.network.play.client.CPacketPlayerTryUseItemOnBlock;
import com.backdoored.event.PacketSent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import com.backdoored.utils.TimeUtils;
import com.backdoored.event.PlayerMove;
import com.backdoored.utils.Utils;
import com.backdoored.gui.CategoriesInit;
import net.minecraft.block.Block;
import net.minecraft.util.math.Vec3d;
import java.time.Instant;
import com.backdoored.setting.Setting;
import com.backdoored.hacks.BaseHack;

public class Announcer extends BaseHack
{
    private Setting movementMsg;
    private Instant lastSentMovement;
    private Vec3d lastVec;
    private Setting blockMsg;
    private Instant lastSentBlocks;
    private Block lastBlock;
    private int numBlocks;
    private Setting breakMsg;
    private Instant lastSentBreak;
    private Block lastBreak;
    private int numBreaks;
    private Setting attackMsg;
    private Instant lastSentAttack;
    private Setting guiMsg;
    private Setting screenshotMsg;
    private AnnouncerScriptHandler announcerScriptHandler;
    
    public Announcer() {
        super("Announcer", CategoriesInit.CHATBOT, "Dont use this unless your a penis");
        this.movementMsg = new Setting("Movement", this, true);
        this.lastSentMovement = Instant.now();
        this.lastVec = null;
        this.blockMsg = new Setting("Block Place", this, true);
        this.lastSentBlocks = Instant.now();
        this.lastBlock = null;
        this.numBlocks = 0;
        this.breakMsg = new Setting("Block Break", this, true);
        this.lastSentBreak = Instant.now();
        this.lastBreak = null;
        this.numBreaks = 0;
        this.attackMsg = new Setting("Attack Entities", this, true);
        this.lastSentAttack = Instant.now();
        this.guiMsg = new Setting("Gui", this, true);
        this.screenshotMsg = new Setting("Screenshot", this, true);
    }
    
    public void onEnabled() {
        try {
            this.announcerScriptHandler = new AnnouncerScriptHandler();
        }
        catch (Exception e) {
            this.setEnabled(false);
            Utils.printMessage("Failed to initialise Announcer script: " + e.getMessage(), "red");
            e.printStackTrace();
        }
    }
    
    @SubscribeEvent
    public void onMovement(final PlayerMove.Pre event) {
        if (TimeUtils.hasTimePassed(this.lastSentMovement, Instant.now(), 60) && this.movementMsg.getValBoolean() && this.getEnabled()) {
            if (this.lastVec == null) {
                this.lastVec = Announcer.mc.field_71439_g.func_174791_d();
                return;
            }
            final int distance = (int)Math.round(this.lastVec.func_72438_d(Announcer.mc.field_71439_g.func_174791_d()));
            if (distance > 0) {
                this.send(this.announcerScriptHandler.onMove(distance));
                this.lastSentMovement = Instant.now();
            }
        }
    }
    
    @SubscribeEvent
    public void onPacket(final PacketSent event) {
        if (event.packet instanceof CPacketPlayerTryUseItemOnBlock && this.blockMsg.getValBoolean() && this.getEnabled()) {
            final CPacketPlayerTryUseItemOnBlock packet = (CPacketPlayerTryUseItemOnBlock)event.packet;
            final ItemStack itemStack = Announcer.mc.field_71439_g.func_184586_b(packet.func_187022_c());
            if (itemStack.func_77973_b() instanceof ItemBlock) {
                final Block block = ((ItemBlock)itemStack.func_77973_b()).func_179223_d();
                if (this.lastBlock == null) {
                    this.lastBlock = block;
                }
                if (this.lastBlock.equals(block)) {
                    ++this.numBlocks;
                }
            }
            if (TimeUtils.hasTimePassed(this.lastSentBlocks, Instant.now(), 60) && this.numBlocks > 0) {
                this.send(this.announcerScriptHandler.onBlocksPlace(this.numBlocks, itemStack.func_82833_r()));
                this.lastSentBlocks = Instant.now();
                this.lastBlock = null;
            }
        }
    }
    
    @SubscribeEvent
    public void onBlockBreak(final BlockEvent.BreakEvent event) {
        if (this.getEnabled() && event.getPlayer().equals((Object)Announcer.mc.field_71439_g)) {
            final Block brokenBlock = event.getState().func_177230_c();
            if (this.lastBreak == null) {
                this.lastBreak = brokenBlock;
            }
            if (this.lastBreak.equals(brokenBlock)) {
                ++this.numBreaks;
            }
            if (TimeUtils.hasTimePassed(this.lastSentBreak, Instant.now(), 60) && this.numBreaks > 0) {
                this.send(this.announcerScriptHandler.onBlocksBreak(this.numBreaks, brokenBlock.func_149732_F()));
                this.lastSentBreak = Instant.now();
                this.lastBreak = null;
            }
        }
    }
    
    @SubscribeEvent
    public void onAttack(final AttackEntityEvent event) {
        if (event.getTarget() instanceof EntityLivingBase && this.attackMsg.getValBoolean() && this.getEnabled() && TimeUtils.hasTimePassed(this.lastSentAttack, Instant.now(), 60)) {
            this.send(this.announcerScriptHandler.onAttack(event.getTarget().func_145748_c_().func_150260_c()));
            this.lastSentAttack = Instant.now();
        }
    }
    
    @SubscribeEvent
    public void onGui(final GuiOpenEvent event) {
        if (this.guiMsg.getValBoolean() && this.getEnabled() && event.getGui() != null && event.getGui() instanceof GuiInventory) {
            this.send(this.announcerScriptHandler.onOpenInventory((GuiInventory)event.getGui()));
        }
    }
    
    @SubscribeEvent
    public void onScreenshot(final ScreenshotEvent event) {
        if (this.screenshotMsg.getValBoolean() && this.getEnabled()) {
            this.send(this.announcerScriptHandler.onScreenshot());
        }
    }
    
    @SubscribeEvent
    public void onHackEnabled(final HackEnabled event) {
        if (this.getEnabled()) {
            this.send(this.announcerScriptHandler.onModuleEnabled());
        }
    }
    
    @SubscribeEvent
    public void onHackDisabled(final HackDisabled event) {
        if (this.getEnabled()) {
            this.send(this.announcerScriptHandler.onModuleDisabled());
        }
    }
    
    @SubscribeEvent
    public void onPlayerJoin(final PlayerJoin event) {
        if (this.getEnabled()) {
            this.send(this.announcerScriptHandler.onPlayerJoin());
        }
    }
    
    @SubscribeEvent
    public void onPlayerLeave(final PlayerLeave event) {
        if (this.getEnabled()) {
            this.send(this.announcerScriptHandler.onPlayerLeave());
        }
    }
    
    private void send(String msg) {
        if (msg == null) {
            return;
        }
        msg = this.announcerScriptHandler.onSendMessage(msg);
        if (msg == null) {
            return;
        }
        if (this.getEnabled()) {
            Announcer.mc.field_71439_g.func_71165_d(msg + " thanks to Backdoored Client");
        }
    }
}
