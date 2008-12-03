/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.jcr170.implementation;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.UUID;
import javax.jcr.AccessDeniedException;
import javax.jcr.InvalidItemStateException;
import javax.jcr.Item;
import javax.jcr.ItemExistsException;
import javax.jcr.ItemNotFoundException;
import javax.jcr.ItemVisitor;
import javax.jcr.MergeException;
import javax.jcr.NoSuchWorkspaceException;
import javax.jcr.Node;
import javax.jcr.NodeIterator;
import javax.jcr.PathNotFoundException;
import javax.jcr.Property;
import javax.jcr.PropertyIterator;
import javax.jcr.ReferentialIntegrityException;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.UnsupportedRepositoryOperationException;
import javax.jcr.Value;
import javax.jcr.ValueFormatException;
import javax.jcr.lock.Lock;
import javax.jcr.lock.LockException;
import javax.jcr.nodetype.ConstraintViolationException;
import javax.jcr.nodetype.NoSuchNodeTypeException;
import javax.jcr.nodetype.NodeDefinition;
import javax.jcr.nodetype.NodeType;
import javax.jcr.version.Version;
import javax.jcr.version.VersionException;
import javax.jcr.version.VersionHistory;
import org.semanticwb.Logger;
import org.semanticwb.SWBException;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.GenericIterator;
import org.semanticwb.platform.SemanticClass;
import org.semanticwb.platform.SemanticProperty;
import org.semanticwb.platform.SemanticVocabulary;
import org.semanticwb.repository.BaseNode;

/**
 *
 * @author victor.lorenzana
 */
public class SimpleNode implements Node
{
    static Logger log=SWBUtils.getLogger(SimpleNode.class);
    private String path;
    private String id;
    static final String DEFAULT_PRIMARY_NODE_TYPE_NAME = "nt:unstructured";
    protected static final String NOT_SUPPORTED_YET = "Not supported yet.";
    protected static final String WAS_NOT_FOUND = " was not found";
    private static final String PATH_SEPARATOR = "/";
    private static final ValueFactoryImp factory = new ValueFactoryImp();
    protected BaseNode node;
    private final BaseNode root;
    private final HashSet<SemanticClass> mixins = new HashSet<SemanticClass>();
    private final HashMap<String, SimpleNode> childs = new HashMap<String, SimpleNode>();
    private final ArrayList<SimpleNode> removedchilds = new ArrayList<SimpleNode>();
    private final HashMap<String, PropertyImp> properties = new HashMap<String, PropertyImp>();
    private final String name;
    protected final SemanticClass clazz;
    protected final SessionImp session;
    private final int index;
    protected final SimpleNode parent;

    SimpleNode(SessionImp session, String name, SemanticClass clazz, SimpleNode parent, int index, String id) throws RepositoryException
    {
        this.id = id;
        this.name = name;
        root = session.getRootBaseNode();
        this.clazz = clazz;
        this.session = session;
        this.index = index;
        this.parent = parent;
        if (parent != null)
        {
            if (parent.getPath().equals(PATH_SEPARATOR))
            {
                this.path = parent.getPath() + name;
            }
            else
            {
                this.path = parent.getPath() + PATH_SEPARATOR + name;
            }
        }
        else
        {
            this.path = PATH_SEPARATOR;
        }
        if (parent != null && !parent.childs.containsKey(id))
        {
            parent.childs.put(this.id, this);
        }
        try
        {
            String[] supertypes = root.getSuperTypes(clazz);
            for (String superType : supertypes)
            {
                SemanticClass superTypeClazz = root.getSemanticClass(superType);
                if (superTypeClazz.equals(BaseNode.vocabulary.mix_Referenceable) || superTypeClazz.isSubClass(BaseNode.vocabulary.mix_Referenceable))
                {
                    PropertyImp prop = addProperty(getName(BaseNode.vocabulary.jcr_uuid), superTypeClazz);
                    prop.setValueInternal(UUID.randomUUID().toString());
                }
                if (superTypeClazz.equals(BaseNode.vocabulary.mix_Versionable) || superTypeClazz.isSubClass(BaseNode.vocabulary.mix_Versionable))
                {
                    PropertyImp prop = addProperty(getName(BaseNode.vocabulary.jcr_isCheckedOut), superTypeClazz);
                    prop.setValueInternal(true);
                }
                if (superTypeClazz.equals(BaseNode.vocabulary.mix_Lockable) || superTypeClazz.isSubClass(BaseNode.vocabulary.mix_Lockable))
                {
                    PropertyImp prop = addProperty(getName(BaseNode.vocabulary.jcr_lockOwner), superTypeClazz);
                    prop.setValueInternal(null);
                    prop = addProperty(getName(BaseNode.vocabulary.jcr_lockIsDeep), superTypeClazz);
                    prop.setValueInternal(false);
                }
                if (root.isMixIn(superTypeClazz))
                {
                    mixins.add(superTypeClazz);
                }
            }
        }
        catch (SWBException e)
        {
        }

    }

