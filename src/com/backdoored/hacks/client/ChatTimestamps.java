package com.backdoored.hacks.client;

import java.util.Date;
import java.text.SimpleDateFormat;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import com.backdoored.utils.ColourUtils;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import com.backdoored.gui.CategoriesInit;
import com.backdoored.setting.Setting;
import com.backdoored.hacks.BaseHack;

public class ChatTimestamps extends BaseHack
{
    private Setting includeSeconds;
    private Setting includeMillis;
    private Setting hour;
    private Setting colourS;
    private Setting bracketS;
    
    public ChatTimestamps() {
        super("Chat Timestamps", CategoriesInit.CLIENT, "Timestamps on chat messages");
        this.includeSeconds = new Setting("Seconds", this, false);
        this.includeMillis = new Setting("Milliseconds", this, false);
        this.hour = new Setting("Hour format", this, "12", new String[] { "12", "24" });
        this.colourS = new Setting("Colour", this, "Light Purple", new String[] { "Dark Red", "Red", "Gold", "Yellow", "Dark Green", "Green", "Aqua", "Dark Aqua", "Dark Blue", "Blue", "Light Purple", "Dark Purple", "White", "Gray", "Dark Gray", "Black" });
        this.bracketS = new Setting("Brackets", this, true);
    }
    
    @SubscribeEvent
    public void onClientChat(final ClientChatReceivedEvent event) {
        if (this.getEnabled()) {
            final String colour = ColourUtils.strToColour(this.colourS.getValString());
            final String[] brackets = this.bracketS.getValBoolean() ? new String[] { "<", ">" } : new String[] { "", "" };
            final String msg = colour + brackets[0] + this.getTime() + brackets[1] + "§r " + event.getMessage().func_150254_d();
            event.setMessage((ITextComponent)new TextComponentString(msg));
        }
    }
    
    private String getTime() {
        final StringBuilder sbFormat = new StringBuilder();
        if (this.hour.getValString().equals("12")) {
            sbFormat.append("hh");
        }
        else {
            sbFormat.append("HH");
        }
        sbFormat.append(":mm");
        if (this.includeSeconds.getValBoolean()) {
            sbFormat.append(":ss");
        }
        if (this.includeMillis.getValBoolean()) {
            sbFormat.append(".SSS");
        }
        SimpleDateFormat format;
        if (this.includeMillis.getValBoolean()) {
            format = new SimpleDateFormat(sbFormat.toString());
        }
        else {
            format = new SimpleDateFormat(sbFormat.toString());
        }
        return format.format(new Date());
    }
}
