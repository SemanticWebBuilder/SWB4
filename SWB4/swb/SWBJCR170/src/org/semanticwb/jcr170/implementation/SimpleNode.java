/**  
* SemanticWebBuilder es una plataforma para el desarrollo de portales y aplicaciones de integración, 
* colaboración y conocimiento, que gracias al uso de tecnología semántica puede generar contextos de 
* información alrededor de algún tema de interés o bien integrar información y aplicaciones de diferentes 
* fuentes, donde a la información se le asigna un significado, de forma que pueda ser interpretada y 
* procesada por personas y/o sistemas, es una creación original del Fondo de Información y Documentación 
* para la Industria INFOTEC, cuyo registro se encuentra actualmente en trámite. 
* 
* INFOTEC pone a su disposición la herramienta SemanticWebBuilder a través de su licenciamiento abierto al público (‘open source’), 
* en virtud del cual, usted podrá usarlo en las mismas condiciones con que INFOTEC lo ha diseñado y puesto a su disposición; 
* aprender de él; distribuirlo a terceros; acceder a su código fuente y modificarlo, y combinarlo o enlazarlo con otro software, 
* todo ello de conformidad con los términos y condiciones de la LICENCIA ABIERTA AL PÚBLICO que otorga INFOTEC para la utilización 
* del SemanticWebBuilder 4.0. 
* 
* INFOTEC no otorga garantía sobre SemanticWebBuilder, de ninguna especie y naturaleza, ni implícita ni explícita, 
* siendo usted completamente responsable de la utilización que le dé y asumiendo la totalidad de los riesgos que puedan derivar 
* de la misma. 
* 
* Si usted tiene cualquier duda o comentario sobre SemanticWebBuilder, INFOTEC pone a su disposición la siguiente 
* dirección electrónica: 
*  http://www.semanticwebbuilder.org
**/ 
 
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
import java.util.Set;
import java.util.UUID;
import java.util.regex.Pattern;
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
import javax.jcr.PropertyType;
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
import javax.jcr.version.VersionIterator;
import org.semanticwb.Logger;
import org.semanticwb.SWBException;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.GenericIterator;
import org.semanticwb.platform.SemanticClass;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.platform.SemanticProperty;
import org.semanticwb.platform.SemanticVocabulary;
import org.semanticwb.repository.BaseNode;
import org.semanticwb.repository.ChildNodeDefinition;
import org.semanticwb.repository.HierarchyNode;
import org.semanticwb.repository.LockUserComparator;
import org.semanticwb.repository.Lockable;
import org.semanticwb.repository.Referenceable;
import org.semanticwb.repository.Traceable;
import org.semanticwb.repository.Versionable;

/**
 *
 * @author victor.lorenzana
 */
public class SimpleNode implements Node
{
    private boolean delete=false;
    private static final String JCR_FROZENNODE_NAME = "jcr:frozenNode";
    private static Logger log = SWBUtils.getLogger(SimpleNode.class);
    private String id;
    private boolean modified = false;
    protected static final String NOT_SUPPORTED_YET = "Not supported yet.";
    protected static final String WAS_NOT_FOUND = " was not found";
    private static final ValueFactoryImp factory = new ValueFactoryImp();
    protected BaseNode node;
    private final HashSet<SemanticClass> mixins = new HashSet<SemanticClass>();
    private final HashMap<String, SimpleNode> childs = new HashMap<String, SimpleNode>();
    protected final ArrayList<SimpleNode> removedchilds = new ArrayList<SimpleNode>();
    private final HashMap<String, PropertyImp> properties = new HashMap<String, PropertyImp>();
    private final String name;
    protected final SemanticClass clazz;
    protected final SessionImp session;
    private final int index;
    protected SimpleNode parent;
    protected final NodeDefinitionImp nodeDefinition;
    protected VersionHistoryImp versionHistory = null;
    private String path;

