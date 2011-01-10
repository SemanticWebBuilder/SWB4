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
import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.WebSite;
import org.semanticwb.opensocial.model.Gadget;
import org.semanticwb.opensocial.model.data.Group;
import org.semanticwb.opensocial.model.data.Person;
import org.semanticwb.opensocial.resources.RPCException;
import org.semanticwb.opensocial.util.HtmlScape;
import org.semanticwb.opensocial.util.NoneScape;
import org.semanticwb.opensocial.util.Scape;

/**
 *
 * @author victor.lorenzana
 */
public class PeopleService implements Service
{

    private static final Logger log = SWBUtils.getLogger(PeopleService.class);
    /*static
    {
    Iterator<Person> _persons = Person.ClassMgr.listPersons();
    while (_persons.hasNext())
    {
    Person person = _persons.next();
    person.remove();
    }

    Iterator<Group> groups = Group.ClassMgr.listGroups();
    while (groups.hasNext())
    {
    Group g = groups.next();
    g.remove();
    }
    WebSite site = WebSite.ClassMgr.getWebSite("reg_digital_demo");

    Person john_doe = Person.ClassMgr.createPerson("john.doe", site);
    Name name = Name.ClassMgr.createName(site);
    name.setFormatted("Demo Friend");
    john_doe.setName(name);
    john_doe.setAge(20);
    john_doe.setGender("male");
    john_doe.setProfileUrl("http://www.infotec");

    Group friendsOfGeorgeDoe = Group.ClassMgr.createGroup(john_doe.getId() + FRIENDS, site);
    john_doe.addGroup(friendsOfGeorgeDoe);
    friendsOfGeorgeDoe.setTitle("friends");
    friendsOfGeorgeDoe.setDescription("friends");



    Person george_doe = Person.ClassMgr.createPerson("George.doe", site);
    name = Name.ClassMgr.createName(site);
    name.setFormatted("George Doe");
    george_doe.setName(name);
    george_doe.setAge(20);
    george_doe.setGender("famale");
    george_doe.setProfileUrl("http://www.infotec");






    Person jane_doe = Person.ClassMgr.createPerson("jane.doe", site);
    name = Name.ClassMgr.createName(site);
    name.setFormatted("Jane Doe");
    jane_doe.setName(name);
    jane_doe.setAge(37);
    jane_doe.setGender("male");
    jane_doe.setProfileUrl("http://www.infotec");


    Person Maija = Person.ClassMgr.createPerson("maija", site);
    name = Name.ClassMgr.createName(site);
    name.setFormatted("Maija Meikäläinen");
    Maija.setName(name);
    Maija.setAge(37);
    Maija.setGender("male");
    Maija.setProfileUrl("http://www.infotec");

    //friend.setThumbnailUrl("a");
    friendsOfGeorgeDoe.addPerson(jane_doe);
    friendsOfGeorgeDoe.addPerson(Maija);
    friendsOfGeorgeDoe.addPerson(george_doe);



    }*/

    public JSONObject update(Person person, JSONObject params, WebSite site, Gadget gadget) throws RPCException
    {
        String groupid = Group.SELF;
        if (params.optString("groupId") != null && !params.optString("groupId").equals(""))
        {
            groupid = params.optString("groupId");
        }

        try
        {
            Group group = Group.createGroup(groupid, person, site);
            if (group != null)
            {
                JSONObject personParam = params.getJSONObject("Person");
                String personid = personParam.getString("id");
                if (personid != null && !personid.equals(""))
                {
                    Person personToUpdate = Person.ClassMgr.getPerson(personid, site);
                    JSONObject returnJSON = new JSONObject();
                    // TODO: falta actalizar a la persona
                    returnJSON.put("person", personToUpdate.toJSONObject());
                    return returnJSON;
                }
            }
        }
        catch (JSONException e)
        {
            throw new RPCException(e);
        }
        return null;
    }


    /*
     * Containers MAY support request to create a new relationships between users. This is a generalization of many use cases including invitation, and contact creation. Containers MAY require a dual opt-in process before the friend record appears in the collection, and in this case SHOULD return a 202 Accepted response, indicating that the request is 'in flight' and may or may not be ultimately successful.
     * A request to create a relationship MUST support the Standard-Request-Parameters and the following additional parameters:
    Name Type Description
    userId User-Id User ID of the person initiating the relationship request. Defaults to "@me", which MUST return the currently logged in user.
    groupId Group-Id The Group ID specifying the type of relationship. Defaults to "@friends".
    person Person The target of the relationship.

     */
    public JSONObject create(Person person, JSONObject params, WebSite site, Gadget gadget) throws RPCException
    {
        String groupid = Group.FRIENDS;
        if (params.optString("groupId") != null && !params.optString("groupId").equals(""))
        {
            groupid = params.optString("groupId");
        }
        try
        {
            JSONObject personParam = params.getJSONObject("Person");
            Group group = Group.getGroup(groupid, person, site);
            if (group != null)
            {
                String personid = personParam.getString("id");
                if (personid != null && !personid.equals(""))
                {
                    Person personToAdd = Person.ClassMgr.getPerson(personid, site);
                    boolean exists = false;
                    Iterator<Person> persons = group.listPersons();
                    while (persons.hasNext())
                    {
                        if (persons.next().getId().equals(personid))
                        {
                            exists = true;
                            break;
                        }
                    }
                    if (!exists)
                    {
                        group.addPerson(personToAdd);
                    }
                }
            }

        }
        catch (JSONException e)
        {
            throw new RPCException(e);
        }
        return null;
    }

    public JSONObject get(Person person, JSONObject params, WebSite site, Gadget gadget) throws RPCException
    {
        JSONObject response = new JSONObject();
        JSONArray entries = new JSONArray();
        try
        {
            ArrayList<Person> persons = new ArrayList<Person>();
            response.put("entry", entries);
            Scape scape = new HtmlScape();
            String escapeType = "htmlEscape";
            if (params.optString("escapeType") != null && !params.optString("escapeType").equals(""))
            {
                escapeType = params.optString("escapeType");
            }
            if ("none".equals(escapeType))
            {
                scape = new NoneScape();
            }
            String groupId = params.getString("groupId").trim();
            if (groupId.equals(Group.SELF)) //Defaults to "@self", which MUST return only the Person object(s) specified by the userId parameter
            {
                if (person != null)
                {
                    JSONObject jsonperson = new JSONObject();
                    JSONArray fields = params.getJSONArray("fields");
                    for (int i = 0; i < fields.length(); i++)
                    {
                        String field = fields.getString(i);
                        jsonperson.put(field, person.getValueFromField(field, scape));
                    }
                    return jsonperson;

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
                    jsonperson.put(field, p.getValueFromField(field, scape));
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

    public void delete(Person person, JSONObject params, WebSite site, Gadget gadget) throws RPCException
    {
        String groupid = Group.FRIENDS;
        if (params.optString("groupId") != null && !params.optString("groupId").equals(""))
        {
            groupid = params.optString("groupId");
        }
        try
        {
            if (!groupid.equals(Group.FRIENDS))
            {
                JSONObject personParam = params.getJSONObject("Person");
                Group group = Group.getGroup(groupid, person, site);
                if (group != null)
                {
                    String personid = personParam.getString("id");
                    if (personid != null && !personid.equals(""))
                    {
                        Person persontoRemove = Person.ClassMgr.getPerson(personid, site);
                        group.removePerson(persontoRemove);
                    }
                }
            }

        }
        catch (JSONException e)
        {
            throw new RPCException(e);
        }
    }

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
}
