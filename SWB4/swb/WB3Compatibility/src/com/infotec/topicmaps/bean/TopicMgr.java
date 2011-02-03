/**
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
**/


/*
 * TopicMaps.java
 *
 * Created on 23 de abril de 2002, 16:18
 */

package com.infotec.topicmaps.bean;

import com.infotec.topicmaps.*;
import com.infotec.topicmaps.util.*;
import com.infotec.topicmaps.db.*;
import org.apache.xerces.parsers.*;
import org.xml.sax.*;

import java.io.*;
import java.net.URL;
import java.util.*;
import com.infotec.appfw.lib.AFObserver;

import com.infotec.appfw.util.AFUtils;

/** Objeto: Manejador de TopicMaps
 * @author Javier Solis Gonzalez
 * @version
 */
public class TopicMgr implements AFObserver
{
    
    private ArrayList observers = new ArrayList();
    
    public static final String TM_ADMIN="WBAdmin";
    public static final String TM_GLOBAL="WBAGlobal";
    
    static protected TopicMgr instance;       // The single instance
    private PrintWriter log = null;
    private IDGenerator idgenerator;

    protected URL urlMap;
    protected HashMap topicmaps;
    private String encode = "UTF-8";

    /** Creates new TopicMaps */
    protected TopicMgr()
    {
        if (log == null) log = new PrintWriter(System.err);
        idgenerator = new IDGenerator();
        topicmaps = new HashMap();
    }

    protected TopicMgr(java.io.PrintWriter log)
    {
        this();
        if (log != null) this.log = log;
    }

    public void init()
    {
//        InputStream is = getClass().getResourceAsStream("/topicmaps.properties");
//        Properties Props = new Properties();
//        try
//        {
//            Props.load(is);
//        } catch (Exception e)
//        {
//            log(e, "Can't read the properties file. " +
//                   "Make sure topicmaps.properties is in the CLASSPATH", true);
//            return;
//        }
//        //System.setProperty("com.techquila.topicmap.baseURL",Props.getProperty("URLDefault"));
//        log(com.infotec.appfw.util.AFUtils.getLocaleString("locale_core", "log_TopicMgr_init_init"), true);
//
//        try
//        {
//            TopicMap tm = null;
//            if (Props.getProperty("useDataBase").equals("true"))
//            {
//
//                Enumeration en = DBTopicMap.getInstance().getTopicMaps();
//                while (en.hasMoreElements())
//                {
//                    RecTopicMap rectm = (RecTopicMap) en.nextElement();
//                    //System.out.println(rectm.getId());
//                    tm = readTopicMapFromDB(rectm.getId());
//                    if (tm != null) addTopicMap(tm);
//                }
//
//            } else
//            {
//                tm = readTopicMap("/../topicmaps/" + Props.getProperty("TMDefault"));
//                if (tm != null) addTopicMap(tm);
//            }
//            //tm.setId(Props.getProperty("TopicMapID"));
//            setEncode(Props.getProperty("TMEncode", "UTF-8"));
//        } catch (Exception e)
//        {
//            log(e, com.infotec.appfw.util.AFUtils.getLocaleString("locale_core", "error_TopicMgr_init_maintminiterror"), true);
//        }
    }

    static public TopicMgr getInstance()
    {
        if (instance == null)
        {
            makeInstance(null);
        }
        return instance;
    }

    static public TopicMgr getInstance(java.io.PrintWriter log)
    {
        if (instance == null)
        {
            makeInstance(log);
        } else
        {
            instance.setLog(log);
        }
        return instance;
    }
    
    static private synchronized void makeInstance(java.io.PrintWriter log)
    {
        if (instance == null)
        {
            if(log!=null)
            {
                instance = new TopicMgr(log);
            }else
            {
                instance = new TopicMgr();
            }
            instance.init();
        }
    }    

