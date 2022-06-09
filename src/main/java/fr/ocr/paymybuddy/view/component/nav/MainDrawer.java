package fr.ocr.paymybuddy.view.component.nav;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.RouterLink;
import fr.ocr.paymybuddy.view.ListView;

public class MainDrawer extends VerticalLayout {
    public MainDrawer() {
        createRouterLinks();
    }

    private void createRouterLinks() {
        RouterLink listLink = new RouterLink("List", ListView.class);
        add(listLink);
    }
}
