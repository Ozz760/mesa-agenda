package com.mesa.agenda.todo.ui.view;

import java.time.Clock;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Optional;

import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.mesa.agenda.base.ui.component.ViewToolbar;
import com.mesa.agenda.todo.domain.Student;
import com.mesa.agenda.todo.domain.Todo;
import com.mesa.agenda.todo.service.StudentService;
import com.mesa.agenda.todo.service.TodoService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Main;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Menu;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.lumo.LumoUtility;

import jakarta.annotation.security.RolesAllowed; // Add this import for PageRequest

@RolesAllowed("STUDENT") // Restrict access to users with the "STUDENT" role
@Route("/todoview") // Route for the TodoView page
@PageTitle("Task List")
@Menu(order = 0, icon = "vaadin:clipboard-check", title = "Task List")
public class TodoView extends Main {

        private final TodoService todoService;
        private final Grid<Student> studentGrid; // New grid for displaying students

        final TextField description;
        final DatePicker dueDate;
        final Button createBtn;
        final Grid<Todo> todoGrid;

        public TodoView(TodoService todoService, Clock clock, StudentService studentService) {
                this.todoService = todoService;

                // Input fields for creating a new task
                description = new TextField();
                description.setPlaceholder("What do you want to do?");
                description.setAriaLabel("Task description");
                description.setMaxLength(Todo.DESCRIPTION_MAX_LENGTH);
                description.setMinWidth("20em");

                dueDate = new DatePicker();
                dueDate.setPlaceholder("Due date");
                dueDate.setAriaLabel("Due date");

                createBtn = new Button("Create", event -> createTodo());
                createBtn.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

                // Formatters for displaying dates
                var dateTimeFormatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)
                                .withZone(clock.getZone())
                                .withLocale(getLocale());
                var dateFormatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM).withLocale(getLocale());

                // Grid to display tasks
                todoGrid = new Grid<>();
                todoGrid.setItems(query -> todoService
                                .listForUser(getLoggedInUsername(),
                                                PageRequest.of(query.getPage(), query.getPageSize()))
                                .stream());
                todoGrid.addColumn(Todo::getDescription).setHeader("Description");
                todoGrid.addColumn(todo -> Optional.ofNullable(todo.getDueDate()).map(dateFormatter::format)
                                .orElse("Never"))
                                .setHeader("Due Date");
                todoGrid.addColumn(todo -> dateTimeFormatter.format(todo.getCreationDate())).setHeader("Creation Date");
                todoGrid.setSizeFull();

                // Grid to display students
                studentGrid = new Grid<>(Student.class);
                studentGrid.setItems(studentService.getAllStudents());
                studentGrid.setColumns("username", "firstName", "lastName", "email", "role", "active", "createdAt");
                studentGrid.setSizeFull();

                // Layout and styling
                setSizeFull();
                addClassNames(LumoUtility.BoxSizing.BORDER, LumoUtility.Display.FLEX, LumoUtility.FlexDirection.COLUMN,
                                LumoUtility.Padding.MEDIUM, LumoUtility.Gap.SMALL);

                add(new ViewToolbar("Task List", ViewToolbar.group(description, dueDate, createBtn)));
                add(todoGrid);
                add(new ViewToolbar("Students", studentGrid));
        }

        private void createTodo() {
                // Create a new task for the logged-in user
                todoService.createTodoForUser(description.getValue(), dueDate.getValue(), getLoggedInUsername());
                todoGrid.getDataProvider().refreshAll();
                description.clear();
                dueDate.clear();
                Notification.show("Task added", 3000, Notification.Position.BOTTOM_END)
                                .addThemeVariants(NotificationVariant.LUMO_SUCCESS);
        }

        private String getLoggedInUsername() {
                // Get the username of the currently logged-in user
                Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
                return authentication.getName();
        }
}
