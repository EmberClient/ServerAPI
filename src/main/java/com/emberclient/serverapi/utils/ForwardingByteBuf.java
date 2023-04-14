package com.emberclient.serverapi.utils;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.util.ByteProcessor;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.FileChannel;
import java.nio.channels.GatheringByteChannel;
import java.nio.channels.ScatteringByteChannel;
import java.nio.charset.Charset;

public abstract class ForwardingByteBuf extends ByteBuf implements Forwarding {
    @Override
    public abstract ByteBuf delegate();

    @Override
    public int capacity() {
        return this.delegate().capacity();
    }

    @Override
    public ByteBuf capacity(int newCapacity) {
        return this.delegate().capacity(newCapacity);
    }

    @Override
    public int maxCapacity() {
        return this.delegate().maxCapacity();
    }

    @Override
    public ByteBufAllocator alloc() {
        return this.delegate().alloc();
    }

    @Deprecated
    @Override
    public ByteOrder order() {
        return this.delegate().order();
    }

    @Deprecated
    @Override
    public ByteBuf order(ByteOrder endianness) {
        return this.delegate().order(endianness);
    }

    @Override
    public ByteBuf unwrap() {
        return this.delegate().unwrap();
    }

    @Override
    public boolean isDirect() {
        return this.delegate().isDirect();
    }

    @Override
    public boolean isReadOnly() {
        return this.delegate().isReadOnly();
    }

    @Override
    public ByteBuf asReadOnly() {
        return this.delegate().asReadOnly();
    }

    @Override
    public int readerIndex() {
        return this.delegate().readerIndex();
    }

    @Override
    public ByteBuf readerIndex(int readerIndex) {
        return this.delegate().readerIndex(readerIndex);
    }

    @Override
    public int writerIndex() {
        return this.delegate().writerIndex();
    }

    @Override
    public ByteBuf writerIndex(int writerIndex) {
        return this.delegate().writerIndex(writerIndex);
    }

    @Override
    public ByteBuf setIndex(int readerIndex, int writerIndex) {
        return this.delegate().setIndex(readerIndex, writerIndex);
    }

    @Override
    public int readableBytes() {
        return this.delegate().readableBytes();
    }

    @Override
    public int writableBytes() {
        return this.delegate().writableBytes();
    }

    @Override
    public int maxWritableBytes() {
        return this.delegate().maxWritableBytes();
    }

    @Override
    public int maxFastWritableBytes() {
        return this.delegate().maxFastWritableBytes();
    }

    @Override
    public boolean isReadable() {
        return this.delegate().isReadable();
    }

    @Override
    public boolean isReadable(int size) {
        return this.delegate().isReadable(size);
    }

    @Override
    public boolean isWritable() {
        return this.delegate().isWritable();
    }

    @Override
    public boolean isWritable(int size) {
        return this.delegate().isWritable(size);
    }

    @Override
    public ByteBuf clear() {
        return this.delegate().clear();
    }

    @Override
    public ByteBuf markReaderIndex() {
        return this.delegate().markReaderIndex();
    }

    @Override
    public ByteBuf resetReaderIndex() {
        return this.delegate().resetReaderIndex();
    }

    @Override
    public ByteBuf markWriterIndex() {
        return this.delegate().markWriterIndex();
    }

    @Override
    public ByteBuf resetWriterIndex() {
        return this.delegate().resetWriterIndex();
    }

    @Override
    public ByteBuf discardReadBytes() {
        return this.delegate().discardReadBytes();
    }

    @Override
    public ByteBuf discardSomeReadBytes() {
        return this.delegate().discardSomeReadBytes();
    }

    @Override
    public ByteBuf ensureWritable(int minWritableBytes) {
        return this.delegate().ensureWritable(minWritableBytes);
    }

    @Override
    public int ensureWritable(int minWritableBytes, boolean force) {
        return this.delegate().ensureWritable(minWritableBytes, force);
    }

    @Override
    public boolean getBoolean(int index) {
        return this.delegate().getBoolean(index);
    }

    @Override
    public byte getByte(int index) {
        return this.delegate().getByte(index);
    }

    @Override
    public short getUnsignedByte(int index) {
        return this.delegate().getUnsignedByte(index);
    }

    @Override
    public short getShort(int index) {
        return this.delegate().getShort(index);
    }

    @Override
    public short getShortLE(int index) {
        return this.delegate().getShortLE(index);
    }

