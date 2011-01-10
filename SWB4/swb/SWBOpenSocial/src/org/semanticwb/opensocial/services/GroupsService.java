/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.opensocial.services;

import java.util.Iterator;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.WebSite;
import org.semanticwb.opensocial.model.Gadget;
import org.semanticwb.opensocial.model.data.Group;
import org.semanticwb.opensocial.model.data.Person;
import org.semanticwb.opensocial.resources.RPCException;

/**
 *
 * @author victor.lorenzana
 */
public class GroupsService implements Service {

    private static final Logger log = SWBUtils.getLogger(GroupsService.class);
    public JSONObject get(Person person, JSONObject params, WebSite site, Gadget gadget) throws RPCException
    {
        String groupid=null;
        if (params.optString("groupId") != null && !params.optString("groupId").equals(""))
        {
            groupid = params.optString("groupId");
        }
        if(groupid==null) // all
        {
            JSONArray array=new JSONArray();
            JSONObject obj=new JSONObject();
            try
            {
                obj.put("entry", array);

                Iterator<Group> groups=person.listGroups();
                while(groups.hasNext())
                {
                    Group group=groups.next();
                    array.put(group.toJSONObject());
                }
            }
            catch(JSONException e)
            {
                log.debug(e);
            }
            return obj;
        }
        else
        {            
            Group group = Group.createGroup(groupid, person, site);
            if(group!=null)
            {
                return group.toJSONObject();
            }
        }
        return null;
    }

    public JSONObject update(Person person, JSONObject params, WebSite site, Gadget gadget) throws RPCException
    {
        String groupid=null;
        if (params.optString("groupId") != null && !params.optString("groupId").equals(""))
        {
            groupid = params.optString("groupId");
        }
        if(groupid!=null)
        {
            if(Group.getGroup(groupid, person, site)!=null)
            {
                Group group=Group.getGroup(groupid, person, site);
                return group.toJSONObject();
            }
        }
        return null;
    }

    public JSONObject delete(Person person, JSONObject params, WebSite site, Gadget gadget) throws RPCException
    {
        String groupid=null;
        if (params.optString("groupId") != null && !params.optString("groupId").equals(""))
        {
            groupid = params.optString("groupId");
        }
        if(groupid!=null)
        {
            if(Group.getGroup(groupid, person, site)!=null)
            {
                Group.getGroup(groupid, person, site).remove();
            }
        }
        return null;
    }

    public JSONObject create(Person person, JSONObject params, WebSite site, Gadget gadget) throws RPCException
    {
        String groupid=null;
        if (params.optString("groupId") != null && !params.optString("groupId").equals(""))
        {
            groupid = params.optString("groupId");
        }
        if(groupid!=null)
        {
            if(Group.getGroup(groupid, person, site)==null)
            {                
                Group group=Group.createGroup(groupid, person, site);
                person.addGroup(group);
                return group.toJSONObject();
            }
        }
        return null;
    }

    

    

}
