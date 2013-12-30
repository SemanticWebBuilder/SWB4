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
package org.semanticwb.dimension;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Timer;
import java.util.TimerTask;
import org.semanticwb.Logger;
import org.semanticwb.SWBPortal;
import org.semanticwb.SWBUtils;

/**
 * Cloud Controller methods to facilitate instance management
 *
 * @author serch
 */
public final class SWBCloudControlCenter {

    private static Logger log = SWBUtils.getLogger(SWBCloudControlCenter.class);
    public ArrayList<InstanceData> runningInstances;
    private Timer cloudTimer = null;
    private final static long _PERIOD = 5 * 60 * 1000l;
    private String placement = null;

    private Collection<String> seg = null;
    private String baseName = null;
    private String farmName = null;
    private String farmId = null;
    private String netName = null;
    private String ImageName = null;
    private double avgCPU = 0.0;
    private int maxInstances = 0;
    private String user = "";
    private String password = "";
    private String netId = "";
    private String imageId = "";
    private int count = 0;

    /**
     * update configuration and instance data
     */
    public final void reloadData() throws IOException {
        log.trace("Reloading data " + SWBDimensionDataUtils.checkIfCanLaunch());
        if (SWBDimensionDataUtils.checkIfCanLaunch()) {

            setNetName(SWBDimensionDataUtils.getValueOf("/NetworkName"));
//            seg = new ArrayList<String>();
//            seg.add(SWBDimensionDataUtils.getValueOf("/ImageName"));
//            seg.add(SWBDimensionDataUtils.getValueOf("/FarmName"));
            ImageName = SWBDimensionDataUtils.getValueOf("/ImageName");
            farmName = SWBDimensionDataUtils.getValueOf("/FarmName");
            baseName = SWBDimensionDataUtils.getValueOf("/BaseName");
            password = SWBDimensionDataUtils.getValueOf("/password");
            avgCPU = Double.parseDouble(SWBDimensionDataUtils.getValueOf("/MaxCPU"));
            runningInstances = new ArrayList<InstanceData>();

            if (ImageName != null && !("".equals(ImageName)) && farmName != null && !("".equals(farmName))
                    && netName != null && !(netName.equals(""))) {
                netId = Utils.getNetId(netName);
                setFarmId(Utils.getFarmId(netId, farmName));
                setImageId(Utils.getImageId(ImageName));
            }

//            lbName = SWBDimensionDataUtils.getValueOf("/LoadBal");
            try {
                maxInstances = Integer.parseInt(SWBDimensionDataUtils.getValueOf("/MaxNumberInstances"));
            } catch (NumberFormatException nfe) {
                log.debug("MaxNumberInstances not an Integer", nfe);
            }

            for (InstanceData cur : ((DimDataServicesImp) SWBPortal.getCloud()).getRunningInstances()) {
                //System.out.println("instance with name at runnind isntance: " + cur.getServerName());
                //System.out.println("get deployed: " + cur.getDeployed());
                if ("true".equals(cur.getDeployed())) {
                    //System.out.println("reload Data running name:  " + cur.getServerName());
                    runningInstances.add(cur);
                    //System.out.println("tamaño de runningInstance en cc:  " + runningInstances.size());
                }
            }
        }
    }

    /**
     * Add an InstanceData to the list
     *
     * @param data InstanceData to be added
     */
    public final void addInstanceData(InstanceData data) {
        runningInstances.add(data);
    }

    /**
     * remove an InstanceData from the list
     *
     * @param data InstanceData to be removed
     */
    public final void removeInstanceData(InstanceData data) {
        runningInstances.remove(data);
    }

    /**
     * get an Iterator of instanceData
     *
     * @return Iterator of instanceData
     */
    public final Iterator<InstanceData> listInstanceData() {
        return null != runningInstances ? runningInstances.iterator() : null;
    }

