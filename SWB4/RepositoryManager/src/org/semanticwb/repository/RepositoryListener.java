/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.repository;

import javax.jcr.Repository;
import javax.jcr.Session;

/**
 *
 * @author victor.lorenzana
 */
public interface RepositoryListener
{

    public void onShutDown(Repository repository);

    public void onOpenSession(Session session);
}
