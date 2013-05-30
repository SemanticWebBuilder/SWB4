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
package org.semanticwb.portal.access;

import java.util.*;
import java.io.*;

import java.text.*;
import org.semanticwb.Logger;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBPortal;
import org.semanticwb.SWBUtils;
import org.semanticwb.base.SWBAppObject;
import org.semanticwb.model.Resource;
import org.semanticwb.model.SWBContext;

// TODO: Auto-generated Javadoc
/**
 * <PRE>
 * objeto: registra hits a archivo log y administra objetos SWBHitCounter
 * 
 * Opciones en "wb/accessLogPeriod":
 * ***           - yearly    (un archivo por a�o)
 * ***           - monthly   (un archivo por mes ***por defecto***)
 * ***           - daily     (un archivo diario)
 * </PRE>.
 * 
 * @author Javier Solis Gonzalez
 */
public class SWBAccessLog implements SWBAppObject
{
    
    /** The log. */
    public static Logger log = SWBUtils.getLogger(SWBAccessLog.class);

    /** The global. */
    private HashMap global;                 //tipo 0
    
    /** The device. */
    private HashMap device;                 //tipo 1
    
    /** The language. */
    private HashMap language;               //tipo 2
    
    /** The topic. */
    private HashMap topic;                  //tipo 3
    
    /** The usertype. */
    private HashMap usertype;               //tipo 4
    
    /** The session. */
    private HashMap session;                //tipo 5
    
    /** The login. */
    private HashMap login;                  //tipo 6
    
    /** The unique login. */
    private HashMap uniqueLogin;            //tipo 7
    
    /** The unique login ids. */
    private ArrayList uniqueLoginIds;       //tipo 7
    
    /** The resource. */
    private HashMap resource;               //tipo 8

    //public PrintWriter log = null;
    //public PrintWriter logh = null;
    //public PrintWriter logsess = null;
    //public PrintWriter loglogin = null;
    
    /** The hlog. */
    public HashMap hlog = null;
    
    /** The hlogh. */
    public HashMap hlogh = null;
    
    /** The hlogsess. */
    public HashMap hlogsess = null;
    
    /** The hloglogin. */
    public HashMap hloglogin = null;
    
    /** The date patern. */
    public String datePatern = null;
    
    /** The len patern. */
    public int lenPatern = 7;

    /** The dbpatern. */
    public String dbpatern=null;
    
    /** The df. */
    private SimpleDateFormat df = null;
    
    /** The workp. */
    private String workp="/";
    
    /** The instance hits. */
    private long instanceHits=0;
    
    /** The instance sess. */
    private long instanceSess=0;
    
    /** The instance logins. */
    private long instanceLogins=0;
    
    /** The instance unique logins. */
    private long instanceUniqueLogins=0;

    /**
     * Creates a new instance of SWBAccessLog.
     */
    public SWBAccessLog()
    {
        log.event("Initializing SWBAccessLog...");
        global = new HashMap();
        device = new HashMap();
        language = new HashMap();
        topic = new HashMap();
        usertype = new HashMap();
        session = new HashMap();
        login = new HashMap();
        uniqueLoginIds = new ArrayList();
        uniqueLogin = new HashMap();
        resource = new HashMap();
        
        hlog =  new HashMap();
        hlogh =  new HashMap();
        hlogsess =  new HashMap();
        hloglogin =  new HashMap();  
    }

    /**
     * Close logs.
     * 
     * @param logs the logs
     */
    private void closeLogs(HashMap logs)
    {
        if(logs==null)return;
        Iterator it=logs.values().iterator();
        while(it.hasNext())
        {
            PrintWriter o=(PrintWriter)it.next();
            o.close();
            it.remove();
        }
    }
    
    /**
     * Close logs.
     */
    private void closeLogs()
    {
        closeLogs(hlog);
        closeLogs(hlogh);
        closeLogs(hlogsess);
        closeLogs(hloglogin);
    }    
    
