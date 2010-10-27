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
 * TopicMap.java
 *
 * Created on 22 de abril de 2002, 15:52
 */

package com.infotec.topicmaps;

import com.infotec.topicmaps.bean.*;
import com.infotec.topicmaps.db.*;
import com.infotec.topicmaps.util.*;
import java.io.*;
import java.util.*;
import com.infotec.wb.core.*;
import com.infotec.appfw.lib.AFObserver;
import com.infotec.appfw.util.AFUtils;
import org.semanticwb.model.WebPage;

/**
 * Objeto <B>TopicMap</B> que es parte de la implementacion del estandar XTM de TopicMaps.
 * <BR>
 * A topic map is a collection of topics, associations, and scopes (collectively called topic map nodes) that may exist in one of two
 * forms:<BR>
 * <BR>
 * a serialized interchange format (e.g. as a topic map document expressed in XTM or some other syntax), or
 * an application-internal form, as constrained by the XTM Processing Requirements defined in Annex F
 * The purpose of a topic map is to convey knowledge about resources through a superimposed layer, or map, of the resources.
 * A topic map captures the subjects of which resources speak, and the relationships between resources, in a way that is implementation-independent.
 * @author Javier Solis Gonzalez
 * @version 1.1
 */
public class TopicMap
{
    org.semanticwb.model.WebSite ws = null;

    public TopicMap(org.semanticwb.model.WebSite website)
    {
        this(website.getId());
        ws = website;
    }

    public org.semanticwb.model.WebSite getNative()
    {
        return ws;
    }


    public static final String CNF_WBConfig="CNF_WBConfig";
    public static final String CNF_WBRule="CNF_WBRule";
    public static final String CNF_WBInnerRule="CNF_WBInnerRule";
    public static final String CNF_WBRole="CNF_WBRole";
    public static final String CNF_WBPermission="CNF_WBPermission";
    public static final String CNF_WBCamp="CNF_WBCamp";
    public static final String CNF_WBTemplate="CNF_WBTemplate";
    public static final String REC_WBContent="REC_WBContent";
    public static final String CNF_WBPFlow="CNF_WBPFlow";
    public static final String CNF_WBSortName="CNF_WBSortName";
    public static final String CNF_WBMDTable="CNF_WBMDTable";
    public static final String CNF_WBIcon="CNF_WBIcon";
    public static final String CNF_WBSecAction="CNF_WBSecAction";     
    public static final String CNF_WBCalendar="CNF_WBCalendar";     
    
    private ArrayList observers = new ArrayList();

    protected String m_id;
    protected String m_xmlns = "http://www.topicmaps.org/xtm/1.0/";
    protected String m_xmlns_xlink = "http://www.w3.org/1999/xlink";
    protected String m_xml_base;

    protected HashMap m_topics;       //Topic
    protected HashMap m_associations; //Association
    protected HashMap m_mergemap;     //MergeMap
    protected Topic m_home;           //Topico Inicial
    protected Topic m_lang;           //Topico del idioma por defecto

    protected RecTopicMap dbdata = null;  //objeto de base de datos, si es nulo, no se sincroniza con base de datos
    protected boolean syncronized = false;

    protected List changes;        //cambios del mapa

    /** Creates new TopicMap */
    public TopicMap()
    {
        this(TopicMgr.getInstance().getNewId());
    }

    /**
     * @param id  */
    public TopicMap(String id)
    {
        this.m_id = id;
        m_topics = new HashMap();
        m_associations = new HashMap();
        m_mergemap = new HashMap();
        changes = Collections.synchronizedList(new ArrayList());
        dbdata = new RecTopicMap(m_id);
    }

    /** Getter for property m_id.
     * @return Value of property m_id.
     */
    public String getId()
    {
        return m_id;
    }

    /** Setter for property m_id.
     * @param id New value of property m_id.
     */
    public void setId(String id)
    {
        this.m_id = id;
        dbdata.setId(id);
    }

