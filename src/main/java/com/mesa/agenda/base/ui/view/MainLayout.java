package com.mesa.agenda.base.ui.view;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.avatar.Avatar;
import com.vaadin.flow.component.avatar.AvatarVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.menubar.MenuBar;
import com.vaadin.flow.component.menubar.MenuBarVariant;
import com.vaadin.flow.component.orderedlayout.Scroller;
import com.vaadin.flow.component.sidenav.SideNav;
import com.vaadin.flow.component.sidenav.SideNavItem;
import com.vaadin.flow.router.Layout;
import com.vaadin.flow.server.menu.MenuConfiguration;
import com.vaadin.flow.server.menu.MenuEntry;

import static com.vaadin.flow.theme.lumo.LumoUtility.*;

@Layout
public final class MainLayout extends AppLayout {

    public MainLayout() {
        //This layout is intentionally minimal — no sidebar, no header
        // The toolbar and content are handled by individual views.
        // This file is still important as it tells Vaadin where to put routing content

    }
}








 //   public MainLayout() {
//        setPrimarySection(Section.DRAWER);
//        addToDrawer(createHeader());//new Scroller(createSideNav())

//        addToNavbar(createHeader());
   // }

//    private Div createHeader() {
//        // TODO Replace with real application logo and name
//        var appLogo = VaadinIcon.CUBES.create();
//        appLogo.addClassNames(TextColor.PRIMARY, IconSize.LARGE);
//
//        var appName = new Span("Mesa Agenda");
//        appName.addClassNames(FontWeight.SEMIBOLD, FontSize.LARGE);
//
//        var header = new Div(appLogo, appName);
//        header.addClassNames(Display.FLEX, Padding.MEDIUM, Gap.MEDIUM, AlignItems.CENTER);
//        return header;
//    }

//    private SideNav createSideNav() {
//        var nav = new SideNav();
//        nav.addClassNames(Margin.Horizontal.MEDIUM);
//        MenuConfiguration.getMenuEntries().forEach(entry -> nav.addItem(createSideNavItem(entry)));
//        return nav;
//    }
//
//    private SideNavItem createSideNavItem(MenuEntry menuEntry) {
//        if (menuEntry.icon() != null) {
//            return new SideNavItem(menuEntry.title(), menuEntry.path(), new Icon(menuEntry.icon()));
//        } else {
//            return new SideNavItem(menuEntry.title(), menuEntry.path());
//        }
//    }
//}
