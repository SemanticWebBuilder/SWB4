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

/**
 *
 * @author Sergio Téllez
 */
public class CaseResponseTime {

    public long getAverageProcessInstances(Process process) {
        long sumprocesstime = 0;
        int closedinstances = 0;
        Iterator<ProcessInstance> it=CaseProcessInstance.getClosedProcessInstance(process).iterator();
        while(it.hasNext()) {
            closedinstances++;
            ProcessInstance pi =  it.next();
            sumprocesstime += pi.getEnded().getTime() - pi.getCreated().getTime();
        }
        if (closedinstances!=0)
            sumprocesstime = sumprocesstime/closedinstances;
        return sumprocesstime;
    }

    public long getMinimumProcessInstance(Process process) {
        long minimum = 0;
        long processtime = 0;
        int closedinstances = 0;
        Iterator<ProcessInstance> it=CaseProcessInstance.getClosedProcessInstance(process).iterator();
        while(it.hasNext()) {
            ProcessInstance pi =  it.next();
            processtime = pi.getEnded().getTime() - pi.getCreated().getTime();
            if (closedinstances==0 || processtime < minimum)
                minimum = processtime;
            closedinstances++;
        }
        return minimum;
    }

    public long getMaximumProcessInstance(Process process) {
        long maximum = 0;
        long processtime = 0;
        int closedinstances = 0;
        Iterator<ProcessInstance> it=CaseProcessInstance.getClosedProcessInstance(process).iterator();
        while(it.hasNext()) {
            ProcessInstance pi =  it.next();
            processtime = pi.getEnded().getTime() - pi.getCreated().getTime();
            if (closedinstances==0 || processtime > maximum)
                maximum = processtime;
            closedinstances++;
        }
        return maximum;
    }

    public static long getResponseTimeInstance(ProcessInstance pinst) {
        long processtime = 0;
        if (null != pinst.getEnded())
            processtime = pinst.getEnded().getTime() - pinst.getCreated().getTime();
        else if (null != pinst.getUpdated())
            processtime = pinst.getUpdated().getTime() - pinst.getCreated().getTime();
        return processtime;
    }
}