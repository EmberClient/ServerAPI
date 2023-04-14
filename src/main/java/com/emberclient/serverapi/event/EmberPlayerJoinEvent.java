package com.emberclient.serverapi.event;

import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;

public class EmberPlayerJoinEvent extends PlayerEvent {
    //<editor-fold desc="<Handler List>" defaultstate="collapsed">
    private static final HandlerList handlers = new HandlerList();

    public static HandlerList getHandlerList() {
        return handlers;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }
    //</editor-fold>

    public EmberPlayerJoinEvent(Player player) {
        super(player);
    }
}
