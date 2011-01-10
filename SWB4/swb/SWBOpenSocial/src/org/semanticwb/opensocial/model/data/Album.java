package org.semanticwb.opensocial.model.data;

import org.json.JSONException;
import org.json.JSONObject;
import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.WebSite;


public class Album extends org.semanticwb.opensocial.model.data.base.AlbumBase 
{
    private static final Logger log = SWBUtils.getLogger(Album.class);
    public Album(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public static Album getAlbum(String id,Person person,WebSite site)
    {
        String id_=id+"@"+person.getId();
        return Album.ClassMgr.getAlbum(id_, site);
    }
    public static Album createAlbum(String id,Person person,WebSite site)
    {
        String id_=id+"@"+person.getId();
        return Album.ClassMgr.createAlbum(id_, site);
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
