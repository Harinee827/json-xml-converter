JSON-to-XML Converter
A minimal Java CLI tool that reads a JSON file and outputs equivalent XML.
It’s handy when you need to feed JSON-based data into systems or workflows
that only accept XML.

Build
  mvn clean package

Run
  java -jar target/json-xml-converter-1.0-SNAPSHOT.jar sample.json output.xml

Dependencies
  jackson-databind  https://github.com/FasterXML/jackson
