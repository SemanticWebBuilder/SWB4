/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.rdf;

import org.semanticwb.rdf.imp.ModelImp;

/**
 *
 * @author Jei
 */
public class ModelFactory {
        
    public Model createDefaultModel()
    {
        return new ModelImp(com.hp.hpl.jena.rdf.model.ModelFactory.createDefaultModel());
    }

}
