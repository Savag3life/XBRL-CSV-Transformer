package me.savag3.xbrl.parsers.impl;

import me.savag3.xbrl.Record;
import me.savag3.xbrl.parsers.ParserTemplate;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.File;

/**
 * @author Savag3life
 * @since 1.0.0
 */
public class HTMLParser extends ParserTemplate<Document> {

    public HTMLParser() {
        super("HTML-Parser", ".html");
    }

    @Override
    public Document parseDocument(File file) {
        Document doc = null;
        try {
            doc = Jsoup.parse(file, "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (doc == null) {
            log("Failed to read file: " + file.getName());
            return null;
        }

        this.outputValues.put("file:name", file.getName());
        return doc;
    }

    @Override
    public void parse(Document document) {

        document.select("ix|nonNumeric").forEach(e -> {
            this.outputValues.put(e.attr("name"), e.text());
        });

        document.select("ix|nonFraction").forEach(e -> {
            this.outputValues.put(e.attr("name"), e.text());
        });

        new Record(this.outputValues);
    }
}
