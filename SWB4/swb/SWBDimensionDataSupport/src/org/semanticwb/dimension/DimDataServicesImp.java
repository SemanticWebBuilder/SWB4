/*
 * SemanticWebBuilder es una plataforma para el desarrollo de portales y aplicaciones de integración,
 * colaboración y conocimiento, que gracias al uso de tecnología semántica puede generar contextos de
 * información alrededor de algún tema de interés o bien integrar información y aplicaciones de diferentes
 * fuentes, donde a la información se le asigna un significado, de forma que pueda ser interpretada y
 * procesada por personas y/o sistemas, es una creación original del Fondo de Información y Documentación
 * para la Industria INFOTEC, cuyo registro se encuentra actualmente en trámite.
 * 
 * INFOTEC pone a su disposición la herramienta SemanticWebBuilder a través de su licenciamiento abierto al público (?open source?),
 * en virtud del cual, usted podrá usarlo en las mismas condiciones con que INFOTEC lo ha diseñado y puesto a su disposición;
 * aprender de él; distribuirlo a terceros; acceder a su código fuente y modificarlo, y combinarlo o enlazarlo con otro software,
 * todo ello de conformidad con los términos y condiciones de la LICENCIA ABIERTA AL PÚBLICO que otorga INFOTEC para la utilización
 * del SemanticWebBuilder 4.0.
 * 
 * INFOTEC no otorga garantía sobre SemanticWebBuilder, de ninguna especie y naturaleza, ni implícita ni explícita,
 * siendo usted completamente responsable de la utilización que le dé y asumiendo la totalidad de los riesgos que puedan derivar
 * de la misma.
 * 
 * Si usted tiene cualquier duda o comen tario sobre SemanticWebBuilder, INFOTEC pone a su disposición la siguiente
 * dirección electrónica:
 * http://www.semanticwebbuilder.org
 */
package org.semanticwb.dimension;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.TimerTask;
import java.util.logging.Level;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
import static org.semanticwb.dimension.SWBDimensionDataUtils.*;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceException;
import org.semanticwb.portal.services.SWBCloud;

/**
 *
 * @author serch
 */
public final class DimDataServicesImp implements SWBCloud {

    private static Logger log = SWBUtils.getLogger(DimDataServicesImp.class);
    private static SWBCloudControlCenter controlCenter;

    public DimDataServicesImp() throws IOException {
        Utils.initServices();
        controlCenter = new SWBCloudControlCenter();
    }

    public void reloadCredentials() throws IOException {
        Utils.initServices();
    }

    public List<String> getImagesNames() {
        List<String> ret = new ArrayList<String>();
//        for (String ImageName : Utils.getImagesNames(true)) {
//            ret.add(image.getImageId() + "|" + image.getName());
//        }
        return ret;
    }

//    public List<String> serverNames() {
//        List<String> ret = new ArrayList<String>();
//        for (Server  serverId : Utils.getserverNames()) {
//            ret.add("" + instanceType);
//        }
//        return ret;
//    }
    public List<InstanceData> getRunningInstances() throws IOException {
        List<InstanceData> ret = new ArrayList<InstanceData>();
        String name = null;
        String basename = controlCenter.getBaseName();

        if (basename != null && !"".equals(basename)) {
//          int add = Utils.getRunningInstances().size(); 
//          int min = 0;   
            for (InstanceData server : Utils.getRunningInstances()) {
                name = server.getServerName();
//              System.out.println("comparo basename generado: " + basename+Integer.toString(add-min) + "con : " + name );
//              System.out.println("STRING OBJETO: " + server.toString() + "con nombre: "+ name);
                if (name.startsWith(basename)) {
//                  System.out.println("GUARDO OBJETO: "+ server.toString());
//                  System.out.println("REAL ID EN GETRUNNING: " + server.getRealId());
                    ret.add(server);
//            controlCenter.addInstanceData(server);
//            min++;
                }

            }
        }
        return ret;
    }

