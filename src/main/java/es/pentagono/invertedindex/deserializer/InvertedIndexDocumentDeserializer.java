package es.pentagono.invertedindex.deserializer;

import es.pentagono.Document;
import es.pentagono.invertedindex.DocumentLoader;

import java.io.*;
import java.util.stream.Collectors;

public class InvertedIndexDocumentDeserializer implements DocumentLoader {

    public Document load(int documentId) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(
                new File("datalake\\" + documentId).getAbsolutePath()));

        return new Document(
                documentId,
                reader.readLine(),
                reader.lines().collect(Collectors.joining("\n")));
    }
}
