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

import static java.lang.management.ManagementFactory.*;
import com.sun.management.GarbageCollectorMXBean;
import com.sun.management.GcInfo;
import java.lang.management.MemoryPoolMXBean;
import java.lang.management.MemoryUsage;
import java.util.*;
import javax.management.ObjectName;

// TODO: Auto-generated Javadoc
/**
 * The Class SWBLocalMemoryPool.
 * 
 * @author serch
 */
public class SWBLocalMemoryPool
{

    /** The pool name. */
    private String poolName;
   // private ObjectName objName;
    /** The pool. */
   private MemoryPoolMXBean pool;
    
    /** The gc m beans. */
    private Map<ObjectName, Long> gcMBeans;
    
    /** The last gc info. */
    private GcInfo lastGcInfo;

    /**
     * Instantiates a new sWB local memory pool.
     * 
     * @param poolName the pool name
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public SWBLocalMemoryPool(ObjectName poolName) throws java.io.IOException
    {
        //this.objName = objName;
        this.pool = newPlatformMXBeanProxy(getPlatformMBeanServer(),
                poolName.toString(),
                MemoryPoolMXBean.class);
        this.poolName = this.pool.getName();
        this.gcMBeans = new HashMap<ObjectName, Long>();
        this.lastGcInfo = null;

        String[] mgrNames = pool.getMemoryManagerNames();
        for (String name : mgrNames)
        {
            try
            {
                ObjectName mbeanName = new ObjectName(GARBAGE_COLLECTOR_MXBEAN_DOMAIN_TYPE + ",name=" + name);
                if (getPlatformMBeanServer().isRegistered(mbeanName))
                {
                    gcMBeans.put(mbeanName, new Long(0));
                }
            } catch (Exception e)
            {
                assert false;
            }

        }
    }

    /**
     * Checks if is collected memory pool.
     * 
     * @return true, if is collected memory pool
     */
    public boolean isCollectedMemoryPool()
    {
        return (gcMBeans.size() != 0);
    }

//    public ObjectName getObjectName()
//    {
//        return objName;
//    }

    /**
 * Gets the stat.
 * 
 * @return the stat
 * @throws IOException Signals that an I/O exception has occurred.
 */
public SWBMemoryPoolStat getStat() throws java.io.IOException
    {
        long usageThreshold = (pool.isUsageThresholdSupported()
                ? pool.getUsageThreshold()
                : -1);
        long collectThreshold = (pool.isCollectionUsageThresholdSupported()
                ? pool.getCollectionUsageThreshold()
                : -1);
        long lastGcStartTime = 0;
        long lastGcEndTime = 0;
        MemoryUsage beforeGcUsage = null;
        MemoryUsage afterGcUsage = null;
        long gcId = 0;
        if (lastGcInfo != null)
        {
            gcId = lastGcInfo.getId();
            lastGcStartTime = lastGcInfo.getStartTime();
            lastGcEndTime = lastGcInfo.getEndTime();
            beforeGcUsage = lastGcInfo.getMemoryUsageBeforeGc().get(poolName);
            afterGcUsage = lastGcInfo.getMemoryUsageAfterGc().get(poolName);
        }

        Set<Map.Entry<ObjectName, Long>> set = gcMBeans.entrySet();
        for (Map.Entry<ObjectName, Long> e : set)
        {
            GarbageCollectorMXBean gc =
                    newPlatformMXBeanProxy(getPlatformMBeanServer(),
                    e.getKey().getCanonicalName(),
                    com.sun.management.GarbageCollectorMXBean.class);
            Long gcCount = e.getValue();
            Long newCount = gc.getCollectionCount();
            if (newCount > gcCount)
            {
                gcMBeans.put(e.getKey(), new Long(newCount));
                lastGcInfo = gc.getLastGcInfo();
                if (lastGcInfo.getEndTime() > lastGcEndTime)
                {
                    gcId = lastGcInfo.getId();
                    lastGcStartTime = lastGcInfo.getStartTime();
                    lastGcEndTime = lastGcInfo.getEndTime();
                    beforeGcUsage = lastGcInfo.getMemoryUsageBeforeGc().get(poolName);
                    afterGcUsage = lastGcInfo.getMemoryUsageAfterGc().get(poolName);
                    assert (beforeGcUsage != null);
                    assert (afterGcUsage != null);
                }
            }
        }

        MemoryUsage usage = pool.getUsage();
        return new SWBMemoryPoolStat(poolName,
                usageThreshold,
                usage,
                gcId,
                lastGcStartTime,
                lastGcEndTime,
                collectThreshold,
                beforeGcUsage,
                afterGcUsage);
    }
}
