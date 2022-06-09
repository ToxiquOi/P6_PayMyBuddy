package fr.ocr.paymybuddy.view;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import fr.ocr.paymybuddy.entity.UserEntity;
import fr.ocr.paymybuddy.security.UserRole;
import fr.ocr.paymybuddy.service.UserService;
import fr.ocr.paymybuddy.view.component.form.UserFormComponent;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.security.PermitAll;


@PermitAll
@PageTitle("User List | PayMyBuddy")
@Route(value = "list", layout = MainView.class)
public class ListView extends VerticalLayout {

    private final Grid<UserEntity> usersGrid = new Grid<>(UserEntity.class);

    private final TextField filterText = new TextField();
    private final Button searchButton = new Button("Search");

    private final Button createUserButton = new Button();

    private final UserService userService;

    private UserFormComponent userForm;

    @Autowired
    public ListView(UserService userService) {
        this.userService = userService;

        addClassName("list-view");
        setSizeFull();

        add(getToolbar(), getContent());
        updateGrid();
    }

    private void updateGrid() {
        usersGrid.setItems(userService.findAllUsers(filterText.getValue()));
    }

    private void updateCreateUserButton() {
        createUserButton.setText(userForm.isVisible()? "Hide Form" : "Create user");
    }

    public void editContact(UserEntity contact) {

        if (contact == null) {
            closeEditor();
        } else {
            userForm.setUser(contact);
            userForm.setVisible(true);
            updateCreateUserButton();
            addClassName("editing");
        }
    }

    private void closeEditor() {
        userForm.setUser(null);
        userForm.setVisible(false);
        updateCreateUserButton();
        removeClassName("editing");
    }

    private void addContact() {
        usersGrid.asSingleSelect().clear();
        editContact(new UserEntity());
    }

    private Component getContent() {
        HorizontalLayout content = new HorizontalLayout(getGrid(), getUserForm());

        content.setFlexGrow(2, usersGrid);
        content.setFlexGrow(1, userForm);

        content.addClassNames("content");
        content.setSizeFull();

        return content;
    }

    private HorizontalLayout getToolbar() {
        HorizontalLayout searchLayout = getSearchPanel();
        HorizontalLayout formControlLayout = getFormControlPanel();

        HorizontalLayout toolbar = new HorizontalLayout(searchLayout, formControlLayout);
        toolbar.addClassName("toolbar");
        toolbar.setFlexGrow(2, searchLayout);
        toolbar.setFlexGrow(1, formControlLayout);
        toolbar.setWidthFull();

        return toolbar;
    }

    private HorizontalLayout getSearchPanel() {
        filterText.setPlaceholder("Filter by name...");
        filterText.setClearButtonVisible(true);
        filterText.setValueChangeMode(ValueChangeMode.LAZY);

        searchButton.addClickListener((buttonClickEvent) -> updateGrid());

        return new HorizontalLayout(filterText, searchButton);
    }

    private HorizontalLayout getFormControlPanel() {
        createUserButton.addClickListener((buttonClickEvent) -> {
            if(!userForm.isVisible())
                addContact();
            else
                closeEditor();
        });

        HorizontalLayout controlPanel = new HorizontalLayout(createUserButton);
        controlPanel.setJustifyContentMode(JustifyContentMode.END);
        return controlPanel;
    }

    private Grid<UserEntity> getGrid() {
        usersGrid.addClassName("contact-grid");
        usersGrid.setSizeFull();
        usersGrid.setHeightFull();
        usersGrid.setColumns("id", "lastname", "firstname", "email", "birthdate");
        usersGrid.getColumns().forEach(col -> col.setAutoWidth(true));

        usersGrid.asSingleSelect().addValueChangeListener(event -> {
            editContact(event.getValue());
        });

        return usersGrid;
    }


    private UserFormComponent getUserForm() {
        userForm = new UserFormComponent();
        userForm.setWidth("25em");
        userForm.setVisible(false);
        updateCreateUserButton();

        userForm.addListener(UserFormComponent.SaveEvent.class,
                saveEvent -> {
                    UserEntity user = saveEvent.getSource().getUser();

                    userService.saveUser(user);
                    updateGrid();
                }
        );

        userForm.addListener(UserFormComponent.DeleteEvent.class,
                deleteEvent -> {
                    userService.deleteUser(deleteEvent.getSource().getUser());
                    updateGrid();
                }
        );

        userForm.addListener(UserFormComponent.CloseEvent.class,
                closeEvent -> {
                    closeEditor();
                }
        );

        return userForm;
    }
}
