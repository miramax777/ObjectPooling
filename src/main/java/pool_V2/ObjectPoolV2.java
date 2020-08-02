package pool_V2;

import pool_V1.ObjectState;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.ByteBuffer;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import static pool_V1.ObjectState.FREE;
import static pool_V1.ObjectState.IN_USE;

public final class ObjectPoolV2 {

    private int poolCapacity;
    private ConcurrentHashMap<PriceQuotationV2, ObjectState> objectsStore = new ConcurrentHashMap<>(poolCapacity);
    private ByteBuffer directBuffer;
    private boolean poolLock;

    public ObjectPoolV2() {
        this.poolCapacity = 1;
    }

    public ObjectPoolV2(int poolCapacity) {
        this.poolCapacity = poolCapacity;
    }

    public final PriceQuotationV2 acquire() {
        if (poolLock) {
            throw new RuntimeException("Obtaining objects from the pool is not allowed.");
        }

        if (directBuffer != null) {
            objectsStore = readBuffer(directBuffer).orElseThrow(() -> new RuntimeException("Error reading buffer."));
        }

        if (objectsStore.size() == poolCapacity & !objectsStore.containsValue(FREE)) {
            throw new RuntimeException("Object creation limit has been reached. Limit " + poolCapacity);
        }

        if (!objectsStore.containsValue(FREE)) {
            final PriceQuotationV2 priceQuotation = new PriceQuotationV2();
            objectsStore.put(priceQuotation, IN_USE);
            rewriteBuffer();

            return priceQuotation;
        }

        final PriceQuotationV2 acquiredObject = objectsStore.entrySet().stream()
                .filter(obj -> obj.getValue().equals(FREE))
                .findAny()
                .orElseThrow(NoSuchElementException::new)
                .getKey();

        objectsStore.replace(acquiredObject, IN_USE);
        rewriteBuffer();

        return acquiredObject;
    }

    public final void release(final PriceQuotationV2 priceQuotation) {
        priceQuotation.setToInitial();
        objectsStore.replace(priceQuotation, ObjectState.FREE);
        rewriteBuffer();
    }

    public final synchronized void releaseAll() {
        poolLock = true;
    }

    public final int poolCapacity() {
        return poolCapacity;
    }

    public final int numberOfPresentObject() {
        return objectsStore.size();
    }

    public final int numberOfFreeObject() {
        return (int)objectsStore.entrySet().stream()
                .filter(obj -> obj.getValue().equals(FREE))
                .count();
    }

    private void rewriteBuffer() {
        byte[] bufferedObjectStore = new byte[0];
        try {
            bufferedObjectStore = serialize(objectsStore);
            cleanBuffer(this.directBuffer);
        } catch (final IOException e) {
            System.out.println("Objects store serializing error.");
        }

        this.directBuffer = ByteBuffer.allocateDirect(bufferedObjectStore.length).put(bufferedObjectStore);
    }

    private Optional<ConcurrentHashMap<PriceQuotationV2, ObjectState>> readBuffer(final ByteBuffer buffer) {
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

    private void cleanBuffer(final ByteBuffer cleanableBuffer) {
        if (cleanableBuffer == null) {
            return;
        }

        if (!cleanableBuffer.isDirect()) {
            throw new RuntimeException("Buffer isn't direct!");
        }

        try {
            final Method cleanerMethod = cleanableBuffer.getClass().getMethod("cleaner");
            cleanerMethod.setAccessible(true);
            final Object cleaner = cleanerMethod.invoke(cleanableBuffer);
            final Method cleanMethod = cleaner.getClass().getMethod("clean");
            cleanMethod.setAccessible(true);
            cleanMethod.invoke(cleaner);
        } catch (final IllegalAccessException e) {
            System.out.println("Error when try cleaning buffer.");
        } catch (final InvocationTargetException e) {
            System.out.println("Error when try cleaning buffer. ");
        } catch (final NoSuchMethodException e) {
            System.out.println("Error when try cleaning buffer.  ");
        }
    }

    private byte[] serialize(final ConcurrentHashMap<PriceQuotationV2, ObjectState> obj) throws IOException {
        final ByteArrayOutputStream arrayOutputStream = new ByteArrayOutputStream();
        final ObjectOutputStream objectOutputStream = new ObjectOutputStream(arrayOutputStream);
        objectOutputStream.writeObject(obj);
        objectOutputStream.flush();
        return arrayOutputStream.toByteArray();
    }

    @SuppressWarnings("unchecked")
    private Optional<ConcurrentHashMap<PriceQuotationV2, ObjectState>> deserialize(final byte[] objectBytes) {
        try {
            return Optional.of((ConcurrentHashMap<PriceQuotationV2, ObjectState>) new ObjectInputStream(new ByteArrayInputStream(objectBytes)).readObject());
        } catch (final IOException e) {
            System.out.println("Byte buffer of objects store deserialize error.");
        } catch (final ClassNotFoundException e) {
            System.out.println("Byte buffer of objects store deserialize error. ");
        }

        return Optional.empty();
    }
}
