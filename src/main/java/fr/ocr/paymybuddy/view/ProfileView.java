package fr.ocr.paymybuddy.view;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import fr.ocr.paymybuddy.entity.UserEntity;
import fr.ocr.paymybuddy.exception.UserPasswordException;
import fr.ocr.paymybuddy.service.AuthenticationService;
import fr.ocr.paymybuddy.service.UserService;
import fr.ocr.paymybuddy.service.WalletService;
import fr.ocr.paymybuddy.view.component.dialog.PasswordUpdateDialog;
import fr.ocr.paymybuddy.view.component.dialog.WalletDialog;
import fr.ocr.paymybuddy.view.component.form.ProfileForm;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.security.PermitAll;

@PermitAll
@Route(value = "profile", layout = MainView.class)
public class ProfileView extends VerticalLayout {

    private final AuthenticationService authenticationService;
    private final UserService userService;
    private final WalletService walletService;

    private ProfileForm profileForm;
    private Button viewWalletButton = new Button("Portefeuille");
    private Button updatePasswordButton = new Button("Change password");


    @Autowired
    public ProfileView(AuthenticationService authenticationService, UserService userService, WalletService walletService) {
        this.authenticationService = authenticationService;
        this.userService = userService;
        this.walletService = walletService;

        addClassName("profile-view");
        setJustifyContentMode(JustifyContentMode.CENTER);
        setSizeFull();

        viewWalletButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        viewWalletButton.addClickListener(buttonClickEvent ->  showWalletDialog());

        updatePasswordButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        updatePasswordButton.addClickListener(buttonClickEvent -> showUpdatePasswordDialog());

        getProfileForm();
        viewWalletButton.setWidth(profileForm.getWidth());
        updatePasswordButton.setWidth(profileForm.getWidth());
        setHorizontalComponentAlignment(Alignment.CENTER, viewWalletButton);
        setHorizontalComponentAlignment(Alignment.CENTER, updatePasswordButton);
        add(profileForm, viewWalletButton, updatePasswordButton);
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

    private void showUpdatePasswordDialog() {
        UserEntity currentUser = userService.getCurrentUser();
        PasswordUpdateDialog dialog = new PasswordUpdateDialog(currentUser);

        dialog.addUpdatePasswordListener((event) -> {
            try {
                userService.updatePassword(event.getUser());
                UI.getCurrent().access(() -> {
                    Notification notif = new Notification();
                    notif.add(new Text("Update done "), new Icon("lumo", "checkmark"));
                    notif.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
                    notif.setDuration(3600);
                    notif.open();
                });
            } catch (UserPasswordException e) {
                UI.getCurrent().access(() -> {
                    Notification notif = Notification.show(e.getMessage());
                    notif.addThemeVariants(NotificationVariant.LUMO_ERROR);
                });
            }
        });

        dialog.open();
    }

    private void showWalletDialog() {
        UserEntity currentUser = userService.getCurrentUser();
        WalletDialog dialog = new WalletDialog(currentUser.getWallet());

        dialog.addSendListener((event) -> {
           this.walletService.addMoneyToWallet(event.getCredit(), currentUser.getWallet());
           this.walletService.save(currentUser.getWallet());
           dialog.updateDialog(currentUser.getWallet());
        });

        dialog.open();
    }

    public void enableForm() {
        profileForm.setFormReadOnly(false);
    }

    public void disableForm() {
        profileForm.setFormReadOnly(true);
    }
}
