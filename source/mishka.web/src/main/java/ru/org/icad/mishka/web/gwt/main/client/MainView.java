package ru.org.icad.mishka.web.gwt.main.client;

import com.google.gwt.dom.client.Style;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.RootLayoutPanel;

public class MainView {
    public static final MainView instance = new MainView();
    private DockLayoutPanel mainLayout;

    public void init(){
        mainLayout = new DockLayoutPanel(Style.Unit.PX);
        RootLayoutPanel.get().add(mainLayout);
    }

    public DockLayoutPanel getMainLayout() {
        return mainLayout;
    }
}
