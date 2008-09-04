
/*
 * WBResourceWindow.java
 *
 * Created on 22 de marzo de 2006, 06:30 PM
 */

package org.semanticwb.portal.resources;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Locale;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.semanticwb.SWBException;
import org.semanticwb.SWBPlatform;
import org.semanticwb.servlet.SWBHttpServletResponseWrapper;

/**
 *
 * @author Javier Solis Gonzalez
 */
public class SWBResourceWindowRender
{
    SWBResource resource=null;
    
    /** Creates a new instance of WBResourceWindow */
    public SWBResourceWindowRender(SWBResource resource)
    {
        this.resource=resource;
    }
    
    
    public void render(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramsRequest) throws SWBException, IOException 
    {
        //System.out.println("id:"+paramsRequest.getResourceBase().getId()+" "+resource.getClass()+" "+(resource instanceof WBResourceWindow));
        if(resource instanceof SWBResourceWindow 
           && ((SWBResourceWindow)resource).windowSupport(request,paramsRequest)
        )
        {
            //System.out.println("--> id:"+paramsRequest.getResourceBase().getId());
            Locale locale=new Locale(paramsRequest.getUser().getLanguage().getId());
            HttpServletResponse res=new SWBHttpServletResponseWrapper(response);
            resource.render(request, res, paramsRequest);
            String content=res.toString();                
            window(request,content,response.getWriter(),paramsRequest);
        }else
        {
            resource.render(request, response, paramsRequest);
        }        
    }
    
    public void window(HttpServletRequest request, String content, PrintWriter out, SWBParamRequest paramsRequest) throws SWBException, IOException
    {
        String title=((SWBResourceWindow)resource).getTitle(request, paramsRequest);
        
//        out.println("<table border='0' cellpadding='0' cellspacing='2' width='100%' height='100%'>");
//        out.println("  <tr><td bgcolor='#aaccFF' height='12'>");
//        out.println("    <table border='0' cellpadding='0' cellspacing='0' width='100%' height='10'>");
//        out.println("      <tr><td>&nbsp;<font face='arial'><i><b>"+title+"</b></font></td>");
//        out.println("	     <td align='right'>");
//        out.println("	       <font face='arial' size='-3'>");

        String id=paramsRequest.getResourceBase().getPortletType().getId()+"_"+paramsRequest.getResourceBase().getId()+"_"+paramsRequest.getResourceBase().getWebSite().getId();
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
    
    private void printMode(PrintWriter out,SWBParamRequest paramsRequest,String mode)
    {
        SWBResourceURL url=paramsRequest.getRenderUrl();
        String title=mode;
        String img=null;
        
        String web=SWBPlatform.getContextPath();
        
        if(mode.equals(paramsRequest.Mode_VIEW))
        {
            title="Regresar";
            img=web+"wbadmin/images/portlet/back.png";
        }else if(mode.equals(paramsRequest.Mode_EDIT))
        {
            title="Preferencias";
            img=web+"wbadmin/images/portlet/edit.png";
        }else if(mode.equals(paramsRequest.Mode_HELP))
        {
            title="Ayuda";
            img=web+"wbadmin/images/portlet/help.png";
        }else if(mode.equals(paramsRequest.Mode_ADMIN))
        {
            title="Configuraci�n";
            img=web+"wbadmin/images/portlet/configuration.png";
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
    
    private void printWindowState(PrintWriter out,SWBParamRequest paramsRequest,String state)
    {
        SWBResourceURL url=paramsRequest.getRenderUrl();
        String title=state;
        String img=null;
        String web=SWBPlatform.getContextPath();
        if(state.equals(paramsRequest.WinState_NORMAL))
        {
            title="Restaurar";
            img=web+"wbadmin/images/portlet/restore.png";
        }else if(state.equals(paramsRequest.WinState_MAXIMIZED))
        {
            title="Maximizar";
            img=web+"wbadmin/images/portlet/maximize.png";
        }else if(state.equals(paramsRequest.WinState_MINIMIZED))
        {
            title="Minimizar";
            img=web+"wbadmin/images/portlet/minimize.png";
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
