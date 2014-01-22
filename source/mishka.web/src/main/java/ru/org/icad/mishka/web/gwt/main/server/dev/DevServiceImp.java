package ru.org.icad.mishka.web.gwt.main.server.dev;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import ru.org.icad.mishka.app.dev.DevDbTool;
import ru.org.icad.mishka.web.gwt.main.client.menu.dev.DevService;

import java.util.List;

public class DevServiceImp extends RemoteServiceServlet implements DevService {

    @Override
    public List<String> getDbTableNames() {
        DevDbTool devDbTool = new DevDbTool();

        return devDbTool.getTableNames();

    }

    @Override
    public String getTableContent(String tableName) {
        DevDbTool devDbTool = new DevDbTool();

        return devDbTool.getTableContent(tableName);
    }
}
