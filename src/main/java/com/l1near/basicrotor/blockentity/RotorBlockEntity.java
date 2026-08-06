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
import net.minecraft.util.Mth;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import com.l1near.basicrotor.registry.ModBlockEntities;

public class RotorBlockEntity extends BlockEntity {

    /*
    ---------------------------
    Fields
    ---------------------------
    */
    private final MovementData movementData;

    private final MovementRuntime movementRuntime;

    private final AssemblyRuntime assemblyRuntime;

    private final LinkedAssembly linkedAssembly;

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

        MovementInput movementInput = new MovementInput();

        int power =
                level.getBestNeighborSignal(worldPosition);

        movementInput.setPowered(power > 0);

        movementRuntime.tick(movementInput);

        assemblyRuntime.update(
                linkedAssembly,
                movementData
        );

        assemblyRuntime.update(
                linkedAssembly,
                movementData
        );

        System.out.println(
                "State = "
                        + movementData.getState()
                        + " | Speed = "
                        + movementData.getSpeed()
        );
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
}