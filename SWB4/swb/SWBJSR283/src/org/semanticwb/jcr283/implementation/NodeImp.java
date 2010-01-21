/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.jcr283.implementation;

import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Iterator;
import java.util.UUID;
import javax.jcr.AccessDeniedException;
import javax.jcr.Binary;
import javax.jcr.InvalidItemStateException;
import javax.jcr.InvalidLifecycleTransitionException;
import javax.jcr.Item;
import javax.jcr.ItemExistsException;
import javax.jcr.ItemNotFoundException;
import javax.jcr.MergeException;
import javax.jcr.NoSuchWorkspaceException;
import javax.jcr.Node;
import javax.jcr.NodeIterator;
import javax.jcr.PathNotFoundException;
import javax.jcr.Property;
import javax.jcr.PropertyIterator;
import javax.jcr.ReferentialIntegrityException;
import javax.jcr.RepositoryException;
import javax.jcr.UnsupportedRepositoryOperationException;
import javax.jcr.Value;
import javax.jcr.ValueFormatException;
import javax.jcr.lock.Lock;
import javax.jcr.lock.LockException;
import javax.jcr.nodetype.ConstraintViolationException;
import javax.jcr.nodetype.NoSuchNodeTypeException;
import javax.jcr.nodetype.NodeDefinition;
import javax.jcr.nodetype.NodeType;
import javax.jcr.version.ActivityViolationException;
import javax.jcr.version.Version;
import javax.jcr.version.VersionException;
import javax.jcr.version.VersionHistory;
import org.semanticwb.Logger;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBUtils;
import org.semanticwb.jcr283.repository.model.Base;
import org.semanticwb.platform.SemanticClass;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.platform.SemanticProperty;

/**
 *
 * @author victor.lorenzana
 */
public class NodeImp extends ItemImp implements Node
{

    public static final String JCR_MIXINTYPES = "jcr:mixinTypes";
    public static final String JCR_UUID = "jcr:uuid";
    private static final String ALL = "*";
    private final static Logger log = SWBUtils.getLogger(NodeImp.class);
    private final static NodeTypeManagerImp nodeTypeManager = new NodeTypeManagerImp();
    private final static ValueFactoryImp valueFactoryImp = new ValueFactoryImp();
    private final NodeDefinitionImp nodeDefinitionImp;
    private SemanticObject obj = null;
    private final int index;
    private final NodeTypeImp nodeType;
    NodeImp(Base base, NodeImp parent, int index, String path, int depth, SessionImp session)
    {
        this(NodeTypeManagerImp.loadNodeType(base.getSemanticObject().getSemanticClass()),base.getSemanticObject(), base.getName(), parent, index, path, depth, session);
    }

    NodeImp(NodeTypeImp nodeType, NodeDefinitionImp nodeDefinition, String name, NodeImp parent, int index, String path, int depth, SessionImp session)
    {
        super(null, name, parent, path, depth, session);
        this.index = index;
        this.nodeDefinitionImp = nodeDefinition;
        this.isNew = true;
        loadProperties();
        this.nodeType=nodeType;
        try
        {
            String propertyPath = getPathFromName(Property.JCR_PRIMARY_TYPE);
            PropertyImp prop = nodeManager.getProperty(propertyPath);
            prop.set(valueFactoryImp.createValue(nodeType.getName()));
            this.isModified = true;
        }
        catch (Exception e)
        {
            log.error(e);
        }

        try
        {
            if (isReferenceable())
            {
                String id = UUID.randomUUID().toString();
                String propertyPath = getPathFromName(JCR_UUID);
                PropertyImp prop = nodeManager.getProperty(propertyPath);
                prop.set(valueFactoryImp.createValue(id));

            }
        }
        catch (Exception e)
        {
            log.debug(e);
        }

    }

    NodeImp(NodeTypeImp nodeType,SemanticObject obj, String name, NodeImp parent, int index, String path, int depth, SessionImp session)
    {
        super(obj, name, parent, path, depth, session);
        this.obj = obj;
        this.index = index;
        nodeDefinitionImp = new NodeDefinitionImp(obj, NodeTypeManagerImp.loadNodeType(obj.getSemanticClass()));
        loadProperties();
        this.nodeType=nodeType;
    }