    public static void main(String arg[])
    {
        TopicMgr tm = TopicMgr.getInstance();
        Topic tp = tm.getTopicMap().getTopic("TOP_INFOTEC");
        System.out.println(tp.getDisplayName());
        Iterator it = tp.getChild().iterator();
        while (it.hasNext())
        {
            Topic t = (Topic) it.next();
            System.out.println("-->" + t.getDisplayName());
        }
        tm.writeTopicMap(tm.getTopicMap(), "e:/topic.xml");
    }

    public TopicMap readTopicMap(String filename)
    {
        XTMParser parser = new XTMParser();
        try
        {
            if (getClass().getResource(filename) == null)
            {
                filename = "default.xtm.xml";
            }
            urlMap = getClass().getResource(filename);
            //System.out.println(urlMap);
            return parser.readTopicMap(getClass().getResourceAsStream(filename));
        } catch (IOException ex)
        {
            log(ex, "Error in reading topic map file", true);
            return null;
        } catch (SAXException exsax)
        {
            log(exsax, "Error in parse of topic map file.", true);
            return null;
        }
    }
    
    public void addConfigTopics(TopicMap tm)
    {
        Topic cnf = addConfigTopic(tm,TopicMap.CNF_WBConfig,"Webbuilder Config",null);
        addConfigTopic(tm,TopicMap.CNF_WBRule,"Webbuilder Rules",cnf);
        addConfigTopic(tm,TopicMap.CNF_WBInnerRule,"Webbuilder Inner Rules",cnf);
        addConfigTopic(tm,TopicMap.CNF_WBRole,"Webbuilder Roles",cnf);
        addConfigTopic(tm,TopicMap.CNF_WBPermission,"Webbuilder Permission",cnf);
        addConfigTopic(tm,TopicMap.CNF_WBCamp,"Webbuilder Camp",cnf);
        addConfigTopic(tm,TopicMap.CNF_WBTemplate,"Webbuilder Template",cnf);
        addConfigTopic(tm,TopicMap.REC_WBContent,"Webbuilder Content",cnf);
        addConfigTopic(tm,TopicMap.CNF_WBPFlow,"Webbuilder Publish Flow",cnf);
        addConfigTopic(tm,TopicMap.CNF_WBSortName,"Webbuilder SortName",cnf);
        addConfigTopic(tm,TopicMap.CNF_WBMDTable,"Webbuilder MetaData Table",cnf);
        addConfigTopic(tm,TopicMap.CNF_WBIcon,"Webbuilder Icon",cnf);
        addConfigTopic(tm,TopicMap.CNF_WBSecAction,"Webbuilder Security Action",cnf);
        addConfigTopic(tm,TopicMap.CNF_WBCalendar,"Webbuilder Calendarization",cnf);        
    }
    
    private Topic addConfigTopic(TopicMap tm, String id, String name, Topic type)
    {
        Topic cnf=(Topic)tm.getTopics().get(id);
        if(cnf==null)
        {
            //System.out.println("nwe tm:"+tm.getId()+" id:"+id);
            cnf = new Topic(tm);
            cnf.setId(id);
            BaseName bn = new BaseName(name);
            cnf.addBaseName(bn);
            if(type!=null)cnf.addType(type);
            tm.addTopic(cnf);
            cnf.getDbdata().setSystem(1);
            cnf.getDbdata().setVirtual(true);
        }else if(cnf.getDisplayBaseName()==null)
        {
            //System.out.println("id:"+id+" cnf:"+cnf.getDisplayName());
            BaseName bn = new BaseName(name);
            cnf.addBaseName(bn);
            if(type!=null)cnf.addType(type);
            cnf.getDbdata().setSystem(1);
            cnf.getDbdata().setVirtual(true);        
        }
        return cnf;
    }

