package ru.org.icad.mishka.app.versions;

import ru.org.icad.mishka.app.jdbc.JDBCHandler;
import ru.org.icad.mishka.app.jdbc.JDBCTool;
import ru.org.icad.mishka.app.jdbc.ParamsProvider;
import ru.org.icad.mishka.app.jdbc.RSHandler;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.sql.*;
import java.util.Collections;
import java.util.List;
import java.util.StringTokenizer;

/**
 * User: Boss
 * Date: 10/3/13
 * Time: 11:33 PM
 */
public class UpgradeManager {
    public static final Version NOT_INSTALLED = new Version();
    public static final String TABLE_INSTALLED_VERSIONS = "INSTALLED_VERSIONS";
    public static final String COL_MAJOR_TABLE_VERSION = "major";
    public static final String COL_MINOR_TABLE_VERSION = "minor";
    public static final String COL_ASSEMBLY_TABLE_VERSION = "assembly";
    public static final String COL_INSTALLED_TABLE_VERSION = "installed";

    private static final Log log = LogFactory.getLog(UpgradeManager.class);

    public boolean isVersioningInitialized() throws SQLException {
        return JDBCTool.instance().executeQuery(
                "select table_name from user_tables where table_name = ?",
                new JDBCHandler<Boolean>() {
                    @Override
                    public Boolean onResultSet(ResultSet rs) throws SQLException {
                        if(rs.next()){
                            return true;
                        }
                        return false;
                    }

                    @Override
                    public void parametrize(PreparedStatement statement) throws SQLException {
                        statement.setString(1, TABLE_INSTALLED_VERSIONS);
                    }
                }
        );
    }

    public Version getCurrentVersion() throws SQLException {
        if(!isVersioningInitialized()){
            initializeVersions();
            return NOT_INSTALLED;
        }

        Integer versionsCount = JDBCTool.instance().selectInt("select count(*) from installed_versions", null);

        if(versionsCount == null || versionsCount == 0){
            return NOT_INSTALLED;
        }

        return JDBCTool.instance().executeQuery(
                "select major, minor, assembly, installed " +
                        "from installed_versions where installed = " +
                        "   (select max(installed) from installed_versions)",
                new RSHandler<Version>() {
                    @Override
                    public Version onResultSet(ResultSet rs) throws SQLException {
                        if(rs.next()){
                            int major = rs.getInt(COL_MAJOR_TABLE_VERSION);
                            int minor = rs.getInt(COL_MINOR_TABLE_VERSION);
                            Date installed = rs.getDate(COL_INSTALLED_TABLE_VERSION);
                            int assembly = rs.getInt(COL_ASSEMBLY_TABLE_VERSION);
                            return new Version(major, minor, assembly, installed);
                        }
                        return new Version();
                    }
                }
        );
    }

    private void initializeVersions() throws SQLException {
        JDBCTool.instance().executeUpdate(
                "create table installed_versions (\n" +
                        "major int NOT NULL,\n" +
                        "minor int NOT NULL,\n" +
                        "assembly int, \n" +
                        "installed date default sysdate NOT NULL\n" +
                        ")"
        );
    }

    public void setInstalledVersion(final Version version) throws SQLException {
        JDBCTool.instance().executeUpdate(
                "insert into installed_versions (major, minor, assembly, installed) values (?,?,?,?)",
                new ParamsProvider(){
                    @Override
                    public void parametrize(PreparedStatement statement) throws SQLException {
                        statement.setInt(1, version.getMajor());
                        statement.setInt(2, version.getMinor());
                        statement.setInt(3, version.getAssembly());
                        statement.setDate(4, new Date(System.currentTimeMillis()));
                    }
                }
        );
    }

    public void performUpgrade(List<VersionInstaller> installers) throws SQLException {
        Collections.sort(installers);
        Version currentVersion = getCurrentVersion();
        log.info("Upgrading from version " + currentVersion);
        for(VersionInstaller installer: installers ){
            if(installer.getVersion().compareTo(currentVersion) <= 0){
                log.info("Version" + installer.getVersion() + " is already installed");
            } else {
                log.info("Installing version " + installer.getVersion());
                install(installer);
            }
        }
    }

    public void install(VersionInstaller installer) throws SQLException {
        for(String script: installer.getScripts().values()){
            //simplest option
            StringTokenizer tok = new StringTokenizer(script, ";", false);
            while(tok.hasMoreTokens()){
                String toRun = tok.nextToken();
                JDBCTool.instance().executeUpdate(toRun);
            }
            setInstalledVersion(installer.getVersion());
        }
    }
}
