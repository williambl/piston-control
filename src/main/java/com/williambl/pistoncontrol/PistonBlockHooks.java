package com.williambl.pistoncontrol;

import net.minecraft.block.entity.PistonBlockEntity;
import net.minecraft.nbt.CompoundTag;

import java.util.HashMap;
import java.util.Map;

public interface PistonBlockHooks {

    Map<PistonBlockEntity, CompoundTag> blockEntityTags = new HashMap<>();

    default Map<PistonBlockEntity, CompoundTag> getBlockEntityTags() {
        return blockEntityTags;
    }
}
