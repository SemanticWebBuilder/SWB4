/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.rdf;

/**
 *
 * @author Jei
 */
public interface Statement 
{
    /** An accessor method to return the subject of the statements.
     * @return The subject of the statement.
     */
    public Resource getSubject();
    
    /** An accessor function to return the predicate of the statement.
     * @return The predicate of the statement.
     */
    public Property getPredicate();
    
    /** An accessor funtion to return the object of the statement.
     * @return Return the object of the statement.
     */
    public RDFNode getObject();    
}
