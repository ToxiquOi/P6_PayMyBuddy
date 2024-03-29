package fr.ocr.paymybuddy.view;

import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.login.LoginI18n;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import fr.ocr.paymybuddy.entity.UserEntity;
import fr.ocr.paymybuddy.service.UserService;
import fr.ocr.paymybuddy.view.component.AppLogoComponent;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;


@AnonymousAllowed
@Route("login")
@PageTitle("Login | PayMyBuddy")
public class LoginView extends VerticalLayout implements BeforeEnterObserver {

    private final LoginForm loginForm = new LoginForm();
    private final Button inscriptionButton = new Button("Sign On");

    @Autowired
    public LoginView() {

        addClassName("login-view");
        setSizeFull();

        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);

        VerticalLayout centeredLayout = createCenteredLayout();
        setHorizontalComponentAlignment(Alignment.CENTER, centeredLayout);

        add(centeredLayout);
    }

    @Override
    public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {
        if (
                beforeEnterEvent.getLocation()
                .getQueryParameters()
                .getParameters()
                .containsKey("error")
        ) {
            loginForm.setError(true);
        }
    }

    private LoginI18n createLoginI18n() {
        LoginI18n i18n = LoginI18n.createDefault();

        LoginI18n.Form i18nForm = i18n.getForm();
        i18nForm.setTitle("");
        i18nForm.setUsername("Email");
        i18nForm.setPassword("Password");
        i18nForm.setSubmit("Log in");
        i18nForm.setForgotPassword("Forgot password");
        i18n.setForm(i18nForm);

        LoginI18n.ErrorMessage i18nErrorMessage = i18n.getErrorMessage();
        i18nErrorMessage.setTitle("Incorrect username or password");
        i18nErrorMessage.setMessage("Check that you have entered the correct username and password and try again.");
        i18n.setErrorMessage(i18nErrorMessage);

        return i18n;
    }

    private VerticalLayout createCenteredLayout() {
        AppLogoComponent appLogoComponent = new AppLogoComponent();
        LoginI18n i18n = createLoginI18n();
        VerticalLayout centeredLayout = new VerticalLayout(appLogoComponent, loginForm, inscriptionButton);

        inscriptionButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        inscriptionButton.addClickListener(event -> getUI().ifPresent(ui -> ui.navigate("inscription")));

        loginForm.setAction("login");
        loginForm.addLoginListener(event -> getUI().ifPresent(ui -> ui.navigate(PaymentView.class)));
        loginForm.setI18n(i18n);

        centeredLayout.setMaxWidth(120, Unit.MM);
        centeredLayout.setMinWidth(80, Unit.MM);
        centeredLayout.setHorizontalComponentAlignment(Alignment.CENTER, appLogoComponent, loginForm);
        centeredLayout.getStyle()
                .set("border", "solid 1px black")
                .set("border-radius", "2mm");

        return centeredLayout;
    }
}
