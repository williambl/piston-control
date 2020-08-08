package com.williambl.pistoncontrol.mixin;

import com.williambl.pistoncontrol.IterableHashSet;
import net.fabricmc.fabric.api.tag.TagRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.resource.ResourcePackManager;
import net.minecraft.resource.ResourcePackProfile;
import net.minecraft.resource.ResourceType;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tag.BlockTags;
import net.minecraft.tag.Tag;
import net.minecraft.util.Identifier;
import net.minecraft.world.level.storage.LevelStorage;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import static com.williambl.pistoncontrol.PistonControl.STICKY_MAP;
import static com.williambl.pistoncontrol.PistonControl.getMODID;

@Mixin(MinecraftServer.class)
public abstract class MinecraftServerReloadResourcesMixin {
    @Inject(at = @At("TAIL"), method = "reloadResources")
    private void postReloadResources(Collection<String> collection, CallbackInfoReturnable<CompletableFuture<Void>> infoReturnable) {
        this.setSelectivelySticky();
    }

    @Inject(at = @At("TAIL"), method = "loadWorldResourcePack")
    private void postLoadWorldResourcePack(CallbackInfo info) {
        this.setSelectivelySticky();
    }

    private void setSelectivelySticky() {
        STICKY_MAP.clear();

        for (Block plank : BlockTags.PLANKS.values())
          STICKY_MAP.put(plank, new IterableHashSet<>(BlockTags.PLANKS));

        for (Block log : BlockTags.LOGS.values())
            STICKY_MAP.put(log, new IterableHashSet<>(BlockTags.LOGS));

//        ArrayList<ResourcePackProfile> profiles = new ArrayList<>(getDataPackManager().getProfiles());
//
//        for (ResourcePackProfile profile : profiles) {
//            Collection<Identifier> foundResources = profile.createResourcePack().findResources(ResourceType.SERVER_DATA, getMODID(), "tags/blocks/selectively_sticky", Integer.MAX_VALUE, (string) -> true);
//            if (!foundResources.isEmpty()) {
//                for (Identifier id : foundResources) {
//                    Tag<Block> blockTag = TagRegistry.block(id);
//                    List<Block> tagValues = blockTag.values();
//                    for (Block block : tagValues)
//                        STICKY_MAP.put(block, new IterableHashSet<>(blockTag));
//                }
//            }
//        }
    }

    @Accessor abstract LevelStorage.Session getSession();
    @Accessor abstract ResourcePackManager<ResourcePackProfile> getDataPackManager();
}
