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
public class PUTMethod extends Method {

    PUTMethod(String id,Resource resource)
    {
        super(id,HTTPMethod.PUT,resource);
    }

    @Override
    public RepresentationResponse execute(Map<String, Object> values) throws ExecutionRestException
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
