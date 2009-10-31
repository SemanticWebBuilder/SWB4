/*
 * INFOTEC WebBuilder es una herramienta para el desarrollo de portales de conocimiento, colaboración e integración para Internet,
 * la cual, es una creación original del Fondo de Información y Documentación para la Industria INFOTEC, misma que se encuentra
 * debidamente registrada ante el Registro Público del Derecho de Autor de los Estados Unidos Mexicanos con el
 * No. 03-2002-052312015400-14, para la versión 1; No. 03-2003-012112473900 para la versión 2, y No. 03-2006-012012004000-01
 * para la versión 3, respectivamente.
 *
 * INFOTEC pone a su disposición la herramienta INFOTEC WebBuilder a través de su licenciamiento abierto al público (‘open source’),
 * en virtud del cual, usted podrá usarlo en las mismas condiciones con que INFOTEC lo ha diseñado y puesto a su disposición;
 * aprender de él; distribuirlo a terceros; acceder a su código fuente y modificarlo, y combinarlo o enlazarlo con otro software,
 * todo ello de conformidad con los términos y condiciones de la LICENCIA ABIERTA AL PÚBLICO que otorga INFOTEC para la utilización
 * de INFOTEC WebBuilder 3.2.
 *
 * INFOTEC no otorga garantía sobre INFOTEC WebBuilder, de ninguna especie y naturaleza, ni implícita ni explícita,
 * siendo usted completamente responsable de la utilización que le dé y asumiendo la totalidad de los riesgos que puedan derivar
 * de la misma.
 *
 * Si usted tiene cualquier duda o comentario sobre INFOTEC WebBuilder, INFOTEC pone a su disposición la siguiente
 * dirección electrónica:
 *
 *                                          http://www.webbuilder.org.mx
 */


/*
 * Resource.java
 *
 * Created on 24 de junio de 2002, 16:38
 */

package com.infotec.wb.core;

import javax.servlet.http.*;
import java.io.*;

import com.infotec.wb.core.*;
import com.infotec.wb.core.db.*;
import com.infotec.wb.util.*;
import com.infotec.topicmaps.*;
import com.infotec.appfw.exception.*;
import org.w3c.dom.*;

import javax.xml.parsers.*;
import java.util.*;

import com.infotec.wb.lib.*;
import com.infotec.topicmaps.db.*;
import com.infotec.topicmaps.bean.*;
import com.infotec.wb.core.db.RecResource;
import com.infotec.wb.core.db.RecResourceData;
import com.infotec.wb.core.db.DBCatalogs;
import com.infotec.wb.core.db.DBRule;
import com.infotec.appfw.util.AFUtils;

/**
 * Objeto: el objeto Resource es un objeto que se encuentra en la capa de lógica y
 * de interacción dentro del core, este objeto es el encargado de proporcionar a
 * los WBResources (recursos de usuario)la información por defecto que requiere
 * WebBuilder para administrar el recurso.
 *
 * Object: the Resource object is an object that is in the layer of logic and
 * interaction within Core, this object is the one in charge to provide to the
 * WBResources (user resources) the information by defect that requires WebBuilder
 * to administer the resource.
 * @author Javier Solis Gonzalez
 * @version 1.2
 */
public class Resource
{
    protected RecResource recResource;
    protected Document dom;
    protected Document domconf;
    protected ArrayList rules = new ArrayList();
    protected ArrayList roles = new ArrayList();
    protected int randpriority;
    protected NodeList interval = null;
    protected NodeList filterMap = null;
    protected long maxviews = -1;
    private boolean cached = false;
    private TopicMap topicmap=null;
    
    private boolean wb2Resource=false;
    
    public static final String Mode_EDIT="edit";
    public static final String Mode_VIEW="view";
    public static final String Mode_HELP="help";
    public static final String Mode_ADMIN="admin";
    public static final String Mode_ADMHLP="admhlp";
    
    public static final int UsrLevel_NONE=0;
    public static final int UsrLevel_SEE=1;
    public static final int UsrLevel_CREATE=2;
    public static final int UsrLevel_MODIFY=3;
    public static final int UsrLevel_REMOVE=4;
    public static final int UsrLevel_ADMIN=5;
    
    public static final int ResType_CONTENT=1;
    public static final int ResType_STRATEGY=2;
    public static final int ResType_SYSTEM=3;
    public static final int ResType_APPLICATION=4;
    public static final int ResType_INTERN=9;
    
    public static final String WinState_NORMAL="normal";
    public static final String WinState_MAXIMIZED="maximized";
    public static final String WinState_MINIMIZED="minimized";
    
    //Evaluacion de fechas para cumplir con apariciones
    private long time4views=0;
    private long viewsDif=0;
    private long endDate=0;
    private boolean time4ViewsWarning=false;

    /** Crea un nuevo objeto Resource
     */
    public Resource(String topicmap)
    {
        recResource = new RecResource(topicmap);
    }

    /** Crea un nuevo objeto Resource con la informacion del registro de base de datos RecResource.
     * @param recResource Objeto de referencia a un registro de la tabla wbresource de la base de datos.
     */
    public Resource(RecResource recResource)
    {
        setRecResource(recResource);
    }

