/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.jcr170.implementation;

import java.util.Hashtable;
import javax.jcr.AccessDeniedException;
import javax.jcr.NamespaceException;
import javax.jcr.NamespaceRegistry;
import javax.jcr.RepositoryException;
import javax.jcr.UnsupportedRepositoryOperationException;
import org.semanticwb.model.SWBContext;
import org.semanticwb.model.SWBVocabulary;

/**
 *
 * @author victor.lorenzana
 */
public class NamespaceRegistryImp implements NamespaceRegistry
{
    
    private final Hashtable<String,String> namespaces;
    NamespaceRegistryImp()
    {           
        SWBVocabulary vocabulary=SWBContext.getVocabulary();
        namespaces=vocabulary.listUris();
    }
    
    public void registerNamespace(String prefix, String uri) throws NamespaceException, UnsupportedRepositoryOperationException, AccessDeniedException, RepositoryException
    {        
        throw new UnsupportedRepositoryOperationException("Not supported yet.");
    }

    public void unregisterNamespace(String arg0) throws NamespaceException, UnsupportedRepositoryOperationException, AccessDeniedException, RepositoryException
    {
        throw new UnsupportedRepositoryOperationException("Not supported yet.");
    }

    public String[] getPrefixes() throws RepositoryException
    {
        return namespaces.keySet().toArray(new String[namespaces.keySet().size()]);
    }

    public String[] getURIs() throws RepositoryException
    {
        return namespaces.values().toArray(new String[namespaces.keySet().size()]);
    }

    public String getURI(String prefix) throws NamespaceException, RepositoryException
    {
        return namespaces.get(prefix);
    }

    public String getPrefix(String uri) throws NamespaceException, RepositoryException
    {
        for(String prefix : this.namespaces.keySet())
        {
            String namespace=namespaces.get(prefix);
            if(namespace.equals(uri))
            {
                return prefix;
            }
        }
        throw new NamespaceException("the uri "+ uri +" was not found");
    }
}
