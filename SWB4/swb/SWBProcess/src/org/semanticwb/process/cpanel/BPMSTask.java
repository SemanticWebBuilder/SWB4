/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.process.cpanel;

import java.text.SimpleDateFormat;
import java.util.*;
//import org.semanticwb.portal.*;
import org.semanticwb.portal.api.*;
//import org.semanticwb.process.*;
import org.semanticwb.process.model.*;
import org.semanticwb.model.*;
//import org.semanticwb.*;

/**
 *
 * @author haydee.vertti
 */
public class BPMSTask
{
    public static class ClassMgr
    {
        public final static String DATE_FORMAT = "dd/MM/yyyy";
        public final static int SORT_BY_DATE = 0;
        public final static int SORT_BY_TITLE = 1;
        public final static int SORT_BY_PROCESS = 2;
        //public final static int SORT_BY_PRIORITY = 4; Al descomentar, aumentar MAX_SORT
        public final static int MAX_SORT = 3;

        /**
        * Recibe una fecha en formato YYYY-MM-DD (del control Dojo) y lo convierte
        * a dd/MM/yyyy
        *
        * @param            strDate String
        * @return      		String con formato dd/MM/yyyy
        * @see
        */
        public static String changeDateFormat(String strDate)
        {
            String strReturn = "";
            try
            {
                String[] strArray = strDate.split("-");
                strReturn = strArray[2] + "/" + strArray[1] + "/" + strArray[0];
            } catch(Exception e){
              //log.error("Error en BPMSTask.changeDateFormat", e);
                System.out.println("Error en BPMSTask.changeDateFormat:"
                        + e.getMessage());
            }
            return strReturn;
        }

        /**
         * Convierte un objeto String (con una fecha) en un objeto Calendar.
         * Se usa para manipular la fecha proporcionada por el usuario.
         *
         * @param               String con fecha en formato dd/MM/yyyy (DATE_FORMAT)
         * @return      		java.util.Calendar con la fecha correspondiente al
         *                      String proporcionado
         * @see
         */
        public static java.util.Calendar dateStringToCalendar(String strDate)
                  throws java.text.ParseException {
            java.util.Calendar cal = java.util.Calendar.getInstance();
            try
            {
                java.text.SimpleDateFormat sdf =
                        new SimpleDateFormat(DATE_FORMAT);
                Date d1 = sdf.parse(strDate);
                cal.setTime(d1);
            } catch(Exception e){
              //log.error("Error en BPMSTask.dateStringToCalendar", e);
                System.out.println("Error en BPMSTask.dateStringToCalendar:" +
                        e.getMessage());
            }
            return cal;
        }

        /**
        * Genera un vector con todas las tareas (FlowNodeInstance) de vTasks
        * cuya fecha de creacion cumple con los parametros seleccionados.
        * Las fechas de filterValue estan separadas mediante pipes
        *
        * @param            vTasks Vector
        * @param            filterValue String
        * @return      		Vector con objetos FlowNodeInstance
        * @see
        */
        public static Vector filterTasksByDate(Vector vTasks, String filterValue)
        {
            Vector vFilteredTasks = new Vector();
            int index = 0;
            try
            {
                String [] strDates = filterValue.split("\\|");
                java.util.Calendar dtInitial = dateStringToCalendar(strDates[0]);
                //System.out.println("....dtInitial:" + dtInitial.getTime());
                java.util.Calendar dtFinal = null;
                if(strDates.length>1){
                    dtFinal = dateStringToCalendar(strDates[1]);
                    dtFinal.add(java.util.Calendar.HOUR, 23);
                    dtFinal.add(java.util.Calendar.MINUTE, 59);
                    //System.out.println("....dtFinal:" + dtFinal.getTime());
                }
                for(int i=0; i<vTasks.size();i++)
                {
                    FlowNodeInstance fobi = (FlowNodeInstance)vTasks.get(i);
                    Date dtCreated = fobi.getCreated();
                    //System.out.println("....dtCreated:" + dtCreated.toString());
                    if(strDates.length>1)
                    {
                        if(dtInitial.equals(dtFinal))
                        {
                            if((dtCreated.after(dtInitial.getTime())
                                    || dtCreated.equals(dtInitial.getTime()))
                                    &&
                                    (dtCreated.before(dtFinal.getTime())))
                            {
                                vFilteredTasks.add(index,fobi);
                                index++;
                            }
                        } else {
                            if((dtCreated.after(dtInitial.getTime())
                                    || dtCreated.equals(dtInitial.getTime()))
                                    &&
                                    (dtCreated.before(dtFinal.getTime())
                                    || dtCreated.equals(dtFinal.getTime())))
                            {
                                vFilteredTasks.add(index,fobi);
                                index++;
                            }
                        }
                    } else {
                        if(dtCreated.after(dtInitial.getTime())
                                || dtCreated.equals(dtInitial.getTime()))
                        {
                            vFilteredTasks.add(index,fobi);
                            index++;
                        }
                    }
                }
            }
            catch(Exception e){
                System.out.println("Error en BPMSTask.filterByDate:" +
                        e.getMessage());
                //log.error("Error en BPMSTask.filterByDate", e);
            }
            return vFilteredTasks;
        }

