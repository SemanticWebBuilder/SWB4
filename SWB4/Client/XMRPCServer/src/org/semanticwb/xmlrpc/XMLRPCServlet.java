/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.xmlrpc;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.servlet.ServletInputStream;
import javax.servlet.ServletOutputStream;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.jdom.output.XMLOutputter;
import org.jdom.xpath.XPath;
import static org.semanticwb.xmlrpc.Base64.decode;

/**
 *
 * @author victor.lorenzana
 */
public abstract class XMLRPCServlet extends HttpServlet
{
    private static final
    String RETURN = "\r\n";

    Hashtable<String, Object> cacheObjects = new Hashtable<String, Object>();
    private static final String PREFIX_PROPERTY_PATH = "org.semanticwb.xmlrpc.";
    private static final String XMLRPC_DOCUMENT = "xmlrpc";
    private static String realm = "Secure Area";
    private static String prefixBasic = "Basic ";
    private static SimpleDateFormat iso8601dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
    {
        try
        {
            String pUserName = null;
            String pPassword = null;
            String authorization = request.getHeader("Authorization");
            if ( authorization == null || authorization.equals("") )
            {
                response.setHeader("WWW-Authenticate", prefixBasic + "realm=\"" + realm + "\"");
                response.setStatus(response.SC_UNAUTHORIZED);
                return;
            }
            else
            {
                if ( authorization.startsWith(prefixBasic) )
                {
                    String userpassEncoded = authorization.substring(6);
                    String userpassDecoded = new String(decode(userpassEncoded));
                    pUserName = getUserName(userpassDecoded);
                    pPassword = getPassword(userpassDecoded);
                    if ( !this.isAuthenticate(pUserName, pPassword) )
                    {
                        response.sendError(response.SC_FORBIDDEN);
                    }
                }
                else
                {
                    response.sendError(response.SC_FORBIDDEN);
                }
            }
            Document xmlrpcDocument;
            Set<Part> parts = new HashSet<Part>();
            if ( isMultipart(request) )
            {
                WBFileUpload upload = new WBFileUpload();
                upload.getFiles(request);
                for ( String name : upload.getFileNames() )
                {
                    if ( !name.equals(XMLRPC_DOCUMENT) )
                    {
                        byte[] content = upload.getFileData(name);
                        Part part = new Part(content, name, upload.getFileName(name));
                        parts.add(part);
                    }
                }
                xmlrpcDocument = getDocument(upload.getFileData(XMLRPC_DOCUMENT));
            }
            else
            {
                xmlrpcDocument = getDocument(request);
            }

            try
            {
                if ( pUserName != null && pPassword != null )
                {

                    String methodName = getMethodName(xmlrpcDocument);
                    ArrayList<Method> methods = getMethods(methodName);
                    Object[] parameters = deserialize(xmlrpcDocument, methods);
                    Method method = getMethod(methodName, parameters, methods);
                    String objectName = method.getDeclaringClass().getName();
                    Object objResponse = execute(objectName, method, parameters, parts, pUserName, pPassword);
                    Document docResponse = this.serialize(objResponse);
                    sendResponse(response, docResponse);
                }
            }
            catch ( Exception cne )
            {
                try
                {
                    Document docResponse = this.getException(cne);
                    sendResponse(response, docResponse);
                }
                catch ( IOException ioeSendResponse )
                {
                    Document docResponse = this.getException(ioeSendResponse);
                    sendResponse(response, docResponse);
                }
            }
        }
        catch ( Exception jde )
        {
            try
            {
                Document docResponse = this.getException(jde);
                sendResponse(response, docResponse);
            }
            catch ( Exception ex )
            {
                // No se puede hacer nada, no puede seralizar la respuesta, debe guardar el error en el log
                // TODO:
                ex.printStackTrace(System.out);
            }
        }

    }

    protected void beforeExecute(Object objToExecute, Set<Part> parts, String user, String password) throws Exception
    {
        if ( objToExecute instanceof XmlRpcObject )
        {
            XmlRpcObject xmlRpcObject = ( XmlRpcObject ) objToExecute;
            xmlRpcObject.setParts(parts);
        }
    }

