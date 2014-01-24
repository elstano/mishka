package ru.org.icad.mishka.app.loader.excel;

import com.google.common.collect.Lists;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.org.icad.mishka.app.model.CastingSpeed;
import ru.org.icad.mishka.app.model.Mark;
import ru.org.icad.mishka.app.model.Mould;
import ru.org.icad.mishka.app.util.ExcelUtil;

import java.util.Collections;
import java.util.List;

public class CastingSpeedLoader implements ExcelLoader<CastingSpeed> {
    private static final Logger LOGGER = LoggerFactory.getLogger(CastingSpeedLoader.class);

    @Override
    public List<CastingSpeed> load(String filePath, String sheetName) {
        XSSFSheet sheet = ExcelUtil.getSheet(filePath, sheetName);

        if (sheet == null) {
            LOGGER.error("Not found sheet name: " + sheetName);

            return Collections.emptyList();
        }

        List<CastingSpeed> castingSpeeds = Lists.newArrayList();

        final int lastRowNum = sheet.getLastRowNum();
        for (int rowCounter = 0; rowCounter <= lastRowNum; rowCounter++) {
            Row row = sheet.getRow(rowCounter);

            if (row == null) {
                continue;
            }

            Cell mouldIdCell = row.getCell(0);
            if (mouldIdCell == null) {
                continue;
            }

            if (Cell.CELL_TYPE_NUMERIC != mouldIdCell.getCellType()) {
                continue;
            }

            final int mouldId = Double.valueOf(mouldIdCell.getNumericCellValue()).intValue();
            final int markId = ExcelUtil.getIntCellValue(row, 1);
            final int speed = ExcelUtil.getIntCellValue(row, 1);

            CastingSpeed castingSpeed = new CastingSpeed();
            castingSpeed.setMould(new Mould(mouldId));
            castingSpeed.setMark(new Mark(markId));
            castingSpeed.setSpeed(speed);

            castingSpeeds.add(castingSpeed);
        }

        return castingSpeeds;
    }
}
