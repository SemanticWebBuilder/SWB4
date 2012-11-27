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

import javax.jcr.Node;
import javax.jcr.RepositoryException;
import javax.jcr.lock.Lock;
import javax.jcr.lock.LockException;

/**
 *
 * @author victor.lorenzana
 */
public class LockImp implements Lock
{

    private final SimpleNode nodeLock;
    private final String lockowner;
    private final boolean isDeep;
    private final boolean isSessionScoped;
    LockImp(SimpleNode node) throws RepositoryException
    {
        this(node, false);
    }
    LockImp(SimpleNode node,boolean isSessionScoped) throws RepositoryException
    {
        if ( !(node.isLockable() && node.isLocked()) )
        {
            throw new IllegalArgumentException("The node " + node.getName() + " is not lockable or is not locked");
        }        
        nodeLock = node.getLockBaseNode();
        lockowner = node.getLockOwner();
        isDeep = node.isDeepLock();
        this.isSessionScoped=isSessionScoped;
    }

    public SimpleNode getLockBaseNode()
    {
        return nodeLock;
    }

    public String getLockOwner()
    {
        return lockowner;
    }

    public boolean isDeep()
    {
        return isDeep;
    }

    public Node getNode()
    {
        return nodeLock;
    }

    public String getLockToken()
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public boolean isLive() throws RepositoryException
    {
        boolean isLive=false;
        if(nodeLock.isLocked())
        {
            isLive=true;
        }
        return isLive;
    }

    public boolean isSessionScoped()
    {
        return isSessionScoped;
    }

    public void refresh() throws LockException, RepositoryException
    {

    }

    @Override
    public boolean equals(Object obj)
    {
        boolean equals = false;
        if ( obj instanceof LockImp )
        {
            equals = (( LockImp ) obj).nodeLock.equals(this.nodeLock);
        }
        return equals;
    }

    @Override
    public int hashCode()
    {
        return nodeLock.hashCode();
    }
}
