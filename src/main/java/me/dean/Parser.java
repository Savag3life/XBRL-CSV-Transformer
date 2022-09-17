package me.dean;

import lombok.Getter;
import me.dean.parsers.ParserTemplate;
import me.dean.parsers.impl.HTMLParser;
import me.dean.parsers.impl.XMLParser;

import java.io.File;
import java.nio.file.Files;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Parser {

    @Getter private static Parser instance;

    private final List<String> results = new ArrayList<>();

    private final List<ParserTemplate<?>> parsers = Arrays.asList(
            new XMLParser(),
            new HTMLParser()
    );

    private static int batchCount = 1;

    public Parser(String[] args) {

        instance = this;

        if (args.length == 0) {
            System.out.println("Please specify the path to the folder containing the files to be processed.");
            return;
        }

        String path = args[0];
        File folder = new File(path);

        if (!folder.exists()) {
            System.out.println("The specified path does not exist.");
            return;
        }

        if (!folder.isDirectory()) {
            System.out.println("The specified path is not a directory.");
            return;
        }

        File[] files = folder.listFiles(f -> f.getName().endsWith(".xml") || f.getName().endsWith(".html"));

        if (files == null) {
            System.out.println("The specified path does not contain any files.");
            return;
        }

        int batchSize = -1;
        if (args.length > 2) {
            try {
                batchSize = Integer.parseInt(args[1]);
            } catch (NumberFormatException e) {
                System.out.println("The specified batch size is not a valid integer.");
                return;
            }
        }

        Instant start = Instant.now();
        for (File f : files) {
            parsers.stream()
                    .filter(parser -> f.getName().endsWith(parser.getFileExtension()))
                    .findFirst()
                    .ifPresent(parser -> {
                        parser.log("Parsing file: " + f.getName());
                        parser.handle(f);
                    });

            if (batchSize > 0 && results.size() >= batchSize) {
                publish();
            }
        }
        System.out.format("Finished parsing %d files in %dms.", files.length, Duration.between(start, Instant.now()).toMillis());
        System.out.println("All Done! Wrote " + batchCount + " files to ../output/ (batch-size: " + batchSize + ")");
    }

    private void writeStringToFile(String content, File file) {
        try {
            Files.write(file.toPath(), content.getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public synchronized void addResult(String result) {
        results.add(result);
    }

    public synchronized void publish() {
        new File("../output").mkdirs();
        writeStringToFile(String.join("",  results), new File("../output", "results-batch-" + batchCount + ".txt"));
        results.clear();
        batchCount++;
    }
}