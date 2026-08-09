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

    private final MovementData movementData;
    private static final float RETURN_SPEED = 1.0F;
    //Starting
    private void updateStarting(MovementInput input) {

        float speed =
                movementData.getSpeed();

        speed += movementData.getAcceleration();

        speed = Mth.clamp(
                speed,
                0.0F,
                movementData.getMaxSpeed()
        );

        movementData.setSpeed(speed);

        rotateBy(speed);


        if (speed >= movementData.getMaxSpeed()) {

            movementData.setState(
                    MovementState.RUNNING
            );
        }
    }

    private void updateRunning(MovementInput input) {

        rotateBy(
                movementData.getSpeed()
        );
    }



    private void updateBraking(MovementInput input) {

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

        rotateBy(speed);
    }

    private void updateReturning(MovementInput input) {

        float distance =
                getForwardAngleDistance(
                        movementData.getRotation(),
                        movementData.getHomeRotation()
                );

        if (distance <= RETURN_SPEED) {

            movementData.setRotation(
                    movementData.getHomeRotation()
            );

            movementData.setState(
                    MovementState.STOPPED
            );

            return;
        }

        rotateBy(RETURN_SPEED);
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

    /*
    ---------------------------
    Helpers
    ---------------------------
    */

    //Rotation
    private void rotateBy(float angle) {

        movementData.setLastRotation(
                movementData.getRotation()
        );

        movementData.setRotation(
                movementData.getRotation()
                        + angle
        );
    }

    //Forward Angle Distance
    private float getForwardAngleDistance(
            float current,
            float target
    ) {

        if (target >= current) {
            return target - current;
        }

        return (360.0F - current) + target;
    }
}