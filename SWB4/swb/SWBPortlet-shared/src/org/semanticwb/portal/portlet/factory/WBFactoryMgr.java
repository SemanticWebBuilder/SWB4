/*
 * WBFactoryMgr.java
 *
 * Created on 22 de agosto de 2005, 08:32 PM
 */
package org.semanticwb.portal.portlet.factory;

import java.net.URL;
import java.util.HashMap;
import java.util.Set;

import javax.portlet.PortalContext;
import javax.servlet.ServletContext;
import org.semanticwb.portal.portlet.WBPortletContainer;


/**
 *
 * @author Javier Solis Gonzalez
 */
public class WBFactoryMgr {
    private static WBPortletContainer portletContainer = null;
    private static PortalContext portalContext = null;
    
    private static HashMap contexts=new HashMap();

    public static WBPortletContainer getPortletContainer() {
        return portletContainer;
    }

    public static void setPortletContainer(WBPortletContainer container) {
        portletContainer = container;
    }

    public static PortalContext getPortalContext() {
        return portalContext;
    }

    public static void setPortalContext(PortalContext context) {
        portalContext = context;
    }
    
    public static void addServletContext(ServletContext context)
    {
        try
        {
            URL url=context.getResource("/");
            String path=url.toString();
            if(path.endsWith("/"))path=path.substring(0,path.length()-1);
            path=path.substring(path.lastIndexOf("/")+1);
            //System.out.println("host:"+url.getHost());
            System.out.println("**************************************************");
            System.out.println("Context Path:"+path);
            //path=context.getServletContextName();
            //System.out.println("Context Name:"+path);
            System.out.println("**************************************************");
            contexts.put(path, context);
        }catch(Exception e){e.printStackTrace();}
    }
        
    
    public static void removeServletContext(ServletContext context)
    {
        removeServletContext(context.getServletContextName());
    }    
    
    
    public static void removeServletContext(String contextName)
    {
        contexts.remove(contextName);
    }    
    
    public static ServletContext getServletContext(String contextName)
    {
        return (ServletContext)contexts.get(contextName);
    }    
    
    public static Set getServletContextNames()
    {
        return contexts.keySet();
    }
}
