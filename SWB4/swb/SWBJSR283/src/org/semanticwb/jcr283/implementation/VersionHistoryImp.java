/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.jcr283.implementation;

import java.util.HashSet;
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
import org.semanticwb.jcr283.repository.model.Base;
import org.semanticwb.model.GenericIterator;

/**
 *
 * @author victor.lorenzana
 */
public class VersionHistoryImp extends NodeImp implements VersionHistory
{
    private static final String JCR_PREDECESSORS = "jcr:predecessors";

    private static final String JCR_ROOT_VERSION_NAME = "jcr:rootVersion";
    private static final String JCR_SUCCESSORS = "jcr:successors";
    private final NodeImp versionableNode;
    private VersionImp jcr_rootVersion;

    protected VersionHistoryImp(org.semanticwb.jcr283.repository.model.VersionHistory vh, NodeImp parent, int index, String path, int depth, SessionImp session) throws RepositoryException
    {
        super(vh, parent, index, path, depth, session);
        PropertyImp prop = nodeManager.getProtectedProperty(this.getPathFromName("jcr:versionableUuid"));
        if (prop.getLength() == -1)
        {
            throw new RepositoryException("The versionableUuid property was not found");
        }
        String versionableId = prop.getString();
        versionableNode = (NodeImp) session.getNodeByIdentifier(versionableId);
        GenericIterator<Base> childs = vh.listNodes();
        while (childs.hasNext())
        {
            Base child = childs.next();
            if (child.getName().equals(JCR_ROOT_VERSION_NAME))
            {
                String childPath = getPathFromName(JCR_ROOT_VERSION_NAME);
                org.semanticwb.jcr283.repository.model.Version versionNode = new org.semanticwb.jcr283.repository.model.Version(child.getSemanticObject());
                jcr_rootVersion = new VersionImp(versionNode, this, this.getIndex() + 1, childPath, this.depth + 1, session);
                nodeManager.addNode(jcr_rootVersion, childPath, path);
                break;
            }
        }
        if (jcr_rootVersion == null)
        {
            String path_jcr_rootVersion = this.getPathFromName(JCR_ROOT_VERSION_NAME);
            if (!nodeManager.hasNode(path_jcr_rootVersion))
            {
                jcr_rootVersion = (VersionImp) this.insertNode(JCR_ROOT_VERSION_NAME);
            }
            else
            {
                jcr_rootVersion = (VersionImp) nodeManager.getProtectedNode(path_jcr_rootVersion, session);
            }
        }

    }

    public VersionHistoryImp(NodeDefinitionImp nodeDefinition, NodeImp parent, SessionImp session, NodeImp versionableNode) throws NoSuchNodeTypeException, RepositoryException
    {
        super(SWBRepository.getNodeTypeManagerImp().getNodeTypeImp("nt:versionHistory"), nodeDefinition, "jcr:versionHistory", parent, 0, parent.path + PATH_SEPARATOR + "jcr:versionHistory", parent.getDepth() + 1, session, UUID.randomUUID().toString(),true);
        this.versionableNode = versionableNode;
        String path_jcr_rootVersion = this.getPathFromName(JCR_ROOT_VERSION_NAME);
        if (!nodeManager.hasNode(path_jcr_rootVersion))
        {
            jcr_rootVersion = (VersionImp) this.insertNode(JCR_ROOT_VERSION_NAME);
        }
        else
        {
            jcr_rootVersion = (VersionImp) nodeManager.getProtectedNode(path_jcr_rootVersion, session);
        }
        String pathBaseVersion = versionableNode.getPathFromName("jcr:baseVersion");
        PropertyImp prop = nodeManager.getProtectedProperty(pathBaseVersion);
        if (prop.getLength() == -1)
        {
            prop.set(new ValueFactoryImp().createValue(jcr_rootVersion));
        }
        session.getWorkspaceImp().getVersionManagerImp().setBaseVersion(jcr_rootVersion, versionableNode.path);

    }


    NodeImp insertVersionNode(String nameToAdd) throws RepositoryException
    {
       VersionImp newversion=(VersionImp)this.insertNode(nameToAdd, null);
       newversion.init(versionableNode);
       VersionImp baseverion=this.versionableNode.getBaseVersionImp();


       PropertyImp jcr_predecessors=nodeManager.getProtectedProperty(newversion.getPathFromName(JCR_PREDECESSORS));
       jcr_predecessors.addValue(valueFactoryImp.createValue(baseverion));
       
       PropertyImp jcr_successors=nodeManager.getProtectedProperty(baseverion.getPathFromName(JCR_SUCCESSORS));
       jcr_successors.addValue(valueFactoryImp.createValue(newversion));

       return newversion;
    }
    @Deprecated
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
        return jcr_rootVersion;
    }

    public VersionIterator getAllLinearVersions() throws RepositoryException
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public VersionIterator getAllVersions() throws RepositoryException
    {
        HashSet<VersionImp> getAllVersions=new HashSet<VersionImp>();
        nodeManager.loadChilds(this, session, false);
        for(NodeImp node : nodeManager.getProtectedChildNodes(path))
        {
            getAllVersions.add((VersionImp)node);
        }
        return new VersionIteratorImp(getAllVersions);
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
