package es.pentagono.persisters;

import es.pentagono.Document;
import es.pentagono.Event;
import es.pentagono.MetadataPersister;
import es.pentagono.events.StoreEvent;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.sql.*;

import static java.nio.file.StandardOpenOption.APPEND;
import static java.nio.file.StandardOpenOption.CREATE;

public class SQLMetadataPersister implements MetadataPersister {

    private final static String url = "jdbc:sqlite:/appM/metadataDatamart/content.db"; // "jdbc:sqlite:" + System.getenv("DATAMART") + "/metadata/content.db"
    private static final String DATAMART = "/appM/metadataDatamart";// System.getenv("DATAMART");
    private static final String EVENT_FILE = "/app/datalake/events/updates.log";// System.getenv("DATAMART");
    private static final String CONFIG_HEADER = "filename\n";

    public SQLMetadataPersister() {
    }

    @Override
    public void persist(Document document) {
        try {
            createDatamartDirectory();
            Class.forName("org.sqlite.JDBC");
            if (existsInDatamart(document.uuid)) return;
            createTable();
            insertIntoTable(document);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void persist(Event event) {
        try {
            if (existsInDatamart(((StoreEvent) event).filename)) return;
            write(Paths.get(DATAMART + "/metadata.config"), ((StoreEvent) event).filename + "\n", APPEND);
            write(Paths.get(EVENT_FILE), ((StoreEvent) event).ts.getTime() + "\t" + ((StoreEvent) event).filename + "\tMETADATA DATAMART\tDOCUMENT ADDED\n", APPEND);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void createDatamartDirectory() throws IOException {
        if (notExist(DATAMART)) {
            createDirectory(Path.of(DATAMART));
            createConfigFile();
        }
    }

    private void createDirectory(Path path) {
        path.toFile().mkdirs();
    }

    private static boolean notExist(String file) {
        return !Files.exists(Paths.get(file));
    }

    private void createConfigFile() throws IOException {
        write(Paths.get(DATAMART + "/metadata.config"), CONFIG_HEADER, CREATE);
    }


    private void createTable() {
        String sql = "CREATE TABLE IF NOT EXISTS metadata (\n"
                + "	uuid text PRIMARY KEY,\n"
                + "	title text,\n"
                + "	author text,\n"
                + "	language text,\n"
                + "	releaseDate DATE,\n"
                + "	wordCount INTEGER\n"
                + ");";

        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private void insertIntoTable(Document document) {
        String sql = "INSERT INTO metadata " +
                "VALUES ('" + document.uuid + "'," + document.metadata + ")";

        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement()) {
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void write(Path path, String text, StandardOpenOption option) throws IOException {
        Files.write(path, text.getBytes(), option);
    }

    private boolean existsInDatamart(String filename) throws IOException {
        if (notExist(DATAMART + "/metadata.config")) return false;
        return Files.readAllLines(Paths.get(DATAMART + "/metadata.config")).stream()
                .anyMatch(s -> s.contains(filename));
    }
}
