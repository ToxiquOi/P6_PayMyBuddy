package fr.ocr.paymybuddy.view.component;

import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.html.H1;

public class AppLogoComponent extends H1 {

    public AppLogoComponent() {
        super("Pay My Buddy");
        setClassName("app-logo");

        setMinHeight(5, Unit.MM);
        setMaxHeight(20, Unit.MM);
        setMinWidth(44, Unit.MM);
        setMaxWidth(69, Unit.MM);

        getStyle()
                .set("color", "white")
                .set("background", "linear-gradient(90deg, rgba(2,0,36,1) 0%, rgba(45,143,54,1) 0%, rgba(0,255,111,1) 100%)")
                .set("border-radius", "2mm")
                .set("padding", "1mm 2mm 2mm 2mm");
    }
}
