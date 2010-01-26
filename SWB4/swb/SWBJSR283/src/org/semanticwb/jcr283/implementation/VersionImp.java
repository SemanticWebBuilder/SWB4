/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.jcr283.implementation;

import java.util.Calendar;
import java.util.UUID;
import javax.jcr.Node;
import javax.jcr.RepositoryException;
import javax.jcr.nodetype.NoSuchNodeTypeException;
import javax.jcr.version.Version;
import javax.jcr.version.VersionHistory;

/**
 *
 * @author victor.lorenzana
 */
public class VersionImp extends NodeImp implements Version {

    public VersionImp(org.semanticwb.jcr283.repository.model.Version version, NodeImp parent, int index, String path, int depth, SessionImp session)
    {
        super(version, parent, index, path, depth, session);
    }
    public VersionImp(NodeDefinitionImp nodeDefinition, String name, NodeImp parent, int index, String path, int depth, SessionImp session, String id) throws NoSuchNodeTypeException,RepositoryException
    {
        super(SWBRepository.getNodeTypeManagerImp().getNodeTypeImp("nt:version"), nodeDefinition, name, parent, index, path, depth, session, UUID.randomUUID().toString());
    }

    public VersionHistory getContainingHistory() throws RepositoryException
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Calendar getCreated() throws RepositoryException
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Version getLinearSuccessor() throws RepositoryException
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Version[] getSuccessors() throws RepositoryException
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Version getLinearPredecessor() throws RepositoryException
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Version[] getPredecessors() throws RepositoryException
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Node getFrozenNode() throws RepositoryException
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
