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
import org.semanticwb.model.GenericIterator;
import org.semanticwb.model.WebSite;
import org.semanticwb.opensocial.model.Gadget;
import org.semanticwb.opensocial.model.data.AppData;
import org.semanticwb.opensocial.model.data.Group;
import org.semanticwb.opensocial.model.data.Person;
import org.semanticwb.opensocial.resources.RPCException;

/**
 *
 * @author victor.lorenzana
 */
public class AppDataService implements Service
{

    private static final Logger log = SWBUtils.getLogger(AppDataService.class);

    private String getData(String key, Person person, String appid,WebSite site)
    {
        if (person != null)
        {
            AppData appdata=AppData.getAppData(key, person, site);
            if(appdata!=null && appid.equals(appdata))
            {
                return appdata.getValue();
            }
        }
        return null;
    }

    public JSONObject get(Person person, JSONObject params, WebSite site, Gadget gadget) throws RPCException
    {
        JSONObject response = new JSONObject();
        try
        {
            ArrayList<Person> persons = new ArrayList<Person>();
            String groupId = params.getString("groupId").trim();
            String appid = gadget.getId();
            if (params.optString("appId") != null && !params.optString("appId").equals(""))
            {
                appid = params.optString("appId");
            }
            if (groupId.equals("@self"))
            {
                if (person != null)
                {
                    JSONObject responseKeys = new JSONObject();
                    JSONArray keys = params.getJSONArray("keys");
                    for (int i = 0; i < keys.length(); i++)
                    {
                        String key = keys.getString(i);
                        String data = getData(key, person, appid,site);
                        if (data != null)
                        {
                            responseKeys.accumulate(key, data);
                        }

                    }
                    response.put(person.getId(), responseKeys);
                    return response;
                }
            }
            else if (groupId.equals(Group.FRIENDS))
            {
                if (person != null)
                {
                    Group group = Group.getGroup(Group.FRIENDS, person, site);
                    if (group != null)
                    {
                        Iterator<Person> _persons = group.listPersons();
                        while (_persons.hasNext())
                        {
                            Person personToAdd = _persons.next();
                            persons.add(personToAdd);
                        }

                    }
                }
            }
            else if (groupId.equals("@all"))
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
                                Person personToAdd = _persons.next();
                                persons.add(personToAdd);
                            }
                        }
                    }
                }
            }
            for (Person p : persons)
            {
                JSONObject responseKeys = new JSONObject();
                JSONArray keys = params.getJSONArray("keys");
                for (int i = 0; i < keys.length(); i++)
                {
                    String key = keys.getString(i);
                    String data = getData(key, p, appid,site);
                    if (data != null)
                    {
                        responseKeys.accumulate(key, data);
                    }

                }
                response.accumulate(p.getId(), responseKeys);
            }

        }
        catch (JSONException je)
        {
            je.printStackTrace();
        }
        return response;
    }

    public JSONObject update(Person personUserID, JSONObject params, WebSite site, Gadget gadget) throws RPCException
    {

        try
        {
            ArrayList<Person> persons = new ArrayList<Person>();
            String groupId = params.getString("groupId").trim();
            String appid = gadget.getId();
            if (params.optString("appId") != null && !params.optString("appId").equals(""))
            {
                appid = params.optString("appId");
            }
            if (groupId.equals("@self"))
            {
                persons.add(personUserID);
            }
            else if (groupId.equals("@all"))
            {
                Iterator<Group> groups = personUserID.listGroups();
                while (groups.hasNext())
                {
                    Group group = groups.next();
                    Iterator<Person> _persons = group.listPersons();
                    while (_persons.hasNext())
                    {
                        Person person = _persons.next();
                        persons.add(person);
                    }
                }
            }
            else
            {
                Iterator<Group> groups = personUserID.listGroups();
                while (groups.hasNext())
                {
                    Group group = groups.next();
                    if (group.getId().equals(groupId))
                    {
                        Iterator<Person> _persons = group.listPersons();
                        while (_persons.hasNext())
                        {
                            Person person = _persons.next();
                            persons.add(person);
                        }
                    }
                }
            }

            for (Person p : persons)
            {
                if (p != null)
                {
                    // update data
                    JSONObject data = params.getJSONObject("data");
                    Iterator keys = data.keys();
                    while (keys.hasNext())
                    {
                        String key = keys.next().toString();
                        String value = data.get(key).toString();
                        if (getData(key, p, appid,site) == null)
                        {
                            AppData appdata = AppData.ClassMgr.createAppData(key, site);
                            appdata.setKey(key);
                            appdata.setValue(value);
                            appdata.setAppid(gadget.getId());
                            p.addAppData(appdata);
                        }
                        else
                        {
                            GenericIterator<AppData> appdatas = p.listAppDatas();
                            while (appdatas.hasNext())
                            {
                                AppData appdata = appdatas.next();
                                if (appdata.getKey().equals(key) && appid.equals(appdata.getAppid()))
                                {
                                    appdata.setValue(value);
                                    break;
                                }
                            }
                        }
                    }
                }
            }

        }
        catch (JSONException je)
        {
            je.printStackTrace();
        }
        return null;
    }

    public JSONObject delete(Person person, JSONObject params, WebSite site, Gadget gadget) throws RPCException
    {
        JSONObject result=new JSONObject();
        String appid = gadget.getId();
        GenericIterator<AppData> data = person.listAppDatas();
        ArrayList<AppData> appdatas=new ArrayList<AppData>();
        while (data.hasNext())
        {
            AppData appData = data.next();
            if (appid.equals(appData.getAppid()))
            {
                appdatas.add(appData);
            }
        }
        for(AppData appdata : appdatas)
        {
            try
            {
                result.put(appdata.getKey(), appdata.getValue());
                appdata.remove();
            }
            catch(JSONException e)
            {
                log.debug(e);
            }
        }
        return result;
    }

    public JSONObject create(Person person, JSONObject params, WebSite site, Gadget gadget) throws RPCException
    {
        try
        {
            JSONObject data = params.getJSONObject("data");
            Iterator keys = data.keys();
            while (keys.hasNext())
            {
                String key = keys.next().toString();
                String value = data.getString(key);
                String appid = gadget.getId();
                if (getData(key, person, appid,site) == null)
                {
                    AppData appdata = AppData.ClassMgr.createAppData(key, site);
                    appdata.setKey(key);
                    appdata.setValue(value);
                    appdata.setAppid(gadget.getId());
                    person.addAppData(appdata);
                }
                else
                {
                    GenericIterator<AppData> appdatas = person.listAppDatas();
                    while (appdatas.hasNext())
                    {
                        AppData appdata = appdatas.next();
                        if (appdata.getKey().equals(key) && appid.equals(appdata.getAppid()))
                        {
                            appdata.setValue(value);
                            break;
                        }
                    }
                }
            }

        }
        catch (JSONException e)
        {
            log.debug(e);
        }
        return null;
    }
}
