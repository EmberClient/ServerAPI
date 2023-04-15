package com.emberclient.serverapi.packet;

/**
 * Represents a packet sent using custom payload packets.
 */
public interface Packet {
    /**
     * Returns the id of this packet.
     *
     * @return the id of this packet
     */
    int packetId();
}