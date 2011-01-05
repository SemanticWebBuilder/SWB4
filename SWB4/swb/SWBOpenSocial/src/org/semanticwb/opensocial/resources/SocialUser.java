/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.opensocial.resources;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
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
            Set<UserPrefs> getUserPrefs = new HashSet<UserPrefs>();
            Iterator<PersonalizedGadged> personalizedGadgeds = PersonalizedGadged.ClassMgr.listPersonalizedGadgeds(site);
            while (personalizedGadgeds.hasNext())
            {
                PersonalizedGadged pgadget = personalizedGadgeds.next();
                UserPrefs pref = new UserPrefs(pgadget.getGadget(), pgadget.getId());
                getUserPrefs.add(pref);
            }
            return getUserPrefs.toArray(new UserPrefs[getUserPrefs.size()]);
        }
    }

    public void saveUserPref(Gadget gadget, String moduleId, String key, String value, WebSite site)
    {
        if (user == null)
        {
            if (!userprefsManager.contains(gadget, moduleId))
            {
                userprefsManager.add(new UserPrefs(gadget, moduleId));
            }
            UserPrefs prefs = userprefsManager.get(gadget, moduleId);
            prefs.put(key, value);
        }
        else
        {
            PersonalizedGadged pgadget = null;

            User _user = User.ClassMgr.getUser(user, site);
            if (_user != null)
            {
                Iterator<PersonalizedGadged> preferences = PersonalizedGadged.ClassMgr.listPersonalizedGadgedByUser(_user);
                while (preferences.hasNext())
                {
                    PersonalizedGadged personalizedGadged = preferences.next();
                    String urltest = personalizedGadged.getGadget().getUrl();
                    if (urltest.equals(gadget.getUrl()) && personalizedGadged.getId().equals(moduleId))
                    {
                        pgadget = personalizedGadged;
                        break;
                    }
                }
                if (pgadget != null && value!=null)
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

    public JSONObject getJSONUserPrefs(Gadget gadget, String moduleId,WebSite site)
    {
        JSONObject getJSONUserPrefs = new JSONObject();
        if (user == null)
        {
            System.out.println("getJSONUserPrefs moduleId: "+moduleId);
            UserPrefs prefs = userprefsManager.get(gadget, moduleId);
            if(prefs!=null)
            {
                System.out.println(" found "+moduleId);
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
            User _user = User.ClassMgr.getUser(user, site);
            if (_user != null)
            {
                Iterator<PersonalizedGadged> preferences = PersonalizedGadged.ClassMgr.listPersonalizedGadgedByUser(_user);
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
