/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.opensocial.resources;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import org.json.JSONObject;
import org.semanticwb.Logger;
import org.semanticwb.SWBPortal;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.GenericIterator;
import org.semanticwb.model.User;
import org.semanticwb.model.WebSite;
import org.semanticwb.opensocial.model.Gadget;
import org.semanticwb.opensocial.model.PersonalizedGadged;
import org.semanticwb.opensocial.model.UserPref;
import org.semanticwb.opensocial.model.data.Group;
import org.semanticwb.opensocial.model.data.Name;
import org.semanticwb.opensocial.model.data.Person;

/**
 *
 * @author victor.lorenzana
 */
public class SocialUser
{

    private static final Logger log = SWBUtils.getLogger(SocialUser.class);
    private final String user;
    private final UserPrefManager userprefsManager = new UserPrefManager();

    public SocialUser(User user)
    {
        this.user = user == null ? null : user.getId();
    }

    public String getUserId()
    {
        return user;
    }
    
    public UserPrefs[] getUserPrefs(WebSite site)
    {
        if (user == null)
        {
            return userprefsManager.toArray(new UserPrefs[userprefsManager.size()]);
        }
        else
        {
            User _user = site.getUserRepository().getUser(user);
            Set<UserPrefs> getUserPrefs = new HashSet<UserPrefs>();
            Iterator<PersonalizedGadged> personalizedGadgeds = PersonalizedGadged.ClassMgr.listPersonalizedGadgedByUser(_user, site);
            ArrayList<PersonalizedGadged> toDelete = new ArrayList<PersonalizedGadged>();
            while (personalizedGadgeds.hasNext())
            {
                PersonalizedGadged pgadget = personalizedGadgeds.next();
                if (pgadget.getGadget() != null && pgadget.getId() != null)
                {
                    UserPrefs pref = new UserPrefs(pgadget.getGadget(), pgadget.getId());
                    getUserPrefs.add(pref);
                }
                else
                {
                    toDelete.add(pgadget);
                }
            }
            for (PersonalizedGadged pgadget : toDelete)
            {
                System.out.println("deleting pgadget " + pgadget.getId());
                pgadget.remove();
            }
            return getUserPrefs.toArray(new UserPrefs[getUserPrefs.size()]);
        }
    }

    public void checkOsapiFeature(Gadget gadget, WebSite site)
    {
        checkOsapiFeature(gadget, site, false);
    }
    public void checkOsapiFeature(Gadget gadget, WebSite site, boolean forceupdate)
    {
        for (String feature : gadget.getFeatures())
        {
            if ("osapi".equals(feature)) // osapi is not valid for non register users
            {
                if (user != null)
                {
                    User _user = site.getUserRepository().getUser(user);
                    Person person = Person.ClassMgr.createPerson(user, site);
                    if (person == null)
                    {
                        person = Person.ClassMgr.createPerson(user, site);
                        Name name = Name.ClassMgr.createName(site);
                        name.setGivenName(_user.getFirstName());
                        name.setFamilyName(_user.getLastName());
                        name.setMiddleName(_user.getSecondLastName());
                        name.setFormatted(_user.getFullName());
                        name.setGivenName(_user.getFirstName());
                        person.setThumbnailUrl(SWBPortal.getDistributorPath() + SWBPortal.getWebWorkPath() + _user.getPhoto());
                        person.setName(name);
                        if(_user.getEmail()!=null)
                        {
                            person.addEmail(_user.getEmail());
                        }
                        Group friends = Group.ClassMgr.createGroup("@friends", site);
                        friends.setTitle("friends");
                        friends.setDescription("friends");
                        person.addGroup(friends);
                    }
                    else
                    {
                        if (forceupdate)
                        {
                            Name name = person.getName();
                            if(name==null)
                            {
                                name = Name.ClassMgr.createName(site);
                                person.setName(name);
                            }
                            name.setGivenName(_user.getFirstName());
                            name.setFamilyName(_user.getLastName());
                            name.setMiddleName(_user.getSecondLastName());
                            name.setFormatted(_user.getFullName());
                            name.setGivenName(_user.getFirstName());
                            person.setThumbnailUrl(SWBPortal.getWebWorkPath() + _user.getPhoto());
                        }
                    }

                }
            }
        }

    }

