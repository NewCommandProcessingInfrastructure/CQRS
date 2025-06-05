package com.java.deadlock;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class App {

	@Getter
	@AllArgsConstructor
	static class Friend {
		private final String name;

		public synchronized void bow(Friend bower) {
//			System.out.format("%s: %s" + "  has bowed to me!%n", this.name, bower.getName());
			log.info("{}:{} has bowed to me!", this.name, bower.getName());
			bower.bowBack(this);
		}

		public synchronized void bowBack(Friend bower) {
//			System.out.format("%s: %s" + " has bowed back to me!%n", this.name, bower.getName());
			log.info("{}:{} has bowed back to me!", this.name, bower.getName());
		}
	}

	public static void main(String[] args) {
		System.out.println("Java Threads Deadlock!");
		final Friend alphonse = new Friend("Alphonse");
		final Friend gaston = new Friend("Gaston");
		new Thread(new Runnable() {
			public void run() {
				alphonse.bow(gaston);
			}
		}).start();
		new Thread(new Runnable() {
			public void run() {
				gaston.bow(alphonse);
			}
		}).start();
	}
}
