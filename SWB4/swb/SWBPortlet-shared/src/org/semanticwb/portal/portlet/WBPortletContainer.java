/*
 * WBPortletContainer.java
 *
 * Created on 22 de agosto de 2005, 01:22 PM
 */
package org.semanticwb.portal.portlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.portlet.Portlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.semanticwb.portal.api.SWBActionResponse;
import org.semanticwb.portal.api.SWBParamRequest;


/**
 *
 * @author Javier Solis Gonzalez
 */
public interface WBPortletContainer
{
    final static String ATT_PORTLET_DEFINITION = "com.infotec.wb.portlet.PortletDefinition";
    final static String ATT_PORTLET = "com.infotec.wb.portlet.Portlet";
    final static String ATT_METHOD = "com.infotec.wb.portlet.Method";
    final static String ATT_PORTLET_REQUEST = "javax.portlet.request";
    final static String ATT_PORTLET_RESPONSE = "javax.portlet.response";
    final static String ATT_PORTLET_CONFIG = "javax.portlet.config";
    final static Integer VAL_METHOD_RENDER = new Integer(0);
    final static Integer VAL_METHOD_ACTION = new Integer(1);
    final static Integer VAL_METHOD_LOAD = new Integer(2);
    final static Integer VAL_METHOD_INIT = new Integer(3);
    
    public Portlet loadPortlet(WBPortletDefinition def, HttpServletRequest request);
    
    public WBPortletDefinition getPortletDefinition(String site, String id);
    
    public void removePortletDefinition(String site, String id);
    
    public void addPortletDefinition(WBPortletDefinition def);
    
    public void log(Throwable e);
    
    public void log(String msg);
    
    public void log(String msg, Throwable e);
    
    public void invoke_render(HttpServletRequest request,
    HttpServletResponse response, SWBParamRequest params,
    WBPortletDefinition def) throws ServletException, IOException;
    
    public void invoke_action(HttpServletRequest request,
    SWBActionResponse params, WBPortletDefinition def)
    throws ServletException, IOException;
}
