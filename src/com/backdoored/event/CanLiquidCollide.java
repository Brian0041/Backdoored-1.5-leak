package com.backdoored.event;

import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

public class CanLiquidCollide extends BackdooredEvent
{
    public CallbackInfoReturnable<Boolean> cir;
    
    public CanLiquidCollide(final CallbackInfoReturnable<Boolean> cir) {
        super();
        this.cir = cir;
    }
}
