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
package org.semanticwb.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
import javax.servlet.http.HttpServletRequest;
import org.semanticwb.Logger;
import org.semanticwb.SWBException;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBUtils;
import org.semanticwb.platform.SemanticObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;

// TODO: Auto-generated Javadoc
/**
 * The Class Resource.
 */
public class Resource extends org.semanticwb.model.base.ResourceBase {

    /** The log. */
    private static Logger log = SWBUtils.getLogger(Resource.class);
    
    /** The siteid. */
    private String siteid = null;
    
    /** The randpriority. */
    protected int randpriority;
    
    /** The hits. */
    private long hits = 0;
    
    /** The views. */
    private long views = 0;
    
    /** The timer. */
    private long timer=System.currentTimeMillis();                     //valores de sincronizacion de views, hits
    
    /** The time. */
    private static long time;               //tiempo en milisegundos por cada actualizacion
    
    /** The viewed. */
    private boolean viewed = false;
    
    /** The rand. */
    private static Random rand = new Random();

    static {
        time = 600000L;
        try {
            time = 1000L * Long.parseLong((String) SWBPlatform.getEnv("swb/accessLogTime", "600"));
        } catch (Exception e) {
            log.error("Error to read accessLogTime...", e);
        }
    }

    /**
     * Instantiates a new resource.
     * 
     * @param base the base
     */
    public Resource(SemanticObject base) {
        super(base);
        //System.out.println("Create Resource:"+base.getURI());
        //new Exception().printStackTrace();
    }

    /**
     * Gets the web site id.
     * 
     * @return the web site id
     */
    public String getWebSiteId() {
        if (siteid == null) {
            siteid = getWebSite().getId();
        }
        return siteid;
    }

    /**
     * Checks if is cached.
     * 
     * @return true, if is cached
     */
    public boolean isCached() {
        boolean ret = false;
        if (getResourceType().getResourceCache() > 0) {
            ret = true;
        }
        return ret;
    }

    /**
     * Refresh rand priority.
     */
    public void refreshRandPriority() {
        //TODO:
        //if (this.getCamp() == 1)
        //    randpriority = SWBPriorityCalculator.getInstance().calcPriority(0);
        //else if (this.getCamp() == 2)
        //    randpriority = WBUtils.getInstance().calcPriority(6);
        //else
        randpriority = calcPriority(getPriority());
    }

    /**
     * Calc priority.
     * 
     * @param p the p
     * @return the int
     */
    private int calcPriority(int p) {
        int ret = 0;
        if (p == 0) {
            ret = 0;
        } else if (p == 1) {
            ret = 1;
        } else if (p == 5) {
            ret = 50;
        } else if (p > 5) {
            ret = 60;
        } else {
            ret = rand.nextInt(10 * p) + 1;
        }
        return ret;
    }

    /**
     * Sets the rand priority.
     * 
     * @param randpriority the new rand priority
     */
    public void setRandPriority(int randpriority) {
        this.randpriority = randpriority;
    }

    /**
     * Gets the rand priority.
     * 
     * @return the rand priority
     * @return
     */
    public int getRandPriority() {
        return randpriority;
    }

    /**
     * Gets the dom.
     * 
     * @return the dom
     */
    public Document getDom() {
        return getSemanticObject().getDomProperty(swb_xml);
    }

    /**
     * Gets the dom conf.
     * 
     * @return the dom conf
     */
    public Document getDomConf() {
        return getSemanticObject().getDomProperty(swb_xmlConf);
    }

    /** Asigna un atributo al DOM del recurso.
     * Si no existe el atributo, lo crea y si existe lo modifica
     * @param name String nombre del atributo
     * @param value String valor del atributo
     */
    public void setAttribute(String name, String value) {
        try {
            Document dom = getDom();
            Element res = (Element) dom.getFirstChild();
            if (res == null) {
                Element ele = dom.createElement("resource");
                dom.appendChild(ele);
            }
            SWBUtils.XML.setAttribute(dom, name, value);
        } catch (Exception e) {
            log.error("Error in setAttribute: " + name + " ->Resource " + getId(), e);
        }
    }

    /**
     * Lee un atributo del DOM del Recurso
     * Si el atributo no esta declarado regresa el valor por defecto defvalue.
     * 
     * @param name the name
     * @param defvalue the defvalue
     * @return the attribute
     */
    public String getAttribute(String name, String defvalue) {
        String ret = getAttribute(name);
        if (ret == null) {
            ret = defvalue;
        }
        return ret;
    }