    SimpleNode(BaseNode node, SessionImp session, SimpleNode parent, String id) throws RepositoryException
    {
        this.id = id;
        if (node == null)
        {
            throw new IllegalArgumentException("The base node is null");
        }
        this.node = node;
        this.session = session;
        if (parent == null)
        {
            root = node;
        }
        else
        {
            root = session.getRootBaseNode();
        }
        this.clazz = node.getSemanticObject().getSemanticClass();
        this.index = 0;
        this.parent = parent;
        if (parent != null)
        {
            if (parent.getPath().equals(PATH_SEPARATOR))
            {
                this.path = parent.getPath() + node.getName();
            }
            else
            {
                this.path = parent.getPath() + PATH_SEPARATOR + node.getName();
            }
        }
        else
        {
            this.path = PATH_SEPARATOR;
        }
        this.name = node.getName();
        Iterator<SemanticClass> classes = node.getSemanticObject().listSemanticClasses();
        while (classes.hasNext())
        {
            SemanticClass parentClazz = classes.next();
            if (node.isMixIn(parentClazz))
            {
                if (parentClazz.equals(BaseNode.vocabulary.mix_Referenceable) || parentClazz.isSubClass(BaseNode.vocabulary.mix_Referenceable))
                {
                    addProperty(getName(BaseNode.vocabulary.jcr_uuid), parentClazz);
                }
                if (parentClazz.equals(BaseNode.vocabulary.mix_Versionable) || parentClazz.isSubClass(BaseNode.vocabulary.mix_Versionable))
                {
                    addProperty(getName(BaseNode.vocabulary.jcr_isCheckedOut), parentClazz);
                }
                if (parentClazz.equals(BaseNode.vocabulary.mix_Lockable) || parentClazz.isSubClass(BaseNode.vocabulary.mix_Lockable))
                {
                    addProperty(getName(BaseNode.vocabulary.jcr_lockOwner), parentClazz);
                    addProperty(getName(BaseNode.vocabulary.jcr_lockIsDeep), parentClazz);

                }
                mixins.add(parentClazz);
            }
        }
        if (parent != null && !parent.childs.containsKey(id))
        {
            parent.childs.put(this.id, this);
        }

        Iterator<SemanticProperty> semanticProperties = clazz.listProperties();
        while (semanticProperties.hasNext())
        {
            SemanticProperty prop = semanticProperties.next();
            if (!node.isInternal(prop))
            {
                try
                {
                    addProperty(prop, node, false, clazz);
                }
                catch (Exception e)
                {
                }
            }
        }

    }

    boolean isDeleted()
    {
        return parent.removedchilds.contains(this);
    }

    private PropertyImp addProperty(SemanticProperty property, BaseNode node, boolean isNew, SemanticClass clazz) throws ValueFormatException, VersionException, LockException, ConstraintViolationException, RepositoryException
    {
        PropertyImp prop = addProperty(property, node, clazz);
        prop.setNew(isNew);
        return prop;

    }

    private PropertyImp addProperty(SemanticProperty property, BaseNode node, SemanticClass clazz) throws ValueFormatException, VersionException, LockException, ConstraintViolationException, RepositoryException
    {
        if (this.properties.containsKey(getName(property)))
        {
            return this.properties.get(getName(property));
        }
        else
        {
            PropertyDefinitionImp propertyDefinition = new PropertyDefinitionImp(session, property);
            PropertyImp prop = new PropertyImp(this, clazz, getName(property), propertyDefinition);
            this.properties.put(prop.getName(), prop);
            return prop;
        }
    }

    private PropertyImp addProperty(String name, SemanticClass clazz) throws RepositoryException
    {
        PropertyImp prop = null;
        if (!this.properties.containsKey(name))
        {
            if (session.getRootBaseNode().existsProperty(clazz, name))
            {
                SemanticProperty property = session.getRootBaseNode().getSemanticProperty(name, clazz);
                PropertyDefinitionImp propertyDefinition = new PropertyDefinitionImp(session, property);
                prop = new PropertyImp(this, clazz, name, propertyDefinition);
                this.properties.put(name, prop);
            }
            else
            {
                PropertyDefinitionImp propertyDefinition = new PropertyDefinitionImp(name);
                prop = new PropertyImp(this, clazz, name, propertyDefinition);
                this.properties.put(name, prop);
            }
        }
        else
        {
            prop = this.properties.get(name);
        }
        return prop;
    }

    private PropertyImp addProperty(String name) throws RepositoryException
    {
        return addProperty(name, clazz);

    }

    String getSimplePath()
    {
        return path;
    }

    public BaseNode getBaseNode()
    {
        return node;
    }

    public void setBaseNode(BaseNode node)
    {
        this.node = node;
    }

    private boolean isParentCheckOut()
    {
        boolean isParentCheckOut = false;
        try
        {
            if (getParent() != null)
            {
                SimpleNode parentNode = this.parent;
                try
                {
                    if (parentNode.isCheckedOut())
                    {
                        isParentCheckOut = true;
                    }
                }
                catch (Exception e)
                {
                }
                if (parentNode.isParentCheckOut())
                {
                    isParentCheckOut = true;
                }
            }
        }
        catch (Exception e)
        {
        }
        return isParentCheckOut;
    }

