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
package org.semanticwb.process.resources.reports;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import net.sf.jxls.exception.ParsePropertyException;
import net.sf.jxls.transformer.XLSTransformer;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.Role;
import org.semanticwb.model.RoleRef;
import org.semanticwb.model.User;
import org.semanticwb.model.UserRepository;
import org.semanticwb.model.WebSite;
import org.semanticwb.process.model.GraphicalElement;
import org.semanticwb.process.model.UserTask;
import org.semanticwb.process.model.Process;

/**
 *
 * @author hasdai
 */
public class TaskRoleSegregationReport {
    public static Logger log = SWBUtils.getLogger(TaskRoleSegregationReport.class);
    
    private static Comparator<TaskBean> taskNameComparator = new Comparator<TaskBean>() {
        @Override
        public int compare(TaskBean o1, TaskBean o2) {
            return o1.getTaskName().compareTo(o2.getTaskName());
        }
    };
    
    private static Comparator<ProcessBean> processNameComparator = new Comparator<ProcessBean>() {
        @Override
        public int compare(ProcessBean o1, ProcessBean o2) {
            return o1.getProcessName().compareTo(o2.getProcessName());
        }
    };
    
    public TaskRoleSegregationReport() {
    }
    
    public static ArrayList<ProcessBean> generateBeans(WebSite site) {
        ArrayList<ProcessBean> processBeans = new ArrayList<ProcessBean>();
        
        //Obtener mapa de usuarios por Rol
        UserRepository userRep = site.getUserRepository();
        HashMap<String, ArrayList<String>> userMap = new HashMap<String, ArrayList<String>>();
        Iterator<User> users = userRep.listUsers();
        while (users.hasNext()) {
            User user = users.next();
            if (user.isActive() && user.listRoles().hasNext()) {
                Iterator<Role> roles = user.listRoles();
                while (roles.hasNext()) {
                    Role role = roles.next();
                    if (userMap.get(role.getTitle()) != null) {
                        ArrayList<String> ubeans = userMap.get(role.getTitle());
                        ubeans.add(user.getFullName());
                        userMap.put(role.getTitle(), ubeans);
                    } else {
                        ArrayList<String> ubeans = new ArrayList<String>();
                        ubeans.add(user.getFullName());
                        userMap.put(role.getTitle(), ubeans);
                    }
                }
            }
        }
        
        Iterator<Process> processes = Process.ClassMgr.listProcesses(site);
        while (processes.hasNext()) {
            Process process = processes.next();
            if (process.isValid()) {
                ArrayList<String> sproles = new ArrayList<String>();
                ArrayList<TaskBean> ptasks = new ArrayList<TaskBean>();
                
                //Obtener la lista de tareas y roles del proceso
                Iterator<GraphicalElement> containeds = process.listAllContaineds();
                while (containeds.hasNext()) {
                    GraphicalElement contained = containeds.next();
                    if (contained instanceof UserTask) {
                        UserTask uTask = (UserTask) contained;
                        TaskBean taskBean = new TaskBean(uTask.getTitle());
                        
                        Iterator<RoleRef> roleRefs = uTask.listRoleRefs();
                        while (roleRefs.hasNext()) {
                            //Obtener el rol asociado a la tarea
                            RoleRef roleRef = roleRefs.next();
                            Role role = roleRef.getRole();
                            if (role != null) {
                                String roleTitle = role.getTitle();
                                if (roleTitle != null && !sproles.contains(roleTitle)) {
                                    sproles.add(roleTitle);
                                }
                                taskBean.addRole(roleTitle);
                            }
                        }
                        ptasks.add(taskBean);
                    }
                }
                
                //Ordenar roles por nombre
                Collections.sort(sproles);
                
                //Crear lista de roles del proceso
                ArrayList<RoleBean> proles = new ArrayList<RoleBean>();
                Iterator<String> itRoles = sproles.iterator();
                while (itRoles.hasNext()) {
                    String rName = itRoles.next();
                    proles.add(new RoleBean(rName));
                }
                
                Iterator<TaskBean> itTasks = ptasks.iterator();
                while (itTasks.hasNext()) {
                    TaskBean pTask = itTasks.next();
                    itRoles = sproles.iterator();
                    while (itRoles.hasNext()) {
                        String roleBean = itRoles.next();
                        if (pTask.hasRole(roleBean)) {
                            String uNames = "";
                            if (userMap.get(roleBean) != null) {
                                Iterator<String> usersPerRole = userMap.get(roleBean).iterator();
                                while (usersPerRole.hasNext()) {
                                    String string = usersPerRole.next();
                                    uNames += string;
                                    if (usersPerRole.hasNext()) uNames += ", ";
                                }
                            }
                            pTask.addUser(new UserBean(uNames));
                        } else {
                            pTask.addUser(new UserBean(""));
                        }
                    }
                }
                
                //Ordenar tareas por nombre
                Collections.sort(ptasks, taskNameComparator);
                ProcessBean pBean = new ProcessBean(process.getTitle());
                pBean.setRoles(proles);
                pBean.setTasks(ptasks);
                processBeans.add(pBean);
            }
        }
        
        //Ordenar procesos por nombre
        Collections.sort(processBeans, processNameComparator);
        return processBeans;
    }
    
    public static void generateReport(String templatePath, String outPath, ArrayList<ProcessBean> beansList) {
        Map mbeans = new HashMap();
        mbeans.put("bean", beansList);
        
        XLSTransformer transformer = new XLSTransformer();
        try {
            try {
                transformer.transformXLS(templatePath, mbeans, outPath);
            } catch (InvalidFormatException ex) {
                log.error(ex);
            }
        } catch (ParsePropertyException ex) {
            log.error(ex);
        } catch (IOException ex) {
            log.error(ex);
        }
    }
}
