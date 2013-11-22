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

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.semanticwb.Logger;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBPortal;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.*;
import org.semanticwb.platform.*;
import org.semanticwb.portal.api.*;
import org.semanticwb.process.model.*;
import org.semanticwb.process.model.Process;

/**
 *
 * @author hasdai
 */
public class ProcessExport extends GenericResource {
    public static final String ACTION_EXPORT = "export";
    public static final String ACTION_IMPORT = "import";
    public static final String GET_FILE = "getFile";
    private static Logger log =SWBUtils.getLogger(ProcessExport.class);

    @Override
    public void processRequest(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        String mode = paramRequest.getMode();
        if (mode.equals(GET_FILE)) {
            doGetFile(request, response, paramRequest);
        } else {
            super.processRequest(request, response, paramRequest);
        }
    }
    
    @Override
    public void processAction(HttpServletRequest request, SWBActionResponse response) throws SWBResourceException, IOException {
        String action = response.getAction();        
        
        if (action.equals(ACTION_EXPORT)) {
            if (response.getWebPage().getWebSite() instanceof ProcessSite) {
                ProcessSite site = (ProcessSite) response.getWebPage().getWebSite();
                ArrayList<String> processIds = new ArrayList<String>();
                Map params = request.getParameterMap();
                
                Iterator<String> keys = params.keySet().iterator();
                while (keys.hasNext()) {
                    String key = keys.next();
                    if (request.getParameter(key) != null) {
                        processIds.add(key);
                    }
                }
                createZipPackage(getProcessPackage(site, processIds, true), site);
            }
            response.setMode("getFile");
        } else if (action.equals(ACTION_IMPORT)) {
            org.semanticwb.portal.util.FileUpload fup = new org.semanticwb.portal.util.FileUpload();
            fup.getFiles(request, null);
            byte[] bcont = fup.getFileData("ffile");
            
            System.out.println(fup.getFileName("ffile"));
            
            File file = new File(SWBPortal.getWorkPath()+getResourceBase().getWorkPath());
            file.mkdirs();
            OutputStream outs = new FileOutputStream(SWBPortal.getWorkPath()+getResourceBase().getWorkPath()+"/ProcessPackage.zip");
            
            SWBUtils.IO.copyStream(new ByteArrayInputStream(bcont), outs);
            
            String path = SWBPortal.getWorkPath()+getResourceBase().getWorkPath() + "/";
            String zip = path + "ProcessPackage.zip";
            File zipFile = new File(zip);
            if (zipFile.exists()) {
                java.io.File extractTo = new File(path+"_tmp");
                org.semanticwb.SWBUtils.IO.unzip(zipFile, extractTo);
                //String pkgData = SWBUtils.IO.readFileFromZipAsString(zipFile.getAbsolutePath(), "PkgData.json");
                String pkgData = SWBUtils.IO.readInputStream(new FileInputStream(zipFile.getAbsolutePath()+"PkgData.json"));
                try {
                    JSONObject data = new JSONObject(pkgData);
                    importProcessesPackage(data, extractTo, (ProcessSite) response.getWebPage().getWebSite());
                } catch (Exception ex) {
                    log.error(ex);
                }
                SWBUtils.IO.removeDirectory(extractTo.getAbsolutePath());
            }
        } else {
            super.processAction(request, response);
        }
    }
    
    public void doGetFile(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        response.setHeader("Cache-Control", "no-cache");
        response.setHeader("Pragma", "no-cache");

        try {
            String basepath = SWBPortal.getWorkPath() + getResourceBase().getWorkPath() + "/";
            File zip = new File(basepath+"ProcessPackage.zip");
            if (zip.exists()) {
                response.setContentType("application/octet-stream");
                response.setHeader("Content-Disposition", "attachment; filename=\"ProcessPackage.zip\";");

                OutputStream out = response.getOutputStream();
                SWBUtils.IO.copyStream(new FileInputStream(zip), out);
            }
        } catch (Exception e) {
            log.error("Error al obtener el zip.", e);
        }
    }

    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        PrintWriter out = response.getWriter();
        WebSite model = paramRequest.getWebPage().getWebSite();
        StringBuilder sb = new StringBuilder();
        SWBResourceURL actUrl = paramRequest.getActionUrl().setAction(ACTION_EXPORT);
        SWBResourceURL importUrl = paramRequest.getActionUrl().setAction(ACTION_IMPORT);
        String lang = "es";
        
        if (paramRequest.getUser() != null && paramRequest.getUser().getLanguage() != null) {
            lang = paramRequest.getUser().getLanguage();
        }
        
        Iterator<ProcessGroup> groups = ProcessGroup.ClassMgr.listProcessGroups(model);
        if (groups.hasNext()) {
            boolean hasProcess = false;
            sb.append("  <form dojoType=\"dijit.form.Form\" action=\"" + actUrl + "\" method=\"post\">");
            sb.append("    <fieldset><legend>Exportar procesos del sitio</legend>");
            sb.append("    <table>");

            while (groups.hasNext()) {
                ProcessGroup group = groups.next();
                Iterator<Process> processes = group.listProcesses();
                ArrayList<Process> _processes = new ArrayList<Process>();
                while (processes.hasNext()) {
                    Process process = processes.next();
                    if (!process.isDeleted()) {
                        _processes.add(process);
                        if (!hasProcess) {
                            hasProcess = true;
                        }
                    }
                }

                processes = SWBComparator.sortByDisplayName(_processes.iterator(), lang);
                if (processes.hasNext()) {
                    sb.append("    <tr>");
                    sb.append("      <td align=\"left\" colspan=\"2\"><b>" + group.getDisplayTitle(lang) + "</b></td>");
                    sb.append("    </tr>");
                    while (processes.hasNext()) {
                        Process process = processes.next();
                        sb.append("    <tr>");
                        sb.append("      <td><input type=\"checkbox\" name=\"" + process.getId() + "\"/></td><td align=\"left\">" + process.getDisplayTitle(lang) + "</td>");
                        sb.append("    </tr>");
                    }
                }
            }
            
            if (hasProcess) {
                sb.append("    <tr>");
                sb.append("      <td align=\"center\" colspan=\"2\"><input type=\"submit\" value=\"Exportar seleccionados\"/></td>");
                sb.append("    </tr>");
            } else {
                sb.append("    <tr>");
                sb.append("      <td align=\"center\" colspan=\"2\"><span>No existen procesos para exportar</span></td>");
                sb.append("    </tr>");
            }
            sb.append("  </table>");
            sb.append("  </fieldset>");
            sb.append("</form>");
        }
        sb.append("<form dojoType=\"dijit.form.Form\" action=\"" + importUrl + "\" method=\"post\" enctype=\"multipart/form-data\">");
        sb.append("  <fieldset><legend>Importar procesos desde archivo</legend>");
        sb.append("    <table>");
        sb.append("      <tr>");
        sb.append("         <td align=\"center\"><input id=\"ffile\" type=\"file\" name=\"ffile\" value=\"\" style=\"width:300px;\"></td>");
        sb.append("      </tr>");
        sb.append("      <tr>");
        sb.append("         <td align=\"center\"><input type=\"submit\" value=\"Importar\"/></td>");
        sb.append("      </tr>");
        sb.append("    </table>");
        sb.append("  </fieldset>");
        sb.append("</form>");
        
