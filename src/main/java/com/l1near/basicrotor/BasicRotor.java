package com.l1near.basicrotor;

/*
---------------------------
Imports
---------------------------
*/

import com.l1near.basicrotor.assembly.LinkedAssembly;
import com.l1near.basicrotor.registry.ModBlockEntities;
import com.l1near.basicrotor.registry.ModBlocks;
import com.l1near.basicrotor.registry.ModItems;
import net.fabricmc.api.ModInitializer;
import com.l1near.basicrotor.network.payload.AssemblySnapshotPayload;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import com.l1near.basicrotor.network.payload.RotorMovementPayload;
import com.l1near.basicrotor.assembly.manager.AssemblyManager;
import com.l1near.basicrotor.network.AssemblySync;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import com.l1near.basicrotor.network.payload.AssemblyRequestPayload;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;

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