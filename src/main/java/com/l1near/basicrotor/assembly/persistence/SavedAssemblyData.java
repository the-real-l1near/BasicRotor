package com.l1near.basicrotor.assembly.persistence;

/*
---------------------------
Imports
---------------------------
*/

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.l1near.basicrotor.assembly.LinkedAssembly;
import com.l1near.basicrotor.assembly.LinkedBlockData;

public class SavedAssemblyData {

    /*
    ---------------------------
    Fields
    ---------------------------
    */

    public static final Codec<SavedAssemblyData> CODEC =
            RecordCodecBuilder.create(
                    instance ->
                            instance.group(
                                    BlockPos.CODEC
                                            .fieldOf("origin_pos")
                                            .forGetter(
                                                    SavedAssemblyData::getOriginPos
                                            ),
                                    SavedBlockData.CODEC
                                            .listOf()
                                            .fieldOf("blocks")
                                            .forGetter(
                                                    SavedAssemblyData::getSavedBlocks
                                            )
                            ).apply(
                                    instance,
                                    SavedAssemblyData::fromSavedBlocks
                            )
            );

    private final BlockPos originPos;

    private final LinkedHashMap<BlockPos, BlockState> blocks;

    //From Assembly
    public static SavedAssemblyData fromAssembly(
            LinkedAssembly assembly
    ) {

        LinkedHashMap<BlockPos, BlockState> blocks =
                new LinkedHashMap<>();

        for (Map.Entry<BlockPos, LinkedBlockData> entry
                : assembly.getEntries()) {

            blocks.put(
                    entry.getKey(),
                    entry.getValue().getBlockState()
            );
        }

        return new SavedAssemblyData(
                assembly.getOriginPos(),
                blocks
        );
    }

    //To Assembly
    public LinkedAssembly toAssembly() {

        LinkedAssembly assembly =
                new LinkedAssembly(
                        originPos
                );

        for (Map.Entry<BlockPos, BlockState> entry
                : blocks.entrySet()) {

            assembly.addBlock(
                    entry.getKey(),
                    new LinkedBlockData(
                            entry.getValue()
                    )
            );
        }

        return assembly;
    }

    /*
    ---------------------------
    Constructors
    ---------------------------
    */

    public SavedAssemblyData(
            BlockPos originPos,
            LinkedHashMap<BlockPos, BlockState> blocks
    ) {
        this.originPos = originPos;
        this.blocks = blocks;
    }

    /*
    ---------------------------
    Methods
    ---------------------------
    */

    //Factory
    private static SavedAssemblyData fromSavedBlocks(
            BlockPos originPos,
            List<SavedBlockData> savedBlocks
    ) {

        LinkedHashMap<BlockPos, BlockState> blocks =
                new LinkedHashMap<>();

        for (SavedBlockData savedBlock : savedBlocks) {

            blocks.put(
                    savedBlock.getRelativePos(),
                    savedBlock.getBlockState()
            );
        }

        return new SavedAssemblyData(
                originPos,
                blocks
        );
    }

    //Getters
    public BlockPos getOriginPos() {
        return originPos;
    }

    public Map<BlockPos, BlockState> getBlocks() {
        return blocks;
    }

    public List<SavedBlockData> getSavedBlocks() {

        List<SavedBlockData> savedBlocks =
                new ArrayList<>();

        for (Map.Entry<BlockPos, BlockState> entry
                : blocks.entrySet()) {

            savedBlocks.add(
                    new SavedBlockData(
                            entry.getKey(),
                            entry.getValue()
                    )
            );
        }

        return savedBlocks;
    }
}