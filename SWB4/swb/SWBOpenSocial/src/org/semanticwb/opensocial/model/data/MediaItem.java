package org.semanticwb.opensocial.model.data;

import org.json.JSONException;
import org.json.JSONObject;
import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.WebSite;


public class MediaItem extends org.semanticwb.opensocial.model.data.base.MediaItemBase 
{
    private static final Logger log = SWBUtils.getLogger(MediaItem.class);
    public MediaItem(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public static MediaItem getMediaItem(String id,Person person,WebSite site)
    {
        String id_=id+"@"+person.getId();
        return MediaItem.ClassMgr.getMediaItem(id_, site);
    }
    public static MediaItem createMediaItem(String id,Person person,WebSite site)
    {
        String id_=id+"@"+person.getId();
        return MediaItem.ClassMgr.createMediaItem(id_, site);
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
