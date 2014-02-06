package ru.org.icad.mishka.web.gwt.main.client.menu.dev;

import com.google.gwt.user.client.rpc.AsyncCallback;

import java.util.List;

public interface DevServiceAsync {

    void getDbTableNames(AsyncCallback<List<String>> async);

    void getTableContent(String tableName, AsyncCallback<String> async);

    void restrictionProcess(AsyncCallback<Void> async);

    void cleanTable(String tableName, AsyncCallback<Void> async);

    void castingProcess(AsyncCallback<Void> async);

    void transportProcess(AsyncCallback<Void> async);
}