package fr.ocr.paymybuddy.view;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.router.Route;
import fr.ocr.paymybuddy.entity.UserEntity;
import fr.ocr.paymybuddy.service.ContactService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.security.PermitAll;
import java.util.Set;

@PermitAll
@Route(value = "payment", layout = MainView.class)
public class PaymentView extends VerticalLayout {

    private final ContactService contactService;

    @Autowired
    public PaymentView(ContactService contactService) {
        this.contactService = contactService;

        add(createPaymentPanel());
    }

    public HorizontalLayout createPaymentPanel() {

        Set<UserEntity> contacts = contactService.findCurrentUserContact();

        Select<UserEntity> contactSelect = new Select<>();
        contactSelect.setLabel("Contact");
        contactSelect.setItems(contacts);
        contactSelect.setItemLabelGenerator(user -> user.getFirstname() + " " + user.getLastname());
        contactSelect.setValue(contacts.isEmpty() ? contactSelect.getEmptyValue() : contacts.iterator().next());

        IntegerField integerField = new IntegerField();
        Div euroPrefix = new Div();
        euroPrefix.setText("€");
        integerField.setValue(0);
        integerField.setSuffixComponent(euroPrefix);
        integerField.setHasControls(true);
        integerField.setMin(0);

        Button payButton = new Button("Pay");

        HorizontalLayout paymentPanel = new HorizontalLayout(contactSelect, integerField, payButton);
        paymentPanel.setAlignItems(Alignment.END);

       return paymentPanel;
    }
}