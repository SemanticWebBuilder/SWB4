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
import javax.jcr.lock.LockException;
import javax.jcr.nodetype.ConstraintViolationException;
import javax.jcr.nodetype.NoSuchNodeTypeException;
import javax.jcr.version.VersionException;
import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
import org.semanticwb.jcr283.repository.model.Base;
import org.semanticwb.jcr283.repository.model.Unstructured;
import org.semanticwb.model.GenericIterator;

/**
 *
 * @author victor.lorenzana
 */
public class NodeManager
{

    private Hashtable<String, NodeImp> nodes = new Hashtable<String, NodeImp>();
    private Hashtable<String, PropertyImp> properties = new Hashtable<String, PropertyImp>();
    private Hashtable<String, NodeImp> nodesRemoved = new Hashtable<String, NodeImp>();
    private Hashtable<String, PropertyImp> propertiesRemoved = new Hashtable<String, PropertyImp>();
    private final static Logger log = SWBUtils.getLogger(NodeManager.class);

    private static int getIndex(Base node)
    {
        int getIndex = 0;
        GenericIterator<Base> childs = node.listNodes();
        while (childs.hasNext())
        {
            Base child = childs.next();
            if (child.getName().equals(node.getName()))
            {
                getIndex++;
                if (node.equals(child))
                {
                    return getIndex--;
                }
            }
        }
        return getIndex;
    }

    public NodeImp loadRoot(org.semanticwb.jcr283.repository.model.Workspace ws, SessionImp session)
    {
        if (ws.getRoot() == null)
        {
            Unstructured newroot = Unstructured.ClassMgr.createUnstructured("jcr:root", ws);
            ws.setRoot(newroot);
        }
        String path = "/";
        NodeImp root = new NodeImp(ws.getRoot(), null, 0, path, 0, session);
        nodes.put(path, root);
        return root;

    }

    public NodeImp getRoot()
    {
        return this.nodes.get("/");
    }

    public NodeImp addNode(NodeImp node, String path)
    {
        if (!this.nodes.containsKey(path))
        {
            this.nodes.put(path, node);
        }
        return this.nodes.get(path);
    }

    public PropertyImp addProperty(PropertyImp node, String path)
    {
        if (!this.properties.containsKey(path))
        {
            this.properties.put(path, node);
        }
        return this.properties.get(path);
    }

    /**
     *
     * @param path
     * @param exact
     * @return
     */
    public int countNodes(String path, boolean exact)
    {

        int countNodes = 0;
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
                        if (dif.startsWith("[") && dif.endsWith("]") && dif.indexOf("/") == -1)
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
        return countNodes;
    }

    public boolean hasNode(String path)
    {
        return this.nodes.get(path) == null ? false : true;
    }

    public void move(String oldPath, String newPath, NodeImp newParent)
    {
    }

    public void save() throws RepositoryException
    {
        nodes.get("/").save();
    }

    public void save(String path,int depth) throws AccessDeniedException, ItemExistsException, ConstraintViolationException, InvalidItemStateException, ReferentialIntegrityException, VersionException, LockException, NoSuchNodeTypeException, RepositoryException
    {
        if (nodes.containsKey(path))
        {
            NodeImp node = nodes.get(path);
            node.saveData();
            for(PropertyImp prop : getChildProperties(node))
            {
                prop.saveData();
            }
        }
        if (properties.containsKey(path))
        {            
            PropertyImp node = properties.get(path);
            node.saveData();
        }

    }

    public void saveProperties(String nodePath) throws AccessDeniedException, ItemExistsException, ConstraintViolationException, InvalidItemStateException, ReferentialIntegrityException, VersionException, LockException, NoSuchNodeTypeException, RepositoryException
    {
        if (nodes.containsKey(nodePath))
        {
            NodeImp node = nodes.get(nodePath);
            node.saveData();
        }
    }

