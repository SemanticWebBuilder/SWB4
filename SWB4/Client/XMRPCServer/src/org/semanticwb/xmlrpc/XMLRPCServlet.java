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
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
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
                    Object[] parameters = deserialize(xmlrpcDocument);
                    Object objResponse = execute(getMethodName(xmlrpcDocument), parameters, parts, pUserName, pPassword);
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

    private String getUserName(String userpassDecoded) throws IOException
    {
        String userName = "";
        String[] values = userpassDecoded.split(":");
        userName = values[0];
        return userName;
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
            throw new XmlRpcException("This kind of convertion is not possible");
        }
        if ( svalue != null )
        {
            Element elementType = new Element(type);
            value.setContent(elementType);
            elementType.setText(svalue);

        }
    }

    private void addArray(Object obj, Element elementType) throws XmlRpcException
    {
        Element data = new Element("data");
        elementType.addContent(data);
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

    private Class[] getParameterTypes(Object[] parameters)
    {
        Class[] classToReturn = new Class[parameters.length];
        for ( int i = 0; i < parameters.length; i++ )
        {
            if ( parameters[i].getClass() == Integer.class )
            {
                classToReturn[i] = int.class;
            }
            else if ( parameters[i].getClass() == String.class )
            {
                classToReturn[i] = String.class;
            }
            else if ( parameters[i].getClass() == Double.class )
            {
                classToReturn[i] = double.class;
            }
            else if ( parameters[i].getClass() == Float.class )
            {
                classToReturn[i] = float.class;
            }
            else if ( parameters[i].getClass() == Boolean.class )
            {
                classToReturn[i] = boolean.class;
            }
            else
            {
                classToReturn[i] = parameters[i].getClass();
            }
        }
        return classToReturn;
    }

    private String getClassFullPath(String objectName) throws ClassNotFoundException
    {
        String classFullPath = System.getProperty(PREFIX_PROPERTY_PATH + objectName, null);
        if ( classFullPath == null )
        {
            throw new ClassNotFoundException("The class for he Object Name: " + objectName + " was not found");
        }
        return classFullPath;
    }

    private Object execute(String pCallMethod, Object[] parameters, Set<Part> parts, String user, String password) throws ClassNotFoundException, XmlRpcException, InstantiationException, IllegalAccessException, NoSuchMethodException
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
        Class[] parameterTypes = getParameterTypes(parameters);
        Method methodToFind = clazz.getMethod(methodName, parameterTypes);
        Object objToExecute = clazz.newInstance();
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
            Object objectToReturn = methodToFind.invoke(objToExecute, parameters);
            afterExecute(objToExecute);
            return objectToReturn;
        }
        catch ( InvocationTargetException inte )
        {
            throw new XmlRpcException(inte.getTargetException().getLocalizedMessage(), inte.getTargetException());
        }


    }

    protected void afterExecute(Object objToExecute)
    {

    }

    private Object getParameter(Element eType) throws ParseException, JDOMException
    {
        Object res = null;
        String name = eType.getName();
        if ( name.equalsIgnoreCase("i4") || name.equalsIgnoreCase("int") )
        {
            res = Integer.parseInt(eType.getText());
        }
        else if ( name.equalsIgnoreCase("boolean") )
        {
            res = eType.getText().equals("1") ? true : false;
        }
        else if ( name.equalsIgnoreCase("dateTime.iso8601") )
        {
            String dateTime = eType.getText();
            res = iso8601dateFormat.parse(dateTime);
        }
        else if ( name.equalsIgnoreCase("float") )
        {
            res = Float.parseFloat(eType.getText());
        }
        else if ( name.equalsIgnoreCase("double") )
        {
            res = Double.parseDouble(eType.getText());
        }
        else if ( name.equalsIgnoreCase("string") )
        {
            res = eType.getText();
        }
        else if ( name.equalsIgnoreCase("array") )
        {
            res = deserializeArray(eType);
        }
        else
        {
            res = null;
        }
        return res;

    }

    private Object deserializeArray(Element array) throws JDOMException, ParseException
    {
        List listValues = XPath.selectNodes(array, "./data/value");
        Object[] arrayToReturn = new Object[listValues.size()];
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
                    arrayToReturn[i] = this.getParameter(eType);
                }
            }
            i++;
        }

        return arrayToReturn;
    }

    private Object[] deserialize(Document document) throws JDOMException, ParseException
    {
        ArrayList<Object> parameters = new ArrayList<Object>();
        List values = XPath.selectNodes(document, "/methodCall/params/param/value");
        for ( Object oElement : values )
        {
            Element eValue = ( Element ) oElement;
            for ( Object objElementType : eValue.getChildren() )
            {
                Element eType = ( Element ) objElementType;
                parameters.add(getParameter(eType));
            }
        }
        return parameters.toArray();
    }

    private String getMethodName(Document document) throws XmlRpcException, JDOMException
    {
        Element methodName = ( Element ) XPath.selectSingleNode(document.getRootElement(), "/methodCall/methodName");
        if ( methodName == null )
        {
            throw new XmlRpcException("The methodName tag was not found");
        }
        return methodName.getText();
    }

    private Document getException(Exception e) throws JDOMException, IOException
    {
        StringBuilder messageError = new StringBuilder(e.getLocalizedMessage() + "\r\n");
        for ( StackTraceElement element : e.getStackTrace() )
        {
            messageError.append(element.toString() + "\r\n");
        }
        if ( e.getCause() != null )
        {
            messageError.append(" cause: \r\n");
            messageError.append(e.getCause().getLocalizedMessage() + "\r\n");
            for ( StackTraceElement element : e.getCause().getStackTrace() )
            {
                messageError.append(element.toString() + "\r\n");
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
