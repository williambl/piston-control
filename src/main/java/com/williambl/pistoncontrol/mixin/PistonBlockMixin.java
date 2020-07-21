package com.williambl.pistoncontrol.mixin;

import com.williambl.pistoncontrol.PistonBlockHooks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.PistonBlock;
import net.minecraft.block.PistonExtensionBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.PistonBlockEntity;
import net.minecraft.block.piston.PistonHandler;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.List;
import java.util.Map;

@Mixin(PistonBlock.class)
public class PistonBlockMixin extends Block implements PistonBlockHooks {

    private CompoundTag tmpBlockEntityTag = null;

    public PistonBlockMixin(Settings settings) {
        super(settings);
    }

    @Redirect(
            at = @At(value = "FIELD", target = "Lnet/minecraft/block/Blocks;OBSIDIAN:Lnet/minecraft/block/Block;", opcode = Opcodes.GETSTATIC),
            method = "isMovable"
    )
    private static Block getObsidianBlock() {
        return null;
    }

    @Redirect(
            at = @At(value = "INVOKE", target = "net/minecraft/block/BlockState.getHardness(Lnet/minecraft/world/BlockView;Lnet/minecraft/util/math/BlockPos;)F"),
            method = "isMovable"
    )
    private static float getHardnessValue(BlockState blockState, BlockView world, BlockPos pos) {
        return 0;
    }

    @Redirect(
            at = @At(value = "INVOKE", target = "net/minecraft/block/Block.hasBlockEntity()Z"),
            method = "isMovable"
    )
    private static boolean hasBlockEntity(Block block) {
        return false;
    }

    @Inject(
            at = @At(
                    value = "INVOKE_ASSIGN",
                    target = "net/minecraft/world/World.getBlockState(Lnet/minecraft/util/math/BlockPos;)Lnet/minecraft/block/BlockState;",
                    ordinal = 3
            ),
            method = "move",
            locals = LocalCapture.CAPTURE_FAILHARD
    )
    private void saveBEs(World world, BlockPos pos, Direction dir, boolean retract, CallbackInfoReturnable<Boolean> cir, BlockPos blockPos, PistonHandler pistonHandler, Map map, List list, List list2, List list3, BlockState[] blockStates, Direction direction, int j, int l, BlockPos blockPos4) {
        BlockEntity be = world.getBlockEntity(blockPos4);
        world.removeBlockEntity(blockPos4);
        if (be != null) {
            CompoundTag tag = new CompoundTag();
            be.toTag(tag);
            tmpBlockEntityTag = tag;
        }
    }

    @Redirect(
            at = @At(
                    value = "INVOKE",
                    target = "net/minecraft/block/PistonExtensionBlock.createBlockEntityPiston(Lnet/minecraft/block/BlockState;Lnet/minecraft/util/math/Direction;ZZ)Lnet/minecraft/block/entity/BlockEntity;",
                    ordinal = 0
            ),
            method = "move"
    )
    private BlockEntity createBlockEntityPiston(BlockState pushedBlock, Direction dir, boolean extending, boolean bl) {
        PistonBlockEntity be = (PistonBlockEntity) PistonExtensionBlock.createBlockEntityPiston(pushedBlock, dir, extending, bl);
        blockEntityTags.put(be, tmpBlockEntityTag);
        return be;
    }

}
