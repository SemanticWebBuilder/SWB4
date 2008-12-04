/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.xmlrpc;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.xpath.XPath;

/**
 *
 * @author victor.lorenzana
 */
public class XmlRpcSerializer {

    private static SimpleDateFormat iso8601dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
    public static Object deserializeResponse(java.lang.Class clazz, Document requestDocument) throws XmlRpcException, ParseException
    {
        try
        {
            Element param = ( Element ) XPath.selectSingleNode(requestDocument, "/methodResponse/params/param");
            if ( param == null )
            {
                try
                {
                    Element eName = ( Element ) XPath.selectSingleNode(requestDocument, "/methodResponse/fault/value/struct/member[2]/name");
                    if ( eName.getText().equals("faultString") )
                    {
                        Element eValue = ( Element ) XPath.selectSingleNode(requestDocument, "/methodResponse/fault/value/struct/member[2]/value/string");
                        throw new XmlRpcException(eValue.getText());
                    }
                    throw new XmlRpcException("The faultString tag was not found");
                }
                catch ( JDOMException jde2 )
                {
                    throw new XmlRpcException("XMLRPC response was not found");
                }
            }
            else
            {
                return deserializeObject(clazz, param);
            }
        }
        catch ( JDOMException jde )
        {
            try
            {
                Element eName = ( Element ) XPath.selectSingleNode(requestDocument, "/methodResponse/fault/value/struct/member/name[1]");
                if ( eName.getText().equals("faultString") )
                {
                    Element stringValue = ( Element ) XPath.selectSingleNode(requestDocument, "/methodResponse/fault/value/struct/member/name[1]/value/string");
                    throw new XmlRpcException(stringValue.getText());
                }
                throw new XmlRpcException("XMLRPC response was not found");
            }
            catch ( JDOMException jde2 )
            {
                throw new XmlRpcException("XMLRPC response was not found");
            }
        }
    }
    
    public static Object[] deserializeRequest(Document document, ArrayList<Method> methods) throws JDOMException, ParseException, XmlRpcException
    {
        ArrayList<Method> newMethods = new ArrayList<Method>();
        ArrayList<Object> parameters = new ArrayList<Object>();
        for ( Method method : methods )
        {
            try
            {
                List values = XPath.selectNodes(document, "/methodCall/params/param/value");
                methods = selectMethodsWithSameNumberOfParameters(methods, values.size());                
                int iParameter=0;
                for ( Object oElement : values )
                {
                    Class expectedClass = method.getParameterTypes()[iParameter];
                    Element eValue = ( Element ) oElement;
                    for ( Object objElementType : eValue.getChildren() )
                    {
                        Element eType = ( Element ) objElementType;
                        parameters.add(deserializeObject(expectedClass, eType));
                    }  
                    iParameter++;
                }
                newMethods.add(method);
            }
            catch ( Exception e )
            {
                parameters = new ArrayList<Object>();
                break;
            }
        }
        return parameters.toArray();
    }
    
    public static Document serializeRequest(String pMethodName, Object[] pParams) throws XmlRpcException
    {
        Document doc = new Document();
        Element methodCall = new Element("methodCall");
        Element methodName = new Element("methodName");
        methodName.setText(pMethodName);
        methodCall.addContent(methodName);
        doc.setRootElement(methodCall);
        Element params = new Element("params");
        if ( pParams != null )
        {
            serializeObjects(params, pParams);
        }
        methodCall.addContent(params);
        return doc;
    }
     
