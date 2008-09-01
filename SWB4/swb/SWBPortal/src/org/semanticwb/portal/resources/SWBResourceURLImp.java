/*
 * WBResRequest.java
 *
 * Created on 1 de junio de 2004, 01:39 PM
 */

package org.semanticwb.portal.resources;

import java.util.*;
import javax.servlet.http.HttpServletRequest;
import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
import org.semanticwb.base.util.URLEncoder;
import org.semanticwb.model.Portlet;
import org.semanticwb.model.WebPage;
import org.semanticwb.servlet.internal.DistributorParams;

/**
 * Clase que implementa WBResourceURL, que sirve para generar un URL valido por el portal para el recursos.
 * @author Javier Solis Gonzalez
 */
public class SWBResourceURLImp implements SWBResourceURL
{
    private static Logger log=SWBUtils.getLogger(SWBResourceURLImp.class);
    
    private String action=null;
    private int callMethod=0;
    private String mode=Mode_VIEW;
    private String winState=WinState_NORMAL;
    private boolean secure=false;
    private int urlType=UrlType_RENDER;  //0 render, 1 action
    private Portlet resource=null;
    private Portlet virtResource=null;
    private WebPage topic=null;
    private WebPage adminTopic=null;
    private HttpServletRequest request=null;
    private HashMap map=new HashMap();    
    private String extParams=null;
    
    private boolean haveVirtTP=false;
    private boolean onlyContent=false;    
    
    /** Creates a new instance of WBResRequest */
    public SWBResourceURLImp(HttpServletRequest request, Portlet resource,WebPage topic, int urlType)
    {
        this.request=request;
        this.resource=resource;
        this.virtResource=resource;
        this.topic=topic;
        this.urlType=urlType;
        this.adminTopic=topic;
    }
    
    public String getAction()
    {
        return action;
    }
    
    public SWBResourceURL setAction(String action)
    {
        this.action=action;
        return this;
    }
    
    public int getCallMethod()
    {
        return callMethod;
    }
    
    public SWBResourceURL setCallMethod(int callMethod)
    {
        this.callMethod=callMethod;
        return this;
    }
    
    public String getMode()
    {
        return mode;
    }

    public SWBResourceURL setMode(String mode)
    {
        this.mode=mode;
        return this;
    }
    
    public String getWindowState()
    {
        return winState;
    }
    
    public SWBResourceURL setWindowState(String winState)
    {
        this.winState=winState;
        return this;
    }
    
    /**
     * Regresa url del recurso
     * @return String url
     */    
    @Override
    public String toString()
    {
        return toString(false);
    }
    
    /**
     * Returns the portlet URL string representation to be embedded in the
     * markup.<br>
     * Note that the returned String may not be a valid URL, as it may
     * be rewritten by the portal/portlet-container before returning the
     * markup to the client.
     *
     * @return   the encoded URL as a string
     */
    public String toString(boolean encodeAmp)
    {
        //encodeAmp=encodeAmp && callMethod!=Call_DIRECT;
        String s="";
        {
            //NOTA:validar si request==null caso de WBActionResponse
            //if(secure)s="https://"+request.getRequestURI();
            //if(topic!=adminTopic && adminTopic!=null)
            if(haveVirtTP)
            {
                s+=adminTopic.getRealUrl();
                s+="/"+DistributorParams.URLP_VTOPIC+"/"+topic.getWebSite().getId()+"/"+topic.getId();
            }else{
                s+=topic.getRealUrl();
            }
            //System.out.println("ResourceURL->onlyContent:"+onlyContent);
            if(onlyContent)s+="/"+DistributorParams.URLP_ONLYCONTENT;            
            if(urlType==UrlType_RENDER)s+="/"+DistributorParams.URLP_RENDERID+"/";
            if(urlType==UrlType_ACTION)s+="/"+DistributorParams.URLP_ACTIONID+"/";
            s+=virtResource.getId();
            if(!virtResource.getWebSite().equals(topic.getWebSite()))s+="/"+DistributorParams.URLP_TOPICMAPID+"/"+virtResource.getWebSite().getId();
            if(callMethod==Call_DIRECT)s+="/"+DistributorParams.URLP_METHOD+"/"+callMethod;
            if(action!=null)s+="/"+DistributorParams.URLP_ACTION+"/"+action;
            if(!mode.equals(Mode_VIEW))s+="/"+DistributorParams.URLP_MODE+"/"+mode;
            if(!winState.equals(WinState_NORMAL))s+="/"+DistributorParams.URLP_WINSTATE+"/"+winState;
        }
        
//        String q=DistributorParams._getQueryString(map);
//        if(q!=null)s+=q;
        
        if(extParams!=null)s+=extParams;
        
        String q="";
        Iterator it=map.keySet().iterator();
        while(it.hasNext())
        {
            String key=(String)it.next();
            try
            {
                Object val=map.get(key);
                if(val instanceof String)
                {
                    if(encodeAmp)q+="&amp;";
                    else q+="&";
                    q+=key+"="+URLEncoder.encode((String)val);
                }else 
                {
                    String vals[]=(String[])val;
                    for(int x=0;x<vals.length;x++)
                    {
                        if(encodeAmp)q+="&amp;";
                        else q+="&";
                        q+=key+"=";
                        if(vals[x]!=null)
                        {
                            q+=URLEncoder.encode(vals[x]);
                        }
                    }
                }
            }catch(Exception e){log.error(e);}
        }
        if(q.length()>0 && s.indexOf('?')==-1)
        {
            if(encodeAmp)q="?"+q.substring(5);
            else q="?"+q.substring(1);
        }
        s+=q;
        
        return s;
    }
    
    public boolean isSecure()
    {
        return secure;
    }    
    
    public SWBResourceURL setSecure(boolean secure)
    {
        this.secure=secure;
        return this;
    }
    
    public int getURLType()
    {
        return urlType;
    }
    
    public void setURLType(int type)
    {
        this.urlType=type;
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
    
    /** Getter for property resource.
     * @return Value of property resource.
     *
     */
    public Portlet getResourceBase()
    {
        return resource;
    }
    
    /** Setter for property resource.
     * @param resource New value of property resource.
     *
     */
    public void setResourceBase(Portlet resource)
    {
        this.resource = resource;
        this.virtResource = resource;
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

  public void setParameters(java.util.Map parameters)
  {
      if(parameters!=null)
      {
          Iterator it=parameters.keySet().iterator();
          while(it.hasNext())
          {
              String key=(String)it.next();
              map.put(key, parameters.get(key));
          }
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

  public void setParameter(String key, String value)
  {
      if(key!=null && value!=null)
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

    public void setParameter(String key, String[] values)
    {
      if(key!=null && values!=null)
        map.put(key, values);
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
