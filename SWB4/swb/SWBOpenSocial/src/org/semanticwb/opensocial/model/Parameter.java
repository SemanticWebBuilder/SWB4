/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.opensocial.model;

import org.json.JSONObject;

/**
 *
 * @author victor.lorenzana
 */
public class Parameter
{
    String name;
    public JSONObject toJSONObject()
    {
        JSONObject obj = new JSONObject();
        try
        {
            obj.put("name", name);
        }
        catch(Exception e){}
        return obj;
    }
}
