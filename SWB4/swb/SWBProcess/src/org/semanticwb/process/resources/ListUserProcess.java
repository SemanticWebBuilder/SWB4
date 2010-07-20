/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.process.resources;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.semanticwb.model.User;
import org.semanticwb.model.WebPage;
import org.semanticwb.portal.api.GenericResource;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceException;
import org.semanticwb.process.model.FlowNodeInstance;
import org.semanticwb.process.model.Instance;
import org.semanticwb.process.model.ProcessInstance;
import org.semanticwb.process.model.ProcessSite;
import org.semanticwb.process.model.Process;
import org.semanticwb.process.model.SWBProcessMgr;

/**
 *
 * @author juan.fernandez
 */
public class ListUserProcess extends GenericResource {

    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException
    {

        PrintWriter out = response.getWriter();
        User user = paramRequest.getUser();
        ProcessSite psite = (ProcessSite)paramRequest.getWebPage().getWebSite();
        WebPage wp = paramRequest.getWebPage();
        //out.println("<h2>"+psite.getDisplayTitle(user.getLanguage())+"</h2>");
	out.println("<p class=\"tit1\">Procesos</p>");
	out.println("<ul class=\"tarea\">");

        int cuantos = 0;
        String styleclass = "";
        Iterator<Process> itpro = psite.listProcesses();
        while (itpro.hasNext()) {
            Process process = itpro.next();
            Iterator<ProcessInstance> itprocins = SWBProcessMgr.getActiveProcessInstance(psite, process).iterator();
            while (itprocins.hasNext()) {
                ProcessInstance procins = itprocins.next();
                List<FlowNodeInstance> lfnins = SWBProcessMgr.getUserTaskInstances(procins, user);
                if(lfnins.size()>0)
                {
                    cuantos++;
                    styleclass = "t1";
                    WebPage ppage=process.getProcessWebPage();
                    if(ppage!=null)
                    {
                        if(wp.getURI().equals(ppage.getURI())) styleclass = "t2-sel";
                        out.println("<li class=\"t1\">"+process.getDisplayTitle(user.getLanguage())+"(<a href=\""+ppage.getUrl()+"\">"+lfnins.size()+"</a>)</li>");
                    }
                }
            }
            
                
        }
        if(cuantos==0)
            out.println("<li class=\"t1\">No hay tareas pendientes</li>");
//        out.println("<li class=\"t1 \"><a href=\"#\">Todas</a></li>");
//        out.println("<li class=\"t2-sel\"><a href=\"#\">Pendientes</a></li>");
//        out.println("<li class=\"t3\"><a href=\"#\">Terminados</a></li>");
//        out.println("<li class=\"t4\"><a href=\"#\">Cerrados</a></li>");
        out.println("</ul>");
    }



}
