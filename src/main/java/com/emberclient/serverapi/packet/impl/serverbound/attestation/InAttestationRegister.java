package com.emberclient.serverapi.packet.impl.serverbound.attestation;

import com.emberclient.serverapi.packet.impl.data.AttestationRegisterResult;
import com.emberclient.serverapi.utils.ByteBufWrapper;
import com.emberclient.serverapi.event.EmberAttestationRegisterEvent;
import com.emberclient.serverapi.packet.impl.ServerboundPacket;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.security.spec.X509EncodedKeySpec;

public class InAttestationRegister implements ServerboundPacket {
    private AttestationRegisterResult status;
    private X509EncodedKeySpec publicKey;

    @Override
    public void read(@NotNull ByteBufWrapper buf) {
        this.status = buf.readEnum(AttestationRegisterResult.class);

        if (this.status.equals(AttestationRegisterResult.SUCCESS)) {
            this.publicKey = new X509EncodedKeySpec(buf.readByteArray());
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
