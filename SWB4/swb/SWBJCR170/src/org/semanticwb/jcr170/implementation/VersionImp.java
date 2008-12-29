/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.jcr170.implementation;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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

    VersionImp(BaseNode version, VersionHistoryImp historyNode, SessionImp session) throws RepositoryException
    {
        super(version, session);
        if (!version.isVersionNode())
        {
            throw new IllegalArgumentException("The node is not a version node");
        }
        this.history = historyNode.node;
        if (!history.isVersionHistoryNode())
        {
            throw new IllegalArgumentException("The node is not a version history");
        }
        this.historyNode = historyNode;
        this.parent = historyNode;

    }

    public VersionHistory getContainingHistory() throws RepositoryException
    {
        return historyNode;
    }

    public Calendar getCreated() throws RepositoryException
    {
        org.semanticwb.repository.Version version = new org.semanticwb.repository.Version(node.getSemanticObject());
        Date date = version.getCreated();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal;
    }

    public Version[] getSuccessors() throws RepositoryException
    {
        org.semanticwb.repository.Version versionNode = new org.semanticwb.repository.Version(node.getSemanticObject());

        try
        {
            ArrayList<Version> versions = new ArrayList<Version>();
            for (BaseNode baseNode : versionNode.getBaseSuccessors())
            {
                VersionImp version = new VersionImp(baseNode, historyNode, session);
                versions.add(version);
            }
            return versions.toArray(new Version[versions.size()]);
        }
        catch (SWBException swbe)
        {
            throw new RepositoryException(swbe);
        }
    }

    public Version[] getPredecessors() throws RepositoryException
    {
        org.semanticwb.repository.Version versionNode = new org.semanticwb.repository.Version(node.getSemanticObject());
        try
        {
            ArrayList<Version> versions = new ArrayList<Version>();
            for (BaseNode baseNode : versionNode.getBasePredecessors())
            {
                VersionImp version = new VersionImp(baseNode, historyNode, session);
                versions.add(version);
            }
            return versions.toArray(new Version[versions.size()]);
        }
        catch (SWBException swbe)
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
