package com.backdoored.hacks.client;

import java.util.Objects;
import com.backdoored.utils.Utils;
import org.apache.logging.log4j.LogManager;
import com.backdoored.hacks.chatbot.ChatBotScriptHandler;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.util.text.ChatType;
import java.util.regex.Pattern;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import com.backdoored.gui.CategoriesInit;
import com.backdoored.hacks.chatbot.ScriptHandler;
import com.backdoored.setting.Setting;
import com.backdoored.hacks.BaseHack;

public class ChatFilter extends BaseHack
{
    private final Setting whispers;
    private final Setting mentions;
    private final Setting gameInfo;
    private ScriptHandler scriptHandler;
    
    public ChatFilter() {
        super("Chat Filter", "Filter your chat", CategoriesInit.MISC);
        this.whispers = new Setting("Allow Whispers", this, true);
        this.mentions = new Setting("Allow Mentions", this, true);
        this.gameInfo = new Setting("Allow Game Info", this, true);
    }
    
    @SubscribeEvent
    public void onClientChatRecieved(final ClientChatReceivedEvent event) {
        if (this.getEnabled()) {
            event.setCanceled(true);
            final String message = event.getMessage().func_150260_c().toLowerCase();
            if (this.whispers.getValBoolean()) {
                final String[] split = message.split(Pattern.quote(" "));
                if (split.length >= 3 && split[1].equals("whispers:")) {
                    event.setCanceled(false);
                }
            }
            if (this.mentions.getValBoolean() && message.contains(ChatFilter.mc.field_71439_g.func_70005_c_().toLowerCase())) {
                event.setCanceled(false);
            }
            if (this.gameInfo.getValBoolean() && event.getType() == ChatType.GAME_INFO) {
                event.setCanceled(false);
            }
            if (!event.isCanceled()) {
                event.setCanceled(this.isExcludedByScript(event.getMessage().func_150260_c()));
            }
        }
    }
    
    public boolean isExcludedByScript(final String text) {
        if (this.scriptHandler == null) {
            try {
                this.scriptHandler = new ScriptHandler().eval(ChatBotScriptHandler.getScriptContents("Backdoored/chatfilter.js")).addLogger(LogManager.getLogger("BackdooredChatFilter"));
            }
            catch (Exception e) {
                this.setEnabled(false);
                Utils.printMessage("Failed to initialise Chat Filter script: " + e.getMessage(), "red");
                e.printStackTrace();
                return false;
            }
        }
        try {
            final Object out = this.scriptHandler.invokeFunction("isExcluded", text);
            return Objects.requireNonNull(out);
        }
        catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
