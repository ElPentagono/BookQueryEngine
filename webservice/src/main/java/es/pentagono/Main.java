package es.pentagono;

import es.pentagono.commands.DocumentWordsCommand;
import es.pentagono.deserializers.GsonMetadataDeserializer;
import es.pentagono.serializers.JsonAppearanceSerializer;

public class Main {
    public static void main(String[] args) {
        WebService webService = new WebService();
        webService.add("/documents/:words", new DocumentWordsCommand(new JsonAppearanceSerializer(new GsonMetadataDeserializer())));
        webService.start();
    }
}
