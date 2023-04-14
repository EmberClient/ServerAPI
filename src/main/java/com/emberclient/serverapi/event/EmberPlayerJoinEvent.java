package com.emberclient.serverapi.event;

import lombok.Getter;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;

public class EmberPlayerJoinEvent extends PlayerEvent {
    @Getter private static HandlerList handlerList = new HandlerList();

    @Override
    public HandlerList getHandlers() {
        return handlerList;
    }

    public EmberPlayerJoinEvent(Player player) {
        super(player);
    }
}
