/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.office.comunication;

import javax.jcr.Session;


/**
 *
 * @author victor.lorenzana
 */
public interface RepositorySupport {
    public void setSession(Session session);
    public void logout();
}
