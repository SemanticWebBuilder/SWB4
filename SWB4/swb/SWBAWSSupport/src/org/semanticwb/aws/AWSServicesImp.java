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
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.TimerTask;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
import static org.semanticwb.aws.SWBAWSDataUtils.*;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceException;
import org.semanticwb.portal.services.SWBCloud;

/**
 *
 * @author serch
 */
public final class AWSServicesImp implements SWBCloud {

    private static Logger log = SWBUtils.getLogger(AWSServicesImp.class);
    private static SWBCloudControlCenter controlCenter;

    public AWSServicesImp() {
        Utils.initServices();
        controlCenter = new SWBCloudControlCenter();
    }

    public void reloadCredentials() {
        Utils.initServices();
    }

    public List<String> getKeyNames() {
        List<String> ret = new ArrayList<String>();
        for (KeyPairInfo info : Utils.getKeys()) {
            ret.add(info.getKeyName());
        }
        return ret;
    }

    public List<String> getImagesNames() {
        List<String> ret = new ArrayList<String>();
        for (Image image : Utils.getImages(true)) {
            ret.add(image.getImageId() + "|" + image.getName());
        }
        return ret;
    }

    public List<String> getInstanceTypes() {
        List<String> ret = new ArrayList<String>();
        for (InstanceType instanceType : Utils.getInstanceTypes()) {
            ret.add("" + instanceType);
        }
        return ret;
    }

    public List<String> getSecurityGroups() {
        List<String> ret = new ArrayList<String>();
        for (SecurityGroup securityGroup : Utils.getSecurityGroups()) {
            ret.add(securityGroup.getGroupName());
        }
        return ret;
    }

    public List<String> getLoadBalancers() {
        List<String> ret = new ArrayList<String>();
        for (LoadBalancerDescription loadBalancerDescription : Utils.getLoadBalancerDescriptions()) {
            ret.add(loadBalancerDescription.getLoadBalancerName());
        }
        return ret;
    }

