package ru.org.icad.mishka.web.gwt.main.client.settings.theme;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;

/**
 * User: Boss
 * Date: 10/8/13
 * Time: 8:53 PM
 */
public interface SettingsBundle extends ClientBundle{
    public static SettingsBundle instance = GWT.create(SettingsBundle.class);

    @Source("settings.css")
    SettingsCss css();
}
