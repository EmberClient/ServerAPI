package com.emberclient.serverapi.packet.impl;

import com.emberclient.serverapi.utils.ByteBufWrapper;
import com.emberclient.serverapi.packet.Packet;
import org.jetbrains.annotations.NotNull;

/**
 * Represents a packet what gets sent from the server to the client.
 *
 * @see Packet
 */
public interface ClientboundPacket extends Packet {
    /**
     * Writes this packet to the given buffer.
     *
     * @param buf the buffer
     */
    default void write(@NotNull ByteBufWrapper buf) {
        // empty
    }
}