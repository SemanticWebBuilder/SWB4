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
import javax.servlet.http.HttpServletResponse;
import org.semanticwb.Logger;
import org.semanticwb.SWBPortal;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.Resource;
import org.semanticwb.model.User;
import org.semanticwb.model.WebPage;

// TODO: Auto-generated Javadoc
/**
 * Clase que implementa WBActionResponse.
 * Esta clase da acceso al Response del recurso cuando es llamado por el ActionResponse.
 * @author Javier Solis Gonzalez
 */
public class SWBActionResponseImp implements SWBActionResponse
{
    
    /** The log. */
    private static Logger log = SWBUtils.getLogger(SWBActionResponseImp.class);
    
    /** The response. */
    private HttpServletResponse response;
    
    /** The win state. */
    private String winState=WinState_NORMAL;
    
    /** The mode. */
    private String mode=Mode_VIEW;
    
    /** The location. */
    private String location=null;
    
    /** The map. */
    private HashMap map=new HashMap();
    
    /** The topic. */
    private WebPage topic=null;
    
    /** The admin topic. */
    private WebPage adminTopic=null;
    
    /** The user. */
    private User user=null;
    
    /** The user level. */
    private int userLevel=0;    
    
    /** The action. */
    private String action=null;    
    
    /** The secure. */
    private boolean secure=false;   
    
    /** The resource. */
    private Resource resource=null;
    
    /** The virt resource. */
    private Resource virtResource=null;
    
    /** The call method. */
    private int callMethod=0;

    /** The locale. */
    private Locale locale=null;
    
    /** The bundle. */
    private String bundle=null;
    
    /** The loader. */
    private ClassLoader loader=null;
    
    /** The have virt tp. */
    private boolean haveVirtTP=false;
    
    /** The only content. */
    private boolean onlyContent=false;    
    
    /** The ext params. */
    private String extParams=null;    
    
    /**
     * Creates a new instance of WBResponse.
     * 
     * @param response the response
     */
    public SWBActionResponseImp(HttpServletResponse response)
    {
        this.response = response;
    }

  /**
   * Sets the window state of a resource to the given window state.
   * <p>
   * Possible values are the standard window states and any custom
   * window states supported by the portal and the resource.
   * Standard window states are:
   * <ul>
   * <li>MINIMIZED
   * <li>NORMAL
   * <li>MAXIMIZED
   * </ul>
   * 
   * @param state the new window state
   * @exception WindowStateException
   * if the resource cannot switch to the specified window state.
   * To avoid this exception the resource can check the allowed
   * window states with <code>Request.isWindowStateAllowed()</code>.
   * @exception java.lang.IllegalStateException
   * if the method is invoked after <code>sendRedirect</code> has been called.
   * @see WindowState
   */

  public void setWindowState(String state)
  {
      winState=state;
  }
  

  /* (non-Javadoc)
   * @see org.semanticwb.portal.api.SWBParameters#getWindowState()
   */
  public String getWindowState()
  {
      return winState;
  }
  


  /**
   * Sets the resource mode of a resource to the given resource mode.
   * <p>
   * Possible values are the standard resource modes and any custom
   * resource modes supported by the portal and the resource. Resources
   * must declare in the deployment descriptor the resource modes they
   * support for each markup type.
   * Standard resource modes are:
   * <ul>
   * <li>EDIT
   * <li>HELP
   * <li>VIEW
   * </ul>
   * <p>
   * Note: The resource may still be called in a different window
   * state in the next render call, depending on the resource container / portal.
   * 
   * @param mode the new mode
   * @exception ResourceModeException
   * if the resource cannot switch to this resource mode,
   * because the resource or portal does not support it for this markup,
   * or the current user is not allowed to switch to this resource mode.
   * To avoid this exception the resource can check the allowed
   * resource modes with <code>Request.isResourceModeAllowed()</code>.
   * @exception java.lang.IllegalStateException
   * if the method is invoked after <code>sendRedirect</code> has been called.
   */

  public void setMode(String mode)
  {
     this.mode=mode;
  }
  
  /* (non-Javadoc)
   * @see org.semanticwb.portal.api.SWBParameters#getMode()
   */
  public String getMode()
  {
      return mode;
  }