    /** Getter for property m_xmlns.
     * @return Value of property m_xmlns.
     */
    public String getXmlns()
    {
        return m_xmlns;
    }

    /** Setter for property m_xmlns.
     * @param xmlns New value of property m_xmlns.
     */
    public void setXmlns(String xmlns)
    {
        this.m_xmlns = xmlns;
    }

    /** Getter for property m_xmlns_xlink.
     * @return Value of property m_xmlns_xlink.
     */
    public java.lang.String getXmlns_xlink()
    {
        return m_xmlns_xlink;
    }

    /** Setter for property m_xmlns_xlink.
     * @param xmlns_xlink New value of property m_xmlns_xlink.
     */
    public void setXmlns_xlink(java.lang.String xmlns_xlink)
    {
        this.m_xmlns_xlink = xmlns_xlink;
    }

    /** Getter for property m_xml_base.
     * @return Value of property m_xml_base.
     */
    public String getXml_base()
    {
        return m_xml_base;
    }

    /** Setter for property m_xml_base.
     * @param xml_base New value of property m_xml_base.
     */
    public void setXml_base(String xml_base)
    {
        this.m_xml_base = xml_base;
    }

    /** Getter for property m_topics.
     * @return Value of property m_topics.
     */
    public HashMap getTopics()
    {
        return m_topics;
    }

    /** Setter for property m_topics.
     * @param topics New value of property m_topics.
     */
    public void setTopics(HashMap topics)
    {
        this.m_topics = topics;
    }

    /** Getter for property m_associations.
     * @return Value of property m_associations.
     */
    public HashMap getAssociations()
    {
        return m_associations;
    }

    /** Setter for property m_associations.
     * @param associations New value of property m_associations.
     */
    public void setAssociations(HashMap associations)
    {
        this.m_associations = associations;
    }

    /** Getter for property m_mergemap.
     * @return Value of property m_mergemap.
     */
    public HashMap getMergeMaps()
    {
        return m_mergemap;
    }

    /** Setter for property m_mergemap.
     * @param mergemap New value of property m_mergemap.
     */
    public void setMergeMap(HashMap mergemap)
    {
        this.m_mergemap = mergemap;
    }

    /**
     * @param id
     * @return  */
    public Topic getTopic(String id)
    {
        return getTopic(id, false);
    }

    /** trae un topico deleted o no
     * @param id
     * @param deleted
     * @return  */
    public Topic getTopic(String id, boolean deleted)
    {
        WebPage page=ws.getWebPage(id);
        if(page!=null)
        {
            return new Topic(page);
        }else return null;
    }

    /**
     * @param id
     * @return  */
    public Association getAssociation(String id)
    {
        return (Association) m_associations.get(id);
    }

    /**
     * @param id
     * @return  */
//    public MergeMap getMergeMap(String id)
//    {
//        return (MergeMap) m_mergemap.get(id);
//    }

    /** Getter for property m_home.
     * @return Value of property m_home.
     */
    public Topic getHome()
    {
        return m_home;
    }

    /**
     * @param home  */
    public void setHome(Topic home)
    {
        m_home = home;
        dbdata.setHome(home.getId());
        if (syncronized) changes.add("um:");
    }

    /** Getter for property m_lang.
     * @return Value of property m_lang.
     */
    public Topic getlang()
    {
        return m_lang;
    }

    public Topic getTopicLang(String language)
    {
        return this.getTopic("IDM_WB" + language);
    }

    /**
     * @param lang  */
    public void setLang(Topic lang)
    {
        m_lang = lang;
        dbdata.setLang(lang.getId());
        if (syncronized) changes.add("um:");
    }

    /**
     * @param topic  */
    public void addTopic(Topic topic)
    {
        topic.m_parent = this;
        m_topics.put(topic.getId(), topic);
        if (syncronized) changes.add("at:" + topic.getId());
    }

    /**
     * @param association  */
    public void addAssociation(Association association)
    {
        addAssociation(association, true);
    }

