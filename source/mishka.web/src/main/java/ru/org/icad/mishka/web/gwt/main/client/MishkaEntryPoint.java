package ru.org.icad.mishka.web.gwt.main.client;

import ru.org.icad.mishka.web.gwt.main.client.menu.MenuPresenter;
import ru.org.icad.mishka.web.gwt.main.client.search.SearchPresenter;
import com.google.gwt.core.client.EntryPoint;

/**
 * User: Boss
 * Date: 9/24/13
 * Time: 12:36 PM
 */
public class MishkaEntryPoint implements EntryPoint {
    @Override
    public void onModuleLoad() {
        MainView.instance.init();
        new SearchPresenter().initSearchPanel();
        new MenuPresenter().initMenu();
    }
}
