package fr.ocr.paymybuddy.view.component.nav;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.router.RouterLink;

import fr.ocr.paymybuddy.service.AuthenticationService;
import fr.ocr.paymybuddy.view.ContactView;
import fr.ocr.paymybuddy.view.MainView;
import fr.ocr.paymybuddy.view.PaymentView;
import fr.ocr.paymybuddy.view.ProfileView;
import fr.ocr.paymybuddy.view.component.AppLogoComponent;

public class MainHeader extends HorizontalLayout {

    private final AuthenticationService securityService;

    public MainHeader(AuthenticationService securityService) {
        this.securityService = securityService;

        addClassNames("py-0", "px-m");
        setDefaultVerticalComponentAlignment(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.START);
        setWidth("100%");
        setHeight(14, Unit.MM);
        setMaxHeight(20, Unit.MM);

        Button logout = new Button("Log out", e -> this.securityService.logout());

        RouterLink profileLnk = new RouterLink("Profile", ProfileView.class);
        RouterLink contactLnk = new RouterLink("Contact", ContactView.class);
        RouterLink transferLnk = new RouterLink("Transfer", PaymentView.class);


        add(/*new DrawerToggle(),*/ getLogo(), transferLnk, profileLnk, contactLnk, logout);
    }

    private Component getLogo() {
        AppLogoComponent logo = new AppLogoComponent();
        logo.setHeight(8, Unit.MM);
        logo.setWidth(46, Unit.MM);
        logo.getStyle().set("font-size", "7mm");

        HorizontalLayout logoDiv = new HorizontalLayout(logo);
        logoDiv.setDefaultVerticalComponentAlignment(Alignment.START);
        logoDiv.setJustifyContentMode(JustifyContentMode.START);

        logoDiv.getStyle().set("margin-top", "-3mm");

        expand(logoDiv);
        return logoDiv;
    }
}
