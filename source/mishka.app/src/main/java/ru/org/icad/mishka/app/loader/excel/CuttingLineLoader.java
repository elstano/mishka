package ru.org.icad.mishka.app.loader.excel;

import com.google.common.collect.Lists;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.org.icad.mishka.app.model.CastingUnitHomogenCuttingLine;
import ru.org.icad.mishka.app.model.CuttingLine;
import ru.org.icad.mishka.app.util.ExcelUtil;

import java.util.Collections;
import java.util.List;

public class CuttingLineLoader implements ExcelLoader<CuttingLine> {
    private static final Logger LOGGER = LoggerFactory.getLogger(CuttingLineLoader.class);

    @Override
    public List<CuttingLine> load(String filePath, String sheetName) {
        XSSFSheet sheet = ExcelUtil.getSheet(filePath, sheetName);

        if (sheet == null) {
            LOGGER.error("Not found sheet name: " + sheetName);

            return Collections.emptyList();
        }

        List<CuttingLine> cuttingLines = Lists.newArrayList();

        final int lastRowNum = sheet.getLastRowNum();
        for (int rowCounter = 0; rowCounter <= lastRowNum; rowCounter++) {
            Row row = sheet.getRow(rowCounter);

            if (row == null) {
                continue;
            }

            Cell diameterCell = row.getCell(0);
            if (diameterCell == null) {
                continue;
            }

            if (Cell.CELL_TYPE_NUMERIC != diameterCell.getCellType()) {
                continue;
            }

            final int diameter = Double.valueOf(diameterCell.getNumericCellValue()).intValue();
            final int length = ExcelUtil.getIntCellValue(row, 1);
            final int speed = ExcelUtil.getIntCellValue(row, 2);
            final int castingUnitHomogenCuttingLineId = ExcelUtil.getIntCellValue(row, 3);

            CuttingLine cuttingLine = new CuttingLine();
            cuttingLine.setCastingUnitHomogenCuttingLine(new CastingUnitHomogenCuttingLine(castingUnitHomogenCuttingLineId));
            cuttingLine.setDiameter(diameter);
            cuttingLine.setLength(length);
            cuttingLine.setSpeed(speed);

            cuttingLines.add(cuttingLine);
        }

        return cuttingLines;
    }
}
