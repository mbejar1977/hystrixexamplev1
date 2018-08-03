package org.marco;

import java.util.concurrent.SynchronousQueue;
import java.util.function.Predicate;

class Consumes implements Runnable {

  SynchronousQueue<String> queue;

  public Consumes(SynchronousQueue<String> queue) {
    super();
    this.queue = queue;
  }

  @Override
  public void run() {
    try {
      Thread.sleep(3000);
      String string = queue.poll();
      System.out.println(string);

    } catch (InterruptedException exc) {
      exc.printStackTrace();
    }

  }
}


public class SynchronousQueueExample {
  public static void main(String[] s) throws InterruptedException {
    SynchronousQueue<String> queue = new SynchronousQueue<>();
    System.out.println("Begins.");
    new Thread(new Consumes(queue)).start();
    queue.put("This string says nothing.");
    System.out.println("Just after put.");
    Thread.sleep(1000);
    System.out.println("after sleep");
    queue.put("This string says nothing too.");
    System.out.println("Ends.");
  }
}
