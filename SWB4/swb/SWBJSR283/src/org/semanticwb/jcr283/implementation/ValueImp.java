/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.jcr283.implementation;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.net.URI;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;
import javax.jcr.Binary;
import javax.jcr.Node;
import javax.jcr.PropertyType;
import javax.jcr.RepositoryException;
import javax.jcr.Value;
import javax.jcr.ValueFormatException;
import javax.xml.namespace.QName;
import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;

/**
 *
 * @author victor.lorenzana
 */
public class ValueImp implements Value
{

    static Logger log = SWBUtils.getLogger(ValueImp.class);
    private static SimpleDateFormat iso8601dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
    private final int type;
    ByteArrayOutputStream out = new ByteArrayOutputStream();
    Object value;

    ValueImp(Object value, int type) throws RepositoryException
    {
        this.type = type;

        if (value instanceof InputStream)
        {
            byte[] buffer = new byte[2048];
            InputStream in = (InputStream) value;
            try
            {
                int read = in.read(buffer);
                while (read != -1)
                {
                    out.write(buffer, 0, read);
                    read = in.read(buffer);
                }
                this.value = out;
            }
            catch (IOException ioe)
            {
                throw new IllegalArgumentException(ioe);
            }

        }
        else if (value instanceof String)
        {
            String ovalue = (String) value;
            switch (type)
            {
                case PropertyType.STRING:
                    this.value = value;
                    break;
                case PropertyType.BINARY:
                    this.value = toBinary(ovalue);
                    break;
                case PropertyType.BOOLEAN:
                    this.value = toBoolean(ovalue);
                    break;
                case PropertyType.DATE:
                    this.value = toDate(ovalue);
                    break;
                case PropertyType.DECIMAL:
                    this.value = toDecimal(ovalue);
                    break;
                case PropertyType.DOUBLE:
                    this.value = toDouble(ovalue);
                    break;
                case PropertyType.LONG:
                    this.value = toLong(ovalue);
                    break;
                case PropertyType.NAME:
                    this.value = toName(ovalue);
                    break;
                case PropertyType.PATH:
                    this.value = toPath(ovalue);
                    break;
                case PropertyType.REFERENCE:
                    this.value = toReference(ovalue);
                    break;
                case PropertyType.URI:
                    this.value = toUri(ovalue);
                    break;
                case PropertyType.WEAKREFERENCE:
                    this.value = toWeakReference(ovalue);
                    break;

                default:
                    throw new ValueFormatException("The value can not be converted");

            }
        }
        else if (value instanceof Binary)
        {
            Binary ovalue = (Binary) value;
            String newvalue = toString(ovalue);
            switch (type)
            {
                case PropertyType.STRING:
                    this.value = newvalue;
                case PropertyType.BINARY:
                    this.value = value;
                    break;
                case PropertyType.BOOLEAN:
                    this.value = toBoolean(newvalue);
                    break;
                case PropertyType.DATE:
                    this.value = toDate(newvalue);
                    break;
                case PropertyType.DECIMAL:
                    this.value = toDecimal(newvalue);
                    break;
                case PropertyType.DOUBLE:
                    this.value = toDouble(newvalue);
                    break;
                case PropertyType.LONG:
                    this.value = toLong(newvalue);
                    break;
                case PropertyType.NAME:
                    this.value = toName(newvalue);
                    break;
                case PropertyType.PATH:
                    this.value = toPath(newvalue);
                    break;
                case PropertyType.REFERENCE:
                    this.value = toReference(newvalue);
                    break;
                case PropertyType.URI:
                    this.value = toUri(newvalue);
                    break;
                case PropertyType.WEAKREFERENCE:
                    this.value = toWeakReference(newvalue);
                    break;
                default:
                    throw new ValueFormatException("The value can not be converted");
            }

        }
        else if (value instanceof Calendar)
        {
            Calendar ovalue = (Calendar) value;
            switch (type)
            {
                case PropertyType.STRING:
                    this.value = toString(ovalue);
                    break;
                case PropertyType.DATE:
                    this.value = ovalue;
                    break;
                case PropertyType.DECIMAL:
                    this.value = toDecimal(ovalue);
                    break;
                case PropertyType.DOUBLE:
                    this.value = toDouble(ovalue);
                    break;
                case PropertyType.LONG:
                    this.value = toLong(ovalue);
                    break;
                default:
                    throw new ValueFormatException("The value can not be converted");

            }
        }
        else if (value instanceof Double)
        {
            Double ovalue = (Double) value;
            switch (type)
            {
                case PropertyType.STRING:
                    this.value = toString(ovalue);
                    break;
                case PropertyType.BINARY:
                    this.value = toBinary(ovalue);
                    break;
                case PropertyType.DATE:
                    this.value = toDate(ovalue);
                    break;
                case PropertyType.DECIMAL:
                    this.value = toDecimal(ovalue);
                    break;
                case PropertyType.DOUBLE:
                    this.value = value;
                    break;
                case PropertyType.LONG:
                    this.value = toLong(ovalue);
                    break;
                default:
                    throw new ValueFormatException("The value can not be converted");

            }
        }
        else if (value instanceof BigDecimal)
        {
            BigDecimal ovalue = (BigDecimal) value;
            switch (type)
            {
                case PropertyType.STRING:
                    this.value = toString(ovalue);
                    break;
                case PropertyType.BINARY:
                    this.value = toBinary(ovalue);
                    break;
                case PropertyType.DATE:
                    this.value = toDate(ovalue);
                    break;
                case PropertyType.DECIMAL:
                    this.value = ovalue;
                    break;
                case PropertyType.DOUBLE:
                    this.value = toDouble(ovalue);
                    break;
                case PropertyType.LONG:
                    this.value = toLong(ovalue);
                    break;
                default:
                    throw new ValueFormatException("The value can not be converted");

            }
        }
        else if (value instanceof Long)
        {
            Long ovalue = (Long) value;
            switch (type)
            {
                case PropertyType.STRING:
                    this.value = toString(ovalue);
                    break;
                case PropertyType.BINARY:
                    this.value = toBinary(ovalue);
                    break;
                case PropertyType.DATE:
                    this.value = toDate(ovalue);
                    break;
                case PropertyType.DECIMAL:
                    this.value = toDecimal(ovalue);
                    break;
                case PropertyType.DOUBLE:
                    this.value = toDouble(ovalue);
                    break;
                case PropertyType.LONG:
                    this.value = ovalue;
                    break;
                default:
                    throw new ValueFormatException("The value can not be converted");

            }
        }
        else if (value instanceof Boolean)
        {
            Boolean ovalue = (Boolean) value;
            switch (type)
            {
                case PropertyType.STRING:
                    this.value = toString(ovalue);
                    break;
                case PropertyType.BINARY:
                    this.value = toBinary(ovalue);
                    break;
                default:
                    throw new ValueFormatException("The value can not be converted");

            }
        }
        else if (value instanceof QName)
        {
            QName ovalue = (QName) value;
            switch (type)
            {
                case PropertyType.STRING:
                    this.value = toString(ovalue);
                    break;
                case PropertyType.BINARY:
                    this.value = toBinary(ovalue);
                    break;

                case PropertyType.PATH:
                    this.value = toPath(ovalue);
                    break;

                case PropertyType.URI:
                    this.value = toUri(ovalue);
                    break;


                default:
                    throw new ValueFormatException("The value can not be converted");

            }
        }
        else if (value instanceof URL)
        {
            URL ovalue = (URL) value;
            switch (type)
            {
                case PropertyType.STRING:
                    this.value = toString(ovalue);
                    break;
                case PropertyType.BINARY:
                    this.value = toBinary(ovalue);
                    break;
                case PropertyType.NAME:
                    this.value = toName(ovalue);
                    break;
                case PropertyType.PATH:
                    this.value = ovalue;
                    break;
                case PropertyType.URI:
                    this.value = toUri(ovalue);
                    break;
                default:
                    throw new ValueFormatException("The value can not be converted");

            }
        }
        else if (value instanceof URI)
        {
            URI ovalue = (URI) value;
            switch (type)
            {
                case PropertyType.STRING:
                    this.value = toString(ovalue);
                    break;
                case PropertyType.BINARY:
                    this.value = toBinary(ovalue);
                    break;
                case PropertyType.NAME:
                    this.value = toName(ovalue);
                    break;
                case PropertyType.PATH:
                    this.value = toPath(ovalue);
                    break;                
                case PropertyType.URI:
                    this.value = ovalue;
                    break;
                default:
                    throw new ValueFormatException("The value can not be converted");

            }
        }
        else
        {

            this.value = value;
        }
    }

