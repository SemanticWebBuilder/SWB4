/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.jcr283.implementation;

import java.util.UUID;
import javax.jcr.AccessDeniedException;
import javax.jcr.NodeIterator;
import javax.jcr.ReferentialIntegrityException;
import javax.jcr.RepositoryException;
import javax.jcr.UnsupportedRepositoryOperationException;
import javax.jcr.nodetype.NoSuchNodeTypeException;
import javax.jcr.version.LabelExistsVersionException;
import javax.jcr.version.Version;
import javax.jcr.version.VersionException;
import javax.jcr.version.VersionHistory;
import javax.jcr.version.VersionIterator;

/**
 *
 * @author victor.lorenzana
 */
public class VersionHistoryImp extends NodeImp implements VersionHistory {

    private final NodeImp versionableNode;
    
    public VersionHistoryImp(org.semanticwb.jcr283.repository.model.VersionHistory vh, NodeImp parent, int index, String path, int depth, SessionImp session) throws RepositoryException
    {
        super(vh, parent, index, path, depth, session);
        PropertyImp prop=nodeManager.getProperty(this.getPathFromName("jcr:versionableUuid"));
        if(prop.getLength()==-1)
        {
            throw new RepositoryException("The versionableUuid property was not found");
        }
        String id=prop.getString();
        versionableNode=(NodeImp)session.getNodeByIdentifier(id);
    }
    public VersionHistoryImp(NodeDefinitionImp nodeDefinition, NodeImp parent, SessionImp session,NodeImp versionableNode) throws NoSuchNodeTypeException,RepositoryException
    {
        super(SWBRepository.getNodeTypeManagerImp().getNodeTypeImp("nt:versionHistory"), nodeDefinition, "jcr:versionHistory", parent, 0, parent.path+PATH_SEPARATOR+"jcr:versionHistory", parent.getDepth()+1, session, UUID.randomUUID().toString());
        this.versionableNode=versionableNode;
    
    }       

    public String getVersionableUUID() throws RepositoryException
    {
        return getVersionableIdentifier();
    }

    public String getVersionableIdentifier() throws RepositoryException
    {
        return versionableNode.getIdentifier();
    }

    public Version getRootVersion() throws RepositoryException
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public VersionIterator getAllLinearVersions() throws RepositoryException
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public VersionIterator getAllVersions() throws RepositoryException
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public NodeIterator getAllLinearFrozenNodes() throws RepositoryException
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public NodeIterator getAllFrozenNodes() throws RepositoryException
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Version getVersion(String versionName) throws VersionException, RepositoryException
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Version getVersionByLabel(String label) throws VersionException, RepositoryException
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void addVersionLabel(String versionName, String label, boolean moveLabel) throws LabelExistsVersionException, VersionException, RepositoryException
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void removeVersionLabel(String label) throws VersionException, RepositoryException
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public boolean hasVersionLabel(String label) throws RepositoryException
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public boolean hasVersionLabel(Version version, String label) throws VersionException, RepositoryException
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public String[] getVersionLabels() throws RepositoryException
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public String[] getVersionLabels(Version version) throws VersionException, RepositoryException
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void removeVersion(String versionName) throws ReferentialIntegrityException, AccessDeniedException, UnsupportedRepositoryOperationException, VersionException, RepositoryException
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
