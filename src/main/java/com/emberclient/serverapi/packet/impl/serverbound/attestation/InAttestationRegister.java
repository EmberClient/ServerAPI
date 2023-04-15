package com.emberclient.serverapi.packet.impl.serverbound.attestation;

import com.emberclient.serverapi.packet.impl.data.AttestationRegisterResult;
import com.emberclient.serverapi.utils.ByteBufWrapper;
import com.emberclient.serverapi.event.EmberAttestationRegisterEvent;
import com.emberclient.serverapi.packet.impl.ServerboundPacket;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

public class InAttestationRegister implements ServerboundPacket {
    private AttestationRegisterResult status;
    private X509EncodedKeySpec publicKey;

    @Override
    public void read(@NotNull ByteBufWrapper buf) {
        this.status = buf.readEnum(AttestationRegisterResult.class);

        if (this.status.equals(AttestationRegisterResult.SUCCESS)) {
            String publicKey = buf.readString();
            this.publicKey = new X509EncodedKeySpec(Base64.getDecoder().decode(publicKey));
        }
    }

    @Override
    public void handle(@NotNull Player player) {
        Bukkit.getPluginManager().callEvent(new EmberAttestationRegisterEvent(player, this.status, this.publicKey));
    }

    @Override
    public int packetId() {
        return 1001;
    }
}
