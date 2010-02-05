/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.jcr283.implementation;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Set;
import javax.jcr.AccessDeniedException;
import javax.jcr.InvalidItemStateException;
import javax.jcr.ItemExistsException;
import javax.jcr.ReferentialIntegrityException;
import javax.jcr.RepositoryException;
import javax.jcr.lock.LockException;
import javax.jcr.nodetype.ConstraintViolationException;
import javax.jcr.nodetype.NoSuchNodeTypeException;
import javax.jcr.version.VersionException;
import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
import org.semanticwb.jcr283.repository.model.Base;
import org.semanticwb.jcr283.repository.model.Root;
import org.semanticwb.jcr283.repository.model.Workspace;
import org.semanticwb.model.GenericIterator;

/**
 *
 * @author victor.lorenzana
 */
public class NodeManager
{

    private static final String JCR_SYSTEM = "jcr:system";
    private static final String JCR_VERSION_STORAGE = "jcr:versionStorage";
    private static final String PATH_SEPARATOR = "/";
    private Hashtable<String, NodeStatus> nodes = new Hashtable<String, NodeStatus>();
    private Hashtable<String, NodeStatus> nodesbyId = new Hashtable<String, NodeStatus>();
    private Hashtable<String, HashSet<NodeStatus>> nodesbyParent = new Hashtable<String, HashSet<NodeStatus>>();
    private Hashtable<String, PropertyStatus> properties = new Hashtable<String, PropertyStatus>();
    private Hashtable<String, HashSet<PropertyStatus>> propertiesbyParent = new Hashtable<String, HashSet<PropertyStatus>>();
    private final static Logger log = SWBUtils.getLogger(NodeManager.class);

    public NodeManager()
    {
    }

    public NodeImp loadRoot(org.semanticwb.jcr283.repository.model.Workspace ws, SessionImp session) throws RepositoryException
    {
        if (!nodes.containsKey(PATH_SEPARATOR))
        {
            //log.trace("Loading root node for repository " + ws.getName());
            if (ws.getRoot() == null)
            {
                Root newroot = Root.ClassMgr.createRoot("jcr:root", ws);
                ws.setRoot(newroot);
                newroot.setPrimaryType(Root.sclass.getPrefix() + ":" + Root.sclass.getName());
                newroot.setName("jcr:root");
            }
            RootNodeImp root = new RootNodeImp(ws.getRoot(), session);
            this.addNode(root, "/", null);

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
            this.addNode(system, systemPath, "/");
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
            this.addNode(jcr_versionStorage, jcr_versionStorage.path, system.path);
            jcr_versionStorage.saveData();
        }
        else
        {
            NodeImp versionStorage = nodes.get(system.getPathFromName(JCR_VERSION_STORAGE)).getNode();
            this.addNode(versionStorage, versionStorage.path, system.path);
        }
    }

    public NodeImp getRoot()
    {
        return this.nodes.get(PATH_SEPARATOR).getNode();
    }

    public NodeImp getNodeByIdentifier(String id, SessionImp session) throws RepositoryException
    {
        if (nodesbyId.containsKey(id))
        {
            if (!nodesbyId.get(id).isDeleted())
            {
                return nodesbyId.get(id).getNode();
            }
        }
        else
        {
            // load node
            Base nodeToLoad = null;
            ArrayList<Base> nodesToLoad = new ArrayList<Base>();
            Workspace ws = Workspace.ClassMgr.getWorkspace(session.getWorkspace().getName());
            NodeTypeImp baseNodeTye = NodeTypeManagerImp.loadNodeType(Base.sclass);
            NodeTypeIteratorImp nodeTypes = baseNodeTye.getSubtypesImp();
            while (nodeTypes.hasNext())
            {
                NodeTypeImp nodeType = (NodeTypeImp) nodeTypes.nextNodeType();
                nodeToLoad = (org.semanticwb.jcr283.repository.model.Base) ws.getSemanticObject().getModel().getGenericObject(ws.getSemanticObject().getModel().getObjectUri(id, nodeType.getSemanticClass()), nodeType.getSemanticClass());
                if (nodeToLoad != null)
                {
                    break;
                }
            }

            if (nodeToLoad != null)
            {
                nodesToLoad.add(nodeToLoad);
                Base parent = nodeToLoad.getParentNode();
                NodeImp parentloaded = null;
                boolean loaded = false;
                while (loaded != true)
                {
                    if (nodesbyId.containsKey(parent.getId()))
                    {
                        loaded = true;
                        parentloaded = nodesbyId.get(parent.getId()).getNode();
                    }
                    else
                    {
                        String tempid = parent.getId();
                        nodesToLoad.add(parent);
                        parent = parent.getParentNode();
                        if (parent == null)
                        {
                            throw new RepositoryException("The parentNode for the node with id " + tempid + " was not found");
                        }
                    }
                }
                if (parentloaded != null)
                {
                    for (int i = nodesToLoad.size() - 1; i >= 0; i--)
                    {
                        Base base = nodesToLoad.get(i);
                        String path = parentloaded.path;
                        String childpath = parentloaded.getPathFromName(base.getName());
                        int childIndex = countNodes(base.getName(), parentloaded, session, false, base.getId());
                        if (childIndex > 0)
                        {
                            childIndex--;
                        }
                        if (childIndex > 0)
                        {
                            childpath += "[" + childIndex + "]";
                        }
                        NodeImp temp = NodeImp.createNodeImp(base, parentloaded, childIndex, childpath, session);
                        this.addNode(temp, childpath,path);
                        parentloaded = temp;
                    }
                }
            }
        }
        if (nodesbyId.get(id) != null)
        {
            return nodesbyId.get(id).getNode();
        }
        return null;
    }