    /**
     * @param recResource  */
    public void setRecResource(RecResource recResource)
    {
        this.recResource = recResource;
        
        topicmap=TopicMgr.getInstance().getTopicMap(recResource.getTopicMapId());

        //resource cache
        RecResourceType restype=DBResourceType.getInstance().getResourceType(recResource.getTypeMap(),recResource.getType());
        if (restype!=null && restype.getCache() > 0) cached = true;

        if (recResource.getXml() != null && recResource.getXml().length() > 0)
        {
            dom = com.infotec.appfw.util.AFUtils.getInstance().XmltoDom(recResource.getXml());
            if (dom == null) AFUtils.log(com.infotec.appfw.util.AFUtils.getLocaleString("locale_core", "error_Resource_setRecResource_xml") + recResource.getId(), true);
        }

        if (recResource.getXmlConf() != null && recResource.getXmlConf().length() > 0)
        {
            domconf = com.infotec.appfw.util.AFUtils.getInstance().XmltoDom(recResource.getXmlConf());
            if (domconf == null) AFUtils.log(com.infotec.appfw.util.AFUtils.getLocaleString("locale_core", "error_Resource_setRecResource_xmlconf") + recResource.getId(), true);
        }

        if (dom == null)
        {
            try
            {
                dom = com.infotec.appfw.util.AFUtils.getInstance().getNewDocument();
                Element el = dom.createElement("resource");
                dom.appendChild(el);
            } catch (Exception e)
            {
                AFUtils.log(e, com.infotec.appfw.util.AFUtils.getLocaleString("locale_core", "error_Resource_setRecResource_newDom") + recResource.getId(), true);
            }
        }

        if (domconf == null)
        {
            try
            {
                domconf = com.infotec.appfw.util.AFUtils.getInstance().getNewDocument();
                Element el = domconf.createElement("resource");
                domconf.appendChild(el);
            } catch (Exception e)
            {
                AFUtils.log(e, com.infotec.appfw.util.AFUtils.getLocaleString("locale_core", "error_Resource_setRecResource_newDomConf") + recResource.getId(), true);
            }
            rules = new ArrayList();
            roles = new ArrayList();
            interval = null;
            filterMap = null;
            maxviews = -1;
        } else
        {
            try
            {
                //**************************** cargar reglas... ****************************************+
                rules = new ArrayList();

                NodeList nl = domconf.getElementsByTagName("rule");
                //System.out.println("nodelist:"+nl);
                int n = nl.getLength();
                for (int x = 0; x < n; x++)
                {
                    Node node = nl.item(x);
                    if (node.getChildNodes().item(0) != null) rules.add(new Integer(node.getChildNodes().item(0).getNodeValue()));
                }
                
                //**************************** cargar roles... ****************************************+
                roles = new ArrayList();

                NodeList nroles = domconf.getElementsByTagName("role");
                //System.out.println("nodelist:"+nl);
                int n1 = nroles.getLength();
                for (int x = 0; x < n1; x++)
                {
                    Node node = nroles.item(x);
                    if (node.getChildNodes().item(0) != null) roles.add(new Integer(node.getChildNodes().item(0).getNodeValue()));
                }

                //**************************** cargar intervalo... ****************************************+
                interval = domconf.getElementsByTagName("interval");

                //**************************** TopicMap Filter... *****************************************
                nl = domconf.getElementsByTagName("topicmap");
                n = nl.getLength();
                if (n > 0)
                {
                    filterMap = nl;
                } else
                    filterMap = null;
                //****************************     Max Views...   *****************************************
                nl = domconf.getElementsByTagName("views");
                n = nl.getLength();
                if (n > 0)
                {
                    if (nl.item(0).hasChildNodes())
                    {
                        String aux = nl.item(0).getFirstChild().getNodeValue();
                        if (aux != null && aux.trim().length() > 0) maxviews = Long.parseLong(aux);
                    } else
                        maxviews = -1;
                } else
                    maxviews = -1;

            } catch (Exception e)
            {
                AFUtils.log(e, com.infotec.appfw.util.AFUtils.getLocaleString("locale_core", "error_Resource_setRecResource_addElement") + recResource.getTitle(), true);
            }
            
            
            //evaluar fecha de terminacion de vistas
            try
            {
                if(!WBUtils.getInstance().isClient())
                {
                    String sdate=getConfAttribute("hitsEndDate",null);
                    if(sdate!=null)
                    {
                        if(viewsDif==0 && time4views==0)
                        {
                            viewsDif=getViews();
                            time4views=System.currentTimeMillis();
                        }
                        endDate=new Date(sdate).getTime();
                    }else
                    {
                        viewsDif=0;
                        time4views=0;
                        endDate=0;
                    }
                }
            } catch (Exception e)
            {
                AFUtils.log(e, com.infotec.appfw.util.AFUtils.getLocaleString("locale_core", "error_Resource_setRecResource_addElement") + recResource.getTitle(), true);
            }            
            
        }
    }

    /** regresa id del recurso.
     * @return Value of property id.
     */
    public long getId()
    {
        return recResource.getId();
    }
    
    /** regresa id del recurso.
     * @return Value of property String topicmapid.
     */
    public String getTopicMapId()
    {
        return recResource.getTopicMapId();
    }    
    
    /** regresa id del recurso.
     * @return Value of property TopicMap.
     */
    public TopicMap getTopicMap()
    {
        return topicmap;
    }    
    

