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
import java.util.Hashtable;
import java.util.Iterator;
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
    private final static Logger log = SWBUtils.getLogger(NodeImp.class);
    private final static NodeTypeManagerImp nodeTypeManager = new NodeTypeManagerImp();
    private final static ValueFactoryImp valueFactoryImp = new ValueFactoryImp();
    private final NodeDefinitionImp nodeDefinitionImp;
    private final Hashtable<String, PropertyImp> properties = new Hashtable<String, PropertyImp>();
    private SemanticObject obj = null;
    private final int index;

    NodeImp(Base base, NodeImp parent, int index, String path, int depth, SessionImp session)
    {
        this(base.getSemanticObject(), base.getName(), parent, index, path, depth, session);
    }

    NodeImp(NodeDefinitionImp nodeDefinition, String name, NodeImp parent, int index, String path, int depth, SessionImp session)
    {
        super(null, name, parent, path, depth, session);
        this.index = index;
        this.nodeDefinitionImp = nodeDefinition;
        loadProperties();
    }

    NodeImp(SemanticObject obj, String name, NodeImp parent, int index, String path, int depth, SessionImp session)
    {
        super(obj, name, parent, path, depth, session);
        this.obj = obj;
        this.index = index;
        nodeDefinitionImp = new NodeDefinitionImp(obj, NodeTypeManagerImp.loadNodeType(obj.getSemanticClass()));
        loadProperties();
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
                        PropertyImp prop = new PropertyImp(semanticProperty, this, this.getPath() + "/" + semanticProperty.getPrefix() + ":" + semanticProperty.getName(), this.getDepth() + 1, this.session);
                        if (!this.properties.containsKey(prop.getName()))
                        {
                            log.debug("loading property " + semanticProperty.getURI() + " for node " + obj.getURI());
                            this.properties.put(prop.getName(), prop);
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
                            PropertyImp prop = new PropertyImp(semanticProperty, this, this.getPath() + "/" + semanticProperty.getPrefix() + ":" + semanticProperty.getName(), this.getDepth() + 1, this.session);
                            if (!this.properties.containsKey(prop.getName()))
                            {
                                log.debug("loading property " + semanticProperty.getURI() + " for node " + obj.getURI());
                                this.properties.put(prop.getName(), prop);
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

    private static boolean isValidRelativePath(String relPath)
    {
        if (relPath.startsWith("/"))
        {
            return false;
        }
        return isPathSegment(relPath);
    }

    private static boolean isPathSegment(String pathsegment)
    {
        return true;
    }

    private static String extractName(String relpath)
    {
        String extractName = relpath;
        if (extractName.endsWith("/"))
        {
            extractName = extractName.substring(0, extractName.length() - 1);
        }
        String name = relpath;
        int pos = name.lastIndexOf("/");
        if (pos != -1)
        {
            name = name.substring(pos + 1);
        }
        pos = name.lastIndexOf("[");
        if (pos != -1)
        {
            name = name.substring(0, pos);
        }
        return name;
    }

    private String normalizePath(String relpath) throws RepositoryException
    {
        if (isValidRelativePath(relpath))
        {
            throw new RepositoryException("The path is not relative");
        }
        if (relpath.startsWith("./"))
        {
            String absPath = this.getPath() + relpath;
            return absPath;
        }
        if (relpath.startsWith("../"))
        {
            if (this.parent != null)
            {
                String newRelativePath = relpath.substring(1);
                return this.parent.normalizePath(newRelativePath);
            }
        }
        String newpath = relpath;
        int pos = newpath.indexOf("./");
        while (pos != -1)
        {

            pos = newpath.indexOf("./");
        }

        pos = newpath.indexOf("/../");
        while (pos != -1)
        {
            String end = newpath.substring(pos + 3);
            newpath = newpath.substring(0, pos);
            pos = newpath.lastIndexOf("/");
            newpath = newpath.substring(0, pos + 1);
            newpath += end;
            pos = newpath.indexOf("/../");
        }
        if (newpath.endsWith("/"))
        {
            newpath = newpath.substring(0, newpath.length() - 1);
        }
        return newpath;
    }

    private NodeImp insertNode(String nameToAdd, NodeDefinitionImp childDefinition) throws RepositoryException
    {
        String childpath = this.path + "/" + nameToAdd;
        if (!childDefinition.allowsSameNameSiblings() && this.session.getWorkspaceImp().getNodeManager().hasNode(childpath, false))
        {
            throw new ItemExistsException("There is a node with the same name in the node " + this.path);
        }
        int childIndex = session.getWorkspaceImp().getNodeManager().countNodes(childpath, false);
        if (childIndex > 0)
        {
            childIndex--;
        }
        if (childIndex > 0)
        {
            childpath += "[" + childIndex + "]";
        }
        NodeImp newChild = new NodeImp(childDefinition, nameToAdd, this, index, childpath, this.getDepth() + 1, session);
        return session.getWorkspaceImp().getNodeManager().addNode(newChild, childpath);

    }

    public Node addNode(String relPath, String primaryNodeTypeName) throws ItemExistsException, PathNotFoundException, NoSuchNodeTypeException, LockException, VersionException, ConstraintViolationException, RepositoryException
    {
        if (!isValidRelativePath(relPath))
        {
            //TODO:ERROR
        }
        String absPath = normalizePath(relPath);
        NodeImp nodeParent = this.session.getWorkspaceImp().getNodeManager().getNode(absPath);
        if (nodeParent == null)
        {
            throw new PathNotFoundException("The node with path " + relPath + " was not found");
        }
        NodeTypeImp nodeType = null;
        if (primaryNodeTypeName == null)
        {
            nodeType = nodeParent.nodeDefinitionImp.getDefaultPrimaryTypeImp();
            primaryNodeTypeName = nodeType.getName();
        }
        else
        {
            nodeType = nodeTypeManager.getNodeTypeImp(primaryNodeTypeName);
        }
        if (nodeType == null)
        {
            throw new NoSuchNodeTypeException("The node type was not found");
        }
        String nameToAdd = extractName(relPath);
        if (!nodeType.canAddChildNode(nameToAdd))
        {
            //TODO:ERROR
            throw new ConstraintViolationException("The node can no be added");
        }
        NodeDefinitionImp childDefinition = null;
        for (NodeDefinitionImp childNodeDefinition : nodeType.getChildNodeDefinitionsImp())
        {
            if (childNodeDefinition.getName().equals("*"))
            {
                if (primaryNodeTypeName == null)
                {
                    primaryNodeTypeName = childNodeDefinition.getDefaultPrimaryTypeName();
                }
                if (primaryNodeTypeName != null)
                {
                    String names[] = childNodeDefinition.getRequiredPrimaryTypeNames();
                    for (String name : names)
                    {
                        if (name.equals(primaryNodeTypeName))
                        {
                            childDefinition = childNodeDefinition;
                        }
                    }
                }

            }
            else
            {
                if (childNodeDefinition.getName().equals(primaryNodeTypeName))
                {
                    String names[] = childNodeDefinition.getRequiredPrimaryTypeNames();
                    for (String name : names)
                    {
                        if (name.equals(primaryNodeTypeName))
                        {
                            childDefinition = childNodeDefinition;
                        }
                    }
                }
            }

        }
        if (childDefinition == null)
        {
            return nodeParent.insertNode(nameToAdd, childDefinition);
        }
        else
        {
            throw new ConstraintViolationException("The node can not be added");
        }
    }

    public void orderBefore(String srcChildRelPath, String destChildRelPath) throws UnsupportedRepositoryOperationException, VersionException, ConstraintViolationException, ItemNotFoundException, LockException, RepositoryException
    {
        if (!isValidRelativePath(srcChildRelPath))
        {
        }
        if (!isValidRelativePath(destChildRelPath))
        {
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
            //TODO: ERROR
        }
        NodeImp node = this.session.getWorkspaceImp().getNodeManager().getNode(relPath);
        if (node == null)
        {
            throw new PathNotFoundException("The node with path " + relPath + " was not found");
        }
        return node;
    }

    public NodeIterator getNodes() throws RepositoryException
    {
        this.session.getWorkspaceImp().getNodeManager().loadChilds(this, this.path, depth, session, false);
        NodeIteratorImp iterator = new NodeIteratorImp(this.session.getWorkspaceImp().getNodeManager().getChilds(this));
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
        String name = extractName(relPath);
        PropertyImp prop = this.properties.get(name);
        if (prop == null)
        {
            throw new PathNotFoundException("The property " + relPath + " was not found");
            // TODO: ERROR
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
        NodeType nodeTypePrimaryItem = this.getDefinition().getDefaultPrimaryType();
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public String getUUID() throws UnsupportedRepositoryOperationException, RepositoryException
    {
        throw new UnsupportedOperationException("Not supported yet.");
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
            //TODO:ERROR
        }
        return this.session.getWorkspaceImp().getNodeManager().hasNode(relPath, true);
    }

    public boolean hasProperty(String relPath) throws RepositoryException
    {
        String name = extractName(relPath);
        if (properties.get(name) == null)
        {
            return false;
        }
        return true;
    }

    public boolean hasNodes() throws RepositoryException
    {
        return this.session.getWorkspaceImp().getNodeManager().hasNode(path, true);
    }

    public boolean hasProperties() throws RepositoryException
    {
        if (properties.size() > 0)
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    public NodeType getPrimaryNodeType() throws RepositoryException
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public NodeType[] getMixinNodeTypes() throws RepositoryException
    {
        throw new UnsupportedOperationException("Not supported yet.");
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
        PropertyImp prop = properties.get(JCR_MIXINTYPES);
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
                String name = semanticProperty.getPrefix() + ":" + semanticProperty.getName();
                PropertyImp propMix = new PropertyImp(semanticProperty, this, this.getPath() + "/" + name, this.getDepth() + 1, session);
                properties.put(name, propMix);
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
        PropertyImp prop = properties.get(JCR_MIXINTYPES);
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
                String name = semanticProperty.getPrefix() + ":" + semanticProperty.getName();
                properties.remove(name);
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
        if (properties.containsKey(JCR_MIXINTYPES))
        {
            return false;
        }
        return false;
    }

    public NodeDefinition getDefinition() throws RepositoryException
    {
        return nodeDefinitionImp;
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
