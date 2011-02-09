/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.rest.publish;

import java.net.URLEncoder;
import javax.servlet.http.HttpServletRequest;
import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
import org.semanticwb.platform.SemanticClass;
import org.semanticwb.rest.util.HTTPMethod;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 *
 * @author victor.lorenzana
 */
public class XMLRepresentation implements Representation
{

    public static final String XSD_STRING = "xsd:string";
    private static final String MEDIA_TYPE = "mediaType";
    private static final String APPLICATION_XML = "application/xml";
    private static final String VALUE = "value";
    protected static final String STATUS = "status";
    protected static final String ELEMENT = "element";
    public static final String WADL_NS_2009 = "http://wadl.dev.java.net/2009/02";
    private static final Logger log = SWBUtils.getLogger(XMLRepresentation.class);
    private final SemanticClass clazz;

    public XMLRepresentation(SemanticClass clazz)
    {
        this.clazz = clazz;
    }

    public RepresentationMediaType getRepresentationMediaType()
    {
        return RepresentationMediaType.XML;
    }

    public void addWADL(Element param,Element request,Element method, HTTPMethod HTTPMethod, String elementType)
    {
        Document doc = request.getOwnerDocument();
        String WADL_NS = request.getNamespaceURI();

        


        Element option = doc.createElementNS(WADL_NS, "option");
        option.setAttribute(VALUE, "xml");
        option.setAttribute(MEDIA_TYPE, APPLICATION_XML);
        param.appendChild(option);

        if (HTTPMethod == HTTPMethod.POST || HTTPMethod == HTTPMethod.PUT)
        {

            Element representation = doc.createElementNS(WADL_NS, "representation");
            representation.setAttribute(MEDIA_TYPE, APPLICATION_XML);
            request.appendChild(representation);

            Element _param = doc.createElementNS(WADL_NS, "param");
            _param.setAttribute("name", "format");
            _param.setAttribute("style", "query");
            _param.setAttribute("type", XSD_STRING);
            _param.setAttribute("fixed", getRepresentationMediaType().toString());
            _param.setAttribute("required", "true");
            representation.appendChild(_param);

        }

        if (WADL_NS.equals(WADL_NS_2009))
        {
            Element response = doc.createElementNS(WADL_NS, "response");
            method.appendChild(response);
            Element representation = doc.createElementNS(WADL_NS, "representation");
            response.appendChild(representation);
            representation.setAttribute(MEDIA_TYPE, APPLICATION_XML);
            response.setAttribute(STATUS, "400");
            representation.setAttribute(ELEMENT, SemanticClassPublisher.ERROR_PREFIX + ":Error");
        }
        Element response = doc.createElementNS(WADL_NS, "response");
        method.appendChild(response);
        Element representation = doc.createElementNS(WADL_NS, "representation");
        response.appendChild(representation);
        representation.setAttribute(MEDIA_TYPE, APPLICATION_XML);
        if (WADL_NS.equals(WADL_NS_2009))
        {
            response.setAttribute(STATUS, "200");
            representation.setAttribute(ELEMENT, elementType);
        }
    }

    public void addIncludes(Element grammars, HttpServletRequest servletRequest)
    {
        Document doc = grammars.getOwnerDocument();
        String WADL_NS = grammars.getNamespaceURI();
        try
        {
            Element include = doc.createElementNS(WADL_NS, "include");
            grammars.appendChild(include);
            include.setAttributeNS(WADL_NS, "href", servletRequest.getRequestURI() + "?clsmgr=xsd&classuri=" + URLEncoder.encode(clazz.getURI(), "utf-8"));
        }
        catch (Exception e)
        {
            log.error(e);
        }


        /*String xsd = URLEncoder.encode(clazz.getURI());
        Element include = doc.createElementNS(WADL_NS, "include");
        grammars.appendChild(include);
        include.setAttributeNS(WADL_NS, "href", servletRequest.getRequestURI() + "?xsd=" + xsd);*/
    }
}