    public TopicMap readTopicMapFromDB(String id)
    {
        XTMParser reader = new XTMParser();
        SAXParser parser = new SAXParser();
        parser.setContentHandler(reader);
        try
        {
            DBTopicMap dbtm = DBTopicMap.getInstance();
            RecTopicMap rectm = dbtm.getTopicMap(id);
            //System.out.println("tm:"+rectm.getId());
            //parser.parse(new InputSource(new StringReader(rectm.getXml())));
            parser.parse(new InputSource(new InputStreamReader(new java.io.ByteArrayInputStream(rectm.getXml().getBytes()), encode)));
            TopicMap tm = reader.getTopicMap();
            tm.setId(id);

            ArrayList tptosync = new ArrayList();
            Enumeration en = dbtm.getTopics(rectm.getId());
            while (en.hasMoreElements())
            {
                RecTopic rectp = (RecTopic) en.nextElement();
                try
                {
                    reader.setStartFrom(tm);
                    //System.out.println("tp:"+rectp.getId());
                    //parser.parse(new InputSource(new StringReader(rectp.getXml())));
                    parser.parse(new InputSource(new InputStreamReader(new java.io.ByteArrayInputStream(rectp.getXml().getBytes()), encode)));
                    Topic tp = reader.getTopic();
                    tp.setDbdata(rectp);
                    tptosync.add(tp);
                    //tp.setDBSyncronized(true);

                } catch (Exception e)
                {
                    log(e, com.infotec.appfw.util.AFUtils.getLocaleString("locale_core", "error_TopicMgr_readTopicMapFromDB_TopicTransfError") + rectp.getId(), true);
                }
            }

            //Topicos de configuraci�n
            addConfigTopics(tm);
            
            Enumeration oc = dbtm.getOccurrences(rectm.getId());
            while (oc.hasMoreElements())
            {
                RecOccurrence recocc = (RecOccurrence) oc.nextElement();
                try
                {
                    Topic tp = tm.getTopic(recocc.getIdtp());
                    if (tp != null)
                    {
                        reader.setStartFrom(tp);
                        //************************* descomentar lo siguiente para estar en estandar ********************
                        
                        //System.out.println("occ:"+recocc.getId());
                        //parser.parse(new InputSource(new StringReader(recocc.getXml())));
                        parser.parse(new InputSource(new InputStreamReader(new java.io.ByteArrayInputStream(recocc.getXml().getBytes()),encode)));
                        Occurrence occ=reader.getOccurrence();
                        
                        /************************** aceleracion borrar para estar con estandar ****************************/
//                        boolean data = true;
//
//                        int xt = recocc.getXml().indexOf("topicRef");
//                        int xi = recocc.getXml().indexOf("\"", xt);
//                        int xf = recocc.getXml().indexOf("\"", xi + 1);
//                        String stp = recocc.getXml().substring(xi + 2, xf);
//                        xt = recocc.getXml().indexOf("resourceData", xf);
//                        if (xt == -1)
//                        {
//                            data = false;
//                            xt = recocc.getXml().indexOf("resourceRef", xf);
//                            xi = recocc.getXml().indexOf("\"", xt);
//                            xf = recocc.getXml().indexOf("\"", xi + 1);
//                        } else
//                        {
//                            xi = recocc.getXml().indexOf(">", xt);
//                            xf = recocc.getXml().indexOf("<", xi + 1);
//                        }
//                        String sdt = recocc.getXml().substring(xi + 1, xf);
//                        //System.out.println(tp.getId()+":"+stp+":"+sdt+":"+data);
//
//                        Topic typ = tm.getTopic(stp);
//                        if (typ == null)
//                        {
//                            typ = new Topic();
//                            typ.setId(stp);
//                            tm.addTopic(typ);
//                        }
//
//                        Occurrence occ;
//                        if (data)
//                        {
//                            //if (stp.startsWith("IDM"))
//                            //{
//                                occ = new Occurrence(typ, AFUtils.replaceXMLTags(AFUtils.getInstance().decode(sdt, encode)));
//                           // } else
//                           // {
//                           //     occ = new Occurrence(typ, AFUtils.replaceXMLTags(AFUtils.getInstance().decode(sdt, encode)));
//                            //}
//                            occ.setId(recocc.getId());
//                        } else
//                        {
//                            occ = new Occurrence(typ, null);
//                            occ.setResourceRef(sdt);
//                            occ.setId(recocc.getId());
//                        }
//
//                        tp.addOccurrence(occ);

                        //************************** aceleracion borrar para estar con estandar *****************************/

                        occ.setDbdata(recocc);
                        occ.setDBSyncronized(true);
                    }
                } catch (Exception e)
                {
                    log(e, com.infotec.appfw.util.AFUtils.getLocaleString("locale_core", "error_Topicmgr_readTopicMapFromDB_OccTransfError") + recocc.getId(), true);
                }
            }

            Iterator it = tptosync.iterator();
            while (it.hasNext())
            {
                ((Topic) it.next()).setDBSyncronized(true);
            }

            en = dbtm.getAssociations(rectm.getId());
            while (en.hasMoreElements())
            {
                reader.setStartFrom(tm);
                RecAssociation recass = (RecAssociation) en.nextElement();
                //parser.parse(new InputSource(new StringReader(recass.getXml())));
                parser.parse(new InputSource(new InputStreamReader(new java.io.ByteArrayInputStream(recass.getXml().getBytes()), encode)));
                Association ass = reader.getAssociation();
                ass.setDbdata(recass);
                ass.setDBSyncronized(true);
            }

            tm.setDbdata(rectm);
            tm.setDBSyncronized(true);

            reader = null;
            parser = null;
            System.gc();
            return tm;

        } catch (Exception e)
        {
            log(e, com.infotec.appfw.util.AFUtils.getLocaleString("locale_core", "error_TopicMgr_readTopicMapFromDB_ReadTmError"), true);
            return null;
        }
    }

