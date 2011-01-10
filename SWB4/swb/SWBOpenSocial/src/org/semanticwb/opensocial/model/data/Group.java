package org.semanticwb.opensocial.model.data;

import org.json.JSONException;
import org.json.JSONObject;
import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.WebSite;


public class Group extends org.semanticwb.opensocial.model.data.base.GroupBase 
{
    public static final String ALL = "@all";
    public static final String FRIENDS = "@friends";
    private static final Logger log = SWBUtils.getLogger(Group.class);
    public Group(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
    public static Group getGroup(String groupid,Person person,WebSite site)
    {
        String groupid_=groupid+"@"+person.getId();
        return Group.ClassMgr.getGroup(groupid_, site);
    }
    public static Group createGroup(String groupid,Person person,WebSite site)
    {
        String groupid_=groupid+"@"+person.getId();
        return Group.ClassMgr.createGroup(groupid_, site);
    }
    public JSONObject toJSONObject()
    {
        JSONObject person=new JSONObject();
        try
        {
            person.put("id", this.getId());
        }
        catch(JSONException e)
        {
            log.debug(e);
        }
        return person;
    }
}
