package fr.ocr.paymybuddy.view.component.dialog;

import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import fr.ocr.paymybuddy.entity.UserEntity;
import fr.ocr.paymybuddy.view.component.form.PasswordUpdateForm;

public class PasswordUpdateDialog extends Dialog {

    private final PasswordUpdateForm updateForm;

    public PasswordUpdateDialog(UserEntity user) {
        updateForm = new PasswordUpdateForm(user);

        Button closeButton = new Button("Close", e -> close());
        closeButton.addThemeVariants(ButtonVariant.LUMO_ERROR);

        setHeaderTitle("Change your password");
        add(updateForm);
        getFooter().add(closeButton);
    }

    public void addUpdatePasswordListener(ComponentEventListener<PasswordUpdateForm.UpdatePasswordEvent> listener) {
        updateForm.addUpdatePasswordListener(listener);
    }
}