    @Override
    public int getUnsignedShort(int index) {
        return this.delegate().getUnsignedShort(index);
    }

    @Override
    public int getUnsignedShortLE(int index) {
        return this.delegate().getUnsignedShortLE(index);
    }

    @Override
    public int getMedium(int index) {
        return this.delegate().getMedium(index);
    }

    @Override
    public int getMediumLE(int index) {
        return this.delegate().getMediumLE(index);
    }

    @Override
    public int getUnsignedMedium(int index) {
        return this.delegate().getUnsignedMedium(index);
    }

    @Override
    public int getUnsignedMediumLE(int index) {
        return this.delegate().getUnsignedMediumLE(index);
    }

    @Override
    public int getInt(int index) {
        return this.delegate().getInt(index);
    }

    @Override
    public int getIntLE(int index) {
        return this.delegate().getIntLE(index);
    }

    @Override
    public long getUnsignedInt(int index) {
        return this.delegate().getUnsignedInt(index);
    }

    @Override
    public long getUnsignedIntLE(int index) {
        return this.delegate().getUnsignedIntLE(index);
    }

    @Override
    public long getLong(int index) {
        return this.delegate().getLong(index);
    }

    @Override
    public long getLongLE(int index) {
        return this.delegate().getLongLE(index);
    }

    @Override
    public char getChar(int index) {
        return this.delegate().getChar(index);
    }

    @Override
    public float getFloat(int index) {
        return this.delegate().getFloat(index);
    }

    @Override
    public float getFloatLE(int index) {
        return this.delegate().getFloatLE(index);
    }

    @Override
    public double getDouble(int index) {
        return this.delegate().getDouble(index);
    }

    @Override
    public double getDoubleLE(int index) {
        return this.delegate().getDoubleLE(index);
    }

    @Override
    public ByteBuf getBytes(int index, ByteBuf dst) {
        return this.delegate().getBytes(index, dst);
    }

    @Override
    public ByteBuf getBytes(int index, ByteBuf dst, int length) {
        return this.delegate().getBytes(index, dst, length);
    }

    @Override
    public ByteBuf getBytes(int index, ByteBuf dst, int dstIndex, int length) {
        return this.delegate().getBytes(index, dst, dstIndex, length);
    }

    @Override
    public ByteBuf getBytes(int index, byte[] dst) {
        return this.delegate().getBytes(index, dst);
    }

    @Override
    public ByteBuf getBytes(int index, byte[] dst, int dstIndex, int length) {
        return this.delegate().getBytes(index, dst, dstIndex, length);
    }

    @Override
    public ByteBuf getBytes(int index, ByteBuffer dst) {
        return this.delegate().getBytes(index, dst);
    }

    @Override
    public ByteBuf getBytes(int index, OutputStream out, int length) throws IOException {
        return this.delegate().getBytes(index, out, length);
    }

    @Override
    public int getBytes(int index, GatheringByteChannel out, int length) throws IOException {
        return this.delegate().getBytes(index, out, length);
    }

    @Override
    public int getBytes(int index, FileChannel out, long position, int length) throws IOException {
        return this.delegate().getBytes(index, out, position, length);
    }

    @Override
    public CharSequence getCharSequence(int index, int length, Charset charset) {
        return this.delegate().getCharSequence(index, length, charset);
    }

    @Override
    public ByteBuf setBoolean(int index, boolean value) {
        return this.delegate().setBoolean(index, value);
    }

    @Override
    public ByteBuf setByte(int index, int value) {
        return this.delegate().setByte(index, value);
    }

    @Override
    public ByteBuf setShort(int index, int value) {
        return this.delegate().setShort(index, value);
    }

    @Override
    public ByteBuf setShortLE(int index, int value) {
        return this.delegate().setShortLE(index, value);
    }

    @Override
    public ByteBuf setMedium(int index, int value) {
        return this.delegate().setMedium(index, value);
    }

    @Override
    public ByteBuf setMediumLE(int index, int value) {
        return this.delegate().setMediumLE(index, value);
    }

    @Override
    public ByteBuf setInt(int index, int value) {
        return this.delegate().setInt(index, value);
    }

    @Override
    public ByteBuf setIntLE(int index, int value) {
        return this.delegate().setIntLE(index, value);
    }

    @Override
    public ByteBuf setLong(int index, long value) {
        return this.delegate().setLong(index, value);
    }

    @Override
    public ByteBuf setLongLE(int index, long value) {
        return this.delegate().setLongLE(index, value);
    }

