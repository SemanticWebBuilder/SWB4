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
import org.semanticwb.SWBUtils;
import org.semanticwb.model.SWBComparator;
import org.semanticwb.model.User;
import org.semanticwb.model.UserGroup;
import org.semanticwb.model.WebSite;
import org.semanticwb.portal.api.*;
import org.semanticwb.process.model.FlowNodeInstance;
import org.semanticwb.process.model.GraphicalElement;
import org.semanticwb.process.model.Instance;
import org.semanticwb.process.model.Lane;
import org.semanticwb.process.model.Pool;
import org.semanticwb.process.model.Process;
import org.semanticwb.process.model.ProcessInstance;
import org.semanticwb.process.model.ProcessSite;
import org.semanticwb.process.model.SWBProcessMgr;
import org.semanticwb.process.model.UserTask;

/***
 * Recurso Bandeja de Tareas de Usuario.
 * @author Hasdai Pacheco {haxdai@gmail.com}
 */
public class UserTaskInboxResource extends org.semanticwb.process.resources.taskinbox.base.UserTaskInboxResourceBase 
{
    private static Logger log = SWBUtils.getLogger(UserTaskInboxResource.class);
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
        } else if (action.equals("CREATE")) {
            User user=response.getUser();
            String pid = request.getParameter("pid");
            ProcessInstance inst = null;
            
            if (pid != null && !pid.trim().equals("")) {
                Process process = Process.ClassMgr.getProcess(pid, response.getWebPage().getWebSite());
                if (process != null) {
                    inst = SWBProcessMgr.createProcessInstance(process, user);
                }
            }
            if (inst != null) {
                request.getSession(true).setAttribute("msg", "OK"+inst.getId());
            }
            response.setMode(response.Mode_VIEW);
        } else if (action.equals("setPageItems")) {
            String ipp = request.getParameter("ipp");
            int itemsPerPage = 0;
            
            if (ipp == null || ipp.trim().equals("")) {
                ipp = "5";
            }
            
            try {
                itemsPerPage = Integer.parseInt(ipp);
            } catch (NumberFormatException e) {
                log.error("UserTaskInboxResource",e);
            }
            setItemsPerPage(itemsPerPage);
            response.setMode(response.Mode_VIEW);
        } else {
            super.processAction(request, response);
        }
    }

    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        String jsp = "/swbadmin/jsp/process/userTaskInbox.jsp";
        if (getViewJSP() != null && !getViewJSP().trim().equals("")) {
            jsp = getViewJSP();
        }
        //String jsp = SWBPortal.getWebWorkPath() + "/models/" + paramRequest.getWebPage().getWebSiteId() + "/jsp/process/taskInbox/userTaskInbox.jsp";

        if (getDisplayCols() == null || getDisplayCols().trim().equals("")) {
            setDisplayCols("idCol|pnameCol|nameCol|sdateCol|edateCol|actionsCol");
        }

        try {
            RequestDispatcher rd = request.getRequestDispatcher(jsp);
            request.setAttribute("paramRequest", paramRequest);
            request.setAttribute("instances", getUserTaskInstances(request, paramRequest));
            request.setAttribute("displayCols", getDisplayCols());
            request.setAttribute("statusWp", getDisplayMapWp());
            request.setAttribute("itemsPerPage", getItemsPerPage());
            request.setAttribute("showPWpLink", isShowProcessWPLink());
            rd.include(request, response);
        } catch (Exception e) {
            log.error("Error including jsp in view mode", e);
        }
    }

    public void doConfig(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        String jsp = "/swbadmin/jsp/process/userTaskInboxConfig.jsp";
        if (getViewJSP() != null && !getViewJSP().trim().equals("")) {
            jsp = getViewJSP();
        }
        //String jsp = SWBPortal.getWebWorkPath() + "/models/" + paramRequest.getWebPage().getWebSiteId() + "/jsp/process/taskInbox/userTaskInboxConfig.jsp";
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
        ArrayList<FlowNodeInstance> unpaged = new ArrayList<FlowNodeInstance>();
        WebSite site = paramRequest.getWebPage().getWebSite();
        User user = paramRequest.getUser();
        String sortType = request.getParameter("sort");
        int itemsPerPage = getItemsPerPage();
        int statusFilter = ProcessInstance.STATUS_PROCESSING;
        Process p = null;
        int page = 1;

        if (request.getParameter("sFilter") != null && !request.getParameter("sFilter").trim().equals("")) {
            statusFilter = Integer.valueOf(request.getParameter("sFilter"));
        }

        if (request.getParameter("pFilter") != null && !request.getParameter("pFilter").trim().equals("")) {
            p = Process.ClassMgr.getProcess(request.getParameter("pFilter"), site);
        }
        
        //Obtener todas las tareas de usuario por el estatus solicitado
        //TODO: Agregar código para cuando solicitan todos los estados
        ArrayList<FlowNodeInstance> userTaskInstances = SWBProcessMgr.getUserTaskInstancesWithStatus((ProcessSite)site, statusFilter, p);//SWBProcessMgr.getUserTaskInstancesWithStatus((ProcessSite)site, statusFilter);
        
        //Iniciar el filtrado
        if (userTaskInstances != null) {
            Iterator<FlowNodeInstance> fnInstances = userTaskInstances.iterator();
            while (fnInstances.hasNext()) {
                FlowNodeInstance flowNodeInstance = fnInstances.next();
                if (!filterUserTaskInstance(flowNodeInstance, user, statusFilter)) {
                    unpaged.add(flowNodeInstance);
                }
            }
        }
        
//        Iterator<Process> processes = Process.ClassMgr.listProcesses(site);
//        while (processes.hasNext()) {
//            Process process = processes.next();
//            if (process.isActive()) {
//                Iterator<ProcessInstance> processInstances = process.listProcessInstances();
//                while (processInstances.hasNext()) {
//                    ProcessInstance processInstance = processInstances.next();
//                    Iterator<FlowNodeInstance> nodeInstances = null;
//
//                    if (isFilterByGroup()) { //Si hay que filtrar por grupo de usuarios
//                        UserGroup iug = processInstance.getOwnerUserGroup();
//                        UserGroup uug = user.getUserGroup();
//
//                        if (iug != null && uug != null) { //Si la instancia y el usuario tienen grupo
//                            if (user.getUserGroup().getURI().equals(processInstance.getOwnerUserGroup().getURI())) { //Si tienen el mismo grupo
//                                nodeInstances = processInstance.listAllFlowNodeInstance();
//                            }
//                        } else if (iug == null && uug == null) { //Si el proceso y el usuario no tienen grupo
//                            nodeInstances = processInstance.listAllFlowNodeInstance();
//                        }
//                    } else { //Si no hay que filtrar por grupo de usuarios
//                        nodeInstances = processInstance.listAllFlowNodeInstance();
//                    }
//
//                    if (nodeInstances != null) {
//                        while (nodeInstances.hasNext()) {
//                            FlowNodeInstance flowNodeInstance = nodeInstances.next();
//                            if (flowNodeInstance.getFlowNodeType() instanceof UserTask) {
//                                UserTask utask = (UserTask) flowNodeInstance.getFlowNodeType();
//                                boolean canAccess = false;
//                                User owner = flowNodeInstance.getAssignedto();
//                                
//                                if (owner != null) { //Tiene propieario
//                                    if (owner.getURI().equals(user.getURI())) {
//                                        canAccess = true;
//                                    }
//                                } else if (user.haveAccess(utask)) { //No tiene propietario
//                                    GraphicalElement parent = utask.getParent();
//                                    if (parent == null || parent instanceof Pool || (parent != null && parent instanceof Lane && user.haveAccess(parent))) {
//                                        canAccess = true;
//                                    }
//                                }
//                                
//                                if (canAccess) {
//                                    if (statusFilter > 0) {
//                                        if (p != null) {
//                                            if (flowNodeInstance.getStatus() == statusFilter && utask.getProcess().getURI().equals(p.getURI())) {
//                                                t_instances.add(flowNodeInstance);
//                                            }
//                                        } else {
//                                            if (flowNodeInstance.getStatus() == statusFilter) {
//                                                t_instances.add(flowNodeInstance);
//                                            }
//                                        }
//                                    } else if (p != null) {
//                                        if (utask.getProcess().getURI().equals(p.getURI())) {
//                                            t_instances.add(flowNodeInstance);
//                                        }
//                                    } else {
//                                        t_instances.add(flowNodeInstance);
//                                    }
//                                }
//                            }
//                        }
//                    }
//                }
//            }
//        }

        //Realizar Ordenamiento de instancias
        if (sortType == null || sortType.trim().equals("")) {
            sortType = "date";
        } else {
            sortType = sortType.trim();
        }
        
        if (sortType.equals("date")) {
            unpaged = (ArrayList<FlowNodeInstance>)SWBUtils.Collections.copyIterator(SWBComparator.sortByCreated(unpaged.iterator(), false));
        } else if (sortType.equals("name")) {
            Collections.sort(unpaged, taskNameComparator);
        } 
        
        //Realizar paginado de instancias
        int maxPages = 1;
        if (request.getParameter("page") != null && !request.getParameter("page").trim().equals("")) {
            page = Integer.valueOf(request.getParameter("page"));
            if (page < 0) page = 1;
        }

        if (itemsPerPage < 5) itemsPerPage = 5;
        
        if (unpaged.size() >= itemsPerPage) {
            maxPages = (int)Math.ceil((double)unpaged.size() / itemsPerPage);
        }
        if (page > maxPages) page = maxPages;

        int sIndex = (page - 1) * itemsPerPage;
        if (unpaged.size() > itemsPerPage && sIndex > unpaged.size() - 1) {
            sIndex = unpaged.size() - itemsPerPage;
        }

        int eIndex = sIndex + itemsPerPage;
        if (eIndex >= unpaged.size()) eIndex = unpaged.size();

        request.setAttribute("maxPages", maxPages);
        ArrayList<FlowNodeInstance> instances = new ArrayList<FlowNodeInstance>();
        for (int i = sIndex; i < eIndex; i++) {
            FlowNodeInstance instance = unpaged.get(i);
            instances.add(instance);
        }
        return instances;
    }
    
    private boolean filterUserTaskInstance(FlowNodeInstance fni, User user, int statusFilter) {
        boolean hasGroup = false;
        boolean hasStatus = false;
        boolean canAccess = false;
        
        Process pType = fni.getProcessInstance().getProcessType();
        
        if (!pType.isValid()) {
            return true;
        }
        
        canAccess = fni.haveAccess(user);
        
        System.out.println(fni+" "+pType+" "+canAccess);

        if (canAccess) {
            //Verificar filtrado por grupo
            if (isFilterByGroup()) {
                UserGroup iug = fni.getProcessInstance().getOwnerUserGroup();
                UserGroup uug = user.getUserGroup();

                if (iug != null && uug != null) { //Si la instancia y el usuario tienen grupo
                    if (uug.equals(iug)) { //Si tienen el mismo grupo
                        hasGroup = true;
                    }
                } else if (iug == null && uug == null) { //Si el proceso y el usuario no tienen grupo
                    hasGroup = true;
                }
            }

            //Verificar filtrado por estatus
            if (statusFilter > 0 && fni.getStatus() == statusFilter) {
                hasStatus = true;
            }
        }
        return canAccess && (hasGroup && hasStatus);
    }
}
