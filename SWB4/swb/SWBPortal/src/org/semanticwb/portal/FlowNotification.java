/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.portal;

import org.semanticwb.model.Resource;

// TODO: Auto-generated Javadoc
/**
 * The Interface FlowNotification.
 * 
 * @author victor.lorenzana
 */
public interface FlowNotification 
{
    /**
     * Autorize.
     * 
     * @param resource the resource
     */
    public void autorize(Resource resource);
    
    /**
     * Publish.
     * 
     * @param resource the resource
     */
    public void publish(Resource resource);
    
    /**
     * No autorize.
     * 
     * @param resource the resource
     */
    public void noAutorize(Resource resource);
}
