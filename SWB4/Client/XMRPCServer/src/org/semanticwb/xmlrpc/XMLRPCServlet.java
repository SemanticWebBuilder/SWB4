/**  
* SemanticWebBuilder es una plataforma para el desarrollo de portales y aplicaciones de integración, 
* colaboración y conocimiento, que gracias al uso de tecnología semántica puede generar contextos de 
* información alrededor de algún tema de interés o bien integrar información y aplicaciones de diferentes 
* fuentes, donde a la información se le asigna un significado, de forma que pueda ser interpretada y 
* procesada por personas y/o sistemas, es una creación original del Fondo de Información y Documentación 
* para la Industria INFOTEC, cuyo registro se encuentra actualmente en trámite. 
* 
* INFOTEC pone a su disposición la herramienta SemanticWebBuilder a través de su licenciamiento abierto al público (‘open source’), 
* en virtud del cual, usted podrá usarlo en las mismas condiciones con que INFOTEC lo ha diseñado y puesto a su disposición; 
* aprender de él; distribuirlo a terceros; acceder a su código fuente y modificarlo, y combinarlo o enlazarlo con otro software, 
* todo ello de conformidad con los términos y condiciones de la LICENCIA ABIERTA AL PÚBLICO que otorga INFOTEC para la utilización 
* del SemanticWebBuilder 4.0. 
* 
* INFOTEC no otorga garantía sobre SemanticWebBuilder, de ninguna especie y naturaleza, ni implícita ni explícita, 
* siendo usted completamente responsable de la utilización que le dé y asumiendo la totalidad de los riesgos que puedan derivar 
* de la misma. 
* 
* Si usted tiene cualquier duda o comentario sobre SemanticWebBuilder, INFOTEC pone a su disposición la siguiente 
* dirección electrónica: 
*  http://www.semanticwebbuilder.org
**/ 
 
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.xmlrpc;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletException;
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
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;
import org.jdom.xpath.XPath;
import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
import static org.semanticwb.xmlrpc.XmlRpcSerializer.*;

/**
 *
 * @author victor.lorenzana
 */
public abstract class XMLRPCServlet extends HttpServlet
{

    private static String boundary = "gc0p4Jq0M2Yt08jU534c0p";
    private static Logger log = SWBUtils.getLogger(XMLRPCServlet.class);
    private static final String RETURN = "\r\n";
    //private static Hashtable<String, Object> cacheObjects = new Hashtable<String, Object>();
    private static final String PREFIX_PROPERTY_PATH = "org.semanticwb.xmlrpc.";
    private static final String XMLRPC_DOCUMENT = "xmlrpc";

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        try
        {
            Document xmlrpcDocument;
            Set<Part> parts = new HashSet<Part>();
            if (isMultipart(request))
            {
                WBFileUpload upload = new WBFileUpload();
                upload.getFiles(request);
                for (String name : upload.getFileNames())
                {
                    if (!name.equals(XMLRPC_DOCUMENT))
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
                writeDocumentToConsole(xmlrpcDocument);
            }

            try
            {

                String methodName = getMethodName(xmlrpcDocument);
                ArrayList<Method> methods = getMethods(methodName);
                Object[] parameters = deserializeRequest(xmlrpcDocument, methods);
                Method method = getMethod(methodName, methods, parameters);
                String objectName = method.getDeclaringClass().getName();
                String user="",pass="";
                if(request.getAttribute("user")!=null)
                {
                    user=request.getAttribute("user").toString();
                }
                if(request.getAttribute("password")!=null)
                {
                    pass=request.getAttribute("password").toString();
                }
                Response objResponse = execute(objectName, method, parameters, parts, user, pass);
                Document docResponse = serializeResponse(objResponse.getObject());
                sendResponse(response, docResponse, objResponse);

            }
            catch (Exception cne)
            {                
                try
                {
                    Document docResponse = XMLRPCServlet.getException(cne);
                    sendResponse(response, docResponse);
                }
                catch (IOException ioeSendResponse)
                {
                    Document docResponse = XMLRPCServlet.getException(ioeSendResponse);
                    sendResponse(response, docResponse);
                }
            }
        }
        catch (Exception jde)
        {
            try
            {
                Document docResponse = XMLRPCServlet.getException(jde);
                sendResponse(response, docResponse);
            }
            catch (Exception ex)
            {
                // No se puede hacer nada, no puede seralizar la respuesta, debe guardar el error en el log
                log.error(ex);                
            }
        }

    }

