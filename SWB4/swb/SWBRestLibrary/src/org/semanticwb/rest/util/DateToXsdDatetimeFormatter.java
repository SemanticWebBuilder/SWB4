/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.rest.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.IllegalFormatException;
import java.util.TimeZone;

/**
 *
 * @author victor.lorenzana
 */
public class DateToXsdDatetimeFormatter {

    private final static SimpleDateFormat date_time = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");

    private final static SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-ddZ");

    public DateToXsdDatetimeFormatter () {}

    public DateToXsdDatetimeFormatter (TimeZone timeZone)  {
        date_time.setTimeZone(timeZone);
        date.setTimeZone(timeZone);
    }

    /**
    *  Parse a xml date string in the format produced by this class only.
    *  This method cannot parse all valid xml date string formats -
    *  so don't try to use it as part of a general xml parser
    */
    public synchronized Date parse(String xmlDateTime) throws ParseException  {
        if ( xmlDateTime.length() != 25 )  {
            throw new ParseException("Date not in expected xml datetime format", 0);
        }

        StringBuilder sb = new StringBuilder(xmlDateTime);
        sb.deleteCharAt(22);
        return date_time.parse(sb.toString());
    }

    public synchronized Date parseDate(String xmlDateTime) throws ParseException  {
        if ( xmlDateTime.length() != 16 )  {
            throw new ParseException("Date not in expected xml datetime format", 0);
        }

        StringBuilder sb = new StringBuilder(xmlDateTime);
        sb.deleteCharAt(22);
        return date_time.parse(sb.toString());
    }

    public synchronized String format(Date xmlDateTime) throws IllegalFormatException  {
        String s =  date_time.format(xmlDateTime);
        StringBuilder sb = new StringBuilder(s);
        sb.insert(22, ':');
        return sb.toString();
    }
    public synchronized String formatDate(Date xmlDateTime) throws IllegalFormatException  {
        String s =  date.format(xmlDateTime);
        StringBuilder sb = new StringBuilder(s);
        sb.insert(13, ':');
        return sb.toString();
    }

    public synchronized void setTimeZone(String timezone)  {
        date_time.setTimeZone(TimeZone.getTimeZone(timezone));
    }

}
