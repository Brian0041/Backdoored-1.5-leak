package com.backdoored.hacks.render;

import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraft.util.math.AxisAlignedBB;
import java.util.Iterator;
import org.lwjgl.opengl.GL11;
import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.function.Function;
import java.util.List;
import com.backdoored.utils.GluProjection;
import java.util.LinkedList;
import net.minecraft.entity.player.EntityPlayer;
import com.backdoored.gui.CategoriesInit;
import com.backdoored.setting.Setting;
import com.backdoored.hacks.BaseHack;

public class CsgoEsp extends BaseHack
{
    private Setting r;
    private Setting g;
    private Setting b;
    private Setting a;
    private Setting lineWidth;
    
    public CsgoEsp() {
        super("Csgo esp", CategoriesInit.RENDER, "swoopae on top");
        this.r = new Setting("r", this, 0.0, 0.0, 1.0);
        this.g = new Setting("g", this, 0.0, 0.0, 1.0);
        this.b = new Setting("b", this, 0.0, 0.0, 1.0);
        this.a = new Setting("a", this, 1.0, 0.0, 1.0);
        this.lineWidth = new Setting("Line Width", this, 1.0, 1.0, 4.0);
    }
    
    private void doPlayers(final float partialTicks) {
        for (final EntityPlayer player : CsgoEsp.mc.field_71441_e.field_73010_i) {
            if (player.equals((Object)CsgoEsp.mc.field_71439_g)) {
                continue;
            }
            final List<GluProjection.Projection> projectionList = new LinkedList<GluProjection.Projection>();
            final AxisAlignedBB playerBB = player.func_174813_aQ().func_72321_a(0.1, 0.1, 0.1);
            playerBB.func_72317_d(-(player.field_70165_t - player.field_70142_S) * (1.0f - partialTicks), -(player.field_70163_u - player.field_70137_T) * (1.0f - partialTicks), -(player.field_70161_v - player.field_70136_U) * (1.0f - partialTicks));
            final double renderPosX = CsgoEsp.mc.func_175598_ae().field_78730_l;
            final double renderPosY = CsgoEsp.mc.func_175598_ae().field_78731_m;
            final double renderPosZ = CsgoEsp.mc.func_175598_ae().field_78728_n;
            playerBB.func_72317_d(-renderPosX, -renderPosY, -renderPosZ);
            for (double x = playerBB.field_72340_a; x <= playerBB.field_72336_d; x += (playerBB.field_72336_d - playerBB.field_72340_a) / 2.0) {
                for (double y = playerBB.field_72338_b; y <= playerBB.field_72337_e; y += (playerBB.field_72337_e - playerBB.field_72338_b) / 2.0) {
                    for (double z = playerBB.field_72339_c; z <= playerBB.field_72334_f; z += (playerBB.field_72334_f - playerBB.field_72339_c) / 2.0) {
                        final GluProjection.Projection projection = GluProjection.getInstance().project(x, y, z, GluProjection.ClampMode.NONE, false);
                        if (!projection.isType(GluProjection.Projection.Type.INVERTED)) {
                            projectionList.add(projection);
                        }
                    }
                }
            }
            if (projectionList.isEmpty()) {
                continue;
            }
            final List<Double> xCoordinates = projectionList.stream().map((Function<? super Object, ?>)GluProjection.Projection::getX).collect((Collector<? super Object, ?, List<Double>>)Collectors.toList());
            final double minX = Collections.min((Collection<? extends Double>)xCoordinates);
            final double maxX = Collections.max((Collection<? extends Double>)xCoordinates);
            final List<Double> yCoordinates = projectionList.stream().map((Function<? super Object, ?>)GluProjection.Projection::getY).collect((Collector<? super Object, ?, List<Double>>)Collectors.toList());
            final double minY = Collections.min((Collection<? extends Double>)yCoordinates);
            final double maxY = Collections.max((Collection<? extends Double>)yCoordinates);
            GL11.glBegin(2);
            GL11.glVertex2d(maxX, maxY);
            GL11.glVertex2d(maxX, minY);
            GL11.glVertex2d(minX, minY);
            GL11.glVertex2d(minX, maxY);
            GL11.glEnd();
        }
    }
    
    @SubscribeEvent(priority = EventPriority.NORMAL)
    @Override
    public void RenderHud(final RenderGameOverlayEvent.Post event) {
        if (!this.getEnabled()) {
            return;
        }
        final boolean blend = GL11.glGetBoolean(3042);
        GL11.glPushMatrix();
        GL11.glEnable(3042);
        GL11.glDisable(3553);
        GL11.glBlendFunc(770, 771);
        GL11.glEnable(2848);
        GL11.glLineWidth((float)this.lineWidth.getValDouble());
        GL11.glColor4f((float)this.r.getValDouble(), (float)this.g.getValDouble(), (float)this.b.getValDouble(), (float)this.a.getValDouble());
        this.doPlayers(event.getPartialTicks());
        GL11.glDisable(2848);
        if (blend) {
            GlStateManager.func_179147_l();
            GL11.glEnable(3042);
        }
        else {
            GlStateManager.func_179084_k();
            GL11.glDisable(3042);
        }
        GL11.glEnable(3553);
        GL11.glPopMatrix();
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
    }
}
