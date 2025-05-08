package com.mesa.agenda.todo.ui.view;

import com.mesa.agenda.base.ui.component.ViewToolbar;
import com.mesa.agenda.todo.domain.Todo;
import com.mesa.agenda.todo.service.TodoService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Main;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Menu;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.lumo.LumoUtility;

import java.time.Clock;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Optional;

import static com.vaadin.flow.spring.data.VaadinSpringDataHelpers.toSpringPageRequest;

@Route("")
@PageTitle("Task List")
@Menu(order = 0, icon = "vaadin:clipboard-check", title = "Task List")
public class TodoView extends Main {

    final TextField description;
    final DatePicker dueDate;
    final Button createBtn;
    final Grid<Todo> todoGrid;
    private final TodoService todoService;
    private final Dialog todoDialog = new Dialog();
    private final TextArea dialogDescription = new TextArea("Description");
    private final DatePicker dialogDueDate = new DatePicker("Due Date");
    private final Button dialogSaveBtn = new Button("Save");
    private final Button dialogCancelBtn = new Button("Cancel");
    private Long editingTodoId = null;

    public TodoView(TodoService todoService, Clock clock) {
        this.todoService = todoService;

        description = new TextField();
        description.setPlaceholder("What do you want to do?");
        description.setAriaLabel("Task description");
        description.setMaxLength(Todo.DESCRIPTION_MAX_LENGTH);
        description.setMinWidth("30em");

        dueDate = new DatePicker();
        dueDate.setPlaceholder("Due date");
        dueDate.setAriaLabel("Due date");
        dueDate.setMinWidth("15em");

        createBtn = new Button("Create", event -> createTodo());
        createBtn.addThemeVariants(ButtonVariant.LUMO_PRIMARY);


        var dateTimeFormatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM).withZone(clock.getZone())
                .withLocale(getLocale());
        var dateFormatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM).withLocale(getLocale());

        todoGrid = new Grid<>();
        todoGrid.setItems(query -> todoService.list(toSpringPageRequest(query)).stream());
        todoGrid.addColumn(Todo::getDescription).setHeader("Description");
        todoGrid.addColumn(todo -> Optional.ofNullable(todo.getDueDate()).map(dateFormatter::format).orElse("Never"))
                .setHeader("Due Date");
        todoGrid.addColumn(todo -> dateTimeFormatter.format(todo.getCreationDate())).setHeader("Creation Date");

        todoGrid.addComponentColumn(todo -> {

            /**************************EDIT BUTTON*********************************/
            Button editButton = new Button(new Icon("lumo", "edit"));
            editButton.getElement().getStyle()
                    .set("color", "var(--lumo-body-text-color)");
            editButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
            editButton.setTooltipText("Edit this task");
            editButton.addClickListener(e -> {
                dialogDescription.setValue(todo.getDescription());
                dialogDueDate.setValue(todo.getDueDate());
                editingTodoId = todo.getId();
                todoDialog.open();
            });
            /***********************************************************************/

            /**************************DELETE BUTTON*********************************/
            Button deleteButton = new Button(new Icon("vaadin", "trash"));
            deleteButton.addThemeVariants(ButtonVariant.LUMO_ERROR, ButtonVariant.LUMO_TERTIARY_INLINE);
            deleteButton.getElement()
                    .getStyle()
                    .set("color", "var(--lumo-primary-color)");
            deleteButton.setTooltipText("Delete this task");
            deleteButton.addClickListener(e -> {
                todoService.deleteTodo(todo.getId());
                todoGrid.getDataProvider().refreshAll();  // Refresh the grid
                Notification.show("Task Deleted", 3000, Notification.Position.BOTTOM_END)
                        .addThemeVariants(NotificationVariant.LUMO_ERROR);
            });
            /**************************************************************/
            var actions = new HorizontalLayout(editButton, deleteButton);
            actions.setSpacing(false);
            actions.setPadding(false);
            return actions;
        }).setHeader("Actions").setAutoWidth(false).setFlexGrow(0);

        todoGrid.setSizeFull();

        /***************DOUBLE CLICK EVENT*********************/
        todoGrid.addItemDoubleClickListener(event -> {
            Todo todo = event.getItem();
            dialogDescription.setValue(todo.getDescription());
            dialogDueDate.setValue(todo.getDueDate());
            editingTodoId = todo.getId();
            todoDialog.open();
        });
        /******************************************************/

        setSizeFull();
        addClassNames(LumoUtility.BoxSizing.BORDER, LumoUtility.Display.FLEX, LumoUtility.FlexDirection.COLUMN,
                LumoUtility.Padding.MEDIUM, LumoUtility.Gap.SMALL);


        add(new ViewToolbar("Task List", ViewToolbar.group(description, dueDate, createBtn)));

        /*************************EDIT/VIEW POPUP**************************************/
        HorizontalLayout dialogButtons = new HorizontalLayout(dialogCancelBtn, dialogSaveBtn);


        dialogDescription.setWidthFull();
        dialogDescription.setMinHeight("6em");
        dialogDescription.setMaxHeight("6em");


        dialogDueDate.setWidthFull();

        dialogButtons.setJustifyContentMode(FlexComponent.JustifyContentMode.BETWEEN);
        dialogCancelBtn.setMinWidth("5em");
        dialogCancelBtn.setMaxWidth("5em");
        dialogSaveBtn.setMinWidth("5em");
        dialogSaveBtn.setMaxWidth("5em");


        var spacer = new Div();
        spacer.setHeight("10%");

        todoDialog.add(dialogDescription, dialogDueDate, spacer, dialogButtons);
        todoDialog.setHeaderTitle("Edit Task");
        todoDialog.setHeight("50%");

        dialogCancelBtn.addClickListener(e -> {
            todoDialog.close();
        });
        dialogCancelBtn.getStyle().set("background-color", "var(--lumo-primary-color)");

        dialogSaveBtn.addClickListener(e -> {
            if (editingTodoId != null) {
                todoService.updateTodo(editingTodoId, dialogDescription.getValue(), dialogDueDate.getValue());
            } else {
                todoService.createTodo(dialogDescription.getValue(), dialogDueDate.getValue());
            }

            todoGrid.getDataProvider().refreshAll();
            todoDialog.close();
        });
        dialogSaveBtn.getStyle().set("background-color", "var(--lumo-primary-color)");

        add(todoDialog);

        /**************************************************************/
        add(todoGrid);

    }

    private void createTodo() {
        String desc = description.getValue();
        LocalDate due = dueDate.getValue();

        if (editingTodoId != null) {
            todoService.updateTodo(editingTodoId, desc, due);
            editingTodoId = null;
            createBtn.setText("Create");
        } else {
            todoService.createTodo(desc, due);
        }

        // Reset form
        todoGrid.getDataProvider().refreshAll();
        description.clear();
        dueDate.clear();

        Notification.show("Task saved", 3000, Notification.Position.BOTTOM_END)
                .addThemeVariants(NotificationVariant.LUMO_SUCCESS);
    }

}
