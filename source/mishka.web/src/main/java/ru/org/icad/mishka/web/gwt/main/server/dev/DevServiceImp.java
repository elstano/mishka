package ru.org.icad.mishka.web.gwt.main.server.dev;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import ru.org.icad.mishka.app.dev.CastingDev;
import ru.org.icad.mishka.app.dev.DbToolDev;
import ru.org.icad.mishka.app.dev.RestrictionDev;
import ru.org.icad.mishka.web.gwt.main.client.menu.dev.DevService;

import java.util.List;

public class DevServiceImp extends RemoteServiceServlet implements DevService {

    @Override
    public List<String> getDbTableNames() {
        DbToolDev dbToolDev = new DbToolDev();

        return dbToolDev.getTableNames();

    }

    @Override
    public String getTableContent(String tableName) {
        DbToolDev dbToolDev = new DbToolDev();

        return dbToolDev.getTableContent(tableName);
    }

    @Override
    public void cleanTable(String tableName) {
        DbToolDev dbToolDev = new DbToolDev();

        dbToolDev.cleanTable(tableName);
    }

    @Override
    public void restrictionProcess() {
        RestrictionDev restrictionDev = new RestrictionDev();

        restrictionDev.restrictionProcess();
    }

    @Override
    public void castingProcess() {
        CastingDev castingDev = new CastingDev();

        castingDev.castingProcess();
    }
}
