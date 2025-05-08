package com.mesa.agenda.base.ui.view;

import com.mesa.agenda.auth.ui.view.LoginView;
import com.vaadin.flow.component.html.Main;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.Route;

@Route("")
public class RootView extends Main implements BeforeEnterObserver {
  @Override
  public void beforeEnter(BeforeEnterEvent event) {
    event.forwardTo(LoginView.class);
  }
}