    public List<InstanceData> getRunningInstances() {
        List<InstanceData> ret = new ArrayList<InstanceData>();
        for (Reservation reservation : Utils.getRunningInstances()) {
            log.trace("Reservation: " + reservation);
            for (Instance instance : reservation.getInstances()) {
                log.trace("Instance: " + instance.getInstanceId() + "|" + instance.getState() + "|" + instance.getTags());
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

    public List<String> getAvailabilityZones() {
        List<String> ret = new ArrayList<String>();
        for (AvailabilityZone zone : Utils.getAvailabilityZones()) {
            ret.add(zone.getZoneName());
        }
        return ret;
    }

    public List<String> getElasticIPs() {
        List<String> ret = new ArrayList<String>();
        for (Address address : Utils.getElastic()) {
            ret.add(address.getPublicIp());
        }
        return ret;
    }

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
            log.debug("Launching Instance " + countRunningClientInstances() + " - " + SWBAWSDataUtils.getValueOf("/launched"));
            if ("true".equals(SWBAWSDataUtils.getValueOf("/launched"))
                    && countRunningClientInstances() == 0) {
                log.debug("Creating...");
                String id = createInstance(controlCenter.getPlacement(), controlCenter.getAmiID(),
                        controlCenter.getInstanceType(), controlCenter.getSeg(), controlCenter.getKeyPair(),
                        controlCenter.getMemory(), controlCenter.getAppServ(), controlCenter.getElasticDNS(),
                        controlCenter.getLbName());
                log.event("Created " + id);
            }
            TimerTask tt = new MonitorCloudTask();
            controlCenter.activateMonitoring(tt);
            controlCenter.reloadData();
        }
    }

    public SWBCloudControlCenter getControlCenter() {
        return controlCenter;
    }

    public double getCPUUSage(final String instanceID) {
        return Utils.getCPUAverage(instanceID);
    }

    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, 
    SWBParamRequest paramRequest, PrintWriter out) throws SWBResourceException, IOException {
String val = getValueOf("/launched"); 
        boolean launched = (null!=val&&"true".equals(val))?true:false;
        if ("launch".equals(paramRequest.getAction())){
            if (null==val || "false".equals(val)){
                setValueOf("/launched", "true");
                launched=true;
                launch();
            } else {
                setValueOf("/launched", "false");
                launched=false;
            }
        }
        if ("shutdown".equals(paramRequest.getAction())){
            setValueOf("/launched", "false");
            launched=false;
            Iterator<InstanceData> lista = getControlCenter().listInstanceData();    
            while(lista.hasNext()){
                InstanceData inDat = lista.next();
                removeInstance(getControlCenter().getLbName(),inDat.getId());
                getControlCenter().removeInstanceData(inDat);
            }
        }
        if ("accUpdate".equals(paramRequest.getAction())) {
            String error = processCredentials(request);
            if (null != error) {
                out.print("<script>alert('" + error + "');</script>");
            }
        }
        if ("confUpdate".equals(paramRequest.getAction())){
            String error = processConfig(request);
            if (null != error) {
                out.print("<script>alert('" + error + "');</script>");
            }
        }

        
        
        getControlCenter().reloadData();
        Iterator<InstanceData> iid = getControlCenter().listInstanceData();
        if (null!=iid && iid.hasNext()){
            out.print(getFormShutdown(paramRequest));
        }
        out.print(getFormCredentials(paramRequest, launched));
        //out.print("<br/><br/>");
        if (checkIfParameterOk("/accessKey") && checkIfParameterOk("/secretKey")) {
            List<String> secGrp = getSecurityGroups();
            out.print(getFormConfig(paramRequest, secGrp, launched));
            if (checkIfCanLaunch()) out.print(getFormLaunch(paramRequest, launched));
            //SWBPortal.getAWSCloud().getRunningInstances();
        }

    }
    
    
    private String getFormCredentials(final SWBParamRequest paramRequest, final boolean launched) {
        String access = getValueOf("/accessKey");
        String secret = getValueOf("/secretKey");
        if (access == null) {
            access = "";
        }
        if (secret == null) {
            secret = "";
        }

        String forma = null;
        if (!launched) {
        forma = "<form id=\"credentialsAWS\" dojoType=\"dijit.form.Form\" class=\"swbform\" action=\""
                + paramRequest.getRenderUrl().setAction("accUpdate")
                + "\" onsubmit=\"submitForm('credentialsAWS');return false;\" method=\"post\">\n"
                + "<fieldset>\n"
                + "<legend>Datos de Accesso</legend>"
                + "	    <table>\n"
                + "                <tr><td width=\"200px\" align=\"right\"><label for=\"accessKey\">AccessKey &nbsp;</label></td><td><input _id=\"accessKey\" name=\"accessKey\" value=\"" + access + "\" dojoType=\"dijit.form.ValidationTextBox\" required=\"false\" promptMessage=\"Captura el AccessKey\" style=\"width:300px;\" /></td></tr>\n"
                + "                <tr><td width=\"200px\" align=\"right\"><label for=\"secretKey\">SecretKey &nbsp;</label></td><td><input _id=\"secretKey\" name=\"secretKey\" value=\"" + secret + "\" dojoType=\"dijit.form.ValidationTextBox\" required=\"false\" promptMessage=\"Captura el SecretKey\" style=\"width:300px;\" /></td></tr>\n"
                + "	    </table>\n"
                + "	</fieldset>"
                + "<fieldset><span align=\"center\">\n"
                + "    <button dojoType=\"dijit.form.Button\" type=\"submit\">Enviar accesso</button>\n"
                + "</span></fieldset>\n"
                + "</form>";
        } else {
            forma = "";
        }
        return forma;
    }