    /**
     * @param association
     * @param log  */
    public void addAssociation(Association association, boolean log)
    {
        association.m_parent = this;
        m_associations.put(association.getId(), association);

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
        if (syncronized && log) changes.add("aa:" + association.getId());
    }

//    /**
//     * @param mergemap  */
//    public void addMergeMap(MergeMap mergemap)
//    {
//        m_mergemap.put(mergemap.getId(), mergemap);
//    }

    public void removeTopicandChild(String id)
    {
        removeTopicandChild(null, id);
    }

    public void removeTopicandChild(WBUser user, String id)
    {
        removeTopic(user, id);
    }


    public void removeTopic(WBUser admuser, String id)
    {
        removeTopic(admuser, id, true);
    }

    /**
     * @param id  */
    public void removeTopic(String id)
    {
        removeTopic(id, true);
    }

    /**
     * @param id
     * @param log  */
    public void removeTopic(String id, boolean log)
    {
        removeTopic(null, id, log);
    }


    /**
     * @param id
     * @param log  */
    public void removeTopic(WBUser user, String id, boolean log)
    {
        WebPage wp = ws.getWebPage(id);
        if(null!=wp)wp.remove();
    }


    /** Remueve todas las asociaciones en donde intervenga el topico ya sea como miembro, role o tipo.
     * @param tp  */
    public void removeAssociationsWithTopic(Topic tp)
    {
        removeAssociationsWithTopic(tp, true);
    }

    /** Remueve todas las asociaciones en donde intervenga el topico ya sea como miembro, role o tipo.
     * @param tp
     * @param log  */
    public void removeAssociationsWithTopic(Topic tp, boolean log)
    {
        Iterator it = this.m_associations.values().iterator();
        while (it.hasNext())
        {
            boolean remove = false;
            Association aux = (Association) it.next();
            if (aux.getType() == tp) remove = true;
            Iterator me = aux.getMembers().iterator();
            while (me.hasNext())
            {
                Member member = (Member) me.next();
                Iterator resref = member.getTopicRefs().values().iterator();
                while (resref.hasNext())
                {
                    Topic top = (Topic) resref.next();
                    if (top == tp) remove = true;
                }
                if (member.getRoleSpec().getTopicRef() == tp) remove = true;
            }
            if (remove)
            {
                //System.out.println("removed:"+aux.getId());
                it.remove();

                Iterator itm = aux.getMembers().iterator();
                while (itm.hasNext())
                {
                    Member mem = (Member) itm.next();
                    Iterator itr = mem.getTopicRefs().values().iterator();
                    while (itr.hasNext())
                    {
                        Topic tpc = (Topic) itr.next();
                        tpc.getAssociations().remove(aux);
                    }
                }
                if (syncronized && log) changes.add("ra:" + aux.getId());

            }
        }
    }

    /** Remueve todas las asociaciones en donde intervenga el topico solo como miembro.
     * @param tp  */
    public void removeAssociationsToTopic(Topic tp)
    {
        Iterator it = this.m_associations.values().iterator();
        while (it.hasNext())
        {
            boolean remove = false;
            Association aux = (Association) it.next();
            Iterator me = aux.getMembers().iterator();
            while (me.hasNext())
            {
                Member member = (Member) me.next();
                Iterator resref = member.getTopicRefs().values().iterator();
                while (resref.hasNext())
                {
                    Topic top = (Topic) resref.next();
                    if (top == tp) remove = true;
                }
            }
            if (remove)
            {
                it.remove();

                Iterator itm = aux.getMembers().iterator();
                while (itm.hasNext())
                {
                    Member mem = (Member) itm.next();
                    Iterator itr = mem.getTopicRefs().values().iterator();
                    while (itr.hasNext())
                    {
                        Topic tpc = (Topic) itr.next();
                        tpc.getAssociations().remove(aux);
                    }
                }
                if (syncronized) changes.add("ra:" + aux.getId());
            }
        }
    }

