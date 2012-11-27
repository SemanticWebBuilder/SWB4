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

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Locale;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.semanticwb.SWBPlatform;
import org.semanticwb.servlet.SWBHttpServletResponseWrapper;

// TODO: Auto-generated Javadoc
/**
 * The Class SWBResourceWindowRender.
 * 
 * @author Javier Solis Gonzalez
 */
public class SWBResourceWindowRender
{
    
    /** The resource. */
    SWBResource resource=null;
    
    /**
     * Creates a new instance of WBResourceWindow.
     * 
     * @param resource the resource
     */
    public SWBResourceWindowRender(SWBResource resource)
    {
        this.resource=resource;
    }
    
    
    /**
     * Render.
     * 
     * @param request the request
     * @param response the response
     * @param paramsRequest the params request
     * @throws SWBResourceException the sWB resource exception
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public void render(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramsRequest) throws SWBResourceException, IOException 
    {
        //System.out.println("id:"+paramsRequest.getResourceBase().getId()+" "+resource.getClass()+" "+(resource instanceof WBResourceWindow));
        if(resource instanceof SWBResourceWindow 
           && ((SWBResourceWindow)resource).windowSupport(request,paramsRequest)
        )
        {
            //System.out.println("--> id:"+paramsRequest.getResourceBase().getId());
            Locale locale=new Locale(paramsRequest.getUser().getLanguage());
            HttpServletResponse res=new SWBHttpServletResponseWrapper(response);
            resource.render(request, res, paramsRequest);
            String content=res.toString();                
            window(request,content,response.getWriter(),paramsRequest);
        }else
        {
            resource.render(request, response, paramsRequest);
        }        
    }
    
    /**
     * Window.
     * 
     * @param request the request
     * @param content the content
     * @param out the out
     * @param paramsRequest the params request
     * @throws SWBResourceException the sWB resource exception
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public void window(HttpServletRequest request, String content, PrintWriter out, SWBParamRequest paramsRequest) throws SWBResourceException, IOException
    {
        String title=((SWBResourceWindow)resource).getTitle(request, paramsRequest);
        
//        out.println("<table border='0' cellpadding='0' cellspacing='2' width='100%' height='100%'>");
//        out.println("  <tr><td bgcolor='#aaccFF' height='12'>");
//        out.println("    <table border='0' cellpadding='0' cellspacing='0' width='100%' height='10'>");
//        out.println("      <tr><td>&nbsp;<font face='arial'><i><b>"+title+"</b></font></td>");
//        out.println("	     <td align='right'>");
//        out.println("	       <font face='arial' size='-3'>");

        String id=paramsRequest.getResourceBase().getResourceType().getId()+"_"+paramsRequest.getResourceBase().getId()+"_"+paramsRequest.getResourceBase().getWebSite().getId();
        out.println("<div class=\"portlet\" id=\"portlet-window-"+id+"\">");
        out.println("  <div class=\"portlet-topper\">");
        out.println("    <span class=\"portlet-title\">"+title+"</span>");
        out.println("    <div class=\"portlet-icons\" id=\"portlet-icon-bar_"+id+"\">");

        String[] modes=((SWBResourceWindow)resource).getModes(request, paramsRequest);
        if(modes!=null)
        {
            for(int x=0;x<modes.length;x++)
            {
                String mode=modes[x];
                printMode(out,paramsRequest,mode);
            }
            //if(modes.length>0)out.println(" 	         |");
        }

        String[] states=((SWBResourceWindow)resource).getWindowStates(request, paramsRequest);
        if(states!=null)
        {
            for(int x=0;x<states.length;x++)
            {
                String state=states[x];
                printWindowState(out,paramsRequest,state);
            }        
        }
        
//        out.println("          </font>&nbsp;");
//  	  out.println("        </td>");
//        out.println("      </tr>");
//        out.println("    </table>");
//        out.println("  </td></tr>");
//        out.println("  <tr><td valign='top'>");
        
        out.println("    </div>");
        out.println("  </div>");
        out.println("  <div class=\"portlet-content\">");
        out.println("    <div class=\"portlet-content-container\" style=\"\">");
        out.println("      <div>");
        
        out.println(content);
        
//        out.println("  </td></tr>");
//        out.println("</table>");
        
        out.println("      </div>");
        out.println("    </div>");
        out.println("  </div>");
        out.println("</div>");   
    }    
    
    /**
     * Prints the mode.
     * 
     * @param out the out
     * @param paramsRequest the params request
     * @param mode the mode
     */
    private void printMode(PrintWriter out,SWBParamRequest paramsRequest,String mode)
    {
        SWBResourceURL url=paramsRequest.getRenderUrl();
        String title=mode;
        String img=null;
        
        String web=SWBPlatform.getContextPath();
        
        if(mode.equals(paramsRequest.Mode_VIEW))
        {
            title="Regresar";
            img=web+"/swbadmin/images/portlet/back.png";
        }else if(mode.equals(paramsRequest.Mode_EDIT))
        {
            title="Preferencias";
            img=web+"/swbadmin/images/portlet/edit.png";
        }else if(mode.equals(paramsRequest.Mode_HELP))
        {
            title="Ayuda";
            img=web+"/swbadmin/images/portlet/help.png";
        }else if(mode.equals(paramsRequest.Mode_ADMIN))
        {
            title="Configuración";
            img=web+"/swbadmin/images/portlet/configuration.png";
        }
        
        if(url.getMode().equals(mode))
        {
            //out.println("            "+mode);
        }else
        {
            //out.println("            <a href=\""+paramsRequest.getRenderUrl().setMode(mode)+"\">"+mode+"</a>");
            out.print("      <nobr>");
            out.print("<a href=\""+paramsRequest.getRenderUrl().setMode(mode)+"\">");
            if(img==null)
            {
                out.print(title);
            }else
            {
                out.print("<img align=\"absmiddle\" border=\"0\" src=\""+img+"\" title=\""+title+"\" />");
            }
            out.print("</a>");
            out.println("</nobr>");
        }
    }   
    
    /**
     * Prints the window state.
     * 
     * @param out the out
     * @param paramsRequest the params request
     * @param state the state
     */
    private void printWindowState(PrintWriter out,SWBParamRequest paramsRequest,String state)
    {
        SWBResourceURL url=paramsRequest.getRenderUrl();
        String title=state;
        String img=null;
        String web=SWBPlatform.getContextPath();
        if(state.equals(paramsRequest.WinState_NORMAL))
        {
            title="Restaurar";
            img=web+"/swbadmin/images/portlet/restore.png";
        }else if(state.equals(paramsRequest.WinState_MAXIMIZED))
        {
            title="Maximizar";
            img=web+"/swbadmin/images/portlet/maximize.png";
        }else if(state.equals(paramsRequest.WinState_MINIMIZED))
        {
            title="Minimizar";
            img=web+"/swbadmin/images/portlet/minimize.png";
        }
        if(url.getWindowState().equals(state))
        {
            //out.println("            "+st);
        }else
        {
            //out.println("            <a href=\""+paramsRequest.getRenderUrl().setWindowState(state)+"\">"+st+"</a>");
            out.print("      <nobr>");
            out.print("<a href=\""+paramsRequest.getRenderUrl().setWindowState(state)+"\">");
            if(img==null)
            {
                out.print(title);
            }else
            {
                out.print("<img align=\"absmiddle\" border=\"0\" src=\""+img+"\" title=\""+title+"\" />");
            }
            out.print("</a>");
            out.println("</nobr>");
        }
    }     
      
    
}
