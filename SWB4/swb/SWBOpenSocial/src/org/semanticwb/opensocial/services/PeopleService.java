/*
 * SemanticWebBuilder es una plataforma para el desarrollo de portales y aplicaciones de integración,
 * colaboración y conocimiento, que gracias al uso de tecnología semántica puede generar contextos de
 * información alrededor de algún tema de interés o bien integrar información y aplicaciones de diferentes
 * fuentes, donde a la información se le asigna un significado, de forma que pueda ser interpretada y
 * procesada por personas y/o sistemas, es una creación original del Fondo de Información y Documentación
 * para la Industria INFOTEC, cuyo registro se encuentra actualmente en trámite.
 *
 * INFOTEC pone a su disposición la herramienta SemanticWebBuilder a través de su licenciamiento abierto al público (‘open source’),
 * en virtud del cual, usted podrá usarlo en las mismas condiciones con que INFOTEC lo ha diseñado y puesto a su disposición;
 * aprender de él; distribuirlo a terceros; acceder a su código fuente y modificarlo, y combinarlo o enlazarlo con otro software,
 * todo ello de conformidad con los términos y condiciones de la LICENCIA ABIERTA AL PÚBLICO que otorga INFOTEC para la utilización
 * del SemanticWebBuilder 4.0.
 *
 * INFOTEC no otorga garantía sobre SemanticWebBuilder, de ninguna especie y naturaleza, ni implícita ni explícita,
 * siendo usted completamente responsable de la utilización que le dé y asumiendo la totalidad de los riesgos que puedan derivar
 * de la misma.
 *
 * Si usted tiene cualquier duda o comentario sobre SemanticWebBuilder, INFOTEC pone a su disposición la siguiente
 * dirección electrónica:
 *  http://www.semanticwebbuilder.org
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
            log.debug(e);
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
            log.debug(e);
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
            log.debug(jsone);
            throw new RPCException(jsone);
        }
        return response;
    }

    public JSONObject delete(Person person, JSONObject params, WebSite site, Gadget gadget) throws RPCException
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
            log.debug(e);
            throw new RPCException(e);
        }
        return null;
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
