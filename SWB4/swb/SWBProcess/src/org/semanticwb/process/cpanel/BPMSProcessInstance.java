/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.process.cpanel;

import java.util.*;
//import org.semanticwb.portal.*;
import org.semanticwb.portal.api.*;
//import org.semanticwb.process.*;
import org.semanticwb.process.model.*;
import org.semanticwb.model.*;
//import org.semanticwb.*;
import org.semanticwb.platform.*;
import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;

/**
 *
 * @author haydee.vertti
 */
public class BPMSProcessInstance {

    public static class ClassMgr
    {
        public final static int STATUS_ALERT = 999;
        private static Logger log = SWBUtils.getLogger(BPMSProcessInstance.class);

        /**
         * Genera un Vector con todos objetos de tipo ProcessSite encontrados en
         * la instancia de SWB.
         *
         * @param               paramRequest SWBParamRequest
         * @return      		Vector de objetos ProcessSite
         * @see
         */
        public static Vector getAllProcessSites(SWBParamRequest paramRequest)
        {
            Vector allSites = new Vector();
            int index = 0;
            try
            {
                Iterator<ProcessSite> itProcessSites =
                        ProcessSite.ClassMgr.listProcessSites();
                while(itProcessSites.hasNext())
                {
                    allSites.add(index, (ProcessSite) itProcessSites.next());
                    index++;
                }
            } catch(Exception e){
              log.error("Error en BPMSProcessInstance.getAllProcessSites", e);
                System.out.println("Error en BPMSProcessInstance.getAllProcessSites:"
                        + e.getMessage());
            }
            return allSites;
        }

        //TODO: COmentarios
        /*
        public static Vector getAllProcessDefinitions()
        {
            Vector vDefs = new Vector();

            Vector allSites = this.getAllProcessSites(paramRequest);


            return vDefs;
        }
         */


        /**
         * Construye un vector con todas las definiciones de proceso que
         * encuentra en el sitio de procesos.
         * Obtiene todos los objetos de tipo ProcessWebPage del sitio y
         * solicita a cada uno su definición de proceso (objeto de tipo
         * org.semanticwb.process.model.Process).
         *
         * @param               paramRequest SWBParamRequest
         * @return      		Vector de objetos org.semanticwb.process.model.Process
         * @see
         */
        public static Vector getAllProcessDefinitions(
                SWBParamRequest paramRequest)
        {
            Vector vAllProcessDefinitions = new Vector();
            int index = 0;
            try
            {
                WebPage webPage = (WebPage)paramRequest.getWebPage();
                ProcessSite site = (ProcessSite) webPage.getWebSite();
                Iterator <ProcessWebPage> itProcessWebPages =
                        site.listProcessWebPages();
               while(itProcessWebPages.hasNext())
               {
                    ProcessWebPage pwp =
                            (ProcessWebPage) itProcessWebPages.next();
                    if(pwp.isActive()){
                        org.semanticwb.process.model.Process process = pwp.getProcess();
                        //org.semanticwb.process.Process process = org.semanticwb.process.SWBProcessMgr.getProcess(pwp);
                        vAllProcessDefinitions.add(index, process);
                        index++;
                    }
               }

            } catch(Exception e){
              log.error("Error en BPMSProcessInstance.getAllProcessDefinitions", e);
                System.out.println("Error en " +
                        "BPMSProcessInstance.getAllProcessDefinitions:" +
                        e.getMessage());
            }
            return vAllProcessDefinitions;
        }

        //CONTAINED
        public static Vector getContainedArtifacts(org.semanticwb.process.model.Process process)
        {
            int index = 0;
            Vector vContained = new Vector();
            GenericIterator git = process.listContaineds();
            while(git.hasNext())
            {
                GenericObject gob = git.next();
                if(gob instanceof org.semanticwb.process.model.Artifact)
                {
                    SemanticObject sob = gob.getSemanticObject();
                    SemanticClass cls = sob.getSemanticClass();
                    vContained.add(index, cls);
                    index++;
                }
            }
            return vContained;
        }

        public static Vector getContainedSubProcesses(org.semanticwb.process.model.Process process)
        {
            int index = 0;
            Vector vContained = new Vector();
            GenericIterator git = process.listContaineds();
            while(git.hasNext())
            {
                GenericObject gob = git.next();
                if(gob instanceof SubProcess)
                {
                    SubProcess subp = (SubProcess) gob;
                    vContained.add(index, subp);
                    index++;
                }
            }
            return vContained;
        }

