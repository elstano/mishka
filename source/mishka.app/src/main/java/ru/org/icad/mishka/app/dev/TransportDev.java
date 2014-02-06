package ru.org.icad.mishka.app.dev;

import org.jetbrains.annotations.Nullable;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class TransportDev {

    private static final String START_DATE = "01/05/2013";
    private static final String END_DATE = "01/06/2013";

    @PersistenceContext(unitName = "MishkaService")
    private EntityManager em;

    public void transportProcess() {
        Date startDate = stringToDate(START_DATE);
        Date endDate = stringToDate(END_DATE);

    }


    @Nullable
    private Date stringToDate(String string) {
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        try {
            return new Date(format.parse(string).getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
}