    /**
     * @param id  */
    public void removeAssociation(String id)
    {
        removeAssociation(id, true);
    }

    /**
     * @param id
     * @param log  */
    public void removeAssociation(String id, boolean log)
    {
        Association aux = (Association) m_associations.get(id);
        m_associations.remove(id);

        Iterator itm = aux.getMembers().iterator();
        while (itm.hasNext())
        {
            Member mem = (Member) itm.next();
            Iterator itr = mem.getTopicRefs().values().iterator();
            while (itr.hasNext())
            {
                Topic tpc = (Topic) itr.next();
                tpc.getAssociations().remove(aux);
            }
        }
        if (syncronized && log) changes.add("ra:" + id);
    }

    /**
     * @param id  */
    public void removeMergeMap(String id)
    {
        m_mergemap.remove(id);
        if (syncronized) changes.add("rm:" + id);
    }

    /** Getter for property dbdata.
     * @return Value of property dbdata.
     */
    public com.infotec.topicmaps.db.RecTopicMap getDbdata()
    {
        return dbdata;
    }

    /** Setter for property dbdata.
     * @param dbdata New value of property dbdata.
     */
    public void setDbdata(com.infotec.topicmaps.db.RecTopicMap dbdata)
    {
        this.dbdata = dbdata;
    }

