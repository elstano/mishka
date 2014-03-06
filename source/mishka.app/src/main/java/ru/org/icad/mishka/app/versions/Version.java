package ru.org.icad.mishka.app.versions;

import java.util.Date;

public class Version implements Comparable<Version>{
    private int major;
    private int minor;
    private int assembly;
    private Date installed;

    public Version() {
        this.major = 0;
        this.minor = 0;
        this.assembly = 0;
        this.installed = new Date(0);
    }

    public Version(int major, int minor, int assembly, Date installed) {
        this.major = major;
        this.minor = minor;
        this.assembly = assembly;
        this.installed = installed;
    }

    public Version(int major, int minor, int assembly) {
        this.major = major;
        this.minor = minor;
        this.assembly = assembly;
        this.installed = null;
    }

    public Version(int major, int minor){
        this.major = major;
        this.minor = minor;
        this.assembly = -1;
        this.installed = null;
    }

    public int getMajor() {
        return major;
    }

    public int getMinor() {
        return minor;
    }

    public int getAssembly() {
        return assembly;
    }

    public Date getInstalled() {
        return installed;
    }

    @Override
    public int compareTo(Version o) {
        if(this.major != o.major){
            return this.major - o.major;
        }
        return this.minor - o.minor;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append(major).append(".").append(minor).append(".").append(assembly);
        if(installed == null){
            result.append(" (").append(installed).append(") ");
        }
        return  result.toString();
    }
}