        public static Vector getContainedTasks(org.semanticwb.process.model.Process process)
        {
            int index = 0;
            Vector vContained = new Vector();
            GenericIterator git = process.listContaineds();
            while(git.hasNext())
            {
                GenericObject gob = git.next();
                if(gob instanceof Task)
                {
                    Task tsk = (Task) gob;
                    vContained.add(index, tsk);
                    index++;
                } else if(gob instanceof SubProcess)
                {
                    org.semanticwb.process.model.Process subp = (org.semanticwb.process.model.Process) gob;
                    Vector vSubs = getContainedTasks(subp);
                    vContained.addAll(vSubs);
                    index = index + vSubs.size();
                }
            }
            return vContained;
        }

        public static Vector getContainedFlowNodes(org.semanticwb.process.model.Process process)
        {
            int index = 0;
            Vector vContained = new Vector();
            GenericIterator git = process.listContaineds();
            while(git.hasNext())
            {
                GenericObject gob = git.next();
                if(gob instanceof FlowNode)
                {
                    FlowNode flon = (FlowNode) gob;
                    vContained.add(index, flon);
                    index++;
                }
            }
            return vContained;
        }

        public static Vector getContainedFlowNodesInstances(org.semanticwb.process.model.Process process)
        {
            int index = 0;
            Vector vContained = new Vector();
            GenericIterator git = process.listContaineds();
            while(git.hasNext())
            {
                GenericObject gob = git.next();
                if(gob instanceof FlowNodeInstance)
                {
                    FlowNodeInstance flon = (FlowNodeInstance) gob;
                    vContained.add(index, flon);
                    index++;
                }
            }
            return vContained;
        }

        /*
        * Genera el codigo HTML necesario para imprimir las propieades y valores de
        * todos los artefactos de todos los procesos del sitio de procesos
        *
        * @param            paramRequest SWBParamRequest
        * @return      		StringBuffer con codigo HTML
        * @see
        */
        public static StringBuffer printArtifact(Vector vAllArtifacts)
        {
            StringBuffer sb = new StringBuffer();
            try
            {
                for(int i=0; i<vAllArtifacts.size(); i++)
                {
                    ProcessObject pob = (ProcessObject) vAllArtifacts.get(i);
                    SemanticObject sob =
                            SemanticObject.getSemanticObject(pob.getURI());
                    SemanticClass cls = sob.getSemanticClass();
                    sb.append("<h2>" + cls.getRootClass().getName() + "</h2>");
                    sb.append("<h3> " + pob.getURI() + ":>" + pob.getId() +
                            "</h3>");
                    sb.append("<h4>" + sob.getDisplayName() + "</h4>");
                    Vector vProps  =
                            BPMSProcessInstance.ClassMgr.getArtifactProperties(cls);
                    sortPropertiesByName(vProps);
                    for(int j=0; j<vProps.size(); j++)
                    {
                        SemanticProperty sProp = (SemanticProperty)vProps.get(j);
                        String strPropValue =
                                BPMSProcessInstance.ClassMgr.getPropertyValue(sob,
                                    sProp).equalsIgnoreCase("null")
                                ?""
                                :BPMSProcessInstance.ClassMgr.getPropertyValue(sob, sProp);
                        String strPropLabel = sProp.getLabel()==null
                                ?sProp.getDisplayName()
                                :sProp.getLabel();
                        sb.append("<h5>" + strPropLabel + ":" +
                                strPropValue  + "</h5>");
                    }
                }
            } catch(Exception e){
              log.error("Error en BPMSProcessInstance.printArtifact", e);
                System.out.println(
                        "Error en BPMSProcessInstance.printArtifact:" +
                        e.getMessage());
            }
            return sb;
        }