    public void removeInstance(InstanceData inDat) throws IOException {
//        System.out.println("REMOVE Instance indat" + inDat.getServerName());
//        System.out.println("TOSTRING OBJETO REMOVE :  " + inDat.toString());
        String realId = "";
        String info = "";
        if (inDat != null) {

            info = Utils.getRealInfo(controlCenter.getNetId());
            realId = Utils.getRealId(inDat.getServerName(), info);
            inDat.setRealId(realId);
            Utils.removeServer(inDat, controlCenter.getFarmId(), controlCenter.getNetId());
            controlCenter.setCount(0);
//        controlCenter.removeInstanceData(inDat);
        }

    }

    private int countRunningClientInstances() {
        try {
            int count = 0;
            for (InstanceData id : getRunningInstances()) {
                if ("true".equals(id.getStarted())) {
                    count++;
                }
            }
            return count;
        } catch (IOException ex) {
            java.util.logging.Logger.getLogger(DimDataServicesImp.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }
    }

    @Override
    public void launch() {
        if (controlCenter.isLaunched()) {
            try {
                controlCenter.reloadData();
            } catch (IOException ex) {
                java.util.logging.Logger.getLogger(DimDataServicesImp.class.getName()).log(Level.SEVERE, null, ex);
            }

            log.debug("Launching Instance " + countRunningClientInstances() + " - " + SWBDimensionDataUtils.getValueOf("/launched"));
            if ("true".equals(SWBDimensionDataUtils.getValueOf("/launched"))
                    && countRunningClientInstances() == 0) {
                log.debug("Creating...");
                String name = createInstance(controlCenter.getImageId(),
                        controlCenter.getNetId(), controlCenter.getFarmId());
                log.event("Launched... "+name);

            }

            TimerTask tt = new MonitorCloudTask();
            controlCenter.activateMonitoring(tt);

            try {
                controlCenter.reloadData();
            } catch (IOException ex) {
                java.util.logging.Logger.getLogger(DimDataServicesImp.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public SWBCloudControlCenter getControlCenter() {
        return controlCenter;
    }

    public double getCPUUSage(final InstanceData instance) {
        double load = 0;
        try {
            load = Utils.getCPUAverage(instance, controlCenter.getNetId());

        } catch (IOException ex) {
            log.error("Failed at getCPUUSSage: " + ex, ex);
        }
        return load;
    }

    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response,
            SWBParamRequest paramRequest, PrintWriter out) throws SWBResourceException, IOException {

        String val = getValueOf("/launched");
        boolean launched = (null != val && "true".equals(val)) ? true : false;
        if ("launch".equals(paramRequest.getAction())) {
            if (null == val || "false".equals(val)) {
                setValueOf("/launched", "true");
                launched = true;
                launch();
            } else {
                setValueOf("/launched", "false");
                launched = false;
            }
        }
        if ("shutdown".equals(paramRequest.getAction())) {
//            System.out.println("entro al shutdown");
            setValueOf("/launched", "false");
            launched = false;
            Iterator<InstanceData> lista = getRunningInstances().iterator();
            while (lista.hasNext()) {

                InstanceData inDat = lista.next();
//                 System.out.println("SERVIDOR A BORRAR: " + inDat.getServerName());
//                System.out.println("TO STRING SERVIDOR: " + inDat.toString());
                removeInstance(inDat);
//                getControlCenter().removeInstanceData(inDat);
            }
        }
        if ("accUpdate".equals(paramRequest.getAction())) {
            String error = processCredentials(request);
            if (null != error) {
                out.print("<script>alert('" + error + "');</script>");
            }
        }
        if ("confUpdate".equals(paramRequest.getAction())) {
            String error = processConfig(request);

            if (null != error) {
                out.print("<script>alert('" + error + "');</script>");
            }
        }
        if ("confUpdate2".equals(paramRequest.getAction())) {
            String error = processConfig2(request);
            if (null != error) {
                out.print("<script>alert('" + error + "');</script>");
            }
        }

//        getControlCenter().reloadData();
        if (launched) {
            getRunningInstances();
        }
        Iterator<InstanceData> iid = getControlCenter().listInstanceData();
        if (null != iid && iid.hasNext()) {
            out.print(getFormShutdown(paramRequest));
        }
        out.print(getFormCredentials(paramRequest, launched));
        out.print("<br/><br/>");
//        System.out.println("tome las credenciales");

        if (checkIfParameterOk("/user") && checkIfParameterOk("/password")) {
            try {
                if (Utils.url != null && Utils.url != "") {
                    String urlOrg = Utils.getOrgId();
                    if (urlOrg != null && urlOrg != "not found" && urlOrg != "") {
                        Utils.urlOrg = Utils.url + urlOrg;

                    }
                }
            } catch (IOException e) {
                log.error("Credenciales de Dimension Data incorrectas");
            }
            out.print(getFormConfig(paramRequest, launched));

            if (checkIfParameterOk("/NetworkName") && checkIfParameterOk("/ImageName")
                    && checkIfParameterOk("/MaxNumberInstances")) {
                out.print(getFormConfig2(paramRequest, launched));
            } else {
                out.println("Los datos no son correctos");
            }

            if (checkIfCanLaunch()) {
//              
                out.print(getFormLaunch(paramRequest, launched));
            }
//            SWBPortal.getCloud().getRunningInstances();
        }

    }

    private String getFormCredentials(final SWBParamRequest paramRequest, final boolean launched) throws IOException {
        String access = getValueOf("/user");
        String secret = getValueOf("/password");
//        System.out.println("Userpass: " + access + secret);
        if (access == null) {
            access = "";
        }
        if (secret == null) {
            secret = "";
        }

        String forma = null;
        if (!launched) {

            forma = "<form id=\"credentialsDD\" dojoType=\"dijit.form.Form\" class=\"swbform\" action=\""
                    + paramRequest.getRenderUrl().setAction("accUpdate")
                    + "\" onsubmit=\"submitForm('credentialsDD');return false;\" method=\"post\">\n"
                    + "<fieldset>\n"
                    + "<legend>Datos de Accesso</legend>"
                    + "	    <table>\n"
                    + "                <tr><td width=\"200px\" align=\"right\"><label for=\"user\">User &nbsp;</label></td><td><input _id=\"user\" name=\"user\" value=\"" + access + "\" dojoType=\"dijit.form.ValidationTextBox\" required=\"false\" promptMessage=\"Captura el Usuario\" style=\"width:300px;\" /></td></tr>\n"
                    + "                <tr><td width=\"200px\" align=\"right\"><label for=\"password\">Password &nbsp;</label></td><td><input _id=\"password\" name=\"password\" value=\"" + secret + "\" dojoType=\"dijit.form.ValidationTextBox\" type=\"password\" required=\"false\" promptMessage=\"Captura la contrase&#241;a \" style=\"width:300px;\" /></td></tr>\n"
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

    private String processCredentials(final HttpServletRequest request) throws IOException {
        String error = null;
        String acc = request.getParameter("user").trim();
        String sec = request.getParameter("password").trim();
        if (null == acc || "".equals(acc) || null == sec || "".equals(sec)) {
            error = "No se proporcionaron los datos de acceso a DimensionData";
        } else {
            setValueOf("/user", acc);
            setValueOf("/password", sec);
            reloadCredentials();
        }

        return error;
    }

    private String processConfig(final HttpServletRequest request) {
        String error = null;

        String imageName = request.getParameter("ImageName").trim();
        if (null != imageName && (!"".equals(imageName))) {
            setValueOf("/ImageName", imageName);
        } else {
            removeValue("/ImageName");
        }
        String instanceType = request.getParameter("NetworkName").trim();
        if (null != instanceType && (!"".equals(instanceType))) {
            setValueOf("/NetworkName", instanceType);
        } else {
            removeValue("/NetworkName");
        }
        String maxNumberInstances = request.getParameter("MaxNumberInstances").trim();
        if (null != maxNumberInstances && (!"".equals(maxNumberInstances))) {
            setValueOf("/MaxNumberInstances", maxNumberInstances);
        } else {
            removeValue("/MaxNumberInstances");
        }

        return error;
    }

    private String getFormConfig(SWBParamRequest paramRequest, final boolean launched) throws IOException {
        String maxNum = getValueOf("/MaxNumberInstances");
        String imageName = getValueOf("/ImageName");
        String netname = getValueOf("/NetworkName");

        if (null == maxNum) {
            maxNum = "";
        }
        if (null == imageName) {
            imageName = "";
        }
        if (null == netname) {
            netname = "";
        }

        String forma = null;
        if (!launched) {

            forma = "<form id=\"configDD\" dojoType=\"dijit.form.Form\" class=\"swbform\" action=\""
                    + paramRequest.getRenderUrl().setAction("confUpdate")
                    + "\" onsubmit=\"submitForm('configDD');return false;\" method=\"post\">\n"
                    + "<fieldset>\n"
                    + "<legend>Configuraci&oacute;n</legend>"
                    + "	    <table>\n"
                    + "               <tr><td width=\"200px\" align=\"right\"><label for=\"NetworkName\">Network Name &nbsp;</label></td><td><select name=\"NetworkName\" dojoType=\"dijit.form.ComboBox\" >" + getNetName() + "</td></tr>\n"
                    //                    + "<script type=\"dojo/method\" event=\"onChange\" args=\"newValue\">"
                    //                    + "alert(\"value changed to \", newValue)"
                    //                    +  "<\\script> <\\select>" 
                    + "                <tr><td width=\"200px\" align=\"right\"><label for=\"ImageName\">Image Name &nbsp;</label></td><td><select name=\"ImageName\" >" + getImageName() + "</td></tr>\n"
                    + "                <tr><td width=\"200px\" align=\"right\"><label for=\"MaxNumberInstances\">Max Instance to launch &nbsp;</label></td><td><input _id=\"MaxNumberInstances\" name=\"MaxNumberInstances\" "
                    + "value=\"" + maxNum + "\" dojoType=\"dijit.form.ValidationTextBox\" required=\"true\" promptMessage=\"Set the max number servers to Launch\" invalidMessage=\"Not a number\" style=\"width:40px;\"  "
                    + "</td></tr>\n"
                    + "	    </table>\n"
                    + "	</fieldset>"
                    + "<fieldset><span align=\"center\">\n"
                    + "    <button dojoType=\"dijit.form.Button\" type=\"submit\">Continuar </button>\n"
                    + "</span></fieldset>\n"
                    + "</form>";
        } else {
            forma = "<fieldset class=\"swbform\">\n"
                    + "<legend>Configuraci&oacute;n</legend>"
                    + "	    <table>\n"
                    + "                <tr><td width=\"200px\" align=\"right\"><label for=\"NetName\">Network Name &nbsp;</label></td><td>" + getValueOf("/NetworkName") + "</td></tr>\n"
                    + "                <tr><td width=\"200px\" align=\"right\"><label for=\"ImageName\">Image Name &nbsp;</label></td><td>" + getValueOf("/ImageName") + "</td></tr>\n"
                    + "                <tr><td width=\"200px\" align=\"right\"><label for=\"MaxNumberInstances\">Max Instance to launch &nbsp;</label></td><td>" + maxNum + "</td></tr>\n"
                    + "	    </table>\n"
                    + "	</fieldset>";
        }

        return forma;
    }

    private String getFormLaunch(final SWBParamRequest paramRequest, final boolean launched) {
        String lnch = (launched ? "Detener" : "Lanzar");
        String forma = "<form id=\"launchDData\" dojoType=\"dijit.form.Form\" class=\"swbform\" action=\""
                + paramRequest.getRenderUrl().setAction("launch")
                + "\" onsubmit=\"submitForm('launchDData');return false;\" method=\"post\">\n"
                + "<fieldset>"
                + "<legend>Lanzamiento</legend>"
                + "<span align=\"center\">\n"
                + "    <button dojoType=\"dijit.form.Button\" type=\"submit\">" + lnch + "</button>\n"
                + "</span></fieldset>\n"
                + "</form>";

        return forma;
    }

    private String getFormShutdown(final SWBParamRequest paramRequest) throws IOException {
        log.trace("DimDataServicesImp.getFormShutdown");
        String list = "";
        Iterator<InstanceData> datos = getRunningInstances().iterator();
        while (datos.hasNext()) {
            list += ", " + datos.next().getServerName();
        }
        if (!list.equals("")) {
            list = list.substring(2);
        }
        log.trace("DDataServicesImp.getFormShutdown list:" + list);
        String forma = "<form id=\"shutDData\" dojoType=\"dijit.form.Form\" class=\"swbform\" action=\""
                + paramRequest.getRenderUrl().setAction("shutdown")
                + "\" onsubmit=\"submitForm('shutDData');return false;\" method=\"post\">\n"
                + "<fieldset>"
                + "<legend>Shutdown</legend>"
                + "	    <table>\n"
                + "                <tr><td width=\"200px\" align=\"right\"><label for=\"AvZone\">Instances List &nbsp;</label></td><td>" + list + "</td></tr>\n"
                + "	    </table>\n"
                + "<span align=\"center\">\n"
                + "    <button dojoType=\"dijit.form.Button\" type=\"submit\">Apagar todas las instancias</button>\n"
                + "</span></fieldset>\n"
                + "</form>";

        return forma;
    }

    String createInstance(final String imageid, final String netId, final String FarmId) {
        final String serverName = controlCenter.getBaseName() + controlCenter.getCount();
        new Thread() {
            public void run() {
                try {
                    String ret;
                    String serverId;
                    String realServerId;
                    String doc;
                    String ip;
                    String info = null;
                    if (imageid != null && !(imageid.equals("")) && netId != null && !(netId.equals(""))) {
                        info = Utils.setInfo(imageid, netId, serverName);
                    }
//            System.out.println("Se creara un servidor con nombre:  "+ serverName);
                    Utils.runInstance(info, serverName);
//            System.out.println("cree el servidor");
                    List<InstanceData> run = getRunningInstances();
                    for (InstanceData instance : run) {
                        ret = instance.getServerName();
//                System.out.println("comparo instancia: " +ret + "nombre enviado:  " + serverName);
                        if (ret.equals(serverName)) {
                            serverId = instance.getServerId();
                            Utils.createRealS(serverId, serverName, netId);
                            doc = Utils.getRealInfo(netId);
                            realServerId = Utils.getRealId(serverName, doc);
                            instance.setRealId(realServerId);
                            ip = Utils.getRealIp(doc, serverName);
                            instance.setIp(ip);
                            Utils.addToFarm(netId, FarmId, realServerId);
//                 System.out.println("OBJETO CREADO: " + instance.toString());
                        }
                    }

                } catch (IOException ex) {
                    java.util.logging.Logger.getLogger(DimDataServicesImp.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }.start();
        return serverName;
    }

    private String getImageName() throws IOException {
        String value = getValueOf("/ImageName");
        if (null == value) {
            value = "";
        }
        String checked = "";
        String ret = "<option></option>";
        for (String name : Utils.getImageNames()) {
            if (name.equalsIgnoreCase(value)) {
                checked = " selected=\"true\"";
            } else {
                checked = "";
            }
            ret += "<option" + checked + ">" + name + "</option>";
        }
        return ret;
    }

    private String getFarmName() throws IOException {
//        System.out.println("obtener los Farm");

        String netName = getValueOf("/NetworkName");
        String value = getValueOf("/FarmName");
        if (null == value) {
            value = "";
        }
//        System.out.println("NetNAme : " + netName);
//        System.out.println("FarmName(getValueOf) : "+ value);
        String checked = "";
        String ret = "<option></option>";
        for (String name : Utils.getFarmNames(netName)) {
            if (name.equalsIgnoreCase(value)) {
                checked = " selected=\"true\"";
            } else {
                checked = "";
            }
            ret += "<option" + checked + ">" + name + "</option>";
        }
        return ret;

    }

    private String getNetName() throws IOException {
//        System.out.println("obtengo netname");
        String value = getValueOf("/NetworkName");
        if (null == value) {
            value = "";
        }
        String checked = "";
        String ret = "<option></option>";
        for (String name : Utils.getNetNames()) {
            if (name.equalsIgnoreCase(value)) {
                checked = " selected=\"true\"";
            } else {
                checked = "";
            }
            ret += "<option" + checked + ">" + name + "</option>";
//            System.out.println("checked"+checked);  
        }
        return ret;
    }

    private String getFarmName(String netname) throws IOException {
//        System.out.println("Entro farm con string"+ netname);
        String value = getValueOf("/FarmName");

        if (null == value) {
            value = "";
        }
        String checked = "";
        String ret = "<option></option>";
        for (String name : Utils.getFarmNames(netname)) {
            if (name.equalsIgnoreCase(value)) {
                checked = " selected=\"true\"";
            } else {
                checked = "";
            }
            ret += "<option" + checked + ">" + name + "</option>";
        }
        return ret;
    }

    private String getFormConfig2(SWBParamRequest paramRequest, boolean launched) throws IOException {

        String farmName = getValueOf("/FarmName");
        String maxCPU = getValueOf("/MaxCPU");
        String baseName = getValueOf("/BaseName");

        if (null == maxCPU) {
            maxCPU = "";
        }
        if (null == farmName) {
            farmName = "";
        }
        String forma = null;
        if (!launched) {

            forma = "<form id=\"configDD2\" dojoType=\"dijit.form.Form\" class=\"swbform\" action=\""
                    + paramRequest.getRenderUrl().setAction("confUpdate2")
                    + "\" onsubmit=\"submitForm('configDD2');return false;\" method=\"post\">\n"
                    + "<fieldset>\n"
                    + "<legend>Configuraci&oacute;n 2</legend>"
                    + "	    <table>\n"
                    + "<tr><td width=\"200px\" align=\"right\"><label for=\"FarmName\">Farm Name &nbsp;</label></td><td><select name=\"FarmName\" >" + getFarmName() + "</td></tr>\n"
                    + "                <tr><td width=\"200px\" align=\"right\"><label for=\"MaxCPU\">CPU Average Level to launch &nbsp;</label></td><td><input _id=\"MaxCPU\" name=\"MaxCPU\" "
                    + "value=\"" + maxCPU + "\" dojoType=\"dijit.form.ValidationTextBox\" required=\"true\" promptMessage=\"Set the CPU average usage level to Launch other instances\" invalidMessage=\"Not a number\" style=\"width:40px;\"  "
                    + "trim=\"true\"/></td></tr>\n"
                    + "                <tr><td width=\"200px\" align=\"right\"><label for=\"BaseName\">Servers Base Name &nbsp;</label></td><td><input _id=\"BaseName\" name=\"BaseName\" "
                    + "value=\"" + baseName + "\" dojoType=\"dijit.form.ValidationTextBox\" required=\"true\" promptMessage=\"Set the base name for the servers to launch\" style=\"width:40px;\"  "
                    + "trim=\"true\"/></td></tr>\n"
                    + "	    </table>\n"
                    + "	</fieldset>"
                    + "<fieldset><span align=\"center\">\n"
                    + "    <button dojoType=\"dijit.form.Button\" type=\"submit\">Enviar configuraci&oacute;n</button>\n"
                    + "</span></fieldset>\n"
                    + "</form>";
        } else {
            forma = "<fieldset class=\"swbform\">\n"
                    + "<legend>Configuraci&oacute;n</legend>"
                    + "	    <table>\n"
                    + "                <tr><td width=\"200px\" align=\"right\"><label for=\"FarmName\">Farm Name &nbsp;</label></td><td>" + getValueOf("/FarmName") + "</td></tr>\n"
                    + "                <tr><td width=\"200px\" align=\"right\"><label for=\"MaxCPU\">CPU Average Level to launch &nbsp;</label></td><td>" + maxCPU + "</td></tr>\n"
                    + "                <tr><td width=\"200px\" align=\"right\"><label for=\"BaseName\">Servers Base Name &nbsp;</label></td><td>" + baseName + "</td></tr>\n"
                    + "	    </table>\n"
                    + "	</fieldset>";
        }

        return forma;
    }

    private String processConfig2(final HttpServletRequest request) {
        String error = null;

        String farmName = request.getParameter("FarmName").trim();
        if (null != farmName && (!"".equals(farmName))) {
            setValueOf("/FarmName", farmName);
        } else {
            removeValue("/FarmName");
        }

        String baseName = request.getParameter("BaseName").trim();
        if (null != baseName && (!"".equals(baseName))) {
            setValueOf("/BaseName", baseName);
        } else {
            removeValue("/BaseName");
        }
        String maxCPU = request.getParameter("MaxCPU").trim();
        if (null != maxCPU && (!"".equals(maxCPU))) {
            setValueOf("/MaxCPU", maxCPU);
        } else {
            removeValue("/MaxCPU");
        }
        return error;
    }

}
