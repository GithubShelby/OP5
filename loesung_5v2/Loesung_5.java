package aufgaben.loesung_5v2;

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

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

public class Loesung_5
{
    public static void main(String[] args)
    {
        BlockingQueue<Integer> queue = new LinkedBlockingQueue<>();

        Producer producer = new Producer(queue);
        Consumer consumer = new Consumer(queue);

        ExecutorService executor = Executors.newFixedThreadPool(2);
        executor.execute(producer);
        executor.execute(consumer);

        executor.shutdown();
    }
}
