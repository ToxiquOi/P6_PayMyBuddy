package fr.ocr.paymybuddy.view;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.provider.DataProvider;
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
@CssImport(value = "./styles/view/payment-view.css")
@CssImport(value = "./styles/component/buddy-grid.css", themeFor = "vaadin-grid")
@CssImport(value = "./styles/component/payment-form.css", themeFor = "vaadin-form-layout")
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

        VerticalLayout gridLayout = new VerticalLayout();
        gridLayout.addClassName("grid-layout");
        H3 gridLabel = new H3("Mes transactions");
        gridLabel.addClassName("payment-label");
        gridLayout.add(gridLabel, paymentGrid);
        gridLayout.setHorizontalComponentAlignment(Alignment.START, gridLabel);
        gridLayout.setWidth("80%");

        int currentUserId = userService.getCurrentUser().getWallet().getId();
        paymentGrid.setDataProvider(DataProvider.fromCallbacks(
                (fetch) -> paymentService.getCurrentUserPayment(fetch.getPage()).stream(),
                (count) -> this.paymentService.countPaymentOfUser(currentUserId)
        ));

        paymentGrid.getDataProvider().refreshAll();

        setHorizontalComponentAlignment(Alignment.CENTER, gridLayout);
        add(createPaymentForm(), gridLayout);
    }


    public VerticalLayout createPaymentForm() {
        PaymentForm form = new PaymentForm(contactService, userService);
        form.setWidthFull();


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
        });

        VerticalLayout paymentLayout = new VerticalLayout();
        paymentLayout.addClassName("payment-layout");
        H3 formLabel = new H3("Envoyer de l'argent");
        formLabel.addClassName("payment-label");
        paymentLayout.setWidth("35%");
        paymentLayout.add(formLabel, form);
        paymentLayout.setHorizontalComponentAlignment(Alignment.START, formLabel);

        setHorizontalComponentAlignment(Alignment.CENTER, paymentLayout);
        return paymentLayout;
    }




}