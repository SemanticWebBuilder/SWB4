/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.jcr283.implementation;

import java.util.Map;
import javax.jcr.Repository;
import javax.jcr.RepositoryException;
import javax.jcr.RepositoryFactory;

/**
 *
 * @author victor.lorenzana
 */
public final class RepositoryFactoryImp implements RepositoryFactory {

    public Repository getRepository(Map parameters) throws RepositoryException
    {
        return new SWBRepository();
    }

}
