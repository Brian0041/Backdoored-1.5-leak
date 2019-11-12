package com.backdoored.mixin;

import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.common.MinecraftForge;
import com.backdoored.event.GetLadderBoundingBox;
import net.minecraft.util.math.AxisAlignedBB;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.block.state.IBlockState;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Shadow;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.BlockLadder;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ BlockLadder.class })
public class MixinBlockLadder
{
    @Shadow
    @Final
    public static PropertyDirection field_176382_a;
    
    public MixinBlockLadder() {
        super();
    }
    
    @Inject(method = { "getBoundingBox" }, at = { @At("HEAD") }, cancellable = true)
    public void getBoundingBox(final IBlockState state, final IBlockAccess source, final BlockPos pos, final CallbackInfoReturnable<AxisAlignedBB> cir) {
        final GetLadderBoundingBox event = new GetLadderBoundingBox(state, source, pos, MixinBlockLadder.field_176382_a, cir);
        MinecraftForge.EVENT_BUS.post((Event)event);
    }
}
