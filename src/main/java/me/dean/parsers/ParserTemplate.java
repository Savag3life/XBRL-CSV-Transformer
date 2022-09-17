package me.dean.parsers;

import lombok.Getter;
import me.dean.Parser;

import java.io.File;

public abstract class ParserTemplate<T> {

    protected final String name;
    @Getter private final String fileExtension;

    public ParserTemplate(String name, String fileExtension) {
        this.name = name;
        this.fileExtension = fileExtension;
    }

    public void log(String log) {
        System.out.println("[" + name + "] " + log);
    }

    public abstract T parseDocument(File file);

    public abstract void parse(T document);

    public void handle(File file) {
        T document = parseDocument(file);
        if (document != null) {
            parse(document);
        }
    }

    public String sanitize(String input) {
        return input.replaceAll(",", ".");
    }

    public static synchronized void publish(String valueSet, String entrySet) {
        Parser.getInstance().addResult(valueSet.substring(0, valueSet.length() - 1) + "\n" + entrySet.substring(0, entrySet.length() - 1) + "\n");
    }
}
