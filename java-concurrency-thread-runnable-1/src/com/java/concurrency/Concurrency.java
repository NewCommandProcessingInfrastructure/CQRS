package com.java.concurrency;

public class Concurrency {
	
	private static int COUNTER = 0;

	public static void main(String[] args) {
		System.out.println("Java Concurrency Example");
		
		Runnable incrementTask = () -> {
			for(int i=0; i<50; i++) {
				COUNTER++;
				System.out.println(Thread.currentThread().getName() + " incremented counter to: " + COUNTER);
			
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			
			}
		};
		
		Thread thread1 = new Thread(incrementTask, "Thread-1");
		Thread thread2 = new Thread(incrementTask, "Thread-2");
		
		thread1.start();
		thread2.start();
	}
}
