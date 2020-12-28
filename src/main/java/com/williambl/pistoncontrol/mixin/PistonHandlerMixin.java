package com.williambl.pistoncontrol.mixin;

import com.williambl.pistoncontrol.PistonControl;
import net.minecraft.block.Block;
import net.minecraft.block.piston.PistonHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static com.williambl.pistoncontrol.PistonControl.STICKY_BLOCKS;
import static com.williambl.pistoncontrol.PistonControl.STICKY_MAP;

@Mixin(PistonHandler.class)
public abstract class PistonHandlerMixin {
    @Shadow private static boolean isBlockSticky(Block block) {
        throw new AbstractMethodError();
    }

    @Inject(
            at = @At("HEAD"),
            method = "isBlockSticky(Lnet/minecraft/block/Block;)Z",
            cancellable = true
    )
    private static void overrideBlockStickiness(Block block, CallbackInfoReturnable<Boolean> info) {
        info.setReturnValue(STICKY_BLOCKS.contains(block) || STICKY_MAP.containsKey(block));
    }

    @Inject(
            at = @At(value = "HEAD"),
            method = "isAdjacentBlockStuck",
            cancellable = true
    )
    private static void overrideAdjacentStickiness(Block block, Block block2, CallbackInfoReturnable<Boolean> cir) {
        if (STICKY_MAP.containsKey(block) && STICKY_MAP.containsKey(block2) && !STICKY_MAP.get(block).intersects(STICKY_MAP.get(block2))) {
            cir.setReturnValue(false);
        } else {
            cir.setReturnValue(isBlockSticky(block) || isBlockSticky(block2));
        }
    }
}
