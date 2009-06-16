/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.portal.admin.resources.swbtree;

import org.json.JSONException;
import org.json.JSONObject;
import org.semanticwb.SWBPlatform;

/**
 *
 * @author javier.solis
 */
public class TreeUtils
{
    private static int nullnode=0;

    static public JSONObject getAction(String name, String value, String target) throws JSONException
    {
        JSONObject obj=new JSONObject();
        obj.put("id", "_NOID_"+(nullnode++));
        obj.put("name", name);
        obj.put("value", value);
        obj.put("target", target);
        return obj;
    }

    static public JSONObject getEvent(String name, JSONObject action) throws JSONException
    {
        JSONObject obj=new JSONObject();
        obj.put("id", "_NOID_"+(nullnode++));
        obj.put("name", name);
        obj.put("action", action);
        return obj;
    }

    static public JSONObject getMenuItem(String title, String icon, JSONObject action) throws JSONException
    {
        JSONObject obj=new JSONObject();
        obj.put("id", "_NOID_"+(nullnode++));
        obj.put("title", title);
        obj.put("icon", icon);
        obj.put("action", action);
        return obj;
    }

    static public JSONObject getDNode(String id, String title, String type, String icon) throws JSONException
    {
        return getNode(id+"|"+(nullnode++), title, type, icon);
    }
    
    static public JSONObject getNode(String id, String title, String type, String icon) throws JSONException
    {
        if(title==null)title="Topic";
        JSONObject obj=new JSONObject();
        obj.put("id", id);
        obj.put("title",title);
        obj.put("type",type);
        obj.put("icon",icon);
        return obj;
    }

    static public JSONObject getReloadAction() throws JSONException
    {
        return getAction("reload",null,null);
    }

    static public JSONObject getNewTabAction() throws JSONException
    {
        return getAction("newTab",SWBPlatform.getContextPath()+"/swbadmin/jsp/objectTab.jsp",null);
    }

    static public JSONObject getMenuSeparator() throws JSONException
    {
        return getMenuItem("_",null, null);
    }

    static public JSONObject getDummy() throws JSONException
    {
        return getNode("_NOID_" + (nullnode++), "DUMMY", "DUMMY", "DUMMY");
    }

    //018008499809

}
