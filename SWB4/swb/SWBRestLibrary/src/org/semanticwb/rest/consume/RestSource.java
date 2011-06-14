/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.rest.consume;

import java.net.URL;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import org.w3c.dom.Document;


/**
 *
 * @author victor.lorenzana
 */
public class RestSource
{

    public static final Map<String,Class<RepresentationRequest>> representationsRequest=Collections.synchronizedMap(new HashMap<String,Class<RepresentationRequest>>());
    public static final Map<String,Class<RepresentationResponse>> representationsResponse=Collections.synchronizedMap(new HashMap<String, Class<RepresentationResponse>>());
    
    private final URL wadlurl;    
    static
    {
        addRepresentationRequest(JSON.APPLICATION_JSON, (Class<RepresentationRequest>)JSON.class.asSubclass(RepresentationRequest.class));
        addRepresentationRequest(ApplicationXML.APPLICATION_XML, (Class<RepresentationRequest>)ApplicationXML.class.asSubclass(RepresentationRequest.class));
        addRepresentationRequest(AtomXML.APPLICATION_ATOM_XML, (Class<RepresentationRequest>)AtomXML.class.asSubclass(RepresentationRequest.class));
        addRepresentationRequest(MultipartFormData.MULTIPART_FORM_DATA, (Class<RepresentationRequest>)MultipartFormData.class.asSubclass(RepresentationRequest.class));
        addRepresentationRequest(XWWWFormUrlEncoded.APPLICATION_XWWW_FORM_URL_ENCODED, (Class<RepresentationRequest>)XWWWFormUrlEncoded.class.asSubclass(RepresentationRequest.class));

        addRepresentationResponse(JSON.APPLICATION_JSON, (Class<RepresentationResponse>)JSON.class.asSubclass(RepresentationResponse.class));
        addRepresentationResponse(ApplicationXML.APPLICATION_XML, (Class<RepresentationResponse>)ApplicationXML.class.asSubclass(RepresentationResponse.class));
        addRepresentationResponse(AtomXML.APPLICATION_ATOM_XML, (Class<RepresentationResponse>)AtomXML.class.asSubclass(RepresentationResponse.class));

    }
    
    public static void addRepresentationRequest(String mediaType,Class<RepresentationRequest> clazz)
    {
       if(!representationsRequest.containsKey(mediaType))
       {
           representationsRequest.put(mediaType, clazz);
       }
    }
    public static Class<RepresentationRequest> getRepresentationRequest(String mediaType)
    {
       return representationsRequest.get(mediaType);
    }
    public static void addRepresentationResponse(String mediaType,Class<RepresentationResponse> clazz)
    {
       if(!representationsResponse.containsKey(mediaType))
       {
           representationsResponse.put(mediaType, clazz);
       }
    }
    public static Class<RepresentationResponse> getRepresentationResponse(String mediaType)
    {
       return representationsResponse.get(mediaType);
    }
    public RestSource(URL url)
    {
        if (url == null)
        {
            throw new NullPointerException("the url is null");
        }
        this.wadlurl = url;
        if (!(url.getProtocol().toLowerCase().startsWith("http") || url.getProtocol().toLowerCase().startsWith("https")))
        {
            throw new IllegalArgumentException("The protocol " + url.getProtocol() + " is not suported (only http or https is supported)");
        }
    }
    public static boolean isWADL(Document doc)
    {

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
        if (!(xmlns.equals(RestPublish.WADL_NS_2006) || xmlns.equals(RestPublish.WADL_NS_2009)))
        {
            return true;
        }
        return false;        
        
    }
    public ServiceInfo getServiceInfo() throws RestException
    {
        ServiceInfo serviceInfo = new ServiceInfo(this.wadlurl);
        serviceInfo.loadService();
        return serviceInfo;
    }

    public URL getUrl()
    {
        return wadlurl;
    }
}
