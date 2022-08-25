package fr.ocr.paymybuddy.view.component.form;

import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import fr.ocr.paymybuddy.entity.UserEntity;
import lombok.Getter;

public class PasswordUpdateForm extends FormLayout {

    private final PasswordField password = new PasswordField("Enter password");
    private final PasswordField confirmPassword = new PasswordField("Confirm password");

    private final Button confirmButton = new Button("Update", event -> validateAndSave());

    private final Binder<UserEntity> binder = new Binder<>(UserEntity.class);

    private  UserEntity user;

    public PasswordUpdateForm(UserEntity user) {
        this.user = user;
        binder.forField(confirmPassword)
                .withValidator(pass -> confirmPassword.getValue().equals(password.getValue()),"Please enter identical password")
                .bind(get -> confirmPassword.getValue(), UserEntity::setPassword);

        VerticalLayout formLayout = new VerticalLayout(password, confirmPassword, confirmButton);
        formLayout.setHorizontalComponentAlignment(FlexComponent.Alignment.CENTER, password, confirmPassword);
        formLayout.setHorizontalComponentAlignment(FlexComponent.Alignment.END, confirmButton);

        add(formLayout);
    }

    public void addUpdatePasswordListener(ComponentEventListener<UpdatePasswordEvent> listener) {
        super.addListener(UpdatePasswordEvent.class, listener);
    }

    private void validateAndSave() {
        try {
            binder.writeBean(user);
            fireEvent(new UpdatePasswordEvent(this, user));
        } catch (ValidationException e) {
            e.printStackTrace();
        }
    }

    @Getter
    public static abstract class AbstractUpdatePasswordFormEvent extends ComponentEvent<PasswordUpdateForm> {

        private final UserEntity user;

        public AbstractUpdatePasswordFormEvent(PasswordUpdateForm source, UserEntity user) {
            super(source, false);
            this.user = user;
        }
    }

    public static class UpdatePasswordEvent extends AbstractUpdatePasswordFormEvent {
        public UpdatePasswordEvent(PasswordUpdateForm passwordUpdateForm, UserEntity user) {
            super(passwordUpdateForm, user);
        }
    }
}