        /*
        * Genera el codigo HTML necesario para imprimir las propieades y valores de
        * todos los artefactos de todos los procesos del sitio de procesos
        * capturados como SemanticClass
        *
        * @param            paramRequest SWBParamRequest
        * @return      		StringBuffer con codigo HTML
        * @see
        */
        public static StringBuffer printArtifactCls(Vector vAllArtifacts)
        {
            StringBuffer sb = new StringBuffer();
            try
            {
                for(int i=0; i<vAllArtifacts.size(); i++)
                {
                    SemanticClass cls = (SemanticClass)vAllArtifacts.get(i);
                    SemanticObject sob = cls.getSemanticObject();
                    sb.append("<h2>" + cls.getRootClass().getName() + "</h2>");
                    sb.append("<h3> " + cls.getURI() + ":>" + cls.getClassId() +
                            "</h3>");
                    sb.append("<h4>" + cls.getClassName() + "</h4>");
                    Vector vProps  =
                            BPMSProcessInstance.ClassMgr.getArtifactProperties(cls);
                    sortPropertiesByName(vProps);
                    for(int j=0; j<vProps.size(); j++)
                    {
                        SemanticProperty sProp = (SemanticProperty)vProps.get(j);
                        String strPropValue =
                                BPMSProcessInstance.ClassMgr.getPropertyValue(sob,
                                    sProp).equalsIgnoreCase("null")
                                ?""
                                :BPMSProcessInstance.ClassMgr.getPropertyValue(sob, sProp);
                        String strPropLabel = sProp.getLabel()==null
                                ?sProp.getDisplayName()
                                :sProp.getLabel();
                        sb.append("<h5>" + strPropLabel + ":" +
                                strPropValue  + "</h5>");
                    }
                }
            } catch(Exception e){
              log.error("Error en BPMSProcessInstance.printArtifactCls", e);
                System.out.println(
                        "Error en BPMSProcessInstance.printArtifactCls:" +
                        e.getMessage());
            }
            return sb;
        }

        /**
        * Genera un vector de objetos SemanticProperty con las propiedades de un
        * objeto SemanticClass (instancia de un artefacto)
        *
        * @param            cls SemanticClass
        * @return      		Vector de objetos SemanticProperty
        * @see
        */
        public static Vector getArtifactProperties(SemanticClass cls)
        {
            Vector vProperties = new Vector();
            int index = 0;
            try
            {
                Iterator<SemanticProperty> itProps = cls.listProperties();
                while(itProps.hasNext())
                {
                    SemanticProperty sProp = (SemanticProperty)itProps.next();
                    vProperties.add(index, sProp);
                }
            } catch(Exception e){
              log.error("Error en BPMSProcessInstance.getArtifactProperties", e);
                System.out.println(
                        "Error en BPMSProcessInstance.getArtifactProperties:" +
                        e.getMessage());
            }
            return vProperties;
        }


        /**
        * Obtiene los artefactos de un proceso, dependiendo del criterio
        * seleccionado:
        * 1 = Artefactos asociados a tareas
        * 2 = Artefactos asociados a subprocesos
        * 3 = Todos los artefactos
        *
        * @param            org.semanticwb.process.model.Process process
        * @param            int criteria
        * @return      		Vector de objetos SemanticClass
        * @see
        */
        public static Vector getProcessArtifacts(
                org.semanticwb.process.model.Process process, int criteria)
        {
            Vector vArtifacts = new Vector();
            int index = 0;
            try
            {
                if(criteria ==1 || criteria==3)
                {
                    GenericIterator git = process.listContaineds();
                    while(git.hasNext())
                    {
                        GenericObject gob = git.next();
                        if(gob instanceof org.semanticwb.process.model.Artifact)
                        {
                            SemanticObject sob = gob.getSemanticObject();
                            SemanticClass cls = sob.getSemanticClass();
                            vArtifacts.add(index, cls);
                            index++;
                        }
                    }
                }
                if(criteria ==2 || criteria==3)
                {
                    Iterator<GenericObject> itRelated = process.listRelatedObjects();
                    while(itRelated.hasNext())
                    {
                        GenericObject gob = itRelated.next();
                        if(gob instanceof org.semanticwb.process.model.Artifact)
                        {
                            SemanticObject relSob =
                                    (SemanticObject)itRelated.next();
                            SemanticClass cls = relSob.getSemanticClass();
                            vArtifacts.add(index, cls);
                            index++;
                        }
                    }
                }
            } catch(Exception e){
              log.error("Error en BPMSProcessInstance.getProcessArtifacts(criteria)", e);
                System.out.println(
                        "Error en BPMSProcessInstance.getProcessArtifacts(criteria):" +
                        e.getMessage());
            }
            return vArtifacts;
        }