    SimpleNode(SessionImp session, String name, SemanticClass clazz, SimpleNode parent, int index, String id) throws RepositoryException
    {
        this.id = id;
        this.name = name;
        this.clazz = clazz;
        nodeDefinition = this.getNodeDefinition(session.getRootBaseNode(), clazz, name, session);
        this.session = session;
        this.index = index;
        this.parent = parent;
        this.path = parent.getPath();
        if (path.endsWith("/"))
        {
            path += name;
        }
        else
        {
            path += "/" + name;
        }
        if (parent != null && !parent.childs.containsKey(id))
        {
            parent.childs.put(this.id, this);
        }

        if (clazz.equals(HierarchyNode.nt_HierarchyNode) || clazz.isSubClass(HierarchyNode.nt_HierarchyNode))
        {
            PropertyImp prop = addProperty(getName(Traceable.jcr_created), clazz, false);
            Value time = factory.createValue(Calendar.getInstance());
            prop.setValueInternal(time.getString());
        }


        Set<String> supertypes = session.getRootBaseNode().getSuperTypes(clazz);
        for (String superType : supertypes)
        {
            try
            {
                SemanticClass superTypeClazz = session.getRootBaseNode().getSemanticClass(superType);
                if (superTypeClazz.equals(Referenceable.mix_Referenceable) || superTypeClazz.isSubClass(Referenceable.mix_Referenceable))
                {
                    PropertyImp prop = addProperty(getName(Referenceable.jcr_uuid), superTypeClazz, false);
                    prop.setValueInternal(UUID.randomUUID().toString());
                    session.addSimpleNode(this);
                }
                if (superTypeClazz.equals(Versionable.mix_Versionable) || superTypeClazz.isSubClass(Versionable.mix_Versionable))
                {
                    PropertyImp prop = addProperty(getName(Versionable.jcr_isCheckedOut), superTypeClazz, false);
                    prop.setValueInternal(true);
                }
                if (superTypeClazz.equals(Lockable.mix_Lockable) || superTypeClazz.isSubClass(Lockable.mix_Lockable))
                {
                    PropertyImp prop = addProperty(getName(Lockable.jcr_lockOwner), superTypeClazz, false);
                    prop.setValueInternal(null);
                    prop = addProperty(getName(Lockable.jcr_lockIsDeep), superTypeClazz, false);
                    prop.setValueInternal(false);
                }
                if (session.getRootBaseNode().isMixIn(superTypeClazz))
                {
                    mixins.add(superTypeClazz);
                }
            }
            catch (SWBException e)
            {
                log.error(e);
            }
        }
        Iterator<SemanticProperty> semanticProperties = clazz.listProperties();
        while (semanticProperties.hasNext())
        {
            SemanticProperty prop = semanticProperties.next();
            if (!session.getRootBaseNode().isInternal(prop))
            {
                try
                {
                    PropertyImp propImp;
                    if (prop.isDataTypeProperty())
                    {
                        propImp = addProperty(getName(prop), clazz, false);
                    }
                    else
                    {
                        propImp = addProperty(getName(prop), clazz, true);
                    }

                    if (prop.equals(BaseNode.jcr_primaryType) && propImp.getValue() == null)
                    {
                        propImp.setValueInternal(clazz.getPrefix() + ":" + clazz.getName());
                    }

                }
                catch (Exception e)
                {
                    log.debug(e);
                }
            }
        }
    }

