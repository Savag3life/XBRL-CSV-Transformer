package me.dean.parsers.impl;

import me.dean.Record;
import me.dean.parsers.ParserTemplate;
import org.dom4j.Document;
import org.dom4j.io.SAXReader;

import java.io.File;

public class XMLParser extends ParserTemplate<Document> {

    public XMLParser() {
        super("XML-Parser", ".xml");
    }

    @Override
    public Document parseDocument(File file) {
        SAXReader reader = new SAXReader();
        Document document = null;
        try {
            document = reader.read(file);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (document == null) {
            log("Failed to read file: " + file.getName());
            return null;
        }

        this.outputValues.put("file:name", file.getName());
        return document;
    }

    @Override
    public void parse(Document document) {
        document.getRootElement().elementIterator().forEachRemaining(node -> {
            this.outputValues.put(node.getName(), node.getText());
        });

        new Record(this.outputValues);
    }
}
