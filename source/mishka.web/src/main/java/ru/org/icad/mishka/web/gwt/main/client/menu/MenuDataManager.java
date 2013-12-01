package ru.org.icad.mishka.web.gwt.main.client.menu;

import ru.org.icad.mishka.web.gwt.main.client.menu.theme.MenuRSBundle;
import ru.org.icad.mishka.web.gwt.main.client.settings.SettingsPresenter;

import java.util.ArrayList;
import java.util.List;

/**
 * User: Boss
 * Date: 10/6/13
 * Time: 10:29 PM
 */
public class MenuDataManager {
    private List<MenuItem> items = new ArrayList<MenuItem>();

    {
        items.add(new MenuItem(MenuRSBundle.INSTANCE.factories(),"Factories", null));
        items.add(new MenuItem(MenuRSBundle.INSTANCE.orders(),"Orders", null));
        items.add(new MenuItem(MenuRSBundle.INSTANCE.settings(),"Settings", new SettingsPresenter()));
//        items.add(new MenuItem("fds","fdsa2"));
//        items.add(new MenuItem("fds","fdsa3"));
//        items.add(new MenuItem("fds","fdsa4"));
//        items.add(new MenuItem("fds","fdsa5"));
    }

    public List<MenuItem> getMenuItems(){
        return items;
    }
}