    SimpleNode(BaseNode node, SessionImp session) throws RepositoryException
    {

        if (node == null)
        {
            throw new IllegalArgumentException("The base node is null");
        }
        this.id = node.getId();
        this.node = node;
        this.session = session;
        nodeDefinition = this.getNodeDefinition(node, node.getSemanticObject().getSemanticClass(), node.getName(), session);
        this.clazz = node.getSemanticObject().getSemanticClass();
        this.index = getIndexInParent();
        session.addSimpleNode(this);
        this.name = node.getName();
        if (node.getParent() == null || this.getParent().getPath() == null)
        {
            this.path = "/";
        }
        else
        {
            String parentPath = this.getParent().getPath();
            if (parentPath.endsWith("/"))
            {
                this.path = parentPath + this.name + "[" + index + "]";
            }
            else
            {
                this.path = parentPath + "/" + this.name + "[" + index + "]";
            }
        }
        Iterator<SemanticClass> classes = node.getSemanticObject().listSemanticClasses();
        while (classes.hasNext())
        {
            SemanticClass parentClazz = classes.next();
            if (node.isMixIn(parentClazz))
            {
                if (parentClazz.equals(Referenceable.mix_Referenceable) || parentClazz.isSubClass(Referenceable.mix_Referenceable))
                {
                    addProperty(getName(Referenceable.jcr_uuid), parentClazz, false);
                }
                if (parentClazz.equals(Versionable.mix_Versionable) || parentClazz.isSubClass(Versionable.mix_Versionable))
                {
                    addProperty(getName(Versionable.jcr_isCheckedOut), parentClazz, false);
                }
                if (parentClazz.equals(Lockable.mix_Lockable) || parentClazz.isSubClass(Lockable.mix_Lockable))
                {
                    addProperty(getName(Lockable.jcr_lockOwner), parentClazz, false);
                }
                mixins.add(parentClazz);
            }
        }
        if (node.getParent() != null)
        {
            String parentId = node.getParent().getId();
            if (session.existSimpleNodeByID(parentId))
            {
                this.parent = session.getSimpleNodeByID(parentId);
                if (parent != null && !parent.childs.containsKey(id))
                {
                    parent.childs.put(this.id, this);
                }
            }
        }
        // loading declared properties
        Iterator<SemanticProperty> semanticProperties = clazz.listProperties();
        while (semanticProperties.hasNext())
        {
            SemanticProperty prop = semanticProperties.next();
            if (!node.isInternal(prop))
            {
                try
                {
                    if (prop.isObjectProperty())
                    {
                        addProperty(prop, node, false, clazz, true);                        
                    }
                    else
                    {
                        addProperty(prop, node, false, clazz, false);
                    }
                }
                catch (Exception e)
                {
                    log.debug(e);
                }
            }
        }

        //loading undeclared properties
        Iterator<SemanticProperty> props = node.getSemanticObject().listProperties();
        while (props.hasNext())
        {
            SemanticProperty prop = props.next();
            if (!node.isInternal(prop))
            {
                try
                {
                    if (prop.isObjectProperty())
                    {
                        addProperty(prop, node, false, clazz, true);
                    }
                    else if (prop.isDataTypeProperty())
                    {
                        addProperty(prop, node, false, clazz, false);
                    }
                }
                catch (Exception e)
                {
                    log.debug(e);
                }
            }
        }
        if (node.isVersionable())
        {
            if (versionHistory == null)
            {
                try
                {
                    versionHistory = new VersionHistoryImp(node.getHistoryNode(), session, this);
                }
                catch (SWBException e)
                {
                    log.error(e);
                }
            }
        }

    }

    private NodeDefinitionImp getNodeDefinition(BaseNode node, SemanticClass clazz, String name, SessionImp session)
    {
        if (node != null)
        {
            return new NodeDefinitionImp(node.getSemanticObject(), session);
        }
        return null;
    }

    boolean isDeleted()
    {
        return parent.removedchilds.contains(this);
    }

    private PropertyImp addProperty(SemanticProperty property, BaseNode node, boolean isNew, SemanticClass clazz, boolean isNode) throws ValueFormatException, VersionException, LockException, ConstraintViolationException, RepositoryException
    {
        PropertyImp prop = addProperty(property, node, clazz, isNode);
        prop.setNew(isNew);
        return prop;

    }

    private PropertyImp addProperty(SemanticProperty property, BaseNode node, SemanticClass clazz, boolean isNode) throws RepositoryException
    {
        if (this.properties.containsKey(getName(property)))
        {
            return this.properties.get(getName(property));
        }
        else
        {
            PropertyDefinitionImp propertyDefinition = new PropertyDefinitionImp(session, property);
            PropertyImp prop = new PropertyImp(this, clazz, getName(property), propertyDefinition, isNode,session);
            this.properties.put(prop.getName(), prop);
            return prop;
        }
    }

