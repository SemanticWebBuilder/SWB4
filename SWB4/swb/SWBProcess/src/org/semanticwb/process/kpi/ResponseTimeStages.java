/**
* SemanticWebBuilder Process (SWBP) es una plataforma para la gestión de procesos de negocio mediante el uso de 
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

package org.semanticwb.process.kpi;

import java.util.Iterator;
import java.util.ArrayList;
import org.semanticwb.process.model.Process;
import org.semanticwb.process.model.ProcessInstance;
import org.semanticwb.process.model.FlowNodeInstance;
import org.semanticwb.process.model.SubProcessInstance;

/**
 *
 * @author Sergio Téllez
 */
public class ResponseTimeStages {

    public static long getAverageTimeStages(Process process, String origin, String destiny) {
        long processtime = 0;
        int closedinstances = 0;
        Iterator<ProcessInstance> it = CaseProcessInstance.getClosedProcessInstance(process).iterator();
        while(it.hasNext()) {
            closedinstances++;
            ProcessInstance pinst = it.next();
            processtime += getTimeStage(pinst,origin,destiny);
        }
        if (closedinstances == 0) return processtime;
        else return processtime/closedinstances;
    }

    public static long getMinimumTimeStages(Process process, String origin, String destiny) {
        long minimum = 0;
        long processtime = 0;
        int closedinstances = 0;
        Iterator<ProcessInstance> it = CaseProcessInstance.getClosedProcessInstance(process).iterator();
        while(it.hasNext()) {
            ProcessInstance pinst = it.next();
            processtime = getTimeStage(pinst,origin,destiny);
            if (closedinstances==0 || processtime < minimum)
                minimum = processtime;
            closedinstances++;
        }
        return minimum;
    }

    public static long getMaximumTimeStages(Process process, String origin, String destiny) {
        long maximum = 0;
        long processtime = 0;
        int closedinstances = 0;
        Iterator<ProcessInstance> it = CaseProcessInstance.getClosedProcessInstance(process).iterator();
        while(it.hasNext()) {
            ProcessInstance pinst = it.next();
            processtime = getTimeStage(pinst,origin,destiny);
            if (closedinstances==0 || processtime > maximum)
                maximum = processtime;
            closedinstances++;
        }
        return maximum;
    }

    private static long getTimeStage(ProcessInstance pinst, String origin, String destiny) {
        long processtime = 0;
        FlowNodeInstance forigin = null;
        FlowNodeInstance fodestiny = null;
        Iterator<FlowNodeInstance> fnit = pinst.listFlowNodeInstances();
        while (fnit.hasNext()) {
            ArrayList<FlowNodeInstance> container = new ArrayList();
            FlowNodeInstance fni = fnit.next();
            getStage(fni, origin, container);
            if (!container.isEmpty())
                forigin = container.get(0);
            else {
                container = new ArrayList();
                getStage(fni, destiny, container);
                if (!container.isEmpty())
                    fodestiny = container.get(0);
            }
        }
        if (null != forigin && null != fodestiny)
            processtime = fodestiny.getEnded().getTime() - forigin.getCreated().getTime();
        return processtime;
    }

    private static void getStage(FlowNodeInstance fni, String stage, ArrayList<FlowNodeInstance> container) {
        if (stage.equalsIgnoreCase("" + fni.getFlowNodeType().hashCode()))
            container.add(fni);
        if (fni instanceof SubProcessInstance) {
            SubProcessInstance spi = (SubProcessInstance)fni;
            Iterator<FlowNodeInstance> acit = spi.listFlowNodeInstances();
            if (acit.hasNext()) {
                while(acit.hasNext()) {
                    FlowNodeInstance actinst =  acit.next();
                    getStage(actinst, stage, container);
                }
            }
        }
    }
}