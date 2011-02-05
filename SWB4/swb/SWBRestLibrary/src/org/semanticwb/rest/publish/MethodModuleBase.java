/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.rest.publish;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 *
 * @author victor.lorenzana
 */
public abstract class MethodModuleBase implements MethodModule
{

    protected static final String NAME = "name";
    protected static final String QUERY = "query";
    protected static final String REQUIRED = "required";
    protected static final String STATUS = "status";
    protected static final String STYLE = "style";
    protected static final String TYPE = "type";
    
    
    protected static final String APPLICATION_JSON = "application/json";
    
    
    protected static final String ELEMENT = "element";

    /*public void configureCommonsElements(PublishDefinition definition, Element method, Element request, String WADL_NS, String elementType)
    {
        Document doc = method.getOwnerDocument();
        Element param = doc.createElementNS(WADL_NS, "param");
        param.setAttribute(NAME, "format");
        param.setAttribute(STYLE, QUERY);
        request.appendChild(param);


        Element response = doc.createElementNS(WADL_NS, "response");
        method.appendChild(response);
        
        if (definition.isXML())
        {
            Element option = doc.createElementNS(WADL_NS, "option");
            option.setAttribute(VALUE, "xml");
            option.setAttribute(MEDIA_TYPE, APPLICATION_XML);
            param.appendChild(option);

            if (WADL_NS.equals(WADL_NS_2009))
            {  
                Element representation = doc.createElementNS(WADL_NS, "representation");
                response.appendChild(representation);
                representation.setAttribute(MEDIA_TYPE, APPLICATION_XML);
                response.setAttribute(STATUS, "400");
                representation.setAttribute(ELEMENT, SemanticClassPublisher.REST_RESOURCE_PREFIX + ":Error");
            }


            Element representation = doc.createElementNS(WADL_NS, "representation");
            response.appendChild(representation);
            representation.setAttribute(MEDIA_TYPE, APPLICATION_XML);
            if (WADL_NS.equals(WADL_NS_2009))
            {
                response.setAttribute(STATUS, "200");
                representation.setAttribute(ELEMENT, elementType);
            }
        }
        if (definition.isJSON())
        {
            Element option = doc.createElementNS(WADL_NS, "option");
            option.setAttribute(VALUE, "json");
            option.setAttribute(MEDIA_TYPE, APPLICATION_JSON);
            param.appendChild(option);

            Element representation = doc.createElementNS(WADL_NS, "representation");
            response.appendChild(representation);
            representation.setAttribute(MEDIA_TYPE, APPLICATION_JSON);
        }
        
    }*/
}
