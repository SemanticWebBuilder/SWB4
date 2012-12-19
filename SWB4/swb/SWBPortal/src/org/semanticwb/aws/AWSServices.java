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

import java.util.Collection;
import java.util.List;

/**
 * Interface to AWS SErvices
 * @author serch
 */
public interface AWSServices {
    /**
     * Ask internals to reload AWS Credentials
     */
    void reloadCredentials();
    
    /**
     * Get a List of KeyPair Names 
     * @return List of KeyPair Names
     */
    List<String> getKeyNames();
    
    /**
     * Get a list of AMI names
     * @return list of AMI names
     */
    List<String> getImagesNames();
    
    /**
     * Get a List of Instances Type names
     * @return List of Instances Type names
     */
    List<String> getInstanceTypes();
    
    /**
     * get a List of Security Group names
     * @return List of Security Group names
     */
    List<String> getSecurityGroups();
    
    /**
     * Get a list of Load Balancer names
     * @return list of Load Balancer names
     */
    List<String> getLoadBalancers();
    
    /**
     * get a list of InstanceData representing the Running instances
     * @return list of InstanceData representing the Running instances
     */
    List<InstanceData> getRunningInstances();
    
    /**
     * get a list of availability zone names
     * @return list of availability zone names
     */
    List<String> getAvailabilityZones();
    
    /**
     * Create an instance
     * @param placement name of the availability zone
     * @param amiID name of the AMI
     * @param instanceType name of the Instance Type
     * @param securityGroups Collection of security group names
     * @param keyPair name of the KeyPair to associate to this instance
     * @param memory value to add to the -Xmx Java parameter
     * @param appServ name of the Tomcat to use
     * @param elasticAdminDNS Elastic IP
     * @param loadBalancer name of the Load Balancer to associate this instance with
     * @return id of the instance created
     */
    String createInstance(String placement, String amiID, 
            String instanceType, Collection<String> securityGroups, 
            String keyPair, String memory, String appServ, 
            String elasticAdminDNS, String loadBalancer);
    
    /**
     * Terminate an instance
     * @param loadBalancer name of the load Balancer associated to this instances
     * @param instanceId name of the instance id
     */
    void removeInstance(String loadBalancer, String instanceId);
    
    /**
     * get a list of elastic IPs
     * @return list of elastic IPs
     */
    List<String> getElasticIPs();
    
    /**
     * Launch the AWS system
     */
    void launch();
    
    /**
     * get the Control Center
     * @return The control Center
     */
    SWBCloudControlCenter getControlCenter();
    
    /**
     * get Average CPU Usage of an Instance
     * @param instanceID instance Id to query
     * @return average CPU usage
     */
    double getCPUUSage(String instanceID);
}
