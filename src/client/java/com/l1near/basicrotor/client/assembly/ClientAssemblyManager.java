package com.l1near.basicrotor.client.assembly;

/*
---------------------------
Imports
---------------------------
*/

import net.minecraft.core.BlockPos;

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

    private final LinkedHashMap<BlockPos, VirtualAssemblyData> assemblies;
    private final Set<BlockPos> pendingRequests;
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
            BlockPos rotorPos,
            VirtualAssemblyData assembly
    ) {
        assemblies.put(rotorPos, assembly);
    }

    //Remove
    public void remove(BlockPos rotorPos) {
        assemblies.remove(rotorPos);
    }

    //Contains
    public boolean contains(BlockPos rotorPos) {
        return assemblies.containsKey(rotorPos);
    }

    public boolean markPending(BlockPos rotorPos) {
        return pendingRequests.add(rotorPos);
    }

    public void clearPending(BlockPos rotorPos) {
        pendingRequests.remove(rotorPos);
    }

    //Get
    public VirtualAssemblyData get(BlockPos rotorPos) {
        return assemblies.get(rotorPos);
    }

    //Clear
    public void clear() {
        assemblies.clear();
    }

    //Assemblies
    public Collection<VirtualAssemblyData> getAssemblies() {
        return assemblies.values();
    }
}