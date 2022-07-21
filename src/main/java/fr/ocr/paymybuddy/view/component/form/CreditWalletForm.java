package fr.ocr.paymybuddy.view.component.form;

import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.shared.Registration;
import fr.ocr.paymybuddy.model.CreditWalletModel;
import lombok.Getter;

public class CreditWalletForm extends FormLayout {

    private final IntegerField value = new IntegerField("Montant à créditer");
    private final Button validateButton = new Button("Valider", e -> validateAndSave());

    private final Binder<CreditWalletModel> binder = new Binder<>(CreditWalletModel.class);

    public CreditWalletForm() {
        addClassName("credit-form");

        binder.forField(value).bind(provider -> value.getValue(), CreditWalletModel::setValue);

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
        CreditWalletModel creditWalletModel = new CreditWalletModel();

        try {
            binder.writeBean(creditWalletModel);
            fireEvent(new SendEvent(this, creditWalletModel));
        } catch (ValidationException e) {
            e.printStackTrace();
        }
    }

    @Getter
    public static abstract class AbstractCreditWalletFormEvent extends ComponentEvent<CreditWalletForm> {

        private final CreditWalletModel creditWalletModel;

        public AbstractCreditWalletFormEvent(CreditWalletForm source, CreditWalletModel creditWalletModel) {
            super(source, false);
            this.creditWalletModel = creditWalletModel;
        }
    }

    public static class SendEvent extends AbstractCreditWalletFormEvent {
        public SendEvent(CreditWalletForm source, CreditWalletModel creditWalletModel) {
            super(source, creditWalletModel);
        }
    }

}
