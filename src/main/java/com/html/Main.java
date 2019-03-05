package com.html;

import com.html.parser.Node;
import com.html.parser.csv.Csv;
import com.html.parser.url.Parsable;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {

        if (args.length == 0) {
            return;
        }

        System.out.println("URL's parsing started");

        ExecutorService executor = Executors.newFixedThreadPool(10);
        List<Callable<List<Node>>> callables = new ArrayList<>();
        List<Node> nodes = new ArrayList<>();

        for (String arg : args) {
            callables.add(() -> new Parsable.Fork(arg).parse());
        }

        executor.invokeAll(callables)
            .stream()
            .map(future -> {
                try {
                    return future.get();
                } catch (Exception e) {
                    throw new IllegalStateException(e);
                }
            })
            .forEach(nodes::addAll);

        new Csv().write(nodes);

        executor.shutdown();

        System.out.println("URL's parsing success finished");
    }
}
