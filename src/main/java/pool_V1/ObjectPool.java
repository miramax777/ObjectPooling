package pool_V1;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.nio.ByteBuffer;
import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

public class ObjectPool {

    private final int poolCapacity;
    private List<PriceQuotation> usedObjects = new ArrayList<>();
    private List<PriceQuotation> unusedObjects = new ArrayList<>();
    private ByteBuffer usedObjectsBuffer;
    private ByteBuffer unusedObjectsBuffer;
    private boolean poolLock;

    public ObjectPool() {
        this.poolCapacity = 1;
    }

    public ObjectPool(final int poolCapacity) {
        this.poolCapacity = poolCapacity;
    }

    public final synchronized PriceQuotation acquire() {

        if (poolLock) {
            throw new RuntimeException("Obtaining objects from the pool is not allowed.");
        }

        if (unusedObjects.isEmpty() && usedObjects.size() == poolCapacity) {
            throw new RuntimeException("Object creation limit has been reached. Limit " + poolCapacity);
        }

        try {
            usedObjects = ByteBufferUtil.readBufferToList(usedObjectsBuffer, usedObjects);
            unusedObjects = ByteBufferUtil.readBufferToList(unusedObjectsBuffer, unusedObjects);
        } catch (final IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        final PriceQuotation priceQuotation = unusedObjects.isEmpty()
                ? new PriceQuotation()
                : unusedObjects.iterator().next();

        unusedObjects.remove(priceQuotation);
        usedObjects.add(priceQuotation);

        try {
            rewriteBuffers();
        } catch (final IOException e) {
            e.printStackTrace();
        } catch (final NoSuchMethodException e) {
            e.printStackTrace();
        } catch (final IllegalAccessException e) {
            e.printStackTrace();
        } catch (final InvocationTargetException e) {
            e.printStackTrace();
        }

        return priceQuotation;
    }

    public final synchronized void release(final PriceQuotation priceQuotation) {
        priceQuotation.setToInitialState();

        try {
            usedObjects = ByteBufferUtil.readListFromBuffer(usedObjectsBuffer);
            unusedObjects = ByteBufferUtil.readListFromBuffer(unusedObjectsBuffer);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        usedObjects.remove(priceQuotation);
        unusedObjects.add(priceQuotation);

        try {
            rewriteBuffers();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    public final synchronized void releaseAll() {
        poolLock = true;
    }

    public final synchronized Future<PriceQuotation> releaseAllNow(final Callable<PriceQuotation> callableTask) {
        poolLock = true;

        return new FutureTask<>(callableTask);
    }

    public final int numberOfPresentObject() {
        return usedObjects.size() + unusedObjects.size();
    }

    public final int numberOfFreeObject() {
        return unusedObjects.size();
    }

    private void rewriteBuffers()
            throws IOException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        ByteBufferUtil.cleanBuffer(usedObjectsBuffer);
        ByteBufferUtil.cleanBuffer(unusedObjectsBuffer);
        if (!usedObjects.isEmpty()) {
            final byte[] usedObjectsBytes = ByteBufferUtil.serialize(usedObjects);
            usedObjectsBuffer = ByteBuffer.allocateDirect(usedObjectsBytes.length).put(usedObjectsBytes);
        }

        if (!unusedObjects.isEmpty()) {
            final byte[] unusedObjectsBytes = ByteBufferUtil.serialize(unusedObjects);
            unusedObjectsBuffer = ByteBuffer.allocateDirect(unusedObjectsBytes.length).put(unusedObjectsBytes);
        }
    }
}
