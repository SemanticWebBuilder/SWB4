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
public class VersionImp extends NodeImp implements Version
{    
    private final BaseNode history;
    VersionImp(BaseNode version,BaseNode history,SessionImp session)
    {
        super(version, session);
        if(!version.isVersionNode())
        {
            throw new IllegalArgumentException("The node is not a version node");
        }
        if(!history.isVersionHistoryNode())
        {
            throw new IllegalArgumentException("The node is not a version history");
        }        
        this.history=history;
        
    }
    public VersionHistory getContainingHistory() throws RepositoryException
    {        
        return new VersionHistoryImp(history,session);
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
                VersionImp version=new VersionImp(versionNode, history, session);
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
                VersionImp version=new VersionImp(versionNode, history, session);
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
