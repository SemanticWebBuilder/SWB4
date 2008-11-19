/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.jcr170.implementation;

import java.io.InputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import javax.jcr.Node;
import javax.jcr.PropertyType;
import javax.jcr.RepositoryException;
import javax.jcr.Value;
import javax.jcr.ValueFormatException;

/**
 *
 * @author victor.lorenzana
 */
public class ValueImp implements Value
{      
    private static SimpleDateFormat iso8601dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss"); 
    private final int type;
    Object value;
    ValueImp(Object value,int type)
    {
        this.type=type;
        this.value=value;
    }
    public String getString() throws ValueFormatException, IllegalStateException, RepositoryException
    {
        String valueString=value.toString();
        if(value instanceof Node && type==PropertyType.REFERENCE)
        {
            valueString=((Node)value).getUUID();
        }
        if(value instanceof Calendar)
        {
            valueString=iso8601dateFormat.format(((Calendar)value).getTime());
        }
        return valueString;
    }

    public InputStream getStream() throws IllegalStateException, RepositoryException
    {
        return null;
    }

    public long getLong() throws ValueFormatException, IllegalStateException, RepositoryException
    {
        try
        {
            return Long.parseLong(value.toString());
        }
        catch(NumberFormatException nfe)
        {
            throw new ValueFormatException(nfe);
        }
    }

    public double getDouble() throws ValueFormatException, IllegalStateException, RepositoryException
    {
        try
        {
            return Double.parseDouble(value.toString());
        }
        catch(NumberFormatException nfe)
        {
            throw new ValueFormatException(nfe);
        }
    }

    public Calendar getDate() throws ValueFormatException, IllegalStateException, RepositoryException
    {
        Calendar calendar=null;
        if(type==PropertyType.DATE && value instanceof Calendar)
        {
            calendar=(Calendar)value;
        }
        else
        {
            String stringDate=getString();
            try
            {
                Date date=DateFormat.getDateTimeInstance().parse(stringDate);
                calendar=Calendar.getInstance();
                calendar.setTime(date);                
            }
            catch(ParseException pe)
            {
                throw new ValueFormatException(pe);
            }            
        }
        return calendar;
    }

    public boolean getBoolean() throws ValueFormatException, IllegalStateException, RepositoryException
    {
        try
        {
            return Boolean.parseBoolean(value.toString());
        }
        catch(NumberFormatException nfe)
        {
            throw new ValueFormatException(nfe);
        }
    }

    public int getType()
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
