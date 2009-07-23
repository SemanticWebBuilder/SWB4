/*
 * WBPortletContainer.java
 *
 * Created on 22 de agosto de 2005, 01:22 PM
 */

package org.semanticwb.portal.portlet;

import java.io.PrintWriter;
import java.util.Hashtable;
import javax.portlet.Portlet;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import java.io.IOException;
import java.io.InputStream;
import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletConfig;
import javax.portlet.RenderRequest;
import org.semanticwb.SWBUtils;
import org.semanticwb.portal.api.SWBActionResponse;
import org.semanticwb.portal.api.SWBActionResponseImp;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.lib.SWBResponse;
import org.semanticwb.portal.portlet.factory.WBFactoryMgr;

/**
 *
 * @author Javier Solis Gonzalez
 */
public class WBPortletContainerImp implements WBPortletContainer
{
    final static String WBPORTLET_SERVLET="/WBPortletServlet";
    
    PrintWriter log= new PrintWriter(System.err);

    Hashtable portletDefinitions=null; 
    
    ServletContext context;
    
    /** Creates a new instance of WBPortletContainer */
    public WBPortletContainerImp(ServletContext context)
    {
        log("Initializing WBPortletContaner");
        this.context=context;
        portletDefinitions=new Hashtable();
        WBFactoryMgr.setPortalContext(new PortalContextImp());
    }
    
    public ServletContext getContext(String contextName)
    {
        ServletContext cont=context.getContext(contextName);
        System.out.println("getContext_1:"+cont);
        if(cont==null)
        {
            cont=WBFactoryMgr.getServletContext(contextName);
            System.out.println("getContext_2:"+cont);
        }
        return cont;
    }    
    
    public Portlet loadPortlet(WBPortletDefinition def, HttpServletRequest request)
    {
        //System.out.println("WBPortletContainerImp.loadPortlet:"+def);
        Portlet portlet=null;
        try
        {
            //System.out.println("context:"+def.getContext());
            ServletContext cont=getContext(def.getContext());
            
            // load its portlet.xml
            InputStream stream = cont.getResourceAsStream("/WEB-INF/portlet.xml");
            def.loadDefinition(stream);
            //System.out.println("def:"+def+" class:"+def.getClassName());
            invoke_load(request, cont, def);
            
            WBPortletDefinition invdef=getPortletDefinition(def.getSite(),def.getId());
            //System.out.println("invdef:"+invdef);
            
            //Crear y agregar portelConfig al portletDefinition
            PortletConfig config=new PortletConfigImp(new PortletContextImp(invdef.getServletConfig().getServletContext()),invdef);
            
            //System.out.println("config:"+config);
            invdef.setPortletConfig(config);

            invoke_init(request, cont,invdef);
            
            portlet=invdef.getPortlet();
            
            //System.out.println("return portlet:"+portlet);
            
        }catch(Exception e){log(e);}
        
        
        //System.out.println("WBPortletContainerImp.loadPortlet_ret:"+portlet);
        return portlet;
        
    }
    
    private void invoke_load(HttpServletRequest request, ServletContext cont, WBPortletDefinition def) throws ServletException, IOException
    {
        //HttpServletRequest request=new WBHttpServletRequestWrapper(new WBRequest());
        HttpServletResponse response=new SWBResponse();
        request.setAttribute(ATT_PORTLET_DEFINITION, def);
        request.setAttribute(ATT_METHOD,VAL_METHOD_LOAD);
        RequestDispatcher dispatcher = cont.getRequestDispatcher(WBPORTLET_SERVLET);
        dispatcher.include(request, response);        
    }
    
    private void invoke_init(HttpServletRequest request, ServletContext cont, WBPortletDefinition def) throws ServletException, IOException
    {
        //HttpServletRequest request=new WBRequest();
        HttpServletResponse response=new SWBResponse();
        request.setAttribute(ATT_PORTLET_DEFINITION, def);
        request.setAttribute(ATT_METHOD,VAL_METHOD_INIT);
        RequestDispatcher dispatcher = cont.getRequestDispatcher(WBPORTLET_SERVLET);
        dispatcher.include(request, response);        
    }    
    
