/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.webservices.wsdl.consume;

import java.util.ArrayList;
import org.semanticwb.webservices.ParameterDefinition;
import org.semanticwb.webservices.Response;
import org.semanticwb.webservices.ServiceException;
import org.semanticwb.webservices.ServiceInfo;
import org.semanticwb.webservices.util.XMLDocumentUtil;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 *
 * @author victor.lorenzana
 */
public class WSDLOutput implements Response
{

    private final ArrayList<WSDLParameterDefinition> parameters = new ArrayList<WSDLParameterDefinition>();

    public WSDLOutput(Element output, WSDLOperation operation, ServiceInfo service) throws ServiceException
    {
        String message = output.getAttribute("message");
        Element eMessage = XMLDocumentUtil.getElementByName(message, "message", service,output);
        if (eMessage == null)
        {
            throw new ServiceException("The message " + message + " was not found");
        }
        NodeList parts = eMessage.getElementsByTagNameNS(output.getNamespaceURI(), "part");
        for (int j = 0; j < parts.getLength(); j++)
        {
            Element epart = (Element) parts.item(j);
            WSDLParameterDefinition parameter = new WSDLParameterDefinition(epart, service);
            parameters.add(parameter);
        }
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
}
