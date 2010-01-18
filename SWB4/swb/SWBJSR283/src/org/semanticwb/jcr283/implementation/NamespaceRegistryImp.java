/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.jcr283.implementation;

import javax.jcr.AccessDeniedException;
import javax.jcr.NamespaceException;
import javax.jcr.NamespaceRegistry;
import javax.jcr.RepositoryException;
import javax.jcr.UnsupportedRepositoryOperationException;

/**
 *
 * @author victor.lorenzana
 */
public class NamespaceRegistryImp implements NamespaceRegistry {

    
    public void registerNamespace(String prefix, String uri) throws NamespaceException, UnsupportedRepositoryOperationException, AccessDeniedException, RepositoryException
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void unregisterNamespace(String prefix) throws NamespaceException, UnsupportedRepositoryOperationException, AccessDeniedException, RepositoryException
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public String[] getPrefixes() throws RepositoryException
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public String[] getURIs() throws RepositoryException
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public String getURI(String prefix) throws NamespaceException, RepositoryException
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public String getPrefix(String uri) throws NamespaceException, RepositoryException
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
