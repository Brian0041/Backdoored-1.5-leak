package com.backdoored.hacks.render;

import java.util.Iterator;
import com.backdoored.api.MojangWebApi;
import net.minecraft.entity.passive.AbstractHorse;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.Entity;
import com.backdoored.gui.CategoriesInit;
import com.backdoored.hacks.BaseHack;

public class MobOwner extends BaseHack
{
    public MobOwner() {
        super("Mob Owner", CategoriesInit.RENDER, "Show you owners of mobs");
    }
    
    public void onUpdate() {
        if (!this.getEnabled()) {
            return;
        }
        for (final Entity entity : MobOwner.mc.field_71441_e.field_72996_f) {
            if (entity instanceof EntityTameable) {
                final EntityTameable tameable = (EntityTameable)entity;
                if (tameable.func_70909_n() && tameable.func_70902_q() != null) {
                    tameable.func_174805_g(true);
                    tameable.func_96094_a("Owner: " + tameable.func_70902_q().func_145748_c_().func_150254_d());
                }
            }
            if (entity instanceof AbstractHorse) {
                final AbstractHorse horse = (AbstractHorse)entity;
                if (!horse.func_110248_bS() || horse.func_184780_dh() == null) {
                    continue;
                }
                horse.func_174805_g(true);
                horse.func_96094_a("Owner: " + MojangWebApi.grabRealName(horse.func_184780_dh().toString()));
            }
        }
    }
    
    public void onDisabled() {
        for (final Entity entity : MobOwner.mc.field_71441_e.field_72996_f) {
            if (!(entity instanceof EntityTameable)) {
                if (!(entity instanceof AbstractHorse)) {
                    continue;
                }
            }
            try {
                entity.func_174805_g(false);
            }
            catch (Exception ex) {}
        }
    }
}
