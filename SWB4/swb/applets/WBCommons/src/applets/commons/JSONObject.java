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
    @Override
    public JSONObject put(String key, Object value) throws JSONException
    {
        super.put(key, value);
        return this;
    }

    public JSONObject putOpt(String key, JSONArray value) throws JSONException
    {
        super.putOpt(key, value);
        return this;
    }

    @Override
    public String toString() {
        return super.toString();
    }

}
