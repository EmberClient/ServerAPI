package com.emberclient.serverapi.listener;

import com.emberclient.serverapi.utils.ByteBufWrapper;
import com.emberclient.serverapi.ECServerAPI;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import org.bukkit.entity.Player;
import org.bukkit.plugin.messaging.PluginMessageListener;

public final class MessageListener implements PluginMessageListener {
    @Override
    public void onPluginMessageReceived(String channel, Player player, byte[] message) {
        ECServerAPI serverAPI = ECServerAPI.getInstance();
        if (!channel.equals(ECServerAPI.CHANNEL_NAME)) {
            return;
        }

        ByteBuf buf = Unpooled.wrappedBuffer(message);
        ByteBufWrapper wrapper = new ByteBufWrapper(buf);

        serverAPI.getPacketManager().handle(player, wrapper);
    }
}
