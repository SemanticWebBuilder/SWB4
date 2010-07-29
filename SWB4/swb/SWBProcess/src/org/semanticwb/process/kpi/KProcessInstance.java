/**
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
 **/

package org.semanticwb.process.kpi;

import java.util.Iterator;
import java.util.ArrayList;

import org.semanticwb.model.User;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.platform.SemanticProperty;

import org.semanticwb.process.utils.Restriction;
import org.semanticwb.process.model.ProcessSite;
import org.semanticwb.process.model.ProcessObject;
import org.semanticwb.process.model.ProcessWebPage;
import org.semanticwb.process.model.ProcessInstance;
import org.semanticwb.process.model.FlowNodeInstance;
import org.semanticwb.process.model.SubProcessInstance;

/**
 *
 * @author Sergio Téllez
 */
public class KProcessInstance {

    /**
     * Genera un ArrayList con todas las instancias de proceso de todos
     * los procesos que se encuentran en el sitio de procesos
     *
     * @param               paramRequest SWBParamRequest
     * @return      		ArrayList
     * @see
    */
    public static ArrayList listProcessInstances() {
        ArrayList processInstances = new ArrayList();
        try {
            Iterator isites = ProcessSite.ClassMgr.listProcessSites();
            while (isites.hasNext()) {
                ProcessSite site = (ProcessSite)isites.next();
                Iterator <org.semanticwb.process.model.ProcessWebPage>itProcessWebPages = ProcessWebPage.ClassMgr.listProcessWebPages(site);
                while (itProcessWebPages.hasNext()) {
                    ProcessWebPage pwp = (ProcessWebPage)itProcessWebPages.next();
                    org.semanticwb.process.model.Process process = pwp.getProcess();
                    Iterator it = process.listProcessInstances();
                    while (it.hasNext()) {
                        ProcessInstance pinst = (ProcessInstance)it.next();
                        processInstances.add(pinst);
                    }
                }
            }
        }catch(Exception e) {
             e.printStackTrace();
        }
        return processInstances;
    }