    public static Document serializeResponse(Object value) throws XmlRpcException
    {
        Document doc = new Document();
        Element methodResponse = new Element("methodResponse");
        doc.setRootElement(methodResponse);
        Element params = new Element("params");
        methodResponse.addContent(params);
        Object[] pParams = {value};
        serializeObjects(params, pParams);
        return doc;
    }
    
    
    private static ArrayList<Method> selectMethodsWithSameNumberOfParameters(ArrayList<Method> methods, int parameters)
    {
        ArrayList<Method> newmethods = new ArrayList<Method>();
        for ( Method m : methods )
        {
            if ( m.getParameterTypes().length == parameters )
            {
                newmethods.add(m);
            }
        }
        return newmethods;
    }
    private static Object deserializeArray(Class clazz, Element array) throws JDOMException, ParseException, XmlRpcException
    {
        if ( clazz.isArray() )
        {

            List listValues = XPath.selectNodes(array, "./data/value");
            Class componentType = clazz.getComponentType();
            Object arrayToReturn = Array.newInstance(componentType, listValues.size());
            int i = 0;
            for ( Object objValue : listValues )
            {
                Element eValue = ( Element ) objValue;
                Iterator itValues = eValue.getDescendants();
                while (itValues.hasNext())
                {
                    Object child = itValues.next();
                    if ( child instanceof Element )
                    {
                        Element eType = ( Element ) child;
                        Object value = deserializeObject(componentType, eType);
                        Array.set(arrayToReturn, i, value);
                    }
                }
                i++;
            }
            return arrayToReturn;
        }
        else
        {
            throw new XmlRpcException("The value of return must be an array");
        }        
    }
    private static Object deserializeObject(Class clazz, Element eType) throws ParseException, JDOMException, XmlRpcException
    {
        Object res=null;
        String name = eType.getName();
        if ( name.equalsIgnoreCase("i4") || name.equalsIgnoreCase("int") )
        {
            res = convertInteger(clazz, Integer.parseInt(eType.getText()));
        }
        else if ( name.equalsIgnoreCase("boolean") )
        {
            res = convertBoolean(clazz, eType.getText().equals("1") ? true : false);
        }
        else if ( name.equalsIgnoreCase("dateTime.iso8601") )
        {
            String dateTime = eType.getText();
            res = convertDate(clazz, iso8601dateFormat.parse(dateTime));
        }
        else if ( name.equalsIgnoreCase("float") )
        {
            res = convertFloat(clazz, Float.parseFloat(eType.getText()));
        }
        else if ( name.equalsIgnoreCase("double") )
        {
            res = convertDouble(clazz, Double.parseDouble(eType.getText()));
        }
        else if ( name.equalsIgnoreCase("string") )
        {
            res = convertString(clazz, eType.getText());
        }
        else if ( name.equalsIgnoreCase("array") )
        {
            res = deserializeArray(clazz, eType);
        }
        else if ( name.equalsIgnoreCase("struct") )
        {
            res = deserializeStruct(clazz, eType);
        }
        else if ( name.equalsIgnoreCase("param") ||  name.equalsIgnoreCase("value"))
        {
            if(eType.getChildren().size()>0)
            {
                res=deserializeObject(clazz, (Element)eType.getChildren().get(0));
            }
            else
            {
                return null;
            }
        }
        else
        {
            return null;
        }
        return res;

    }
    private static Object deserializeStruct(Class clazz, Element struct) throws JDOMException, ParseException, XmlRpcException, XmlRpcException
    {
        List listMembers = XPath.selectNodes(struct, "./member");
        try
        {
            Object result = clazz.newInstance();
            for ( Object objMember : listMembers )
            {
                Element eMember = ( Element ) objMember;
                Element eName = ( Element ) XPath.selectSingleNode(eMember, "./name");
                try
                {
                    Field field = clazz.getField(eName.getText());
                    Element eValue = ( Element ) XPath.selectSingleNode(eMember, "./value");
                    List childs = eValue.getChildren();
                    for ( Object child : childs )
                    {
                        if ( child instanceof Element )
                        {
                            Element eType = ( Element ) child;
                            Object value = deserializeObject(field.getType(), eType);
                            field.set(result, value);
                        }
                    }

                }
                catch ( NoSuchFieldException nsfe )
                {
                    throw new XmlRpcException("The clazz " + clazz.getName() + " does not contain the filed " + eName.getText(), nsfe);
                }
            }
            return result;
        }
        catch ( InstantiationException ie )
        {
            throw new XmlRpcException("The clazz " + clazz.getName() + " can not be instancieded", ie);
        }
        catch ( IllegalAccessException ie )
        {
            throw new XmlRpcException("The clazz " + clazz.getName() + " can not be instancieded", ie);
        }
    }
   
    
    private static void serializeObject(Element param, Object obj) throws XmlRpcException
    {
        Element value = new Element("value");
        param.addContent(value);
        String type = "string";
        String svalue = "";
        if(obj==null)
        {
            Element nil = new Element("nil");
            value.addContent(nil);
            return;
        }
        if ( obj instanceof Integer )
        {
            type = "i4";
            svalue = obj.toString();
        }
        else if ( obj instanceof String )
        {
            type = "string";
            svalue = obj.toString();
        }
        else if ( obj instanceof Boolean )
        {
            type = "boolean";
            svalue = (( Boolean ) obj).booleanValue() ? "1" : "0";
        }
        else if ( obj instanceof Date )
        {
            type = "dateTime.iso8601";
            svalue = iso8601dateFormat.format(( Date ) obj);
        }
        else if ( obj instanceof Float )
        {
            type = "float";
            svalue = obj.toString();
        }
        else if ( obj instanceof Double )
        {
            type = "double";
            svalue = obj.toString();
        }
        else if ( obj.getClass().isArray() )
        {
            type = "array";
            svalue = null;
            Element elementType = new Element(type);
            value.setContent(elementType);
            serializeArray(obj, elementType);
        }
        else
        {            
            type = "struct";
            svalue = null;
            Element elementType = new Element(type);
            value.setContent(elementType);
            serializeStruct(obj, elementType);
        }
        if ( svalue != null )
        {
            Element elementType = new Element(type);
            value.setContent(elementType);
            elementType.setText(svalue);

        }
    }
    private static void serializeStruct(Object obj, Element structElement) throws XmlRpcException
    {
        Class clazz = obj.getClass();
        for ( Field field : clazz.getDeclaredFields() )
        {
            try
            {
                Element member = new Element("member");
                Element name = new Element("name");
                name.setText(field.getName());
                member.addContent(name);
                Element value = new Element("value");
                serializeObject(member, field.get(obj));
                member.addContent(value);
                structElement.addContent(member);
            }
            catch ( IllegalAccessException iae )
            {
                iae.printStackTrace(System.out);
            }

        }
    }
    private static void serializeObjects(Element params, Object[] pParams) throws XmlRpcException
    {
        for ( Object obj : pParams )
        {
            if ( obj != null )
            {
                Element param = new Element("param");
                serializeObject(param, obj);
                params.addContent(param);
            }

        }
    }
    private static Boolean convertBoolean(Class clazz, boolean data) throws XmlRpcException
    {
        if(clazz==Boolean.class || clazz==boolean.class)
        {
            return new Boolean(data);
        }
        else
        {
            throw new XmlRpcException("The data are incopatibles, can not be converted boolean to "+clazz.getName());
        }
    }

