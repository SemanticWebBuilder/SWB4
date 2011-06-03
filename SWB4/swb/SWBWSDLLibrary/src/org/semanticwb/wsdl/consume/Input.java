/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.wsdl.consume;

import java.util.ArrayList;
import org.semanticwb.wsdl.util.XMLDocumentUtil;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 *
 * @author victor.lorenzana
 */
public class Input
{

    private final ArrayList<ParameterDefinition> parameters = new ArrayList<ParameterDefinition>();
    private final Element input;
    private final Operation operation;
    public Input(Element input,Operation operation)
    {
        this.operation=operation;
        this.input = input;
        String message = input.getAttribute("message");
        Element[] elements = XMLDocumentUtil.getElement(message, input.getOwnerDocument(),"message");
        for (Element eMessage : elements)
        {
            NodeList parts = eMessage.getElementsByTagNameNS(input.getNamespaceURI(), "part");
            for (int j = 0; j < parts.getLength(); j++)
            {
                Element epart = (Element) parts.item(j);
                ParameterDefinition parameter = new ParameterDefinition(epart,operation);
                parameters.add(parameter);
            }
        }
    }

    public ParameterDefinition[] getParameters()
    {
        return parameters.toArray(new ParameterDefinition[parameters.size()]);
    }
}
