package me.dean.parsers.impl;

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

        return document;
    }

    @Override
    public void parse(Document document) {
        StringBuilder names = new StringBuilder();
        StringBuilder values = new StringBuilder();

        document.getRootElement().elementIterator().forEachRemaining(node -> {
                names.append(sanitize(node.getName())).append(",");
                values.append(sanitize(node.getText())).append(",");
        });

        publish(names.toString(), values.toString());
    }
}
