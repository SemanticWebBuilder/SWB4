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
import java.lang.management.ClassLoadingMXBean;
import java.lang.management.CompilationMXBean;
import java.lang.management.MemoryMXBean;
import java.lang.management.OperatingSystemMXBean;
import java.lang.management.RuntimeMXBean;
import java.lang.management.ThreadMXBean;
import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;

// TODO: Auto-generated Javadoc
/**
 * The Class SWBMonitorBeans.
 * 
 * @author serch
 */
public class SWBMonitorBeans
{
    
    /** The log. */
    private static Logger log = SWBUtils.getLogger(SWBMonitorBeans.class);

    /** The class loading m bean. */
    ClassLoadingMXBean classLoadingMBean = null;
    
    /** The compilation m bean. */
    CompilationMXBean compilationMBean = null;
    
    /** The memory m bean. */
    MemoryMXBean memoryMBean = null;
    
    /** The operating system m bean. */
    OperatingSystemMXBean operatingSystemMBean = null;
    
    /** The runtime m bean. */
    RuntimeMXBean runtimeMBean = null;
    
    /** The thread m bean. */
    ThreadMXBean threadMBean = null;
    
    /** The sun operating system mx bean. */
    com.sun.management.OperatingSystemMXBean sunOperatingSystemMXBean = null;
    
    /** The prev process cpu time. */
    long prevUpTime = -1l, prevProcessCpuTime = 0l;

    /**
     * Update cpu info.
     * 
     * @param data the data
     * @return the float
     */
    public float updateCPUInfo(SWBMonitorData data)
    {
        float cpuUsage = Float.MIN_VALUE;
        if (prevUpTime > 0L && data.upTime > prevUpTime)
        {
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

    /**
     * Instantiates a new sWB monitor beans.
     */
    public SWBMonitorBeans()
    {
        classLoadingMBean = getClassLoadingMXBean();
        compilationMBean = getCompilationMXBean();
        memoryMBean = getMemoryMXBean();
        operatingSystemMBean = getOperatingSystemMXBean();
        runtimeMBean = getRuntimeMXBean();
        threadMBean = getThreadMXBean();

        try
        {
            sunOperatingSystemMXBean = newPlatformMXBeanProxy(getPlatformMBeanServer(), OPERATING_SYSTEM_MXBEAN_NAME, com.sun.management.OperatingSystemMXBean.class);
        } catch (IOException ex)
        {
            assert (false);
        } catch (java.lang.SecurityException noSecEx) {
            log.event("Alert: No security permission to access Platform MBeanServer, cpuInfo not available");
        } catch (Error err) {
            log.event("OperatingSystemMXBean not found...");
        }
    }
}
