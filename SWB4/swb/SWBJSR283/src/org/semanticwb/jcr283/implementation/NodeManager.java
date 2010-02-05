/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.jcr283.implementation;

import java.util.HashSet;
import java.util.Hashtable;
import java.util.Set;
import javax.jcr.AccessDeniedException;
import javax.jcr.InvalidItemStateException;
import javax.jcr.ItemExistsException;
import javax.jcr.ReferentialIntegrityException;
import javax.jcr.RepositoryException;
import javax.jcr.ValueFactory;
import javax.jcr.lock.LockException;
import javax.jcr.nodetype.ConstraintViolationException;
import javax.jcr.nodetype.NoSuchNodeTypeException;
import javax.jcr.version.VersionException;
import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
import org.semanticwb.jcr283.repository.model.Base;
import org.semanticwb.jcr283.repository.model.Root;
import org.semanticwb.model.GenericIterator;

/**
 *
 * @author victor.lorenzana
 */
public final class NodeManager
{

    private static final String JCR_SYSTEM = "jcr:system";
    private static final String JCR_VERSION_STORAGE = "jcr:versionStorage";
    private static final String PATH_SEPARATOR = "/";
    private final HashtableNodeManager nodes;
    private Hashtable<String, PropertyStatus> properties = new Hashtable<String, PropertyStatus>();
    private Hashtable<String, HashSet<PropertyStatus>> propertiesbyParent = new Hashtable<String, HashSet<PropertyStatus>>();
    private final static Logger log = SWBUtils.getLogger(NodeManager.class);
    private final SessionImp session;

    public NodeManager(SessionImp session)
    {
        nodes = new HashtableNodeManager(session);
        this.session = session;
    }

    public NodeImp loadRoot(org.semanticwb.jcr283.repository.model.Workspace ws, SessionImp session) throws RepositoryException
    {
        if (!nodes.containsKey(PATH_SEPARATOR))
        {
            log.trace("Loading root node for repository " + ws.getName());
            if (ws.getRoot() == null)
            {
                Root newroot = Root.ClassMgr.createRoot("jcr:root", ws);
                ws.setRoot(newroot);
                newroot.setPrimaryType(Root.sclass.getPrefix() + ":" + Root.sclass.getName());
                newroot.setName("jcr:root");
            }
            RootNodeImp root = new RootNodeImp(ws.getRoot(), session);
            this.addNode(root);

        }
        RootNodeImp root = (RootNodeImp) nodes.get(PATH_SEPARATOR).getNode();
        String systemPath = root.getPathFromName(JCR_SYSTEM);
        if (!nodes.containsKey(systemPath))
        {
            loadChild(root, JCR_SYSTEM, session, true);
        }
        if (nodes.get(systemPath) == null)
        {
            NodeImp system = root.insertNode(JCR_SYSTEM);
            this.addNode(system);
            system.saveData();
        }
        NodeImp system = nodes.get(systemPath).getNode();
        if (system == null)
        {
            throw new RepositoryException("The node system was not found");
        }
        initVersionStore(system, session);
        return root;

    }

    public void validate() throws ConstraintViolationException, RepositoryException
    {
        nodes.get(PATH_SEPARATOR).getNode().validate();
    }

    private void initVersionStore(NodeImp system, SessionImp session) throws RepositoryException
    {
        loadChilds(system, session, false);
        if (!nodes.containsKey(system.getPathFromName(JCR_VERSION_STORAGE)))
        {
            NodeImp jcr_versionStorage = system.insertNode(JCR_VERSION_STORAGE);
            this.addNode(jcr_versionStorage);
            jcr_versionStorage.saveData();
        }
        else
        {
            NodeImp versionStorage = nodes.get(system.getPathFromName(JCR_VERSION_STORAGE)).getNode();
            this.addNode(versionStorage);
        }
    }

    public NodeImp getRoot()
    {
        return this.nodes.get(PATH_SEPARATOR).getNode();
    }

    public NodeImp getNodeByIdentifier(String id, SessionImp session) throws RepositoryException
    {
        return this.getNodeByIdentifier(id, session, null);
    }

