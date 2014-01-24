package ru.org.icad.mishka.app.loader.excel;

import com.google.common.collect.Lists;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.org.icad.mishka.app.model.Order;
import ru.org.icad.mishka.app.util.ExcelUtil;

import java.util.Collections;
import java.util.List;

public class OrderLoader implements ExcelLoader<Order> {
    private static final Logger LOGGER = LoggerFactory.getLogger(OrderLoader.class);

    @Override
    public List<Order> load(String filePath, String sheetName) {
        XSSFSheet sheet = ExcelUtil.getSheet(filePath, sheetName);

        if (sheet == null) {
            LOGGER.error("Not found sheet name: " + sheetName);

            return Collections.emptyList();
        }

        List<Order> orders = Lists.newArrayList();

        final int lastRowNum = sheet.getLastRowNum();
        for (int rowCounter = 0; rowCounter <= lastRowNum; rowCounter++) {
            Row row = sheet.getRow(rowCounter);

            if (row == null) {
                continue;
            }

            Cell orderIdCell = row.getCell(0);
            if (orderIdCell == null) {
                continue;
            }

            if (Cell.CELL_TYPE_NUMERIC != orderIdCell.getCellType()) {
                continue;
            }

            final int orderId = Double.valueOf(orderIdCell.getNumericCellValue()).intValue();

            Order order = new Order();
            order.setId(orderId);

            orders.add(order);
        }

        return orders;
    }
}
