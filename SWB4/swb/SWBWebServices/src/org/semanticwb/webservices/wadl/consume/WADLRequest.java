/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.webservices.wadl.consume;

import java.util.HashMap;
import org.semanticwb.webservices.ParameterDefinition;
import org.semanticwb.webservices.Request;
import org.semanticwb.webservices.ServiceException;
import org.semanticwb.webservices.ServiceInfo;
import org.semanticwb.webservices.util.XMLDocumentUtil;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 *
 * @author victor.lorenzana
 */
public class WADLRequest implements Request
{

    private final HashMap<String, WADLParameterDefinition> parameters = new HashMap<String, WADLParameterDefinition>();

    public WADLRequest(Element request, WADLOperation operation,ServiceInfo service) throws ServiceException
    {


        NodeList childs = request.getElementsByTagNameNS(request.getNamespaceURI(), "param");
        for (int i = 0; i < childs.getLength(); i++)
        {
            Element eparam = (Element) childs.item(i);
            String name = eparam.getAttribute("name");
            String _type = eparam.getAttribute("type");
            if (_type.equals(""))
            {
                _type = XMLDocumentUtil.getPrefix(service.getJDom(), XMLDocumentUtil.SCHEMA_NAMESPACE) + ":string";
            }
            String _name = eparam.getAttribute("name");
            boolean isRequired = Boolean.parseBoolean(eparam.getAttribute("required"));
            if (XMLDocumentUtil.isBasic(_type, service.getJDom()))
            {
                if (!parameters.containsKey(name))
                {
                    String classtype = XMLDocumentUtil.getClassTypeFromSchemaName(_type).getCanonicalName();
                    WADLParameterDefinition param = new WADLParameterDefinition(eparam, _type, _name, isRequired, classtype,service);
                    parameters.put(name, param);
                }
                Element parent = (Element) eparam.getParentNode();
                if (parent.getLocalName().equals("representation"))
                {
                    parameters.get(name).addRepresentations(parent);
                }


            }
            else
            {
                
                Element definition = XMLDocumentUtil.getGlobalElement(_type, service);
                WADLParameterDefinition param = new WADLParameterDefinition(definition, eparam,service);
                parameters.put(name, param);
            }
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
        return parameters.values().toArray(new ParameterDefinition[parameters.size()]);
    }
}
