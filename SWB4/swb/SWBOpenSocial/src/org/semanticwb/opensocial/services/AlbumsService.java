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
import org.semanticwb.opensocial.model.data.Person;
import org.semanticwb.opensocial.resources.RPCException;

/**
 *
 * @author victor.lorenzana
 */
public class AlbumsService implements Service
{

    private static final Logger log = SWBUtils.getLogger(AlbumsService.class);

    public JSONObject get(Person person, JSONObject params, WebSite site, Gadget gadget) throws RPCException
    {
        ArrayList<Person> persons = new ArrayList<Person>();
        String groupId = Group.SELF;
        if (params.optString("groupId") != null && !params.optString("groupId").equals(""))
        {
            groupId = params.optString("groupId");
        }
        if (groupId.equals(Group.SELF))
        {
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
        JSONArray albums = new JSONArray();
        String appId = params.optString("appId"); // optional
        for (Person p : persons)
        {
            try
            {
                JSONArray ids = params.getJSONArray("id");
                for (int i = 0; i < ids.length(); i++)
                {
                    String id = ids.getString(i);
                    Album album = Album.getAlbum(id, person, site);
                    if (album != null)
                    {
                        if (appId != null && !appId.equals(""))
                        {
                            if (appId.equals(album.getAppId()))
                            {
                                albums.put(album.toJSONObject());
                            }
                        }
                        else
                        {
                            albums.put(album.toJSONObject());
                        }
                    }
                }
            }
            catch (JSONException e)
            {
                log.debug(e);
            }
        }
        JSONObject entry = new JSONObject();
        try
        {
            entry.put("entry", albums);
        }
        catch (JSONException e)
        {
            log.debug(e);
        }
        return entry;
    }

    public JSONObject update(Person person, JSONObject params, WebSite site, Gadget gadget) throws RPCException
    {
        try
        {
            JSONObject albumparams = params.getJSONObject("data");
            String id = albumparams.getString("id");
            if (Album.getAlbum(id, person, site) != null)
            {
                Album album = Album.getAlbum(id, person, site);
            }
        }
        catch (JSONException e)
        {
            log.debug(e);
        }
        return null;
    }

    public JSONObject delete(Person person, JSONObject params, WebSite site, Gadget gadget) throws RPCException
    {
        try
        {
            JSONObject albumparams = params.getJSONObject("data");
            String id = albumparams.getString("id");
            if (Album.getAlbum(id, person, site) != null)
            {
                Album album = Album.createAlbum(id, person, site);
                album.remove();
            }
        }
        catch (JSONException e)
        {
            log.debug(e);
        }
        return null;
    }

    public JSONObject create(Person person, JSONObject params, WebSite site, Gadget gadget) throws RPCException
    {
        try
        {
            JSONObject albumparams = params.getJSONObject("data");
            String id = albumparams.getString("id");
            if (Album.getAlbum(id, person, site) == null)
            {
                Album album = Album.createAlbum(id, person, site);
                album.setAppId(gadget.getId());
                person.addAlbumns(album);
                JSONObject value = new JSONObject();
                value.put("Album-Id", id);
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
