package ru.org.icad.mishka.app.loader.excel;

import com.google.common.collect.Lists;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.org.icad.mishka.app.model.*;
import ru.org.icad.mishka.app.util.ExcelUtil;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

public class TransportCostLoader implements ExcelLoader<TransportCost> {
    private static final Logger LOGGER = LoggerFactory.getLogger(TransportCostLoader.class);

    @Override
    public List<TransportCost> load(String filePath, String sheetName) {
        XSSFSheet sheet = ExcelUtil.getSheet(filePath, sheetName);

        if (sheet == null) {
            LOGGER.error("Not found sheet name: " + sheetName);

            return Collections.emptyList();
        }

        List<TransportCost> transportCosts = Lists.newArrayList();

        final int lastRowNum = sheet.getLastRowNum();
        for (int rowCounter = 0; rowCounter <= lastRowNum; rowCounter++) {
            Row row = sheet.getRow(rowCounter);

            if (row == null) {
                continue;
            }

            Cell plantIdCell = row.getCell(0);
            if (plantIdCell == null) {
                continue;
            }

            if (Cell.CELL_TYPE_NUMERIC != plantIdCell.getCellType()) {
                continue;
            }

            final int plantId = Double.valueOf(plantIdCell.getNumericCellValue()).intValue();
            final int transportDestinationId = ExcelUtil.getIntegerCellValue(row, 1);
            final int  containerTypeId = ExcelUtil.getIntegerCellValue(row, 2);
            final int formId = ExcelUtil.getIntegerCellValue(row, 3);
            final BigDecimal cost = ExcelUtil.getBigDecimalCellValue(row, 4);

            TransportCost transportCost = new TransportCost();
            transportCost.setPlant(new Plant(plantId));
            transportCost.setTransportDestination(new TransportDestination(transportDestinationId));
            transportCost.setContainerType(new ContainerType(containerTypeId));
            transportCost.setForm(new Form(formId));
            transportCost.setCost(cost);

            transportCosts.add(transportCost);
        }

        return transportCosts;
    }
}
