package fr.ocr.paymybuddy.view.component.grid;


import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import fr.ocr.paymybuddy.entity.UserEntity;

public class UserGrid extends Grid<UserEntity> {
    public UserGrid() {
        super(UserEntity.class);
        addClassName("buddy-grid");
        addClassName("user-grid");

        addThemeVariants(GridVariant.LUMO_ROW_STRIPES);

        setSizeFull();
        setHeightFull();

        setSelectionMode(Grid.SelectionMode.MULTI);

        setColumns("lastname", "firstname", "birthdate");
        getColumns().forEach(col -> col.setAutoWidth(true));
    }
}