        /**
        * Genera un vector con todas las tareas (FlowNodeInstance) de vTasks que
        * correspondan a la definicion de proceso seleccionada
        *
        * @param            vTasks Vector
        * @param            filterValue String
        * @return      		Vector de objetos FlowNodeInstance
        * @see
        */
        public static Vector filterTasksByProcess(Vector vTasks,
                String filterValue)
        {
            Vector vFilteredTasks = new Vector();
            int index = 0;
            try
            {
                if(filterValue.length()>0)
                {
                    Vector vFilterValues =
                            BPMSProcessInstance.ClassMgr.stringToVector(filterValue);
                    for(int i=0; i<vTasks.size();i++)
                    {
                        FlowNodeInstance fobi = (FlowNodeInstance)vTasks.get(i);
                        ProcessInstance fpinst = fobi.getProcessInstance();
                        if(null!=fpinst){
                            org.semanticwb.process.model.Process fproc = (org.semanticwb.process.model.Process)fpinst.getProcessType();
                            if(null!=fproc){
                                if(vFilterValues.contains(fproc.getURI()))
                                {
                                    vFilteredTasks.add(index,fobi);
                                    index++;
                                }
                            }
                        }
                        /*
                        FlowNode fobiType = (FlowNode)fobi.getFlowNodeType();
                        GraphicalElement parentProcess = fobiType.getParent();
                        if(vFilterValues.contains(parentProcess.getURI()))
                        {
                            vFilteredTasks.add(index,fobi);
                            index++;
                        }
                         */
                    }
                } else {
                    vFilteredTasks = vTasks;
                }
            }catch(Exception e){
                System.out.println("Error en BPMSTask.filterByProcess:" +
                        e.getMessage());
                //log.error("Error en BPMSTask.filterByProcess", e);
            }
            return vFilteredTasks;
        }

        /**
        * Genera un vector con todas las tareas (FlowNodeInstance) de vTasks cuyo
        * estatus corresponda a los seleccionados
        *
        * @param            vTasks Vector
        * @param            filterValue String
        * @return      		Vector con objetos FlowNodeInstance
        * @see
        */
        public static Vector filterTasksByStatus(Vector vTasks,
                String filterValue)
        {
            Vector vFilteredTasks = new Vector();
            int index = 0;
            try
            {
                if(filterValue.length()>0)
                {
                    Vector vFilterValues =
                            BPMSProcessInstance.ClassMgr.stringToVector(
                                filterValue);
                    for(int i=0; i<vTasks.size();i++)
                    {
                        FlowNodeInstance fobi =
                                (FlowNodeInstance)vTasks.get(i);
                        String strStatus = String.valueOf(fobi.getStatus());
                        if(vFilterValues.contains(strStatus))
                        {
                            vFilteredTasks.add(index,fobi);
                            index++;
                        }
                    }
                } else {
                    vFilteredTasks = vTasks;
                }
            }catch(Exception e){
                System.out.println("Error en BPMSTask.filterByStatus:" +
                        e.getMessage());
                //log.error("Error en BPMSTask.filterByStatus", e);
            }
            return vFilteredTasks;
        }

        /**
        * Genera un vector con todas las tareas (FlowNodeInstance) de vTasks cuyo
        * nombre de tarea (title) corresponda con el seleccionado.
        *
        * @param            vTasks Vector
        * @param            filterValue String
        * @return      		Vector con objetos FlowNodeInstance
        * @see
        */
        public static Vector filterTasksByTitle(Vector vTasks,
                String filterValue)
        {
            Vector vFilteredTasks = new Vector();
            int index = 0;
            try
            {
                if(filterValue.length()>0)
                {
                    Vector vFilterValues =
                            BPMSProcessInstance.ClassMgr.stringToVector(
                                filterValue);
                    for(int i=0; i<vTasks.size();i++)
                    {
                        FlowNodeInstance fobi =
                                (FlowNodeInstance)vTasks.get(i);
                        FlowNode fobiType =
                                (FlowNode)fobi.getFlowNodeType();
                        String strTitle = fobiType.getTitle();
                        if(vFilterValues.contains(strTitle))
                        {
                            vFilteredTasks.add(index,fobi);
                            index++;
                        }
                    }
                } else {
                    vFilteredTasks = vTasks;
                }
            }catch(Exception e){
                System.out.println("Error en BPMSTask.filterByTitle:" +
                        e.getMessage());
                //log.error("Error en ConBPMSProcessInstancerolPanel.filterByTitle", e);
            }
            return vFilteredTasks;
        }


