package fr.ocr.paymybuddy.view.component.form;

import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.button.Button;

import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import fr.ocr.paymybuddy.entity.UserEntity;
import fr.ocr.paymybuddy.security.MyUserDetails;



public class ProfileForm extends AbstractUserForm {

    private final Button save = new Button("Save");
    private final Button cancel = new Button("Cancel");
    private final Button edit = new Button("Edit");

    public ProfileForm(MyUserDetails user) {
        super();
        setUser(user.user());
        setMaxWidth(100, Unit.MM);

        setFormReadOnly(false);

        add(this.getFirstname(),
                this.getLastname(),
                this.getEmail(),
                this.getBirthdate(),
                createButtonsLayout()
        );
    }

    public void setFormReadOnly(boolean enabled) {
        getFirstname().setReadOnly(enabled);
        getLastname().setReadOnly(enabled);
        getBirthdate().setReadOnly(enabled);
        getEmail().setReadOnly(enabled);

        edit.setVisible(enabled);
        edit.setEnabled(enabled);

        save.setVisible(!enabled);
        save.setEnabled(!enabled);
        cancel.setVisible(!enabled);
        cancel.setEnabled(!enabled);
    }

    @Override
    protected HorizontalLayout createButtonsLayout() {
        edit.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        cancel.addThemeVariants(ButtonVariant.LUMO_ERROR);

        save.addClickShortcut(Key.ENTER);
        cancel.addClickShortcut(Key.ESCAPE);

        save.addClickListener(event -> validateAndSave());
        cancel.addClickListener(event -> fireEvent(new CancelEvent(this)));
        edit.addClickListener(event -> fireEvent(new EditEvent(this)));

        binder.addStatusChangeListener(e -> save.setEnabled(binder.isValid()));

        HorizontalLayout btnLayout = new HorizontalLayout(save, cancel, edit);
        btnLayout.setJustifyContentMode(FlexComponent.JustifyContentMode.END);
        return btnLayout;
    }

    @Override
    protected AbstractUserFormEvent createSaveEvent() {
        return new SaveEvent(this, super.user);
    }

    public static class SaveEvent extends AbstractUserForm.AbstractUserFormEvent {
        SaveEvent(ProfileForm source, UserEntity contact) {
            super(source, contact);
        }
    }

    public static class EditEvent extends AbstractUserForm.AbstractUserFormEvent {
        EditEvent(ProfileForm source) {
            super(source, null);
        }
    }

    public static class CancelEvent extends AbstractUserForm.AbstractUserFormEvent {
        CancelEvent(ProfileForm source) {
            super(source, null);
        }
    }
}
