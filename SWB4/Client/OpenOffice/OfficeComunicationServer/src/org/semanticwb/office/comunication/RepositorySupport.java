/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.office.comunication;

import java.util.Map;
import javax.jcr.Repository;


/**
 *
 * @author victor.lorenzana
 */
public interface RepositorySupport 
{       
    public void setRepositories(Map<String,Repository> repositories);    
    public boolean hasListOfRepositories();
}