    public void saveData() throws AccessDeniedException, ItemExistsException, ConstraintViolationException, InvalidItemStateException, ReferentialIntegrityException, VersionException, LockException, NoSuchNodeTypeException, RepositoryException
    {
        if (obj == null)
        {
            // create new Node
            SemanticClass sclass = this.nodeDefinitionImp.getDeclaringNodeTypeImp().getSemanticClass();
            String id = UUID.randomUUID().toString();
            String workspacename = session.getWorkspaceImp().getName();
            org.semanticwb.jcr283.repository.model.Workspace model = org.semanticwb.jcr283.repository.model.Workspace.ClassMgr.getWorkspace(workspacename);
            obj = model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass).getSemanticObject();
            Base base = new Base(obj);
            base.setName(this.name);
            if (parent != null)
            {
                if (parent.getSemanticObject() == null)
                {
                    // TODO:ERROR
                }
                else
                {
                    base.setParentNode(new Base(parent.getSemanticObject()));
                }
            }
        }
        if (isModified)
        {
        }
        this.isNew = false;
        this.isModified = false;

    }

    public SemanticObject getSemanticObject()
    {
        return obj;
    }

    private void loadProperties()
    {
        if (obj != null)
        {
            // stored properties
            Iterator<SemanticProperty> props = obj.listProperties();
            while (props.hasNext())
            {
                SemanticProperty semanticProperty = props.next();
                SemanticClass repositoryPropertyDefinition = SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass(NamespaceRegistryImp.NAMESPACE_NT + "#RepositoryPropertyDefinition");
                if (semanticProperty.getSemanticObject().getSemanticClass().isSubClass(repositoryPropertyDefinition))
                {
                    try
                    {
                        String nameProperty = semanticProperty.getPrefix() + ":" + semanticProperty.getName();
                        String pathProperty = getPathFromName(nameProperty);
                        PropertyImp prop = new PropertyImp(semanticProperty, this, pathProperty, this.session);
                        if (!nodeManager.hasProperty(prop.path))
                        {
                            log.debug("loading property " + semanticProperty.getURI() + " for node " + obj.getURI());
                            nodeManager.addProperty(prop, prop.path);
                        }
                    }
                    catch (Exception e)
                    {
                        log.error(e);
                    }
                }
            }
            for (PropertyDefinitionImp propDef : this.nodeDefinitionImp.getDeclaringNodeTypeImp().getPropertyDefinitionsImp())
            {
                if (propDef.getSemanticProperty() != null)
                {
                    SemanticClass repositoryPropertyDefinition = SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass(NamespaceRegistryImp.NAMESPACE_NT + "#RepositoryPropertyDefinition");
                    SemanticProperty semanticProperty = propDef.getSemanticProperty();
                    if (semanticProperty.getSemanticObject().getSemanticClass().isSubClass(repositoryPropertyDefinition))
                    {
                        try
                        {
                            PropertyImp prop = new PropertyImp(semanticProperty, this, this.getPath() + PATH_SEPARATOR + semanticProperty.getPrefix() + ":" + semanticProperty.getName(), this.session);
                            if (!nodeManager.hasProperty(prop.path))
                            {
                                log.debug("loading property " + semanticProperty.getURI() + " for node " + obj.getURI());
                                nodeManager.addProperty(prop, prop.path);
                            }
                        }
                        catch (Exception e)
                        {
                            log.error(e);
                        }
                    }
                }
            }
        }
    }

    public Node addNode(String relPath) throws ItemExistsException, PathNotFoundException, VersionException, ConstraintViolationException, LockException, RepositoryException
    {
        return addNode(relPath, null);
    }

    private NodeImp insertNode(String nameToAdd, NodeDefinitionImp childDefinition, NodeTypeImp nodeType) throws RepositoryException
    {
        String childpath = path + PATH_SEPARATOR + nameToAdd;
        if (childpath.endsWith(PATH_SEPARATOR))
        {
            childpath = path + nameToAdd;
        }
        if (!childDefinition.allowsSameNameSiblings() && nodeManager.hasNode(childpath, false))
        {
            throw new ItemExistsException("There is a node with the same name in the node " + this.path);
        }
        int childIndex = nodeManager.countNodes(childpath, false);
        if (childIndex > 0)
        {
            childIndex--;
        }
        if (childIndex > 0)
        {
            childpath += "[" + childIndex + "]";
        }
        NodeImp newChild = new NodeImp(nodeType, childDefinition, nameToAdd, this, index, childpath, this.getDepth() + 1, session);
        this.isModified = true;
        return nodeManager.addNode(newChild, childpath);

    }

    public Node addNode(String relPath, String primaryNodeTypeName) throws ItemExistsException, PathNotFoundException, NoSuchNodeTypeException, LockException, VersionException, ConstraintViolationException, RepositoryException
    {
        if (!isValidRelativePath(relPath))
        {
            throw new RepositoryException(THE_PATH_IS_NOT_RELATIVE + relPath);
        }
        String absPath = normalizePath(relPath);
        NodeImp nodeParent = nodeManager.getNode(absPath);
        if (nodeParent == null)
        {
            throw new PathNotFoundException("The node with path " + relPath + " was not found");
        }
        String nameToAdd = extractName(relPath);
        NodeDefinitionImp childDefinition = null;
        for (NodeDefinitionImp childNodeDefinition : parent.nodeDefinitionImp.getDeclaringNodeTypeImp().getChildNodeDefinitionsImp())
        {
            if (childNodeDefinition.getName().equals(nameToAdd))
            {
                childDefinition = childNodeDefinition;
            }
        }
        if (childDefinition == null)
        {
            for (NodeDefinitionImp childNodeDefinition : parent.nodeDefinitionImp.getDeclaringNodeTypeImp().getChildNodeDefinitionsImp())
            {
                if (childNodeDefinition.getName().equals(ALL))
                {
                    childDefinition = childNodeDefinition;
                }
            }
        }
        if (childDefinition == null)
        {
            throw new ConstraintViolationException("The node can not be added");
        }
        NodeTypeImp nodeType = null;
        if (primaryNodeTypeName == null)
        {
            nodeType = childDefinition.getDefaultPrimaryTypeImp();
            primaryNodeTypeName = nodeType.getName();
        }
        else
        {
            nodeType = nodeTypeManager.getNodeTypeImp(primaryNodeTypeName);
            if (nodeType == null)
            {
                throw new NoSuchNodeTypeException("The NodeType " + primaryNodeTypeName + " was not found");
            }
        }
        boolean isConformToRequired = false;
        for (NodeType required : childDefinition.getRequiredPrimaryTypes())
        {
            if (required.equals(nodeType))
            {
                isConformToRequired = true;
                break;
            }
        }
        if (!isConformToRequired)
        {
            throw new ConstraintViolationException("The NodeType " + primaryNodeTypeName + " is not part of required node types ");
        }
        return nodeParent.insertNode(nameToAdd, childDefinition, nodeType);


    }

    public void orderBefore(String srcChildRelPath, String destChildRelPath) throws UnsupportedRepositoryOperationException, VersionException, ConstraintViolationException, ItemNotFoundException, LockException, RepositoryException
    {
        if (!isValidRelativePath(srcChildRelPath))
        {
            throw new RepositoryException(THE_PATH_IS_NOT_RELATIVE + srcChildRelPath);
        }
        if (!isValidRelativePath(destChildRelPath))
        {
            throw new RepositoryException(THE_PATH_IS_NOT_RELATIVE + destChildRelPath);
        }
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Property setProperty(String name, Value value) throws ValueFormatException, VersionException, LockException, ConstraintViolationException, RepositoryException
    {
        return setProperty(name, value, value.getType());
    }

    public Property setProperty(String name, Value value, int type) throws ValueFormatException, VersionException, LockException, ConstraintViolationException, RepositoryException
    {
        return setProperty(name, new Value[]
                {
                    value
                }, type);
    }

    public Property setProperty(String name, Value[] values) throws ValueFormatException, VersionException, LockException, ConstraintViolationException, RepositoryException
    {
        int type = 0;
        return setProperty(name, values, type);
    }

    public Property setProperty(String name, Value[] values, int type) throws ValueFormatException, VersionException, LockException, ConstraintViolationException, RepositoryException
    {
        if (!this.getDefinition().getDeclaringNodeType().canSetProperty(name, values))
        {
            // TODO: ERROR
            throw new ConstraintViolationException("The node can not set this property");
        }
        Property prop = this.getProperty(name);
        if (prop != null)
        {
            prop.setValue(values);
        }
        return prop;
    }

    public Property setProperty(String name, String[] values) throws ValueFormatException, VersionException, LockException, ConstraintViolationException, RepositoryException
    {
        ArrayList<Value> ovalues = new ArrayList<Value>();
        for (String value : values)
        {
            ovalues.add(valueFactoryImp.createValue(value));
        }
        return setProperty(name, ovalues.toArray(new Value[ovalues.size()]));
    }

    public Property setProperty(String name, String[] values, int type) throws ValueFormatException, VersionException, LockException, ConstraintViolationException, RepositoryException
    {
        ArrayList<Value> ovalues = new ArrayList<Value>();
        for (String value : values)
        {
            ovalues.add(valueFactoryImp.createValue(value));
        }
        return setProperty(name, ovalues.toArray(new Value[ovalues.size()]), type);
    }

    public Property setProperty(String name, String value) throws ValueFormatException, VersionException, LockException, ConstraintViolationException, RepositoryException
    {
        return setProperty(name, valueFactoryImp.createValue(value));
    }

    public Property setProperty(String name, String value, int type) throws ValueFormatException, VersionException, LockException, ConstraintViolationException, RepositoryException
    {
        return setProperty(name, valueFactoryImp.createValue(value), type);
    }

    public Property setProperty(String name, InputStream value) throws ValueFormatException, VersionException, LockException, ConstraintViolationException, RepositoryException
    {
        return setProperty(name, valueFactoryImp.createValue(value));
    }

    public Property setProperty(String name, Binary value) throws ValueFormatException, VersionException, LockException, ConstraintViolationException, RepositoryException
    {
        return setProperty(name, valueFactoryImp.createValue(value));
    }

    public Property setProperty(String name, boolean value) throws ValueFormatException, VersionException, LockException, ConstraintViolationException, RepositoryException
    {
        return setProperty(name, valueFactoryImp.createValue(value));
    }

    public Property setProperty(String name, double value) throws ValueFormatException, VersionException, LockException, ConstraintViolationException, RepositoryException
    {
        return setProperty(name, valueFactoryImp.createValue(value));
    }

    public Property setProperty(String name, BigDecimal value) throws ValueFormatException, VersionException, LockException, ConstraintViolationException, RepositoryException
    {
        return setProperty(name, valueFactoryImp.createValue(value));
    }

    public Property setProperty(String name, long value) throws ValueFormatException, VersionException, LockException, ConstraintViolationException, RepositoryException
    {
        return setProperty(name, valueFactoryImp.createValue(value));
    }

    public Property setProperty(String name, Calendar value) throws ValueFormatException, VersionException, LockException, ConstraintViolationException, RepositoryException
    {
        return setProperty(name, valueFactoryImp.createValue(value));
    }

    public Property setProperty(String name, Node value) throws ValueFormatException, VersionException, LockException, ConstraintViolationException, RepositoryException
    {
        return setProperty(name, valueFactoryImp.createValue(value));
    }

    public Node getNode(String relPath) throws PathNotFoundException, RepositoryException
    {
        if (NodeImp.isValidRelativePath(relPath))
        {
            throw new RepositoryException(THE_PATH_IS_NOT_RELATIVE + relPath);
        }
        NodeImp node = nodeManager.getNode(relPath);
        if (node == null)
        {
            throw new PathNotFoundException("The node with path " + relPath + " was not found");
        }
        return node;
    }

    public NodeIterator getNodes() throws RepositoryException
    {
        nodeManager.loadChilds(this, this.path, depth, session, false);
        NodeIteratorImp iterator = new NodeIteratorImp(nodeManager.getChildNodes(this));
        return iterator;
    }

    public NodeIterator getNodes(String namePattern) throws RepositoryException
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public NodeIterator getNodes(String[] nameGlobs) throws RepositoryException
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Property getProperty(String relPath) throws PathNotFoundException, RepositoryException
    {
        if (!isValidRelativePath(relPath))
        {
            throw new RepositoryException(THE_PATH_IS_NOT_RELATIVE + relPath);
        }
        String pathAbsProperty = normalizePath(relPath);
        PropertyImp prop = nodeManager.getProperty(pathAbsProperty);
        if (prop == null)
        {
            throw new PathNotFoundException();
        }
        return prop;
    }

    public PropertyIterator getProperties() throws RepositoryException
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public PropertyIterator getProperties(String namePattern) throws RepositoryException
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public PropertyIterator getProperties(String[] nameGlobs) throws RepositoryException
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Item getPrimaryItem() throws ItemNotFoundException, RepositoryException
    {
        NodeType nodeType = this.getDefinition().getDefaultPrimaryType();
        String primaryItemName = nodeType.getPrimaryItemName();
        if (primaryItemName != null)
        {
            nodeManager.loadChilds(this, path, depth, session, false);
            String primaryItemNamePath = getPathFromName(primaryItemName);
            NodeImp node = nodeManager.getNode(primaryItemNamePath);
            if (node == null)
            {
                PropertyImp prop = nodeManager.getProperty(primaryItemNamePath);
                if (prop == null)
                {
                    throw new ItemNotFoundException();
                }
                else
                {
                    return prop;
                }
            }
            else
            {
                return node;
            }
        }
        else
        {
            throw new ItemNotFoundException();
        }
    }

    public String getUUID() throws UnsupportedRepositoryOperationException, RepositoryException
    {
        if (!isReferenceable())
        {
            throw new UnsupportedRepositoryOperationException("The node is not referenceable");
        }
        String propPath = getPathFromName(JCR_UUID);
        PropertyImp prop = nodeManager.getProperty(propPath);
        return prop.getString();
    }

    public String getIdentifier() throws RepositoryException
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public int getIndex() throws RepositoryException
    {
        return index;
    }

    public PropertyIterator getReferences() throws RepositoryException
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public PropertyIterator getReferences(String name) throws RepositoryException
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public PropertyIterator getWeakReferences() throws RepositoryException
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public PropertyIterator getWeakReferences(String name) throws RepositoryException
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public boolean hasNode(String relPath) throws RepositoryException
    {
        if (!NodeImp.isValidRelativePath(relPath))
        {
            throw new RepositoryException(THE_PATH_IS_NOT_RELATIVE + relPath);
        }
        return nodeManager.hasNode(relPath, true);
    }

    public boolean hasProperty(String relPath) throws RepositoryException
    {
        if (!isValidRelativePath(relPath))
        {
            throw new RepositoryException(THE_PATH_IS_NOT_RELATIVE + relPath);
        }
        String pathAbsProperty = normalizePath(relPath);
        return nodeManager.hasProperty(pathAbsProperty);
    }

    public boolean hasNodes() throws RepositoryException
    {
        return nodeManager.hasNode(path, true);
    }

    public boolean hasProperties() throws RepositoryException
    {
        return nodeManager.hasProperty(path, false);
    }

    public NodeType getPrimaryNodeType() throws RepositoryException
    {
        return nodeType;
    }

    public NodeType[] getMixinNodeTypes() throws RepositoryException
    {
        HashSet<NodeType> getMixinNodeTypes = new HashSet<NodeType>();
        for (NodeType superNodeType : nodeDefinitionImp.getDeclaringNodeType().getSupertypes())
        {
            if (superNodeType.isMixin())
            {
                getMixinNodeTypes.add(superNodeType);
            }
        }
        PropertyImp prop = nodeManager.getProperty(getPathFromName(JCR_MIXINTYPES));
        for (Value value : prop.getValues())
        {
            String type = value.getString();
            NodeTypeManagerImp nodeTypeManagerImp = new NodeTypeManagerImp();
            try
            {
                NodeTypeImp superNodeType = nodeTypeManagerImp.getNodeTypeImp(type);
                if (superNodeType.isMixin())
                {
                    getMixinNodeTypes.add(superNodeType);
                }
            }
            catch (Exception e)
            {
                log.debug(e);
            }
        }
        return getMixinNodeTypes.toArray(new NodeType[getMixinNodeTypes.size()]);
    }

    public boolean isNodeType(String nodeTypeName) throws RepositoryException
    {
        return nodeTypeManager.hasNodeType(nodeTypeName);
    }

    public void setPrimaryType(String nodeTypeName) throws NoSuchNodeTypeException, VersionException, ConstraintViolationException, LockException, RepositoryException
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void addMixin(String mixinName) throws NoSuchNodeTypeException, VersionException, ConstraintViolationException, LockException, RepositoryException
    {
        NodeTypeManagerImp mg = new NodeTypeManagerImp();
        NodeTypeImp mixNodeType = mg.getNodeTypeImp(mixinName);
        if (!this.canAddMixin(mixinName))
        {
            throw new ConstraintViolationException("The mixin can be added");
        }
        PropertyImp prop = nodeManager.getProperty(getPathFromName(JCR_MIXINTYPES));
        Value[] values = prop.getValues();
        Value newValue = valueFactoryImp.createValue(mixinName);
        Value[] newValues = new Value[values.length + 1];
        int i = 0;
        for (Value value : values)
        {
            newValues[i] = value;
            i++;
        }
        newValues[i] = newValue;
        prop.setValue(newValues);
        for (PropertyDefinitionImp propDef : mixNodeType.getPropertyDefinitionsImp())
        {
            if (propDef.getSemanticProperty() != null)
            {
                SemanticProperty semanticProperty = propDef.getSemanticProperty();
                String nameProperty = semanticProperty.getPrefix() + ":" + semanticProperty.getName();
                String pathProperty = getPathFromName(nameProperty);
                PropertyImp propMix = new PropertyImp(semanticProperty, this, pathProperty, session);
                nodeManager.addProperty(prop, prop.path);
            }
        }
    }

    public void removeMixin(String mixinName) throws NoSuchNodeTypeException, VersionException, ConstraintViolationException, LockException, RepositoryException
    {
        NodeTypeManagerImp mg = new NodeTypeManagerImp();
        NodeTypeImp mixNodeType = mg.getNodeTypeImp(mixinName);
        for (NodeType supertypes : nodeDefinitionImp.getDefaultPrimaryType().getDeclaredSupertypes())
        {
            if (supertypes.equals(mixNodeType))
            {
                throw new ConstraintViolationException("The mix in can not be deleted, the mixin is declared super nodetype");
            }
        }
        PropertyImp prop = nodeManager.getProperty(getPathFromName(JCR_MIXINTYPES));
        Value[] values = prop.getValues();
        HashSet<Value> newValues = new HashSet<Value>();
        for (Value value : values)
        {
            if (!value.getString().equals(mixinName))
            {
                newValues.add(value);
            }
        }
        prop.setValue(newValues.toArray(new Value[newValues.size()]));

        for (PropertyDefinitionImp propDef : mixNodeType.getPropertyDefinitionsImp())
        {
            if (propDef.getSemanticProperty() != null)
            {
                SemanticProperty semanticProperty = propDef.getSemanticProperty();
                String nameProperty = semanticProperty.getPrefix() + ":" + semanticProperty.getName();
                nodeManager.removeProperty(getPathFromName(nameProperty));
            }
        }
    }

    public boolean canAddMixin(String mixinName) throws NoSuchNodeTypeException, RepositoryException
    {
        NodeTypeManagerImp mg = new NodeTypeManagerImp();
        NodeType mixNodeType = mg.getNodeType(mixinName);
        if (!mixNodeType.isMixin())
        {
            throw new NoSuchNodeTypeException("Tne nodeType is not mixin");
        }
        for (NodeType supertypes : nodeDefinitionImp.getDefaultPrimaryType().getSupertypes())
        {
            if (supertypes.equals(mixNodeType))
            {
                return false;
            }
        }
        if (nodeManager.hasProperty(getPathFromName(JCR_MIXINTYPES)))
        {
            return false;
        }
        return false;
    }

    public NodeDefinition getDefinition() throws RepositoryException
    {
        return nodeDefinitionImp;
    }

    private boolean isReferenceable() throws RepositoryException
    {
        for (NodeType nodeType : this.getMixinNodeTypes())
        {
            if (nodeType.getName().equals("mix:referenceable"))
            {
                return true;
            }
        }
        return false;

    }

    private boolean isVersionable() throws RepositoryException
    {
        for (NodeType nodeType : this.getMixinNodeTypes())
        {
            if (nodeType.getName().equals("mix:versionable"))
            {
                return true;
            }
        }
        return false;

    }

    public Version checkin() throws VersionException, UnsupportedRepositoryOperationException, InvalidItemStateException, LockException, RepositoryException
    {

        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void checkout() throws UnsupportedRepositoryOperationException, LockException, ActivityViolationException, RepositoryException
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void doneMerge(Version version) throws VersionException, InvalidItemStateException, UnsupportedRepositoryOperationException, RepositoryException
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void cancelMerge(Version version) throws VersionException, InvalidItemStateException, UnsupportedRepositoryOperationException, RepositoryException
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void update(String srcWorkspace) throws NoSuchWorkspaceException, AccessDeniedException, LockException, InvalidItemStateException, RepositoryException
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public NodeIterator merge(String srcWorkspace, boolean bestEffort) throws NoSuchWorkspaceException, AccessDeniedException, MergeException, LockException, InvalidItemStateException, RepositoryException
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public String getCorrespondingNodePath(String workspaceName) throws ItemNotFoundException, NoSuchWorkspaceException, AccessDeniedException, RepositoryException
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public NodeIterator getSharedSet() throws RepositoryException
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void removeSharedSet() throws VersionException, LockException, ConstraintViolationException, RepositoryException
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void removeShare() throws VersionException, LockException, ConstraintViolationException, RepositoryException
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public boolean isCheckedOut() throws RepositoryException
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void restore(String versionName, boolean removeExisting) throws VersionException, ItemExistsException, UnsupportedRepositoryOperationException, LockException, InvalidItemStateException, RepositoryException
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void restore(Version version, boolean removeExisting) throws VersionException, ItemExistsException, InvalidItemStateException, UnsupportedRepositoryOperationException, LockException, RepositoryException
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void restore(Version version, String relPath, boolean removeExisting) throws PathNotFoundException, ItemExistsException, VersionException, ConstraintViolationException, UnsupportedRepositoryOperationException, LockException, InvalidItemStateException, RepositoryException
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void restoreByLabel(String versionLabel, boolean removeExisting) throws VersionException, ItemExistsException, UnsupportedRepositoryOperationException, LockException, InvalidItemStateException, RepositoryException
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public VersionHistory getVersionHistory() throws UnsupportedRepositoryOperationException, RepositoryException
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Version getBaseVersion() throws UnsupportedRepositoryOperationException, RepositoryException
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Lock lock(boolean isDeep, boolean isSessionScoped) throws UnsupportedRepositoryOperationException, LockException, AccessDeniedException, InvalidItemStateException, RepositoryException
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Lock getLock() throws UnsupportedRepositoryOperationException, LockException, AccessDeniedException, RepositoryException
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void unlock() throws UnsupportedRepositoryOperationException, LockException, AccessDeniedException, InvalidItemStateException, RepositoryException
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public boolean holdsLock() throws RepositoryException
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public boolean isLocked() throws RepositoryException
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void followLifecycleTransition(String transition) throws UnsupportedRepositoryOperationException, InvalidLifecycleTransitionException, RepositoryException
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public String[] getAllowedLifecycleTransistions() throws UnsupportedRepositoryOperationException, RepositoryException
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean isNode()
    {
        return true;
    }
}
