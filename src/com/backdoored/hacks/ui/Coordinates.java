package com.backdoored.hacks.ui;

import net.minecraft.util.math.Vec3d;
import java.awt.Color;
import com.backdoored.gui.CategoriesInit;
import com.backdoored.setting.Setting;
import com.backdoored.hacks.BaseHack;

public class Coordinates extends BaseHack
{
    private final Setting x;
    private final Setting y;
    
    public Coordinates() {
        super("Coordinates", "Show your coordinates", CategoriesInit.UI);
        this.x = new Setting("x", this, 50, 0, (int)Math.round(Coordinates.mc.field_71443_c * 1.2));
        this.y = new Setting("y", this, 50, 0, (int)Math.round(Coordinates.mc.field_71440_d * 1.2));
    }
    
    public void onRender() {
        if (this.getEnabled()) {
            Coordinates.mc.field_71466_p.func_78276_b(this.vecFormat(Coordinates.mc.field_71439_g.func_174791_d()), this.x.getValInt(), this.y.getValInt(), Color.WHITE.getRGB());
        }
    }
    
    private String vecFormat(final Vec3d vector) {
        return (int)Math.floor(vector.field_72450_a) + ", " + (int)Math.floor(vector.field_72448_b) + ", " + (int)Math.floor(vector.field_72449_c) + " (" + (int)Math.floor(vector.field_72450_a) / 8 + ", " + (int)Math.floor(vector.field_72449_c) / 8 + ")";
    }
}
