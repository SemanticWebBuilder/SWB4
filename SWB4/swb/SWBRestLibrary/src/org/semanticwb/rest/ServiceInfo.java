/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.rest;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.StringTokenizer;
import javax.xml.XMLConstants;
import javax.xml.transform.Source;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
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
    private final Set<Resource> resources = new HashSet<Resource>();
    private String WADL_NS = RestPublish.WADL_NS_2009;
    private final ArrayList<Document> includes=new ArrayList<Document>();
    public ServiceInfo(final URL url)
    {
        this.url = url;
    }

    public URL getResourcesBasePath() throws RestException
    {
        if (resourcesBasePath == null)
        {
            loadService();
        }
        return resourcesBasePath;
    }

    private void fill(Document doc) throws RestException
    {

        resources.clear();
        NodeList lresources = doc.getElementsByTagNameNS(WADL_NS, "resources");
        if (lresources.getLength() == 1)
        {
            Node node = lresources.item(0);
            if (node instanceof Element)
            {
                Element eresources = (Element) node;
                String base = eresources.getAttribute("base");
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
        NodeList nodes = doc.getElementsByTagNameNS(WADL_NS, "resource");
        for (int i = 0; i < nodes.getLength(); i++)
        {
            if (nodes.item(i) instanceof Element)
            {
                Resource resource = Resource.createResourceInfo((Element) nodes.item(i), resourcesBasePath);
                resources.add(resource);
            }
        }
    }

    public Resource[] getResources()
    {
        return resources.toArray(new Resource[resources.size()]);
    }
    private void extractIncludes(Document doc) throws RestException
    {
        includes.clear();
        NodeList gramars=doc.getElementsByTagNameNS(WADL_NS, "grammars");
        for(int i=0;i<gramars.getLength();i++)
        {
            if(gramars.item(i) instanceof Element)
            {
                Element einclude=(Element)gramars.item(i);
                NodeList nodes=einclude.getElementsByTagNameNS(WADL_NS, "include");
                for(int j=0;j<nodes.getLength();j++)
                {
                    if(nodes.item(j) instanceof Element)
                    {
                        Element include=(Element)nodes.item(j);
                        String spath=include.getAttribute("href");
                        URL path=url;
                        try
                        {
                            URI uriPath=new URI(spath);
                            if(!uriPath.isAbsolute())
                            {
                                URI base=url.toURI();
                                if(!url.toString().endsWith("/"))
                                {
                                    String newpath=url.toString()+"/";
                                    base=new URI(newpath);
                                }
                                URI temp=base.resolve(uriPath);
                                path=temp.normalize().toURL();
                            }
                            else
                            {
                                path=uriPath.toURL();
                            }
                        }
                        catch(Exception e)
                        {
                            e.printStackTrace();
                            log.debug(e);
                        }
                        try
                        {
                            Document docinclude=getDocument(path);
                            System.out.println(SWBUtils.XML.domToXml(docinclude));
                            includes.add(docinclude);
                        }
                        catch(Exception e)
                        {
                            throw new RestException(e);
                        }

                    }
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
                if (con.getHeaderField(CONTENT_TYPE) != null && con.getHeaderField(CONTENT_TYPE).equalsIgnoreCase(APPLICATION_XML))
                {
                    InputStream in = con.getInputStream();
                    Document response = SWBUtils.XML.xmlToDom(in);
                    if (response == null)
                    {
                        throw new RestException("The content of the url is invalid");
                    }
                    if (isWADL(response))
                    {
                        extractIncludes(response);
                        fill(response);
                    }
                    else
                    {
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
                if (con.getHeaderField(CONTENT_TYPE) != null && (con.getHeaderField(CONTENT_TYPE).equalsIgnoreCase(APPLICATION_XML) || con.getHeaderField(CONTENT_TYPE).equalsIgnoreCase(TEXT_XML)))
                {
                    InputStream in = con.getInputStream();
                    Document response = SWBUtils.XML.xmlToDom(in);
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
        boolean validate=false;
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
                Document docschema = null;
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
                    SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
                    StringReader reader = new StringReader(SWBUtils.XML.domToXml(docschema));
                    Source schemaFile = new StreamSource(reader);
                    try
                    {
                        Schema schema = factory.newSchema(schemaFile);
                        Validator validator = schema.newValidator();
                        DOMSource source = new DOMSource(doc);
                        validator.validate(source);
                        validate=true;
                    }
                    catch (IOException ioe)
                    {
                        log.debug(ioe);
                        validate=false;
                    }
                    catch (SAXException saxe)
                    {
                        log.debug(saxe);
                        validate=false;
                    }
                }
            }
        }
        return validate;

    }

    private boolean isWADL(Document doc)
    {

        if(!validate(doc))
        {
            return false;
        }
        String xmlns = doc.getDocumentElement().getAttribute("xmlns");
        if(!(xmlns.equals(RestPublish.WADL_NS_2006) || xmlns.equals(RestPublish.WADL_NS_2009)))
        {
            return false;

        }
        WADL_NS=xmlns;
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
            e.printStackTrace();
        }
        return false;
    }

    public URL getURLForGET()
    {
        return null;
    }
}
