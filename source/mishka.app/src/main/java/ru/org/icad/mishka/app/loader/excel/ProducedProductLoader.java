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
            final int productId = ExcelUtil.getIntegerCellValue(row, 1);
            final Integer weight = ExcelUtil.getIntegerCellValue(row, 2);
            final Integer diameter = ExcelUtil.getIntegerCellValue(row, 3);
            final Integer length = ExcelUtil.getIntegerCellValue(row, 4);
            final Integer width = ExcelUtil.getIntegerCellValue(row, 5);
            final Integer height = ExcelUtil.getIntegerCellValue(row, 6);
            final int tonnage = ExcelUtil.getIntegerCellValue(row, 7);
            final int period = ExcelUtil.getIntegerCellValue(row, 8);

            ProducedProduct producedProduct = new ProducedProduct();
            producedProduct.setPlant(new Plant(plantId));
            producedProduct.setProduct(new Product(productId));
            producedProduct.setWeight(weight);
            producedProduct.setDiameter(diameter);
            producedProduct.setLength(length);
            producedProduct.setWidth(width);
            producedProduct.setHeight(height);
            producedProduct.setTonnage(tonnage);
            producedProduct.setPeriod(period);

            producedProducts.add(producedProduct);
        }

        return producedProducts;
    }
}
