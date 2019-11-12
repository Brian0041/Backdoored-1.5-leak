package com.backdoored.commands;

import java.io.File;
import com.backdoored.utils.Utils;
import com.backdoored.config.ConfigJsonManager;

public class Save extends CommandBase
{
    public Save() {
        super("save");
    }
    
    @Override
    public boolean execute(final String[] args) {
        if (args.length <= 0) {
            ConfigJsonManager.save();
            Utils.printMessage("Saved config", "red");
            return true;
        }
        ConfigJsonManager.save(new File("Backdoored/config-" + args[0].toLowerCase() + ".json"));
        Utils.printMessage("Saved new config under name: " + args[0].toLowerCase(), "red");
        return true;
    }
    
    @Override
    public String getSyntax() {
        return "-save <save name>";
    }
}
