/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.xmlrpc;

import org.semanticwb.xmlrpc.*;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author victor.lorenzana
 */
public class Response {
    private Object response;
    private Set<Part> responseParts=new HashSet<Part>();
    public Response(Object response)
    {
        this.response=response;
    }
    public Response(Object response,Set<Part> responseParts)
    {
        this.response=response;
        this.responseParts=responseParts;
    }
    public Object getObject()
    {
        return response;
    }
    public Set<Part> getResponseParts()
    {
        return responseParts;
    }
}