    public void writeTopicMap(TopicMap topicmap, String filename)
    {
        try
        {
            XTMEncoder encoder = new XTMEncoder(new BufferedOutputStream(new FileOutputStream(filename)));
            //XTMEncoder encoder = new XTMEncoder(System.out);
            encoder.encodeTopicMap(topicmap);
            encoder.writeXml();
            encoder.close();
        } catch (Exception e)
        {
            log(e, com.infotec.appfw.util.AFUtils.getLocaleString("locale_core", "error_TopicMgr_readTopicMapFromDB_xmlcreateError"), true);
        }
    }

    /** almacesa un topicmap en base de datos, no debe de existir este mapa anteriormente en la base de datos
     */
    public void writeTopicMap2DB(TopicMap topicmap, String idadm)//,String title,String description)
    {
        try
        {
            ByteArrayOutputStream str = new ByteArrayOutputStream();
            XTMEncoder encoder = new XTMEncoder();
            encoder.encodeTopicMap(topicmap, false);
            encoder.writeXml(str);
            //out.println(str.toString());
            //RecTopicMap recTopicMap=new RecTopicMap(topicmap.getId(),0,title,topicmap.getHome().getId(),description,1,str.toString(),null,null);
            topicmap.getDbdata().setIdAdm(idadm);
            //topicmap.getDbdata().setActive(1);
            topicmap.getDbdata().setXml(str.toString());
            topicmap.getDbdata().create();

            Iterator it = topicmap.getTopics().values().iterator();
            while (it.hasNext())
            {
                ByteArrayOutputStream stp = new ByteArrayOutputStream();
                Topic tp = (Topic) it.next();
                encoder.clearDocement();
                encoder.encodeTopic(tp, null, false);
                encoder.writeXml(stp);
                //out.println(stp.toString());
                tp.getDbdata().setIdAdm(idadm);
                //tp.getDbdata().setActive(1);
                tp.getDbdata().setIdtm(topicmap.getId());
                tp.getDbdata().setXml(new String(stp.toByteArray()));
                tp.getDbdata().create();
                tp.setDBSyncronized(true);
                //RecTopic recTopic=new RecTopic(tp.getId(),topicmap.getId(),0,1,stp.toString(),null,null,null);
                //recTopic.create();

                Iterator itoc = tp.getOccurrences().iterator();
                while (itoc.hasNext())
                {
                    ByteArrayOutputStream sto = new ByteArrayOutputStream();
                    Occurrence occ = (Occurrence) itoc.next();
                    encoder.clearDocement();
                    encoder.encodeOccurrence(occ, null);
                    encoder.writeXml(sto);
                    //out.println(sto.toString());
                    occ.getDbdata().setIdtm(topicmap.getId());
                    occ.getDbdata().setIdtp(tp.getId());
                    occ.getDbdata().setXml(sto.toString());
                    //occ.getDbdata().setActive(1);
                    occ.getDbdata().create();
                    occ.setDBSyncronized(true);
                    //RecOccurrence recOccurrence=new RecOccurrence(occ.getId(),topicmap.getId(),tp.getId(),sto.toString(),null,0);
                    //recOccurrence.create();
                }
            }

            it = topicmap.getAssociations().values().iterator();
            while (it.hasNext())
            {
                ByteArrayOutputStream stp = new ByteArrayOutputStream();
                Association as = (Association) it.next();
                encoder.clearDocement();
                encoder.encodeAssociation(as, null);
                encoder.writeXml(stp);
                //out.println(stp.toString());
                as.getDbdata().setIdtm(topicmap.getId());
                as.getDbdata().setXml(stp.toString());
                as.getDbdata().create();
                as.setDBSyncronized(true);
                //RecAssociation recAssociation=new RecAssociation(as.getId(),topicmap.getId(),stp.toString(),null);
                //recAssociation.create();
            }
            topicmap.setDBSyncronized(true);

        } catch (Exception e)
        {
            log(e, com.infotec.appfw.util.AFUtils.getLocaleString("locale_core", "error_TopicMgr_readTopicMapFromDB_xmlcreateError"), true);
        }
    }