    private String processCredentials(final HttpServletRequest request) {
        String error = null;
        String acc = request.getParameter("accessKey").trim();
        String sec = request.getParameter("secretKey").trim();
        if (null == acc || "".equals(acc) || null == sec || "".equals(sec)) {
            error = "No se proporcionaron los datos de acceso a AWS";
        } else {
            setValueOf("/accessKey", acc);
            setValueOf("/secretKey", sec);
            reloadCredentials();
        }

        return error;
    }

    private String processConfig(final HttpServletRequest request) {
        String error = null;
        String keyPair = request.getParameter("KeyPair").trim();
        
        if (null!=keyPair && (!"".equals(keyPair))) {
            setValueOf("/KeyPair", keyPair);
        } else {
            removeValue("/KeyPair");
        }
        String imageId = request.getParameter("ImageId").trim();
        if (null!=imageId && (!"".equals(imageId))){
            setValueOf("/ImageId", imageId);
        } else {
            removeValue("/ImageId");
        }
        String instanceType = request.getParameter("InstanceType").trim();
        if (null!=instanceType && (!"".equals(instanceType))){
            setValueOf("/InstanceType", instanceType);
        } else {
            removeValue("/InstanceType");
        }
        String maxNumberInstances = request.getParameter("MaxNumberInstances").trim();
        if (null!=maxNumberInstances && (!"".equals(maxNumberInstances))){
            setValueOf("/MaxNumberInstances", maxNumberInstances);
        } else {
            removeValue("/MaxNumberInstances");
        }
        String avZone = request.getParameter("AvZone").trim();
        if (null!=avZone && (!"".equals(avZone))){
            setValueOf("/AvZone", avZone);
        } else {
            removeValue("/AvZone");
        }
        String memory = request.getParameter("Memory").trim();
        if (null!=memory && (!"".equals(memory))){
            setValueOf("/Memory", memory);
        } else {
            removeValue("/Memory");
        }
        String appServer = request.getParameter("AppServer").trim();
        if (null!=appServer && (!"".equals(appServer))){
            setValueOf("/AppServer", appServer);
        } else {
            removeValue("/AppServer");
        }
        String secGrpInt = request.getParameter("SecGrpInt").trim();
        if (null!=secGrpInt && (!"".equals(secGrpInt))){
            setValueOf("/SecGrpInt", secGrpInt);
        } else {
            removeValue("/SecGrpInt");
        }
        String secGrpExt = request.getParameter("SecGrpExt").trim();
        if (null!=secGrpExt && (!"".equals(secGrpExt))){
            setValueOf("/SecGrpExt", secGrpExt);
        } else {
            removeValue("/SecGrpExt");
        }
        String elastic = request.getParameter("Elastic").trim();
        if (null!=elastic && (!"".equals(elastic))){
             setValueOf("/Elastic", elastic);
        } else {
            removeValue("/Elastic");
        }
        String loadBal = request.getParameter("LoadBal").trim();
        if (null!=loadBal && (!"".equals(loadBal))){
            setValueOf("/LoadBal", loadBal);
        } else {
            removeValue("/LoadBal");
        }
        String maxCPU = request.getParameter("MaxCPU").trim();
        if (null!=maxCPU && (!"".equals(maxCPU))){
            setValueOf("/MaxCPU", maxCPU);
        } else {
            removeValue("/MaxCPU");
        }
        return error;
    }