  /**
   * Instructs the resource container to send a redirect response
   * to the client using the specified redirect location URL.
   * <p>
   * This method only accepts an absolute URL (e.g.
   * <code>http://my.co/myportal/mywebap/myfolder/myresource.gif</code>)
   * or a full path URI (e.g. <code>/myportal/mywebap/myfolder/myresource.gif</code>).
   * If required,
   * the resource container may encode the given URL before the
   * redirection is issued to the client.
   * <p>
   * The sendRedirect method can not be invoked after any of the
   * following methods of the ActionResponse interface has been called:
   * <ul>
   * <li>setResourceMode
   * <li>setWindowState
   * <li>setRenderParameter
   * <li>setRenderParameters
   * </ul>
   * 
   * @param location the location
   * @param		location	the redirect location URL
   * @exception	java.io.IOException
   * if an input or output exception occurs.
   * @exception	java.lang.IllegalArgumentException
   * if a relative path URL is given
   * @exception java.lang.IllegalStateException
   * if the method is invoked after any of above mentioned methods of
   * the ActionResponse interface has been called.
   */

  public void sendRedirect(String location)
  {
      this.location=location.replace("&amp;","&");  //replace "&amp;" for "&"
  }
  
  /* (non-Javadoc)
   * @see org.semanticwb.portal.api.SWBActionResponse#getLocation()
   */
  public String getLocation()
  {
        String s="";
        if(location!=null)
        {
            s=location;
        }
        else
        {
            SWBResourceURLImp url=new SWBResourceURLImp(null,resource,topic,UrlType_RENDER);
            if(resource!=virtResource)url.setVirtualResource(virtResource);
            url.setParameters(map);
            if(haveVirtTP)url.setAdminTopic(adminTopic);
            url.setOnlyContent(onlyContent);
            url.setAction(action);
            url.setCallMethod(callMethod);
            url.setMode(mode);
            url.setSecure(secure);
            url.setWindowState(winState);
            url.setExtURIParams(extParams);
            s=url.toString(false);              //no codifica "&" por "&amp;"
            
//            //if(secure)s="https://"+request.getRequestURI();
//            //if(topic!=adminTopic && adminTopic!=null)
//            if(haveVirtTP)
//            {
//                s+=adminTopic.getUrl();
//                s+="/"+DistributorParams.URLP_VTOPIC+"/"+topic.getMap().getId()+"/"+topic.getId();
//            }else{
//                s+=topic.getUrl();
//            }
//            s+="/"+DistributorParams.URLP_RENDERID+"/";
//            s+=resource.getId();
//            if(resource.getTopicMap()!=topic.getMap())s+="/"+DistributorParams.URLP_TOPICMAPID+"/"+resource.getTopicMapId();
//            if(callMethod==Call_DIRECT)s+="/"+DistributorParams.URLP_METHOD+"/"+callMethod;
//            if(action!=null)s+="/"+DistributorParams.URLP_ACTION+"/"+action;
//            if(!mode.equals(Mode_VIEW))s+="/"+DistributorParams.URLP_MODE+"/"+mode;
//            if(!winState.equals(WinState_NORMAL))s+="/"+DistributorParams.URLP_WINSTATE+"/"+winState;
//        }
//        
//        String q="";
//        Iterator it=map.keySet().iterator();
//        while(it.hasNext())
//        {
//            String key=(String)it.next();
//            try
//            {
//                q+="&"+key+"="+com.infotec.appfw.util.URLEncoder.encode((String)map.get(key));
//            }catch(Exception e){AFUtils.log(e);}
//        }
//        if(q.length()>0 && s.indexOf('?')==-1)
//        {
//            q="?"+q.substring(1);
//        }
//        s+=q;
        }
        return s;
  }


  /**
   * Sets a parameter map for the render request.
   * <p>
   * All previously set render parameters are cleared.
   * <p>
   * These parameters will be accessible in all
   * sub-sequent render calls via the
   * <code>ResourceRequest.getParameter</code> call until
   * a new request is targeted to the resource.
   * <p>
   * The given parameters do not need to be encoded
   * prior to calling this method.
   * 
   * @param parameters the new render parameters
   * @exception	java.lang.IllegalArgumentException
   * if parameters is <code>null</code>, if
   * any of the key/values in the Map are <code>null</code>,
   * if any of the keys is not a String, or if any of
   * the values is not a String array.
   * @exception java.lang.IllegalStateException
   * if the method is invoked after <code>sendRedirect</code> has been called.
   */

