package fr.ocr.paymybuddy.view;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import fr.ocr.paymybuddy.exception.WalletBalanceException;
import fr.ocr.paymybuddy.service.ContactService;
import fr.ocr.paymybuddy.service.PaymentService;
import fr.ocr.paymybuddy.service.UserService;
import fr.ocr.paymybuddy.view.component.form.PaymentForm;
import fr.ocr.paymybuddy.view.component.grid.PaymentGrid;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.security.PermitAll;



@PermitAll
@Route(value = "payment", layout = MainView.class)
@CssImport(value = "./styles/component/buddy-grid.css", themeFor = "vaadin-grid")
public class PaymentView extends VerticalLayout {

    private final UserService userService;
    private final ContactService contactService;
    private final PaymentService paymentService;

    private final PaymentGrid paymentGrid = new PaymentGrid();

    @Autowired
    public PaymentView(ContactService contactService, PaymentService paymentService, UserService userService) {
        addClassName("payment-view");
        this.userService = userService;
        this.contactService = contactService;
        this.paymentService = paymentService;

        add(createPaymentForm(), paymentGrid);

        setHorizontalComponentAlignment(Alignment.CENTER, paymentGrid);

        updateGrid();
    }

    public void updateGrid() {
        this.paymentGrid.setItems(userService.getCurrentUser()
                .getWallet()
                .getPayments()
                .stream()
                .sorted((pay1, pay2) -> pay2.getTransactionDate().compareTo(pay1.getTransactionDate()))
                .toList());
    }

    public FormLayout createPaymentForm() {
        PaymentForm form = new PaymentForm(contactService, userService);
        form.addListener(PaymentForm.SendEvent.class, event -> {
            try {
                paymentService.proceedToPayment(event.getPaymentEntity());
                UI.getCurrent().access(() -> {
                    Notification notif = new Notification();
                    notif.add(new Text("Payment done "), new Icon("lumo", "checkmark"));
                    notif.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
                    notif.setDuration(3600);
                    notif.open();
                });
            } catch (WalletBalanceException ex) {
                UI.getCurrent().access(() -> {
                    Notification notif = Notification.show("Cannot proceed to payment, please check your wallet balance");
                    notif.addThemeVariants(NotificationVariant.LUMO_ERROR);
                });
            }
            updateGrid();
        });

        setHorizontalComponentAlignment(Alignment.CENTER, form);

       return form;
    }




}