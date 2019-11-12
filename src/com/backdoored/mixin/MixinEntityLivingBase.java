package com.backdoored.mixin;

import org.spongepowered.asm.mixin.Shadow;
import net.minecraft.entity.EntityLivingBase;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ EntityLivingBase.class })
public class MixinEntityLivingBase
{
    public MixinEntityLivingBase() {
        super();
    }
    
    @Shadow
    public void func_70664_aZ() {
    }
}
