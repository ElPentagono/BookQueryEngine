package es.pentagono.loaders;

import es.pentagono.Metadata;
import es.pentagono.MetadataLoader;

import java.sql.*;
import java.time.LocalDate;

public class SQLMetadataLoader implements MetadataLoader {

    private final static String url = "jdbc:sqlite:/appM/metadataDatamart/content.db";// System.getenv("URL");

    public SQLMetadataLoader() {
    }

    @Override
    public Metadata load(String uuid) {
        String sql = "SELECT * FROM metadata WHERE uuid = " + "'" + uuid + "'";
        try {
            Class.forName("org.sqlite.JDBC");
            Connection conn = DriverManager.getConnection(url,"","");
            Statement stmt = conn.createStatement();
            return metadataOf(stmt.executeQuery(sql));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
        }

    private Metadata metadataOf(ResultSet query) throws SQLException {
        return new Metadata(query.getString("title"),
                query.getString("author"),
                query.getString("language"),
                (query.getString("releaseDate") == null) ? null
                    : LocalDate.parse(query.getString("releaseDate"))
        );
    }
}
