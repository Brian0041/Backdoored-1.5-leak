package com.backdoored.commands;

import com.backdoored.utils.Utils;

public class Prefix extends CommandBase
{
    public Prefix() {
        super("prefix");
    }
    
    @Override
    public boolean execute(final String[] args) {
        if (args.length > 0) {
            Command.commandPrefix = args[0];
            Utils.printMessage("Set cmd prefix to " + Command.commandPrefix, "red");
            return true;
        }
        return false;
    }
    
    @Override
    public String getSyntax() {
        return "Usage: .prefix <new prefix character>";
    }
}
