package com.emberclient.serverapi;

import com.emberclient.serverapi.utils.ForwardingByteBuf;
import io.netty.buffer.ByteBuf;
import io.netty.handler.codec.DecoderException;
import io.netty.handler.codec.EncoderException;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.function.*;

/**
 * A simple {@link ByteBuf} wrapper with useful extension methods.
 *
 * @see ByteBuf
 */
public final class ByteBufWrapper extends ForwardingByteBuf {
    public static final int MAX_VAR_INT_LENGTH = 5;
    public static final int MAX_VAR_LONG_LENGTH = 10;
    public static final int DEFAULT_MAX_STRING_LENGTH = 32767;

    private static final int SEGMENT_BITS = 0x7F;
    private static final int CONTINUE_BIT = 0x80;

    private static final RuntimeException VAR_INT_TOO_BIG = new RuntimeException("VarInt too big");
    private static final RuntimeException VAR_LONG_TOO_BIG = new RuntimeException("VarLong too big");
    private static final DecoderException WEIRD_STRING = new DecoderException("The received encoded string buffer length is less than zero! Weird string!");

    private final ByteBuf delegate;

    public ByteBufWrapper(ByteBuf delegate) {
        this.delegate = delegate;
    }

    /**
     * Calculates the given variable integer's size.
     *
     * @param value the variable integer
     * @return the size of the variable integer
     */
    public static int calculateVarIntSize(int value) {
        for (int i = 1; i < MAX_VAR_INT_LENGTH; ++i) {
            if ((value & -1 << i * 7) == 0) {
                return i;
            }
        }

        return MAX_VAR_INT_LENGTH;
    }

    /**
     * Calculates the given variable long's size.
     *
     * @param value the variable long
     * @return the size of the variable long
     */
    public static int calculateVarLongSize(long value) {
        for (int i = 1; i < MAX_VAR_LONG_LENGTH; ++i) {
            if ((value & -1L << i * 7) == 0L) {
                return i;
            }
        }

        return MAX_VAR_LONG_LENGTH;
    }

    /**
     * Writes an optional object to the buffer.
     *
     * @param object          the object
     * @param entrySerializer the serializer
     * @param <T>             the type of the object
     */
    public <T> void writeOptional(@Nullable T object, @NotNull BiConsumer<ByteBuf, T> entrySerializer) {
        if (object != null) {
            this.writeBoolean(true);
            entrySerializer.accept(this, object);
        } else {
            this.writeBoolean(false);
        }
    }

    /**
     * Reads an optional object to the buffer.
     *
     * @param entryParser the parser
     * @param <T>         the type of the object
     */
    public <T> T readOptional(@NotNull Function<ByteBuf, T> entryParser) {
        if (this.readBoolean()) {
            return entryParser.apply(this);
        } else {
            return null;
        }
    }

    /**
     * Iterates on the buffer.
     *
     * @param consumer the consumer
     */
    public void forEachInCollection(Consumer<ByteBuf> consumer) {
        int count = this.readVarInt();

        for (int j = 0; j < count; ++j) {
            consumer.accept(this);
        }
    }

    /**
     * Writes the given collection to the buffer.
     *
     * @param <T>             the list's entry type
     * @param entrySerializer the serializer
     * @param collection      the collection
     */
    public <T> void writeCollection(@NotNull Collection<T> collection, @NotNull BiConsumer<ByteBuf, T> entrySerializer) {
        this.writeVarInt(collection.size());

        for (T object : collection) {
            entrySerializer.accept(this, object);
        }
    }

    /**
     * Reads a collection from the buffer.
     *
     * @param <T>               the collection's entry type
     * @param <C>               the collection's type
     * @param collectionFactory a factory that creates a collection with a given size
     * @param entryParser       a parser that parses each entry for the collection given this buf
     * @return the new collection
     */
    @NotNull
    public <T, C extends Collection<T>> C readCollection(@NotNull IntFunction<C> collectionFactory, @NotNull Function<ByteBuf, T> entryParser) {
        int size = this.readVarInt();
        C collection = collectionFactory.apply(size);

        for (int i = 0; i < size; ++i) {
            collection.add(entryParser.apply(this));
        }

        return collection;
    }

