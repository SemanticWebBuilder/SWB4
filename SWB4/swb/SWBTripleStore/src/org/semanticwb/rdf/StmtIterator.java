/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.rdf;

import java.util.NoSuchElementException;

/**
 *
 * @author Jei
 */
public interface StmtIterator {
    
    /**
     * 
     * @return
     */
    public boolean hasNext();
    
    /** Return the next Statement of the iteration.
     * @throws NoSuchElementException if there are no more to be returned.
     * @return The next Resource from the iteration.
     */
    public Statement nextStatement() throws  NoSuchElementException;    

}
