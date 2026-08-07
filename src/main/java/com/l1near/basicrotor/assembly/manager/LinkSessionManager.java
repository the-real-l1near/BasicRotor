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

public final class LinkSessionManager {

    /*
    ---------------------------
    Fields
    ---------------------------
    */

    private static final Map<UUID, LinkSession> sessions =
            new HashMap<>();

    /*
    ---------------------------
    Constructors
    ---------------------------
    */

    private LinkSessionManager() {

    }

    /*
    ---------------------------
    Methods
    ---------------------------
    */

    //Get Session
    public static LinkSession getSession(Player player) {
        return sessions.computeIfAbsent(
                player.getUUID(),
                uuid -> new LinkSession()
        );
    }

    //Clear Session
    public static void clearSession(Player player) {
        sessions.remove(player.getUUID());
    }

}