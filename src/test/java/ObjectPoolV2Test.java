import org.junit.Test;
import pool_V2.ObjectPoolV2;
import pool_V2.PriceQuotationV2;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

import static org.junit.Assert.assertEquals;

public class ObjectPoolV2Test {
    public final static int THREAD_POOL_SIZE = 5;

    @Test
    public void acquireThreeObjects() {
        final ObjectPoolV2 objectPool = new ObjectPoolV2(3);

        objectPool.acquire();
        objectPool.acquire();
        objectPool.acquire();

        assertEquals(3, objectPool.numberOfPresentObject());
    }

    @Test(expected = RuntimeException.class)
    public void acquireObjectsMoreThanPoolCapacity() {
        final ObjectPoolV2 objectPool = new ObjectPoolV2(3);

        objectPool.acquire();
        objectPool.acquire();
        objectPool.acquire();
        objectPool.acquire();
    }

    @Test
    public void acquireAndReleaseObjects() {
        final ObjectPoolV2 objectPool = new ObjectPoolV2(3);

        objectPool.acquire();
        final PriceQuotationV2 object2 = objectPool.acquire();
        final PriceQuotationV2 object3 = objectPool.acquire();
        objectPool.release(object2);
        objectPool.release(object3);
        objectPool.acquire();
        objectPool.acquire();
    }

    @Test
    public void acquireAndReleaseObjectsAndCheckingFreeObjects() {
        final ObjectPoolV2 objectPool = new ObjectPoolV2(3);

        objectPool.acquire();
        final PriceQuotationV2 object2 = objectPool.acquire();
        final PriceQuotationV2 object3 = objectPool.acquire();
        objectPool.release(object2);
        objectPool.release(object3);

        assertEquals(3, objectPool.numberOfPresentObject());
        assertEquals(3, objectPool.numberOfFreeObject());
    }

    @Test
    public void concurrentAccessToObjects() throws InterruptedException {
        final ExecutorService executorService = Executors.newFixedThreadPool(THREAD_POOL_SIZE);

        long startTime = System.nanoTime();
        final Callable<String> callableTask = () -> {
            final ObjectPoolV2 objectPool = new ObjectPoolV2(10000);
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
