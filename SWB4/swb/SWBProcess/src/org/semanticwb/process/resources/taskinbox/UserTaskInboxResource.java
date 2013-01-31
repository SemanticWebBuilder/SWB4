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
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.*;
import org.semanticwb.Logger;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.FormValidateException;
import org.semanticwb.model.Resource;
import org.semanticwb.model.Role;
import org.semanticwb.model.RoleRef;
import org.semanticwb.model.SWBComparator;
import org.semanticwb.model.User;
import org.semanticwb.model.UserGroup;
import org.semanticwb.model.UserRepository;
import org.semanticwb.model.WebSite;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.portal.SWBFormButton;
import org.semanticwb.portal.SWBFormMgr;
import org.semanticwb.portal.api.*;
import org.semanticwb.process.model.FlowNodeInstance;
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
    public static final String COL_IDPROCESS = "idProcessInstance";
    public static final String COL_IDTASK = "idTaskInstance";
    public static final String COL_NAMEPROCESS = "nameProcess";
    public static final String COL_NAMETASK = "nameTask";
    public static final String COL_TASKSUBJECT = "taskSubject";
    public static final String COL_STARTTASK = "startTask";
    public static final String COL_ENDTASK = "endTask";
    public static final String COL_STARTPROCESS = "startProcess";
    public static final String COL_ENDPROCESS = "endProcess";
    public static final String COL_CREATORPROCESS = "creatorProcess";
    public static final String COL_CREATORTASK = "creatorTask";
    public static final String COL_ACTIONS = "actions";
    public static final String COL_STATUSPROCESS = "statusProcess";
    public static final String COL_STATUSTASK = "statusTask";
    public static final String ATT_COLS = "cols";
    private HashMap<String, String> colNames;
    
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
    
    private void initColNames() {
        colNames = new HashMap<String, String>();
        colNames.put(COL_IDPROCESS, "ID de instancia de proceso");
        colNames.put(COL_IDTASK, "ID de instancia de tarea");
        colNames.put(COL_TASKSUBJECT, "Asunto de la instancia de tarea");
        colNames.put(COL_NAMEPROCESS, "Nombre de instancia de proceso");
        colNames.put(COL_NAMETASK, "Nombre de instancia de tarea");
        colNames.put(COL_STARTTASK, "Fecha de inicio de tarea");
        colNames.put(COL_STARTPROCESS, "Fecha de inicio de proceso");
        colNames.put(COL_ENDTASK, "Fecha de fin de tarea");
        colNames.put(COL_ENDPROCESS, "Fecha de fin de proceso");
        colNames.put(COL_CREATORPROCESS, "Creador de proceso");
        colNames.put(COL_CREATORTASK, "Creador de tarea");
        colNames.put(COL_STATUSPROCESS, "Estatus de proceso");
        colNames.put(COL_STATUSTASK, "Estatus de tarea");
        colNames.put(COL_ACTIONS, "Acciones");
    }

    public UserTaskInboxResource()
    {
        initColNames();
    }
    
    /**
     * Establece la configuración inicial de la bandeja de tareas.
     */
    private void initTaskInbox() {
        Resource base = getResourceBase(); 
        if (base.getAttribute(ATT_COLS+"1", "").equals("")) {
            base.setAttribute(ATT_COLS+"1", COL_IDPROCESS+"|Caso");
            base.setAttribute(ATT_COLS+"2", COL_NAMEPROCESS+"|Proceso");
            base.setAttribute(ATT_COLS+"3", COL_NAMETASK+"|Tarea");
            base.setAttribute(ATT_COLS+"4", COL_STARTTASK+"|Iniciada");
            base.setAttribute(ATT_COLS+"5", COL_ENDTASK+"|Cerrada");
            base.setAttribute(ATT_COLS+"6", COL_ACTIONS+"|Acciones");
            try {
                base.updateAttributesToDB();
            } catch (Exception ex) {
                log.error("UserTaskInboxResource - initTaskInbox", ex);
            }
        }
    }

   /**
   * Construye una nueva instancia de un UserTaskInboxResource dado un SemanticObject
   * @param base El SemanticObject con las propiedades para el UserTaskInboxResource
   */
    public UserTaskInboxResource(org.semanticwb.platform.SemanticObject base) {
        super(base);
        initColNames();
    }

    @Override
    public void processRequest(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        String mode = paramRequest.getMode();
        if ("forward".equals(mode)) {
            doForward(request, response, paramRequest);
        } else {
            super.processRequest(request, response, paramRequest);
        }
    }

    @Override
    public void processAction(HttpServletRequest request, SWBActionResponse response) throws SWBResourceException, IOException {
        String action = response.getAction();
        Resource base = getResourceBase();
        if ("CREATE".equals(action)) {
            User user=response.getUser();
            String pid = request.getParameter("pid");
            ProcessInstance inst = null;
            
            if (pid != null && !pid.trim().equals("")) {
                Process process = Process.ClassMgr.getProcess(pid, response.getWebPage().getWebSite());
                if (process != null) 
                {
                    inst = SWBProcessMgr.createSynchProcessInstance(process, user);
                    List<FlowNodeInstance> arr=SWBProcessMgr.getActiveUserTaskInstances(inst,response.getUser());                        
                    if(arr.size()>0)
                    {
                        response.sendRedirect(arr.get(0).getUserTaskUrl());
                        return;
                    }                    
                }
            }
            if (inst != null) {
                request.getSession(true).setAttribute("msg", "OK"+inst.getId());
            }
            response.setMode(response.Mode_VIEW);
        } else if ("setPageItems".equals(action)) {
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
        } else if ("forward".equals(action)) {
            String suri = request.getParameter("suri");
            String sowner = request.getParameter("owner");
            SemanticObject sobj = SemanticObject.createSemanticObject(suri);
            if (sobj != null) {
                FlowNodeInstance fni = (FlowNodeInstance)sobj.createGenericInstance();
                if (fni != null) {
                    User owner = response.getWebPage().getWebSite().getUserRepository().getUser(sowner);
                    fni.setAssignedto(owner);
                }
            }
            response.setMode(SWBParamRequest.Mode_VIEW);
        } else if ("config".equals(action)) {
            int i = 1;
            ArrayList<String> conf = new ArrayList<String>();
            while(!base.getAttribute(ATT_COLS+i, "").equals("")) {
                String val = base.getAttribute(ATT_COLS+i);
                String lbl = request.getParameter("lbl_"+i);
                String sel = request.getParameter("sel_"+i);
                
                if (sel == null) {
                    if (lbl != null && lbl.trim().length() > 0) {
                        String []cfg = val.split("\\|");
                        conf.add(cfg[0]+"|"+lbl.replaceAll("\\|", "_"));
                    }
                }
                base.removeAttribute(ATT_COLS+i);
                i++;
            }
            
            i = 1;
            Iterator<String> it = conf.iterator();
            while (it.hasNext()) {
                String key = it.next();
                base.setAttribute(ATT_COLS+i, key);
                i++;
            }
            
            try {
                base.updateAttributesToDB();
            } catch (Exception e) {
                log.error("UserTaskInboxResource - problema al actualizar base del recurso", e);
            }
            response.setMode(SWBParamRequest.Mode_ADMIN);
        } else {
            super.processAction(request, response);
        }
    }

    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        initTaskInbox();
        String jsp = "/swbadmin/jsp/process/userTaskInbox.jsp";
        if (getViewJSP() != null && !getViewJSP().trim().equals("")) {
            jsp = getViewJSP();
        }

        try {
            RequestDispatcher rd = request.getRequestDispatcher(jsp);
            request.setAttribute("paramRequest", paramRequest);
            request.setAttribute("instances", getUserTaskInstances(request, paramRequest));
            request.setAttribute("statusWp", getDisplayMapWp());
            request.setAttribute("itemsPerPage", getItemsPerPage());
            request.setAttribute("showPWpLink", isShowProcessWPLink());
            request.setAttribute("showPWpLink", isShowProcessWPLink());
            request.setAttribute("base", getResourceBase());
            rd.include(request, response);
        } catch (Exception e) {
            log.error("Error including jsp in view mode", e);
        }
    }
    
    @Override
    public void doAdmin(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        PrintWriter out = response.getWriter();
        StringBuilder sb = new StringBuilder();
        Resource base = getResourceBase();
        initTaskInbox();
        
        SWBFormMgr mgr = new SWBFormMgr(getSemanticObject(), null, SWBFormMgr.MODE_EDIT);
        mgr.setSubmitByAjax(true);
        mgr.setFilterHTMLTags(false);
        mgr.addButton(SWBFormButton.newSaveButton());
        mgr.setType(SWBFormMgr.TYPE_DOJO);
        String act = paramRequest.getRenderUrl().setAction("update").toString();
        
        if("addCol".equals(paramRequest.getAction())) {
            String col = request.getParameter("selectedCol");
            if (col != null && colNames.containsKey(col)) {
                int i = 1;
                while(!base.getAttribute(ATT_COLS+i, "").equals("")) {
                    i++;
                }
                base.setAttribute(ATT_COLS+i, col+"|"+colNames.get(col));
            }
            response.sendRedirect(paramRequest.getRenderUrl().setAction(null).toString());
        } else if("swap".equals(paramRequest.getAction())) {
            String dir = request.getParameter("dir");
            String idx = request.getParameter("idx");
            
            if (idx != null && idx.trim().length() > 0 && dir != null) {
                int pos = 0;
                int newPos = 0;
                boolean valid = false;
                
                String temp = base.getAttribute(ATT_COLS+idx);
                if (temp != null) {
                    try {
                        pos = Integer.parseInt(idx);
                    } catch (Exception e) {}

                    if (dir.equals("down")) {
                        newPos = pos + 1;
                        if (pos > 0 && newPos > 0 && pos < newPos) {
                            valid = true;
                        }
                    } else if (dir.equals("up")) {
                        newPos = pos - 1;
                        if (pos > 0 && newPos > 0 && pos > newPos) {
                            valid = true;
                        }
                    }
                    
                    String tmp2 = base.getAttribute("prop" + newPos);
                    if (tmp2 != null && valid) {
                        base.setAttribute("prop" + pos, tmp2);
                        base.setAttribute("prop" + newPos, temp);
                    }
                    
                    try {
                        base.updateAttributesToDB();
                    } catch (Exception e) {
                        log.error("Error al reordenar propiedad....", e);
                    }
                }
            }
            response.sendRedirect(paramRequest.getRenderUrl().setAction(null).toString());
        } else if("update".equals(paramRequest.getAction())) {
            try {
                mgr.processForm(request);
            } catch(FormValidateException e) {
                log.error(e);
            }
            response.sendRedirect(paramRequest.getRenderUrl().setAction(null).toString());
        } else {
            SWBResourceURL addColUrl = paramRequest.getRenderUrl().setAction("addCol");
            HashMap<String, String> availableCols = new HashMap<String, String>();
            availableCols.putAll(colNames);
            
            sb.append("<form class=\"swbform\" method=\"post\" id=\"").append(mgr.getFormName()).append("\" action=\"").append(act).append("\" dojoType=\"dijit.form.Form\" onSubmit=\"submitForm('").append(mgr.getFormName()).append("');return false;\">");
            sb.append(mgr.getFormHiddens());
            sb.append("  <fieldset>");
            sb.append("    <table>");
            sb.append("      <tr>");
            sb.append("        <td width=\"200px\" align=\"right\">").append("Identificador").append("</td>");
            sb.append("        <td>").append(getId()).append("</td>");
            sb.append("      </tr>");
            sb.append("    </table>");
            sb.append("  </fieldset>");
            sb.append("  <fieldset><legend>Datos Generales</legend>");
            sb.append("    <table>");
            sb.append("      <tr>");
            sb.append("        <td width=\"200px\" align=\"right\">");
            sb.append(mgr.renderLabel(request, utinbox_viewJSP, SWBFormMgr.MODE_VIEW));
            sb.append("        </td>");
            sb.append("        <td>");
            sb.append(mgr.renderElement(request, utinbox_viewJSP, SWBFormMgr.MODE_EDIT));
            sb.append("        </td>");
            sb.append("      </tr>");
            sb.append("      <tr>");
            sb.append("        <td width=\"200px\" align=\"right\">");
            sb.append(mgr.renderLabel(request, utinbox_itemsPerPage, SWBFormMgr.MODE_VIEW));
            sb.append("        </td>");
            sb.append("        <td>");
            sb.append(mgr.renderElement(request, utinbox_itemsPerPage, SWBFormMgr.MODE_EDIT));
            sb.append("        </td>");
            sb.append("      </tr>");
            sb.append("      <tr>");
            sb.append("        <td width=\"200px\" align=\"right\">");
            sb.append(mgr.renderLabel(request, utinbox_displayMapWp, SWBFormMgr.MODE_VIEW));
            sb.append("        </td>");
            sb.append("        <td>");
            sb.append(mgr.renderElement(request, utinbox_displayMapWp, SWBFormMgr.MODE_EDIT));
            sb.append("        </td>");
            sb.append("      </tr>");
            sb.append("      <tr>");
            sb.append("        <td width=\"200px\" align=\"right\">");
            sb.append(mgr.renderLabel(request, utinbox_filterByGroup, SWBFormMgr.MODE_VIEW));
            sb.append("        </td>");
            sb.append("        <td>");
            sb.append(mgr.renderElement(request, utinbox_filterByGroup, SWBFormMgr.MODE_EDIT));
            sb.append("        </td>");
            sb.append("      </tr>");
            sb.append("      <tr>");
            sb.append("        <td width=\"200px\" align=\"right\">");
            sb.append(mgr.renderLabel(request, utinbox_showProcessWPLink, SWBFormMgr.MODE_VIEW));
            sb.append("        </td>");
            sb.append("        <td>");
            sb.append(mgr.renderElement(request, utinbox_showProcessWPLink, SWBFormMgr.MODE_EDIT));
            sb.append("        </td>");
            sb.append("      </tr>");
            sb.append("    </table>");
            sb.append("    <button type=\"submit\" dojoType=\"dijit.form.Button\">Guardar</button>");
            sb.append("  </fieldset>");
            sb.append("</form>");
            
            sb.append("<form id=\"").append(getId()).append("/addColsForm\" class=\"swbform\" action=\"").append(addColUrl).append("\" dojoType=\"dijit.form.Form\" method=\"post\" onSubmit=\"submitForm('").append(getId()).append("/addColsForm'); return false;\">");
            sb.append("  <fieldset>");
            int i = 1;
            HashMap<String, String> selectedCols = new HashMap<String, String>();
            if (!base.getAttribute(ATT_COLS+i, "").equals("")) {
                while(!base.getAttribute(ATT_COLS+i, "").equals("")) {
                    String [] conf = base.getAttribute(ATT_COLS+i).split("\\|");
                    if (conf.length == 2) {
                        selectedCols.put(conf[0], conf[1]);
                        if (availableCols.containsKey(conf[0])) {
                            availableCols.remove(conf[0]);
                        }
                    }
                    i++;
                }
            }
            if (!availableCols.isEmpty()) {
                sb.append("    <select dojoType=\"dijit.form.FilteringSelect\" name=\"selectedCol\">");
                Iterator<String> keys = availableCols.keySet().iterator();
                while (keys.hasNext()) {
                    String key = keys.next();
                    sb.append("      <option value=\"").append(key).append("\">").append(availableCols.get(key)).append("</option>");
                }
                sb.append("    </select>");
                sb.append("    <button type=\"submit\" dojoType=\"dijit.form.Button\">Agregar columna</button><br/>");
            }
            sb.append("  </fieldset>");
            sb.append("</form>");
            
            i = 1;
            if (!base.getAttribute(ATT_COLS+i, "").equals("")) {
                SWBResourceURL editUrl = paramRequest.getActionUrl().setAction("config");
                sb.append("<form class=\"swbform\" id=\""+getId()+"/table\" method=\"post\" action=\""+editUrl+"\" dojoType=\"dijit.form.Form\" onSubmit=\"submitForm('"+getId()+"/table'); return false;\">");
                sb.append("  <fieldset><legend>Configuración de despliegue</legend>");
                sb.append("    <table>");
                sb.append("      <tr>");
                sb.append("        <th>Eliminar</th>");
                sb.append("        <th>Ordenamiento</th>");
                sb.append("        <th>Dato a mostrar</th>");
                sb.append("        <th>Título de columna</th>");
                sb.append("      </tr>");
                
                while(!base.getAttribute(ATT_COLS+i, "").equals("")) {
                    String val = base.getAttribute(ATT_COLS+i);
                    String cfg [] = val.split("\\|");
                    sb.append("      <tr>");
                    sb.append("        <td align=\"center\">");
                    sb.append("          <input type=\"checkbox\" name=\"sel_"+i+"\"/>");
                    sb.append("        </td>");
                    sb.append("        <td align=\"center\">");
                    SWBResourceURL swapUrl = paramRequest.getRenderUrl().setAction("swap").setParameter("idx", String.valueOf(i));
                    if (i != 1) {
                        swapUrl.setParameter("dir", "up");
                        sb.append("          <a href=\"#\" onclick=\"submitUrl('"+swapUrl+"', this); return false;\" title=\"Subir\"><img src=\"").append(SWBPlatform.getContextPath()).append("/swbadmin/images/up.jpg\" /></a>");
                    }
                    if (!base.getAttribute(ATT_COLS+(i+1), "").equals("")) {
                        swapUrl.setParameter("dir", "down");
                        sb.append("          <a href=\"#\" onclick=\"submitUrl('"+swapUrl+"', this); return false;\" title=\"Bajar\"><img src=\"").append(SWBPlatform.getContextPath()).append("/swbadmin/images/down.jpg\" /></a>");
                    }
                    sb.append("        </td>");
                    sb.append("        <td>").append(colNames.get(cfg[0])).append("</td>");
                    sb.append("        <td><input type=\"text\" dojoType=\"dijit.form.TextBox\" id=\"lbl_").append(i).append("\" name=\"lbl_").append(i).append("\" value=\"").append(cfg[1]).append("\" /></td>");
                    sb.append("      </tr>");
                    i++;
                }
                sb.append("    </table>");
            }
            sb.append("  </fieldset>");
            sb.append("  <button type=\"submit\" dojoType=\"dijit.form.Button\">Actualizar columnas</button>");
            sb.append("</form>");
//        }
//            SWBResourceURL render = paramRequest.getRenderUrl();
//            sb.append("<a href=\"#\" onclick=\"submitUrl('"+render+"', this); return false;\">Modo</a>");
//            sb.append("<a href=\""+render+"\">Modo</a>");
            out.println(sb.toString());
        }
    }
    
    public void doForward(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        User user = paramRequest.getUser();
        WebSite site = paramRequest.getWebPage().getWebSite();
        PrintWriter out = response.getWriter();
        StringBuilder sb = new StringBuilder();
        String suri = request.getParameter("suri");
        Iterator<User> tPartners = null;
        
        SemanticObject sobj = SemanticObject.createSemanticObject(suri);
        if (sobj != null) {
            FlowNodeInstance fni = (FlowNodeInstance) sobj.createGenericInstance();
            if (fni != null) {
                User owner = fni.getAssignedto();
                if (owner.equals(user)) {
                    
                    UserRepository ur = site.getUserRepository();
                    UserTask task = (UserTask) fni.getFlowNodeType();
                    ArrayList<Role> taskRoles = new ArrayList<Role>();

                    Iterator<RoleRef> refs = task.listRoleRefs();
                    while (refs.hasNext()) {
                        RoleRef roleRef = refs.next();
                        if (roleRef.getRole() != null && roleRef.isActive()) {
                            taskRoles.add(roleRef.getRole());
                        }
                    }
                    
                    if (taskRoles.isEmpty()) {
                        tPartners = ur.listUsers();
                    } else {
                        ArrayList<User> _users = new ArrayList<User>();
                        Iterator<Role> tRoles = taskRoles.iterator();
                        while (tRoles.hasNext()) {
                            Role role = tRoles.next();
                            Iterator<User> users = User.ClassMgr.listUserByRole(role);
                            while (users.hasNext()) {
                                User user1 = users.next();
                                if (!_users.contains(user1) && !user1.equals(owner)) {
                                    _users.add(user1);
                                }
                            }
                        }
                        tPartners = _users.iterator();
                    }
                }
            }
            
            if (tPartners != null && tPartners.hasNext() && fni != null) {
                SWBResourceURL forward = paramRequest.getActionUrl().setAction("forward");
                SWBResourceURL url = paramRequest.getRenderUrl().setMode(SWBParamRequest.Mode_VIEW);
                sb.append("<script type=\"text/javascript\">");
                sb.append(" dojo.require(\"dijit.form.Form\");");
                sb.append(" dojo.require(\"dijit.form.FilteringSelect\");");
                sb.append(" dojo.require(\"dijit.form.Button\");");
                sb.append("</script>");
                sb.append("<form class=\"swbform\" dojoType=\"dijit.form.Form\" action=\"").append(forward).append("\">");
                sb.append("  <input type=\"hidden\" name=\"suri\" value=\""+suri+"\"/>");
                sb.append("  <fieldset>");
                sb.append("    <table>");
                sb.append("      <tr>");
                sb.append("        <td width=\"200px\" align=\"right\">");
                sb.append("          <label for=\"owner\">Reasignar tarea a: </label>");
                sb.append("        </td>");
                sb.append("        <td>");
                sb.append("          <select name=\"owner\" dojoType=\"dijit.form.FilteringSelect\">");
                sb.append("            <option value=\"--\">Liberar tarea</option>");
                while(tPartners.hasNext()) {
                    User _user = tPartners.next();
                    if (!_user.equals(fni.getAssignedto())) {
                        sb.append("            <option value=\"").append(_user.getId()).append("\">").append((_user.getFullName()==null||_user.getFullName().trim().equals(""))?_user.getId():_user.getFullName()).append("</option>");
                    }
                }
                sb.append("          </select>");
                sb.append("        </td>");
                sb.append("      </tr>");
                sb.append("    </table>");
                sb.append("  </fieldset>");
                sb.append("  <fieldset>");
                sb.append("    <button type=\"submit\" dojoType=\"dijit.form.Button\">Reasignar</button>");
                sb.append("    <button dojoType=\"dijit.form.Button\" onclick=\"window.location='").append(url).append("'; return false;\">Regresar</button>");
                sb.append("  </fieldset>");
                sb.append("</form>");
            } else {
                String url = fni.getUserTaskInboxUrl();             
                sb.append("Esta tarea no puede ser reasignada a otro usuario");
                sb.append("<a href=\"").append(url).append("\">Regresar</a>");
            }
        }
        out.println(sb.toString());
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
        ArrayList<FlowNodeInstance> userTaskInstances = SWBProcessMgr.getUserTaskInstancesWithStatus((ProcessSite)site, statusFilter, p);
        
        //Iniciar el filtrado
        if (userTaskInstances != null) {
            Iterator<FlowNodeInstance> fnInstances = userTaskInstances.iterator();
            while (fnInstances.hasNext()) {
                FlowNodeInstance flowNodeInstance = fnInstances.next();
                if (validUserTaskInstance(flowNodeInstance, user, statusFilter)) {
                    unpaged.add(flowNodeInstance);
                }
            }
        }

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
    
    private boolean validUserTaskInstance(FlowNodeInstance fni, User user, int statusFilter) {
        boolean hasGroup = false;
        boolean hasStatus = false;
        boolean canAccess = false;
        
        Process pType = fni.getProcessInstance().getProcessType();
        
        if (!pType.isValid()) {
            return false;
        }
        canAccess = fni.haveAccess(user);
        
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
            } else {
                hasGroup = true;
            }

            //Verificar filtrado por estatus
            if (statusFilter > 0 && fni.getStatus() == statusFilter) {
                hasStatus = true;
            }
        }
        return canAccess && (hasGroup && hasStatus);
    }
}
