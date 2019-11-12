package com.backdoored.hacks.render;

import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.network.play.server.SPacketParticles;
import net.minecraft.network.play.server.SPacketExplosion;
import com.backdoored.event.PacketRecieved;
import com.backdoored.gui.CategoriesInit;
import com.backdoored.setting.Setting;
import com.backdoored.hacks.BaseHack;

public class NoRender extends BaseHack
{
    private Setting stopExplosions;
    private Setting stopParticles;
    private Setting HELMET;
    private Setting PORTAL;
    private Setting CROSSHAIRS;
    private Setting BOSSHEALTH;
    private Setting BOSSINFO;
    private Setting ARMOR;
    private Setting HEALTH;
    private Setting FOOD;
    private Setting AIR;
    private Setting HOTBAR;
    private Setting EXPERIENCE;
    private Setting TEXT;
    private Setting HEALTHMOUNT;
    private Setting JUMPBAR;
    private Setting CHAT;
    private Setting PLAYER_LIST;
    private Setting POTION_ICONS;
    private Setting SUBTITLES;
    private Setting FPS_GRAPH;
    private Setting VIGNETTE;
    
    public NoRender() {
        super("No Render", CategoriesInit.RENDER, "Dont render things");
        this.stopExplosions = new Setting("Stop Explosions", this, true);
        this.stopParticles = new Setting("Stop Particles", this, true);
        this.HELMET = new Setting("helmet", this, false);
        this.PORTAL = new Setting("portal", this, false);
        this.CROSSHAIRS = new Setting("crosshair", this, false);
        this.BOSSHEALTH = new Setting("bosshealth", this, false);
        this.BOSSINFO = new Setting("bossinfo", this, false);
        this.ARMOR = new Setting("armor", this, false);
        this.HEALTH = new Setting("health", this, false);
        this.FOOD = new Setting("food", this, false);
        this.AIR = new Setting("air", this, false);
        this.HOTBAR = new Setting("hotbar", this, false);
        this.EXPERIENCE = new Setting("experience", this, false);
        this.TEXT = new Setting("text", this, false);
        this.HEALTHMOUNT = new Setting("horse health", this, false);
        this.JUMPBAR = new Setting("horse jump", this, false);
        this.CHAT = new Setting("chat", this, false);
        this.PLAYER_LIST = new Setting("playerlist", this, false);
        this.POTION_ICONS = new Setting("potion icon", this, false);
        this.SUBTITLES = new Setting("subtitles", this, false);
        this.FPS_GRAPH = new Setting("fps graph", this, false);
        this.VIGNETTE = new Setting("vignette", this, false);
    }
    
    @SubscribeEvent
    public void onPacket(final PacketRecieved event) {
        if (this.getEnabled() && this.stopExplosions.getValBoolean() && event.packet instanceof SPacketExplosion) {
            event.setCanceled(true);
        }
        if (this.getEnabled() && this.stopParticles.getValBoolean() && event.packet instanceof SPacketParticles) {
            event.setCanceled(true);
        }
    }
    
    @SubscribeEvent
    public void onOverlay(final RenderGameOverlayEvent event) {
        switch (event.getType()) {
            case HELMET: {
                if (this.HELMET.getValBoolean()) {
                    event.setCanceled(true);
                    break;
                }
                break;
            }
            case PORTAL: {
                if (this.PORTAL.getValBoolean()) {
                    event.setCanceled(true);
                    break;
                }
                break;
            }
            case CROSSHAIRS: {
                if (this.CROSSHAIRS.getValBoolean()) {
                    event.setCanceled(true);
                    break;
                }
                break;
            }
            case BOSSHEALTH: {
                if (this.BOSSHEALTH.getValBoolean()) {
                    event.setCanceled(true);
                    break;
                }
                break;
            }
            case BOSSINFO: {
                if (this.BOSSINFO.getValBoolean()) {
                    event.setCanceled(true);
                    break;
                }
                break;
            }
            case ARMOR: {
                if (this.ARMOR.getValBoolean()) {
                    event.setCanceled(true);
                    break;
                }
                break;
            }
            case HEALTH: {
                if (this.HEALTH.getValBoolean()) {
                    event.setCanceled(true);
                    break;
                }
                break;
            }
            case FOOD: {
                if (this.FOOD.getValBoolean()) {
                    event.setCanceled(true);
                    break;
                }
                break;
            }
            case AIR: {
                if (this.AIR.getValBoolean()) {
                    event.setCanceled(true);
                    break;
                }
                break;
            }
            case HOTBAR: {
                if (this.HOTBAR.getValBoolean()) {
                    event.setCanceled(true);
                    break;
                }
                break;
            }
            case EXPERIENCE: {
                if (this.EXPERIENCE.getValBoolean()) {
                    event.setCanceled(true);
                    break;
                }
                break;
            }
            case TEXT: {
                if (this.TEXT.getValBoolean()) {
                    event.setCanceled(true);
                    break;
                }
                break;
            }
            case HEALTHMOUNT: {
                if (this.HEALTHMOUNT.getValBoolean()) {
                    event.setCanceled(true);
                    break;
                }
                break;
            }
            case JUMPBAR: {
                if (this.JUMPBAR.getValBoolean()) {
                    event.setCanceled(true);
                    break;
                }
                break;
            }
            case CHAT: {
                if (this.CHAT.getValBoolean()) {
                    event.setCanceled(true);
                    break;
                }
                break;
            }
            case PLAYER_LIST: {
                if (this.PLAYER_LIST.getValBoolean()) {
                    event.setCanceled(true);
                    break;
                }
                break;
            }
            case POTION_ICONS: {
                if (this.POTION_ICONS.getValBoolean()) {
                    event.setCanceled(true);
                    break;
                }
                break;
            }
            case SUBTITLES: {
                if (this.SUBTITLES.getValBoolean()) {
                    event.setCanceled(true);
                    break;
                }
                break;
            }
            case FPS_GRAPH: {
                if (this.FPS_GRAPH.getValBoolean()) {
                    event.setCanceled(true);
                    break;
                }
                break;
            }
            case VIGNETTE: {
                if (this.VIGNETTE.getValBoolean()) {
                    event.setCanceled(true);
                    break;
                }
                break;
            }
        }
    }
}
