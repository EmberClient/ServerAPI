package com.emberclient.serverapi.packet;

import com.emberclient.serverapi.ByteBufWrapper;
import org.bukkit.entity.Player;

public abstract class Packet {
    public void write(ByteBufWrapper buf) {
        // empty
    }

    public void read(ByteBufWrapper buf) {
        // empty
    }

    public void handle(Player player) {
        // empty
    }
}
