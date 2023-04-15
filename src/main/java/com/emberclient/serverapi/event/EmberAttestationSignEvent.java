package com.emberclient.serverapi.event;

import com.emberclient.serverapi.packet.impl.data.AttestationSignResult;
import lombok.Getter;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;

public class EmberAttestationSignEvent extends PlayerEvent {
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

    @Getter
    private AttestationSignResult status;

    @Getter
    private byte[] signedData;

    public EmberAttestationSignEvent(Player player, AttestationSignResult status, byte[] signedData) {
        super(player);
        this.player = player;
        this.status = status;
        this.signedData = signedData;
    }
}
