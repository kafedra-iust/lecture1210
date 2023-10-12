package com.example.lecture1210.dao;

import com.example.lecture1210.data.Artist;
import jakarta.annotation.Resource;
import jakarta.ejb.Stateless;
import lombok.SneakyThrows;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Stateless
public class ArtistDao {

    @Resource(name = "jdbc/chinook2")
    DataSource ds;

    public List<Artist> findAll() {
        try (Connection connection = ds.getConnection();
             Statement statement = connection.createStatement();
             ResultSet rs = statement.executeQuery("select * from artist")
        ) {
            List<Artist> artists = new ArrayList<>();
            while (rs.next()) {
                int id = rs.getInt("ArtistId");
                String name = rs.getString("Name");
                artists.add(new Artist(id, name));
            }
            return artists;
        } catch (SQLException e) {
            System.err.println("error");
            return Collections.emptyList();
        }
    }

    public void add(Artist artist) {
        try (Connection connection = ds.getConnection();
             PreparedStatement ps = connection.prepareStatement("insert into artist (Name) values (?)")) {
            ps.setString(1, artist.getName());
            ps.executeUpdate();
        } catch (SQLException ex) {
            System.err.println("error");
        }
    }

    public void delete(int id) {
        try (Connection connection = ds.getConnection();
             PreparedStatement ps = connection.prepareStatement("delete from artist where ArtistId = ?")) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException ex) {
            System.err.println("error");
        }
    }
}