        /**
         * Recibe un vector de objetos FlowNodeInstance y regresa un vector de
         * objetos TaskLink
         *
         * @param               vTasks Vector de objetos FlowNodeInstance
         * @param               paramsRequest SWBParamRequest
         * @return      		Vector de objetos FlowNodeInstance
         * @see
         */
        public static Vector flowNodeInstanceToTaskLink(Vector vTasks,
                SWBParamRequest paramsRequest)
        {
            Vector vTaskLinks = new Vector();
            String strTempHref = "";
            String strTempLegend = "";
            int intTasks = 0;
            try
            {
                for(int i=0; i<vTasks.size(); i++)
                {
                    FlowNodeInstance fobi = (FlowNodeInstance) vTasks.get(i);
                    TaskLink tlink = new TaskLink(fobi,strTempHref,strTempLegend);
                    vTaskLinks.add(intTasks,tlink);
                    intTasks++;
                }
            } catch(Exception e){
              //log.error("Error en BPMSTask.flowNodeInstanceToTaskLink", e);
                System.out.println("Error en " +
                        "BPMSProcessInstance.flowNodeInstanceToTaskLink:" +
                        e.getMessage());
            }
            return vTaskLinks;
        }


        /**
        * Genera un vector con todas las tareas (FlowNodeInstance) del sitio de
        * procesos a las que tiene acceso el usuario actual
        *
        * @param            paramRequest SWBParamRequest
        * @return      		Vector de objetos FlowNodeInstance
        * @see
        */
        public static Vector getAllUserTasks(SWBParamRequest paramsRequest)
        {
            Vector vTasks = new Vector();
            int index = 0;
            try
            {
                User currentUser = paramsRequest.getUser();
                WebPage webPage = (WebPage)paramsRequest.getWebPage();
                ProcessSite site = (ProcessSite)webPage.getWebSite();
                Iterator <ProcessWebPage> itProcessWebPages =
                        site.listProcessWebPages();
                while(itProcessWebPages.hasNext())
                {
                    Vector vSelectedProcesses =
                            BPMSProcessInstance.ClassMgr.getAllProcessDefinitions(
                                paramsRequest);
                    ProcessWebPage pwp =
                            (ProcessWebPage) itProcessWebPages.next();
                    if(pwp.isActive()){
                        org.semanticwb.process.model.Process process = pwp.getProcess();
                        if(vSelectedProcesses.contains(process))
                        {
                            //Iterator itge = process.listContaineds();
                            Iterator<ProcessInstance> itPinst = site.listProcessInstances();
                            while(itPinst.hasNext()){
                                ProcessInstance pi = itPinst.next();
                                Iterator<FlowNodeInstance> itge = pi.listFlowNodeInstances();
                                //Iterator<FlowNodeInstance> itge = SWBProcessMgr.getUserTaskInstances(pi, currentUser).iterator();
                                while(itge.hasNext())
                                {
                                    Object obj = itge.next();
                                    if(obj instanceof org.semanticwb.process.model.FlowNodeInstance)
                                    {
                                        FlowNodeInstance actins = (FlowNodeInstance)obj;
                                        FlowNode type = actins.getFlowNodeType();
                                        if(actins instanceof SubProcessInstance)
                                        {
                                            Vector aux = getAllUserTasks((SubProcessInstance)actins, currentUser);
                                            vTasks.addAll(index, aux);
                                            index = index + aux.size();
                                        }else if(type instanceof Task)
                                        {
                                            if(currentUser.haveAccess(type)){
                                                vTasks.add(index,actins);
                                                index++;
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            } catch(Exception e){
              //log.error("Error en BPMSTask.getAllUserTasks", e);
                System.out.println("Error en BPMSTask.getAllUserTasks1:"
                        + e.getMessage());
            }
            return vTasks;
        }

        /**
        * Genera un vector con todas las tareas (FlowNodeInstance) de una
        * instancia de un proceso a las que tiene acceso el usuario
        *
        * @param            pinst ProcessInstance
        * @param            user User
        * @return      		Vector de objetos FlowNodeInstance
        * @see
        */
        public static Vector getAllUserTasks(SubProcessInstance pinst, User user)
        {
            Vector vTasks = new Vector();
            int index = 0;
            try
            {
                Iterator<FlowNodeInstance> it = pinst.listFlowNodeInstances();
                while(it.hasNext())
                {
                    FlowNodeInstance actins = it.next();
                    FlowNode type = actins.getFlowNodeType();
                    if(actins instanceof SubProcessInstance)
                    {
                        Vector aux = getAllUserTasks((SubProcessInstance)actins, user);
                        vTasks.addAll(index, aux);
                        index = index + aux.size();
                    }else if(type instanceof Task)
                    {
                        if(user.haveAccess(type)){
                            vTasks.add(index,actins);
                            index++;
                        }
                    }
                }
            } catch(Exception e){
              //log.error("Error en BPMSTask.getAllUserTasks", e);
                System.out.println("Error en BPMSTask.getAllUserTasks2:"
                        + e.getMessage());
            }
            return vTasks;
        }

       /**
         * Ordena de manera descendente un Vector de objetos TaskLink.
         * Utiliza la clase estatica TaskLinkDateComparator para comparar
         * los elementos del vector y determinar el orden que corresponde
         * Cada objeto TaskLink tiene un objeto FlowNodeInstance y Strings para
         * los datos del vínculo y la leyenda. El objeto FlowNodeInstance
         * tiene un identificador y fecha de creación.
         * <p>
         * Este método no regresa ningun objeto.
         *
         * @param  v 				Vector con objetos TaskLink
         * @return      			void
         * @see         			TaskLinkDateComparator
         */
        public static void sortByDate(Vector v){
            try {
                TaskLink.TaskLinkDateComparator comparator =
                        new TaskLink.TaskLinkDateComparator();
                Collections.sort(v,comparator);
            }
            catch(Exception e){
                System.out.println("Error en BPMSTask.sortByDate:" +
                        e.getMessage());
                //log.error("Error en BPMSTask.sortByDate", e);
            }
        }

       /**
         * Ordena de manera ascendente un Vector de objetos TaskLink.
         * Utiliza la clase estatica TaskLinkProcessComparator para comparar
         * los elementos del vector y determinar el orden que corresponde
         * Cada objeto TaskLink tiene un objeto FlowNodeInstance y Strings para
         * los datos del vínculo y la leyenda. El objeto FlowNodeInstance
         * tiene un identificador, titulo y fecha de creación.
         * <p>
         * Este método no regresa ningun objeto.
         *
         * @param  v 				Vector con objetos TaskLink
         * @return      			void
         * @see         			TaskLinkTitleComparator
         */
        public static void sortByProcess(Vector v){
            try {
                TaskLink.TaskLinkProcessComparator comparator =
                        new TaskLink.TaskLinkProcessComparator();
                Collections.sort(v,comparator);
            }
            catch(Exception e){
                System.out.println("Error en BPMSTask.sortByProcess:" +
                        e.getMessage());
                //log.error("Error en BPMSTask.sortByProcess", e);
            }
        }

       /**
         * Ordena de manera ascendente un Vector de objetos TaskLink.
         * Utiliza la clase estatica TaskLinkTitleComparator para comparar
         * los elementos del vector y determinar el orden que corresponde
         * Cada objeto TaskLink tiene un objeto FlowNodeInstance y Strings para
         * los datos del vínculo y la leyenda. El objeto FlowNodeInstance
         * tiene un identificador, titulo y fecha de creación.
         * <p>
         * Este método no regresa ningun objeto.
         *
         * @param  v 				Vector con objetos TaskLink
         * @return      			void
         * @see         			TaskLinkTitleComparator
         */
        public static void sortByTitle(Vector v){
            try {
                TaskLink.TaskLinkTitleComparator comparator =
                        new TaskLink.TaskLinkTitleComparator();
                Collections.sort(v,comparator);
            } catch(Exception e){
                System.out.println("Error en BPMSTask.sortByTitle:" +
                        e.getMessage());
                //log.error("Error en BPMSTask.sortByTitle", e);
            }
        }

       /**
         * Determina en base al criterio, que tipo de ordenamiento debe aplicarse
         * a un vector de objetos TaskLink.
         * <p>
         * Este método no regresa ningun objeto.
         *
         * @param  v 				Vector con objetos TaskLink
         * @param  criteria         int
         * @return      			void
         * @see         			sortByDate
         * @see         			sortByTitle
         * @see         			sortByRelevance
         */
        public static void sortTasks(Vector v, int criteria){
            try
            {
                switch(criteria)
                {
                    case BPMSTask.ClassMgr.SORT_BY_TITLE:
                        sortByTitle(v);
                        break;
                    case BPMSTask.ClassMgr.SORT_BY_PROCESS:
                        sortByProcess(v);
                        break;
                        /*
                    case BPMSTask.ClassMgr.SORT_BY_PRIORITY:
                        sortByPriority(v);
                        break;
                         */
                    default:
                        sortByDate(v);
                        break;
                }
            }
            catch(Exception e){
                System.out.println("Error en BPMSTask.sortTasks:" +
                        e.getMessage());
                //log.error("Error en BPMSTask.sortTasks", e);
            }
        }

    }
}
