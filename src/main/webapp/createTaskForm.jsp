<%@ page import="java.util.Collection" %>
<%@ page import="com.training.todo.controller.model.LabelDto" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Todo App</title>
    <link rel="stylesheet" type="text/css" href="../css/form.css">
</head>
<jsp:include page="header.jsp"></jsp:include>
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
