package com.emberclient.serverapi;

import com.emberclient.serverapi.listener.ChannelListener;
import com.emberclient.serverapi.listener.MessageListener;
import com.emberclient.serverapi.packet.Packet;
import com.emberclient.serverapi.packet.PacketManager;
import io.netty.buffer.ByteBuf;
import lombok.Getter;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;

public final class ECServerAPI extends JavaPlugin {
    public static final String CHANNEL_NAME = "ember:data";

    @Getter
    private static ECServerAPI instance;

    @Getter
    private final PacketManager packetManager = new PacketManager();

    private final Set<UUID> playersOnEmber = new HashSet<>();

    @Override
    public void onEnable() {
        instance = this;

        this.getServer().getMessenger().registerOutgoingPluginChannel(this, CHANNEL_NAME);
        this.getServer().getMessenger().registerIncomingPluginChannel(this, CHANNEL_NAME, new MessageListener());

        this.getServer().getPluginManager().registerEvents(new ChannelListener(playersOnEmber), this);
    }

    @Override
    public void onDisable() {
        this.getServer().getMessenger().unregisterOutgoingPluginChannel(this, CHANNEL_NAME);
        this.getServer().getMessenger().unregisterIncomingPluginChannel(this, CHANNEL_NAME);
    }

    public void sendPacket(Player player, Packet packet) {
        if (!this.isPlayerOnEmber(player.getUniqueId())) {
            return;
        }

        ByteBuf buf = this.getPacketManager().getData(packet);
        try {
            player.sendPluginMessage(this, CHANNEL_NAME, buf.array());
        } finally {
            buf.release();
        }
    }

    public boolean isPlayerOnEmber(UUID uuid) {
        return this.playersOnEmber.contains(uuid);
    }
}
