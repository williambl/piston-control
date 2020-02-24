package com.williambl.pistoncontrol;

import net.fabricmc.api.ModInitializer;
import net.minecraft.block.Block;
import net.minecraft.tag.Tag;

public class PistonControl implements ModInitializer {

	public static Tag<Block> PISTON_BEHAVIOR_NORMAL;
	public static Tag<Block> PISTON_BEHAVIOR_DESTROY;
	public static Tag<Block> PISTON_BEHAVIOR_BLOCK;
	public static Tag<Block> PISTON_BEHAVIOR_IGNORE;
	public static Tag<Block> PISTON_BEHAVIOR_PUSH_ONLY;

	@Override
	public void onInitialize() {
	}
}
