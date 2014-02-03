package ru.org.icad.mishka.app.loader.excel;

import org.testng.annotations.Test;
import ru.org.icad.mishka.app.TableName;
import ru.org.icad.mishka.app.model.Heater;

import java.net.URL;
import java.net.URLDecoder;
import java.util.List;

import static org.testng.Assert.assertTrue;

public class HeaterLoaderTest {

    @Test
    public void testLoad() throws Exception {
        URL url = HeaterLoaderTest.class.getResource("/init/dictionaries/excelTables.xlsx");
        String filePath = URLDecoder.decode(url.getPath(), "UTF-8");

        HeaterLoader heaterLoader = new HeaterLoader();
        List<Heater> marks = heaterLoader.load(filePath, TableName.HEATER);

        assertTrue(marks.size() == 4);
    }
}