    /**
     * Writes the given map to the buffer.
     *
     * @param <K>             the key type
     * @param <V>             the value type
     * @param map             the map
     * @param keySerializer   the key serializer
     * @param valueSerializer the value serializer
     */
    public <K, V> void writeMap(@NotNull Map<K, V> map, @NotNull BiConsumer<ByteBuf, K> keySerializer, @NotNull BiConsumer<ByteBuf, V> valueSerializer) {
        this.writeVarInt(map.size());
        map.forEach((key, value) -> {
            keySerializer.accept(this, key);
            valueSerializer.accept(this, value);
        });
    }

    /**
     * Reads a map from the buffer.
     *
     * @param <K>         the key type
     * @param <V>         the value type
     * @param <M>         the map type
     * @param mapFactory  the map factory
     * @param keyParser   the key deserializer
     * @param valueParser the value deserializer
     * @return the new map
     */
    public <K, V, M extends Map<K, V>> M readMap(@NotNull IntFunction<M> mapFactory, @NotNull Function<ByteBuf, K> keyParser, @NotNull Function<ByteBuf, V> valueParser) {
        int size = this.readVarInt();
        M map = mapFactory.apply(size);

        for (int j = 0; j < size; ++j) {
            K k = keyParser.apply(this);
            V v = valueParser.apply(this);
            map.put(k, v);
        }

        return map;
    }

    /**
     * Writes the given byte array to the buffer.
     *
     * @param array the array
     */
    public void writeByteArray(byte @NotNull [] array) {
        this.writeVarInt(array.length);
        this.writeBytes(array);
    }

    /**
     * Reads a byte array from the buffer, with an expected max size of {@link Short#MAX_VALUE}.
     *
     * @return the array
     */
    public byte @NotNull [] readByteArray() {
        return readByteArray(Short.MAX_VALUE);
    }

    /**
     * Reads a byte array from the buffer.
     *
     * @param limit the limit
     * @return the array
     * @throws DecoderException if the length of the array is larger than expected
     */
    public byte @NotNull [] readByteArray(int limit) {
        int len = this.readVarInt();

        if (len > limit)
            throw new DecoderException("The received byte array longer than allowed " + len + " > " + limit);

        byte[] bytes = new byte[len];
        this.readBytes(bytes);
        return bytes;
    }

    /**
     * Writes the given variable integer array to the buffer.
     *
     * @param array the array
     */
    public void writeVarIntArray(int @NotNull [] array) {
        this.writeVarInt(array.length);
        for (int i : array) {
            this.writeVarInt(i);
        }
    }

    /**
     * Reads a variable integer array from the buffer, with an expected max size of {@link Short#MAX_VALUE}.
     *
     * @return the array
     */
    public int @NotNull [] readVarIntArray() {
        return this.readVarIntArray(Short.MAX_VALUE);
    }

    /**
     * Reads a variable integer array from the buffer.
     *
     * @param limit the limit
     * @return the array
     * @throws DecoderException if the length of the array is larger than expected
     */
    public int @NotNull [] readVarIntArray(int limit) {
        int len = this.readVarInt();

        if (len > limit)
            throw new DecoderException("The received varint array longer than allowed " + len + " > " + limit);

        int[] array = new int[len];
        for (int i = 0; i < array.length; i++) {
            array[i] = this.readVarInt();
        }
        return array;
    }

    /**
     * Writes the given integer array to the buffer.
     *
     * @param array the array
     */
    public void writeIntArray(int @NotNull [] array) {
        this.writeVarInt(array.length);
        for (int i : array) {
            this.writeInt(i);
        }
    }

    /**
     * Reads an integer array from the buffer, with an expected max size of {@link Short#MAX_VALUE}.
     *
     * @return the array
     */
    public int @NotNull [] readIntArray() {
        return this.readIntArray(Short.MAX_VALUE);
    }

    /**
     * Reads an integer array from the buffer.
     *
     * @param limit the limit
     * @return the array
     * @throws DecoderException if the length of the array is larger than expected
     */
    public int @NotNull [] readIntArray(int limit) {
        int len = this.readVarInt();

        if (len > limit)
            throw new DecoderException("The received integer array longer than allowed " + len + " > " + limit);

        int[] array = new int[len];
        for (int i = 0; i < array.length; i++) {
            array[i] = this.readInt();
        }
        return array;
    }

    /**
     * Writes the given {@link Enum} to the buffer.
     *
     * @param e the enum
     */
    public void writeEnum(@NotNull Enum<?> e) {
        this.writeVarInt(e.ordinal());
    }

    /**
     * Reads an {@link Enum} from the buffer.
     *
     * @return the tag
     */
    @NotNull
    public <T extends Enum<T>> T readEnum(@NotNull Class<T> clazz) {
        return clazz.getEnumConstants()[this.readVarInt()];
    }

