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

    public SocialUser(User user)
    {
        this.user = user;
    }

    public User getUser()
    {
        return user;
    }
    public JSONObject getJSONUserPrefs(Gadget gadget, String moduleId)
    {
        JSONObject getJSONUserPrefs = new JSONObject();
        if(user!=null)
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
