package fr.ocr.paymybuddy.view;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.Theme;
import fr.ocr.paymybuddy.entity.UserEntity;
import fr.ocr.paymybuddy.service.ContactService;
import fr.ocr.paymybuddy.view.component.grid.UserGrid;

import javax.annotation.security.PermitAll;
import java.util.Set;

@PermitAll
@Route(value = "contact", layout = MainView.class)
@CssImport(value = "./styles/component/buddy-grid.css", themeFor = "vaadin-grid")
public class ContactView extends VerticalLayout {

    private final UserGrid usersGrid = new UserGrid();
    private final UserGrid contactGrid = new UserGrid();

    private final ContactService contactService;

    public ContactView(ContactService contactService) {
        this.contactService = contactService;
        addClassName("contact-view");
        setSizeFull();

        Button addUserSelectedButton = new Button("Add to contacts", buttonClickEvent -> {
            Set<UserEntity> users = getSelectedUsers();
            if(users != null && !users.isEmpty()) {
                contactService.addContactsToUser(users);
                updateView();
            }
        });

        addUserSelectedButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        Button removeSelectedButton = new Button("Remove contacts", buttonClickEvent -> {
            Set<UserEntity> users = getSelectedContacts();
            if(users != null && !users.isEmpty()) {
                contactService.removeContactFromUser(users);
                updateView();
            }
        });

        removeSelectedButton.addThemeVariants(ButtonVariant.LUMO_ERROR);

        add(addUserSelectedButton, createUserGrid(), createContactGrid(), removeSelectedButton);
        updateView();
    }

    private void updateView() {
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

    private UserGrid createUserGrid() {
        usersGrid.addClassName("contact-user-grid");
        return usersGrid;
    }

    private UserGrid createContactGrid() {
        contactGrid.addClassName("contact-contact-grid");
        return contactGrid;
    }

}
