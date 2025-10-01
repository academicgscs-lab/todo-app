package com.training.todo.infrastructure.data_providers.postgres_data_provider;

import com.training.todo.core.usecases.repository.ITaskRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Vector;

public class PsqlTaskRepository extends PsqlRepository implements ITaskRepository {

    public PsqlTaskRepository(String url, String user, String password) {
        super(url, user, password);
    }

    @Override
    public Optional<DbTask> search(String id) throws DBException {
        String sql = "SELECT id, title, description, statusId, creation_date, due_date, start_date FROM tasks WHERE id = ?";
        try (Connection conn = getConnection()){
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                DbTask dbTask = DbTask.builder()
                        .id(rs.getString("id"))
                        .title(rs.getString("title"))
                        .description(rs.getString("description"))
                        .statusId(rs.getString("statusId"))
                        .startDate(LocalDate.ofEpochDay(rs.getLong("start_date")))
                        .creationDate(LocalDate.ofEpochDay(rs.getLong("creation_date")))
                        .dueDate(LocalDate.ofEpochDay(rs.getLong("due_date")))
                        .build();
                return Optional.of(dbTask);
            }
        } catch (SQLException e) {
            throw new DBException(e.getMessage(), e);
        }
        return Optional.empty();
    }

    @Override
    public List<DbTask> search() throws DBException {
        List<DbTask> taskList = new Vector<>();
        String sql = "SELECT id, title, description, statusId, creation_date, due_date, start_date FROM tasks";

        try (Connection conn = getConnection()){
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                DbTask dbTask = DbTask.builder()
                        .id(rs.getString("id"))
                        .title(rs.getString("title"))
                        .description(rs.getString("description"))
                        .statusId(rs.getString("statusId"))
                        .startDate(LocalDate.ofEpochDay(rs.getLong("start_date")))
                        .creationDate(LocalDate.ofEpochDay(rs.getLong("creation_date")))
                        .dueDate(LocalDate.ofEpochDay(rs.getLong("due_date")))
                        .build();
                taskList.add(dbTask);
            }
        } catch (SQLException e) {
            throw new DBException("Search all operation failed", e);
        }

        return taskList;
    }

    @Override
    public void add(DbTask task) throws DBException {
        String sql = "INSERT INTO tasks (id, title, description, statusId, creation_date, due_date, start_date) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = getConnection()){
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, task.id());
            stmt.setString(2, task.title());
            stmt.setString(3, task.description());
            stmt.setString(4, task.statusId());
            stmt.setLong(6, task.creationDate().toEpochDay());
            stmt.setLong(5, task.dueDate().toEpochDay());
            stmt.setLong(7, task.startDate().toEpochDay());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DBException("Add operation has failed", e);
        }
    }

    @Override
    public void update(DbTask task) throws DBException {
        String sql = "UPDATE tasks SET title = ?, description = ?, statusId = ?, creation_date = ?, due_date = ?, start_date = ? WHERE id = ?";
        try (Connection conn = getConnection()){
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, task.title());
            stmt.setString(2, task.description());
            stmt.setString(3, task.statusId());
            stmt.setLong(4, task.creationDate().toEpochDay());
            stmt.setLong(5, task.dueDate().toEpochDay());
            stmt.setLong(6, task.startDate().toEpochDay());
            stmt.setString(7, task.id());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DBException(e.getMessage(), e);
        }
    }

    @Override
    public void delete(String id) throws DBException {
        String sql = "DELETE FROM tasks WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DBException(e.getMessage(), e);
        }
    }
}
