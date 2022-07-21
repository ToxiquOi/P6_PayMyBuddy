package fr.ocr.paymybuddy.view.component.grid;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import fr.ocr.paymybuddy.entity.PaymentEntity;
import org.springframework.util.StringUtils;
import org.vaadin.klaudeta.PaginatedGrid;

public class PaymentGrid extends PaginatedGrid<PaymentEntity> {

    public PaymentGrid() {
        super(PaymentEntity.class);
        addClassName("buddy-grid");
        configureGrid();
    }

    protected void configureGrid() {
        addClassName("payment-grid");
        removeAllColumns();
        addThemeVariants(GridVariant.LUMO_ROW_STRIPES);

        addColumn(paymentEntity -> StringUtils.capitalize(paymentEntity.getSender().getUser().getFirstname()) + " " + StringUtils.capitalize(paymentEntity.getSender().getUser().getLastname()))
                .setHeader("Sender")
                .setSortable(true);
        addColumn(paymentEntity -> StringUtils.capitalize(paymentEntity.getReceiver().getUser().getFirstname()) + " " + StringUtils.capitalize(paymentEntity.getReceiver().getUser().getLastname()))
                .setHeader("Receiver")
                .setSortable(true);
        addColumn(PaymentEntity::getValue)
                .setHeader("Montant")
                .setSortable(true);
        addColumn(PaymentEntity::getMessage)
                .setHeader("Message");
        Grid.Column<PaymentEntity> colDate = addColumn(PaymentEntity::getTransactionDate)
                .setHeader("Date")
                .setSortable(true);

        colDate.setComparator((payment1, payment2) -> payment2.getTransactionDate().compareTo(payment1.getTransactionDate()));


        setVerticalScrollingEnabled(false);
        setWidth("80%");
        setHeightFull();
        setPageSize(5);
        setPaginatorSize(5);
    }
}
