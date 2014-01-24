package ru.org.icad.mishka.app.loader.excel;

import com.google.common.collect.Lists;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.org.icad.mishka.app.model.Filtration;
import ru.org.icad.mishka.app.util.ExcelUtil;

import java.util.Collections;
import java.util.List;

public class FiltrationLoader implements ExcelLoader<Filtration> {
    private static final Logger LOGGER = LoggerFactory.getLogger(FiltrationLoader.class);

    @Override
    public List<Filtration> load(String filePath, String sheetName) {
        XSSFSheet sheet = ExcelUtil.getSheet(filePath, sheetName);

        if (sheet == null) {
            LOGGER.error("Not found sheet name: " + sheetName);

            return Collections.emptyList();
        }

        List<Filtration> filtrations = Lists.newArrayList();

        final int lastRowNum = sheet.getLastRowNum();
        for (int rowCounter = 0; rowCounter <= lastRowNum; rowCounter++) {
            Row row = sheet.getRow(rowCounter);

            if (row == null) {
                continue;
            }

            Cell filtrationIdCell = row.getCell(0);
            if (filtrationIdCell == null) {
                continue;
            }

            if (Cell.CELL_TYPE_NUMERIC != filtrationIdCell.getCellType()) {
                continue;
            }

            final int filtrationId = Double.valueOf(filtrationIdCell.getNumericCellValue()).intValue();
            final String type = ExcelUtil.getStringCellValue(row, 1);

            Filtration filtration = new Filtration();
            filtration.setId(filtrationId);
            filtration.setType(type);

            filtrations.add(filtration);
        }

        return filtrations;
    }
}
