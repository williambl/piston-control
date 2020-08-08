package com.williambl.pistoncontrol.mixin;

import com.williambl.pistoncontrol.IterableHashSet;
import javafx.util.Pair;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.block.piston.PistonHandler;
import net.minecraft.tag.Tag;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import static com.williambl.pistoncontrol.PistonControl.STICKY_MAP;

@Mixin(PistonHandler.class)
public class PistonHandlerAdjacentStuckMixin {
    private static final Block unmatchableBlock = new Block(AbstractBlock.Settings.of(Material.AIR));

    @Redirect(
            at = @At(
                value = "FIELD",
                target ="Lnet/minecraft/block/Blocks;HONEY_BLOCK:Lnet/minecraft/block/Block;",
                ordinal = 0),
            method = "isAdjacentBlockStuck"
    )
    private static Block fakeFirstHoneyBlockCheck(Block block, Block block2) {
        // all logic in this check
        if (
                //both are selectively sticky
                STICKY_MAP.containsKey(block) &&
                STICKY_MAP.containsKey(block2) &&
                //do not intersect
                !STICKY_MAP.get(block).intersects(STICKY_MAP.get(block2))
        )
            // pass check: do not stick
            return block;
        else
            // fail check: do normal sticky check
            return unmatchableBlock;
    }

    @Redirect(
            at = @At(
                value = "FIELD",
                target ="Lnet/minecraft/block/Blocks;SLIME_BLOCK:Lnet/minecraft/block/Block;",
                ordinal = 0
            ),
            method = "isAdjacentBlockStuck"
    )
    private static Block passFirstSlimeBlockCheck(Block block, Block block2) {
        // always pass
        return block2;
    }

    @Redirect(
            at = @At(
                value = "FIELD",
                target ="Lnet/minecraft/block/Blocks;HONEY_BLOCK:Lnet/minecraft/block/Block;",
                ordinal = 1
            ),
            method = "isAdjacentBlockStuck"
    )
    private static Block failSecondHoneyBlockCheck(Block block, Block block2) {
        // always fail
        return unmatchableBlock;
    }

    @Redirect(
            at = @At(
                value = "FIELD",
                target ="Lnet/minecraft/block/Blocks;SLIME_BLOCK:Lnet/minecraft/block/Block;",
                ordinal = 1
            ),
            method = "isAdjacentBlockStuck"
    )
    private static Block failSecondSlimeBlockCheck(Block block, Block block2) {
        // always fail
        return unmatchableBlock;
    }

//    private static <T> boolean doCollectionPairsIntersect(Pair<HashSet<T>, ArrayList<T>> collectionPair1, Pair<HashSet<T>, ArrayList<T>> collectionPair2) {
//        for (T entry : collectionPair1.getValue())
//            if(collectionPair2.getKey().contains(entry))
//                return true;
//
//        return false;
//    }
}
