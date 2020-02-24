package com.williambl.pistoncontrol.mixin;

import net.minecraft.block.Block;
import net.minecraft.block.PistonBlock;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(PistonBlock.class)
public class PistonBlockMixin {
    @Redirect(
            at = @At(value = "FIELD", target = "Lnet/minecraft/block/Blocks;OBSIDIAN:Lnet/minecraft/block/Block;", opcode = Opcodes.GETSTATIC),
            method = "isMovable"
    )
    private static Block getObsidianBlock() {
        return null;
    }
}
