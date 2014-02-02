package ru.org.icad.mishka.app.util;

import com.google.common.collect.Lists;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.FileInputStream;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.Collections;
import java.util.List;


public class ExcelUtil {

    private ExcelUtil() {
    }

    public static List<String> getSheetNames(String filePath) {
        File file = new File(filePath);
        XSSFWorkbook workbook = null;

        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            workbook = new XSSFWorkbook(fileInputStream);
        } catch (Exception e) {
            // empty
        }

        if (workbook == null) {
            return Collections.emptyList();
        }

        List<String> sheetNames = Lists.newArrayList();

        int numberOfSheets = workbook.getNumberOfSheets();
        for (int i = 0; i < numberOfSheets; i++) {
            sheetNames.add(workbook.getSheetName(i));
        }

        return sheetNames;
    }

    @Nullable
    public static XSSFSheet getSheet(String filePath, String sheetName) {
        File file = new File(filePath);
        XSSFWorkbook workbook = null;

        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            workbook = new XSSFWorkbook(fileInputStream);
        } catch (Exception e) {
            // empty
        }

        if (workbook == null) {
            return null;
        }

        return workbook.getSheet(sheetName);
    }

    @Nullable
    public static Double getDoubleCellValue(Row row, int columnNumber) {
        final Cell contentCell = row.getCell(columnNumber);
        if (contentCell != null && Cell.CELL_TYPE_NUMERIC == contentCell.getCellType()) {
            return contentCell.getNumericCellValue();
        }

        return null;
    }

    @Nullable
    public static Integer getIntegerCellValue(Row row, int columnNumber) {
        final Double doubleCellValue = getDoubleCellValue(row, columnNumber);
        if (doubleCellValue != null) {
            return doubleCellValue.intValue();

        }

        return null;
    }

    @Nullable
    public static String getStringCellValue(Row row, int columnNumber) {
        Cell contentCell = row.getCell(columnNumber);
        if (contentCell != null && Cell.CELL_TYPE_STRING == contentCell.getCellType()) {
            return contentCell.getStringCellValue();
        }

        return null;
    }

    @Nullable
    public static BigDecimal getBigDecimalCellValue(Row row, int columnNumber) {
        final Double doubleCellValue = getDoubleCellValue(row, columnNumber);
        if (doubleCellValue != null) {
            return BigDecimal.valueOf(doubleCellValue);
        }

        return null;
    }

    @Nullable
    public static Date getDateCellValue(Row row, int columnNumber) {
        final Cell contentCell = row.getCell(columnNumber);
        if (contentCell != null && Cell.CELL_TYPE_NUMERIC == contentCell.getCellType()) {
            return new Date(contentCell.getDateCellValue().getTime());
        }

        return null;
    }
}
