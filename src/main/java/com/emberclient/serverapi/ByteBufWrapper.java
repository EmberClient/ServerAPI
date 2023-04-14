package com.emberclient.serverapi;


import io.netty.buffer.ByteBuf;

import java.nio.charset.StandardCharsets;

public class ByteBufWrapper {
    private ByteBuf buf;

    public ByteBuf buf() {
        return buf;
    }

    public ByteBufWrapper(ByteBuf buf) {
        this.buf = buf;
    }

    public void writeVarInt(int value) {
        while ((value & -0x80) != 0) {
            this.buf.writeByte(value & 0x7F | 0x80);
            value >>>= 7;
        }

        this.buf.writeByte(value);
    }

    public int readVarInt() {
        int i = 0;
        int j = 0;

        while (true) {
            byte b0 = this.buf.readByte();
            i |= (b0 & 0x7F) << j++ * 7;

            if (j > 5) {
                throw new RuntimeException("VarInt too big");
            }

            if ((b0 & 0x80) != 0x80) {
                break;
            }
        }

        return i;
    }

    public void writeString(String string) {
        byte[] bytes = string.getBytes(StandardCharsets.UTF_8);
        this.writeVarInt(bytes.length);
        this.buf.writeBytes(bytes);
    }

    public String readString() {
        int length = this.readVarInt();
        byte[] bytes = new byte[length];
        this.buf.readBytes(bytes);
        return new String(bytes, StandardCharsets.UTF_8);
    }
}
