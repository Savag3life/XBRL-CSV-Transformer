package me.savag3.parsers;

import lombok.Getter;

import java.io.File;
import java.util.HashMap;

/**
 * @author Savag3life
 * @since 1.0.0
 */
public abstract class ParserTemplate<T> {

    protected final String name;
    @Getter private final String fileExtension;
    @Getter protected final HashMap<String, String> outputValues = new HashMap<>();

    public ParserTemplate(String name, String fileExtension) {
        this.name = name;
        this.fileExtension = fileExtension;
    }

    public void log(String log) {
        System.out.format("[%s] %s", this.name, log);
    }

    public abstract T parseDocument(File file);

    public abstract void parse(T document);

    public void handle(File file) {
        T document = parseDocument(file);
        if (document != null) {
            parse(document);
        }
    }
}
