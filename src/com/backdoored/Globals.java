package com.backdoored;

import com.backdoored.extensions.Wrapper;
import java.util.Comparator;
import java.util.Collection;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import com.backdoored.natives.EncryptedStringPool;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraft.network.NetworkManager;
import java.util.ArrayList;
import java.util.Map;
import com.backdoored.gui.Category;
import com.backdoored.hacks.BaseHack;
import java.util.List;
import net.minecraft.client.Minecraft;

public class Globals
{
    public static final Minecraft mc;
    public static List<BaseHack> hacks;
    public static List<Category> categories;
    public static Map<String, BaseHack> hacksMap;
    public static boolean isAlpha;
    private static ArrayList<Category> temp;
    
    public Globals() {
        super();
    }
    
    public static NetworkManager getNetworkManager() {
        return FMLClientHandler.instance().getClientToServerNetworkManager();
    }
    
    public static BaseHack getHack(final String name) {
        for (final BaseHack hack : Globals.hacks) {
            if (hack.name.equalsIgnoreCase(name)) {
                return hack;
            }
        }
        throw new RuntimeException(EncryptedStringPool.poolGet(10) + name + EncryptedStringPool.poolGet(11));
    }
    
    public static BaseHack getHack(final Class<? extends BaseHack> hackClass) {
        for (final BaseHack hack : Globals.hacks) {
            if (hack.getClass() == hackClass) {
                return hack;
            }
        }
        throw new RuntimeException(EncryptedStringPool.poolGet(12) + hackClass.getName() + EncryptedStringPool.poolGet(11));
    }
    
    public static HashMap<Category, List<BaseHack>> getCategoryMap() {
        final HashMap<Category, List<BaseHack>> retVal = new HashMap<Category, List<BaseHack>>();
        for (final BaseHack hack : Globals.hacks) {
            if (retVal.containsKey(hack.category)) {
                retVal.get(hack.category).add(hack);
            }
            else {
                retVal.put(hack.category, new ArrayList<BaseHack>(Arrays.asList(hack)));
            }
        }
        return retVal;
    }
    
    public static ArrayList<Category> invertedCategories() {
        Globals.temp.clear();
        for (int x = Globals.categories.size() - 1; x >= 0; --x) {
            Globals.temp.add(Globals.categories.get(x));
        }
        return Globals.temp;
    }
    
    static {
        mc = Minecraft.func_71410_x();
        Globals.hacks = new ArrayList<BaseHack>() {
            Globals$1() {
                super();
            }
        };
        Globals.categories = new ArrayList<Category>() {
            Globals$2() {
                super();
            }
        };
        Globals.hacksMap = new HashMap<String, BaseHack>();
        Globals.isAlpha = true;
        Globals.temp = new ArrayList<Category>();
    }
    
    public static class StrLengthComparator implements Comparator<BaseHack>
    {
        public StrLengthComparator() {
            super();
        }
        
        @Override
        public int compare(final BaseHack h1, final BaseHack h2) {
            final int h1Width = Wrapper.fontRenderer.func_78256_a(h1.name);
            final int h2Width = Wrapper.fontRenderer.func_78256_a(h2.name);
            return Integer.compare(h2Width, h1Width);
        }
        
        @Override
        public /* bridge */ int compare(final Object o, final Object o2) {
            return this.compare((BaseHack)o, (BaseHack)o2);
        }
    }
    
    public static class AlphabeticComparator implements Comparator<BaseHack>
    {
        public AlphabeticComparator() {
            super();
        }
        
        @Override
        public int compare(final BaseHack h1, final BaseHack h2) {
            return h1.name.compareTo(h2.name);
        }
        
        @Override
        public /* bridge */ int compare(final Object o, final Object o2) {
            return this.compare((BaseHack)o, (BaseHack)o2);
        }
    }
}
