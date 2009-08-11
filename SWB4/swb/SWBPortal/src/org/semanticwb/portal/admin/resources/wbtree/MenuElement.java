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
 * MenuElement.java
 *
 * Created on 20 de diciembre de 2005, 01:43 PM
 */

package org.semanticwb.portal.admin.resources.wbtree;


import java.util.HashMap;
import java.util.Iterator;
import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.*;
import org.w3c.dom.Element;

/**
 *
 * @author Javier Solis Gonzalez
 */
public class MenuElement
{
    String webPage=null;
    String webPage_action=null;
    User user=null;
    String target="work";
    HashMap parameters=new HashMap();
    String action=null;
    WebSite tma=SWBContext.getAdminWebSite();
    boolean variantName=false;
    WebPage vitualTopic=null;
    String params=null;
    String uri=null;
    private Logger log = SWBUtils.getLogger(MenuElement.class);

    
    public MenuElement(String webPage, String WebPage_action, User user)
    {
        this.webPage=webPage;
        this.webPage_action=WebPage_action;
        this.user=user;
    }
    
    public Element addElement(Element menu)
    {
        if(!user.haveAccess(tma.getWebPage(webPage)))return null;
        Element option=null;
        if(variantName)
        {
            option=SWBTreeUtil.addNode("option",webPage,getVariantName(),menu);
        }else
        {
            option=SWBTreeUtil.addNode("option",webPage,getDisplayName(),menu);
        }
        if(webPage_action!=null)
        {
            String local_params="";
            if(this.params!=null)local_params=this.params;
            Iterator it=parameters.keySet().iterator();
            while(it.hasNext())
            {
                String key=(String)it.next();
                String value=(String)parameters.get(key);
                //params+="&"+key+"="+URLEncoder.encode(value);
                try{
                    local_params+="&"+key+"="+SWBUtils.TEXT.encode(value, "UTF-8");
                }
                catch(Exception e)
                {
                    log.error("Error al codificar el URL. MenuElement.addElement()",e);
                    local_params+="&"+key+"="+value;
                }
            }
            if(local_params.length()>0)local_params="?"+local_params.substring(1);
            if(uri!=null)local_params=uri+local_params;
            WebPage tpacc=tma.getWebPage(webPage_action);
            if(option!=null && tpacc!=null)option.setAttribute("action","showurl="+tpacc.getUrl(vitualTopic)+local_params);
        }
        if(option!=null)option.setAttribute("target", target);        
        return option;
    }
    
    public String getDisplayName()
    {
        String ret = webPage;
        WebPage wp = tma.getWebPage(webPage);
        if(null!=wp)
            ret = tma.getWebPage(webPage).getDisplayName(user.getLanguage());
        return ret;
    }
    
    public String getVariantName()
    {
        String str=getDisplayName();
        try
        {
            //str=((Variant)tma.getWebPage(WebPage).getDisplayBaseName(user.getLanguage()).getVariants().get(0)).getVariantName().getResourceData();
            WebPage wp = tma.getWebPage(webPage);
            if(null!=wp) str=wp.getDisplayName(user.getLanguage());
        }catch(Exception e){log.error(e);}
        return str;
    }    
    
    private String getConfirm(String webPage_msg, String webPage_name)
    {
        String str=webPage_msg+" "+webPage_name;
        WebPage wp = tma.getWebPage(webPage_msg);
        if(null!=wp)
        {
            str = wp.getDisplayName(user.getLanguage());
        }
        wp = tma.getWebPage(webPage_name);
        if(null!=wp)
        {
            str += " " + wp.getDisplayName(user.getLanguage());
        }
        return str + "?";
    }    

    public static Element add(Element menu, String webPage, String WebPage_action, WebPage vtp, User user, String tm, String id)
    {
        return add(menu, webPage, WebPage_action, vtp, user, tm, id, null);
    }
    
    public static Element add(Element menu, String webPage, String webPage_action, WebPage vtp, User user, String tm, String id, String params)
    {
        MenuElement ele=new MenuElement(webPage, webPage_action, user);
        ele.setAction("add");
        ele.setVitualTopic(vtp);
        ele.setParams(params);
        ele.setParameter("wbaTitle",ele.getDisplayName());
        ele.setParameter("tm",tm);
        ele.setParameter("id",id);
        ele.setParameter("tpcomm",webPage);
        Element option=ele.addElement(menu);
        if(option!=null)option.setAttribute("icon","add");
        return option;
    }
    
    public static Element edit(Element menu, String webPage, String webPage_action, WebPage vtp, User user, String tm, String id)
    {
        return edit(menu, webPage, webPage_action, vtp, user, tm, id, null);
    }
    
    
    public static Element edit(Element menu, String webPage, String webPage_action, WebPage vtp, User user, String tm, String id, String params)
    {
        MenuElement ele=new MenuElement(webPage, webPage_action, user);
        ele.setAction("edit");
        ele.setVitualTopic(vtp);
        ele.setParams(params);
        ele.setParameter("tm",tm);
        ele.setParameter("id",id);
        ele.setParameter("tpcomm",webPage);
        Element option=ele.addElement(menu);
        if(option!=null)option.setAttribute("icon","edit");
        return option;
    }  
    
    public static Element active(Element menu, String webPage, String webPage_action, WebPage vtp, User user, String tm, String id)
    {
        MenuElement ele=new MenuElement(webPage, webPage_action, user);
        ele.setAction("active");
        ele.setVitualTopic(vtp);
        ele.setParameter("tm",tm);
        ele.setParameter("id",id);
        ele.setParameter("status","true");
        ele.setTarget("status");
        Element option=ele.addElement(menu);
        if(option!=null)option.setAttribute("icon","active");
        return option;
    }  
    
