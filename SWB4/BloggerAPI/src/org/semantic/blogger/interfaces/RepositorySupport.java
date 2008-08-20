/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semantic.blogger.interfaces;

import java.util.Map;
import javax.jcr.Repository;

/**
 *
 * @author victor.lorenzana
 */
public interface RepositorySupport
{

    public void setRepositories(Map<String, Repository> repositories);

    public boolean hasListOfRepositories();
}
