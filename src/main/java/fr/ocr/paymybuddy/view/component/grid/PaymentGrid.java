package fr.ocr.paymybuddy.view.component.grid;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import fr.ocr.paymybuddy.entity.PaymentEntity;
import lombok.Getter;
import org.springframework.util.StringUtils;
import org.vaadin.klaudeta.LitPagination;
import org.vaadin.klaudeta.PaginatedGrid;

import java.lang.reflect.Field;

@Getter
public class PaymentGrid extends PaginatedGrid<PaymentEntity> {

    private LitPagination customPagination;

    public PaymentGrid() {
        super(PaymentEntity.class);
        addClassName("buddy-grid");

        // Hack de la pagination pour redéfinir sa taille
        try {
            Field paginationField = PaginatedGrid.class.getDeclaredField("pagination");
            paginationField.setAccessible(true);
            this.customPagination = (LitPagination) paginationField.get(this);
        } catch (Exception e) {
            e.printStackTrace();
        }

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
        setHeightFull();
        setWidthFull();
        setPageSize(4);
        setPaginatorSize(4);
    }

    public void setNavigationPageNumber(int page) {
        this.customPagination.setPage(page);
    }

    public void setPaginationTotal(int total) {
        this.customPagination.setTotal(total);
    }

    public void refreshNavigation() {
        this.customPagination.refresh();
    }
}
