package fr.ocr.paymybuddy.view.component.form;

import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.shared.Registration;
import lombok.Getter;

public class CreditWalletForm extends FormLayout {

    private final IntegerField value = new IntegerField("Montant à créditer");
    private final Button validateButton = new Button("Valider", e -> validateAndSave());

    public CreditWalletForm() {
        addClassName("credit-form");

        Div euroPrefix = new Div();
        euroPrefix.setText("€");
        value.setValue(1);
        value.setSuffixComponent(euroPrefix);
        value.setHasControls(true);
        value.setMin(0);

        add(value, validateButton);
    }

    public Registration addSendListener(ComponentEventListener<SendEvent> listener) {
        return super.addListener(SendEvent.class, listener);
    }

    private void validateAndSave() {
        fireEvent(new SendEvent(this, value.getValue()));
    }

    @Getter
    public static abstract class AbstractCreditWalletFormEvent extends ComponentEvent<CreditWalletForm> {

        private final int credit;

        public AbstractCreditWalletFormEvent(CreditWalletForm source, int credit) {
            super(source, false);
            this.credit = credit;
        }
    }

    public static class SendEvent extends AbstractCreditWalletFormEvent {
        public SendEvent(CreditWalletForm source, int credit) {
            super(source, credit);
        }
    }

}