    private PropertyImp addProperty(String name, SemanticClass clazz, boolean isNode) throws RepositoryException
    {
        PropertyImp prop = null;
        if (!this.properties.containsKey(name))
        {
            if (session.getRootBaseNode().existsProperty(clazz, name))
            {
                SemanticProperty property = session.getRootBaseNode().getSemanticProperty(name, clazz);
                PropertyDefinitionImp propertyDefinition = new PropertyDefinitionImp(session, property);
                prop = new PropertyImp(this, clazz, name, propertyDefinition, isNode,session);
                this.properties.put(name, prop);
            }
            else
            {
                PropertyDefinitionImp propertyDefinition = new PropertyDefinitionImp(name);
                prop = new PropertyImp(this, clazz, name, propertyDefinition, false,session);
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
        return addProperty(name, clazz, false);

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
        try
        {
            Item item = session.getItem(relPath);
            if (item != null && item.isNode())
            {
                hasNode = true;
            }
        }
        catch (PathNotFoundException pnf)
        {
            hasNode = false;
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
            SemanticClass mixinClazz = session.getRootBaseNode().getSemanticClass(mixinName);
            if (!session.getRootBaseNode().isMixIn(mixinClazz))
            {
                throw new ConstraintViolationException("The mixinName is not a minxin node type");
            }
            if (mixinClazz.equals(Referenceable.mix_Referenceable) || mixinClazz.isSubClass(Referenceable.mix_Referenceable))
            {
                PropertyImp prop = addProperty(getName(Referenceable.jcr_uuid), mixinClazz, false);
                prop.setValueInternal(UUID.randomUUID().toString());
            }
            if (mixinClazz.equals(Versionable.mix_Versionable) || mixinClazz.isSubClass(Versionable.mix_Versionable))
            {
                PropertyImp prop = addProperty(getName(Versionable.jcr_isCheckedOut), mixinClazz, false);
                prop.setValueInternal(true);
            }
            if (mixinClazz.equals(Lockable.mix_Lockable) || mixinClazz.isSubClass(Lockable.mix_Lockable))
            {
                PropertyImp prop = addProperty(getName(Lockable.jcr_lockOwner), mixinClazz, false);
                prop.setValueInternal(null);
                prop = addProperty(getName(Lockable.jcr_lockIsDeep), mixinClazz, false);
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
            SemanticClass mixin = session.getRootBaseNode().getSemanticClass(mixinName);
            if (!session.getRootBaseNode().isMixIn(mixin))
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
            SemanticClass mixin = session.getRootBaseNode().getSemanticClass(mixinName);
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
        return nodeDefinition;
    }

    public Property setProperty(String name, Value[] value, int type) throws ValueFormatException, VersionException, LockException, ConstraintViolationException, RepositoryException
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
            modified = true;
        }
        else
        {
            if (!getPrimaryNodeType().canSetProperty(name, value))
            {
                throw new ConstraintViolationException("The property " + name + " is not defined in this nodeType(" + getPrimaryNodeType().getName() + ")");
            }
            property = addProperty(name);
            property.setValue(value);
            modified = true;
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
                        new SimpleNode(nodeChild, session);
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
        boolean existsProperty = properties.containsKey(name);
        if (!existsProperty && node != null)
        {
            // puede ser una propiedad agregada
            try
            {
                String uri = node.getUri(name);
                SemanticProperty prop = SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty(uri);
                if (prop != null)
                {
                    String value = node.getSemanticObject().getProperty(prop);
                    if (value != null)
                    {
                        if (prop.isDataTypeProperty())
                        {
                            addProperty(prop, node, clazz, false);
                        }
                        else
                        {
                            addProperty(prop, node, clazz, true);
                        }
                        existsProperty = true;
                    }
                }
            }
            catch (Exception e)
            {
                log.error(e);
            }

        }
        return existsProperty;
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
            throw new PathNotFoundException("The property " + relPath + WAS_NOT_FOUND + " for the NodeType(" + getPrimaryNodeType().getName() + ")");
        }
    }

    public PropertyIterator getProperties() throws RepositoryException
    {
        return new PropertyIteratorImp(properties.values().iterator(), this, properties.values().size());
    }

    public String getUUID() throws UnsupportedRepositoryOperationException, RepositoryException
    {
        if (properties.get(getName(Referenceable.jcr_uuid)) != null)
        {
            PropertyImp prop = properties.get(getName(Referenceable.jcr_uuid));
            String getUUID = prop.getString();
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
        return mixins.contains(Versionable.mix_Versionable);
    }

    protected boolean isLockable()
    {
        return mixins.contains(Lockable.mix_Lockable);
    }

    SimpleNode getLockBaseNode()
    {
        return null;
    }

    public boolean isLockedByParent() throws RepositoryException
    {
        boolean isLockedByParent = false;
        if (parent != null)
        {
            if (parent.isLocked() && parent.isDeepLock())
            {
                return true;
            }
        }
        return isLockedByParent;
    }

    public boolean isLocked() throws RepositoryException
    {
        boolean isLocked = false;
        if (properties.get(getName(Lockable.jcr_lockOwner)) != null)
        {
            String value = properties.get(getName(Lockable.jcr_lockOwner)).getString();
            if (value != null)
            {
                return true;
            }
        }
        if (isLockedByParent())
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
        return path;
    }

    private int getIndexInParent()
    {
        if (node != null)
        {
            int i = 0;
            if (node.getParent() != null)
            {
                GenericIterator<BaseNode> nodes = node.getParent().listNodes();
                while (nodes.hasNext())
                {
                    BaseNode child = nodes.next();
                    if (child.equals(node))
                    {
                        return i;
                    }
                    i++;
                }
            }
        }
        return 0;
    }

    public String getName() throws RepositoryException
    {
        return name;
    }

    public final Node getParent() throws ItemNotFoundException, AccessDeniedException, RepositoryException
    {
        if (parent == null && node != null)
        {
            if (session.existSimpleNodeByID(node.getParent().getId()))
            {
                this.parent = session.getSimpleNodeByID(node.getParent().getId());
                if (!this.parent.childs.containsKey(this.id))
                {
                    this.parent.childs.put(this.id, this);
                }
            }
            else
            {
                this.parent = new SimpleNode(node.getParent(), session);
                if (!this.parent.childs.containsKey(this.id))
                {
                    this.parent.childs.put(this.id, this);
                }
            }
        }
        if (parent == null)
        {
            throw new ItemNotFoundException("The parent for this node was not found");
        }
        return this.parent;
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
        return modified;
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
            child.modified = false;
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
                    if (semanticProperty.isDataTypeProperty())
                    {
                        if (semanticProperty.isBinary())
                        {
                            node.setProperty(semanticProperty, prop.getStream());
                        }
                        else
                        {
                            node.setProperty(semanticProperty, prop.getString());
                        }
                    }
                }
                else
                {
                    log.event("Registring the property " + prop.getName() + " for the class " + prop.getSemanticClass().getURI() + "");
                    String type = SemanticVocabulary.XMLS_STRING;
                    SemanticProperty semanticProperty = node.registerCustomProperty(prop.getName(), type, prop.getSemanticClass());
                    node.setProperty(semanticProperty, prop.getString());
                }
                prop.setModified(false);
                prop.setNew(false);
            }
        }
        modified = false;
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
        
        if(delete)
        {
            for(PropertyImp prop :  this.properties.values())
            {
                prop.remove();
            }            
            if(isVersionable())
            {                
                VersionHistory vh=this.getVersionHistory();
                VersionIterator vi=vh.getAllVersions();
                while(vi.hasNext())
                {
                    Version v=vi.nextVersion();
                    v.remove();
                }
                vh.save();
            }
            for (SimpleNode child : this.childs.values())
            {
                for(PropertyImp prop :  child.properties.values())
                {
                    prop.remove();
                }
                if(child.isVersionable())
                {                    
                    VersionHistory vh=child.getVersionHistory();
                    VersionIterator vi=vh.getAllVersions();
                    while(vi.hasNext())
                    {
                        Version v=vi.nextVersion();
                        v.remove();
                    }
                    vh.save();
                }
                child.delete=delete;
                child.removeChilds();
                if (child.node != null)
                {
                    child.node.remove();
                }
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

    public void checkRequiredProperties() throws SWBException, RepositoryException
    {
        for (PropertyImp prop : this.properties.values())
        {
            PropertyDefinitionImp def = (PropertyDefinitionImp) prop.getDefinition();
            if (def.isMandatory())
            {
                if (!prop.isNode())
                {
                    if (prop.getDefinition().getRequiredType() == PropertyType.BINARY)
                    {
                        if (prop.getStream() == null)
                        {
                            throw new SWBException("The value for the property " + prop.getName() + " is null for the node " + this.getName() + " of type " + clazz.getPrefix() + ":" + clazz.getName());
                        }
                    }
                    else
                    {
                        if (prop.getString() == null)
                        {
                            throw new SWBException("The value for the property " + prop.getName() + " is null for the node " + this.getName() + " of type " + clazz.getPrefix() + ":" + clazz.getName());
                        }
                    }
                }
            }
        }

    }

    public void checkSave() throws SWBException, RepositoryException
    {
        checkRequiredProperties();
        for (SimpleNode child : childs.values())
        {
            child.checkRequiredProperties();
        }
    }

    public void checkVersionable() throws SWBException, RepositoryException
    {
        for (SimpleNode child : childs.values())
        {
            child.checkVersionable();
        }
        node.checkVersionable();
        if (this.versionHistory == null)
        {
            BaseNode history = node.getHistoryNode();
            if (history != null)
            {
                this.versionHistory = new VersionHistoryImp(history, session, this);
            }
        }
    }

    public void save() throws AccessDeniedException, ItemExistsException, ConstraintViolationException, InvalidItemStateException, ReferentialIntegrityException, VersionException, LockException, NoSuchNodeTypeException, RepositoryException
    {
        if (isNew())
        {
            throw new RepositoryException("Can not save a new item Node:" + this.getPath());
        }
        if (modified)
        {
            try
            {
                this.checkSave();
                removeChilds();
                createChilds();
                saveProperties();
                checkVersionable();
                modified = false;
            }
            catch (SWBException swbe)
            {
                throw new RepositoryException(swbe);
            }
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
        addProperty(getName(Lockable.jcr_lockOwner)).setValueInternal(getSession().getUserID());
        addProperty(getName(Lockable.jcr_lockIsDeep)).setValueInternal(isDeep);
        LockImp lock = new LockImp(this, isSessionScoped);
        if (!isSessionScoped && !this.isNew())
        {
            try
            {
                node.setProperty(Lockable.jcr_lockOwner, getSession().getUserID());
                node.setProperty(Lockable.jcr_lockIsDeep, String.valueOf(isDeep));
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

    public void unlock() throws UnsupportedRepositoryOperationException, LockException, AccessDeniedException, InvalidItemStateException, RepositoryException
    {
        if (isLockable() && isLocked())
        {

            // por el momento sólo puede desbloquear el mismo usuario, deberia verse como un super usurio lo puede desbloquear
            LockImp lock = getLockImp();
            properties.remove(getName(Lockable.jcr_lockOwner));
            properties.remove(getName(Lockable.jcr_lockIsDeep));
            if (!this.isNew() && node.isLocked())
            {
                try
                {
                    node.unLock(name, new LockUserComparator()
                    {

                        public boolean canUnlockNodeLockedByUser(String lockOwner, String unlockUser)
                        {
                            return true;
                        }
                    });
                }
                catch (SWBException e)
                {
                    throw new RepositoryException(e);
                }
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
        if (!parent.removedchilds.contains(this))
        {
            parent.removedchilds.add(this);
        }
        parent.childs.remove(this.id);
        delete=true;
        session.removeSimpleNode(this);
        parent.modified = true;
        this.modified = true;
        if (this.node != null && this.node.getSemanticObject().getSemanticClass().equals(org.semanticwb.repository.Version.sclass) && this instanceof VersionImp)
        {
            VersionHistoryImp vh = (VersionHistoryImp) ((VersionImp) this).getContainingHistory();
            SimpleNode nodeParent = vh.parent;
            Version base = nodeParent.getBaseVersion();
            if (base.getName().equals(this.getName()))
            {
                Version[] versions = ((VersionImp) this).getPredecessors();
                SemanticObject newBase = ((VersionImp) versions[versions.length - 1]).node.getSemanticObject();
                nodeParent.node.getSemanticObject().setObjectProperty(org.semanticwb.repository.Versionable.jcr_baseVersion, newBase);
            }
        }
    }

    protected SessionImp getSessionImp()
    {
        return session;
    }

    public String getLockOwner()
    {
        String getLockOwner = null;
        if (properties.get(Lockable.jcr_lockOwner) != null)
        {
            try
            {
                getLockOwner = properties.get(getName(Lockable.jcr_lockOwner)).getString();
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

    public Property setProperty(String name, String[] value, int type) throws ValueFormatException, VersionException, LockException, ConstraintViolationException, RepositoryException
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

    public Property setProperty(String name, String value, int type) throws ValueFormatException, VersionException, LockException, ConstraintViolationException, RepositoryException
    {
        Value[] values =
        {
            factory.createValue(value)
        };
        return setProperty(name, values, 0);
    }

    public Property setProperty(String name, InputStream value) throws ValueFormatException, VersionException, LockException, ConstraintViolationException, RepositoryException
    {
        Value[] values =
        {
            factory.createValue(value)
        };
        return setProperty(name, values, 0);
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
        /*relPath = SessionImp.normalize(relPath, this);
        Item item = session.getItem(relPath);
        if (item.isNode())
        {
        return (Node) item;
        }*/

        for (SimpleNode child : childs.values())
        {
            if (child.getName().equals(relPath))
            {
                return child;
            }
        }
        if (node != null)
        {
            GenericIterator<BaseNode> childsBaseNodes = node.listNodes();
            while (childsBaseNodes.hasNext())
            {
                BaseNode child = childsBaseNodes.next();
                if (child.getName().equals(relPath))
                {
                    BaseNode nodeChild = child;
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
                            return new SimpleNode(nodeChild, session);
                        }
                    }
                }
            }
        }
        throw new PathNotFoundException("The node " + relPath + " was not found");
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
            if (node != null)
            {
                SemanticObject childNodeDefinition = BaseNode.getChildNodeDefinition(node.getSemanticObject().getSemanticClass(), relPath);
                if (childNodeDefinition != null)
                {
                    primaryNodeTypeName = childNodeDefinition.getProperty(ChildNodeDefinition.jcr_defaultPrimaryType);
                    if (primaryNodeTypeName == null)
                    {
                        throw new ConstraintViolationException("The node can not be added without a primaryNodeTypeName");
                    }
                }
            }

        }
        relPath = SessionImp.normalize(relPath, this);
        String nameNode = SessionImp.getNodeName(relPath);
        String parentPath = SessionImp.getParentPath(relPath, this);
        Item item = this;
        if (item.isNode())
        {
            SimpleNode parentNode = (SimpleNode) item;
            session.checksLock(parentNode);
            if (parentNode.isVersionable() && !parentNode.isCheckedOut())
            {
                throw new RepositoryException("The node can not add a child in check-in mode");
            }
            boolean allowsSameNameSiblings = false;
            try
            {
                SemanticClass clazzToCreate = session.getRootBaseNode().getSemanticClass(primaryNodeTypeName);
                allowsSameNameSiblings = session.getRootBaseNode().allowsSameNameSiblings(clazz, clazzToCreate, nameNode);
            }
            catch (SWBException swbe)
            {
            }
            if (!allowsSameNameSiblings)
            {
                boolean exists = false;
                for (SimpleNode child : childs.values())
                {
                    if (child.getName().equals(nameNode))
                    {
                        exists = true;
                        break;
                    }
                }
                if (exists)
                {
                    throw new ItemExistsException("The item " + relPath + " already exists");
                }
            }
            try
            {
                SemanticClass clazzToInsert = session.getRootBaseNode().getSemanticClass(primaryNodeTypeName);
                if (!session.getRootBaseNode().canAddNode(clazzToInsert, clazz))
                {
                    throw new ConstraintViolationException("The nodeType " + clazzToInsert.getPrefix() + ":" + clazzToInsert.getName() + " can not be added to the nodeType " + clazz.getPrefix() + ":" + clazz.getName());
                }
            }
            catch (SWBException e)
            {
                throw new NoSuchNodeTypeException("The node type " + primaryNodeTypeName + " was not found");
            }

            try
            {
                SemanticClass primaryNodeClazz = session.getRootBaseNode().getSemanticClass(primaryNodeTypeName);
                SimpleNode tmp = new SimpleNode(session, nameNode, primaryNodeClazz, this, 0, UUID.randomUUID().toString());
                modified = true;
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

    public Property setProperty(String name, Value value, int type) throws ValueFormatException, VersionException, LockException, ConstraintViolationException, RepositoryException
    {
        Value[] values =
        {
            value
        };
        return setProperty(name, values, type);
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
        if (isVersionable() && node != null)
        {
            BaseNode baseVersion = node.getBaseVersion();
            if (baseVersion != null)
            {
                return new VersionImp(baseVersion, versionHistory, session);
            }
            else
            {
                RepositoryException re = new RepositoryException("The base version was not found, internal error");
                log.debug(re);
                throw re;
            }
        }
        throw new UnsupportedRepositoryOperationException("The node is new or is not versionable");
    }

    public void restore(String versionName, boolean removeExisting) throws VersionException, ItemExistsException, UnsupportedRepositoryOperationException, LockException, InvalidItemStateException, RepositoryException
    {
        Version version = getVersionHistory().getVersion(versionName);
        restore(version, null, removeExisting);
    }

    public void restore(Version version, boolean removeExisting) throws VersionException, ItemExistsException, UnsupportedRepositoryOperationException, LockException, RepositoryException
    {
        restore(version, ".", removeExisting);
    }

    public void restore(Version version, String relPath, boolean removeExisting) throws PathNotFoundException, ItemExistsException, VersionException, ConstraintViolationException, UnsupportedRepositoryOperationException, LockException, InvalidItemStateException, RepositoryException
    {
        if (version.getName().equals(BaseNode.JCR_ROOTVERSION))
        {
            throw new VersionException("The root version can be used as restore version");
        }
        SimpleNode nodeTorestore = null;
        if (relPath.equals("."))
        {
            nodeTorestore = this;
        }
        else
        {
            throw new RepositoryException("The method restore with path " + relPath + " is not defined");
        }
        if (nodeTorestore == null)
        {
            throw new PathNotFoundException("The node with path " + relPath + " was not found");
        }
        else
        {
            Node nodeFrozen = version.getNode(JCR_FROZENNODE_NAME);
            PropertyIterator it = nodeFrozen.getProperties();
            while (it.hasNext())
            {
                Property prop = it.nextProperty();
                SemanticProperty semanticProp=node.getSemanticProperty(prop.getName(), clazz);
                if (this.existsProperty(prop.getName()) && !node.isInternal(semanticProp)  && !prop.getDefinition().isProtected())
                {
                    log.trace("restoring property " + prop.getName());
                    this.setProperty(prop.getName(), prop.getValues());
                }
            }
            if (removeExisting)
            {
                version.remove();
            }
        }
    }

    public void restoreByLabel(String label, boolean removeExisting) throws VersionException, ItemExistsException, UnsupportedRepositoryOperationException, LockException, InvalidItemStateException, RepositoryException
    {
        Version version = getVersionHistory().getVersionByLabel(label);
        restore(version, removeExisting);
    }

    public VersionHistory getVersionHistory() throws UnsupportedRepositoryOperationException, RepositoryException
    {
        if (isVersionable() && node != null)
        {
            return versionHistory;
        }
        throw new UnsupportedRepositoryOperationException("The node is not versionable or is new");
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
            String nameProperty = getName(Versionable.jcr_isCheckedOut);
            if (properties.get(nameProperty) != null)
            {
                try
                {
                    return properties.get(getName(Versionable.jcr_isCheckedOut)).getBoolean();
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
        if (node == null || this.isModified())
        {
            throw new UnsupportedRepositoryOperationException("The node must be saved before, because has changes or is new");
        }
        else
        {
            try
            {
                addProperty(getName(Versionable.jcr_isCheckedOut)).setValueInternal(false);
                if (node != null)
                {
                    node.getSemanticObject().setBooleanProperty(Versionable.jcr_isCheckedOut, false);
                }
                BaseNode version = node.checkin();
                return new VersionImp(version, this.versionHistory, session);
            }
            catch (SWBException e)
            {
                throw new RepositoryException(e);
            }
        }

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

            PropertyImp jcr_isCheckedOut = addProperty(getName(Versionable.jcr_isCheckedOut));
            jcr_isCheckedOut.setValueInternal(true);
            if (node != null)
            {
                node.getSemanticObject().setBooleanProperty(Versionable.jcr_isCheckedOut, true);
            }
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
        ArrayList<SimpleNode> childsToReturn = new ArrayList<SimpleNode>();
        for (SimpleNode child : this.getSimpleNodes())
        {
            String Childname = child.getName();
            if (Pattern.matches(namePattern, new SimpleCharSequence(Childname)))
            {
                childsToReturn.add(child);
            }
        }
        return new NodeIteratorImp(session, childsToReturn);
    }

    @Override
    public String toString()
    {
        return path;
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
    
