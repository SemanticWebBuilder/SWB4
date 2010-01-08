/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.jcr283.implementation;

import java.util.HashSet;
import java.util.Iterator;
import javax.jcr.RepositoryException;
import javax.jcr.Value;
import javax.jcr.nodetype.NoSuchNodeTypeException;
import javax.jcr.nodetype.NodeDefinition;
import javax.jcr.nodetype.NodeType;
import javax.jcr.nodetype.NodeTypeIterator;
import javax.jcr.nodetype.PropertyDefinition;
import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
import org.semanticwb.jcr283.repository.model.Base;
import org.semanticwb.platform.SemanticClass;


/**
 *
 * @author victor.lorenzana
 */
public class NodeTypeImp implements NodeType
{
    private static Logger log = SWBUtils.getLogger(NodeTypeImp.class);
    private final SemanticClass clazz;
    private PropertyDefinition[] propertyDefinitions;
    private NodeDefinition[] childnodeDefinitions;
    HashSet<NodeType> supertypes=new HashSet<NodeType>();
    private final boolean isMixin;
    private final boolean isQueryable;
    private final boolean isAbstract;
    private final boolean hasOrderableChildNodes;
    public NodeTypeImp(SemanticClass clazz)
    {
        this.clazz = clazz;
        loadPropertyDefinitions();
        loadChildNodeDefinitions();
        loadSuperTypes();
        isMixin=false;
        isQueryable=false;
        isAbstract=false;
        hasOrderableChildNodes=false;
    }
    private void loadSuperTypes()
    {
        log.event("loading super types for "+clazz.getURI());
        NodeTypeManagerImp manager=new NodeTypeManagerImp();
        Iterator<SemanticClass> classes=clazz.listSuperClasses();
        while(classes.hasNext())
        {
            SemanticClass superClazz=classes.next();
            if(superClazz.isSubClass(Base.sclass) || superClazz.equals(Base.sclass))            
            {
                try
                {
                    manager.getNodeType(superClazz.getPrefix()+":"+superClazz.getName());
                }
                catch(NoSuchNodeTypeException nsnte)
                {
                    NodeTypeManagerImp.loadNodeType(clazz);
                }
                catch(RepositoryException re)
                {
                    log.error(re);
                }
            }
        }

    }
    private void loadPropertyDefinitions()
    {
        log.event("loading propertyDefinitions for "+clazz.getURI());
    }
    private void loadChildNodeDefinitions()
    {
        log.event("loading nodeDefinitions for "+clazz.getURI());
    }
    public NodeType[] getSupertypes()
    {
        return supertypes.toArray(new NodeType[supertypes.size()]);
    }

    public NodeType[] getDeclaredSupertypes()
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public NodeTypeIterator getSubtypes()
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public NodeTypeIterator getDeclaredSubtypes()
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public boolean isNodeType(String nodeTypeName)
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public PropertyDefinition[] getPropertyDefinitions()
    {
        return propertyDefinitions;
    }

    public NodeDefinition[] getChildNodeDefinitions()
    {
        return childnodeDefinitions;
    }

    public boolean canSetProperty(String propertyName, Value value)
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public boolean canSetProperty(String propertyName, Value[] values)
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public boolean canAddChildNode(String childNodeName)
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public boolean canAddChildNode(String childNodeName, String nodeTypeName)
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public boolean canRemoveItem(String itemName)
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public boolean canRemoveNode(String nodeName)
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public boolean canRemoveProperty(String propertyName)
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public String getName()
    {
        return clazz.getPrefix()+":"+clazz.getName();
    }

    public String[] getDeclaredSupertypeNames()
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public boolean isAbstract()
    {
        return isAbstract;
    }

    public boolean isMixin()
    {
        return isMixin;
    }

    public boolean hasOrderableChildNodes()
    {
        return hasOrderableChildNodes;
    }

    public boolean isQueryable()
    {
        return isQueryable;
    }

    public String getPrimaryItemName()
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public PropertyDefinition[] getDeclaredPropertyDefinitions()
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public NodeDefinition[] getDeclaredChildNodeDefinitions()
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
