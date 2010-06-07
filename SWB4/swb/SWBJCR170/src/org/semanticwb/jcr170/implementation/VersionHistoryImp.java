/**  
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
**/ 
 
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.jcr170.implementation;

import java.util.ArrayList;
import java.util.Iterator;
import javax.jcr.AccessDeniedException;
import javax.jcr.ReferentialIntegrityException;
import javax.jcr.RepositoryException;
import javax.jcr.UnsupportedRepositoryOperationException;
import javax.jcr.version.Version;
import javax.jcr.version.VersionException;
import javax.jcr.version.VersionHistory;
import javax.jcr.version.VersionIterator;
import org.semanticwb.SWBException;
import org.semanticwb.model.GenericIterator;
import org.semanticwb.repository.BaseNode;

/**
 *
 * @author victor.lorenzana
 */
public class VersionHistoryImp extends SimpleNode implements VersionHistory
{

    VersionHistoryImp(BaseNode versionHistory, SessionImp session, SimpleNode parent) throws RepositoryException
    {
        super(versionHistory, session);
        if (!versionHistory.isVersionHistoryNode())
        {
            throw new IllegalArgumentException("The node is not a versionhistory node");
        }
        this.parent = parent;
    }

    public BaseNode getVersionHistoryBase()
    {
        return node;
    }

    public BaseNode[] getVersions() throws SWBException
    {
        ArrayList<BaseNode> versions = new ArrayList<BaseNode>();
        for (BaseNode version : node.getVersions())
        {
            boolean isRemoved=false;
            for (SimpleNode removed : this.removedchilds)
            {
                if (removed.node != null && removed.node.equals(version))
                {
                    isRemoved=true;
                    break;
                }
            }
            if(!isRemoved)
            {
                versions.add(version);
            }
        }
        return versions.toArray(new BaseNode[versions.size()]);
    }

    public String getVersionableUUID() throws RepositoryException
    {
        try
        {
            return node.getUUID();
        }
        catch (SWBException swbe)
        {
            throw new RepositoryException(swbe);
        }
    }

    public Version getRootVersion() throws RepositoryException
    {
        GenericIterator<BaseNode> nodes = this.node.listNodes();
        while (nodes.hasNext())
        {
            BaseNode child = nodes.next();
            if (child.getName().equals("jcr:rootVersion"))
            {
                return new VersionImp(child, this, session);
            }
        }
        throw new RepositoryException("The root version was not found");
    }

    public VersionIterator getAllVersions() throws RepositoryException
    {
        try
        {
            return new VersionIteratorImp(this, session, parent);
        }
        catch (SWBException swbe)
        {
            throw new RepositoryException(swbe);
        }
    }

    public Version getVersion(String name) throws VersionException, RepositoryException
    {
        GenericIterator<BaseNode> nodes = this.node.listNodes();
        while (nodes.hasNext())
        {
            BaseNode child = nodes.next();
            if (child!=null && name!=null && child.getName()!=null && child.getSemanticObject()!=null && child.getSemanticObject().getSemanticClass()!=null && child.getName().equals(name) && child.getSemanticObject().getSemanticClass().equals(org.semanticwb.repository.Version.nt_Version))
            {
                return new VersionImp(child, this, session);
            }
        }
        throw new RepositoryException("The version " + name + " was not found");
    }

    public Version getVersionByLabel(String label) throws RepositoryException
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void addVersionLabel(String arg0, String arg1, boolean arg2) throws VersionException, RepositoryException
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void removeVersionLabel(String arg0) throws VersionException, RepositoryException
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public boolean hasVersionLabel(String arg0) throws RepositoryException
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public boolean hasVersionLabel(Version arg0, String arg1) throws VersionException, RepositoryException
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public String[] getVersionLabels() throws RepositoryException
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public String[] getVersionLabels(Version arg0) throws VersionException, RepositoryException
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void removeVersion(String arg0) throws ReferentialIntegrityException, AccessDeniedException, UnsupportedRepositoryOperationException, VersionException, RepositoryException
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
