package ru.org.icad.mishka.app.excel;

import java.util.List;

/**
 * User: Boss
 * Date: 11/12/13
 * Time: 12:10 AM
 */
public class TemplateGenerator {
    private String tableName;
    private List<Column> columns;
    private String result;

    public TemplateGenerator(String tableName) {
        this.tableName = tableName;
    }

    public String buildTemplate(){
        return null;
    }
}
