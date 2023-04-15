package com.emberclient.serverapi.packet;

import com.emberclient.serverapi.utils.ByteBufWrapper;
import com.emberclient.serverapi.packet.impl.ServerboundPacket;
import com.emberclient.serverapi.packet.impl.attestation.register.InAttestationRegister;
import com.emberclient.serverapi.packet.impl.attestation.sign.InAttestationSign;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class PacketManager {
    private final Map<Integer, Supplier<? extends ServerboundPacket>> serverboundPackets = new HashMap<>();

    public PacketManager() {
        register(InAttestationRegister::new);
        register(InAttestationSign::new);
    }

    private void register(Supplier<? extends ServerboundPacket> supplier) {
        ServerboundPacket dummy = supplier.get();
        this.serverboundPackets.put(dummy.packetId(), supplier);
    }

    public void handle(Player player, ByteBufWrapper buf) {
        int id = buf.readVarInt();
        Supplier<? extends ServerboundPacket> packetClass = this.serverboundPackets.get(id);

        if (packetClass == null) {
            return;
        }

        ServerboundPacket packet = packetClass.get();
        packet.read(buf);
        packet.handle(player);
    }
}
