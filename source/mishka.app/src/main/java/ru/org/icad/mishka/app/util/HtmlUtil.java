package ru.org.icad.mishka.app.util;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

public class HtmlUtil {

    private HtmlUtil() {
    }

    public static String objectToTableRow(Object o) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("<tr>" + "\n");

        Field[] fields = o.getClass().getDeclaredFields();
        for (Field field : fields) {
            if (!Modifier.isPrivate(field.getModifiers())) {
                continue;
            }

            try {
                field.setAccessible(true);

                stringBuilder.append("<td>" + "\n")
                        .append(field.get(o))
                        .append("</td>" + "\n");
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        stringBuilder.append("</tr>" + "\n");

        return stringBuilder.toString();
    }
}
