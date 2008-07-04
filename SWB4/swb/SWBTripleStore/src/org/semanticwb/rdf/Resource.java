/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.rdf;

/**
 *
 * @author Jei
 */
public interface Resource extends RDFNode {

    
    /** Add a property to this resource.
     *
     * <p>A statement with this resource as the subject, p as the predicate and o
     * as the object is added to the model associated with this resource.</p>
     * @param prop The property to be added.
     * @param value The value of the property to be added.
     * @return This resource to allow cascading calls.
     */
    public Resource addProperty( Property prop, String value );   
    
    
    /** Add a property to this resource.
     *
     * <p>A statement with this resource as the subject, p as the predicate and o
     * as the object is added to the model associated with this resource.</p>
     * @param prop The property to be added.
     * @param value The value of the property to be added.
     * @param lang the language of the property
     * @return This resource to allow cascading calls.
     */
    public Resource addProperty( Property prop, String value, String lang );  
    
    
    /** Add a property to this resource.
     *
     * <p>A statement with this resource as the subject, p as the predicate and o
     * as the object is added to the model associated with this resource.</p>
     * @param prop The property to be added.
     * @param node The value of the property to be added.
     * @return This resource to allow cascading calls.
     */
    public Resource addProperty( Property prop, RDFNode node );    
}
