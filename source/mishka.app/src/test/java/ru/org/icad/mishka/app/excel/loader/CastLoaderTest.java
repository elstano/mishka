package ru.org.icad.mishka.app.excel.loader;

import org.testng.annotations.Test;
import ru.org.icad.mishka.app.loader.excel.CastLoader;
import ru.org.icad.mishka.app.model.Cast;

import java.net.URL;
import java.net.URLDecoder;
import java.util.List;

import static org.testng.Assert.assertTrue;

public class CastLoaderTest {

    @Test
    public void testLoad() throws Exception {
        URL url = CastLoaderTest.class.getResource("/последовательность ходок.xlsx");
        String filePath = URLDecoder.decode(url.getPath(), "UTF-8");

        CastLoader castLoader = new CastLoader();
        List<Cast> casts = castLoader.load(filePath, "Sheet1");

        assertTrue(casts.size() == 1056);
    }
}
