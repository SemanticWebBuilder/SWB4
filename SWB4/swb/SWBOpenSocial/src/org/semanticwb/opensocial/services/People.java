/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.opensocial.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.semanticwb.model.WebSite;
import org.semanticwb.opensocial.model.data.Group;
import org.semanticwb.opensocial.model.data.Name;
import org.semanticwb.opensocial.model.data.Person;

/**
 *
 * @author victor.lorenzana
 */
public class People implements Service
{

    class PersonComparator implements Comparator<Person>
    {

        private final String field;

        public PersonComparator(String field)
        {
            this.field = field;
        }

        public int compare(Person o1, Person o2)
        {
            if ("name".equals(field))
            {
                return o1.getName().getFormatted().compareTo(o2.getName().getFormatted());
            }
            return 0;
        }
    }

    public JSONObject get(String userid, JSONObject params, WebSite site)
    {

        Group friends = Group.ClassMgr.getGroup("@friends", site);
        if (friends == null)
        {
            friends = Group.ClassMgr.createGroup("@friends", site);
            friends.setTitle("friends");
            friends.setDescription("friends");
            Person friend = Person.ClassMgr.getPerson("friend1", site);
            if (friend == null)
            {
                friend = Person.ClassMgr.createPerson("friend1", site);
                Name name = Name.ClassMgr.createName(site);
                name.setFormatted("Demo Friend");
                friend.setName(name);
                friend.setAge(37);
                friend.setGender("male");
                friend.setProfileUrl("http://www.infotec");
                friend.setThumbnailUrl("a");
                friends.addPerson(friend);
            }
        }

        JSONObject response = new JSONObject();
        JSONArray entries = new JSONArray();
        try
        {
            ArrayList<Person> persons = new ArrayList<Person>();
            response.put("entry", entries);
            String groupId = params.getString("groupId").trim();
            if (groupId.equals("@self")) //Defaults to "@self", which MUST return only the Person object(s) specified by the userId parameter
            {
                Person person = Person.ClassMgr.getPerson(userid, site);
                if (person != null)
                {
                    persons.add(person);
                }
                else
                {
                    person = Person.ClassMgr.createPerson(userid, site);
                    Name name = Name.ClassMgr.createName(site);
                    name.setFormatted("Demo ");
                    person.setName(name);
                    person.setAge(37);
                    person.setGender("male");
                    person.setProfileUrl("http://www.infotec");
                    person.setThumbnailUrl("a");
                    persons.add(person);
                    // error
                }
            }
            else if (groupId.equals("@all"))
            {
                Iterator<Person> _persons = Person.ClassMgr.listPersons(site);
                while (_persons.hasNext())
                {
                    Person person = _persons.next();
                    persons.add(person);
                }
            }
            else
            {

                Group group = Group.ClassMgr.getGroup(groupId, site);
                if (group == null)
                {
                    group = Group.ClassMgr.createGroup(groupId, site);
                    group.setTitle("title");
                    group.setDescription("description");
                }
                Iterator<Person> _persons = Person.ClassMgr.listPersonByGroup(group);
                while (_persons.hasNext())
                {
                    Person person = _persons.next();
                    persons.add(person);
                }
            }
            String sortBy = params.getString("sortBy");
            Person[] result = persons.toArray(new Person[persons.size()]);
            if (sortBy != null)
            {
                Arrays.sort(result, new PersonComparator(sortBy));
            }
            for (Person p : result)
            {
                JSONObject jsonperson = new JSONObject();
                JSONArray fields = params.getJSONArray("fields");
                for (int i = 0; i < fields.length(); i++)
                {
                    String field = fields.getString(i);
                    jsonperson.put(field, p.getValueFromField(field));
                }
                entries.put(jsonperson);
            }


        }
        catch (JSONException jsone)
        {
            jsone.printStackTrace();
        }
        return response;
    }
}
