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
package org.semanticwb.aws;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.cloudwatch.AmazonCloudWatch;
import com.amazonaws.services.cloudwatch.AmazonCloudWatchClient;
import com.amazonaws.services.cloudwatch.model.Datapoint;
import com.amazonaws.services.cloudwatch.model.Dimension;
import com.amazonaws.services.cloudwatch.model.GetMetricStatisticsRequest;
import com.amazonaws.services.cloudwatch.model.Statistic;
import com.amazonaws.services.ec2.AmazonEC2;
import com.amazonaws.services.ec2.AmazonEC2Client;
import com.amazonaws.services.ec2.model.Address;
import com.amazonaws.services.ec2.model.AvailabilityZone;
import com.amazonaws.services.ec2.model.CreateTagsRequest;
import com.amazonaws.services.ec2.model.DescribeImagesRequest;
import com.amazonaws.services.ec2.model.Filter;
import com.amazonaws.services.ec2.model.Image;
import com.amazonaws.services.ec2.model.InstanceStateChange;
import com.amazonaws.services.ec2.model.InstanceType;
import com.amazonaws.services.ec2.model.KeyPairInfo;
import com.amazonaws.services.ec2.model.Placement;
import com.amazonaws.services.ec2.model.Reservation;
import com.amazonaws.services.ec2.model.RunInstancesRequest;
import com.amazonaws.services.ec2.model.RunInstancesResult;
import com.amazonaws.services.ec2.model.SecurityGroup;
import com.amazonaws.services.ec2.model.Tag;
import com.amazonaws.services.ec2.model.TerminateInstancesRequest;
import com.amazonaws.services.ec2.model.TerminateInstancesResult;
import com.amazonaws.services.elasticloadbalancing.AmazonElasticLoadBalancing;
import com.amazonaws.services.elasticloadbalancing.AmazonElasticLoadBalancingClient;
import com.amazonaws.services.elasticloadbalancing.model.CreateLoadBalancerRequest;
import com.amazonaws.services.elasticloadbalancing.model.CreateLoadBalancerResult;
import com.amazonaws.services.elasticloadbalancing.model.DeregisterInstancesFromLoadBalancerRequest;
import com.amazonaws.services.elasticloadbalancing.model.Listener;
import com.amazonaws.services.elasticloadbalancing.model.LoadBalancerDescription;
import com.amazonaws.services.elasticloadbalancing.model.RegisterInstancesWithLoadBalancerRequest;
import java.io.IOException;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;
import org.semanticwb.Logger;
import org.semanticwb.SWBPortal;
import org.semanticwb.SWBUtils;
import org.xbill.DNS.DClass;
import org.xbill.DNS.ExtendedResolver;
import org.xbill.DNS.Message;
import org.xbill.DNS.Name;
import org.xbill.DNS.Record;
import org.xbill.DNS.Resolver;
import org.xbill.DNS.ReverseMap;
import org.xbill.DNS.Section;
import org.xbill.DNS.Type;

/**
 * Library to access AWS REST Services
 *
 * @author serch
 */
public final class Utils {

    private static Logger log = SWBUtils.getLogger(Utils.class);
    private static AWSCredentials cred = null;
    private static AmazonEC2 ec2 = null;
    private static AmazonElasticLoadBalancing alb = null;
    private static AmazonCloudWatch acw = null;

    public static void initServices() {
        String access = SWBAWSDataUtils.getValueOf("/accessKey");
        String secret = SWBAWSDataUtils.getValueOf("/secretKey");
        if (null != access && null != secret) {
            cred = new BasicAWSCredentials(access, secret);
            ec2 = (AmazonEC2) new AmazonEC2Client(cred);
            alb = new AmazonElasticLoadBalancingClient(cred);
            acw = new AmazonCloudWatchClient(cred);
            if (null != SWBPortal.getEnv("swb/regionEndPoint", null)) {
                ec2.setEndpoint("ec2." + SWBPortal.getEnv("swb/regionEndPoint"));
                alb.setEndpoint("elasticloadbalancing." + SWBPortal.getEnv("swb/regionEndPoint"));
                acw.setEndpoint("monitoring." + SWBPortal.getEnv("swb/regionEndPoint"));
            }
        }
    }

