package com.backdoored.commands;

import java.util.Iterator;
import com.backdoored.utils.Utils;
import com.backdoored.hacks.BaseHack;
import com.backdoored.Globals;

public class Toggle extends CommandBase
{
    public Toggle() {
        super(new String[] { "toggle", "t" });
    }
    
    @Override
    public boolean execute(final String[] args) {
        final StringBuilder hack = new StringBuilder();
        for (final String s : args) {
            hack.append(s);
        }
        for (final BaseHack x : Globals.hacks) {
            if (hack.toString().equalsIgnoreCase(x.name.replace(" ", ""))) {
                x.setEnabled(!x.getEnabled());
                return true;
            }
        }
        Utils.printMessage(hack.toString() + " not found!", "red");
        return false;
    }
    
    @Override
    public String getSyntax() {
        return "-t <hackname>";
    }
}
