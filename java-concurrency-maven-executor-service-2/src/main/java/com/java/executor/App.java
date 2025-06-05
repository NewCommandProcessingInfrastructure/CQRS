package com.java.executor;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class App {

	static class TitleTask implements Callable<String> {
		private final String url;

		public TitleTask(String url) {
			this.url = url;
		}

		@Override
		public String call() throws Exception {
			try {
				URL website = new URL(url);
				BufferedReader in = new BufferedReader(new InputStreamReader(website.openStream()));

				String inputLine;
				while ((inputLine = in.readLine()) != null) {
					if (inputLine.toLowerCase().contains("<title>")) {
						return url + " => " + inputLine.trim();
					}
				}
				in.close();
				return url + " => Title not found";
			} catch (Exception e) {
				return url + " => Error: " + e.getMessage();
			}
		}

	}

	public static void main(String[] args) {
		System.out.println("Executor Service!");
		 List<String> websites = List.of(
         "https://www.google.com",
         "https://www.wikipedia.org",
         "https://www.stackoverflow.com"
     );
		 
		 ExecutorService executor = Executors.newFixedThreadPool(3);

     List<Future<String>> futures = new CopyOnWriteArrayList<>();
     for (String site : websites) {
         futures.add(executor.submit(new TitleTask(site)));
     }

     for (Future<String> future : futures) {
         try {
					System.out.println(future.get());
				} catch (InterruptedException e) {
					e.printStackTrace();
				} catch (ExecutionException e) {
					e.printStackTrace();
				} // Waits for result
     }

     executor.shutdown();
		 
	}
}