    public static List<Image> getImages(final boolean x64Only) {
        ArrayList<Filter> lista = new ArrayList<Filter>();
        Filter filter = new Filter();
        filter.setName("image-type");
        filter.withValues("machine");
        lista.add(filter);
        if (x64Only) {
            filter = new Filter();
            filter.setName("architecture");
            filter.withValues("x86_64");
            lista.add(filter);
        }
        filter = new Filter();
        filter.setName("owner-alias");
        filter.withValues("amazon");
        lista.add(filter);
        filter = new Filter();
        filter.setName("is-public");
        filter.withValues("true");
        lista.add(filter);
        filter = new Filter();
        filter.setName("name");
        filter.withValues("amzn-ami-pv*");
        lista.add(filter);
        filter = new Filter();
        filter.setName("description");
        filter.withValues("Amazon Linux AMI*EBS");
        lista.add(filter);
//        filter = new Filter();
//        filter.setName("image-id");
//        filter.withValues("ami-1624987f");     
//        lista.add(filter);
        //ami-1624987f 
        DescribeImagesRequest describeImagesRequest = new DescribeImagesRequest();
        describeImagesRequest.setFilters(lista);
        return ec2.describeImages(describeImagesRequest).getImages();
    }

    public static List<SecurityGroup> getSecurityGroups() {
        return ec2.describeSecurityGroups().getSecurityGroups();
    }

    public static List<KeyPairInfo> getKeys() {
        return ec2.describeKeyPairs().getKeyPairs();
    }

    public static InstanceType[] getInstanceTypes() {
        return InstanceType.class.getEnumConstants();
    }

    public static List<AvailabilityZone> getAvailabilityZones() {
        return ec2.describeAvailabilityZones().getAvailabilityZones();
    }

    public static Reservation runInstance(final String imageId, final InstanceType instanceType,
            final Collection<String> secGroup, final String key, final String userData,
            final Placement placement) {
        RunInstancesRequest rir = new RunInstancesRequest(imageId, 1, 1);
        rir.setInstanceType(instanceType);
        rir.setSecurityGroups(secGroup);
        rir.setKeyName(key);
        rir.setUserData(userData);
        rir.setPlacement(placement);
        log.trace("ready to launch ");

        RunInstancesResult runInstancesResult = ec2.runInstances(rir);
        log.trace("launched " + runInstancesResult);
        try {
            log.trace("Sleeping...");
            Thread.sleep(30000);
            log.trace("Waking up...");
        } catch (Exception e) {
            log.error(e);
        }
        ArrayList<String> id = new ArrayList<String>();
        for (com.amazonaws.services.ec2.model.Instance res : runInstancesResult.getReservation().getInstances()) {
            id.add(res.getInstanceId());
        }
        CreateTagsRequest ctr = new CreateTagsRequest();
        ctr.withResources(id);
        ctr.withTags(new Tag("type", "swbClient"), new Tag("product", "SWBCloud4AWS"));
        ec2.createTags(ctr);
        log.event("Instance Launched: " + runInstancesResult);
        return runInstancesResult.getReservation();
    }

    public static String getScriptEncoded(final String mem, final String tomcat, final String elastic) {
        String reverse = reverseDns(elastic);
        log.trace("Public DNS of ElasticIP: "+reverse);
        String cad = "#!/bin/bash\n"
                + "yum update -y\n"
                + "yum erase java-1.6.0-openjdk -y\n"
                + "yum install nfs-utils rpcbind -y\n"
                + "yum install java-1.7.0-openjdk-devel -y\n"
                + "service rpcbind start\n"
                + "service nfslock start\n"
                + "chkconfig --level 2345 rpcbind on\n"
                + "chkconfig --level 2345 nfslock on\n"
                + "mkdir /swb\n"
                + "mkdir /swb/portal\n"
                + "mkdir /swb/software\n"
                + "mount -t nfs " + reverse + ":/swb/software /swb/software \n"
                + //        "cd /home/ec2-user\n"+
                //        "wget http://"+adminIP+"/software/"+tomcat+".tar.gz\n"+
                //        "wget http://"+adminIP+"/software/swb.war\n"+
                "chown ec2-user /swb/portal\n"
                + "cd /swb/portal\n"
                + "tar -zxvf /swb/software/" + tomcat + ".tar.gz\n"
                + //        "sed  -i 's/port\\=\\\"8080\\\"/port\\=\\\"80\\\"/' "+tomcat+"/conf/server.xml\n"+
                //        "sed  -i 's/port\\=\\\"8443\\\"/port\\=\\\"443\\\"/' "+tomcat+"/conf/server.xml\n"+
                "sed  -i 's|#!/bin/sh|#!/bin/sh\\n\\nexport CATALINA_OPTS=\"-Dfile.encoding=ISO8859-1 -Xss192k -Xmx" + mem + " -server\"\\n|' " + tomcat + "/bin/catalina.sh\n"
                + "cd " + tomcat + "/webapps\n"
                + "rm -Rf *\n"
                + "mkdir ROOT\n"
                + "cd ROOT\n"
                + "/usr/lib/jvm/java/bin/jar xvf /swb/software/swb.war\n"
                + "cd work\n"
                + "rm -Rf models\n"
                + "mkdir models\n"
                + "mount -t nfs " + reverse + ":/swb/portal/" + tomcat + "/webapps/ROOT/work/models /swb/portal/" + tomcat + "/webapps/ROOT/work/models \n"
                + "cd ..\n"
                + "jar xvf /swb/software/config.jar\n"
                + "cd /swb/portal/" + tomcat + "/bin\n"
                + "./startup.sh\n";
        cad = com.amazonaws.util.BinaryUtils.toBase64(cad.getBytes());
        return cad;
    }

