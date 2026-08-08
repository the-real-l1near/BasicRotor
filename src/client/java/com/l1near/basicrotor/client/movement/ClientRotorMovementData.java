package com.l1near.basicrotor.client.movement;

/*
---------------------------
Fields
---------------------------
*/

public class ClientRotorMovementData {

    /*
    ---------------------------
    Fields
    ---------------------------
    */

    private float rotation;
    private float rotationStep;

    private float renderRotation;
    private float previousRenderRotation;
    private boolean moving;

    /*
    ---------------------------
    Constructors
    ---------------------------
    */

    public ClientRotorMovementData() {

        this.rotation = 0.0F;
        this.rotationStep = 0.0F;

        this.renderRotation = 0.0F;
        this.previousRenderRotation = 0.0F;
        this.moving = false;
    }

    /*
    ---------------------------
    Methods
    ---------------------------
    */

    //Tick
    //Tick
    //Tick
    public void tickRenderRotation() {

        previousRenderRotation =
                renderRotation;

        if (!moving) {

            renderRotation =
                    rotation;

            return;
        }

        renderRotation +=
                rotationStep;

        renderRotation %= 360.0F;

        if (renderRotation < 0.0F) {
            renderRotation += 360.0F;
        }
    }

    //Getters
    public float getRotation() {
        return rotation;
    }

    public float getRotationStep() {
        return rotationStep;
    }

    public float getRenderRotation() {
        return renderRotation;
    }

    public float getPreviousRenderRotation() {
        return previousRenderRotation;
    }

    //Setters
    public void setRotation(float rotation) {
        this.rotation = rotation;
    }

    public void setRotationStep(float rotationStep) {
        this.rotationStep = rotationStep;
    }
    public void setMoving(boolean moving) {
        this.moving = moving;
    }
}