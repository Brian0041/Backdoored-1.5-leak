package com.backdoored.commands;

import java.util.Iterator;
import java.util.Arrays;
import com.backdoored.setting.Setting;
import com.backdoored.setting.SettingsManager;
import com.backdoored.hacks.BaseHack;
import com.backdoored.Globals;
import com.backdoored.utils.Utils;

public class Bind extends CommandBase
{
    public Bind() {
        super(new String[] { "bind", "keybind" });
    }
    
    @Override
    public boolean execute(final String[] args) {
        if (args.length <= 0) {
            Utils.printMessage("Please specify a hack!");
            return false;
        }
        StringBuilder hack = new StringBuilder();
        for (final String s : args) {
            hack.append(s);
        }
        for (final BaseHack x : Globals.hacks) {
            if (hack.toString().equalsIgnoreCase(x.name.replace(" ", ""))) {
                for (final Setting setting : SettingsManager.getSettingsByMod(x)) {
                    if (setting.isBind()) {
                        Utils.printMessage(x.name + ": " + x);
                        return true;
                    }
                }
            }
        }
        hack = new StringBuilder();
        for (final String s : Arrays.copyOf(args, args.length - 1)) {
            hack.append(s);
        }
        for (final BaseHack x : Globals.hacks) {
            if (hack.toString().equalsIgnoreCase(x.name.replace(" ", ""))) {
                for (final Setting setting : SettingsManager.getSettingsByMod(x)) {
                    if (setting.isBind()) {
                        setting.setVal(args[args.length - 1].toUpperCase());
                        Utils.printMessage("Set keybind of hack '" + x.name + "' to '" + setting.getValString() + "'");
                        return true;
                    }
                }
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