    /**
     * Open log.
     * 
     * @param logs the logs
     * @param key the key
     * @param idFile the id file
     * @return the prints the writer
     */
    private PrintWriter openLog(HashMap logs, String key, String idFile)
    {
        String logFile = workp +"_"+key+idFile+"." + datePatern + ".log";        
        
        //accesos
        PrintWriter log2=(PrintWriter)logs.get(key);
        if (log2 != null)
        {
            log2.flush();
            log2.close();
            log2 = null;
            logs.remove(key);
        }

        try
        {
            log2 = new PrintWriter(new FileWriter(logFile, true), true);
            if(log2!=null)logs.put(key,log2);
            return log2;
        } catch (IOException e)
        {
            log.error("SWBAccessLog IOFile Error:"+logFile, e);
            //log = new PrintWriter(System.err);
        }         
        return null;
    }
    

    /* (non-Javadoc)
     * @see org.semanticwb.base.SWBAppObject#destroy()
     */
    /**
     * Destroy.
     */
    public void destroy()
    {
        log.info("Stoping SWBAccessLog...");
        closeLogs();
        updateHits();
        log.info("SWBAccessLog End");
    }

    /* (non-Javadoc)
     * @see org.semanticwb.base.SWBAppObject#init()
     */
    /**
     * Inits the.
     */
    public void init()
    {
        if (SWBPlatform.getEnv("swb/accessLog","/logs/swb_log").startsWith("file:"))
        {
            try {
                workp = new File(SWBPlatform.getEnv("swb/accessLog","/logs/swb_log").substring(5)).getCanonicalPath().replace('\\','/');
            } catch (Exception e) {
                workp = SWBPlatform.getEnv("swb/accessLog","/logs/swb_log").substring(5);
            }
        } else {
            workp = SWBPortal.getWorkPath() + SWBPlatform.getEnv("swb/accessLog");
        }

        try
        {
            File file=new File(workp);
            file.getParentFile().mkdirs();
        }catch(Exception e){log.error("Error Creating Log Directory:"+workp,e);}

        datePatern=getPatern();
        lenPatern=datePatern.length();

    }

    /* (non-Javadoc)
     * @see org.semanticwb.base.SWBAppObject#refresh()
     */
    /**
     * Refresh.
     */
    public void refresh()
    {
    }

    /**
     * Gets the patern.
     * 
     * @return the patern
     * @return
     */
    public String getPatern()
    {
        if(df==null) {
            String period = SWBPlatform.getEnv("swb/accessLogPeriod","monthly");
            if (period.equalsIgnoreCase("yearly")) {
                df = new SimpleDateFormat("yyyy");
            } else if (period != null && period.equalsIgnoreCase("daily")) {
                df = new SimpleDateFormat("yyyy-MM-dd");
            } else {
                df = new SimpleDateFormat("yyyy-MM");
            }
        }
        return df.format(new Date());
    }
    
    
    /**
     * Log.
     * 
     * @param hm the hm
     * @param tm the tm
     * @param strFile the str file
     * @param str the str
     */
    public synchronized void log(HashMap hm, String tm, String strFile, String str)
    {
        //System.out.println("hm:"+hm+" tm:"+tm+" strFile:"+strFile+" str:"+str);
        PrintWriter log=null;
        if (str.startsWith(datePatern))
        {
            log=(PrintWriter)hm.get(tm);
            if (log == null)
            {
                log=openLog(hm,tm,strFile);
            }
        } else
        {
            closeLogs();
            datePatern = str.substring(0, lenPatern);
            log=openLog(hm,tm,strFile);
        }        
        if (log != null) log.println(str);
    }
    
    

    /**
     * Log acc.
     * 
     * @param tm the tm
     * @param str the str
     */
    public void logAcc(String tm, String str)
    {
        log(hlog,tm,"_acc",str);
    }

    /**
     * Hit log.
     * 
     * @param str the str
     */
    public void hitLog(String str)
    {
        StringTokenizer st = new StringTokenizer(str, "|");
        if (st.hasMoreTokens())
        {
            String date = st.nextToken();
            String ipuser = st.nextToken();
            String ipserver = st.nextToken();
            String sess = st.nextToken();
            String map = st.nextToken();
            //String topic = st.nextToken();
            //String rep = st.nextToken();
            //String user = st.nextToken();
            //String usertype = st.nextToken();
            //String device = st.nextToken();
            //String lang = st.nextToken();
            log(hlogh,map,"_hit",str);
        }
    }
    
    /**
     * Session log.
     * 
     * @param rep the rep
     * @param str the str
     */
    public void sessionLog(String rep, String str)
    {
        log(hlogsess,rep,"_sess",str);
    }    
    
