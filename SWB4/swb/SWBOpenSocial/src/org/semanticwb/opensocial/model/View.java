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
    
    public String getName()
    {
        return name;
    }
    @Override
    public boolean equals(Object obj)
    {
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        final View other = (View) obj;
        if ((this.name == null) ? (other.name != null) : !this.name.equals(other.name))
            return false;
        return true;
    }

    @Override
    public int hashCode()
    {
        int hash = 7;
        hash = 89 * hash + (this.name != null ? this.name.hashCode() : 0);
        return hash;
    }


    public JSONObject toJSONObject()
    {        
        JSONObject content=new JSONObject();
        try
        {
            content.put("preferredHeight", preferredHeight);
            content.put("type", type);
            content.put("quirks", scrolling);
            content.put("preferredWidth", preferredWidth);
            
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return content;
    }
}
