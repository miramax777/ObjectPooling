package pool_V1;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.ByteBuffer;
import java.util.LinkedList;
import java.util.List;

public class ByteBufferUtil {

    public static List<PriceQuotation> readListFromBuffer(final ByteBuffer buffer)
            throws IOException, ClassNotFoundException {
        if (buffer == null){
            return new LinkedList<>();
        }

        int bufferLoadCapacity = buffer.capacity();
        byte[] objectBytes = new byte[bufferLoadCapacity];
        if (buffer.position() != 0) {
            buffer.rewind();
        }

        for (int i = 0; i < bufferLoadCapacity; i++) {
            objectBytes[i] = buffer.get();
        }

        return deserialize(objectBytes);
    }

    public static List<PriceQuotation> readBufferToList(final ByteBuffer buffer, final List<PriceQuotation> list)
            throws IOException, ClassNotFoundException {
        return buffer != null ? ByteBufferUtil.readListFromBuffer(buffer) : list;
    }

    public static void cleanBuffer(final ByteBuffer cleanableBuffer) throws
            NoSuchMethodException,
            InvocationTargetException,
            IllegalAccessException {

        if (cleanableBuffer == null) {
            return;
        }

        if (!cleanableBuffer.isDirect()) {
            throw new RuntimeException("Buffer isn't direct!");
        }

        final Method cleanerMethod = cleanableBuffer.getClass().getMethod("cleaner");
        cleanerMethod.setAccessible(true);
        Object cleaner = cleanerMethod.invoke(cleanableBuffer);
        Method cleanMethod = cleaner.getClass().getMethod("clean");
        cleanMethod.setAccessible(true);
        cleanMethod.invoke(cleaner);
    }

    public static byte[] serialize(final List<PriceQuotation> obj) throws IOException {
        final ByteArrayOutputStream arrayOutputStream = new ByteArrayOutputStream();
        final ObjectOutputStream objectOutputStream = new ObjectOutputStream(arrayOutputStream);
        objectOutputStream.writeObject(obj);
        objectOutputStream.flush();
        return arrayOutputStream.toByteArray();
    }

    public static List<PriceQuotation> deserialize(final byte[] bytes) throws ClassNotFoundException, IOException {
        return (List<PriceQuotation>) new ObjectInputStream(new ByteArrayInputStream(bytes)).readObject();
    }
}