    @Override
    public ByteBuf setChar(int index, int value) {
        return this.delegate().setChar(index, value);
    }

    @Override
    public ByteBuf setFloat(int index, float value) {
        return this.delegate().setFloat(index, value);
    }

    @Override
    public ByteBuf setFloatLE(int index, float value) {
        return this.delegate().setFloatLE(index, value);
    }

    @Override
    public ByteBuf setDouble(int index, double value) {
        return this.delegate().setDouble(index, value);
    }

    @Override
    public ByteBuf setDoubleLE(int index, double value) {
        return this.delegate().setDoubleLE(index, value);
    }

    @Override
    public ByteBuf setBytes(int index, ByteBuf src) {
        return this.delegate().setBytes(index, src);
    }

    @Override
    public ByteBuf setBytes(int index, ByteBuf src, int length) {
        return this.delegate().setBytes(index, src, length);
    }

    @Override
    public ByteBuf setBytes(int index, ByteBuf src, int srcIndex, int length) {
        return this.delegate().setBytes(index, src, srcIndex, length);
    }

    @Override
    public ByteBuf setBytes(int index, byte[] src) {
        return this.delegate().setBytes(index, src);
    }

    @Override
    public ByteBuf setBytes(int index, byte[] src, int srcIndex, int length) {
        return this.delegate().setBytes(index, src, srcIndex, length);
    }

    @Override
    public ByteBuf setBytes(int index, ByteBuffer src) {
        return this.delegate().setBytes(index, src);
    }

    @Override
    public int setBytes(int index, InputStream in, int length) throws IOException {
        return this.delegate().setBytes(index, in, length);
    }

    @Override
    public int setBytes(int index, ScatteringByteChannel in, int length) throws IOException {
        return this.delegate().setBytes(index, in, length);
    }

    @Override
    public int setBytes(int index, FileChannel in, long position, int length) throws IOException {
        return this.delegate().setBytes(index, in, position, length);
    }

    @Override
    public ByteBuf setZero(int index, int length) {
        return this.delegate().setZero(index, length);
    }

    @Override
    public int setCharSequence(int index, CharSequence sequence, Charset charset) {
        return this.delegate().setCharSequence(index, sequence, charset);
    }

    @Override
    public boolean readBoolean() {
        return this.delegate().readBoolean();
    }

    @Override
    public byte readByte() {
        return this.delegate().readByte();
    }

    @Override
    public short readUnsignedByte() {
        return this.delegate().readUnsignedByte();
    }

    @Override
    public short readShort() {
        return this.delegate().readShort();
    }

    @Override
    public short readShortLE() {
        return this.delegate().readShortLE();
    }

    @Override
    public int readUnsignedShort() {
        return this.delegate().readUnsignedShort();
    }

    @Override
    public int readUnsignedShortLE() {
        return this.delegate().readUnsignedShortLE();
    }

    @Override
    public int readMedium() {
        return this.delegate().readMedium();
    }

    @Override
    public int readMediumLE() {
        return this.delegate().readMediumLE();
    }

    @Override
    public int readUnsignedMedium() {
        return this.delegate().readUnsignedMedium();
    }

    @Override
    public int readUnsignedMediumLE() {
        return this.delegate().readUnsignedMediumLE();
    }

    @Override
    public int readInt() {
        return this.delegate().readInt();
    }

    @Override
    public int readIntLE() {
        return this.delegate().readIntLE();
    }

    @Override
    public long readUnsignedInt() {
        return this.delegate().readUnsignedInt();
    }

    @Override
    public long readUnsignedIntLE() {
        return this.delegate().readUnsignedIntLE();
    }

    @Override
    public long readLong() {
        return this.delegate().readLong();
    }

    @Override
    public long readLongLE() {
        return this.delegate().readLongLE();
    }

    @Override
    public char readChar() {
        return this.delegate().readChar();
    }

    @Override
    public float readFloat() {
        return this.delegate().readFloat();
    }

    @Override
    public float readFloatLE() {
        return this.delegate().readFloatLE();
    }

    @Override
    public double readDouble() {
        return this.delegate().readDouble();
    }

    @Override
    public double readDoubleLE() {
        return this.delegate().readDoubleLE();
    }

    @Override
    public ByteBuf readBytes(int length) {
        return this.delegate().readBytes(length);
    }

    @Override
    public ByteBuf readSlice(int length) {
        return this.delegate().readSlice(length);
    }

