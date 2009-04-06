/*
 * SWBAccessLog.java
 *
 * Created on 4 de octubre de 2002, 16:18
 */

package org.semanticwb.portal;

import java.net.*;
import java.util.*;
import java.io.*;
import java.lang.reflect.*;

import java.text.*;
import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
import org.semanticwb.base.SWBAppObject;
import org.semanticwb.model.Resource;
import org.semanticwb.model.SWBContext;
import org.semanticwb.model.WebSite;

/** <PRE>
 * objeto: registra hits a archivo log y administra objetos SWBHitCounter
 *
 * Opciones en "wb/accessLogPeriod":
 *      ***           - yearly    (un archivo por a�o)
 *      ***           - monthly   (un archivo por mes ***por defecto***)
 *      ***           - daily     (un archivo diario)
 * </PRE>
 * @author Javier Solis Gonzalez
 */
public class SWBAccessLog implements SWBAppObject
{
    public static Logger log = SWBUtils.getLogger(SWBAccessLog.class);

    static private SWBAccessLog instance = null;       // The single instance

    private HashMap global;                 //tipo 0
    private HashMap device;                 //tipo 1
    private HashMap language;               //tipo 2
    private HashMap topic;                  //tipo 3
    private HashMap usertype;               //tipo 4
    private HashMap session;                //tipo 5
    private HashMap login;                  //tipo 6
    private HashMap uniqueLogin;            //tipo 7
    private ArrayList uniqueLoginIds;       //tipo 7
    private HashMap resource;               //tipo 8

    //public PrintWriter log = null;
    //public PrintWriter logh = null;
    //public PrintWriter logsess = null;
    //public PrintWriter loglogin = null;
    
    public HashMap hlog = null;
    public HashMap hlogh = null;
    public HashMap hlogsess = null;
    public HashMap hloglogin = null;
    
    public String datePatern = null;
    public int lenPatern = 7;

    public String dbpatern=null;
    
    private SimpleDateFormat df = null;
    
    private String workp="/";
    
    private long instanceHits=0;
    private long instanceSess=0;
    private long instanceLogins=0;
    private long instanceUniqueLogins=0;

