/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.jcr283.implementation;

import javax.jcr.AccessDeniedException;
import javax.jcr.InvalidItemStateException;
import javax.jcr.Item;
import javax.jcr.ItemExistsException;
import javax.jcr.ItemNotFoundException;
import javax.jcr.ItemVisitor;
import javax.jcr.Node;
import javax.jcr.ReferentialIntegrityException;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.lock.LockException;
import javax.jcr.nodetype.ConstraintViolationException;
import javax.jcr.nodetype.NoSuchNodeTypeException;
import javax.jcr.version.VersionException;

/**
 *
 * @author victor.lorenzana
 */
public abstract class ItemImp implements Item
{

    protected static final String PATH_SEPARATOR = "/";
    protected static final String THE_PATH_IS_NOT_RELATIVE = "The path is not relative :";
    protected NodeImp parent;
    protected final String name;
    protected boolean isNew;
    protected final String path;
    protected boolean isModified = false;
    protected final int depth;
    protected final NodeManager nodeManager;
    protected final SessionImp session;
    protected final ItemDefinitionImp definition;
    protected final ValueFactoryImp valueFactoryImp;
    protected final String id;
    public ItemImp(ItemDefinitionImp definition, String name, NodeImp parent, String path, int depth, SessionImp session,String id)
    {
        this.name = name;
        this.parent = parent;
        this.path = path;
        this.depth = depth;
        this.session = session;
        this.definition = definition;
        nodeManager = session.getWorkspaceImp().getNodeManager();
        this.valueFactoryImp=session.getValueFactoryImp();
        this.id=id;

    }

    public String getPathFromName(String name)
    {
        String pathProperty = path + PATH_SEPARATOR + name;
        if (path.endsWith(PATH_SEPARATOR))
        {
            pathProperty = path + name;
        }
        return pathProperty;
    }

    @Override
    public String toString()
    {
        return path;
    }

    public static boolean isValidRelativePath(String relPath)
    {
        if (relPath.startsWith(PATH_SEPARATOR))
        {
            return false;
        }
        return isPathSegment(relPath);
    }

    public static boolean isValidAbsPath(String relPath)
    {
        return true;
    }

    public static boolean isPathSegment(String pathsegment)
    {
        return true;
    }

    public static String extractName(String relpath) throws RepositoryException
    {
        String extractName = relpath;
        if (extractName.endsWith(PATH_SEPARATOR))
        {
            extractName = extractName.substring(0, extractName.length() - 1);
        }
        String name = relpath;
        int pos = name.lastIndexOf(PATH_SEPARATOR);
        if (pos != -1)
        {
            name = name.substring(pos + 1);
        }
        pos = name.lastIndexOf("[");
        if (pos != -1)
        {
            name = name.substring(0, pos);
        }
        if (!isValidName(name))
        {
            throw new RepositoryException("The name is not valid");
        }
        return name;
    }

    public static boolean isValidName(String name)
    {
        if (name == null || name.trim().equals(""))
        {
            return false;
        }
        return true;
    }

    public String normalizePath(String relPath) throws RepositoryException
    {
        if (!isValidRelativePath(relPath))
        {
            throw new RepositoryException(THE_PATH_IS_NOT_RELATIVE + relPath);
        }
        if (relPath.startsWith("./"))
        {
            relPath = this.getPath() + relPath;

        }
        if (relPath.startsWith("../"))
        {
            if (this.parent != null)
            {
                String newRelativePath = relPath.substring(1);
                return this.parent.normalizePath(newRelativePath);
            }
        }
        String newpath = relPath;
        int pos = newpath.indexOf("../");
        while (pos != -1)
        {
            String end = newpath.substring(pos + 3);
            newpath = newpath.substring(0, pos);
            if (newpath.endsWith(PATH_SEPARATOR))
            {
                newpath = newpath.substring(0, newpath.length() - 1);
            }
            pos = newpath.lastIndexOf(PATH_SEPARATOR);
            newpath = newpath.substring(0, pos + 1);
            if (newpath.endsWith(PATH_SEPARATOR))
            {
                newpath += end;
            }
            else
            {
                newpath += PATH_SEPARATOR + end;
            }
            pos = newpath.indexOf("../");
        }
        pos = newpath.indexOf("./");
        while (pos != -1)
        {
            newpath = newpath.substring(0, pos) + newpath.substring(pos + 2);
            pos = newpath.indexOf("./");
        }


        if (newpath.endsWith(PATH_SEPARATOR))
        {
            newpath = newpath.substring(0, newpath.length() - 1);
        }
        if (path.endsWith("/"))
        {
            newpath = path + newpath;
        }
        else
        {
            newpath = path + "/" + newpath;
        }
        if (newpath.endsWith("/") && newpath.length() > 1)
        {
            newpath = newpath.substring(0, newpath.length() - 1);
        }
        return newpath;
    }

    public String getPath() throws RepositoryException
    {
        return path;
    }

    public String getName() throws RepositoryException
    {
        return name;
    }

    public Item getAncestor(int depth) throws ItemNotFoundException, AccessDeniedException, RepositoryException
    {
        if(this.getDepth()<depth || depth<0)
        {
            throw new ItemNotFoundException();
        }
        String[] paths=path.split("/");
        StringBuffer pathItem=new StringBuffer();
        if(depth==0)
        {
            pathItem.append("/");
        }
        else
        {
        for(int i=0;i<=depth;i++)
        {
            if(i>0)
            {
                pathItem.append("/"+paths[i]);
            }            
        }
        }
        if(pathItem.toString().equals(""))
        {
            throw new ItemNotFoundException();
        }
        Item item=session.getItem(pathItem.toString());
        if(item==null)
        {
            throw new ItemNotFoundException();
        }
        return item;
    }

    public Node getParent() throws ItemNotFoundException, AccessDeniedException, RepositoryException
    {
        return parent;
    }

    public int getDepth() throws RepositoryException
    {
        return depth;
    }

    public Session getSession() throws RepositoryException
    {
        return session;
    }

    public abstract boolean isNode();

    public boolean isNew()
    {
        return isNew;
    }

    public boolean isModified()
    {
        return isModified;
    }

    public boolean isSame(Item otherItem) throws RepositoryException
    {
        return this.equals(otherItem);
    }

    public abstract void accept(ItemVisitor visitor) throws RepositoryException;

   

    @Deprecated
    public void save() throws AccessDeniedException, ItemExistsException, ConstraintViolationException, InvalidItemStateException, ReferentialIntegrityException, VersionException, LockException, NoSuchNodeTypeException, RepositoryException
    {
        if (this.definition.isProtected())
        {
            throw new ConstraintViolationException("The item " + path + "  is protecetd");
        }
        if(parent!=null && parent.getSemanticObject()==null)
        {
            throw new ConstraintViolationException("The parent of the item " + path + "  is new");
        }
        nodeManager.save(path, depth);
        this.isNew = false;
    }
    public abstract void validate() throws ConstraintViolationException,RepositoryException;

    public void refresh(boolean keepChanges) throws InvalidItemStateException, RepositoryException
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void remove() throws VersionException, LockException, ConstraintViolationException, AccessDeniedException, RepositoryException
    {
        if (!this.definition.isProtected())
        {
            throw new ConstraintViolationException("The item " + path + "  is protecetd");
        }

        if (parent != null)
        {
            if (!parent.definition.isProtected())
            {
                throw new ConstraintViolationException("The item " + parent.path + " is protected");
            }
            String parentPath = parent.getPath();
            nodeManager.removeNode(path, parentPath);
            nodeManager.removeProperty(path, parentPath);
        }        
    }
}
