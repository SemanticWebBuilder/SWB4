
/*
 * WBResponse.java
 *
 * Created on 2 de septiembre de 2003, 11:35
 */

package org.semanticwb.portal.resources;

import java.util.*;
import javax.servlet.http.HttpServletResponse;
import org.semanticwb.Logger;
import org.semanticwb.SWBException;
import org.semanticwb.SWBPortal;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.Portlet;
import org.semanticwb.model.User;
import org.semanticwb.model.WebPage;

/**
 * Clase que implementa WBActionResponse.
 * Esta clase da acceso al Response del recurso cuando es llamado por el ActionResponse.
 * @author Javier Solis Gonzalez
 */
public class SWBActionResponseImp implements SWBActionResponse
{
    private static Logger log = SWBUtils.getLogger(SWBActionResponseImp.class);
    private HttpServletResponse response;
    private String winState=WinState_NORMAL;
    private String mode=Mode_VIEW;
    private String location=null;
    private HashMap map=new HashMap();
    
    private WebPage topic=null;
    private WebPage adminTopic=null;
    private User user=null;
    private int userLevel=0;    
    private String action=null;    
    private boolean secure=false;   
    private Portlet resource=null;
    private int callMethod=0;

    private Locale locale=null;
    private String bundle=null;
    private ClassLoader loader=null;
    
    private boolean haveVirtTP=false;
    private boolean onlyContent=false;    
    private String extParams=null;    
    
    /** Creates a new instance of WBResponse */
    public SWBActionResponseImp(HttpServletResponse response)
    {
        this.response = response;
    }

  /**
   * Sets the window state of a portlet to the given window state.
   * <p>
   * Possible values are the standard window states and any custom 
   * window states supported by the portal and the portlet. 
   * Standard window states are:
   * <ul>
   * <li>MINIMIZED
   * <li>NORMAL
   * <li>MAXIMIZED
   * </ul>
   *
   * @param windowState
   *               the new portlet window state
   *
   * @exception WindowStateException
   *                   if the portlet cannot switch to the specified window state.
   *                   To avoid this exception the portlet can check the allowed
   *                   window states with <code>Request.isWindowStateAllowed()</code>.
   * @exception java.lang.IllegalStateException
   *                    if the method is invoked after <code>sendRedirect</code> has been called.
   *
   * @see WindowState
   */

  public void setWindowState(String state)
  {
      winState=state;
  }
  

  public String getWindowState()
  {
      return winState;
  }
  


  /**
   * Sets the portlet mode of a portlet to the given portlet mode.
   * <p>
   * Possible values are the standard portlet modes and any custom 
   * portlet modes supported by the portal and the portlet. Portlets 
   * must declare in the deployment descriptor the portlet modes they 
   * support for each markup type.  
   * Standard portlet modes are:
   * <ul>
   * <li>EDIT
   * <li>HELP
   * <li>VIEW
   * </ul>
   * <p>
   * Note: The portlet may still be called in a different window
   *       state in the next render call, depending on the portlet container / portal.
   * 
   * @param portletMode
   *               the new portlet mode
   *
   * @exception PortletModeException
   *                   if the portlet cannot switch to this portlet mode,
   *                   because the portlet or portal does not support it for this markup,
   *                   or the current user is not allowed to switch to this portlet mode.
   *                   To avoid this exception the portlet can check the allowed
   *                   portlet modes with <code>Request.isPortletModeAllowed()</code>.
   * @exception java.lang.IllegalStateException
   *                    if the method is invoked after <code>sendRedirect</code> has been called.
   */

  public void setMode(String mode)
  {
     this.mode=mode;
  }
  
  public String getMode()
  {
      return mode;
  }

