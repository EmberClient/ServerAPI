package com.emberclient.serverapi.packet;

import com.emberclient.serverapi.ByteBufWrapper;
import com.emberclient.serverapi.packet.impl.attestation.register.InAttestationRegister;
import com.emberclient.serverapi.packet.impl.attestation.register.OutAttestationRegister;
import com.emberclient.serverapi.packet.impl.attestation.sign.InAttestationSign;
import com.emberclient.serverapi.packet.impl.attestation.sign.OutAttestationSign;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import io.netty.buffer.Unpooled;
import org.bukkit.entity.Player;

public class PacketManager {
    private final BiMap<Integer, Class<? extends Packet>> packets = HashBiMap.create();

    public PacketManager() {
        this.packets.put(1, OutAttestationRegister.class);
        this.packets.put(1001, InAttestationRegister.class);

        this.packets.put(2, OutAttestationSign.class);
        this.packets.put(1002, InAttestationSign.class);
    }

    private int getPacketId(Packet packet) {
        return this.packets.inverse().get(packet.getClass());
    }

    public void handle(Player player, ByteBufWrapper buf) {
        int id = buf.readVarInt();
        Class<? extends Packet> packetClass = this.packets.get(id);

        if (packetClass == null) {
            return;
        }

        try {
            Packet packet = packetClass.newInstance();

            packet.read(buf);
            packet.handle(player);
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public ByteBufWrapper getData(Packet packet) {
        ByteBufWrapper buf = new ByteBufWrapper(Unpooled.buffer());
        buf.writeVarInt(this.getPacketId(packet));
        packet.write(buf);
        return buf;
    }
}
