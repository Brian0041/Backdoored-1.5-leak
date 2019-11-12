package com.backdoored.hacks.combat;

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import com.backdoored.utils.Utils;
import net.minecraft.init.SoundEvents;
import net.minecraftforge.event.entity.PlaySoundAtEntityEvent;
import com.backdoored.gui.CategoriesInit;
import net.minecraft.util.math.Vec3d;
import com.backdoored.hacks.BaseHack;

public class InvisDetect extends BaseHack
{
    private Vec3d soundPositon;
    
    public InvisDetect() {
        super("InvisDetect", CategoriesInit.COMBAT, "Can help locate people in entity god mode");
    }
    
    @SubscribeEvent
    public void onPlaySound(final PlaySoundAtEntityEvent event) {
        if (event.getEntity() == null) {
            return;
        }
        if (event.getSound().equals(SoundEvents.field_187709_dP) || event.getSound().equals(SoundEvents.field_187729_cv) || event.getSound().equals(SoundEvents.field_187732_cw) || event.getSound().equals(SoundEvents.field_191256_dG)) {
            this.soundPositon = event.getEntity().func_174791_d();
            Utils.printMessage("Invis Player at: " + Utils.vectorToString(this.soundPositon, new boolean[0]));
        }
    }
}