  /**
   * Instructs the portlet container to send a redirect response 
   * to the client using the specified redirect location URL.  
   * <p>
   * This method only accepts an absolute URL (e.g. 
   * <code>http://my.co/myportal/mywebap/myfolder/myresource.gif</code>)
   * or a full path URI (e.g. <code>/myportal/mywebap/myfolder/myresource.gif</code>).
   * If required, 
   * the portlet container may encode the given URL before the 
   * redirection is issued to the client.
   * <p>
   * The sendRedirect method can not be invoked after any of the 
   * following methods of the ActionResponse interface has been called:
   * <ul>
   * <li>setPortletMode
   * <li>setWindowState
   * <li>setRenderParameter
   * <li>setRenderParameters
   * </ul>
   *
   * @param		location	the redirect location URL
   *
   * @exception	java.io.IOException	
   *                    if an input or output exception occurs.
   * @exception	java.lang.IllegalArgumentException	
   *                    if a relative path URL is given
   * @exception java.lang.IllegalStateException
   *                    if the method is invoked after any of above mentioned methods of 
   *                    the ActionResponse interface has been called.
   */

  public void sendRedirect(String location)
  {
      this.location=location.replace("&amp;","&");  //replace "&amp;" for "&"
  }
  
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
   * <code>PortletRequest.getParameter</code> call until
   * a new request is targeted to the portlet.
   * <p>
   * The given parameters do not need to be encoded
   * prior to calling this method.
   *
   * @param  parameters   Map containing parameter names for 
   *                      the render phase as 
   *                      keys and parameter values as map 
   *                      values. The keys in the parameter
   *                      map must be of type String. The values 
   *                      in the parameter map must be of type
   *                      String array (<code>String[]</code>).
   *
   * @exception	java.lang.IllegalArgumentException 
   *                      if parameters is <code>null</code>, if
   *                      any of the key/values in the Map are <code>null</code>, 
   *                      if any of the keys is not a String, or if any of 
   *                      the values is not a String array.
   * @exception java.lang.IllegalStateException
   *                    if the method is invoked after <code>sendRedirect</code> has been called.
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
   * <code>PortletRequest.getParameter</code> call until
   * a request is targeted to the portlet.
   * <p>
   * This method replaces all parameters with the given key.
   * <p>
   * The given parameter do not need to be encoded
   * prior to calling this method.
   *
   * @param  key    key of the render parameter
   * @param  value  value of the render parameter
   *
   * @exception	java.lang.IllegalArgumentException	
   *                      if key or value are <code>null</code>.
   * @exception java.lang.IllegalStateException
   *                    if the method is invoked after <code>sendRedirect</code> has been called.
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
   * <code>PortletRequest.getParameter</code> call until
   * a request is targeted to the portlet.
   * <p>
   * This method replaces all parameters with the given key.
   * <p>
   * The given parameter do not need to be encoded
   * prior to calling this method.
   * 
   * @param  key     key of the render parameter
   * @param  values  values of the render parameter
   *
   * @exception	java.lang.IllegalArgumentException	
   *                      if key or value are <code>null</code>.
   * @exception java.lang.IllegalStateException
   *                    if the method is invoked after <code>sendRedirect</code> has been called.
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
  public WebPage getTopic()
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
      if(user!=null)locale=new Locale(user.getLanguage().getId());
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
  
    public boolean isSecure()
    {
        return secure;
    }    
    
    public void setSecure(boolean secure)
    {
        this.secure=secure;
    }
  
    /** Getter for property base.
     * @return Value of property base.
     *
     */
    public Portlet getResourceBase()
    {
        return resource;
    }
    
    /** Setter for property base.
     * @param base New value of property base.
     *
     */
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
        this.resource = resource;
    }
    
    public int getCallMethod()
    {
        return callMethod;
    }
    
    public void setCallMethod(int callMethod)
    {
        this.callMethod=callMethod;
    }    
    
    @Override
    public String toString()
    {
        return getLocation();
    }    
    
    public String getLocaleString(String key) throws SWBException
    {
        return SWBUtils.TEXT.getLocaleString(bundle,key,locale,loader);
    }    
    
    public String getLocaleLogString(String key) throws SWBException
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
     * Parametros adicionales o bien parametros de otros recursos
     * @param otherParams New value of property otherParams.
     */
    public void setExtURIParams(java.lang.String extParams)
    {
        this.extParams = extParams;
    }    
        
}
