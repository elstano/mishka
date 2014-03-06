package ru.org.icad.mishka.web.gwt.main.client;

import ru.org.icad.mishka.web.gwt.main.client.menu.MenuPresenter;
import ru.org.icad.mishka.web.gwt.main.client.search.SearchPresenter;
import com.google.gwt.core.client.EntryPoint;

public class MishkaEntryPoint implements EntryPoint {
    @Override
    public void onModuleLoad() {
        MainView.instance.init();
        new SearchPresenter().initSearchPanel();
        new MenuPresenter().initMenu();
    }
}
