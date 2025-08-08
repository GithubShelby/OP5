/*
Erstellen Sie ein Programm mit zwei Klassen:
Eine Klasse "Producer" erzeugt zufällige Daten und legt sie in eine Warteschlange (Interface 'Queue' aus java.util.Queue) ab;
eine Klasse "Consumer" liest Daten aus der Warteschlange und verarbeitet sie weiter.
Beide Klassen erben von Thread oder implementieren Runnable. Sie speichern jeweils eine Referenz auf die Queue, welche im Konstruktor übergeben wird.
In den überschriebenen run()-Methoden wird eine Endlosschleife gestartet, in der die entsprechende Aufgabe erledigt wird.
Nach dem Erzeugen bzw. Verarbeiten der Daten wird eine zufällige Zeit zwischen 0 und 1000ms gewartet (Thread.sleep).
Zum Verarbeiten reicht eine einfache Konsolenausgabe. Zu Testzwecken können Sie die erstellten Daten ebenfalls ausgeben lassen.

Stellen Sie sicher, dass immer nur ein Producer oder Consumer gleichzeitig auf die Warteschlange zugreift.

In der Main-Methode wird ein ThreadPool erzeugt, in dem ein Producer und ein Consumer gestartet werden.
*/

package aufgaben;

import java.util.Queue;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

class Producer extends Thread
{
    private final Queue<Character> queue;
    private final Random random = new Random();

    public Producer(Queue<Character> queue)
    {
        this.queue = queue;
    }

    @Override
    public void run()
    {
        while (true)
        {
            char c = (char) random.nextInt(65, 91);

            synchronized (queue) // Zugriff auf die Warteschlange synchronisieren
            {
                System.out.println("produced: " + c);
                queue.add(c);
            }

            try
            {
                sleep(random.nextInt(1000)); // Eine zufällige Zeit warten, bevor neue Daten produziert werden
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }
        }
    }
}

class Consumer extends Thread
{
    private final Queue<Character> queue;
    private final Random random = new Random();

    public Consumer(Queue<Character> queue)
    {
        this.queue = queue;
    }

    @Override
    public void run()
    {
        while (true)
        {
            synchronized (queue) // Zugriff auf die Warteschlange synchronisieren
            {
                if (!queue.isEmpty()) // Nur Daten verarbeiten, wenn es welche gibt
                {
                    System.out.println("    consumed: " + queue.poll());
                }
            }

            try
            {
                sleep(random.nextInt(1000)); // Eine zufällige Zeit warten, bevor neue Daten produziert werden
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }
        }
    }
}

public class Loesung_5
{
    public static void main(String[] args)
    {
        Queue<Character> queue = new LinkedBlockingQueue<>();
        Producer producer = new Producer(queue);
        Consumer consumer = new Consumer(queue);

        try (ExecutorService executor = Executors.newCachedThreadPool())
        {
            executor.execute(producer);
            executor.execute(consumer);
        }
    }
}
