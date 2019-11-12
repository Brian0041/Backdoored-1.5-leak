package com.backdoored.hacks.player;

import com.backdoored.gui.CategoriesInit;
import com.backdoored.setting.Setting;
import com.backdoored.hacks.BaseHack;

public class RotationLock extends BaseHack
{
    private final Setting facing;
    
    public RotationLock() {
        super("Rotation Lock", "Lock your rotation", CategoriesInit.PLAYER);
        this.facing = new Setting("Facing", this, "North", new String[] { "North", "East", "South", "West" });
    }
    
    public void onEnabled() {
        RotationLock.mc.field_71439_g.func_70034_d((float)this.getYaw());
    }
    
    public void onUpdate() {
        if (this.getEnabled()) {
            RotationLock.mc.field_71439_g.field_70177_z = (float)this.getYaw();
            RotationLock.mc.field_71439_g.field_70125_A = (float)this.getYaw();
        }
    }
    
    private int getYaw() {
        final String valString = this.facing.getValString();
        int newYaw = 0;
        switch (valString) {
            default: {
                newYaw = 0;
                break;
            }
            case "East": {
                newYaw = 90;
                break;
            }
            case "South": {
                newYaw = 180;
                break;
            }
            case "West": {
                newYaw = -90;
                break;
            }
        }
        return newYaw;
    }
}
