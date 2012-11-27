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

import java.io.InputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Locale;
import javax.portlet.*;
import javax.servlet.ServletContext;
import org.semanticwb.Logger;
import org.semanticwb.SWBException;
import org.semanticwb.SWBUtils;
import org.semanticwb.portal.api.*;
import org.semanticwb.model.*;
import org.semanticwb.portal.portlet.factory.WBFactoryMgr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


/**
 *
 * @author Javier Solis Gonzalez
 */
public class PortletResource extends GenericResource
{
    private static Logger log=SWBUtils.getLogger(PortletResource.class);

    boolean initialized=false;
    //Portlet portlet;
    WBPortletDefinition portletDefinition;
    
    /** Creates a new instance of PortletResource */
    public PortletResource()
    {
    }

    @Override
    public void setResourceBase(Resource base) throws SWBResourceException
    {
        super.setResourceBase(base);
        initialized=false;
    }
    
    
    public void init(HttpServletRequest request) throws SWBResourceException
    {
        try
        {
            //System.out.println("PortletResource.init()");
            WBPortletContainer container=WBFactoryMgr.getPortletContainer();
            if(container==null)
            {
                ServletContext context=request.getSession().getServletContext();
                container=new WBPortletContainerImp(context);
                WBFactoryMgr.setPortletContainer(container);
            }
            
            String portletName=getResourceBase().getAttribute("portlet-name");
            String contextName=getResourceBase().getResourceType().getId();
            System.out.println("portletName:"+portletName);
            System.out.println("contextName:"+contextName);
            if(contextName!=null && portletName!=null)
            {
                initialized=true;
                portletDefinition=new WBPortletDefinitionImp(getResourceBase().getId(),getResourceBase().getWebSiteId(),contextName,portletName);
                Portlet portlet=container.loadPortlet(portletDefinition, request);    
            }
//            if(portlet!=null)
//            {
//                portlet.init(portletDefinition.getPortletConfig());
//            }
            
//            String scontext=getResourceBase().getAttribute("portlet-gui");
//            String classname=getResourceBase().getAttribute("class");
//            ServletContext context=request.getSession().getServletContext().getContext(scontext);
//            Class cls=context.getClass().forName(classname);
//            //Class cls=Thread.currentThread().getContextClassLoader().loadClass(classname);
//            //Class cls=Class.forName(classname);
//            portlet=(Portlet)cls.newInstance();
//            PortletConfig config=new PortletConfigImp(getResourceBase());
//            portlet.init(config);
        }catch(Throwable e){log.error(e);}
    }
    
    
    @Override
    public void render(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramsRequest) throws SWBResourceException, IOException
    {
        if(paramsRequest.getMode().equals(paramsRequest.Mode_ADMIN))
        {
            if(!paramsRequest.WinState_MINIMIZED.equals(paramsRequest.getWindowState()))
            {
                doAdmin(request,response,paramsRequest);
            }
        }else
        {
            if(!initialized)init(request);
            try
            {
//                RenderRequest req=new PortletRequestImp(request, paramsRequest,false);
//                RenderResponse res=new RenderResponseImp(response, paramsRequest);
//                portlet.render(req, res);
                
                Locale locale=new Locale(paramsRequest.getUser().getLanguage());
                try
                {
                    paramsRequest.setWindowTitle(portletDefinition.getResourceBundle(locale).getString("javax.portlet.title"));
                }catch(Exception e){log.error(e);}
                WBFactoryMgr.getPortletContainer().invoke_render(request, response, paramsRequest, portletDefinition);
            }catch(Exception e)
            {
                throw new SWBResourceException("Error:PortletResource.render",e);
            }
        }
    }
    
