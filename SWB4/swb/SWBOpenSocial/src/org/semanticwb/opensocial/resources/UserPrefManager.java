/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.opensocial.resources;

import java.util.HashSet;
import org.semanticwb.opensocial.model.Gadget;

/**
 *
 * @author victor.lorenzana
 */
public class UserPrefManager extends HashSet<UserPrefs> {

    public boolean contains(Gadget gadget,String moduleId)
    {
        if(gadget!=null && moduleId!=null)
        {
            for(UserPrefs prefs : this)
            {
                if(prefs.getGadget().getUrl().equals(gadget.getUrl()) && prefs.getModuleId().equals(moduleId))
                {
                    return true;
                }
            }
        }
        return false;
    }


    public UserPrefs get(Gadget gadget,String moduleId)
    {
        if(gadget!=null && moduleId!=null)
        {
            for(UserPrefs prefs : this)
            {
                if(prefs.getGadget().getUrl().equals(gadget.getUrl()) && prefs.getModuleId().equals(moduleId))
                {
                    return prefs;
                }
            }
        }
        return null;
    }

}
