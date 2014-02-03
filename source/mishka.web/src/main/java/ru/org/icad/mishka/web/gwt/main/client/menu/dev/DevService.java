package ru.org.icad.mishka.web.gwt.main.client.menu.dev;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import java.util.List;

@RemoteServiceRelativePath("DevService")
public interface DevService extends RemoteService {

    List<String> getDbTableNames();

    String getTableContent(String tableName);

    void cleanTable(String tableName);

    void restrictionProcess();
}
