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
import java.util.HashMap;
import java.util.HashSet;
import org.semanticwb.webservices.Operation;
import org.semanticwb.webservices.Service;
import org.semanticwb.webservices.ServiceException;
import org.semanticwb.webservices.ServiceInfo;
import org.semanticwb.webservices.util.XMLDocumentUtil;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 *
 * @author victor.lorenzana
 */
public class WSDLService implements Service
{

    private final String name;
    private final HashMap<String,WSDLOperation> operations = new HashMap<String,WSDLOperation>();

    public WSDLService(Element service, ServiceInfo serviceInfo) throws ServiceException
    {
        name = service.getAttribute("name");
        NodeList _ports = service.getElementsByTagNameNS(XMLDocumentUtil.WSDL_NAMESPACE, "port");
        for (int k = 0; k < _ports.getLength(); k++)
        {
            Element _port = (Element) _ports.item(k);
            String _binding = _port.getAttribute("binding");
            Element eBinding = XMLDocumentUtil.getElementByName(_binding, "binding", serviceInfo,_port);
            if(eBinding==null)
            {
                throw new ServiceException("The binding "+_binding+" was not found");
            }
            String type = eBinding.getAttribute("type");
            Element _portType = XMLDocumentUtil.getElementByName(type, "portType", serviceInfo,eBinding);
            if(_portType==null)
            {
                throw new ServiceException("The portType "+type+" was not found");
            }
            NodeList _operations = _portType.getElementsByTagNameNS(_portType.getNamespaceURI(), "operation");
            for (int i = 0; i < _operations.getLength(); i++)
            {
                Element operationElement = (Element) _operations.item(i);
                WSDLOperation operation = new WSDLOperation(operationElement, serviceInfo,_portType,eBinding);
                operations.put(operation.getName(),operation);
            }

//            int pos = _binding.indexOf(":");
//            if (pos != -1)
//            {
//                _binding = _binding.substring(pos + 1);
//            }
//            NodeList _bindings = service.getOwnerDocument().getElementsByTagNameNS(XMLDocumentUtil.WSDL_NAMESPACE, "binding");
//            for (int l = 0; l < _bindings.getLength(); l++)
//            {
//                Element eBinding = (Element) _bindings.item(l);
//                if (eBinding.getAttribute("name").equals(_binding))
//                {
//                    String type = eBinding.getAttribute("type");
//                    pos = type.indexOf(":");
//                    if (pos != -1)
//                    {
//                        type = type.substring(pos + 1);
//                    }
//                    NodeList _portTypes = service.getOwnerDocument().getElementsByTagNameNS(XMLDocumentUtil.WSDL_NAMESPACE, "portType");
//                    for (int j = 0; j < _portTypes.getLength(); j++)
//                    {
//                        Element _portType = (Element) _portTypes.item(j);
//                        if (_portType.getAttribute("name").equals(type))
//                        {
//                            NodeList _operations = _portType.getElementsByTagNameNS(_portType.getNamespaceURI(), "operation");
//                            for (int i = 0; i < _operations.getLength(); i++)
//                            {
//                                Element operationElement = (Element) _operations.item(i);
//                                WSDLOperation operation = new WSDLOperation(operationElement, serviceInfo);
//                                operations.add(operation);
//                            }
//                        }
//                    }
//                }
//            }
        }
    }

    @Override
    public String getId()
    {
        return name;
    }

    @Override
    public Service[] getServices()
    {
        return new Service[0];
    }

    @Override
    public Operation[] getOperations()
    {
        ArrayList<Operation> getOperations = new ArrayList<Operation>();
        getOperations.addAll(this.operations.values());
        return getOperations.toArray(new Operation[getOperations.size()]);
    }

    @Override
    public Operation getOperationByName(String name)
    {
        return operations.get(name);
    }
}
