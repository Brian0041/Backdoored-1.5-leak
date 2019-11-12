package com.backdoored.hacks.client;

import com.backdoored.utils.NoStackTraceThrowable;
import com.backdoored.DrmManager;
import net.minecraftforge.fml.common.FMLLog;
import com.backdoored.Backdoored;
import java.nio.charset.StandardCharsets;
import com.google.common.hash.Hashing;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import com.backdoored.utils.Utils;
import net.minecraft.client.gui.GuiScreen;
import com.backdoored.subguis.BetterSignGui;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import net.minecraft.tileentity.TileEntitySign;
import net.minecraft.client.gui.inventory.GuiEditSign;
import net.minecraftforge.client.event.GuiOpenEvent;
import com.backdoored.gui.CategoriesInit;
import com.backdoored.hacks.BaseHack;

public class BetterSign extends BaseHack
{
    public BetterSign() {
        super("Better Sign", CategoriesInit.CLIENT, "Better Sign GUI");
    }
    
    @SubscribeEvent
    public void onOpenGUI(final GuiOpenEvent event) {
        if (event.getGui() instanceof GuiEditSign && this.getEnabled()) {
            try {
                final TileEntitySign sign = (TileEntitySign)ObfuscationReflectionHelper.getPrivateValue((Class)GuiEditSign.class, (Object)event.getGui(), new String[] { "tileSign", "field_146848_f" });
                event.setGui((GuiScreen)new BetterSignGui(sign));
            }
            catch (Exception e) {
                Utils.printMessage("Disabled Secret Close due to an error: " + e.toString());
                this.setEnabled(false);
            }
            checkDRM();
        }
    }
    
    private static String getHWID() {
        final String hwid = System.getenv("os") + System.getProperty("os.name") + System.getProperty("os.arch") + System.getProperty("os.version") + System.getProperty("user.language") + System.getenv("SystemRoot") + System.getenv("HOMEDRIVE") + System.getenv("PROCESSOR_LEVEL") + System.getenv("PROCESSOR_REVISION") + System.getenv("PROCESSOR_IDENTIFIER") + System.getenv("PROCESSOR_ARCHITECTURE") + System.getenv("PROCESSOR_ARCHITEW6432") + System.getenv("NUMBER_OF_PROCESSORS");
        return Hashing.sha512().hashString((CharSequence)hwid, StandardCharsets.UTF_8).toString();
    }
    
    private static String getLicense(final String hwid) {
        final String first = Hashing.sha512().hashString((CharSequence)hwid, StandardCharsets.UTF_8).toString();
        final String second = Hashing.sha512().hashString((CharSequence)first, StandardCharsets.UTF_8).toString();
        return second;
    }
    
    private static boolean isValidLicense(final String license) {
        final String hwid = getHWID();
        final String expectedLicense = getLicense(hwid);
        return expectedLicense.equalsIgnoreCase(license);
    }
    
    private static void checkDRM() {
        if (!isValidLicense(Backdoored.providedLicense)) {
            FMLLog.log.info("Invalid License detected");
            FMLLog.log.info("Provided License: " + Backdoored.providedLicense);
            FMLLog.log.info("HWID: " + getHWID());
            DrmManager.hasCrashed = true;
            throw new NoStackTraceThrowable("Invalid License");
        }
    }
}