    public void removeTopicMapFromDB(String id)
    {
        try
        {
            Enumeration enTm = DBTopicMap.getInstance().getAssociations(id);
            while (enTm.hasMoreElements())
            {
                RecAssociation recAso = (RecAssociation) enTm.nextElement();
                recAso.remove();
            }
            Enumeration enOcurre = DBTopicMap.getInstance().getOccurrences(id);
            while (enOcurre.hasMoreElements())
            {
                RecOccurrence recOcurre = (RecOccurrence) enOcurre.nextElement();
                recOcurre.remove();
            }
            Enumeration enTopics = DBTopicMap.getInstance().getTopics(id);
            while (enTopics.hasMoreElements())
            {
                RecTopic recTp = (RecTopic) enTopics.nextElement();
                recTp.remove();
            }
            RecTopicMap recTopicmap = DBTopicMap.getInstance().getTopicMap(id);
            recTopicmap.remove();
        } catch (Exception e)
        {
            log(e, com.infotec.appfw.util.AFUtils.getLocaleString("locale_core", "error_TopicMgr_readTopicMapFromDB_RemoveTmError") + id, true);
        }

    }

    /** Getter for property m_topicMap.
     * @return Value of property m_topicMap.
     */
    public TopicMap getTopicMap()
    {
        Iterator it = topicmaps.values().iterator();
        if (it.hasNext()) return (TopicMap) it.next();
        return null;
    }
    
    public HashMap getIntTopicMaps()
    {
        return topicmaps;
    }

    public TopicMap getTopicMap(String id)
    {
        return (TopicMap) topicmaps.get(id);
    }
    
    /** Setter for property m_topicMap.
     * @param topicMap New value of property m_topicMap.
     */
    public void addTopicMap(TopicMap topicMap)
    {
        this.topicmaps.put(topicMap.getId(), topicMap);
        topicMap.registerObserver(this);
    }

    public void removeTopicMap(String topicMapId)
    {
        TopicMap tm = getTopicMap(topicMapId);
        if (tm != null)
        {
            topicmaps.remove(topicMapId);
            if (tm.isDBSyncronized())
            {
                removeTopicMapFromDB(topicMapId);
            }
            tm.registerObserver(this);
        }
    }

    public void destroy()
    {
        log(com.infotec.appfw.util.AFUtils.getLocaleString("locale_core", "log_TopicMgr_destroy_TopicMgrFinished"), true);
    }