    public synchronized void update2DB()
    {
        //
//        StringBuffer sendChanges = new StringBuffer();
//
//        //synchronized (changes)
//        {
//            Iterator it = changes.iterator();
//            try
//            {
//                while (it.hasNext())
//                {
//                    String str = (String) it.next();
//                    it.remove();
//                    sendChanges.append(str);
//                    sendChanges.append("\n");
//                    //System.out.println(str);
//
//                    String id = str.substring(3);
//                    if (str.startsWith("rt:"))
//                    {
//                        try
//                        {
//                            DBTopicMap.getInstance().getTopic(this.getId(), id).remove();
//                        } catch (Exception e)
//                        {
//                            TopicMgr.getInstance().log(e, com.infotec.appfw.util.AFUtils.getLocaleString("locale_core", "error_TopicMap_update2DB_removeTopic2DBError") + ":" + id, true);
//                        }
//                    } else if (str.startsWith("at:"))
//                    {
//                        try
//                        {
//                            Topic tp = this.getTopic(id, true);
//                            ByteArrayOutputStream xml = new ByteArrayOutputStream();
//                            XTMEncoder encoder = new XTMEncoder();
//                            encoder.encodeTopic(tp, null, false);
//                            encoder.writeXml(xml);
//                            //tp.getDbdata().setActive(0);
//                            tp.getDbdata().setXml(xml.toString());
//                            tp.getDbdata().setIdtm(this.getId());
//                            tp.getDbdata().create();
//                            tp.setDBSyncronized(true);
//                        } catch (Exception e)
//                        {
//                            TopicMgr.getInstance().log(e, com.infotec.appfw.util.AFUtils.getLocaleString("locale_core", "error_TopicMap_update2DB_updateTopic2DBError") + ":" + id, true);
//                            this.getTopics().remove(id);
//                        }
//                    } else if (str.startsWith("ut:"))
//                    {
//                        try
//                        {
//                            Topic tp = this.getTopic(id, true);
//                            ByteArrayOutputStream xml = new ByteArrayOutputStream();
//                            XTMEncoder encoder = new XTMEncoder();
//                            encoder.encodeTopic(tp, null, false);
//                            encoder.writeXml(xml);
//                            RecTopic rec = tp.getDbdata();
//                            rec.setXml(xml.toString());
//                            com.infotec.appfw.util.AFUtils.getInstance().debug("UpdateTopic:\n" + xml);
//                            rec.update();
//                        } catch (Exception e)
//                        {
//                            TopicMgr.getInstance().log(e, com.infotec.appfw.util.AFUtils.getLocaleString("locale_core", "error_TopicMap_update2DB_updateTopic2DBError1") + ":" + id, true);
//                        }
//                    } else if (str.startsWith("ra:"))
//                    {
//                        try
//                        {
//                            DBTopicMap.getInstance().getAssociation(this.getId(), id).remove();
//                        } catch (Exception e)
//                        {
//                            TopicMgr.getInstance().log(e, com.infotec.appfw.util.AFUtils.getLocaleString("locale_core", "error_TopicMap_update2DB_removeAssociation2DBError") + ":" + id, true);
//                        }
//                    } else if (str.startsWith("aa:"))
//                    {
//                        try
//                        {
//                            Association ass = this.getAssociation(id);
//                            ByteArrayOutputStream xml = new ByteArrayOutputStream();
//                            XTMEncoder encoder = new XTMEncoder();
//                            encoder.encodeAssociation(ass, null);
//                            encoder.writeXml(xml);
//                            ass.getDbdata().setXml(xml.toString());
//                            ass.getDbdata().setIdtm(this.getId());
//                            ass.getDbdata().create();
//                            ass.setDBSyncronized(true);
//                        } catch (Exception e)
//                        {
//                            TopicMgr.getInstance().log(e, com.infotec.appfw.util.AFUtils.getLocaleString("locale_core", "error_TopicMap_update2DB_addAssociation2DBError") + ":" + id, true);
//                            this.getAssociations().remove(id);
//                        }
//                    } else if (str.startsWith("ao:"))
//                    {
//                        int x1 = id.indexOf(":");
//                        String strn = id.substring(0, x1);
//                        x1++;
//                        int n = Integer.parseInt(strn);
//                        String tp = id.substring(x1, n + x1);
//                        String occid = id.substring(x1 + n);
//                        Occurrence occ = getTopic(tp, true).getOccurrence(occid);
//                        if (occ != null)
//                        {
//                            if (!occ.isDBSyncronized())
//                            {
//                                try
//                                {
//                                    ByteArrayOutputStream xml = new ByteArrayOutputStream();
//                                    XTMEncoder encoder = new XTMEncoder();
//                                    encoder.encodeOccurrence(occ, null);
//                                    encoder.writeXml(xml);
//                                    occ.getDbdata().setIdtm(this.getId());
//                                    occ.getDbdata().setIdtp(tp);
//                                    occ.getDbdata().setXml(xml.toString());
//                                    occ.getDbdata().create();
//                                    occ.setDBSyncronized(true);
//                                } catch (Exception e)
//                                {
//                                    TopicMgr.getInstance().log(e, com.infotec.appfw.util.AFUtils.getLocaleString("locale_core", "error_TopicMap_update2DB_addOcurrence2DBError") + ":" + id, true);
//                                }
//                            }
//                        } else
//                        {
//                            TopicMgr.getInstance().log(com.infotec.appfw.util.AFUtils.getLocaleString("locale_core", "log_TopicMap_update2DB_addOccurrence2DBError") + "...", true);
//                            getTopic(tp, true).getOccurrences().remove(occ);
//                        }
//                    } else if (str.startsWith("uo:"))
//                    {
//                        try
//                        {
//                            RecOccurrence recocc = DBTopicMap.getInstance().getOccurrence(getId(),id);
//                            Occurrence occ=getTopic(recocc.getIdtp()).getOccurrence(id);
//                            ByteArrayOutputStream xml = new ByteArrayOutputStream();
//                            XTMEncoder encoder = new XTMEncoder();
//                            encoder.encodeOccurrence(occ, null);
//                            encoder.writeXml(xml);
//                            recocc.setXml(xml.toString());
//                            recocc.update();
//                        } catch (Exception e)
//                        {
//                            TopicMgr.getInstance().log(e, com.infotec.appfw.util.AFUtils.getLocaleString("locale_core", "log_TopicMap_update2DB_addOccurrence2DBError") + ":" + id, true);
//                        }
//                    } else if (str.startsWith("ro:"))
//                    {
//                        int x1 = id.indexOf(":");
//                        String strn = id.substring(0, x1);
//                        x1++;
//                        int n = Integer.parseInt(strn);
//                        String tp = id.substring(x1, n + x1);
//                        String occid = id.substring(x1 + n);
//                        try
//                        {
//                            DBTopicMap.getInstance().getOccurrence(this.getId(), occid).remove();
//                        } catch (Exception e)
//                        {
//                            TopicMgr.getInstance().log(e, com.infotec.appfw.util.AFUtils.getLocaleString("locale_core", "error_TopicMap_update2DB_removeOccurrence2DBError") + "...", true);
//                        }
//                    } else if (str.startsWith("um:"))
//                    {
//                        try
//                        {
//                            this.getDbdata().update();
//                        } catch (Exception e)
//                        {
//                            TopicMgr.getInstance().log(e, com.infotec.appfw.util.AFUtils.getLocaleString("locale_core", "error_TopicMap_update2DB_updateMapError") + ": " + this.getId(), true);
//                        }
//                    }
//                }
//            } catch (Exception e)
//            {
//                TopicMgr.getInstance().log(e, com.infotec.appfw.util.AFUtils.getLocaleString("locale_core", "error_TopicMap_update2DB_updateChangesMapError") + "...\n" + sendChanges.toString(), true);
//            }
//        }
//
//        notifyChanges(sendChanges.toString());
    }

