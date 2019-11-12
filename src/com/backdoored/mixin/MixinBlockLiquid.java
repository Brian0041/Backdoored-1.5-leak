package com.backdoored.mixin;

import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.common.MinecraftForge;
import com.backdoored.event.CanLiquidCollide;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import net.minecraft.block.state.IBlockState;
import net.minecraft.block.BlockLiquid;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ BlockLiquid.class })
public class MixinBlockLiquid
{
    public MixinBlockLiquid() {
        super();
    }
    
    @Inject(method = { "canCollideCheck" }, at = { @At("HEAD") }, cancellable = true)
    public void canCollideWithLiquid(final IBlockState state, final boolean hitIfLiquid, final CallbackInfoReturnable<Boolean> cir) {
        final CanLiquidCollide event = new CanLiquidCollide(cir);
        MinecraftForge.EVENT_BUS.post((Event)event);
    }
}
