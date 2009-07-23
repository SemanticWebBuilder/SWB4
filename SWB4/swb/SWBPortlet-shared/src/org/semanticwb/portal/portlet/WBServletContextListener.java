/*
 * ContextListener.java
 *
 * Created on 16 de febrero de 2006, 11:17 AM
 */

package org.semanticwb.portal.portlet;

import javax.servlet.ServletContextListener;
import javax.servlet.ServletContextEvent;
import org.semanticwb.portal.portlet.factory.WBFactoryMgr;

/**
 *
 * @author Javier Solis Gonzalez
 */
public class WBServletContextListener implements ServletContextListener
{
    
    /** Creates a new instance of ContextListener */
    public WBServletContextListener()
    {
    }
    
    public void contextDestroyed(ServletContextEvent servletContextEvent)
    {
        //System.out.println("Stoping ServletContext:"+servletContextEvent.getServletContext());
        WBFactoryMgr.removeServletContext(servletContextEvent.getServletContext());
    }
    
    public void contextInitialized(ServletContextEvent servletContextEvent)
    {
        //System.out.println("Initializing ServletContext:"+servletContextEvent);
        WBFactoryMgr.addServletContext(servletContextEvent.getServletContext());
    }
    
}
