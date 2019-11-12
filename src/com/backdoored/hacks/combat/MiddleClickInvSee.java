package com.backdoored.hacks.combat;

import com.backdoored.utils.NoStackTraceThrowable;
import com.backdoored.DrmManager;
import net.minecraftforge.fml.common.FMLLog;
import com.backdoored.Backdoored;
import java.nio.charset.StandardCharsets;
import com.google.common.hash.Hashing;
import com.backdoored.utils.Utils;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.input.Mouse;
import net.minecraftforge.client.event.MouseEvent;
import com.backdoored.gui.CategoriesInit;
import net.minecraft.entity.player.EntityPlayer;
import com.backdoored.hacks.BaseHack;

public class MiddleClickInvSee extends BaseHack
{
    private EntityPlayer player;
    
    public MiddleClickInvSee() {
        super("Middle Click Inv See", CategoriesInit.COMBAT, "Middle Click another player to see their inventory");
    }
    
    @SubscribeEvent
    public void onMouseEvent(final MouseEvent event) {
        if (this.getEnabled() && Mouse.isButtonDown(2) && MiddleClickInvSee.mc.field_71476_x.field_72308_g instanceof EntityPlayer) {
            this.player = (EntityPlayer)MiddleClickInvSee.mc.field_71476_x.field_72308_g;
        }
    }
    
    public void onRender() {
        if (this.player != null) {
            try {
                MiddleClickInvSee.mc.func_147108_a((GuiScreen)new GuiInventory(this.player));
                this.player = null;
            }
            catch (Exception e) {
                e.printStackTrace();
                Utils.printMessage("Could not display inventory: " + e.toString());
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
