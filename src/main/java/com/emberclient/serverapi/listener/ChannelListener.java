package com.emberclient.serverapi.listener;

import com.emberclient.serverapi.ECServerAPI;
import com.emberclient.serverapi.event.EmberPlayerJoinEvent;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRegisterChannelEvent;
import org.bukkit.event.player.PlayerUnregisterChannelEvent;

public class ChannelListener implements Listener {
    @EventHandler
    public void onRegister(PlayerRegisterChannelEvent event) {
        ECServerAPI serverAPI = ECServerAPI.getInstance();

        if(event.getChannel().equals(ECServerAPI.CHANNEL_NAME)) {
            serverAPI.registerPlayer(event.getPlayer().getUniqueId());
            Bukkit.getPluginManager().callEvent(new EmberPlayerJoinEvent(event.getPlayer()));
        }
    }

    @EventHandler
    public void onUnregister(PlayerUnregisterChannelEvent event) {
        ECServerAPI serverAPI = ECServerAPI.getInstance();

        if(event.getChannel().equals(ECServerAPI.CHANNEL_NAME)) {
            serverAPI.unregisterPlayer(event.getPlayer().getUniqueId());
        }
    }
}
