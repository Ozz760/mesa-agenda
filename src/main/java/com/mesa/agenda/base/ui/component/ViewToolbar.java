package com.mesa.agenda.base.ui.component;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.avatar.Avatar;
import com.vaadin.flow.component.avatar.AvatarVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Header;
import com.vaadin.flow.component.menubar.MenuBar;
import com.vaadin.flow.component.menubar.MenuBarVariant;
import com.vaadin.flow.theme.lumo.LumoUtility.*;
import com.vaadin.flow.component.html.Image;


public final class ViewToolbar extends Composite<Header> {

    public ViewToolbar(String viewTitle, Component... components) {
        addClassNames(Display.FLEX, FlexDirection.COLUMN, JustifyContent.BETWEEN, AlignItems.STRETCH, Gap.MEDIUM,
                FlexDirection.Breakpoint.Medium.ROW, AlignItems.Breakpoint.Medium.CENTER);

//        var drawerToggle = new DrawerToggle();
//        drawerToggle.addClassNames(Margin.NONE);


        /******************************************************/
        // Create MESA logo image
        Image logo = new Image("images/mesa-logo.png", "MESA Logo");
        logo.setHeight("64px"); // adjust size as needed
        logo.getStyle().set("margin-right", "0.5rem");

        var title = new H1(viewTitle);
        title.addClassNames(FontSize.XLARGE, Margin.NONE, FontWeight.LIGHT);

        // Combine logo and title in a row
        var logoAndTitle = new Div(logo, title);
        logoAndTitle.getStyle()
                .set("display", "flex")
                .set("align-items", "center")
                .set("gap", "0.5rem");

        /************************************************************/
        var spacer = new Div();
        spacer.getStyle().set("flex-grow", "1");

        var userMenu = createUserMenu();

        Component rightSection;
        if (components.length > 0) {
            rightSection = group(components);
            rightSection.getElement().getStyle()
                    .set("display", "flex")
                    .set("align-items", "center")
                    .set("gap", "0.5rem");
        } else {
            rightSection = new Div();
        }


        var toolbarRow = new Div( logoAndTitle, rightSection,spacer, userMenu);
        toolbarRow.getStyle()
                .set("display", "flex")
                .set("align-items", "center")
                .set("width", "100%");
        toolbarRow.addClassNames(Padding.MEDIUM, Gap.MEDIUM);

        getContent().removeAll();
        getContent().add(toolbarRow);
    }

    public static Component group(Component... components) {
        var group = new Div(components);
        group.addClassNames(Display.FLEX, FlexDirection.COLUMN, AlignItems.STRETCH, Gap.SMALL,
                FlexDirection.Breakpoint.Medium.ROW, AlignItems.Breakpoint.Medium.CENTER);
        return group;
    }

    private Component createUserMenu() {
        // TODO Replace with real user information and actions
        var avatar = new Avatar("John Smith");
        avatar.addThemeVariants(AvatarVariant.LUMO_XSMALL);
        avatar.addClassNames(Margin.Right.SMALL);
        avatar.setColorIndex(5);

        var userMenu = new MenuBar();
        userMenu.addThemeVariants(MenuBarVariant.LUMO_TERTIARY_INLINE);
        userMenu.addClassNames(Margin.MEDIUM);

        var userMenuItem = userMenu.addItem(avatar);
        userMenuItem.add("John Smith");
        userMenuItem.getSubMenu().addItem("View Profile");
        userMenuItem.getSubMenu().addItem("Manage Settings");
        userMenuItem.getSubMenu().addItem("Logout");

        return userMenu;
    }
}
