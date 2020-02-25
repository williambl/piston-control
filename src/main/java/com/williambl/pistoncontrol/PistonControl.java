package com.williambl.pistoncontrol;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.tag.TagRegistry;
import net.minecraft.block.Block;
import net.minecraft.tag.Tag;
import net.minecraft.util.Identifier;

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
}
