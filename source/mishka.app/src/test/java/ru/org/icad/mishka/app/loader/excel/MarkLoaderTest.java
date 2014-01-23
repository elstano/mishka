package ru.org.icad.mishka.app.loader.excel;

import org.testng.annotations.Test;
import ru.org.icad.mishka.app.model.Mark;
import ru.org.icad.mishka.app.model.Plant;

import java.net.URL;
import java.net.URLDecoder;
import java.util.List;

import static org.testng.Assert.assertTrue;

public class MarkLoaderTest {
    @Test
    public void testLoad() throws Exception {
        URL url = MarkLoaderTest.class.getResource("/init/dictionaries/MARK.xlsx");
        String filePath = URLDecoder.decode(url.getPath(), "UTF-8");

        MarkLoader markLoader = new MarkLoader();
        List<Mark> marks = markLoader.load(filePath, "MARK");

        assertTrue(marks.size() == 372);
    }
}
