package com.emberclient.serverapi.packet.impl.clientbound.attestation;

import com.emberclient.serverapi.utils.ByteBufWrapper;
import com.emberclient.serverapi.packet.impl.ClientboundPacket;
import org.jetbrains.annotations.NotNull;

public class OutAttestationSign implements ClientboundPacket {
    private byte[] verificationBytes;

    public OutAttestationSign(byte[] verificationBytes) {
        this.verificationBytes = verificationBytes;
    }

    @Override
    public void write(@NotNull ByteBufWrapper buf) {
        buf.writeByteArray(this.verificationBytes);
    }

    @Override
    public int packetId() {
        return 2;
    }
}