    /**
     * Login log.
     * 
     * @param rep the rep
     * @param str the str
     */
    public void loginLog(String rep, String str)
    {
        log(hloglogin,rep,"_logins",str);
    }    
    

    /**
     * Update res.
     * 
     * @param str the str
     */
    public void updateRes(String str)
    {
        //2002-10-07 10:33:31|127.0.0.1|200.38.188.168|egobierno|Egob_Empleo|egob||web|en|275|66|182|3|38|166
        StringTokenizer st = new StringTokenizer(str, "|");
        if (st.hasMoreTokens())
        {
            String date = st.nextToken();
            String ipuser = st.nextToken();
            String ipserver = st.nextToken();
            String sess = st.nextToken();
            String map = st.nextToken();
            String topic = st.nextToken();
            String rep = st.nextToken();
            String user = st.nextToken();
            String usertype = st.nextToken();
            String device = st.nextToken();
            String lang = st.nextToken();
            String stime = st.nextToken();
            
            logAcc(map,str);
            

//            System.out.println("SWBAccessLog-----");
//            System.out.println("date:"+date);
//            System.out.println("ipuser:"+ipuser);
//            System.out.println("ipserver:"+ipserver);
//            System.out.println("map:"+map);
//            System.out.println("topic:"+topic);
//            System.out.println("user:"+user);
//            System.out.println("usertype:"+usertype);
//            System.out.println("device:"+device);
//            System.out.println("lang:"+lang);

            String sdate = date.substring(0, 10);

            if(!sdate.equals(dbpatern))
            {
                if(dbpatern!=null)
                {
                    updateHits();
                }
                dbpatern=sdate;
            }

            globalHit(map, sdate);
            deviceHit(map, device, sdate);
            languageHit(map, lang, sdate);
            topicHit(map, topic, sdate);
            userTypeHit(map, usertype, sdate);
            
            
            while (st.hasMoreTokens())
            {
                String resid = st.nextToken();
                try
                {
                    String restm=map;
                    String res=resid;
                    Resource recr=null;
                    if(resid.length()>1 && resid.charAt(0)=='0')
                    {
                        restm=SWBContext.WEBSITE_GLOBAL;
                        res=resid.substring(1);
                    }
                    recr = SWBContext.getWebSite(restm).getResource(res);
//TODO (validar si el recurso requiere monitoreo
//                    if (recr!=null && recr.getHitLog()==1)
//                    {
//                        resourceHit(restm, ""+res, sdate);
//                    }
                } catch (Exception e)
                {
                    log.error("Error to register Resource Hits:"+resid,e);
                }
            }

//            while (st.hasMoreTokens())
//            {
//                String resid = st.nextToken();
//                //System.out.println("resource:"+resid);
//            }
 
        }
    }
    
    /**
     * Update sess.
     * 
     * @param str the str
     */
    public void updateSess(String str)
    {
        //2002-10-07 10:33:31|127.0.0.1|200.38.188.168|wb|nologin|sadfasdfsdsdfsfasklfjsadf
        StringTokenizer st = new StringTokenizer(str, "|");
        if (st.hasMoreTokens())
        {
            String date = st.nextToken();
            String ipuser = st.nextToken();
            String ipserver = st.nextToken();
            String rep = st.nextToken();
            //String login = st.nextToken();
            //String session = st.nextToken();
            
            sessionLog(rep,str);

            String sdate = date.substring(0, 10);

            if(!sdate.equals(dbpatern))
            {
                if(dbpatern!=null)
                {
                    updateHits();
                }
                dbpatern=sdate;
            }

            globalSess(rep, sdate);
/*
            while (st.hasMoreTokens())
            {
                String other = st.nextToken();
            }
*/
        }
    }
    
    /**
     * Update login.
     * 
     * @param str the str
     */
    public void updateLogin(String str)
    {
        //2002-10-07 10:33:31|127.0.0.1|200.38.188.168|wb|nologin|sadfasdfsdsdfsfasklfjsadf
        StringTokenizer st = new StringTokenizer(str, "|");
        if (st.hasMoreTokens())
        {
            String date = st.nextToken();
            String ipuser = st.nextToken();
            String ipserver = st.nextToken();
            String rep = st.nextToken();
            String login = st.nextToken();
            //String session = st.nextToken();
            
            loginLog(rep,str);

            String sdate = date.substring(0, 10);

            if(!sdate.equals(dbpatern))
            {
                if(dbpatern!=null)
                {
                    updateHits();
                }
                dbpatern=sdate;
            }

            globalLogins(rep, sdate);
            
            if(!uniqueLoginIds.contains(login))
            {
                globalUniqueLogins(rep, sdate);
                uniqueLoginIds.add(login);
            }
            
/*
            while (st.hasMoreTokens())
            {
                String other = st.nextToken();
            }
*/
        }
    }    

