/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.jcr283.implementation;

import java.util.HashMap;
import javax.jcr.AccessDeniedException;
import javax.jcr.NamespaceException;
import javax.jcr.NamespaceRegistry;
import javax.jcr.RepositoryException;
import javax.jcr.UnsupportedRepositoryOperationException;
import org.semanticwb.Logger;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBUtils;

/**
 *
 * @author victor.lorenzana
 */
public final class NamespaceRegistryImp implements NamespaceRegistry {

    private static Logger log = SWBUtils.getLogger(NamespaceRegistryImp.class);
    private final HashMap<String,String> values =new HashMap<String, String>();
    public NamespaceRegistryImp()
    {
        values.put(PREFIX_JCR, NAMESPACE_JCR);
        values.put(PREFIX_MIX, NAMESPACE_MIX);
        values.put(PREFIX_NT, NAMESPACE_NT);
        values.put(PREFIX_XML, NAMESPACE_XML);        
        HashMap<String,String>  nsPrefixMap=SWBPlatform.getSemanticMgr().getVocabulary().getNsPrefixMap();
        for(String key : nsPrefixMap.keySet())
        {
            String value=nsPrefixMap.get(key);
            log.trace("Loading prefix "+ key +" for uri "+value);
            values.put(key, value);
        }
    }
    public void registerNamespace(String prefix, String uri) throws NamespaceException, UnsupportedRepositoryOperationException, AccessDeniedException, RepositoryException
    {        
        if(values.containsKey(prefix))
        {
            throw new NamespaceException("The prefix was already registered");
        }
        values.put(prefix, uri);
    }

    public void unregisterNamespace(String prefix) throws NamespaceException, UnsupportedRepositoryOperationException, AccessDeniedException, RepositoryException
    {
        if(SWBPlatform.getSemanticMgr().getVocabulary().getNsPrefixMap().containsKey(prefix))
        {
            throw new AccessDeniedException("The prefix can not be removed");
        }
        values.remove(prefix);
    }

    public String[] getPrefixes() throws RepositoryException
    {
        return values.keySet().toArray(new String[values.keySet().size()]);
    }

    public String[] getURIs() throws RepositoryException
    {
        return values.values().toArray(new String[values.values().size()]);
    }

    public String getURI(String prefix) throws NamespaceException, RepositoryException
    {
        return values.get(prefix);
    }

    public String getPrefix(String uri) throws NamespaceException, RepositoryException
    {
        String getPrefix=null;
        for(String key : values.keySet())
        {
            String urivalue=values.get(key);
            if(urivalue.equals(uri))
            {
                getPrefix=key;
                break;
            }
        }
        return getPrefix;
    }

}