    public boolean canAdd(Gadget gadget, WebSite site)
    {
        for (String feature : gadget.getFeatures())
        {
            if ("osapi".equals(feature)) // osapi is not valid for non register users
            {
                if (user == null)
                {
                    return false;
                }
                checkOsapiFeature(gadget, site);
            }
        }
        return true;
    }

    public void saveUserPref(Gadget gadget, String moduleId, String key, String value, WebSite site)
    {
        if (user == null)
        {
            if (!userprefsManager.contains(gadget, moduleId))
            {
                System.out.println("adding a new anonimous UserPrefs " + moduleId);
                userprefsManager.add(new UserPrefs(gadget, moduleId));
            }
            UserPrefs prefs = userprefsManager.get(gadget, moduleId);
            if (key != null && value != null)
            {
                prefs.put(key, value);
            }
        }
        else
        {
            PersonalizedGadged pgadget = null;
            User _user = site.getUserRepository().getUser(user);
            if (_user != null)
            {
                Iterator<PersonalizedGadged> preferences = PersonalizedGadged.ClassMgr.listPersonalizedGadgedByUser(_user, site);
                while (preferences.hasNext())
                {
                    PersonalizedGadged personalizedGadged = preferences.next();
                    if (personalizedGadged.getGadget() != null)
                    {
                        String urltest = personalizedGadged.getGadget().getUrl();
                        if (urltest.equals(gadget.getUrl()) && personalizedGadged.getId().equals(moduleId))
                        {
                            pgadget = personalizedGadged;
                            break;
                        }
                    }
                }
                if (pgadget != null && value != null)
                {
                    boolean exists = false;
                    Iterator<UserPref> userprefs = pgadget.listUserPrefses();
                    while (userprefs.hasNext())
                    {
                        UserPref pref = userprefs.next();
                        if (pref.getKey().equals(key))
                        {
                            pref.setValue(value);
                            exists = true;
                        }
                    }
                    if (!exists)
                    {
                        UserPref pref = UserPref.ClassMgr.createUserPref(site);
                        pref.setKey(key);
                        pref.setValue(value);
                        pgadget.addUserPrefs(pref);
                    }
                }
            }
        }
    }

    public JSONObject getJSONUserPrefs(Gadget gadget, String moduleId, WebSite site)
    {
        JSONObject getJSONUserPrefs = new JSONObject();
        if (user == null)
        {
            UserPrefs prefs = userprefsManager.get(gadget, moduleId);
            if (prefs != null)
            {
                for (String key : prefs.keySet())
                {
                    String value = prefs.get(key);
                    try
                    {
                        getJSONUserPrefs.put(key, value);
                    }
                    catch (Exception e)
                    {
                        log.error(e);
                    }
                }
            }
        }
        else
        {
            User _user = site.getUserRepository().getUser(user);
            if (_user != null)
            {
                Iterator<PersonalizedGadged> preferences = PersonalizedGadged.ClassMgr.listPersonalizedGadgedByUser(_user, site);
                while (preferences.hasNext())
                {
                    PersonalizedGadged personalizedGadged = preferences.next();
                    if (personalizedGadged.getGadget().getURI().equals(gadget.getURI()) && personalizedGadged.getId().equals(moduleId))
                    {
                        GenericIterator<UserPref> list = personalizedGadged.listUserPrefses();
                        while (list.hasNext())
                        {
                            UserPref pref = list.next();
                            String key = pref.getKey();
                            String value = pref.getValue();
                            try
                            {
                                getJSONUserPrefs.put(key, value);
                            }
                            catch (Exception e)
                            {
                                log.error(e);
                            }
                        }
                        break;
                    }
                }
            }
        }
        return getJSONUserPrefs;
    }
}