    /** regresa un nuevo id el cual no se repite.
     *
     */
    public String getNewId()
    {
        return idgenerator.getID();
    }

    public IDGenerator getIdGenerator()
    {
        return idgenerator;
    }

    /**
     * @param topicMapid
     * @return  */
    public boolean existTopicMap(String topicMapid)
    {
        Iterator it = this.topicmaps.keySet().iterator();
        while (it.hasNext())
        {
            if (((String) it.next()).equalsIgnoreCase(topicMapid)) return true;
        }
        return false;
        //return m_topics.containsKey(topicid);
    }

    /** Getter for property log.
     * @return Value of property log.
     */
    public java.io.PrintWriter getLog()
    {
        return log;
    }

    /** Setter for property log.
     * @param log New value of property log.
     */
    public void setLog(java.io.PrintWriter log)
    {
        this.log = log;
    }

    /**
     * Writes a message to the log file.
     */
    public void log(String msg)
    {
        log(msg, false);
    }

    /**
     * Writes a message to the log file.
     */
    public void log(String msg, boolean debug)
    {
        log.println(new Date() + ": " + msg);
        if (debug) System.out.println(new Date() + ": " + msg);
    }

    /**
     * Writes a message with an Exception to the log file.
     */
    public void log(Throwable e, String msg)
    {
        log(e, msg, false);
    }

    /**
     * Writes a message with an Exception to the log file.
     */
    public void log(Throwable e, String msg, boolean debug)
    {
        log.println(new Date() + ": " + msg);
        e.printStackTrace(log);
        if (debug)
        {
            System.err.println(new Date() + ": " + msg);
            e.printStackTrace(System.err);
        }
    }

    /** Getter for property topicmaps.
     * @return Value of property topicmaps.
     */
    public Iterator getTopicMaps()
    {
        ArrayList arr=new ArrayList(topicmaps.values());
        if(!AFUtils.getEnv("wb/adminTopicMap","false").equalsIgnoreCase("true"))
        {
            arr.remove(getAdminTopicMap());
        }
        return arr.iterator();
    }
    
    public TopicMap getAdminTopicMap()
    {
        return (TopicMap)topicmaps.get(TM_ADMIN);
    }
    
    public TopicMap getGlobalTopicMap()
    {
        return (TopicMap)topicmaps.get(TM_GLOBAL);
    }    

