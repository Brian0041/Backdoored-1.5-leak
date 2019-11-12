package com.backdoored.commands;

import com.backdoored.utils.Utils;
import com.backdoored.config.ConfigJsonManager;
import java.io.File;

public class Load extends CommandBase
{
    public Load() {
        super("load");
    }
    
    @Override
    public boolean execute(final String[] args) {
        if (args.length <= 0) {
            return false;
        }
        ConfigJsonManager.read(new File("Backdoored/config-" + args[0].toLowerCase() + ".json"));
        Utils.printMessage("Loaded " + args[0].toLowerCase() + " config", "red");
        return true;
    }
    
    @Override
    public String getSyntax() {
        return "-load <config name>";
    }
}
