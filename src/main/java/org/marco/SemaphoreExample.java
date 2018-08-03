package org.marco;

import java.io.PrintStream;
import java.util.Arrays;
import java.util.concurrent.Semaphore;

class CoffeeMaker implements Runnable {
  private Semaphore semaphore;
  private String civilianName;
  private String beberage;
  private PrintStream printer = System.out;

  public CoffeeMaker(Semaphore semaphore, String civilianName, String beberage)
      throws InterruptedException {
    super();
    this.semaphore = semaphore;
    this.civilianName = civilianName;
    this.beberage = beberage;

  }

  @Override
  public void run() {


    try {
      semaphore.acquire(1);
      printer.format("%s begins to make beberage %s %n", civilianName, beberage);
      Thread.sleep(4000);
    } catch (InterruptedException exc) {
      exc.printStackTrace();
    } finally {
      semaphore.release();
    }
    printer.format("%s ends his turn. %n", civilianName);

  }
}


public class SemaphoreExample {

  public static void main(String... params) throws InterruptedException {
    Semaphore semaphore = new Semaphore(2);
    Arrays.asList(new CoffeeMaker(semaphore, "Juan", "Mocca"),
        new CoffeeMaker(semaphore, "Ivan Rodrigo", "Protein Milk"),
        new CoffeeMaker(semaphore, "Brenda", "Tea"), new CoffeeMaker(semaphore, "Abraham", "Beer"),
        new CoffeeMaker(semaphore, "Marco", "Chai Tea with lots of cardamome")).stream()
        .map(T -> new Thread(T)).forEach(C -> C.start());

  }

}

