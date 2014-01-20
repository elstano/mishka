package ru.org.icad.mishka.app.excel.loader;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class FileLoader {

    public static List<File> load(String path) {
        File dir = new File(path);
        if (dir.exists() && dir.isDirectory()) {
            File temp = new File(path + "/temp");
            List<File> files = Arrays.asList(dir.listFiles());
                for(File file : files) {
                    try {
                        if(file.isDirectory()) {
                            files.remove(file);
                            continue;
                        }
                        FileUtils.moveFileToDirectory(file, temp, true);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

            return files;
            }


        return Collections.emptyList();
    }
}