    protected void afterExecute(Object objToExecute)
    {
        if ( objToExecute instanceof XmlRpcObject )
        {
            XmlRpcObject xmlRpcObject = ( XmlRpcObject ) objToExecute;
            xmlRpcObject.clearParts();
        }
    }

    private String getUserName(String userpassDecoded) throws IOException
    {
        String userName = "";
        String[] values = userpassDecoded.split(":");
        userName = values[0];
        return userName;
    }

    public Method getMethod(String methodName, Object[] parameters, ArrayList<Method> methods) throws NoSuchMethodException
    {
        switch ( methods.size() )
        {
            case 1:
                return methods.get(0);
            default:
        }
        throw new NoSuchMethodException("The method " + methodName + "was not found");
    }

    private String getPassword(String userpassDecoded) throws IOException
    {
        String password = "";
        String[] values = userpassDecoded.split(":");
        password = values[1];
        return password;
    }

    /**
     * This method is for athentication of the user, using basic authentication
     * @param pUserName User to authenticate
     * @param pPassword Password to autenticate
     * @return true if the user is athenticate, false otherwise
     */
    public abstract boolean isAuthenticate(String pUserName, String pPassword);

    private void sendResponse(ServletResponse response, Document docResponse) throws IOException
    {
        response.setContentType("text/xml");
        ServletOutputStream out = response.getOutputStream();
        XMLOutputter xMLOutputter = new XMLOutputter();
        xMLOutputter.output(docResponse, out);
        out.flush();
        out.close();
    }

    private Document serialize(Object value) throws XmlRpcException
    {
        Document doc = new Document();
        Element methodResponse = new Element("methodResponse");
        doc.setRootElement(methodResponse);
        Element params = new Element("params");
        methodResponse.addContent(params);
        Object[] pParams = {value};
        addParameters(params, pParams);
        return doc;
    }

