/**
* SemanticWebBuilder Process (SWB Process) es una plataforma para la gestión de procesos de negocio mediante el uso de 
* tecnología semántica, que permite el modelado, configuración, ejecución y monitoreo de los procesos de negocio
* de una organización, así como el desarrollo de componentes y aplicaciones orientadas a la gestión de procesos.
* 
* Mediante el uso de tecnología semántica, SemanticWebBuilder Process puede generar contextos de información
* alrededor de algún tema de interés o bien integrar información y aplicaciones de diferentes fuentes asociadas a
* un proceso de negocio, donde a la información se le asigna un significado, de forma que pueda ser interpretada
* y procesada por personas y/o sistemas. SemanticWebBuilder Process es una creación original del Fondo de 
* Información y Documentación para la Industria INFOTEC.
* 
* INFOTEC pone a su disposición la herramienta SemanticWebBuilder Process a través de su licenciamiento abierto 
* al público (‘open source’), en virtud del cual, usted podrá usarlo en las mismas condiciones con que INFOTEC 
* lo ha diseñado y puesto a su disposición; aprender de él; distribuirlo a terceros; acceder a su código fuente,
* modificarlo y combinarlo (o enlazarlo) con otro software. Todo lo anterior de conformidad con los términos y 
* condiciones de la LICENCIA ABIERTA AL PÚBLICO que otorga INFOTEC para la utilización de SemanticWebBuilder Process. 
* 
* INFOTEC no otorga garantía sobre SemanticWebBuilder Process, de ninguna especie y naturaleza, ni implícita ni 
* explícita, siendo usted completamente responsable de la utilización que le dé y asumiendo la totalidad de los 
* riesgos que puedan derivar de la misma. 
* 
* Si usted tiene cualquier duda o comentario sobre SemanticWebBuilder Process, INFOTEC pone a su disposición la
* siguiente dirección electrónica: 
*  http://www.semanticwebbuilder.org.mx
**/

package org.semanticwb.process.resources.taskinbox;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.Iterator;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.*;
import org.semanticwb.Logger;
import org.semanticwb.SWBPortal;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.RoleRef;
import org.semanticwb.model.SWBComparator;
import org.semanticwb.model.User;
import org.semanticwb.model.WebSite;
import org.semanticwb.portal.api.*;
import org.semanticwb.process.model.FlowNodeInstance;
import org.semanticwb.process.model.Process;
import org.semanticwb.process.model.ProcessInstance;
import org.semanticwb.process.model.UserTask;
import org.semanticwb.process.resources.controlpanel.ControlPanelResource;

/***
 * Recurso Bandeja de Tareas de Usuario.
 * @author Hasdai Pacheco {haxdai@gmail.com}
 */
public class UserTaskInboxResource extends org.semanticwb.process.resources.taskinbox.base.UserTaskInboxResourceBase 
{
    private static Logger log = SWBUtils.getLogger(ControlPanelResource.class);
    public static final int SORT_DATE = 1;
    public static final int SORT_NAME = 2;
    public static final int STATUS_ALL = -1;
    private static final String paramCatalog = "idCol|nameCol|pnameCol|sdateCol|edateCol|actionsCol";
    private Comparator taskNameComparator = new Comparator() {
        String lang = "es";
        public void Comparator (String lng) {
            lang = lng;
        }

        public int compare(Object t, Object t1) {
            return ((FlowNodeInstance)t).getFlowNodeType().getDisplayTitle(lang).compareTo(((FlowNodeInstance)t1).getFlowNodeType().getDisplayTitle(lang));
        }
    };
    /*private Comparator taskPriorityComparator = new Comparator() {
        String lang = "es";
        
        public int compare(Object t, Object t1) {
            int it1 = ((FlowNodeInstance)t).getFlowNodeType().ge
            int it2 = ((ProcessInstance)t1).getPriority();
            int ret = 0;

            if (it1 > it2) ret = 1;
            if (it1 < it2) ret = -1;
            return ret;
        }
    };*/

    public UserTaskInboxResource()
    {
    }

   /**
   * Construye una nueva instancia de un UserTaskInboxResource dado un SemanticObject
   * @param base El SemanticObject con las propiedades para el UserTaskInboxResource
   */
    public UserTaskInboxResource(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    @Override
    public void processRequest(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        String mode = paramRequest.getMode();
        if (mode.equals("config")) {
            doConfig(request, response, paramRequest);
        } else {
            super.processRequest(request, response, paramRequest);
        }
    }

    @Override
    public void processAction(HttpServletRequest request, SWBActionResponse response) throws SWBResourceException, IOException {
        String action = response.getAction();
        if (action.equals("setDisplay")) {
            Enumeration params = request.getParameterNames();
            String dCols = "";
            while(params.hasMoreElements()) {
                String param = (String)params.nextElement();
                if (paramCatalog.contains(param)) {
                    dCols += param;
                    if (params.hasMoreElements()) {
                        dCols += "|";
                    }
                }
            }
            setDisplayCols(dCols+"|actionsCol");
            response.setMode(response.Mode_VIEW);
        } else {
            super.processAction(request, response);
        }
    }

    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        String jsp = SWBPortal.getWebWorkPath() + "/models/" + paramRequest.getWebPage().getWebSiteId() + "/jsp/process/taskInbox/userTaskInbox.jsp";
        RequestDispatcher rd = request.getRequestDispatcher(jsp);

        if (getDisplayCols() == null || getDisplayCols().trim().equals("")) {
            setDisplayCols("idCol|pnameCol|nameCol|sdateCol|edateCol|actionsCol");
        }

        try {
            request.setAttribute("paramRequest", paramRequest);
            request.setAttribute("instances", getUserTaskInstances(request, paramRequest));
            request.setAttribute("displayCols", getDisplayCols());
            request.setAttribute("statusWp", getDisplayMapWp());
            rd.include(request, response);
        } catch (Exception e) {
            log.error("Error including jsp in view mode", e);
        }
    }