  public void setRenderParameters(java.util.Map parameters)
  {
      Iterator it=parameters.keySet().iterator();
      while(it.hasNext())
      {
          String key=(String)it.next();
          map.put(key, parameters.get(key));
      }
  }


  /**
   * Sets a String parameter for the render request.
   * <p>
   * These parameters will be accessible in all
   * sub-sequent render calls via the
   * <code>ResourceRequest.getParameter</code> call until
   * a request is targeted to the resource.
   * <p>
   * This method replaces all parameters with the given key.
   * <p>
   * The given parameter do not need to be encoded
   * prior to calling this method.
   * 
   * @param key the key
   * @param value the value
   * @exception	java.lang.IllegalArgumentException
   * if key or value are <code>null</code>.
   * @exception java.lang.IllegalStateException
   * if the method is invoked after <code>sendRedirect</code> has been called.
   */

  public void setRenderParameter(String key, String value)
  {
      if(key==null || value==null)return;
      map.put(key, value);
  }


  /**
   * Sets a String array parameter for the render request.
   * <p>
   * These parameters will be accessible in all
   * sub-sequent render calls via the
   * <code>ResourceRequest.getParameter</code> call until
   * a request is targeted to the resource.
   * <p>
   * This method replaces all parameters with the given key.
   * <p>
   * The given parameter do not need to be encoded
   * prior to calling this method.
   * 
   * @param key the key
   * @param values the values
   * @exception	java.lang.IllegalArgumentException
   * if key or value are <code>null</code>.
   * @exception java.lang.IllegalStateException
   * if the method is invoked after <code>sendRedirect</code> has been called.
   */

  public void setRenderParameter(String key, String[] values)
  {
      if(key==null || values==null)return;
      map.put(key, values);
  }
  
  /** Getter for property topic.
   * @return Value of property topic.
   *
   */
  public WebPage getWebPage()
  {
      return topic;
  }
  
  /** Setter for property topic.
   * @param topic New value of property topic.
   *
   */
  public void setTopic(WebPage topic)
  {
      this.topic = topic;
      if(adminTopic==null)adminTopic=topic;
  }
  
  /** Getter for property user.
   * @return Value of property user.
   *
   */
  public User getUser()
  {
      return user;
  }
  
  /** Setter for property user.
   * @param user New value of property user.
   *
   */
  public void setUser(User user)
  {
      if(user!=null)locale=new Locale(user.getLanguage());
      this.user = user;
  }
  
  /** Getter for property userLevel.
   * @return Value of property userLevel.
   *
   */
  public int getUserLevel()
  {
      return userLevel;
  }
  
  /** Setter for property userLevel.
   * @param userLevel New value of property userLevel.
   *
   */
  public void setUserLevel(int userLevel)
  {
      this.userLevel = userLevel;
  }
  
  /** Getter for property action.
   * @return Value of property action.
   *
   */
  public java.lang.String getAction()
  {
      return action;
  }
  
  /** Setter for property action.
   * @param action New value of property action.
   *
   */
  public void setAction(java.lang.String action)
  {
      this.action = action;
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
  
    /** Getter for property base.
     * @return Value of property base.
     *
     */
    public Resource getResourceBase()
    {
        return resource;
    }
    
    /**
     * Setter for property base.
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
        this.resource = resource;
        this.virtResource=resource;
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
    
    /* (non-Javadoc)
     * @see org.semanticwb.portal.api.SWBParameters#getCallMethod()
     */
    public int getCallMethod()
    {
        return callMethod;
    }
    
    /* (non-Javadoc)
     * @see org.semanticwb.portal.api.SWBActionResponse#setCallMethod(int)
     */
    public void setCallMethod(int callMethod)
    {
        this.callMethod=callMethod;
    }    
    
    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString()
    {
        return getLocation();
    }    
    
    /* (non-Javadoc)
     * @see org.semanticwb.portal.api.SWBActionResponse#getLocaleString(java.lang.String)
     */
    public String getLocaleString(String key) throws SWBResourceException
    {
        return SWBUtils.TEXT.getLocaleString(bundle,key,locale,loader);
    }    
    
    /* (non-Javadoc)
     * @see org.semanticwb.portal.api.SWBActionResponse#getLocaleLogString(java.lang.String)
     */
    public String getLocaleLogString(String key) throws SWBResourceException
    {
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
     * Getter for property response.
     * @return Value of property response.
     */
    public javax.servlet.http.HttpServletResponse getResponse()
    {
        return response;
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
        
}