    @Override
    public ByteBuf readRetainedSlice(int length) {
        return this.delegate().readRetainedSlice(length);
    }

    @Override
    public ByteBuf readBytes(ByteBuf dst) {
        return this.delegate().readBytes(dst);
    }

    @Override
    public ByteBuf readBytes(ByteBuf dst, int length) {
        return this.delegate().readBytes(dst, length);
    }

    @Override
    public ByteBuf readBytes(ByteBuf dst, int dstIndex, int length) {
        return this.delegate().readBytes(dst, dstIndex, length);
    }

    @Override
    public ByteBuf readBytes(byte[] dst) {
        return this.delegate().readBytes(dst);
    }

    @Override
    public ByteBuf readBytes(byte[] dst, int dstIndex, int length) {
        return this.delegate().readBytes(dst, dstIndex, length);
    }

    @Override
    public ByteBuf readBytes(ByteBuffer dst) {
        return this.delegate().readBytes(dst);
    }

    @Override
    public ByteBuf readBytes(OutputStream out, int length) throws IOException {
        return this.delegate().readBytes(out, length);
    }

    @Override
    public int readBytes(GatheringByteChannel out, int length) throws IOException {
        return this.delegate().readBytes(out, length);
    }

    @Override
    public CharSequence readCharSequence(int length, Charset charset) {
        return this.delegate().readCharSequence(length, charset);
    }

    @Override
    public int readBytes(FileChannel out, long position, int length) throws IOException {
        return this.delegate().readBytes(out, position, length);
    }

    @Override
    public ByteBuf skipBytes(int length) {
        return this.delegate().skipBytes(length);
    }

    @Override
    public ByteBuf writeBoolean(boolean value) {
        return this.delegate().writeBoolean(value);
    }

    @Override
    public ByteBuf writeByte(int value) {
        return this.delegate().writeByte(value);
    }

    @Override
    public ByteBuf writeShort(int value) {
        return this.delegate().writeShort(value);
    }

    @Override
    public ByteBuf writeShortLE(int value) {
        return this.delegate().writeShortLE(value);
    }

    @Override
    public ByteBuf writeMedium(int value) {
        return this.delegate().writeMedium(value);
    }

    @Override
    public ByteBuf writeMediumLE(int value) {
        return this.delegate().writeMediumLE(value);
    }

    @Override
    public ByteBuf writeInt(int value) {
        return this.delegate().writeInt(value);
    }

    @Override
    public ByteBuf writeIntLE(int value) {
        return this.delegate().writeIntLE(value);
    }

    @Override
    public ByteBuf writeLong(long value) {
        return this.delegate().writeLong(value);
    }

    @Override
    public ByteBuf writeLongLE(long value) {
        return this.delegate().writeLongLE(value);
    }

    @Override
    public ByteBuf writeChar(int value) {
        return this.delegate().writeChar(value);
    }

    @Override
    public ByteBuf writeFloat(float value) {
        return this.delegate().writeFloat(value);
    }

    @Override
    public ByteBuf writeFloatLE(float value) {
        return this.delegate().writeFloatLE(value);
    }

    @Override
    public ByteBuf writeDouble(double value) {
        return this.delegate().writeDouble(value);
    }

    @Override
    public ByteBuf writeDoubleLE(double value) {
        return this.delegate().writeDoubleLE(value);
    }

    @Override
    public ByteBuf writeBytes(ByteBuf src) {
        return this.delegate().writeBytes(src);
    }

    @Override
    public ByteBuf writeBytes(ByteBuf src, int length) {
        return this.delegate().writeBytes(src, length);
    }

    @Override
    public ByteBuf writeBytes(ByteBuf src, int srcIndex, int length) {
        return this.delegate().writeBytes(src, srcIndex, length);
    }

    @Override
    public ByteBuf writeBytes(byte[] src) {
        return this.delegate().writeBytes(src);
    }

    @Override
    public ByteBuf writeBytes(byte[] src, int srcIndex, int length) {
        return this.delegate().writeBytes(src, srcIndex, length);
    }

    @Override
    public ByteBuf writeBytes(ByteBuffer src) {
        return this.delegate().writeBytes(src);
    }

    @Override
    public int writeBytes(InputStream in, int length) throws IOException {
        return this.delegate().writeBytes(in, length);
    }

    @Override
    public int writeBytes(ScatteringByteChannel in, int length) throws IOException {
        return this.delegate().writeBytes(in, length);
    }

