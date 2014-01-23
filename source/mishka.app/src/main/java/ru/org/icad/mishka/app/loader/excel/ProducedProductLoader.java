package ru.org.icad.mishka.app.loader.excel;

import com.google.common.collect.Lists;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.org.icad.mishka.app.model.Plant;
import ru.org.icad.mishka.app.model.ProducedProduct;
import ru.org.icad.mishka.app.model.Product;
import ru.org.icad.mishka.app.util.ExcelUtil;

import java.util.Collections;
import java.util.List;

public class ProducedProductLoader implements ExcelLoader<ProducedProduct> {
    private static final Logger LOGGER = LoggerFactory.getLogger(ProducedProductLoader.class);

    @Override
    public List<ProducedProduct> load(String filePath, String sheetName) {
        XSSFSheet sheet = ExcelUtil.getSheet(filePath, sheetName);

        if (sheet == null) {
            LOGGER.error("Not found sheet name: " + sheetName);

            return Collections.emptyList();
        }

        List<ProducedProduct> producedProducts = Lists.newArrayList();

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
            final int productId = ExcelUtil.getIntCellValue(row, 1);
            final int tonnage = ExcelUtil.getIntCellValue(row, 1);
            final int length = ExcelUtil.getIntCellValue(row, 1);

            ProducedProduct producedProduct = new ProducedProduct();
            producedProduct.setPlant(new Plant(plantId));
            producedProduct.setProduct(new Product(productId));
            producedProduct.setTonnage(tonnage);
            producedProduct.setLength(length);

            producedProducts.add(producedProduct);
        }

        return producedProducts;
    }
}
