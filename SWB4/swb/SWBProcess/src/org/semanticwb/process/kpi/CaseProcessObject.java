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
import org.semanticwb.process.model.Process;
import org.semanticwb.process.model.ProcessObject;
import org.semanticwb.process.model.ProcessInstance;
import org.semanticwb.process.model.FlowNodeInstance;
import org.semanticwb.process.model.SubProcessInstance;

import org.semanticwb.platform.SemanticObject;
import org.semanticwb.platform.SemanticProperty;

/**
 *
 * @author Sergio Téllez
 */
public class CaseProcessObject {

    public static Object sum(Process process, String processObjectURI, String propertyName) {
        Object sum = null;
        Iterator<ProcessInstance> it = CaseProcessInstance.getClosedProcessInstance(process).iterator();
        while(it.hasNext()) {
            ProcessInstance pinst = it.next();
            sum = getProcessObjects(pinst, processObjectURI, propertyName, sum);
        }
        return sum;
    }

    public static Object average(Process process, String processObjectURI, String propertyName) {
        int instances = 0;
        Object avg = null;
        Iterator<ProcessInstance> it = CaseProcessInstance.getClosedProcessInstance(process).iterator();
        while(it.hasNext()) {
            ProcessInstance pinst = it.next();
            avg = getProcessObjects(pinst, processObjectURI, propertyName, avg);
            instances++;
        }
        return averageObject(avg,instances);
    }

    public static Object maximum(Process process, String processObjectURI, String propertyName) {
        Object maximum = null;
        Iterator<ProcessInstance> it = CaseProcessInstance.getClosedProcessInstance(process).iterator();
        while(it.hasNext()) {
            ProcessInstance pinst = it.next();
            maximum = getMaximumProcessObject(pinst, processObjectURI, propertyName, maximum);
        }
        return maximum;
    }

    public static Object minimum(Process process, String processObjectURI, String propertyName) {
        Object minimum = null;
        Iterator<ProcessInstance> it = CaseProcessInstance.getClosedProcessInstance(process).iterator();
        while(it.hasNext()) {
            ProcessInstance pinst = it.next();
            minimum = getMinimumProcessObject(pinst, processObjectURI, propertyName, minimum);
        }
        return minimum;
    }

    public static ArrayList distincts(Process process, String processObjectURI, String propertyName) {
        ArrayList distincts = new ArrayList();
        Iterator<ProcessInstance> it = CaseProcessInstance.getClosedProcessInstance(process).iterator();
        while(it.hasNext()) {
            ProcessInstance pinst = it.next();
            distinctProcessObjects(pinst, processObjectURI, propertyName, distincts);
        }
        return distincts;
    }

    private static void distinctProcessObjects(ProcessInstance pinst, String processObjectURI, String propertyName, ArrayList distincts) {
        Iterator<ProcessObject> objit = pinst.listProcessObjects();
        while(objit.hasNext()) {
            ProcessObject obj =  objit.next();
            if(obj.getURI().indexOf(processObjectURI) > -1) {
                Iterator<SemanticProperty> spit = obj.getSemanticObject().listProperties();
                while(spit.hasNext()) {
                    SemanticProperty sp = spit.next();
                    if(propertyName.equals(sp.getName()))
                        distinctObjects(obj.getSemanticObject(), sp, distincts);
                }
            }
        }
        Iterator<FlowNodeInstance> foit = pinst.listFlowNodeInstances();
        while(foit.hasNext()) {
            FlowNodeInstance flobin = foit.next();
            if (flobin instanceof SubProcessInstance)
                distinctProcessObjects((SubProcessInstance)flobin, processObjectURI, propertyName, distincts);
        }
    }

    private static void distinctProcessObjects(SubProcessInstance spinst, String processObjectURI, String propertyName, ArrayList distincts) {
        Iterator<ProcessObject> objit = spinst.listProcessObjects();
        while(objit.hasNext()) {
            ProcessObject obj =  objit.next();
            if(obj.getURI().indexOf(processObjectURI) > -1) {
                Iterator<SemanticProperty> spit = obj.getSemanticObject().listProperties();
                while(spit.hasNext()) {
                    SemanticProperty sp = spit.next();
                    if(propertyName.equals(sp.getName()))
                        distinctObjects(obj.getSemanticObject(), sp, distincts);
                }
            }
        }
        Iterator<FlowNodeInstance> foit = spinst.listFlowNodeInstances();
        while(foit.hasNext()) {
            FlowNodeInstance flobin = foit.next();
            if (flobin instanceof SubProcessInstance)
                distinctProcessObjects((SubProcessInstance)flobin, processObjectURI, propertyName, distincts);
        }
    }

