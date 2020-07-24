package com.williambl.pistoncontrol.mixin;

import com.williambl.pistoncontrol.PistonBlockHooks;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.PistonBlockEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PistonBlockEntity.class)
public class PistonBlockEntityMixin {

    @Inject(
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;updateNeighbor(Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/Block;Lnet/minecraft/util/math/BlockPos;)V"),
            method = "finish"
    )
    private void onFinish(CallbackInfo info) {
        moveBE((PistonBlockEntity) (Object) this);
    }

    @Inject(
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;updateNeighbor(Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/Block;Lnet/minecraft/util/math/BlockPos;)V"),
            method = "tick"
    )
    private void onTick(CallbackInfo info) {
        moveBE((PistonBlockEntity) (Object) this);
    }

    private void moveBE(PistonBlockEntity thisAsPBE) {
        BlockPos pos = thisAsPBE.getPos();
        World world = thisAsPBE.getWorld();
        if (world == null)
            return;
        BlockState bs = world.getBlockState(pos);
        if (world.isClient) {
            world.updateListeners(pos, bs, bs, 2);
            return;
        }
        CompoundTag tag = ((PistonBlockHooks) Blocks.PISTON).getBlockEntityTags().get(thisAsPBE);
        if (tag == null)
            return;
        tag.putInt("x", pos.getX());
        tag.putInt("y", pos.getY());
        tag.putInt("z", pos.getZ());

        BlockEntity bEntity = world.getBlockEntity(pos);
        if (bEntity == null)
            return;
        bEntity.fromTag(bs, tag);
        ((ServerWorld) world).getChunkManager().markForUpdate(pos);
    }
}