    /** Compara dos Recursos
     * @param obj
     * @return
     */
    public boolean equals(Object obj)
    {
        return ((Resource) obj).getId() == recResource.getId();
    }

    /** regresa string con el nombre de la clase del recurso.
     * @return Value of property type.
     */
    public int getType()
    {
        return recResource.getType();
    }
    
    /** regresa string con el nombre de la clase del recurso.
     * @return Value of property type.
     */
    public String getTypeMap()
    {
        String ret=recResource.getTypeMap();
        if(ret==null)ret=getTopicMapId();
        return ret;
    }
    

    /** regresa contador de versiones.
     * @return Value of property lastversion.
     */
    public int getLastversion()
    {
        return recResource.getLastversion();
    }

    /** regresa 0 si el recurso no esta activo y 1 si lo esta.
     * @return Value of property active.
     */
    public int getActive()
    {
        return recResource.getActive();
    }
    
    /** regresa 0 si el recurso no es monitoreado con sus hits y 1 si lo esta.
     * @return Value of property hitlog.
     */
    public int getHitLog()
    {
        return recResource.getHitLog();
    }

    /** regresa xmlconf del recurso.
     * @return Value of property xmlconf.
     */
    public java.lang.String getXmlConf()
    {
        return recResource.getXmlConf();
    }

    /** regresa xml del recurso.
     * @return Value of property xml.
     */
    public java.lang.String getXml()
    {
        return recResource.getXml();
    }

    /** regresa descriGetter for property description.
     * @return Value of property description.
     */
    public java.lang.String getDescription()
    {
        return recResource.getDescription();
    }

    /** regresa el numero de version al que el recurso esta apuntando.
     * @return Value of property actualversion.
     */
    public int getActualversion()
    {
        return recResource.getActualversion();
    }

    /** regresa titulo del recurso.
     * @return Value of property title.
     */
    public java.lang.String getTitle()
    {
        return recResource.getTitle();
    }

    /** regresa fecha de ultima actualizacion.
     * @return Value of property lastupdate.
     */
    public java.sql.Timestamp getLastupdate()
    {
        return recResource.getLastupdate();
    }

    /** regresa fecha de creacion.
     * @return Value of property created.
     */
    public java.sql.Timestamp getCreated()
    {
        return recResource.getCreated();
    }

    /** regresa 0 si el recurso fue deleted y 1 si esta disponible.
     * @return Value of property deleted.
     */
    public int getDeleted()
    {
        return recResource.getDeleted();
    }

    /** regresa un arreglo de las reglas asociadas con este recurso.
     * @return aray con el numero de cada regla asociada.
     */
    public Iterator getRules()
    {
        return rules.iterator();
    }
    
     /** regresa un arreglo de los roles asociadas con este recurso.
     * @return aray con el numero de cada role asociado.
     */
    public Iterator getRoles()
    {
        return roles.iterator();
    }
    
     /** 
     * @return un boolean indicando si existe el rol o no en el recurso.
     */
    public boolean haveRole(int role)
    {
        return roles.contains(new Integer(role));
    }
    
    /** 
     * @return un boolean indicando si existe la regla o no en el recurso.
     */
    public boolean haveRule(int rule)
    {
        return rules.contains(new Integer(rule));
    }
    

    /** regresa la version del recurso en objeto DOM.
     * @return Value of property domconf.
     */
    public Document getDomConf()
    {
        return domconf;
    }

    /** regresa la version del recurso en objeto DOM.
     * @return Value of property dom.
     */
    public Document getDom()
    {
        return dom;
    }

    /**
     * @return  */
    public int getSubType()
    {
        return recResource.getIdSubType();
    }
    
    /**
     * @return  */
    public String getSubTypeMap()
    {
        String ret=recResource.getIdSubTypeMap();
        if(ret==null)ret=getTopicMapId();
        return ret;
    }    

    /**
     * @return  */
    public int getCamp()
    {
        return recResource.getIdCamp();
    }

    /**
     * @return  */
    public int getPriority()
    {
        return recResource.getPriority();
    }

    /**
     * @return  */
    public String getIdAdm()
    {
        return recResource.getIdAdm();
    }

    public void refreshRandPriority()
    {
        if (this.getCamp() == 1)
            randpriority = WBUtils.getInstance().calcPriority(0);
        else if (this.getCamp() == 2)
            randpriority = WBUtils.getInstance().calcPriority(6);
        else
            randpriority = WBUtils.getInstance().calcPriority(recResource.getPriority());
    }

    /**
     * @return  */
    public int getRandPriority()
    {
        return randpriority;
    }

    /** Getter for property interval.
     * @return Value of property interval.
     */
    public org.w3c.dom.NodeList getInterval()
    {
        return interval;
    }

    /** Getter for property filterMap.
     * @return Value of property filterMap.
     */
    public org.w3c.dom.NodeList getFilterMap()
    {
        return filterMap;
    }

    /** Getter for property recResource.
     * @return Value of property recResource.
     */
    public RecResource getRecResource()
    {
        return recResource;
    }

