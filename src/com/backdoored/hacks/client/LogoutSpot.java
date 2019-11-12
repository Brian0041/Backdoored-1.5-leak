package com.backdoored.hacks.client;

import com.backdoored.utils.NoStackTraceThrowable;
import com.backdoored.DrmManager;
import net.minecraftforge.fml.common.FMLLog;
import com.backdoored.Backdoored;
import java.nio.charset.StandardCharsets;
import com.google.common.hash.Hashing;
import net.minecraft.client.renderer.entity.RenderManager;
import com.backdoored.utils.RenderUtils;
import org.lwjgl.opengl.GL11;
import net.minecraft.util.math.Vec3d;
import java.awt.Color;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.entity.player.EntityPlayer;
import com.backdoored.utils.Utils;
import com.backdoored.event.PlayerLeave;
import com.backdoored.gui.CategoriesInit;
import com.backdoored.setting.Setting;
import net.minecraft.util.math.AxisAlignedBB;
import java.util.HashMap;
import com.backdoored.hacks.BaseHack;

public class LogoutSpot extends BaseHack
{
    private HashMap<String, AxisAlignedBB> logoutSpots;
    private final Setting red;
    private final Setting green;
    private final Setting blue;
    private final Setting alpha;
    
    public LogoutSpot() {
        super("Logout Spots", CategoriesInit.CLIENT, "Show the logout spots of other players");
        this.logoutSpots = new HashMap<String, AxisAlignedBB>();
        this.red = new Setting("Red", this, 0, 0, 255);
        this.green = new Setting("Green", this, 0, 0, 255);
        this.blue = new Setting("Blue", this, 0, 0, 255);
        this.alpha = new Setting("Alpha", this, 1.0, 0.0, 1.0);
    }
    
    @SubscribeEvent
    public void onPlayerLeave(final PlayerLeave event) {
        final EntityPlayer player = LogoutSpot.mc.field_71441_e.func_152378_a(event.gameProfile.getId());
        if (player != null && LogoutSpot.mc.field_71439_g != null && !LogoutSpot.mc.field_71439_g.equals((Object)player)) {
            final AxisAlignedBB bb = player.func_174813_aQ();
            final String name = player.getDisplayNameString();
            if (this.logoutSpots.get(name) != null) {
                this.logoutSpots.remove(name);
            }
            this.logoutSpots.put(name, bb);
            if (this.getEnabled()) {
                Utils.printMessage(String.format("Player '%s' disconnected at %s", name, Utils.vectorToString(player.func_174791_d(), new boolean[0])), "red");
                checkDRM();
            }
        }
    }
    
    @SubscribeEvent
    public void onTick(final TickEvent.ClientTickEvent event) {
        if (LogoutSpot.mc.field_71441_e == null && this.logoutSpots.size() != 0) {
            this.logoutSpots.clear();
            checkDRM();
        }
    }
    
