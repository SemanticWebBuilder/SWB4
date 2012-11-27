/*
 * SemanticWebBuilder es una plataforma para el desarrollo de portales y aplicaciones de integración,
 * colaboración y conocimiento, que gracias al uso de tecnología semántica puede generar contextos de
 * información alrededor de algún tema de interés o bien integrar información y aplicaciones de diferentes
 * fuentes, donde a la información se le asigna un significado, de forma que pueda ser interpretada y
 * procesada por personas y/o sistemas, es una creación original del Fondo de Información y Documentación
 * para la Industria INFOTEC, cuyo registro se encuentra actualmente en trámite.
 *
 * INFOTEC pone a su disposición la herramienta SemanticWebBuilder a través de su licenciamiento abierto al público (‘open source’),
 * en virtud del cual, usted podrá usarlo en las mismas condiciones con que INFOTEC lo ha diseñado y puesto a su disposición;
 * aprender de él; distribuirlo a terceros; acceder a su código fuente y modificarlo, y combinarlo o enlazarlo con otro software,
 * todo ello de conformidad con los términos y condiciones de la LICENCIA ABIERTA AL PÚBLICO que otorga INFOTEC para la utilización
 * del SemanticWebBuilder 4.0.
 *
 * INFOTEC no otorga garantía sobre SemanticWebBuilder, de ninguna especie y naturaleza, ni implícita ni explícita,
 * siendo usted completamente responsable de la utilización que le dé y asumiendo la totalidad de los riesgos que puedan derivar
 * de la misma.
 *
 * Si usted tiene cualquier duda o comentario sobre SemanticWebBuilder, INFOTEC pone a su disposición la siguiente
 * dirección electrónica:
 *  http://www.semanticwebbuilder.org
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
