package com.emberclient.serverapi.event;

import com.emberclient.serverapi.packet.impl.attestation.sign.AttestationSignResult;
import lombok.Getter;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;

public class EmberAttestationSignEvent extends PlayerEvent {
    @Getter
    private static HandlerList handlerList = new HandlerList();

    @Override
    public HandlerList getHandlers() {
        return handlerList;
    }

    @Getter
    private Player player;

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
