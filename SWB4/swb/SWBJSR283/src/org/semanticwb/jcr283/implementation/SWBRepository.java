/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.jcr283.implementation;

import java.util.Hashtable;
import javax.jcr.Credentials;
import javax.jcr.LoginException;
import javax.jcr.NoSuchWorkspaceException;
import javax.jcr.PropertyType;
import javax.jcr.Repository;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.Value;
import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;


/**
 *
 * @author victor.lorenzana
 */
public class SWBRepository implements Repository {

    private static Logger log = SWBUtils.getLogger(SWBRepository.class);
    private static Hashtable<String, Value> descriptors = new Hashtable<String, Value>();    
    static
    {
        new NodeTypeManagerImp();
        descriptors.put(OPTION_VERSIONING_SUPPORTED, new ValueImp(false,PropertyType.BOOLEAN));
        descriptors.put(SPEC_VERSION_DESC, new ValueImp("2.0",PropertyType.STRING));
        descriptors.put(SPEC_NAME_DESC, new ValueImp("Content Repository for Java Technology API",PropertyType.STRING));
        descriptors.put(REP_VENDOR_DESC, new ValueImp("Semantic INFOTEC WebBuilder 4.0",PropertyType.STRING));
        descriptors.put(REP_VENDOR_URL_DESC, new ValueImp("http://www.webbuilder.org.mx",PropertyType.STRING));
        descriptors.put(REP_NAME_DESC, new ValueImp("Semantic INFOTEC WebBuilder 4.0 Repository",PropertyType.STRING));
        descriptors.put(REP_VERSION_DESC, new ValueImp("1.0.0.0",PropertyType.STRING));        
        descriptors.put(OPTION_TRANSACTIONS_SUPPORTED, new ValueImp(false,PropertyType.BOOLEAN));
        descriptors.put(OPTION_OBSERVATION_SUPPORTED, new ValueImp(false,PropertyType.BOOLEAN));
        descriptors.put(OPTION_LOCKING_SUPPORTED, new ValueImp(true,PropertyType.BOOLEAN));        

    }
    
    public String[] getDescriptorKeys()
    {
        return descriptors.keySet().toArray(new String[descriptors.keySet().size()]);
    }

    public boolean isStandardDescriptor(String key)
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public boolean isSingleValueDescriptor(String key)
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Value getDescriptorValue(String key)
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Value[] getDescriptorValues(String key)
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public String getDescriptor(String key)
    {
        try
        {
            return descriptors.get(key).getString();
        }
        catch(Exception e){
            return null;        
        }
    }

    public Session login(Credentials credentials, String workspaceName) throws LoginException, NoSuchWorkspaceException, RepositoryException
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Session login(Credentials credentials) throws LoginException, RepositoryException
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Session login(String workspaceName) throws LoginException, NoSuchWorkspaceException, RepositoryException
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Session login() throws LoginException, RepositoryException
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