    /**
     * Lee un atributo del DOM del Recurso
     * Si el atributo no esta declarado regresa null.
     * 
     * @param name the name
     * @return the attribute
     */
    public String getAttribute(String name) {
        String ret = null;
        try {
            Document dom = getDom();
            NodeList data = dom.getElementsByTagName(name);
            if (data.getLength() > 0) {
                Node txt = data.item(0).getFirstChild();
                if (txt != null) {
                    ret = txt.getNodeValue();
                }
            }
        } catch (Exception e) {
            log.warn(e);
            //log.error("Error in getAttribute: " + name + " ->Resource " + getId(), noe);
        }
        return ret;
    }

    /**
     * Lee un atributo del DOM del Recurso
     * Si el atributo no esta declarado regresa iterador vacio.
     * 
     * @return the attribute names
     */
    public Iterator<String> getAttributeNames() {
        ArrayList attributeNames = new ArrayList();
        try {
            Document dom = getDom();
            Node root = dom.getFirstChild();

            NodeList data = root.getChildNodes();
            for (int x = 0; x < data.getLength(); x++) {
                attributeNames.add(data.item(x).getNodeName());
            }
        } catch (Exception e) {
            log.warn(e);
        }
        return attributeNames.iterator();
    }

    /**
     * Borra un atributo del DOM del Recurso.
     * 
     * @param name the name
     */
    public void removeAttribute(String name) {
        try {
            Document dom = getDom();
            Node res = dom.getFirstChild();
            NodeList data = dom.getElementsByTagName(name);
            if (data.getLength() > 0) {
                res.removeChild(data.item(0));
            }
        } catch (Exception e) {
            log.error("Error in removeAttribute: " + name + " ->Resource " + getId(), e);
        }
    }

    /**
     * Actualiza los atributos del DOM a base de datos.
     * 
     * @throws SWBException the sWB exception
     */
    public void updateAttributesToDB() throws SWBException {
        Document dom = getDom();
        if (dom != null) {
            String xml = SWBUtils.XML.domToXml(dom);
            if (xml != null && !xml.equals(getXml())) {
                setXml(xml);
            }
        }
    }

    //    @Override
    //    public void setXml(String xml)
    //    {
    //        //Garantiza que se borren las propiedades
    //        getSemanticObject().getRDFResource().removeAll(swb_xml.getRDFProperty());
    //        super.setXml(xml);
    //        m_dom=null;
    //    }
        /**
     * Adds the hit.
     *
     * @param request the request
     * @param user the user
     * @param page the page
     */
    public void addHit(HttpServletRequest request, User user, WebPage page) 
    {
        StringBuffer logbuf = new StringBuffer(300);
        logbuf.append("hit|");
        logbuf.append(request.getRemoteAddr());
        logbuf.append("|");
        logbuf.append(SWBPlatform.getMessageCenter().getAddress());
        logbuf.append("|");
        String sess=request.getSession().getId();
        if(sess!=null)
        {
            int p=sess.length()-10;
            if(p>-1)sess=sess.substring(p);
        }else sess="_";
        logbuf.append(sess);        
        logbuf.append("|");
        logbuf.append(page.getWebSite().getId());
        logbuf.append("|");
        logbuf.append(page.getId());
        logbuf.append("|");
        logbuf.append(user.getUserRepository().getId());
        logbuf.append("|");
        if (user.getLogin() != null)
            logbuf.append(user.getLogin());
        else
            logbuf.append("_");
        logbuf.append("|");
        logbuf.append(user.getSemanticObject().getSemanticClass().getClassId());
        logbuf.append("|");
        logbuf.append(user.getDevice());
        logbuf.append("|");
        if (user.getLanguage() != null && user.getLanguage().length() > 0)
            logbuf.append(user.getLanguage());
        else
            logbuf.append("_");
        logbuf.append("|");
        logbuf.append(getId());
        SWBPlatform.getMessageCenter().sendMessage(logbuf.toString());
    }

    /**
     * Gets the data.
     * 
     * @return the data
     * @return
     */
    public String getData() {
        return getProperty("data");
    }

    /**
     * Gets the data.
     * 
     * @param key the key
     * @return the data
     */
    public String getData(String key) {
        return getProperty("data/" + key);
    }

    /**
     * Sets the data.
     * 
     * @param data the new data
     */
    public void setData(String data) {
        setProperty("data", data);
    }

    /**
     * Sets the data.
     * 
     * @param key the key
     * @param data the data
     */
    public void setData(String key, String data) {
        setProperty("data/" + key, data);
    }

