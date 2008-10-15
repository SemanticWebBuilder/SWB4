package org.semanticwb.model;

import java.util.ArrayList;
import java.util.Iterator;
import javax.servlet.http.HttpServletRequest;
import org.semanticwb.Logger;
import org.semanticwb.SWBException;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.base.*;
import org.semanticwb.platform.SemanticObject;
import org.w3c.dom.*;

public class Portlet extends PortletBase 
{
    private static Logger log = SWBUtils.getLogger(Portlet.class);
        
    private String siteid=null;
    protected int randpriority;    
    
    private Document dom=null;
    
    public Portlet(SemanticObject base)
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
        if(getPortletType().getPortletCache()>0)
        {
            ret=true;
        }
        return ret;
    }
    
//    public void refreshRandPriority()
//    {
//        if (this.getCamp() == 1)
//            randpriority = SWBPriorityCalculator.getInstance().calcPriority(0);
//        else if (this.getCamp() == 2)
//            randpriority = WBUtils.getInstance().calcPriority(6);
//        else
//            randpriority = WBUtils.getInstance().calcPriority(recResource.getPriority());
//    }   
    
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
    
    
    /** Asigna un atributo al DOM del recurso.
     * Si no existe el atributo, lo crea y si existe lo modifica
     * @param name String nombre del atributo
     * @param value String valor del atributo
     */
    public void setAttribute(String name, String value)
    {
        try
        {
            if(dom==null)dom=SWBUtils.XML.xmlToDom(getXml());
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
            if(dom==null)dom=SWBUtils.XML.xmlToDom(getXml());
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
            if(dom==null)dom=SWBUtils.XML.xmlToDom(getXml());
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
            if(dom==null)dom=SWBUtils.XML.xmlToDom(getXml());
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
        if(dom!=null)
        {
            String xml = SWBUtils.XML.domToXml(dom);
            if (xml != null && !xml.equals(getXml()))
            {
                setXml(xml);
            }
        }
    }
    
    /** Actualiza los atributos del DOM a base de datos. */
    public void updateAttributesToDB(String userid, String comment) throws SWBException
    {
        String xml = SWBUtils.XML.domToXml(dom);
        if (xml != null && !xml.equals(getXml()))
        {
            setXml(xml);
            //TODO:
            //recResource.update(userid, comment);
        }
    }

    @Override
    public void setXml(String xml) 
    {
        dom=null;
        super.setXml(xml);
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
}
