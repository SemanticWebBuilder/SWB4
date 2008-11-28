/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.jcr170.implementation;

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
    VersionHistoryImp(BaseNode node,SessionImp session,SimpleNode parent) throws RepositoryException
    {
        super(node, session, parent,node.getId());
        if(!node.isVersionHistoryNode())
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
        throw new UnsupportedOperationException("Not supported yet.");
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

    public Version getVersion(String arg0) throws VersionException, RepositoryException
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Version getVersionByLabel(String arg0) throws RepositoryException
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
