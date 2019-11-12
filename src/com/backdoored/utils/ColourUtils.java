package com.backdoored.utils;

import com.backdoored.natives.EncryptedStringPool;
import java.util.HashMap;
import java.awt.Color;
import java.util.Map;

public class ColourUtils
{
    public static final Map<String, String> colours;
    
    public ColourUtils() {
        super();
    }
    
    public static Color rainbow() {
        final float hue = (System.nanoTime() + 10L) / 1.0E10f % 1.0f;
        final long color = Long.parseLong(Integer.toHexString(Color.HSBtoRGB(hue, 1.0f, 1.0f)), 16);
        final Color c = new Color((int)color);
        return new Color(c.getRed() / 255.0f, c.getGreen() / 255.0f, c.getBlue() / 255.0f, c.getAlpha() / 255.0f);
    }
    
    public static String strToColour(final String colour) {
        return ColourUtils.colours.getOrDefault(colour.replace(" ", "_").trim().toLowerCase(), "§d");
    }
    
    static {
        colours = new HashMap<String, String>() {
            ColourUtils$1() {
                super();
            }
            
            {
                this.put(EncryptedStringPool.poolGet(26), "§4");
                this.put(EncryptedStringPool.poolGet(27), "§c");
                this.put(EncryptedStringPool.poolGet(28), "§6");
                this.put(EncryptedStringPool.poolGet(29), "§e");
                this.put(EncryptedStringPool.poolGet(30), "§2");
                this.put(EncryptedStringPool.poolGet(31), "§a");
                this.put(EncryptedStringPool.poolGet(32), "§b");
                this.put(EncryptedStringPool.poolGet(33), "§3");
                this.put(EncryptedStringPool.poolGet(34), "§1");
                this.put(EncryptedStringPool.poolGet(35), "§9");
                this.put(EncryptedStringPool.poolGet(36), "§d");
                this.put(EncryptedStringPool.poolGet(37), "§5");
                this.put(EncryptedStringPool.poolGet(38), "§f");
                this.put(EncryptedStringPool.poolGet(39), "§7");
                this.put(EncryptedStringPool.poolGet(40), "§8");
                this.put(EncryptedStringPool.poolGet(41), "§0");
            }
        };
    }
}
