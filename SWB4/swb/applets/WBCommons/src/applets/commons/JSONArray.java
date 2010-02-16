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
public class JSONArray extends org.json.JSONArray
{

    public JSONArray()
    {
        super();
    }

    public JSONArray(String json) throws JSONException
    {
        super(json);
    }

    @Override
    public int length() {
        return super.length();
    }

    @Override
    public JSONObject getJSONObject(int index) throws JSONException {
        return new JSONObject(super.getJSONObject(index).toString());
    }



    public JSONArray put(JSONObject value)
    {
        super.put(value);
        return this;
    }


}
