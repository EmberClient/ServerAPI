package com.emberclient.serverapi.packet;

import com.emberclient.serverapi.ByteBufWrapper;
import com.emberclient.serverapi.packet.impl.attestation.register.InAttestationRegister;
import com.emberclient.serverapi.packet.impl.attestation.register.OutAttestationRegister;
import com.emberclient.serverapi.packet.impl.attestation.sign.InAttestationSign;
import com.emberclient.serverapi.packet.impl.attestation.sign.OutAttestationSign;
import io.netty.buffer.Unpooled;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class PacketManager {
    private HashMap<Integer, Class<? extends Packet>> packets = new HashMap<>();

    public PacketManager() {
        packets.put(1, OutAttestationRegister.class);
        packets.put(1001, InAttestationRegister.class);

        packets.put(2, OutAttestationSign.class);
        packets.put(1002, InAttestationSign.class);
    }

    private int getPacketId(Packet packet) {
        for (int id : packets.keySet()) {
            if (packets.get(id).equals(packet.getClass())) {
                return id;
            }
        }
        return -1;
    }

    public void handle(Player player, ByteBufWrapper buf) {
        int id = buf.readVarInt();
        Class<? extends Packet> packetClass = packets.get(id);
        if (packetClass == null) {
            return;
        }
        try {
            Packet packet = packetClass.newInstance();

            packet.attach(player);
            packet.read(buf);

            packet.handle();
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public ByteBufWrapper getData(Packet packet) {
        ByteBufWrapper buf = new ByteBufWrapper(Unpooled.buffer());
        buf.writeVarInt(getPacketId(packet));

        packet.write(buf);

        return buf;
    }
}
