package ru.org.icad.mishka.web.gwt.main.client.settings.theme;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;

public interface SettingsBundle extends ClientBundle{
    public static SettingsBundle instance = GWT.create(SettingsBundle.class);

    @Source("settings.css")
    SettingsCss css();
}
