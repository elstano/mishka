package ru.org.icad.mishka.app.loader.excel;

import com.google.common.collect.Lists;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.org.icad.mishka.app.model.CastingUnitHomogenCuttingLine;
import ru.org.icad.mishka.app.util.ExcelUtil;

import java.util.Collections;
import java.util.List;

public class CastingUnitHomogenCuttingLineLoader implements ExcelLoader<CastingUnitHomogenCuttingLine> {
    private static final Logger LOGGER = LoggerFactory.getLogger(CastingUnitHomogenCuttingLine.class);

    @Override
    public List<CastingUnitHomogenCuttingLine> load(String filePath, String sheetName) {
        XSSFSheet sheet = ExcelUtil.getSheet(filePath, sheetName);

        if (sheet == null) {
            LOGGER.error("Not found sheet name: " + sheetName);

            return Collections.emptyList();
        }

        List<CastingUnitHomogenCuttingLine> castingUnitHomogenCuttingLines = Lists.newArrayList();

        final int lastRowNum = sheet.getLastRowNum();
        for (int rowCounter = 0; rowCounter <= lastRowNum; rowCounter++) {
            Row row = sheet.getRow(rowCounter);

            if (row == null) {
                continue;
            }

            Cell castingUnitHomogenCuttingLineIdCell = row.getCell(0);
            if (castingUnitHomogenCuttingLineIdCell == null) {
                continue;
            }

            if (Cell.CELL_TYPE_NUMERIC != castingUnitHomogenCuttingLineIdCell.getCellType()) {
                continue;
            }

            final int castingUnitHomogenCuttingLineId
                    = Double.valueOf(castingUnitHomogenCuttingLineIdCell.getNumericCellValue()).intValue();
            final int lengthBlankMin = ExcelUtil.getIntCellValue(row, 1);
            final int lengthBlankMax = ExcelUtil.getIntCellValue(row, 2);


            CastingUnitHomogenCuttingLine castingUnitHomogenCuttingLine
                    = new CastingUnitHomogenCuttingLine(castingUnitHomogenCuttingLineId);
            castingUnitHomogenCuttingLine.setLengthBlankMin(lengthBlankMin);
            castingUnitHomogenCuttingLine.setLengthBlankMax(lengthBlankMax);

            castingUnitHomogenCuttingLines.add(castingUnitHomogenCuttingLine);
        }

        return castingUnitHomogenCuttingLines;
    }
}
