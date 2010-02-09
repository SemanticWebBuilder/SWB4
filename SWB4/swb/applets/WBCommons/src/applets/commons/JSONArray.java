/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package applets.commons;

/**
 *
 * @author javier.solis
 */
public class JSONArray extends org.json.JSONArray
{

    public JSONArray put(JSONObject value)
    {
        super.put(value);
        return this;
    }


}
