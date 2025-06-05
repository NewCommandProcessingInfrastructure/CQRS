package com.java.concurrency;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RaceConditionFixed {

	private static int COUNTER = 0;
	
	 // Synchronize access to increment
  public static synchronized void increment() {
      COUNTER++;
      System.out.println(Thread.currentThread().getName() + " incremented counter to: " + COUNTER);
  }

	public static void main(String[] args) {
		System.err.println("Race Condition Fixed");

		 Runnable incrementTask = () -> {
       for (int i = 0; i < 50; i++) {
           increment(); // thread-safe
           try {
               Thread.sleep(10);
           } catch (InterruptedException e) {
               e.printStackTrace();
           }
       }
   };

   Thread t1 = new Thread(incrementTask, "Thread-1");
   Thread t2 = new Thread(incrementTask, "Thread-2");

   t1.start();
   t2.start();
	}



}
