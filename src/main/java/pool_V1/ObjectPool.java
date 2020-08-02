package pool_V1;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.nio.ByteBuffer;
import java.util.*;

public class ObjectPool {

    private final int poolCapacity;
    private List<PriceQuotation> usedObjects = new ArrayList<>();
    private List<PriceQuotation> unusedObjects = new ArrayList<>();
    private ByteBuffer usedObjectsBuffer;
    private ByteBuffer unusedObjectsBuffer;

    public ObjectPool() {
        this.poolCapacity = 1;
    }

    public ObjectPool(final int poolCapacity) {
        this.poolCapacity = poolCapacity;
    }

    public final synchronized PriceQuotation acquire() {

        if (unusedObjects.isEmpty() && usedObjects.size() == poolCapacity) {
            throw new RuntimeException("Object creation limit has been reached. Limit " + poolCapacity);
        }

        try {
            usedObjects = ByteBufferUtil.readBufferToList(usedObjectsBuffer, usedObjects);
            unusedObjects = ByteBufferUtil.readBufferToList(unusedObjectsBuffer, unusedObjects);
        } catch (IOException e) {
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
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        return priceQuotation;
    }

    public final synchronized void release(final PriceQuotation priceQuotation)
            throws IOException, ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        priceQuotation.setToInitialState();

        usedObjects = ByteBufferUtil.readListFromBuffer(usedObjectsBuffer);
        unusedObjects = ByteBufferUtil.readListFromBuffer(unusedObjectsBuffer);

        usedObjects.remove(priceQuotation);
        unusedObjects.add(priceQuotation);

        rewriteBuffers();
    }

    public final synchronized void releaseAll() {

    }

    public final synchronized void releaseAllNow() {

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
