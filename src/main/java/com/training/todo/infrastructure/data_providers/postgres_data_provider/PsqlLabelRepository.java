package com.training.todo.infrastructure.data_providers.postgres_data_provider;

import com.training.todo.core.usecases.repository.ILabelRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.Vector;

public class PsqlLabelRepository extends PsqlRepository implements ILabelRepository {

    public PsqlLabelRepository(String url, String user, String password) {
        super(url, user, password);
    }

    @Override
    public Optional<DBLabel> search(String id) throws DBException {
        String sql = "SELECT id, title, description FROM labels WHERE id = ?";
        try (Connection conn = getConnection()){
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                DBLabel dbItem = DBLabel.builder()
                        .id(rs.getString("id"))
                        .title(rs.getString("title"))
                        .description(rs.getString("description"))
                        .build();
                return Optional.of(dbItem);
            }
        } catch (SQLException e) {
            throw new DBException("Search operation failed", e);
        }
        return Optional.empty();
    }

    @Override
    public List<DBLabel> searchAll() throws DBException {
        List<DBLabel> list = new Vector<>();
        String sql = "SELECT id, title, description FROM labels";
        try (Connection conn = getConnection()){
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                DBLabel dbItem = DBLabel.builder()
                        .id(rs.getString("id"))
                        .title(rs.getString("title"))
                        .description(rs.getString("description"))
                        .build();
                list.add(dbItem);
            }
        } catch (SQLException e) {
            throw new DBException("Search all operation failed", e);
        }

        return list;
    }
}