    /**
     *
     * @param path path to found
     * @param exact true, sear exact path, false ignore the index of the node
     * @return
     */
    public boolean hasNode(String path, boolean exact)
    {
        if (exact)
        {
            return this.nodes.get(path) == null ? false : true;
        }
        else
        {
            if (!path.endsWith("/"))
            {
                path += "/";
            }
            for (String pathNode : nodes.keySet())
            {
                if (pathNode.startsWith(path))
                {
                    String dif = pathNode.substring(path.length());
                    if (!dif.equals(""))
                    {
                        if (dif.startsWith("[") && dif.endsWith("]") && dif.indexOf("/") == -1)
                        {
                            return true;
                        }
                    }
                }
            }
            return false;
        }
    }

    public boolean hasProperty(String path)
    {
        return this.properties.get(path) == null ? false : true;
    }

    public boolean hasProperty(String path, boolean exact)
    {
        if (exact)
        {
            return hasProperty(path);
        }
        else
        {
            if (!path.endsWith("/"))
            {
                path += "/";
            }
            for (String pathNode : properties.keySet())
            {
                if (pathNode.startsWith(path))
                {
                    String dif = pathNode.substring(path.length());
                    if (!dif.equals(""))
                    {
                        if (dif.indexOf("/") == -1)
                        {
                            return true;
                        }
                    }
                }
            }
            return false;
        }

    }

    public NodeImp getNode(String path)
    {
        NodeImp node = this.nodes.get(path);
        if (node == null)
        {
            //TODO: Try to load the node from database
        }
        return null;
    }

    public PropertyImp getProperty(String path)
    {
        PropertyImp node = this.properties.get(path);
        if (node == null)
        {
            //TODO: Try to load the node from database
        }
        return null;
    }

    private Set<PropertyImp> getChildProperties(String path,int depth)
    {
        HashSet<PropertyImp> getChilds = new HashSet<PropertyImp>();
        try
        {
            for (String pathNode : properties.keySet())
            {
                if (pathNode.startsWith(path))
                {
                    String dif = pathNode.substring(path.length());
                    if (!dif.equals(""))
                    {
                        PropertyImp prospect = properties.get(pathNode);
                        if (prospect.getDepth() == (depth + 1))
                        {
                            getChilds.add(prospect);
                        }
                    }
                }
            }
        }
        catch (Exception e)
        {
            log.debug(e);
        }
        return getChilds;
    }

    public Set<PropertyImp> getChildProperties(NodeImp node) throws RepositoryException
    {
        return getChildProperties(node.getPath(),node.getDepth());
    }

    public Set<NodeImp> getChildNodes(NodeImp node)
    {
        HashSet<NodeImp> getChilds = new HashSet<NodeImp>();
        try
        {
            for (String pathNode : nodes.keySet())
            {
                if (pathNode.startsWith(node.getPath()))
                {
                    String dif = pathNode.substring(node.getPath().length());
                    if (!dif.equals(""))
                    {
                        NodeImp prospect = nodes.get(pathNode);
                        if (prospect.getDepth() == (node.getDepth() + 1))
                        {
                            getChilds.add(prospect);
                        }
                    }
                }
            }
        }
        catch (Exception e)
        {
            log.debug(e);
        }
        return getChilds;
    }

    public void loadChilds(NodeImp node, String path, int depth, SessionImp session, boolean replace)
    {
        if (node.getSemanticObject() != null)
        {
            GenericIterator<Base> childs = new Base(node.getSemanticObject()).listNodes();
            while (childs.hasNext())
            {
                Base child = childs.next();
                int childindex = 0;
                childindex = getIndex(child);
                String childpath = null;

                if (path.endsWith("/"))
                {
                    childpath = child.getName();
                }
                else
                {
                    childpath = path + "/" + child.getName();
                }

                if (childindex > 0)
                {
                    childpath += "[" + childindex + "]";
                }
                if (replace || !nodes.containsKey(childpath))
                {
                    NodeImp childNode = new NodeImp(child, node, childindex, childpath, depth + 1, session);
                    nodes.put(path, childNode);
                }
            }
        }
    }

    public void removeProperty(String path)
    {
        if (properties.containsKey(path))
        {
            propertiesRemoved.put(path, properties.remove(path));
        }

    }

    public void removeNode(String path)
    {
        if (nodes.containsKey(path))
        {
            nodesRemoved.put(path, nodes.remove(path));
        }

    }
}