    /** Avisa al observador de que se ha producido un cambio.
     */
    public void syncFromExternalAction(String s, Object obj)
    {
        //System.out.println("syncFromExternalAction:TopicMgr-->"+s+" "+obj);
        try
        {
            if (obj instanceof RecOccurrence)
            {
                RecOccurrence rec = (RecOccurrence) obj;
                TopicMap tm = getTopicMap(rec.getIdtm());
                Topic tp = tm.getTopic(rec.getIdtp());
                if (tp == null)
                {
                    tp = new Topic();
                    tp.setId(rec.getIdtp());
                    tp.setMap(tm);
                    tm.getTopics().put(tp.getId(), tp);
                }
                Occurrence occ = tp.getOccurrence(rec.getId());
                if (s.equals("remove"))
                {
                    if (occ != null) tp.getOccurrences().remove(occ);
                } else if (s.equals("create"))
                {
                    if (occ == null)
                    {
                        Occurrence.createOccurrence(rec);
                    }
                } else if (s.equals("update"))
                {
                    //System.out.println("active:"+rec.getActive());
                    if (occ != null)
                    {
                        tp.getOccurrences().remove(occ);
                        Occurrence.createOccurrence(rec);
                    }
                }
            } else if (obj instanceof RecTopic)
            {
                RecTopic rec = (RecTopic) obj;
                TopicMap tm = getTopicMap(rec.getIdtm());
                //System.out.println("tm:"+tm+ " s:"+s);
                if (tm != null)
                {
                    Topic tp = tm.getTopic(rec.getId());
                    //System.out.println("tp:"+tp);
                    if (s.equals("remove"))
                    {
                        if (tp != null) tm.removeTopic(rec.getId(), false);
                    } else if (s.equals("create"))
                    {
                        if (tp == null)
                        {
                            Topic.createTopic(rec);
                        } else
                        {
                            tp.updateFromDbData(rec);
                        }
                    } else if (s.equals("update"))
                    {
                        if (tp != null)
                        {
                            tp.updateFromDbData(rec);
                        } else
                        {
                            Topic.createTopic(rec);
                        }
                    }
                }
            } else if (obj instanceof RecAssociation)
            {
                RecAssociation rec = (RecAssociation) obj;
                TopicMap tm = getTopicMap(rec.getIdtm());
                if (tm != null)
                {
                    Association ass = tm.getAssociation(rec.getId());
                    if (s.equals("remove"))
                    {
                        if (ass != null) tm.removeAssociation(rec.getId(), false);
                    } else if (s.equals("create"))
                    {
                        if (ass == null)
                        {
                            Association association = Association.createAssociation(rec);
                            Iterator it = association.getMembers().iterator();
                            while (it.hasNext())
                            {
                                Member me = (Member) it.next();
                                Iterator it2 = me.getTopicRefs().values().iterator();
                                while (it2.hasNext())
                                {
                                    Topic tp = (Topic) it2.next();
                                    if (!tp.getAssociations().contains(association)) tp.getAssociations().add(association);
                                }
                            }
                        }
                    } else if (s.equals("update"))
                    {
                        if (ass != null)
                        {
                            //ass.updateFromDbData(rec);
                        }
                    }
                }
            } else if (obj instanceof RecTopicMap)
            {
                RecTopicMap rec = (RecTopicMap) obj;
                TopicMap tm = getTopicMap(rec.getId());
                if (s.equals("remove") && tm != null)
                {
                    topicmaps.remove(tm.getId());
                    tm.registerObserver(this);
                    //removeTopicMap(tm.getId());
                    //System.out.println("topicmap removed...");
                } else if (s.equals("create") && tm == null)
                {
                    TopicMap tmnew = new TopicMap(rec.getId());
                    tmnew.setDbdata(rec);
                    Topic home = new Topic(tmnew);
                    home.setId(rec.getHome());

                    Topic lang = new Topic(tmnew);
                    lang.setId(rec.getLang());

                    tmnew.addTopic(home);
                    tmnew.addTopic(lang);
                    tmnew.setDBSyncronized(true);
                    addTopicMap(tmnew);
                    //System.out.println("topicmap added...");
                } else if (s.equals("update"))
                {
                }
            }
        } catch (Exception e)
        {
            log(e, com.infotec.appfw.util.AFUtils.getLocaleString("locale_core", "error_TopicMgr_syncFromExternalAction_erroral") + " " + s + " " + com.infotec.appfw.util.AFUtils.getLocaleString("locale_core", "error_TopicMgr_syncFromExternalAction_Sync") + obj, true);
        }
    }

    /** Getter for property encode.
     * @return Value of property encode.
     *
     */
    public java.lang.String getEncode()
    {
        return encode;
    }

    /** Setter for property encode.
     * @param encode New value of property encode.
     *
     */
    public void setEncode(java.lang.String encode)
    {
        this.encode = encode;
    }
    
    /** Avisa al observador de que se ha producido un cambio.
     * @param s
     * @param obj  */
    public void sendDBNotify(String s, Object obj)
    {
        //System.out.println("notify:"+changes);
        Iterator itobs = observers.iterator();
        while (itobs.hasNext())
        {
            ((AFObserver) itobs.next()).sendDBNotify(s, obj);
        }        
    }
    
    /** registra el objeto observador para que pueda recibir notoficaciones de cambios
     * @param obs  */
    public void registerObserver(AFObserver obs)
    {
        if (!observers.contains(obs)) observers.add(obs);
    }
    
    /** registra el objeto observador para que pueda recibir notoficaciones de cambios
     * @param obs  */
    public void removeObserver(AFObserver obs)
    {
        observers.remove(obs);
    }        

}
