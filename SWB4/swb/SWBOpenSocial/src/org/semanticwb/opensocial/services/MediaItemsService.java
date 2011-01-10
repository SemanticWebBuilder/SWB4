/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.opensocial.services;

import java.util.ArrayList;
import java.util.Iterator;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.WebSite;
import org.semanticwb.opensocial.model.Gadget;
import org.semanticwb.opensocial.model.data.Album;
import org.semanticwb.opensocial.model.data.Group;
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
        String groupId = Group.SELF;
        if (params.optString("groupId") != null && !params.optString("groupId").equals(""))
        {
            groupId = params.optString("groupId");
        }
        if (groupId.equals(Group.SELF))
        {
        }
        ArrayList<Person> persons = new ArrayList<Person>();
        if (groupId.equals(Group.SELF)) //Defaults to "@self", which MUST return only the Person object(s) specified by the userId parameter
        {
            if (person != null)
            {
                persons.add(person);
            }
        }
        else if (groupId.equals(Group.FRIENDS))
        {
            if (person != null && Group.getGroup(Group.FRIENDS, person, site) != null)
            {
                Group group = Group.getGroup(Group.FRIENDS, person, site);
                Iterator<Person> _persons = group.listPersons();
                while (_persons.hasNext())
                {
                    Person persontoadd = _persons.next();
                    persons.add(persontoadd);
                }

            }
        }
        else if (groupId.equals(Group.ALL))
        {
            if (person != null)
            {
                Iterator<Group> groups = person.listGroups();
                while (groups.hasNext())
                {
                    Group group = groups.next();
                    Iterator<Person> _persons = group.listPersons();
                    while (_persons.hasNext())
                    {
                        Person personToAdd = _persons.next();
                        persons.add(personToAdd);
                    }
                }
            }
        }
        else
        {

            if (person != null)
            {
                Iterator<Group> groups = person.listGroups();
                while (groups.hasNext())
                {
                    Group group = groups.next();
                    if (group.getId().equals(groupId))
                    {
                        Iterator<Person> _persons = group.listPersons();
                        while (_persons.hasNext())
                        {
                            persons.add(_persons.next());
                        }
                    }
                }
            }
        }
        for (Person p : persons)
        {
            ArrayList<Album> albums = new ArrayList<Album>();
            String albumId = params.optString("albumId");
            String appId = params.optString("appId");
            if (albumId == null || albumId.equals(""))
            {
                Iterator<Album> italbums = p.listAlbumnses();
                while (italbums.hasNext())
                {
                    albums.add(italbums.next());
                }
            }
            else
            {
                Album album = Album.getAlbum(albumId, person, site);
                if (album != null)
                {
                    albums.add(album);
                }
            }
            ArrayList<MediaItem> _items = new ArrayList<MediaItem>();
            for (Album album : albums)
            {
                Iterator<MediaItem> items = album.listMediaItems();
                while (items.hasNext())
                {
                    MediaItem item = items.next();
                    if (appId != null)
                    {
                        if (appId.equals(item.getAppId()))
                        {
                            _items.add(item);

                        }
                    }
                    else
                    {
                        _items.add(item);
                    }
                }
            }
            try
            {
                JSONArray ids = params.getJSONArray("id");
                JSONObject entry = new JSONObject();
                JSONArray array = new JSONArray();
                for (MediaItem item : _items)
                {
                    for (int i = 0; i < ids.length(); i++)
                    {
                        String id = ids.getString(i);
                        if (id != null && id.equals(item.getId()))
                        {
                            array.put(item.toJSONObject());
                        }
                    }
                }
                entry.put("entry", array);
                return entry;
            }
            catch (JSONException e)
            {
                log.debug(e);
            }            
        }
        return null;
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
