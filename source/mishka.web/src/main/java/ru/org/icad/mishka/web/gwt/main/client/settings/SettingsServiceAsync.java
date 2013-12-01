package ru.org.icad.mishka.web.gwt.main.client.settings;

import ru.org.icad.mishka.web.gwt.main.shared.settings.SettingsBean;
import com.google.gwt.user.client.rpc.AsyncCallback;

public interface SettingsServiceAsync {
    void getSettings(AsyncCallback<SettingsBean> async);

    void installNewVersion(AsyncCallback<Void> async);
}