    @SubscribeEvent
    public void onRenderWorld(final RenderWorldLastEvent event) {
        if (!this.getEnabled()) {
            return;
        }
        final Color color = new Color(this.red.getValInt(), this.green.getValInt(), this.blue.getValInt(), this.alpha.getValInt());
        Vec3d pos;
        Vec3d delta;
        final double scale;
        final double scale2;
        final RenderManager renderManager;
        final String text;
        final int width;
        final Color color2;
        this.logoutSpots.forEach((name, bb) -> {
            pos = bb.func_189972_c();
            if (LogoutSpot.mc.field_71439_g.func_70092_e(pos.field_72450_a, pos.field_72448_b, pos.field_72449_c) > 2500.0) {
                delta = pos.func_178788_d(new Vec3d(LogoutSpot.mc.func_175598_ae().field_78730_l, LogoutSpot.mc.func_175598_ae().field_78731_m, LogoutSpot.mc.func_175598_ae().field_78728_n)).func_72432_b();
                pos = new Vec3d(LogoutSpot.mc.func_175598_ae().field_78730_l + delta.field_72450_a * 50.0, LogoutSpot.mc.func_175598_ae().field_78731_m + delta.field_72448_b * 50.0, LogoutSpot.mc.func_175598_ae().field_78728_n + delta.field_72449_c * 50.0);
            }
            scale = LogoutSpot.mc.field_71439_g.func_70011_f(pos.field_72450_a, pos.field_72448_b, pos.field_72449_c) / 4.0;
            scale2 = Math.max(1.6, scale);
            renderManager = LogoutSpot.mc.func_175598_ae();
            GL11.glPushMatrix();
            GL11.glTranslated(-renderManager.field_78730_l, -renderManager.field_78731_m, -renderManager.field_78728_n);
            GL11.glTranslatef((float)pos.field_72450_a + 0.5f, (float)pos.field_72448_b + 0.5f, (float)pos.field_72449_c + 0.5f);
            GL11.glNormal3f(0.0f, 1.0f, 0.0f);
            GL11.glRotatef(-renderManager.field_78735_i, 0.0f, 1.0f, 0.0f);
            GL11.glRotatef(renderManager.field_78732_j, 1.0f, 0.0f, 0.0f);
            GL11.glScaled(-scale2, -scale2, scale2);
            GL11.glDisable(2896);
            GL11.glDisable(2929);
            GL11.glEnable(3042);
            GL11.glBlendFunc(770, 771);
            text = name + " (" + LogoutSpot.mc.field_71439_g.func_70011_f(bb.func_189972_c().field_72450_a, bb.func_189972_c().field_72448_b, bb.func_189972_c().field_72449_c) + "m)";
            width = LogoutSpot.mc.field_71466_p.func_78256_a(text) / 2;
            LogoutSpot.mc.field_71466_p.func_175063_a(text, (float)(-width), (float)(-(LogoutSpot.mc.field_71466_p.field_78288_b - 1)), color2.getRGB());
            GL11.glDisable(3042);
            GL11.glEnable(2896);
            GL11.glPopMatrix();
            RenderUtils.glStart((float)(color2.getRed() / 255), (float)(color2.getBlue() / 255), (float)(color2.getRed() / 255), (float)color2.getAlpha());
            RenderUtils.drawOutlinedBox(bb);
            RenderUtils.glEnd();
        });
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
    
    private static /* synthetic */ void lambda$onRenderWorld$0(final Color color, final String name, final AxisAlignedBB bb) {
        Vec3d pos = bb.func_189972_c();
        if (LogoutSpot.mc.field_71439_g.func_70092_e(pos.field_72450_a, pos.field_72448_b, pos.field_72449_c) > 2500.0) {
            final Vec3d delta = pos.func_178788_d(new Vec3d(LogoutSpot.mc.func_175598_ae().field_78730_l, LogoutSpot.mc.func_175598_ae().field_78731_m, LogoutSpot.mc.func_175598_ae().field_78728_n)).func_72432_b();
            pos = new Vec3d(LogoutSpot.mc.func_175598_ae().field_78730_l + delta.field_72450_a * 50.0, LogoutSpot.mc.func_175598_ae().field_78731_m + delta.field_72448_b * 50.0, LogoutSpot.mc.func_175598_ae().field_78728_n + delta.field_72449_c * 50.0);
        }
        double scale = LogoutSpot.mc.field_71439_g.func_70011_f(pos.field_72450_a, pos.field_72448_b, pos.field_72449_c) / 4.0;
        scale = Math.max(1.6, scale);
        final RenderManager renderManager = LogoutSpot.mc.func_175598_ae();
        GL11.glPushMatrix();
        GL11.glTranslated(-renderManager.field_78730_l, -renderManager.field_78731_m, -renderManager.field_78728_n);
        GL11.glTranslatef((float)pos.field_72450_a + 0.5f, (float)pos.field_72448_b + 0.5f, (float)pos.field_72449_c + 0.5f);
        GL11.glNormal3f(0.0f, 1.0f, 0.0f);
        GL11.glRotatef(-renderManager.field_78735_i, 0.0f, 1.0f, 0.0f);
        GL11.glRotatef(renderManager.field_78732_j, 1.0f, 0.0f, 0.0f);
        GL11.glScaled(-scale, -scale, scale);
        GL11.glDisable(2896);
        GL11.glDisable(2929);
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        final String text = name + " (" + LogoutSpot.mc.field_71439_g.func_70011_f(bb.func_189972_c().field_72450_a, bb.func_189972_c().field_72448_b, bb.func_189972_c().field_72449_c) + "m)";
        final int width = LogoutSpot.mc.field_71466_p.func_78256_a(text) / 2;
        LogoutSpot.mc.field_71466_p.func_175063_a(text, (float)(-width), (float)(-(LogoutSpot.mc.field_71466_p.field_78288_b - 1)), color.getRGB());
        GL11.glDisable(3042);
        GL11.glEnable(2896);
        GL11.glPopMatrix();
        RenderUtils.glStart((float)(color.getRed() / 255), (float)(color.getBlue() / 255), (float)(color.getRed() / 255), (float)color.getAlpha());
        RenderUtils.drawOutlinedBox(bb);
        RenderUtils.glEnd();
    }
}
