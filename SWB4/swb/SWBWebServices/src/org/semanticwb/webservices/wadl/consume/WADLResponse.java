/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.webservices.wadl.consume;

import java.util.HashSet;
import org.semanticwb.webservices.ParameterDefinition;
import org.semanticwb.webservices.Response;
import org.semanticwb.webservices.ServiceException;
import org.semanticwb.webservices.ServiceInfo;
import org.semanticwb.webservices.util.HTTPMethod;
import org.semanticwb.webservices.util.XMLDocumentUtil;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 *
 * @author victor.lorenzana
 */
public class WADLResponse implements Response
{
    private final String mediaType;
    private final HashSet<WADLParameterDefinition> parameters = new HashSet<WADLParameterDefinition>();

    public WADLResponse(Element response, WADLOperation operation, ServiceInfo service) throws ServiceException
    {
        String _mediaType=null;
        NodeList childs = response.getElementsByTagNameNS(response.getNamespaceURI(), "representation");
        for (int i = 0; i < childs.getLength(); i++)
        {
            Element representation = (Element) childs.item(i);
            if ((operation.getHTTPMethod() == HTTPMethod.GET || operation.getHTTPMethod() == HTTPMethod.DELETE) && representation.getAttribute("mediaType") == null || representation.getAttribute("mediaType").trim().equals(""))
            {
                throw new ServiceException("The attribute mediaType was not found");
            }
            _mediaType = representation.getAttribute("mediaType");
            if (_mediaType.equals("application/xml"))
            {
                String element = representation.getAttribute("element");                
                Element definition = XMLDocumentUtil.getGlobalElement(element, service);
                if(definition==null)
                {
                    throw new ServiceException("The element "+element+" was not found");
                }
                WADLParameterDefinition _definition = new WADLParameterDefinition(definition, representation,service);
                parameters.add(_definition);
            }
        }
        this.mediaType=_mediaType;
    }

    @Override
    public boolean hasDefinition()
    {
        return !parameters.isEmpty();
    }

    @Override
    public ParameterDefinition[] getDefinitions()
    {
        return parameters.toArray(new ParameterDefinition[parameters.size()]);
    }

    @Override
    public String toString()
    {
        return mediaType;
    }
    
    
    
}
