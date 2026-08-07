package com.l1near.basicrotor.assembly.manager;

/*
---------------------------
Imports
---------------------------
*/

import com.l1near.basicrotor.assembly.LinkedAssembly;
import net.minecraft.core.BlockPos;

import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Set;


public class AssemblyManager {

    /*
    ---------------------------
    Fields
    ---------------------------
    */

    private final LinkedHashMap<BlockPos, LinkedAssembly> assemblies;
    private final Set<BlockPos> pendingRequests;

    /*
    ---------------------------
    Constructors
    ---------------------------
    */

    public AssemblyManager() {

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
            LinkedAssembly assembly
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

    //Get
    public LinkedAssembly get(BlockPos rotorPos) {
        return assemblies.get(rotorPos);
    }

    //Clear
    public void clear() {
        assemblies.clear();
    }

    //Assemblies
    public Collection<LinkedAssembly> getAssemblies() {
        return assemblies.values();
    }

    //Requests
    public boolean markPending(BlockPos rotorPos) {
        return pendingRequests.add(rotorPos);
    }

    public void clearPending(BlockPos rotorPos) {
        pendingRequests.remove(rotorPos);
    }
}