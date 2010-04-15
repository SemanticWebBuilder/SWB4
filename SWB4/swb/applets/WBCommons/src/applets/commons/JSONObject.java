/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package applets.commons;

import org.json.JSONException;

/**
 *
 * @author javier.solis
 */
public class JSONObject extends org.json.JSONObject
{

    public JSONObject()
    {

    }

    public JSONObject(String json) throws JSONException
    {
        super(json);
    }
    

    @Override
    public JSONObject put(String key, Object value) throws JSONException
    {
        if(value!=null)super.put(key, value);
        return this;
    }

    public JSONObject putOpt(String key, JSONArray value) throws JSONException
    {
        super.putOpt(key, value);
        return this;
    }

    @Override
    public String getString(String key){
        String ret=null;
        try
        {
            ret=super.getString(key);
        }catch(Exception noe){}
        return ret;
    }

    @Override
    public int getInt(String key) throws JSONException {
        int ret=0;
        try
        {
            ret=super.getInt(key);
        }catch(Exception noe){}
        return ret;
    }



    @Override
    public String toString() {
        return super.toString();
    }

    @Override
    public JSONArray getJSONArray(String key) throws JSONException
    {
        return new JSONArray(super.getJSONArray(key).toString());
    }

    

}