    /**
     * get Average CPU load
     *
     * @return average CPU load
     */
    public final double getAverageLoad() throws IOException {
        double load = 0.0d;
        int instCount = 0;
        for (InstanceData id : runningInstances) {
            load += ((DimDataServicesImp) SWBPortal.getCloud()).getCPUUSage(id);
            instCount++;
        }
        return load / instCount;
    }

    /**
     * Activate Monitoring system
     *
     * @param task DimDataMonitor
     */
    public final void activateMonitoring(final TimerTask task) {
        if (null == cloudTimer) {
            cloudTimer = new Timer("DimDataMonitor", true);
            cloudTimer.schedule(task, _PERIOD, _PERIOD);
        }
    }

    /**
     * get Configuration value Placement zone
     *
     * @return value Placement zone
     */
    public String getPlacement() {
        return placement;
    }

    public String getImageName() {
        return ImageName;
    }

//    /**
//     * get Configuration value Instance type
//     * @return value instance type
//     */
//    public String getServerName() {
//        return serverName;
//    }
    /**
     * get Configuration value Security Groups
     *
     * @return value Security Groups
     */
    public Collection<String> getSeg() {
        return seg;
    }

    /**
     * get Configuration value KeyPair
     *
     * @return value KeyPair
     */
    /**
     * get Configuration value Memory
     *
     * @return memory
     */
    /**
     * get Configuration value App Server
     *
     * @return value App Server
     */
    public String getBaseName() {
        return baseName;
    }

    public String getFarmName() {
        return farmName;
    }

    /**
     * get Configuration value Load Balancer
     *
     * @return value Load Balancer //
     */
//    public String getLbName() {
//        return lbName;
//    }
    /**
     * get Configuration value AvgCPU
     *
     * @return value AVGCPU
     */
    public double getAvgCPU() {
        return avgCPU;
    }

    /**
     * get Configuration value Max instances
     *
     * @return value Max instances
     */
    public int getMaxInstances() {
        return maxInstances;
    }

    /**
     * is the system launched
     *
     * @return true if system is launched
     */
    boolean isLaunched() {
        return "true".equals(SWBDimensionDataUtils.getValueOf("/launched"));
    }

    /**
     * count running instances
     *
     * @return count of running instances
     */
    int currentInstances() {
        return runningInstances.size();
    }

    /**
     * obtain the first instance data
     *
     * @return first instance data
     */
    InstanceData getFisrtInstance() {
        return runningInstances.get(0);
    }

    /**
     * @return the netId
     */
    public String getNetId() {
        return netId;
    }

    /**
     * @param netId the netId to set
     */
    public void setNetId(String netId) {
        this.netId = netId;
    }

    /**
     * @return the netName
     */
    public String getNetName() {
        return netName;
    }

    /**
     * @param netName the netName to set
     */
    public void setNetName(String netName) {
        this.netName = netName;
    }

    /**
     * @return the farmId
     */
    public String getFarmId() {
        return farmId;
    }

    /**
     * @param farmId the farmId to set
     */
    public void setFarmId(String farmId) {
        this.farmId = farmId;
    }

    /**
     * @return the imageId
     */
    public String getImageId() {
        return imageId;
    }

    /**
     * @param imageId the imageId to set
     */
    public void setImageId(String imageId) {
        this.imageId = imageId;
    }

    /**
     * @return the count
     */
    public String getCount() {
        count = count + 1;
        String con = "0000"+count;
//        if (count >= 0 && count <= 9) {
//            con = "00" + String.valueOf(count);
//        }
//        if (count <= 99 && count > 9) {
//            con = "0" + String.valueOf(count);
//        }
//        if (count <= 999 && count > 99) {
//            con = String.valueOf(count);
//        }
//        if (count > 999) {
//            count = 1;
//            con = "00" + String.valueOf(count);
//        }
        return con.substring(con.length()-3);
    }

    /**
     * @param count the count to set
     */
    public void setCount(int count) {
        this.count = count;
    }

}