        /**
        * Genera un vector con todos los artefactos de todas las instancias de
        * una definicion de proceso
        *
        * @param            process org.semanticwb.process.model.Proces
        * @return      		Vector de objetos FlowNodeInstance
        * @see
        */
        public static Vector getProcessArtifacts(
                org.semanticwb.process.model.Process process)
        {
            Vector vArtifacts = new Vector();
            int index = 0;
            try
            {
                Iterator itge = process.listContaineds();
                while(itge.hasNext())
                {
                    Object obj = itge.next();
                    if(obj instanceof org.semanticwb.process.model.FlowNodeInstance)
                    {
                        FlowNodeInstance floni = (FlowNodeInstance) obj;
                        Vector vFobiArtifacts = getProcessInstanceArtifacts(floni);
                        if(vFobiArtifacts.size()>0)
                        {
                            vArtifacts.addAll(index, vFobiArtifacts);
                            index = index + vFobiArtifacts.size();
                        }
                    }
                }
            } catch(Exception e){
              log.error("Error en BPMSProcessInstance.getProcessArtifacts", e);
                System.out.println(
                        "Error en BPMSProcessInstance.getProcessArtifacts:" +
                        e.getMessage());
            }
            return vArtifacts;
        }


        /**
        * Regresa un vector con las propiedades de todos los artefactos de todas
        * las instancias de un proceso
        *
        * @param            process org.semanticwb.process.model.Process
        * @return      		Vector de objetos SemanticProperty
        * @see
        */
        public static Vector getProcessArtifactsProperties(
                org.semanticwb.process.model.Process process)
        {
            Vector vProperties = new Vector();
            int index = 0;
            try
            {
                Vector vArtifacts =  getProcessArtifacts(process);
                for(int i=0; i<vArtifacts.size(); i++)
                {
                    ProcessObject pob = (ProcessObject) vArtifacts.get(i);

                    SemanticObject sob =
                            SemanticObject.getSemanticObject(pob.getURI());
                    SemanticClass cls = sob.getSemanticClass();
                    Vector vArtProperties =
                            BPMSProcessInstance.ClassMgr.getArtifactProperties(cls);
                    sortPropertiesByName(vArtProperties);
                    if(vArtProperties.size()>0)
                    {
                        vProperties.addAll(index, vArtProperties);
                        index = index + vArtProperties.size();
                    }
                }
            } catch(Exception e){
              log.error("Error en BPMSProcessInstance.getProcessArtifactsProperties", e);
                System.out.println(
                    "Error en BPMSProcessInstance.getProcessArtifactsProperties:"
                    + e.getMessage());
            }
            return vProperties;
        }

        /**
        * Regresa un vector con todos los artefactos de una instancia de un proceso
        *
        * @param            floni FlowNodeInstance
        * @return      		Vector de objetos ProcessObject
        * @see
        */
        public static Vector getProcessInstanceArtifacts(FlowNodeInstance floni)
        {
            Vector vArtifacts = new Vector();
            int index = 0;
            try
            {
                //java.util.List<ProcessObject> arrPob = floni.getAllProcessObjects();
                java.util.List<ProcessObject> arrPob = floni.listHeraquicalProcessObjects();
                for(int j=0; j<arrPob.size(); j++)
                {
                    ProcessObject pob = (ProcessObject) arrPob.get(j);
                    vArtifacts.add(index, pob);
                    index++;
                }
            } catch(Exception e){
              log.error("Error en BPMSProcessInstance.getProcessInstanceArtifacts", e);
                System.out.println(
                        "Error en BPMSProcessInstance.getProcessInstanceArtifacts:"
                        + e.getMessage());
            }
            return vArtifacts;
        }

        public static String getPropertyName(String URI){
            String strPropName = "";
            try
            {
                SemanticProperty semprop =
                        SemanticObject.createSemanticObject(URI).transformToSemanticProperty();
                strPropName = semprop.getDisplayName();
            } catch(Exception e){
              log.error("Error en ControlPanel.getPropertyName", e);
                System.out.println("Error en " +
                        "BPMSProcessInstance.getPropertyName:"
                        + e.getMessage());
            }
            return strPropName;
        }

