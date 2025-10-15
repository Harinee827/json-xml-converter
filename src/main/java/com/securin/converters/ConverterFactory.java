package com.securin.converters;

public final class ConverterFactory {
    public static final XMLJSONConverterI createXMLJSONConverter() {
        return new XMLJSONConverterImpl();
    }
}
