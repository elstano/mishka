package ru.org.icad.mishka.app.loader.excel;

import java.util.List;

public interface ExcelLoader<T> {

    public List<T> load(String filePath, String sheetName);
}