        /**
        * Obtener el valor de una propiedad para una instancia de un artefacto
        * (SemanticObject)
        *
        * @param            sob SemanticObject
        * @param            property SemanticProperty
        * @return      		String Valor de la propiedad
        * @see
        */
        public static String getPropertyValue(SemanticObject sob,
                SemanticProperty property)
                throws com.hp.hpl.jena.rdf.model.ResourceRequiredException
        {
            String strValue = "";
            try
            {
                if(property.isBoolean()){
                    strValue = String.valueOf(sob.getBooleanProperty(property));
                } else if(property.isDouble()){
                    strValue = String.valueOf(sob.getDoubleProperty(property));
                } else if(property.isDate()){
                    strValue = String.valueOf(sob.getDateProperty(property));
                } else if(property.isDateTime()){
                    strValue = String.valueOf(sob.getDateTimeProperty(property));
                } else if(property.isFloat()){
                    strValue = String.valueOf(sob.getFloatProperty(property));
                } else if(property.isInt()){
                    strValue = String.valueOf(sob.getIntProperty(property));
                } else if(property.isLong()){
                    strValue = String.valueOf(sob.getLongProperty(property));
                } else if(property.isString()){
                    strValue = String.valueOf(sob.getProperty(property));
                } else if(property.isObjectProperty()){
                    SemanticObject sobject =
                            (SemanticObject)sob.getObjectProperty(property);
                    if(null!=sobject)
                    {
                        strValue = sobject.getDisplayName();
                    }
                }
                if(null==strValue){
                    strValue="";
                }
            } catch(com.hp.hpl.jena.rdf.model.ResourceRequiredException rre){
              log.error("Error en ControlPanel.getPropertyValue2String", rre);
                System.out.println("Error RRE en " +
                        "BPMSProcessInstance.getPropertyValue2String:"
                        + rre.getMessage());
            } catch(Exception e){
              log.error("Error en ControlPanel.getPropertyValue2String", e);
                System.out.println("Error en " +
                        "BPMSProcessInstance.getPropertyValue2String:"
                        + e.getMessage());
            }
            return strValue;
        }

        /**
         * Genera un ArrayList con las instancias de proceso que tienen una
         * alerta vencida.
         *
         * @param               paramRequest SWBParamRequest
         * @return      		ArrayList
         * @see
         */
        public static ArrayList listProcessInstancesByAlert(
                SWBParamRequest paramRequest, String status)
        {
            ArrayList arrProcessInstances = new ArrayList();
            ArrayList arrAux = new ArrayList();
            int index = 0;
            try
            {
                arrAux = listProcessInstances(paramRequest);
                for(int i=0; i<arrAux.size(); i++)
                {
                    ProcessInstance actins = (ProcessInstance)arrAux.get(i);
                    if(STATUS_ALERT == actins.getStatus())
                    {
                        arrProcessInstances.add(index, actins);
                        index++;
                    }
                }
            } catch(Exception e){
              log.error("Error en BPMSProcessInstance.listProcessInstancesByAlert", e);
                System.out.println("Error en " +
                        "BPMSProcessInstance.listProcessInstancesByAlert:" +
                        e.getMessage());
            }
            return arrProcessInstances;
        }

        /**
         * Genera un ArrayList con las instancias de proceso filtradas por el
         * estatus proporcionado por el usuario
         *
         * @param               paramRequest SWBParamRequest
         * @param               status String
         * @return      		ArrayList
         * @see
         */
        public static ArrayList listProcessInstancesByStatus(
                SWBParamRequest paramRequest, String status)
        {
            ArrayList arrProcessInstances = new ArrayList();
            ArrayList arrAux = new ArrayList();
            int index = 0;
            try
            {
                arrAux = listProcessInstances(paramRequest);
                for(int i=0; i<arrAux.size(); i++)
                {
                    ProcessInstance actins = (ProcessInstance)arrAux.get(i);
                    if(status.equalsIgnoreCase(
                            String.valueOf(actins.getStatus())))
                    {
                        arrProcessInstances.add(index, actins);
                        index++;
                    }
                }
            } catch(Exception e){
              log.error("Error en BPMSProcessInstance.listProcessInstancesByStatus", e);
                System.out.println("Error en " +
                        "BPMSProcessInstance.listProcessInstancesByStatus:" +
                        e.getMessage());
            }
            return arrProcessInstances;
        }

