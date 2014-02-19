/*
 * SemanticWebBuilder es una plataforma para el desarrollo de portales y aplicaciones de integración,
 * colaboración y conocimiento, que gracias al uso de tecnología semántica puede generar contextos de
 * información alrededor de algún tema de interés o bien integrar información y aplicaciones de diferentes
 * fuentes, donde a la información se le asigna un significado, de forma que pueda ser interpretada y
 * procesada por personas y/o sistemas, es una creación original del Fondo de Información y Documentación
 * para la Industria INFOTEC, cuyo registro se encuentra actualmente en trámite.
 *
 * INFOTEC pone a su disposición la herramienta SemanticWebBuilder a través de su licenciamiento abierto al público (‘open source’),
 * en virtud del cual, usted podrá usarlo en las mismas condiciones con que INFOTEC lo ha diseñado y puesto a su disposición;
 * aprender de él; distribuirlo a terceros; acceder a su código fuente y modificarlo, y combinarlo o enlazarlo con otro software,
 * todo ello de conformidad con los términos y condiciones de la LICENCIA ABIERTA AL PÚBLICO que otorga INFOTEC para la utilización
 * del SemanticWebBuilder 4.0.
 *
 * INFOTEC no otorga garantía sobre SemanticWebBuilder, de ninguna especie y naturaleza, ni implícita ni explícita,
 * siendo usted completamente responsable de la utilización que le dé y asumiendo la totalidad de los riesgos que puedan derivar
 * de la misma.
 *
 * Si usted tiene cualquier duda o comentario sobre SemanticWebBuilder, INFOTEC pone a su disposición la siguiente
 * dirección electrónica:
 *  http://www.semanticwebbuilder.org
 */
package org.semanticwb.portal;

import org.semanticwb.security.SWBNeedToChangePassword;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.security.GeneralSecurityException;
import java.security.Principal;
import java.util.HashSet;
import java.util.concurrent.ConcurrentHashMap;
import java.util.Iterator;
import java.util.Set;
import java.util.StringTokenizer;
import javax.security.auth.Subject;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.login.LoginContext;
import javax.security.auth.login.LoginException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.semanticwb.Logger;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBPortal;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.Country;
import org.semanticwb.model.Device;
import org.semanticwb.model.Language;
import org.semanticwb.model.SWBContext;
import org.semanticwb.model.SWBSessionUser;
import org.semanticwb.model.User;
import org.semanticwb.model.UserRepository;
import org.semanticwb.model.WebSite;
import org.semanticwb.platform.SemanticProperty;
import org.semanticwb.servlet.internal.Login;
import org.semanticwb.util.GetIterator;
import org.semanticwb.util.SWBIteratorCache;


// TODO: Auto-generated Javadoc
/**
 * The Class SWBUserMgr.
 * 
 * @author Jei
 */
public class SWBUserMgr 
{
    
    /** The log. */
    private static Logger log = SWBUtils.getLogger(SWBUserMgr.class);
    
    /** The sessionobjects. */
    private ConcurrentHashMap<String, SWBSessionObject> sessionobjects;
    //private HashMap<String, HttpSession> sessions;
    
    /** The Constant SUBJECTATT. */
    public static final String SUBJECTATT = "_swbsubject";
    
    /**
     * Instantiates a new sWB user mgr.
     */
    public SWBUserMgr()
    {
        
    }
    
    /**
     * Inits the.
     */
    public void init() {
        log.event("Initializing SWBUserMgr...");
        sessionobjects=new ConcurrentHashMap<String, SWBSessionObject>();
        //sessions=new HashMap<String, HttpSession>();
    }
    
