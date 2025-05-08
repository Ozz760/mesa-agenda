package com.mesa.agenda.auth.ui.view;

import com.mesa.agenda.base.ui.component.ViewToolbar;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.Main;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.component.orderedlayout.FlexComponent.JustifyContentMode;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.theme.lumo.LumoUtility;

@Route("signup")
@PageTitle("Sign Up")
public class SignupView extends Main {

  public SignupView() {
    addClassNames(LumoUtility.BoxSizing.BORDER, LumoUtility.Display.FLEX,
        LumoUtility.FlexDirection.COLUMN, LumoUtility.Padding.MEDIUM);

    add(new ViewToolbar("Sign Up"));

    var layout = new VerticalLayout();
    layout.setAlignItems(Alignment.CENTER);
    layout.setJustifyContentMode(JustifyContentMode.CENTER);
    layout.setHeightFull();
    layout.setMaxWidth("500px");
    layout.getStyle().set("margin", "0 auto");

    var firstName = new TextField("First Name");
    var lastName = new TextField("Last Name");
    var email = new TextField("Email");
    var password = new PasswordField("Password");
    var confirmPassword = new PasswordField("Confirm Password");

    var fields = new TextField[] { firstName, lastName, email };
    for (var field : fields) {
      field.setWidthFull();
    }
    password.setWidthFull();
    confirmPassword.setWidthFull();

    var signupButton = new Button("Sign Up", _ -> {
      // Basic validation
      if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() ||
          password.isEmpty() || confirmPassword.isEmpty()) {
        Notification.show("Please fill in all fields", 3000,
            Notification.Position.BOTTOM_END);
        return;
      }

      if (!password.getValue().equals(confirmPassword.getValue())) {
        Notification.show("Passwords don't match", 3000,
            Notification.Position.BOTTOM_END);
        return;
      }

      // Demo signup - always succeeds
      // In a real app, validate email format and create user account here
      getUI().ifPresent(ui -> ui.navigate("login"));
      Notification.show("Account created! Please login.", 3000,
          Notification.Position.BOTTOM_END);
    });
    signupButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
    signupButton.setWidthFull();

    var loginLink = new RouterLink("Already have an account? Login", LoginView.class);
    loginLink.getStyle().set("margin-top", "1rem");

    layout.add(firstName, lastName, email, password, confirmPassword,
        signupButton, loginLink);
    add(layout);
  }
}