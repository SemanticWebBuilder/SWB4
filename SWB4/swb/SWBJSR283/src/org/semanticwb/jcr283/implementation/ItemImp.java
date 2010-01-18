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

    private final NodeImp parent;
    private final String name;
    protected boolean isNew;
    private String path;

    public ItemImp(SemanticProperty prop, NodeImp parent, String path)
    {
        this(prop.getPrefix() + ":" + prop.getName(), parent, path);
        isNew = false;
    }

    public ItemImp(SemanticObject obj, String name, NodeImp parent, String path)
    {
        this(name, parent, path);
        isNew = false;
    }

    public ItemImp(String name, NodeImp parent, String path)
    {
        this.name = name;
        this.parent = parent;
        this.path = path;
    }

    @Override
    public String toString()
    {
        return name;
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
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Session getSession() throws RepositoryException
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public abstract boolean isNode();

    public boolean isNew()
    {
        return isNew;
    }

    public boolean isModified()
    {
        throw new UnsupportedOperationException("Not supported yet.");
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
        throw new UnsupportedOperationException("Not supported yet.");
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
