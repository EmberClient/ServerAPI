package com.emberclient.serverapi.packet.impl;

import com.emberclient.serverapi.ByteBufWrapper;
import com.emberclient.serverapi.packet.Packet;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

/**
 * Represents a packet what gets sent from the client to the server.
 *
 * @see Packet
 */
public interface ServerboundPacket extends Packet {
    /**
     * Reads the given buffer.
     *
     * @param buf the buffer
     */
    default void read(@NotNull ByteBufWrapper buf) {
        // empty
    }

    /**
     * Handles this packet (e.g. by calling an event).
     *
     * @param player the player who sent the packet
     */
    default void handle(@NotNull Player player) {
        // empty
    }
}