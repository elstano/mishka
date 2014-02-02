package ru.org.icad.mishka.app.loader.excel;

import com.google.common.collect.Lists;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.org.icad.mishka.app.model.CastingUnit;
import ru.org.icad.mishka.app.model.CastingUnitCollector;
import ru.org.icad.mishka.app.util.ExcelUtil;

import java.util.Collections;
import java.util.List;

public class CastingUnitCollectorLoader implements ExcelLoader<CastingUnitCollector> {
    private static final Logger LOGGER = LoggerFactory.getLogger(CastingUnitCollectorLoader.class);

    @Override
    public List<CastingUnitCollector> load(String filePath, String sheetName) {
        XSSFSheet sheet = ExcelUtil.getSheet(filePath, sheetName);

        if (sheet == null) {
            LOGGER.error("Not found sheet name: " + sheetName);

            return Collections.emptyList();
        }

        List<CastingUnitCollector> castingUnitCollectors = Lists.newArrayList();

        final int lastRowNum = sheet.getLastRowNum();
        for (int rowCounter = 0; rowCounter <= lastRowNum; rowCounter++) {
            Row row = sheet.getRow(rowCounter);

            if (row == null) {
                continue;
            }

            Cell castingUnitCollectorIdCell = row.getCell(0);
            if (castingUnitCollectorIdCell == null) {
                continue;
            }

            if (Cell.CELL_TYPE_NUMERIC != castingUnitCollectorIdCell.getCellType()) {
                continue;
            }

            final int castingUnitCollectorId = Double.valueOf(castingUnitCollectorIdCell.getNumericCellValue()).intValue();
            final int castingUnitId = ExcelUtil.getIntegerCellValue(row, 1);
            final int mixerTonnageMax = ExcelUtil.getIntegerCellValue(row, 2);
            final int mixerRestTonnage = ExcelUtil.getIntegerCellValue(row, 3);
            final int numCleans = ExcelUtil.getIntegerCellValue(row, 5);
            final int cleanTime = ExcelUtil.getIntegerCellValue(row, 6);

            CastingUnitCollector castingUnitCollector = new CastingUnitCollector();
            castingUnitCollector.setId(castingUnitCollectorId);
            castingUnitCollector.setCastingUnit(new CastingUnit(castingUnitId));
            castingUnitCollector.setMixerTonnageMax(mixerTonnageMax);
            castingUnitCollector.setMixerRestTonnage(mixerRestTonnage);
            castingUnitCollector.setNumCleans(numCleans);
            castingUnitCollector.setCleanTime(cleanTime);

            castingUnitCollectors.add(castingUnitCollector);
        }

        return castingUnitCollectors;
    }

}
