/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.rest.consume;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;
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

    private static final Logger log = SWBUtils.getLogger(ServiceInfo.class);
    protected static final String TEXT_XML = "text/xml";
    private static final String APPLICATION_XML = "application/xml";
    private static final String CONTENT_TYPE = "Content-Type";
    private final URL url;
    private URL resourcesBasePath;
    private static final String JSON_CONTENT_TYPE = "json";
    private final Map<String, Resource> resources = new HashMap<String, Resource>();
    private String WADL_NS = RestPublish.WADL_NS_2009;
    private Document doc;

    public Document getDocument()
    {
        Charset charset = Charset.defaultCharset();
        String xml = SWBUtils.XML.domToXml(doc, charset.name(), false);
        return SWBUtils.XML.xmlToDom(xml);
    }

    public ServiceInfo(final URL url)
    {
        this.url = url;
    }

    public Method getMethod(String id)
    {
        for (Resource subresource : resources.values())
        {
            Method tmp = subresource.getMethod(id);
            if (tmp != null)
            {
                return tmp;
            }
        }
        return null;
    }

    public Method[] getMethods()
    {
        ArrayList<Method> allMethods = new ArrayList<Method>();
        for (Resource resource : resources.values())
        {
            allMethods.addAll(Arrays.asList(resource.getAllMethods()));
        }
        return allMethods.toArray(new Method[allMethods.size()]);
    }

    public String getNamespaceURI()
    {
        return WADL_NS;
    }

    public URL getResourcesBasePath() throws RestException
    {
        if (resourcesBasePath == null)
        {
            loadService();
        }
        return resourcesBasePath;
    }

    private void addResources(Element eResources)
    {
    }

    private void fill() throws RestException
    {

        resources.clear();
        NodeList lresources = doc.getElementsByTagNameNS(WADL_NS, "resources");
        for (int i = 0; i < lresources.getLength(); i++)
        {
            if (lresources.item(i) instanceof Element)
            {
                Element eresources = (Element) lresources.item(i);
                NodeList childs = eresources.getChildNodes();
                for (int j = 0; j < childs.getLength(); j++)
                {
                    if (childs.item(j) instanceof Element && ((Element) childs.item(j)).getNamespaceURI().equals(WADL_NS) && ((Element) childs.item(j)).getLocalName().equals("resource"))
                    {
                        Element eresource = ((Element) childs.item(j));
                        String base = eresource.getAttribute("base");
                        try
                        {
                            URI baseuri = new URI(base);
                            if (baseuri.isAbsolute())
                            {
                                resourcesBasePath = baseuri.toURL();
                            }
                            else
                            {
                                URI tempbase = this.url.toURI();
                                if (!tempbase.toString().endsWith("/"))
                                {
                                    String newpath = tempbase.toString() + "/";
                                    tempbase = new URI(newpath);
                                }
                                resourcesBasePath = tempbase.resolve(baseuri).normalize().toURL();
                            }



                            Resource resource = Resource.createResourceInfo(eresource, resourcesBasePath, this);
                            this.resources.put(resource.getId(), resource);

                        }
                        catch (MalformedURLException mfue)
                        {
                            throw new RestException(mfue);
                        }
                        catch (URISyntaxException e)
                        {
                            throw new RestException(e);
                        }
                    }
                }
            }
        }
    }

    public Resource[] getResources()
    {
        return resources.values().toArray(new Resource[resources.size()]);
    }

    public Resource getResource(String id)
    {
        if (resources.containsKey(id))
        {
            return resources.get(id);
        }
        else
        {
            for (Resource resource : resources.values())
            {
                Resource tmp = resource.getResource(id);
                if (tmp != null)
                {
                    return tmp;
                }
            }
        }
        return null;
    }

    private void importInLine(Document docinclude, Element grammars)
    {
        Element rootElement = docinclude.getDocumentElement();
        Node importedNode = grammars.getOwnerDocument().importNode(rootElement, true);
        grammars.appendChild(importedNode);
    }

    private void extractIncludes() throws RestException
    {
        NodeList nodesgrammars = doc.getElementsByTagNameNS(WADL_NS, "grammars");
        for (int i = 0; i < nodesgrammars.getLength(); i++)
        {
            if (nodesgrammars.item(i) instanceof Element)
            {
                Element grammars = (Element) nodesgrammars.item(i);
                ArrayList<Document> includes = new ArrayList<Document>();
                ArrayList<Element> toDelete = new ArrayList<Element>();
                NodeList nodes = grammars.getElementsByTagNameNS(WADL_NS, "include");
                for (int j = 0; j < nodes.getLength(); j++)
                {
                    if (nodes.item(j) instanceof Element)
                    {
                        Element include = (Element) nodes.item(j);
                        String spath = include.getAttribute("href");
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
                            throw new RestException(e);
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

    public void loadService() throws RestException
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
                    }
                }
                if (con.getHeaderField(CONTENT_TYPE) != null && con.getHeaderField(CONTENT_TYPE).equalsIgnoreCase(APPLICATION_XML))
                {

                    InputStreamReader reader = new InputStreamReader(con.getInputStream(), charset);
                    DOMOutputter out = new DOMOutputter();
                    SAXBuilder builder = new SAXBuilder();
                    try
                    {
                        doc = out.output(builder.build(reader));
                        if (doc == null)
                        {
                            throw new RestException("The content of the url is invalid");
                        }
                        if (isWADL())
                        {
                            extractIncludes();
                            fill();
                        }
                        else
                        {
                        }
                    }
                    catch (JDOMException jdone)
                    {
                        throw new RestException("Error trying to get XML", jdone);
                    }
                }
                else
                {
                    throw new RestException("The response has a not valid Content-Type header: " + con.getHeaderField(CONTENT_TYPE) + "(only " + JSON_CONTENT_TYPE + "," + APPLICATION_XML + " are valid)");
                }
            }
            else
            {
                throw new RestException("The document was not found error code " + con.getResponseCode());
            }
        }
        catch (IOException ioe)
        {
            throw new RestException(ioe);
        }
    }

    private Document getDocument(URL url) throws RestException
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
                        throw new RestException("The content of the url is invalid");
                    }
                    return response;
                }
                else
                {
                    throw new RestException("The response has a not valid Content-Type header: " + con.getHeaderField(CONTENT_TYPE) + "(only " + JSON_CONTENT_TYPE + "," + APPLICATION_XML + " are valid)");
                }
            }
            else
            {
                throw new RestException("The document was not found error code " + con.getResponseCode());
            }
        }
        catch (Exception ioe)
        {
            throw new RestException(ioe);
        }
    }

    private boolean validate(Document doc)
    {

        SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        boolean validate = false;

        Document docschema = null;
        if (doc.getDocumentElement().getNamespaceURI().equals(org.semanticwb.rest.util.XMLConstants.WADL_NS_2006))
        {
            InputStream in = XMLConstants.class.getResourceAsStream("/org/semanticwb/rest/util/wadl_2006.xml");
            docschema = SWBUtils.XML.xmlToDom(in);
            try
            {
                in.close();
            }
            catch (Exception e)
            {
                log.debug(e);
            }
            if (docschema != null)
            {

                StringReader reader = new StringReader(SWBUtils.XML.domToXml(docschema));
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
                    log.debug(saxe);
                    validate = false;
                }
            }
        }
        if (doc.getDocumentElement().getNamespaceURI().equals(org.semanticwb.rest.util.XMLConstants.WADL_NS_2009))
        {
            InputStream in = XMLConstants.class.getResourceAsStream("/org/semanticwb/rest/util/wadl_2009.xml");
            docschema = SWBUtils.XML.xmlToDom(in);
            try
            {
                in.close();
            }
            catch (Exception e)
            {
                log.debug(e);
            }
            if (docschema != null)
            {

                StringReader reader = new StringReader(SWBUtils.XML.domToXml(docschema));
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
                    log.debug(saxe);
                    validate = false;
                }
            }
        }
        else
        {
            String schemaLocation = doc.getDocumentElement().getAttributeNS(XMLConstants.W3C_XML_SCHEMA_INSTANCE_NS_URI, "schemaLocation");
            if (schemaLocation == null || schemaLocation.trim().equals(""))
            {
                schemaLocation = "http://www.w3.org/Submission/wadl/wadl.xsd";
            }
            StringTokenizer st = new StringTokenizer(schemaLocation, " \r");
            while (st.hasMoreTokens())
            {
                String location = st.nextToken();
                if (location != null && !location.trim().equals(""))
                {

                    try
                    {
                        URL urlschema = new URL(location);
                        docschema = getDocument(urlschema);
                    }
                    catch (Exception e)
                    {
                        log.debug(e);
                    }
                    if (docschema != null)
                    {

                        StringReader reader = new StringReader(SWBUtils.XML.domToXml(docschema));
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
                            log.debug(saxe);
                            validate = false;
                        }
                    }
                }
            }
        }
        return validate;

    }

    private boolean isWADL()
    {

        if (!validate(doc))
        {
            return false;
        }
        String xmlns = doc.getDocumentElement().getAttribute("xmlns");
        if (!(xmlns.equals(RestPublish.WADL_NS_2006) || xmlns.equals(RestPublish.WADL_NS_2009)))
        {
            return false;

        }
        WADL_NS = xmlns;
        try
        {
            NodeList nodes = doc.getElementsByTagNameNS(WADL_NS, "application");
            if (nodes.getLength() > 0)
            {
                return true;
            }
        }
        catch (Exception e)
        {
            log.debug(e);
        }
        return false;
    }

    public URL getURLForGET()
    {
        return null;
    }
}
