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
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.platform.SemanticProperty;

/**
 *
 * @author victor.lorenzana
 */
public abstract class ItemImp implements Item
{
    protected static final String PATH_SEPARATOR = "/";
    protected static final String THE_PATH_IS_NOT_RELATIVE = "The path is not relative :";
    protected NodeImp parent;
    private final String name;
    private boolean isNew;
    protected final String path;
    protected boolean isModified=false;
    protected final int depth;
    protected final NodeManager nodeManager;
    protected final SessionImp session;



    public ItemImp(SemanticProperty prop, NodeImp parent, String path,int depth,SessionImp session)
    {
        this(prop.getPrefix() + ":" + prop.getName(), parent, path,depth,session);
        isNew = false;        
    }

    public ItemImp(SemanticObject obj, String name, NodeImp parent, String path,int depth,SessionImp session)
    {
        this(name, parent, path,depth,session);
        isNew = false;
    }

    public ItemImp(String name, NodeImp parent, String path,int depth,SessionImp session)
    {
        this.name = name;
        this.parent = parent;
        this.path = path;
        this.depth=depth;
        this.session=session;
        nodeManager = session.getWorkspaceImp().getNodeManager();
    }


    public String getPropertyPath(String name)
    {
        String pathProperty = path + PATH_SEPARATOR + name;
        if (pathProperty.endsWith(PATH_SEPARATOR))
        {
            pathProperty = path + name;
        }
        return pathProperty;
    }
    
    @Override
    public String toString()
    {
        return name;
    }

    public static boolean isValidRelativePath(String relPath)
    {
        if (relPath.startsWith(PATH_SEPARATOR))
        {
            return false;
        }
        return isPathSegment(relPath);
    }

    public static boolean isPathSegment(String pathsegment)
    {
        return true;
    }

    public static String extractName(String relpath)
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
        return name;
    }

    public String normalizePath(String relPath) throws RepositoryException
    {
        if (isValidRelativePath(relPath))
        {
            throw new RepositoryException(THE_PATH_IS_NOT_RELATIVE + relPath);
        }
        if (relPath.startsWith("./"))
        {
            String absPath = this.getPath() + relPath;
            return absPath;
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
            pos = newpath.lastIndexOf(PATH_SEPARATOR);
            newpath = newpath.substring(0, pos + 1);
            newpath += end;
            pos = newpath.indexOf("/../");
        }
        if (newpath.endsWith(PATH_SEPARATOR))
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
        throw new UnsupportedOperationException("Not supported yet.");
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
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void accept(ItemVisitor visitor) throws RepositoryException
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void save() throws AccessDeniedException, ItemExistsException, ConstraintViolationException, InvalidItemStateException, ReferentialIntegrityException, VersionException, LockException, NoSuchNodeTypeException, RepositoryException
    {
        nodeManager.save(path);
        this.isNew=false;        
    }

    public void refresh(boolean keepChanges) throws InvalidItemStateException, RepositoryException
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void remove() throws VersionException, LockException, ConstraintViolationException, AccessDeniedException, RepositoryException
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
