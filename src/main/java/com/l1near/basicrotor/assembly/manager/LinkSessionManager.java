package com.l1near.basicrotor.assembly.manager;

/*
---------------------------
Imports
---------------------------
*/

import com.l1near.basicrotor.assembly.session.LinkSession;
import net.minecraft.world.entity.player.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class LinkSessionManager {

    /*
    ---------------------------
    Fields
    ---------------------------
    */

    private final Map<UUID, LinkSession> sessions;

    /*
    ---------------------------
    Constructors
    ---------------------------
    */

    public LinkSessionManager() {
        this.sessions = new HashMap<>();
    }

    /*
    ---------------------------
    Methods
    ---------------------------
    */

    //Get Session
    public LinkSession getSession(Player player) {
        return sessions.computeIfAbsent(
                player.getUUID(),
                uuid -> new LinkSession()
        );
    }

    //Clear Session
    public void clearSession(Player player) {
        sessions.remove(player.getUUID());
    }

}