package com.emberclient.serverapi.packet.impl.attestation.sign;

import com.emberclient.serverapi.utils.ByteBufWrapper;
import com.emberclient.serverapi.event.EmberAttestationSignEvent;
import com.emberclient.serverapi.packet.impl.ServerboundPacket;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Base64;

public class InAttestationSign implements ServerboundPacket {
    private AttestationSignResult status;
    private byte[] signedData;

    @Override
    public void read(@NotNull ByteBufWrapper buf) {
        this.status = buf.readEnum(AttestationSignResult.class);

        if (this.status.equals(AttestationSignResult.SUCCESS)) {
            String encodedData = buf.readString();
            this.signedData = Base64.getDecoder().decode(encodedData);
        }
    }

    @Override
    public void handle(@NotNull Player player) {
        Bukkit.getPluginManager().callEvent(new EmberAttestationSignEvent(player, this.status, this.signedData));
    }

    @Override
    public int packetId() {
        return 1002;
    }
}
