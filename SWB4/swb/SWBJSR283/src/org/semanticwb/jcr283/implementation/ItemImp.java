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
public abstract class ItemImp implements Item{

    private final NodeImp parent;
    private final String name;
    protected boolean isNew;
    public ItemImp(SemanticProperty prop,NodeImp parent)
    {
        this.name=prop.getPrefix()+":"+prop.getName();
        this.parent=parent;
        isNew=false;
    }
    public ItemImp(SemanticObject obj,String name,NodeImp parent)
    {
        this.name=name;
        this.parent=parent;
        isNew=false;
    }

    public ItemImp(String name,NodeImp parent)
    {
        this.name=name;
        this.parent=parent;
    }
    
    public String getPath() throws RepositoryException
    {
        return parent.getPath()+"/"+this.getName();
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

    public abstract  boolean isNode();
    

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
