package com.emberclient.serverapi.packet.impl.attestation.register;

import com.emberclient.serverapi.ByteBufWrapper;
import com.emberclient.serverapi.event.EmberAttestationRegisterEvent;
import com.emberclient.serverapi.packet.Packet;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

public class InAttestationRegister extends Packet {
    private AttestationRegisterResult status;
    private X509EncodedKeySpec publicKey;

    @Override
    public void read(ByteBufWrapper buf) {
        this.status = AttestationRegisterResult.fromCode(buf.readVarInt());

        if (this.status.equals(AttestationRegisterResult.SUCCESS)) {
            String publicKey = buf.readString();
            this.publicKey = new X509EncodedKeySpec(Base64.getDecoder().decode(publicKey));
        }
    }

    public void handle(Player player) {
        Bukkit.getPluginManager().callEvent(new EmberAttestationRegisterEvent(player, this.status, this.publicKey));
    }
}
