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
package org.semanticwb.portal.api;

import java.util.*;
import javax.servlet.http.HttpServletRequest;
import org.semanticwb.Logger;
import org.semanticwb.SWBPortal;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.Resource;
import org.semanticwb.model.User;
import org.semanticwb.model.WebPage;
import org.semanticwb.portal.lib.SWBRequest;

// TODO: Auto-generated Javadoc
/**
 * Clase que implementa WBParamRequest.
 * Ccontiene los objetos que son pasados al recurso como parametros.
 * Ejemplo: WebPage, User..
 * @author Javier Solis Gonzalez
 */
public class SWBParamRequestImp implements SWBParamRequest
{
    
    /** The log. */
    private static Logger log=SWBUtils.getLogger(SWBParamRequestImp.class);
    
    /** The args. */
    private Map args=new HashMap();
    
    /** The topic. */
    private WebPage topic=null;
    
    /** The admin topic. */
    private WebPage adminTopic=null;
    
    /** The user. */
    private User user=null;
    
    /** The user level. */
    private int userLevel=0;
    
    /** The resource. */
    private Resource resource=null;
    
    /** The virt resource. */
    private Resource virtResource=null;
    
    /** The action. */
    private String action=Action_EDIT;
    
    /** The call method. */
    private int callMethod=0;
    
    /** The mode. */
    private String mode=Mode_VIEW;
    
    /** The win state. */
    private String winState=WinState_NORMAL;
    
    /** The secure. */
    private boolean secure=false;
    
    /** The request. */
    private HttpServletRequest request=null;
    
    /** The locale. */
    private Locale locale=null;
    
    /** The bundle. */
    private String bundle=null;
    
    /** The loader. */
    private ClassLoader loader=null;
    
    /** The window title. */
    private String windowTitle=null;
    
    /** The template head. */
    private String templateHead=null;
    
    /** The have virt tp. */
    private boolean haveVirtTP=false;
    
    /** The only content. */
    private boolean onlyContent=false;
    
    /** The ext params. */
    private String extParams=null;
    
    /**
     * Creates a new instance of WBResRequest.
     * 
     * @param request the request
     * @param resource the resource
     * @param topic the topic
     * @param user the user
     */
    public SWBParamRequestImp(HttpServletRequest request, Resource resource, WebPage topic, User user)
    {
        this.request=request;
        this.resource=resource;
        this.virtResource=resource;
        this.topic=topic;
        this.adminTopic=topic;
        this.user=user;
        //this.userLevel=userLevel;
        this.locale=new Locale(user.getLanguage());
        try
        {
            this.bundle=resource.getResourceType().getResourceBundle();
        }catch(Exception e){log.error(e);}
        try
        {
            this.loader=(ClassLoader)SWBPortal.getResourceMgr().getResourceLoaders().get(resource.getResourceType().getResourceClassName());
        }catch(Exception e){log.error(e);}
    }
    
    /* (non-Javadoc)
     * @see org.semanticwb.portal.api.SWBParameters#getAction()
     */
    public String getAction()
    {
        return action;
    }
    
    /**
     * Sets the action.
     * 
     * @param action the new action
     */
    public void setAction(String action)
    {
        this.action=action;
    }
    
    
    /* (non-Javadoc)
     * @see org.semanticwb.portal.api.SWBParamRequest#getArgument(java.lang.String)
     */
    public String getArgument(String key)
    {
        return (String)args.get(key);
    }
    
    /* (non-Javadoc)
     * @see org.semanticwb.portal.api.SWBParamRequest#getArgument(java.lang.String, java.lang.String)
     */
    public String getArgument(String key, String defValue)
    {
        String str=(String)args.get(key);        
        if(str==null)str=defValue;
        return str;
    }

    /* (non-Javadoc)
     * @see org.semanticwb.portal.api.SWBParamRequest#getArguments()
     */
    public java.util.Map getArguments()
    {
        return args;
    }
    
    /**
     * Sets the arguments.
     * 
     * @param args the new arguments
     */
    public void setArguments(Map args)
    {
        this.args=args;
    }
    
    /* (non-Javadoc)
     * @see org.semanticwb.portal.api.SWBParameters#getCallMethod()
     */
    public int getCallMethod()
    {
        return callMethod;
    }
    
    /**
     * Sets the call method.
     * 
     * @param callMethod the new call method
     */
    public void setCallMethod(int callMethod)
    {
        this.callMethod=callMethod;
    }
    
    /* (non-Javadoc)
     * @see org.semanticwb.portal.api.SWBParameters#getMode()
     */
    public String getMode()
    {
        return mode;
    }

