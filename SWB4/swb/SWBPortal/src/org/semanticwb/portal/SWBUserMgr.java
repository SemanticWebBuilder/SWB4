/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.portal;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.StringTokenizer;
import javax.security.auth.Subject;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.login.LoginContext;
import javax.security.auth.login.LoginException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.semanticwb.Logger;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBPortal;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.Device;
import org.semanticwb.model.SWBContext;
import org.semanticwb.model.User;
import org.semanticwb.model.UserRepository;
import org.semanticwb.model.WebSite;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.servlet.internal.Login;


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
    public Subject getSubject(HttpServletRequest request, String website)
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
        return  sessobj.getSubject(website);
    }

    public void unboundSessionObject(String sessID)
    {
        sessionobjects.remove(sessID);
        sessions.remove(sessID);
    }
    
    public User getUser(HttpServletRequest request, WebSite site)
    {
        User ret=null;
        UserRepository rep=site.getUserRepository();
        if(rep==null)rep=SWBContext.getDefaultRepository();
        Subject sub=getSubject(request, site.getId());
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

            //User session log
            {
                StringBuffer logbuf=new StringBuffer();
                logbuf.append("ses|");
                logbuf.append(request.getRemoteAddr());
                logbuf.append("|");
                logbuf.append(SWBPortal.getMessageCenter().getAddress());
                logbuf.append("|");
                logbuf.append(ret.getSemanticObject().getModel().getName());
                logbuf.append("|");
                String lg=ret.getLogin();
                if(lg==null)lg="_";
                logbuf.append(lg);
                logbuf.append("|");
                logbuf.append(""+request.getSession(true).hashCode());
                SWBPortal.getMessageCenter().sendMessage(logbuf.toString());
            }
            
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

    public void login(HttpServletRequest request, HttpServletResponse response, WebSite website) throws ClassNotFoundException, InvocationTargetException, InstantiationException, IllegalAccessException, LoginException
    {
        UserRepository ur = website.getUserRepository();
        Constructor[] constructor = Class.forName(ur.getCallBackHandlerClassName()).getConstructors();
        int method = 0;
        for (int i = 0; i < constructor.length; i++)
        {
            if (constructor[i].getParameterTypes().length == 4)
            {
                method = i;
            }
        }
        CallbackHandler callback = (CallbackHandler) constructor[method].newInstance(request, response, ur.getAuthMethod(), website.getId());
        LoginContext lc;
        Subject subject = SWBPortal.getUserMgr().getSubject(request, website.getId());
                log.trace("Sending calback:"+callback);
               // request.getSession(true).invalidate();
                lc = new LoginContext(ur.getLoginContext(), subject, callback);
                lc.login();
                User user = null;
                Iterator it = subject.getPrincipals().iterator();
                if (it.hasNext())
                {
                    user = (User) it.next();
                    log.trace("user checked?:"+user.hashCode()+":"+user.isSigned());
                }
                Login.sendLoginLog(request, user);

    }

}