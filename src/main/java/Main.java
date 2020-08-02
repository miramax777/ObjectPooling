import pool_V1.ObjectPool;
import pool_V1.PriceQuotation;
import pool_V2.*;
import pool_V2.PriceQuotationV2;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.time.Instant;

public class Main {
    public static void main(String[] args) throws InterruptedException, CloneNotSupportedException, ClassNotFoundException, NoSuchMethodException, InvocationTargetException, IllegalAccessException, IOException {

//        for(;;) {
//            ObjectPoolV2 pool = new ObjectPoolV2(10);
//
//            PriceQuotationV2 object1 = pool.acquire();
//            object1.setDateAndTime(Instant.now().getEpochSecond());
//            object1.setPurchasePrice(34.53d);
//            object1.setSellingPrice(34.53d);
//            object1.setNameOfTradingInstrument("test1".toCharArray());
//
//            PriceQuotationV2 object2 = pool.acquire();
//            object2.setDateAndTime(Instant.now().getEpochSecond());
//            object2.setPurchasePrice(34.53d);
//            object2.setSellingPrice(34.53d);
//            object2.setNameOfTradingInstrument("test2".toCharArray());
//
//            PriceQuotationV2 object3 = pool.acquire();
//            object3.setDateAndTime(Instant.now().getEpochSecond());
//            object3.setPurchasePrice(34.53d);
//            object3.setSellingPrice(34.53d);
//            object3.setNameOfTradingInstrument("test3".toCharArray());
//
//            PriceQuotationV2 object4 = pool.acquire();
//            object4.setDateAndTime(Instant.now().getEpochSecond());
//            object4.setPurchasePrice(34.53d);
//            object4.setSellingPrice(34.53d);
//            object4.setNameOfTradingInstrument("test4".toCharArray());
//
//            PriceQuotationV2 object5 = pool.acquire();
//            object5.setDateAndTime(Instant.now().getEpochSecond());
//            object5.setPurchasePrice(34.53d);
//            object5.setSellingPrice(34.53d);
//            object5.setNameOfTradingInstrument("test5".toCharArray());
//
//            PriceQuotationV2 object6 = pool.acquire();
//            object6.setDateAndTime(Instant.now().getEpochSecond());
//            object6.setPurchasePrice(34.53d);
//            object6.setSellingPrice(34.53d);
//            object6.setNameOfTradingInstrument("test6".toCharArray());
//
//            PriceQuotationV2 object7 = pool.acquire();
//            object7.setDateAndTime(Instant.now().getEpochSecond());
//            object7.setPurchasePrice(34.53d);
//            object7.setSellingPrice(34.53d);
//            object7.setNameOfTradingInstrument("test7".toCharArray());
//
//            PriceQuotationV2 object8 = pool.acquire();
//            object8.setDateAndTime(Instant.now().getEpochSecond());
//            object8.setPurchasePrice(34.53d);
//            object8.setSellingPrice(34.53d);
//            object8.setNameOfTradingInstrument("test8".toCharArray());
//
//            PriceQuotationV2 object9 = pool.acquire();
//            object9.setDateAndTime(Instant.now().getEpochSecond());
//            object9.setPurchasePrice(34.53d);
//            object9.setSellingPrice(34.53d);
//            object9.setNameOfTradingInstrument("test9".toCharArray());
//
//            PriceQuotationV2 object10 = pool.acquire();
//            object10.setDateAndTime(Instant.now().getEpochSecond());
//            object10.setPurchasePrice(34.53d);
//            object10.setSellingPrice(34.53d);
//            object10.setNameOfTradingInstrument("test10".toCharArray());
//
//            System.out.println("Pool is full");
//
//            Thread.sleep(5000);
//
//            pool.release(object1);
//            pool.release(object2);
//            pool.release(object3);
//            pool.release(object4);
//            pool.release(object5);
//            pool.release(object6);
//            pool.release(object7);
//            pool.release(object8);
//            pool.release(object9);
//            pool.release(object10);
//
//            System.out.println("Release all objects");
//
//            Thread.sleep(5000);
//
//            System.out.println("Memory before running GC " + (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / 1024 / 1024);
//            System.gc();
//            System.out.println("Memory after running GC " + (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / 1024 / 1024);
//
//            Thread.sleep(5000);
//        }

        for(;;) {
            ObjectPool pool = new ObjectPool(10);

            PriceQuotation object1 = pool.acquire();
            object1.setDateAndTime(Instant.now().getEpochSecond());
            object1.setPurchasePrice(34.53d);
            object1.setSellingPrice(34.53d);
            object1.setNameOfTradingInstrument("test1".toCharArray());

            PriceQuotation object2 = pool.acquire();
            object2.setDateAndTime(Instant.now().getEpochSecond());
            object2.setPurchasePrice(34.53d);
            object2.setSellingPrice(34.53d);
            object2.setNameOfTradingInstrument("test2".toCharArray());

            PriceQuotation object3 = pool.acquire();
            object3.setDateAndTime(Instant.now().getEpochSecond());
            object3.setPurchasePrice(34.53d);
            object3.setSellingPrice(34.53d);
            object3.setNameOfTradingInstrument("test3".toCharArray());

            PriceQuotation object4 = pool.acquire();
            object4.setDateAndTime(Instant.now().getEpochSecond());
            object4.setPurchasePrice(34.53d);
            object4.setSellingPrice(34.53d);
            object4.setNameOfTradingInstrument("test4".toCharArray());

            PriceQuotation object5 = pool.acquire();
            object5.setDateAndTime(Instant.now().getEpochSecond());
            object5.setPurchasePrice(34.53d);
            object5.setSellingPrice(34.53d);
            object5.setNameOfTradingInstrument("test5".toCharArray());

            PriceQuotation object6 = pool.acquire();
            object6.setDateAndTime(Instant.now().getEpochSecond());
            object6.setPurchasePrice(34.53d);
            object6.setSellingPrice(34.53d);
            object6.setNameOfTradingInstrument("test6".toCharArray());

            PriceQuotation object7 = pool.acquire();
            object7.setDateAndTime(Instant.now().getEpochSecond());
            object7.setPurchasePrice(34.53d);
            object7.setSellingPrice(34.53d);
            object7.setNameOfTradingInstrument("test7".toCharArray());

            PriceQuotation object8 = pool.acquire();
            object8.setDateAndTime(Instant.now().getEpochSecond());
            object8.setPurchasePrice(34.53d);
            object8.setSellingPrice(34.53d);
            object8.setNameOfTradingInstrument("test8".toCharArray());

            PriceQuotation object9 = pool.acquire();
            object9.setDateAndTime(Instant.now().getEpochSecond());
            object9.setPurchasePrice(34.53d);
            object9.setSellingPrice(34.53d);
            object9.setNameOfTradingInstrument("test9".toCharArray());

            PriceQuotation object10 = pool.acquire();
            object10.setDateAndTime(Instant.now().getEpochSecond());
            object10.setPurchasePrice(34.53d);
            object10.setSellingPrice(34.53d);
            object10.setNameOfTradingInstrument("test10".toCharArray());

            System.out.println("Pool is full");

            Thread.sleep(5000);

            pool.release(object1);
            pool.release(object2);
            pool.release(object3);
            pool.release(object4);
            pool.release(object5);
            pool.release(object6);
            pool.release(object7);
            pool.release(object8);
            pool.release(object9);
            pool.release(object10);

            System.out.println("Release all objects");

            Thread.sleep(5000);

            System.out.println("Memory before running GC " + (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / 1024 / 1024);
            System.gc();
            System.out.println("Memory after running GC " + (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / 1024 / 1024);

            Thread.sleep(5000);
        }
    }
}