    /** Creates a new instance of SWBAccessLog */
    public SWBAccessLog()
    {
        log.event("Initializing SWBAccessLog");
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

    /** Get Instance.
     * @return  */
    static public SWBAccessLog getInstance()
    {
        if (instance == null)
        {
            instance = new SWBAccessLog();
            instance.init();
        }
        return instance;
    }
    
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
    
    private void closeLogs()
    {
        closeLogs(hlog);
        closeLogs(hlogh);
        closeLogs(hlogsess);
        closeLogs(hloglogin);
    }    
    
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
    

    public void destroy()
    {
        log.info("Stoping SWBAccessLog...");
        closeLogs();
        updateHits();
        instance=null;                
        log.info("SWBAccessLog End");
    }

    public void init()
    {
        //TODO:
/*
        datePatern=WBUtils.getInstance().getAccessLogDatePatern();
        lenPatern=datePatern.length();
        
        workp=WBUtils.getInstance().getAccessLogPath();
 */

/*        
        String logFile = workp +  "." + datePatern + ".log";
        String hitFile = workp +  "_hits." + datePatern + ".log";
        String hitSess = workp +  "_sess." + datePatern + ".log";
        String hitLogin = workp +  "_logins." + datePatern + ".log";
        AFUtils.log("AccessLog:" + logFile, true);
        AFUtils.log("HitsLog:" + hitFile, true);
        AFUtils.log("SessionLog:" + hitSess, true);
        AFUtils.log("LoginLog:" + hitLogin, true);
        openLog();
 */
    }

/*    
    private void openLog()
    {
        //accesos
        if (log != null)
        {
            log.flush();
            log.close();
            log = null;
        }
 
        String logFile = workp + "." + datePatern + ".log";
        
        try
        {
            log = new PrintWriter(new FileWriter(logFile, true), true);
        } catch (IOException e)
        {
            AFUtils.log(e, com.infotec.appfw.util.AFUtils.getLocaleString("locale_wb2_lib", "error_SWBAccessLog_IOFile") + logFile, true);
            log = new PrintWriter(System.err);
        }
        
        //hits
        if (logh != null)
        {
            logh.flush();
            logh.close();
            logh = null;
        }
        
        String hitFile = workp + "_hits." + datePatern + ".log";
        
        try
        {
            logh = new PrintWriter(new FileWriter(hitFile, true), true);
        } catch (IOException e)
        {
            AFUtils.log(e, com.infotec.appfw.util.AFUtils.getLocaleString("locale_wb2_lib", "error_SWBAccessLog_IOFile") + hitFile, true);
            logh = new PrintWriter(System.err);
        }

        //sesiones
        if (logsess != null)
        {
            logsess.flush();
            logsess.close();
            logsess = null;
        }
        
        String hitSess = workp +  "_sess." + datePatern + ".log";
        
        try
        {
            logsess = new PrintWriter(new FileWriter(hitSess, true), true);
        } catch (IOException e)
        {
            AFUtils.log(e, com.infotec.appfw.util.AFUtils.getLocaleString("locale_wb2_lib", "error_SWBAccessLog_IOFile") + hitSess, true);
            logsess = new PrintWriter(System.err);
        }
        
        //logins
        if (loglogin != null)
        {
            loglogin.flush();
            loglogin.close();
            loglogin = null;
        }

        String hitLogin = workp +  "_logins." + datePatern + ".log";
        
        try
        {
            loglogin = new PrintWriter(new FileWriter(hitLogin, true), true);
        } catch (IOException e)
        {
            AFUtils.log(e, com.infotec.appfw.util.AFUtils.getLocaleString("locale_wb2_lib", "error_SWBAccessLog_IOFile") + hitLogin, true);
            loglogin = new PrintWriter(System.err);
        }                
    }
*/
    
    public void refresh()
    {
    }

    /**
     * @return  */
    public String getPatern()
    {
        return df.format(new Date());
    }
    
    
    public synchronized void log(HashMap hm, String tm, String strFile, String str)
    {
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
     * @param str  */
    public void logAcc(String tm, String str)
    {
        log(hlog,tm,"_acc",str);
    }

    /**
     * @param str  */
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
     * @param str  */
    public void sessionLog(String rep, String str)
    {
        log(hlogsess,rep,"_sess",str);
    }    
    
    /**
     * @param str  */
    public void loginLog(String rep, String str)
    {
        log(hloglogin,rep,"_logins",str);
    }    
    

    /**
     * @param str  */
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
            

            System.out.println("date:"+date);
            System.out.println("ipuser:"+ipuser);
            System.out.println("ipserver:"+ipserver);
            System.out.println("map:"+map);
            System.out.println("topic:"+topic);
            System.out.println("user:"+user);
            System.out.println("usertype:"+usertype);
            System.out.println("device:"+device);
            System.out.println("lang:"+lang);

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
                    WebSite site=SWBContext.getWebSite(restm);
                    Resource res=null;
                    if(site!=null)
                    {
                        res=site.getResource(resid);
                    }
//                    long res = Long.parseLong(resid);
//                    RecResource recr=null;
//                    if(resid.length()>1 && resid.charAt(0)=='0')
//                    {
//                        restm=TopicMgr.TM_GLOBAL;
//                    }
//                    recr = DBResource.getInstance().getResource(restm,res);
//TODO
//                    if (res!=null && res.getHitLog()==1)
                    {
                        resourceHit(restm, res.getId(), sdate);
                    }
                } catch (Exception e)
                {
                    log.error("Error to register resource hits:"+resid,e);
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
     * @param str  */
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
     * @param str  */
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
     * @param map
     * @param sdate  */
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
     * @param sdate  */
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
     * @param map
     * @param rep
     * @param sdate  */
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
     * @param map
     * @param rep
     * @param sdate  */
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
     * @param map
     * @param dev
     * @param sdate  */
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
     * @param map
     * @param lang
     * @param sdate  */
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
     * @param map
     * @param tp
     * @param sdate  */
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
     * @param map
     * @param dev
     * @param sdate  */
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
     * @param map
     * @param ut
     * @param sdate  */
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
     * @param map
     * @return  */
    public long getGlobalHits(String map)
    {
        long ret = 0;
        SWBHitCounter cont = (SWBHitCounter) global.get(map);
        if (cont != null) ret = cont.getHits();
        return ret;
    }

    public long getInstanceHits()
    {
        return instanceHits;
    }

    public long getInstanceSessions()
    {
        return instanceSess;
    }

    public long getInstanceLogins()
    {
        return instanceLogins;
    }
    
    public long getInstanceUniqueLogins()
    {
        return instanceUniqueLogins;
    }    

    /**
     * @param map
     * @param id
     * @return  */
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
     * @param map
     * @param id
     * @return  */
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
     * @param map
     * @param id
     * @return  */
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
     * @param map
     * @param id
     * @return  */
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
     * @param map
     * @param id
     * @return  */
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
    
    private void updateHitsIter(Iterator it)
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
