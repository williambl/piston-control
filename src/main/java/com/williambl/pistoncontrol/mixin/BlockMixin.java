package com.williambl.pistoncontrol.mixin;

import com.williambl.pistoncontrol.PistonControl;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.piston.PistonBehavior;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Block.class)
public class BlockMixin {
    @Inject(
            at = @At("HEAD"),
            method = "net/minecraft/block/Block.getPistonBehavior(Lnet/minecraft/block/BlockState;)Lnet/minecraft/block/piston/PistonBehavior;",
            cancellable = true
    )
    private void getPistonBehavior(BlockState state, CallbackInfoReturnable<PistonBehavior> info) {
        if (PistonControl.PISTON_BEHAVIOR_NORMAL.contains(state.getBlock()))
            info.setReturnValue(PistonBehavior.NORMAL);
        if (PistonControl.PISTON_BEHAVIOR_DESTROY.contains(state.getBlock()))
            info.setReturnValue(PistonBehavior.DESTROY);
        if (PistonControl.PISTON_BEHAVIOR_BLOCK.contains(state.getBlock()))
            info.setReturnValue(PistonBehavior.BLOCK);
        if (PistonControl.PISTON_BEHAVIOR_IGNORE.contains(state.getBlock()))
            info.setReturnValue(PistonBehavior.IGNORE);
        if (PistonControl.PISTON_BEHAVIOR_PUSH_ONLY.contains(state.getBlock()))
            info.setReturnValue(PistonBehavior.PUSH_ONLY);
    }
}
