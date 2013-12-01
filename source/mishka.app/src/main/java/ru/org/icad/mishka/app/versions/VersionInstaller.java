package ru.org.icad.mishka.app.versions;

import java.util.TreeMap;

/**
 * User: Boss
 * Date: 10/9/13
 * Time: 1:13 AM
 */
public class VersionInstaller implements Comparable<VersionInstaller>{
    private int major;
    private int minor;

    private TreeMap<String, String> scripts;

    public VersionInstaller(int major, int minor) {
        this.major = major;
        this.minor = minor;
        this.scripts = new TreeMap<>();
    }

    public int getMajor() {
        return major;
    }

    public void setMajor(int major) {
        this.major = major;
    }

    public int getMinor() {
        return minor;
    }

    public void setMinor(int minor) {
        this.minor = minor;
    }

    public TreeMap<String, String> getScripts() {
        return scripts;
    }

    public Version getVersion(){
        return new Version(major, minor);
    }

    @Override
    public int compareTo(VersionInstaller o) {
        if(this.getMajor() != o.getMajor()){
            return this.getMajor() - o.getMajor();
        }
        return this.getMinor() - o.getMinor();
    }
}
