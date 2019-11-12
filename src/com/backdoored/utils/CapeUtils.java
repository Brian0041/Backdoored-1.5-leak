package com.backdoored.utils;

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import com.backdoored.event.FindCapeEvent;
import java.util.Iterator;
import java.io.Reader;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import com.backdoored.natives.EncryptedStringPool;
import net.minecraftforge.common.MinecraftForge;
import net.minecraft.util.ResourceLocation;
import java.util.ArrayList;

public class CapeUtils
{
    private static ArrayList<String> owners;
    private static ArrayList<String> devs;
    private static ResourceLocation capeLoc;
    private static ResourceLocation devCapeLoc;
    
    public CapeUtils() {
        super();
        MinecraftForge.EVENT_BUS.register((Object)this);
        CapeUtils.owners = new ArrayList<String>();
        CapeUtils.devs = new ArrayList<String>();
        this.getOwners();
        this.getDevs();
    }
    
    private boolean getOwners() {
        try {
            final URL uri = new URL(EncryptedStringPool.poolGet(21));
            final BufferedReader in = new BufferedReader(new InputStreamReader(uri.openStream()));
            String line;
            while ((line = in.readLine()) != null) {
                CapeUtils.owners.add(line);
            }
            in.close();
            System.out.println(EncryptedStringPool.poolGet(22) + CapeUtils.owners.toString());
            return true;
        }
        catch (Exception e) {
            System.out.println(EncryptedStringPool.poolGet(23));
            e.printStackTrace();
            return false;
        }
    }
    
    private boolean getDevs() {
        try {
            final URL uri = new URL(EncryptedStringPool.poolGet(24));
            final BufferedReader in = new BufferedReader(new InputStreamReader(uri.openStream()));
            String line;
            while ((line = in.readLine()) != null) {
                CapeUtils.devs.add(line);
            }
            in.close();
            System.out.println("Gave dev capes to: " + CapeUtils.devs.toString());
            return true;
        }
        catch (Exception e) {
            System.out.println(EncryptedStringPool.poolGet(25));
            e.printStackTrace();
            return false;
        }
    }
    
    public static capeStatus isCaped(final String username) {
        if (CapeUtils.owners == null) {
            new CapeUtils();
        }
        for (final String dev : CapeUtils.devs) {
            if (dev.equalsIgnoreCase(username)) {
                return capeStatus.dev;
            }
        }
        for (final String user : CapeUtils.owners) {
            if (user.equalsIgnoreCase(username)) {
                return capeStatus.owner;
            }
        }
        return capeStatus.none;
    }
    
    @SubscribeEvent
    public void onFindCape(final FindCapeEvent event) {
        switch (isCaped(event.networkPlayerInfo.func_178845_a().getName())) {
            case owner: {
                event.capeLoc = CapeUtils.capeLoc;
                break;
            }
            case dev: {
                event.capeLoc = CapeUtils.devCapeLoc;
                break;
            }
        }
    }
    
    static {
        CapeUtils.owners = null;
        CapeUtils.devs = null;
        CapeUtils.capeLoc = new ResourceLocation("backdoored", EncryptedStringPool.poolGet(19));
        CapeUtils.devCapeLoc = new ResourceLocation("backdoored", EncryptedStringPool.poolGet(20));
    }
    
    private enum capeStatus
    {
        dev, 
        owner, 
        none;
        
        private static final /* synthetic */ capeStatus[] $VALUES;
        
        public static capeStatus[] values() {
            return capeStatus.$VALUES.clone();
        }
        
        public static capeStatus valueOf(final String name) {
            return Enum.valueOf(capeStatus.class, name);
        }
        
        static {
            $VALUES = new capeStatus[] { capeStatus.dev, capeStatus.owner, capeStatus.none };
        }
    }
}