    /**
     * @param id
     * @param user
     * @return  */
    public boolean addRule(int id, String user)
    {
        try
        {
            Node res = domconf.getFirstChild();
            Element rule = domconf.createElement("rule");
            rule.appendChild(domconf.createTextNode("" + id));
            res.appendChild(rule);
            recResource.setXmlConf(com.infotec.appfw.util.AFUtils.getInstance().DomtoXml(domconf));
            recResource.update(user, com.infotec.appfw.util.AFUtils.getLocaleString("locale_core", "admlog_Resource_addRule_msg1") + " " + id + " " + DBRule.getInstance().getRule(getTopicMapId(),id).getTitle() + " " + com.infotec.appfw.util.AFUtils.getLocaleString("locale_core", "admlog_Resource_addRule_msg2") + " " + getId() + " " + getTitle());
            //recResource.update();
            return true;
        } catch (Exception e)
        {
            AFUtils.log(e, com.infotec.appfw.util.AFUtils.getLocaleString("locale_core", "error_Resource_addRule"), true);
        }
        return false;
    }

    /**
     * @param id
     * @param user
     * @return  */
    public boolean removeRule(int id, String user)
    {
        try
        {
            Node res = domconf.getFirstChild();
            NodeList ru = domconf.getElementsByTagName("rule");
            for (int i = 0; i < ru.getLength(); i++)
            {
                if (ru.item(i).getFirstChild().getNodeValue().equals("" + id))
                {
                    res.removeChild(ru.item(i));
                }
            }
            recResource.setXmlConf(com.infotec.appfw.util.AFUtils.getInstance().DomtoXml(domconf));
            recResource.update(user, com.infotec.appfw.util.AFUtils.getLocaleString("locale_core", "admlog_Resource_removeRule_msg1") + " " + id + " " + DBRule.getInstance().getRule(getTopicMapId(),id).getTitle() + " " + com.infotec.appfw.util.AFUtils.getLocaleString("locale_core", "admlog_Resource_removeRule_msg2") + " " + getId() + " " + getTitle());
            //recResource.update();
            return true;
        } catch (Exception e)
        {
            AFUtils.log(e, com.infotec.appfw.util.AFUtils.getLocaleString("locale_core", "error_Resource_removeRule"), true);
        }
        return false;
    }

    
    /**
     * @param id
     * @param user
     * @return  */
    public boolean addRole(int id, String repository, String user)
    {
        try
        {
            Node res = domconf.getFirstChild();
            Element role = domconf.createElement("role");
            role.appendChild(domconf.createTextNode("" + id));
            res.appendChild(role);
            recResource.setXmlConf(com.infotec.appfw.util.AFUtils.getInstance().DomtoXml(domconf));
            recResource.update(user, "role was added width id:" + " " + id + " " + DBRole.getInstance().getRole(id,repository).getName() + " " + ",to resource width id:" + " " + getId() + " " + getTitle());
            //recResource.update();
            return true;
        } catch (Exception e)
        {
            AFUtils.log(e, "Error occurred when assign role to the resource width id:"+getId(), true);
        }
        return false;
    }

    /**
     * @param id
     * @param user
     * @return  */
    public boolean removeRole(int id, String repository, String user)
    {
        try
        {
            Node res = domconf.getFirstChild();
            NodeList ru = domconf.getElementsByTagName("role");
            for (int i = 0; i < ru.getLength(); i++)
            {
                if (ru.item(i).getFirstChild().getNodeValue().equals("" + id))
                {
                    res.removeChild(ru.item(i));
                }
            }
            recResource.setXmlConf(com.infotec.appfw.util.AFUtils.getInstance().DomtoXml(domconf));
            recResource.update(user, "role was unassigned width id:" + " " + id + " " + DBRole.getInstance().getRole(id, repository).getName() + " " + ",from resource width id:" + " " + getId() + " " + getTitle());
            return true;
        } catch (Exception e)
        {
            AFUtils.log(e, "Error when unassign role from resource width id:"+getId(), true);
        }
        return false;
    }
    
    
    /** Getter for property maxviews.
     * @return Value of property maxviews.
     */
    public long getMaxViews()
    {
        return maxviews;
    }
    
    /**
     * @return  */
    public long getCache()
    {
        return recResource.getCache();
    }
    

    /**
     * @return  */
    public long getViews()
    {
        return recResource.getViews();
    }

    /**
     * @return  */
    public long getHits()
    {
        return recResource.getHits();
    }

    /**
     * @param request
     * @param user
     * @param topic  */
    public void addHit(HttpServletRequest request, WBUser user, Topic topic)
    {
        StringBuffer logbuf = new StringBuffer(300);
        logbuf.append("hit|");
        logbuf.append(request.getRemoteAddr());
        logbuf.append("|");
        logbuf.append(WBMessageCenter.getInstance().getAddress());
        logbuf.append("|");
        String sess=request.getSession().getId();
        if(sess!=null)
        {
            int p=sess.length()-10;
            if(p>-1)sess=sess.substring(p);
        }else sess="_";
        logbuf.append(sess);        
        logbuf.append("|");
        logbuf.append(topic.getMap().getId());
        logbuf.append("|");
        logbuf.append(topic.getId());
        logbuf.append("|");
        logbuf.append(user.getRepository());
        logbuf.append("|");
        if (user.getLogin() != null)
            logbuf.append(user.getLogin());
        else
            logbuf.append("_");
        logbuf.append("|");
        if (user.getUserType() != null)
            logbuf.append(user.getUserType());
        else
            logbuf.append("_");
        logbuf.append("|");
        logbuf.append(user.getDevice());
        logbuf.append("|");
        if (user.getLanguage() != null && user.getLanguage().length() > 0)
            logbuf.append(user.getLanguage());
        else
            logbuf.append("_");
        logbuf.append("|");
        logbuf.append(getId());
        WBMessageCenter.getInstance().sendMessage(logbuf.toString());
    }

