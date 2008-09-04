
/*
 * SWBResRequest.java
 *
 * Created on 1 de junio de 2004, 01:39 PM
 */

package org.semanticwb.portal.api;

import java.util.*;
import javax.servlet.http.HttpServletRequest;
import org.semanticwb.Logger;
import org.semanticwb.SWBException;
import org.semanticwb.SWBPortal;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.Portlet;
import org.semanticwb.model.User;
import org.semanticwb.model.WebPage;

/**
 * Clase que implementa WBParamRequest.
 * Ccontiene los objetos que son pasados al recurso como parametros.
 * Ejemplo: WebPage, User..
 * @author Javier Solis Gonzalez
 */
public class SWBParamRequestImp implements SWBParamRequest
{
    private static Logger log=SWBUtils.getLogger(SWBParamRequestImp.class);
    private Map args=new HashMap();
    private WebPage topic=null;
    private WebPage adminTopic=null;
    private User user=null;
    private int userLevel=0;
    private Portlet resource=null;
    private Portlet virtResource=null;
    private String action=Action_EDIT;
    private int callMethod=0;
    private String mode=Mode_VIEW;
    private String winState=WinState_NORMAL;
    private boolean secure=false;
    private HttpServletRequest request=null;
    private Locale locale=null;
    private String bundle=null;
    private ClassLoader loader=null;
    private String windowTitle=null;
    private String templateHead=null;
    
    private boolean haveVirtTP=false;
    private boolean onlyContent=false;
    private String extParams=null;
    
    /** Creates a new instance of WBResRequest */
    public SWBParamRequestImp(HttpServletRequest request, Portlet resource, WebPage topic, User user)
    {
        this.request=request;
        this.resource=resource;
        this.virtResource=resource;
        this.topic=topic;
        this.adminTopic=topic;
        this.user=user;
        //this.userLevel=userLevel;
        this.locale=new Locale(user.getLanguage().getId());
        try
        {
            this.bundle=resource.getPortletType().getPortletBundle();
        }catch(Exception e){log.error(e);}
        try
        {
            this.loader=(ClassLoader)SWBPortal.getResourceMgr().getResourceLoaders().get(resource.getPortletType().getPortletClassName());
        }catch(Exception e){log.error(e);}
    }
    
    public String getAction()
    {
        return action;
    }
    
    public void setAction(String action)
    {
        this.action=action;
    }
    
    
    public String getArgument(String key)
    {
        return (String)args.get(key);
    }
    
    public String getArgument(String key, String defValue)
    {
        String str=(String)args.get(key);        
        if(str==null)str=defValue;
        return str;
    }

    public java.util.Map getArguments()
    {
        return args;
    }
    
    public void setArguments(Map args)
    {
        this.args=args;
    }
    
    public int getCallMethod()
    {
        return callMethod;
    }
    
    public void setCallMethod(int callMethod)
    {
        this.callMethod=callMethod;
    }
    
    public String getMode()
    {
        return mode;
    }

    public void setMode(String mode)
    {
        this.mode=mode;
    }
    
    public WebPage getTopic()
    {
        return topic;
    }

    public void setTopic(WebPage topic)
    {
        this.topic=topic;
        if(adminTopic==null)adminTopic=topic;
    }
    
    public User getUser()
    {
        return user;
    }

    public void setUser(User user)
    {
        if(user!=null)locale=new Locale(user.getLanguage().getId());
        this.user=user;
    }
    
    public int getUserLevel()
    {
        return userLevel;
    }

    public void setUserLevel(int userLevel)
    {
        this.userLevel=userLevel;
    }
    
    public String getWindowState()
    {
        return winState;
    }
    
    public void setWindowState(String winState)
    {
        this.winState=winState;
    }
    
    public Portlet getResourceBase()
    {
        return resource;
    }
    
    public void setResourceBase(Portlet resource)
    {
        try
        {
            if(resource!=null)
            {
                this.bundle=resource.getPortletType().getPortletBundle();
                this.loader=(ClassLoader)SWBPortal.getResourceMgr().getResourceLoaders().get(resource.getPortletType().getPortletClassName());
            }
        }catch(Exception e){log.error(e);}
        this.resource=resource;
        this.virtResource=resource;
    }
    
    public SWBResourceURL getActionUrl()
    {
        SWBResourceURLImp url=new SWBResourceURLImp(request,resource,topic,UrlType_ACTION);
        if(resource!=virtResource)url.setVirtualResource(virtResource);
        if(haveVirtTP)url.setAdminTopic(adminTopic);
        url.setOnlyContent(onlyContent);
        //url.setAction(action);
        url.setCallMethod(callMethod);
        url.setMode(mode);
        url.setSecure(secure);
        url.setWindowState(winState);
        url.setExtURIParams(extParams);
        return url;
    }