    public String getString() throws ValueFormatException, IllegalStateException, RepositoryException
    {
        if (value == null)
        {
            return null;
        }
        String valueString = value.toString();
        if (value instanceof Node && type == PropertyType.REFERENCE)
        {
            valueString = ((Node) value).getUUID();
        }
        if (value instanceof Calendar)
        {
            //valueString = iso8601dateFormat.format(((Calendar) value).getTime());
            valueString = SWBUtils.TEXT.iso8601DateFormat(((Calendar) value).getTime());
        }
        return valueString;
    }

    public InputStream getStream() throws IllegalStateException, RepositoryException
    {
        if (value == null)
        {
            return null;
        }
        if (value instanceof ByteArrayOutputStream && type == PropertyType.BINARY)
        {
            ByteArrayOutputStream mout = (ByteArrayOutputStream) value;
            return new ByteArrayInputStream(mout.toByteArray());
        }
        return null;
    }

    public long getLong() throws ValueFormatException, IllegalStateException, RepositoryException
    {
        if (value == null)
        {
            throw new ValueFormatException();
        }
        try
        {
            return Long.parseLong(value.toString());
        }
        catch (NumberFormatException nfe)
        {
            throw new ValueFormatException(nfe);
        }
    }

    public double getDouble() throws ValueFormatException, IllegalStateException, RepositoryException
    {
        if (value == null)
        {
            throw new ValueFormatException();
        }
        try
        {
            return Double.parseDouble(value.toString());
        }
        catch (NumberFormatException nfe)
        {
            throw new ValueFormatException(nfe);
        }
    }

