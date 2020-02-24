package com.williambl.pistoncontrol.mixin;

import jdk.internal.org.objectweb.asm.Opcodes;
import net.minecraft.block.Block;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(Block.class)
public class PistonBlockMixin {
    @Redirect(
            at = @At(value = "FIELD", target = "net.minecraft.block.Blocks.OBSIDIAN", opcode = Opcodes.GETSTATIC),
            method = "isMovable"
    )
    private static Block getObsidianBlock() {
        return null;
    }
}
