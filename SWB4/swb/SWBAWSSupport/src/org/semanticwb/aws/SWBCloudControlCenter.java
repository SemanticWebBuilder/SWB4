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
 * http://www.semanticwebbuilder.org
 */
package org.semanticwb.aws;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.SortedSet;
import java.util.Timer;
import java.util.TimerTask;
import java.util.TreeSet;
import org.semanticwb.Logger;
import org.semanticwb.SWBPortal;
import org.semanticwb.SWBUtils;

/**
 * Cloud Controller
 * methods to facilitate instance management
 * @author serch
 */
public final class SWBCloudControlCenter {

    private static Logger log = SWBUtils.getLogger(SWBCloudControlCenter.class);
    private SortedSet<InstanceData> runningInstances;
    private Timer cloudTimer = null;
    private final static long _PERIOD = 5 * 60 * 1000l;
    private String placement = null;
    private String amiID = null;
    private String instanceType = null;
    private Collection<String> seg = null;
    private String keyPair = null;
    private String memory = null;
    private String appServ = null;
    private String elasticDNS = null;
    private String lbName = null;
    private double avgCPU = 0.0;
    private int maxInstances = 0;

    /**
     * update configuration and instance data
     */
    public final void reloadData() {
        log.trace("Reloading data "+SWBAWSDataUtils.checkIfCanLaunch());
        if (SWBAWSDataUtils.checkIfCanLaunch()) {
            placement = SWBAWSDataUtils.getValueOf("/AvZone");
            amiID = SWBAWSDataUtils.getValueOf("/ImageId");
            instanceType = SWBAWSDataUtils.getValueOf("/InstanceType");
            seg = new ArrayList<String>();
            seg.add(SWBAWSDataUtils.getValueOf("/SecGrpInt"));
            seg.add(SWBAWSDataUtils.getValueOf("/SecGrpExt"));
            keyPair = SWBAWSDataUtils.getValueOf("/KeyPair");
            memory = SWBAWSDataUtils.getValueOf("/Memory");
            appServ = SWBAWSDataUtils.getValueOf("/AppServer");
            elasticDNS = SWBAWSDataUtils.getValueOf("/Elastic");
            lbName = SWBAWSDataUtils.getValueOf("/LoadBal");
            try {
                maxInstances = Integer.parseInt(SWBAWSDataUtils.getValueOf("/MaxNumberInstances"));
            } catch (NumberFormatException nfe) {
                log.debug("MaxNumberInstances not an Integer", nfe);
            }
            try {
                avgCPU = Double.parseDouble(SWBAWSDataUtils.getValueOf("/MaxCPU"));
            } catch (NumberFormatException nfe) {
                log.debug("Average to Launch not a number", nfe);
            }

            runningInstances = Collections.synchronizedSortedSet(new TreeSet<InstanceData>());
//            for (InstanceData cur : SWBPortal.getAWSCloud().getRunningInstances()) {
//                if ("swbClient".equals(cur.getType()) && "running".equals(cur.getStatus())) {
//                    runningInstances.add(cur);
//                }
//            }
        } else {
            runningInstances = null;
        }
    }

    /**
     * Add an InstanceData to the list
     * @param data InstanceData to be added
     */
    public final void addInstanceData(InstanceData data) {
        runningInstances.add(data);
    }

    /**
     * remove an InstanceData from the list
     * @param data InstanceData to be removed
     */
    public final void removeInstanceData(InstanceData data) {
        runningInstances.remove(data);
    }
    
    /**
     * get an Iterator of instanceData
     * @return Iterator of instanceData
     */
    public final Iterator<InstanceData> listInstanceData(){
        return null!=runningInstances?runningInstances.iterator():null;
    }

    /**
     * get Average CPU load
     * @return average CPU load
     */
    public final double getAverageLoad() {
        double load = 0.0d;
        int instCount = 0;
        for (InstanceData id : runningInstances) {
//            load += SWBPortal.getAWSCloud().getCPUUSage(id.getId());
            instCount++;
        }
        return load / instCount;
    }

    /**
     * Activate Monitoring system
     * @param task AWS Timer Monitoring Task
     */
    public final void activateMonitoring(final TimerTask task) {
        if (null == cloudTimer) {
            cloudTimer = new Timer("AWSMonitor", true);
            cloudTimer.schedule(task, _PERIOD, _PERIOD);
        }
    }

    /**
     * get Configuration value Placement zone
     * @return value Placement zone
     */
    public String getPlacement() {
        return placement;
    }

    /**
     * get Configuration value AMIID
     * @return value AMIID
     */
    public String getAmiID() {
        return amiID;
    }

    /**
     * get Configuration value Instance type
     * @return value instance type
     */
    public String getInstanceType() {
        return instanceType;
    }

    /**
     * get Configuration value Security Groups
     * @return value Security Groups
     */
    public Collection<String> getSeg() {
        return seg;
    }

    /**
     * get Configuration value KeyPair
     * @return value KeyPair
     */
    public String getKeyPair() {
        return keyPair;
    }

    /**
     * get Configuration value Memory
     * @return memory
     */
    public String getMemory() {
        return memory;
    }

    /**
     * get Configuration value App Server
     * @return value App Server
     */
    public String getAppServ() {
        return appServ;
    }

    /**
     * get Configuration value Elastic IP
     * @return ElasticIP
     */
    public String getElasticDNS() {
        return elasticDNS;
    }

    /**
     * get Configuration value Load Balancer
     * @return value Load Balancer
     */
    public String getLbName() {
        return lbName;
    }

    /**
     * get Configuration value AvgCPU
     * @return value AVGCPU
     */
    public double getAvgCPU() {
        return avgCPU;
    }

    /**
     * get Configuration value Max instances
     * @return value Max instances
     */
    public int getMaxInstances() {
        return maxInstances;
    }

    /**
     * is the system launched
     * @return true if system is launched
     */
    boolean isLaunched() {
        return "true".equals(SWBAWSDataUtils.getValueOf("/launched"));
    }

    /**
     * count running instances
     * @return count of running instances
     */
    int currentInstances() {
        return runningInstances.size();
    }

    /**
     * obtain the first instance data
     * @return first instance data
     */
    InstanceData getFisrtInstance() {
        return runningInstances.first();
    }
}