        /**
         * Genera un ArrayList con las instancias de proceso a las cuales tiene
         * acceso el usuario proporcionado
         *
         * @param               paramRequest SWBParamRequest
         * @param               user User
         * @return      		ArrayList
         * @see
         */
        public static ArrayList listProcessInstancesByUser(
                SWBParamRequest paramRequest, User user)
        {
            ArrayList arrProcessInstances = new ArrayList();
            ArrayList arrAux = new ArrayList();
            int index = 0;
            try
            {
                arrAux = listProcessInstances(paramRequest);
                for(int i=0; i<arrAux.size(); i++)
                {
                    ProcessInstance actins = (ProcessInstance)arrAux.get(i);
                    FlowNode type = actins.getFlowNodeInstance().getFlowNodeType();
                    if(user.haveAccess(type))
                    {
                        arrProcessInstances.add(index, actins);
                        index++;
                    }
                }
            } catch(Exception e){
              log.error("Error en BPMSProcessInstance.listProcessInstancesByUser", e);
                System.out.println("Error en " +
                        "BPMSProcessInstance.listProcessInstancesByUser:" +
                        e.getMessage());
            }
            return arrProcessInstances;
        }

        /**
         * Genera un ArrayList con las instancias de un proceso en particular.
         * Se requiere la URI de la definicion de proceso
         *
         * @param               paramRequest SWBParamRequest
         * @param               URI String
         * @return      		ArrayList
         * @see
         */
        public static ArrayList listProcessInstancesByProcess(
                SWBParamRequest paramRequest, String URI)
        {
            ArrayList arrProcessInstances = new ArrayList();
            ArrayList arrAux = new ArrayList();
            int index = 0;
            try
            {
                arrAux = listProcessInstances(paramRequest);
                for(int i=0; i<arrAux.size(); i++)
                {
                    ProcessInstance actins = (ProcessInstance)arrAux.get(i);
                    System.out.println("en listProcessInstancesByProcess URI:" + URI);
                    //ProcessInstance parentInstance = (ProcessInstance)actins.getParentProcessInstance();
                    //System.out.println("parentInstance.URI:" + parentInstance.getURI());
                    FlowNode type = actins.getFlowNodeInstance().getFlowNodeType();
                    System.out.println("en listProcessInstancesByProcess type.URI:" + type.getURI());
                    if(type.getURI().equalsIgnoreCase(URI))
                    {
                        arrProcessInstances.add(index, actins);
                        index++;
                    }
                }
            } catch(Exception e){
              log.error("Error en BPMSProcessInstance.listProcessInstancesByProcess", e);
                System.out.println("Error en " +
                        "BPMSProcessInstance.listProcessInstancesByProcess:" +
                        e.getMessage());
            }
            return arrProcessInstances;
        }

