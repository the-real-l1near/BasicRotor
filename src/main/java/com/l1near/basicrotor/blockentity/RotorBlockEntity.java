package com.l1near.basicrotor.blockentity;

/*
---------------------------
Imports
---------------------------
*/

import com.l1near.basicrotor.assembly.LinkedAssembly;
import com.l1near.basicrotor.assembly.runtime.AssemblyRuntime;
import com.l1near.basicrotor.movement.MovementData;
import com.l1near.basicrotor.movement.MovementInput;
import com.l1near.basicrotor.movement.MovementRuntime;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import com.l1near.basicrotor.registry.ModBlockEntities;
import com.l1near.basicrotor.network.payload.RotorMovementPayload;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.server.level.ServerPlayer;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import com.l1near.basicrotor.BasicRotor;

public class RotorBlockEntity extends BlockEntity {

    /*
    ---------------------------
    Fields
    ---------------------------
    */
    private final MovementData movementData;

    private final MovementRuntime movementRuntime;

    private final AssemblyRuntime assemblyRuntime;

    private LinkedAssembly linkedAssembly;

    /*
    ---------------------------
    Constructors
    ---------------------------
    */

    public RotorBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.ROTOR, pos, state);

        this.movementData = new MovementData();
        this.movementRuntime = new MovementRuntime(movementData);
        this.linkedAssembly = new LinkedAssembly(pos);
        this.assemblyRuntime = new AssemblyRuntime();
    }
    /*
    ---------------------------
    Methods
    ---------------------------
    */

    //Tick
    public void tick() {
        if (level == null) {
            return;
        }

        if (level.isClientSide()) {
            clientTick();
        } else {
            serverTick();
        }
    }
    //clientTick
    private void clientTick() {

    }

    //serverTick
    private void serverTick() {

        if (level instanceof ServerLevel serverLevel) {

            LinkedAssembly savedAssembly =
                    BasicRotor
                            .getAssemblyManager(
                                    serverLevel
                            )
                            .get(
                                    worldPosition
                            );

            if (savedAssembly != null) {

                linkedAssembly =
                        savedAssembly;
            }
        }

        MovementInput movementInput = new MovementInput();

        int power =
                level.getBestNeighborSignal(worldPosition);

        movementInput.setPowered(power > 0);

        movementRuntime.tick(movementInput);

        assemblyRuntime.update(
                level,
                linkedAssembly,
                movementData
        );

        if (!level.isClientSide()) {

            float rotationStep =
                    movementData.getRotation()
                            - movementData.getLastRotation();

            if (rotationStep < 0.0F) {
                rotationStep += 360.0F;
            }

            if (level instanceof ServerLevel serverLevel) {

                RotorMovementPayload payload =
                        new RotorMovementPayload(
                                worldPosition,
                                movementData.getRotation(),
                                rotationStep,
                                assemblyRuntime.isVirtualized()
                        );

                for (ServerPlayer player
                        : PlayerLookup.tracking(
                        serverLevel,
                        worldPosition
                )) {

                    ServerPlayNetworking.send(
                            player,
                            payload
                    );
                }
            }
        }
    }

    //Getter
    public float getRotation() {
        return movementData.getRotation();
    }

    public float getRotation(float partialTick) {
        return Mth.lerp(
                partialTick,
                movementData.getLastRotation(),
                movementData.getRotation()
        );
    }

    public LinkedAssembly getLinkedAssembly() {
        return linkedAssembly;
    }

    public AssemblyRuntime getAssemblyRuntime() {
        return assemblyRuntime;
    }

    public MovementData getMovementData() {
        return movementData;
    }

    //Setters
    public void setLinkedAssembly(LinkedAssembly linkedAssembly) {
        this.linkedAssembly = linkedAssembly;
    }
}