    public void addParameter(Element param, Object obj) throws XmlRpcException
    {
        Element value = new Element("value");
        param.addContent(value);
        String type = "string";
        String svalue = "";
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
            addArray(obj, elementType);
        }
        else
        {
            //throw new XmlRpcException("This kind of convertion is not possible");
            type = "struct";
            svalue = null;
            Element elementType = new Element(type);
            value.setContent(elementType);
            addStruct(obj, elementType);
        }
        if ( svalue != null )
        {
            Element elementType = new Element(type);
            value.setContent(elementType);
            elementType.setText(svalue);

        }
    }

    private void addStruct(Object obj, Element structElement) throws XmlRpcException
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
                addParameter(member, field.get(obj));
                member.addContent(value);
                structElement.addContent(member);
            }
            catch ( IllegalAccessException iae )
            {
                iae.printStackTrace(System.out);
            }

        }
    }

    private void addArray(Object obj, Element arrayElement) throws XmlRpcException
    {
        Element data = new Element("data");
        arrayElement.addContent(data);
        int len = Array.getLength(obj);
        for ( int i = 0; i < len; i++ )
        {
            Object value = Array.get(obj, i);
            addParameter(data, value);
        }
    }

    public void addParameters(Element params, Object[] pParams) throws XmlRpcException
    {
        for ( Object obj : pParams )
        {
            if ( obj != null )
            {
                Element param = new Element("param");
                addParameter(param, obj);
                params.addContent(param);
            }

        }
    }

    private String getClassFullPath(String objectName) throws ClassNotFoundException
    {
        String classFullPath = System.getProperty(PREFIX_PROPERTY_PATH + objectName, null);
        if ( classFullPath == null )
        {
            throw new ClassNotFoundException("The class for the Object Name: " + objectName + " was not found");
        }
        return classFullPath;
    }

    private Object execute(String objectName, Method method, Object[] parameters, Set<Part> parts, String user, String password) throws ClassNotFoundException, XmlRpcException, InstantiationException, IllegalAccessException, NoSuchMethodException
    {
        Class clazz = method.getDeclaringClass();

        Object objToExecute = cacheObjects.get(objectName);
        if ( objToExecute == null )
        {
            cacheObjects.put(objectName, clazz.newInstance());
            objToExecute = cacheObjects.get(objectName);
        }
        synchronized ( objToExecute )
        {
            try
            {
                beforeExecute(objToExecute, parts, user, password);
            }
            catch ( Exception e )
            {
                throw new XmlRpcException("The opject can not be inizialited into the method setupObject, cause: " + e.getLocalizedMessage(), e);
            }
            try
            {
                Object objectToReturn = method.invoke(objToExecute, parameters);
                afterExecute(objToExecute);
                return objectToReturn;
            }
            catch ( InvocationTargetException inte )
            {
                throw new XmlRpcException(inte.getTargetException().getLocalizedMessage(), inte.getTargetException());
            }
        }


    }

    private <T> T convertString(Class<T> clazz, String data) throws XmlRpcException
    {
        if(clazz==String.class)
        {
            return ( T ) data;
        }
        throw new XmlRpcException("The data are incopatibles, can not be converted String to "+clazz.getName());
    }

    private <T> T convertInteger(Class<T> clazz, int data) throws XmlRpcException
    {
        if(clazz==Integer.class || clazz==int.class)
        {
            return ( T ) new Integer(data);
        }
        else
        {
            throw new XmlRpcException("The data are incopatibles, can not be converted int to "+clazz.getName());
        }
    }

    private <T> T convertBoolean(Class<T> clazz, boolean data) throws XmlRpcException
    {
        if(clazz==Boolean.class || clazz==boolean.class)
        {
            return ( T ) new Boolean(data);
        }
        else
        {
            throw new XmlRpcException("The data are incopatibles, can not be converted boolean to "+clazz.getName());
        }
    }

    private <T> T convertFloat(Class<T> clazz, float data) throws XmlRpcException
    {
        if(clazz==Float.class || clazz==float.class)
        {
            return ( T ) new Float(data);
        }
        else
        {
            throw new XmlRpcException("The data are incopatibles, can not be converted float to "+clazz.getName());
        }
    }

    private <T> T convertDouble(Class<T> clazz, double data) throws XmlRpcException
    {
        if(clazz==Double.class || clazz==double.class)
        {
            return ( T ) new Double(data);
        }
        else
        {
            throw new XmlRpcException("The data are incopatibles, can not be converted double to "+clazz.getName());
        }
    }

    private <T> T convertDate(Class<T> clazz, Date data) throws XmlRpcException
    {
        if ( data.getClass().equals(clazz) )
        {
            return ( T ) data;
        }
        throw new XmlRpcException("The data are incopatibles, can not be converted Date to "+clazz.getName());
    }

    private <T> T getParameter(Class<T> clazz, Element eType) throws ParseException, JDOMException, XmlRpcException
    {
        T res = null;
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
        /*else if ( name.equalsIgnoreCase("struct") )
        {
            res = deserializeStruct(clazz, eType);
        }*/
        else
        {
            res = null;
        }
        return res;

    }

    /*private <T> T deserializeStruct(Class<T> clazz, Element struct) throws JDOMException, ParseException
    {
        List listValues = XPath.selectNodes(struct, "./member");
        return null;
    }*/

    private <T> T deserializeArray(Class<T> clazz, Element array) throws JDOMException, ParseException, XmlRpcException
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
                        Object value = getParameter(componentType, eType);
                        Array.set(arrayToReturn, i, value);
                    }
                }
                i++;
            }
            return clazz.cast(arrayToReturn);
        }
        else
        {
            throw new XmlRpcException("The value of return must be an array");
        }        
    }

    private ArrayList<Method> selectMethodsWithSameNumberOfParameters(ArrayList<Method> methods, int parameters)
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

    private Object[] deserialize(Document document, ArrayList<Method> methods) throws JDOMException, ParseException, XmlRpcException
    {
        ArrayList<Method> newMethods = new ArrayList<Method>();
        ArrayList<Object> parameters = new ArrayList<Object>();
        for ( Method method : methods )
        {
            try
            {
                List values = XPath.selectNodes(document, "/methodCall/params/param/value");
                methods = selectMethodsWithSameNumberOfParameters(methods, values.size());                
                for ( Object oElement : values )
                {
                    Class expectedClass = method.getParameterTypes()[0];
                    Element eValue = ( Element ) oElement;
                    for ( Object objElementType : eValue.getChildren() )
                    {
                        Element eType = ( Element ) objElementType;
                        parameters.add(getParameter(expectedClass, eType));
                    }                    
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

    private String getMethodName(Document document) throws XmlRpcException, JDOMException, ClassNotFoundException
    {
        Element methodNameElement = ( Element ) XPath.selectSingleNode(document.getRootElement(), "/methodCall/methodName");
        if ( methodNameElement == null )
        {
            throw new XmlRpcException("The methodName tag was not found");
        }
        return methodNameElement.getText();
    }

    private ArrayList<Method> getMethods(String pCallMethod) throws XmlRpcException, JDOMException, ClassNotFoundException
    {
        String[] values = pCallMethod.split("\\.");
        if ( values.length != 2 )
        {
            throw new XmlRpcException("The callMethos is incorrect");
        }
        String objectName = values[0];
        String methodName = values[1];
        String classFullPath = getClassFullPath(objectName);
        Class clazz = Class.forName(classFullPath);
        ArrayList<Method> methods = new ArrayList<Method>();
        for ( Method m : clazz.getMethods() )
        {
            if ( m.getName().equals(methodName) )
            {
                methods.add(m);
            }
        }
        return methods;
    }

    private Document getException(Exception e) throws JDOMException, IOException
    {
        StringBuilder messageError = new StringBuilder(e.getLocalizedMessage() + RETURN);
        for ( StackTraceElement element : e.getStackTrace() )
        {
            messageError.append(element.toString() + RETURN);
        }
        if ( e.getCause() != null )
        {
            messageError.append(" cause: \r\n");
            messageError.append(e.getCause().getLocalizedMessage() + RETURN);
            for ( StackTraceElement element : e.getCause().getStackTrace() )
            {
                messageError.append(element.toString() + RETURN);
            }
        }
        String xmlString = "<?xml version=\"1.0\" encoding=\"utf-8\"?><methodResponse><fault><value><struct><member><name>faultCode</name>" +
                "<value><int>" + e.hashCode() + "</int></value></member><member><name>faultString</name>" +
                "<value><string>" + messageError + "</string></value></member></struct>" +
                "</value></fault></methodResponse>";

        Reader stringReader = new StringReader(xmlString);
        SAXBuilder builder = new SAXBuilder();
        return builder.build(stringReader);
    }

    private Document getDocument(byte[] document) throws JDOMException, IOException
    {
        ByteArrayInputStream in = new ByteArrayInputStream(document);
        SAXBuilder builder = new SAXBuilder();
        return builder.build(in);
    }

    private Document getDocument(HttpServletRequest request) throws IOException, JDOMException
    {
        ServletInputStream in = request.getInputStream();
        SAXBuilder builder = new SAXBuilder();
        in.close();
        return builder.build(in);
    }

    private boolean isMultipart(HttpServletRequest request)
    {
        boolean isMultipart = false;
        if ( request.getContentType().indexOf("multipart/form-data") != -1 )
        {
            isMultipart = true;
        }
        return isMultipart;
    }

    public static void addMappingType(String objectName, Class clazz)
    {
        System.setProperty(PREFIX_PROPERTY_PATH + objectName, clazz.getName());
    }

    public static void addMappingType(Map<String, Class> mapType)
    {
        for ( String objectName : mapType.keySet() )
        {
            Class clazz = mapType.get(objectName);
            addMappingType(objectName, clazz);
        }
    }
}
