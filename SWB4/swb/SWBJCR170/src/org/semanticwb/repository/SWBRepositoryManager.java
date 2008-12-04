/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.repository;

import org.semanticwb.repository.*;
import java.util.ArrayList;
import org.semanticwb.jcr170.implementation.RepositoryImp;
import javax.jcr.Repository;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.SimpleCredentials;
import org.semanticwb.Logger;
import org.semanticwb.SWBException;
import org.semanticwb.SWBUtils;

/**
 *
 * @author victor.lorenzana
 */
public final class SWBRepositoryManager implements RepositoryManager
{
    private static Logger log = SWBUtils.getLogger(SWBRepositoryManager.class);
    private RepositoryImp repository;
    public SWBRepositoryManager() throws SWBException,RepositoryException
    {
        repository=new RepositoryImp();
    }

    public void init()
    {
       log.event("Initilizing SWBRepositoryManager with repository "+ repository.getDescriptor(Repository.REP_NAME_DESC) +" ...");
    }
    public Repository getRepository()
    {
        return repository;
    }
    public ArrayList<String> getWorkspaces()
    {
        ArrayList<String> workspaces=new ArrayList<String>();
        for(String  workspace : repository.listWorkspaces())
        {
            workspaces.add(workspace);
        }
        return workspaces;
    }
    public Session openSession(String workspace,String id,String password) throws Exception
    {
        return repository.login(new SimpleCredentials(id, password.toCharArray()), workspace);
    }

    public String getName()
    {
        return "swb";
    }

    public boolean isUsedForOffice()
    {
        return true;
    }
}
