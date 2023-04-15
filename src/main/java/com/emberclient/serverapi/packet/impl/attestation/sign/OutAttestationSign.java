package com.emberclient.serverapi.packet.impl.attestation.sign;

import com.emberclient.serverapi.utils.ByteBufWrapper;
import com.emberclient.serverapi.packet.impl.ClientboundPacket;
import org.jetbrains.annotations.NotNull;

import java.util.Base64;

public class OutAttestationSign implements ClientboundPacket {
    private byte[] verificationBytes;

    public OutAttestationSign(byte[] verificationBytes) {
        this.verificationBytes = verificationBytes;
    }

    @Override
    public void write(@NotNull ByteBufWrapper buf) {
        buf.writeString(Base64.getEncoder().encodeToString(this.verificationBytes));
    }

    @Override
    public int packetId() {
        return 2;
    }
}
