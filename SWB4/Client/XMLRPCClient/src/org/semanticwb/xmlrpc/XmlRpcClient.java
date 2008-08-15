/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.xmlrpc;

import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.util.List;
import java.io.*;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.Proxy.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.jdom.output.XMLOutputter;
import org.jdom.xpath.XPath;
import static org.semanticwb.xmlrpc.Base64.encode;
import static java.net.HttpURLConnection.*;

/**
 *
 * @author victor.lorenzana
 */
class XmlRpcClient
{

    private Map<String, List<String>> responseProperties = new HashMap<String, List<String>>();
    private static String boundary = "gc0p4Jq0M2Yt08jU534c0p";
    private static SimpleDateFormat iso8601dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
    private XmlRpcClientConfig config;

    public XmlRpcClient(XmlRpcClientConfig config)
    {
        this.config = config;
    }

    public void setConfig(XmlRpcClientConfig config)
    {
        this.config = config;
    }

    public XmlRpcClientConfig getClientConfig()
    {
        return config;
    }

    public Map<String, List<String>> getResponseProperties()
    {
        return responseProperties;
    }

    public <T> T execute(Class<T> clazz, String methodName, Object[] parameters, Set<Attachment> attachments) throws XmlRpcException, HttpException
    {
        for ( Attachment attachment : attachments )
        {
            if ( attachment.getFile().isDirectory() )
            {
                throw new XmlRpcException("The attachment '" + attachment.getName() + "' is a directory");
            }
            if ( !attachment.getFile().exists() )
            {
                throw new XmlRpcException("The attachment '" + attachment.getName() + "' does not exist");
            }
        }
        Document requestDoc = getXmlRpcDocument(methodName, parameters);
        //showDocDebug(requestDoc);
        Document responseDoc = request(requestDoc, attachments);
        try
        {
            return deserialize(clazz, responseDoc);
        }
        catch ( ParseException pe )
        {
            throw new XmlRpcException(pe.getMessage(), pe);
        }
    }

    private <T> T deserialize(java.lang.Class<T> clazz, Document requestDocument) throws XmlRpcException, ParseException
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
                return getParameter(clazz, param);

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

    private <T> T convertString(Class<T> clazz, String data) throws XmlRpcException
    {
        if ( data.getClass().equals(clazz) )
        {
            return ( T ) data;
        }
        throw new XmlRpcException("The data can not be converted");
    }

    private <T> T convertInteger(Class<T> clazz, int data)
    {
        return ( T ) new Integer(data);
    }

    private <T> T convertBoolean(Class<T> clazz, boolean data)
    {
        return ( T ) new Boolean(data);
    }

    private <T> T convertFloat(Class<T> clazz, float data)
    {
        return ( T ) new Float(data);
    }

    private <T> T convertDouble(Class<T> clazz, double data)
    {
        return ( T ) new Double(data);
    }

    private <T> T convertDate(Class<T> clazz, Date data) throws XmlRpcException
    {
        if ( data.getClass().equals(clazz) )
        {
            return ( T ) data;
        }
        throw new XmlRpcException("The data can not be converted");
    }

    private <T> T getValue(Class<T> clazz, Element type) throws XmlRpcException, ParseException, JDOMException
    {
        T result = null;
        String sType = type.getName();
        if ( sType.equals("string") )
        {
            result = convertString(clazz, type.getText());
        }
        else if ( sType.equals("i4") || sType.equals("int") )
        {
            result = convertInteger(clazz, Integer.parseInt(type.getText()));
        }
        else if ( sType.equals("boolean") )
        {
            boolean tempres = type.getText().equals("1") ? true : false;
            result = convertBoolean(clazz, tempres);
        }
        else if ( sType.equals("float") )
        {
            result = convertFloat(clazz, Float.parseFloat(type.getText()));
        }
        else if ( sType.equals("double") )
        {
            result = convertDouble(clazz, Double.parseDouble(type.getText()));
        }
        else if ( sType.equals("dateTime.iso8601") )
        {
            String date = type.getText();
            result = convertDate(clazz, iso8601dateFormat.parse(date));
        }
        else if ( sType.equals("array") )
        {
            result = deserializeArray(clazz, type);
        }
        else if ( sType.equals("struct") )
        {
            result = deserializeStruct(clazz, type);
        }
        else
        {
            throw new XmlRpcException("The type of data can not be deserialized type: " + sType);
        }
        return result;
    }

