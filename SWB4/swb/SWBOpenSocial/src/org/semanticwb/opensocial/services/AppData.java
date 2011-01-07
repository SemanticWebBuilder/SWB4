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
import org.semanticwb.model.GenericIterator;
import org.semanticwb.model.WebSite;
import org.semanticwb.opensocial.model.data.Group;
import org.semanticwb.opensocial.model.data.Name;
import org.semanticwb.opensocial.model.data.Person;

/**
 *
 * @author victor.lorenzana
 */
public class AppData implements Service
{

    public JSONObject get(String userid, JSONObject params, WebSite site)
    {
        JSONObject response = new JSONObject();
        try
        {
            ArrayList<Person> persons=new ArrayList<Person>();
            String groupId = params.getString("groupId").trim();
            if (groupId.equals("@self"))
            {
                Person person=Person.ClassMgr.getPerson(userid, site);
                if(person==null)
                {
                    person=Person.ClassMgr.createPerson(userid, site);
                    Name name=Name.ClassMgr.createName(site);
                    name.setFormatted("Demo ");
                    person.setName(name);
                    person.setAge(37);
                    person.setGender("male");
                    person.setProfileUrl("http://www.infotec");
                    person.setThumbnailUrl("a");

                }
                persons.add(person);
            }
            else if(groupId.equals("@all"))
            {
                Iterator<Person> _persons=Person.ClassMgr.listPersons(site);
                while(_persons.hasNext())
                {
                    Person person=_persons.next();
                    persons.add(person);
                }
            }
            else
            {
                Group group=Group.ClassMgr.getGroup(groupId, site);
                if(group==null)
                {
                    group=Group.ClassMgr.createGroup(groupId, site);
                    group.setTitle("title");
                    group.setDescription("description");
                }
                Iterator<Person> _persons=Person.ClassMgr.listPersonByGroup(group);
                while(_persons.hasNext())
                {
                    Person person=_persons.next();
                    persons.add(person);
                }
            }
            for(Person p : persons)
            {
                JSONArray keys=params.getJSONArray("keys");
                for(int i=0;i<keys.length();i++)
                {
                    String key=keys.getString(i);
                    GenericIterator<org.semanticwb.opensocial.model.data.AppData> data=p.listAppDatas();
                    while(data.hasNext())
                    {
                        org.semanticwb.opensocial.model.data.AppData appData=data.next();
                        if(key.equals(appData.getKey()))
                        {
                            String value=appData.getValue();
                            response.put(key, value);
                        }
                    }

                }
            }

        }
        catch (JSONException je)
        {
            je.printStackTrace();
        }
        return response;
    }
}
