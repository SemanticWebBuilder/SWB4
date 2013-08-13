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

import java.io.IOException;
import static java.lang.management.ManagementFactory.*;
import static org.semanticwb.portal.monitor.SWBFormatUtils.*;
import java.lang.management.*;
import java.util.*;
import javax.management.*;
import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;

// TODO: Auto-generated Javadoc
/**
 * The Class SWBSummary.
 * 
 * @author serch
 */
public class SWBSummary
{

    /** The log. */
    private static Logger log = SWBUtils.getLogger(SWBSummary.class);

    /** The class loading m bean. */
    private ClassLoadingMXBean classLoadingMBean = null;
    
    /** The compilation m bean. */
    private CompilationMXBean compilationMBean = null;
    
    /** The memory m bean. */
    private MemoryMXBean memoryMBean = null;
    
    /** The operating system m bean. */
    private OperatingSystemMXBean operatingSystemMBean = null;
    
    /** The runtime m bean. */
    private RuntimeMXBean runtimeMBean = null;
    
    /** The thread m bean. */
    private ThreadMXBean threadMBean = null;
    
    /** The sun operating system mx bean. */
    private com.sun.management.OperatingSystemMXBean sunOperatingSystemMXBean = null;
    
    /** The memory pool proxies. */
    private List<SWBLocalMemoryPool> memoryPoolProxies = null;
    
    /** The garbage collector m beans. */
    private List<GarbageCollectorMXBean> garbageCollectorMBeans = null;
    
    /** The prev process cpu time. */
    private long prevUpTime, prevProcessCpuTime;

    /**
     * Instantiates a new sWB summary.
     */
    public SWBSummary()
    {
        classLoadingMBean = getClassLoadingMXBean();
        compilationMBean = getCompilationMXBean();
        memoryMBean = getMemoryMXBean();
        operatingSystemMBean = getOperatingSystemMXBean();
        runtimeMBean = getRuntimeMXBean();
        threadMBean = getThreadMXBean();
        garbageCollectorMBeans = getGarbageCollectorMXBeans();

        try
        {
            sunOperatingSystemMXBean = newPlatformMXBeanProxy(getPlatformMBeanServer(), OPERATING_SYSTEM_MXBEAN_NAME, com.sun.management.OperatingSystemMXBean.class);
            getMemoryPoolProxies();
        } catch (IOException ex)
        {
            assert (false);
        } catch (java.lang.SecurityException noSecEx)
        {
            log.event("Alert: No security permission to access Platform MBeanServer, local system info not available");
        } catch (Error err) {
            log.event("OperatingSystemMXBean not found...");
        }
    }

