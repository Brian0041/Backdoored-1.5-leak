package com.backdoored.setting;

import java.util.Iterator;
import com.backdoored.hacks.BaseHack;
import java.util.ArrayList;

public class SettingsManager
{
    static ArrayList<Setting> settings;
    
    public SettingsManager() {
        super();
    }
    
    public static ArrayList<Setting> getSettings() {
        return SettingsManager.settings;
    }
    
    public static ArrayList<Setting> getSettingsByMod(final BaseHack mod) {
        final ArrayList<Setting> out = new ArrayList<Setting>();
        for (final Setting s : getSettings()) {
            if (s.hack == mod) {
                out.add(s);
            }
        }
        return out;
    }
    
    public static Setting getSetting(final BaseHack hack, final String name) {
        for (final Setting set : getSettings()) {
            if (set.getName().equalsIgnoreCase(name) && set.hack == hack) {
                return set;
            }
        }
        System.err.println("Error Setting NOT found: '" + hack.name + "-" + name + "'!");
        return null;
    }
    
    static {
        SettingsManager.settings = new ArrayList<Setting>();
    }
}