    public void doConfig(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        String jsp = SWBPortal.getWebWorkPath() + "/models/" + paramRequest.getWebPage().getWebSiteId() + "/jsp/process/taskInbox/userTaskInboxConfig.jsp";
        RequestDispatcher rd = request.getRequestDispatcher(jsp);
        try {
            request.setAttribute("paramRequest", paramRequest);
            request.setAttribute("displayCols", getDisplayCols());
            rd.include(request, response);
        } catch (Exception e) {
            log.error("Error including JSP in config mode", e);
        }
    }

    /***
     * Obtiene las instancias de las tareas de usuario en el sitio de procesos
     * ordenadas y filtradas de acuerdo a los criterios especificados.
     * @param request Objeto HttpServletRequest
     * @param paramRequest Objeto SWBParamRequest
     * @return Lista de instancias de tareas de usuario filtradas y ordenadas.
     */
    private ArrayList<FlowNodeInstance> getUserTaskInstances(HttpServletRequest request, SWBParamRequest paramRequest) {
        ArrayList<FlowNodeInstance> t_instances = new ArrayList<FlowNodeInstance>();
        WebSite site = paramRequest.getWebPage().getWebSite();
        User user = paramRequest.getUser();
        String sortType = request.getParameter("sort");
        int itemsPerPage = getItemsPerPage();
        int statusFilter = STATUS_ALL;
        Process p = null;
        int page = 1;

        if (sortType == null || sortType.trim().equals("")) {
            sortType = "date";
        } else {
            sortType = sortType.trim();
        }

        if (request.getParameter("page") != null && !request.getParameter("page").trim().equals("")) {
            page = Integer.valueOf(request.getParameter("page"));
            if (page < 0) page = 1;
        }

        if (itemsPerPage < 5) itemsPerPage = 5;

        if (request.getParameter("sFilter") != null && !request.getParameter("sFilter").trim().equals("")) {
            statusFilter = Integer.valueOf(request.getParameter("sFilter"));
        }

        if (request.getParameter("pFilter") != null && !request.getParameter("pFilter").trim().equals("")) {
            p = Process.ClassMgr.getProcess(request.getParameter("pFilter"), site);
        }
        
        Iterator<Process> processes = Process.ClassMgr.listProcesses(site);
        while (processes.hasNext()) {
            Process process = processes.next();
            Iterator<ProcessInstance> processInstances = process.listProcessInstances();
            while (processInstances.hasNext()) {
                ProcessInstance processInstance = processInstances.next();
                Iterator<FlowNodeInstance> nodeInstances = processInstance.listAllFlowNodeInstance();
                while (nodeInstances.hasNext()) {
                    FlowNodeInstance flowNodeInstance = nodeInstances.next();
                    if (flowNodeInstance.getFlowNodeType() instanceof UserTask) {
                        UserTask utask = (UserTask) flowNodeInstance.getFlowNodeType();
                        Iterator<RoleRef> roles = utask.listRoleRefs();
                        while (roles.hasNext()) {
                            RoleRef roleRef = roles.next();
                            if (user.hasRole(roleRef.getRole())) {
                                if (statusFilter > 0) {
                                    if (p != null) {
                                        if (flowNodeInstance.getStatus() == statusFilter && utask.getProcess().getURI().equals(p.getURI())) {
                                            t_instances.add(flowNodeInstance);
                                        }
                                    } else {
                                        if (flowNodeInstance.getStatus() == statusFilter) {
                                            t_instances.add(flowNodeInstance);
                                        }
                                    }
                                } else if (p != null) {
                                    if (utask.getProcess().getURI().equals(p.getURI())) {
                                        t_instances.add(flowNodeInstance);
                                    }
                                } else {
                                    t_instances.add(flowNodeInstance);
                                }
                                break;
                            }
                        }
                    }
                }
            }
        }

        Iterator<FlowNodeInstance> it_ins = null;
        if (sortType.equals("date")) {
            it_ins = SWBComparator.sortByCreated(t_instances.iterator());
        } else if (sortType.equals("name")) {
            Collections.sort(t_instances, taskNameComparator);
            it_ins = t_instances.iterator();
        } 
//        else if (sortType.equals("priority")) {
//            Collections.sort(t_instances, processPriorityComparator);
//            it_ins = t_instances.iterator();
//        }

        if (it_ins != null) {
            t_instances = new ArrayList<FlowNodeInstance>();
            while (it_ins.hasNext()) {
                FlowNodeInstance nodeInstance = it_ins.next();
                t_instances.add(nodeInstance);
            }
        }

        int maxPages = 1;
        if (t_instances.size() >= itemsPerPage) {
            maxPages = (int)Math.ceil((double)t_instances.size() / itemsPerPage);
        }
        if (page > maxPages) page = maxPages;

        int sIndex = (page - 1) * itemsPerPage;
        if (t_instances.size() > itemsPerPage && sIndex >= t_instances.size() - 1) {
            sIndex = t_instances.size() - itemsPerPage;
        }

        int eIndex = sIndex + itemsPerPage;
        if (eIndex >= t_instances.size()) eIndex = t_instances.size();

        request.setAttribute("maxPages", maxPages);
        ArrayList<FlowNodeInstance> instances = new ArrayList<FlowNodeInstance>();
        for (int i = sIndex; i < eIndex; i++) {
            FlowNodeInstance instance = t_instances.get(i);
            instances.add(instance);
        }
        return instances;
    }
}
