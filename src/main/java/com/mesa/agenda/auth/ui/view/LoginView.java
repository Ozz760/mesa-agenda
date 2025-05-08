package com.mesa.agenda.auth.ui.view;

import com.mesa.agenda.base.ui.component.ViewToolbar;
import com.vaadin.flow.component.html.Main;
import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.component.orderedlayout.FlexComponent.JustifyContentMode;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.theme.lumo.LumoUtility;

@Route("login")
@PageTitle("Login")
public class LoginView extends Main implements BeforeEnterObserver {

  private final LoginForm loginForm = new LoginForm();

  public LoginView() {
    addClassNames(LumoUtility.BoxSizing.BORDER, LumoUtility.Display.FLEX,
        LumoUtility.FlexDirection.COLUMN, LumoUtility.Padding.MEDIUM);

    add(new ViewToolbar("Login"));

    var layout = new VerticalLayout();
    layout.setAlignItems(Alignment.CENTER);
    layout.setJustifyContentMode(JustifyContentMode.CENTER);
    layout.setHeightFull();

    loginForm.addLoginListener(event -> {
      // Demo login - always succeeds
      String username = event.getUsername();
      // In a real app, validate credentials here
      getUI().ifPresent(ui -> ui.navigate("todo"));
      Notification.show("Welcome back, " + username + "!", 3000,
          Notification.Position.BOTTOM_END);
    });

    var signupLink = new RouterLink("Don't have an account? Sign up", SignupView.class);
    signupLink.getStyle().set("margin-top", "1rem");
    signupLink.getStyle().set("color", "blue");
    signupLink.getStyle().set("text-decoration", "underline");

    layout.add(loginForm, signupLink);
    add(layout);
  }

  @Override
  public void beforeEnter(BeforeEnterEvent event) {
    if (event.getLocation().getQueryParameters().getParameters().containsKey("error")) {
      loginForm.setError(true);
    }
  }
}