    /**
     * Gets the data.
     * 
     * @param usr the usr
     * @return the data
     * @return
     */
    public String getData(User usr) {
        return getProperty("data/usr/" + usr.getUserRepository().getId() + "/" + usr.getId());
    }

    /**
     * Sets the data.
     * 
     * @param usr the usr
     * @param data the data
     */
    public void setData(User usr, String data) {
        setProperty("data/usr/" + usr.getUserRepository().getId() + "/" + usr.getId(), data);
    }

    /**
     * Gets the data.
     * 
     * @param usr the usr
     * @param page the page
     * @return the data
     * @return
     */
    public String getData(User usr, WebPage page) {
        return getProperty("data/usr/" + usr.getUserRepository().getId() + "/" + usr.getId() + "/wp/" + page.getWebSiteId() + "/" + page.getId());
    }

    /**
     * Sets the data.
     * 
     * @param usr the usr
     * @param page the page
     * @param data the data
     */
    public void setData(User usr, WebPage page, String data) {
        setProperty("data/usr/" + usr.getUserRepository().getId() + "/" + usr.getId() + "/wp/" + page.getWebSiteId() + "/" + page.getId(), data);
    }

    /**
     * Gets the data.
     * 
     * @param page the page
     * @return the data
     * @return
     */
    public String getData(WebPage page) {
        return getProperty("data/wp/" + page.getWebSiteId() + "/" + page.getId());
    }

    /**
     * Sets the data.
     * 
     * @param page the page
     * @param data the data
     */
    public void setData(WebPage page, String data) {
        setProperty("data/wp/" + page.getWebSiteId() + "/" + page.getId(), data);
    }

    /**
     * Evalua el filtro de secciones
     * 
     * @param topic the topic
     * @return true, if successful
     * @return
     */
    public boolean evalFilterMap(WebPage topic) 
    {
        //System.out.println("Resource:evalFilterMap:"+this+" "+this.getResourceType()+" "+this.getTitle()+" "+topic);
        ResourceFilter pfilter = getResourceFilter();
        if(pfilter==null)return true;
        else return pfilter.evalFilterMap(topic);
    }    

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.ResourceBase#getHits()
     */
    @Override
    public long getHits() {
        if (hits == 0) {
            hits = super.getHits();
        }
        return hits;
    }

    /**
     * Inc hits.
     * 
     * @return true, if successful
     */
    public boolean incHits() {
        boolean ret = false;
        synchronized(this)
        {
            viewed = true;
            if (hits == 0) {
                hits = getHits();
            }
            hits += 1;
            long t = System.currentTimeMillis() - timer;
            if (t > time || t < -time) {
                //TODO: evalDate4Views();
                ret = true;
            }
        }
        return ret;
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.ResourceBase#setHits(long)
     */
    @Override
    public void setHits(long hits) {
        super.setHits(hits);
        this.hits = hits;
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.ResourceBase#getViews()
     */
    @Override
    public long getViews() {
        if (views == 0) {
            views = super.getViews();
        }
        return views;
    }

    /**
     * Inc views.
     * 
     * @return true, if successful
     */
    public boolean incViews() 
    {

        boolean ret = false;
        synchronized(this)
        {
            //System.out.println("incViews:"+views);
            viewed = true;
            if (views == 0) {
                views = getViews();
            }
            views += 1;
            long t = System.currentTimeMillis() - timer;
            if (t > time || t < -time) {
                //TODO: evalDate4Views();
                //System.out.println("res:"+getId()+" t:"+t+" > " +time+" "+timer+" "+System.currentTimeMillis());
                ret = true;
            }
        }
        return ret;
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.ResourceBase#setViews(long)
     */
    @Override
    public void setViews(long views) {
        //System.out.println("setViews:"+views);
        super.setViews(views);
        this.views = views;
    }

    /**
     * Update views.
     */
    public void updateViews() {
        //System.out.println("updateViews:"+views);
        if (viewed) {
            timer = System.currentTimeMillis();
            if (views > 0) {
                setViews(views);
            }
            if (hits > 0) {
                setHits(hits);
            }
            viewed = false;
            //System.out.println("************************************** Update Resource "+ getId() +"-->"+ views +" "+timer+" ************************");
            //System.out.println((char)7);
        }
    }

    @Override
    public boolean isValid() {
        boolean ret=super.isValid();
        Resourceable resourceable=getResourceable();
        if(ret && resourceable!=null && resourceable instanceof SWBClass) ret=((SWBClass)resourceable).isValid();
        if(ret)ret=getResourceType().isValid();
        if(ret && getResourceSubType()!=null)ret=getResourceSubType().isValid();
        return ret;
    }


}