    /**
     *  return String title
     */
    public void invoke_render(HttpServletRequest request, HttpServletResponse response, SWBParamRequest params, WBPortletDefinition def) throws ServletException, IOException
    {
        RenderRequest req=new RenderRequestImp(request, params, def, false);
        RenderResponseImp res=new RenderResponseImp(response, params);
        ServletContext cont=getContext(def.getContext());
        try
        {
            Portlet portlet=def.getPortlet();
            request.setAttribute(ATT_PORTLET_REQUEST,req);
            request.setAttribute(ATT_PORTLET_RESPONSE,res);
            request.setAttribute(ATT_PORTLET_CONFIG,def.getPortletConfig());
            request.setAttribute(ATT_PORTLET, portlet);
            request.setAttribute(ATT_METHOD,VAL_METHOD_RENDER);
            RequestDispatcher dispatcher = cont.getRequestDispatcher(WBPORTLET_SERVLET);
            dispatcher.include(request, response);        
//            portlet.render(req, res);
        }catch(Exception e)
        {
            log(e);
        }finally
        {
            request.removeAttribute(ATT_PORTLET_REQUEST);
            request.removeAttribute(ATT_PORTLET_RESPONSE);
            request.removeAttribute(ATT_PORTLET_CONFIG);
            request.removeAttribute(ATT_PORTLET);
            request.removeAttribute(ATT_METHOD);
        }
        params.setWindowTitle(res.getTitle());
        //return res.getTitle(); 
    }    
    
    public void invoke_action(HttpServletRequest request, SWBActionResponse params, WBPortletDefinition def) throws ServletException, IOException
    {
        HttpServletResponse response=((SWBActionResponseImp)params).getResponse();
        ActionRequest req=new ActionRequestImp(request, params, def, false);
        ActionResponse res=new ActionResponseImp(response, params);
        ServletContext cont=getContext(def.getContext());
        try
        {
            Portlet portlet=def.getPortlet();
            request.setAttribute(ATT_PORTLET_REQUEST,req);
            request.setAttribute(ATT_PORTLET_RESPONSE,res);
            request.setAttribute(ATT_PORTLET_CONFIG,def.getPortletConfig());
            request.setAttribute(ATT_PORTLET, portlet);
            request.setAttribute(ATT_METHOD,VAL_METHOD_ACTION);
            RequestDispatcher dispatcher = cont.getRequestDispatcher(WBPORTLET_SERVLET);
            dispatcher.include(request, response);        
//            portlet.processAction(req,res);
        }catch(Exception e)
        {
            log(e);
        }finally
        {
            request.removeAttribute(ATT_PORTLET);
            request.removeAttribute(ATT_PORTLET_REQUEST);
            request.removeAttribute(ATT_PORTLET_RESPONSE);
            request.removeAttribute(ATT_PORTLET_CONFIG);
            request.removeAttribute(ATT_METHOD);
        }
    }       
    
//    /**
//     * Getter for property log.
//     * @return Value of property log.
//     */
//    public PrintWriter getLog()
//    {
//        return SWBUtils.AFUtils.getInstance().getLog();
//    }
    
    public WBPortletDefinition getPortletDefinition(String site, String id)
    {
        Hashtable map=(Hashtable)portletDefinitions.get(site);
        if(map!=null)
        {
            return (WBPortletDefinition)map.get(id);
        }
        return null;
    }
    
    public void removePortletDefinition(String site, String id)
    {
        Hashtable map=(Hashtable)portletDefinitions.get(site);
        if(map!=null)
        {
            map.remove(id);
        }
    }
    
    public void addPortletDefinition(WBPortletDefinition def)
    {
        if(def==null)return;
        Hashtable map=(Hashtable)portletDefinitions.get(def.getSite());
        if(map==null)
        {
            map=new Hashtable();
            portletDefinitions.put(def.getSite(), map);
        }
        map.put(new Long(def.getId()), def);        
    }
    
    public void log(Throwable e)
    {
        SWBUtils.getLogger(this.getClass()).error(e);
    }
    
    public void log(String msg)
    {
        SWBUtils.getLogger(this.getClass()).debug(msg);
        //AFUtils.getInstance().log(msg);
    }
    
    public void log(String msg, Throwable e)
    {
        SWBUtils.getLogger(this.getClass()).error(msg,e);
        //AFUtils.getInstance().log(e,msg);
    }
    
}
