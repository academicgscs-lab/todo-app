package com.training.todo;

import com.training.todo.application.LabelManager;
import com.training.todo.application.TaskManager;
import com.training.todo.application.validation.ITaskValidation;
import com.training.todo.application.validation.creation.DateTaskValidation;
import com.training.todo.application.validation.creation.ObligatoryFieldsTaskValidation;
import com.training.todo.domain.Task;
import com.training.todo.domain.label.Label;
import com.training.todo.infrastructure.persistence.helpers.LabelPersistenceHelper;
import com.training.todo.infrastructure.persistence.helpers.TaskPersistenceHelper;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

import java.util.Collection;
import java.util.Optional;
import java.util.Vector;

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

        Vector<ITaskValidation> creationValidations = new Vector<>();
        creationValidations.add(new ObligatoryFieldsTaskValidation());
        creationValidations.add(new DateTaskValidation());
        TaskManager taskManager = new TaskManager(labelManager, creationValidations);

        loadLabels(labelManager);
        loadTasks(taskManager, labelManager);

        context.setAttribute("labelManager", labelManager);
        context.setAttribute("taskManager", taskManager);

    }
}