    public Calendar getDate() throws ValueFormatException, IllegalStateException, RepositoryException
    {
        if (value == null)
        {
            return null;
        }
        Calendar calendar = null;
        if (type == PropertyType.DATE && value instanceof Calendar)
        {
            calendar = (Calendar) value;
        }
        else
        {
            String stringDate = getString();
            try
            {
                Date date = SWBUtils.TEXT.iso8601DateParse(stringDate);
                calendar = Calendar.getInstance();
                calendar.setTime(date);
            }
            catch (ParseException pe)
            {
                try
                {
                    Date date = iso8601dateFormat.parse(stringDate);
                    calendar = Calendar.getInstance();
                    calendar.setTime(date);
                }
                catch (ParseException pe2)
                {
                    throw new ValueFormatException(pe);
                }
            }
        }
        return calendar;
    }

    public boolean getBoolean() throws ValueFormatException, IllegalStateException, RepositoryException
    {
        if (value == null)
        {
            throw new ValueFormatException();
        }
        try
        {
            return Boolean.parseBoolean(value.toString());
        }
        catch (NumberFormatException nfe)
        {
            throw new ValueFormatException(nfe);
        }
    }

    public int getType()
    {
        return type;
    }

    public Binary getBinary() throws RepositoryException
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public BigDecimal getDecimal() throws ValueFormatException, RepositoryException
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    private Binary toBinary(String value) throws RepositoryException
    {
        try
        {
            byte[] convert = value.getBytes("utf-8");
            return new BinaryImp(convert);
        }
        catch (Exception e)
        {
            throw new RepositoryException(e);
        }
    }

    private Calendar toDate(String value) throws ValueFormatException
    {
        try
        {
            Date date = iso8601dateFormat.parse(value);
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            return cal;
        }
        catch (Exception e)
        {
            throw new ValueFormatException(e);
        }
    }

    private Double toDouble(String value) throws ValueFormatException
    {
        try
        {
            return Double.valueOf(value);
        }
        catch (Exception e)
        {
            throw new ValueFormatException(e);
        }
    }

    private BigDecimal toDecimal(String value) throws ValueFormatException
    {
        try
        {
            return new BigDecimal(value);
        }
        catch (Exception e)
        {
            throw new ValueFormatException(e);
        }
    }

    private Long toLong(String value) throws ValueFormatException
    {
        try
        {
            return Long.valueOf(value);
        }
        catch (Exception e)
        {
            throw new ValueFormatException(e);
        }
    }

    private Boolean toBoolean(String value) throws ValueFormatException
    {
        try
        {
            return Boolean.valueOf(value);
        }
        catch (Exception e)
        {
            throw new ValueFormatException(e);
        }
    }

    private QName toName(String value) throws ValueFormatException
    {
        int pos = value.indexOf(":");
        if (pos != -1)
        {
            String prefix = value.substring(0, pos);
            String name = value.substring(pos + 1);
            NamespaceRegistryImp reg = new NamespaceRegistryImp();
            try
            {
                String uri = reg.getURI(prefix);
                if (uri == null)
                {
                    throw new ValueFormatException("The uri for the prefix " + prefix + " was not found");
                }
                return new QName(uri, name);
            }
            catch (Exception e)
            {
                throw new ValueFormatException(e);
            }
        }
        else
        {
            throw new ValueFormatException("The prefix was not found");
        }

    }

    private URL toPath(String value) throws ValueFormatException
    {
        try
        {
            return new URL(value);
        }
        catch (Exception e)
        {
            throw new ValueFormatException(e);
        }
    }

