package com.training.todo.infrastructure.controller.servlet;

import com.training.todo.core.usecases.exceptions.InvalidTaskException;
import com.training.todo.infrastructure.controller.model.TaskDto;
import com.training.todo.infrastructure.controller.service.TaskService;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/TodoList")
public class TaskServlet extends HttpServlet {
    private TaskService taskService;
    private List<List<TaskDto>> taskList;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        ServletContext context = getServletContext();
        taskService = (TaskService) context.getAttribute("taskService");
        taskList = taskService.getTaskListSegmented();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");

        if (action == null) {
            // list files
            req.setAttribute("taskList", taskService.getTasks());
            req.getRequestDispatcher("todoList.jsp").forward(req, resp);
        } else if ("delete".equals(action)) {
            showDeleteConfirmation(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String action = req.getParameter("action");
        if ("confirmDelete".equals(action)) {
            confirmDelete(req, resp);
        }
        resp.sendRedirect(req.getContextPath() + "/TodoList");
    }

    private void showDeleteConfirmation(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String id = req.getParameter("id");
        TaskDto task = taskService.getTask(id);
        req.setAttribute("task", task);
        RequestDispatcher dispatcher = req.getRequestDispatcher("deleteConfirm.jsp");
        dispatcher.forward(req, resp);
    }

    private void confirmDelete(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String id = req.getParameter("id");
        try {
            taskService.deleteTask(id);
        } catch (InvalidTaskException e) {
            req.setAttribute("errorMessage", e.getMessage());
            req.getRequestDispatcher("TodoList").forward(req, resp);
        }
    }
}