    public NodeImp getNodeByIdentifier(String id, SessionImp session, NodeTypeImp nodeTypeToSeach) throws RepositoryException
    {
        return nodes.getNodeByIdentifier(id, session, nodeTypeToSeach);
    }

    public NodeImp addNode(NodeImp node)
    {
        if (nodes.containsKey(node.path))
        {
            return nodes.get(node.path).getNode();
        }
        else
        {

            nodes.put(node.path, new NodeStatus(node, session));
        }
        return nodes.get(node.path).getNode();
    }

    private void restoreProperty(String path)
    {
        if (properties.containsKey(path))
        {
            PropertyStatus prop = properties.get(path);
            prop.restore();
        }
    }

    public PropertyImp addProperty(PropertyImp property, String path, String pathParent, boolean replace)
    {
        if (!this.properties.containsKey(path))
        {
            PropertyStatus propertyStatus = new PropertyStatus(property);
            this.properties.put(path, propertyStatus);
            HashSet<PropertyStatus> childnodes = new HashSet<PropertyStatus>();
            if (propertiesbyParent.containsKey(pathParent))
            {
                childnodes = propertiesbyParent.get(pathParent);
            }
            childnodes.add(propertyStatus);
            propertiesbyParent.put(pathParent, childnodes);

        }
        else
        {
            if (replace)
            {
                PropertyStatus propertyStatus = new PropertyStatus(property);
                this.properties.put(path, propertyStatus);
                HashSet<PropertyStatus> childnodes = new HashSet<PropertyStatus>();
                if (propertiesbyParent.containsKey(pathParent))
                {
                    childnodes = propertiesbyParent.get(pathParent);
                }
                childnodes.add(propertyStatus);
                propertiesbyParent.put(pathParent, childnodes);
            }
            else
            {
                restoreProperty(path);
            }
        }
        return this.properties.get(path).getProperty();
    }

    /**
     *
     * @param path
     * @param exact
     * @return
     */
    public int countNodes(String name, NodeImp parent, SessionImp session, boolean loadchilds, String id) throws RepositoryException
    {
        return nodes.countNodes(name, parent, session, loadchilds, id);

    }

    public boolean hasNode(String path)
    {
        return this.nodes.get(path) == null ? false : true;
    }

    public boolean hasNode(String parentNode, String name)
    {
        return nodes.hasNode(parentNode, name);
    }

    public void move(String oldPath, String newPath, NodeImp newParent)
    {
    }

    @SuppressWarnings(value = "deprecation")
    public void save() throws RepositoryException
    {
        nodes.get(PATH_SEPARATOR).getNode().save();
    }

    public void save(String path, int depth) throws AccessDeniedException, ItemExistsException, ConstraintViolationException, InvalidItemStateException, ReferentialIntegrityException, VersionException, LockException, NoSuchNodeTypeException, RepositoryException
    {
        if (nodes.containsKey(path))
        {
            NodeImp node = nodes.get(path).getNode();
            node.saveData();
            for (PropertyImp prop : getChildProperties(node))
            {
                prop.saveData();
            }
        }
        if (properties.containsKey(path))
        {
            PropertyImp node = properties.get(path).getProperty();
            node.saveData();
        }

    }

    public void saveProperties(String nodePath) throws AccessDeniedException, ItemExistsException, ConstraintViolationException, InvalidItemStateException, ReferentialIntegrityException, VersionException, LockException, NoSuchNodeTypeException, RepositoryException
    {
        if (nodes.containsKey(nodePath))
        {
            NodeImp node = nodes.get(nodePath).getNode();
            node.saveData();
        }
    }

    /**
     *
     * @param path path to found
     * @param exact true, sear exact path, false ignore the index of the node
     * @return
     */
    public boolean hasChildNodes(String pathParent)
    {
        return nodes.hasChildNodes(pathParent);
    }

    public boolean hasProperty(String path)
    {
        return this.properties.get(path) == null ? false : true;
    }

    public boolean hasChildProperty(String pathParent)
    {
        return this.propertiesbyParent.containsKey(pathParent) && this.propertiesbyParent.get(pathParent).size() > 0;

    }

