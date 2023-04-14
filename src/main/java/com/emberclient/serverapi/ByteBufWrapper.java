package com.emberclient.serverapi;

import com.emberclient.serverapi.utils.ForwardingByteBuf;
import io.netty.buffer.ByteBuf;

import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.StringJoiner;

public final class ByteBufWrapper extends ForwardingByteBuf {
    private final ByteBuf delegate;

    public ByteBufWrapper(ByteBuf delegate) {
        this.delegate = delegate;
    }

    @Override
    public ByteBuf delegate() {
        return delegate;
    }

    public void writeVarInt(int value) {
        while ((value & -0x80) != 0) {
            this.writeByte(value & 0x7F | 0x80);
            value >>>= 7;
        }

        this.writeByte(value);
    }

    public int readVarInt() {
        int i = 0;
        int j = 0;

        while (true) {
            byte b0 = this.readByte();
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
        this.writeBytes(bytes);
    }

    public String readString() {
        int length = this.readVarInt();
        byte[] bytes = new byte[length];
        this.readBytes(bytes);
        return new String(bytes, StandardCharsets.UTF_8);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ByteBufWrapper that = (ByteBufWrapper) o;
        return Objects.equals(delegate, that.delegate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(delegate);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", ByteBufWrapper.class.getSimpleName() + "[", "]")
            .add("delegate=" + delegate)
            .toString();
    }
}
