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
package org.semanticwb.portal.admin.resources;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.semanticwb.Logger;
import org.semanticwb.SWBPortal;
import org.semanticwb.SWBUtils;
import org.semanticwb.aws.InstanceData;
import static org.semanticwb.aws.SWBAWSDataUtils.*;
import org.semanticwb.portal.api.GenericResource;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceException;

/**
 *Cloud Configuration Resource
 * @author serch
 */
public final class SWBCloudConfig extends GenericResource {

    private static Logger log = SWBUtils.getLogger(SWBCloudConfig.class);

    /**
     * View Method for Cloud Configuration Resource
     * @param request the Request
     * @param response the Response
     * @param paramRequest the paramRequest
     * @throws SWBResourceException
     * @throws IOException 
     */
    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response,
            SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        PrintWriter out = response.getWriter();
        //out.print(jsElements());
        if (SWBPortal.getCloud()==null){
            out.println("<fieldset class=\"swbform\"><legend>Information</legend>"
                    + "<form></br></br></br></br></br>"
                    + "This function only works in an EC2 AWS"
                    + "</br></br></br></br></br>"
                    + "</br></br></br></br></br></form></fieldset>");
            return;
        }
        /**
        String val = getValueOf("/launched"); 
        boolean launched = (null!=val&&"true".equals(val))?true:false;
        if ("launch".equals(paramRequest.getAction())){
            if (null==val || "false".equals(val)){
                setValueOf("/launched", "true");
                launched=true;
                SWBPortal.getCloud().launch();
            } else {
                setValueOf("/launched", "false");
                launched=false;
            }
        }
        if ("shutdown".equals(paramRequest.getAction())){
            setValueOf("/launched", "false");
            launched=false;
            Iterator<InstanceData> lista = SWBPortal.getAWSCloud().getControlCenter().listInstanceData();    
            while(lista.hasNext()){
                InstanceData inDat = lista.next();
                SWBPortal.getAWSCloud().removeInstance(SWBPortal.getAWSCloud().getControlCenter().getLbName(),inDat.getId());
                SWBPortal.getAWSCloud().getControlCenter().removeInstanceData(inDat);
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

        
        
        SWBPortal.getAWSCloud().getControlCenter().reloadData();
        Iterator<InstanceData> iid = SWBPortal.getAWSCloud().getControlCenter().listInstanceData();
        if (null!=iid && iid.hasNext()){
            out.print(getFormShutdown(paramRequest));
        }
        out.print(getFormCredentials(paramRequest, launched));
        //out.print("<br/><br/>");
        if (checkIfParameterOk("/accessKey") && checkIfParameterOk("/secretKey")) {
            List<String> secGrp = SWBPortal.getAWSCloud().getSecurityGroups();
            out.print(getFormConfig(paramRequest, secGrp, launched));
            if (checkIfCanLaunch()) out.print(getFormLaunch(paramRequest, launched));
            //SWBPortal.getAWSCloud().getRunningInstances();
        }

    }

    private static String jsElements() {
        return "    <script type=\"text/javascript\">\n"
                + "      // scan page for widgets and instantiate them\n"
                + "      dojo.require(\"dojo.parser\");\n"
                + "      dojo.require(\"dijit._Calendar\");\n"
                + "      dojo.require(\"dijit.ProgressBar\");\n\n"
                + "     // editor:\n"
                + "      dojo.require(\"dijit.Editor\");\n\n"
                + "      // various Form elemetns\n"
                + "      dojo.require(\"dijit.form.Form\");\n"
                + "      dojo.require(\"dijit.form.CheckBox\");\n"
                + "      dojo.require(\"dijit.form.Textarea\");\n"
                + "      dojo.require(\"dijit.form.FilteringSelect\");\n"
                + "      dojo.require(\"dijit.form.TextBox\");\n"
                + "      dojo.require(\"dijit.form.DateTextBox\");\n"
                + "      dojo.require(\"dijit.form.TimeTextBox\");\n"
                + "      dojo.require(\"dijit.form.Button\");\n"
                + "      dojo.require(\"dijit.form.NumberSpinner\");\n"
                + "      dojo.require(\"dijit.form.Slider\");\n"
                + "      dojo.require(\"dojox.form.BusyButton\");\n"
                + "      dojo.require(\"dojox.form.TimeSpinner\");\n"
                + "    </script>\n\n";
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
            SWBPortal.getAWSCloud().reloadCredentials();
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
        for (String name : SWBPortal.getAWSCloud().getKeyNames()) {
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
        for (String name : SWBPortal.getAWSCloud().getImagesNames()) {
            int i = name.indexOf("|");
            if (name.substring(0, i).equalsIgnoreCase(value)) checked=" selected=\"true\""; else checked="";
            ret += "<option value=\"" + name.substring(0, i) + "\""+checked+">" + name.substring(i + 1) + "</option>";
        }
        return ret;
    }

    private String getInstanceTypes() {
        String value = getValueOf("/InstanceType");
        if (null==value) value="";
        String checked = "";
        String ret = "<option></option>";
        for (String name : SWBPortal.getAWSCloud().getInstanceTypes()) {
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

    private String getLoadBalancers() {
        String value = getValueOf("/LoadBal");
        if (null==value) value="";
        String checked = "";
        String ret = "<option></option>";
        for (String name : SWBPortal.getAWSCloud().getLoadBalancers()) {
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
        for (String name : SWBPortal.getAWSCloud().getElasticIPs()) {
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
        for (String name : SWBPortal.getAWSCloud().getAvailabilityZones()) {
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
        Iterator<InstanceData> datos = SWBPortal.getAWSCloud().getControlCenter().listInstanceData();
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
    **/
    }
}


