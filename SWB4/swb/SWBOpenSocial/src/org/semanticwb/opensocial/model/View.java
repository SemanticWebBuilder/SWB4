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
public class View {
    String name;
    int preferredHeight;
    String type;
    boolean scrolling;
    int preferredWidth;


    public JSONObject toJSONObject()
    {
        JSONObject obj=new JSONObject();
        JSONObject content=new JSONObject();
        try
        {
            content.put("preferredHeight", preferredHeight);
            content.put("type", type);
            content.put("quirks", scrolling);
            content.put("preferredWidth", preferredWidth);
            obj.put(name, content);
        }
        catch(Exception e)
        {
            
        }
        return obj;
    }
}
