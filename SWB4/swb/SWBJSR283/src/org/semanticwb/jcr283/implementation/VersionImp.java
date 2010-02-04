/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.jcr283.implementation;

import java.util.Calendar;
import java.util.UUID;
import javax.jcr.Node;
import javax.jcr.Property;
import javax.jcr.PropertyIterator;
import javax.jcr.RepositoryException;
import javax.jcr.nodetype.NoSuchNodeTypeException;
import javax.jcr.nodetype.NodeType;
import javax.jcr.version.OnParentVersionAction;
import javax.jcr.version.Version;
import javax.jcr.version.VersionHistory;

/**
 *
 * @author victor.lorenzana
 */
public class VersionImp extends NodeImp implements Version
{

    public VersionImp(org.semanticwb.jcr283.repository.model.Version version, VersionHistoryImp parent, int index, String path, int depth, SessionImp session)
    {
        super(version, parent, index, path, depth, session);
    }

    public VersionImp(NodeDefinitionImp nodeDefinition, String name, VersionHistoryImp parent, int index, String path, int depth, SessionImp session, String id) throws NoSuchNodeTypeException, RepositoryException
    {
        super(SWBRepository.getNodeTypeManagerImp().getNodeTypeImp("nt:version"), nodeDefinition, name, parent, index, path, depth, session, id,true);

    }

    public void init(NodeImp target) throws RepositoryException
    {
        createFrozenNode(target);
    }

    private void createFrozenNode(NodeImp target) throws RepositoryException
    {
        NodeImp frozenNode = this.insertNode("jcr:frozenNode");
        initFrozenNode(frozenNode, target);
    }

    private void initFrozenNode(NodeImp frozenNode, NodeImp target) throws RepositoryException
    {
        PropertyImp jcr_frozenPrimaryType = nodeManager.getProtectedProperty(frozenNode.getPathFromName("jcr:frozenPrimaryType"));
        if (jcr_frozenPrimaryType.getLength() == -1)
        {
            jcr_frozenPrimaryType.set(valueFactoryImp.createValue(target.getPrimaryNodeType().getName()));
        }
        PropertyImp jcr_frozenMixinTypes = nodeManager.getProtectedProperty(frozenNode.getPathFromName("jcr:frozenMixinTypes"));
        if (jcr_frozenMixinTypes.getLength() == -1)
        {
            for (NodeType mixinNodeType : target.getMixinNodeTypes())
            {
                jcr_frozenMixinTypes.addValue(valueFactoryImp.createValue(mixinNodeType.getName()));
            }
        }
        PropertyImp jcr_frozenUuid = nodeManager.getProtectedProperty(frozenNode.getPathFromName("jcr:frozenUuid"));
        if (jcr_frozenUuid.getLength() == -1)
        {
            jcr_frozenUuid.set(valueFactoryImp.createValue(target.id));
        }
        copyNodes(frozenNode, target);
        copyProperties(frozenNode, target);
    }

    private void copyNodes(NodeImp frozenNode, NodeImp target) throws RepositoryException
    {
        nodeManager.loadChilds(target, session, false);
        for (NodeImp child : nodeManager.getChildNodes(target))
        {
            int onParentVersion = child.definition.getOnParentVersion();
            switch (onParentVersion)
            {
                case OnParentVersionAction.VERSION:
                    NodeImp childFrozenNode = frozenNode.insertNode(child.getName());
                    initFrozenNode(childFrozenNode, child);
                    break;
                case OnParentVersionAction.ABORT:
                    throw new RepositoryException("The definition of child " + child.path + " is abort");                
            }
        }
    }

    private void copyProperties(NodeImp frozenNode, NodeImp target) throws RepositoryException
    {
        PropertyIterator props = target.getProperties();
        while (props.hasNext())
        {
            Property prop = props.nextProperty();
            int onParentVersion = prop.getDefinition().getOnParentVersion();
            switch (onParentVersion)
            {
                case OnParentVersionAction.VERSION:
                    frozenNode.setProperty(prop.getName(), prop.getValues());
                    break;
                case OnParentVersionAction.ABORT:
                    throw new RepositoryException("The definition of propert " + prop.getPath() + " is abort");                
            }            
        }
    }

    public VersionHistory getContainingHistory() throws RepositoryException
    {
        return (VersionHistory) parent;
    }

    public Calendar getCreated() throws RepositoryException
    {
        PropertyImp created = nodeManager.getProtectedProperty(getPathFromName("jcr:created"));
        return created.getDate();
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
        nodeManager.loadChilds(this, session, false);
        return nodeManager.getNode(getPathFromName("jcr:frozenNode"));
    }
}
