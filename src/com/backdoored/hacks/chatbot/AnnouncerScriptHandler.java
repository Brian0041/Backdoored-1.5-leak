package com.backdoored.hacks.chatbot;

import net.minecraft.client.gui.inventory.GuiInventory;
import java.util.Objects;
import com.backdoored.utils.Utils;

public class AnnouncerScriptHandler extends ChatBotScriptHandler
{
    private static final String FILENAME = "Backdoored/announcer.js";
    
    public AnnouncerScriptHandler() throws Exception {
        super();
        this.scriptHandler = new ScriptHandler().eval(ChatBotScriptHandler.getScriptContents("Backdoored/announcer.js")).addLogger(AnnouncerScriptHandler.logger);
        Utils.printMessage("Successfully read and evaluated announcer script", "green");
    }
    
    String onSendMessage(final String msg) {
        try {
            return Objects.requireNonNull(this.scriptHandler.invokeFunction("onSendMessage", msg));
        }
        catch (Exception e) {
            e.printStackTrace();
            return msg;
        }
    }
    
    String onMove(final int blocks) {
        return this.invokeForString("onMove", blocks);
    }
    
    String onAttack(final String player) {
        return this.invokeForString("onAttack", player);
    }
    
    String onBlocksBreak(final int num, final String block) {
        return this.invokeForString("onBlocksBreak", num, block);
    }
    
    String onBlocksPlace(final int num, final String block) {
        return this.invokeForString("onBlocksPlace", num, block);
    }
    
    String onOpenInventory(final GuiInventory inventory) {
        return this.invokeForString("onOpenInventory", new Object[0]);
    }
    
    String onScreenshot() {
        return this.invokeForString("onScreenshot", new Object[0]);
    }
    
    String onModuleEnabled() {
        return this.invokeForString("onModuleEnabled", new Object[0]);
    }
    
    String onModuleDisabled() {
        return this.invokeForString("onModuleDisabled", new Object[0]);
    }
    
    String onPlayerJoin() {
        return this.invokeForString("onPlayerJoin", new Object[0]);
    }
    
    String onPlayerLeave() {
        return this.invokeForString("onPlayerLeave", new Object[0]);
    }
    
    private String invokeForString(final String name, final Object... args) {
        return this.invokeForString(name, args);
    }
}