    /**
     * Sets the mode.
     * 
     * @param mode the new mode
     */
    public void setMode(String mode)
    {
        this.mode=mode;
    }
    
    /* (non-Javadoc)
     * @see org.semanticwb.portal.api.SWBParameters#getWebPage()
     */
    public WebPage getWebPage()
    {
        return topic;
    }

    /**
     * Sets the topic.
     * 
     * @param topic the new topic
     */
    public void setTopic(WebPage topic)
    {
        this.topic=topic;
        if(adminTopic==null)adminTopic=topic;
    }
    
    /* (non-Javadoc)
     * @see org.semanticwb.portal.api.SWBParameters#getUser()
     */
    public User getUser()
    {
        return user;
    }

    /**
     * Sets the user.
     * 
     * @param user the new user
     */
    public void setUser(User user)
    {
        if(user!=null)locale=new Locale(user.getLanguage());
        this.user=user;
    }
    
    /* (non-Javadoc)
     * @see org.semanticwb.portal.api.SWBParameters#getUserLevel()
     */
    public int getUserLevel()
    {
        return userLevel;
    }

    /**
     * Sets the user level.
     * 
     * @param userLevel the new user level
     */
    public void setUserLevel(int userLevel)
    {
        this.userLevel=userLevel;
    }
    
    /* (non-Javadoc)
     * @see org.semanticwb.portal.api.SWBParameters#getWindowState()
     */
    public String getWindowState()
    {
        return winState;
    }
    
    /**
     * Sets the window state.
     * 
     * @param winState the new window state
     */
    public void setWindowState(String winState)
    {
        this.winState=winState;
    }
    
    /* (non-Javadoc)
     * @see org.semanticwb.portal.api.SWBParameters#getResourceBase()
     */
    public Resource getResourceBase()
    {
        return resource;
    }
    
    /**
     * Sets the resource base.
     * 
     * @param resource the new resource base
     */
    public void setResourceBase(Resource resource)
    {
        try
        {
            if(resource!=null)
            {
                this.bundle=resource.getResourceType().getResourceBundle();
                this.loader=(ClassLoader)SWBPortal.getResourceMgr().getResourceLoaders().get(resource.getResourceType().getResourceClassName());
            }
        }catch(Exception e){log.error(e);}
        this.resource=resource;
        this.virtResource=resource;
    }
    
    /* (non-Javadoc)
     * @see org.semanticwb.portal.api.SWBParamRequest#getActionUrl()
     */
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

    /* (non-Javadoc)
     * @see org.semanticwb.portal.api.SWBParamRequest#getRenderUrl()
     */
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
    
    /* (non-Javadoc)
     * @see org.semanticwb.portal.api.SWBParameters#isSecure()
     */
    public boolean isSecure()
    {
        return secure;
    }    
    
    /**
     * Sets the secure.
     * 
     * @param secure the new secure
     */
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
    
    /* (non-Javadoc)
     * @see org.semanticwb.portal.api.SWBParamRequest#getLocaleString(java.lang.String)
     */
    public String getLocaleString(String key) throws SWBResourceException
    {
        return SWBUtils.TEXT.getLocaleString(bundle,key,locale,loader);
    }
    
    /* (non-Javadoc)
     * @see org.semanticwb.portal.api.SWBParamRequest#getLocaleLogString(java.lang.String)
     */
    public String getLocaleLogString(String key) throws SWBResourceException
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
    
    /**
     * Sets the virtual topic.
     * 
     * @param virt the new virtual topic
     */
    public void setVirtualTopic(WebPage virt)
    {
        adminTopic = topic;
        topic=virt;
        haveVirtTP=true;
    }
    
    /* (non-Javadoc)
     * @see org.semanticwb.portal.api.SWBParameters#haveVirtualWebPage()
     */
    public boolean haveVirtualWebPage()
    {
        return haveVirtTP;
    }
    
    /**
     * Getter for property virtResource.
     * @return Value of property virtResource.
     */
    public Resource getVirtualResource()
    {
        return virtResource;
    }
    
    /**
     * Setter for property virtResource.
     * @param virtResource New value of property virtResource.
     */
    public void setVirtualResource(Resource virtResource)
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
     * Parametros adicionales o bien parametros de otros recursos.
     * 
     * @param extParams the new ext uri params
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
     * Pasarle contenido al header del Template (style, javascript, etc).
     * 
     * @param templateHead New value of property templateHead.
     */    
    public void setTemplateHead(String templateHead)
    {
        this.templateHead = templateHead;
    }
    
}
