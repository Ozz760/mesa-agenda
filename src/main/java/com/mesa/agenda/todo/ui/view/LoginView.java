package com.mesa.agenda.todo.ui.view;

import com.mesa.agenda.security.SecurityUtils;
import com.vaadin.flow.component.login.LoginOverlay;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver; // Import SecurityUtils
import com.vaadin.flow.router.Route;

import jakarta.annotation.security.PermitAll;

@PermitAll                          // allow everyone to see the login form
@Route("login")
public class LoginView extends LoginOverlay implements BeforeEnterObserver {

  public LoginView() {
    setAction("login");             // Spring Security’s default form action
    setOpened(true);
  }

  @Override
  public void beforeEnter(BeforeEnterEvent event) {
    // if the user is already logged in, send them to the main view
    if (SecurityUtils.isUserLoggedIn()) {
      event.forwardTo("");
    }
    // show the “bad credentials” message if ?error is present
    setError(
      event.getLocation()
           .getQueryParameters()
           .getParameters()
           .containsKey("error")
    );
  }
}