    /**
     * Global hit.
     * 
     * @param map the map
     * @param sdate the sdate
     */
    public void globalHit(String map, String sdate)
    {
        instanceHits++;
        Object obj = global.get(map);
        if (obj != null)
        {
            SWBHitCounter cont = (SWBHitCounter) obj;
            cont.hit(sdate);
        } else
        {
            SWBHitCounter cont = new SWBHitCounter(map, null, 0, 0, sdate);
            global.put(map, cont);
            cont.hit(sdate);
        }
    }
    
    /**
     * Global sess.
     * 
     * @param rep the rep
     * @param sdate the sdate
     */
    public void globalSess(String rep, String sdate)
    {
        instanceSess++;
        Object obj = session.get(rep);
        if (obj != null)
        {
            SWBHitCounter cont = (SWBHitCounter) obj;
            cont.hit(sdate);
        } else
        {
            SWBHitCounter cont = new SWBHitCounter(rep, null, 5, 0, sdate);
            session.put(rep, cont);
            cont.hit(sdate);
        }
    }    
    
    /**
     * Global unique logins.
     * 
     * @param rep the rep
     * @param sdate the sdate
     */
    public void globalUniqueLogins(String rep, String sdate)
    {
        instanceUniqueLogins++;
        Object obj = uniqueLogin.get(rep);
        if (obj != null)
        {
            SWBHitCounter cont = (SWBHitCounter) obj;
            cont.hit(sdate);
        } else
        {
            SWBHitCounter cont = new SWBHitCounter(rep, null, 7, 0, sdate);
            uniqueLogin.put(rep, cont);
            cont.hit(sdate);
        }
    }      
    
    /**
     * Global logins.
     * 
     * @param rep the rep
     * @param sdate the sdate
     */
    public void globalLogins(String rep, String sdate)
    {
        instanceLogins++;
        Object obj = login.get(rep);
        if (obj != null)
        {
            SWBHitCounter cont = (SWBHitCounter) obj;
            cont.hit(sdate);
        } else
        {
            SWBHitCounter cont = new SWBHitCounter(rep, null, 6, 0, sdate);
            login.put(rep, cont);
            cont.hit(sdate);
        }
    }     

    /**
     * Device hit.
     * 
     * @param map the map
     * @param dev the dev
     * @param sdate the sdate
     */
    public void deviceHit(String map, String dev, String sdate)
    {
        Object obj = device.get(map);
        if (obj != null)
        {
            //System.out.println("se encontro mapa:"+map+" obj:"+obj);
            HashMap ma = (HashMap) obj;
            obj = ma.get(dev);
            if (obj != null)
            {
                //System.out.println("se encontro dev:"+dev);
                SWBHitCounter cont = (SWBHitCounter) obj;
                cont.hit(sdate);
            } else
            {
                //System.out.println("no se encontro dev:"+dev);
                SWBHitCounter cont = new SWBHitCounter(map, dev, 1, 0, sdate);
                ma.put(dev, cont);
                cont.hit(sdate);
            }
        } else
        {
            //System.out.println("no se encontro mapa:"+map);
            HashMap ma = new HashMap();
            device.put(map, ma);
            SWBHitCounter cont = new SWBHitCounter(map, dev, 1, 0, sdate);
            ma.put(dev, cont);
            cont.hit(sdate);
        }
    }

    /**
     * Language hit.
     * 
     * @param map the map
     * @param lang the lang
     * @param sdate the sdate
     */
    public void languageHit(String map, String lang, String sdate)
    {
        Object obj = language.get(map);
        if (obj != null)
        {
            HashMap ma = (HashMap) obj;
            obj = ma.get(lang);
            if (obj != null)
            {
                SWBHitCounter cont = (SWBHitCounter) obj;
                cont.hit(sdate);
            } else
            {
                SWBHitCounter cont = new SWBHitCounter(map, lang, 2, 0, sdate);
                ma.put(lang, cont);
                cont.hit(sdate);
            }
        } else
        {
            HashMap ma = new HashMap();
            language.put(map, ma);
            SWBHitCounter cont = new SWBHitCounter(map, lang, 2, 0, sdate);
            ma.put(lang, cont);
            cont.hit(sdate);
        }
    }

