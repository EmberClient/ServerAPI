package com.emberclient.serverapi.event;

import com.emberclient.serverapi.packet.impl.attestation.register.AttestationRegisterResult;
import lombok.Getter;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;

import java.security.spec.X509EncodedKeySpec;

public class EmberAttestationRegisterEvent extends PlayerEvent {
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
    private AttestationRegisterResult status;

    @Getter
    private X509EncodedKeySpec publicKey;

    public EmberAttestationRegisterEvent(Player player, AttestationRegisterResult status, X509EncodedKeySpec publicKey) {
        super(player);
        this.player = player;
        this.status = status;
        this.publicKey = publicKey;
    }
}
