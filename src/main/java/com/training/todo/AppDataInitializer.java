package com.training.todo;

import com.training.todo.application.LabelManager;
import com.training.todo.application.TaskManager;
import com.training.todo.infrastructure.persistence.helpers.LabelPersistenceHelper;
import com.training.todo.infrastructure.persistence.helpers.TaskPersistenceHelper;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

@WebListener
public class AppDataInitializer implements ServletContextListener {
    private final static String HOME = String.format("%s/%s", "C:\\Users\\Santiago.Concha\\Documents\\tomcatDemoSample", "persistence");

    private void loadLabels(LabelManager manager) {
        new LabelPersistenceHelper(HOME)
                .read()
                .ifPresent(manager::appendLabelCollection);
    }

    private void loadTasks(TaskManager manager, LabelManager labelManager) {
        new TaskPersistenceHelper(HOME, labelManager)
                .read()
                .ifPresent(manager::appendTasks);
    }

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext context = sce.getServletContext();
        LabelManager labelManager = new LabelManager();

        TaskManager taskManager = new TaskManager(labelManager);

        loadLabels(labelManager);
        loadTasks(taskManager, labelManager);

        context.setAttribute("labelManager", labelManager);
        context.setAttribute("taskManager", taskManager);

    }
}