    public static Element unactive(Element menu, String webPage, String webPage_action, WebPage vtp, User user, String tm, String id)
    {
        MenuElement ele=new MenuElement(webPage, webPage_action, user);
        ele.setAction("unactive");
        ele.setVitualTopic(vtp);
        ele.setParameter("tm",tm);
        ele.setParameter("id",id);
        ele.setParameter("status","true");
        ele.setTarget("status");
        ele.setVariantName(true);
        Element option=ele.addElement(menu);
        if(option!=null)option.setAttribute("icon","unactive");
        return option;
    }      
    
    public static Element remove(Element menu, String webPage, String webPage_action, WebPage vtp, User user, String webPage_confirm, String tm, String id)
    {
        return remove(menu, webPage, webPage_action, vtp, user, webPage_confirm, tm, id, null);
    }
    
    
    public static Element remove(Element menu, String webPage, String webPage_action, WebPage vtp, User user, String webPage_confirm, String tm, String id, String params)
    {
        MenuElement ele=new MenuElement(webPage, webPage_action, user);
        ele.setAction("remove");
        ele.setVitualTopic(vtp);
        ele.setParams(params);
        ele.setParameter("tm",tm);
        ele.setParameter("id",id);
        ele.setParameter("status","true");
        ele.setTarget("status");
        Element option=ele.addElement(menu);
        if(option==null)return null;
        option.setAttribute("confirm",ele.getConfirm("WBAd_glos_RemoveConfirm",webPage_confirm));
        option.setAttribute("shortCut","DELETE");
        option.setAttribute("icon","remove");
        return option;
    }      
    
    public static Element copy(Element menu, String webPage, String webPage_action, WebPage vtp, User user, String webPage_confirm, String tm, String id)
    {
        return copy(menu, webPage, webPage_action, vtp, user, webPage_confirm, tm, id, null,"status");
    }
    
    public static Element copy(Element menu, String webPage, String webPage_action, WebPage vtp, User user, String webPage_confirm, String tm, String id, String params, String target)
    {
        MenuElement ele=new MenuElement(webPage, webPage_action, user);
        ele.setAction("copy");
        ele.setVitualTopic(vtp);
        ele.setParams(params);
        ele.setParameter("tm",tm);
        ele.setParameter("id",id);
        if(target.equals("status"))
        {
            ele.setParameter("status","true");
        }else
        {
            ele.setParameter("tpcomm",webPage);
            ele.setParameter("wbaTitle",ele.getDisplayName());            
        }
        ele.setTarget(target);
        Element option=ele.addElement(menu);
        if(option==null)return null;
        option.setAttribute("confirm",ele.getConfirm("WBAd_glos_CopyConfirm",webPage_confirm));
        option.setAttribute("shortCut","COPY");
        option.setAttribute("icon","trans");
        return option;
    }       
    
    public static Element addOption(Element menu, String webPage, String WebPage_action, WebPage vtp, User user, String uri, String tm, String params)
    {
        MenuElement ele=new MenuElement(webPage, WebPage_action, user);
        ele.setVitualTopic(vtp);
        ele.setUri(uri);
        ele.setParams(params);
        ele.setParameter("tm",tm);
        //ele.setParameter("id",id);
        Element option=ele.addElement(menu);
        if(option!=null)option.setAttribute("icon","trans");
        return option;
    }
    
    public void setParameter(String key, String value)
    {
        if(key!=null && value!=null)
            parameters.put(key, value);
    }
    
    public String getParameter(String key)
    {
        return (String)parameters.get(key);
    }    
    
    /**
     * Getter for property target.
     * @return Value of property target.
     */
    public java.lang.String getTarget()
    {
        return target;
    }
    
    /**
     * Setter for property target.
     * @param target New value of property target.
     */
    public void setTarget(java.lang.String target)
    {
        this.target = target;
    }        
    
    /**
     * Setter for property target.
     * @param target New value of property target.
     */
    public void setAction(java.lang.String action)
    {
        this.action = action;
        setParameter("act",action);
    }
    
    /**
     * Getter for property target.
     * @return Value of property target.
     */
    public java.lang.String getAction()
    {
        return action;
    }
    
    /**
     * Getter for property user.
     * @return Value of property user.
     */
    public User getUser()
    {
        return user;
    }
    
    /**
     * Setter for property user.
     * @param user New value of property user.
     */
    public void setUser(User user)
    {
        this.user = user;
    }
    
    /**
     * Getter for property variantName.
     * @return Value of property variantName.
     */
    public boolean isVariantName()
    {
        return variantName;
    }
    
    /**
     * Setter for property variantName.
     * @param variantName New value of property variantName.
     */
    public void setVariantName(boolean variantName)
    {
        this.variantName = variantName;
    }
    
    /**
     * Getter for property vitualTopic.
     * @return Value of property vitualTopic.
     */
    public WebPage getVitualTopic()
    {
        return vitualTopic;
    }
    
    /**
     * Setter for property vitualTopic.
     * @param vitualTopic New value of property vitualTopic.
     */
    public void setVitualTopic(WebPage vitualTopic)
    {
        this.vitualTopic = vitualTopic;
    }
    
    /**
     * Getter for property params.
     * @return Value of property params.
     */
    public java.lang.String getParams()
    {
        return params;
    }
    
    /**
     * Setter for property params.
     * @param params New value of property params.
     */
    public void setParams(java.lang.String params)
    {
        this.params = params;
    }
    
    /**
     * Getter for property url.
     * @return Value of property url.
     */
    public java.lang.String getUri()
    {
        return uri;
    }
    
    /**
     * Setter for property url.
     * @param url New value of property url.
     */
    public void setUri(java.lang.String uri)
    {
        this.uri = uri;
    }
}
