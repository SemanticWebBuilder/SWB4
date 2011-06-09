/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.wsdl.consume;

import java.net.URL;

/**
 *
 * @author victor.lorenzana
 */
public class WSDL
{

    private final URL url;

    public WSDL(URL url)
    {
        
        if (url == null)
            throw new NullPointerException("The url can not be null");
        if (!(url.getProtocol().toLowerCase().startsWith("http") || url.getProtocol().toLowerCase().startsWith("https")))
        {
            throw new IllegalArgumentException("The protocol " + url.getProtocol() + " is not suported (only http or https is supported)");
        }
        this.url = url;
        

    }

    public ServiceInfo getServiceInfo() throws ServiceException
    {
        ServiceInfo serviceInfo = new ServiceInfo(this.url);
        serviceInfo.loadService();
        return serviceInfo;
    }

}
