/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.jcr283.implementation;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import javax.jcr.RepositoryException;
import javax.jcr.UnsupportedRepositoryOperationException;
import javax.jcr.nodetype.InvalidNodeTypeDefinitionException;
import javax.jcr.nodetype.NoSuchNodeTypeException;
import javax.jcr.nodetype.NodeDefinitionTemplate;
import javax.jcr.nodetype.NodeType;
import javax.jcr.nodetype.NodeTypeDefinition;
import javax.jcr.nodetype.NodeTypeExistsException;
import javax.jcr.nodetype.NodeTypeIterator;
import javax.jcr.nodetype.NodeTypeManager;
import javax.jcr.nodetype.NodeTypeTemplate;
import javax.jcr.nodetype.PropertyDefinitionTemplate;
import org.semanticwb.Logger;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBUtils;
import org.semanticwb.jcr283.repository.model.NodeTypes;
import org.semanticwb.platform.SemanticClass;

/**
 *
 * @author victor.lorenzana
 */
public class NodeTypeManagerImp implements NodeTypeManager
{

    private static Logger log = SWBUtils.getLogger(NodeTypeManagerImp.class);
    private static final HashMap<String, NodeTypeImp> types = new HashMap<String, NodeTypeImp>();

    static
    {
        loadNodeTypes();
    }

    public static NodeTypeImp loadNodeType(SemanticClass clazz)
    {

        if (clazz.isSubClass(NodeTypes.sclass) || clazz.equals(NodeTypes.sclass))
        {
            String name = clazz.getPrefix() + ":" + clazz.getName();
            NodeTypeImp nodeType = null;
            if (!types.containsKey(name))
            {
                log.trace("Loading nodetype ... " + clazz.getURI() + " ...");
                nodeType = new NodeTypeImp(clazz);
                types.put(name, nodeType);
                nodeType.loadPropertyDefinitions();
                nodeType.loadChildNodeDefinitions();
                nodeType.loadSuperTypes();
                nodeType.loadSubTypes();
            }
            nodeType = types.get(name);
            return nodeType;
        }
        return null;
    }

    private static void loadNodeTypes()
    {
        log.trace("Loading nodetypes...");
        Iterator<SemanticClass> classes = SWBPlatform.getSemanticMgr().getVocabulary().listSemanticClasses();
        while (classes.hasNext())
        {
            SemanticClass clazz = classes.next();            
            loadNodeType(clazz);
        }
    }

    public NodeType getNodeType(String nodeTypeName) throws NoSuchNodeTypeException, RepositoryException
    {
        return types.get(nodeTypeName);
    }

    public NodeTypeImp getNodeTypeImp(String nodeTypeName) throws NoSuchNodeTypeException, RepositoryException
    {
        return types.get(nodeTypeName);
    }

    public boolean hasNodeType(String name) throws RepositoryException
    {
        return types.get(name) == null ? false : true;
    }

    public NodeTypeIterator getAllNodeTypes() throws RepositoryException
    {
        HashSet<NodeTypeImp> nodes = new HashSet<NodeTypeImp>();
        for (NodeTypeImp nodeType : types.values())
        {
            nodes.add(nodeType);
        }
        return new NodeTypeIteratorImp(nodes);
    }

    public NodeTypeIterator getPrimaryNodeTypes() throws RepositoryException
    {
        HashSet<NodeTypeImp> nodes = new HashSet<NodeTypeImp>();
        for (NodeTypeImp nodeType : types.values())
        {
            if (!nodeType.isMixin())
            {
                nodes.add(nodeType);
            }
        }
        return new NodeTypeIteratorImp(nodes);
    }

    public NodeTypeIterator getMixinNodeTypes() throws RepositoryException
    {
        HashSet<NodeTypeImp> nodes = new HashSet<NodeTypeImp>();
        for (NodeTypeImp nodeType : types.values())
        {
            if (nodeType.isMixin())
            {
                nodes.add(nodeType);
            }
        }
        return new NodeTypeIteratorImp(nodes);
    }

    public NodeTypeTemplate createNodeTypeTemplate() throws UnsupportedRepositoryOperationException, RepositoryException
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public NodeTypeTemplate createNodeTypeTemplate(NodeTypeDefinition ntd) throws UnsupportedRepositoryOperationException, RepositoryException
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public NodeDefinitionTemplate createNodeDefinitionTemplate() throws UnsupportedRepositoryOperationException, RepositoryException
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public PropertyDefinitionTemplate createPropertyDefinitionTemplate() throws UnsupportedRepositoryOperationException, RepositoryException
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public NodeType registerNodeType(NodeTypeDefinition ntd, boolean allowUpdate) throws InvalidNodeTypeDefinitionException, NodeTypeExistsException, UnsupportedRepositoryOperationException, RepositoryException
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public NodeTypeIterator registerNodeTypes(NodeTypeDefinition[] ntds, boolean allowUpdate) throws InvalidNodeTypeDefinitionException, NodeTypeExistsException, UnsupportedRepositoryOperationException, RepositoryException
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void unregisterNodeType(String name) throws UnsupportedRepositoryOperationException, NoSuchNodeTypeException, RepositoryException
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void unregisterNodeTypes(String[] names) throws UnsupportedRepositoryOperationException, NoSuchNodeTypeException, RepositoryException
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
