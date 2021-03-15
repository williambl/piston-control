package com.williambl.pistoncontrol;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.tag.TagRegistry;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityType;
import net.minecraft.tag.BlockTags;
import net.minecraft.tag.Tag;
import net.minecraft.util.Identifier;

import java.util.Map;

public class PistonControl implements ModInitializer {

	private static final String MODID = "pistoncontrol";

	public static Tag<Block> PISTON_BEHAVIOR_NORMAL = TagRegistry.block(new Identifier(MODID, "piston_behavior_normal"));
	public static Tag<Block> PISTON_BEHAVIOR_DESTROY = TagRegistry.block(new Identifier(MODID, "piston_behavior_destroy"));
	public static Tag<Block> PISTON_BEHAVIOR_BLOCK = TagRegistry.block(new Identifier(MODID, "piston_behavior_block"));
	public static Tag<Block> PISTON_BEHAVIOR_PUSH_ONLY = TagRegistry.block(new Identifier(MODID, "piston_behavior_push_only"));
	public static Tag<Block> STICKY_BLOCKS = TagRegistry.block(new Identifier(MODID, "sticky_blocks"));

	public static Tag<EntityType<?>> ENTITY_PISTON_BEHAVIOR_NORMAL = TagRegistry.entityType(new Identifier(MODID, "piston_behavior_normal"));
	public static Tag<EntityType<?>> PISTON_BEHAVIOR_IGNORE = TagRegistry.entityType(new Identifier(MODID, "piston_behavior_ignore"));

	public static final Multimap<Block, Tag<Block>> STICKY_MAP = HashMultimap.create();

	@Override
	public void onInitialize() {
		ServerLifecycleEvents.END_DATA_PACK_RELOAD.register((server, manager, success) -> {
			populateStickyMap();
		});
		ServerLifecycleEvents.SERVER_STARTED.register((server) -> {
			populateStickyMap();
		});
	}

	private void populateStickyMap() {
		STICKY_MAP.clear();

		Map<Identifier, Tag<Block>> tags = BlockTags.getTagGroup().getTags();
		tags.keySet().stream().filter(it -> it.getPath().startsWith("selectively_sticky"))
				.forEach(id -> {
					Tag<Block> tag = tags.get(id);
					tag.values().forEach(block -> STICKY_MAP.put(block, tag));
				});
	}
}

