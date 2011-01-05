/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.opensocial.resources;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.json.JSONObject;
import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.GenericIterator;
import org.semanticwb.model.User;
import org.semanticwb.model.WebSite;
import org.semanticwb.opensocial.model.Gadget;
import org.semanticwb.opensocial.model.PersonalizedGadged;
import org.semanticwb.opensocial.model.UserPref;

/**
 *
 * @author victor.lorenzana
 */
public class SocialUser
{

    private static final Logger log = SWBUtils.getLogger(SocialUser.class);
    private final User user;
    private final Map<Gadget, UserPrefs> userprefsManager = new HashMap<Gadget, UserPrefs>();

    public SocialUser(User user)
    {
        this.user = user;
    }

    public User getUser()
    {
        return user;
    }

    public void saveUserPref(Gadget gadget, String moduleId, String key, String value, WebSite site)
    {
        if (user == null)
        {
            if (!userprefsManager.containsKey(gadget))
            {
                userprefsManager.put(gadget, new UserPrefs());
            }
            UserPrefs prefs = userprefsManager.get(gadget);
            prefs.put(key, value);
        }
        else
        {
            PersonalizedGadged pgadget = null;

            Iterator<PersonalizedGadged> preferences = PersonalizedGadged.ClassMgr.listPersonalizedGadgedByUser(user);
            while (preferences.hasNext())
            {
                PersonalizedGadged personalizedGadged = preferences.next();
                String urltest = personalizedGadged.getGadget().getUrl();
                if (urltest.equals(gadget.getUrl()))
                {
                    pgadget = personalizedGadged;
                    break;
                }
            }
            if (pgadget == null)
            {
                pgadget = PersonalizedGadged.ClassMgr.createPersonalizedGadged(site);
                pgadget.setGadget(gadget);
                pgadget.setUser(user);
            }
            if (value != null)
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

    public JSONObject getJSONUserPrefs(Gadget gadget, String moduleId)
    {
        JSONObject getJSONUserPrefs = new JSONObject();
        if (user == null)
        {
            if (!userprefsManager.containsKey(gadget))
            {
                userprefsManager.put(gadget, new UserPrefs());
            }
            UserPrefs prefs = userprefsManager.get(gadget);
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
        else
        {
            Iterator<PersonalizedGadged> preferences = PersonalizedGadged.ClassMgr.listPersonalizedGadgedByUser(user);
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
        return getJSONUserPrefs;
    }
}