    /**
     * @param topic
     * @return  */
    public boolean evalFilterMap(Topic topic)
    {
        boolean ret = false;
        NodeList fi = getFilterMap();
        if (fi == null) return true;
        for (int x = 0; x < fi.getLength(); x++)
        {
            Element el = (Element) fi.item(x);
            if (topic.getMap().getId().equals(el.getAttribute("id")))
            {
                NodeList ti = el.getElementsByTagName("topic");
                for (int y = 0; y < ti.getLength(); y++)
                {
                    Element eltp = (Element) ti.item(y);
                    Topic atopic = topic.getMap().getTopic(eltp.getAttribute("id"));
                    if (atopic != null)
                    {
                        if (topic == atopic)
                            ret = true;
                        else if (eltp.getAttribute("childs").equals("true"))
                        {
                            if (topic.isChildof(atopic)) ret = true;
                        }
                    }
                }
            }
        }
        return ret;
    }

    /**
     * @param tp
     * @return  */
    public boolean setFlowOfContent2Init(Topic tp)
    {
        if(tp==null)return false;
        boolean ret = false;
        Iterator IterContents = tp.getOccurrencesOfType("REC_WBContent");
        while (IterContents.hasNext())
        {
            Occurrence content = (Occurrence) IterContents.next();
            if (Integer.parseInt(content.getResourceData()) == recResource.getId())
            {
                try
                {
                    RecOccurrence recOcurre = content.getDbdata();
                    recOcurre.setFlow(null);
                    recOcurre.setStatus(0);
                    recOcurre.update();
                    ret = true;
                } catch (AFException e)
                {
                    AFUtils.log(com.infotec.appfw.util.AFUtils.getLocaleString("locale_core", "error_Resource_setFlowofContent2Init"));
                }
            }
        }
        return ret;
    }

    /**
     * @throws AFException
     * @return  */
    public String getData() throws AFException
    {
        try
        {
            RecResourceData rec = new RecResourceData();
            rec.setResid(getId());
            rec.setResidtm(getTopicMapId());
            rec.load();
            return rec.getData();
        } catch (Exception e)
        {
            throw new AFException(com.infotec.appfw.util.AFUtils.getLocaleString("locale_core", "error_Resource_getData") + e.getMessage(), "Resource:getData");
        }
    }

    /**
     * @param data
     * @throws AFException  */
    public void setData(String data) throws AFException
    {
        try
        {
            RecResourceData rec = new RecResourceData();
            rec.setResid(getId());
            rec.setResidtm(getTopicMapId());
            if(data!=null)
            {
                rec.setData(data);
                if (rec.exist())
                    rec.update();
                else
                    rec.create();
            }else
            {
                rec.remove();
            }
        } catch (Exception e)
        {
            throw new AFException(com.infotec.appfw.util.AFUtils.getLocaleString("locale_core", "error_Resource_setData") + e.getMessage(), "Resource:getData");
        }
    }

    /**
     * @param usr
     * @throws AFException
     * @return  */
    public String getData(WBUser usr) throws AFException
    {
        try
        {
            RecResourceData rec = new RecResourceData();
            rec.setResid(getId());
            rec.setResidtm(getTopicMapId());
            rec.setUserid(usr.getId());
            rec.load();
            return rec.getData();
        } catch (Exception e)
        {
            throw new AFException(com.infotec.appfw.util.AFUtils.getLocaleString("locale_core", "error_Resource_getData") + e.getMessage(), "Resource:getData");
        }
    }

    /**
     * @param usr
     * @param data
     * @throws AFException  */
    public void setData(WBUser usr, String data) throws AFException
    {
        try
        {
            RecResourceData rec = new RecResourceData();
            rec.setResid(getId());
            rec.setResidtm(getTopicMapId());
            rec.setUserid(usr.getId());
            if(data!=null)
            {
                rec.setData(data);
                if (rec.exist())
                    rec.update();
                else
                    rec.create();
            }else
            {
                rec.remove();
            }
        } catch (Exception e)
        {
            throw new AFException(com.infotec.appfw.util.AFUtils.getLocaleString("locale_core", "error_Resource_setData") + e.getMessage(), "Resource:getData");
        }
    }

    /**
     * @param usr
     * @param topic
     * @throws AFException
     * @return  */
    public String getData(WBUser usr, Topic topic) throws AFException
    {
        try
        {
            RecResourceData rec = new RecResourceData();
            rec.setResid(getId());
            rec.setResidtm(getTopicMapId());
            rec.setTopicid(topic.getId());
            rec.setUserid(usr.getId());
            rec.load();
            return rec.getData();
        } catch (Exception e)
        {
            throw new AFException(com.infotec.appfw.util.AFUtils.getLocaleString("locale_core", "error_Resource_getData") + e.getMessage(), "Resource:getData");
        }
    }

