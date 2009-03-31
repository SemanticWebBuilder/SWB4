package org.semanticwb.model;

import java.util.ArrayList;
import java.util.Iterator;
import javax.servlet.http.HttpServletRequest;
import org.semanticwb.Logger;
import org.semanticwb.SWBException;
import org.semanticwb.SWBUtils;
import org.semanticwb.platform.SemanticObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;


public class Resource extends org.semanticwb.model.base.ResourceBase 
{
    private static Logger log = SWBUtils.getLogger(Resource.class);

    private String siteid=null;
    protected int randpriority;

    private Document m_dom=null;
    private Document m_filter=null;
    private NodeList m_filternode=null;

    public Resource(SemanticObject base)
    {
        super(base);
    }

    public String getWebSiteId()
    {
        if(siteid==null)
        {
            siteid=getWebSite().getId();
        }
        return siteid;
    }

    public boolean isCached()
    {
        boolean ret=false;
        if(getResourceType().getResourceCache()>0)
        {
            ret=true;
        }
        return ret;
    }

    public void refreshRandPriority()
    {
        //TODO:
//        if (this.getCamp() == 1)
//            randpriority = SWBPriorityCalculator.getInstance().calcPriority(0);
//        else if (this.getCamp() == 2)
//            randpriority = WBUtils.getInstance().calcPriority(6);
//        else
//            randpriority = WBUtils.getInstance().calcPriority(recResource.getPriority());
    }

    public void setRandPriority(int randpriority)
    {
        this.randpriority=randpriority;
    }

    /**
     * @return  */
    public int getRandPriority()
    {
        return randpriority;
    }

    public Document getDom() throws SWBException
    {
       if(m_dom==null)
       {
           m_dom=getSemanticObject().getDomProperty(swb_xml);
       }
       return m_dom;
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
            Document dom=getDom();
            Element res = (Element) dom.getFirstChild();
            if(res==null){
                Element ele = dom.createElement("resource");
                dom.appendChild(ele);
            }
            SWBUtils.XML.setAttribute(dom, name, value);
        } catch (Exception e)
        {
            log.error("Error in setAttribute: " + name + " ->Resource " + getId(),e);
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
            Document dom=getDom();
            NodeList data = dom.getElementsByTagName(name);
            if (data.getLength() > 0)
            {
                Node txt = data.item(0).getFirstChild();
                if (txt != null) ret = txt.getNodeValue();
            }
        } catch (Exception e)
        {
            //log.error("Error in getAttribute: " + name + " ->Resource " + getId(), e);
        }
        return ret;
    }

    /** Lee un atributo del DOM del Recurso
     * Si el atributo no esta declarado regresa null.
     */
    public Iterator<String> getAttributeNames()
    {
        ArrayList vec=new ArrayList();
        try
        {
            Document dom=getDom();
            Node root=dom.getFirstChild();
            NodeList data=root.getChildNodes();
            for(int x=0;x<data.getLength();x++)
            {
                vec.add(data.item(x).getNodeName());
            }
        } catch (Exception e)
        {
            log.error(" ->Resource " + getId(), e);
        }
        return vec.iterator();
    }


    /** Borra un atributo del DOM del Recurso
     */
    public void removeAttribute(String name)
    {
        try
        {
            Document dom=getDom();
            Node res = dom.getFirstChild();
            NodeList data = dom.getElementsByTagName(name);
            if (data.getLength() > 0)
            {
                res.removeChild(data.item(0));
            }
        } catch (Exception e)
        {
            log.error("Error in removeAttribute: " + name + " ->Resource " + getId(), e);
        }
    }

    /** Actualiza los atributos del DOM a base de datos. */
    public void updateAttributesToDB() throws SWBException
    {
        Document dom=getDom();
        if(dom!=null)
        {
            String xml = SWBUtils.XML.domToXml(dom);
            if (xml != null && !xml.equals(getXml()))
            {
                setXml(xml);
            }
        }
    }

    @Override
    public void setXml(String xml)
    {
        super.setXml(xml);
        m_dom=null;
    }

    public void addHit(HttpServletRequest request, User user, WebPage page)
    {
        //TODO:
    }

    /**
     * @throws AFException
     * @return  */
    public String getData()
    {
        return getProperty("data");
    }

    /**
     * @param data
     * @throws AFException  */
    public void setData(String data)
    {
        setProperty("data", data);
    }

    /**
     * @param usr
     * @throws AFException
     * @return  */
    public String getData(User usr)
    {
        return getProperty("data/usr/"+usr.getUserRepository().getId()+"/"+usr.getId());
    }

    /**
     * @param usr
     * @param data
     * @throws AFException  */
    public void setData(User usr, String data)
    {
        setProperty("data/usr/"+usr.getUserRepository().getId()+"/"+usr.getId(),data);
    }

    /**
     * @param usr
     * @param topic
     * @throws AFException
     * @return  */
    public String getData(User usr, WebPage page)
    {
        return getProperty("data/usr/"+usr.getUserRepository().getId()+"/"+usr.getId()+"/wp/"+page.getWebSiteId()+"/"+page.getId());
    }

    /**
     * @param usr
     * @param topic
     * @param data
     * @throws AFException  */
    public void setData(User usr, WebPage page, String data)
    {
        setProperty("data/usr/"+usr.getUserRepository().getId()+"/"+usr.getId()+"/wp/"+page.getWebSiteId()+"/"+page.getId(),data);
    }

    /**
     * @param topic
     * @throws AFException
     * @return  */
    public String getData(WebPage page)
    {
        return getProperty("data/wp/"+page.getWebSiteId()+"/"+page.getId());
    }

    /**
     * @param topic
     * @param data
     * @throws AFException  */
    public void setData(WebPage page, String data)
    {
        setProperty("data/wp/"+page.getWebSiteId()+"/"+page.getId(),data);
    }

    /** Getter for property filterMap.
     * @return Value of property filterMap.
     */
    public org.w3c.dom.NodeList getFilterNode()
    {
        ResourceFilter pfilter=getResourceFilter();
        if(pfilter!=null)
        {
            Document aux=pfilter.getSemanticObject().getDomProperty(swb_xml);
            if(aux!=m_filter)
            {
                m_filter=aux;
                NodeList nl = aux.getElementsByTagName("topicmap");
                int n = nl.getLength();
                if (n > 0)
                {
                    m_filternode = nl;
                } else
                {
                    m_filternode = null;
                }
            }
        }else
        {
            m_filternode=null;
        }
        //System.out.println("getFilterNode:"+getURI()+" "+m_filternode);
        return m_filternode;
    }


    /**  org.semanticwb.model.Inheritable
     * @param topic
     * @return  */
    public boolean evalFilterMap(WebPage topic)
    {
        boolean ret = false;
        NodeList fi = getFilterNode();
        if (fi == null) return true;
        for (int x = 0; x < fi.getLength(); x++)
        {
            Element el = (Element) fi.item(x);
            //System.out.println("evalFilterMap:"+topic.getWebSiteId()+"="+el.getAttribute("id"));
            if (topic.getWebSiteId().equals(el.getAttribute("id")))
            {
                NodeList ti = el.getElementsByTagName("topic");
                for (int y = 0; y < ti.getLength(); y++)
                {
                    Element eltp = (Element) ti.item(y);
                    WebPage atopic = topic.getWebSite().getWebPage(eltp.getAttribute("id"));
                    if (atopic != null)
                    {
                        if (topic.equals(atopic))
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

    public void incHits()
    {
        setHits(getHits()+1);
    }

    public void incViews()
    {
        setViews(getViews()+1);
    }

}
