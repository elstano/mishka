package ru.org.icad.mishka.app.loader.excel;

import org.testng.annotations.Test;
import ru.org.icad.mishka.app.model.CastHouse;

import java.net.URL;
import java.net.URLDecoder;
import java.util.List;

import static org.testng.Assert.assertTrue;

public class CastHouseLoaderTest {

    @Test
    public void testLoad() throws Exception {
        URL url = MarkLoaderTest.class.getResource("/init/dictionaries/CAST_HOUSE.xlsx");
        String filePath = URLDecoder.decode(url.getPath(), "UTF-8");

        CastHouseLoader castHouseLoader = new CastHouseLoader();
        List<CastHouse> castHouses = castHouseLoader.load(filePath, "CAST_HOUSE");

        assertTrue(castHouses.size() == 12);
    }
}