    /**
     * @param usr
     * @param topic
     * @param data
     * @throws AFException  */
    public void setData(WBUser usr, Topic topic, String data) throws AFException
    {
        try
        {
            RecResourceData rec = new RecResourceData();
            rec.setResid(getId());
            rec.setResidtm(getTopicMapId());
            rec.setTopicid(topic.getId());
            rec.setUserid(usr.getId());
            if(data!=null)
            {
                rec.setData(data);
                if (rec.exist())
                    rec.update();
                else
                    rec.create();
            }else
            {
                rec.remove();
            }
        } catch (Exception e)
        {
            throw new AFException(com.infotec.appfw.util.AFUtils.getLocaleString("locale_core", "error_Resource_setData") + e.getMessage(), "Resource:getData");
        }
    }

    /**
     * @param topic
     * @throws AFException
     * @return  */
    public String getData(Topic topic) throws AFException
    {
        try
        {
            RecResourceData rec = new RecResourceData();
            rec.setResid(getId());
            rec.setResidtm(getTopicMapId());
            rec.setTopicid(topic.getId());
            rec.load();
            return rec.getData();
        } catch (Exception e)
        {
            throw new AFException(com.infotec.appfw.util.AFUtils.getLocaleString("locale_core", "error_Resource_getData") + e.getMessage(), "Resource:getData");
        }
    }

    /**
     * @param topic
     * @param data
     * @throws AFException  */
    public void setData(Topic topic, String data) throws AFException
    {
        try
        {
            RecResourceData rec = new RecResourceData();
            rec.setResid(getId());
            rec.setResidtm(getTopicMapId());
            rec.setTopicid(topic.getId());
            if(data!=null)
            {
                rec.setData(data);
                if (rec.exist())
                    rec.update();
                else
                    rec.create();
            }else
            {
                rec.remove();
            }
        } catch (Exception e)
        {
            throw new AFException(com.infotec.appfw.util.AFUtils.getLocaleString("locale_core", "error_Resource_setData") + e.getMessage(), "Resource:getData");
        }
    }

    /** Getter for property cached.
     * @return Value of property cached.
     */
    public boolean isCached()
    {
        return cached;
    }

    /** Regresa la ruta de trabajo relativa al directorio work del recurso.
     */
    public String getResourceWorkPath()
    {
        try
        {
            return "/sites/"+getTopicMapId()+"/resources/" + getResourceType().getName() + "/" + getId();
        } catch (Exception e)
        {
            AFUtils.log(e, com.infotec.appfw.util.AFUtils.getLocaleString("locale_core", "error_Resource_getResourceWorkPath"), true);
        }
        ;
        return "";
    }
    
    /** Regresa la ruta de trabajo relativa al directorio work del recurso.
     */    
    public static String getResourceWorkPath(RecResource rec)
    {
        try
        {
            return "/sites/"+rec.getTopicMapId()+"/resources/" + rec.getResourceType().getName() + "/" + rec.getId();
        } catch (Exception e)
        {
            AFUtils.log(e, com.infotec.appfw.util.AFUtils.getLocaleString("locale_core", "error_Resource_getResourceWorkPath"), true);
        }
        ;
        return "";        
    }

    /** Crea el directorio de trabajo del recurso.
     */
    public boolean makeResourceWorkPath()
    {
        boolean ret = false;
        //String ruta=AFUtils.getInstance().getWorkPath()+"/resources";
        String ruta = WBUtils.getInstance().getWorkPath() + getResourceWorkPath();
        try
        {
            File f = new File(ruta);
            if (!f.exists()) f.mkdirs();
            /*
            ruta+="/"+DBCatalogs.getInstance().getObject(getType()).getName();
            f=new File(ruta);
            if(!f.exists())f.mkdir();
            ruta+="/"+getId();
            f=new File(ruta);
            if(!f.exists())f.mkdir();
             */
            ret = true;
        } catch (Exception e)
        {
            AFUtils.log(e, com.infotec.appfw.util.AFUtils.getLocaleString("locale_core", "error_Resource_makeResourceWorkPath"), true);
        }
        ;
        return ret;
    }

    /**
     *
     * @return
     * @deprecated No usar...
     * Se mantiene por compatibilidad
     * usar getRenderUrl()
     */    
    public String getAdminUrl()
    {
        return WBUtils.getInstance().getWebPath() + com.infotec.appfw.util.AFUtils.getInstance().getEnv("wb/admresource") + "/" + getId() + "/_tm/"+getTopicMapId();
    }

    /**
     *
     * @param topic
     * @return
     * @deprecated No usar...
     * Se mantiene por compatibilidad
     * usar getRenderUrl()
     */    
    public String getUrl(Topic topic)
    {
        WBResourceURL url=new WBResourceURLImp(null, this,topic,WBResourceURL.UrlType_RENDER);
        url.setMode(url.Mode_VIEW);
        url.setCallMethod(url.Call_DIRECT);
        return url.toString(); 
        //return WBUtils.getInstance().getWebPath() + com.infotec.appfw.util.AFUtils.getInstance().getEnv("wb/response") + "/" + topic.getMap().getId() + "/" + topic.getId() + "/" + getId();
    }