    private static void writeDocumentToConsole(Document document)
    {
        try
        {
            Format format = Format.getPrettyFormat();
            format.setEncoding("UTF-8");
            XMLOutputter xMLOutputter = new XMLOutputter(format);
            xMLOutputter.output(document, System.out);
        }
        catch (Exception ex)
        {
            ex.printStackTrace(System.out);
        }
    }

    protected void beforeExecute(Object objToExecute, Set<Part> parts, String user, String password) throws Exception
    {
        if (objToExecute instanceof XmlRpcObject)
        {
            XmlRpcObject xmlRpcObject = (XmlRpcObject) objToExecute;
            xmlRpcObject.init(this.getServletConfig());
            xmlRpcObject.setUser(user);
            xmlRpcObject.setPassword(password);
            xmlRpcObject.setRequestParts(parts);
        }
    }

    protected void afterExecute(Object objToExecute)
    {
        if (objToExecute instanceof XmlRpcObject)
        {
            XmlRpcObject xmlRpcObject = (XmlRpcObject) objToExecute;
            xmlRpcObject.clearRequestParts();
        }
    }

    private static Method getMethod(String methodName, ArrayList<Method> methods, Object[] parameters) throws NoSuchMethodException
    {
        for (Method m : methods)
        {
            if (m.getParameterTypes().length == parameters.length)
            {
                return m;
            }
        }
        throw new NoSuchMethodException("The method " + methodName + " was not found");
    }

    private static void sendPart(byte[] content, String name, String filename, OutputStream out) throws IOException
    {
        String newBoundary = "\r\n--" + boundary + "\r\n";
        String contentDisposition = "Content-Disposition: form-data; name=\"" + name + "\"; filename=\"" + filename + "\"\r\n\r\n";
        out.write(newBoundary.getBytes());
        out.write(contentDisposition.getBytes());
        out.write(content);
    }

    private static void writeEnd(OutputStream out) throws IOException
    {
        String newBoundary = "\r\n--" + boundary + "\r\n";
        out.write(newBoundary.getBytes());
    }

    private static void sendXmlDocumentPart(Document requestDoc, OutputStream out) throws IOException
    {
        String newBoundary = "--" + boundary + "\r\n";
        String contentDisposition = "Content-Disposition: form-data; name=\"xmlrpc\"; filename=\"xmlrpc.xml\"\r\n\r\n";
        out.write(newBoundary.getBytes());
        out.write(contentDisposition.getBytes());
        Format format = Format.getPrettyFormat();
        format.setEncoding("UTF-8");
        XMLOutputter outp = new XMLOutputter(format);
        outp.output(requestDoc, out);
    }

    private static void sendResponse(ServletResponse response, Document docResponse, Response objToResponse) throws IOException
    {
        if (objToResponse.getResponseParts() == null || objToResponse.getResponseParts().size() == 0)
        {
            response.setContentType("text/xml;charset=utf-8");
            ServletOutputStream out = response.getOutputStream();
            XMLOutputter xMLOutputter = new XMLOutputter();
            String xml = xMLOutputter.outputString(docResponse);
            out.write(xml.getBytes("utf-8"));
            out.flush();
            out.close();
        }
        else
        {
            response.setContentType("multipart/form-data; boundary=" + boundary);
            OutputStream out = response.getOutputStream();
            sendXmlDocumentPart(docResponse, out);
            for (Part attachment : objToResponse.getResponseParts())
            {
                sendPart(attachment.getContent(), attachment.getName(), attachment.getFileName(), out);
            }
            writeEnd(out);
            out.flush();
            out.close();
        }

    }

    private static void sendResponse(ServletResponse response, Document docResponse) throws IOException
    {
        response.setContentType("text/xml;charset=utf-8");
        ServletOutputStream out = response.getOutputStream();
        Format format = Format.getPrettyFormat();
        format.setEncoding("UTF-8");
        XMLOutputter xMLOutputter = new XMLOutputter(format);
        xMLOutputter.output(docResponse, out);
        out.flush();
        out.close();
    }

    private static String getClassFullPath(String objectName) throws ClassNotFoundException
    {
        String classFullPath = System.getProperty(PREFIX_PROPERTY_PATH + objectName, null);
        if (classFullPath == null)
        {
            throw new ClassNotFoundException("The class for the Object Name: " + objectName + " was not found");
        }
        return classFullPath;
    }

