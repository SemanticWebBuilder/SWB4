/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.xmlrpc;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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

/**
 *
 * @author victor.lorenzana
 */
public class XMLRPCServlet extends HttpServlet
{

    private static SimpleDateFormat iso8601dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
    {
        try
        {
            Document xmlrpcDocument;
            List<Part> parts = new ArrayList<Part>();
            if (isMultipart(request))
            {
                WBFileUpload upload = new WBFileUpload();
                upload.getFiles(request);
                for (String name : upload.getFileNames())
                {
                    if(!name.equals("xmlrpc"))
                    {
                        byte[] content = upload.getFileData(name);
                        Part part = new Part(content,name,upload.getFileName(name));
                        parts.add(part);
                    }
                }
                xmlrpcDocument = getDocument(upload.getFileData("xmlrpc"));
            }
            else
            {
                xmlrpcDocument = getDocument(request);
            }


            try
            {
                Object[] parameters = getParameters(xmlrpcDocument);
                Object objResponse = execute(getCallMethod(xmlrpcDocument), parameters, parts);
                Document docResponse = this.serialize(objResponse);
                sendResponse(response, docResponse);
            }
            catch (Exception cne)
            {
                try
                {
                    Document docResponse = this.getException(cne);
                    sendResponse(response, docResponse);
                }
                catch (IOException ioeSendResponse)
                {

                }
            }
        }
        catch (JDOMException jde)
        {
            try
            {
                Document docResponse = this.getException(jde);
                sendResponse(response, docResponse);
            }
            catch (Exception ex)
            {

            }
        }
        catch (IOException ioe)
        {
            try
            {
                Document docResponse = this.getException(ioe);
                sendResponse(response, docResponse);
            }
            catch (Exception ioeSendResponse)
            {

            }
        }

    }

    private void sendResponse(ServletResponse response, Document docResponse) throws IOException
    {
        ServletOutputStream out = response.getOutputStream();
        XMLOutputter xMLOutputter = new XMLOutputter();
        xMLOutputter.output(docResponse,out);      
        out.flush();
        out.close();
    }

    private Document serialize(Object value) throws XmlRpcException
    {
        Document doc = new Document();
        Element methodResponse = new Element("methodResponse");
        doc.setRootElement(methodResponse);
        Element params = new Element("params");
        Object[] pParams = {value};
        addParameters(params, pParams);
        methodResponse.addContent(params);
        return doc;
    }

    public void addParameter(Element param, Object obj) throws XmlRpcException
    {
        Element value = new Element("value");
        String type = "string";
        String svalue = "";
        if (obj instanceof Integer)
        {
            type = "i4";
            svalue = obj.toString();
        }
        else
        {
            if (obj instanceof String)
            {
                type = "string";
                svalue = obj.toString();
            }
            else
            {
                if (obj instanceof Boolean)
                {
                    type = "boolean";
                    svalue = ((Boolean) obj).booleanValue() ? "1" : "0";
                }
                else
                {
                    if (obj instanceof Date)
                    {
                        type = "dateTime.iso8601";                        
                        svalue = iso8601dateFormat.format((Date) obj);
                    }
                    else
                    {
                        if (obj instanceof Double || obj instanceof Float)
                        {
                            type = "double";
                            svalue = obj.toString();
                        }
                        else
                        {
                            throw new XmlRpcException("This kind of convertion is not possible");
                        }
                    }
                }
            }
        }
        Element elementType = new Element(type);
        elementType.setText(svalue);
        value.setContent(elementType);
        param.addContent(value);
    }

    public void addParameters(Element params, Object[] pParams) throws XmlRpcException
    {
        for (Object obj : pParams)
        {
            if (obj != null)
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
        for (int i = 0; i < parameters.length; i++)
        {
            if(parameters[i].getClass()==Integer.class)
            {
                classToReturn[i] = int.class;
            }
            else if(parameters[i].getClass()==Double.class)
            {
                classToReturn[i] = double.class;
            }
            else if(parameters[i].getClass()==Boolean.class)
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
        String classFullPath = System.getProperty("org.semanticwb.xmlrpc." + objectName, null);
        if (classFullPath == null)
        {
            throw new ClassNotFoundException("The class for he Object Name: " + objectName + " was not found");
        }
        return classFullPath;
    }

    private Object execute(String pCallMethod, Object[] parameters, List<Part> parts) throws ClassNotFoundException, XmlRpcException, InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException
    {
        String[] values = pCallMethod.split("\\.");
        if (values.length != 2)
        {
            throw new XmlRpcException("The callMethos is incorrect");
        }
        String objectName = values[0];
        String methodName = values[1];
        String classFullPath = getClassFullPath(objectName);
        Class clazz = Class.forName(classFullPath);
        Class[] parameterTypes=this.getParameterTypes(parameters);        
        Method methodToFind = clazz.getMethod(methodName,parameterTypes);
        Object objToExecute = clazz.newInstance();
        if (objToExecute instanceof XmlRpcObject)
        {
            XmlRpcObject xmlRpcObject = (XmlRpcObject) objToExecute;
            xmlRpcObject.setParts(parts);
        }                
        return methodToFind.invoke(objToExecute, parameters);
        
    }

    private Object getParameter(Element eType) throws ParseException
    {
        Object res = null;
        String name = eType.getName();
        if (name.equalsIgnoreCase("i4") || name.equalsIgnoreCase("int"))
        {
            res = Integer.parseInt(eType.getText());
        }
        else
        {
            if (name.equalsIgnoreCase("boolean"))
            {
                res = eType.getText().equals("1") ? true : false;
            }
            else
            {
                if (name.equalsIgnoreCase("dateTime.iso8601"))
                {                    
                    String dateTime=eType.getText();                                        
                    res = iso8601dateFormat.parse(dateTime);
                }
                else
                {
                    if (name.equalsIgnoreCase("double"))
                    {
                        res = Double.parseDouble(eType.getText());
                    }
                    else
                    {
                        if (name.equalsIgnoreCase("string"))
                        {
                            res = eType.getText();
                        }
                        else
                        {
                            res = null;
                        }
                    }
                }
            }
        }
        return res;

    }

    private Object[] getParameters(Document document) throws JDOMException, ParseException
    {
        ArrayList<Object> parameters = new ArrayList<Object>();             
        List values = XPath.selectNodes(document, "/methodCall/params/param/value");
        for (Object oElement : values)
        {
            Element eValue = (Element) oElement;
            for (Object objElementType : eValue.getChildren())
            {
                Element eType = (Element) objElementType;
                parameters.add(getParameter(eType));
            }
        }
        return parameters.toArray();
    }

    private String getCallMethod(Document document) throws XmlRpcException, JDOMException
    {
        Element methodName = (Element) XPath.selectSingleNode(document.getRootElement(), "/methodCall/methodName");
        if (methodName == null)
        {
            throw new XmlRpcException("The methodName tag was not found");
        }
        return methodName.getText();
    }

    private Document getException(Exception e) throws JDOMException,IOException
    {
        String xmlString="<?xml version=\"1.0\" encoding=\"utf-8\"?><methodResponse><fault><value><struct><member><name>faultCode</name>" +
                "<value><int>"+ e.hashCode() +"</int></value></member><member><name>faultString</name>" +
                "<value><string>" + e.toString() + "</string></value></member></struct>" +
                "</value></fault></methodResponse>";
        Reader stringReader=new StringReader(xmlString);
        SAXBuilder builder=new SAXBuilder();
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
        if (request.getContentType().indexOf("multipart/form-data") != -1)
        {
            return true;
        }
        return false;
    }
}
