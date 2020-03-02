package com.williambl.pistoncontrol.mixin;

import com.williambl.pistoncontrol.BlockEntityHooks;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(BlockEntity.class)
public class BlockEntityMixin implements BlockEntityHooks {

    @Shadow private BlockState cachedState;

    @Override
    public void setCachedState(BlockState state) {
        cachedState = state;
    }
}
