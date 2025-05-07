package com.mesa.agenda.todo.ui.view;

import com.mesa.agenda.common.security.SecurityUtils;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.Route;

@Route("todo-login")
public class LoginView extends Div implements BeforeEnterObserver {

  @Override
  public void beforeEnter(BeforeEnterEvent event) {
    System.out.println("Entering LoginView: User logged in status: " + SecurityUtils.isUserLoggedIn());
    if (SecurityUtils.isUserLoggedIn()) {
      System.out.println("Redirecting to /home");
      UI.getCurrent().navigate("/home");
    }
  }
}
