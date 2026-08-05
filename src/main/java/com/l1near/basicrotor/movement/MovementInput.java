package com.l1near.basicrotor.movement;

/*
---------------------------
Imports
---------------------------
*/

public class MovementInput {

    /*
    ---------------------------
    Fields
    ---------------------------
    */

    private boolean powered;

    /*
    ---------------------------
    Constructors
    ---------------------------
    */

    public MovementInput() {
        this.powered = false;
    }

    /*
    ---------------------------
    Methods
    ---------------------------
    */

    //Getters
    public boolean isPowered() {
        return powered;
    }

    //Setters
    public void setPowered(boolean powered) {
        this.powered = powered;
    }

}