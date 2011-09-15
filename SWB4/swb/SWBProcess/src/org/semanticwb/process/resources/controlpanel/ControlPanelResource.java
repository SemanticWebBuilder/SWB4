package org.semanticwb.process.resources.controlpanel;

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
import org.semanticwb.model.SWBComparator;
import org.semanticwb.model.User;
import org.semanticwb.model.WebSite;
import org.semanticwb.portal.api.*;
import org.semanticwb.process.model.ProcessInstance;
import org.semanticwb.process.model.Process;
import org.semanticwb.process.model.ProcessGroup;
import org.semanticwb.process.model.ProcessSite;
import org.semanticwb.process.model.SWBProcessMgr;

/***
 * Recurso Panel de Control para monitoreo de instancias de procesos.
 * @author Hasdai Pacheco {haxdai@gmail.com}
 */
public class ControlPanelResource extends org.semanticwb.process.resources.controlpanel.base.ControlPanelResourceBase 
{
    private static Logger log = SWBUtils.getLogger(ControlPanelResource.class);
    public static final int SORT_DATE = 1;
    public static final int SORT_NAME = 2;
    public static final int STATUS_ALL = -1;
    private static final String paramCatalog = "idCol|priorityCol|nameCol|sdateCol|edateCol|pendingCol|rolesCol|actionsCol";
    private Comparator processNameComparator = new Comparator() {
        String lang = "es";
        public void Comparator (String lng) {
            lang = lng;
        }

        public int compare(Object t, Object t1) {
            return ((ProcessInstance)t).getProcessType().getDisplayTitle(lang).compareTo(((ProcessInstance)t1).getProcessType().getDisplayTitle(lang));
        }
    };
    private Comparator processPriorityComparator = new Comparator() {
        String lang = "es";
        
        public int compare(Object t, Object t1) {
            int it1 = ((ProcessInstance)t).getPriority();
            int it2 = ((ProcessInstance)t1).getPriority();
            int ret = 0;

            if (it1 > it2) ret = 1;
            if (it1 < it2) ret = -1;
            return ret;
        }
    };

    public ControlPanelResource()
    {
    }

