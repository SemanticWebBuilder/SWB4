/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.wsdl.consume;

import java.util.ArrayList;
import org.semanticwb.wsdl.util.XMLDocumentUtil;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 *
 * @author victor.lorenzana
 */
public class Ouput
{

    private final ArrayList<ParameterDefinition> parameters = new ArrayList<ParameterDefinition>();
    private final Element output;
    private final Operation operation;
    public Ouput(Element output,Operation operation) throws ServiceException
    {
        this.operation=operation;
        this.output = output;
        String message = output.getAttribute("message");
        Element[] elements = XMLDocumentUtil.getElementByName(message, output.getOwnerDocument(),"message");
        for (Element eMessage : elements)
        {
            NodeList parts = eMessage.getElementsByTagNameNS(output.getNamespaceURI(), "part");
            for (int j = 0; j < parts.getLength(); j++)
            {
                Element epart = (Element) parts.item(j);
                ParameterDefinition parameter = new ParameterDefinition(epart);
                parameters.add(parameter);
            }

        }
    }

    public ParameterDefinition[] getParameters()
    {
        return parameters.toArray(new ParameterDefinition[parameters.size()]);
    }
    public ParameterDefinition getParameterDefinitionByName(String name)
    {
        if(name==null)
        {
            throw new NullPointerException("The name can not be null");
        }
        for(ParameterDefinition def : parameters)
        {
            if(name.equals(def.getName()))
            {
                return def;
            }
        }
        return null;
    }
}
