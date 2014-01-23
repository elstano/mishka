package ru.org.icad.mishka.app.loader.excel;

import com.google.common.collect.Lists;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.org.icad.mishka.app.model.CastingUnit;
import ru.org.icad.mishka.app.model.CastingUnitProductChange;
import ru.org.icad.mishka.app.util.ExcelUtil;

import java.util.Collections;
import java.util.List;

public class CastingUnitProductChangeLoader implements ExcelLoader<CastingUnitProductChange> {
    private static final Logger LOGGER = LoggerFactory.getLogger(CastingUnitProductChangeLoader.class);

    @Override
    public List<CastingUnitProductChange> load(String filePath, String sheetName) {
        XSSFSheet sheet = ExcelUtil.getSheet(filePath, sheetName);

        if (sheet == null) {
            LOGGER.error("Not found sheet name: " + sheetName);

            return Collections.emptyList();
        }

        List<CastingUnitProductChange> castingUnitProductChanges = Lists.newArrayList();

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
            final int markId1 = ExcelUtil.getIntCellValue(row, 1);
            final int markId2 = ExcelUtil.getIntCellValue(row, 2);
            final int time = ExcelUtil.getIntCellValue(row, 3);
            final int tonnage = ExcelUtil.getIntCellValue(row, 4);
            final int cleanNecessity = ExcelUtil.getIntCellValue(row, 5);

            CastingUnitProductChange castingUnitProductChange = new CastingUnitProductChange();
            castingUnitProductChange.setCastingUnit(new CastingUnit(castingUnitId));
            castingUnitProductChange.setMarkId1(markId1);
            castingUnitProductChange.setMarkId2(markId2);
            castingUnitProductChange.setTime(time);
            castingUnitProductChange.setTonnage(tonnage);
            castingUnitProductChange.setCleanNecessity(cleanNecessity);

            castingUnitProductChanges.add(castingUnitProductChange);
        }

        return castingUnitProductChanges;
    }
}
