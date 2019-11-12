package com.backdoored.commands;

import java.util.Iterator;
import com.backdoored.utils.Utils;

public class Help extends CommandBase
{
    public Help() {
        super(new String[] { "help", "commands" });
    }
    
    @Override
    public boolean execute(final String[] args) {
        Utils.printMessage("Commands:\n");
        for (final CommandBase cmd : CommandBase.objects) {
            Utils.printMessage(cmd.aliases.get(0) + "\n");
        }
        Utils.printMessage("Commands:\ntoggle/t> Toggle a module\nfriend> add/del/list friends\nprefix> change cmd prefix\nfakemsg> fake a msg from someone\nspectate> spectate a player\nsave> save a config under a name\nload> load a config by name\nfakeplayer> fake a player\nimport> import a friends list from another client ", "red");
        return true;
    }
    
    @Override
    public String getSyntax() {
        return "Commands:\ntoggle/t> Toggle a module\nfriend> add/del/list friends\nprefix> change cmd prefix\nfakemsg> fake a msg from someone\nspectate> spectate a player\nsave> save a config under a name\nload> load a config by name\nfakeplayer> fake a player\nimport> import a friends list from another client ";
    }
}