    private static Object getMinimumProcessObject(ProcessInstance pinst, String processObjectURI, String propertyName, Object minimum) {
        Iterator<ProcessObject> objit = pinst.listProcessObjects();
        while(objit.hasNext()) {
            ProcessObject obj =  objit.next();
            if(obj.getURI().indexOf(processObjectURI) > -1) {
                Iterator<SemanticProperty> spit = obj.getSemanticObject().listProperties();
                while(spit.hasNext()) {
                    SemanticProperty sp = spit.next();
                    if(propertyName.equals(sp.getName()))
                        minimum = minimumObject(obj.getSemanticObject(), sp, minimum);
                }
            }
        }
        Iterator<FlowNodeInstance> foit = pinst.listFlowNodeInstances();
        while(foit.hasNext()) {
            FlowNodeInstance flobin = foit.next();
            if (flobin instanceof SubProcessInstance)
                minimum = getMinimumProcessObject((SubProcessInstance)flobin, processObjectURI, propertyName, minimum);
        }
        return minimum;
    }

    private static Object getMinimumProcessObject(SubProcessInstance pinst, String processObjectURI, String propertyName, Object minimum) {
        Iterator<ProcessObject> objit = pinst.listProcessObjects();
        while(objit.hasNext()) {
            ProcessObject obj =  objit.next();
            if(obj.getURI().indexOf(processObjectURI) > -1) {
                Iterator<SemanticProperty> spit = obj.getSemanticObject().listProperties();
                while(spit.hasNext()) {
                    SemanticProperty sp = spit.next();
                    if(propertyName.equals(sp.getName()))
                        minimum = minimumObject(obj.getSemanticObject(), sp, minimum);
                }
            }
        }
        Iterator<FlowNodeInstance> foit = pinst.listFlowNodeInstances();
        while(foit.hasNext()) {
            FlowNodeInstance flobin = foit.next();
            if (flobin instanceof SubProcessInstance)
                minimum = getMinimumProcessObject((SubProcessInstance)flobin, processObjectURI, propertyName, minimum);
        }
        return minimum;
    }

    private static Object getMaximumProcessObject(ProcessInstance pinst, String processObjectURI, String propertyName, Object maximum) {
        Iterator<ProcessObject> objit = pinst.listProcessObjects();
        while(objit.hasNext()) {
            ProcessObject obj =  objit.next();
            if(obj.getURI().indexOf(processObjectURI) > -1) {
                Iterator<SemanticProperty> spit = obj.getSemanticObject().listProperties();
                while(spit.hasNext()) {
                    SemanticProperty sp = spit.next();
                    if(propertyName.equals(sp.getName()))
                        maximum = maximumObject(obj.getSemanticObject(), sp, maximum);
                }
            }
        }
        Iterator<FlowNodeInstance> foit = pinst.listFlowNodeInstances();
        while(foit.hasNext()) {
            FlowNodeInstance flobin = foit.next();
            if (flobin instanceof SubProcessInstance)
                maximum = getMaximumProcessObject((SubProcessInstance)flobin, processObjectURI, propertyName, maximum);
        }
        return maximum;
    }

    private static Object getMaximumProcessObject(SubProcessInstance spinst, String processObjectURI, String propertyName, Object maximum) {
        Iterator<ProcessObject> objit = spinst.listProcessObjects();
        while(objit.hasNext()) {
            ProcessObject obj =  objit.next();
            if(obj.getURI().indexOf(processObjectURI) > -1) {
                Iterator<SemanticProperty> spit = obj.getSemanticObject().listProperties();
                while(spit.hasNext()) {
                    SemanticProperty sp = spit.next();
                    if(propertyName.equals(sp.getName()))
                        maximum = maximumObject(obj.getSemanticObject(), sp, maximum);
                }
            }
        }
        Iterator<FlowNodeInstance> foit = spinst.listFlowNodeInstances();
        while(foit.hasNext()) {
            FlowNodeInstance flobin = foit.next();
            if (flobin instanceof SubProcessInstance)
                maximum = getMaximumProcessObject((SubProcessInstance)flobin, processObjectURI, propertyName, maximum);
        }
        return maximum;
    }

