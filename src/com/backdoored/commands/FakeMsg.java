package com.backdoored.commands;

import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.ChatType;
import com.backdoored.utils.Utils;

public class FakeMsg extends CommandBase
{
    public FakeMsg() {
        super(new String[] { "fakemsg", "msg", "impersonate" });
    }
    
    @Override
    public boolean execute(final String[] args) {
        if (args.length < 3) {
            return false;
        }
        final String s3 = args[0];
        switch (s3) {
            default: {
                final StringBuilder sb = new StringBuilder();
                sb.append("<").append(args[1]).append("> ");
                for (int i = 2; i < args.length; ++i) {
                    sb.append(args[i]).append(" ");
                }
                Utils.printMessage(sb.toString());
                return true;
            }
            case "whisper": {
                final String s2 = args[1];
                final StringBuilder sb2 = new StringBuilder();
                for (int j = 2; j < args.length; ++j) {
                    sb2.append(args[j]).append(" ");
                }
                this.mc.field_71456_v.func_191742_a(ChatType.CHAT, (ITextComponent)new TextComponentString("§d" + s2 + " whispers: " + sb2.toString()));
                return true;
            }
            case "server": {
                final StringBuilder sb = new StringBuilder("§e[SERVER] ");
                for (int j = 1; j < args.length; ++j) {
                    sb.append(args[j]).append(" ");
                }
                Utils.printMessage(sb.toString());
                return true;
            }
            case "suicide": {
                final String firstPlayer = args[1];
                final StringBuilder sb = new StringBuilder("§4");
                for (int k = 2; k < args.length; ++k) {
                    sb.append(args[k]).append(" ");
                }
                String msg = sb.toString();
                msg = msg.replace(" player ", " §3" + firstPlayer + " §4");
                Utils.printMessage(msg);
                return true;
            }
            case "kill": {
                final String firstPlayer = args[1];
                final String secondPlayer = args[2];
                final StringBuilder sb = new StringBuilder("§4");
                for (int l = 3; l < args.length; ++l) {
                    sb.append(args[l]).append(" ");
                }
                String msg = sb.toString();
                msg = msg.replace(" player1 ", " §3" + firstPlayer + " §4");
                msg = msg.replace(" player2 ", " §3" + secondPlayer + " §4");
                Utils.printMessage(msg);
                return true;
            }
            case "killWeapon": {
                final String firstPlayer = args[1];
                final String secondPlayer = args[2];
                final String weapon = args[3];
                final StringBuilder sb = new StringBuilder("§4");
                for (int m = 4; m < args.length; ++m) {
                    sb.append(args[m]).append(" ");
                }
                String msg = sb.toString();
                msg = msg.replace(" player1 ", " §3" + firstPlayer + " §4");
                msg = msg.replace(" player2 ", " §3" + secondPlayer + " §4");
                msg = msg.replace(" weapon ", " §6" + weapon + " §4");
                Utils.printMessage(msg);
                return true;
            }
        }
    }
    
    @Override
    public String getSyntax() {
        return "-fakemsg chat 4yl im kinda ez ngl\n-fakemsg whisper John200410 Backdoored client on top\n-fakemsg server buy prio pls";
    }
}
