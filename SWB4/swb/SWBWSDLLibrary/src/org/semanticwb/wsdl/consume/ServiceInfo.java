/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.wsdl.consume;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashSet;

import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.jdom.output.DOMOutputter;
import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 *
 * @author victor.lorenzana
 */
public class ServiceInfo
{

    protected static final String TEXT_XML = "text/xml";
    private static final String APPLICATION_XML = "application/xml";
    private static final Logger log = SWBUtils.getLogger(ServiceInfo.class);
    private static final String SCHEMA_NAMESPACE = "http://www.w3.org/2000/10/XMLSchema";
    private static final String WSDL_NAMESPACE = "http://schemas.xmlsoap.org/wsdl/";
    private static final String CONTENT_TYPE = "Content-Type";
    private static final String APPLICATION_WSDL_CONTENT_TYPE ="application/wsdl+xml";
    private final URL url;
    private Document doc;
    private final HashSet<Operation> operations = new HashSet<Operation>();

    public ServiceInfo(URL url)
    {
        this.url = url;
    }

    public void loadService() throws ServiceException
    {
        try
        {
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            if (con.getResponseCode() == 200)
            {
                Charset charset = Charset.forName("utf-8");
                String contentType = con.getContentType();
                if (contentType != null)
                {
                    int pos = contentType.indexOf("charset=");
                    if (pos != -1)
                    {
                        String scharset = contentType.substring(pos + 8).trim();
                        charset = Charset.forName(scharset);
                        contentType = contentType.substring(0, pos).trim();
                        if (contentType.endsWith(";"))
                        {
                            contentType = contentType.substring(0, contentType.length() - 1);
                        }
                    }
                }

                if (contentType != null && (contentType.equalsIgnoreCase(TEXT_XML) || contentType.equalsIgnoreCase(APPLICATION_WSDL_CONTENT_TYPE)))
                {

                    InputStreamReader reader = new InputStreamReader(con.getInputStream(), charset);
                    DOMOutputter out = new DOMOutputter();
                    SAXBuilder builder = new SAXBuilder();
                    try
                    {
                        doc = out.output(builder.build(reader));
                        if (doc == null)
                        {
                            throw new ServiceException("The content of the url is invalid");
                        }
                        if (isWSDL())
                        {
                            extractIncludes();
                            fill();
                        }
                        else
                        {
                            throw new ServiceException("The document is not a WADL valid");
                        }
                    }
                    catch (JDOMException jdone)
                    {
                        throw new ServiceException("Error trying to get XML", jdone);
                    }
                }
                else
                {
                    throw new ServiceException("The response has a not valid Content-Type header: " + con.getHeaderField(CONTENT_TYPE) + "(only " + APPLICATION_XML + " are valid)");
                }
            }
            else
            {
                throw new ServiceException("The document was not found error code " + con.getResponseCode());
            }
        }
        catch (IOException ioe)
        {
            throw new ServiceException(ioe);
        }
    }

    public boolean isWSDL()
    {
        if (!validate(doc))
        {
            return false;
        }
        String attname="";
        String prefix=doc.getDocumentElement().getPrefix();
        if(prefix==null)
        {
            attname="xmlns";
        }
        else
        {
            attname="xmlns:"+prefix;
        }

        String xmlns = doc.getDocumentElement().getAttribute(attname);
        if (!(xmlns.equals(WSDL_NAMESPACE)))
        {
            return false;

        }
        return true;
    }

    private void extractIncludes() throws ServiceException
    {
        NodeList nodesgrammars = doc.getElementsByTagNameNS(WSDL_NAMESPACE, "definitions");
        for (int i = 0; i < nodesgrammars.getLength(); i++)
        {
            if (nodesgrammars.item(i) instanceof Element)
            {
                Element grammars = (Element) nodesgrammars.item(i);
                ArrayList<Document> includes = new ArrayList<Document>();
                ArrayList<Element> toDelete = new ArrayList<Element>();
                NodeList nodes = grammars.getElementsByTagNameNS(WSDL_NAMESPACE, "include");
                for (int j = 0; j < nodes.getLength(); j++)
                {
                    if (nodes.item(j) instanceof Element)
                    {
                        Element include = (Element) nodes.item(j);
                        String spath = include.getAttribute("location");
                        URL path = url;
                        try
                        {
                            URI uriPath = new URI(spath);
                            if (!uriPath.isAbsolute())
                            {
                                URI base = url.toURI();
                                if (!url.toString().endsWith("/"))
                                {
                                    String newpath = url.toString() + "/";
                                    base = new URI(newpath);
                                }
                                URI temp = base.resolve(uriPath);
                                path = temp.normalize().toURL();
                            }
                            else
                            {
                                path = uriPath.toURL();
                            }
                        }
                        catch (Exception e)
                        {
                            log.debug(e);
                        }
                        try
                        {
                            Document docinclude = getDocument(path);
                            includes.add(docinclude);
                        }
                        catch (Exception e)
                        {
                            throw new ServiceException(e);
                        }

                    }
                }

                for (Element e : toDelete)
                {
                    grammars.removeChild(e);
                }
                for (Document includedoc : includes)
                {
                    importInLine(includedoc, grammars);
                }

            }
        }
    }

