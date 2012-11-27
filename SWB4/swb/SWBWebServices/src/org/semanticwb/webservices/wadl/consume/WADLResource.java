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

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import org.semanticwb.webservices.Operation;
import org.semanticwb.webservices.Service;
import org.semanticwb.webservices.ServiceException;
import org.semanticwb.webservices.ServiceInfo;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 *
 * @author victor.lorenzana
 */
public class WADLResource implements Service
{

    private final String id;
    private final URL path;
    private final HashMap<String, WADLOperation> operations = new HashMap<String, WADLOperation>();
    private final HashSet<WADLResource> services = new HashSet<WADLResource>();

    public WADLResource(Element eresource, URL basePath, ServiceInfo service) throws ServiceException
    {
        String _id = eresource.getAttribute("id");
        if (_id == null || eresource.getAttribute("id").trim().equals(""))
        {
            _id = eresource.getAttribute("path");
        }
        this.id = _id;
        URL _path;
        if (eresource.getAttribute("path") != null && !eresource.getAttribute("path").trim().equals(""))
        {
            try
            {
                String spath = eresource.getAttribute("path");
                URI uriPath = new URI(spath);
                if (!uriPath.isAbsolute())
                {
                    URI base = basePath.toURI();
                    if (!basePath.toString().endsWith("/"))
                    {
                        String newpath = basePath.toString() + "/";
                        base = new URI(newpath);
                    }
                    URI temp = base.resolve(uriPath);
                    _path = temp.normalize().toURL();
                }
                else
                {
                    _path = uriPath.toURL();
                }
            }
            catch (URISyntaxException me)
            {
                throw new ServiceException(me);
            }
            catch (MalformedURLException me)
            {
                throw new ServiceException(me);
            }
        }
        else
        {
            _path = basePath;
        }
        this.path = _path;

        NodeList childs = eresource.getChildNodes();
        for (int i = 0; i < childs.getLength(); i++)
        {
            if (childs.item(i) instanceof Element && ((Element) childs.item(i)).getNamespaceURI().equals(eresource.getNamespaceURI()))
            {
                Element child = (Element) childs.item(i);
                if (child.getLocalName().equals("method"))
                {
                    Element eMetod = (Element) childs.item(i);
                    WADLOperation m = new WADLOperation(eMetod, service);
                    operations.put(m.getName(), m);
                }
                if (child.getLocalName().equals("resource"))
                {
                    WADLResource resource = new WADLResource(child, basePath, service);
                    services.add(resource);

                }
            }
        }
    }

    @Override
    public String getId()
    {
        return id;
    }

    @Override
    public Service[] getServices()
    {
        return this.services.toArray(new Service[this.services.size()]);
    }

    @Override
    public Operation[] getOperations()
    {
        ArrayList<Operation> getOperations = new ArrayList<Operation>();
        getOperations.addAll(this.operations.values());
        for (WADLResource resource : this.services)
        {
            getOperations.addAll(Arrays.asList(resource.getOperations()));
        }
        return getOperations.toArray(new Operation[getOperations.size()]);
    }

    @Override
    public Operation getOperationByName(String name)
    {
        Operation op = this.operations.get(name);
        if (op != null)
        {
            return op;
        }
        for (Service service : this.services)
        {
            op = service.getOperationByName(name);
            if (op != null)
            {
                return op;
            }
        }
        return null;
    }
}
