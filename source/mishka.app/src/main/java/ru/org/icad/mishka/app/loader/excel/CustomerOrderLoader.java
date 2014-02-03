package ru.org.icad.mishka.app.loader.excel;

import com.google.common.collect.Lists;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.org.icad.mishka.app.model.ContainerType;
import ru.org.icad.mishka.app.model.CustomerOrder;
import ru.org.icad.mishka.app.model.Product;
import ru.org.icad.mishka.app.model.TransportDestination;
import ru.org.icad.mishka.app.util.ExcelUtil;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.Collections;
import java.util.List;

public class CustomerOrderLoader implements ExcelLoader<CustomerOrder> {
    private static final Logger LOGGER = LoggerFactory.getLogger(CustomerOrderLoader.class);

    @Override
    public List<CustomerOrder> load(String filePath, String sheetName) {
        XSSFSheet sheet = ExcelUtil.getSheet(filePath, sheetName);

        if (sheet == null) {
            LOGGER.error("Not found sheet name: " + sheetName);

            return Collections.emptyList();
        }

        List<CustomerOrder> customerOrders = Lists.newArrayList();

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

            if (row.getRowNum() == 0) {
                continue;
            }

            final String orderId = orderIdCell.getStringCellValue();
            final int tonnage = ExcelUtil.getIntegerCellValue(row, 1);
            final int toleranceMinus = ExcelUtil.getIntegerCellValue(row, 2);
            final int tolerancePlus = ExcelUtil.getIntegerCellValue(row, 3);
            final Date dueDate = ExcelUtil.getDateCellValue(row, 4);
            final Date shippingDate = ExcelUtil.getDateCellValue(row, 5);
            final int transportDestinationId = ExcelUtil.getIntegerCellValue(row, 6);
            final Integer productId = ExcelUtil.getIntegerCellValue(row, 7);
            final Integer diameter = ExcelUtil.getIntegerCellValue(row, 8);
            final Integer width = ExcelUtil.getIntegerCellValue(row, 9);
            final Integer height = ExcelUtil.getIntegerCellValue(row, 10);
            final Integer length = ExcelUtil.getIntegerCellValue(row, 11);
            final Integer weight = ExcelUtil.getIntegerCellValue(row, 12);
            final BigDecimal premium = ExcelUtil.getBigDecimalCellValue(row, 13);
            final int containerTypeId = ExcelUtil.getIntegerCellValue(row, 14);
            final Integer transportCapacity = ExcelUtil.getIntegerCellValue(row, 15);
            final int timePriority = ExcelUtil.getIntegerCellValue(row, 16);
            final int tonnagePriority = ExcelUtil.getIntegerCellValue(row, 17);
            final Date directiveDate = ExcelUtil.getDateCellValue(row, 18);
            final Integer directiveShift = ExcelUtil.getIntegerCellValue(row, 19);
            final String customer = ExcelUtil.getStringCellValue(row, 20);
            final Date period = ExcelUtil.getDateCellValue(row, 21);

            CustomerOrder customerOrder = new CustomerOrder();
            customerOrder.setId(orderId);
            customerOrder.setTonnage(tonnage);
            customerOrder.setToleranceMinus(toleranceMinus);
            customerOrder.setTolerancePlus(tolerancePlus);
            customerOrder.setDueDate(dueDate);
            customerOrder.setShippingDate(shippingDate);
            customerOrder.setTransportDestination(new TransportDestination(transportDestinationId));

            if (productId != null) {
                customerOrder.setProduct(new Product(productId));
            }

            customerOrder.setDiameter(diameter);
            customerOrder.setWidth(width);
            customerOrder.setHeight(height);
            customerOrder.setLength(length);
            customerOrder.setWeight(weight);
            customerOrder.setPremium(premium);
            customerOrder.setContainerType(new ContainerType(containerTypeId));
            customerOrder.setTransportCapacity(transportCapacity);
            customerOrder.setTimePriority(timePriority);
            customerOrder.setTonnagePriority(tonnagePriority);
            customerOrder.setDirectiveDate(directiveDate);
            customerOrder.setDirectiveShift(directiveShift);
            customerOrder.setCustomer(customer);
            customerOrder.setPeriod(period);

            customerOrders.add(customerOrder);
        }

        return customerOrders;
    }
}
