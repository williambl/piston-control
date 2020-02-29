package com.williambl.pistoncontrol.mixin;

import com.williambl.pistoncontrol.PistonControl;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Entity.class)
public class EntityMixin {
    @Inject(
            at = @At("HEAD"),
            method = "getPistonBehavior()Lnet/minecraft/block/piston/PistonBehavior;",
            cancellable = true
    )
    private void getPistonBehavior(CallbackInfoReturnable<PistonBehavior> info) {
        Entity entity = (Entity) (Object) this;

        if (PistonControl.ENTITY_PISTON_BEHAVIOR_NORMAL.contains(entity.getType())) {
            info.setReturnValue(PistonBehavior.NORMAL);
        }
        if (PistonControl.PISTON_BEHAVIOR_IGNORE.contains(entity.getType())) {
            info.setReturnValue(PistonBehavior.IGNORE);
        }
    }
}
