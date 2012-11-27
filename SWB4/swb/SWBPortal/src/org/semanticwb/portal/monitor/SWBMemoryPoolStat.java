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

import java.lang.management.MemoryUsage;

// TODO: Auto-generated Javadoc
/**
 * The Class SWBMemoryPoolStat.
 * 
 * @author serch
 */
public class SWBMemoryPoolStat
{

    /** The pool name. */
    private String poolName;
    
    /** The usage threshold. */
    private long usageThreshold;
    
    /** The usage. */
    private MemoryUsage usage;
    
    /** The last gc id. */
    private long lastGcId;
    
    /** The last gc start time. */
    private long lastGcStartTime;
    
    /** The last gc end time. */
    private long lastGcEndTime;
    
    /** The collect threshold. */
    private long collectThreshold;
    
    /** The before gc usage. */
    private MemoryUsage beforeGcUsage;
    
    /** The after gc usage. */
    private MemoryUsage afterGcUsage;

    /**
     * Instantiates a new sWB memory pool stat.
     * 
     * @param name the name
     * @param usageThreshold the usage threshold
     * @param usage the usage
     * @param lastGcId the last gc id
     * @param lastGcStartTime the last gc start time
     * @param lastGcEndTime the last gc end time
     * @param collectThreshold the collect threshold
     * @param beforeGcUsage the before gc usage
     * @param afterGcUsage the after gc usage
     */
    SWBMemoryPoolStat(String name,
            long usageThreshold,
            MemoryUsage usage,
            long lastGcId,
            long lastGcStartTime,
            long lastGcEndTime,
            long collectThreshold,
            MemoryUsage beforeGcUsage,
            MemoryUsage afterGcUsage)
    {
        this.poolName = name;
        this.usageThreshold = usageThreshold;
        this.usage = usage;
        this.lastGcId = lastGcId;
        this.lastGcStartTime = lastGcStartTime;
        this.lastGcEndTime = lastGcEndTime;
        this.collectThreshold = collectThreshold;
        this.beforeGcUsage = beforeGcUsage;
        this.afterGcUsage = afterGcUsage;
    }

    /**
     * Gets the pool name.
     * 
     * @return the pool name
     */
    public String getPoolName()
    {
        return poolName;
    }

    /**
     * Gets the usage.
     * 
     * @return the usage
     */
    public MemoryUsage getUsage()
    {
        return usage;
    }

    /**
     * Gets the usage threshold.
     * 
     * @return the usage threshold
     */
    public long getUsageThreshold()
    {
        return usageThreshold;
    }

    /**
     * Gets the collection usage threshold.
     * 
     * @return the collection usage threshold
     */
    public long getCollectionUsageThreshold()
    {
        return collectThreshold;
    }

    /**
     * Gets the last gc id.
     * 
     * @return the last gc id
     */
    public long getLastGcId()
    {
        return lastGcId;
    }

    /**
     * Gets the last gc start time.
     * 
     * @return the last gc start time
     */
    public long getLastGcStartTime()
    {
        return lastGcStartTime;
    }

    /**
     * Gets the last gc end time.
     * 
     * @return the last gc end time
     */
    public long getLastGcEndTime()
    {
        return lastGcEndTime;
    }

    /**
     * Gets the before gc usage.
     * 
     * @return the before gc usage
     */
    public MemoryUsage getBeforeGcUsage()
    {
        return beforeGcUsage;
    }

    /**
     * Gets the after gc usage.
     * 
     * @return the after gc usage
     */
    public MemoryUsage getAfterGcUsage()
    {
        return beforeGcUsage;
    }
}