    private static Object getProcessObjects(ProcessInstance pinst, String processObjectURI, String propertyName, Object sum) {
        Iterator<ProcessObject> objit = pinst.listProcessObjects();
        while(objit.hasNext()) {
            ProcessObject obj =  objit.next();
            if (obj.getURI().indexOf(processObjectURI) > -1) {
                Iterator<SemanticProperty> spit = obj.getSemanticObject().listProperties();
                while(spit.hasNext()) {
                    SemanticProperty sp = spit.next();
                    if(propertyName.equals(sp.getName()))
                        sum = sumatoryObject(obj.getSemanticObject(), sp, sum);
                }
            }
        }
        Iterator<FlowNodeInstance> foit = pinst.listFlowNodeInstances();
        while(foit.hasNext()) {
            FlowNodeInstance flobin = foit.next();
            if (flobin instanceof SubProcessInstance)
                sum = getProcessObjects((SubProcessInstance)flobin, processObjectURI, propertyName, sum);
        }
        return sum;
    }

    private static Object getProcessObjects(SubProcessInstance spinst, String processObjectURI, String propertyName, Object sum) {
        Iterator<ProcessObject> objit = spinst.listProcessObjects();
        while (objit.hasNext()) {
            ProcessObject obj =  objit.next();
            if(obj.getURI().indexOf(processObjectURI) > -1) {
                Iterator<SemanticProperty> spit = obj.getSemanticObject().listProperties();
                while(spit.hasNext()) {
                    SemanticProperty sp = spit.next();
                    if(propertyName.equals(sp.getName()))
                        sum = sumatoryObject(obj.getSemanticObject(), sp, sum);
                }
            }
        }
        Iterator<FlowNodeInstance> foit = spinst.listFlowNodeInstances();
        while(foit.hasNext()) {
            FlowNodeInstance flobin = foit.next();
            if (flobin instanceof SubProcessInstance)
                 sum = getProcessObjects((SubProcessInstance)flobin, processObjectURI, propertyName, sum);
        }
        return sum;
    }

    /**
     * Obtener la sumatoria de una propiedad para una instancia de un artefacto
     * (SemanticObject)
     *
     * @param            sob SemanticObject
     * @param            property SemanticProperty
     * @return           Object Valor de la propiedad
     * @see
    */
    private static Object sumatoryObject(SemanticObject sob, SemanticProperty property, Object value) throws com.hp.hpl.jena.rdf.model.ResourceRequiredException {
        try {
            if(property.isInt()) {
                int ivalue = 0;
                if (null != value)
                    ivalue = ((Integer)value).intValue();
                ivalue += sob.getIntProperty(property);
                value = (Object)ivalue;
            }else if(property.isFloat()) {
                float fvalue = 0;
                if (null != value)
                    fvalue = ((Float)value).floatValue();
                fvalue += sob.getFloatProperty(property);
                value = (Object)fvalue;
            }else if(property.isDouble()) {
                double dvalue = 0.0;
                if (null != value)
                    dvalue = ((Double)value).floatValue();
                dvalue += sob.getDoubleProperty(property);
                value = (Object)dvalue;
            } else if(property.isLong()) {
                long lvalue = 0;
                if (null != value)
                    lvalue = ((Long)value).longValue();
                lvalue += sob.getLongProperty(property);
                value = (Object)lvalue;
            }
        }catch(com.hp.hpl.jena.rdf.model.ResourceRequiredException rre) {
            rre.printStackTrace();
        }catch(Exception e) {
            e.printStackTrace();
        }
        return value;
    }

    /**
     * Obtener el máximo de una propiedad para una instancia de un artefacto
     * (SemanticObject)
     *
     * @param            sob SemanticObject
     * @param            property SemanticProperty
     * @return      		Object Valor de la propiedad
     * @see
    */
    private static Object maximumObject(SemanticObject sob, SemanticProperty property, Object value) throws com.hp.hpl.jena.rdf.model.ResourceRequiredException {
        try {
            if(property.isInt()){
                int ivalue = 0;
                if (null != value)
                    ivalue = ((Integer)value).intValue();
                if (ivalue < sob.getIntProperty(property))
                    value = new Integer(sob.getIntProperty(property));
            }else if(property.isFloat()){
                float fvalue = 0;
                if (null != value)
                    fvalue = ((Float)value).floatValue();
                if (fvalue < sob.getFloatProperty(property))
                    value = new Float(sob.getFloatProperty(property));
            }else if(property.isDouble()){
                double dvalue = 0.0;
                if (null != value)
                    dvalue = ((Double)value).floatValue();
                if (dvalue < sob.getDoubleProperty(property))
                    value = new Double(sob.getDoubleProperty(property));
            } else if(property.isLong()){
                long lvalue = 0;
                if (null != value)
                    lvalue = ((Long)value).longValue();
                if (lvalue < sob.getLongProperty(property))
                    value = new Long(sob.getLongProperty(property));
            }
        } catch(com.hp.hpl.jena.rdf.model.ResourceRequiredException rre){
            rre.printStackTrace();
        } catch(Exception e){
            e.printStackTrace();
        }return value;
    }