    public boolean hasProperties() throws RepositoryException
    {
        return false;
    }

    public boolean hasNode(String relPath) throws RepositoryException
    {
        boolean hasNode = false;
        SessionImp.checkRelPath(relPath);
        relPath = SessionImp.normalize(relPath, this);
        Item item = session.getItem(relPath);
        if (item != null && item.isNode())
        {
            hasNode = true;
        }
        return hasNode;
    }

    public boolean hasProperty(String relPath) throws RepositoryException
    {
        boolean hasProperty = false;
        SessionImp.checkRelPath(relPath);
        relPath = SessionImp.normalize(relPath, this);
        Item item = session.getItem(relPath);
        if (item != null && !item.isNode())
        {
            hasProperty = true;
        }
        return hasProperty;
    }

    public boolean isNodeType(String arg0) throws RepositoryException
    {
        return false;
    }

    public void addMixin(String mixinName) throws NoSuchNodeTypeException, VersionException, ConstraintViolationException, LockException, RepositoryException
    {
        session.checksLock(this);
        try
        {
            SemanticClass mixinClazz = root.getSemanticClass(mixinName);
            if (!root.isMixIn(mixinClazz))
            {
                throw new ConstraintViolationException("The mixinName is not a minxin node type");
            }
            if (mixinClazz.equals(BaseNode.vocabulary.mix_Referenceable) || mixinClazz.isSubClass(BaseNode.vocabulary.mix_Referenceable))
            {
                PropertyImp prop = addProperty(getName(BaseNode.vocabulary.jcr_uuid), mixinClazz);
                prop.setValueInternal(UUID.randomUUID().toString());
            }
            if (mixinClazz.equals(BaseNode.vocabulary.mix_Versionable) || mixinClazz.isSubClass(BaseNode.vocabulary.mix_Versionable))
            {
                PropertyImp prop = addProperty(getName(BaseNode.vocabulary.jcr_isCheckedOut), mixinClazz);
                prop.setValueInternal(true);
            }
            if (mixinClazz.equals(BaseNode.vocabulary.mix_Lockable) || mixinClazz.isSubClass(BaseNode.vocabulary.mix_Lockable))
            {
                PropertyImp prop = addProperty(getName(BaseNode.vocabulary.jcr_lockOwner), mixinClazz);
                prop.setValueInternal(null);
                prop = addProperty(getName(BaseNode.vocabulary.jcr_isCheckedOut), mixinClazz);
                prop.setValueInternal(false);
            }

            mixins.add(mixinClazz);
        }
        catch (SWBException e)
        {
            throw new NoSuchNodeTypeException(e);
        }
    }

    public void removeMixin(String mixinName) throws NoSuchNodeTypeException, VersionException, ConstraintViolationException, LockException, RepositoryException
    {
        session.checksLock(this);
        try
        {
            SemanticClass mixin = root.getSemanticClass(mixinName);
            if (!root.isMixIn(mixin))
            {
                throw new ConstraintViolationException("The mixinName is not a minxin node type");
            }
            mixins.remove(mixin);
        }
        catch (SWBException e)
        {
            throw new NoSuchNodeTypeException(e);
        }
    }

    public boolean canAddMixin(String mixinName) throws NoSuchNodeTypeException, RepositoryException
    {
        boolean canAddMixin = true;
        try
        {
            SemanticClass mixin = root.getSemanticClass(mixinName);
            canAddMixin = !mixins.contains(mixin);
        }
        catch (SWBException swb)
        {
            throw new NoSuchNodeTypeException(swb);
        }
        return canAddMixin;
    }

    public NodeDefinition getDefinition() throws RepositoryException
    {
        return null;
    }

    public Property setProperty(String name, Value[] value, int arg2) throws ValueFormatException, VersionException, LockException, ConstraintViolationException, RepositoryException
    {
        session.checksLock(this);
        if (!isCheckedOut())
        {
            throw new RepositoryException("The node can not modify properties in check-in mode");
        }
        PropertyImp property = properties.get(name);
        if (property != null)
        {
            property.setValue(value);
        }
        else
        {
            if (!getPrimaryNodeType().canSetProperty(name, value))
            {
                throw new ConstraintViolationException("The property can not be added");
            }
            property = addProperty(name);
            property.setValue(value);
        }
        return property;
    }

    public boolean isDeepLock()
    {
        boolean isDeepLock = false;
        return isDeepLock;
    }

    Iterator<SimpleNode> getChilds()
    {
        return null;
    }

    public SimpleNode getNodeByName(String name) throws RepositoryException
    {
        for (SimpleNode childNode : getSimpleNodes())
        {
            if (childNode.getName().equals(name))
            {
                return childNode;
            }
        }
        return null;
    }

    public String getId()
    {
        return id;
    }

