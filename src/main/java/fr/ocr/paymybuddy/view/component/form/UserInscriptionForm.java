package fr.ocr.paymybuddy.view.component.form;

import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import fr.ocr.paymybuddy.entity.UserEntity;


public class UserInscriptionForm extends AbstractUserForm {

    private final Button returnButton = new Button("Return");
    private final Button createButton = new Button("Create !");

    public UserInscriptionForm() {
        super();
        setUser(new UserEntity());
        createDefaultLayout();
    }


    @Override
    protected HorizontalLayout createButtonsLayout() {
        returnButton.addThemeVariants(ButtonVariant.LUMO_ERROR);
        returnButton.addClickShortcut(Key.ESCAPE);

        createButton.addThemeVariants(ButtonVariant.LUMO_SUCCESS, ButtonVariant.LUMO_PRIMARY);
        createButton.addClickShortcut(Key.ENTER);

        createButton.addClickListener(event -> validateAndSave());
        returnButton.addClickListener(event -> fireEvent(new ReturnEvent(this)));

        return new HorizontalLayout(returnButton, createButton);
    }

    @Override
    protected AbstractUserFormEvent createSaveEvent() {
        return new CreateEvent(this, user);
    }

    public static class CreateEvent extends AbstractUserForm.AbstractUserFormEvent {
        CreateEvent(UserInscriptionForm source, UserEntity contact) {
            super(source, contact);
        }
    }

    public static class ReturnEvent extends AbstractUserForm.AbstractUserFormEvent {
        ReturnEvent(UserInscriptionForm source) {
            super(source, null);
        }
    }
}
