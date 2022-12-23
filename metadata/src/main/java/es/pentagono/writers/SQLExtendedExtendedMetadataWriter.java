package es.pentagono.writers;

import es.pentagono.Configuration;
import es.pentagono.ExtendedMetadataWriter;

import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class SQLExtendedExtendedMetadataWriter implements ExtendedMetadataWriter {

    private final static String url = Configuration.getProperty("database");
    private final static Path datamart = Path.of(Configuration.getProperty("datamart"));

    @Override
    public void write(String extendedMetadata) {
        try {
            Class.forName("org.sqlite.JDBC");
            createDirectory(datamart);
            createTable();
            insertIntoTable(extendedMetadata);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void createDirectory(Path path) {
        if (!Files.exists(path)) path.toFile().mkdirs();
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

    private void insertIntoTable(String extendedMetadata) {
        String sql = "INSERT INTO metadata " +
                "VALUES (" + extendedMetadata + ")";

        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement()) {
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
