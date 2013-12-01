package ru.org.icad.mishka.web.gwt.main.client;

import com.google.gwt.dom.client.Style;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.RootLayoutPanel;

/**
 * User: Boss
 * Date: 10/6/13
 * Time: 10:18 PM
 */
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
