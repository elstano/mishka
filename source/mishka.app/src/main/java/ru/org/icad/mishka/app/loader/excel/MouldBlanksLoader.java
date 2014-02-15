package ru.org.icad.mishka.app.loader.excel;

import com.google.common.collect.Lists;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.org.icad.mishka.app.model.Mould;
import ru.org.icad.mishka.app.model.MouldBlanks;
import ru.org.icad.mishka.app.util.ExcelUtil;

import java.util.Collections;
import java.util.List;

public class MouldBlanksLoader implements ExcelLoader<MouldBlanks> {
    private static final Logger LOGGER = LoggerFactory.getLogger(ProductCostLoader.class);

    @Override
    public List<MouldBlanks> load(String filePath, String sheetName) {
        XSSFSheet sheet = ExcelUtil.getSheet(filePath, sheetName);

        if (sheet == null) {
            LOGGER.error("Not found sheet name: " + sheetName);

            return Collections.emptyList();
        }

        List<MouldBlanks> mouldBlankses = Lists.newArrayList();

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
            final Integer numBlanks = ExcelUtil.getIntegerCellValue(row, 1);


            MouldBlanks mouldBlanks = new MouldBlanks();
            mouldBlanks.setMould(new Mould(mouldId));
            mouldBlanks.setNumBlanks(numBlanks);

            mouldBlankses.add(mouldBlanks);
        }

        return mouldBlankses;
    }
}
