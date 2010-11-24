/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.resources.rest;

import java.net.URL;

import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;

/**
 *
 * @author victor.lorenzana
 */
public class RestSource
{
    private static final Logger log=SWBUtils.getLogger(RestSource.class);
    private final URL wadlurl;
    private ServiceInfo serviceInfo;

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