    /**
     * Writes the given variable integer to the buffer.
     *
     * @param value the variable integer
     */
    public void writeVarInt(int value) {
        while ((value & -CONTINUE_BIT) != 0) {
            this.writeByte(value & SEGMENT_BITS | CONTINUE_BIT);
            value >>>= 7;
        }

        this.writeByte(value);
    }

    /**
     * Reads a variable integer from the buffer.
     *
     * @return the variable integer
     */
    public int readVarInt() {
        int number = 0;
        int chunk = 0;

        byte currentByte;

        do {
            currentByte = this.readByte();
            number |= (currentByte & SEGMENT_BITS) << chunk++ * 7;
            if (chunk > 5) {
                throw VAR_INT_TOO_BIG;
            }
        } while ((currentByte & CONTINUE_BIT) == CONTINUE_BIT);

        return number;
    }

    /**
     * Writes the given variable long to the buffer.
     *
     * @param value the variable long
     */
    public void writeVarLong(long value) {
        while ((value & -SEGMENT_BITS) != 0L) {
            this.writeByte((int) (value & SEGMENT_BITS) | CONTINUE_BIT);
            value >>>= 7;
        }

        this.writeByte((int) value);
    }

    /**
     * Reads a variable long from the buffer.
     *
     * @return the variable integer
     */
    public long readVarLong() {
        long number = 0L;
        int chunk = 0;

        byte currentByte;

        do {
            currentByte = this.readByte();
            number |= (long) (currentByte & SEGMENT_BITS) << chunk++ * 7;
            if (chunk > 10) {
                throw VAR_LONG_TOO_BIG;
            }
        } while ((currentByte & CONTINUE_BIT) == CONTINUE_BIT);

        return number;
    }

    /**
     * Writes the given {@link UUID} to the buffer.
     *
     * @param uuid the uuid
     */
    public void writeUuid(@NotNull UUID uuid) {
        this.writeLong(uuid.getMostSignificantBits());
        this.writeLong(uuid.getLeastSignificantBits());
    }

    /**
     * Reads an {@link UUID} from the buffer.
     *
     * @return the tag
     */
    @NotNull
    public UUID readUuid() {
        return new UUID(readLong(), readLong());
    }

    /**
     * Writes the given {@link String} to the buffer with a max size of 32767.
     *
     * @param s the string
     * @throws DecoderException if the length of the string is longer than {@link Short#MAX_VALUE}
     */
    public void writeString(@NotNull String s) {
        this.writeString(s, DEFAULT_MAX_STRING_LENGTH);
    }

    /**
     * Writes the given {@link String} to the buffer.
     *
     * @param s             the string
     * @param allowedLength the allowed length
     * @throws DecoderException if the length of the string is longer than {@code allowedLength}
     */
    public void writeString(@NotNull String s, int allowedLength) {
        byte[] bytes = s.getBytes(StandardCharsets.UTF_8);

        if (bytes.length > allowedLength) {
            throw new EncoderException("String too big ('" + s + "' was " + bytes.length + " bytes encoded, max " + allowedLength + ")");
        } else {
            this.writeVarInt(bytes.length);
            this.writeBytes(bytes);
        }
    }

    /**
     * Reads a {@link String} from the buffer, with a max size of 32767.
     *
     * @return the result
     * @throws DecoderException if the buffer is bigger than the expected,
     *                          or the length of the string is bigger than the expected
     */
    @NotNull
    public String readString() {
        return this.readString(DEFAULT_MAX_STRING_LENGTH);
    }

    /**
     * Reads a {@link String} from the buffer.
     *
     * @param expectedLength the expected length of the result
     * @return the result
     * @throws DecoderException if the buffer is bigger than the expected,
     *                          or the length of the string is bigger than the expected
     */
    @NotNull
    public String readString(int expectedLength) {
        int length = this.readVarInt();

        if (length > expectedLength * 4) {
            throw new DecoderException("The received encoded string buffer length is longer than maximum allowed (" + length + " > " + expectedLength * 4 + ")");
        } else if (length < 0) {
            throw WEIRD_STRING;
        } else {
            String s = new String(this.readBytes(length).array(), StandardCharsets.UTF_8);

            if (s.length() > expectedLength) {
                throw new DecoderException("The received string length is longer than maximum allowed (" + length + " > " + expectedLength + ")");
            } else {
                return s;
            }
        }
    }

    @Override
    public ByteBuf delegate() {
        return delegate;
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
