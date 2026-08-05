package com.l1near.basicrotor.movement;

/*
---------------------------
Imports
---------------------------
*/

public class MovementData {

    /*
    ---------------------------
    Fields
    ---------------------------
    */

    private float rotation;

    private float lastRotation;

    private float speed;

    private float acceleration;

    private float maxSpeed;

    private float friction;

    /*
    ---------------------------
    Constructors
    ---------------------------
    */

    public MovementData() {
        this.rotation = 0.0F;
        this.lastRotation = 0.0F;

        this.speed = 0.0F;
        this.acceleration = 0.2F;

        this.maxSpeed = 30.0F;
        this.friction = 0.995F;
    }

    /*
    ---------------------------
    Methods
    ---------------------------
    */

    //Getters
    public float getRotation() {
        return rotation;
    }

    public float getLastRotation() {
        return lastRotation;
    }

    public float getSpeed() {
        return speed;
    }

    public float getAcceleration() {
        return acceleration;
    }

    public float getMaxSpeed() {
        return maxSpeed;
    }

    public float getFriction() {
        return friction;
    }

    //Setters
    public void setRotation(float rotation) {
        this.rotation = rotation;
    }

    public void setLastRotation(float lastRotation) {
        this.lastRotation = lastRotation;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public void setAcceleration(float acceleration) {
        this.acceleration = acceleration;
    }

    public void setMaxSpeed(float maxSpeed) {
        this.maxSpeed = maxSpeed;
    }

    public void setFriction(float friction) {
        this.friction = friction;
    }

}