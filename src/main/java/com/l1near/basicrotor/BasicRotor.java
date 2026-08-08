package com.l1near.basicrotor;

/*
---------------------------
Imports
---------------------------
*/

import com.l1near.basicrotor.assembly.LinkedAssembly;
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

public class BasicRotor implements ModInitializer {

    /*
    ---------------------------
    Fields
    ---------------------------
    */

    public static final String MOD_ID = "basicrotor";
    private static final AssemblyManager ASSEMBLY_MANAGER =
            new AssemblyManager();

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

                    for (var assembly
                            : ASSEMBLY_MANAGER.getAssemblies()) {

                        AssemblySync.sendSnapshot(
                                handler.player,
                                assembly
                        );
                    }
                }
        );
        ServerPlayNetworking.registerGlobalReceiver(
                AssemblyRequestPayload.TYPE,
                (payload, context) -> {

                    context.server().execute(() -> {

                        LinkedAssembly assembly =
                                ASSEMBLY_MANAGER.get(
                                        payload.rotorPos()
                                );

                        if (assembly == null) {
                            return;
                        }

                        AssemblySync.sendSnapshot(
                                context.player(),
                                assembly
                        );
                    });
                }
        );
        PlayerBlockBreakEvents.AFTER.register(
                (level, player, blockPos, blockState, blockEntity) -> {
                    LinkedAssembly rotorAssembly =
                            ASSEMBLY_MANAGER.get(
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

                        ASSEMBLY_MANAGER.remove(
                                blockPos
                        );

                        if (level instanceof ServerLevel serverLevel) {

                            AssemblySync.sendRemove(
                                    serverLevel,
                                    blockPos
                            );
                        }

                        return;
                    }
                    LinkedAssembly assembly =
                            ASSEMBLY_MANAGER.findByBlockPos(
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

                        ASSEMBLY_MANAGER.remove(
                                assembly.getOriginPos()
                        );

                        if (level instanceof ServerLevel serverLevel) {

                            AssemblySync.sendRemove(
                                    serverLevel,
                                    assembly.getOriginPos()
                            );
                        }

                        return;
                    }
                    if (level instanceof ServerLevel serverLevel) {

                        AssemblySync.sendSnapshotToTracking(
                                serverLevel,
                                assembly
                        );
                    }
                    System.out.println(
                            "[BasicRotor] Linked block removed. Remaining blocks: "
                                    + assembly.size()
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
    public static AssemblyManager getAssemblyManager() {
        return ASSEMBLY_MANAGER;
    }
}