package com.l1near.basicrotor.movement;

/*
---------------------------
Imports
---------------------------
*/

import net.minecraft.util.Mth;

public class MovementRuntime {

    /*
    ---------------------------
    Fields
    ---------------------------
    */
    private static final float ACCELERATION = 0.05F;
    private static final float MAX_SPEED = 1.0F;
    private final MovementData movementData;
    private static final float BRAKING_FORCE = 0.5F;
    //Starting
    private void updateStarting(MovementInput input) {

        movementData.setLastRotation(
                movementData.getRotation()
        );

        float speed =
                movementData.getSpeed();

        speed += movementData.getAcceleration();

        speed = Mth.clamp(
                speed,
                0.0F,
                movementData.getMaxSpeed()
        );

        movementData.setSpeed(speed);
        System.out.println(
                "STARTING speed = " + speed
        );
        movementData.setRotation(
                movementData.getRotation()
                        + speed
        );


        if (speed >= movementData.getMaxSpeed()) {

            movementData.setState(
                    MovementState.RUNNING
            );
        }
    }

    private void updateRunning(MovementInput input) {

        movementData.setLastRotation(
                movementData.getRotation()
        );

        movementData.setRotation(
                movementData.getRotation()
                        + movementData.getSpeed()
        );
        System.out.println(
                "RUNNING speed = " + movementData.getSpeed()
        );
    }



    private void updateBraking(MovementInput input) {

        movementData.setLastRotation(
                movementData.getRotation()
        );


        float speed =
                movementData.getSpeed();


        speed -= movementData.getAcceleration();


        if (speed <= 0.0F) {

            speed = 0.0F;

            movementData.setState(
                    MovementState.RETURNING
            );
        }


        movementData.setSpeed(speed);
        System.out.println(
                "BRAKING speed = " + speed
        );

        movementData.setRotation(
                movementData.getRotation()
                        + speed
        );
    }

    private void updateReturning(MovementInput input) {

    }

    private void updateStopped(MovementInput input) {

    }


    /*
    ---------------------------
    Constructors
    ---------------------------
    */

    public MovementRuntime(MovementData movementData) {
        this.movementData = movementData;
    }

    /*
    ---------------------------
    Methods
    ---------------------------
    */

    //Tick
    public void tick(MovementInput input) {

        updateState(input);

        switch (movementData.getState()) {
            case STARTING ->
                    updateStarting(input);
            case RUNNING ->
                    updateRunning(input);

            case BRAKING ->
                    updateBraking(input);

            case RETURNING ->
                    updateReturning(input);

            case STOPPED ->
                    updateStopped(input);
        }
    }

    private void updateState(MovementInput input) {

        MovementState currentState =
                movementData.getState();


        if (input.isPowered()) {

            if (currentState == MovementState.STOPPED) {

                movementData.setHomeRotation(
                        movementData.getRotation()
                );

                movementData.setState(
                        MovementState.STARTING
                );
            }

        } else {

            if (currentState == MovementState.RUNNING) {

                movementData.setState(
                        MovementState.BRAKING
                );
            }
        }
    }

}