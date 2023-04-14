package com.emberclient.serverapi.packet.impl.attestation.sign;

import com.emberclient.serverapi.ByteBufWrapper;
import com.emberclient.serverapi.packet.Packet;

import java.util.Base64;

public class OutAttestationSign extends Packet {
    private byte[] verificationBytes;

    public OutAttestationSign(byte[] verificationBytes) {
        this.verificationBytes = verificationBytes;
    }

    @Override
    public void write(ByteBufWrapper buf) {
        buf.writeString(Base64.getEncoder().encodeToString(this.verificationBytes));
    }

    @Override
    public void read(ByteBufWrapper buf) {

    }

    @Override
    public void handle() {

    }

    @Override
    public void attach(Object object) {

    }
}
