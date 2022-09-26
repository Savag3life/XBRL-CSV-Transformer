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

public class Parser implements Formats {

    @Getter private static Parser instance;

    @Getter private final List<String> keys = new ArrayList<>() {{ add("file:name"); }};
    private final List<Record> records = new ArrayList<>();

    private final List<ParserTemplate<?>> parsers = Arrays.asList(
            new XMLParser(),
            new HTMLParser()
    );

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

        Instant start = Instant.now();

        for (File f : files) {
            parsers.stream()
                    .filter(parser -> f.getName().endsWith(parser.getFileExtension()))
                    .findFirst()
                    .ifPresent(parser -> {
                        parser.log("Parsing file: " + f.getName());
                        parser.handle(f);
                    });
        }

        publish();
        System.out.println("System counted %d unique columns.".formatted(keys.size() - 1));
        System.out.format("Finished parsing %d files in %dms.\n", files.length, Duration.between(start, Instant.now()).toMillis());
        System.out.format("All Done! Wrote %d rows to ./output/results.csv\n", records.size());
    }

    private void writeStringToFile(String content, File file) {
        try {
            Files.write(file.toPath(), content.getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addNewColumn(String key, String creator) {
        System.out.println("Adding new column: \"" + key + "\" (created by '" + creator + "')");
        keys.add(key);
        this.records.forEach(record -> record.addNewColumn(""));
    }

    public void addRecord(Record record) {
        this.records.add(record);
    }

    public void publish() {
        new File("output").mkdirs();

        String keys = String.join(",", this.keys);
        StringBuilder sb = new StringBuilder();
        sb.append(keys).append("\n");

        for (Record record : records) {
            sb.append(arrayToCSV(record.getValues())).append("\n");
        }

        writeStringToFile(String.join("", sb.toString()), new File("output", "results.csv"));
    }
}