    @Override
    public void doAdmin(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramsRequest) throws SWBResourceException, IOException
    {    
        PrintWriter out=response.getWriter();
        String portletname=request.getParameter("portlet-name");
        if(portletname!=null)
        {
            getResourceBase().setAttribute("portlet-name",portletname);
            try {
                getResourceBase().updateAttributesToDB();
            } catch (SWBException e) {
                log.error(e);
            }
        }else
        {
            portletname=getResourceBase().getAttribute("portlet-name");
        }

        out.println("<P class=\"box\">");
        
        out.println("<form method=\"POST\" action=\""+paramsRequest.getRenderUrl()+"\">");
        out.println("<table width=\"100%\" border=\"0\" cellpadding=\"10\" cellspacing=\"0\" bgcolor=\"#FFFFFF\">");
        out.println("<tr><td align=\"right\" class=\"datos\">Portlet:</td><td class=\"valores\" align=\"left\">");
        out.println("<select name=\"portlet-name\" size=\"1\">");

        String contextName=getResourceBase().getResourceType().getId();
        WBPortletContainerImp container=(WBPortletContainerImp)WBFactoryMgr.getPortletContainer();
        if(container==null)
        {
            ServletContext context=request.getSession().getServletContext();
            container=new WBPortletContainerImp(context);
            WBFactoryMgr.setPortletContainer(container);
        }

        ServletContext context=container.getContext(contextName);
        if(context!=null)
        {
            InputStream in = context.getResourceAsStream("/WEB-INF/portlet.xml");
            if(in!=null)
            {
                Document doc=SWBUtils.XML.xmlToDom(in);
                NodeList nl=doc.getElementsByTagName("portlet");
                for(int x=0;x<nl.getLength();x++)
                {
                    Element root=(Element)nl.item(x);
                    String name=getTagValue(root,"portlet-name");
                    String selected="";
                    if(name.equals(portletname))selected="selected";
                    out.println("<option value=\""+name+"\" "+selected+">"+name+"</option>");
                }
            }
        }else
        {
            //TODO: No se encontro contexto
        }
        out.println("</select>");    
        out.println("</td></tr>");
        String save="Guardar";
        out.println("<tr><td colspan=\"2\" align=\"center\"><input type=\"submit\" value=\""+save+"\" class=\"boton\"></td></tr>");
        out.println("</table>");
        out.println("</form>");
        out.println("</p>");
    }
    
    
    private String getTagValue(Element root, String tag)
    {
        NodeList nl=root.getElementsByTagName(tag);
        if(nl.getLength()>0)
        {
            Node nd=nl.item(0);
            if(nd.hasChildNodes())
            {
                return nd.getFirstChild().getNodeValue();
            }
        }
        return null;
    }
    
    @Override
    public void processAction(HttpServletRequest request, SWBActionResponse response) throws SWBResourceException, IOException
    {
        if(!initialized)init(request);
        try
        {
            WBFactoryMgr.getPortletContainer().invoke_action(request, response, portletDefinition);
        }catch(Exception e)
        {
            throw new SWBResourceException("Error:PortletResource.render",e);
        }        
    }
    
    @Override
    public String[] getModes(HttpServletRequest request, SWBParamRequest paramRequest) throws SWBResourceException, java.io.IOException
    {
        if(portletDefinition!=null)
        {
            Object aux[]=portletDefinition.getPortletModesSupport().getModes().toArray();
            String ret[]=new String[aux.length];
            for(int x=0;x<aux.length;x++)
            {
                ret[x]=(String)aux[x];
            }
            return ret;
        }else
        {
            return new String[]{paramRequest.Mode_VIEW,paramRequest.Mode_ADMIN};
        }
    }
    
//    public String getTitle(HttpServletRequest request, WBParamRequest paramRequest) throws AFException, java.io.IOException
//    {
//        return paramRequest.getResourceBase().getTitle();
//    }
    
//    public String[] getWindowStates(HttpServletRequest request, WBParamRequest paramRequest) throws AFException, java.io.IOException
//    {
//        return new String[]{paramRequest.WinState_MINIMIZED,paramRequest.WinState_MAXIMIZED, paramRequest.WinState_NORMAL};
//    }

    @Override
    public boolean windowSupport(HttpServletRequest request, SWBParamRequest paramRequest) throws SWBResourceException, java.io.IOException
    {
        if(paramRequest.getAdminTopic().getId().equals("WBAd_sysi_TopicsContents"))return false;
        return true;
    }    
    
    
}

