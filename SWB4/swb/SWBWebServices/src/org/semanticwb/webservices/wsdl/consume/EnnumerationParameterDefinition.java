/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.webservices.wsdl.consume;

import java.util.HashSet;
import org.semanticwb.webservices.ServiceException;
import org.semanticwb.webservices.ServiceInfo;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 *
 * @author victor.lorenzana
 */
public class EnnumerationParameterDefinition extends WSDLParameterDefinition
{
    private final HashSet<String> values=new HashSet<String>();
    public EnnumerationParameterDefinition(String namespace,Element definition, String type, String name, boolean isRequired, ServiceInfo service)
    {
        
        super(namespace, definition, type, name, isRequired, service);
        NodeList enumerations=definition.getElementsByTagNameNS(definition.getNamespaceURI(),"enumeration");
        for(int i=0;i<enumerations.getLength();i++)
        {
            Element e=(Element)enumerations.item(i);
            String value=e.getAttribute("value");
            values.add(value);            
        }
        
    }
    @Override
    public void add(Element element, Object value) throws ServiceException
    {
        if(value==null)
        {
            throw new ServiceException("The value can not be null");
        }
        String _value=value.toString();
        if(!values.contains(_value))
        {
            StringBuilder sb=new StringBuilder();
            for(String svalue : values)
            {
                sb.append(" ");
                sb.append(svalue);
            }
            throw new ServiceException("The value "+_value +" is not valid, valid values are "+sb.toString().trim());
        }
        super.add(element, value);        
    }
}
