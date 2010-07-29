
package org.semanticwb.process.utils;

import java.util.Date;
import java.util.Calendar;
import java.util.GregorianCalendar;


/**
 *
 * @author Sergio Téllez
 */
public class DateInterval {

    private String dateInit = null;
    private String dateFinal = null;

    public DateInterval(String dateInit, String dateFinal) {
        this.dateInit = dateInit;
        this.dateFinal = dateFinal;
    }

    public void setDateInit(String dateInit) {
        this.dateInit = dateInit;
    }

    public void setDateFinal(String dateFinal) {
        this.dateFinal = dateFinal;
    }

    public String getDateInit() {
        if (null!=dateInit)
            return this.dateInit;
        else
            return "";
    }

    public String getDateFinal() {
        if (null!=dateFinal)
            return this.dateFinal;
        else
            return "";
    }

    public int getYearDateInit() {
        return Integer.parseInt(dateInit.substring(6,10));
    }

    public int getMonthDateInit() {
        return Integer.parseInt(dateInit.substring(3,5));
    }

    public int getDayDateInit() {
        return Integer.parseInt(dateInit.substring(0,2));
    }

    public int getYearDateFinal() {
        return Integer.parseInt(dateFinal.substring(6,10));
    }

    public int getMonthDateFinal() {
        return Integer.parseInt(dateFinal.substring(3,5));
    }

    public int getDayDateFinal() {
        return Integer.parseInt(dateFinal.substring(0,2));
    }

    public boolean equalsToDateInit(Date anotherDate) {
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTime(anotherDate);
        if (calendar.get(Calendar.YEAR)==getYearDateInit() && calendar.get(Calendar.MONTH)==(getMonthDateInit()-1) && calendar.get(Calendar.DAY_OF_MONTH)==getDayDateInit())
            return true;
        return false;
    }

    public boolean equalsToDateFinal(Date anotherDate) {
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTime(anotherDate);
        if (calendar.get(Calendar.YEAR)==getYearDateFinal() && calendar.get(Calendar.MONTH)==(getMonthDateFinal()-1) && calendar.get(Calendar.DAY_OF_MONTH)==getDayDateFinal())
            return true;
        return false;
    }

    public Date dateInit() {
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.set(getYearDateInit(), getMonthDateInit(), getDayDateInit());
        return calendar.getTime();
    }

    public Date dateFinal() {
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.set(getYearDateFinal(), getMonthDateFinal(), getDayDateFinal());
        return calendar.getTime();
    }

    @Override
    public String toString() {
        return getDateInit() + " " + getDateFinal();
    }
}