    public SimpleNode[] getSimpleNodeByName(String name) throws RepositoryException
    {
        ArrayList<SimpleNode> nodes = new ArrayList<SimpleNode>();
        for (SimpleNode childNode : getSimpleNodes())
        {
            if (childNode.name.equals(name))
            {
                nodes.add(childNode);
            }
        }
        return nodes.toArray(new SimpleNode[nodes.size()]);
    }

    public SimpleNode getSimpleNodeById(String id) throws RepositoryException
    {
        for (SimpleNode childNode : getSimpleNodes())
        {
            if (childNode.id.equals(id))
            {
                return childNode;
            }
        }
        return null;
    }

    SimpleNode[] getSimpleNodes() throws RepositoryException
    {
        if (node != null)
        {
            GenericIterator<BaseNode> childNodes = node.listNodes();
            while (childNodes.hasNext())
            {
                BaseNode nodeChild = childNodes.next();
                boolean isDeleted = false;
                for (SimpleNode childNode : this.removedchilds)
                {
                    if (childNode.id.equals(nodeChild.getId()))
                    {
                        isDeleted = true;
                        break;
                    }
                }
                if (!isDeleted)
                {
                    if (!childs.containsKey(nodeChild.getId()))
                    {
                        new SimpleNode(nodeChild, session, this, nodeChild.getId());
                    }
                }

            }
        }
        return childs.values().toArray(new SimpleNode[childs.size()]);
    }

    public NodeIterator getNodes() throws RepositoryException
    {
        return new NodeIteratorImp(session, getSimpleNodes(), childs.size());
    }

    private boolean existsProperty(String name)
    {
        return properties.containsKey(name);
    }

    public Property getProperty(String relPath) throws PathNotFoundException, RepositoryException
    {
        SessionImp.checkRelPath(relPath);
        if (SessionImp.isName(relPath) && this.existsProperty(relPath))
        {
            return properties.get(relPath);
        }
        else
        {
            throw new PathNotFoundException("The property " + relPath + WAS_NOT_FOUND);
        }
    }

    public PropertyIterator getProperties() throws RepositoryException
    {
        return new PropertyIteratorImp(properties.values().iterator(), this, properties.values().size());
    }

    public String getUUID() throws UnsupportedRepositoryOperationException, RepositoryException
    {
        if (properties.get(getName(BaseNode.vocabulary.jcr_uuid)) != null)
        {
            String getUUID = properties.get(getName(BaseNode.vocabulary.jcr_uuid)).getString();
            if (getUUID == null)
            {
                throw new UnsupportedRepositoryOperationException();
            }
            return getUUID;
        }
        else
        {
            throw new UnsupportedRepositoryOperationException();
        }

    }

    public boolean hasNodes() throws RepositoryException
    {
        return this.childs.size() > 0;
    }

    public NodeType getPrimaryNodeType() throws RepositoryException
    {
        return new NodeTypeImp(clazz, session);
    }

    public NodeType[] getMixinNodeTypes() throws RepositoryException
    {
        ArrayList<NodeType> mininNodeTypes = new ArrayList<NodeType>();
        for (SemanticClass mixInClazz : mixins)
        {
            NodeTypeImp nodeType = new NodeTypeImp(mixInClazz, session);
            mininNodeTypes.add(nodeType);
        }
        return mininNodeTypes.toArray(new NodeType[mininNodeTypes.size()]);
    }

    protected boolean isVersionable()
    {
        return mixins.contains(BaseNode.vocabulary.mix_Versionable);
    }

    protected boolean isLockable()
    {
        return mixins.contains(BaseNode.vocabulary.mix_Lockable);
    }

    SimpleNode getLockBaseNode()
    {
        return null;
    }

    public Lock lock(boolean isDeep, boolean isSessionScoped) throws UnsupportedRepositoryOperationException, LockException, AccessDeniedException, InvalidItemStateException, RepositoryException
    {
        if (!isLockable())
        {
            throw new UnsupportedRepositoryOperationException("The node can not be lockable");
        }
        if (isLocked())
        {
            throw new LockException("The node is already locked");
        }
        addProperty(getName(BaseNode.vocabulary.jcr_lockOwner)).setValueInternal(getSession().getUserID());
        addProperty(getName(BaseNode.vocabulary.jcr_lockIsDeep)).setValueInternal(isDeep);
        LockImp lock = new LockImp(this, isSessionScoped);
        if (!isSessionScoped && !this.isNew())
        {
            try
            {
                node.setProperty(BaseNode.vocabulary.jcr_lockOwner, getSession().getUserID());
                node.setProperty(BaseNode.vocabulary.jcr_lockIsDeep, String.valueOf(isDeep));
            }
            catch (Exception e)
            {
            }
        }
        if (isSessionScoped)
        {
            session.addLockSession(lock);
        }
        return lock;

    }

