package es.pentagono;

import es.pentagono.commands.DocumentWordsCommand;

import java.util.Map;

public class Main {
    public static void main(String[] args) {
        WebService webService = new WebService();
        webService.add("/documents/:words", new DocumentWordsCommand());
        webService.start();
    }
}
