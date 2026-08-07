package com.l1near.basicrotor.network;

/*
---------------------------
Imports
---------------------------
*/

import com.l1near.basicrotor.assembly.LinkedAssembly;
import com.l1near.basicrotor.network.payload.AssemblySnapshotPayload;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.block.Block;

import java.util.ArrayList;
import java.util.List;

public class AssemblySync {

    /*
    ---------------------------
    Methods
    ---------------------------
    */

    //Snapshot
    public static void sendSnapshot(
            ServerPlayer player,
            LinkedAssembly assembly
    ) {

        List<AssemblySnapshotPayload.BlockEntry> blockEntries =
                new ArrayList<>();

        for (var entry : assembly.getEntries()) {

            blockEntries.add(
                    new AssemblySnapshotPayload.BlockEntry(
                            entry.getKey(),
                            Block.getId(
                                    entry.getValue().getBlockState()
                            )
                    )
            );
        }

        AssemblySnapshotPayload payload =
                new AssemblySnapshotPayload(
                        assembly.getOriginPos(),
                        blockEntries
                );

        ServerPlayNetworking.send(
                player,
                payload
        );
    }
}