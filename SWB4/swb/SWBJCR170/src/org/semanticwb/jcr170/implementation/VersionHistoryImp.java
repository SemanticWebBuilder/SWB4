/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.jcr170.implementation;

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
import org.semanticwb.repository.BaseNode;

/**
 *
 * @author victor.lorenzana
 */
public class VersionHistoryImp extends SimpleNode implements VersionHistory
{    
    VersionHistoryImp(BaseNode versionHistory,SessionImp session,SimpleNode parent) throws RepositoryException
    {
        super(versionHistory, session, parent,versionHistory.getId());
        if(!versionHistory.isVersionHistoryNode())
        {
            throw new IllegalArgumentException("The node is not a versionhistory node");
        }
    }
    public BaseNode getVersionHistoryBase()
    {
        return node;
    }
    public BaseNode[] getVersions() throws SWBException
    {
        return node.getVersions();
    }
    public String getVersionableUUID() throws RepositoryException
    {
        try
        {
            return node.getUUID();
        }
        catch(SWBException swbe)
        {
            throw new RepositoryException(swbe);
        }
    }
    public Version getRootVersion() throws RepositoryException
    {
        Iterator<BaseNode> nodes=this.node.listNodes();
        while(nodes.hasNext())
        {
            BaseNode child=nodes.next();
            if(child.getName().equals("jcr:rootVersion"))
            {
                return new VersionImp(child, node, session, parent);
            }
        }
        throw new RepositoryException("The root version was not found");
    }

    public VersionIterator getAllVersions() throws RepositoryException
    {        
        try
        {
            return new VersionIteratorImp(this,session,parent);
        }
        catch(SWBException swbe)
        {
            throw new RepositoryException(swbe);
        }
    }

    public Version getVersion(String name) throws VersionException, RepositoryException
    {
        Iterator<BaseNode> nodes=this.node.listNodes();
        while(nodes.hasNext())
        {
            BaseNode child=nodes.next();
            if(child.getName().equals(name) && child.getSemanticObject().getSemanticClass().equals(BaseNode.vocabulary.nt_Version))
            {
                return new VersionImp(child, node, session, parent);
            }
        }
        throw new RepositoryException("The version "+ name +" was not found");
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
