package com.l1near.basicrotor.client;

/*
---------------------------
Imports
---------------------------
*/

import com.l1near.basicrotor.client.render.RotorBlockEntityRenderer;
import com.l1near.basicrotor.registry.ModBlockEntities;
import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import com.l1near.basicrotor.client.assembly.ClientAssemblyManager;
import com.l1near.basicrotor.client.assembly.VirtualAssemblyData;
import com.l1near.basicrotor.network.payload.AssemblySnapshotPayload;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.world.level.block.Block;
import com.l1near.basicrotor.assembly.LinkedBlockData;
import com.l1near.basicrotor.network.payload.RotorMovementPayload;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import com.l1near.basicrotor.network.payload.AssemblyRequestPayload;
import com.l1near.basicrotor.network.payload.AssemblyRemovePayload;

public class BasicRotorClient implements ClientModInitializer {

    /*
    ---------------------------
    Fields
    ---------------------------
    */

    private static final ClientAssemblyManager ASSEMBLY_MANAGER =
            new ClientAssemblyManager();

    /*
    ---------------------------
    Methods
    ---------------------------
    */

    //Getters
    public static ClientAssemblyManager getAssemblyManager() {
        return ASSEMBLY_MANAGER;
    }

    //Overides
    @Override
    public void onInitializeClient() {

        BlockEntityRenderers.register(
                ModBlockEntities.ROTOR,
                RotorBlockEntityRenderer::new
        );

        ClientPlayNetworking.registerGlobalReceiver(
                AssemblySnapshotPayload.TYPE,
                (payload, context) -> {

                    context.client().execute(() -> {
                        VirtualAssemblyData assembly =
                                new VirtualAssemblyData(
                                        payload.rotorPos()
                                );

                        for (AssemblySnapshotPayload.BlockEntry entry
                                : payload.blocks()) {

                            assembly.addBlock(
                                    entry.relativePos(),
                                    new LinkedBlockData(
                                            Block.stateById(
                                                    entry.blockStateId()
                                            )
                                    )
                            );
                        }

                        ASSEMBLY_MANAGER.register(
                                payload.rotorPos(),
                                assembly
                        );
                        ASSEMBLY_MANAGER.clearPending(
                                payload.rotorPos()
                        );
                    });
                }
        );

        ClientPlayNetworking.registerGlobalReceiver(
                RotorMovementPayload.TYPE,
                (payload, context) -> {

                    context.client().execute(() -> {

                        VirtualAssemblyData assembly =
                                ASSEMBLY_MANAGER.get(
                                        payload.rotorPos()
                                );

                        if (assembly == null) {

                            if (ASSEMBLY_MANAGER.markPending(
                                    payload.rotorPos()
                            )) {

                                ClientPlayNetworking.send(
                                        new AssemblyRequestPayload(
                                                payload.rotorPos()
                                        )
                                );
                            }

                            return;
                        }

                        assembly.setRotation(
                                payload.rotation()
                        );

                        assembly.setRotationStep(
                                payload.rotationStep()
                        );

                        assembly.setVirtualized(
                                payload.virtualized()
                        );
                    });
                }
        );

        ClientPlayNetworking.registerGlobalReceiver(
                AssemblyRemovePayload.TYPE,
                (payload, context) -> {

                    context.client().execute(() -> {

                        ASSEMBLY_MANAGER.remove(
                                payload.rotorPos()
                        );

                        ASSEMBLY_MANAGER.clearPending(
                                payload.rotorPos()
                        );
                    });
                }
        );

        ClientTickEvents.END_CLIENT_TICK.register(
                client -> {

                    for (VirtualAssemblyData assembly
                            : ASSEMBLY_MANAGER.getAssemblies()) {

                        assembly.tickRenderRotation();
                    }
                }
        );

    }
}