package me.dean.parsers.impl;

import me.dean.parsers.ParserTemplate;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.File;

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

        return doc;
    }

    @Override
    public void parse(Document document) {
        StringBuilder names = new StringBuilder();
        StringBuilder values = new StringBuilder();

        document.select("ix|nonNumeric").forEach(e -> {
                names.append(sanitize(e.attr("name"))).append(",");
                values.append(sanitize(e.text())).append(",");
        });

        document.select("ix|nonFraction").forEach(e -> {
            names.append(sanitize(e.attr("name"))).append(",");
            values.append(sanitize(e.text())).append(",");
        });

        publish(names.toString(), values.toString());
    }
}
