package com.emberclient.serverapi;

import com.emberclient.serverapi.listener.ChannelListener;
import com.emberclient.serverapi.listener.MessageListener;
import com.emberclient.serverapi.packet.Packet;
import com.emberclient.serverapi.packet.PacketManager;
import io.netty.buffer.ByteBuf;
import lombok.Getter;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public final class ECServerAPI extends JavaPlugin {
    public static final String CHANNEL_NAME = "ember:data";

    @Getter
    private static ECServerAPI instance;

    @Getter
    private final PacketManager packetManager = new PacketManager();

    private final List<UUID> playersOnEmber = new ArrayList<>();

    @Override
    public void onEnable() {
        instance = this;

        this.getServer().getMessenger().registerOutgoingPluginChannel(this, CHANNEL_NAME);
        this.getServer().getMessenger().registerIncomingPluginChannel(this, CHANNEL_NAME, new MessageListener());

        this.getServer().getPluginManager().registerEvents(new ChannelListener(), this);
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

    public void registerPlayer(UUID uuid) {
        this.playersOnEmber.add(uuid);
    }

    public void unregisterPlayer(UUID uuid) {
        this.playersOnEmber.remove(uuid);
    }
}
