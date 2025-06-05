package com.java.executor;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletionService;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import lombok.extern.slf4j.Slf4j;

@Slf4j
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

	public static void main(String[] args) throws InterruptedException {
		System.out.println("Executor Service!");
//		 List<String> websites = List.of(
//         "https://www.google.com",
//         "https://www.wikipedia.org",
//         "https://www.stackoverflow.com"
//     );
//
//		 ExecutorService executor = Executors.newFixedThreadPool(3);
//
//     List<Future<String>> futures = new CopyOnWriteArrayList<>();
//     for (String site : websites) {
//         futures.add(executor.submit(new TitleTask(site)));
//     }
//
//     for (Future<String> future : futures) {
//         try {
////					System.out.println(future.get());
//					log.info(future.get());
//				} catch (InterruptedException e) {
//					e.printStackTrace();
//				} catch (ExecutionException e) {
//					e.printStackTrace();
//				} // Waits for result
//     }
//
//     executor.shutdown();
		
		 List<String> websites = List.of(
         "https://www.google.com",
         "https://www.wikipedia.org",
         "https://www.stackoverflow.com"
 );

 ExecutorService executor = Executors.newFixedThreadPool(3);
 CompletionService<String> completionService = new ExecutorCompletionService<>(executor);

 // Submit tasks
 for (String site : websites) {
     completionService.submit(new TitleTask(site));
 }

 // Process completed tasks as they finish (non-blocking main thread in the sense that it does not wait for all)
 for (int i = 0; i < websites.size(); i++) {
     try {
         Future<String> completedFuture = completionService.take();  // blocks only here waiting for one completed task
         String result = completedFuture.get();  // get result of that completed task
         log.info(result);
     } catch (ExecutionException e) {
         log.error("Error during task execution", e);
     }
 }

 executor.shutdown();
 executor.awaitTermination(10, TimeUnit.SECONDS);

	}
}
