package aufgaben;
/*
Schreiben Sie ein Programm, welches die Summe aller Zahlen in einem Array mithilfe mehrerer Threads berechnet.
Teilen Sie das Array in zwei gleich große Teile und lassen jeden Thread die Summe einer Hälfte berechnen.
Die beiden Teil-Summen werden für die gesamte Summe kombiniert.
 */

import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

public class Loesung_3
{
    private static final Random random = new Random();

    //private static final AtomicInteger sum = new AtomicInteger(0);
    private static int sum = 0;

    public static void main(String[] args) throws InterruptedException
    {
        int[] array = new int[10];
        for (int i = 0; i < array.length; i++)
        {
            array[i] = random.nextInt(100);
        }

        System.out.println(Arrays.toString(array));

        ExecutorService executor = Executors.newFixedThreadPool(2);

        executor.execute(() -> sum(Arrays.copyOfRange(array, 0, array.length/2)));
        executor.execute(() -> sum(Arrays.copyOfRange(array, array.length/2, array.length)));

        executor.shutdown();

        while (!executor.isTerminated()) // Ist der ExecutorService nicht im Try-With-Resources, muss eventuell auf das Ergebnis gewartet werden.
        {
            System.out.print(".");
            Thread.sleep(1);
        }
        //System.out.println(sum.get());
        System.out.println(sum);
    }

    private static void sum(int[] array)
    {
        int erg =  Arrays.stream(array).sum();
        //sum.addAndGet(erg);
        synchronized (Loesung_3.class)
        {
            sum += erg;
        }
    }
}
