package me.dean.parsers.impl;

import me.dean.Record;
import me.dean.parsers.ParserTemplate;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.File;
import java.util.HashMap;

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
