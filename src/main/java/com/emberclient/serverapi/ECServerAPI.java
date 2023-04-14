package com.emberclient.serverapi;

import com.emberclient.serverapi.listener.ChannelListener;
import com.emberclient.serverapi.listener.MessageListener;
import com.emberclient.serverapi.packet.Packet;
import com.emberclient.serverapi.packet.PacketManager;
import lombok.Getter;
import org.bukkit.Bukkit;
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

        getServer().getMessenger().registerOutgoingPluginChannel(this, CHANNEL_NAME);
        getServer().getMessenger().registerIncomingPluginChannel(this, CHANNEL_NAME, new MessageListener());

        getServer().getPluginManager().registerEvents(new ChannelListener(), this);
    }

    @Override
    public void onDisable() {
        getServer().getMessenger().unregisterOutgoingPluginChannel(this, CHANNEL_NAME);
        getServer().getMessenger().unregisterIncomingPluginChannel(this, CHANNEL_NAME);
    }

    public void sendPacket(Player player, Packet packet) {
        if (!isPlayerOnEmber(player.getUniqueId())) {
            return;
        }

        player.sendPluginMessage(this, CHANNEL_NAME, this.getPacketManager().getData(packet).buf().array());
    }

    public boolean isPlayerOnEmber(UUID uuid) {
        return playersOnEmber.contains(uuid);
    }

    public void registerPlayer(UUID uuid) {
        playersOnEmber.add(uuid);
    }

    public void unregisterPlayer(UUID uuid) {
        playersOnEmber.remove(uuid);
    }
}
