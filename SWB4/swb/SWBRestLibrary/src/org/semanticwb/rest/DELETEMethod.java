/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.rest;

import java.util.Map;

/**
 *
 * @author victor.lorenzana
 */
public class DELETEMethod extends Method{

    DELETEMethod(String id,Resource resource)
    {
        super(id,HTTPMethod.DELETE,resource);
    }

    @Override
    public RepresentationResponse execute(Map<String, Object> values) throws ExecutionRestException
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    
}
