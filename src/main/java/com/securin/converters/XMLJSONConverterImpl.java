package com.securin.converters;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class XMLJSONConverterImpl implements XMLJSONConverterI {
    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    public void convertJSONtoXML(File json, File xml) throws IOException {
        JsonNode root = mapper.readTree(json);
        try (FileWriter writer = new FileWriter(xml)) {
            writer.write("<?xml version=\"1.0\"?>\n");
            writeNode(root, writer, root.isObject() ? "object" : "array", null, 0);
        }
    }

    private void writeNode(JsonNode node, FileWriter w, String tag, String name, int indent)
            throws IOException {
        String pad = "  ".repeat(indent);
        String attr = name != null ? " name=\"" + name + "\"" : "";
        switch (node.getNodeType()) {
            case OBJECT:
                w.write(pad + "<object" + attr + ">\n");
                node.fields().forEachRemaining(e ->
                    safeWrite(() -> writeNode(e.getValue(), w, null, e.getKey(), indent+1))
                );
                w.write(pad + "</object>\n");
                break;
            case ARRAY:
                w.write(pad + "<array" + attr + ">\n");
                for (JsonNode item : node) {
                    safeWrite(() -> writeNode(item, w, null, null, indent+1));
                }
                w.write(pad + "</array>\n");
                break;
            case STRING:
                w.write(pad + "<string" + attr + ">"
                        + escape(node.textValue())
                        + "</string>\n");
                break;
            case NUMBER:
                w.write(pad + "<number" + attr + ">"
                        + node.numberValue()
                        + "</number>\n");
                break;
            case BOOLEAN:
                w.write(pad + "<boolean" + attr + ">"
                        + node.booleanValue()
                        + "</boolean>\n");
                break;
            case NULL:
                w.write(pad + "<null" + attr + "/>\n");
                break;
            default:
                break;
        }
    }

    private void safeWrite(IORunnable r) {
        try {
            r.run();
        } catch (IOException ignored) { }
    }

    private String escape(String s) {
        return s.replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;");
    }

    @FunctionalInterface
    private interface IORunnable {
        void run() throws IOException;
    }
}
