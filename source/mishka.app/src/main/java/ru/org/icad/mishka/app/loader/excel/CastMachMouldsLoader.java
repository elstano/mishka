package ru.org.icad.mishka.app.loader.excel;

import com.google.common.collect.Lists;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.org.icad.mishka.app.model.CastMachMoulds;
import ru.org.icad.mishka.app.model.CastingUnitCastingMachine;
import ru.org.icad.mishka.app.model.CastingUnitHomogenCuttingLine;
import ru.org.icad.mishka.app.model.Mould;
import ru.org.icad.mishka.app.util.ExcelUtil;

import java.util.Collections;
import java.util.List;

public class CastMachMouldsLoader implements ExcelLoader<CastMachMoulds> {
    private static final Logger LOGGER = LoggerFactory.getLogger(CastingUnitHomogenCuttingLine.class);

    @Override
    public List<CastMachMoulds> load(String filePath, String sheetName) {
        XSSFSheet sheet = ExcelUtil.getSheet(filePath, sheetName);

        if (sheet == null) {
            LOGGER.error("Not found sheet name: " + sheetName);

            return Collections.emptyList();
        }

        List<CastMachMoulds> CastMachMoulds = Lists.newArrayList();

        final int lastRowNum = sheet.getLastRowNum();
        for (int rowCounter = 0; rowCounter <= lastRowNum; rowCounter++) {
            Row row = sheet.getRow(rowCounter);

            if (row == null) {
                continue;
            }

            Cell castMachIdCell = row.getCell(0);
            if (castMachIdCell == null) {
                continue;
            }

            if (Cell.CELL_TYPE_NUMERIC != castMachIdCell.getCellType()) {
                continue;
            }

            final int castMachId
                    = Double.valueOf(castMachIdCell.getNumericCellValue()).intValue();
            final int mouldId = ExcelUtil.getIntegerCellValue(row, 1);


            CastMachMoulds castMachMoulds = new CastMachMoulds();
            castMachMoulds.setCastingUnitCastingMachine(new CastingUnitCastingMachine(castMachId));
            castMachMoulds.setMould(new Mould(mouldId));

            CastMachMoulds.add(castMachMoulds);
        }

        return CastMachMoulds;
    }
}
