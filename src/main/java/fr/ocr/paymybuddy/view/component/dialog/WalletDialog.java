package fr.ocr.paymybuddy.view.component.dialog;

import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import fr.ocr.paymybuddy.entity.WalletEntity;
import fr.ocr.paymybuddy.view.component.form.CreditWalletForm;

public class WalletDialog extends Dialog {

    private final IntegerField sold = new IntegerField("Solde du portefeuille");
    private final IntegerField transactionNb = new IntegerField("Nombre de transactions");

    private CreditWalletForm walletForm = new CreditWalletForm();

    public WalletDialog(WalletEntity wallet) {
        this();
        updateDialog(wallet);
    }

    public WalletDialog() {
        addClassName("wallet-dialog");

        Button closeButton = new Button("Close", e -> close());
        closeButton.addThemeVariants(ButtonVariant.LUMO_ERROR);

        setHeaderTitle("Portefeuille");
        add(createLayout());
        getFooter().add(closeButton);
    }

    public void updateDialog(WalletEntity wallet) {
        setSold(wallet);
        setNbTransact(wallet);
    }

    public void addSendListener(ComponentEventListener<CreditWalletForm.SendEvent> listener) {
        walletForm.addSendListener(listener);
    }

    public void setSold(WalletEntity wallet) {
        this.sold.setValue(wallet.getBalance());
    }

    public void setNbTransact(WalletEntity wallet) {
        this.transactionNb.setValue(wallet.getPayments().size());
    }


    private VerticalLayout createLayout() {
        VerticalLayout layout  = new VerticalLayout();

        sold.setWidthFull();
        sold.setReadOnly(true);
        transactionNb.setWidthFull();
        transactionNb.setReadOnly(true);

        layout.add(sold, transactionNb, walletForm);
        return layout;
    }

    @Override
    public void open() {
        walletForm = new CreditWalletForm();
        super.open();
    }
}
