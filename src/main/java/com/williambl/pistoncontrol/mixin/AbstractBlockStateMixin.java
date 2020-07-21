package com.williambl.pistoncontrol.mixin;

import com.williambl.pistoncontrol.PistonControl;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.piston.PistonBehavior;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AbstractBlock.AbstractBlockState.class)
public class AbstractBlockStateMixin {
    @Inject(
            at = @At("HEAD"),
            method = "getPistonBehavior()Lnet/minecraft/block/piston/PistonBehavior;",
            cancellable = true
    )
    private void getPistonBehavior(CallbackInfoReturnable<PistonBehavior> info) {
        BlockState state = (BlockState) (Object) this;

        if (PistonControl.PISTON_BEHAVIOR_NORMAL.contains(state.getBlock())) {
            info.setReturnValue(PistonBehavior.NORMAL);
        }
        if (PistonControl.PISTON_BEHAVIOR_DESTROY.contains(state.getBlock())) {
            info.setReturnValue(PistonBehavior.DESTROY);
        }
        if (PistonControl.PISTON_BEHAVIOR_BLOCK.contains(state.getBlock())) {
            info.setReturnValue(PistonBehavior.BLOCK);
        }
        if (PistonControl.PISTON_BEHAVIOR_PUSH_ONLY.contains(state.getBlock())) {
            info.setReturnValue(PistonBehavior.PUSH_ONLY);
        }
    }
}
