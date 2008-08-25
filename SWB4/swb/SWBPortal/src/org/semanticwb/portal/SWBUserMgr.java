/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.portal;

import java.util.HashMap;
import java.util.Iterator;
import javax.security.auth.Subject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.semanticwb.Logger;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.User;
import org.semanticwb.model.WebSite;
import org.semanticwb.platform.SWBInstanceObject;
import org.semanticwb.platform.SemanticObject;

/**
 *
 * @author Jei
 */
public class SWBUserMgr implements SWBInstanceObject
{
    private Logger log = SWBUtils.getLogger(SWBUserMgr.class);
    private HashMap<String, SWBSessionObject> sessionobjects;
    private HashMap<String, HttpSession> sessions;
    
    public static final String SUBJECTATT = "_swbsubject";
    
    public SWBUserMgr()
    {
        
    }
    
    public void init(SWBPlatform context) {
        log.event("Initializing SWBUserMgr...");
        sessionobjects=new HashMap<String, SWBSessionObject>();
        sessions=new HashMap<String, HttpSession>();
    }
    
    /**
     * @param request <PRE>Recibe <I>requets</I> para buscar cookie de Usuario y de languaje</PRE>
     * @return <PRE>Regresa un objeto <I>WBUser</I></PRE>
     */
    public Subject getSubject(HttpServletRequest request)
    {
        HttpSession ses = request.getSession(true);
        
        SWBSessionObject sessobj=(SWBSessionObject)ses.getAttribute(SUBJECTATT);
        if(sessobj==null)
        {
            sessobj = new SWBSessionObject();
            ses.setAttribute(SUBJECTATT, sessobj);
            if(SWBPlatform.getEnv("swb/usersTrace","false").equals("true"))
            {
                sessionobjects.put(ses.getId(), sessobj);
                sessions.put(ses.getId(), ses);
            }
        }
        return  sessobj.getSubject();
    }    
    
    public User getUser(HttpServletRequest request, WebSite site)
    {
        User ret=null;
        Subject sub=getSubject(request);
        Iterator it=sub.getPrincipals().iterator();
        while(it.hasNext())
        {
            User usr=(User)it.next();
            if(site.getUserRepository().getId().equals(usr.getUserRepository().getId()))
            {
                ret=usr;
                break;
            }
        }
        if(ret==null)
        {
            String language = request.getLocale().getLanguage().trim();
            //language=DBUser.getInstance(repository).getProperty("defaultLanguage",language);        
            
            ret=new User(new SemanticObject(site.getUserRepository().getSemanticObject().getModel()));
            sub.getPrincipals().add(ret);
            ret.setLanguage(language);
            //TODO: validar dispositivo
            //ret.setDevice(XXX);
            ret.setIp(request.getRemoteAddr());
        }
        return ret;
    }
    
}