    /** Asigna un atributo al DOM del recurso.
     * Si no existe el atributo, lo crea y si existe lo modifica
     * @param name String nombre del atributo
     * @param value String valor del atributo
     */
    public void setAttribute(String name, String value)
    {
        try
        {
            AFUtils.setDomAttribute(dom, name, value);
        } catch (Exception e)
        {
            AFUtils.log(e, com.infotec.appfw.util.AFUtils.getLocaleString("locale_core", "error_Resource_setAttribute") + " " + name + " ->Resource " + getId(), true);
        }
    }
    
    /** Asigna un atributo al DOM de configuración del recurso.
     * Si no existe el atributo, lo crea y si existe lo modifica
     * @param name String nombre del atributo
     * @param value String valor del atributo
     */
    public void setConfAttribute(String name, String value)
    {
        if(value==null)
        {
            removeConfAttribute(name);
        }else
        {        
            try
            {
                NodeList data = domconf.getElementsByTagName(name);
                if (data.getLength() > 0)
                {
                    Node txt = data.item(0).getFirstChild();
                    if (txt != null)
                    {
                        txt.setNodeValue(value);
                    } else
                    {
                        data.item(0).appendChild(domconf.createTextNode(value));
                    }
                } else
                {
                    Element res = (Element) domconf.getFirstChild();
                    Element ele = domconf.createElement(name);
                    ele.appendChild(domconf.createTextNode(value));
                    res.appendChild(ele);
                }
            } catch (Exception e)
            {
                AFUtils.log(e, com.infotec.appfw.util.AFUtils.getLocaleString("locale_core", "error_Resource_setAttribute") + " " + name + " ->Resource " + getId(), true);
            }
        }
    }

    /** Lee un atributo del DOM del Recurso
     * Si el atributo no esta declarado regresa el valor por defecto defvalue.
     */
    public String getAttribute(String name, String defvalue)
    {
        String ret = getAttribute(name);
        if (ret == null) ret = defvalue;
        return ret;
    }


    /** Lee un atributo del DOM del Recurso
     * Si el atributo no esta declarado regresa null.
     */
    public String getAttribute(String name)
    {
        String ret = null;
        try
        {
            NodeList data = dom.getElementsByTagName(name);
            if (data.getLength() > 0)
            {
                Node txt = data.item(0).getFirstChild();
                if (txt != null) ret = txt.getNodeValue();
            }
        } catch (Exception e)
        {
            AFUtils.log(e, com.infotec.appfw.util.AFUtils.getLocaleString("locale_core", "error_Resource_getAttribute") + " " + name + " ->Resource " + getId(), true);
        }
        return ret;
    }
    
    /** Lee un atributo del DOM del Recurso
     * Si el atributo no esta declarado regresa null.
     */
    public Enumeration getAttributeNames()
    {
        Vector vec=new Vector();
        try
        {
            Node root=dom.getFirstChild();
            NodeList data=root.getChildNodes();
            for(int x=0;x<data.getLength();x++)
            {
                vec.add(data.item(x).getNodeName());
            }
        } catch (Exception e)
        {
            AFUtils.log(e," ->Resource " + getId(), true);
        }
        return vec.elements();
    }
    
    
    
     /** Lee un atributo del DOM del Recurso
     * Si el atributo no esta declarado regresa el valor por defecto defvalue.
     */
    public String getConfAttribute(String name, String defvalue)
    {
        String ret = getConfAttribute(name);
        if (ret == null) ret = defvalue;
        return ret;
    }


    /** Lee un atributo del DOM del Recurso
     * Si el atributo no esta declarado regresa null.
     */
    public String getConfAttribute(String name)
    {
        String ret = null;
        try
        {
            NodeList data = domconf.getElementsByTagName(name);
            if (data.getLength() > 0)
            {
                Node txt = data.item(0).getFirstChild();
                if (txt != null) ret = txt.getNodeValue();
            }
        } catch (Exception e)
        {
            AFUtils.log(e, com.infotec.appfw.util.AFUtils.getLocaleString("locale_core", "error_Resource_getAttribute") + " " + name + " ->Resource " + getId(), true);
        }
        return ret;
    }
    
    
    
    

    /** Borra un atributo del DOM del Recurso
     */
    public void removeAttribute(String name)
    {
        try
        {
            Node res = dom.getFirstChild();
            NodeList data = dom.getElementsByTagName(name);
            if (data.getLength() > 0)
            {
                res.removeChild(data.item(0));
            }
        } catch (Exception e)
        {
            AFUtils.log(e, com.infotec.appfw.util.AFUtils.getLocaleString("locale_core", "error_Resource_removeAttribute") + " " + name + " ->Resource " + getId(), true);
        }
    }
    
    
      /** Borra un atributo del DOM del Recurso
     */
    public void removeConfAttribute(String name)
    {
        try
        {
            Node res = domconf.getFirstChild();
            NodeList data = domconf.getElementsByTagName(name);
            if (data.getLength() > 0)
            {
                res.removeChild(data.item(0));
            }
        } catch (Exception e)
        {
            AFUtils.log(e, "ConfAttribute was removed :" + " " + name + " ->Resource " + getId(), true);
        }
    }

    /** Actualiza los atributos del DOM a base de datos. */
    public void updateAttributesToDB() throws AFException
    {
        String xml = com.infotec.appfw.util.AFUtils.getInstance().DomtoXml(dom);
        if (xml != null && !xml.equals(recResource.getXml()))
        {
            recResource.setXml(xml);
            recResource.update();
        }
    }
    
