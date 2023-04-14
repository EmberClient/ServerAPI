package com.emberclient.serverapi.packet.impl.attestation.sign;

import com.emberclient.serverapi.ByteBufWrapper;
import com.emberclient.serverapi.event.EmberAttestationSignEvent;
import com.emberclient.serverapi.packet.Packet;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Base64;

public class InAttestationSign extends Packet {
    private AttestationSignResult status;
    private byte[] signedData;

    @Override
    public void read(ByteBufWrapper buf) {
        this.status = buf.readEnum(AttestationSignResult.class);

        if (this.status.equals(AttestationSignResult.SUCCESS)) {
            String encodedData = buf.readString();
            this.signedData = Base64.getDecoder().decode(encodedData);
        }
    }

    @Override
    public void handle(Player player) {
        Bukkit.getPluginManager().callEvent(new EmberAttestationSignEvent(player, this.status, this.signedData));
    }
}
