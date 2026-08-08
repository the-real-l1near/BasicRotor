package com.l1near.basicrotor.client.assembly;

/*
---------------------------
Imports
---------------------------
*/

import com.l1near.basicrotor.client.data.ClientRotorKey;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;

import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Set;

public class ClientAssemblyManager {

    /*
    ---------------------------
    Fields
    ---------------------------
    */

    private final LinkedHashMap<ClientRotorKey, VirtualAssemblyData> assemblies;

    private final Set<ClientRotorKey> pendingRequests;

    /*
    ---------------------------
    Constructors
    ---------------------------
    */

    public ClientAssemblyManager() {
        this.assemblies = new LinkedHashMap<>();
        this.pendingRequests = new HashSet<>();
    }

    /*
    ---------------------------
    Methods
    ---------------------------
    */

    //Register
    public void register(
            ResourceKey<Level> dimension,
            BlockPos rotorPos,
            VirtualAssemblyData assembly
    ) {

        assemblies.put(
                new ClientRotorKey(
                        dimension,
                        rotorPos
                ),
                assembly
        );
    }

    //Remove
    public void remove(
            ResourceKey<Level> dimension,
            BlockPos rotorPos
    ) {

        assemblies.remove(
                new ClientRotorKey(
                        dimension,
                        rotorPos
                )
        );
    }

    //Contains
    public boolean contains(
            ResourceKey<Level> dimension,
            BlockPos rotorPos
    ) {

        return assemblies.containsKey(
                new ClientRotorKey(
                        dimension,
                        rotorPos
                )
        );
    }

    //Requests
    public boolean markPending(
            ResourceKey<Level> dimension,
            BlockPos rotorPos
    ) {

        return pendingRequests.add(
                new ClientRotorKey(
                        dimension,
                        rotorPos
                )
        );
    }

    public void clearPending(
            ResourceKey<Level> dimension,
            BlockPos rotorPos
    ) {

        pendingRequests.remove(
                new ClientRotorKey(
                        dimension,
                        rotorPos
                )
        );
    }

    //Get
    public VirtualAssemblyData get(
            ResourceKey<Level> dimension,
            BlockPos rotorPos
    ) {

        return assemblies.get(
                new ClientRotorKey(
                        dimension,
                        rotorPos
                )
        );
    }

    //Clear
    public void clear() {
        assemblies.clear();
        pendingRequests.clear();
    }

    //Assemblies
    public Collection<VirtualAssemblyData> getAssemblies() {
        return assemblies.values();
    }
}