package com.l1near.basicrotor.network;

/*
---------------------------
Imports
---------------------------
*/

import com.l1near.basicrotor.assembly.LinkedAssembly;
import com.l1near.basicrotor.network.payload.AssemblySnapshotPayload;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.block.Block;

import java.util.ArrayList;
import java.util.List;
import com.l1near.basicrotor.network.payload.AssemblyRemovePayload;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.minecraft.server.level.ServerLevel;

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

    //Remove
    public static void sendRemove(
            ServerLevel level,
            BlockPos rotorPos
    ) {

        AssemblyRemovePayload payload =
                new AssemblyRemovePayload(
                        rotorPos
                );

        for (ServerPlayer player
                : PlayerLookup.tracking(
                level,
                rotorPos
        )) {

            ServerPlayNetworking.send(
                    player,
                    payload
            );
        }
    }

    //Snapshot To Tracking
    public static void sendSnapshotToTracking(
            ServerLevel level,
            LinkedAssembly assembly
    ) {

        for (ServerPlayer player
                : PlayerLookup.tracking(
                level,
                assembly.getOriginPos()
        )) {

            sendSnapshot(
                    player,
                    assembly
            );
        }
    }
}