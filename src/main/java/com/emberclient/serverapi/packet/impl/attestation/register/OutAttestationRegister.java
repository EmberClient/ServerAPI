package com.emberclient.serverapi.packet.impl.attestation.register;

import com.emberclient.serverapi.packet.impl.ClientboundPacket;

public class OutAttestationRegister implements ClientboundPacket {
    @Override
    public int packetId() {
        return 1;
    }
}
