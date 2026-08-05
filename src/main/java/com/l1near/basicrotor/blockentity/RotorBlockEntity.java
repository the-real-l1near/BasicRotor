package com.l1near.basicrotor.blockentity;

/*
---------------------------
Imports
---------------------------
*/

import com.l1near.basicrotor.movement.MovementData;
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

    /*
    ---------------------------
    Constructors
    ---------------------------
    */

    public RotorBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.ROTOR, pos, state);

        this.movementData = new MovementData();
        this.movementRuntime = new MovementRuntime(movementData);
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
        movementRuntime.tick();
    }

    //serverTick
    private void serverTick() {

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
}