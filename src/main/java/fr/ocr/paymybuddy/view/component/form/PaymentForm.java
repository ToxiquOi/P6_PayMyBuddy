package fr.ocr.paymybuddy.view.component.form;

import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.shared.Registration;
import fr.ocr.paymybuddy.entity.PaymentEntity;
import fr.ocr.paymybuddy.entity.UserEntity;
import fr.ocr.paymybuddy.service.ContactService;
import fr.ocr.paymybuddy.service.UserService;
import lombok.Getter;

import java.util.Set;

public class PaymentForm extends FormLayout {

    private final ContactService contactService;
    private final Binder<PaymentEntity> binder = new BeanValidationBinder<>(PaymentEntity.class);

    private final Select<UserEntity> receivers = new Select<>();
    private final IntegerField value = new IntegerField("Value");
    private final TextField message = new TextField("Message");
    private final UserService userService;

    Button payButton = new Button("Send");

    protected PaymentEntity paymentEntity;

    public PaymentForm(ContactService contactService, UserService userService) {
        this.contactService = contactService;
        this.userService = userService;

        addClassName("payment-form");

        binder.forField(message)
                    .withValidator(mess -> !message.isEmpty(), "Cannot be empty")
                    .bind(getProvider -> message.getValue(), PaymentEntity::setMessage);

        binder.forField(value)
                    .withValidator(val -> value.getValue() > 0, "Cannot be 0")
                    .bind(getProvider -> value.getValue(), PaymentEntity::setValue);


        payButton.addClickListener(buttonClickEvent -> validateAndSave());

        HorizontalLayout paymentPanel = createPaymentPanel();
        paymentPanel.setWidthFull();
        paymentPanel.setAlignItems(FlexComponent.Alignment.CENTER);
        paymentPanel.setMinHeight(10, Unit.EM);


        add(paymentPanel, payButton);
    }

    public void updateContactSelect() {
        Set<UserEntity> contact = contactService.findCurrentUserContact();
        receivers.setItems(contact);
        receivers.setValue(receivers.isEmpty() ? receivers.getEmptyValue() : contact.iterator().next());
    }

    private void validateAndSave() {
        paymentEntity = new PaymentEntity();
        paymentEntity.setSender(userService.getCurrentUser().getWallet());
        paymentEntity.setReceiver(receivers.getValue().getWallet());
        try {
            binder.writeBean(paymentEntity);
            fireEvent(new SendEvent(this, paymentEntity));
        } catch (ValidationException e) {
            e.printStackTrace();
        }
        paymentEntity = null;
        updateContactSelect();
    }

    public <T extends ComponentEvent<?>> Registration addListener(Class<T> eventType,
                                                                  ComponentEventListener<T> listener) {
        return getEventBus().addListener(eventType, listener);
    }

    private HorizontalLayout createPaymentPanel() {
        receivers.setLabel("Dest.");
        receivers.setItemLabelGenerator(wallet -> wallet.getFirstname() + " " + wallet.getLastname());
        updateContactSelect();

        Div euroPrefix = new Div();
        euroPrefix.setText("€");
        value.setValue(0);
        value.setSuffixComponent(euroPrefix);
        value.setHasControls(true);
        value.setMin(0);

        message.setWidthFull();

        return new HorizontalLayout(receivers, value, message);
    }

    @Getter
    public static abstract class AbstractPaymentFormEvent extends ComponentEvent<PaymentForm> {

        private final PaymentEntity paymentEntity;

        public AbstractPaymentFormEvent(PaymentForm source, PaymentEntity paymentEntity) {
            super(source, false);
            this.paymentEntity = paymentEntity;
        }
    }

    @Getter
    public static class SendEvent extends AbstractPaymentFormEvent {
        public SendEvent(PaymentForm source, PaymentEntity paymentEntity) {
            super(source, paymentEntity);
        }
    }
}
