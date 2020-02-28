package com.williambl.pistoncontrol;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.tag.TagRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.tag.Tag;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

public class PistonControl implements ModInitializer {

	private static String MODID = "pistoncontrol";

	public static Tag<Block> PISTON_BEHAVIOR_NORMAL = TagRegistry.block(new Identifier(MODID, "piston_behavior_normal"));
	public static Tag<Block> PISTON_BEHAVIOR_DESTROY = TagRegistry.block(new Identifier(MODID, "piston_behavior_destroy"));
	public static Tag<Block> PISTON_BEHAVIOR_BLOCK = TagRegistry.block(new Identifier(MODID, "piston_behavior_block"));
	public static Tag<Block> PISTON_BEHAVIOR_IGNORE = TagRegistry.block(new Identifier(MODID, "piston_behavior_ignore"));
	public static Tag<Block> PISTON_BEHAVIOR_PUSH_ONLY = TagRegistry.block(new Identifier(MODID, "piston_behavior_push_only"));
	public static Tag<Block> STICKY_BLOCKS = TagRegistry.block(new Identifier(MODID, "sticky_blocks"));

	@Override
	public void onInitialize() {
	}

	public void moveBE(World world, BlockPos pos, Direction direction) {
		BlockPos newPos = pos.offset(direction.getOpposite());
		BlockEntity be = world.getBlockEntity(pos);
		BlockState bs = world.getBlockState(pos);
		if (be == null)
			return;
		if (world.isClient) {
			world.updateListeners(newPos, bs, bs, 2);
			return;
		}
		be.setPos(newPos);
		CompoundTag tag = new CompoundTag();
		be.toTag(tag);
		world.removeBlockEntity(pos);
		world.removeBlock(pos, true);
		world.setBlockState(newPos, bs);
		world.getBlockEntity(newPos).fromTag(tag);
		((ServerWorld) world).getChunkManager().markForUpdate(newPos);
	}
}
