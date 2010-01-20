/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.jcr283.implementation;

import java.util.HashSet;
import java.util.Hashtable;
import java.util.Set;
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

    public NodeImp getNode(String path)
    {
        NodeImp node = this.nodes.get(path);
        if (node == null)
        {
            //TODO: Try to load the node from database
        }
        return null;
    }

    public Set<NodeImp> getChilds(NodeImp node)
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
}
