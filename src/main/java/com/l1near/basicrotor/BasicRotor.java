package com.l1near.basicrotor;

/*
---------------------------
Imports
---------------------------
*/

import com.l1near.basicrotor.assembly.LinkedAssembly;
import com.l1near.basicrotor.assembly.persistence.AssemblySavedData;
import com.l1near.basicrotor.blockentity.RotorBlockEntity;
import com.l1near.basicrotor.registry.ModBlockEntities;
import com.l1near.basicrotor.registry.ModBlocks;
import com.l1near.basicrotor.registry.ModItems;
import net.fabricmc.api.ModInitializer;
import com.l1near.basicrotor.network.payload.AssemblySnapshotPayload;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import com.l1near.basicrotor.network.payload.RotorMovementPayload;
import com.l1near.basicrotor.assembly.manager.AssemblyManager;
import com.l1near.basicrotor.network.AssemblySync;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import com.l1near.basicrotor.network.payload.AssemblyRequestPayload;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import com.l1near.basicrotor.network.payload.AssemblyRemovePayload;

import java.util.HashMap;
import java.util.Map;


public class BasicRotor implements ModInitializer {

    /*
    ---------------------------
    Fields
    ---------------------------
    */

    public static final String MOD_ID = "basicrotor";
    private static final Map<ServerLevel, AssemblyManager> ASSEMBLY_MANAGERS =
            new HashMap<>();

    /*
    ---------------------------
    Constructors
    ---------------------------
    */

    @Override
    public void onInitialize() {
        ModBlocks.init();
        ModItems.init();
        ModBlockEntities.init();
        PayloadTypeRegistry.clientboundPlay().register(
                AssemblySnapshotPayload.TYPE,
                AssemblySnapshotPayload.CODEC
        );
        PayloadTypeRegistry.clientboundPlay().register(
                RotorMovementPayload.TYPE,
                RotorMovementPayload.CODEC
        );
        PayloadTypeRegistry.serverboundPlay().register(
                AssemblyRequestPayload.TYPE,
                AssemblyRequestPayload.CODEC
        );
        PayloadTypeRegistry.clientboundPlay().register(
                AssemblyRemovePayload.TYPE,
                AssemblyRemovePayload.CODEC
        );
        ServerPlayConnectionEvents.JOIN.register(
                (handler, sender, server) -> {

                    ServerPlayer player =
                            handler.player;

                    ServerLevel serverLevel =
                            (ServerLevel) player.level();

                    AssemblyManager assemblyManager =
                            getAssemblyManager(
                                    serverLevel
                            );

                    for (LinkedAssembly assembly
                            : assemblyManager.getAssemblies()) {

                        AssemblySync.sendSnapshot(
                                player,
                                assembly
                        );
                    }
                }
        );
        ServerPlayNetworking.registerGlobalReceiver(
                AssemblyRequestPayload.TYPE,
                (payload, context) -> {

                    context.server().execute(() -> {

                        ServerPlayer player =
                                context.player();

                        ServerLevel serverLevel =
                                (ServerLevel) player.level();

                        LinkedAssembly assembly =
                                getAssemblyManager(
                                        serverLevel
                                ).get(
                                        payload.rotorPos()
                                );

                        if (assembly == null) {
                            return;
                        }

                        AssemblySync.sendSnapshot(
                                player,
                                assembly
                        );
                    });
                }
        );
        PlayerBlockBreakEvents.AFTER.register(
                (level, player, blockPos, blockState, blockEntity) -> {

                    if (!(level instanceof ServerLevel serverLevel)) {
                        return;
                    }

                    AssemblyManager assemblyManager =
                            getAssemblyManager(
                                    serverLevel
                            );

                    LinkedAssembly rotorAssembly =
                            assemblyManager.get(
                                    blockPos
                            );

                    if (rotorAssembly != null) {

                        if (blockEntity instanceof RotorBlockEntity rotor) {

                            if (rotor.getAssemblyRuntime().isVirtualized()) {

                                BlockPos originPos =
                                        rotorAssembly.getOriginPos();

                                for (var entry : rotorAssembly.getEntries()) {

                                    BlockPos worldPos =
                                            originPos.offset(
                                                    entry.getKey()
                                            );

                                    level.setBlock(
                                            worldPos,
                                            entry.getValue().getBlockState(),
                                            3
                                    );
                                }
                            }
                        }

                        assemblyManager.remove(
                                blockPos
                        );

                        saveAssemblyManager(
                                serverLevel
                        );

                        AssemblySync.sendRemove(
                                serverLevel,
                                blockPos
                        );

                        return;
                    }

                    LinkedAssembly assembly =
                            assemblyManager.findByBlockPos(
                                    blockPos
                            );

                    if (assembly == null) {
                        return;
                    }

                    BlockPos relativePos =
                            blockPos.subtract(
                                    assembly.getOriginPos()
                            );

                    assembly.removeBlock(
                            relativePos
                    );

                    if (assembly.isEmpty()) {

                        assemblyManager.remove(
                                assembly.getOriginPos()
                        );

                        saveAssemblyManager(
                                serverLevel
                        );

                        AssemblySync.sendRemove(
                                serverLevel,
                                assembly.getOriginPos()
                        );

                        return;
                    }

                    saveAssemblyManager(
                            serverLevel
                    );

                    AssemblySync.sendSnapshotToTracking(
                            serverLevel,
                            assembly
                    );
                }
        );
    }


    /*
    ---------------------------
    Methods
    ---------------------------
    */

    //Getters
    public static AssemblyManager getAssemblyManager(
            ServerLevel level
    ) {
        return ASSEMBLY_MANAGERS.computeIfAbsent(
                level,
                key -> {

                    AssemblyManager assemblyManager =
                            new AssemblyManager();

                    AssemblySavedData
                            .get(level)
                            .loadIntoManager(
                                    assemblyManager
                            );

                    return assemblyManager;
                }
        );
    }

    //Save Assembly Manager
    public static void saveAssemblyManager(
            ServerLevel level
    ) {

        AssemblyManager assemblyManager =
                getAssemblyManager(
                        level
                );

        AssemblySavedData
                .get(level)
                .updateFromManager(
                        assemblyManager
                );
    }
}