    private URI toUri(String value) throws ValueFormatException
    {
        try
        {
            return new URI(value);
        }
        catch (Exception e)
        {
            throw new ValueFormatException(e);
        }
    }

    private UUID toReference(String value) throws ValueFormatException
    {
        try
        {
            return UUID.fromString(value);
        }
        catch (Exception e)
        {
            throw new ValueFormatException(e);
        }
    }

    private UUID toWeakReference(String value) throws ValueFormatException
    {
        try
        {
            return UUID.fromString(value);
        }
        catch (Exception e)
        {
            throw new ValueFormatException(e);
        }
    }

    private String toString(Binary value) throws ValueFormatException
    {
        String valueToReturn = value.toString();
        if (valueToReturn == null)
        {
            throw new ValueFormatException("The value can not be converted to String");
        }
        return valueToReturn;
    }

    private String toString(Calendar value)
    {
        Date date = value.getTime();
        return iso8601dateFormat.format(date);
    }

    private Double toDouble(Calendar value)
    {
        return new Double(value.getTime().getTime());

    }

    private BigDecimal toDecimal(Calendar value)
    {
        return new BigDecimal(value.getTime().getTime());

    }

    private Long toLong(Calendar value)
    {
        return value.getTime().getTime();

    }

    private String toString(Double value)
    {
        return value.toString();
    }

    private Binary toBinary(Double value) throws RepositoryException
    {
        return toBinary(value.toString());
    }

    private BigDecimal toDecimal(Double value)
    {
        return new BigDecimal(value);
    }

    private Calendar toDate(Double value)
    {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(value.longValue());
        return cal;
    }

    private Long toLong(Double value)
    {
        return value.longValue();
    }

    private String toString(BigDecimal value)
    {
        return value.toString();
    }

    private Binary toBinary(BigDecimal value) throws RepositoryException
    {
        return toBinary(value.toString());
    }

    private Double toDouble(BigDecimal value)
    {
        return value.doubleValue();
    }

    private Calendar toDate(BigDecimal value)
    {
        return toDate(new Double(value.longValue()));
    }

    private Long toLong(BigDecimal value)
    {
        return value.longValue();
    }

    private String toString(Long value)
    {
        return value.toString();
    }

    private Binary toBinary(Long value) throws RepositoryException
    {
        return toBinary(value.toString());
    }

    private BigDecimal toDecimal(Long value)
    {
        return BigDecimal.valueOf(value);
    }

    private Calendar toDate(Long value)
    {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(value);
        return cal;
    }

    private Double toDouble(Long value)
    {
        return new Double(value);
    }

    private String toString(Boolean value)
    {
        return value.toString();
    }

    private Binary toBinary(Boolean value) throws RepositoryException
    {
        return toBinary(value.toString());
    }

    private String toString(QName value)
    {
        return value.toString();
    }

    private Binary toBinary(QName value) throws RepositoryException
    {
        return toBinary(value.toString());
    }

    private URL toPath(QName value) throws ValueFormatException
    {
        try
        {
            return new URL("./" + value.toString());
        }
        catch (Exception e)
        {
            throw new ValueFormatException(e);
        }
    }

    private URI toUri(QName value) throws ValueFormatException
    {
        try
        {
            return new URI("./" + value.toString());
        }
        catch (Exception e)
        {
            throw new ValueFormatException(e);
        }
    }

    private String toString(URL value)
    {
        return value.toString();
    }

    private Binary toBinary(URL value) throws RepositoryException
    {
        return toBinary(value.toString());
    }

    private QName toName(URL value) throws ValueFormatException
    {
        String path = value.toString();
        if (path.startsWith("./"))
        {
            path = path.substring(2);
            return toName(path);
        }
        throw new ValueFormatException("The value can not be converted to NAME");
    }

    private URI toUri(URL value) throws ValueFormatException
    {
        try
        {
            return value.toURI();
        }
        catch (Exception e)
        {
            throw new ValueFormatException(e);
        }
    }

    private String toString(URI value)
    {
        return value.toString();
    }

    private Binary toBinary(URI value) throws RepositoryException
    {
        return toBinary(value.toString());
    }

    private QName toName(URI value) throws ValueFormatException
    {
        try
        {
            return toName(value.toURL());
        }
        catch(Exception e)
        {
            throw new ValueFormatException(e);
        }
    }

    private URL toPath(URI value) throws ValueFormatException
    {
        String path=value.toString();
        if(path.startsWith("/"))
        {
            path="."+path;
        }
        try
        {
            return new URL(path);
        }
        catch(Exception e)
        {
            throw new ValueFormatException(e);
        }
    }
}
