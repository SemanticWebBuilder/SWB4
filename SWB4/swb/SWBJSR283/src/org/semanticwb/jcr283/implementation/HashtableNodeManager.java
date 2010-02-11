/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.jcr283.implementation;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Set;
import javax.jcr.RepositoryException;

import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;


import org.semanticwb.jcr283.repository.model.Base;
import org.semanticwb.jcr283.repository.model.Workspace;
import org.semanticwb.model.GenericIterator;

/**
 *
 * @author victor.lorenzana
 */
public final class HashtableNodeManager extends Hashtable<String, NodeStatus>
{
    private static final String MIX_REFERENCEABLE = "mix:referenceable";

    private static final String PATH_SEPARATOR = "/";
    private final static Logger log = SWBUtils.getLogger(HashtableNodeManager.class);
    private Hashtable<String, NodeStatus> nodesbyId = new Hashtable<String, NodeStatus>();
    private Hashtable<String, HashSet<NodeStatus>> nodesbyParent = new Hashtable<String, HashSet<NodeStatus>>();
    private Hashtable<String, NodeStatus> nodesByUUID = new Hashtable<String, NodeStatus>();
    //private final SessionImp session;
    private final LockManagerImp lockManager;

    public HashtableNodeManager(SessionImp session)
    {
        //this.session = session;
        lockManager = session.getWorkspaceImp().getLockManagerImp();
    }

    public NodeImp getNodeByIdentifier(String id, SessionImp session, NodeTypeImp nodeTypeToSeach) throws RepositoryException
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
                        this.put(childpath, new NodeStatus(temp, session));
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

    void clearDeletedChildNodes(String parenPath) throws RepositoryException
    {
        HashSet<NodeStatus> childs = nodesbyParent.get(parenPath);
        if (childs != null && childs.size() > 0)
        {
            for (NodeStatus node : childs)
            {
                if (node.isDeleted() && node.getNode().getDefinition().isProtected())
                {
                    this.remove(node.getNode().path);
                }
            }
        }
    }

    @Override
    public synchronized NodeStatus remove(Object key)
    {
        NodeStatus nodeStatus = this.get(key.toString());
        if (nodeStatus != null)
        {
            try
            {
                if (nodeStatus.getNode().isNodeType(MIX_REFERENCEABLE))
                {
                    String uuid = nodeStatus.getNode().getUUID();
                    nodesByUUID.remove(uuid);
                }
            }
            catch (Exception e)
            {
                log.error(e);
            }
            if(nodeStatus.getNode().parent!=null)
            {
                String pathParent=nodeStatus.getNode().parent.path;
                HashSet<NodeStatus> childs=nodesbyParent.get(pathParent);
                if(childs!=null && childs.contains(nodeStatus))
                {
                    childs.remove(nodeStatus);
                }
            }
            nodesbyId.remove(nodeStatus.getNode().id);
        }
        return super.remove(key.toString());
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

    public void loadChilds(NodeImp node, SessionImp session, boolean replace) throws RepositoryException
    {
        if (node.getSemanticObject() != null && !this.get(node.path).getAddChildLoaded())
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
                if (replace || !this.containsKey(childpath))
                {
                    NodeImp childNode = NodeImp.createNodeImp(child, node, childindex, childpath, session);
                    put(childpath, new NodeStatus(childNode, session));
                }
            }
            this.get(node.path).allChildLoaded();
        }
    }

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
    }

    @Override
    public synchronized NodeStatus put(String path, NodeStatus nodestatus)
    {
        nodesbyId.put(nodestatus.getNode().id, nodestatus);
        if (nodestatus.getNode().parent != null && nodestatus.getNode().parent.path != null)
        {
            String pathParent = nodestatus.getNode().parent.path;
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
            NodeStatus parent = this.get(pathParent);
            if (parent.isLocked() && parent.getLock().isDeep())
            {
                try
                {
                    lockManager.lockParent(path);
                }
                catch (Exception e)
                {
                    log.error(e);
                }
            }
        }
        try
        {
            if (nodestatus.getNode().isNodeType(MIX_REFERENCEABLE))
            {
                nodesByUUID.put(nodestatus.getNode().getUUID(), nodestatus);
            }
        }
        catch (Exception e)
        {
            log.error(e);

        }
        return super.put(path, nodestatus);
    }

    public boolean hasChildNodes(String pathParent)
    {
        return this.nodesbyParent.containsKey(pathParent) && this.nodesbyParent.get(pathParent).size() > 0;
    }
}
