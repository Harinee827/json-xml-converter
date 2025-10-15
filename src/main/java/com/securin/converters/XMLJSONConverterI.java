package com.securin.converters;

import java.io.File;
import java.io.IOException;

public interface XMLJSONConverterI {
    void convertJSONtoXML(File json, File xml) throws IOException;
}
