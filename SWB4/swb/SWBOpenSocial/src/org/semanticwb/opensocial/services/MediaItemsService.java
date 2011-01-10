/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.opensocial.services;

import org.json.JSONException;
import org.json.JSONObject;
import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.WebSite;
import org.semanticwb.opensocial.model.Gadget;
import org.semanticwb.opensocial.model.data.Album;
import org.semanticwb.opensocial.model.data.MediaItem;
import org.semanticwb.opensocial.model.data.Person;
import org.semanticwb.opensocial.resources.RPCException;

/**
 *
 * @author victor.lorenzana
 */
public class MediaItemsService implements Service
{

    private static final Logger log = SWBUtils.getLogger(MediaItemsService.class);

    public JSONObject get(Person person, JSONObject params, WebSite site, Gadget gadget) throws RPCException
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public JSONObject update(Person person, JSONObject params, WebSite site, Gadget gadget) throws RPCException
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void delete(Person person, JSONObject params, WebSite site, Gadget gadget) throws RPCException
    {
        try
        {
            String id = params.getString("id");
            MediaItem item = MediaItem.getMediaItem(id, person, site);
            if (item != null)
            {
                item.remove();
            }
        }
        catch (JSONException e)
        {
            log.debug(e);
        }
    }

    public JSONObject create(Person person, JSONObject params, WebSite site, Gadget gadget) throws RPCException
    {
        try
        {
            JSONObject mediaItem = params.getJSONObject("data");
            String id = mediaItem.getString("id");
            String albumid = mediaItem.getString("album_id");
            Album album = Album.getAlbum(albumid, person, site);
            if (album != null && MediaItem.getMediaItem(id, person, site) == null)
            {
                MediaItem item = MediaItem.createMediaItem(id, person, site);
                album.addMediaItem(item);
                JSONObject value = new JSONObject();
                value.put("MediaItem-Id", id);
                value.put("Album-Id", albumid);
                return value;
            }

        }
        catch (JSONException e)
        {
            log.debug(e);
        }
        return null;
    }
}