    public SWBResourceURL getRenderUrl()
    {
        SWBResourceURLImp url=new SWBResourceURLImp(request,resource,topic,UrlType_RENDER);
        if(resource!=virtResource)url.setVirtualResource(virtResource);
        if(haveVirtTP)url.setAdminTopic(adminTopic);
        url.setOnlyContent(onlyContent);
        //url.setAction(action);
        url.setCallMethod(callMethod);
        url.setMode(mode);
        url.setSecure(secure);
        url.setWindowState(winState);
        url.setExtURIParams(extParams);
        //System.out.println("getRenderUrl->onlyContent:"+onlyContent);
        return url;
    }
    
    public boolean isSecure()
    {
        return secure;
    }    
    
    public void setSecure(boolean secure)
    {
        this.secure=secure;
    }
    
    /** Getter for property request.
     * @return Value of property request.
     *
     */
    public javax.servlet.http.HttpServletRequest getRequest()
    {
        return request;
    }
    
    /** Setter for property request.
     * @param request New value of property request.
     *
     */
    public void setRequest(javax.servlet.http.HttpServletRequest request)
    {
        this.request = request;
    }
    
    public String getLocaleString(String key) throws SWBException
    {
        return SWBUtils.TEXT.getLocaleString(bundle,key,locale,loader);
    }
    
    public String getLocaleLogString(String key) throws SWBException
    {
        //System.out.println("bundle:"+bundle+" key:"+key+" loc:"+AFUtils.getLocale());
        return SWBUtils.TEXT.getLocaleString(bundle,key,SWBUtils.TEXT.getLocale());
    }
    
    /** Getter for property adminTopic.
     * @return Value of property adminTopic.
     *
     */
    public WebPage getAdminTopic()
    {
        return adminTopic;
    }
    
    /** Setter for property adminTopic.
     * @param adminTopic New value of property adminTopic.
     *
     */
    public void setAdminTopic(WebPage adminTopic)
    {
        this.adminTopic = adminTopic;
        haveVirtTP=true;
    }
    
    public void setVirtualTopic(WebPage virt)
    {
        adminTopic = topic;
        topic=virt;
        haveVirtTP=true;
    }
    
    public boolean haveVirtualTopic()
    {
        return haveVirtTP;
    }
    
    /**
     * Getter for property virtResource.
     * @return Value of property virtResource.
     */
    public Portlet getVirtualResource()
    {
        return virtResource;
    }
    
    /**
     * Setter for property virtResource.
     * @param virtResource New value of property virtResource.
     */
    public void setVirtualResource(Portlet virtResource)
    {
        this.virtResource = virtResource;
    }
    
    /**
     * Getter for property onlyContent.
     * @return Value of property onlyContent.
     */
    public boolean isOnlyContent()
    {
        return onlyContent;
    }
    
    /**
     * Setter for property onlyContent.
     * @param onlyContent New value of property onlyContent.
     */
    public void setOnlyContent(boolean onlyContent)
    {
        this.onlyContent = onlyContent;
    }    
    
    /**
     * Getter for property windowTitle.
     * @return Value of property windowTitle.
     */
    public java.lang.String getWindowTitle()
    {
        if(windowTitle!=null)
            return windowTitle;
        return getResourceBase().getTitle();
    }
    
    /**
     * Setter for property windowTitle.
     * @param windowTitle New value of property windowTitle.
     */
    public void setWindowTitle(java.lang.String windowTitle)
    {
        this.windowTitle = windowTitle;
    }
    
    /**
     * Getter for property otherParams.
     * @return Value of property otherParams.
     */
    public java.lang.String getExtURIParams()
    {
        return extParams;
    }
    
    /**
     * Parametros adicionales o bien parametros de otros recursos
     * @param otherParams New value of property otherParams.
     */
    public void setExtURIParams(java.lang.String extParams)
    {
        this.extParams = extParams;
    }

    /**
     * Getter for property templateHead.
     * @return Value of property templateHead.
     */    
    public String getTemplateHead()
    {
        return templateHead;
    }

    /**
     * Pasarle contenido al header del Template (style, javascript, etc)
     * @param templateHead New value of property templateHead.
     */    
    public void setTemplateHead(String templateHead)
    {
        this.templateHead = templateHead;
    }
    
}
