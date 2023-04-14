package com.emberclient.serverapi.listener;

import com.emberclient.serverapi.ECServerAPI;
import com.emberclient.serverapi.event.EmberPlayerJoinEvent;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRegisterChannelEvent;
import org.bukkit.event.player.PlayerUnregisterChannelEvent;

import java.util.Set;
import java.util.UUID;

public class ChannelListener implements Listener {
    private final Set<UUID> trackedPlayers;

    public ChannelListener(Set<UUID> trackedPlayers) {
        this.trackedPlayers = trackedPlayers;
    }

    @EventHandler
    public void onRegister(PlayerRegisterChannelEvent event) {
        if (event.getChannel().equals(ECServerAPI.CHANNEL_NAME)) {
            this.trackedPlayers.add(event.getPlayer().getUniqueId());
            Bukkit.getPluginManager().callEvent(new EmberPlayerJoinEvent(event.getPlayer()));
        }
    }

    @EventHandler
    public void onUnregister(PlayerUnregisterChannelEvent event) {
        if (event.getChannel().equals(ECServerAPI.CHANNEL_NAME)) {
            this.trackedPlayers.remove(event.getPlayer().getUniqueId());
        }
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        this.trackedPlayers.remove(event.getPlayer().getUniqueId());
    }
}
