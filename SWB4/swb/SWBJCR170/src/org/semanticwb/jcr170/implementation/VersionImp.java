/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.jcr170.implementation;

import java.util.ArrayList;
import java.util.Calendar;
import javax.jcr.RepositoryException;
import javax.jcr.version.Version;
import javax.jcr.version.VersionHistory;
import org.semanticwb.SWBException;
import org.semanticwb.repository.BaseNode;

/**
 *
 * @author victor.lorenzana
 */
public class VersionImp extends SimpleNode implements Version
{    
    private final BaseNode history;
    private final VersionHistoryImp historyNode;
    VersionImp(BaseNode version,VersionHistoryImp historyNode,SessionImp session) throws RepositoryException
    {
        super(version, session,historyNode,version.getId());
        if(!version.isVersionNode())
        {
            throw new IllegalArgumentException("The node is not a version node");
        }
        this.history=historyNode.node;
        if(!history.isVersionHistoryNode())
        {
            throw new IllegalArgumentException("The node is not a version history");
        }
        this.historyNode=historyNode;
        
    }
    public VersionHistory getContainingHistory() throws RepositoryException
    {        
        return historyNode;
    }

    public Calendar getCreated() throws RepositoryException
    {
        try
        {
            return node.getCreated();
        }
        catch(SWBException swbe)
        {
            throw new RepositoryException(swbe);
        }
    }

    public Version[] getSuccessors() throws RepositoryException
    {
        try
        {
            ArrayList<Version> versions=new ArrayList<Version>();
            for(BaseNode versionNode : node.getSuccessors())
            {
                VersionImp version=new VersionImp(versionNode, historyNode, session);
                versions.add(version);
            }
            return versions.toArray(new Version[versions.size()]);
        }
        catch(SWBException swbe)
        {
            throw new RepositoryException(swbe);
        }
    }

    public Version[] getPredecessors() throws RepositoryException
    {
        try
        {
            ArrayList<Version> versions=new ArrayList<Version>();
            for(BaseNode versionNode : node.getPredecessors())
            {
                VersionImp version=new VersionImp(versionNode, historyNode, session);
                versions.add(version);
            }
            return versions.toArray(new Version[versions.size()]);
        }
        catch(SWBException swbe)
        {
            throw new RepositoryException(swbe);
        }
    }

    
    @Override
    public String toString()
    {
        return node.getName();
    }
}
