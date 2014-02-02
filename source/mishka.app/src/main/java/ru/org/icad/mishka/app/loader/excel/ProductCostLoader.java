package ru.org.icad.mishka.app.loader.excel;

import com.google.common.collect.Lists;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.org.icad.mishka.app.model.Form;
import ru.org.icad.mishka.app.model.Mark;
import ru.org.icad.mishka.app.model.Plant;
import ru.org.icad.mishka.app.model.ProductCost;
import ru.org.icad.mishka.app.util.ExcelUtil;

import java.sql.Date;
import java.util.Collections;
import java.util.List;

public class ProductCostLoader implements ExcelLoader<ProductCost> {
    private static final Logger LOGGER = LoggerFactory.getLogger(ProductCostLoader.class);

    @Override
    public List<ProductCost> load(String filePath, String sheetName) {
        XSSFSheet sheet = ExcelUtil.getSheet(filePath, sheetName);

        if (sheet == null) {
            LOGGER.error("Not found sheet name: " + sheetName);

            return Collections.emptyList();
        }

        List<ProductCost> productCosts = Lists.newArrayList();

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
            final int markId = ExcelUtil.getIntegerCellValue(row, 1);
            final int formId = ExcelUtil.getIntegerCellValue(row, 2);
            final Date date = ExcelUtil.getDateCellValue(row, 3);

            ProductCost productCost = new ProductCost();
            productCost.setPlant(new Plant(plantId));
            productCost.setMark(new Mark(markId));
            productCost.setForm(new Form(formId));
            productCost.setDate(date);

            productCosts.add(productCost);
        }

        return productCosts;
    }
}
