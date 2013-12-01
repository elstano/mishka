package ru.org.icad.mishka.web.gwt.main.client.settings;

import ru.org.icad.mishka.web.gwt.main.client.MainView;
import ru.org.icad.mishka.web.gwt.main.client.menu.OptionPresenter;
import ru.org.icad.mishka.web.gwt.main.client.settings.theme.SettingsBundle;
import ru.org.icad.mishka.web.gwt.main.client.settings.theme.SettingsCss;
import ru.org.icad.mishka.web.gwt.main.shared.settings.SettingsBean;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;

/**
 * User: Boss
 * Date: 10/8/13
 * Time: 12:35 AM
 */
public class SettingsPresenter implements OptionPresenter{
    private SettingsServiceAsync settingsService = GWT.create(SettingsService.class);

    private FlexTable initializedPane;
    private Button initializedButton;

    @Override
    public void presentOption() {
        settingsService.getSettings(new AsyncCallback<SettingsBean>(){
            @Override
            public void onFailure(Throwable throwable) {
                throw new RuntimeException(throwable);
            }

            @Override
            public void onSuccess(SettingsBean settingsBean) {
                initAsync(settingsBean);
            }
        });
    }

    private void initAsync(SettingsBean response){
        SettingsCss css = SettingsBundle.instance.css();
        css.ensureInjected();
        initializedPane = new FlexTable();
        initializedPane.setStyleName(css.settingsLineTable());
        initializedPane.setText(0, 0, "Initialized");
        initializedPane.getCellFormatter().setStyleName(0, 0, css.settingsCaption());
        initializedPane.setText(0, 1, "" + response.isInitialized());

        initializedPane.setText(0,2, "Current Version");
        initializedPane.getCellFormatter().setStyleName(0,2,css.settingsCaption());
        initializedPane.setText(0, 3, response.getCurrentVersion());

        initializedButton = new Button("Upgrade DB");
        initializedButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent clickEvent) {
                if(Window.confirm("Are you sure you want to upgrade the database to the latest version?")){
                    settingsService.installNewVersion(new AsyncCallback<Void>() {
                        @Override
                        public void onFailure(Throwable throwable) {

                        }

                        @Override
                        public void onSuccess(Void aVoid) {
                            Window.alert("success");
                        }
                    });
                }
            }
        });

        initializedPane.setWidget(0,4,initializedButton);


        MainView.instance.getMainLayout().add(initializedPane);
    }

    @Override
    public void destroy() {
        initializedPane.removeFromParent();
    }
}