        out.print(sb.toString());
    }
    
    private void createZipPackage(JSONObject data, ProcessSite site) {
        Resource base = getResourceBase();
        String basepath = SWBPortal.getWorkPath() + base.getWorkPath() + "/";
        String sitePath = SWBPortal.getWorkPath() + "/models/"+site.getId()+"/";
        try {
            File resPath = new File(basepath);
            if (!resPath.exists()) {
                resPath.mkdirs();
            }
            
            File zip = new File(basepath+"ProcessPackage.zip");
            if (zip.exists()) zip.delete();
            ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(zip));
            ZipEntry entry = new ZipEntry("PkgData.json");
            zos.putNextEntry(entry);
            //zos.write(data.toString(2).getBytes("utf-8"));
            zos.write(data.toString(2).getBytes());
            zos.closeEntry();
            
            //Copiar filesystem de las plantillas de archivos
            JSONArray processTools = data.getJSONArray("tools");
            if (processTools != null) {
                for (int i = 0; i < processTools.length(); i++) {
                    JSONObject tool = processTools.getJSONObject(i);
                    String clsName = tool.getString("class");
                    if (clsName.equals("org.semanticwb.process.model.ProcessFileTemplate")) {
                        SemanticObject semObject = SemanticObject.createSemanticObject(tool.getString("uri"));
                        if (semObject != null) {
                            String objPath = SWBPortal.getWorkPath()+semObject.getWorkPath()+"/";
                            File directory2Zip = new File(objPath);
                            if(directory2Zip != null && directory2Zip.exists()){
                                //System.out.println("dirPath: "+objPath+", basePath: "+sitePath);
                                org.semanticwb.SWBUtils.IO.zip(directory2Zip, new File(sitePath), zos);
                            }
                        }
                    }
                }
            }
            
            //Almacenar los filesystems de los objetos
            processTools = data.getJSONArray("processes");
            if (processTools != null) {
                for (int i = 0; i < processTools.length(); i++) {
                    JSONObject process = processTools.getJSONObject(i);
                    JSONArray nodes = process.getJSONArray("nodes");
                    if (nodes != null) {
                        for (int j = 0; j < nodes.length(); j++) {
                            JSONObject node = nodes.getJSONObject(j);
                            if (!node.optString("resources", "").equals("")) {
                                JSONArray resources = node.getJSONArray("resources");
                                for (int k = 0; k < resources.length(); k++) {
                                    JSONObject resource = resources.getJSONObject(k) ;
                                    if (!resource.optString("uri", "").equals("")) {
                                        SemanticObject semObject = SemanticObject.createSemanticObject(resource.getString("uri"));
                                        if (semObject != null) {
                                            String objPath = SWBPortal.getWorkPath()+semObject.getWorkPath()+"/";
                                            File directory2Zip = new File(objPath);
                                            if(directory2Zip != null && directory2Zip.exists()){
                                                //System.out.println("dirPath: "+objPath+", basePath: "+sitePath);
                                                org.semanticwb.SWBUtils.IO.zip(directory2Zip, new File(sitePath), zos);
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            
            zos.close();
        } catch (Exception ex) {
            log.error(ex);
        }
    }
    
    private JSONObject getProcessJSON(Process process) {
        JSONObject ret = getElementJSON(process);
        JSONArray nodes = new JSONArray();
        JSONArray data = new JSONArray();
        JSONArray conns = new JSONArray();
        HashMap<String, ConnectionObject> cobjs = new HashMap<String, ConnectionObject>();
        //Model model = ModelFactory.createDefaultModel();
        
        Iterator<GraphicalElement> itChilds = process.listAllContaineds();
        while (itChilds.hasNext()) {
            GraphicalElement ge = itChilds.next();
            //model.add(ge.getSemanticObject().getRDFResource().listProperties());
            JSONObject child = getProcessElementJSON(ge);
            if (ge instanceof ItemAware) {
                ItemAware temp = (ItemAware)ge;
                try {
                    if (temp.getDataObjectClass() != null) child.put(ItemAware.swp_dataObjectClass.getPropId(), temp.getDataObjectClass().getURI());
                    else child.put(ItemAware.swp_dataObjectClass.getPropId(), "");
                    
                    child.put(ItemAware.swp_name.getPropId(), temp.getName());
                    data.put(child);
                } catch (Exception ex) {
                    log.error(ex);
                }
            } else {
                nodes.put(child);
            }
            
            Iterator<ConnectionObject> itConns = ge.listOutputConnectionObjects();
            while (itConns.hasNext()) {
                ConnectionObject connectionObject = itConns.next();
                if (cobjs.get(connectionObject.getURI()) == null) {
                    cobjs.put(connectionObject.getURI(), connectionObject);
                    JSONObject conn = getElementJSON(connectionObject);
                    try {
                        conn.put(ConnectionObject.swp_connectionPoints.getPropId(), connectionObject.getConnectionPoints());
                        if (connectionObject.getSource() != null) conn.put(ConnectionObject.swp_source.getPropId(), connectionObject.getSource().getURI());
                        else conn.put(ConnectionObject.swp_source.getPropId(), "");
                        
                        if (connectionObject.getTarget() != null) conn.put(ConnectionObject.swp_target.getPropId(), connectionObject.getTarget().getURI());
                        else conn.put(ConnectionObject.swp_target.getPropId(), "");
                        
                        conns.put(conn);
                    } catch (Exception ex) {
                        log.error(ex);
                    }
                }
            }
        }
        
        try {
            if (process.getAdministrationRole() != null) ret.put(Process.swp_administrationRole.getPropId(), process.getAdministrationRole().getURI());
            if (process.getAssigmentNotificationTemplate() != null) ret.put(Process.swp_assigmentNotificationTemplate.getPropId(), process.getAssigmentNotificationTemplate().getURI());
            if (process.getDelayNotificationTemplate() != null) ret.put(Process.swp_delayNotificationTemplate.getPropId(), process.getDelayNotificationTemplate().getURI());
            if (process.getNotificationRole() != null) ret.put(Process.swp_notificationRole.getPropId(), process.getNotificationRole().getURI());
            if (process.getParentWebPage() != null) ret.put(Process.swp_parentWebPage.getPropId(), process.getParentWebPage().getURI());
            if (process.getProcessGroup() != null) ret.put(Process.swp_processGroup.getPropId(), getElementJSON(process.getProcessGroup()));
                    
            ret.put(Process.swp_filterByOwnerUserGroup.getPropId(), process.isFilterByOwnerUserGroup());
            ret.put("nodes", nodes);
            ret.put("data", data);
            ret.put("conns", conns);
        } catch (Exception ex) {
            log.error(ex);
        }
        return ret;
    }
    
    private JSONObject getProcessElementJSON(GraphicalElement ge) {
        JSONObject ret = getElementJSON(ge);
        
        try {            
            ret.put(GraphicalElement.swp_height.getPropId(), ge.getHeight());
            ret.put(GraphicalElement.swp_width.getPropId(), ge.getWidth());
            ret.put(GraphicalElement.swp_x.getPropId(), ge.getX());
            ret.put(GraphicalElement.swp_y.getPropId(), ge.getY());
            
            if (ge.getContainer() != null) 
                ret.put(GraphicalElement.swp_container.getPropId(), ge.getContainer().getURI());
            else {
                ret.put(GraphicalElement.swp_container.getPropId(), "");
            }
            
            if (ge.getParent() != null) {
                ret.put(GraphicalElement.swp_parent.getPropId(), ge.getParent().getURI());
            } else {
                ret.put(GraphicalElement.swp_parent.getPropId(), "");
            }

            if (ge.getLabelSize() != 0) ret.put(GraphicalElement.swp_labelSize.getPropId(), ge.getLabelSize());
            else ret.put(GraphicalElement.swp_labelSize.getPropId(), 10);
            
            if (ge instanceof ResourceAssignmentable) {
                ret.put(ResourceAssignmentable.swp_resourceAssignationRule.getPropId(), ((ResourceAssignmentable)ge).getResourceAssignationRule());
            }
            
            if (ge instanceof CallActivity) {
                if (((CallActivity)ge).getCalledElement() != null) ret.put(CallActivity.swp_calledElement.getPropId(), ((CallActivity)ge).getCalledElement().getURI());
                else ret.put(CallActivity.swp_calledElement.getPropId(), "");
            }
            
            if (ge instanceof ActivityConfable) {
                ActivityConfable temp = (ActivityConfable)ge;
                ret.put(ActivityConfable.swp_forCompensation.getPropId(), temp.isForCompensation());
                if (temp.getLoopCharacteristics() != null) ret.put(ActivityConfable.swp_loopCharacteristics.getPropId(), getElementJSON(temp.getLoopCharacteristics()));
                else ret.put(ActivityConfable.swp_loopCharacteristics.getPropId(), "");
            }
            
            if (ge instanceof ActionCodeable) {
                ret.put(ActionCodeable.swp_actionCode.getPropId(), ((ActionCodeable)ge).getActionCode());
            }
            
            if (ge instanceof CatchMessageable) {
                CatchMessageable temp = (CatchMessageable)ge;
                JSONArray mappings = new JSONArray();
                
                Iterator<ItemAwareMapping> itm = temp.listItemAwareMappings();
                while (itm.hasNext()) {
                    ItemAwareMapping itemAwareMapping = itm.next();
                    JSONObject mapping = getElementJSON(itemAwareMapping);
                    mapping.put(ItemAwareMapping.swp_localItemAware.getPropId(), itemAwareMapping.getLocalItemAware().getURI());
                    mapping.put(ItemAwareMapping.swp_remoteItemAware.getPropId(), itemAwareMapping.getRemoteItemAware().getURI());
                    mappings.put(mapping);
                }
                
                ret.put(CatchMessageable.swp_hasItemAwareMapping.getPropId(), mappings);
            }
            
            if (ge instanceof ThrowMessageable) {
                ThrowMessageable temp = (ThrowMessageable)ge;
                JSONArray messageItems = new JSONArray();
                
                Iterator<ItemAware> itm = temp.listMessageItemAwares();
                while (itm.hasNext()) {
                    ItemAware itemAware = itm.next();
                    messageItems.put(itemAware.getURI());
                }
                
                ret.put(ThrowMessageable.swp_hasMessageItemAware.getPropId(), messageItems);
            }
            
            if (ge instanceof ProcessPeriodRefable) {
                ProcessPeriodRefable temp = (ProcessPeriodRefable)ge;
                JSONArray processPeriodRefs = new JSONArray();
                
                Iterator<ProcessPeriodRef> refs = temp.listProcessPeriodRefs();
                while (refs.hasNext()) {
                    ProcessPeriodRef processPeriodRef = refs.next();
                    JSONObject ref = getElementJSON(processPeriodRef);
                    
                    if (processPeriodRef.getProcessPeriod() != null) ref.put(ProcessPeriodRef.swp_processPeriod.getPropId(), processPeriodRef.getProcessPeriod().getURI());
                    else ref.put(ProcessPeriodRef.swp_processPeriod.getPropId(), "");
                    
                    processPeriodRefs.put(ref);
                }
                ret.put(ProcessPeriodRefable.swp_hasProcessPeriodRef.getPropId(), processPeriodRefs);
            }
            
            if (ge instanceof RoleRefable) {
                RoleRefable temp = (RoleRefable)ge;
                JSONArray roleRefs = new JSONArray();
                
                Iterator<RoleRef> refs = temp.listRoleRefs();
                while (refs.hasNext()) {
                    RoleRef roleRef = refs.next();
                    JSONObject ref = getElementJSON(roleRef);
                    
                    if (roleRef.getRole() != null) ref.put(RoleRef.swb_role.getPropId(), roleRef.getRole().getURI());
                    else ref.put(RoleRef.swb_role.getPropId(), "");
                    
                    roleRefs.put(ref);
                }
                ret.put(RoleRefable.swb_hasRoleRef.getPropId(), roleRefs);
            }
            
            if (ge instanceof Collectionable) {
                ret.put(Collectionable.swp_collection.getPropId(), ((Collectionable)ge).isCollection());
            }
            
            if (ge instanceof ScriptTask) {
                ret.put(ScriptTask.swp_scriptCode.getPropId(), ((ScriptTask)ge).getScriptCode());
            }
            
            if (ge instanceof ServiceTask) {
                if (((ServiceTask)ge).getProcessService() != null) ret.put(ServiceTask.swp_processService.getPropId(), getElementJSON(((ServiceTask)ge).getProcessService()));
                else ret.put(ServiceTask.swp_processService.getPropId(), "");
            }
            
            if (ge instanceof UserTask) {
                UserTask temp = (UserTask)ge;
                ret.put(UserTask.swp_notificationTime.getPropId(), temp.getNotificationTime());
                
                JSONArray actsArray = new JSONArray();
                Iterator<TaskAction> actions = temp.listTaskActions();
                while (actions.hasNext()) {
                    TaskAction taskAction = actions.next();
                    JSONObject action = getElementJSON(taskAction);
                    
                    action.put(TaskAction.swp_taskActionCode.getPropId(), taskAction.getCode());
                    action.put(TaskAction.swp_taskActionType.getPropId(), taskAction.getActionType());
                    actsArray.put(action);
                }
                ret.put(UserTask.swp_hasTaskAction.getPropId(), actsArray);
            }
            
            if (ge instanceof CatchEvent) {
                ret.put(CatchEvent.swp_interruptor.getPropId(), ((CatchEvent)ge).isInterruptor());
            }
            
            if (ge instanceof MultipleIntermediateThrowEvent) {
                MultipleIntermediateThrowEvent temp = (MultipleIntermediateThrowEvent)ge;
                JSONArray events = new JSONArray();
                
                Iterator<IntermediateThrowEvent> itm = temp.listThrowEventses();
                while (itm.hasNext()) {
                    IntermediateThrowEvent event = itm.next();
                    events.put(event.getURI());
                }
                
                ret.put(MultipleIntermediateThrowEvent.swp_hasThrowEvents.getPropId(), events);
            }
            
            if (ge instanceof Gateway) {
                ret.put(Gateway.swp_gatewayMode.getPropId(), ((Gateway)ge).getGatewayMode());
                if (ge instanceof ComplexGateway) {
                    ret.put(ComplexGateway.swp_startTokens.getPropId(), ((ComplexGateway)ge).getStartTokens());
                }
            }
            
            if (ge instanceof DataStore) {
                ret.put(DataStore.swp_dataObjectId.getPropId(), ((DataStore)ge).getDataObjectId());
                ret.put(DataStore.swp_dataObjectInitializationCode.getPropId(), ((DataStore)ge).getInitializationCode());
            }
            
        } catch (Exception ex) {
            log.error(ex);
        }
        return ret;
    }
    
    private JSONObject getElementJSON(SWBClass element) {
        JSONObject ret = new JSONObject();
        String pkg = "org.semanticwb.process.model.";
        try {
            ret.put("uri", element.getURI());
            ret.put("class", pkg+element.getSemanticObject().getSemanticClass().getClassCodeName());
            ret.put("sclass", element.getSemanticObject().getSemanticClass().getURI());
            //TODO: Agregar propiedades de calendarRefable
            if (element instanceof Resourceable) {
                JSONArray resArray = new JSONArray();
                Resourceable temp = (Resourceable)element;
                Iterator<Resource> resources = temp.listResources();
                while (resources.hasNext()) {
                    Resource resource = resources.next();
                    //System.out.println("Checando resource "+resource.getURI());
                    JSONObject res = getElementJSON(resource);
                    
                    res.put("resTypeId", resource.getResourceType().getId());
                    res.put("resTypeClass", resource.getResourceType().getResourceClassName());
                    res.put("resTypeCache", resource.getResourceType().getResourceCacheType());
                    res.put("resTypeTitle", resource.getResourceType().getTitle());
                    res.put("resTypeMode", resource.getResourceType().getResourceMode());
                    SWBResource swbres = SWBPortal.getResourceMgr().getResource(resource);
                    if (swbres instanceof Versionable) {
                        Versionable ver = (Versionable)swbres;
                        if (ver.getActualVersion() != null) {
                            res.put(Versionable.swb_actualVersion.getPropId(), getElementJSON(ver.getActualVersion()));
                        }
                    }
                    
                    resArray.put(res);
                }
                ret.put("resources", resArray);
            }
            if (element instanceof Activeable) {
                Activeable temp = (Activeable)element;
                ret.put(Activeable.swb_active.getPropId(), temp.isActive());
            }
            if (element instanceof Expirable) {
                Expirable temp = (Expirable)element;
                if (temp.getExpiration() != null) {
                    ret.put(Expirable.swb_expiration.getPropId(), SWBUtils.TEXT.iso8601DateFormat(temp.getExpiration()));
                }
            }
            if (element instanceof Callable) {
                Callable temp = (Callable)element;
                ret.put(Callable.swp_callable.getPropId(), temp.isCallable());
            }
            if (element instanceof Descriptiveable) {
                Descriptiveable temp = (Descriptiveable)element;
                ret.put(Descriptiveable.swb_title.getPropId(), temp.getTitle());
                ret.put(Descriptiveable.swb_description.getPropId(), temp.getDescription());
            }
            if (element instanceof XMLable) {
                XMLable temp = (XMLable)element;
                ret.put(XMLable.swb_xml.getPropId(), temp.getXml());
            }
            if (element instanceof Sortable) {
                ret.put(Sortable.swb_index.getPropId(), ((Sortable)element).getIndex());
            }
            if (element instanceof StoreRepositoryNodeable) {
                StoreRepositoryNodeable temp = (StoreRepositoryNodeable)element;
                ret.put(StoreRepositoryNodeable.swp_storeRepNodeId.getPropId(), temp.getNodeId());
                ret.put(StoreRepositoryNodeable.swp_storeRepNodeName.getPropId(), temp.getNodeName());
            }
            if (element instanceof ProcessRuleRefable) {
                ProcessRuleRefable temp = (ProcessRuleRefable)element;
                JSONArray processRuleRefs = new JSONArray();
                
                Iterator<ProcessRuleRef> refs = temp.listProcessRuleRefs();
                while (refs.hasNext()) {
                    ProcessRuleRef processRuleRef = refs.next();
                    JSONObject ref = getElementJSON(processRuleRef);
                    
                    if (processRuleRef.getProcessRule() != null) ref.put(ProcessRuleRef.swp_processRule.getPropId(), processRuleRef.getProcessRule().getURI());
                    else ref.put(ProcessRuleRef.swp_processRule.getPropId(), "");
                    
                    processRuleRefs.put(ref);
                }
                ret.put(ProcessRuleRefable.swp_hasProcessRuleRef.getPropId(), processRuleRefs);
            }
            if (element instanceof Versionable) {
                Versionable temp = (Versionable) element;
                if (temp.getActualVersion() != null) {
                    ret.put(Versionable.swb_actualVersion.getPropId(), getElementJSON(temp.getActualVersion()));
                }
            }
            
            if (element instanceof VersionInfo) {
                VersionInfo temp = (VersionInfo)element;
                ret.put(VersionInfo.swb_versionAuthorized.getPropId(), temp.isVersionAuthorized());
                if (temp.getVersionComment() != null) ret.put(VersionInfo.swb_versionComment.getPropId(), temp.getVersionComment());
                if (temp.getVersionFile() != null) ret.put(VersionInfo.swb_versionFile.getPropId(), temp.getVersionFile());
                ret.put(VersionInfo.swb_versionNumber.getPropId(), temp.getVersionNumber());
                if (temp.getVersionValue() != null) ret.put(VersionInfo.swb_versionValue.getPropId(), temp.getVersionValue());
            }
            if (element instanceof ProcessRule) {
                ProcessRule temp = (ProcessRule)element;
                ret.put(ProcessRule.swp_processRuleGroup.getPropId(), getElementJSON(temp.getProcessRuleGroup()));
                ret.put(ProcessRule.swp_ruleCondition.getPropId(), temp.getRuleCondition());
            }
            if (element instanceof NotificationTemplate) {
                NotificationTemplate temp = (NotificationTemplate)element;
                ret.put(NotificationTemplate.swp_notificationTemplateSubject.getPropId(), temp.getSubject());
                ret.put(NotificationTemplate.swp_notificationTemplateBody.getPropId(), temp.getBody());
            }
            if (element instanceof ProcessFileTemplate) {
                ProcessFileTemplate temp = (ProcessFileTemplate)element;
                ret.put(ProcessFileTemplate.swp_fileName.getPropId(), temp.getFileName());
            }
            if (element instanceof DBConnection) {
                DBConnection temp = (DBConnection)element;
                ret.put(DBConnection.swp_dbconUrl.getPropId(), temp.getUrl());
                ret.put(DBConnection.swp_dbconPassword.getPropId(), temp.getPassword());
                ret.put(DBConnection.swp_dbconUser.getPropId(), temp.getUser());
            }
            if (element instanceof WebService) {
                WebService temp = (WebService)element;
                ret.put(WebService.swp_wsUrl.getPropId(), temp.getUrl());
            }
            if (element instanceof SendMail) {
                SendMail temp = (SendMail)element;
                ret.put(SendMail.swp_sendMailContent.getPropId(), temp.getContent());
                ret.put(SendMail.swp_sendMailFrom.getPropId(), temp.getFrom());
                ret.put(SendMail.swp_sendMailTo.getPropId(), temp.getTo());
                ret.put(SendMail.swp_sendMailSubject.getPropId(), temp.getSubject());
            }
            if (element instanceof SparQLQuery) {
                SparQLQuery temp = (SparQLQuery)element;
                ret.put(SparQLQuery.swp_sparQLCode.getPropId(), temp.getCode());
                ret.put(SparQLQuery.swp_sparQLQuery.getPropId(), temp.getQuery());
            }
            if (element instanceof SQLQuery) {
                SQLQuery temp = (SQLQuery)element;
                ret.put(SQLQuery.swp_dbConnection.getPropId(), temp.getDbConnection().getURI());
                ret.put(SQLQuery.swp_sqlCode.getPropId(), temp.getCode());
                ret.put(SQLQuery.swp_sqlQuery.getPropId(), temp.getQuery());
            }
            if (element instanceof StoreRepositoryFile) {
                ret.put(StoreRepositoryFile.swp_storeRepNodeVarName.getPropId(), ((StoreRepositoryFile)element).getNodeVarName());
            }
            if (element instanceof TransformRepositoryFile) {
                TransformRepositoryFile temp = (TransformRepositoryFile)element;
                ret.put(TransformRepositoryFile.swp_transformRepNodeVarName.getPropId(), temp.getNodeVarName());
                
                if (temp.getFileTemplate() != null) ret.put(TransformRepositoryFile.swp_transformRepFileTemplate.getPropId(), temp.getFileTemplate());
                else ret.put(TransformRepositoryFile.swp_transformRepFileTemplate.getPropId(), "");
            }
            if (element instanceof WebServiceInvoker) {
                //TODO: Exportar invocadores de servicios web
            }
        } catch (Exception ex) {
            log.error(ex);
        }
        return ret;
    }
    
    private JSONObject getProcessPackage(ProcessSite site, ArrayList<String> processIds, boolean addProcessTools) {
        JSONObject pkg = new JSONObject();
        JSONArray processTools = new JSONArray();
        JSONArray siteRoles = new JSONArray();
        JSONArray processes = new JSONArray();

        if (addProcessTools) {
            //Plantillas de notificación
            Iterator<NotificationTemplate> templates = NotificationTemplate.ClassMgr.listNotificationTemplates(site);
            while (templates.hasNext()) {
                NotificationTemplate template = templates.next();
                processTools.put(getElementJSON(template));
            }

            //Plantillas de archivos
            Iterator<ProcessFileTemplate> fileTemplates = ProcessFileTemplate.ClassMgr.listProcessFileTemplates(site);
            while (fileTemplates.hasNext()) {
                ProcessFileTemplate processFileTemplate = fileTemplates.next();
                processTools.put(getElementJSON(processFileTemplate));
            }

            //Intervalos de ejecución
            Iterator<ProcessPeriod> periods = ProcessPeriod.ClassMgr.listProcessPeriods(site);
            while (periods.hasNext()) {
                ProcessPeriod processPeriod = periods.next();
                processTools.put(getElementJSON(processPeriod));
            }

            //Reglas de negocio
            Iterator<ProcessRule> rules = ProcessRule.ClassMgr.listProcessRules(site);
            while (rules.hasNext()) {
                ProcessRule processRule = rules.next();
                processTools.put(getElementJSON(processRule));
            }

            //Grupos de reglas de negocio
            Iterator<ProcessRuleGroup> prGroups = ProcessRuleGroup.ClassMgr.listProcessRuleGroups(site);
            while (prGroups.hasNext()) {
                ProcessRuleGroup processRuleGroup = prGroups.next();
                processTools.put(getElementJSON(processRuleGroup));
            }

            //Conexiones a bases de datos
            Iterator<DBConnection> dbs = DBConnection.ClassMgr.listDBConnections(site);
            while (dbs.hasNext()) {
                DBConnection dBConnection = dbs.next();
                processTools.put(getElementJSON(dBConnection));
            }

            //Servicios Web
            Iterator<WebService> services = WebService.ClassMgr.listWebServices(site);
            while (services.hasNext()) {
                WebService webService = services.next();
                processTools.put(getElementJSON(webService));
            }
            
            //Roles
            Iterator<Role> roles = site.getUserRepository().listRoles();
            while (roles.hasNext()) {
                Role role = roles.next();
                siteRoles.put(getElementJSON(role));
            }
        }

        //Procesos de negocio
        Iterator<String> keys = processIds.iterator();
        //Iterator<Process> itProcesses = Process.ClassMgr.listProcesses(site);
        while (keys.hasNext()) {
            String key = keys.next();
            Process process = Process.ClassMgr.getProcess(key, site);
            if (process != null) {
                processes.put(getProcessJSON(process));
            }
        }
        
        try {
            pkg.put("tools", processTools);
            pkg.put("processes", processes);
            pkg.put("siteRoles", siteRoles);
        } catch (Exception ex) {
            log.error(ex);
        }
        
        return pkg;
    }
    
    private void importProcessesPackage(JSONObject data, java.io.File targetDir, ProcessSite site) {
        //Crear herramientas de procesos
        HashMap<String, GenericObject> _elements = new HashMap<String, GenericObject>();
        SemanticModel model = site.getSemanticObject().getModel();
        GenericObject go = null;
        
        try {
            JSONArray elements = data.getJSONArray("tools");
            if (elements != null) {
                for (int i = 0; i < elements.length(); i++) {
                    JSONObject tool = elements.getJSONObject(i);
                    String suri = tool.getString("uri");
                    String clsUri = tool.getString("sclass");
                    go = getGenericObject(suri, clsUri, model);
                    
                    if (go != null) {
                        _elements.put(suri, go);
                        Descriptiveable desc = (Descriptiveable)go;
                        if (desc.getTitle() == null || (desc.getTitle() != null && !desc.getTitle().equals(tool.optString(Descriptiveable.swb_title.getPropId(), "")))) {
                            setBaseProperties(go, tool);
                            if (go instanceof ProcessRule) {
                                ProcessRule temp = (ProcessRule)go;
                                String prUri = tool.getJSONObject(ProcessRule.swp_processRuleGroup.getPropId()).getString("uri");
                                ProcessRuleGroup ruleGroup = (ProcessRuleGroup) getGenericObject(prUri, ProcessRuleGroup.sclass.getURI(), model);
                                setBaseProperties(ruleGroup, tool.getJSONObject(ProcessRule.swp_processRuleGroup.getPropId()));
                                temp.setProcessRuleGroup(ruleGroup);
                                temp.setRuleCondition(tool.optString(ProcessRule.swp_ruleCondition.getPropId(), ""));
                            }
                            if (go instanceof NotificationTemplate) {
                                NotificationTemplate temp = (NotificationTemplate)go;
                                temp.setSubject(tool.optString(NotificationTemplate.swp_notificationTemplateSubject.getPropId(), ""));
                                temp.setBody(tool.optString(NotificationTemplate.swp_notificationTemplateBody.getPropId(), ""));
                            }
                            if (go instanceof ProcessFileTemplate) {
                                ProcessFileTemplate temp = (ProcessFileTemplate)go;
                                temp.setFileName(tool.optString(ProcessFileTemplate.swp_fileName.getPropId(), ""));
                                //TODO: Copiar los archivos
                            }
                            if (go instanceof DBConnection) {
                                DBConnection temp = (DBConnection)go;
                                temp.setUrl(tool.optString(DBConnection.swp_dbconUrl.getPropId(), ""));
                                temp.setPassword(tool.optString(DBConnection.swp_dbconPassword.getPropId(), ""));
                                temp.setUser(tool.optString(DBConnection.swp_dbconUser.getPropId(), ""));
                            }
                            if (go instanceof WebService) {
                                WebService temp = (WebService)go;
                                temp.setUrl(tool.optString(WebService.swp_wsUrl.getPropId(), ""));
                            }
                            
                            int pos = suri.indexOf("#");
                            //System.out.println("suri: "+suri);
                            String newSObjPath = go.getSemanticObject().getWorkPath();
                            if(pos > -1) {
                                int pos1 = suri.indexOf(":", pos);
                                if(pos1 > -1) {
                                    String dirOldSObj = suri.substring(pos + 1, pos1);
                                    String idOldSobj = suri.substring(pos1 + 1);
                                    File fileOldSObjPath = new File(targetDir.getAbsolutePath() + "/" + dirOldSObj + "/" + idOldSobj + "/");
                                    //System.out.println("path:"+fileOldSObjPath.getAbsolutePath());
                                    if(fileOldSObjPath.isDirectory() && fileOldSObjPath.exists()) {
                                        boolean r = SWBUtils.IO.copyStructure(targetDir.getAbsolutePath() + "/" + dirOldSObj + "/" + idOldSobj + "/", SWBPortal.getWorkPath() + newSObjPath+"/");
                                        //System.out.println(r+" Copiando estructura de "+targetDir.getAbsolutePath() + "/" + dirOldSObj + "/" + idOldSobj + "/  a  "+SWBPortal.getWorkPath() + newSObjPath+"/");
                                        //System.out.println(idOldSobj+"-->"+_elements.get(nodeUri).getId());
                                    }
                                }
                            }
                        }
                    }
                }
            }
            
            //Crear roles
            elements = data.getJSONArray("siteRoles");
            if (elements != null) {
                for (int i = 0; i < elements.length(); i++) {
                    JSONObject processJson = elements.getJSONObject(i);
                    String suri = processJson.getString("uri");
                    SemanticObject sobj = SemanticObject.createSemanticObject(suri);
                    if (sobj == null) {
                        String id = suri.substring(suri.lastIndexOf(":")+1, suri.length());
                        Role role = site.getUserRepository().createRole(id);
                        role.setTitle(processJson.optString(Descriptiveable.swb_title.getPropId(), ""));
                        _elements.put(suri, role);
                    } else {
                        _elements.put(suri, sobj.createGenericInstance());
                    }
                }
            }
            
            //Crear elementos
            elements = data.getJSONArray("processes");
            if (elements != null) {
                for (int i = 0; i < elements.length(); i++) {
                    JSONObject processJson = elements.getJSONObject(i);
                    String suri = processJson.getString("uri");
                    String clsUri = processJson.getString("sclass");
                    go = getGenericObject(suri, clsUri, model);
                    
                    if (go != null) {
                        _elements.put(suri, go);
                        
                        //Crear objetos de datos del proceso
                        JSONArray processObjs = processJson.getJSONArray("data");
                        for (int j = 0; j < processObjs.length(); j++) {
                            JSONObject processObjJson = processObjs.getJSONObject(j);
                            String doUri = processObjJson.getString("uri");
                            String doClsUri = processObjJson.getString("sclass");
                            GenericObject gdo = getGenericObject(doUri, doClsUri, model);
                            if (gdo != null) _elements.put(doUri, gdo);
                        }
                        
                        //Crear nodos del proceso
                        processObjs = processJson.getJSONArray("nodes");
                        for (int j = 0; j < processObjs.length(); j++) {
                            JSONObject processObjJson = processObjs.getJSONObject(j);
                            String doUri = processObjJson.getString("uri");
                            String doClsUri = processObjJson.getString("sclass");
                            GenericObject gdo = getGenericObject(doUri, doClsUri, model);
                            if (gdo != null) _elements.put(doUri, gdo);
                        }
                        
                        //Crear conexiones del proceso
                        processObjs = processJson.getJSONArray("conns");
                        for (int j = 0; j < processObjs.length(); j++) {
                            JSONObject processObjJson = processObjs.getJSONObject(j);
                            String doUri = processObjJson.getString("uri");
                            String doClsUri = processObjJson.getString("sclass");
                            GenericObject gdo = getGenericObject(doUri, doClsUri, model);
                            if (gdo != null) _elements.put(doUri, gdo);
                        }
                    }
                }
            }
            
            //Establecer propiedades de los elementos
            if (elements != null) {
                for (int i = 0; i < elements.length(); i++) {
                    JSONObject processJson = elements.getJSONObject(i);
                    String suri = processJson.getString("uri");
                    go = _elements.get(suri);
                    
                    if (go != null) {
                        Process process = (Process)go;
                        String pgUri = processJson.getJSONObject(Process.swp_processGroup.getPropId()).getString("uri");
                        ProcessGroup pGroup = (ProcessGroup) getGenericObject(pgUri, ProcessGroup.sclass.getURI(), model);
                        setBaseProperties(pGroup, processJson.getJSONObject(Process.swp_processGroup.getPropId()));
                        process.setProcessGroup(pGroup);
                        
                        setProcessProperties(go, processJson, _elements, site);
                        
                        //Crear objetos de datos del proceso
                        JSONArray processObjs = processJson.getJSONArray("data");
                        for (int j = 0; j < processObjs.length(); j++) {
                            JSONObject processObjJson = processObjs.getJSONObject(j);
                            String doUri = processObjJson.getString("uri");
                            GenericObject gdo = _elements.get(doUri);
                            setProcessProperties(gdo, processObjJson, _elements, site);
                        }
                        
                        //Crear nodos del proceso
                        processObjs = processJson.getJSONArray("nodes");
                        for (int j = 0; j < processObjs.length(); j++) {
                            JSONObject node = processObjs.getJSONObject(j);
                            String doUri = node.getString("uri");
                            GenericObject gdo = _elements.get(doUri);
                            setProcessProperties(gdo, node, _elements, site);
                            if (gdo instanceof Resourceable) { //Copiar fileSystem
                                JSONArray resources = node.getJSONArray("resources");
                                for (int k = 0; k < resources.length(); k++) {
                                    JSONObject resource = resources.getJSONObject(k);
                                    String nodeUri = resource.getString("uri");
                                    int pos = nodeUri.indexOf("#");
                                    
                                    if (_elements.get(nodeUri) != null) {
                                        String newSObjPath = _elements.get(nodeUri).getSemanticObject().getWorkPath();
                                        if(pos > -1) {
                                            int pos1 = nodeUri.indexOf(":", pos);
                                            if(pos1 > -1) {
                                                String dirOldSObj = nodeUri.substring(pos + 1, pos1);
                                                String idOldSobj = nodeUri.substring(pos1 + 1);
                                                File fileOldSObjPath = new File(targetDir.getAbsolutePath() + "/" + dirOldSObj + "/" + idOldSobj + "/");
                                                if(fileOldSObjPath.isDirectory() && fileOldSObjPath.exists()) {
                                                    boolean r = SWBUtils.IO.copyStructure(targetDir.getAbsolutePath() + "/" + dirOldSObj + "/" + idOldSobj + "/", SWBPortal.getWorkPath() + newSObjPath+"/");
                                                    //System.out.println(r+" Copiando estructura de "+targetDir.getAbsolutePath() + "/" + dirOldSObj + "/" + idOldSobj + "/  a  "+SWBPortal.getWorkPath() + newSObjPath+"/");
                                                    //System.out.println(idOldSobj+"-->"+_elements.get(nodeUri).getId());
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        
                        //Crear conexiones del proceso
                        processObjs = processJson.getJSONArray("conns");
                        for (int j = 0; j < processObjs.length(); j++) {
                            JSONObject conn = processObjs.getJSONObject(j);
                            String doUri = conn.getString("uri");
                            GenericObject gdo = _elements.get(doUri);
                            setProcessProperties(gdo, conn, _elements, site);
                        }
                    }
                }
            }
        } catch (Exception ex) {
            log.error(ex);
        }
        
        //                    Iterator props = tool.keys();
        //                    while (props.hasNext()) {
        //                        String k = (String) props.next();
        //                        if (!k.equals("class") && !k.equals("uri")) {
        //                            System.out.println(k + ":" + tool.get(k));
        //                        }
        //                    }
    }
    
    private void setProcessProperties(GenericObject go, JSONObject obj, HashMap<String,GenericObject> elements, ProcessSite site) throws JSONException {
        setBaseProperties(go, obj);
        
        if (go instanceof GraphicalElement) {
            GraphicalElement temp = (GraphicalElement) go;
            
            temp.setX(obj.getInt(GraphicalElement.swp_x.getPropId()));
            temp.setY(obj.getInt(GraphicalElement.swp_y.getPropId()));
            temp.setWidth(obj.getInt(GraphicalElement.swp_width.getPropId()));
            temp.setHeight(obj.getInt(GraphicalElement.swp_height.getPropId()));
            temp.setLabelSize(obj.getInt(GraphicalElement.swp_labelSize.getPropId()));
            if (!obj.optString(GraphicalElement.swp_container.getPropId(), "").equals("")) {
                GenericObject container = elements.get(obj.getString(GraphicalElement.swp_container.getPropId()));
                if (container != null)  {
                    temp.setContainer((Containerable)container);
                }
            }
            if (!obj.optString(GraphicalElement.swp_parent.getPropId(), "").equals("")) {
                GenericObject parent = elements.get(obj.getString(GraphicalElement.swp_parent.getPropId()));
                if (parent != null)  {
                    temp.setParent((GraphicalElement)parent);
                }
            }
        }
        
        if (go instanceof ResourceAssignmentable) {
            if (!obj.optString(ResourceAssignmentable.swp_resourceAssignationRule.getPropId(), "").equals("")) ((ResourceAssignmentable)go).setResourceAssignationRule(obj.getInt(ResourceAssignmentable.swp_resourceAssignationRule.getPropId()));
        }

        if (go instanceof CallActivity) {
            if (!obj.optString(CallActivity.swp_calledElement.getPropId(), "").equals("")) {
                GenericObject called = elements.get(obj.getString("uri"));
                if (called != null) {
                    ((CallActivity)go).setCalledElement((Callable)called);
                }
            }
        }

        if (go instanceof ActivityConfable) {
            ((ActivityConfable)go).setForCompensation(obj.getBoolean(ActivityConfable.swp_forCompensation.getPropId()));
//            if (temp.getLoopCharacteristics() != null) ret.put(ActivityConfable.swp_loopCharacteristics.getPropId(), getElementJSON(temp.getLoopCharacteristics()));
//            else ret.put(ActivityConfable.swp_loopCharacteristics.getPropId(), "");
        }

        if (go instanceof ActionCodeable) {
            ((ActionCodeable)go).setActionCode(obj.optString(ActionCodeable.swp_actionCode.getPropId(), ""));
        }
        
        if (go instanceof CatchMessageable) {
            CatchMessageable temp = (CatchMessageable)go;
            JSONArray mappings = obj.getJSONArray(CatchMessageable.swp_hasItemAwareMapping.getPropId());
            
            for (int i = 0; i < mappings.length(); i++) {
                JSONObject mapping = mappings.getJSONObject(i);
                String localUri = mapping.optString(ItemAwareMapping.swp_localItemAware.getPropId());
                String remoteUri = mapping.optString(ItemAwareMapping.swp_remoteItemAware.getPropId());
                if (!localUri.equals("") && !remoteUri.equals("")) {
                    if (elements.get(localUri) != null && elements.get(remoteUri) != null) {
                        ItemAwareMapping mp = ItemAwareMapping.ClassMgr.createItemAwareMapping(site);
                        mp.setLocalItemAware((ItemAware)elements.get(localUri));
                        mp.setRemoteItemAware((ItemAware)elements.get(remoteUri));
                        temp.addItemAwareMapping(mp);
                    }
                }
            }
        }

        if (go instanceof ThrowMessageable) {
            ThrowMessageable temp = (ThrowMessageable)go;
            JSONArray messageItems = obj.getJSONArray(ThrowMessageable.swp_hasMessageItemAware.getPropId());

            for (int i = 0; i < messageItems.length(); i++) {
                String msgItem = messageItems.getString(i);
                GenericObject item = elements.get(msgItem);
                if (item != null) {
                    temp.addMessageItemAware((ItemAware)item);
                }
            }
        }

        if (go instanceof ProcessRuleRefable) {
            ProcessRuleRefable temp = (ProcessRuleRefable)go;
            JSONArray ruleRefs = obj.getJSONArray(ProcessRuleRefable.swp_hasProcessRuleRef.getPropId());

            for (int i = 0; i < ruleRefs.length(); i++) {
                JSONObject ruleRef = ruleRefs.getJSONObject(i);
                if (!ruleRef.optString(ProcessRuleRef.swp_processRule.getPropId(), "").equals("")) {
                    GenericObject rule = elements.get(ruleRef.getString(ProcessRuleRef.swp_processRule.getPropId()));
                    if (rule != null) {
                        ProcessRuleRef ref = ProcessRuleRef.ClassMgr.createProcessRuleRef(site);
                        ref.setProcessRule((ProcessRule)rule);
                        ref.setActive(ruleRef.getBoolean(ProcessRuleRef.swb_active.getPropId()));
                        temp.addProcessRuleRef(ref);
                    }
                }
            }
        }
        
        if (go instanceof RoleRefable) {
            RoleRefable temp = (RoleRefable)go;
            JSONArray roleRefs = obj.optJSONArray(RoleRefable.swb_hasRoleRef.getPropId());
            if (roleRefs != null) {
                for (int i = 0; i < roleRefs.length(); i++) {
                    JSONObject roleRef = roleRefs.getJSONObject(i);
                    if (!roleRef.optString(RoleRef.swb_role.getPropId(), "").equals("")) {
                        GenericObject role = elements.get(roleRef.getString(RoleRef.swb_role.getPropId()));
                        if (role != null) {
                            RoleRef ref = RoleRef.ClassMgr.createRoleRef(site);
                            ref.setRole((Role)role);
                            ref.setActive(roleRef.getBoolean(RoleRef.swb_active.getPropId()));
                            temp.addRoleRef(ref);
                        }
                    }
                }
            }
        }

        if (go instanceof ProcessPeriodRefable) {
            ProcessPeriodRefable temp = (ProcessPeriodRefable)go;
            JSONArray periodRefs = obj.getJSONArray(ProcessPeriodRefable.swp_hasProcessPeriodRef.getPropId());

            for (int i = 0; i < periodRefs.length(); i++) {
                JSONObject periodRef = periodRefs.getJSONObject(i);
                if (!periodRef.optString(ProcessPeriodRef.swp_processPeriod.getPropId(), "").equals("")) {
                    GenericObject period = elements.get(periodRef.getString(ProcessPeriodRef.swp_processPeriod.getPropId()));
                    if (period != null) {
                        ProcessPeriodRef ref = ProcessPeriodRef.ClassMgr.createProcessPeriodRef(site);
                        ref.setProcessPeriod((ProcessPeriod)period);
                        ref.setActive(periodRef.getBoolean(ProcessPeriodRef.swb_active.getPropId()));
                        temp.addProcessPeriodRef(ref);
                    }
                }
            }
        }

        if (go instanceof Collectionable) {
            ((Collectionable)go).setCollection(obj.getBoolean(Collectionable.swp_collection.getPropId()));
        }

        if (go instanceof ScriptTask) {
            ((ScriptTask)go).setScriptCode(obj.optString(ScriptTask.swp_scriptCode.getPropId(), ""));
        }

        if (go instanceof ServiceTask) {
            if (!obj.optString(ServiceTask.swp_processService.getPropId(), "").equals("")) {
                JSONObject ps = obj.getJSONObject(ServiceTask.swp_processService.getPropId());
                String psUri = ps.getString("uri");
                String psClsUri = ps.getString("sclass");
                GenericObject gps = getGenericObject(psUri, psClsUri, site.getSemanticObject().getModel());
                
                if (gps != null) {
                    setBaseProperties(gps, ps);
                    if (gps instanceof TransformRepositoryFile) {
                        String ftemplate = ps.getString(TransformRepositoryFile.swp_transformRepFileTemplate.getPropId());
                        GenericObject template = elements.get(ftemplate);
                        if (template != null) {
                            ((TransformRepositoryFile)gps).setFileTemplate((ProcessFileTemplate)template);
                        }
                        ((TransformRepositoryFile)gps).setNodeVarName(ps.optString(TransformRepositoryFile.swp_transformRepNodeVarName.getPropId(), ""));
                    } else if (gps instanceof SendMail) {
                        ((SendMail)gps).setContent(ps.optString(SendMail.swp_sendMailContent.getPropId(), ""));
                        ((SendMail)gps).setFrom(ps.optString(SendMail.swp_sendMailFrom.getPropId(), ""));
                        ((SendMail)gps).setSubject(ps.optString(SendMail.swp_sendMailSubject.getPropId(), ""));
                        ((SendMail)gps).setTo(ps.optString(SendMail.swp_sendMailTo.getPropId(), ""));
                    } else if (gps instanceof SparQLQuery) {
                        ((SparQLQuery)gps).setCode(ps.optString(SparQLQuery.swp_sparQLCode.getPropId()));
                        ((SparQLQuery)gps).setQuery(ps.optString(SparQLQuery.swp_sparQLQuery.getPropId()));
                    } else if (gps instanceof SQLQuery) {
                        if (!ps.optString(SQLQuery.swp_dbConnection.getPropId(), "").equals("")) {
                            GenericObject dbconn = elements.get(ps.getString(SQLQuery.swp_dbConnection.getPropId()));
                            if (dbconn != null) ((SQLQuery)gps).setDbConnection((DBConnection) dbconn);
                        }
                    } else if (gps instanceof StoreRepositoryFile) {
                        ((StoreRepositoryFile)gps).setNodeVarName(ps.optString(StoreRepositoryFile.swp_storeRepNodeVarName.getPropId(), ""));
                    } /*else if (gps instanceof WebServiceInvoker) {

                    }*/
                    ((ServiceTask)go).setProcessService((ProcessService)gps);
                }
            }
        }
//            if (((ServiceTask)ge).getProcessService() != null) ret.put(ServiceTask.swp_processService.getPropId(), getElementJSON(((ServiceTask)ge).getProcessService()));
//            else ret.put(ServiceTask.swp_processService.getPropId(), "");
//        }

        if (go instanceof UserTask) {
            UserTask temp = (UserTask)go;
            temp.setNotificationTime(obj.getInt(UserTask.swp_notificationTime.getPropId()));

            JSONArray actsArray = obj.getJSONArray(UserTask.swp_hasTaskAction.getPropId());
            for (int i = 0; i < actsArray.length(); i++) {
                JSONObject act = actsArray.getJSONObject(i);
                TaskAction tact = TaskAction.ClassMgr.createTaskAction(site);
                tact.setCode(act.optString(TaskAction.swp_taskActionCode.getPropId()));
                tact.setActionType(act.getInt(TaskAction.swp_taskActionType.getPropId()));
                temp.addTaskAction(tact);
            }
        }

        if (go instanceof CatchEvent) {
            ((CatchEvent)go).setInterruptor(obj.getBoolean(CatchEvent.swp_interruptor.getPropId()));
        }
//
//        if (ge instanceof MultipleIntermediateThrowEvent) {
//            MultipleIntermediateThrowEvent temp = (MultipleIntermediateThrowEvent)ge;
//            JSONArray events = new JSONArray();
//
//            Iterator<IntermediateThrowEvent> itm = temp.listThrowEventses();
//            while (itm.hasNext()) {
//                IntermediateThrowEvent event = itm.next();
//                events.put(event.getURI());
//            }
//
//            ret.put(MultipleIntermediateThrowEvent.swp_hasThrowEvents.getPropId(), events);
//        }
//
//        if (ge instanceof Gateway) {
//            ret.put(Gateway.swp_gatewayMode.getPropId(), ((Gateway)ge).getGatewayMode());
//            if (ge instanceof ComplexGateway) {
//                ret.put(ComplexGateway.swp_startTokens.getPropId(), ((ComplexGateway)ge).getStartTokens());
//            }
//        }
//
        if (go instanceof DataStore) {
            ((DataStore)go).setDataObjectId(obj.optString(DataStore.swp_dataObjectId.getPropId(), ""));
            ((DataStore)go).setInitializationCode(obj.optString(DataStore.swp_dataObjectInitializationCode.getPropId(), ""));
        }
        
        if (go instanceof Resourceable) {
            JSONArray resources = obj.getJSONArray("resources");
            for (int i = 0; i < resources.length(); i++) {
                JSONObject resource = resources.getJSONObject(i);
                ResourceType resType = ResourceType.ClassMgr.getResourceType(resource.getString("resTypeId"), site);
                if (resType == null) { // Si no existe el tipo, crearlo
                    resType = ResourceType.ClassMgr.createResourceType(resource.getString("resTypeId"), site);
                    resType.setTitle(resource.optString("resTypeTitle"));
                    resType.setResourceMode(resource.getInt("resTypeMode"));
                    resType.setResourceClassName(resource.getString("resTypeClass"));
                }
                
                Resource res = null;
                boolean create = true;
                if (resource.optString("resTypeId", "").equals("ProcessForm")) {
                    Iterator<Resource> resit = ((Resourceable)go).listResources();
                    while (resit.hasNext() && create) {
                        Resource resource1 = resit.next();
                        if (resource1.getResourceType().getId().equals("ProcessForm")) {
                            create = false;
                            res = resource1;
                        }
                    }
                }
                
                if (create) {
                    res = Resource.ClassMgr.createResource(site);
                    res.setResourceType(resType);
                    ((Resourceable)go).addResource(res);
                }
                SWBResource swbres = SWBPortal.getResourceMgr().getResource(res);
                if (swbres instanceof Versionable) {
                    Versionable ver = (Versionable)swbres;
                    if (ver.getActualVersion() == null) {
                        if (!resource.optString(Versionable.swb_actualVersion.getPropId(), "").equals("")) {
                            JSONObject versionInfo = resource.getJSONObject(Versionable.swb_actualVersion.getPropId());
                            VersionInfo vi = swbres.getResourceBase().getWebSite().createVersionInfo();
                            vi.setVersionFile(versionInfo.getString(VersionInfo.swb_versionFile.getPropId()));
                            vi.setVersionNumber(1);
                            vi.setVersionComment(versionInfo.getString(VersionInfo.swb_versionComment.getPropId()));
                            ver.setActualVersion(vi);
                            ver.setLastVersion(vi);
                        } 
                    }
                }
                
                setBaseProperties(res, resource);
                elements.put(resource.getString("uri"), res);
           }
        }
        
        if (go instanceof Process) {
            Process process = (Process)go;
            process.setFilterByOwnerUserGroup(obj.getBoolean(Process.swp_filterByOwnerUserGroup.getPropId()));
                        
            if (!obj.optString(Process.swp_assigmentNotificationTemplate.getPropId(), "").equals("")) {
                GenericObject template = elements.get(obj.getString(Process.swp_assigmentNotificationTemplate.getPropId()));
                if (template != null) {
                    process.setAssigmentNotificationTemplate((NotificationTemplate)template);
                }
            }
            if (!obj.optString(Process.swp_delayNotificationTemplate.getPropId(), "").equals("")) {
                GenericObject template = elements.get(obj.getString(Process.swp_delayNotificationTemplate.getPropId()));
                if (template != null) {
                    process.setDelayNotificationTemplate((NotificationTemplate)template);
                }
            }
//                        if (!processJson.optString(Process.swp_administrationRole.getPropId(), "").equals("")) {
//                            SemanticObject so = SemanticObject.createSemanticObject(processJson.getString(Process.swp_administrationRole.getPropId()));
//                            if (so != null) {
//                                Role role = (Role)so.createGenericInstance();
//                                process.setAdministrationRole(role);
//                            }
//                        }
//                        if (!processJson.optString(Process.swp_notificationRole.getPropId(), "").equals("")) {
//                            SemanticObject so = SemanticObject.createSemanticObject(processJson.getString(Process.swp_notificationRole.getPropId()));
//                            if (so != null) {
//                                Role role = (Role)so.createGenericInstance();
//                                process.setNotificationRole(role);
//                            }
//                        }
//                        if (!processJson.optString(Process.swp_parentWebPage.getPropId(), "").equals("")) {
//                            SemanticObject so = SemanticObject.createSemanticObject(processJson.getString(Process.swp_parentWebPage.getPropId()));
//                            if (so != null) {
//                                WebPage wp = (WebPage)so.createGenericInstance();
//                                process.setParentWebPage(wp);
//                            }
//                        }
        }
        
        if (go instanceof ItemAware) {
            ItemAware temp = (ItemAware)go;
            if (!obj.optString(ItemAware.swp_dataObjectClass.getPropId(), "").equals("")) {
                SemanticObject doCls = SemanticObject.createSemanticObject(obj.getString(ItemAware.swp_dataObjectClass.getPropId()));
                if (doCls != null) {
                    temp.setDataObjectClass(doCls);
                }
            }
            if (!obj.optString(ItemAware.swp_name.getPropId(), "").equals("")) temp.setName(obj.getString(ItemAware.swp_name.getPropId()));
        }
        
        if (go instanceof ConnectionObject) {
            ConnectionObject temp = (ConnectionObject)go;
            String sourceUri = obj.getString(ConnectionObject.swp_source.getPropId());
            String targetUri = obj.getString(ConnectionObject.swp_target.getPropId());
            
            if (elements.get(sourceUri) != null && elements.get(targetUri) != null) {
                temp.setSource((GraphicalElement)elements.get(sourceUri));
                temp.setTarget((GraphicalElement)elements.get(targetUri));
                temp.setConnectionPoints(obj.optString(ConnectionObject.swp_connectionPoints.getPropId(), ""));
            }
        }
    }
    
    private void setBaseProperties(GenericObject go, JSONObject obj) {
        try {
            if (go instanceof Descriptiveable) {
                Descriptiveable temp = (Descriptiveable)go;
                temp.setTitle(obj.optString(Descriptiveable.swb_title.getPropId(), ""));
                temp.setDescription(obj.optString(Descriptiveable.swb_description.getPropId(), ""));
            }
            
            if (go instanceof Activeable) ((Activeable)go).setActive(obj.getBoolean(Activeable.swb_active.getPropId()));
            if (go instanceof XMLable) 
            {
                if (!obj.optString(XMLable.swb_xml.getPropId(), "").equals("")) {
                    ((XMLable)go).setXml(obj.optString(XMLable.swb_xml.getPropId(), ""));
                }
            }
            if (go instanceof Expirable) {
                if (!obj.optString(Expirable.swb_expiration.getPropId(), "").equals("")) {
                    ((Expirable)go).setExpiration(SWBUtils.TEXT.iso8601DateParse(obj.getString(Expirable.swb_expiration.getPropId())));
                }
            }
            if (go instanceof Callable) ((Callable)go).setCallable(obj.getBoolean(Callable.swp_callable.getPropId()));
            if (go instanceof Sortable) {
                if (obj.optInt(Sortable.swb_index.getPropId(), -1) != -1) {
                    ((Sortable)go).setIndex(obj.getInt(Sortable.swb_index.getPropId()));
                }
            }
            
            if (go instanceof StoreRepositoryNodeable) {
                StoreRepositoryNodeable temp = (StoreRepositoryNodeable)go;
                if (!obj.optString(StoreRepositoryNodeable.swp_storeRepNodeId.getPropId(), "").equals("")) {
                    temp.setNodeId(obj.getString(StoreRepositoryNodeable.swp_storeRepNodeId.getPropId()));
                }
                if (!obj.optString(StoreRepositoryNodeable.swp_storeRepNodeName.getPropId(), "").equals("")) {
                    temp.setNodeName(obj.getString(StoreRepositoryNodeable.swp_storeRepNodeName.getPropId()));
                }
            }
        } catch (Exception ex) {
                log.error(ex);
        }
    }
    
    private GenericObject getGenericObject(String suri, String clsUri, SemanticModel model) {
        GenericObject go = null;
        try {
            String id = suri.substring(suri.lastIndexOf(":")+1, suri.length());
            SemanticClass scls = SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass(clsUri);
            SemanticObject so = model.getSemanticObject(model.getObjectUri(id, scls));

            if (so == null) { //El objeto no existe
                go = model.createGenericObject(model.getObjectUri(id, scls), scls);
            } else { //El objeto existe
                go = model.getGenericObject(model.getObjectUri(id, scls));
            }
        } catch (Exception ex) {
            log.error(ex);
        }
        return go;
    }
}
