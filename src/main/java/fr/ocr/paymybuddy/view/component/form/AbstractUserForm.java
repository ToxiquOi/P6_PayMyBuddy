package fr.ocr.paymybuddy.view.component.form;

import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.shared.Registration;
import fr.ocr.paymybuddy.entity.UserEntity;
import lombok.Getter;

@Getter
public abstract class AbstractUserForm extends FormLayout {

    protected final TextField firstname = new TextField("First Name");
    protected final TextField lastname = new TextField("Last Name");
    protected final EmailField email = new EmailField("Email");
    protected final PasswordField password = new PasswordField("Password");
    protected final DatePicker birthdate = new DatePicker("Birthdate");

    protected final Binder<UserEntity> binder = new BeanValidationBinder<>(UserEntity.class);

    protected UserEntity user;

    protected AbstractUserForm() {
        binder.bindInstanceFields(this);
    }

    /**
     * Need to be invoked in derived constructor if form struct not require change
     */
    protected void createDefaultLayout() {
        add(firstname, lastname, birthdate, email, password, createButtonsLayout());
    }

    public void setUser(UserEntity user) {
        this.user = user;
        binder.readBean(user);
    }

    public void setFormReadOnly(boolean enabled) {
        getFirstname().setReadOnly(enabled);
        getLastname().setReadOnly(enabled);
        getBirthdate().setReadOnly(enabled);
        getEmail().setReadOnly(enabled);
    }

    protected void validateAndSave() {
        try {
            binder.writeBean(user);
            fireEvent(createSaveEvent());
        } catch (ValidationException e) {
            e.printStackTrace();
        }
    }

    public <T extends ComponentEvent<?>> Registration addListener(Class<T> eventType,
                                                                  ComponentEventListener<T> listener) {
        return getEventBus().addListener(eventType, listener);
    }

    protected abstract HorizontalLayout createButtonsLayout();

    protected abstract AbstractUserFormEvent createSaveEvent();


    public static abstract class AbstractUserFormEvent extends ComponentEvent<AbstractUserForm> {

        private final UserEntity userEntity;

        public AbstractUserFormEvent(AbstractUserForm source, UserEntity userEntity) {
            super(source, false);
            this.userEntity = userEntity;
        }
    }
}
