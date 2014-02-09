package ru.org.icad.mishka.app.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

public final class TimeUtil {

    private TimeUtil() {
    }

    public static String convertTimeToString(long time) {
        DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");

        return df.format(time);
    }
}
