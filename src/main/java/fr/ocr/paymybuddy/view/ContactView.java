package fr.ocr.paymybuddy.view;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import fr.ocr.paymybuddy.entity.UserEntity;
import fr.ocr.paymybuddy.service.AuthenticationService;
import fr.ocr.paymybuddy.service.ContactService;

import javax.annotation.security.PermitAll;
import java.util.Set;

@PermitAll
@Route(value = "contact", layout = MainView.class)
public class ContactView extends VerticalLayout {

    private final Grid<UserEntity> usersGrid = new Grid<>(UserEntity.class);
    private final Button addUserSectedButton = new Button("Add to contacts");

    private final Grid<UserEntity> contactGrid = new Grid<>(UserEntity.class);

    private final ContactService contactService;
    private final AuthenticationService authenticationService;

    public ContactView(ContactService contactService, AuthenticationService authenticationService) {
        this.contactService = contactService;
        this.authenticationService = authenticationService;
        addClassName("contact-view");
        setSizeFull();

        addUserSectedButton.addClickListener(buttonClickEvent -> contactService.addContactsToUser(getSelectedUsers()));

        add(addUserSectedButton, createUserGrid(), createContactGrid());
        updateUserGrid();
        updateContactGrid();
    }

    private void updateUserGrid() {
        usersGrid.setItems(contactService.findUserNotInContact());
    }

    private void updateContactGrid() {
        contactGrid.setItems(contactService.findCurrentUserContact());
    }

    private Set<UserEntity> getSelectedUsers() {
        return usersGrid.getSelectedItems();
    }

    private Set<UserEntity> getSelectedContacts() {
        return contactGrid.getSelectedItems();
    }

    private Grid<UserEntity> createUserGrid() {
        usersGrid.addClassName("contact-user-grid");

        usersGrid.setSizeFull();
        usersGrid.setHeightFull();

        usersGrid.setSelectionMode(Grid.SelectionMode.MULTI);

        usersGrid.setColumns("lastname", "firstname", "birthdate");
        usersGrid.getColumns().forEach(col -> col.setAutoWidth(true));

        return usersGrid;
    }

    private Grid<UserEntity> createContactGrid() {
        contactGrid.addClassName("contact-contact-grid");

        contactGrid.setSizeFull();
        contactGrid.setHeightFull();

        contactGrid.setSelectionMode(Grid.SelectionMode.MULTI);

        contactGrid.setColumns("lastname", "firstname", "birthdate");
        contactGrid.getColumns().forEach(col -> col.setAutoWidth(true));

        return contactGrid;
    }

}
