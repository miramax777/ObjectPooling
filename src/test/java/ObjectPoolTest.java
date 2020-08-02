import org.junit.Test;
import pool_V1.ObjectPool;
import pool_V1.PriceQuotation;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

import static org.junit.Assert.assertEquals;

public class ObjectPoolTest {

    @Test
    public void acquireThreeObjects() {
        final ObjectPool objectPool = new ObjectPool(3);

        objectPool.acquire();
        objectPool.acquire();
        objectPool.acquire();

        assertEquals(3, objectPool.numberOfPresentObject());
    }

    @Test(expected = RuntimeException.class)
    public void acquireObjectsMoreThanPoolCapacity() {
        final ObjectPool objectPool = new ObjectPool(3);

        objectPool.acquire();
        objectPool.acquire();
        objectPool.acquire();
        objectPool.acquire();
    }

    @Test
    public void acquireAndReleaseObjects()
            throws IOException, ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        final ObjectPool objectPool = new ObjectPool(3);

        objectPool.acquire();
        final PriceQuotation object2 = objectPool.acquire();
        final PriceQuotation object3 = objectPool.acquire();
        objectPool.release(object2);
        objectPool.release(object3);
        objectPool.acquire();
        objectPool.acquire();
    }

    @Test
    public void acquireAndReleaseObjectsAndCheckingFreeObjects()
            throws IOException, ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        final ObjectPool objectPool = new ObjectPool(3);

        objectPool.acquire();
        final PriceQuotation object2 = objectPool.acquire();
        final PriceQuotation object3 = objectPool.acquire();
        objectPool.release(object2);
        objectPool.release(object3);

        assertEquals(3, objectPool.numberOfPresentObject());
        assertEquals(2, objectPool.numberOfFreeObject());
    }

    @Test
    public void concurrentAccessToObjects() throws InterruptedException {
        final ExecutorService executorService = Executors.newFixedThreadPool(5);

        long startTime = System.nanoTime();
        final Callable<String> callableTask = () -> {
            final ObjectPool objectPool = new ObjectPool(10000);
            for (int i = 0; i < 10000; i++) {
                objectPool.acquire();
            }
            return "Create 10 000 PriceQuotations in Object pool. Object pool contains "
                    + objectPool.numberOfPresentObject() + " it takes "
                    + (System.nanoTime() - startTime) / 1000000L + " ms";
        };

        final List<Callable<String>> callableTasks = new ArrayList<>();
        callableTasks.add(callableTask);
        callableTasks.add(callableTask);
        callableTasks.add(callableTask);
        callableTasks.add(callableTask);
        callableTasks.add(callableTask);

        final List<Future<String>> futures = executorService.invokeAll(callableTasks);
        futures.forEach(f -> {
            try {
                System.out.println(f.get());
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        });
    }
}
