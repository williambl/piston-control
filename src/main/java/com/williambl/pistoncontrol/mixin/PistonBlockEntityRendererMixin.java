package com.williambl.pistoncontrol.mixin;

import com.williambl.pistoncontrol.BlockEntityHooks;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.PistonBlockEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderDispatcher;
import net.minecraft.client.render.block.entity.PistonBlockEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PistonBlockEntityRenderer.class)
public class PistonBlockEntityRendererMixin {
    @Inject(
            at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/block/entity/PistonBlockEntityRenderer;method_3575(Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/BlockState;Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;Lnet/minecraft/world/World;ZI)V"),
            method = "render"
    )
    private void renderBlockEntity(PistonBlockEntity pistonBlockEntity, float f, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, int j, CallbackInfo ci) {
        if (pistonBlockEntity.getPushedBlock().getBlock().hasBlockEntity()) {
            BlockEntity be = ((BlockEntityProvider) pistonBlockEntity.getPushedBlock().getBlock()).createBlockEntity(pistonBlockEntity.getWorld());
            if (be != null && BlockEntityRenderDispatcher.INSTANCE.get(be) != null) {
                be.setLocation(pistonBlockEntity.getWorld(), pistonBlockEntity.getPos());
                ((BlockEntityHooks)be).setCachedState(pistonBlockEntity.getPushedBlock());
                BlockEntityRenderDispatcher.INSTANCE.render(be, f, matrixStack, vertexConsumerProvider);
                be.markRemoved();
            }
        }
    }
}