     /** Actualiza los atributos del DOM a base de datos. */
    public void updateConfAttributesToDB() throws AFException
    {
        String confxml = com.infotec.appfw.util.AFUtils.getInstance().DomtoXml(domconf);
        if (confxml != null && !confxml.equals(recResource.getXmlConf()))
        {
            recResource.setXmlConf(confxml);
            recResource.update();
        }
    }

    /** Actualiza los atributos del DOM a base de datos. */
    public void updateAttributesToDB(String userid, String comment) throws AFException
    {
        String xml = com.infotec.appfw.util.AFUtils.getInstance().DomtoXml(dom);
        if (xml != null && !xml.equals(recResource.getXml()))
        {
            recResource.setXml(xml);
            recResource.update(userid, comment);
        }
    }
    
    /** Actualiza los atributos del DOM a base de datos. */
    public void updateConfAttributesToDB(String userid, String comment) throws AFException
    {
        String xml = com.infotec.appfw.util.AFUtils.getInstance().DomtoXml(domconf);
        if (xml != null && !xml.equals(recResource.getXmlConf()))
        {
            recResource.setXmlConf(xml);
            recResource.update(userid, comment);
        }
    }    
    
    /**
     *
     * @return
     */    
    public RecResourceType getResourceType()
    {
        return getRecResource().getResourceType();
    }
    
    /** Verifica si el usuario tiene permisos de acceso al recurso.
     * @return boolean
     */
    public boolean haveAccess(WBUser user)
    {
        return checkRoles(user) && checkRules(user);
    }    
    
    /**
     *
     * @param user
     * @return
     */    
    public boolean checkRules(WBUser user)
    {
        boolean passrule = true;
        boolean criterial_or=true;
        
        String cnf_rule=this.getConfAttribute("CNF_WBRule");
        if(cnf_rule!=null && cnf_rule.equals(Topic.CONFIG_DATA_AND_CRITERIAL))criterial_or=false;
        
        //System.out.println("haveAccess:"+getId()+" crit:"+criterial_or);
        Iterator ru = this.getRules();
        while (ru.hasNext())
        {
            int nrule = ((Integer) ru.next()).intValue();
            //System.out.println("rule:"+nrule);
            if (RuleMgr.getInstance().eval(user, nrule, this.getTopicMapId()))
            {
                passrule = true;
                if(criterial_or)break;
            } else
            {
                passrule = false;
                if(!criterial_or)break;
            }
        }        
        //System.out.println("haveAccess ret:"+passrule);
        return passrule;
    }
    
    /**
     *
     * @param user
     * @return
     */    
    public boolean checkRoles(WBUser user)
    {
        boolean pass = true;
        boolean criterial_or=true;
        
        String cnf_rule=this.getConfAttribute("CNF_WBRole");
        if(cnf_rule!=null && cnf_rule.equals(Topic.CONFIG_DATA_AND_CRITERIAL))criterial_or=false;
        
        //System.out.println("haveAccess:"+getId()+" crit:"+criterial_or);
        Iterator ru = this.getRoles();
        while (ru.hasNext())
        {
            int nrole = ((Integer) ru.next()).intValue();
            //System.out.println("role:"+nrole);
            if (user.haveRole(nrole))
            {
                pass = true;
                if(criterial_or)break;
            } else
            {
                pass = false;
                if(!criterial_or)break;
            }
        }        
        //System.out.println("haveAccess ret:"+pass);
        return pass;
    }    
    
    /**
     * Getter for property wb2Resource.
     * @return Value of property wb2Resource.
     */
    public boolean isWb2Resource()
    {
        return wb2Resource;
    }
    
    /**
     * Setter for property wb2Resource.
     * @param wb2Resource New value of property wb2Resource.
     */
    void setWb2Resource(boolean wb2Resource)
    {
        this.wb2Resource = wb2Resource;
    }
    
    public void evalDate4Views()
    {
        if(endDate>0)
        {
            long actTime=System.currentTimeMillis();
            long range=4*60*60*1000;
            long actTimeDif=actTime-time4views;
            if(time4views>0 && actTimeDif>range)
            {
                long dif=getViews()-viewsDif;
                long timeDif=endDate-actTime;

                long cal=timeDif*dif/actTimeDif+getViews();
                if((cal+dif)>maxviews)
                {
                    if(getPriority()>1)
                    {
                        getRecResource().setPriority(getPriority()-1);
                        try
                        {
                            getRecResource().update();
                        }catch(AFException e){AFUtils.log(e);}
                    }
                    time4ViewsWarning=false;
                }else if((cal-dif)<maxviews)
                {
                    if(getPriority()<5)
                    {
                        getRecResource().setPriority(getPriority()+1);
                        try
                        {
                            getRecResource().update();
                        }catch(AFException e){AFUtils.log(e);}
                        time4ViewsWarning=false;
                    }else
                    {
                        time4ViewsWarning=true;
                    }
                }
                    
                viewsDif=getViews();
                time4views=System.currentTimeMillis();    
            }
        }
    }
    
    public boolean hasWarningOfViews()
    {
        return time4ViewsWarning;
    }
    
}