    /**
     * Gets the subject.
     * 
     * @param request <PRE>Recibe <I>requets</I> para buscar cookie de Usuario y de languaje</PRE>
     * @param website the website
     * @return the subject
     * @return
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
                //sessions.put(ses.getId(), ses);
            }
        }
        if(sessobj.isHaveToChangePassword())
        {
            throw new SWBNeedToChangePassword("To proceed you need to change your password");
        }
        if("true".equalsIgnoreCase(SWBPortal.getSecEnv("session/restrictToSingleIP", "false")))
        {
            if(sessobj.getIpAddress()==null)
            {
                sessobj.setIpAddress(request.getRemoteAddr());
            }else if(!sessobj.getIpAddress().equals(request.getRemoteAddr()))
            {
                throw new SWBIPValidationExeption("The Session IP has Changed, from "+sessobj.getIpAddress()+" to "+request.getRemoteAddr()+", nice try, but keep going...");
            }
        }
        return  sessobj.getSubject(website);
    }

    /**
     * Gets the number of session objects.
     * 
     * @return the number of session objects
     */
    public int getNumberOfSessionObjects()
    {
        return sessionobjects.size();
    }

    /**
     * List session objects.
     * 
     * @return the iterator
     */
    public Iterator<SWBSessionObject> listSessionObjects()
    {
        return sessionobjects.values().iterator();
    }

    /**
     * Unbound session object.
     * 
     * @param sessID the sess id
     */
    public void unboundSessionObject(String sessID)
    {
        sessionobjects.remove(sessID);
        //sessions.remove(sessID);
    }
    
    /**
     * Gets the user.
     * 
     * @param request the request
     * @param site the site
     * @return the user
     */
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

            if(!site.hasLanguage(language))
            {
                Language l=site.getLanguage();
                if(l==null)
                {
                    final WebSite fsite=site;
                    Iterator<Language> i = SWBIteratorCache.getIterator(site.getId()+"_language",new GetIterator() 
                    {
                        @Override
                        public Iterator getIterator()
                        {
                            return fsite.listLanguages();
                        }
                    } , 1000*120);                         
                    //Iterator<Language> i=SWBUtils.Collections.copyIterator(site.listLanguages()).iterator();
                    if(i.hasNext())l=i.next();
                }
                if(l!=null)language=l.getId();
            }

            //language=DBUser.getInstance(repository).getProperty("defaultLanguage",language);        
            
            ret=new SWBSessionUser(rep); 
            if (null==ret.getId() && rep.isUserRepRememberUser()) 
            {
                checkCookie(request, (SWBSessionUser)ret, rep);
            }//System.out.println("retID:"+ret.getId());
            try
            {
                sub.getPrincipals().add(ret);
            }catch(Exception e){log.error(e);}
            
            if(language==null)language="es"; //si todo fallo usamos español por efecto
            
            ret.setLanguage(language); 
            //validar dispositivo
            Device dev=getDevice(request, site);
            if(dev!=null)
            {
                //System.out.println("User:"+ret+" device:"+dev);
                ret.setDevice(dev);
            }
            ret.setIp(request.getRemoteAddr());

            //Validacion de pais
            String scountry = SWBPortal.UTIL.getIPCountryCode(request.getRemoteAddr());
            Country c=null;
            if(scountry!=null)
            {
                if(!scountry.equals("--"))
                {
                    c=site.getCountry(scountry.toLowerCase());
                }
                if(c==null)c=site.getCountry();

                if(c==null)
                {
                    final WebSite fsite=site;
                    Iterator<Country> i = SWBIteratorCache.getIterator(site.getId()+"_country",new GetIterator() 
                    {
                        @Override
                        public Iterator getIterator()
                        {
                            return fsite.listCountries();
                        }
                    } , 1000*120);                    
                    //Iterator<Country> i=SWBUtils.Collections.copyIterator(site.listCountries()).iterator();
                    if(i.hasNext())c=i.next();
                }
            }
            if(c!=null)ret.setCountry(c.getId());

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

