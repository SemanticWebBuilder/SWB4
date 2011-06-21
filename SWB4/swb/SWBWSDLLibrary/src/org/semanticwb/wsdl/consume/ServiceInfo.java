/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.wsdl.consume;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashSet;
import javax.xml.XMLConstants;
import javax.xml.transform.Source;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.jdom.output.DOMOutputter;
import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

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
    private static final String SCHEMA_2001_NAMESPACE = "http://www.w3.org/2001/XMLSchema";
    private static final String WSDL_NAMESPACE = "http://schemas.xmlsoap.org/wsdl/";
    private static final String CONTENT_TYPE = "Content-Type";
    private static final String APPLICATION_WSDL_CONTENT_TYPE = "application/wsdl+xml";
    private final URL url;
    private Document doc;
    //private ArrayList<Document> schemas = new ArrayList<Document>();
    private final HashSet<Operation> operations = new HashSet<Operation>();

    public ServiceInfo(URL url)
    {
        this.url = url;

    }
    /*private void extractSchemas()
    {
    NodeList _schemas=doc.getElementsByTagNameNS(SCHEMA_2001_NAMESPACE, "schema");
    ArrayList<Element> eSchemas=new ArrayList<Element>();
    for(int i=0;i<_schemas.getLength();i++)
    {
    Element schema=(Element)_schemas.item(i);
    eSchemas.add(schema);           
    }
    for(Element schema : eSchemas)
    {
    Document doc_schema=SWBUtils.XML.getNewDocument();
    Node newSchema=doc_schema.importNode(schema, true);
    doc_schema.appendChild(newSchema);  
    }
    for(Element schema : eSchemas)
    {
    schema.getParentNode().removeChild(schema);
    }
    
    }*/

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


        if (!validate())
        {
            return false;
        }
        String attname = "";
        String prefix = doc.getDocumentElement().getPrefix();
        if (prefix == null)
        {
            attname = "xmlns";
        }
        else
        {
            attname = "xmlns:" + prefix;
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
        NodeList nodesgrammars = doc.getElementsByTagNameNS(WSDL_NAMESPACE, "types");
        for (int i = 0; i < nodesgrammars.getLength(); i++)
        {
            if (nodesgrammars.item(i) instanceof Element)
            {
                Element grammars = (Element) nodesgrammars.item(i);
                ArrayList<Document> includes = new ArrayList<Document>();
                ArrayList<Element> toDelete = new ArrayList<Element>();
                NodeList nodes = grammars.getElementsByTagNameNS(SCHEMA_NAMESPACE, "import");
                if (nodes.getLength() == 0)
                {
                    nodes = grammars.getElementsByTagNameNS(SCHEMA_2001_NAMESPACE, "import");
                }
                for (int j = 0; j < nodes.getLength(); j++)
                {
                    if (nodes.item(j) instanceof Element)
                    {
                        Element include = (Element) nodes.item(j);
                        toDelete.add((Element) include.getParentNode());
                        String spath = include.getAttribute("schemaLocation");
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

    private boolean validate()
    {



        SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        boolean validate = false;

        String docschema = null;
        InputStream in = XMLConstants.class.getResourceAsStream("/org/semanticwb/wsdl/util/wsdlschema.xml");




        try
        {
            InputStreamReader reader = new InputStreamReader(in, Charset.forName("utf-8"));
            DOMOutputter out = new DOMOutputter();
            SAXBuilder builder = new SAXBuilder();
            Document docSchema = out.output(builder.build(reader));
            docschema = SWBUtils.XML.domToXml(docSchema);

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
        return validate;
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
                        contentType = contentType.substring(0, pos).trim();
                        if (contentType.endsWith(";"))
                        {
                            contentType = contentType.substring(0, contentType.length() - 1);
                        }
                        charset = Charset.forName(scharset);
                    }
                }
                if (contentType != null && (contentType.equalsIgnoreCase(APPLICATION_XML) || contentType.equalsIgnoreCase(TEXT_XML)))
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
        String xml = SWBUtils.XML.domToXml(doc, "utf-8", true);
        StringReader r = new StringReader(xml);
        SAXBuilder builder = new SAXBuilder();
        org.jdom.Document _jdom = null;
        try
        {
            _jdom = builder.build(r);
        }
        catch (Exception e)
        {
            log.error(e);
            throw new ServiceException(e);
        }
        NodeList _portTypes = doc.getElementsByTagNameNS(WSDL_NAMESPACE, "portType");
        for (int j = 0; j < _portTypes.getLength(); j++)
        {
            Element _portType = (Element) _portTypes.item(j);
            NodeList _operations = _portType.getElementsByTagNameNS(WSDL_NAMESPACE, "operation");
            for (int i = 0; i < _operations.getLength(); i++)
            {
                Element operationElement = (Element) _operations.item(i);
                Operation operation = new Operation(operationElement, _jdom);
                operations.add(operation);
            }
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

    public Operation getOperationByName(String name)
    {
        if (name == null)
        {
            throw new NullPointerException("The operation name is null");
        }
        for (Operation op : operations)
        {
            if (name.equals(op.getName()))
            {
                return op;
            }
        }
        return null;
    }
}
