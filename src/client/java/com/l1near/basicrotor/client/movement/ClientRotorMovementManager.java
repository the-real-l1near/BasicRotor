package com.l1near.basicrotor.client.movement;

/*
---------------------------
Imports
---------------------------
*/

import net.minecraft.core.BlockPos;

import java.util.LinkedHashMap;
import java.util.Map;

public class ClientRotorMovementManager {

    /*
    ---------------------------
    Fields
    ---------------------------
    */

    private final LinkedHashMap<BlockPos, ClientRotorMovementData> rotors;

    /*
    ---------------------------
    Constructors
    ---------------------------
    */

    public ClientRotorMovementManager() {
        this.rotors = new LinkedHashMap<>();
    }

    /*
    ---------------------------
    Methods
    ---------------------------
    */

    //Get Or Create
    public ClientRotorMovementData getOrCreate(
            BlockPos rotorPos
    ) {
        return rotors.computeIfAbsent(
                rotorPos,
                key -> new ClientRotorMovementData()
        );
    }

    //Get
    public ClientRotorMovementData get(
            BlockPos rotorPos
    ) {
        return rotors.get(
                rotorPos
        );
    }

    //Remove
    public void remove(
            BlockPos rotorPos
    ) {
        rotors.remove(
                rotorPos
        );
    }

    //Clear
    public void clear() {
        rotors.clear();
    }

    //Get All
    public Iterable<Map.Entry<BlockPos, ClientRotorMovementData>> getEntries() {
        return rotors.entrySet();
    }
}