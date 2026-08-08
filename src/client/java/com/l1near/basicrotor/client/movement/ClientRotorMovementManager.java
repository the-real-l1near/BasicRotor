package com.l1near.basicrotor.client.movement;

/*
---------------------------
Imports
---------------------------
*/

import net.minecraft.core.BlockPos;

import java.util.LinkedHashMap;
import java.util.Map;
import com.l1near.basicrotor.client.data.ClientRotorKey;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;

public class ClientRotorMovementManager {

    /*
    ---------------------------
    Fields
    ---------------------------
    */

    private final LinkedHashMap<ClientRotorKey, ClientRotorMovementData> rotors;

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
            ResourceKey<Level> dimension,
            BlockPos rotorPos
    ) {

        ClientRotorKey key =
                new ClientRotorKey(
                        dimension,
                        rotorPos
                );

        return rotors.computeIfAbsent(
                key,
                ignored -> new ClientRotorMovementData()
        );
    }

    //Get
    public ClientRotorMovementData get(
            ResourceKey<Level> dimension,
            BlockPos rotorPos
    ) {

        return rotors.get(
                new ClientRotorKey(
                        dimension,
                        rotorPos
                )
        );
    }

    //Remove
    public void remove(
            ResourceKey<Level> dimension,
            BlockPos rotorPos
    ) {

        rotors.remove(
                new ClientRotorKey(
                        dimension,
                        rotorPos
                )
        );
    }

    //Clear
    public void clear() {
        rotors.clear();
    }

    //Get All
    public Iterable<Map.Entry<ClientRotorKey, ClientRotorMovementData>> getEntries() {
        return rotors.entrySet();
    }
}