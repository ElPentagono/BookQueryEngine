package es.pentagono.managers;

import es.pentagono.Metadata;
import es.pentagono.MetadataManager;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class SQLMetadataManager implements MetadataManager {

    public SQLMetadataManager() {
    }

    @Override
    public int count() {
        String sql = "SELECT COUNT(*) FROM metadata";
        try {
            Class.forName("org.sqlite.JDBC");
            Connection conn = DriverManager.getConnection("jdbc:sqlite:/appM/metadataDatamart/content.db"); // System.getenv("URL")
            Statement stmt = conn.createStatement();
            return Integer.parseInt(stmt.executeQuery(sql).getString("COUNT(*)"));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return 0;
    }

    @Override
    public List<Metadata> metadata() {
        String sql = "SELECT * FROM metadata";

        try {
            Class.forName("org.sqlite.JDBC");
            Connection conn = DriverManager.getConnection("jdbc:sqlite:/appM/metadataDatamart/content.db"); // System.getenv("URL")
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            return parseToList(rs);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    private static List<Metadata> parseToList(ResultSet rs) throws SQLException {
        List<Metadata> result = new ArrayList<>();

        while (rs.next()) {
            result.add(new Metadata(
                    rs.getString("title"),
                    rs.getString("author"),
                    rs.getString("language"),
                    (rs.getString("releaseDate") == null) ? null
                        : LocalDate.parse(rs.getString("releaseDate")))
            );
        }
        return result;
    }
}
