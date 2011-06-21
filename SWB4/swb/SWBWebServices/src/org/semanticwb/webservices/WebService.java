/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.webservices;

import java.net.URL;
import org.semanticwb.webservices.util.XMLDocumentUtil;
import org.semanticwb.webservices.wadl.consume.WADLServiceInfo;
import org.semanticwb.webservices.wsdl.consume.WSDLServiceInfo;
import org.w3c.dom.Document;

/**
 *
 * @author victor.lorenzana
 */
public class WebService
{

    public static ServiceInfo getServiceinfo(URL url) throws ServiceException
    {
        Document description = XMLDocumentUtil.getDocument(url);
        if (WADLServiceInfo.isWADL(description))
        {
            return new WADLServiceInfo(description,url);
        }
        else if (WSDLServiceInfo.isWSDL(description))
        {
            return new WSDLServiceInfo(description,url);
        }
        throw new ServiceException("The document type is not supported");
    }
}
