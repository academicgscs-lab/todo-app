<%@ page import="com.training.todo.infrastructure.controller.model.LabelDto" %>
<%@ page import="java.util.Collection" %>
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
