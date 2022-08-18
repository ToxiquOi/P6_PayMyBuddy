package fr.ocr.paymybuddy.view;

import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import fr.ocr.paymybuddy.service.AuthenticationService;
import fr.ocr.paymybuddy.view.component.nav.MainHeader;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.security.PermitAll;

@PermitAll
@Route("")
@PageTitle("Main | PayMyBuddy")
public class MainView extends AppLayout {

    private final AuthenticationService authenticationService;

    @Autowired
    public MainView(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
//        createDrawer();
        createHeader();
        setPrimarySection(Section.DRAWER);
    }

    private void createHeader() {
        MainHeader mainHeader = new MainHeader(authenticationService);
        addToNavbar(mainHeader);
    }

    private void createDrawer() {
//        addToDrawer(new MainDrawer());
    }
}
