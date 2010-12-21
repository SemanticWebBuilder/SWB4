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
public class FeatureDetail
{

    String name;
    Parameter[] parameters;
    boolean required = true;

    public JSONObject toJSONObject()
    {
        JSONObject obj = new JSONObject();
        try
        {
            for (Parameter parameter : parameters)
            {
                obj.put("parameters", parameter.toJSONObject());
            }
            obj.put("required", required);
        }
        catch (Exception e)
        {
        }
        return obj;
    }

    @Override
    public String toString()
    {
        return toJSONObject().toString();
    }

}
