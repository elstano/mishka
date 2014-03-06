package ru.org.icad.mishka.web.gwt.main.shared.settings;

import java.io.Serializable;

public class SettingsBean implements Serializable {
    private boolean initialized;
    private String currentVersion;

    public boolean isInitialized() {
        return initialized;
    }

    public void setInitialized(boolean initialized) {
        this.initialized = initialized;
    }

    public String getCurrentVersion() {
        return currentVersion;
    }

    public void setCurrentVersion(String currentVersion) {
        this.currentVersion = currentVersion;
    }
}