    private boolean validate(Document doc)
    {
        return true;
        /*SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        boolean validate = false;

        String docschema = null;
        InputStream in = XMLConstants.class.getResourceAsStream("/org/semanticwb/wsdl/util/wsdlschema.xml");

        


        try
        {
            InputStreamReader reader = new InputStreamReader(in, Charset.forName("utf-8"));
            char[] buffer = new char[1024];
            StringBuilder sb = new StringBuilder();
            int read = reader.read(buffer);
            while (read != -1)
            {
                sb.append(new String(buffer, 0, buffer.length));
                read = reader.read(buffer);
            }
            docschema=sb.toString();
            in.close();
        }
        catch (Exception e)
        {
            log.debug(e);
        }
        if (docschema != null)
        {

            StringReader reader = new StringReader(docschema);
            Source schemaFile = new StreamSource(reader);
            try
            {
                Schema schema = factory.newSchema(schemaFile);
                Validator validator = schema.newValidator();
                DOMSource source = new DOMSource(doc);
                validator.validate(source);
                validate = true;
            }
            catch (IOException ioe)
            {
                log.debug(ioe);
                validate = false;
            }
            catch (SAXException saxe)
            {
                String xml = SWBUtils.XML.domToXml(doc, Charset.defaultCharset().name(), true);
                log.debug("El documento no es válido con namespace " + doc.getDocumentElement().getNamespaceURI() + "\r\n " + xml, saxe);
                validate = false;
            }
        }
        return validate;*/
    }

    private Document getDocument(URL url) throws ServiceException
    {
        try
        {

            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            if (con.getResponseCode() == 200)
            {
                Charset charset = Charset.forName("utf-8");
                String contentType = con.getContentType();
                if (contentType != null)
                {
                    int pos = contentType.indexOf("charset=");
                    if (pos != -1)
                    {
                        String scharset = contentType.substring(pos + 8).trim();
                        charset = Charset.forName(scharset);
                    }
                }
                if (con.getHeaderField(CONTENT_TYPE) != null && (con.getHeaderField(CONTENT_TYPE).equalsIgnoreCase(APPLICATION_XML) || con.getHeaderField(CONTENT_TYPE).equalsIgnoreCase(TEXT_XML)))
                {
                    InputStreamReader reader = new InputStreamReader(con.getInputStream(), charset);
                    DOMOutputter out = new DOMOutputter();
                    SAXBuilder builder = new SAXBuilder();
                    Document response = out.output(builder.build(reader));
                    if (response == null)
                    {
                        throw new ServiceException("The content of the url is invalid");
                    }
                    return response;
                }
                else
                {
                    throw new ServiceException("The response has a not valid Content-Type header: " + con.getHeaderField(CONTENT_TYPE) + "(only " + APPLICATION_XML + " are valid)");
                }
            }
            else
            {
                throw new ServiceException("The document was not found error code " + con.getResponseCode());
            }
        }
        catch (Exception ioe)
        {
            throw new ServiceException(ioe);
        }
    }

    private void importInLine(Document docinclude, Element grammars)
    {
        Element rootElement = docinclude.getDocumentElement();
        Node importedNode = grammars.getOwnerDocument().importNode(rootElement, true);
        grammars.appendChild(importedNode);
    }

    private void fill() throws ServiceException
    {
        NodeList _operations = doc.getElementsByTagNameNS(WSDL_NAMESPACE,"operation");
        for (int i = 0; i < _operations.getLength(); i++)
        {
            Element operationElement = (Element) _operations.item(i);
            Operation operation = new Operation(operationElement,this);
            operations.add(operation);
        }
    }

    public Operation[] getOperations()
    {
        return operations.toArray(new Operation[operations.size()]);
    }
    public URL getUrl()
    {
        return url;
    }
}
