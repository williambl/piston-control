package com.williambl.pistoncontrol.mixin;

import com.williambl.pistoncontrol.PistonControl;
import net.minecraft.block.Block;
import net.minecraft.block.piston.PistonHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PistonHandler.class)
public class PistonHandlerMixin {
    @Inject(
            at = @At("HEAD"),
            method = "isBlockSticky(Lnet/minecraft/block/Block;)Z",
            cancellable = true
    )
    private static void isBlockSticky(Block block, CallbackInfoReturnable<Boolean> info) {
        info.setReturnValue(PistonControl.STICKY_BLOCKS.contains(block));
    }
}
