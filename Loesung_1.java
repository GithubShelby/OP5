// Schreiben Sie ein Programm mit einem Thread-Pool von 5 Threads und einer gemeinsamen Zähler-Variable.
// Jeder Thread gibt die eigene Thread-ID aus, erhöht den Zähler um einen bestimmten Wert und gibt dann den aktuellen Wert des Zählers aus.

package aufgaben;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Loesung_1
{
    static int count;
    public static void main(String[] args)
    {
        try (ExecutorService executor = Executors.newFixedThreadPool(5))
        {
            for (int i = 0; i < 5; i++) {
                executor.execute(() ->
                {
                    synchronized (Loesung_1.class) {
                        System.out.println("id: " + Thread.currentThread().threadId());
                        count = count + 1;
                        System.out.println(count);
                    }
                });
            }
        }
    }
}
