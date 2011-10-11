/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.remotetriplestore;

/**
 *
 * @author javier.solis.g
 */
public interface RTransactionHandler 
{
    public void begin(Long id);
    public void abort(Long id);
    public void commit(Long id);    
}
