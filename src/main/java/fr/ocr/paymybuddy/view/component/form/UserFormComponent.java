package fr.ocr.paymybuddy.view.component.form;

import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;

import fr.ocr.paymybuddy.entity.UserEntity;
import lombok.Getter;

@Getter
public class UserFormComponent extends AbstractUserForm {

    private final Button save = new Button("Save");
    private final Button delete = new Button("Delete");
    private final Button close = new Button("Cancel");

    public UserFormComponent() {
        super();
        createDefaultLayout();
    }

    @Override
    protected HorizontalLayout createButtonsLayout() {
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
        close.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        save.addClickShortcut(Key.ENTER);
        close.addClickShortcut(Key.ESCAPE);

        save.addClickListener(event -> validateAndSave());
        delete.addClickListener(event -> fireEvent(new DeleteEvent(this, user)));

        close.addClickListener(event -> fireEvent(new CloseEvent(this)));
        binder.addStatusChangeListener(e -> save.setEnabled(binder.isValid()));

        return new HorizontalLayout(save, delete, close);
    }

    @Override
    protected AbstractUserFormEvent createSaveEvent() {
        return new SaveEvent(this, user);
    }

    public static class SaveEvent extends AbstractUserForm.AbstractUserFormEvent {
        SaveEvent(UserFormComponent source, UserEntity contact) {
            super(source, contact);
        }
    }

    public static class DeleteEvent extends AbstractUserForm.AbstractUserFormEvent {
        DeleteEvent(UserFormComponent source, UserEntity contact) {
            super(source, contact);
        }
    }

    public static class CloseEvent extends AbstractUserForm.AbstractUserFormEvent {
        CloseEvent(UserFormComponent source) {
            super(source, null);
        }
    }
}