    /**
     * Topic hit.
     * 
     * @param map the map
     * @param tp the tp
     * @param sdate the sdate
     */
    public void topicHit(String map, String tp, String sdate)
    {
        Object obj = topic.get(map);
        if (obj != null)
        {
            HashMap ma = (HashMap) obj;
            obj = ma.get(tp);
            if (obj != null)
            {
                SWBHitCounter cont = (SWBHitCounter) obj;
                cont.hit(sdate);
            } else
            {
                SWBHitCounter cont = new SWBHitCounter(map, tp, 3, 0, sdate);
                ma.put(tp, cont);
                cont.hit(sdate);
            }
        } else
        {
            HashMap ma = new HashMap();
            topic.put(map, ma);
            SWBHitCounter cont = new SWBHitCounter(map, tp, 3, 0, sdate);
            ma.put(tp, cont);
            cont.hit(sdate);
        }
    }
    
    /**
     * Resource hit.
     * 
     * @param map the map
     * @param res the res
     * @param sdate the sdate
     */
    public void resourceHit(String map, String res, String sdate)
    {
        Object obj = resource.get(map);
        if (obj != null)
        {
            //System.out.println("se encontro mapa:"+map+" obj:"+obj);
            HashMap ma = (HashMap) obj;
            obj = ma.get(res);
            if (obj != null)
            {
                //System.out.println("se encontro dev:"+dev);
                SWBHitCounter cont = (SWBHitCounter) obj;
                cont.hit(sdate);
            } else
            {
                //System.out.println("no se encontro dev:"+dev);
                SWBHitCounter cont = new SWBHitCounter(map, res, 8, 0, sdate);
                ma.put(res, cont);
                cont.hit(sdate);
            }
        } else
        {
            //System.out.println("no se encontro mapa:"+map);
            HashMap ma = new HashMap();
            device.put(map, ma);
            SWBHitCounter cont = new SWBHitCounter(map, res, 8, 0, sdate);
            ma.put(res, cont);
            cont.hit(sdate);
        }
    }    

    /**
     * User type hit.
     * 
     * @param map the map
     * @param ut the ut
     * @param sdate the sdate
     */
    public void userTypeHit(String map, String ut, String sdate)
    {
        Object obj = usertype.get(map);
        if (obj != null)
        {
            HashMap ma = (HashMap) obj;
            obj = ma.get(ut);
            if (obj != null)
            {
                SWBHitCounter cont = (SWBHitCounter) obj;
                cont.hit(sdate);
            } else
            {
                SWBHitCounter cont = new SWBHitCounter(map, ut, 4, 0, sdate);
                ma.put(ut, cont);
                cont.hit(sdate);
            }
        } else
        {
            HashMap ma = new HashMap();
            usertype.put(map, ma);
            SWBHitCounter cont = new SWBHitCounter(map, ut, 4, 0, sdate);
            ma.put(ut, cont);
            cont.hit(sdate);
        }
    }


    /**
     * Gets the global hits.
     * 
     * @param map the map
     * @return the global hits
     * @return
     */
    public long getGlobalHits(String map)
    {
        long ret = 0;
        SWBHitCounter cont = (SWBHitCounter) global.get(map);
        if (cont != null) ret = cont.getHits();
        return ret;
    }

    /**
     * Gets the instance hits.
     * 
     * @return the instance hits
     */
    public long getInstanceHits()
    {
        return instanceHits;
    }

    /**
     * Gets the instance sessions.
     * 
     * @return the instance sessions
     */
    public long getInstanceSessions()
    {
        return instanceSess;
    }

    /**
     * Gets the instance logins.
     * 
     * @return the instance logins
     */
    public long getInstanceLogins()
    {
        return instanceLogins;
    }
    
    /**
     * Gets the instance unique logins.
     * 
     * @return the instance unique logins
     */
    public long getInstanceUniqueLogins()
    {
        return instanceUniqueLogins;
    }    

