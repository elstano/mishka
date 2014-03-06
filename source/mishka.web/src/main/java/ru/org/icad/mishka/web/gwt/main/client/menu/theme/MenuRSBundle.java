package ru.org.icad.mishka.web.gwt.main.client.menu.theme;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.ImageResource;

public interface MenuRSBundle extends ClientBundle {
    public static MenuRSBundle INSTANCE = GWT.create(MenuRSBundle.class);

    @Source("menu.css")
    MenuCss css();

    @Source("settings.png")
    ImageResource settings();

    @Source("factories.png")
    ImageResource factories();

    @Source("orders.png")
    ImageResource orders();

    @Source("product.png")
    ImageResource products();

    @Source("nsi.png")
    ImageResource nsi();

    @Source("schedule.png")
    ImageResource schedule();

    @Source("filter.png")
    ImageResource filter();

    @Source("sgp.png")
    ImageResource sgp();

    @Source("chemistry.png")
    ImageResource chemistry();

    @Source("transport.png")
    ImageResource transport();

    @Source("resultSchedule.png")
    ImageResource resultSchedule();

    @Source("bug.png")
    ImageResource bug();
}
