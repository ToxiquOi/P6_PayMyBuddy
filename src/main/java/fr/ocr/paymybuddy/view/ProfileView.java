package fr.ocr.paymybuddy.view;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import fr.ocr.paymybuddy.service.AuthenticationService;
import fr.ocr.paymybuddy.service.UserService;
import fr.ocr.paymybuddy.view.component.form.ProfileForm;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.security.PermitAll;

@PermitAll
@Route(value = "profile", layout = MainView.class)
public class ProfileView extends VerticalLayout {

    private final AuthenticationService authenticationService;
    private final UserService userService;

    private ProfileForm profileForm;

    @Autowired
    public ProfileView(AuthenticationService authenticationService, UserService userService) {
        this.authenticationService = authenticationService;
        this.userService = userService;

        addClassName("profile-view");
        setJustifyContentMode(JustifyContentMode.CENTER);
        setSizeFull();

        add(getProfileForm());
    }

    private ProfileForm getProfileForm() {
        profileForm = new ProfileForm(authenticationService.getAuthenticatedUser());
        profileForm.getPassword().setEnabled(false);
        disableForm();

        profileForm.addListener(ProfileForm.SaveEvent.class,
                saveEvent -> {
                    userService.saveUser(saveEvent.getSource().getUser());
                    disableForm();
                }
        );

        profileForm.addListener(ProfileForm.EditEvent.class, editEvent -> enableForm());

        profileForm.addListener(ProfileForm.CancelEvent.class, cancelEvent -> disableForm());

        setHorizontalComponentAlignment(Alignment.CENTER, profileForm);
        return profileForm;
    }

    public void enableForm() {
        profileForm.setFormEnabled(true);
    }

    public void disableForm() {
        profileForm.setFormEnabled(false);
    }
}