    private String getFormConfig(SWBParamRequest paramRequest, final List<String> secGrp, final boolean launched) {
        String maxNum = getValueOf("/MaxNumberInstances");
        String memory = getValueOf("/Memory");
        String appserv = getValueOf("/AppServer");
        String maxCPU = getValueOf("/MaxCPU");
        if (null==maxNum) maxNum="";
        if (null==memory) memory="";
        if (null==appserv) appserv="";
        if (null==maxCPU) maxCPU="";
        String forma =null; 
        if (!launched) {
        forma = "<form id=\"configAWS\" dojoType=\"dijit.form.Form\" class=\"swbform\" action=\""
                + paramRequest.getRenderUrl().setAction("confUpdate")
                + "\" onsubmit=\"submitForm('configAWS');return false;\" method=\"post\">\n"
                + "<fieldset>\n"
                + "<legend>Configuración</legend>"
                + "	    <table>\n"
                + "                <tr><td width=\"200px\" align=\"right\"><label for=\"AvZone\">Placement Zone &nbsp;</label></td><td><select name=\"AvZone\" >" + getAvailableZones() + "</select></td></tr>\n"
                + "                <tr><td width=\"200px\" align=\"right\"><label for=\"KeyPair\">KeyPair &nbsp;</label></td><td><select name=\"KeyPair\" >" + getKeyPairList() + "</select></td></tr>\n"
                + "                <tr><td width=\"200px\" align=\"right\"><label for=\"ImageId\">Image &nbsp;</label></td><td><select name=\"ImageId\" >" + getImagesID() + "</td></tr>\n"
                + "                <tr><td width=\"200px\" align=\"right\"><label for=\"InstanceType\">InstanceType &nbsp;</label></td><td><select name=\"InstanceType\" >" + getInstanceTypes() + "</td></tr>\n"
                + "                <tr><td width=\"200px\" align=\"right\"><label for=\"MaxNumberInstances\">Max Instance to launch &nbsp;</label></td><td><input _id=\"MaxNumberInstances\" name=\"MaxNumberInstances\" "
                + "value=\""+maxNum+"\" dojoType=\"dijit.form.ValidationTextBox\" required=\"true\" promptMessage=\"Set the max number of EC2 Instances to Launch\" invalidMessage=\"Not a number\" style=\"width:40px;\"  "
                + "trim=\"true\"/></td></tr>\n"
                + "                <tr><td width=\"200px\" align=\"right\"><label for=\"Memory\">Memory String &nbsp;</label></td><td><input _id=\"Memory\" name=\"Memory\" "
                + "value=\""+memory+"\" dojoType=\"dijit.form.ValidationTextBox\" required=\"true\" promptMessage=\"Set the memory to configura\" invalidMessage=\"Not a number\" style=\"width:300px;\"  "
                + "trim=\"true\"/></td></tr>\n"
                + "                <tr><td width=\"200px\" align=\"right\"><label for=\"AppServer\">AppServer String &nbsp;</label></td><td><input _id=\"AppServer\" name=\"AppServer\" "
                + "value=\""+appserv+"\" dojoType=\"dijit.form.ValidationTextBox\" required=\"true\" promptMessage=\"Set the base name of the appserver tar.gz file\" style=\"width:300px;\"  "
                + "trim=\"true\"/></td></tr>\n"
                + "                <tr><td width=\"200px\" align=\"right\"><label for=\"SecGrpInt\">Internal Security Group &nbsp;</label></td><td><select name=\"SecGrpInt\" >" + getSecGroupFor(secGrp, "/SecGrpInt") + "</td></tr>\n"
                + "                <tr><td width=\"200px\" align=\"right\"><label for=\"SecGrpExt\">External Security Group &nbsp;</label></td><td><select name=\"SecGrpExt\" >" + getSecGroupFor(secGrp, "/SecGrpExt") + "</td></tr>\n"
                + "                <tr><td width=\"200px\" align=\"right\"><label for=\"Elastic\">Elastic Admin IP &nbsp;</label></td><td><select name=\"Elastic\" >" + getElastic() + "</td></tr>\n"
                + "                <tr><td width=\"200px\" align=\"right\"><label for=\"LoadBal\">LoadBalancer &nbsp;</label></td><td><select name=\"LoadBal\" >" + getLoadBalancers() + "</td></tr>\n"
                + "                <tr><td width=\"200px\" align=\"right\"><label for=\"MaxCPU\">CPU Average Level to launch &nbsp;</label></td><td><input _id=\"MaxCPU\" name=\"MaxCPU\" "
                + "value=\""+maxCPU+"\" dojoType=\"dijit.form.ValidationTextBox\" required=\"true\" promptMessage=\"Set the CPU average usage level to Launch other instances\" invalidMessage=\"Not a number\" style=\"width:40px;\"  "
                + "trim=\"true\"/></td></tr>\n"
                + "	    </table>\n"
                + "	</fieldset>"
                + "<fieldset><span align=\"center\">\n"
                + "    <button dojoType=\"dijit.form.Button\" type=\"submit\">Enviar configuración</button>\n"
                + "</span></fieldset>\n"
                + "</form>";
        }
        else {
            forma =  "<fieldset class=\"swbform\">\n"
                + "<legend>Configuración</legend>"
                + "	    <table>\n"
                + "                <tr><td width=\"200px\" align=\"right\"><label for=\"AvZone\">Placement Zone &nbsp;</label></td><td>"+getValueOf("/AvZone") +"</td></tr>\n"
                + "                <tr><td width=\"200px\" align=\"right\"><label for=\"KeyPair\">KeyPair &nbsp;</label></td><td>"+getValueOf("/KeyPair") +"</td></tr>\n"
                + "                <tr><td width=\"200px\" align=\"right\"><label for=\"ImageId\">Image &nbsp;</label></td><td>"+getValueOf("/ImageId") +"</td></tr>\n"
                + "                <tr><td width=\"200px\" align=\"right\"><label for=\"InstanceType\">InstanceType &nbsp;</label></td><td>"+getValueOf("/InstanceType") +"</td></tr>\n"
                + "                <tr><td width=\"200px\" align=\"right\"><label for=\"MaxNumberInstances\">Max Instance to launch &nbsp;</label></td><td>"+maxNum +"</td></tr>\n"
                + "                <tr><td width=\"200px\" align=\"right\"><label for=\"Memory\">Memory String &nbsp;</label></td><td>"+memory+"</td></tr>\n"
                + "                <tr><td width=\"200px\" align=\"right\"><label for=\"AppServer\">AppServer String &nbsp;</label></td><td>"+appserv+"</td></tr>\n"
                + "                <tr><td width=\"200px\" align=\"right\"><label for=\"SecGrpInt\">Internal Security Group &nbsp;</label></td><td>"+getValueOf("/SecGrpInt") +"</td></tr>\n"
                + "                <tr><td width=\"200px\" align=\"right\"><label for=\"SecGrpExt\">External Security Group &nbsp;</label></td><td>"+getValueOf("/SecGrpExt") +"</td></tr>\n"
                + "                <tr><td width=\"200px\" align=\"right\"><label for=\"Elastic\">Elastic Admin IP &nbsp;</label></td><td>"+getValueOf("/Elastic") +"</td></tr>\n"
                + "                <tr><td width=\"200px\" align=\"right\"><label for=\"LoadBal\">LoadBalancer &nbsp;</label></td><td>"+getValueOf("/LoadBal") +"</td></tr>\n"
                + "                <tr><td width=\"200px\" align=\"right\"><label for=\"MaxCPU\">CPU Average Level to launch &nbsp;</label></td><td>"+maxCPU +"</td></tr>\n"
                + "	    </table>\n"
                + "	</fieldset>";
        }

        return forma;
    }