    public boolean isLockedByParent() throws RepositoryException
    {
        boolean isLockedByParent=false;
        if(parent!=null)
        {
            if(parent.isLocked() && parent.isDeepLock())
            {
                return true;
            }
        }
        return isLockedByParent;
    }    
    public boolean isLocked() throws RepositoryException
    {
        boolean isLocked = false;
        if (properties.get(getName(BaseNode.vocabulary.jcr_lockOwner)) != null)
        {
            String value = properties.get(getName(BaseNode.vocabulary.jcr_lockOwner)).getString();
            if (value != null)
            {
                return true;
            }
        }
        if(isLockedByParent())
        {
            return true;
        }
        if (node != null)
        {
            return node.isLocked();
        }
        return isLocked;
    }

    public String getPath() throws RepositoryException
    {
        if (path == null)
        {
            throw new RepositoryException("The was unable to find");
        }
        return path;

    }

    public String getName() throws RepositoryException
    {
        return name;
    }

    public final Node getParent() throws ItemNotFoundException, AccessDeniedException, RepositoryException
    {
        return parent;
    }

    public Item getAncestor(int depth) throws ItemNotFoundException, AccessDeniedException, RepositoryException
    {
        throw new UnsupportedOperationException(NOT_SUPPORTED_YET);
    }

    public int getDepth() throws RepositoryException
    {
        int depth = 0;
        Node thisNode = this;
        Node parentNode = this.getParent();
        while (parentNode == null)
        {
            thisNode = parentNode;
            parentNode = thisNode.getParent();
            depth++;
        }
        return depth;
    }

    public Session getSession() throws RepositoryException
    {
        return session;
    }

    public boolean isNode()
    {
        return true;
    }

