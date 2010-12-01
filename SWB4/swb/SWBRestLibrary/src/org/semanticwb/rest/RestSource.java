/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.rest;

import java.net.URL;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;

/**
 *
 * @author victor.lorenzana
 */
public class RestSource
{

    public static final Map<String,Class<RepresentationRequest>> representationsRequest=Collections.synchronizedMap(new HashMap<String,Class<RepresentationRequest>>());
    public static final Map<String,Class<RepresentationResponse>> representationsResponse=Collections.synchronizedMap(new HashMap<String, Class<RepresentationResponse>>());
    private static final Logger log=SWBUtils.getLogger(RestSource.class);
    private final URL wadlurl;
    private ServiceInfo serviceInfo;
    static
    {
        addRepresentationRequest(AtomXML.APPLICATION_ATOM_XML, (Class<RepresentationRequest>)AtomXML.class.asSubclass(RepresentationRequest.class));
        addRepresentationRequest(ApplicationMultipartFormData.MULTIPART_FORM_DATA, (Class<RepresentationRequest>)ApplicationMultipartFormData.class.asSubclass(RepresentationRequest.class));
        addRepresentationRequest(XWWWFormUrlEncoded.APPLICATION_XWWW_FORM_URL_ENCODED, (Class<RepresentationRequest>)XWWWFormUrlEncoded.class.asSubclass(RepresentationRequest.class));


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
        serviceInfo = new ServiceInfo(url);

    }

    public ServiceInfo getServiceInfo() throws RestException
    {
        serviceInfo.loadService();
        return serviceInfo;
    }

    public URL getWadlUrl()
    {
        return wadlurl;
    }
}
