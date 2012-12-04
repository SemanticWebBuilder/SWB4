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
package org.semanticwb.process.resources;

import java.io.IOException;
import java.io.PrintWriter;
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
	out.println("<p class=\"tit1\">"+paramRequest.getLocaleString("lblProcess")+"</p>");
	out.println("<ul class=\"tarea\">");

        int cuantos = 0;
        int numpen=0;
        String styleclass = "";
        Iterator<Process> itpro = psite.listProcesses();
        while (itpro.hasNext()) {
            numpen=0;
            styleclass = "t1";
            Process process = itpro.next();
            WebPage ppage= process.getProcessWebPage();
            Iterator<ProcessInstance> itprocins = SWBProcessMgr.getActiveProcessInstance(psite, process).iterator();
            while (itprocins.hasNext()) {
                ProcessInstance procins = itprocins.next();
                List<FlowNodeInstance> lfnins = SWBProcessMgr.getActiveUserTaskInstances(procins, user);
                if(lfnins.size()>0)
                {
                    numpen++;
                    cuantos++;
                }
            }
            if(numpen>0&&ppage!=null)
            {
                if(wp.getURI().equals(ppage.getURI())) styleclass = "t2-sel";
                out.println("<li class=\""+styleclass+"\">"+process.getDisplayTitle(user.getLanguage())+"(<a href=\""+ppage.getUrl()+"\">"+numpen+"</a>)</li>");
            }
        }
        if(cuantos==0)
            out.println("<li class=\"t1\">"+paramRequest.getLocaleString("lblNoActivities")+"No hay tareas pendientes"+"</li>");
        out.println("</ul>");
    }
}
