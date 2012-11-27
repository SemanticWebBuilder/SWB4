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
package org.semanticwb.jcr170.implementation;

import java.util.Hashtable;
import javax.jcr.AccessDeniedException;
import javax.jcr.NamespaceException;
import javax.jcr.NamespaceRegistry;
import javax.jcr.RepositoryException;
import javax.jcr.UnsupportedRepositoryOperationException;
import org.semanticwb.repository.BaseNode;

/**
 *
 * @author victor.lorenzana
 */
public class NamespaceRegistryImp implements NamespaceRegistry
{
    
    private final Hashtable<String,String> namespaces;
    NamespaceRegistryImp()
    {   
        namespaces=BaseNode.listUris();
    }
    
    public void registerNamespace(String prefix, String uri) throws NamespaceException, UnsupportedRepositoryOperationException, AccessDeniedException, RepositoryException
    {        
        throw new UnsupportedRepositoryOperationException("Not supported yet.");
    }

    public void unregisterNamespace(String arg0) throws NamespaceException, UnsupportedRepositoryOperationException, AccessDeniedException, RepositoryException
    {
        throw new UnsupportedRepositoryOperationException("Not supported yet.");
    }

    public String[] getPrefixes() throws RepositoryException
    {
        return namespaces.keySet().toArray(new String[namespaces.keySet().size()]);
    }

    public String[] getURIs() throws RepositoryException
    {
        return namespaces.values().toArray(new String[namespaces.keySet().size()]);
    }

    public String getURI(String prefix) throws NamespaceException, RepositoryException
    {
        return namespaces.get(prefix);
    }

    public String getPrefix(String uri) throws NamespaceException, RepositoryException
    {
        for(String prefix : this.namespaces.keySet())
        {
            String namespace=namespaces.get(prefix);
            if(namespace.equals(uri))
            {
                return prefix;
            }
        }
        throw new NamespaceException("the uri "+ uri +" was not found");
    }
}