    private String getKeyPairList() {
        String value = getValueOf("/KeyPair");
        if (null==value) value="";
        String checked = "";
        String ret = "<option></option>";
        for (String name : getKeyNames()) {
            if (name.equalsIgnoreCase(value)) checked=" selected=\"true\""; else checked="";
            ret += "<option"+checked+">" + name + "</option>";
        }
        return ret;
    }

    private String getImagesID() {
        String value = getValueOf("/ImageId");
        if (null==value) value="";
        String checked = "";
        String ret = "<option></option>";
        for (String name : getImagesNames()) {
            int i = name.indexOf("|");
            if (name.substring(0, i).equalsIgnoreCase(value)) checked=" selected=\"true\""; else checked="";
            ret += "<option value=\"" + name.substring(0, i) + "\""+checked+">" + name.substring(i + 1) + "</option>";
        }
        return ret;
    }

    private String getInstanceTypesStr() {
        String value = getValueOf("/InstanceType");
        if (null==value) value="";
        String checked = "";
        String ret = "<option></option>";
        for (String name : getInstanceTypes()) {
            if (name.equalsIgnoreCase(value)) checked=" selected=\"true\""; else checked="";
            ret += "<option"+checked+">" + name + "</option>";
        }
        return ret;
    }

    private String getSecGroupFor(final List<String> lista, final String tipo) {
        String value = getValueOf(tipo);
        if (null==value) value="";
        String checked = "";
        String ret = "<option></option>";
        for (String name : lista) {
            if (name.equalsIgnoreCase(value)) checked=" selected=\"true\""; else checked="";
            ret += "<option"+checked+">" + name + "</option>";
        }
        return ret;
    }

