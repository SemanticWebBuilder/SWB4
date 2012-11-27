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

import java.io.Serializable;
import java.lang.management.MemoryUsage;

// TODO: Auto-generated Javadoc
/**
 * The Class SWBMonitorData.
 * 
 * @author serch
 */
public class SWBMonitorData implements Serializable
{

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 4012L;
    
    /** The start time. */
    public long upTime, startTime = -1L;
    
    /** The process cpu time. */
    public long processCpuTime = -1L;
    
    /** The time stamp. */
    public long timeStamp;
    
    /** The n cp us. */
    public int nCPUs;
    
    /** The objects pending. */
    public long currentHeap, maxHeap, currentCommited, objectsPending = -1L;
    
    /** The instant cpu. */
    public float instantCPU = Float.MIN_VALUE;
  //  public long commitedVirtualMem, totalPhysicalMem, freePhysicalMem, totalSwapMem, FreeSwapMem = -1L;

    /**
   * Instantiates a new sWB monitor data.
   * 
   * @param beans the beans
   */
  public SWBMonitorData(SWBMonitorBeans beans)
    {
        if (null != beans.sunOperatingSystemMXBean)
        {
            processCpuTime = beans.sunOperatingSystemMXBean.getProcessCpuTime();
//            commitedVirtualMem = beans.sunOperatingSystemMXBean.getCommittedVirtualMemorySize();
//            FreeSwapMem = beans.sunOperatingSystemMXBean.getFreeSwapSpaceSize();
//            freePhysicalMem = beans.sunOperatingSystemMXBean.getFreePhysicalMemorySize();
//            totalPhysicalMem = beans.sunOperatingSystemMXBean.getTotalPhysicalMemorySize();
//            totalSwapMem = beans.sunOperatingSystemMXBean.getTotalSwapSpaceSize();
        }
        nCPUs = beans.operatingSystemMBean.getAvailableProcessors();
        upTime = beans.runtimeMBean.getUptime();
        startTime = beans.runtimeMBean.getStartTime();
        timeStamp = System.currentTimeMillis();
        MemoryUsage u = beans.memoryMBean.getHeapMemoryUsage();
        objectsPending = beans.memoryMBean.getObjectPendingFinalizationCount();
        currentHeap = u.getUsed();
        maxHeap = u.getMax();
        currentCommited = u.getCommitted();
        instantCPU = beans.updateCPUInfo(this);
    }
}
