package ru.org.icad.mishka.web.gwt.main.server.settings;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.org.icad.mishka.app.tx.Callable;
import ru.org.icad.mishka.app.tx.TxUtil;
import ru.org.icad.mishka.app.versions.UpgradeManager;
import ru.org.icad.mishka.app.versions.VersionParser;
import ru.org.icad.mishka.web.gwt.main.client.settings.SettingsService;
import ru.org.icad.mishka.web.gwt.main.shared.settings.SettingsBean;

import java.io.IOException;
import java.sql.SQLException;

/**
 * User: Boss
 * Date: 10/8/13
 * Time: 12:22 AM
 */
public class SettingsServiceImpl extends RemoteServiceServlet implements SettingsService {
    private final static Logger LOGGER = LoggerFactory.getLogger(SettingsServiceImpl.class);

    @Override
    public SettingsBean getSettings() {
        try {
            SettingsBean result = new SettingsBean();
            UpgradeManager upgradeManager = new UpgradeManager();
            result.setInitialized(upgradeManager.isVersioningInitialized());
            result.setCurrentVersion(upgradeManager.getCurrentVersion().toString());
            return result;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void installNewVersion() {
        TxUtil.executeInTransaction(new Callable<Object>() {
            @Override
            public Object run() {
                try {
                    LOGGER.info("installing new version: started");
                    VersionParser parser = new VersionParser();
                    parser.parseInstallers();
                    new UpgradeManager().performUpgrade(parser.getInstallers());
                    LOGGER.info("installing new version: finished");
                    return null;
                } catch (IOException | SQLException e) {
                    LOGGER.error("Fail: ", e);
                    throw new RuntimeException(e);
                }
            }
        });
    }

    @Override
    public void resetDB() {
        try {
            LOGGER.info("resetting database: started");
            new UpgradeManager().resetDB();
            LOGGER.info("resetting database: finished");
        } catch (SQLException e) {
            LOGGER.error("Error during resetting of the database", e);
            throw new RuntimeException(e);
        }
    }
}
