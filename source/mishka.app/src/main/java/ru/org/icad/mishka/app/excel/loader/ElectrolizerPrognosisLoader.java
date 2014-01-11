package ru.org.icad.mishka.app.excel.loader;

import com.google.common.collect.Lists;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.org.icad.mishka.app.entity.CastingUnit;
import ru.org.icad.mishka.app.entity.ElectrolizerPrognosis;

import java.io.File;
import java.io.FileInputStream;
import java.sql.Date;
import java.util.Collections;
import java.util.List;

public class ElectrolizerPrognosisLoader {
    private static final Logger LOGGER = LoggerFactory.getLogger(ElectrolizerPrognosisLoader.class);

    private ElectrolizerPrognosisLoader() {
    }

    public static List<ElectrolizerPrognosis> load(String filePath, String sheetName) {
        File file = new File(filePath);
        XSSFWorkbook workbook = null;

        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            workbook = new XSSFWorkbook(fileInputStream);
        } catch (Exception e) {
            LOGGER.error("Catch error: ", e);
        }

        if (workbook == null) {
            return Collections.emptyList();
        }

        XSSFSheet sheet = workbook.getSheet(sheetName);

        List<ElectrolizerPrognosis> electrolizerPrognosises = Lists.newArrayList();

        final int lastRowNum = sheet.getLastRowNum();
        for (int rowCounter = 0; rowCounter <= lastRowNum; rowCounter++) {
            Row row = sheet.getRow(rowCounter);

            if (row == null) {
                continue;
            }

            Cell castingUnitIdCell = row.getCell(0);
            if (castingUnitIdCell == null) {
                continue;
            }

            if (Cell.CELL_TYPE_NUMERIC != castingUnitIdCell.getCellType()) {
                continue;
            }

            final int castingUnitId = Double.valueOf(castingUnitIdCell.getNumericCellValue()).intValue();
            CastingUnit castingUnit = new CastingUnit();
            castingUnit.setId(castingUnitId);

            final int electrolizerId = getIntCellValue(row, 1);

            Cell dateCell = row.getCell(2);
            final Date date = new Date(dateCell.getDateCellValue().getTime());

            final int shift = getIntCellValue(row, 3);
            final int tonnage = getIntCellValue(row, 4);

            double contentFe = getDoubleCellValue(row, 5);
            double contentSi = getDoubleCellValue(row, 6);
            double contentCu = getDoubleCellValue(row, 7);
            double contentMg = getDoubleCellValue(row, 8);
            double contentMn = getDoubleCellValue(row, 9);
            double contentTi = getDoubleCellValue(row, 10);

            ElectrolizerPrognosis electrolizerPrognosis = new ElectrolizerPrognosis();
            electrolizerPrognosis.setCastingUnit(castingUnit);
            electrolizerPrognosis.setId(electrolizerId);
            electrolizerPrognosis.setDate(date);
            electrolizerPrognosis.setShift(shift);
            electrolizerPrognosis.setTonnage(tonnage);
            electrolizerPrognosis.setFe(contentFe);
            electrolizerPrognosis.setSi(contentSi);
            electrolizerPrognosis.setCu(contentCu);
            electrolizerPrognosis.setMg(contentMg);
            electrolizerPrognosis.setMn(contentMn);
            electrolizerPrognosis.setTi(contentTi);

            electrolizerPrognosises.add(electrolizerPrognosis);
        }

        return electrolizerPrognosises;
    }


    private static Double getDoubleCellValue(Row row, int columnNumber) {
        final Cell contentCell = row.getCell(columnNumber);
        if (contentCell != null && Cell.CELL_TYPE_NUMERIC == contentCell.getCellType()) {
            return contentCell.getNumericCellValue();
        }

        return null;
    }

    private static Integer getIntCellValue(Row row, int columnNumber) {
        final Double doubleCellValue = getDoubleCellValue(row, columnNumber);
        if (doubleCellValue != null) {
            return doubleCellValue.intValue();

        }

        return null;
    }
}