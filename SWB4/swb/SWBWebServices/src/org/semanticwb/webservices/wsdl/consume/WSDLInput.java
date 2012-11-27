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
package org.semanticwb.webservices.wsdl.consume;

import java.util.ArrayList;
import org.semanticwb.webservices.ParameterDefinition;
import org.semanticwb.webservices.Request;
import org.semanticwb.webservices.ServiceException;
import org.semanticwb.webservices.ServiceInfo;
import org.semanticwb.webservices.util.XMLDocumentUtil;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 *
 * @author victor.lorenzana
 */
public class WSDLInput implements Request
{

    private final ArrayList<WSDLParameterDefinition> parameters = new ArrayList<WSDLParameterDefinition>();

    public WSDLInput(Element input, WSDLOperation operation, ServiceInfo service) throws ServiceException
    {
        String message = input.getAttribute("message");
        Element eMessage = XMLDocumentUtil.getElementByName(message, "message", service,input);
        if (eMessage == null)
        {
            throw new ServiceException("The message " + message + " was not found");
        }
        NodeList parts = eMessage.getElementsByTagNameNS(input.getNamespaceURI(), "part");
        for (int j = 0; j < parts.getLength(); j++)
        {
            Element epart = (Element) parts.item(j);
            WSDLParameterDefinition parameter = new WSDLParameterDefinition(epart,service);
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
    public WSDLParameterDefinition[] getWSDLParameterDefinitions()
    {
        return parameters.toArray(new WSDLParameterDefinition[parameters.size()]);
    }
    
}
