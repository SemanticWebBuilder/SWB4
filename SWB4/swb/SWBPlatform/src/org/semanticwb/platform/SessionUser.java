/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.platform;

import java.security.Principal;
import java.util.HashMap;

/**
 *
 * @author Jei
 */
public class SessionUser {

    private static long req=0;
    private HashMap<String, Principal> usrs=new HashMap();

    public SessionUser(Principal user, String usrrep)
    {
        usrs.put(usrrep, user);
        if(usrrep!=null)usrs.put(null, user);
        req++;
    }

    public Principal getUser(String usrrep)
    {
        return usrs.get(usrrep);
    }

    public void setUser(Principal user, String usrrep)
    {
        //System.out.println("setUser:"+user.getName());
        usrs.put(usrrep, user);
        if(usrrep!=null)usrs.put(null, user);
        req++;
    }

    public long geRequestID()
    {
        return req;
    }

}
