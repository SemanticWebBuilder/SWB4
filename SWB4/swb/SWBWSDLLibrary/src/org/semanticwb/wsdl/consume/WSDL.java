/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.wsdl.consume;

import java.net.URL;
import org.w3c.dom.Document;

/**
 *
 * @author victor.lorenzana
 */
public class WSDL
{
    private static final String WSDL_NAMESPACE = "http://schemas.xmlsoap.org/wsdl/";
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
    public static boolean isWSDL(Document doc)
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
        if (!(xmlns.equals(WSDL_NAMESPACE)))
        {
            return true;
        }
        return false;
    }
    public ServiceInfo getServiceInfo() throws ServiceException
    {
        ServiceInfo serviceInfo = new ServiceInfo(this.url);
        serviceInfo.loadService();
        return serviceInfo;
    }

}