   /**
   * Construye una nueva instancia de un ControlPanelResource dado un SemanticObject
   * @param base El SemanticObject con las propiedades para el ControlPanelResource
   */
    public ControlPanelResource(org.semanticwb.platform.SemanticObject base)
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
        } else if (action.equals("create")) {
            User user = response.getUser();
            String pid = request.getParameter("pid");
            if (pid != null && !pid.trim().equals("")) {
                Process p = Process.ClassMgr.getProcess(pid, response.getWebPage().getWebSite());
                SWBProcessMgr.createProcessInstance(p, user);
            }
            response.setMode(SWBParamRequest.Mode_VIEW);
        } else {
            super.processAction(request, response);
        }
    }

    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        String jsp = SWBPortal.getWebWorkPath() + "/models/" + paramRequest.getWebPage().getWebSiteId() + "/jsp/process/controlPanel/businessControlPanel.jsp";
        RequestDispatcher rd = request.getRequestDispatcher(jsp);

        if (getDisplayCols() == null || getDisplayCols().trim().equals("")) {
            setDisplayCols("idCol|nameCol|sdateCol|edateCol|pendingCol|actionsCol");
        }

        try {
            request.setAttribute("paramRequest", paramRequest);
            request.setAttribute("instances", getProcessInstances(request, paramRequest));
            request.setAttribute("displayCols", getDisplayCols());
            request.setAttribute("statusWp", getDisplayMapWp());
            rd.include(request, response);
        } catch (Exception e) {
            log.error("ControlPanelResource: Error including view JSP", e);
        }
    }
    
    public void doConfig(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        String jsp = SWBPortal.getWebWorkPath() + "/models/" + paramRequest.getWebPage().getWebSiteId() + "/jsp/process/controlPanel/businessControlPanelConfig.jsp";
        RequestDispatcher rd = request.getRequestDispatcher(jsp);
        try {
            request.setAttribute("paramRequest", paramRequest);
            request.setAttribute("displayCols", getDisplayCols());
            rd.include(request, response);
        } catch (Exception e) {
            log.error("ControlPanelResource: Error including config JSP", e);
        }
    }

    /***
     * Obtiene la lista de insancias de los procesos existentes en el
     * sitio Web de procesos aplicando los filtros y ordenamiento indicados.
     * @param request Objeto HttpServletRequest
     * @param paramRequest Objeto SWBParamRequest
     * @return Lista de instancias de procesos filtradas y ordenadas.
     */
    private ArrayList<ProcessInstance> getProcessInstances(HttpServletRequest request, SWBParamRequest paramRequest) {
        ArrayList<ProcessInstance> t_instances = new ArrayList<ProcessInstance>();
        WebSite site = paramRequest.getWebPage().getWebSite();
        ProcessGroup group = null;
        String sortType = request.getParameter("sort");
        int itemsPerPage = getItemsPerPage();
        int statusFilter = STATUS_ALL;
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
        
        if (request.getParameter("gFilter") != null && !request.getParameter("gFilter").trim().equals("")) {
            group = ProcessGroup.ClassMgr.getProcessGroup(request.getParameter("gFilter"), site);
        }

        Iterator<Process> processes = Process.ClassMgr.listProcesses(site);
        if (processes != null && processes.hasNext()) {
            while(processes.hasNext()) {
                Process process = processes.next();
                t_instances.addAll(_getProcessInstances((ProcessSite)site, group, process, statusFilter));
            }
        }

        Iterator<ProcessInstance> it_ins = null;
        if (sortType.equals("date")) {
            it_ins = SWBComparator.sortByCreated(t_instances.iterator());
        } else if (sortType.equals("name")) {
            Collections.sort(t_instances, processNameComparator);
            it_ins = t_instances.iterator();
        } else if (sortType.equals("priority")) {
            Collections.sort(t_instances, processPriorityComparator);
            it_ins = t_instances.iterator();
        }        

        if (it_ins != null) {
            t_instances = new ArrayList<ProcessInstance>();
            while (it_ins.hasNext()) {
                ProcessInstance processInstance = it_ins.next();
                t_instances.add(processInstance);
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
        ArrayList<ProcessInstance> instances = new ArrayList<ProcessInstance>();
        for (int i = sIndex; i < eIndex; i++) {
            ProcessInstance instance = t_instances.get(i);
            instances.add(instance);
        }
        return instances;
    }

    /***
     * Obtiene la lista de instancias de procesos de un sitio filtradas por
     * grupo y/o estado.
     * @param site Sitio Web de Procesos del cual se listarán los procesos.
     * @param group Grupo al que deberán pertenecer los procesos.
     * @param process Tipo de proceso del cual se recuperarán las instancias.
     * @param status Estado de las instancias que serán listadas.
     * @return Lista de instancias de procesos filtradas por grupo y/o estado.
     */
    ArrayList<ProcessInstance> _getProcessInstances(ProcessSite site, ProcessGroup group, Process process, int status) {
        ArrayList<ProcessInstance> instances = new ArrayList<ProcessInstance>();
        Iterator<ProcessInstance> it=process.listProcessInstances();
        while (it.hasNext()) {
            ProcessInstance processInstance = (ProcessInstance)it.next();
            if (status >= 0) {
                if (group != null) {
                    if (processInstance.getStatus() == status && process.getProcessGroup().getURI().equals(group.getURI())) {
                        instances.add(processInstance);
                    }
                } else {
                    if (processInstance.getStatus() == status) {
                        instances.add(processInstance);
                    }
                }
            } else if (group != null) {
                if (process.getProcessGroup().getURI().equals(group.getURI())) {
                    instances.add(processInstance);
                }
            } else {
                instances.add(processInstance);
            }
        }
        return instances;
    }
}