package ru.org.icad.mishka.web.gwt.main.client.menu.theme;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.ImageResource;

/**
 * User: Boss
 * Date: 10/7/13
 * Time: 10:48 PM
 */
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

    ImageResource nsi();

    ImageResource schedule();

    ImageResource filter();

    ImageResource sgp();

    ImageResource chemistry();

    ImageResource transport();

    ImageResource resultSchedule();
}
