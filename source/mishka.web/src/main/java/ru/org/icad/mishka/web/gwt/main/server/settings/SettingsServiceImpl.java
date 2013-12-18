package ru.org.icad.mishka.web.gwt.main.server.settings;

import ru.org.icad.mishka.app.tx.Callable;
import ru.org.icad.mishka.app.tx.TxUtil;
import ru.org.icad.mishka.app.versions.UpgradeManager;
import ru.org.icad.mishka.app.versions.VersionParser;
import ru.org.icad.mishka.web.gwt.main.client.settings.SettingsService;
import ru.org.icad.mishka.web.gwt.main.shared.settings.SettingsBean;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.IOException;
import java.sql.SQLException;

/**
 * User: Boss
 * Date: 10/8/13
 * Time: 12:22 AM
 */
public class SettingsServiceImpl extends RemoteServiceServlet implements SettingsService{
    private static final Log log = LogFactory.getLog(SettingsServiceImpl.class);

    @Override
    public SettingsBean getSettings() {
        try {
            SettingsBean result = new SettingsBean();
            UpgradeManager upgradeManager = new UpgradeManager();
            result.setInitialized(upgradeManager.isVersioningInitialized());
            result.setCurrentVersion(upgradeManager.getCurrentVersion().toString());
            return result;
        } catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public void installNewVersion() {
        TxUtil.executeInTransaction(new Callable<Object>() {
            @Override
            public Object run() {
                try {
                    log.info("installing new version: started");
                    VersionParser parser = new VersionParser();
                    parser.parseInstallers();
                    new UpgradeManager().performUpgrade(parser.getInstallers());
                    log.info("installing new version: finished");
                    return null;
                } catch (IOException | SQLException e) {
                    log.error(e);
                    throw new RuntimeException(e);
                }
            }
        });
    }

    @Override
    public void resetDB() {
        try {
            log.info("resetting database: started");
            new UpgradeManager().resetDB();
            log.info("resetting database: finished");
        } catch (SQLException e) {
            log.error("Error during resetting of the database", e);
            throw new RuntimeException(e);
        }
    }
}
