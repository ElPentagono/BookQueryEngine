package es.pentagono;

import es.pentagono.commands.DocumentWordsCommand;
import es.pentagono.commands.StatsTypeCommand;
import es.pentagono.loaders.SQLMetadataLoader;
import es.pentagono.managers.SQLMetadataManager;
import es.pentagono.serializers.GsonAppearanceSerializer;
import es.pentagono.serializers.GsonEventSerializer;
import es.pentagono.serializers.GsonMetadataSerializer;

public class Main {
    public static void main(String[] args) {
        WebService webService = new WebService();
        webService.add("/documents/:words", new DocumentWordsCommand(
                new SQLMetadataLoader(),
                new GsonAppearanceSerializer()
        ));
        webService.add("/stats/:type", new StatsTypeCommand(
                new GsonEventSerializer(),
                new GsonMetadataSerializer(),
        new SQLMetadataManager()));

        webService.start();
    }
}
