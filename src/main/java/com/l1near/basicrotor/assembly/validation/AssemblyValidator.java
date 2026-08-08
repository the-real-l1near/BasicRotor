package com.l1near.basicrotor.assembly.validation;

/*
---------------------------
Imports
---------------------------
*/

import com.l1near.basicrotor.registry.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

import java.util.HashSet;
import java.util.Set;

public class AssemblyValidator {

    /*
    ---------------------------
    Fields
    ---------------------------
    */

    private static final Set<Block> UNLINKABLE_BLOCKS =
            new HashSet<>();

    static {

        UNLINKABLE_BLOCKS.add(
                ModBlocks.ROTOR
        );

        UNLINKABLE_BLOCKS.add(
                Blocks.BEDROCK
        );

        UNLINKABLE_BLOCKS.add(
                Blocks.END_PORTAL
        );

        UNLINKABLE_BLOCKS.add(
                Blocks.END_PORTAL_FRAME
        );

        UNLINKABLE_BLOCKS.add(
                Blocks.NETHER_PORTAL
        );

        UNLINKABLE_BLOCKS.add(
                Blocks.COMMAND_BLOCK
        );

        UNLINKABLE_BLOCKS.add(
                Blocks.CHAIN_COMMAND_BLOCK
        );

        UNLINKABLE_BLOCKS.add(
                Blocks.REPEATING_COMMAND_BLOCK
        );

        UNLINKABLE_BLOCKS.add(
                Blocks.STRUCTURE_BLOCK
        );

        UNLINKABLE_BLOCKS.add(
                Blocks.JIGSAW
        );

        UNLINKABLE_BLOCKS.add(
                Blocks.BARRIER
        );

        UNLINKABLE_BLOCKS.add(
                Blocks.SAND
        );

        UNLINKABLE_BLOCKS.add(
                Blocks.RED_SAND
        );

        UNLINKABLE_BLOCKS.add(
                Blocks.GRAVEL
        );

        UNLINKABLE_BLOCKS.add(
                Blocks.ANVIL
        );

        UNLINKABLE_BLOCKS.add(
                Blocks.CHIPPED_ANVIL
        );

        UNLINKABLE_BLOCKS.add(
                Blocks.DAMAGED_ANVIL
        );
    }
    /*
    ---------------------------
    Methods
    ---------------------------
    */

    //Block Validation
    public static boolean canLinkBlock(
            Level level,
            BlockPos blockPos
    ) {

        Block block =
                level
                        .getBlockState(
                                blockPos
                        )
                        .getBlock();

        return !UNLINKABLE_BLOCKS.contains(
                block
        );
    }
}