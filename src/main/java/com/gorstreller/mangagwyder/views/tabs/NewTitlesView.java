package com.gorstreller.mangagwyder.views.tabs;

import com.gorstreller.mangagwyder.views.base.BaseLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;

@Route(value = "new-titles")
@RouteAlias(value = "new-titles")
public class NewTitlesView extends BaseLayout {

    public NewTitlesView() {
        super();

        VerticalLayout titlesList = new VerticalLayout();
        titlesList.addClassName("titles-list");

        addFooter();
    }
}
