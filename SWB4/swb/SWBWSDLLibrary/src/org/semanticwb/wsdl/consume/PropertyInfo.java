/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.wsdl.consume;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 *
 * @author victor.lorenzana
 */
public class PropertyInfo
{

    private final Field field;
    private final String name;
    private final String type;
    private boolean ismultiple = false;
    private final ClassInfo info;
    private final String tagname;
    private final boolean isBasic;
    public PropertyInfo(Field field, ClassInfo info, String tagname)
    {
        this.tagname = tagname;
        this.field = field;
        name = field.getName();
        ismultiple = field.getType().isArray();
        String _type = field.getType().getCanonicalName();
        boolean _isBasic=field.getType().isPrimitive();
        if (_type.equals("java.util.ArrayList"))
        {
            ismultiple = true;

            ParameterizedType stringListType = (ParameterizedType) field.getGenericType();
            if (stringListType.getActualTypeArguments() != null && stringListType.getActualTypeArguments().length > 0 && stringListType.getActualTypeArguments()[0] instanceof Class)
            {
                _type = ((Class) stringListType.getActualTypeArguments()[0]).getCanonicalName();
                _isBasic=((Class) stringListType.getActualTypeArguments()[0]).isPrimitive();
            }

        }
        this.isBasic=_isBasic;
        if (_type.equals("float"))
        {
            _type = "java.lang." + _type;
        }
        else if (_type.equals("int"))
        {
            _type = "java.lang." + _type;
        }
        else if (_type.equals("short"))
        {
            _type = "java.lang." + _type;
        }
        else if (_type.equals("long"))
        {
            _type = "java.lang." + _type;
        }
        else if (_type.equals("byte"))
        {
            _type = "java.lang." + _type;
        }
        else if (_type.equals("double"))
        {
            _type = "java.lang." + _type;
        }
        else if (_type.equals("long"))
        {
            _type = "java.lang." + _type;
        }
        this.type = _type;
        this.info = info;
    }
    public boolean isBasic()
    {
        return isBasic;
    }
    public String getName()
    {
        return name;
    }

    public Field getField()
    {
        return field;
    }

    public Object getValue(Object instance)
    {
        try
        {
            return field.get(instance);
        }
        catch (IllegalAccessException iae)
        {
            return null;
        }
    }

    public void fill(Object instance, Object value) throws ServiceException
    {
        if (field.getDeclaringClass().getCanonicalName().equals(instance.getClass().getCanonicalName()))
        {
            try
            {
                field.set(instance, value);
            }
            catch (IllegalAccessException iae)
            {
                throw new ServiceException(iae);
            }
        }
    }

    public String getType()
    {
        return type;
    }

    public Class getDefinitionClass()
    {
        return field.getType();
    }

    @Override
    public boolean equals(Object obj)
    {
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        final PropertyInfo other = (PropertyInfo) obj;
        if ((this.name == null) ? (other.name != null) : !this.name.equals(other.name))
            return false;
        return true;
    }

    @Override
    public int hashCode()
    {
        int hash = 5;
        hash = 37 * hash + (this.name != null ? this.name.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString()
    {
        return "PropertyInfo{" + "name=" + name + '}';
    }

    public ArrayList<Object> getValues(Element element, Object instance) throws ServiceException
    {
        ArrayList<Object> values = new ArrayList<Object>();
        NodeList list = element.getElementsByTagNameNS(info.getNamespace(), tagname);
        for (int i = 0; i < list.getLength(); i++)
        {
            Element eprop = (Element) list.item(i);

            if (type.startsWith("java.lang."))
            {
                String _type = type.substring(10);
                String value = eprop.getTextContent();
                if (value == null)
                {
                    values.add(null);
                    //return null;
                }
                if (_type.equals("String"))
                {
                    values.add(value);
                    //return value;
                }
                if (_type.equals("int"))
                {
                    values.add(Integer.parseInt(value));
                    //return Integer.parseInt(value);
                }
                if (_type.equals("long"))
                {
                    values.add(Long.parseLong(value));
                    //return Long.parseLong(value);
                }
                if (_type.equals("double"))
                {
                    values.add(Double.parseDouble(value));
                    //return Double.parseDouble(value);
                }
                if (_type.equals("float"))
                {
                    values.add(Float.parseFloat(value));
                    //return Float.parseFloat(value);
                }

            }
            else
            {
                Object propInstance = info.newInstance();

                ClassInfo propInfo = info.getParameterDefinition().getInfo(type);
                for (PropertyInfo prop : propInfo.getProperties())
                {
                    Object valueprop = prop.getValue(element, propInstance);
                    prop.fill(propInstance, valueprop);
                }
            }
        }
        return values;
    }

    public Object getValue(Element element, Object instance) throws ServiceException
    {
        NodeList list = element.getElementsByTagNameNS(info.getNamespace(), tagname);
        for (int i = 0; i < list.getLength(); i++)
        {
            Element eprop = (Element) list.item(i);

            if (type.startsWith("java.lang."))
            {
                String _type = type.substring(10);
                String value = eprop.getTextContent();
                if (value == null)
                {
                    return null;
                }
                if (_type.equals("String"))
                {
                    return value;
                }
                if (_type.equals("int"))
                {
                    return Integer.parseInt(value);
                }
                if (_type.equals("long"))
                {
                    return Long.parseLong(value);
                }
                if (_type.equals("double"))
                {
                    return Double.parseDouble(value);
                }
                if (_type.equals("float"))
                {
                    return Float.parseFloat(value);
                }

            }
            else
            {
                Object propInstance = info.newInstance();

                ClassInfo propInfo = info.getParameterDefinition().getInfo(type);
                Object _value = propInfo.newInstance();
                try
                {
                    field.set(propInstance, _value);
                }
                catch (Exception e)
                {
                    throw new ServiceException(e);
                }

                for (PropertyInfo prop : propInfo.getProperties())
                {
                    if (prop.ismultiple)
                    {
                        //String propClass=prop.type;
                        ArrayList<Object> valueprop = prop.getValues(element, propInstance);

                        for (Object value : valueprop)
                        {
                            try
                            {
                                Method m = prop.getField().getType().getMethod("add", Object.class);
                                Object objfield = prop.getField().get(_value);
                                m.invoke(objfield, value);
                            }
                            catch (Exception e)
                            {
                                throw new ServiceException(e);
                            }
                        }
                    }
                    else
                    {
                        Object valueprop = prop.getValue(element, propInstance);
                        prop.fill(propInstance, valueprop);
                    }
                }
            }
        }
        return null;
    }
}