    private String getLoadBalancersStr() {
        String value = getValueOf("/LoadBal");
        if (null==value) value="";
        String checked = "";
        String ret = "<option></option>";
        for (String name : getLoadBalancers()) {
            if (name.equalsIgnoreCase(value)) checked=" selected=\"true\""; else checked="";
            ret += "<option"+checked+">" + name + "</option>";
        }
        return ret;
    }

    private String getElastic() {
        String value = getValueOf("/Elastic");
        if (null==value) value="";
        String checked = "";
        String ret = "<option></option>";
        for (String name : getElasticIPs()) {
            if (name.equalsIgnoreCase(value)) checked=" selected=\"true\""; else checked="";
            ret += "<option"+checked+">" + name + "</option>";
        }
        return ret;
    }

    private String getAvailableZones() {
        String value = getValueOf("/AvZone");
        if (null==value) value="";
        String checked = "";
        String ret = "<option></option>";
        for (String name : getAvailabilityZones()) {
            if (name.equalsIgnoreCase(value)) checked=" selected=\"true\""; else checked="";
            ret += "<option"+checked+">" + name + "</option>";
        }
        return ret;
    }
    
    private String getFormLaunch(final SWBParamRequest paramRequest, final boolean launched){
        String lnch = (launched?"Detener":"Lanzar");
        String forma = "<form id=\"launcAWS\" dojoType=\"dijit.form.Form\" class=\"swbform\" action=\""
                + paramRequest.getRenderUrl().setAction("launch")
                + "\" onsubmit=\"submitForm('launcAWS');return false;\" method=\"post\">\n"
                + "<fieldset>"
                + "<legend>Lanzamiento</legend>"
                + "<span align=\"center\">\n"
                + "    <button dojoType=\"dijit.form.Button\" type=\"submit\">"+lnch+"</button>\n"
                + "</span></fieldset>\n"
                + "</form>";

        return forma;
    }
    
    private String getFormShutdown(final SWBParamRequest paramRequest){
        String list = "";
        Iterator<InstanceData> datos = getControlCenter().listInstanceData();
        while(datos.hasNext()) {
            list += ", "+datos.next().getId();
        }
        list = list.substring(2);
        String forma = "<form id=\"shutAWS\" dojoType=\"dijit.form.Form\" class=\"swbform\" action=\""
                + paramRequest.getRenderUrl().setAction("shutdown")
                + "\" onsubmit=\"submitForm('shutAWS');return false;\" method=\"post\">\n"
                + "<fieldset>"
                + "<legend>Shutdown</legend>"
                + "	    <table>\n"
                + "                <tr><td width=\"200px\" align=\"right\"><label for=\"AvZone\">Instances List &nbsp;</label></td><td>"+list +"</td></tr>\n"
                + "	    </table>\n"                
                + "<span align=\"center\">\n"
                + "    <button dojoType=\"dijit.form.Button\" type=\"submit\">Apagar todas las instancias</button>\n"
                + "</span></fieldset>\n"
                + "</form>";

        return forma;
    }
}
