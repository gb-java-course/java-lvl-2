package ru.gb.level2.lesson5;

import java.util.Arrays;

public class Lesson5 {

    private static final int SIZE = 100000000;

    private static final Object mon = new Object();

    public static void main(String[] args) {
        float[] array1 = createAndFillArrayWith(1f);
        long beforeSync = System.currentTimeMillis();
        calcNewValuesSync(array1);
        long afterSync = System.currentTimeMillis();

        float[] array2 = createAndFillArrayWith(1f);
        long beforeAsync = System.currentTimeMillis();
        calcNewValuesAsync(array2, 5);
        long afterAsync = System.currentTimeMillis();

        System.out.println(afterSync - beforeSync);
        System.out.println(afterAsync - beforeAsync);
        checkResult(array1, array2);

        //Output for 2 threads:
        //25305
        //13388
        //Arrays are equal!

        //Output for 5 threads:
        //25455
        //5705
        //Arrays are equal!

        //Output for 10 threads:
        //25318
        //3993
        //Arrays are equal!
    }

    private static float[] createAndFillArrayWith(float value) {
        float[] array = new float[SIZE];
        Arrays.fill(array, value);
        return array;
    }

    private static void calcNewValuesSync(float[] array) {
        for (int i = 0; i < array.length; i++) {
            array[i] = (float) (array[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
        }
    }

    private static void calcNewValuesAsync(float[] array, int threadCount) {
        int h = array.length / threadCount;

        int startIndex = 0;
        int endIndex = h;
        Thread[] threads = new Thread[threadCount];
        for (int i = 0; i < threadCount; i++) {
            threads[i] = createCalculatingThread(array, startIndex, endIndex);
            startIndex += h;
            endIndex += h;
        }

        for (int i = 0; i < threadCount; i++) {
            threads[i].start();
        }

        try {
            for (int i = 0; i < threadCount; i++) {
                threads[i].join();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static Thread createCalculatingThread(float[] array, int start, int end) {
        return new Thread(() -> {
            int tempSize = end - start;
            float[] tempArray = new float[tempSize];
            int tempCount = 0;
            for (int i = start; i < end; i++) {
                tempArray[tempCount++] = (float) (array[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
            }
            synchronized (mon) {
                System.arraycopy(tempArray, 0, array, start, tempSize);
            }
        });
    }

    private static void checkResult(float[] arraySync, float[] arrayAsync) {
        if (arraySync.length != arrayAsync.length) {
            System.err.printf("Arrays have different lengths, arr1 = %d, arr2 = %d", arraySync.length, arrayAsync.length);
            return;
        }

        int differencesCount = 0;
        for (int i = 0; i < arraySync.length; i++) {
            if (arraySync[i] != arrayAsync[i]) {
                differencesCount++;
            }
        }

        if (differencesCount != 0) {
            System.err.printf("Differences count = %d", differencesCount);
            return;
        }

        System.out.println("Arrays are equal!");
    }
}
