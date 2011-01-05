/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.opensocial.resources;

import java.util.HashMap;
import org.semanticwb.opensocial.model.Gadget;

/**
 *
 * @author victor.lorenzana
 */
public class UserPrefs extends HashMap<String, String> {


    private final String moduleId;
    private final Gadget gadget;
    public UserPrefs(Gadget gadget,String moduleId)
    {
        this.moduleId=moduleId;
        this.gadget=gadget;
    }
    public String getModuleId()
    {
        return moduleId;
    }
    public Gadget getGadget()
    {
        return gadget;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        final UserPrefs other = (UserPrefs) obj;
        if ((this.moduleId == null) ? (other.moduleId != null) : !this.moduleId.equals(other.moduleId))
            return false;
        return true;
    }

    @Override
    public int hashCode()
    {
        int hash = 3;
        hash = 97 * hash + (this.moduleId != null ? this.moduleId.hashCode() : 0);
        return hash;
    }

    
}
