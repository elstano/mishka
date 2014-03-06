package ru.org.icad.mishka.web.gwt.main.client.settings;

import ru.org.icad.mishka.web.gwt.main.shared.settings.SettingsBean;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("SettingsService")
public interface SettingsService extends RemoteService {
    SettingsBean getSettings();

    void installNewVersion();

    void resetDB();
}