    private static Float convertFloat(Class clazz, float data) throws XmlRpcException
    {
        if(clazz==Float.class || clazz==float.class)
        {
            return new Float(data);
        }
        else
        {
            throw new XmlRpcException("The data are incopatibles, can not be converted float to "+clazz.getName());
        }
    }

    private static Double convertDouble(Class clazz, double data) throws XmlRpcException
    {
        if(clazz==Double.class || clazz==double.class)
        {
            return new Double(data);
        }
        else
        {
            throw new XmlRpcException("The data are incopatibles, can not be converted double to "+clazz.getName());
        }
    }
    private static String convertString(Class clazz, String data) throws XmlRpcException
    {
        if(clazz==String.class)
        {
            return data;
        }
        throw new XmlRpcException("The data are incopatibles, can not be converted String to "+clazz.getName());
    }

    private static Integer convertInteger(Class clazz, int data) throws XmlRpcException
    {
        if(clazz==Integer.class || clazz==int.class)
        {
            return new Integer(data);
        }
        else
        {
            throw new XmlRpcException("The data are incopatibles, can not be converted int to "+clazz.getName());
        }
    }
    private static void serializeArray(Object obj, Element arrayElement) throws XmlRpcException
    {
        Element data = new Element("data");
        arrayElement.addContent(data);
        int len = Array.getLength(obj);
        for ( int i = 0; i < len; i++ )
        {
            Object value = Array.get(obj, i);
            serializeObject(data, value);
        }
    }

    private static Date convertDate(Class clazz, Date data) throws XmlRpcException
    {
        if ( clazz==data.getClass() )
        {
            return data;
        }
        throw new XmlRpcException("The data are incopatibles, can not be converted Date to "+clazz.getName());
    }
}