    public NodeImp addNode(NodeImp node, String path, String pathParent)
    {
        //log.trace("Inserting node " + path + " into the NodeManager");
        NodeStatus nodestatus = null;
        if (this.nodes.containsKey(path))
        {
            nodestatus = this.nodes.get(path);
        }
        else
        {
            nodestatus = new NodeStatus(node);
            this.nodes.put(path, nodestatus);
        }
        if (!nodesbyId.containsKey(node.id))
        {
            nodesbyId.put(node.id, nodestatus);
        }
        if (pathParent != null)
        {
            if (nodesbyParent.containsKey(pathParent))
            {
                HashSet<NodeStatus> childs = nodesbyParent.get(pathParent);
                if (childs == null)
                {
                    childs = new HashSet<NodeStatus>();
                }
                if (!childs.contains(nodestatus))
                {
                    childs.add(nodestatus);
                }

            }
            else
            {
                HashSet<NodeStatus> childs = new HashSet<NodeStatus>();
                if (childs == null)
                {
                    childs = new HashSet<NodeStatus>();
                }
                if (!childs.contains(nodestatus))
                {
                    childs.add(nodestatus);
                }
                nodesbyParent.put(pathParent, childs);
            }
        }
        return nodestatus.getNode();
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
        int countNodes = 0;
        if (nodesbyParent.containsKey(parent.path))
        {
            if (loadchilds)
            {
                loadChilds(parent, session, false);
            }
            HashSet<NodeStatus> childnodes = nodesbyParent.get(parent.path);
            for (NodeStatus nodeStatus : childnodes)
            {
                if (nodeStatus.getNode().name.equals(name))
                {
                    if (id == null)
                    {
                        countNodes++;
                    }
                    else
                    {
                        if (!id.equals(nodeStatus.getNode().id))
                        {
                            countNodes++;
                        }
                    }
                }
            }
        }
        return countNodes;

        /*int countNodes = 0;
        if (exact)
        {
        return this.nodes.containsKey(path) ? 1 : 0;
        }
        else
        {
        for (String pathNode : nodes.keySet())
        {
        if (pathNode.startsWith(path))
        {
        String dif = pathNode.substring(path.length());
        if (!dif.equals(""))
        {
        if (dif.startsWith("[") && dif.endsWith("]") && dif.indexOf(PATH_SEPARATOR) == -1)
        {
        countNodes++;
        }
        }
        else
        {
        countNodes++;
        }
        }
        }
        }
        return countNodes;*/
    }

    public boolean hasNode(String path)
    {
        return this.nodes.get(path) == null ? false : true;
    }

    public boolean hasNode(String parentNode, String name)
    {
        if (this.nodesbyParent.containsKey(parentNode) && this.nodesbyParent.get(parentNode).size() > 0)
        {
            Set<NodeStatus> childs = this.nodesbyParent.get(parentNode);
            for (NodeStatus node : childs)
            {
                try
                {
                    if (!node.isDeleted() && node.getNode().getName().equals(name))
                    {
                        return true;
                    }
                }
                catch (Exception e)
                {
                    log.debug(e);
                    return false;
                }
            }
        }
        return false;
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
        return this.nodesbyParent.containsKey(pathParent) && this.nodesbyParent.get(pathParent).size() > 0;
    }

