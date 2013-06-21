/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.base.util;

import java.text.ParseException;
import java.util.Date;

/**
 *
 * @author javier.solis.g
 */
public class SimpleDateFormatTS
{
    private final ThreadLocal<java.text.SimpleDateFormat> sd;
    
    public SimpleDateFormatTS(final String patern)
    {
        sd = new ThreadLocal<java.text.SimpleDateFormat>()
        {
            @Override
            protected java.text.SimpleDateFormat initialValue()
            {
                return new java.text.SimpleDateFormat(patern);
            }
        };            
    }    
    
    public Date parse(String txt) throws ParseException
    {
        return sd.get().parse(txt);
    }
    
    public String format(Date date)
    {
        return sd.get().format(date);
    }
}
