package com.williambl.pistoncontrol.mixin;

import com.williambl.pistoncontrol.IterableHashSet;
import net.minecraft.block.Block;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tag.BlockTags;
import net.minecraft.tag.Tag;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import static com.williambl.pistoncontrol.PistonControl.STICKY_MAP;

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

        Map<Identifier, Tag<Block>> tags = BlockTags.getContainer().getEntries();
        tags.keySet().stream().filter(it -> it.getPath().startsWith("selectively_sticky"))
                .forEach(id -> {
                    Tag<Block> tag = tags.get(id);
                    IterableHashSet<Tag<Block>> tagSet = new IterableHashSet<>(tag);
                    tag.values().forEach(block -> STICKY_MAP.put(block, tagSet));
                });
    }
}