    public boolean isNew()
    {
        if (node == null)
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    public boolean isModified()
    {
        return true;
    }

    public boolean isSame(Item otherItem) throws RepositoryException
    {
        return false;
    }

    public void accept(ItemVisitor visitor) throws RepositoryException
    {
    }

    private void saveProperties() throws SWBException, RepositoryException
    {
        for (SimpleNode child : this.childs.values())
        {
            child.saveProperties();
        }
        for (SemanticClass mixinClazz : this.mixins)
        {
            node.registerClass(mixinClazz);
        }
        for (PropertyImp prop : this.properties.values())
        {
            if (prop.isNew() || prop.isModified())
            {
                if (node.existsProperty(prop.getName(), prop.getSemanticClass()))
                {
                    SemanticProperty semanticProperty = node.getSemanticProperty(prop.getName(), prop.getSemanticClass());
                    node.setProperty(semanticProperty, prop.getString());
                }
                else
                {
                    String type = SemanticVocabulary.XMLS_STRING;
                    SemanticProperty semanticProperty = node.registerCustomProperty(prop.getName(), type, prop.getSemanticClass());
                    node.setProperty(semanticProperty, prop.getString());
                }
                prop.setModified(false);
                prop.setNew(false);
            }
        }
    }

    private void removeChilds() throws RepositoryException, SWBException
    {
        for (SimpleNode child : this.removedchilds)
        {
            child.removeChilds();
            if (child.node != null)
            {
                child.node.remove();
            }
        }
        // delete childNodes
        this.removedchilds.clear();
    }

    private void createChilds() throws RepositoryException, SWBException
    {
        ArrayList<SimpleNode> newChilds = new ArrayList<SimpleNode>();
        for (SimpleNode child : this.childs.values())
        {
            if (child.node == null)
            {
                child.node = node.createNodeBase(child.getName(), child.clazz);
                child.id = child.node.getId();
            }
            child.createChilds();
            newChilds.add(child);
        }
        this.childs.clear();
        for (SimpleNode child : newChilds)
        {
            this.childs.put(child.id, child);
        }
    }

    public void save() throws AccessDeniedException, ItemExistsException, ConstraintViolationException, InvalidItemStateException, ReferentialIntegrityException, VersionException, LockException, NoSuchNodeTypeException, RepositoryException
    {
        if (isNew())
        {
            throw new RepositoryException("Can not save a new item Node:" + this.getPath());
        }
        try
        {
            removeChilds();
            createChilds();
            saveProperties();
            node.save();
        }
        catch (SWBException swbe)
        {
            throw new RepositoryException(swbe);
        }

    }

    public boolean holdsLock() throws RepositoryException
    {
        throw new UnsupportedOperationException(NOT_SUPPORTED_YET);
    }

    protected LockImp getLockImp()
    {
        return session.getLock(this);
    }

    public void unlock() throws UnsupportedRepositoryOperationException, LockException, AccessDeniedException, InvalidItemStateException, RepositoryException
    {
        if (isLockable() && isLocked())
        {

            // por el momento sólo puede desbloquear el mismo usuario, deberia verse como un super usurio lo puede desbloquear
            LockImp lock = getLockImp();
            properties.remove(getName(BaseNode.vocabulary.jcr_lockOwner));
            properties.remove(getName(BaseNode.vocabulary.jcr_lockIsDeep));
            if (lock != null && lock.isSessionScoped())
            {
                session.removeLockSession(lock);
            }
            return;

        }
        else
        {
            throw new UnsupportedRepositoryOperationException("The node " + name + " is not lockable or is not locked");
        }
    }

    public void refresh(boolean keepChanges) throws InvalidItemStateException, RepositoryException
    {
        throw new UnsupportedOperationException(NOT_SUPPORTED_YET);
    }

    public void remove() throws VersionException, LockException, ConstraintViolationException, RepositoryException
    {
        if (parent.isVersionable() && !parent.isCheckedOut())
        {
            throw new RepositoryException("The node can not be removed in check-in mode");
        }

        session.checksLock(this);
        parent.removedchilds.add(this);
        parent.childs.remove(this.id);
    }

    protected SessionImp getSessionImp()
    {
        return session;
    }

    public String getLockOwner()
    {
        String getLockOwner = null;
        if (properties.get(BaseNode.vocabulary.jcr_lockOwner) != null)
        {
            try
            {
                getLockOwner = properties.get(getName(BaseNode.vocabulary.jcr_lockOwner)).getString();
            }
            catch (Exception e)
            {
            }
        }
        return getLockOwner;
    }

    public Lock getLock() throws UnsupportedRepositoryOperationException, LockException, AccessDeniedException, RepositoryException
    {
        if (isLockable() && isLocked())
        {
            LockImp lock = getLockImp();
            if (lock == null)
            {
                if (isLocked())
                {
                    lock = new LockImp(this);
                }
                else
                {
                    throw new LockException("The node is not locked");
                }
            }
            return lock;
        }
        throw new UnsupportedRepositoryOperationException("The node " + this.getName() + " is not lockable or is not locked");
    }

    public Property setProperty(String name, String[] value, int arg2) throws ValueFormatException, VersionException, LockException, ConstraintViolationException, RepositoryException
    {
        Value[] values = new Value[value.length];
        int i = 0;
        for (String oValue : value)
        {
            values[i] = factory.createValue(oValue);
            i++;
        }
        return setProperty(name, values, 0);
    }

    public Property setProperty(String name, String value) throws ValueFormatException, VersionException, LockException, ConstraintViolationException, RepositoryException
    {
        Value[] values =
        {
            factory.createValue(value)
        };
        return setProperty(name, values, 0);
    }

    public Property setProperty(String name, String value, int arg2) throws ValueFormatException, VersionException, LockException, ConstraintViolationException, RepositoryException
    {
        Value[] values =
        {
            factory.createValue(value)
        };
        return setProperty(name, values, 0);
    }

    public Property setProperty(String name, InputStream value) throws ValueFormatException, VersionException, LockException, ConstraintViolationException, RepositoryException
    {
        throw new UnsupportedRepositoryOperationException(NOT_SUPPORTED_YET);
    }

    public Property setProperty(String name, boolean value) throws ValueFormatException, VersionException, LockException, ConstraintViolationException, RepositoryException
    {
        Value[] values =
        {
            factory.createValue(value)
        };
        return setProperty(name, values, 0);
    }

    public Property setProperty(String name, double value) throws ValueFormatException, VersionException, LockException, ConstraintViolationException, RepositoryException
    {
        Value[] values =
        {
            factory.createValue(value)
        };
        return setProperty(name, values, 0);
    }

    public Property setProperty(String name, long value) throws ValueFormatException, VersionException, LockException, ConstraintViolationException, RepositoryException
    {
        Value[] values =
        {
            factory.createValue(value)
        };
        return setProperty(name, values, 0);
    }

    public Property setProperty(String name, Calendar value) throws ValueFormatException, VersionException, LockException, ConstraintViolationException, RepositoryException
    {
        Value[] values =
        {
            factory.createValue(value)
        };
        return setProperty(name, values, 0);
    }

    public Property setProperty(String name, Node node) throws ValueFormatException, VersionException, LockException, ConstraintViolationException, RepositoryException
    {
        throw new UnsupportedOperationException(NOT_SUPPORTED_YET);
    }

    public Node getNode(String relPath) throws PathNotFoundException, RepositoryException
    {
        SessionImp.checkRelPath(relPath);
        relPath = SessionImp.normalize(relPath, this);
        Item item = session.getItem(relPath);
        if (item.isNode())
        {
            return (Node) item;
        }
        throw new PathNotFoundException("The path is not a node");
    }

    public Node addNode(String relPath) throws ItemExistsException, PathNotFoundException, VersionException, ConstraintViolationException, LockException, RepositoryException
    {
        return addNode(relPath, null);
    }

    public Node addNode(String relPath, String primaryNodeTypeName) throws ItemExistsException, PathNotFoundException, NoSuchNodeTypeException, LockException, VersionException, ConstraintViolationException, RepositoryException
    {
        SessionImp.checkRelPath(relPath);
        if (primaryNodeTypeName == null)
        {
            primaryNodeTypeName = DEFAULT_PRIMARY_NODE_TYPE_NAME;
        }
        relPath = SessionImp.normalize(relPath, this);
        String nameNode = SessionImp.getNodeName(relPath);
        String parentPath = SessionImp.getParentPath(relPath, this);
        Item item = session.getItem(parentPath);
        if (item.isNode())
        {
            Node parentNode = (Node) item;
            session.checksLock(parentNode);
            if (!parentNode.isCheckedOut())
            {
                throw new RepositoryException("The node can not add a child in check-in mode");
            }

            if (session.itemExists(relPath))
            {
                throw new ItemExistsException("The item " + relPath + " already exists");
            }
            //NodeTypeImp nodeType = ( NodeTypeImp ) this.getPrimaryNodeType();
            try
            {
                SemanticClass clazzToInsert = session.getRootBaseNode().getSemanticClass(primaryNodeTypeName);
                if (!session.getRootBaseNode().canAddNode(clazzToInsert, clazz))
                {
                    throw new ConstraintViolationException("The node can not be added to this node");
                }
            }
            catch (SWBException e)
            {
                throw new NoSuchNodeTypeException("The node type " + primaryNodeTypeName + " was not found");
            }

            try
            {
                SemanticClass primaryNodeClazz = root.getSemanticClass(primaryNodeTypeName);
                SimpleNode tmp = new SimpleNode(session, nameNode, primaryNodeClazz, this, 0, UUID.randomUUID().toString());
                return tmp;
            }
            catch (SWBException swbe)
            {
                throw new NoSuchNodeTypeException(swbe);
            }
        }
        else
        {
            throw new NoSuchNodeTypeException("The relpath is not a node");
        }
    }

    public Property setProperty(String name, Value value) throws ValueFormatException, VersionException, LockException, ConstraintViolationException, RepositoryException
    {
        return setProperty(name, value, 0);
    }

    public Property setProperty(String name, Value value, int arg2) throws ValueFormatException, VersionException, LockException, ConstraintViolationException, RepositoryException
    {
        Value[] values =
        {
            value
        };
        return setProperty(name, values, arg2);
    }

    public Property setProperty(String name, Value[] value) throws ValueFormatException, VersionException, LockException, ConstraintViolationException, RepositoryException
    {
        return setProperty(name, value, 0);
    }

    public void orderBefore(String srcName, String destName) throws UnsupportedRepositoryOperationException, VersionException, ConstraintViolationException, ItemNotFoundException, LockException, RepositoryException
    {
        throw new UnsupportedRepositoryOperationException(NOT_SUPPORTED_YET);
    }

    public Property setProperty(String name, String[] value) throws ValueFormatException, VersionException, LockException, ConstraintViolationException, RepositoryException
    {
        return setProperty(name, value, 0);
    }

    public Version getBaseVersion() throws UnsupportedRepositoryOperationException, RepositoryException
    {
        if (isVersionable())
        {
        }
        throw new UnsupportedRepositoryOperationException(NOT_SUPPORTED_YET);
    }

    public void restore(String versionName, boolean arg1) throws VersionException, ItemExistsException, UnsupportedRepositoryOperationException, LockException, InvalidItemStateException, RepositoryException
    {
        throw new UnsupportedRepositoryOperationException(NOT_SUPPORTED_YET);
    }

    public void restore(Version version, boolean arg1) throws VersionException, ItemExistsException, UnsupportedRepositoryOperationException, LockException, RepositoryException
    {
        restore(version, null, arg1);
    }

    public void restore(Version version, String relPath, boolean arg2) throws PathNotFoundException, ItemExistsException, VersionException, ConstraintViolationException, UnsupportedRepositoryOperationException, LockException, InvalidItemStateException, RepositoryException
    {
        if (isVersionable())
        {
        }
        throw new UnsupportedRepositoryOperationException(NOT_SUPPORTED_YET);
    }

    public void restoreByLabel(String label, boolean arg1) throws VersionException, ItemExistsException, UnsupportedRepositoryOperationException, LockException, InvalidItemStateException, RepositoryException
    {
        if (isVersionable())
        {
        }
        throw new UnsupportedRepositoryOperationException(NOT_SUPPORTED_YET);
    }

    public VersionHistory getVersionHistory() throws UnsupportedRepositoryOperationException, RepositoryException
    {
        if (isVersionable() && node != null)
        {
            try
            {
                return new VersionHistoryImp(node.getHistoryNode(), session, this);
            }
            catch (SWBException e)
            {
                throw new RepositoryException(e);
            }
        }
        throw new UnsupportedRepositoryOperationException(NOT_SUPPORTED_YET);
    }

    public final int getIndex() throws RepositoryException
    {
        return index;
    }

    public Item getPrimaryItem() throws ItemNotFoundException, RepositoryException
    {
        throw new UnsupportedOperationException(NOT_SUPPORTED_YET);
    }

    private String getName(SemanticProperty prop)
    {
        return prop.getPrefix() + ":" + prop.getName();
    }

    public boolean isCheckedOut() throws RepositoryException
    {
        if (isVersionable())
        {
            String nameProperty = getName(BaseNode.vocabulary.jcr_isCheckedOut);
            if (properties.get(nameProperty) != null)
            {
                try
                {
                    return properties.get(getName(BaseNode.vocabulary.jcr_isCheckedOut)).getBoolean();
                }
                catch (ValueFormatException e)
                {
                    return false;
                }
            }
        }
        return true;
    }

    public String getCorrespondingNodePath(String workspaceName) throws ItemNotFoundException, NoSuchWorkspaceException, AccessDeniedException, RepositoryException
    {
        throw new UnsupportedOperationException(NOT_SUPPORTED_YET);
    }

    public void doneMerge(Version version) throws VersionException, InvalidItemStateException, UnsupportedRepositoryOperationException, RepositoryException
    {
        throw new UnsupportedOperationException(NOT_SUPPORTED_YET);
    }

    public void cancelMerge(Version version) throws VersionException, InvalidItemStateException, UnsupportedRepositoryOperationException, RepositoryException
    {
        throw new UnsupportedOperationException(NOT_SUPPORTED_YET);
    }

    public void update(String srcWorkspaceName) throws NoSuchWorkspaceException, AccessDeniedException, LockException, InvalidItemStateException, RepositoryException
    {
        throw new UnsupportedOperationException(NOT_SUPPORTED_YET);
    }

    public NodeIterator merge(String srcWorkspace, boolean bestEffort) throws NoSuchWorkspaceException, AccessDeniedException, MergeException, LockException, InvalidItemStateException, RepositoryException
    {
        throw new UnsupportedOperationException(NOT_SUPPORTED_YET);
    }

    private boolean hasPendingChanges()
    {
        if (properties.isEmpty() && childs.isEmpty())
        {
            return true;
        }
        return false;
    }

    public PropertyIterator getReferences() throws RepositoryException
    {
        throw new UnsupportedOperationException(NOT_SUPPORTED_YET);
    }

    public Version checkin() throws VersionException, UnsupportedRepositoryOperationException, InvalidItemStateException, LockException, RepositoryException
    {
        throw new UnsupportedRepositoryOperationException("The node must be saved before, because has changes or is new");
    }

    public void checkout() throws UnsupportedRepositoryOperationException, LockException, RepositoryException
    {
        session.checksLock(this);
        if (isVersionable())
        {
            if (hasPendingChanges())
            {
                throw new UnsupportedRepositoryOperationException("The node must be saved before, because has changes or is new");
            }

            addProperty(getName(BaseNode.vocabulary.jcr_isCheckedOut)).setValueInternal(true);
            return;

        }
        else
        {
            throw new UnsupportedRepositoryOperationException("The node is not versionable");
        }

    }

    private static final String getPrefix(String name)
    {
        String namePrefix = null;
        int pos = name.indexOf(":");
        if (pos != -1)
        {
            namePrefix = name.substring(0, pos);
            if (namePrefix.trim().equals(""))
            {
                namePrefix = null;
            }
        }
        return namePrefix;
    }

    private static final String getNameProperty(String name)
    {
        String nameProperty = null;
        int pos = name.indexOf(":");
        if (pos != -1)
        {
            nameProperty = name.substring(pos + 1);
        }
        else
        {
            nameProperty = name;
        }
        return nameProperty;
    }

    public PropertyIterator getProperties(String namePattern) throws RepositoryException
    {
        ArrayList<Property> propeties = new ArrayList<Property>();
        String[] names = namePattern.split("|");
        for (String propertyNames : names)
        {
            if (propertyNames != null && !propertyNames.trim().equals(""))
            {
                PropertyIterator myProperties = this.getProperties();
                while (myProperties.hasNext())
                {
                    Property prop = myProperties.nextProperty();
                    String namePrefix = getPrefix(propertyNames);
                    String namePropertyToFind = getNameProperty(propertyNames);
                    String propPrefix = getPrefix(prop.getName());
                    String propName = SessionImp.getNodeName(prop.getName());
                    boolean prefixEquals = false;
                    boolean nameEquals = false;
                    if (namePrefix != null && propPrefix != null)
                    {
                        if (namePrefix.equals("*") || namePrefix.equals(propPrefix))
                        {
                            prefixEquals = true;
                        }
                    }
                    if (namePropertyToFind != null && propName != null)
                    {
                        if (namePropertyToFind.equals("*") || namePropertyToFind.equals(propName))
                        {
                            prefixEquals = true;
                        }
                    }
                    if (prefixEquals && nameEquals)
                    {
                        propeties.add(prop);
                    }
                }
            }
        }
        return new PropertyIteratorImp(properties.values().iterator(), this, properties.values().size());
    }

    public NodeIterator getNodes(String namePattern) throws RepositoryException
    {
        throw new UnsupportedOperationException(NOT_SUPPORTED_YET);
    }

    

    @Override
    public String toString()
    {
        return this.path;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (obj == null)
        {
            return false;
        }
        if (getClass() != obj.getClass())
        {
            return false;
        }
        final SimpleNode other = (SimpleNode) obj;
        if ((this.id == null) ? (other.id != null) : !this.id.equals(other.id))
        {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode()
    {
        int hash = 7;
        hash = 37 * hash + (this.id != null ? this.id.hashCode() : 0);
        return hash;
    }
}   
    
