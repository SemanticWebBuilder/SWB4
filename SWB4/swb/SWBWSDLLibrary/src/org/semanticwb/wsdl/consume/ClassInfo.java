/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.wsdl.consume;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Map;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Text;

/**
 *
 * @author victor.lorenzana
 */
public class ClassInfo
{

    private final Class clazz;
    private final String code;
    private final ArrayList<PropertyInfo> properties = new ArrayList<PropertyInfo>();
    private final String name;
    private final String namespace;
    private final ParameterDefinition definition;
    private final Map<String, String> tagnames;

    public ClassInfo(Class clazz, String code, String name, String namespace, ParameterDefinition definition, final Map<String, String> tagnames)
    {
        this.tagnames = tagnames;
        this.definition = definition;
        this.name = name;
        this.namespace = namespace;
        this.clazz = clazz;
        this.code = code;
        String _package = clazz.getPackage() == null ? "" : clazz.getPackage().toString();
        if (!_package.equals("java.lang"))
        {
            for (Field field : clazz.getFields())
            {
                String tagname = tagnames.get(field.getName());
                PropertyInfo prop = new PropertyInfo(field, this, tagname);
                this.properties.add(prop);
            }
        }
    }

    public Object newInstance() throws ServiceException
    {
        try
        {
            return clazz.newInstance();
        }
        catch (Exception e)
        {
            throw new ServiceException(e);
        }
    }

    public ParameterDefinition getParameterDefinition()
    {
        return this.definition;
    }

    public String getNamespace()
    {
        return namespace;
    }

    public Class getDefinition()
    {
        return this.clazz;
    }

    public String getCode()
    {
        return code;
    }

    public PropertyInfo[] getProperties()
    {
        return properties.toArray(new PropertyInfo[properties.size()]);
    }

    public Element createElement(Object value, Document doc)
    {
        Element element = doc.createElementNS(namespace, name);
        if (properties.isEmpty()) // simple type
        {
            Text text = doc.createTextNode(value.toString());
            element.appendChild(text);
        }
        else // complex type
        {
            for (PropertyInfo prop : properties)
            {
                Object valueprop = prop.getValue(value);
                if (valueprop != null)
                {
                    Element elementCont = doc.createElementNS(namespace, prop.getName());
                    element.appendChild(elementCont);
                    Class _clazz = valueprop.getClass();
                    String _package = _clazz.getPackage() == null ? "" : _clazz.getPackage().getName();
                    if (_package.equals("java.lang"))
                    {
                        Text textprop = doc.createTextNode(valueprop.toString());
                        elementCont.appendChild(textprop);
                    }
                    else
                    {
                        ClassInfo info = definition.getInfo(_clazz);
                        Element eProp = info.createElement(valueprop, doc);
                        elementCont.appendChild(eProp);
                    }

                }
            }
        }
        return element;
    }
}