        /**
         * Genera un ArrayList con todas las instancias de proceso de todos
         * los procesos que se encuentran en el sitio de procesos
         *
         * @param               paramRequest SWBParamRequest
         * @return      		ArrayList
         * @see
         */
        public static ArrayList listProcessInstances(
                SWBParamRequest paramRequest)
        {
            ArrayList arrProcessInstances = new ArrayList();
            int index = 0;
            try
            {
                WebPage webPage = (WebPage)paramRequest.getWebPage();
                ProcessSite site = (ProcessSite)webPage.getWebSite();
                Iterator <ProcessWebPage> itProcessWebPages =
                        site.listProcessWebPages();
                while(itProcessWebPages.hasNext())
                {
                    Vector vSelectedProcesses =
                            getAllProcessDefinitions(paramRequest);
                    ProcessWebPage pwp =
                            (ProcessWebPage) itProcessWebPages.next();
                    if(pwp.isActive()){
                        org.semanticwb.process.model.Process process = pwp.getProcess();
                        if(vSelectedProcesses.contains(process))
                        {
                            Iterator it = process.listContaineds();
                            while(it.hasNext())
                            {
                                Object obj = it.next();
                                if(obj instanceof org.semanticwb.process.model.FlowNodeInstance)
                                {
                                    FlowNodeInstance floni = (FlowNodeInstance) obj;
                                    FlowNode type = floni.getFlowNodeType();
                                    if(floni instanceof SubProcessInstance)
                                    {
                                        arrProcessInstances.add(index, floni);
                                        index++;
                                        ArrayList aux = listProcessInstances((SubProcessInstance) floni);
                                        if(aux.size()>0)
                                        {
                                            arrProcessInstances.addAll(index, aux);
                                            index = index + aux.size();
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            } catch(Exception e){
              log.error("Error en BPMSProcessInstance.listProcessInstances", e);
                System.out.println("Error en " +
                        "BPMSProcessInstance.listProcessInstances:" +
                        e.getMessage());
            }
            return arrProcessInstances;
        }

        /**
         * Genera un ArrayList con todas las instancias de subprocesos que
         * corresponden a una instancia de proceso
         *
         * @param               paramRequest SWBParamRequest
         * @return      		ArrayList
         * @see
         */
        private static ArrayList listProcessInstances(SubProcessInstance pinst)
        {
            ArrayList arrProcessInstances = new ArrayList();
            int index = 0;
            try
            {
                Iterator<FlowNodeInstance> it =
                        pinst.listFlowNodeInstances();
                while(it.hasNext())
                {
                    FlowNodeInstance actins = it.next();
                    FlowNode type = actins.getFlowNodeType();
                    if(actins instanceof SubProcessInstance)
                    {
                        arrProcessInstances.add(index, actins);
                        index++;

                        ArrayList aux =
                                listProcessInstances((SubProcessInstance)actins);
                        if(aux.size()>0)
                        {
                            arrProcessInstances.addAll(index, aux);
                            index = index + aux.size();
                        }
                    }
                }
            } catch(Exception e){
              log.error("Error en BPMSProcessInstance.listProcessInstances", e);
                System.out.println("Error en BPMSProcessInstance.listProcessInstances:"
                        + e.getMessage());
            }
            return arrProcessInstances;
        }


        /**
         * Construye un objeto Vector, con los elementos de un objeto String.
         * Los elementos del objeto String estan separados por |.
         * Se usa para obtener un valor de la base del recurso. Este valor está
         * formado por el conjunto de los valores de los checkboxes seleccionados
         * por el usuario.
         *
         * @param               String con elementos separados por |
         * @return      		Vector con los valores seleccionados de un grupo
         *                      de checkboxes
         * @see         		checkboxesToString
         * @see
         */
        public static Vector stringToVector(String strValues){
            Vector vDefinitions = new Vector();
            try
            {
                if(strValues!=null && !strValues.equalsIgnoreCase("")){
                    String [] arrSplit = strValues.split("\\|");
                    vDefinitions = new Vector(Arrays.asList(arrSplit));
                }
            } catch(Exception e){
              log.error("Error en BPMSProcessInstance.stringToVector", e);
                System.out.println("Error en BPMSProcessInstance.stringToVector:" +
                        e.getMessage());
            }
            return vDefinitions;
        }

        /**
        * Ordena un vector de propiedades (SemanticProperty) por nombre, usando
        * la clase PropertyNameComparator
        *
        * @param            paramRequest SWBParamRequest
        * @return      		void
        * @see
        */
        public static void sortPropertiesByName(Vector v){
            try {
                PropertyNameComparator comparator =
                        new PropertyNameComparator();
                Collections.sort(v,comparator);
            } catch(Exception e){
                System.out.println("Error en BPMSProcessInstance.sortPropertiesByName:" +
                        e.getMessage());
                log.error("Error en BPMSProcessInstance.sortByTitle", e);
            }
        }

        /**
        * Comparar y ordenar propiedades de acuerdo a su nombre
        *
        * @param            paramRequest SWBParamRequest
        * @return      		int
        * @see
        */
        static class PropertyNameComparator implements Comparator{
            public int compare(Object obj1, Object obj2){
                int result=0;
                SemanticProperty property1 = (SemanticProperty)obj1;
                SemanticProperty property2 = (SemanticProperty)obj2;
                String value1 = property1.getDisplayName();
                String value2 = property2.getDisplayName();
                String id1 = property1.getPropId();
                String id2 = property2.getPropId();
                result = value1.compareTo(value2);
                if(result==0){
                    result = id1.compareTo(id2);
                }
                return result;
            }
        }

    }
}