    /**
     * Obtener el máximo de una propiedad para una instancia de un artefacto
     * (SemanticObject)
     *
     * @param            sob SemanticObject
     * @param            property SemanticProperty
     * @return      		Object Valor de la propiedad
     * @see
    */
    private static Object minimumObject(SemanticObject sob, SemanticProperty property, Object value) throws com.hp.hpl.jena.rdf.model.ResourceRequiredException {
        try {
            if (null != value) {
                if(property.isInt()) {
                    int ivalue = ((Integer)value).intValue();
                    if (ivalue > sob.getIntProperty(property))
                        value = new Integer(sob.getIntProperty(property));
                }else if(property.isFloat()) {
                    float fvalue = ((Float)value).floatValue();
                    if (fvalue > sob.getFloatProperty(property))
                        value = new Float(sob.getFloatProperty(property));
                }else if(property.isDouble()) {
                    double dvalue = ((Double)value).floatValue();
                    if (dvalue > sob.getDoubleProperty(property))
                        value = new Double(sob.getDoubleProperty(property));
                }else if(property.isLong()) {
                    long lvalue = ((Long)value).longValue();
                    if (lvalue > sob.getLongProperty(property))
                        value = new Long(sob.getLongProperty(property));
                }
            }else {
                if(property.isInt())
                    value = new Integer(sob.getIntProperty(property));
                else if(property.isFloat())
                    value = new Float(sob.getFloatProperty(property));
                else if(property.isDouble())
                    value = new Double(sob.getDoubleProperty(property));
                else if(property.isLong())
                    value = new Long(sob.getLongProperty(property));
            }
        } catch(com.hp.hpl.jena.rdf.model.ResourceRequiredException rre) {
            rre.printStackTrace();
        } catch(Exception e) {
            e.printStackTrace();
        }
        return value;
    }

    /**
     * Obtener el máximo de una propiedad para una instancia de un artefacto (SemanticObject)
     * @param            sob SemanticObject
     * @param            property SemanticProperty
     * @return      		Object Valor de la propiedad
     * @see
    */
    private static void distinctObjects(SemanticObject sob, SemanticProperty property, ArrayList distincts) throws com.hp.hpl.jena.rdf.model.ResourceRequiredException {
        try {
            if(property.isInt()) {
                Integer ivalue = new Integer(sob.getIntProperty(property));
                if (!distincts.contains(ivalue))
                    distincts.add(ivalue);
            }else if(property.isFloat()){
                Float fvalue = new Float(sob.getFloatProperty(property));
                if (!distincts.contains(fvalue))
                    distincts.add(fvalue);
            }else if(property.isDouble()){
                Double dvalue = new Double(sob.getDoubleProperty(property));
                if (!distincts.contains(dvalue))
                    distincts.add(dvalue);
            }else if(property.isLong()){
                Long lvalue = new Long(sob.getLongProperty(property));
                if (!distincts.contains(lvalue))
                    distincts.add(lvalue);
            }
        }catch(com.hp.hpl.jena.rdf.model.ResourceRequiredException rre) {
            rre.printStackTrace();
        }catch(Exception e) {
            e.printStackTrace();
        }
    }

    private static Object averageObject(Object sum, int instances) {
        double avg = 0;
        if (sum instanceof Integer)
            avg = ((Integer)sum).intValue()/instances;
        else if (sum instanceof Float)
            avg = ((Float)sum).floatValue()/instances;
        else if (sum instanceof Double)
            avg = ((Double)sum).doubleValue()/instances;
        else if (sum instanceof Long)
            avg = ((Long)sum).longValue()/instances;
        return new java.lang.Double(avg);
    }
}