    @Override
    public int writeBytes(FileChannel in, long position, int length) throws IOException {
        return this.delegate().writeBytes(in, position, length);
    }

    @Override
    public ByteBuf writeZero(int length) {
        return this.delegate().writeZero(length);
    }

    @Override
    public int writeCharSequence(CharSequence sequence, Charset charset) {
        return this.delegate().writeCharSequence(sequence, charset);
    }

    @Override
    public int indexOf(int fromIndex, int toIndex, byte value) {
        return this.delegate().indexOf(fromIndex, toIndex, value);
    }

    @Override
    public int bytesBefore(byte value) {
        return this.delegate().bytesBefore(value);
    }

    @Override
    public int bytesBefore(int length, byte value) {
        return this.delegate().bytesBefore(length, value);
    }

    @Override
    public int bytesBefore(int index, int length, byte value) {
        return this.delegate().bytesBefore(index, length, value);
    }

    @Override
    public int forEachByte(ByteProcessor processor) {
        return this.delegate().forEachByte(processor);
    }

    @Override
    public int forEachByte(int index, int length, ByteProcessor processor) {
        return this.delegate().forEachByte(index, length, processor);
    }

    @Override
    public int forEachByteDesc(ByteProcessor processor) {
        return this.delegate().forEachByteDesc(processor);
    }

    @Override
    public int forEachByteDesc(int index, int length, ByteProcessor processor) {
        return this.delegate().forEachByteDesc(index, length, processor);
    }

    @Override
    public ByteBuf copy() {
        return this.delegate().copy();
    }

    @Override
    public ByteBuf copy(int index, int length) {
        return this.delegate().copy(index, length);
    }

    @Override
    public ByteBuf slice() {
        return this.delegate().slice();
    }

    @Override
    public ByteBuf retainedSlice() {
        return this.delegate().retainedSlice();
    }

    @Override
    public ByteBuf slice(int index, int length) {
        return this.delegate().slice(index, length);
    }

    @Override
    public ByteBuf retainedSlice(int index, int length) {
        return this.delegate().retainedSlice(index, length);
    }

    @Override
    public ByteBuf duplicate() {
        return this.delegate().duplicate();
    }

    @Override
    public ByteBuf retainedDuplicate() {
        return this.delegate().retainedDuplicate();
    }

    @Override
    public int nioBufferCount() {
        return this.delegate().nioBufferCount();
    }

    @Override
    public ByteBuffer nioBuffer() {
        return this.delegate().nioBuffer();
    }

    @Override
    public ByteBuffer nioBuffer(int index, int length) {
        return this.delegate().nioBuffer(index, length);
    }

    @Override
    public ByteBuffer internalNioBuffer(int index, int length) {
        return this.delegate().internalNioBuffer(index, length);
    }

    @Override
    public ByteBuffer[] nioBuffers() {
        return this.delegate().nioBuffers();
    }

    @Override
    public ByteBuffer[] nioBuffers(int index, int length) {
        return this.delegate().nioBuffers(index, length);
    }

    @Override
    public boolean hasArray() {
        return this.delegate().hasArray();
    }

    @Override
    public byte[] array() {
        return this.delegate().array();
    }

    @Override
    public int arrayOffset() {
        return this.delegate().arrayOffset();
    }

    @Override
    public boolean hasMemoryAddress() {
        return this.delegate().hasMemoryAddress();
    }

    @Override
    public long memoryAddress() {
        return this.delegate().memoryAddress();
    }

    @Override
    public String toString(Charset charset) {
        return this.delegate().toString(charset);
    }

    @Override
    public String toString(int index, int length, Charset charset) {
        return this.delegate().toString(index, length, charset);
    }

    @Override
    public int compareTo(ByteBuf buffer) {
        return this.delegate().compareTo(buffer);
    }

    @Override
    public ByteBuf retain(int increment) {
        return this.delegate().retain(increment);
    }

    @Override
    public ByteBuf retain() {
        return this.delegate().retain();
    }

    @Override
    public ByteBuf touch() {
        return this.delegate().touch();
    }

    @Override
    public ByteBuf touch(Object hint) {
        return this.delegate().touch(hint);
    }

    @Override
    public int refCnt() {
        return this.delegate().refCnt();
    }

    @Override
    public boolean release() {
        return this.delegate().release();
    }

    @Override
    public boolean release(int decrement) {
        return this.delegate().release(decrement);
    }
}
