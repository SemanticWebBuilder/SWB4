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
package org.semanticwb.portal.monitor;

import com.sun.management.GcInfo;
import static java.lang.management.ManagementFactory.*;
import static org.semanticwb.portal.monitor.SWBFormatUtils.*;
import java.lang.management.GarbageCollectorMXBean;
import java.lang.management.MemoryUsage;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

// TODO: Auto-generated Javadoc
/**
 * The Class SWBGCDump.
 * 
 * @author serch
 */
public class SWBGCDump
{

    /** The garbage collector m beans. */
    private static List<GarbageCollectorMXBean> garbageCollectorMBeans = getGarbageCollectorMXBeans();

    /**
     * Gets the collectors.
     * 
     * @return the collectors
     */
    public com.sun.management.GarbageCollectorMXBean[] getCollectors(){
        return garbageCollectorMBeans.toArray(new com.sun.management.GarbageCollectorMXBean[garbageCollectorMBeans.size()]);
    }

    /**
     * Gets the gc.
     * 
     * @return the gc
     */
    public static String getGc()
    {
        StringBuilder ret = new StringBuilder();
        for (GarbageCollectorMXBean gc : garbageCollectorMBeans)
        {
            ret.append("[" + gc.getName() + ": ");
            ret.append("Colecciones=" + gc.getCollectionCount());
            ret.append(" Tiempo usado=" + formatMillis(gc.getCollectionTime()));
            ret.append("]\n");
        }
        return ret.toString();
    }

    /**
     * Gets the verbose gc.
     * 
     * @return the verbose gc
     */
    public static String getVerboseGc()
    {
        StringBuilder ret = new StringBuilder();
        for (GarbageCollectorMXBean gc : garbageCollectorMBeans)
        {
            ret.append("Colector:" + gc.getName() + ": ");
            ret.append("Colecciones=" + gc.getCollectionCount());
            ret.append(" Tiempo usado=" + formatMillis(gc.getCollectionTime()));
            ret.append("\n");
            com.sun.management.GarbageCollectorMXBean gci = (com.sun.management.GarbageCollectorMXBean) gc;
            GcInfo info = gci.getLastGcInfo();
            if (null != info)
            {
                ret.append(getGCInfo(info));
            }
        }
        return ret.toString();
    }

    /**
     * Gets the gC info.
     * 
     * @param gci the gci
     * @return the gC info
     */
    public static String getGCInfo(GcInfo gci)
    {

        try
        {
            StringBuilder ret = new StringBuilder();
            long id = gci.getId();
            long startTime = gci.getStartTime();
            long endTime = gci.getEndTime();
            long duration = gci.getDuration();



            if (startTime == endTime)
            {
                return "";
            }
            ret.append("ID: " + id + "\n");
            ret.append("Hora Inicio: " + startTime + "\n");
            ret.append("Hora de Termino: " + endTime + "\n");
            ret.append("Duraci&oacute;n: " + duration + "\n");
            Map mapBefore = gci.getMemoryUsageBeforeGc();
            Map mapAfter = gci.getMemoryUsageAfterGc();


            ret.append("\nDetalles antes del GC....\n");
            Set memType = mapBefore.keySet();
            Iterator it = memType.iterator();
            while (it.hasNext())
            {
                String type = (String) it.next();
                ret.append(type + "\n");
                MemoryUsage mu1 = (MemoryUsage) mapBefore.get(type);
                ret.append("Tama&ntilde;o Inicial: " + mu1.getInit());
                ret.append(" Usada: " + mu1.getUsed());
                ret.append(" Max: " + mu1.getMax());
                ret.append(" Asignada: " + mu1.getCommitted());
                ret.append(" \n");
            }

            ret.append("\nDetalles despues del GC....\n");
            memType = mapAfter.keySet();
            it = memType.iterator();
            while (it.hasNext())
            {
                String type = (String) it.next();
                ret.append(type + "\n");
                MemoryUsage mu2 = (MemoryUsage) mapAfter.get(type);
                ret.append("Tama&ntilde;o Inicial: " + mu2.getInit());
                ret.append(" Usada: " + mu2.getUsed());
                ret.append(" Max: " + mu2.getMax());
                ret.append(" Asignada: " + mu2.getCommitted());
                ret.append(" \n");
            }
            ret.append("\n\n");
            return ret.toString();
        } catch (RuntimeException re)
        {
            throw re;
        } catch (Exception exp)
        {
            throw new RuntimeException(exp);
        }
    }
}