    public void notifyChanges(String changes)
    {
        //System.out.println("notify:"+changes);
        Iterator itobs = observers.iterator();
        while (itobs.hasNext())
        {
            AFObserver ofs=(AFObserver) itobs.next();
            try
            {
                ofs.sendDBNotify(changes, this);
            }catch(Exception e){AFUtils.log(e,"Error notifing TopicMap Changes...");}
        }
    }

    /** Getter for property syncronized.
     * @return Value of property syncronized.
     */
    public boolean isDBSyncronized()
    {
        return syncronized;
    }

    /** Setter for property syncronized.
     * @param syncronized New value of property syncronized.
     */
    public void setDBSyncronized(boolean syncronized)
    {
        this.syncronized = syncronized;
        if (syncronized)
        {
            if (dbdata.getHome() != null)
            {
                this.m_home = this.getTopic(dbdata.getHome(), true);
            }
            if (dbdata.getLang() != null)
            {
                this.m_lang = this.getTopic(dbdata.getLang(), true);
            }
            syncAssociations();
        }
    }

    /**
     *  Agraga las associaciones a los topicos, se utiliza despues de cargar el mapa...
     */
    private void syncAssociations()
    {
        Iterator itass = this.getAssociations().values().iterator();
        while (itass.hasNext())
        {
            Association association = (Association) itass.next();
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


    /**
     * @param topicid
     * @return  */
    public boolean existTopic(String topicid)
    {
        WebPage wp = ws.getWebPage(topicid);
        if(null!=wp) return true;
        return false;
        //return m_topics.containsKey(topicid);
    }

    public String toString()
    {
        return getId();
    }
    
    public boolean isActive()
    {
        //return getDbdata().getActive()==1 && getDbdata().getDeleted()==0;
        return ws.isActive();
    }    
    
/*    
    public RecMDAttribute getMDAttribute(String key)
    {
        Iterator it=getMDAttributes();
        while(it.hasNext())
        {
            RecMDAttribute rec=(RecMDAttribute)it.next();
            if(rec.getName().equals(key))return rec;
        }
        return null;
    }
    
    public RecMDAttribute getMDAttribute(int key)
    {
        Iterator it=getMDAttributes();
        while(it.hasNext())
        {
            RecMDAttribute rec=(RecMDAttribute)it.next();
            if(rec.getId()==key)return rec;
        }
        return null;
    }
    
    /**
     *Return RecMDAttribute Iterator
     *
    public Iterator getMDAttributes()
    {
        return DBMetaData.getInstance().getMDTopicMapAttributes(this.getDbdata().getNId());
    }
    */

}
