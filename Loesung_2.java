// Erstellen Sie eine Klasse 'Konto' mit BigDecimal 'kontostand', einer Methode zum Verändern des Kontostandes und einem
// Konstruktor, in dem der Kontostand auf 0 festgelegt wird.
// Die Methode soll den Kontostand um einen übergebenen Wert ändern. Wird ein positiver Wert übergeben, wird der Kontostand erhöht,
// bei einem negativen Wert verringert.
//
// In der Main-Methode sollen 10 Threads gestartet werden, die jeweils 100_000 Mal den Kontostand um 1 erhöhen und anschließend um 1 verringern.
// Wenn alle Threads abgelaufen sind, wird ein Kontostand von exakt 0 erwartet. Stellen Sie sicher, dass dieses Ergebnis erreicht wird,
// indem der Vorgang synchronisiert wird.

package aufgaben;


import java.math.BigDecimal;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

class Konto
{
    private BigDecimal kontostand;

    public synchronized void kontostandAendern(BigDecimal wert)
    {
        kontostand = kontostand.add(wert);
    }

    public BigDecimal getKontostand()
    {
        return kontostand;
    }

    public Konto()
    {
        kontostand = BigDecimal.valueOf(0);
    }
}


public class Loesung_2
{
    public static void main(String[] args)
    {
        Konto k = new Konto();

        BigDecimal eins = BigDecimal.valueOf(1);
        BigDecimal minusEins = BigDecimal.valueOf(-1);

        try (ExecutorService executor = Executors.newCachedThreadPool())
        {
            for (int i = 0; i < 10; i++)
            {
                executor.execute(() ->
                {
                    System.out.println(Thread.currentThread());
                    for (int j = 0; j < 100_000; j++)
                    {
                        k.kontostandAendern(eins);
                        k.kontostandAendern(minusEins);
                    }
                });
            }
        }

        System.out.println(k.getKontostand());
    }
}