    NodeImp getProtectedNode(String path, SessionImp session) throws RepositoryException
    {
        boolean deleted = false;
        NodeImp node = this.nodes.get(path).getNode();
        if (node == null)
        {
            //TODO: Try to load the node from database
            String[] paths = path.split(PATH_SEPARATOR);
            for (String fragment : paths)
            {
                int depth = 0;
                NodeImp nodetoextract = nodes.get(PATH_SEPARATOR).getNode();
                if (fragment.equals(""))
                {
                    nodetoextract = nodes.get(PATH_SEPARATOR).getNode();
                    loadChilds(nodetoextract, session, false);
                    depth = 0;
                }
                else
                {
                    try
                    {
                        loadChilds(nodetoextract, session, false);
                        depth++;
                    }
                    catch (Exception e)
                    {
                        throw new RepositoryException(e);
                    }
                }

            }
            node = this.nodes.get(path).getNode();
        }
        else
        {
            deleted = this.nodes.get(path).isDeleted();
        }
        if (deleted || !node.getDefinition().isProtected())
        {
            node = null;
        }
        return node;
    }

    public NodeImp getNode(String path)
    {
        NodeImp node = this.nodes.get(path).getNode();
        if (node != null && !this.nodes.get(path).isDeleted())
        {
            return node;
        }
        return null;
    }

    public NodeImp getNode(String path, SessionImp session) throws RepositoryException
    {
        boolean deleted = false;
        NodeImp node = this.nodes.get(path).getNode();
        if (node == null)
        {
            //TODO: Try to load the node from database
            String[] paths = path.split(PATH_SEPARATOR);
            for (String fragment : paths)
            {
                int depth = 0;
                NodeImp nodetoextract = nodes.get(PATH_SEPARATOR).getNode();
                if (fragment.equals(""))
                {
                    nodetoextract = nodes.get(PATH_SEPARATOR).getNode();
                    loadChilds(nodetoextract, session, false);
                    depth = 0;
                }
                else
                {
                    try
                    {
                        loadChilds(nodetoextract, session, false);
                        depth++;
                    }
                    catch (Exception e)
                    {
                        throw new RepositoryException(e);
                    }
                }

            }
            node = this.nodes.get(path).getNode();
        }
        else
        {
            deleted = this.nodes.get(path).isDeleted();
        }
        if (node.getDefinition().isProtected() || deleted)
        {
            node = null;
        }
        return node;
    }

    public PropertyImp getProperty(String path)
    {
        PropertyImp propertyImp = this.properties.get(path).getProperty();
        if (propertyImp.definition.isProtected())
        {
            propertyImp = null;
        }
        return propertyImp;
    }

    public PropertyImp getAllProperty(String path)
    {
        PropertyImp propertyImp = this.properties.get(path).getProperty();
        return propertyImp;
    }

    PropertyImp getProtectedProperty(String path) throws RepositoryException
    {
        PropertyImp propertyImp = this.properties.get(path).getProperty();
        if (!propertyImp.getDefinition().isProtected())
        {
            propertyImp = null;
        }
        return propertyImp;
    }

    private Set<PropertyImp> getChildProperties(String pathParent) throws RepositoryException
    {
        HashSet<PropertyImp> getChilds = new HashSet<PropertyImp>();
        if (propertiesbyParent.containsKey(pathParent) && propertiesbyParent.get(pathParent).size() > 0)
        {
            for (PropertyStatus prop : this.propertiesbyParent.get(pathParent))
            {
                if (!prop.isDeleted() && !prop.getProperty().getDefinition().isProtected())
                {
                    getChilds.add(prop.getProperty());
                }
            }
        }
        return getChilds;
    }

    Set<PropertyImp> getProtectedChildProperties(String pathParent) throws RepositoryException
    {
        HashSet<PropertyImp> getChilds = new HashSet<PropertyImp>();
        if (propertiesbyParent.containsKey(pathParent) && propertiesbyParent.get(pathParent).size() > 0)
        {
            for (PropertyStatus prop : this.propertiesbyParent.get(pathParent))
            {
                if (!prop.isDeleted() && prop.getProperty().getDefinition().isProtected())
                {
                    getChilds.add(prop.getProperty());
                }
            }
        }
        return getChilds;
    }

