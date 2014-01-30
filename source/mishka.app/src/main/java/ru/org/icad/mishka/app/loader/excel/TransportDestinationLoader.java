package ru.org.icad.mishka.app.loader.excel;

import com.google.common.collect.Lists;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.org.icad.mishka.app.model.TransportDestination;
import ru.org.icad.mishka.app.util.ExcelUtil;

import java.util.Collections;
import java.util.List;

public class TransportDestinationLoader implements ExcelLoader<TransportDestination> {
    private static final Logger LOGGER = LoggerFactory.getLogger(TransportCostLoader.class);

    @Override
    public List<TransportDestination> load(String filePath, String sheetName) {
        XSSFSheet sheet = ExcelUtil.getSheet(filePath, sheetName);

        if (sheet == null) {
            LOGGER.error("Not found sheet name: " + sheetName);

            return Collections.emptyList();
        }

        List<TransportDestination> transportDestinations = Lists.newArrayList();

        final int lastRowNum = sheet.getLastRowNum();
        for (int rowCounter = 0; rowCounter <= lastRowNum; rowCounter++) {
            Row row = sheet.getRow(rowCounter);

            if (row == null) {
                continue;
            }

            Cell transportDestinationIdCell = row.getCell(0);
            if (transportDestinationIdCell == null) {
                continue;
            }

            if (Cell.CELL_TYPE_NUMERIC != transportDestinationIdCell.getCellType()) {
                continue;
            }

            final int transportDestinationId = Double.valueOf(transportDestinationIdCell.getNumericCellValue()).intValue();
            final String name = ExcelUtil.getStringCellValue(row, 1);

            TransportDestination transportDestination = new TransportDestination(transportDestinationId);
            transportDestination.setName(name);

            transportDestinations.add(transportDestination);
        }

        return transportDestinations;
    }
}