    /**
     * Gets the device hits.
     * 
     * @param map the map
     * @param id the id
     * @return the device hits
     * @return
     */
    public long getDeviceHits(String map, String id)
    {
        long ret = 0;
        HashMap aux = (HashMap) device.get(map);
        if (aux != null)
        {
            SWBHitCounter cont = (SWBHitCounter) aux.get(id);
            if (cont != null) ret = cont.getHits();
        }
        return ret;
    }

    /**
     * Gets the language hits.
     * 
     * @param map the map
     * @param id the id
     * @return the language hits
     * @return
     */
    public long getLanguageHits(String map, String id)
    {
        long ret = 0;
        HashMap aux = (HashMap) language.get(map);
        if (aux != null)
        {
            SWBHitCounter cont = (SWBHitCounter) aux.get(id);
            if (cont != null) ret = cont.getHits();
        }
        return ret;
    }

    /**
     * Gets the topic hits.
     * 
     * @param map the map
     * @param id the id
     * @return the topic hits
     * @return
     */
    public long getTopicHits(String map, String id)
    {
        long ret = 0;
        HashMap aux = (HashMap) topic.get(map);
        if (aux != null)
        {
            SWBHitCounter cont = (SWBHitCounter) aux.get(id);
            if (cont != null) ret = cont.getHits();
        }
        return ret;
    }
    
    /**
     * Gets the resource hits.
     * 
     * @param map the map
     * @param id the id
     * @return the resource hits
     * @return
     */
    public long getResourceHits(String map, String id)
    {
        long ret = 0;
        HashMap aux = (HashMap) resource.get(map);
        if (aux != null)
        {
            SWBHitCounter cont = (SWBHitCounter) aux.get(id);
            if (cont != null) ret = cont.getHits();
        }
        return ret;
    }    

    /**
     * Gets the user type hits.
     * 
     * @param map the map
     * @param id the id
     * @return the user type hits
     * @return
     */
    public long getUserTypeHits(String map, String id)
    {
        long ret = 0;
        HashMap aux = (HashMap) usertype.get(map);
        if (aux != null)
        {
            SWBHitCounter cont = (SWBHitCounter) aux.get(id);
            if (cont != null) ret = cont.getHits();
        }
        return ret;
    }

   
    /**
     * Update hits.
     */
    public void updateHits()
    {
        log.info("Updating hits...");
        log.debug("UpdateHits Topic...");
        updateHitsIter(topic.values().iterator());
        log.debug("UpdateHits Device...");
        updateHitsIter(device.values().iterator());
        log.debug("UpdateHits Language...");
        updateHitsIter(language.values().iterator());
        log.debug("UpdateHits UserType...");
        updateHitsIter(usertype.values().iterator());
        
        log.debug("UpdateHits Global...");
        Iterator it2=global.values().iterator();
        while(it2.hasNext())
        {
            SWBHitCounter cont=(SWBHitCounter)it2.next();
            cont.update();
        }
        
        log.debug("UpdateHits Sessions...");
        it2=session.values().iterator();
        while(it2.hasNext())
        {
            SWBHitCounter cont=(SWBHitCounter)it2.next();
            cont.update();
        }        
        
        log.debug("UpdateHits Logins...");
        it2=login.values().iterator();
        while(it2.hasNext())
        {
            SWBHitCounter cont=(SWBHitCounter)it2.next();
            cont.update();
        }        
        
        log.debug("UpdateHits UniqueLogins...");
        it2=uniqueLogin.values().iterator();
        while(it2.hasNext())
        {
            SWBHitCounter cont=(SWBHitCounter)it2.next();
            cont.update();
        }        
        uniqueLoginIds.clear();
        
        log.debug("UpdateHits Resources...");
        updateHitsIter(resource.values().iterator());        
    }
    
    /**
     * Update hits iter.
     * 
     * @param it the it
     */
    public void updateHitsIter(Iterator it)
    {
        while(it.hasNext())
        {
            Object obj=it.next();
            //System.out.println("Obj:"+obj);
            Iterator it2=((HashMap)obj).values().iterator();
            while(it2.hasNext())
            {
                SWBHitCounter cont=(SWBHitCounter)it2.next();
                cont.update();
            }
        }
    }    

    /**
     * Getter for property workp.
     * @return Value of property workp.
     */
    public java.lang.String getWorkPath()
    {
        return workp;
    }
    
    /**
     * Setter for property workp.
     * @param workp New value of property workp.
     */
    public void setWorkPath(java.lang.String workp)
    {
        this.workp = workp;
    }

}
