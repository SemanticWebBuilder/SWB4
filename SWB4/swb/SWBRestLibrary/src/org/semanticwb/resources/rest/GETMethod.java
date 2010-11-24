/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.resources.rest;

import java.util.Map;
import org.w3c.dom.Document;

/**
 *
 * @author victor.lorenzana
 */
public final class GETMethod extends Method
{

    GETMethod(String id,Resource resource)
    {
        super(id,HTTPMethod.GET,resource);
    }

    @Override
    public RepresentationResponse execute(Map<String, Object> values) throws ExecutionRestException
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
