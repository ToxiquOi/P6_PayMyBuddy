package fr.ocr.paymybuddy.view;

import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import fr.ocr.paymybuddy.service.UserService;
import fr.ocr.paymybuddy.view.component.AppLogoComponent;
import fr.ocr.paymybuddy.view.component.form.UserInscriptionForm;
import org.springframework.beans.factory.annotation.Autowired;

@AnonymousAllowed
@PageTitle("Inscription | PayMyBuddy")
@Route("inscription")
public class InscriptionView extends VerticalLayout {

    UserInscriptionForm userForm = new UserInscriptionForm();

    @Autowired
    public InscriptionView(UserService userService) {
        addClassName("inscription-view");

        AppLogoComponent logo = new AppLogoComponent();
        logo.getStyle().set("margin-top", "auto");

        H1 title = new H1("Sign on Pay My Buddy !");

        userForm.setFieldFullWidth();
        userForm.setWidth("35%");
        userForm.addListener(UserInscriptionForm.ReturnEvent.class, returnEvent -> navigateToLogin());
        userForm.addListener(UserInscriptionForm.CreateEvent.class, createEvent -> {
            userService.saveUser(createEvent.getUserEntity());
            navigateToLogin();
        });

        setHorizontalComponentAlignment(Alignment.START, logo);
        setHorizontalComponentAlignment(Alignment.CENTER, title);
        setHorizontalComponentAlignment(Alignment.CENTER, userForm);
        add(logo, title, userForm);
    }

    private void navigateToLogin() {
        getUI().ifPresent(ui -> ui.navigate("login"));
    }
}
