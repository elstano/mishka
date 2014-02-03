package ru.org.icad.mishka.app.loader.excel;

import com.google.common.collect.Lists;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.org.icad.mishka.app.model.*;
import ru.org.icad.mishka.app.util.ExcelUtil;

import java.util.Collections;
import java.util.List;

public class CastingUnitMarksLoader implements ExcelLoader<CastingUnitMarks> {
    private static final Logger LOGGER = LoggerFactory.getLogger(CastingUnitMarksLoader.class);

    @Override
    public List<CastingUnitMarks> load(String filePath, String sheetName) {
        XSSFSheet sheet = ExcelUtil.getSheet(filePath, sheetName);

        if (sheet == null) {
            LOGGER.error("Not found sheet name: " + sheetName);

            return Collections.emptyList();
        }

        List<CastingUnitMarks> castingUnitMarkses = Lists.newArrayList();

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
            final int markId = ExcelUtil.getIntegerCellValue(row, 1);

            CastingUnitMarks castingUnitMarks = new CastingUnitMarks();
            castingUnitMarks.setCastingUnit(new CastingUnit(castingUnitId));
            castingUnitMarks.setMark(new Mark(markId));

            castingUnitMarkses.add(castingUnitMarks);
        }

        return castingUnitMarkses;
    }
}
