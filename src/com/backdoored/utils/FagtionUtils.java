package com.backdoored.utils;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.FMLLog;
import java.io.Reader;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import com.backdoored.natives.EncryptedStringPool;
import java.util.HashMap;

public class FagtionUtils
{
    private static HashMap<String, String> fagMap;
    
    public FagtionUtils() {
        super();
    }
    
    public static boolean loadFagtions() {
        try {
            final URL uri = new URL(EncryptedStringPool.poolGet(43));
            final BufferedReader in = new BufferedReader(new InputStreamReader(uri.openStream()));
            String line;
            while ((line = in.readLine()) != null) {
                final String[] tempWords = line.split(":");
                FagtionUtils.fagMap.put(tempWords[0], tempWords[1]);
                FMLLog.log.info(tempWords[0] + " is in " + tempWords[1]);
            }
            in.close();
            return true;
        }
        catch (Exception e) {
            FMLLog.log.info("Failed to load fagtions");
            e.printStackTrace();
            return false;
        }
    }
    
    public static String getFagtion(final EntityPlayer player) {
        return FagtionUtils.fagMap.getOrDefault(player.func_70005_c_(), "");
    }
    
    public static String getFagtion(final String name) {
        return FagtionUtils.fagMap.getOrDefault(name, "");
    }
    
    static {
        FagtionUtils.fagMap = new HashMap<String, String>();
    }
}
