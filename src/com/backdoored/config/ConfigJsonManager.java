package com.backdoored.config;

import java.util.ArrayList;
import java.io.Writer;
import java.io.PrintWriter;
import java.io.FileWriter;
import java.util.Iterator;
import com.backdoored.commands.Command;
import com.backdoored.gui.Category;
import com.backdoored.setting.Setting;
import com.backdoored.setting.SettingsManager;
import com.backdoored.hacks.BaseHack;
import com.backdoored.Globals;
import org.apache.commons.io.FileUtils;
import java.nio.charset.Charset;
import org.json.JSONObject;
import java.io.File;

public class ConfigJsonManager
{
    private static final File FILE;
    public static JSONObject rootObj;
    private static JSONObject hacksObj;
    private static JSONObject categoriesObj;
    private static JSONObject commandsObj;
    
    public ConfigJsonManager() {
        super();
    }
    
    public static void read() {
        read(ConfigJsonManager.FILE);
    }
    
    public static void read(final File file) {
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
            String jsonS = FileUtils.readFileToString(file, Charset.defaultCharset());
            if (jsonS.trim().isEmpty() && file.renameTo(file)) {
                save();
                jsonS = FileUtils.readFileToString(file, Charset.defaultCharset());
            }
            ConfigJsonManager.rootObj = new JSONObject(jsonS);
            ConfigJsonManager.hacksObj = ConfigJsonManager.rootObj.getJSONObject("Hacks");
            ConfigJsonManager.categoriesObj = ConfigJsonManager.rootObj.getJSONObject("Categories");
            ConfigJsonManager.commandsObj = ConfigJsonManager.rootObj.getJSONObject("Commands");
            for (final BaseHack x : Globals.hacks) {
                if (getHackVal(x, "Enabled") != null) {
                    x.setEnabled((boolean)getHackVal(x, "Enabled"));
                }
                for (final Setting y : SettingsManager.getSettingsByMod(x)) {
                    if (getHackVal(x, y.getName()) != null) {
                        y.setVal(getHackVal(x, y.getName()));
                    }
                }
            }
            for (final Category x2 : Globals.categories) {
                if (getCategoryVal(x2, "x") != null) {
                    x2.button.x = (int)getCategoryVal(x2, "x");
                }
                if (getCategoryVal(x2, "y") != null) {
                    x2.button.y = (int)getCategoryVal(x2, "y");
                }
                if (getCategoryVal(x2, "open") != null) {
                    x2.button.rightClickToggled = (boolean)getCategoryVal(x2, "open");
                }
            }
            if (getCommandVal("prefix") != null) {
                Command.commandPrefix = (String)getCommandVal("prefix");
            }
        }
        catch (Exception e) {
            System.out.println("ERROR READING BACKDOORED CONFIG FILE!!!");
            e.printStackTrace();
        }
    }
    
    public static Object getHackVal(final BaseHack hack, final String key) {
        return getHackVal(hack.name, key);
    }
    
    public static Object getHackVal(final String hack, final String key) {
        try {
            return ConfigJsonManager.hacksObj.getJSONObject(hack).get(key);
        }
        catch (Exception e) {
            return null;
        }
    }
    
    public static Object getCategoryVal(final Category category, final String key) {
        return getCategoryVal(category.button.text, key);
    }
    
    public static Object getCategoryVal(final String category, final String key) {
        try {
            return ConfigJsonManager.categoriesObj.getJSONObject(category).get(key);
        }
        catch (Exception e) {
            return null;
        }
    }
    
    public static Object getCommandVal(final String key) {
        try {
            return ConfigJsonManager.commandsObj.get(key);
        }
        catch (Exception e) {
            return null;
        }
    }
    
    public static void save() {
        save(ConfigJsonManager.FILE);
    }
    
    public static void save(final File file) {
        ConfigJsonManager.rootObj.put("Hacks", (Object)processHacks());
        ConfigJsonManager.rootObj.put("Categories", (Object)processCategories());
        ConfigJsonManager.rootObj.put("Commands", (Object)processCommands());
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
            if (file.renameTo(file)) {
                final PrintWriter printWriter = new PrintWriter(new FileWriter(file));
                printWriter.print(ConfigJsonManager.rootObj.toString(4));
                printWriter.close();
            }
            else {
                System.out.println("Failed to save, file already in use");
            }
        }
        catch (Exception e) {
            System.out.println("ERROR SAVING BACKDOORED CONFIG FILE!!!");
            e.printStackTrace();
        }
    }
    
    private static JSONObject processHacks() {
        for (final BaseHack hack : Globals.hacks) {
            final JSONObject thisObj = new JSONObject();
            thisObj.put("Enabled", hack.button.leftClickToggled);
            final ArrayList<Setting> settings = SettingsManager.getSettingsByMod(hack);
            if (settings != null) {
                for (final Setting setting : settings) {
                    thisObj.put(setting.getName(), setting.getVal());
                }
            }
            ConfigJsonManager.hacksObj.put(hack.name, (Object)thisObj);
        }
        return ConfigJsonManager.hacksObj;
    }
    
    private static JSONObject processCategories() {
        for (final Category category : Globals.categories) {
            final JSONObject thisObj = new JSONObject();
            thisObj.put("x", category.button.x);
            thisObj.put("y", category.button.y);
            thisObj.put("open", category.button.rightClickToggled);
            ConfigJsonManager.categoriesObj.put(category.button.text, (Object)thisObj);
        }
        return ConfigJsonManager.categoriesObj;
    }
    
    private static JSONObject processCommands() {
        ConfigJsonManager.commandsObj.put("prefix", (Object)Command.commandPrefix);
        return ConfigJsonManager.commandsObj;
    }
    
    static {
        FILE = new File("Backdoored/config.json");
        ConfigJsonManager.rootObj = new JSONObject();
        ConfigJsonManager.hacksObj = new JSONObject();
        ConfigJsonManager.categoriesObj = new JSONObject();
        ConfigJsonManager.commandsObj = new JSONObject();
    }
}
