package ru.org.icad.mishka.app.util;

import org.jetbrains.annotations.Nullable;

import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public final class TimeUtil {

    public static final String DD_MM_YYYY_HH_MM_SS = "dd/MM/yyyy HH:mm:ss";

    private TimeUtil() {
    }

    public static String convertTimeToString(long time) {
        DateFormat df = new SimpleDateFormat(DD_MM_YYYY_HH_MM_SS);

        return df.format(time);
    }

    @Nullable
    public static Date stringToDate(String string) {
        SimpleDateFormat format = new SimpleDateFormat(DD_MM_YYYY_HH_MM_SS);
        try {
            return new Date(format.parse(string).getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
}