    /**
     * Genera un ArrayList con todas las instancias de subprocesos que corresponden a una instancia de proceso
     *
     * @param               paramRequest SWBParamRequest
     * @return      		ArrayList
     * @see
    */
    private static ArrayList listProcessInstances(SubProcessInstance pinst) {
        ArrayList processInstances = new ArrayList();
        int index = 0;
        try {
            Iterator<FlowNodeInstance> it = pinst.listFlowNodeInstances();
            while(it.hasNext()) {
                FlowNodeInstance actins = it.next();
                if (actins instanceof SubProcessInstance) {
                    processInstances.add(index, actins);
                    index++;
                    ArrayList aux = listProcessInstances((SubProcessInstance)actins);
                    if(aux.size()>0) {
                        processInstances.addAll(index, aux);
                        index = index + aux.size();
                    }
                }
            }
        }catch(Exception e) {
            e.printStackTrace();
        }
        return processInstances;
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
    public static ArrayList listProcessInstancesByStatus(ArrayList instances, String status) {
        ArrayList aux = instances;
        ArrayList processInstances = new ArrayList();
        try {
            for (int i=0; i<aux.size(); i++) {
                ProcessInstance actins = (ProcessInstance)aux.get(i);
                if (status.equalsIgnoreCase(String.valueOf(actins.getStatus())))
                    processInstances.add(actins);
            }
        }catch(Exception e) {
            e.printStackTrace();
        }
        return processInstances;
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
    public static ArrayList listProcessInstancesByUser(ArrayList instances, User user) {
        ArrayList aux = instances;
        ArrayList processInstances = new ArrayList();
        try {
            for(int i=0; i<aux.size(); i++) {
                ProcessInstance actins = (ProcessInstance)aux.get(i);
                if (user.equals(actins.getModifiedBy()))
                    processInstances.add(actins);
            }
        }catch(Exception e) {
            e.printStackTrace();
        }
        return processInstances;
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
    public static ArrayList listProcessInstancesByProcess(ArrayList instances, String URI) {
        ArrayList aux = instances;
        ArrayList processInstances = new ArrayList();
        try {
            for (int i=0; i<aux.size(); i++) {
                ProcessInstance actins = (ProcessInstance)aux.get(i);
                if (actins.getProcessType().getURI().equalsIgnoreCase(URI))
                    processInstances.add(actins);
            }
        }catch(Exception e) {
            e.printStackTrace();
        }
        return processInstances;
    }

    public static ArrayList filterArtifactsObjects(ProcessInstance pinst, Restriction restriction, ArrayList filterinstances) {
        Iterator<ProcessObject> objit = pinst.listProcessObjects();
        while (objit.hasNext()) {
            ProcessObject obj =  objit.next();
            Iterator<SemanticProperty> spit = obj.getSemanticObject().listProperties();
            while (spit.hasNext()) {
                SemanticProperty sp = spit.next();
                //System.out.println(sp.getName() + " " + BPMSProcessInstance.getPropertyValue(obj.getSemanticObject(), sp));
                if (restriction.getProperty().equalsIgnoreCase(sp.getName())) {
                    if (restriction.match(obj.getSemanticObject(), sp) && !filterinstances.contains(pinst))
                        filterinstances.add(pinst);
                }
            }
        }
        Iterator<FlowNodeInstance> foit = pinst.listFlowNodeInstances();
        while (foit.hasNext()) {
            FlowNodeInstance flobin = foit.next();
            if (flobin instanceof SubProcessInstance) {
                SubProcessInstance spi=(SubProcessInstance)flobin;
                //filterinstances = filterArtifactsObjects(spi.getProcessInstance(), restriction, filterinstances);
                filterinstances = filterArtifactsObjects((SubProcessInstance)spi, restriction, filterinstances);
            }
        }
        return filterinstances;
    }

    /**
     * Added by Sergio Téllez 23 July 2010
     *
     */
    public static ArrayList filterArtifactsObjects(SubProcessInstance spinst, Restriction restriction, ArrayList filterinstances) {
        Iterator<ProcessObject> objit = spinst.listProcessObjects();
        while (objit.hasNext()) {
            ProcessObject obj =  objit.next();
            Iterator<SemanticProperty> spit = obj.getSemanticObject().listProperties();
            while (spit.hasNext()) {
                SemanticProperty sp = spit.next();
                if (restriction.getProperty().equalsIgnoreCase(sp.getName())) {
                    if (restriction.match(obj.getSemanticObject(), sp) && !filterinstances.contains(spinst.getProcessInstance()))
                        filterinstances.add(spinst.getProcessInstance());
                }
            }
        }
        Iterator<FlowNodeInstance> foit = spinst.listFlowNodeInstances();
        while (foit.hasNext()) {
            FlowNodeInstance flobin = foit.next();
            if (flobin instanceof SubProcessInstance) {
                SubProcessInstance spi=(SubProcessInstance)flobin;
                filterinstances = filterArtifactsObjects((SubProcessInstance)spi, restriction, filterinstances);
            }
        }
        return filterinstances;
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
    public static String getPropertyValue(SemanticObject sob, SemanticProperty property) throws com.hp.hpl.jena.rdf.model.ResourceRequiredException {
       String strValue = "";
       try {
           if(property.isBoolean()){
               strValue = String.valueOf(sob.getBooleanProperty(property));
           } else if(property.isInt()){
               strValue = String.valueOf(sob.getIntProperty(property));
           } else if(property.isDate()){
               strValue = String.valueOf(sob.getDateProperty(property));
           } else if(property.isDateTime()){
               strValue = String.valueOf(sob.getDateTimeProperty(property));
           } else if(property.isString()){
               strValue = String.valueOf(sob.getProperty(property));
           } else if(property.isFloat()){
               strValue = String.valueOf(sob.getFloatProperty(property));
           } else if(property.isDouble()){
               strValue = String.valueOf(sob.getDoubleProperty(property));
           } else if(property.isLong()){
               strValue = String.valueOf(sob.getLongProperty(property));
           } else if(property.isObjectProperty()){
               SemanticObject sobject = (SemanticObject)sob.getObjectProperty(property);
               if(null!=sobject)
                   strValue = sobject.getDisplayName();
           }
       }catch(com.hp.hpl.jena.rdf.model.ResourceRequiredException rre) {
           rre.printStackTrace();
       }catch(Exception e) {
           e.printStackTrace();
       }
       return strValue;
    }
}