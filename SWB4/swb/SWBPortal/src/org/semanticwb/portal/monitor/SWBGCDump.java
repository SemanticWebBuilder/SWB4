/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
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

/**
 *
 * @author serch
 */
public class SWBGCDump
{

    private static List<GarbageCollectorMXBean> garbageCollectorMBeans = getGarbageCollectorMXBeans();

    public GarbageCollectorMXBean[] getCollectors(){
        return garbageCollectorMBeans.toArray(new GarbageCollectorMXBean[garbageCollectorMBeans.size()]);
    }

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
            ret.append("Diraci&oacute;n: " + duration + "\n");
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
