package com.williambl.pistoncontrol;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.tag.TagRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.EntityType;
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
	public static Tag<Block> PISTON_BEHAVIOR_PUSH_ONLY = TagRegistry.block(new Identifier(MODID, "piston_behavior_push_only"));
	public static Tag<Block> STICKY_BLOCKS = TagRegistry.block(new Identifier(MODID, "sticky_blocks"));

	public static Tag<EntityType<?>> ENTITY_PISTON_BEHAVIOR_NORMAL = TagRegistry.entityType(new Identifier(MODID, "piston_behavior_normal"));
	public static Tag<EntityType<?>> PISTON_BEHAVIOR_IGNORE = TagRegistry.entityType(new Identifier(MODID, "piston_behavior_ignore"));

	@Override
	public void onInitialize() {
	}
}

