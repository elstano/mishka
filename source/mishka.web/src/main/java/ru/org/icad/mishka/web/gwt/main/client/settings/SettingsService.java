package ru.org.icad.mishka.web.gwt.main.client.settings;

import ru.org.icad.mishka.web.gwt.main.shared.settings.SettingsBean;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * User: Boss
 * Date: 10/8/13
 * Time: 12:20 AM
 */


@RemoteServiceRelativePath("SettingsService")
public interface SettingsService extends RemoteService {
    SettingsBean getSettings();

    void installNewVersion();
}
