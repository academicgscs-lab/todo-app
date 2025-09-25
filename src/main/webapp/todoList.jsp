<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List, com.training.todo.controller.model.TaskDto" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Todo App</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 20px; }
        .task-list { display: flex; flex-wrap: wrap; gap: 20px; }
        .todo-card { border: 1px solid #ccc; border-radius: 8px; padding: 15px; width: 250px; box-shadow: 2px 2px 8px rgba(0,0,0,0.1); }
        .todo-card h3 { margin-top: 0; }
        .todo-card p { margin: 5px 0; }
        .todo-card form { margin-top: 10px; }
        .todo-card button {
            background-color: #007bff;
            color: white;
            border: none;
            padding: 8px 15px;
            border-radius: 5px;
            cursor: pointer;
        }
        .todo-card button:hover {
            background-color: #0056b3;
        }

        .add-button {
            display: inline-block;
            padding: 10px 20px;
            background-color: #28a745;
            color: white;
            text-decoration: none;
            border-radius: 5px;
            font-weight: bold;
        }
        .add-button:hover {
            background-color: #218838;
        }
    </style>
</head>
<body>
<div>
    <h1>Todo App</h1>
    <a href="TodoList/new" class="add-button">Add +</a>
</div>

<div class="task-list">
    <%
        List<TaskDto> taskList = (List<TaskDto>) request.getAttribute("taskList");
        if (taskList != null && !taskList.isEmpty()) {
            for (TaskDto task : taskList) {
    %>
    <div class="todo-card">
        <h3><%= task.getTitle() %></h3>
        <p><strong>Description:</strong> <%= task.getDescription() %></p>
        <p><strong>Status:</strong> <%= task.getStatus() %></p>
        <p><strong>Start Date:</strong> <%= task.getStartDate() %></p>
        <p><strong>Target Date:</strong> <%= task.getDueDate() %></p>

        <form action="edit" method="post">
            <input type="hidden" name="id" value="<%= task.getId() %>">
            <button type="submit">Edit</button>
        </form>

        <form action="delete" method="post">
            <input type="hidden" name="id" value="<%= task.getId() %>">
            <button type="submit">Delete</button>
        </form>

    </div>
    <%
        } // End of for loop
    } else {
    %>
    <p>No movies available at the moment.</p>
    <%
        } // End of if-else
    %>
</div>
</body>
</html>
