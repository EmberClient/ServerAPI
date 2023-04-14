package com.emberclient.serverapi.event;

import com.emberclient.serverapi.packet.impl.attestation.register.AttestationRegisterResult;
import lombok.Getter;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import java.security.spec.X509EncodedKeySpec;

public class EmberAttestationRegisterEvent extends Event {
    @Getter
    private static HandlerList handlerList = new HandlerList();

    @Override
    public HandlerList getHandlers() {
        return handlerList;
    }

    @Getter
    private Player player;

    @Getter
    private AttestationRegisterResult status;

    @Getter
    private X509EncodedKeySpec publicKey;

    public EmberAttestationRegisterEvent(Player player, AttestationRegisterResult status, X509EncodedKeySpec publicKey) {
        super(false);
        this.player = player;
        this.status = status;
        this.publicKey = publicKey;
    }
}
