/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.remotetriplestore;

import com.hp.hpl.jena.graph.TransactionHandler;
import com.hp.hpl.jena.graph.Triple;

/**
 *
 * @author javier.solis.g
 */
public interface RGraph 
{
    public void performAdd(Triple t, Long id);

    public void performDelete(Triple t, Long id); 
    
    public TransactionHandler getTransactionHandler();
    
}
