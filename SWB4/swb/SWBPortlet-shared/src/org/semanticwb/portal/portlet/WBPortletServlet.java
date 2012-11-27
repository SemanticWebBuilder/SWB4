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
package org.semanticwb.portal.portlet;

import java.io.*;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.Portlet;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import javax.servlet.*;
import javax.servlet.http.*;
import org.semanticwb.portal.portlet.factory.WBFactoryMgr;


/**
 *
 * @author Javier Solis Gonzalez
 * @version
 */
public class WBPortletServlet extends HttpServlet
{
    /** Initializes the servlet.
     */
    public void init(ServletConfig config) throws ServletException
    {
        super.init(config);
    }
    
    /** Destroys the servlet.
     */
    public void destroy()
    {
    }
    
    /** Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     */
    protected void processRequest(HttpServletRequest request,
    HttpServletResponse response) throws ServletException, IOException
    {
        //System.out.println("*************************************************************************************");
        Integer method = (Integer) request.getAttribute(WBPortletContainer.ATT_METHOD);
        
        //System.out.println("request:"+request.getContextPath());
        
        //System.out.println("WBPortletServlet.processRequest:"+method);
        if (method == null)
        {
            return;
        }
        
        if (method.equals(WBPortletContainer.VAL_METHOD_LOAD))
        {
            WBPortletDefinition def = (WBPortletDefinition) request.getAttribute(WBPortletContainer.ATT_PORTLET_DEFINITION);
            
            if (def != null)
            {
                try
                {
                    def.setPortletClassLoader(Thread.currentThread().getContextClassLoader());
                    
                    //System.out.println("Loading class:"+def.getClassName()+" ->"+def.getPortletClassLoader());
                    Class cls = def.getPortletClassLoader().loadClass(def.getClassName());
                    def.setServletConfig(getServletConfig());
                    
                    Portlet portlet = (Portlet) cls.newInstance();
                    def.setPortlet(portlet);
                    WBFactoryMgr.getPortletContainer().addPortletDefinition(def);
                    
                    //System.out.println("Load Portlet:"+def);
                } catch (Exception e)
                {
                    WBFactoryMgr.getPortletContainer().log("Error loading Portlet class:" +
                    def.getClassName(), e);
                }
            } else
            {
                WBFactoryMgr.getPortletContainer().log("WBPortletServlet.processRequest: The PortletDefinition==null");
            }
        }else if (method.equals(WBPortletContainer.VAL_METHOD_INIT))
        {
            WBPortletDefinition def = (WBPortletDefinition) request.getAttribute(WBPortletContainer.ATT_PORTLET_DEFINITION);
            //System.out.println("Init portelt:getDef:"+def);
            
            if (def != null)
            {
                try
                {
                    Portlet portlet = def.getPortlet();
                    //System.out.println("Init portelt:portlet:"+portlet);
                    //System.out.println("Init portelt:portletConfig:"+def.getPortletConfig());
                    portlet.init(def.getPortletConfig());
                    //System.out.println("End Init");
                } catch (Throwable e)
                {
                    //System.out.println(e);
                    WBFactoryMgr.getPortletContainer().log("Error initializing Portlet class:" +
                    def.getClassName(), e);
                }
            } else
            {
                WBFactoryMgr.getPortletContainer().log("WBPortletServlet.processRequest: The PortletDefinition==null");
            }
        } else if (method.equals(WBPortletContainer.VAL_METHOD_RENDER))
        {
            Portlet portlet = (Portlet) request.getAttribute(WBPortletContainer.ATT_PORTLET);
            
            try
            {
                RenderRequest req = (RenderRequest) request.getAttribute(WBPortletContainer.ATT_PORTLET_REQUEST);
                
                //System.out.println("Portlet:"+portlet);
                //System.out.println("RenderRequest:"+req+" intro:"+req.getAttribute(WBPortletContainer.ATT_PORTLET_REQUEST));
                //System.out.println("r_session_req:"+request.getSession());
                //System.out.println("r_session_pt:"+((HttpServletRequestWrapper)req).getSession());
                ((HttpServletRequestWrapper) req).setRequest(request);
                
                RenderResponse res = (RenderResponse) request.getAttribute(WBPortletContainer.ATT_PORTLET_RESPONSE);
                portlet.render(req, res);
            } catch (Exception e)
            {
                WBFactoryMgr.getPortletContainer().log("WBPortletServlet.processRequest: Render->" +
                portlet, e);
            }
        } else if (method.equals(WBPortletContainer.VAL_METHOD_ACTION))
        {
            Portlet portlet = (Portlet) request.getAttribute(WBPortletContainer.ATT_PORTLET);
            
            try
            {
                ActionRequest req = (ActionRequest) request.getAttribute(WBPortletContainer.ATT_PORTLET_REQUEST);
                
                //System.out.println("a_session_req:"+request.getServletPath());
                //System.out.println("a_session_pt:"+((HttpServletRequestWrapper)req).getServletPath());
                ((HttpServletRequestWrapper) req).setRequest(request);
                
                //System.out.println("RenderRequest:"+req+" intro:"+req.getAttribute(WBPortletContainer.ATT_PORTLET_REQUEST));
                ActionResponse res = (ActionResponse) request.getAttribute(WBPortletContainer.ATT_PORTLET_RESPONSE);
                portlet.processAction(req, res);
            } catch (Exception e)
            {
                WBFactoryMgr.getPortletContainer().log("WBPortletServlet.processRequest: Action->" +
                portlet, e);
            }
        }
        
        //        response.setContentType("text/html");
        //        PrintWriter out = response.getWriter();
        //        out.close();
    }
    
    /** Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     */
    protected void doGet(HttpServletRequest request,
    HttpServletResponse response) throws ServletException, IOException
    {
        processRequest(request, response);
    }
    
    /** Handles the HTTP <code>POST</code> method.
     * @param request servlet request
     * @param response servlet response
     */
    protected void doPost(HttpServletRequest request,
    HttpServletResponse response) throws ServletException, IOException
    {
        processRequest(request, response);
    }
    
    /** Returns a short description of the servlet.
     */
    public String getServletInfo()
    {
        return "Short description";
    }
}
