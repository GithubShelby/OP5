package aufgaben;

/* Schreiben Sie eine Methode, die so lange zwei Zufallszahlen (von 0 bis 100) ermittelt und miteinander multipliziert, bis beide Zufallszahlen gleich sind.
Das Ergebnis der Multiplikation soll in eine Datei geschrieben werden.
Diese Methode lassen Sie von zwei Threads ausführen.
Jeder Thread wird dabei eigene Zufallszahlen erzeugen und multiplizieren, aber die Ergebnisse der Multiplikation in eine gemeinsame Datei abspeichern.
 */

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;

public class Loesung_4
{
    private static AtomicInteger count = new AtomicInteger(0);
    public static void main(String[] args)
    {
        try
        {
            Files.deleteIfExists(Path.of("src/aufgaben/log.txt"));
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        try (ExecutorService executor = Executors.newCachedThreadPool())
        {
            executor.execute(Loesung_4::multiply);
            executor.execute(Loesung_4::multiply);
        }
        System.out.println("Anzahl: " + count.get());
    }

    public static void multiply()
    {
        try (FileWriter writer = new FileWriter("log.txt", true))
        {
            while (true)
            {
                int a = ThreadLocalRandom.current().nextInt(100);
                int b = ThreadLocalRandom.current().nextInt(100);

                int p = a * b;
                System.out.println(Thread.currentThread().threadId() + " " + p);

                //Thread.sleep(100);
                count.incrementAndGet(); // Zum Testen die Anzahl an Werten speichern. Den Wert vergleiche ich mit der Anzahl an Einträgen in der Datei.

                // Ich konnte beim Testen dieser Lösung keinen Fehler provozieren. Sicherheitshalber sollte aber immer synchronisiert werden.
                // Alternativ das Programm so bauen, dass nur ein Thread für das Schreiben in die Datei zuständig ist
                // und dieser sich die Daten von anderen Threads geben lässt.
                synchronized (Loesung_4.class)
                {
                    writer.write(a + "*" + b + "=" + p);
                    writer.write("\n");
                    writer.flush();
                }
                if (a == b)
                    break;

            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