    public boolean hasProperty(String path)
    {
        return this.properties.get(path) == null ? false : true;
        /*PropertyStatus propStatus=this.properties.get(path);
        if(propStatus==null)
        {
        return false;
        }
        if(propStatus.getProperty().definition.isProtected() || propStatus.isDeleted())
        {
        return false;
        }
        return true;*/
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
                        String pathParent = nodetoextract.getPath();
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
                        String pathParent = nodetoextract.getPath();
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
                        this.addNode(childNode, childpath, path);
                        return childNode;
                    }
                }
            }
        }
        return null;
    }

    Set<NodeImp> getProtectedChildNodes(String parenPath) throws RepositoryException
    {
        HashSet<NodeImp> getChilds = new HashSet<NodeImp>();
        HashSet<NodeStatus> childs = nodesbyParent.get(parenPath);
        if (childs != null && childs.size() > 0)
        {
            for (NodeStatus node : childs)
            {
                if (!node.isDeleted() && node.getNode().getDefinition().isProtected())
                {
                    getChilds.add(node.getNode());
                }
            }
        }
        return getChilds;
    }

    void clearDeletedChildNodes(String parenPath) throws RepositoryException
    {
        HashSet<NodeStatus> childs = nodesbyParent.get(parenPath);
        if (childs != null && childs.size() > 0)
        {
            for (NodeStatus node : childs)
            {
                if (node.isDeleted() && node.getNode().getDefinition().isProtected())
                {
                    nodes.remove(node.getNode().path);
                }
            }
        }
    }

    Set<NodeImp> getDeletedChildNodes(String parenPath) throws RepositoryException
    {
        HashSet<NodeImp> getChilds = new HashSet<NodeImp>();
        HashSet<NodeStatus> childs = nodesbyParent.get(parenPath);
        if (childs != null && childs.size() > 0)
        {
            for (NodeStatus node : childs)
            {
                if (node.isDeleted() && node.getNode().getDefinition().isProtected())
                {
                    getChilds.add(node.getNode());
                }
            }
        }
        return getChilds;
    }

    public Set<NodeImp> getChildNodes(String parenPath) throws RepositoryException
    {
        HashSet<NodeImp> getChilds = new HashSet<NodeImp>();
        HashSet<NodeStatus> childs = nodesbyParent.get(parenPath);
        if (childs != null && childs.size() > 0)
        {
            for (NodeStatus node : childs)
            {
                if (!node.isDeleted() && !node.getNode().getDefinition().isProtected())
                {
                    getChilds.add(node.getNode());
                }
            }
        }
        return getChilds;
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
                        this.addNode(childNode, childpath, path);
                    }
                }
            }
        }
    }

    public void loadChilds(NodeImp node, SessionImp session, boolean replace) throws RepositoryException
    {
        if (node.getSemanticObject() != null && !nodes.get(node.path).getAddChildLoaded())
        {
            GenericIterator<Base> childs = new Base(node.getSemanticObject()).listNodes();
            while (childs.hasNext())
            {
                Base child = childs.next();
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
                    NodeImp childNode = NodeImp.createNodeImp(child, node, childindex, childpath, session);
                    //NodeImp childNode=NodeImp.createNodeImp(child, node, childindex, childpath, session);
                    this.addNode(childNode, childpath, path);
                }
            }
            nodes.get(node.path).allChildLoaded();
        }
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

class NodeStatus
{

    private boolean deleted;
    private final NodeImp node;
    private boolean allchildLoaded = false;

    public NodeStatus(NodeImp node, boolean deleted)
    {
        this.deleted = deleted;
        this.node = node;
    }

    public boolean getAddChildLoaded()
    {
        return allchildLoaded;
    }

    public void allChildLoaded()
    {
        allchildLoaded = true;
    }

    public NodeStatus(NodeImp node)
    {
        this.deleted = false;
        this.node = node;
    }

    public NodeImp getNode()
    {
        return node;
    }

    public void delete()
    {
        this.deleted = true;
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
        final NodeStatus other = (NodeStatus) obj;
        if (this.node != other.node && (this.node == null || !this.node.equals(other.node)))
        {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode()
    {
        int hash = 7;
        hash = 67 * hash + (this.node != null ? this.node.hashCode() : 0);
        return hash;
    }

    public void restore()
    {
        this.deleted = false;
    }

    public boolean isDeleted()
    {
        return deleted;
    }
}

class PropertyStatus
{

    private boolean deleted;
    private final PropertyImp property;

    public PropertyStatus(PropertyImp property, boolean deleted)
    {
        this.deleted = deleted;
        this.property = property;
    }

    public PropertyStatus(PropertyImp property)
    {
        this.deleted = false;
        this.property = property;
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
        final PropertyStatus other = (PropertyStatus) obj;
        if (this.property != other.property && (this.property == null || !this.property.equals(other.property)))
        {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode()
    {
        int hash = 3;
        hash = 37 * hash + (this.property != null ? this.property.hashCode() : 0);
        return hash;
    }

    public PropertyImp getProperty()
    {
        return property;
    }

    public void delete()
    {
        this.deleted = true;
    }

    public void restore()
    {
        this.deleted = false;
    }

    public boolean isDeleted()
    {
        return deleted;
    }
}
