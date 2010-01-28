/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.portal;

import org.semanticwb.model.Resource;

/**
 *
 * @author victor.lorenzana
 */
public interface FlowNotification {


    public void autorize(Resource resource);
    public void publish(Resource resource);
    public void noAutorize(Resource resource);
}
