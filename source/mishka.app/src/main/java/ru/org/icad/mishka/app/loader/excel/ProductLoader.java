package ru.org.icad.mishka.app.loader.excel;

import com.google.common.collect.Lists;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.org.icad.mishka.app.model.Product;
import ru.org.icad.mishka.app.util.ExcelUtil;

import java.util.Collections;
import java.util.List;

public class ProductLoader implements ExcelLoader<Product> {
    private static final Logger LOGGER = LoggerFactory.getLogger(ProductLoader.class);

    @Override
    public List<Product> load(String filePath, String sheetName) {
        XSSFSheet sheet = ExcelUtil.getSheet(filePath, sheetName);

        if (sheet == null) {
            LOGGER.error("Not found sheet name: " + sheetName);

            return Collections.emptyList();
        }

        List<Product> products = Lists.newArrayList();

        final int lastRowNum = sheet.getLastRowNum();
        for (int rowCounter = 0; rowCounter <= lastRowNum; rowCounter++) {
            Row row = sheet.getRow(rowCounter);

            if (row == null) {
                continue;
            }

            Cell productIdCell = row.getCell(0);
            if (productIdCell == null) {
                continue;
            }

            if (Cell.CELL_TYPE_NUMERIC != productIdCell.getCellType()) {
                continue;
            }

            final int productId = Double.valueOf(productIdCell.getNumericCellValue()).intValue();
            final String productName = ExcelUtil.getStringCellValue(row, 1);

            Product product = new Product();
            product.setId(productId);
            product.setName(productName);

            products.add(product);
        }

        return products;
    }
}

