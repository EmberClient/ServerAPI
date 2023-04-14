package com.emberclient.serverapi.packet;

import com.emberclient.serverapi.ByteBufWrapper;

public abstract class Packet {
    public abstract void write(ByteBufWrapper buf);
    public abstract void read(ByteBufWrapper buf);
    public abstract void handle();
    public abstract void attach(Object object);
}
