/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.webservices;

import org.json.JSONObject;

/**
 *
 * @author victor.lorenzana
 */
public interface Operation
{
    public String getName();    
    public Request getInput();
    public Response getOutput();
    public JSONObject execute(JSONObject object) throws ServiceException;
}
