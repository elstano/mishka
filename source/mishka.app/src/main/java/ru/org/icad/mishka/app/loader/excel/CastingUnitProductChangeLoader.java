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
            final int markId1 = ExcelUtil.getIntegerCellValue(row, 1);
            final int markId2 = ExcelUtil.getIntegerCellValue(row, 2);
            final Integer tonnage = ExcelUtil.getIntegerCellValue(row, 3);
            final String cleanNecessity = ExcelUtil.getStringCellValue(row, 4);
            final Integer timePrepareCollector = ExcelUtil.getIntegerCellValue(row, 5);
            final Integer timePourCollectorDistributor = ExcelUtil.getIntegerCellValue(row, 6);
            final Integer timePrepareDistributor = ExcelUtil.getIntegerCellValue(row, 7);
            final Integer timePrepareCastingMachine = ExcelUtil.getIntegerCellValue(row, 8);
            final Integer timeCast = ExcelUtil.getIntegerCellValue(row, 9);
            final Integer timePourCollectorTwo = ExcelUtil.getIntegerCellValue(row, 10);
            final Integer timePrepareCollectorTwo = ExcelUtil.getIntegerCellValue(row, 11);

            CastingUnitProductChange castingUnitProductChange = new CastingUnitProductChange();
            castingUnitProductChange.setCastingUnit(new CastingUnit(castingUnitId));
            castingUnitProductChange.setMarkId1(markId1);
            castingUnitProductChange.setMarkId2(markId2);
            castingUnitProductChange.setTonnage(tonnage);
            castingUnitProductChange.setCleanNecessity(cleanNecessity);
            castingUnitProductChange.setTimePrepareCollector(timePrepareCollector);
            castingUnitProductChange.setTimePourCollectorDistributor(timePourCollectorDistributor);
            castingUnitProductChange.setTimePrepareDistributor(timePrepareDistributor);
            castingUnitProductChange.setTimePrepareCastingMachine(timePrepareCastingMachine);
            castingUnitProductChange.setTimeCast(timeCast);
            castingUnitProductChange.setTimePourCollectorTwo(timePourCollectorTwo);
            castingUnitProductChange.setTimePrepareCollectorTwo(timePrepareCollectorTwo);

            castingUnitProductChanges.add(castingUnitProductChange);
        }

        return castingUnitProductChanges;
    }
}