    private <T> T deserializeStruct(Class<T> clazz, Element struct) throws JDOMException, ParseException, XmlRpcException, XmlRpcException
    {
        List listMembers = XPath.selectNodes(struct, "./member");
        try
        {
            T result = clazz.newInstance();
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
                            Object value = getValue(field.getType(), eType);
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
                List childs = eValue.getChildren();
                for ( Object child : childs )
                {
                    if ( child instanceof Element )
                    {
                        Element eType = ( Element ) child;
                        Object value = getValue(componentType, eType);
                        Array.set(arrayToReturn, i, value);
                    }
                }
                i++;
            }
            return clazz.cast(arrayToReturn);
        }
        return null;
    }

    private <T> T getParameter(java.lang.Class<T> clazz, Element param) throws XmlRpcException, ParseException, JDOMException
    {
        Iterator values = param.getDescendants();
        while (values.hasNext())
        {
            Element elementValue = ( Element ) values.next();
            if ( elementValue.getName().equals("value") )
            {
                Iterator types = elementValue.getDescendants();
                while (types.hasNext())
                {
                    Element type = ( Element ) types.next();
                    return getValue(clazz, type);
                }
            }
        }
        throw new XmlRpcException("The tag value was not found");
    }

    private Document getDocument(InputStream in) throws XmlRpcException
    {
        SAXBuilder builder = new SAXBuilder();
        try
        {
            return builder.build(in);
        }
        catch ( Exception jde )
        {
            throw new XmlRpcException("It was not possible to contruct the document from the InputStream");
        }
    }

    private OutputStream sendHeaders(HttpURLConnection connection) throws IOException
    {
        connection.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);
        OutputStream out = connection.getOutputStream();
        return out;
    }

    private void sendFile(File file, String name, OutputStream out) throws IOException
    {
        String newBoundary = "\r\n--" + boundary + "\r\n";
        String contentDisposition = "Content-Disposition: form-data; name=\"" + name + "\"; filename=\"" + file.getName() + "\"\r\n\r\n";
        out.write(newBoundary.getBytes());
        out.write(contentDisposition.getBytes());
        FileInputStream in = new FileInputStream(file);
        byte[] buffer = new byte[2048];
        int read = in.read(buffer);
        while (read != -1)
        {
            out.write(buffer, 0, read);
            read = in.read(buffer);
        }
        in.close();
    }

    private void writeEnd(OutputStream out) throws IOException
    {
        String newBoundary = "\r\n--" + boundary + "\r\n";
        out.write(newBoundary.getBytes());
    }

    private void sendXmlDocumentPart(Document requestDoc, OutputStream out) throws IOException
    {
        String newBoundary = "--" + boundary + "\r\n";
        String contentDisposition = "Content-Disposition: form-data; name=\"xmlrpc\"; filename=\"xmlrpc.xml\"\r\n\r\n";
        out.write(newBoundary.getBytes());
        out.write(contentDisposition.getBytes());
        XMLOutputter outp = new XMLOutputter();
        outp.output(requestDoc, out);
    }

    private String getUserPassWordEncoded()
    {
        String userPassword = config.getUserName() + ":" + config.getPassword();
        String encoded = new String(encode(userPassword.getBytes()));
        return encoded;
    }

    private Document request(Document requestDoc, Set<Attachment> attachments) throws XmlRpcException, HttpException
    {
        OutputStream out = null;
        try
        {
            Proxy proxy;
            if ( config.usesProxyServer() )
            {
                proxy = new Proxy(Type.HTTP, new InetSocketAddress(config.getProxyServer().toString(), config.getProxyPort()));
            }
            else
            {
                proxy = Proxy.NO_PROXY;
            }
            HttpURLConnection connection = ( HttpURLConnection ) config.getWebAddress().toURL().openConnection(proxy);
            HttpURLConnection.setFollowRedirects(true);
            if ( config.hasUserPassWord() )
            {
                connection.setRequestProperty("Authorization", "Basic " + getUserPassWordEncoded());
            }
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
            connection.setDoInput(true);
            out = sendHeaders(connection);
            sendXmlDocumentPart(requestDoc, out);
            for ( Attachment attachment : attachments )
            {
                File file = attachment.getFile();
                String name = attachment.getName();
                sendFile(file, name, out);
            }
            writeEnd(out);
            out.close();
            int responseCode = connection.getResponseCode();
            String contentType = connection.getHeaderField("Content-Type");
            InputStream error = connection.getErrorStream();
            switch ( responseCode )
            {
                case HTTP_OK:
                    this.responseProperties = connection.getHeaderFields();
                    return getResponse(connection.getInputStream(), contentType);
                case HTTP_NOT_FOUND:
                    throw new HttpException("The path " + connection.getURL() + " was not found", HTTP_NOT_FOUND, getDetail(error, contentType));
                default:
                    throw new HttpException(connection.getResponseMessage(), HTTP_NOT_FOUND, getDetail(error, contentType));
            }
        }
        catch ( MalformedURLException mfe )
        {
            throw new XmlRpcException(mfe);
        }
        catch ( IOException ioe )
        {
            throw new XmlRpcException(ioe);
        }
        finally
        {
            if ( out != null )
            {
                try
                {
                    out.close();
                }
                catch ( IOException ioe )
                {
                // Revisar
                //throw new XmlRpcException(ioe);
                }
            }
        }
    }

    private String getDetail(InputStream in, String contentType) throws XmlRpcException
    {
        StringBuilder sb = new StringBuilder();

        String charSet = "utf-8";
        if ( contentType != null )
        {
            int posInit = contentType.indexOf("charset=");
            if ( posInit != -1 )
            {
                charSet = contentType.substring(posInit + 8);
            }
        }
        try
        {

            byte[] buffer = new byte[2048];
            int read = in.read(buffer);
            while (read != -1)
            {
                sb.append(new String(buffer, 0, read, charSet));
                read = in.read(buffer);
            }
        }
        catch ( IOException ioe )
        {
            throw new XmlRpcException("Error gettting the detail of response", ioe);
        }
        finally
        {
            if ( in != null )
            {
                try
                {
                    in.close();
                }
                catch ( IOException ioe )
                {
                    throw new XmlRpcException("Error closing conexión in the detail of response", ioe);
                }
            }
        }
        return sb.toString();
    }

    private Document getResponse(InputStream in, String contentType) throws XmlRpcException
    {
        if ( contentType == null )
        {
            throw new XmlRpcException("The content-Type is not valid");
        }
        if ( !contentType.startsWith("text/xml") )
        {
            throw new XmlRpcException("The content-Type is not text/xml");
        }
        Document doc = getDocument(in);
        try
        {
            in.close();
        }
        catch ( IOException ioe )
        {
            throw new XmlRpcException("Error getting the response document", ioe);
        }
        return doc;
    }

    private void addParameter(Element param, Object obj) throws XmlRpcException
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
            throw new XmlRpcException("This Type is not supported");
        }
        if ( svalue != null )
        {
            Element elementType = new Element(type);
            elementType.setText(svalue);
            value.setContent(elementType);
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

    private void addParameters(Element params, Object[] pParams) throws XmlRpcException
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

    private Document getXmlRpcDocument(
            String pMethodName, Object[] pParams) throws XmlRpcException
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
            addParameters(params, pParams);
        }
        methodCall.addContent(params);
        return doc;
    }
}