    /**
     * Gets the device.
     * 
     * @param request the request
     * @param site the site
     * @return the device
     */
    private Device getDevice(HttpServletRequest request, WebSite site)
    {
        Device ret=null;
        //Comienza detección de Dispositivo
        String useragent = request.getHeader("User-Agent");
        log.debug("User-Agent:"+useragent);
        //System.out.println("User-Agent:"+useragent);
        if(useragent!=null)
        {            
            final WebSite fsite=site;
            Iterator<Device> listaDev = SWBIteratorCache.getIterator(site.getId()+"_device",new GetIterator() 
            {
                @Override
                public Iterator getIterator()
                {
                    return fsite.listDevices();
                }
            } , 1000*120);
            
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
                        }else
                        {
                            cont=0;
                            break;
                        }
                    }
                    //System.out.println(dev+" cont:"+cont);
                    if (cont > coincide)
                    {
                        coincide = cont;
                        ret=dev;
                    }
                }
            }
        }
        return ret;
    }

    /**
     * Login.
     * 
     * @param request the request
     * @param response the response
     * @param website the website
     * @throws ClassNotFoundException the class not found exception
     * @throws InvocationTargetException the invocation target exception
     * @throws InstantiationException the instantiation exception
     * @throws IllegalAccessException the illegal access exception
     * @throws LoginException the login exception
     */
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
        Subject subject = SWBPortal.getUserMgr().getSubject(request, website.getId());
        log.trace("Sending calback:"+callback);
       // request.getSession(true).invalidate();
        LoginContext lc = new LoginContext(ur.getLoginContext(), subject, callback);
        lc.login();
        Iterator it = subject.getPrincipals().iterator();
        if (it.hasNext())
        {
            User user = (User) it.next();
            if(user.getUserRepository().equals(ur))
            {
                log.trace("M user checked?:"+user.hashCode()+":"+user.isSigned());
                Login.sendLoginLog(request, user);
            }
        }
    }

    /**
     * Regresa usuarios en session ordenados por ultimo acceso
     * @param website
     * @return
     */
    public Set<User> getSessionUsers(String website)
    {
        String ur = SWBContext.getWebSite(website).getUserRepository().getId();
        HashSet<User> set=new HashSet();
        Iterator<SWBSessionObject> it=listSessionObjects();
        while (it.hasNext())
        {
            SWBSessionObject so = it.next();
            Subject su=so.getSubjectByUserRep(ur);
            Iterator<Principal> it2=su.getPrincipals().iterator();
            if(it2.hasNext())
            {
                set.add((User)it2.next());
            }
        }
        return set;
    }

    public java.security.KeyPair getSessionKey(HttpServletRequest request){
        return sessionobjects.get(request.getSession().getId()).getKey();
    }

    private void checkCookie(final HttpServletRequest request, final SWBSessionUser session, final UserRepository urep) {
        String id = "swb." + urep.getId();
        if (null != request.getCookies()) {
            for (Cookie current : request.getCookies()) {
                if (id.equals(current.getName())) {
                    try {
                        String value = SWBUtils.TEXT.decodeBase64(current.getValue());
                        SemanticProperty sp = SWBPlatform.getSemanticMgr().getModel(SWBPlatform.getSemanticMgr().SWBAdmin).getSemanticProperty(SWBPlatform.getSemanticMgr().SWBAdminURI + "/PrivateKey");
                        //System.out.println("sp:"+sp);
                        String pass = SWBPlatform.getSemanticMgr().getModel(SWBPlatform.getSemanticMgr().SWBAdmin).getModelObject().getProperty(sp);
                        byte[] buid = SWBUtils.CryptoWrapper.PBEAES128Decipher(pass, value.getBytes());
                        //System.out.println("uid: "+ new String(buid));
                        String sbuid = new String(buid);
                        String uid = sbuid.substring(sbuid.lastIndexOf(':') + 1);
                        User user = urep.getUser(uid);
                        if (null != user) {
                            session.updateUser(user);
                        }
                    } catch (GeneralSecurityException gse) {
                        log.error("checkCookie:", gse);
                    }
                }
            }
        }
    }
}