/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.platform;

/**
 *
 * @author javier.solis
 */
public interface SemanticObserver
{
    public void notify(SemanticObject obj, Object prop, String action);
}
