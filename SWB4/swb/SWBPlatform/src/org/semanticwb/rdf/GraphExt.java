/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.triplestore.ext;

import com.hp.hpl.jena.graph.Triple;
import com.hp.hpl.jena.graph.TripleMatch;
import com.hp.hpl.jena.util.iterator.ExtendedIterator;

/**
 *
 * @author javier.solis.g
 */
public interface GraphExt
{
    /**
     * Conteo de triples
     * @param tm TripleMatch
     * @param stype Tipo de Clase obtenido del URI
     * @return Iterador de Triples
     */
    public long count(TripleMatch tm, String stype);
    
    /**
     * Busquedas extendidas
     * @param tm TripleMatch
     * @param stype Tipo de Clase obtenido del URI
     * @param limit Liminte de Resultados
     * @param offset Desplazamiento de los resultados
     * @param sortby ordenado por "subj", "prop", "obj", "timems"
     * @return Iterador de Triples
     */
    public ExtendedIterator<Triple> find(TripleMatch tm, String stype, Long limit, Long offset, String sortby);
        
    
}
