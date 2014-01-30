package ru.org.icad.mishka.app.loader.excel;

import com.google.common.collect.Lists;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.org.icad.mishka.app.model.*;
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
            final int plantId = ExcelUtil.getIntCellValue(row, 2);
            final String spec = ExcelUtil.getStringCellValue(row, 3);
            final String series = ExcelUtil.getStringCellValue(row, 4);
            final int markId = ExcelUtil.getIntCellValue(row, 5);
            final int formId = ExcelUtil.getIntCellValue(row, 6);
            final int filtrationId = ExcelUtil.getIntCellValue(row, 7);
//            final int homogenization = ExcelUtil.getIntCellValue(row, 8);
            final int clipping = ExcelUtil.getIntCellValue(row, 9);
            final int cob = ExcelUtil.getIntCellValue(row, 10);
            final double siMin = ExcelUtil.getDoubleCellValue(row, 11);
            final double siMax = ExcelUtil.getDoubleCellValue(row, 12);
            final double feMin = ExcelUtil.getDoubleCellValue(row, 13);
            final double feMax = ExcelUtil.getDoubleCellValue(row, 14);
            final double cuMin = ExcelUtil.getDoubleCellValue(row, 15);
            final double cuMax = ExcelUtil.getDoubleCellValue(row, 16);
            final double mgMin = ExcelUtil.getDoubleCellValue(row, 17);
            final double mgMax = ExcelUtil.getDoubleCellValue(row, 18);
            final double mnMin = ExcelUtil.getDoubleCellValue(row, 19);
            final double mnMax = ExcelUtil.getDoubleCellValue(row, 20);
            final double tiMin = ExcelUtil.getDoubleCellValue(row, 25);
            final double tiMax = ExcelUtil.getDoubleCellValue(row, 26);

            Product product = new Product();
            product.setId(productId);
            product.setName(productName);
            product.setPlant(new Plant(plantId));
            product.setSpec(spec);
            product.setSeries(series);
            product.setMark(new Mark(markId));
            product.setForm(new Form(formId));
            product.setFiltration(new Filtration(filtrationId));
//            product.setHomogenization();
            product.setClipping(clipping);
            product.setCob(cob);
            product.setSiMin(siMin);
            product.setSiMax(siMax);
            product.setFeMin(feMin);
            product.setFeMax(feMax);
            product.setCuMin(cuMin);
            product.setCuMax(cuMax);
            product.setMgMin(mgMin);
            product.setMgMax(mgMax);
            product.setMnMin(mnMin);
            product.setMnMax(mnMax);
            product.setTiMin(tiMin);
            product.setTiMax(tiMax);

            products.add(product);
        }

        return products;
    }
}

