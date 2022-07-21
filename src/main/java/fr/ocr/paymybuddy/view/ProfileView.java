package fr.ocr.paymybuddy.view;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import fr.ocr.paymybuddy.entity.UserEntity;
import fr.ocr.paymybuddy.service.AuthenticationService;
import fr.ocr.paymybuddy.service.UserService;
import fr.ocr.paymybuddy.service.WalletService;
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


    @Autowired
    public ProfileView(AuthenticationService authenticationService, UserService userService, WalletService walletService) {
        this.authenticationService = authenticationService;
        this.userService = userService;
        this.walletService = walletService;

        addClassName("profile-view");
        setJustifyContentMode(JustifyContentMode.CENTER);
        setSizeFull();

        viewWalletButton.addClickListener(buttonClickEvent ->  showWalletDialog());


        getProfileForm();
        viewWalletButton.setWidth(profileForm.getWidth());
        setHorizontalComponentAlignment(Alignment.CENTER, viewWalletButton);
        add(profileForm, viewWalletButton);
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

    private void showWalletDialog() {
        UserEntity currentUser = userService.getCurrentUser();
        WalletDialog dialog = new WalletDialog(currentUser.getWallet());

        dialog.addSendListener((event) -> {
           this.walletService.addMoneyToWallet(event.getCreditWalletModel().getValue(), currentUser.getWallet());
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
