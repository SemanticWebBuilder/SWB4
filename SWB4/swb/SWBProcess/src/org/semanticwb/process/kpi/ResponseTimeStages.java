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
import org.semanticwb.process.model.Process;
import org.semanticwb.process.model.ProcessInstance;
import org.semanticwb.process.model.FlowNodeInstance;
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
            FlowNodeInstance fni = fnit.next();
            if (origin.equalsIgnoreCase(fni.getFlowNodeType().getTitle()))
                forigin = fni;
            else if (destiny.equalsIgnoreCase(fni.getFlowNodeType().getTitle()))
                fodestiny = fni;
        }
        if (null != forigin && null != fodestiny)
            processtime = fodestiny.getEnded().getTime() - forigin.getCreated().getTime();
        return processtime;
    }
}