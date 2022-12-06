package es.pentagono.persisters;

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

    private final static String url = "jdbc:sqlite:" + System.getenv("DATAMART") + "/metadata/content.db";
    private static final String DATAMART = System.getenv("DATAMART");
    private static final String LOG_HEADER = "filename\tts";

    public SQLMetadataPersister() {
    }

    @Override
    public void persist(String filename, String content) {
        try {
            createDatamartDirectory();
            Class.forName("org.sqlite.JDBC");
            if (existsInDatamart(filename)) return;
            createTable();
            insertIntoTable(filename, content);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void persist(Event event) {
        try {
            if (existsInDatamart(((StoreEvent) event).filename)) return;
            write(Paths.get(DATAMART + "/metadata/events/updates.log"), new StoreEvent(((StoreEvent) event).filename).toString(), APPEND);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void createDatamartDirectory() throws IOException {
        if (notExist(DATAMART + "/metadata")) {
            createDirectory(Path.of(DATAMART + "/metadata/events"));
            addHeaderStoreEventFile();
        }
    }

    private void createDirectory(Path path) {
        if (!Files.exists(path)) path.toFile().mkdirs();
    }

    private static boolean notExist(String file) {
        return !Files.exists(Paths.get(file));
    }

    private void addHeaderStoreEventFile() throws IOException {
        write(Paths.get(DATAMART + "/metadata/events/updates.log"), LOG_HEADER, CREATE);
    }


    private void createTable() {
        String sql = "CREATE TABLE IF NOT EXISTS metadata (\n"
                + "	uuid text PRIMARY KEY,\n"
                + "	title text,\n"
                + "	author text,\n"
                + "	language text,\n"
                + "	releaseDate text\n"
                + ");";

        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private void insertIntoTable(String filename, String content) {
        String sql = "INSERT INTO metadata " +
                "VALUES ('" + filename + "'," + content + ")";

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
        if (notExist(DATAMART + "/metadata/events/updates.log")) return false;
        return Files.readAllLines(Paths.get(DATAMART + "/metadata/events/updates.log")).stream()
                .map(line -> line.split("\t")[0])
                .anyMatch(s -> s.contains(filename));
    }
}
