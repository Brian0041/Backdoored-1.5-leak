package com.backdoored.hacks.ui.hud;

import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;
import java.util.Comparator;
import com.backdoored.Globals;
import java.awt.Color;
import com.backdoored.utils.ColourUtils;
import com.backdoored.extensions.Wrapper;
import com.backdoored.Backdoored;
import org.lwjgl.opengl.GL11;
import java.util.Objects;
import com.backdoored.hacks.BaseHack;
import com.backdoored.setting.SettingsManager;
import com.backdoored.setting.Setting;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraft.util.ResourceLocation;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;

public class HudNotif extends Gui
{
    private static Minecraft mc;
    private ResourceLocation dev_hud_logo;
    private ResourceLocation hud_logo;
    private static final String text = "Backdoored 1.5";
    private static int Ypos;
    
    public HudNotif() {
        super();
        this.dev_hud_logo = new ResourceLocation("backdoored", "textures/dev-donor-client.png");
        this.hud_logo = new ResourceLocation("backdoored", "textures/backdoored-standard-client.png");
    }
    
    public void render(final RenderGameOverlayEvent event, final Setting[] settings, final Hud caller) {
        if (Hud.INSTANCE.button.leftClickToggled) {
            boolean isRainbowEnabled = false;
            try {
                final Setting rainbowSetting = SettingsManager.getSetting(caller, "Rainbow Colour");
                if (rainbowSetting == null) {
                    throw new NullPointerException(rainbowSetting.getVal().toString());
                }
                isRainbowEnabled = Objects.requireNonNull(rainbowSetting.getVal());
            }
            catch (Exception ex) {}
            if (settings[0].getValBoolean()) {
                GL11.glPushAttrib(1048575);
                GL11.glPushMatrix();
                GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
                if (Backdoored.getDevMode()) {
                    HudNotif.mc.field_71446_o.func_110577_a(this.dev_hud_logo);
                }
                else {
                    HudNotif.mc.field_71446_o.func_110577_a(this.hud_logo);
                }
                GL11.glTranslatef(1.0f, 1.0f, 0.0f);
                func_152125_a(0, 0, 0.0f, 0.0f, 208, 208, 30, 30, 208.0f, 208.0f);
                GL11.glPopMatrix();
                GL11.glPopAttrib();
            }
            if (settings[1].getValBoolean()) {
                int textX = 2;
                if (settings[0].getValBoolean()) {
                    textX = 32;
                }
                this.func_73731_b(Wrapper.fontRenderer, "Backdoored 1.5", textX, 2, isRainbowEnabled ? ColourUtils.rainbow().getRGB() : Color.RED.getRGB());
            }
            if (settings[2].getValBoolean()) {
                final List<BaseHack> hack = Globals.hacks;
                if (settings[6].getValString().equals("Length") && Globals.isAlpha) {
                    hack.sort(new Globals.StrLengthComparator());
                }
                else if (settings[6].getValString().equals("Alphabetical") && !Globals.isAlpha) {
                    hack.sort(new Globals.AlphabeticComparator());
                }
                for (final BaseHack x : hack) {
                    for (final Setting z : Objects.requireNonNull(SettingsManager.getSettingsByMod(x))) {
                        if (z.getName().equals("Is Visible") && Boolean.valueOf(z.getValString()) && x.button.leftClickToggled) {
                            int stringX = 0;
                            int stringY = 0;
                            if (settings[3].getValString().equalsIgnoreCase("top right")) {
                                stringX = event.getResolution().func_78326_a() - Wrapper.fontRenderer.func_78256_a(x.name);
                            }
                            if (settings[3].getValString().equalsIgnoreCase("top left")) {
                                stringX = 2;
                            }
                            if (settings[3].getValString().equalsIgnoreCase("bot right")) {
                                stringX = event.getResolution().func_78326_a() - Wrapper.fontRenderer.func_78256_a(x.name);
                                stringY = event.getResolution().func_78328_b();
                            }
                            if (settings[3].getValString().equalsIgnoreCase("bot left")) {
                                stringX = 2;
                                stringY = event.getResolution().func_78328_b();
                            }
                            if (settings[3].getValString().equalsIgnoreCase("custom")) {
                                stringX = settings[4].getValInt();
                                stringY = settings[5].getValInt();
                            }
                            if (settings[3].getValString().equalsIgnoreCase("top left") || settings[3].getValString().equalsIgnoreCase("top right")) {
                                this.func_73731_b(Wrapper.fontRenderer, x.name, stringX, stringY + HudNotif.Ypos, isRainbowEnabled ? ColourUtils.rainbow().getRGB() : Color.RED.getRGB());
                            }
                            if (settings[3].getValString().equalsIgnoreCase("bot left") || settings[3].getValString().equalsIgnoreCase("bot right")) {
                                this.func_73731_b(Wrapper.fontRenderer, x.name, stringX, stringY - HudNotif.Ypos - Wrapper.fontRenderer.field_78288_b, isRainbowEnabled ? ColourUtils.rainbow().getRGB() : Color.RED.getRGB());
                            }
                            if (settings[3].getValString().equalsIgnoreCase("custom")) {
                                this.func_73731_b(Wrapper.fontRenderer, x.name, stringX, stringY + HudNotif.Ypos, isRainbowEnabled ? ColourUtils.rainbow().getRGB() : Color.RED.getRGB());
                            }
                            HudNotif.Ypos += 10;
                        }
                    }
                }
            }
            HudNotif.Ypos = 2;
        }
    }
    
    static {
        HudNotif.mc = Globals.mc;
        HudNotif.Ypos = 2;
    }
}
