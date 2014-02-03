package ru.org.icad.mishka.app.loader.excel;

import org.testng.annotations.Test;
import ru.org.icad.mishka.app.model.Plant;

import java.net.URL;
import java.net.URLDecoder;
import java.util.List;

import static org.testng.Assert.assertTrue;

public class PlantLoaderTest {
    @Test
    public void testLoad() throws Exception {
        URL url = ElectrolizerPrognosisLoaderTest.class.getResource("/init/dictionaries/excelTables.xlsx");
        String filePath = URLDecoder.decode(url.getPath(), "UTF-8");

        PlantLoader plantLoader = new PlantLoader();
        List<Plant> electrolizerPrognosises = plantLoader.load(filePath, "PLANT");

        assertTrue(electrolizerPrognosises.size() == 6);
    }
}