    public static List<InstanceStateChange> terminateInstance(final String instanceId) {
        log.trace("Terminating "+instanceId);
        TerminateInstancesRequest tir = new TerminateInstancesRequest();
        tir.withInstanceIds(instanceId);
        TerminateInstancesResult tres = ec2.terminateInstances(tir);
        return tres.getTerminatingInstances();
    }

    public static String createLoadBalancer(final String name, final String protocol, final int balancedPort, final int instencesPort, final String zone) {
        CreateLoadBalancerRequest clbr = new CreateLoadBalancerRequest(name);
        Listener listener = new Listener(protocol, balancedPort, instencesPort);
        clbr.withListeners(listener);
        clbr.withAvailabilityZones(zone);
        CreateLoadBalancerResult lbRes = alb.createLoadBalancer(clbr);
        return lbRes.getDNSName();
    }

    public static void addInstanceToLoadBalancer(final String name, final String instanceName) {
        RegisterInstancesWithLoadBalancerRequest riwlbr = new RegisterInstancesWithLoadBalancerRequest();
        riwlbr.withLoadBalancerName(name);
        com.amazonaws.services.elasticloadbalancing.model.Instance instance = new com.amazonaws.services.elasticloadbalancing.model.Instance(instanceName);
        riwlbr.withInstances(instance);
        log.trace("Adding "+instanceName+" to "+name);
        alb.registerInstancesWithLoadBalancer(riwlbr);
    }

    public static void removeInstanceFromLoadBalancer(final String name, final String instanceName) {
        DeregisterInstancesFromLoadBalancerRequest riwlbr = new DeregisterInstancesFromLoadBalancerRequest();
        riwlbr.withLoadBalancerName(name);
        com.amazonaws.services.elasticloadbalancing.model.Instance instance = new com.amazonaws.services.elasticloadbalancing.model.Instance(instanceName);
        riwlbr.withInstances(instance);
        log.trace("Removing "+instanceName+" to "+name);
        alb.deregisterInstancesFromLoadBalancer(riwlbr);
    }

    public static List<LoadBalancerDescription> getLoadBalancerDescriptions() {
        return alb.describeLoadBalancers().getLoadBalancerDescriptions();
    }

    public static List<Reservation> getRunningInstances() {
        return ec2.describeInstances().getReservations();
    }

    public static Reservation createInstance(final String placement, final String amiID,
            final String instanceType, final Collection<String> securityGroups,
            final String keyPair, final String memory, final String appServ, final String elasticAdminDNS) {
        log.trace("creating instance");
        Placement place = new Placement(placement);
        return runInstance(amiID, InstanceType.fromValue(instanceType),
                securityGroups, keyPair, getScriptEncoded(memory, appServ, elasticAdminDNS), place);
    }

    public static List<Address> getElastic() {
        return ec2.describeAddresses().getAddresses();
    }

    public static double getCPUAverage(final String instanceID) {
        log.trace("Getting CPU Average of "+instanceID);
        double acc = 0.0;
        int points = 0;
        GetMetricStatisticsRequest gmsr = new GetMetricStatisticsRequest().withDimensions(
                new Dimension().withName("InstanceId").withValue(instanceID)).withMetricName("CPUUtilization");
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MINUTE, -15);
        gmsr.withStartTime(cal.getTime()).withEndTime(Calendar.getInstance().getTime());
        gmsr.withPeriod(60).withStatistics(Statistic.Average.name()).withNamespace("AWS/EC2");
        for (Datapoint dp : acw.getMetricStatistics(gmsr).getDatapoints()) {
            log.trace("Point: "+dp.getTimestamp()+ " - "+dp.getAverage());
            acc += dp.getAverage();
            points++;
        }
        return acc / points;
    }

    public static String reverseDns(String elasticIP) {
        try {
            Resolver res = new ExtendedResolver();

            Name name = ReverseMap.fromAddress(elasticIP);
            Record record = Record.newRecord(name, Type.PTR, DClass.IN);
            Message query = Message.newQuery(record);
            Message response = res.send(query);

            Record[] answers = response.getSectionArray(Section.ANSWER);
            if (answers.length == 0) {
                log.event("can't resolve " + elasticIP);
                return elasticIP;
            } else {
                return answers[0].rdataToString();
            }
        } catch (IOException ioe) {
            log.event("can't Resolve " + elasticIP, ioe);
            return elasticIP;
        }
    }

}
