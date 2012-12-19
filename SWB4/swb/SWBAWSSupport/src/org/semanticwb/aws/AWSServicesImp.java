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

import com.amazonaws.services.ec2.model.Address;
import com.amazonaws.services.ec2.model.AvailabilityZone;
import com.amazonaws.services.ec2.model.Image;
import com.amazonaws.services.ec2.model.Instance;
import com.amazonaws.services.ec2.model.InstanceType;
import com.amazonaws.services.ec2.model.KeyPairInfo;
import com.amazonaws.services.ec2.model.Reservation;
import com.amazonaws.services.ec2.model.SecurityGroup;
import com.amazonaws.services.ec2.model.Tag;
import com.amazonaws.services.elasticloadbalancing.model.LoadBalancerDescription;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.TimerTask;
import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;

/**
 *
 * @author serch
 */
public final class AWSServicesImp implements AWSServices {

    private static Logger log = SWBUtils.getLogger(AWSServicesImp.class);
    private static SWBCloudControlCenter controlCenter;

    public AWSServicesImp() {
        Utils.initServices();
        controlCenter = new SWBCloudControlCenter();
    }

    @Override
    public void reloadCredentials() {
        Utils.initServices();
    }

    @Override
    public List<String> getKeyNames() {
        List<String> ret = new ArrayList<String>();
        for (KeyPairInfo info : Utils.getKeys()) {
            ret.add(info.getKeyName());
        }
        return ret;
    }

    @Override
    public List<String> getImagesNames() {
        List<String> ret = new ArrayList<String>();
        for (Image image : Utils.getImages(true)) {
            ret.add(image.getImageId() + "|" + image.getName());
        }
        return ret;
    }

    @Override
    public List<String> getInstanceTypes() {
        List<String> ret = new ArrayList<String>();
        for (InstanceType instanceType : Utils.getInstanceTypes()) {
            ret.add("" + instanceType);
        }
        return ret;
    }

    @Override
    public List<String> getSecurityGroups() {
        List<String> ret = new ArrayList<String>();
        for (SecurityGroup securityGroup : Utils.getSecurityGroups()) {
            ret.add(securityGroup.getGroupName());
        }
        return ret;
    }

    @Override
    public List<String> getLoadBalancers() {
        List<String> ret = new ArrayList<String>();
        for (LoadBalancerDescription loadBalancerDescription : Utils.getLoadBalancerDescriptions()) {
            ret.add(loadBalancerDescription.getLoadBalancerName());
        }
        return ret;
    }

    @Override
    public List<InstanceData> getRunningInstances() {
        List<InstanceData> ret = new ArrayList<InstanceData>();
        for (Reservation reservation : Utils.getRunningInstances()) {
            log.trace("Reservation: "+reservation);
            for (Instance instance : reservation.getInstances()) {
                log.trace("Instance: "+instance.getInstanceId()+"|"+instance.getState()+"|"+instance.getTags());
                if ("running".equals(instance.getState().getName())) {
                    InstanceData id = new InstanceData();
                    id.setId(instance.getInstanceId());
                    List<Tag> ltag = instance.getTags();
                    for (Tag tag : ltag) {
                        if ("type".equals(tag.getKey())) {
                            id.setType(tag.getValue());
                        }
                    }
                    id.setIp(instance.getPrivateIpAddress());
                    id.setStatus(instance.getState().getName());
                    id.setStartupTime(instance.getLaunchTime().getTime());
                    ret.add(id);
                }
            }
        }
        return ret;
    }

    @Override
    public List<String> getAvailabilityZones() {
        List<String> ret = new ArrayList<String>();
        for (AvailabilityZone zone : Utils.getAvailabilityZones()) {
            ret.add(zone.getZoneName());
        }
        return ret;
    }

    @Override
    public List<String> getElasticIPs() {
        List<String> ret = new ArrayList<String>();
        for (Address address : Utils.getElastic()) {
            ret.add(address.getPublicIp());
        }
        return ret;
    }

    @Override
    public String createInstance(final String placement, final String amiID,
            final String instanceType, final Collection<String> securityGroups,
            final String keyPair, final String memory, final String appServ,
            final String elasticAdminDNS, final String loadBalancer) {
        String ret = null;
        Reservation res = Utils.createInstance(placement, amiID, instanceType, securityGroups, keyPair, memory, appServ, elasticAdminDNS);
        for (Instance instance : res.getInstances()) {
            ret = instance.getInstanceId();
            Utils.addInstanceToLoadBalancer(loadBalancer, ret);
        }
        return ret;
    }

    @Override
    public void removeInstance(final String loadBalancer, final String instanceId) {
        Utils.removeInstanceFromLoadBalancer(loadBalancer, instanceId);
        Utils.terminateInstance(instanceId);
        
    }

    private int countRunningClientInstances() {
        int count = 0;
        for (InstanceData id : getRunningInstances()) {
            if ("swbClient".equals(id.getType()) && "running".equals(id.getStatus())) {
                count++;
            }
        }
        return count;
    }

    @Override
    public void launch() {
        if (controlCenter.isLaunched()) {
            controlCenter.reloadData();
            log.debug("Launching Instance "+countRunningClientInstances() + " - "+SWBAWSDataUtils.getValueOf("/launched"));
            if ("true".equals(SWBAWSDataUtils.getValueOf("/launched"))
                    && countRunningClientInstances() == 0) {
                log.debug("Creating...");
                String id = createInstance(controlCenter.getPlacement(), controlCenter.getAmiID(),
                        controlCenter.getInstanceType(), controlCenter.getSeg(), controlCenter.getKeyPair(),
                        controlCenter.getMemory(), controlCenter.getAppServ(), controlCenter.getElasticDNS(),
                        controlCenter.getLbName());
                log.event("Created "+id);
            }
            TimerTask tt = new MonitorCloudTask();
            controlCenter.activateMonitoring(tt);
            controlCenter.reloadData();
        }
    }

    @Override
    public SWBCloudControlCenter getControlCenter() {
        return controlCenter;
    }

    @Override
    public double getCPUUSage(final String instanceID) {
        return Utils.getCPUAverage(instanceID);
    }
}
