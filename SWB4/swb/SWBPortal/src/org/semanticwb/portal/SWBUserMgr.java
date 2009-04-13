/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.portal;

import java.util.HashMap;
import java.util.Iterator;
import java.util.StringTokenizer;
import javax.security.auth.Subject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.semanticwb.Logger;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.Device;
import org.semanticwb.model.SWBContext;
import org.semanticwb.model.SWBVocabulary;
import org.semanticwb.model.User;
import org.semanticwb.model.UserRepository;
import org.semanticwb.model.WebSite;
import org.semanticwb.platform.SWBInstanceObject;
import org.semanticwb.platform.SemanticObject;

/**
 *
 * @author Jei
 */
public class SWBUserMgr 
{
    private static Logger log = SWBUtils.getLogger(SWBUserMgr.class);
    private HashMap<String, SWBSessionObject> sessionobjects;
    private HashMap<String, HttpSession> sessions;
    
    public static final String SUBJECTATT = "_swbsubject";
    
    public SWBUserMgr()
    {
        
    }
    
    public void init() {
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
        UserRepository rep=site.getUserRepository();
        if(rep==null)rep=SWBContext.getDefaultRepository();
        Subject sub=getSubject(request);
        Iterator it=sub.getPrincipals().iterator();
        while(it.hasNext())
        {
            User usr=(User)it.next();

            if(rep.equals(usr.getUserRepository()))
            {
                ret=usr;
                break;
            }
        }
        if(ret==null)
        {
            String language = request.getLocale().getLanguage().trim();
            //language=DBUser.getInstance(repository).getProperty("defaultLanguage",language);        
            
            ret=new User(new SemanticObject(rep.getSemanticObject().getModel(),User.swb_User));
            sub.getPrincipals().add(ret);
            ret.setLanguage(language);
            //TODO: validar dispositivo
            Device dev=getDevice(request, site);
            if(dev!=null)
            {
                //System.out.println("User:"+ret+" device:"+dev);
                ret.setDevice(dev);
            }
            ret.setIp(request.getRemoteAddr());
        }
        return ret;
    }

    private Device getDevice(HttpServletRequest request, WebSite site)
    {
        Device ret=null;
        //Comienza detección de Dispositivo
        String useragent = request.getHeader("User-Agent");
        log.debug("User-Agent:"+useragent);
        //System.out.println("User-Agent:"+useragent);
        if(useragent!=null)
        {
            Iterator<Device> listaDev = site.listDevices();
            int coincide = 0;
            while (listaDev.hasNext())
            {
                Device dev = listaDev.next();
                String auxuseragent=useragent;
                String useragentmatch=dev.getUserAgent();
                //System.out.println("dev: "+dev+" "+useragentmatch);
                if(useragentmatch!=null)
                {
                    int cont = 0;
                    StringTokenizer st = new StringTokenizer(useragentmatch);
                    while (st.hasMoreElements())
                    {
                        String token = st.nextToken();
                        //System.out.println("Token: "+token);
                        int pos = auxuseragent.indexOf(token);
                        //System.out.println("Pos: "+pos);
                        if (pos >= 0)
                        {
                            cont++;
                            auxuseragent = auxuseragent.substring(pos);
                        }
                    }
                    //System.out.println(dev+" cont:"+cont);
                    if (cont > coincide)
                    {
                        coincide = cont;
                        ret=dev;
                        //System.out.println("ret:"+ret);
                        //TODO: Detectar Navegador
    //                    if (recDevice.getUserAgent().indexOf("Mozilla") != -1)
    //                    {
    //                        if (device1.indexOf("MSIE") != -1 || device1.indexOf("Opera") != -1)
    //                        {
    //                            usr.setNavegador("Explorer");
    //                        } else if (device1.indexOf("en") != -1 || device1.indexOf("Netscape") != -1)
    //                        {
    //                            usr.setNavegador("Netscape");
    //                        }
    //                    }
                    }
                }
            }
        }
        return ret;
    }

}