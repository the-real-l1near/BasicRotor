package com.l1near.basicrotor.blockentity;

/*
---------------------------
Imports
---------------------------
*/

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
    private float rotation;
    private float lastRotation;

    private float speed = 0.0F;
    private float acceleration = 0.2F;

    private static final float MAX_SPEED = 30.0F;
    private static final float FRICTION = 0.995F;

    /*
    ---------------------------
    Constructors
    ---------------------------
    */

    public RotorBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.ROTOR, pos, state);
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
        lastRotation = rotation;


        speed += acceleration;


        speed = Mth.clamp(speed, -MAX_SPEED, MAX_SPEED);


        speed *= FRICTION;


        rotation += speed;
    }

    //serverTick
    private void serverTick() {

    }

    //Getter
    public float getRotation() {
        return rotation;
    }

    public float getRotation(float partialTick) {
        return Mth.lerp(partialTick, lastRotation, rotation);
    }
}