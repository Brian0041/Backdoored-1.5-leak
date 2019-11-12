package com.backdoored.utils;

import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.item.ItemStack;

public final class ItemNBTUtil
{
    public ItemNBTUtil() {
        super();
    }
    
    public static boolean detectNBT(final ItemStack stack) {
        return stack.func_77942_o();
    }
    
    public static void initNBT(final ItemStack stack) {
        if (!detectNBT(stack)) {
            injectNBT(stack, new NBTTagCompound());
        }
    }
    
    public static void injectNBT(final ItemStack stack, final NBTTagCompound nbt) {
        stack.func_77982_d(nbt);
    }
    
    public static NBTTagCompound getNBT(final ItemStack stack) {
        initNBT(stack);
        return stack.func_77978_p();
    }
    
    public static void setBoolean(final ItemStack stack, final String tag, final boolean b) {
        getNBT(stack).func_74757_a(tag, b);
    }
    
    public static void setByte(final ItemStack stack, final String tag, final byte b) {
        getNBT(stack).func_74774_a(tag, b);
    }
    
    public static void setShort(final ItemStack stack, final String tag, final short s) {
        getNBT(stack).func_74777_a(tag, s);
    }
    
    public static void setInt(final ItemStack stack, final String tag, final int i) {
        getNBT(stack).func_74768_a(tag, i);
    }
    
    public static void setLong(final ItemStack stack, final String tag, final long l) {
        getNBT(stack).func_74772_a(tag, l);
    }
    
    public static void setFloat(final ItemStack stack, final String tag, final float f) {
        getNBT(stack).func_74776_a(tag, f);
    }
    
    public static void setDouble(final ItemStack stack, final String tag, final double d) {
        getNBT(stack).func_74780_a(tag, d);
    }
    
    public static void setCompound(final ItemStack stack, final String tag, final NBTTagCompound cmp) {
        if (!tag.equalsIgnoreCase("ench")) {
            getNBT(stack).func_74782_a(tag, (NBTBase)cmp);
        }
    }
    
    public static void setString(final ItemStack stack, final String tag, final String s) {
        getNBT(stack).func_74778_a(tag, s);
    }
    
    public static void setList(final ItemStack stack, final String tag, final NBTTagList list) {
        getNBT(stack).func_74782_a(tag, (NBTBase)list);
    }
    
    public static boolean verifyExistence(final ItemStack stack, final String tag) {
        return !stack.func_190926_b() && detectNBT(stack) && getNBT(stack).func_74764_b(tag);
    }
    
    @Deprecated
    public static boolean verifyExistance(final ItemStack stack, final String tag) {
        return verifyExistence(stack, tag);
    }
    
    public static boolean getBoolean(final ItemStack stack, final String tag, final boolean defaultExpected) {
        return verifyExistence(stack, tag) ? getNBT(stack).func_74767_n(tag) : defaultExpected;
    }
    
    public static byte getByte(final ItemStack stack, final String tag, final byte defaultExpected) {
        return verifyExistence(stack, tag) ? getNBT(stack).func_74771_c(tag) : defaultExpected;
    }
    
    public static short getShort(final ItemStack stack, final String tag, final short defaultExpected) {
        return verifyExistence(stack, tag) ? getNBT(stack).func_74765_d(tag) : defaultExpected;
    }
    
    public static int getInt(final ItemStack stack, final String tag, final int defaultExpected) {
        return verifyExistence(stack, tag) ? getNBT(stack).func_74762_e(tag) : defaultExpected;
    }
    
    public static long getLong(final ItemStack stack, final String tag, final long defaultExpected) {
        return verifyExistence(stack, tag) ? getNBT(stack).func_74763_f(tag) : defaultExpected;
    }
    
    public static float getFloat(final ItemStack stack, final String tag, final float defaultExpected) {
        return verifyExistence(stack, tag) ? getNBT(stack).func_74760_g(tag) : defaultExpected;
    }
    
    public static double getDouble(final ItemStack stack, final String tag, final double defaultExpected) {
        return verifyExistence(stack, tag) ? getNBT(stack).func_74769_h(tag) : defaultExpected;
    }
    
    public static NBTTagCompound getCompound(final ItemStack stack, final String tag, final boolean nullifyOnFail) {
        return verifyExistence(stack, tag) ? getNBT(stack).func_74775_l(tag) : (nullifyOnFail ? null : new NBTTagCompound());
    }
    
    public static String getString(final ItemStack stack, final String tag, final String defaultExpected) {
        return verifyExistence(stack, tag) ? getNBT(stack).func_74779_i(tag) : defaultExpected;
    }
    
    public static NBTTagList getList(final ItemStack stack, final String tag, final int objtype, final boolean nullifyOnFail) {
        return verifyExistence(stack, tag) ? getNBT(stack).func_150295_c(tag, objtype) : (nullifyOnFail ? null : new NBTTagList());
    }
}
