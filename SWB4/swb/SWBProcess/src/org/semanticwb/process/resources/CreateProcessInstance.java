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
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.semanticwb.model.*;
import org.semanticwb.portal.api.*;
import org.semanticwb.process.model.Process;
import org.semanticwb.process.model.*;

/**
 *
 * @author juan.fernandez
 */
public class CreateProcessInstance extends GenericAdmResource{ 

    public void doShowMenu(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        User user = paramRequest.getUser();
        PrintWriter out = response.getWriter();
        Map<String, ArrayList<Process>> groups = new TreeMap<String, ArrayList<Process>>();
        ArrayList<Process> processes = new ArrayList<Process>();
        boolean showGroups = false;
        
        String lang = "es";
        if (paramRequest.getUser() != null && paramRequest.getUser().getLanguage()!=null) lang = paramRequest.getUser().getLanguage();
        
        if (!getResourceBase().getAttribute("showGroups", "").equals("")) {
            showGroups = true;
        }
        
        ArrayList<Process> pccs = null;
        //Obtener los eventos de inicio
        Iterator<StartEvent> startEvents = StartEvent.ClassMgr.listStartEvents(paramRequest.getWebPage().getWebSite());            
        while(startEvents.hasNext()) {
            StartEvent sevt = startEvents.next();
            //Si el usuario tiene permisos en el evento
            if (user.haveAccess(sevt)) {
                Process itp = sevt.getProcess();
                //Si el proceso al que pertenece el evento y es válido
                if (itp != null && itp.isValid()) {
                    if (showGroups) {
                        if(itp.getProcessGroup() != null) {
                            String pg = itp.getProcessGroup().getDisplayTitle(lang);

                            //Si ya existe el grupo de procesos en el treemap
                            if(groups.get(pg) != null) {
                                pccs = groups.get(pg);
                                if (!pccs.contains(itp)) {
                                    pccs.add(itp);
                                }
                                groups.put(pg, pccs);
                            } else { //Si no existe el grupo de procesos en el treemap
                                pccs = new ArrayList<Process>();
                                pccs.add(itp);
                                groups.put(pg, pccs);
                            }
                        } 
                    } else {
                        if (!processes.contains(itp)) {
                            processes.add(itp);
                        }
                    }
                }
            }
        }

        SWBResourceURL createUrl = paramRequest.getActionUrl().setAction("CREATE");
        
        if (showGroups) {
            Iterator<String> keys = groups.keySet().iterator();
            if (keys.hasNext()) {
                out.println("<ul id=\"processMenu\">");
                while(keys.hasNext()) {                    
                    String key = keys.next();
                    out.println("  <li id=\"processGroup\">"+key);
                    out.println("    <ul>");
                    Iterator<Process> it_pccs = SWBComparator.sortByDisplayName(groups.get(key).iterator(), lang);
                    while(it_pccs.hasNext()) {
                        Process pcc = it_pccs.next();
                        createUrl.setParameter("prid", pcc.getId());
                        out.println(      "<li><a href=\""+createUrl+"\">"+pcc.getTitle()+"</a></li>");
                    }
                    out.println("    </ul>");
                    out.println("  </li>");                    
                }
                out.println("</ul>");
            }
        } else {
            if (!processes.isEmpty()) {
                out.println("  <li id=\"processMenu\">");
                out.println("    <ul>");
                Iterator<Process> it_pccs = SWBComparator.sortByDisplayName(processes.iterator(), lang);
                while(it_pccs.hasNext()) {
                    Process pcc = it_pccs.next();
                    createUrl.setParameter("prid", pcc.getId());
                    out.println(      "<li><a href=\""+createUrl+"\">"+pcc.getTitle()+"</a></li>");
                }
                out.println("    </ul>");
                out.println("  </li>");
            }
        }
    }
    
    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException
    {
        PrintWriter out = response.getWriter();
        SWBResourceURL url =  paramRequest.getActionUrl();
        Process process = null;
        String label = getResourceBase().getAttribute("linkLabel", "");
        String pid = getResourceBase().getAttribute("processId", "");
        User user = paramRequest.getUser();
        
        if (!getResourceBase().getAttribute("asMenu", "").equals("")) {
            doShowMenu(request, response, paramRequest);
        } else {
            if (label.equals("") && paramRequest.getArguments().containsKey("label")) {
                label = (String)paramRequest.getArguments().get("label");
            }

            if (pid.equals("") && paramRequest.getArguments().containsKey("process")) {
                pid = (String)paramRequest.getArguments().get("process");
            }

            if (paramRequest.getWebPage() instanceof WrapperProcessWebPage) {
                process = ((WrapperProcessWebPage)paramRequest.getWebPage()).getProcess();
            } else if (!pid.trim().equals("")) {
                process = Process.ClassMgr.getProcess(pid, paramRequest.getWebPage().getWebSite());
            }

            if (process != null) {
                boolean canStart = false;
                Iterator<StartEvent> startEvents = StartEvent.ClassMgr.listStartEventByContainer(process);
                while(startEvents.hasNext()) {
                    StartEvent sevt = startEvents.next();
                    if (user.haveAccess(sevt) && process.isValid()) {
                        canStart = true;
                    }
                }

                if (canStart) {
                    url.setParameter("prid", process.getId());
                    url.setAction("CREATE");
                    out.println("<a href=\""+url.toString()+"\">"+label+"</a>");
                }
            }
        }
    }

    @Override
    public void processAction(HttpServletRequest request, SWBActionResponse response) throws SWBResourceException, IOException {
        WebPage wp = response.getWebPage();
        User user=response.getUser();
        String act = response.getAction();
        ProcessInstance inst = null;
        
        if (act.equals("CREATE")) {
            String pid = request.getParameter("prid");
            Process process = Process.ClassMgr.getProcess(pid, wp.getWebSite());
            
            if (process != null) {
                inst = SWBProcessMgr.createSynchProcessInstance(process, user);
                List<FlowNodeInstance> arr = SWBProcessMgr.getActiveUserTaskInstances(inst,response.getUser());                        
                if (arr.size() > 0) {
                    response.sendRedirect(arr.get(0).getUserTaskUrl());
                    return;
                }     
            }
            
            String url=process.getProcessWebPage().getUrl();
            ResourceType rtype=ResourceType.ClassMgr.getResourceType("ProcessTaskInbox", wp.getWebSite());

            if (rtype != null) {
                Resource res=rtype.getResource();
                if(res!=null)
                {
                    Resourceable resable=res.getResourceable();
                    if(resable instanceof WebPage)
                    {
                        url=((WebPage)resable).getUrl();
                    }
                }
            }
            if (inst != null) {
                request.getSession(true).setAttribute("msg", "OK"+inst.getId());
            }
            response.sendRedirect(url);
        }
    }
}