package com.mesa.agenda.base.ui.component;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.avatar.Avatar;
import com.vaadin.flow.component.avatar.AvatarVariant;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Header;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.menubar.MenuBar;
import com.vaadin.flow.component.menubar.MenuBarVariant;
import com.vaadin.flow.theme.lumo.LumoUtility.*;

public final class ViewToolbar extends Composite<Header> {

    private final Button toggleDark = new Button();


    public ViewToolbar(String viewTitle, Component... components) {
        addClassNames(Display.FLEX, FlexDirection.COLUMN, JustifyContent.BETWEEN, AlignItems.STRETCH, Gap.MEDIUM,
                FlexDirection.Breakpoint.Medium.ROW, AlignItems.Breakpoint.Medium.CENTER);


        // MESA logo image
        Image logo = new Image("images/mesa-logo.png", "MESA Logo");
        logo.setHeight("96px");
        logo.getStyle().set("margin-right", "0.5rem");

        var title = new H1(viewTitle);
        title.addClassNames(FontSize.XXLARGE, Margin.NONE, FontWeight.EXTRABOLD);

        var spacer = new Div();
        spacer.getStyle().set("flex-grow", "1");

        var logoAndTitle = new Div(logo, title);
        logoAndTitle.getStyle()
                .set("display", "flex")
                .set("align-items", "center")
                .set("gap", "2.0rem");



        var userMenu = createUserMenu();

        Component rightSection;
        if (components.length > 0) {
            rightSection = group(components);
            rightSection.getElement().getStyle()
                    .set("display", "flex")
                    .set("align-items", "center")
                    .set("gap", "0.75rem");

        } else {
            rightSection = new Div();
        }


        var toolbarRow = new Div(logoAndTitle, rightSection, spacer, userMenu);
        toolbarRow.getStyle()
                .set("display", "flex")
                .set("align-items", "center")
                .set("width", "100%");
        toolbarRow.addClassNames(Gap.MEDIUM);

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
        avatar.addClassNames(Margin.Right.SMALL);
        avatar.getElement().getStyle()
                .set("background-color", "var(--lumo-primary-color)")
                .set("color", "white")
                .set("width", "2.2rem")
                .set("height", "2.2rem")
                .set("font-size", "18px");

        toggleDark.setIcon(VaadinIcon.MOON.create());
        toggleDark.addClickListener(e -> toggleDarkTheme());
        toggleDark.getElement().getStyle()
                .set("color", "black")
                .set("background", "none")
                .set("font-size", "20px")
                .set("margin-left", "0.5rem");


        var userMenu = new MenuBar();
        userMenu.addThemeVariants(MenuBarVariant.LUMO_TERTIARY_INLINE);

        var userMenuItem = userMenu.addItem(avatar);
        userMenuItem.getSubMenu().addItem("View Profile");
        userMenuItem.getSubMenu().addItem("Manage Settings");
        userMenuItem.getSubMenu().addItem("Logout");

        var wrapper = new Div(toggleDark, userMenu);
        wrapper.getStyle()
                .set("display", "flex")
                .set("align-items", "center");
        return wrapper;
    }


    private void toggleDarkTheme() {
        String js = """
                    const root = document.documentElement;
                    const isDark = root.getAttribute('theme') === 'dark';
                    root.setAttribute('theme', isDark ? '' : 'dark');
                    return isDark ? 'light' : 'dark';
                """;

        getContent().getElement().executeJs(js).then(String.class, theme -> {
            Icon icon;
            if ("dark".equals(theme)) {
                icon = VaadinIcon.SUN_O.create();
                icon.getElement().getStyle()
                        .set("color", "white");
            } else {
                icon = VaadinIcon.MOON.create();
                icon.getElement().getStyle()
                        .remove("color"); // resets to default
            }
            toggleDark.setIcon(icon);
        });
    }


}
