package com.java.concurrency;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class App {
	
  // Method to fetch content from a URL (blocking)
  public static String fetchUrl(String url) throws Exception {
      StringBuilder content = new StringBuilder();
      try (BufferedReader in = new BufferedReader(new InputStreamReader(new URL(url).openStream()))) {
          String line;
          while ((line = in.readLine()) != null) {
              content.append(line).append("\n");
          }
      }
      return content.toString();
  }
	
    public static void main(String[] args) {
        System.out.println("Completable Futures!");
        
//        List<String> urls = List.of(
//            "https://example.com",
//            "https://www.wikipedia.org",
//            "https://openjdk.java.net"
//        );
        
   		 List<String> urls = List.of(
           "https://www.google.com",
           "https://www.wikipedia.org",
           "https://www.stackoverflow.com"
   				 );

        // Step 2: Create CompletableFutures for each URL fetch
        List<CompletableFuture<String>> futures = urls.stream()
            .map(url -> CompletableFuture.supplyAsync(() -> {
                try {
                	log.info("Inside supplyAsync: {}", url);
                    return fetchUrl(url);
                } catch (Exception e) {
                    return "Failed to fetch " + url + ": " + e.getMessage();
                }
            }))
            .collect(Collectors.toList());

        // Step 3: Combine all futures into one
        CompletableFuture<Void> allDone = CompletableFuture.allOf(
            futures.toArray(new CompletableFuture[0])
        );

        // Step 4: When all are done, process results
        allDone.thenRun(() -> {
//            System.out.println("All URLs fetched. Results:");
            log.info("All URLs fetched. Results:");
            futures.forEach(future -> {
                try {
                    String content = future.get(); // get content (already done)
//                    System.out.println("Content length: " + content.length());
                    log.info("Content length: {}" , content.length());
                } catch (Exception e) {
//                    System.out.println("Error getting future result: " + e.getMessage());
                    log.info("Error getting future result: {}" , e.getMessage());
                }
            });
        });

        // Step 5: Wait for all to complete before exiting main thread
        allDone.join();
    }
}
