/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.platform;

import com.hp.hpl.jena.rdf.model.Statement;

/**
 *
 * @author javier.solis.g
 */
public interface SemanticTSObserver 
{
    /**
     * This method is called when information about an Semantic
     * which was previously requested using an asynchronous
     * interface becomes available.
     * 
     * @param obj the SemanticObject
     * @param stmt the Statement
     * @param action the action
     * @param remote the bollean
     */
    public void notify(SemanticObject obj, Statement stmt, String action, boolean remote);    
}
