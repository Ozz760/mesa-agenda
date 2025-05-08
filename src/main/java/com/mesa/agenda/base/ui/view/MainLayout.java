package com.mesa.agenda.base.ui.view;

import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.router.Layout;

@Layout
public final class MainLayout extends AppLayout {

    public MainLayout() {
        //This layout is intentionally minimal — no sidebar, no header
        // The toolbar and content are handled by individual views.
        // This file is still important as it tells Vaadin where to put routing content

    }
}
