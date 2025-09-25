package com.training.todo.controller.servlet;

import com.training.todo.application.LabelManager;
import com.training.todo.application.TaskManager;
import com.training.todo.application.exceptions.InvalidTaskException;
import com.training.todo.controller.model.TaskDto;
import com.training.todo.controller.service.LabelService;
import com.training.todo.controller.service.TaskService;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.LocalDate;

@WebServlet("/TodoList/new")
public class NewTaskServlet extends HttpServlet {
    private TaskService taskService;
    private LabelService labelService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);

        ServletContext context = getServletContext();
        TaskManager taskManager = (TaskManager) context.getAttribute("taskManager");
        LabelManager labelManager = (LabelManager) context.getAttribute("labelManager");
        labelService = new LabelService(labelManager);
        taskService = new TaskService(taskManager, labelManager);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("labelList", labelService.getLabels());
        req.getRequestDispatcher("/taskForm.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        TaskDto dto = mapToDto(req);
        try {
            taskService.createTask(dto);
            resp.sendRedirect(req.getContextPath());
        } catch (InvalidTaskException e) {
            req.setAttribute("errorMessage", e.getMessage());
            req.getRequestDispatcher("/taskForm.jsp").forward(req, resp);
        }
    }

    public static TaskDto mapToDto(HttpServletRequest req) {
        LocalDate startDate = LocalDate.parse(req.getParameter("startDate"));
        LocalDate dueDate = LocalDate.parse(req.getParameter("dueDate"));

        return TaskDto.builder()
                .id(req.getParameter("id"))
                .title(req.getParameter("title"))
                .description(req.getParameter("description"))
                .status(req.getParameter("statusId"))
                .startDate(startDate)
                .dueDate(dueDate)
                .build();
    }
}
