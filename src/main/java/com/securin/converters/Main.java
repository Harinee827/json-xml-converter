package com.securin.converters;

import java.io.File;

public class Main {
    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("Usage: java -jar json-xml-converter.jar <in.json> <out.xml>");
            System.exit(1);
        }
        try {
            File in  = new File(args[0]);
            File out = new File(args[1]);
            ConverterFactory.createXMLJSONConverter()
                            .convertJSONtoXML(in, out);
            System.out.println("Converted to " + out.getAbsolutePath());
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            System.exit(2);
        }
    }
}
