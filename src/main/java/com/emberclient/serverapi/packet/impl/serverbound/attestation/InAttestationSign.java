package com.emberclient.serverapi.packet.impl.serverbound.attestation;

import com.emberclient.serverapi.packet.impl.data.AttestationSignResult;
import com.emberclient.serverapi.utils.ByteBufWrapper;
import com.emberclient.serverapi.event.EmberAttestationSignEvent;
import com.emberclient.serverapi.packet.impl.ServerboundPacket;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class InAttestationSign implements ServerboundPacket {
    private AttestationSignResult status;
    private byte[] signedData;

    @Override
    public void read(@NotNull ByteBufWrapper buf) {
        this.status = buf.readEnum(AttestationSignResult.class);

        if (this.status.equals(AttestationSignResult.SUCCESS)) {
            this.signedData = buf.readByteArray();
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
