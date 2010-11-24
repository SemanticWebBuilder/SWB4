/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.rest;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;
import org.semanticwb.SWBUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 *
 * @author victor.lorenzana
 */
public class ServiceInfo {

    private static final String APPLICATION_XML = "application/xml";
    private static final String CONTENT_TYPE = "Content-Type";
    public static final String WADL_NS = "http://research.sun.com/wadl/2006/10";
    private final URL url;
    private URL resourcesBasePath;
    private static final String JSON_CONTENT_TYPE = "json";
    private final Set<Resource> resources=new HashSet<Resource>();
    public ServiceInfo(final URL url)
    {
        this.url=url;        
    }
    public URL getResourcesBasePath() throws RestException
    {
        if(resourcesBasePath==null)
        {
            loadService();
        }
        return resourcesBasePath;
    }
    private void fill(Document doc) throws RestException
    {
        resources.clear();
        NodeList lresources=doc.getElementsByTagNameNS(WADL_NS, "resources");
        if(lresources.getLength()==1)
        {
            Node node=lresources.item(0);
            if(node instanceof Element)
            {
                Element eresources=(Element)node;
                String base=eresources.getAttribute("base");
                try
                {
                    resourcesBasePath=new URL(url, base);
                }
                catch(MalformedURLException mfue)
                {
                    throw new RestException(mfue);
                }
            }
        }
        NodeList nodes=doc.getElementsByTagNameNS(WADL_NS, "resource");
        for(int i=0;i<nodes.getLength();i++)
        {
            if(nodes.item(i) instanceof Element)
            {
                Resource resource=Resource.createResourceInfo((Element)nodes.item(i),resourcesBasePath);
                resources.add(resource);
            }
        }
    }
    public Resource[] getResources()
    {
        return resources.toArray(new Resource[resources.size()]);
    }
    public void loadService() throws RestException
    {
        try
        {
            HttpURLConnection con=(HttpURLConnection)url.openConnection();
            if(con.getResponseCode()==200)
            {
                if(con.getHeaderField(CONTENT_TYPE)!=null && con.getHeaderField(CONTENT_TYPE).equalsIgnoreCase(APPLICATION_XML))
                {
                    InputStream in=con.getInputStream();
                    Document response=SWBUtils.XML.xmlToDom(in);
                    if(response==null)
                    {
                        throw new RestException("The content of the url is invalid");
                    }
                    if(isWADL(response))
                    {
                        fill(response);
                    }
                    else
                    {
                        
                    }
                }
                else
                {
                    throw new RestException("The response has a not valid Content-Type header: "+con.getHeaderField(CONTENT_TYPE)+"(only "+JSON_CONTENT_TYPE+","+APPLICATION_XML+" are valid)");
                }
            }
            else
            {
                throw new RestException("The document was not found error code "+con.getResponseCode());
            }
        }
        catch(IOException ioe)
        {
            throw new RestException(ioe);
        }
    }
    private boolean isWADL(Document doc)
    {
        try
        {
            NodeList nodes=doc.getElementsByTagNameNS(WADL_NS, "application");
            if(nodes.getLength()>0)
            {
                return true;
            }
        }
        catch(Exception e)
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
