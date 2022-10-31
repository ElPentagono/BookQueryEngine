package es.pentagono.invertedindex.deserializer;

import es.pentagono.Document;
import es.pentagono.invertedindex.DocumentLoader;
import es.pentagono.MetadataBuilder;

import java.io.*;
import java.util.stream.Collectors;

public class InvertedIndexDocumentDeserializer implements DocumentLoader {
    @Override
    public Document load(int documentId) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(
                new File("datalake\\" + documentId).getAbsolutePath()));

        return new Document(
                documentId,
                new MetadataBuilder().build(reader.readLine()),
                reader.lines().collect(Collectors.joining("\n")));
    }
}
