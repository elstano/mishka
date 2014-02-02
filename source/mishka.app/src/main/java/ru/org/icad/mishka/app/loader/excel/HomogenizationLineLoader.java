package ru.org.icad.mishka.app.loader.excel;

import com.google.common.collect.Lists;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.org.icad.mishka.app.model.CastingUnitHomogenCuttingLine;
import ru.org.icad.mishka.app.model.HomogenizationLine;
import ru.org.icad.mishka.app.util.ExcelUtil;

import java.util.Collections;
import java.util.List;

public class HomogenizationLineLoader implements ExcelLoader<HomogenizationLine> {
    private static final Logger LOGGER = LoggerFactory.getLogger(HomogenizationLineLoader.class);

    @Override
    public List<HomogenizationLine> load(String filePath, String sheetName) {
        XSSFSheet sheet = ExcelUtil.getSheet(filePath, sheetName);

        if (sheet == null) {
            LOGGER.error("Not found sheet name: " + sheetName);

            return Collections.emptyList();
        }

        List<HomogenizationLine> homogenizationLines = Lists.newArrayList();

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
            final int diameter = ExcelUtil.getIntegerCellValue(row, 1);
            final int loadTime = ExcelUtil.getIntegerCellValue(row, 1);

            HomogenizationLine homogenizationLine = new HomogenizationLine();
            homogenizationLine.setCastingUnitHomogenCuttingLine(
                    new CastingUnitHomogenCuttingLine(castingUnitHomogenCuttingLineId)
            );
            homogenizationLine.setDiameter(diameter);
            homogenizationLine.setLoadTime(loadTime);

            homogenizationLines.add(homogenizationLine);
        }

        return homogenizationLines;
    }
}