    Set<PropertyImp> getAllChildProperties(String pathParent)
    {
        HashSet<PropertyImp> getChilds = new HashSet<PropertyImp>();
        if (propertiesbyParent.containsKey(pathParent) && propertiesbyParent.get(pathParent).size() > 0)
        {
            for (PropertyStatus prop : this.propertiesbyParent.get(pathParent))
            {
                if (!prop.isDeleted())
                {
                    getChilds.add(prop.getProperty());
                }
            }
        }
        return getChilds;
    }

    public Set<PropertyImp> getChildProperties(NodeImp node) throws RepositoryException
    {
        return getChildProperties(node.getPath());
    }

    public Set<NodeImp> getChildNodes(NodeImp node) throws RepositoryException
    {
        return this.getChildNodes(node.getPath());
    }

    public NodeImp getChildNodeById(NodeImp parent, String id, SessionImp session, boolean replace) throws RepositoryException
    {
        if (parent.getSemanticObject() != null)
        {
            GenericIterator<Base> childs = new Base(parent.getSemanticObject()).listNodes();
            while (childs.hasNext())
            {
                Base child = childs.next();
                if (child.getId().equals(id))
                {
                    int childindex = 0;
                    childindex = countNodes(child.getName(), parent, session, false, child.getId());
                    String childpath = null;
                    String path = parent.path;
                    if (path.endsWith(PATH_SEPARATOR))
                    {
                        childpath = path + child.getName();
                    }
                    else
                    {
                        childpath = path + PATH_SEPARATOR + child.getName();
                    }

                    if (childindex > 0)
                    {
                        childpath += "[" + childindex + "]";
                    }
                    if (replace || !nodes.containsKey(childpath))
                    {
                        NodeImp childNode = new NodeImp(child, parent, childindex, childpath, parent.getDepth() + 1, session);
                        this.addNode(childNode);
                        return childNode;
                    }
                }
            }
        }
        return null;
    }

    Set<NodeImp> getProtectedChildNodes(String parenPath) throws RepositoryException
    {
        return nodes.getProtectedChildNodes(parenPath);
    }

    void clearDeletedChildNodes(String parenPath) throws RepositoryException
    {
        nodes.clearDeletedChildNodes(parenPath);
    }

    Set<NodeImp> getDeletedChildNodes(String parenPath) throws RepositoryException
    {
        return nodes.getDeletedChildNodes(parenPath);
    }

    public Set<NodeImp> getChildNodes(String parenPath) throws RepositoryException
    {
        return nodes.getChildNodes(parenPath);
    }

    public void loadChild(NodeImp node, String name, SessionImp session, boolean replace) throws RepositoryException
    {
        if (node.getSemanticObject() != null)
        {
            GenericIterator<Base> childs = new Base(node.getSemanticObject()).listNodes();
            while (childs.hasNext())
            {
                Base child = childs.next();
                if (child.getName().equals(name))
                {
                    int childindex = 0;
                    childindex = countNodes(child.getName(), node, session, false, child.getId());
                    String childpath = null;
                    String path = node.path;
                    if (path.endsWith(PATH_SEPARATOR))
                    {
                        childpath = path + child.getName();
                    }
                    else
                    {
                        childpath = path + PATH_SEPARATOR + child.getName();
                    }

                    if (childindex > 0)
                    {
                        childpath += "[" + childindex + "]";
                    }
                    if (replace || !nodes.containsKey(childpath))
                    {
                        NodeImp childNode = new NodeImp(child, node, childindex, childpath, node.getDepth() + 1, session);
                        this.addNode(childNode);
                    }
                }
            }
        }
    }

    public void loadChilds(NodeImp node, SessionImp session, boolean replace) throws RepositoryException
    {
        nodes.loadChilds(node, session, replace);
    }

    NodeStatus getNodeStatus(String path)
    {
        return nodes.get(path);
    }

    public void removeProperty(String path, String parentPath)
    {
        if (properties.containsKey(path))
        {
            properties.get(path).delete();
        }
    }

    public void removeNode(String path, String parentPath)
    {
        if (nodes.containsKey(path))
        {
            nodes.get(path).delete();
        }
    }
}


