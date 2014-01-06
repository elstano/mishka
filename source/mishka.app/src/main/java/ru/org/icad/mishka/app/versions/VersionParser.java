package ru.org.icad.mishka.app.versions;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * User: Boss
 * Date: 10/9/13
 * Time: 1:16 AM
 */
public class VersionParser {
    public static final String INSTALLER_RES = "installer/installer.zip";
    public static final int BUF_SIZE = 31000;

    private static final Logger LOGGER = LoggerFactory.getLogger(VersionParser.class);

    private Map<String, VersionInstaller> installers = new HashMap<>();

    public void parseInstallers() throws IOException {
        InputStream in = this.getClass().getClassLoader().getResourceAsStream(INSTALLER_RES);
        ZipInputStream zin = new ZipInputStream(in);
        ZipEntry zentry;

        while((zentry = zin.getNextEntry()) != null){
            if(zentry.isDirectory()){
                continue;
            }
            ByteArrayOutputStream bos = new ByteArrayOutputStream();

            byte[] buf = new byte[BUF_SIZE];
            int length = 0;
            while((length=zin.read(buf, 0,BUF_SIZE)) > 0){
                bos.write(buf, 0, length);
            }
            String content = bos.toString();
            String[] version = getVersion(zentry.getName());
            if(version != null){
                addScript(version, zentry.getName(), content);
            }

        }
    }

    private void addScript(String[] version, String name, String content){
        String key = version[0] + "." + version[1];
        VersionInstaller installer = installers.get(key);
        if(installer == null){
            if(StringUtils.isBlank(version[0]) || !StringUtils.isNumeric(version[0])){
                LOGGER.warn("invalid major version skipped: " + version[0]);
                return;
            }
            if(StringUtils.isBlank(version[1]) || !StringUtils.isNumeric(version[1])){
                LOGGER.warn("invalid major version skipped: " + version[1]);
                return;
            }
            installer = new VersionInstaller(Integer.parseInt(version[0]), Integer.parseInt(version[1]));
            installers.put(key, installer);
        }

        installer.getScripts().put(name, content);
    }

    private String[] getVersion(String name){
        if(!name.contains("install")){
            return null;
        }
        StringTokenizer tokens = new StringTokenizer(name, "/", false);
        String major = null;
        String minor = null;
        for(int i=0; tokens.hasMoreTokens(); i++){
            String token = tokens.nextToken();
            if(!StringUtils.isNumeric(token)){
                continue;
            }
            if(i == 1){
                major = token;
            } else if(i == 2){
                minor = token;
            }
        }
        if(major != null && minor != null){
            return new String[]{major, minor};
        }
        return null;
    }

    public List<VersionInstaller> getInstallers(){
        return new ArrayList<>(installers.values());
    }
}
