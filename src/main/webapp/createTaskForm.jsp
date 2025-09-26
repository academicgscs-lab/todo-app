<%@ page import="java.util.Collection" %>
<%@ page import="com.training.todo.controller.model.LabelDto" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>TodoApp</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 20px;
        }

        .form-container {
            max-width: 500px;
            margin: auto;
            padding: 20px;
            border: 1px solid #ccc;
            border-radius: 8px;
        }

        .form-container input[type="text"], .form-container input[type="date"] {
            width: 90%;
            padding: 10px;
            margin: 8px 0;
            display: inline-block;
            border: 1px solid #ccc;
            border-radius: 4px;
        }

        .form-container button {
            background-color: #28a745;
            color: white;
            border: none;
            padding: 12px 20px;
            border-radius: 5px;
            cursor: pointer;
            width: 100%;
        }

        .form-container button:hover {
            background-color: #218838;
        }

        .error-message {
            color: red;
        }
    </style>
</head>
<body>
<div class="form-container">
    <h1>New TODO</h1>

    <%-- Display an error message if it exists in the request --%>
    <% String errorMessage = (String) request.getAttribute("errorMessage"); %>
    <% if (errorMessage != null) { %>
    <p class="error-message"><%= errorMessage %>
    </p>
    <% } %>

    <form action="new" method="post">
        <label for="title"><b>Title:</b></label>
        <input type="text" id="title" name="title" required>

        <label for="description"><b>Description:</b></label>
        <input type="text" id="description" name="description" required>

        <label for="startDate"><b>Start date:</b></label>
        <input type="date" id="startDate" name="startDate" required>

        <label for="dueDate"><b>Target date:</b></label>
        <input type="date" id="dueDate" name="dueDate" required>

        <label for="statusId"><b>Status:</b></label>
        <select id="statusId" name="statusId" required>
            <%
                Collection<LabelDto> labelList = (Collection<LabelDto>) request.getAttribute("labelList");
                if (labelList != null) {
                    for (LabelDto item : labelList) {
            %>
            <option
                    value="<%= item.getId() %>"><%= item.getName() %>
            </option>
            <%
                    } // end for
                } // end if
            %>
        </select>

        <button type="submit">Save</button>
    </form>
</div>
</body>
</html>