    private Response execute(String objectName, Method method, Object[] parameters, Set<Part> parts, String user, String password) throws ClassNotFoundException, XmlRpcException, InstantiationException, IllegalAccessException, NoSuchMethodException
    {
        Class clazz = method.getDeclaringClass();

        /*Object objToExecute = cacheObjects.get(objectName);
        if ( objToExecute == null )
        {
        cacheObjects.put(objectName, clazz.newInstance());
        objToExecute = cacheObjects.get(objectName);
        }*/
        Object objToExecute = clazz.newInstance();
        try
        {
            beforeExecute(objToExecute, parts, user, password);
        }
        catch (Exception e)
        {
            throw new XmlRpcException("The object can not be inizialited into the method setupObject, cause: " + e.getLocalizedMessage(), e);
        }
        try
        {
            Object objectToReturn = method.invoke(objToExecute, parameters);
            afterExecute(objToExecute);
            Response resp = null;
            if (objToExecute instanceof XmlRpcObject)
            {
                resp = new Response(objectToReturn, ((XmlRpcObject) objToExecute).responseParts);
            }
            else
            {
                resp = new Response(objectToReturn);
            }
            return resp;
        }
        catch (InvocationTargetException inte)
        {
            log.debug(inte.getTargetException());
            throw new XmlRpcException(inte.getTargetException().getLocalizedMessage(), inte.getTargetException());
        }

    }

    private static String getMethodName(Document document) throws XmlRpcException, JDOMException, ClassNotFoundException
    {
        try
        {
            Element methodNameElement = (Element) XPath.selectSingleNode(document.getRootElement(), "/methodCall/methodName");
            if (methodNameElement == null)
            {
                throw new XmlRpcException("The methodName tag was not found");
            }
            return methodNameElement.getText();
        }
        catch (XmlRpcException e)
        {
            log.error(e);
            throw e;
        }
        catch (JDOMException e)
        {
            log.error(e);
            throw e;
        }
        
    }

    private static ArrayList<Method> getMethods(String pCallMethod) throws XmlRpcException, JDOMException, ClassNotFoundException
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
        ArrayList<Method> methods = new ArrayList<Method>();
        for (Method m : clazz.getMethods())
        {
            if (m.getName().equals(methodName))
            {
                methods.add(m);
            }
        }
        return methods;
    }

    private static void addElement(Element element, String name, int value) throws JDOMException, IOException
    {
        Element emember = new Element("member");
        Element ename = new Element("name");
        ename.setText(name);
        Element evalue = new Element("value");
        Element eint = new Element("int");
        evalue.addContent(eint);
        eint.setText(String.valueOf(value));
        element.addContent(emember);
        emember.addContent(ename);
        emember.addContent(evalue);
    }

    private static void addElement(Element element, String name, String value) throws JDOMException, IOException
    {
        Element emember = new Element("member");
        Element ename = new Element("name");
        ename.setText(name);
        Element evalue = new Element("value");
        Element eint = new Element("string");
        eint.setText(value);
        evalue.addContent(eint);
        element.addContent(emember);
        emember.addContent(ename);
        emember.addContent(evalue);
    }

    private static Document getException(Exception e) throws JDOMException, IOException
    {
        StringBuilder messageError = new StringBuilder(e.getLocalizedMessage() + RETURN);
        for (StackTraceElement element : e.getStackTrace())
        {
            messageError.append(element.toString() + RETURN);
        }
        if (e.getCause() != null)
        {
            messageError.append(" cause: \r\n");
            messageError.append(e.getCause().getLocalizedMessage() + RETURN);
            for (StackTraceElement element : e.getCause().getStackTrace())
            {
                messageError.append(element.toString() + RETURN);
            }
        }

        Document doc = new Document();
        Element methodResponse = new Element("methodResponse");
        doc.addContent(methodResponse);
        Element fault = new Element("fault");
        methodResponse.addContent(fault);
        Element value = new Element("value");
        Element struct = new Element("struct");
        fault.addContent(value);
        value.addContent(struct);
        addElement(struct, "faultCode", e.hashCode());
        addElement(struct, "faultString", messageError.toString());
        log.debug(e);
        return doc;

    }

    private static Document getDocument(byte[] document) throws JDOMException, IOException
    {
        ByteArrayInputStream in = new ByteArrayInputStream(document);
        SAXBuilder builder = new SAXBuilder();
        return builder.build(in);
    }

    private static Document getDocument(HttpServletRequest request) throws IOException, JDOMException
    {
        ServletInputStream in = request.getInputStream();
        SAXBuilder builder = new SAXBuilder();
        Document docToReturn = builder.build(in);
        in.close();
        return docToReturn;
    }

    private static boolean isMultipart(HttpServletRequest request)
    {
        boolean isMultipart = false;
        if (request.getContentType().indexOf("multipart/form-data") != -1)
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
        for (String objectName : mapType.keySet())
        {
            Class clazz = mapType.get(objectName);
            addMappingType(objectName, clazz);
        }
    }
}
