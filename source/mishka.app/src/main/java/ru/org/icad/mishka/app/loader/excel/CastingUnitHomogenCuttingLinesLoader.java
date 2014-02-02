package ru.org.icad.mishka.app.loader.excel;

import com.google.common.collect.Lists;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.org.icad.mishka.app.model.CastingUnit;
import ru.org.icad.mishka.app.model.CastingUnitHomogenCuttingLine;
import ru.org.icad.mishka.app.model.CastingUnitHomogenCuttingLines;
import ru.org.icad.mishka.app.util.ExcelUtil;

import java.util.Collections;
import java.util.List;

public class CastingUnitHomogenCuttingLinesLoader implements ExcelLoader<CastingUnitHomogenCuttingLines> {
    private static final Logger LOGGER = LoggerFactory.getLogger(CastingUnitHomogenCuttingLinesLoader.class);

    @Override
    public List<CastingUnitHomogenCuttingLines> load(String filePath, String sheetName) {
        XSSFSheet sheet = ExcelUtil.getSheet(filePath, sheetName);

        if (sheet == null) {
            LOGGER.error("Not found sheet name: " + sheetName);

            return Collections.emptyList();
        }

        List<CastingUnitHomogenCuttingLines> castingUnitHomogenCuttingLineses = Lists.newArrayList();

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
            final int castingUnitHomogenCuttingLineId = ExcelUtil.getIntegerCellValue(row, 1);

            CastingUnitHomogenCuttingLines castingUnitHomogenCuttingLines = new CastingUnitHomogenCuttingLines();
            castingUnitHomogenCuttingLines.setCastingUnit(new CastingUnit(castingUnitId));
            castingUnitHomogenCuttingLines.setCastingUnitHomogenCuttingLine(
                    new CastingUnitHomogenCuttingLine(castingUnitHomogenCuttingLineId)
            );

            castingUnitHomogenCuttingLineses.add(castingUnitHomogenCuttingLines);
        }

        return castingUnitHomogenCuttingLineses;
    }
}