    /**
     * Gets the memory pool proxies.
     * 
     * @return the memory pool proxies
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public Collection<SWBLocalMemoryPool> getMemoryPoolProxies()
            throws IOException
    {

        // TODO: How to deal with changes to the list??
        if (memoryPoolProxies == null)
        {
            ObjectName poolName = null;
            try
            {
                poolName = new ObjectName(MEMORY_POOL_MXBEAN_DOMAIN_TYPE + ",*");
            } catch (MalformedObjectNameException e)
            {
                // should not reach here
                assert (false);
            }
            Set mbeans = getPlatformMBeanServer().queryNames(poolName, null);
            if (mbeans != null)
            {
                memoryPoolProxies = new ArrayList<SWBLocalMemoryPool>();
                Iterator iterator = mbeans.iterator();
                while (iterator.hasNext())
                {
                    ObjectName objName = (ObjectName) iterator.next();
                    SWBLocalMemoryPool p = new SWBLocalMemoryPool(objName);
                    memoryPoolProxies.add(p);
                }
            }
        }
        return memoryPoolProxies;
    }

    /**
     * Gets the sample.
     * 
     * @return the sample
     */
    public SWBSummaryData getSample()
    {
        SWBSummaryData ret = new SWBSummaryData();
        ret.vmName = runtimeMBean.getVmName() + " version " + runtimeMBean.getVmVersion();
        ret.vmVendor = runtimeMBean.getVmVendor();
        ret.vmInstanceName = runtimeMBean.getName();
        if (null != sunOperatingSystemMXBean)
        {
            ret.processCpuTime = sunOperatingSystemMXBean.getProcessCpuTime();
            ret.commitedVirtualMem = sunOperatingSystemMXBean.getCommittedVirtualMemorySize();
            ret.freeSwapMem = sunOperatingSystemMXBean.getFreeSwapSpaceSize();
            ret.freePhysicalMem = sunOperatingSystemMXBean.getFreePhysicalMemorySize();
            ret.totalPhysicalMem = sunOperatingSystemMXBean.getTotalPhysicalMemorySize();
            ret.totalSwapMem = sunOperatingSystemMXBean.getTotalSwapSpaceSize();
//            ret.systemLoadAverage = sunOperatingSystemMXBean.getSystemLoadAverage();
        }
        ret.jitCompiler = compilationMBean.getName();
        ret.vmLibraryPath = runtimeMBean.getLibraryPath();
        ret.vmClassPath = runtimeMBean.getClassPath();
        ret.vmBootClassPath = runtimeMBean.getBootClassPath();
        Iterator<String> it = runtimeMBean.getInputArguments().iterator();
        StringBuilder sb = new StringBuilder();
        while (it.hasNext())
        {
            sb.append(it.next()).append(" ");
        }
        ret.vmArgs = sb.toString().trim();
        ret.osName = operatingSystemMBean.getName() + " " + operatingSystemMBean.getVersion();
        ret.vmArch = operatingSystemMBean.getArch();
        ret.nCPUs = operatingSystemMBean.getAvailableProcessors();
        ret.upTime = runtimeMBean.getUptime();
        ret.startTime = runtimeMBean.getStartTime();
//        ret.systemLoadAverage = operatingSystemMBean.getSystemLoadAverage();
        ret.liveTh = threadMBean.getThreadCount();
        ret.deamonTh = threadMBean.getDaemonThreadCount();
        ret.peakTh = threadMBean.getPeakThreadCount();
        ret.startedTh = threadMBean.getTotalStartedThreadCount();
        ret.currentClass = classLoadingMBean.getLoadedClassCount();
        ret.totalClass = classLoadingMBean.getTotalLoadedClassCount();
        ret.unloadedClass = classLoadingMBean.getUnloadedClassCount();
        MemoryUsage u = memoryMBean.getHeapMemoryUsage();
        ret.currentHeap = u.getUsed();
        ret.maxHeap = u.getMax();
        ret.currentCommited = u.getCommitted();
        ret.objectsPending = memoryMBean.getObjectPendingFinalizationCount();
        ArrayList<String> gcList = new ArrayList<String>();
        for (GarbageCollectorMXBean garbageCollectorMBean : garbageCollectorMBeans)
        {
            String gcName = garbageCollectorMBean.getName();
            long gcCount = garbageCollectorMBean.getCollectionCount();
            long gcTime = garbageCollectorMBean.getCollectionTime();
            gcList.add("Nombre=" + gcName + " Colecciones: " + gcCount + " Tiempo: " + formatTime(gcTime));
        }
        ret.gcDetails = gcList.toArray(new String[gcList.size()]);
//        try {
//        ret.internalName=(String)lvm.findByName("sun.rt.internalVersion").getValue();
//        } catch (Exception e) {ret.internalName="No disponible";}
        ret.instantCPU = updateCPUInfo(ret);
        return ret;
    }

    /**
     * Update cpu info.
     * 
     * @param data the data
     * @return the float
     */
    public float updateCPUInfo(SWBSummaryData data) {
        float cpuUsage = Float.MIN_VALUE;
	    if (prevUpTime > 0L && data.upTime > prevUpTime) {
		// elapsedCpu is in ns and elapsedTime is in ms.
		long elapsedCpu = data.processCpuTime - prevProcessCpuTime;
		long elapsedTime = data.upTime - prevUpTime;
		// cpuUsage could go higher than 100% because elapsedTime
		// and elapsedCpu are not fetched simultaneously. Limit to
		// 99% to avoid Plotter showing a scale from 0% to 200%.
		cpuUsage =
		    Math.min(99.99F,
			     elapsedCpu / (elapsedTime * 10000F * data.nCPUs));
	    }
	    this.prevUpTime = data.upTime;
	    this.prevProcessCpuTime = data.processCpuTime;
            return cpuUsage;
	}

}
