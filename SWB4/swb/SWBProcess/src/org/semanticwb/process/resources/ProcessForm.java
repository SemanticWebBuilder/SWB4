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
package org.semanticwb.process.resources;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.NodeIterator;
import com.hp.hpl.jena.rdf.model.RDFNode;
import java.io.*;
import java.security.GeneralSecurityException;
import java.security.Signature;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.util.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.semanticwb.Logger;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBPortal;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.*;
import org.semanticwb.platform.*;
import org.semanticwb.portal.SWBFormMgr;
import org.semanticwb.portal.SWBForms;
import org.semanticwb.portal.api.*;
import org.semanticwb.portal.util.Base64;
import org.semanticwb.process.forms.SWBFormMgrLayer;
import org.semanticwb.process.model.*;

/**
 *
 * @author javier.solis
 */
public class ProcessForm extends GenericResource {

    /**
     * The log.
     */
    private Logger log = SWBUtils.getLogger(ProcessForm.class);
    private HashMap<String, SemanticObject> hmFormEle = null;
    static final String ADMINMODE_SIMPLE = "simple";
    static final String ADMINMODE_ADVANCE = "advance";
    static final String FE_MODE_VIEW = "view";
    static final String FE_MODE_EDIT = "edit";
    static final String FE_DEFAULT = "generico";
    static final String MODE_SIGN = "sign";
    static final String MODE_ACUSE = "acuse";

    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        PrintWriter out = response.getWriter();
        String lang = paramRequest.getUser().getLanguage();

        StringBuilder ret = new StringBuilder("");
        User user = paramRequest.getUser();

        Resource base = getResourceBase();

        String adminMode = base.getAttribute("adminMode", ADMINMODE_SIMPLE);

        String action = paramRequest.getAction();

        String suri = request.getParameter("suri");
        if (suri == null) {
            out.println("Parámetro no definido...");
            return;
        }

        SemanticOntology ont = SWBPlatform.getSemanticMgr().getOntology();
        FlowNodeInstance foi = (FlowNodeInstance) ont.getGenericObject(suri);


        User asigned = foi.getAssignedto();
        if (asigned != null && !asigned.equals(user)) {
            out.println("Tarea asignada previamente a otro usuario...");
            return;
        }

        if (foi.getStatus() == Instance.STATUS_CLOSED || foi.getStatus() == Instance.STATUS_ABORTED || foi.getStatus() == Instance.STATUS_STOPED) {
            out.println("La tarea ya ha sido ejecutada...");
            return;
        }

        if (asigned == null) {
            foi.setAssigned(new Date());
            foi.setAssignedto(user);
        }

        SWBProcessFormMgr mgr = new SWBProcessFormMgr(foi);
        mgr.setAction(paramRequest.getActionUrl().setAction("process").toString());
        mgr.clearProperties();

        HashMap<String, SemanticClass> hmclass = new HashMap<String, SemanticClass>();
        HashMap<String, SemanticProperty> hmprops = new HashMap<String, SemanticProperty>();
        Iterator<ItemAwareReference> it = foi.listHeraquicalItemAwareReference().iterator();
        while (it.hasNext()) {
            ItemAwareReference item = it.next();
            SWBClass obj = item.getProcessObject();

            if (obj != null) {
                SemanticClass cls = obj.getSemanticObject().getSemanticClass();

                hmclass.put(item.getItemAware().getName(), cls);

                Iterator<SemanticProperty> itp = cls.listProperties();
                while (itp.hasNext()) {
                    SemanticProperty prop = itp.next();
                    hmprops.put(item.getItemAware().getName() + "|" + prop.getPropId(), prop);
                }
            }
        }

        if (ADMINMODE_SIMPLE.equals(adminMode)) {

            out.println(SWBForms.DOJO_REQUIRED);

            SWBResourceURL urlact = paramRequest.getActionUrl();
            urlact.setAction("process");
            out.println("<script type=\"text/javascript\">function validateForm" + foi.getId() + "(form) {if (form.validate()) {return true;} else {alert('Algunos de los datos no son válidos. Verifique la información proporcionada.'); return false;}}</script>");
            out.println("<div id=\"processForm\">");
            out.println("<form id=\"" + foi.getId() + "/form\" dojoType=\"dijit.form.Form\" class=\"swbform\" action=\"" + urlact + "\" method=\"post\" onSubmit=\"return validateForm" + foi.getId() + "(this);\">");
            out.println("<input type=\"hidden\" name=\"suri\" value=\"" + suri + "\"/>");
            out.println("<input type=\"hidden\" name=\"smode\" value=\"edit\"/>");

            boolean printHeaders = false;

            int max = 1;
            while (!base.getAttribute("prop" + max, "").equals("")) {

                String val = base.getAttribute("prop" + max);
                String varName = "";
                String propid = "";
                String modo = "";
                String fe = "";
                StringTokenizer stoken = new StringTokenizer(val, "|");
                if (stoken.hasMoreTokens()) {
                    varName = stoken.nextToken();
                    propid = stoken.nextToken();
                    modo = stoken.nextToken();
                    fe = stoken.nextToken();
                }

                SemanticProperty semprop = hmprops.get(varName + "|" + propid);

                String strMode = "";

                SemanticClass semclass = hmclass.get(varName);

                if (semclass != null && semprop != null) {
                    if (modo.equals(FE_MODE_VIEW)) {
                        mgr.addProperty(semprop, varName, SWBFormMgr.MODE_VIEW);
                        strMode = SWBFormMgr.MODE_VIEW;
                    } else if (modo.equals(FE_MODE_EDIT)) {
                        mgr.addProperty(semprop, varName, SWBFormMgr.MODE_EDIT);
                        strMode = SWBFormMgr.MODE_VIEW;
                    }

                    SemanticObject sofe = ont.getSemanticObject(fe);

                    SWBProcessFormMgr fmgr = new SWBProcessFormMgr(foi);

                    if (!printHeaders) {
                        out.println("<fieldset>");
//                        out.println("<legend>Datos Generales</legend>");
                        out.println("<table>");
                        printHeaders = true;
                    }

                    out.println("<tr><td width=\"200px\" align=\"right\"><label for=\"title\">" + fmgr.renderLabel(request, semprop, varName, modo) + "</label></td>");
                    out.println("<td>");
                    if (null != sofe) {
                        FormElement frme = (FormElement) sofe.createGenericInstance();
                        out.println(fmgr.renderElement(request, varName, semprop, frme, modo));
                    } else {
                        out.println(fmgr.renderElement(request, varName, semprop, modo));
                    }
                    out.println("</td></tr>");
                }
                max++;
            }

            if (printHeaders) {
                out.println("    </table>");
                out.println("</fieldset>");
            }

            out.println("<fieldset><span align=\"center\">");

            if (base.getAttribute("btnSave", "").equals("use")) {
                out.println("<button dojoType=\"dijit.form.Button\" type=\"submit\">Guardar</button>");
            }
            if (base.getAttribute("btnAccept", "").equals("use")) {
                out.println("<button dojoType=\"dijit.form.Button\" name=\"accept\" type=\"submit\">Concluir Tarea</button>");
            }
            if (base.getAttribute("btnReject", "").equals("use")) {
                out.println("<button dojoType=\"dijit.form.Button\" name=\"reject\" type=\"submit\">Rechazar Tarea</button>");
            }
            if (base.getAttribute("btnCancel", "").equals("use")) {
                out.println("<button dojoType=\"dijit.form.Button\" onclick=\"window.location='" + foi.getUserTaskInboxUrl() + "?suri=" + suri + "'\">Regresar</button>");
            }

            out.println("</span></fieldset>");
            out.println("</form>");
            out.println("</div>");

        } else if (ADMINMODE_ADVANCE.equals(adminMode)) {
            if (action.equals("add") || action.equals("edit")) {
                String basepath = SWBPortal.getWorkPath() + base.getWorkPath() + "/";
                String xml = SWBUtils.IO.getFileFromPath(basepath + "code.xml");
//                if(null==xml)  xml=base.getXmlConf();
                if (xml != null && xml.trim().length() > 0) {
                    SWBFormMgrLayer swbFormMgrLayer = new SWBFormMgrLayer(xml, paramRequest, request);
                    String html = swbFormMgrLayer.getHtml();
                    ret.append(html);
                }
            }
            out.println(ret.toString());
        }
    }

    
    public void doAcuse(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        PrintWriter out = response.getWriter();
        Resource base = getResourceBase();
        User user = paramRequest.getUser();
        
        String suri = request.getParameter("suri");
        if (suri == null) {
            out.println("Parámetro no definido...");
            return;
        }
        
        String x509 = request.getParameter("sg");
        if (x509 == null) {
            out.println("Parámetro no definido...");
            return;
        }

        SemanticOntology ont = SWBPlatform.getSemanticMgr().getOntology();
        FlowNodeInstance foi = (FlowNodeInstance) ont.getGenericObject(suri);
        X509SingInstance x509SingInstance = (X509SingInstance) ont.getGenericObject(x509);
        
        if (null==x509SingInstance){
            out.println("El objeto no se encontró...");
            return;
        }
        
        out.println(SWBForms.DOJO_REQUIRED);
        
        out.println("<div id=\"processForm\" class=\"swbform\">");
        out.println("<h2>Acuse de Firma</h2>");
        out.println("<fieldset>");
        out.println("<table>");
        
        out.println("<tr>");
        
        
        
        out.println("<td width=\"200px\" align=\"right\"><label for=\"title\">Valor Firmado:</label></td>");
        out.println("<td><div id=\"code\">");
        out.println(htmlwrap(x509SingInstance.getOriginalString(), 50));
        out.println("</div></td></tr>");
        
        out.println("<td width=\"200px\" align=\"right\"><label for=\"title\">Firma:</label></td>");
        out.println("<td><div id=\"code\">");
        out.println(htmlwrap(x509SingInstance.getSignedString(), 50));
        out.println("<div></td></tr>");
        
        out.println("<td width=\"200px\" align=\"right\"><label for=\"title\">Instancia de proceso:</label></td>");
        out.println("<td>");
        out.println(x509SingInstance.getFlowNodeInstance().getProcessInstance().getProcessType().getDisplayTitle(paramRequest.getUser().getLanguage()));
        out.println("</td></tr>");
        
        out.println("<td width=\"200px\" align=\"right\"><label for=\"title\">Instancia de Tarea:</label></td>");
        out.println("<td>");
        out.println(x509SingInstance.getFlowNodeInstance().getFlowNodeType().getDisplayTitle(paramRequest.getUser().getLanguage()));
        out.println("</td></tr>");

        out.println("<td width=\"200px\" align=\"right\"><label for=\"title\">Firmada por:</label></td>");
        out.println("<td>");
        out.println(x509SingInstance.getCertificate().getName());
        out.println("</td></tr>");
        
        out.println("<td width=\"200px\" align=\"right\"><label for=\"title\">RFC:</label></td>");
        out.println("<td>");
        out.println(x509SingInstance.getCertificate().getSerial());
        out.println("</td></tr>");
        
        out.println("<td width=\"200px\" align=\"right\"><label for=\"title\">Fecha:</label></td>");
        out.println("<td>");
        out.println(x509SingInstance.getCreated());
        out.println("</td></tr>");
        
        out.println("    </table>");
        out.println("</fieldset>");
        out.println("<fieldset><span align=\"center\">");
       // out.println("<button dojoType=\"dijit.form.Button\" name=\"btn_signed\" type=\"submit\">Concluir Tarea</button>");
        
        out.println("<button dojoType=\"dijit.form.Button\" onclick=\"window.location='" + foi.getUserTaskInboxUrl() + "?suri=" + suri + "'\">Salir</button>");
        
        out.println("</span></fieldset>");
        out.println("</div>");
        
    }
    
    public void doSign(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        PrintWriter out = response.getWriter();
        Resource base = getResourceBase();
        User user = paramRequest.getUser();
        
        String suri = request.getParameter("suri");
        if (suri == null) {
            out.println("Parámetro no definido...");
            return;
        }

        SemanticOntology ont = SWBPlatform.getSemanticMgr().getOntology();
        FlowNodeInstance foi = (FlowNodeInstance) ont.getGenericObject(suri);

        String SigCad = getStringToSign(foi); 
        String signTemp=null;
        try {
            signTemp=SWBUtils.CryptoWrapper.comparablePassword(SigCad);
        } catch (GeneralSecurityException gse){
            log.error(gse);
        }
        
        User asigned = foi.getAssignedto();
        if (asigned != null && !asigned.equals(user)) {
            out.println("Tarea asignada previamente a otro usuario...");
            return;
        }

        if (foi.getStatus() == Instance.STATUS_CLOSED || foi.getStatus() == Instance.STATUS_ABORTED || foi.getStatus() == Instance.STATUS_STOPED) {
            out.println("La tarea ya ha sido ejecutada...");
            return;
        }

        SWBResourceURL urlact = paramRequest.getActionUrl();
        urlact.setAction("processSign");
       
        out.println(SWBForms.DOJO_REQUIRED);
        
        out.println("<script type=\"text/javascript\">function validateForm" + foi.getId() + "(form) {if (form.validate()) {return true;} else {alert('Algunos de los datos no son válidos. Verifique la información proporcionada.'); return false;}}");
        out.println("function setSignature(data){ dataElement = document.getElementById('hiddenSign'); dataElement.value=data; form = document.getElementById('" + foi.getId() + "/form'); alert('Documento Firmado');form.submit();}</script>");
        out.println("<div id=\"processForm\">");
        out.println("<form id=\"" + foi.getId() + "/form\" dojoType=\"dijit.form.Form\" class=\"swbform\" action=\"" + urlact + "\" method=\"post\" onSubmit=\"return validateForm" + foi.getId() + "(this);\">");
        out.println("<input type=\"hidden\" name=\"suri\" value=\"" + suri + "\"/>");
        //out.println("<input type=\"hidden\" name=\"smode\" value=\"edit\"/>");
        out.println("<input type=\"hidden\" id=\"hiddenSign\" name=\"hiddenSign\" value=\"\"/>");
        out.println("<fieldset>");
        out.println("<table>");
        
        out.println("<tr>");
        out.println("<td width=\"200px\" align=\"right\"><label for=\"title\">Firmar documento:</label></td>");
        out.println("<td>");
        out.println("<applet code=\"signatureapplet.SignatureApplet.class\" codebase=\"/swbadmin/lib\" archive=\""
                    + SWBPlatform.getContextPath()
                    + "/swbadmin/lib/SWBAplDigitalSignature.jar\" width=\"600\" height=\"250\">");
        out.println("<param name=\"message\" value=\"" + signTemp + "\">");
        out.println("</applet>");
        out.println("</td></tr>");
        out.println("    </table>");
        out.println("</fieldset>");
        out.println("<fieldset><span align=\"center\">");
       // out.println("<button dojoType=\"dijit.form.Button\" name=\"btn_signed\" type=\"submit\">Concluir Tarea</button>");
        if (base.getAttribute("btnCancel", "").equals("use")) {
            out.println("<button dojoType=\"dijit.form.Button\" onclick=\"window.location='" + foi.getUserTaskInboxUrl() + "?suri=" + suri + "'\">Regresar</button>");
        }
        out.println("</span></fieldset>");
        out.println("</form>");
        out.println("</div>");

    }

    public String getFormHTML(HttpServletRequest request, SWBActionResponse response) throws SWBResourceException, IOException {
        StringBuilder ret = new StringBuilder();
        Resource base = getResourceBase();

        SemanticOntology ont = SWBPlatform.getSemanticMgr().getOntology();
        UserTask ut = (UserTask) base.getResourceable();

        HashMap<String, SemanticClass> hmclass = new HashMap<String, SemanticClass>();
        HashMap<String, SemanticProperty> hmprops = new HashMap<String, SemanticProperty>();

        Iterator<ItemAware> it = ut.listHerarquicalRelatedItemAwarePlusNullOutputs().iterator();
        while (it.hasNext()) {
            ItemAware item = it.next();

            SemanticClass cls = item.getItemSemanticClass();
            if (cls != null) {
                hmclass.put(item.getName(), cls);

                Iterator<SemanticProperty> itp = cls.listProperties();
                while (itp.hasNext()) {
                    SemanticProperty prop = itp.next();
                    hmprops.put(prop.getPropId(), prop);
                }
            }

            //TODO: Agregar propiedades del item
        }

        ret.append("<div id=\"processForm\">");
        ret.append("\n<form");

        if (request.getParameter("useDojo") != null && request.getParameter("useDojo").equals("dojo")) {
            ret.append(" htmlType=\"dojo\" ");
        }
        ret.append(" class=\"swbform\">");

        ret.append("\n    <fieldset>");
//        ret.append("\n      <legend>Datos Generales</legend>");
        ret.append("\n      <table>");

        int max = 1;
        while (!base.getAttribute("prop" + max, "").equals("")) {

            String val = base.getAttribute("prop" + max);
            String varName = "";
            String propid = "";
            String modo = "";
            String fe = "";
            StringTokenizer stoken = new StringTokenizer(val, "|");
            if (stoken.hasMoreTokens()) {
                varName = stoken.nextToken();
                propid = stoken.nextToken();
                modo = stoken.nextToken();
                fe = stoken.nextToken();
            }

            SemanticProperty semprop = hmprops.get(propid);

            String strMode = "";

            SemanticClass semclass = hmclass.get(varName);

            if (semclass != null && semprop != null) {
                if (modo.equals(FE_MODE_VIEW)) {
                    strMode = SWBFormMgr.MODE_VIEW;
                } else if (modo.equals(FE_MODE_EDIT)) {
                    strMode = SWBFormMgr.MODE_EDIT;
                }
                SemanticObject sofe = ont.getSemanticObject(fe);
                String strFE = "";

                if (null != sofe) {
                    strFE = "formElement=\"" + sofe.getDisplayName(response.getUser().getLanguage()) + "\"";
                }

                ret.append("\n     <tr>");
                ret.append("\n       <td width=\"200px\" align=\"right\"><label name=\"" + varName + "." + semprop.getName() + "\" /></td>");
                ret.append("\n       <td><property name=\"" + varName + "." + semprop.getName() + "\" " + strFE + " mode=\"" + strMode + "\" /></td>");
                ret.append("\n    </tr>");
            }
            max++;
        }

        ret.append("\n      </table> ");
        ret.append("\n      </fieldset> ");
        ret.append("\n      <fieldset>");

        // validar que botones seleccionaron
        if (request.getParameter("btnSave") != null) {
            ret.append("\n          <Button type=\"savebtn\"/>");
        }
        if (request.getParameter("btnAccept") != null) {
            ret.append("\n          <Button type=\"submit\" name=\"accept\" title=\"Concluir Tarea\" isBusyButton=\"true\" />");
        }
        if (request.getParameter("btnReject") != null) {
            ret.append("\n          <Button isBusyButton=\"true\" name=\"reject\" title=\"Rechazar Tarea\" type=\"submit\" />");
        }
        if (request.getParameter("btnCancel") != null) {
            ret.append("\n          <Button type=\"cancelbtn\"/>");
        }

        ret.append("\n      </fieldset>");
        ret.append("\n</form>");
        ret.append("\n</div>");

        return ret.toString();
    }
    
    private String getStringToSign(FlowNodeInstance flowNodeInstance) {

        Iterator<ItemAwareReference> it = flowNodeInstance.listHeraquicalItemAwareReference().iterator();

        ByteArrayOutputStream bout = new ByteArrayOutputStream();

        Model model = ModelFactory.createDefaultModel();

        while (it.hasNext()) {
            ItemAwareReference itemAwareReference = it.next();
            model.add(itemAwareReference.getProcessObject()
                    .getSemanticObject().getRDFResource().listProperties());
                    
        }
        // "RDF/XML", "RDF/XML-ABBREV", "N-TRIPLE" and "N3"
        model.write(bout, "N-TRIPLE");

        String ret = null;
        try {
        ret = bout.toString("UTF8");
        } catch (IOException ioe){
            log.error(ioe);
        }
        return ret;
    }

    @Override
    public void processAction(HttpServletRequest request, SWBActionResponse response) throws SWBResourceException, IOException {
        Resource base = getResourceBase();

        UserTask ut = null;
        String suri = request.getParameter("suri");

        if ("updAdminMode".equals(response.getAction())) {

            base.setAttribute("adminMode", request.getParameter("adminMode"));
            try {
                base.updateAttributesToDB();
            } catch (Exception e) {
                log.error("Error al actualizar el modo de administración del recurso.",e);
            }

            if (request.getParameter("suri") != null) {
                response.setRenderParameter("suri", suri);
            }

        } else if ("updXMLConfig".equals(response.getAction())) {

            String basepath = SWBPortal.getWorkPath() + base.getWorkPath() + "/";
            File xmlFile = new File(basepath);
            if (!xmlFile.exists()) {
                xmlFile.mkdirs();
            }

            if (xmlFile.exists()) {
                try {
                    String value = getFormHTML(request, response);
                    xmlFile = new File(basepath + "code.xml");
                    PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(xmlFile)));
                    out.print(value);
                    out.flush();
                } catch (Exception e) {
                    log.error("Error saving file: " + xmlFile.getAbsolutePath(), e);
                }
            }

        } else if ("saveXMLFile".equals(response.getAction())) {
            String basepath = SWBPortal.getWorkPath() + base.getWorkPath() + "/";
            File xmlFile = new File(basepath);
            if (xmlFile.exists()) {
                try {
                    String value = null;
                    if (request.getParameter("hiddencode") != null && request.getParameter("hiddencode").trim().length() > 0) {
                        value = request.getParameter("hiddencode");
                    } else {
                        value = request.getParameter("resource" + base.getId());
                    }
                    xmlFile = new File(basepath + "code.xml");
                    PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(xmlFile)));
                    out.print(value);
                    out.flush();
                } catch (Exception e) {
                    log.error("Error saving file: " + xmlFile.getAbsolutePath(), e);
                }
            }

            response.setMode(SWBActionResponse.Mode_ADMIN);
        } else if ("processSign".equals(response.getAction())) {
            FlowNodeInstance foi = (FlowNodeInstance) SWBPlatform.getSemanticMgr().getOntology().getGenericObject(suri);
            if (suri == null) {
                return;
            }
            String cadenaOrig=null;
            String cadenaBase=getStringToSign(foi);
            try {
                cadenaOrig=SWBUtils.CryptoWrapper.comparablePassword(cadenaBase);
            } catch (GeneralSecurityException gse){
                log.error(gse);
            }
            //System.out.println("processSign:"+foi+" "+cadenaOrig);

            String appletHidden = request.getParameter("hiddenSign");
            //System.out.println("appletValue:"+appletHidden);
            User user = response.getUser();
            try {
                CertificateFactory cf = CertificateFactory.getInstance("X.509");
                //System.out.println("user.getExternalID():"+user.getExternalID());
                Iterator<SemanticObject>it = foi.getProcessSite().getSemanticModel().listSubjects(X509Certificate.swp_X509Serial, user.getExternalID()); //TODO: Modificar Búsqueda
                if (it.hasNext()){
                    X509Certificate certObj = (X509Certificate)it.next().createGenericInstance();
                    //System.out.println("certObj:"+certObj);
                    FileInputStream fis = new FileInputStream(SWBPortal.getWorkPath()+certObj.getWorkPath()+"/"+certObj.getFile());
                    BufferedInputStream bis = new BufferedInputStream(fis);

                    Certificate cert=null;
                    //System.out.println("datos:"+bis.available());
                    if (bis.available() > 0) {
                        cert = cf.generateCertificate(bis);
                    }
                    //System.out.println("cert:"+cert);
                    bis.close();
                    fis.close();
                    Signature sig = Signature.getInstance("SHA1withRSA");
                    sig.initVerify(cert);
                    byte[] data = Base64.decode(appletHidden);
                    sig.update(cadenaOrig.getBytes());
                    boolean flag = sig.verify(data);
                    //System.out.println("validado:"+flag);
                    if (flag){
                        X509SingInstance x509SingInstance=X509SingInstance.ClassMgr.createX509SingInstance(foi.getProcessSite());
                        x509SingInstance.setCertificate(certObj);
                        x509SingInstance.setFlowNodeInstance(foi);
                        x509SingInstance.setOriginalString(cadenaOrig);
                        x509SingInstance.setSignedString(appletHidden);
                        
                        File file = new File(SWBPortal.getWorkPath()+x509SingInstance.getWorkPath());
                        file.mkdirs();
                        FileOutputStream fileOut = new FileOutputStream(SWBPortal.getWorkPath()+x509SingInstance.getWorkPath()+"/baseData.nt");
                        fileOut.write(cadenaBase.getBytes("utf8"));
                        fileOut.flush();
                        fileOut.close();
                        
                        foi.close(response.getUser(), Instance.ACTION_ACCEPT);
                        response.setMode(MODE_ACUSE);
                        response.setRenderParameter("sg", x509SingInstance.getURI());
                        
                        //response.sendRedirect(foi.getUserTaskInboxUrl());//URL BOTON
                        
                    } else {
                        //TODO: Fallo de validación de firma.....
                        response.setMode(MODE_SIGN);
                    }
                    
                }
            } catch (Exception gse){
                //TODO: Como poner el error...
                log.error("Error al firmar la tarea...", gse);
            }
            // TODO: falta procesar el parámetro del applet de la firma
            
            // al final se cierra ....
            
            

        } else if ("process".equals(response.getAction())) {
            FlowNodeInstance foi = (FlowNodeInstance) SWBPlatform.getSemanticMgr().getOntology().getGenericObject(suri);
            if (suri == null) {
                return;
            }
            SWBProcessFormMgr mgr = new SWBProcessFormMgr(foi);
            mgr.clearProperties();

            HashMap<String, String> hmprops = new HashMap();

            int i = 1;
            while (!base.getAttribute("prop" + i, "").equals("")) {

                String sprop = base.getAttribute("prop" + i);
                StringTokenizer stoken = new StringTokenizer(sprop, "|");

                String varName = "";
                String propid = "";

                if (stoken.hasMoreTokens()) {
                    varName = stoken.nextToken();
                    propid = stoken.nextToken();
                }

                hmprops.put(varName + "|" + propid, sprop);
                i++;
            }

            Iterator<ItemAwareReference> it = foi.listHeraquicalItemAwareReference().iterator();
            while (it.hasNext()) {
                ItemAwareReference item = it.next();
                SWBClass obj = item.getProcessObject();
                if (obj != null) {
                    String varName = item.getItemAware().getName();
                    SemanticClass cls = obj.getSemanticObject().getSemanticClass();
                    Iterator<SemanticProperty> itp = cls.listProperties();
                    while (itp.hasNext()) {
                        SemanticProperty prop = itp.next();
                        if (isViewProperty(response, varName, prop, hmprops)) {
                            mgr.addProperty(prop, varName, SWBFormMgr.MODE_VIEW);
                        } else if (isEditProperty(response, varName, prop, hmprops)) {
                            mgr.addProperty(prop, varName, SWBFormMgr.MODE_EDIT);
                        }
                    }
                }
            }
            try {
                mgr.processForm(request);

                // validar por si requiere firmado
                if (base.getAttribute("useSign", "").equals("true")) {
                    //redireccionamiento a doSign
                    response.setMode(MODE_SIGN);
                    //response.setAction("");
                } else if (request.getParameter("accept") != null) {
                    foi.close(response.getUser(), Instance.ACTION_ACCEPT);

//                    ProcessInstance pi=foi.getProcessInstance();
//                    List<FlowNodeInstance> l=SWBProcessMgr.getUserTaskInstances(pi, response.getUser());
//                    if(l.size()==1)
//                    {
//                        FlowNode fn=l.get(0).getFlowNodeType();
//                        if(fn instanceof UserTask)url=((UserTask)fn).getTaskWebPage().getUrl()+"?suri="+l.get(0).getURI();
//                    }

                    response.sendRedirect(foi.getUserTaskInboxUrl());
                    
                } else if (request.getParameter("reject") != null) {
                    foi.close(response.getUser(), Instance.ACTION_REJECT);
                    
                    response.sendRedirect(foi.getUserTaskInboxUrl());
                }
            } catch (Exception e) {
                log.error("Error al procesar ....",e);
                response.setRenderParameter("err", "invalidForm");
            }
            
            

        } else if ("removeprop".equals(response.getAction())) {
            String prop = request.getParameter("prop");

            HashMap<Integer, String> hmprops = new HashMap();
            int i = 1;
            while (!base.getAttribute("prop" + i, "").equals("")) {
                //System.out.println(i + " = " + base.getAttribute("prop" + i));
                if (!prop.equals("" + i)) {
                    hmprops.put(new Integer(i), base.getAttribute("prop" + i));
                }
                base.removeAttribute("prop" + i);
                i++;
            }

            ArrayList list = new ArrayList(hmprops.keySet());
            Collections.sort(list);

            i = 1;
            Iterator<Integer> itprop = list.iterator();
            while (itprop.hasNext()) {
                Integer integer = itprop.next();
                String thisprop = hmprops.get(integer);
                base.setAttribute("prop" + i, thisprop);
                i++;
            }
            try {
                base.updateAttributesToDB();
            } catch (Exception e) {
                log.error("Error al eliminar y guardar las propiedades del recurso en la DB.", e);
            }
        } else if ("addprops".equals(response.getAction())) {

            //configuración de botones
            if (request.getParameter("btnSave") != null) {
                base.setAttribute("btnSave", request.getParameter("btnSave"));
            } else {
                base.removeAttribute("btnSave");
            }
            if (request.getParameter("btnAccept") != null) {
                base.setAttribute("btnAccept", request.getParameter("btnAccept"));
            } else {
                base.removeAttribute("btnAccept");
            }
            if (request.getParameter("btnReject") != null) {
                base.setAttribute("btnReject", request.getParameter("btnReject"));
            } else {
                base.removeAttribute("btnReject");
            }
            if (request.getParameter("btnCancel") != null) {
                base.setAttribute("btnCancel", request.getParameter("btnCancel"));
            } else {
                base.removeAttribute("btnCancel");
            }
            if (request.getParameter("useSign") != null) {
                base.setAttribute("useSign", request.getParameter("useSign"));
            } else {
                base.removeAttribute("useSign");
            }

            String[] props = request.getParameterValues("existentes");

            HashMap<Integer, String> hmprops = new HashMap();
            int i = 1;
            while (!base.getAttribute("prop" + i, "").equals("")) {
                hmprops.put(new Integer(i), base.getAttribute("prop" + i));
                base.removeAttribute("prop" + i);
                i++;
            }

            HashMap<String, String> hmparam = new HashMap();

            if (props != null && props.length > 0) {
                for (int j = 0; j < props.length; j++) {
                    hmparam.put(props[j], props[j]);
                }
            }

            Iterator<Integer> itstr = hmprops.keySet().iterator();
            while (itstr.hasNext()) {
                Integer intg = itstr.next();
                String sprop = hmprops.get(intg);

                StringTokenizer stoken = new StringTokenizer(sprop, "|");

                String varName = "";
                String propid = "";

                if (stoken.hasMoreTokens()) {
                    varName = stoken.nextToken();
                    propid = stoken.nextToken();
                }

                if (hmparam.get(varName + "|" + propid) != null) {
                    hmparam.remove(varName + "|" + propid);

                } else if (hmparam.get(varName + "|" + propid) == null) {
                    itstr.remove();
                }

            }

            int maximo = 0;
            Iterator<Integer> itint = hmprops.keySet().iterator();
            while (itint.hasNext()) {
                Integer num = itint.next();
                if (num.intValue() > maximo) {
                    maximo = num.intValue();
                }
            }

            //Agregar propiedades faltantes

            String defaultFE = FE_DEFAULT;
            String defaultMode = FE_MODE_EDIT;

            HashMap<String, String> hmclsprop = new HashMap<String, String>();

            // agregando al hmprops las propiedades nuevas
            Iterator<String> itpar = hmparam.keySet().iterator();
            while (itpar.hasNext()) {

                String strnew = itpar.next();

                StringTokenizer stoken = new StringTokenizer(strnew, "|");

                String varName = "";
                String propid = "";

                if (stoken.hasMoreTokens()) {
                    varName = stoken.nextToken();
                    propid = stoken.nextToken();
                }

                SemanticProperty sempro = SWBPlatform.getSemanticMgr().getVocabulary().getSemanticPropertyById(propid);
                //System.out.println("sempro: "+sempro);
                if (sempro != null) {
                    SemanticObject dp = sempro.getDisplayProperty();

                    if (dp != null) {
                        //tiene displayproperty
                        DisplayProperty disprop = new DisplayProperty(dp);

                        //FormElement por defecto
                        SemanticObject semobjFE = disprop.getFormElement();
                        if (semobjFE != null) {
                            defaultFE = semobjFE.getURI();
                        } else {
                            defaultFE = FE_DEFAULT;
                        }
                    }
                } else {
                    defaultFE = FE_DEFAULT;
                }

                String value = strnew + "|" + defaultMode + "|" + defaultFE;
                hmclsprop.put(strnew, value);
            }

            // ordenando las propiedades
            ArrayList list = new ArrayList(hmprops.keySet());
            Collections.sort(list);

            //guardando las propiedades
            i = 1;
            Iterator<Integer> itprop = list.iterator();
            while (itprop.hasNext()) {
                Integer integer = itprop.next();
                String thisprop = hmprops.get(integer);
                base.setAttribute("prop" + i, thisprop);
                i++;
            }

            // guardando las propiedades de acuerdo al index del display property

            ArrayList list2 = new ArrayList(hmclsprop.keySet());
            Collections.sort(list2);

            Iterator<String> itprop2 = list2.iterator();
            while (itprop2.hasNext()) {
                String string = itprop2.next();
                String thisprop = hmclsprop.get(string);
                base.setAttribute("prop" + i, thisprop);
                i++;
            }

            try {
                base.updateAttributesToDB();
            } catch (Exception e) {
                log.error("Error al guardar las propiedades de acuerdo al display property.",e);
            }


        } else if ("swap".equals(response.getAction())) {
            String direction = request.getParameter("direction");
            String propid = request.getParameter("prop");
            if (null != direction && "down".equals(direction)) {
                String tmp = base.getAttribute("prop" + propid);
                if (tmp != null) {
                    int pos = 0;
                    int posdown = 0;
                    try {
                        pos = Integer.parseInt(propid);
                        posdown = pos + 1;
                    } catch (Exception e) {
                    }
                    String tmp2 = base.getAttribute("prop" + posdown);
                    if (tmp2 != null && pos > 0 && posdown > 0 && pos < posdown) {
                        base.setAttribute("prop" + pos, tmp2);
                        base.setAttribute("prop" + posdown, tmp);
                    }
                }

            } else if (null != direction && "up".equals(direction)) {
                String tmp = base.getAttribute("prop" + propid);
                if (tmp != null) {
                    int pos = 0;
                    int posup = 0;
                    try {
                        pos = Integer.parseInt(propid);
                        posup = pos - 1;
                    } catch (Exception e) {
                    }
                    String tmp2 = base.getAttribute("prop" + posup);
                    if (tmp2 != null && pos > 0 && posup > 0 && pos > posup) {
                        base.setAttribute("prop" + pos, tmp2);
                        base.setAttribute("prop" + posup, tmp);
                    }
                }
            }
            try {
                base.updateAttributesToDB();
            } catch (Exception e) {
                log.error("Error al subir la propiedad....", e);
            }

        } else if ("updtItem".equals(response.getAction())) {

            String pid = request.getParameter("prop");
            if (pid != null) {
                String prop2change = base.getAttribute("prop" + pid);
                if (null != prop2change) {
                    StringTokenizer stoken = new StringTokenizer(prop2change, "|");
                    String varName = "";
                    String propid = "";
                    String modo = "";
                    String fele = "";

                    try {
                        if (stoken.hasMoreTokens()) {
                            varName = stoken.nextToken();
                            propid = stoken.nextToken();
                            modo = stoken.nextToken();
                            fele = stoken.nextToken();
                        }

                        if (request.getParameter("feuri") != null) {
                            fele = request.getParameter("feuri");
                        }
                        if (request.getParameter("mode") != null) {
                            modo = request.getParameter("mode");
                        }

                        String newvalue = varName + "|" + propid + "|" + modo + "|" + fele;

                        base.setAttribute("prop" + pid, newvalue);
                        base.updateAttributesToDB();

                    } catch (Exception e) {
                        log.error("Error al cambiar el orden de las propiedades.", e);
                        base.setAttribute("prop" + pid, prop2change);
                        try {
                            base.updateAttributesToDB();
                        } catch (Exception eupd) {
                            log.error("Error al restaurar el valor de la propiedad.", eupd);
                        }

                    }
                }
            }
        } else if ("update".equals(response.getAction())) {
            try {
                FlowNodeInstance foi = (FlowNodeInstance) SWBPlatform.getSemanticMgr().getOntology().getGenericObject(suri);
                String basepath = SWBPortal.getWorkPath() + base.getWorkPath() + "/code.xml";
                String xml = SWBUtils.IO.getFileFromPath(basepath); //base.getXmlConf();//bundle.getBundle(getClass().getName(), new java.util.Locale(user.getLanguage()));
                if (xml != null && xml.trim().length() > 0) {
                    SWBFormMgrLayer.update2DB(request, response, foi, xml);
                }
                response.setAction(SWBActionResponse.Action_EDIT);
            } catch (Exception e) {
                log.error(e);
            }
        }
        response.setRenderParameter("suri", suri);
    }

    @Override
    public void doAdmin(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        Resource base = getResourceBase();
        PrintWriter out = response.getWriter();
        User user = paramRequest.getUser();
        String lang = user.getLanguage();
        String suri = request.getParameter("suri");

        String action = paramRequest.getAction();

        String adminMode = base.getAttribute("adminMode", ADMINMODE_SIMPLE);

        if (action != null && (action.equals(ADMINMODE_ADVANCE) || action.equals(ADMINMODE_SIMPLE))) {
            adminMode = action;
        }

        HashMap<String, String> hmsc = new HashMap();
        HashMap<String, SemanticProperty> hmprops = new HashMap();
        HashMap<String, SemanticProperty> hmselected = new HashMap();

        UserTask ut = null;
        if (base.getResourceable() instanceof UserTask) {
            ut = (UserTask) base.getResourceable();

        }

        if (request.getParameter("errormsg") != null) {
            out.println("<script type=\"text/javascript\">");
            out.println("   alert('" + request.getParameter("errormsg") + "');");
            out.println("</script>");
        }

        if (ut == null) {
            out.println("Parámetro no definido...");
            return;
        }

        SemanticOntology ont = SWBPlatform.getSemanticMgr().getSchema();
        SemanticVocabulary sv = SWBPlatform.getSemanticMgr().getVocabulary();

        if (hmFormEle == null) {
            hmFormEle = new HashMap<String, SemanticObject>();

            Iterator<SemanticObject> itfe = ont.listInstancesOfClass(sv.getSemanticClass(SemanticVocabulary.SWB_FORMELEMENT));
            while (itfe.hasNext()) {
                SemanticObject sofe = itfe.next();
                hmFormEle.put(sofe.getURI(), sofe);
            }
        }

        Iterator<ItemAware> it = ut.listHerarquicalRelatedItemAwarePlusNullOutputs().iterator();
        while (it.hasNext()) {
            ItemAware item = it.next();
            SemanticClass cls = item.getItemSemanticClass();
            if (cls != null) {
                Iterator<SemanticProperty> itp = cls.listProperties();
                while (itp.hasNext()) {
                    SemanticProperty prop = itp.next();
                    String name = item.getName() + "|" + prop.getPropId();
                    hmsc.put(name, prop.getPropertyCodeName());
                    hmprops.put(name, prop);
                }
            }
        }

        int max = 1;
        while (!base.getAttribute("prop" + max, "").equals("")) {

            String val = base.getAttribute("prop" + max);
            String varName = "";
            String propid = "";
            String modo = "";
            String fe = "";
            StringTokenizer stoken = new StringTokenizer(val, "|");
            if (stoken.hasMoreTokens()) {
                varName = stoken.nextToken();
                propid = stoken.nextToken();
                modo = stoken.nextToken();
                fe = stoken.nextToken();
            }
            if (hmprops.get(varName + "|" + propid) != null) {
                SemanticProperty sprop = ont.getSemanticProperty(propid);
                hmselected.put(varName + "|" + propid, sprop);
            }
            max++;
        }

        out.println("<div class=\"swbform\">");

        if (ADMINMODE_SIMPLE.equals(adminMode)) {

            out.println("<script type=\"text/javascript\">");
            out.println("function MoveItems(lstbxFrom,lstbxTo) ");
            out.println("{ ");
            out.println("	var varFromBox = document.getElementById(lstbxFrom); ");
            out.println("	var varToBox = document.getElementById(lstbxTo); ");
            out.println("	if ((varFromBox != null) && (varToBox != null))  ");
            out.println("	{  ");
            out.println("		if(varFromBox.length < 1)  ");
            out.println("		{ ");
            out.println("			alert('No hay propiedades en la lista.'); ");
            out.println("			return false; ");
            out.println("		} ");
            out.println("		if(varFromBox.options.selectedIndex == -1) // no hay elementos seleccionados");
            out.println("		{ ");
            out.println("			alert('Selecciona una propiedad de la lista.'); ");
            out.println("			return false; ");
            out.println("		} ");
            out.println("		while ( varFromBox.options.selectedIndex >= 0 )  ");
            out.println("		{  ");
            out.println("			var newOption = new Option(); // crea una opcion en el select  ");
            out.println("			newOption.text = varFromBox.options[varFromBox.options.selectedIndex].text;  ");
            out.println("			newOption.value = varFromBox.options[varFromBox.options.selectedIndex].value;  ");
            out.println("			varToBox.options[varToBox.length] = newOption; //agrega la opción al final del select destino");
            out.println("			varFromBox.remove(varFromBox.options.selectedIndex); //quita la opción del select origen ");
            out.println("		}  ");
            out.println("	} ");
            out.println("	return false;  ");
            out.println("} ");

            out.println("function enviatodos(lstbox)");
            out.println("{");
            out.println("	var list = document.getElementById(lstbox);");
            out.println("	for (var i=0; i<list.options.length; i++){");
            out.println("	 list.options[i].selected=true;");
            out.println("	}");
            out.println("	return true;");
            out.println("}");
            out.println("   function updItem(uri,param,sel) {");
            out.println("       var valor = sel.options[sel.options.selectedIndex].value;");
            out.println("       var url = uri+'&'+param+'='+escape(valor);");
            out.println("       window.location=url;");
            out.println("}");
            out.println("</script>");

            SWBResourceURL urladd = paramRequest.getActionUrl();
            urladd.setAction("addprops");

            long idform = System.currentTimeMillis();

            out.println("<form action=\"" + urladd + "\" id=\"" + idform + "/forma\" method=\"post\" onsubmit=\"if(enviatodos('" + idform + "/existentes')) { this.form.submit(); return false; } else { return false;}\">");
            out.println("<input type=\"hidden\" name=\"suri\" value=\"" + suri + "\">");
            out.println("<fieldset>");
            out.println("<legend>" + "Configuración" + "</legend>");
            out.println("<table border=\"0\" width=\"100%\" >");
            out.println("<tr>");
            out.println("<th>Propiedades");
            out.println("</th>");
            out.println("<th>");
            out.println("</th>");
            out.println("<th>Seleccionadas");
            out.println("</th>");
            out.println("</tr>");
            out.println("<tr>");
            out.println("<td>");

            ArrayList list = new ArrayList(hmprops.keySet());
            Collections.sort(list);

            // select con la lista de propiedades existentes
            out.println("<select size=\"10\" name=\"propiedades\" id=\"" + idform + "/propiedades\" multiple style=\"width: 100%;\">");
            //Iterator<String> its = hmprops.keySet().iterator();
            Iterator<String> its = list.iterator();
            while (its.hasNext()) {

                String str = its.next();
                String varName = "";
                String propid = "";
                StringTokenizer stoken = new StringTokenizer(str, "|");
                if (stoken.hasMoreTokens()) {
                    varName = stoken.nextToken();
                    propid = stoken.nextToken();
                }
                if (hmselected.get(str) == null) {
                    SemanticProperty sp = hmprops.get(str);
                    if (!propid.equals("swb:valid")) {
                        out.println("<option value=\"" + str + "\">");
                        out.println(varName + "." + sp.getPropertyCodeName());
                        out.println("</option>");
                    }
                }
            }
            out.println("</select>");
            out.println("</td>");
            out.println("<td valign=\"middle\" width=\"25px\">");
            // botones
            out.println("<button dojoType=\"dijit.form.Button\" type = \"button\" style=\"width: 25px;\" id = \"" + idform + "btnMoveLeft\" onclick = \"MoveItems('" + idform + "/existentes','" + idform + "/propiedades');\"><-</button><br>");
            out.println("<button dojoType=\"dijit.form.Button\" type = \"button\" style=\"width: 25px;\" id = \"" + idform + "btnMoveRight\" onclick = \"MoveItems('" + idform + "/propiedades','" + idform + "/existentes');\">-></button>");
            out.println("</td>");
            out.println("<td>");
            // select con la lista de propiedades seleccionadas
            out.println("<select size=\"10\" name=\"existentes\" id=\"" + idform + "/existentes\" multiple style=\"width: 100%;\">");
            its = hmselected.keySet().iterator();
            while (its.hasNext()) {
                String str = its.next();
                String varName = "";
                String propid = "";
                StringTokenizer stoken = new StringTokenizer(str, "|");
                if (stoken.hasMoreTokens()) {
                    varName = stoken.nextToken();
                    propid = stoken.nextToken();
                }


                SemanticProperty sp = hmprops.get(str);
                out.println("<option value=\"" + str + "\">");
                out.println(varName + "." + sp.getPropertyCodeName());
                out.println("</option>");
            }
            out.println("</select>");
            out.println("</td>");
            out.println("</tr>");

            //botones para despliegue en forma básica

            out.println("<tr>");
            out.println("<td>");
            out.println("<input type=\"checkbox\" name=\"btnAccept\" id=\"" + idform + "_btnAccept\" value=\"use\" " + (base.getAttribute("btnAccept", "").equals("use") ? "checked" : "") + "><label for=\"" + idform + "_btnAccept\">Utilizar botón aceptar</label>");
            out.println("</td>");
            out.println("</tr>");
            out.println("<tr>");
            out.println("<td>");
            out.println("<input type=\"checkbox\" name=\"btnReject\" id=\"" + idform + "_btnReject\" value=\"use\" " + (base.getAttribute("btnReject", "").equals("use") ? "checked" : "") + "><label for=\"" + idform + "_btnReject\">Utilizar botón rechazar</label>");
            out.println("</td>");
            out.println("</tr>");
            out.println("<tr>");
            out.println("<td>");
            out.println("<input type=\"checkbox\" name=\"btnCancel\" id=\"" + idform + "_btnCancel\" value=\"use\" " + (base.getAttribute("btnCancel", "").equals("use") ? "checked" : "") + "><label for=\"" + idform + "_btnCancel\">Utilizar boton cancelar</label>");
            out.println("</td>");
            out.println("</tr>");
            out.println("<tr>");
            out.println("<td>");
            out.println("<input type=\"checkbox\" name=\"btnSave\" id=\"" + idform + "_btnSave\" value=\"use\" " + (base.getAttribute("btnSave", "").equals("use") ? "checked" : "") + "><label for=\"" + idform + "_btnSave\">Utilizar botón guardar</label>");
            out.println("</td>");
            out.println("</tr>");
            out.println("<tr>");
            out.println("<td>");
            out.println("<input type=\"checkbox\" name=\"useSign\" id=\"" + idform + "_useSign\" value=\"true\" " + (base.getAttribute("useSign", "").equals("true") ? "checked" : "") + "><label for=\"" + idform + "_useSign\">Utilizar firmado</label>");
            out.println("</td>");
            out.println("</tr>");

            out.println("<tr>");
            out.println("<td colspan=\"3\" >");
            // botones para guadar cambios
            out.println("<button  dojoType=\"dijit.form.Button\" type=\"submit\">Guardar</button>");
            out.println("</td>");
            out.println("</tr>");
            out.println("</table>");
            out.println("</fieldset>");
            out.println("<fieldset>");
            out.println("<table border=\"0\" width=\"100%\">");
            out.println("<tr>");
            out.println("<th>Acción");
            out.println("</th>");
            out.println("<th>Propiedad");
            out.println("</th>");
            out.println("<th>FormElement");
            out.println("</th>");
            out.println("<th>Modo");
            out.println("</th>");
            out.println("</tr>");
            int i = 1;
            while (!base.getAttribute("prop" + i, "").equals("")) {
                String val = base.getAttribute("prop" + i);
                String varName = "";
                String propid = "";
                String modo = "";
                String fe = "";
                StringTokenizer stoken = new StringTokenizer(val, "|");
                if (stoken.hasMoreTokens()) {
                    varName = stoken.nextToken();
                    propid = stoken.nextToken();
                    modo = stoken.nextToken();
                    fe = stoken.nextToken();
                }
                out.println("<tr>");

                SWBResourceURL urlrem = paramRequest.getActionUrl();
                urlrem.setAction("removeprop");
                urlrem.setParameter("prop", "" + i);
                urlrem.setParameter("suri", suri);

                out.println("<td>");
                out.println("<a href=\"#\" onclick=\"window.location='" + urlrem + "'; return false;\"><img src=\"" + SWBPlatform.getContextPath() + "/swbadmin/images/delete.gif\" border=\"0\" alt=\"eliminar\"></a>");

                if (i != max - 1) {
                    SWBResourceURL urldown = paramRequest.getActionUrl();
                    urldown.setAction("swap");
                    urldown.setParameter("suri", suri);
                    urldown.setParameter("prop", "" + i);
                    urldown.setParameter("direction", "down");
                    out.println("<a href=\"#\" onclick=\"window.location='" + urldown + "'; return false;\"><img src=\"" + SWBPlatform.getContextPath() + "/swbadmin/images/down.jpg\" border=\"0\" alt=\"bajar\"></a>");
                } else {
                    out.println("<img src=\"" + SWBPlatform.getContextPath() + "/swbadmin/images/space.jpg\" border=\"0\" >");
                }

                if (i != 1) //sorderdown>0
                {
                    SWBResourceURL urlup = paramRequest.getActionUrl();
                    urlup.setAction("swap");
                    urlup.setParameter("suri", suri);
                    urlup.setParameter("prop", "" + i);
                    urlup.setParameter("direction", "up");
                    urlup.setParameter("ract", "config");
                    out.println("<a href=\"#\" onclick=\"window.location='" + urlup + "'; return false;\"><img src=\"" + SWBPlatform.getContextPath() + "/swbadmin/images/up.jpg\" border=\"0\" alt=\"subir\"></a>");
                }
                out.println("</td>");
                out.println("<td>");

                out.println(varName + "." + hmsc.get(varName + "|" + propid));

                out.println("</td>");
                out.println("<td>");

                SWBResourceURL urlupd = paramRequest.getActionUrl();
                urlupd.setAction("updtItem");
                urlupd.setParameter("prop", "" + i);
                urlupd.setParameter("suri", suri);

                out.println("<select id=\"" + suri + "/" + i + "/sfe\" name=\"formElement\" style=\"width:300px;\" onchange=\"updItem('" + urlupd + "','feuri',this);\" >"); //
                out.println(getFESelect(fe, paramRequest, hmprops.get(varName + "|" + propid)));
                out.println("</select>");
                out.println("</td>");
                out.println("<td>");
                out.println("<select id=\"" + suri + "/" + i + "/smode\" name=\"mode\" style=\"width:80px;\" onchange=\"updItem('" + urlupd + "','mode',this);\">"); //
                out.println("<option value=\"edit\" " + (modo.equals("edit") ? "selected" : "") + " >Edit</option>");
                out.println("<option value=\"view\" " + (modo.equals("view") ? "selected" : "") + " >View</option>");
                out.println("</select>");
                out.println("</td>");
                out.println("</tr>");
                i++;
            }

            out.println("</table>");
            out.println("</fieldset>");

            if (hmprops.size() > 0) {
                out.println("<fieldset>");
                SWBResourceURL urladv = paramRequest.getRenderUrl();
                urladv.setAction(ADMINMODE_ADVANCE);
                out.println("<button dojoType=\"dijit.form.Button\" type=\"button\" onclick=\"window.location='" + urladv + "'; return false;\">Ir al modo avanzado</button>");
                out.println("</fieldset>");
            }
            out.println("</form>");

        } else if (ADMINMODE_ADVANCE.equals(adminMode)) {
            SWBResourceURL urladd = paramRequest.getActionUrl();
            urladd.setAction("updAdminMode");
            out.println("<form action=\"" + urladd + "\" id=\"" + suri + "/forma\" method=\"post\" onsubmit=\"if(enviatodos('existentes')) { this.form.submit(); return false; } else { return false;}\">");
            out.println("<input type=\"hidden\" name=\"suri\" value=\"" + suri + "\">");
            out.println("<fieldset>");
            out.println("<legend>" + "Modo avanzado" + "</legend>");
            out.println("<table>");

            out.println("<tr>");
            out.println("<td>");
            out.println("<input type=\"checkbox\" name=\"adminMode\" id=\"" + suri + "_adminmode\"  " + (adminMode.equals(ADMINMODE_ADVANCE) ? "checked" : "") + " value=\"" + ADMINMODE_ADVANCE + "\"><label for=\"" + suri + "_adminmode\">Utilizar modo avanzado</label>");
            out.println("</td>");
            out.println("</tr>");
            out.println("</table>");

            out.println("</fieldset>");
            out.println("<fieldset>");
            out.println("<table>");
            out.println("<tr>");
            out.println("<td colspan=\"3\" align=\"center\">");

            out.println("<button dojoType=\"dijit.form.Button\" type=\"submit\">Guardar</button>");

            SWBResourceURL urlbck = paramRequest.getRenderUrl();
            urlbck.setAction(ADMINMODE_SIMPLE);
            urlbck.setParameter("suri", suri);

            out.println("<button  dojoType=\"dijit.form.Button\" type=\"button\" onclick=\"window.location='" + urlbck + "'; return false;\">Regresar</button>");

            out.println("</td>");
            out.println("</tr>");
            out.println("</table>");
            out.println("</fieldset>");
            out.println("</form>");

            if (base.getAttribute("adminMode") != null && base.getAttribute("adminMode").equals(ADMINMODE_ADVANCE)) {

                urladd = paramRequest.getActionUrl();
                urladd.setAction("updXMLConfig");
                out.println("<form action=\"" + urladd + "\" id=\"" + suri + "/forma\" method=\"post\" onsubmit=\"if(enviatodos('existentes')) { this.form.submit(); return false; } else { return false;}\">");
                out.println("<input type=\"hidden\" name=\"suri\" value=\"" + suri + "\">");
                out.println("<fieldset>");
                out.println("<legend>" + "Configuración XML" + "</legend>");
                out.println("<table>");

                out.println("<tr>");
                out.println("<td>");
                out.println("<input type=\"checkbox\" name=\"btnAccept\" id=\"" + suri + "_btnAccept\" value=\"incluir\"><label for=\"" + suri + "_btnAccept\">Utilizar botón aceptar</label>");
                out.println("</td>");
                out.println("</tr>");
                out.println("<tr>");
                out.println("<td>");
                out.println("<input type=\"checkbox\" name=\"btnReject\" id=\"" + suri + "_btnReject\" value=\"incluir\"><label for=\"" + suri + "_btnReject\">Utilizar botón rechazar</label>");
                out.println("</td>");
                out.println("</tr>");
                out.println("<tr>");
                out.println("<td>");
                out.println("<input type=\"checkbox\" name=\"btnCancel\" id=\"" + suri + "_btnCancel\" value=\"incluir\"><label for=\"" + suri + "_btnCancel\">Utilizar boton cancelar</label>");
                out.println("</td>");
                out.println("</tr>");
                out.println("<tr>");
                out.println("<td>");
                out.println("<input type=\"checkbox\" name=\"btnSave\" id=\"" + suri + "_btnSave\" value=\"incluir\"><label for=\"" + suri + "_btnSave\">Utilizar botón guardar</label>");
                out.println("</td>");
                out.println("</tr>");
                out.println("<tr>");
                out.println("<td>");
                out.println("<input type=\"checkbox\" name=\"useDojo\" id=\"" + suri + "_useDojo\" value=\"dojo\"><label for=\"" + suri + "_useDojo\">Utilizar Dojo</label>");
                out.println("</td>");
                out.println("</tr>");
                out.println("</table>");

                out.println("</fieldset>");
                out.println("<fieldset>");
                out.println("<table>");
                out.println("<tr>");
                out.println("<td colspan=\"3\" align=\"center\">");

                out.println("<button dojoType=\"dijit.form.Button\" type=\"submit\">Generar XML</button>");
                out.println("</td>");
                out.println("</tr>");
                out.println("</table>");
                out.println("</fieldset>");
                out.println("</form>");

                String basepath = SWBPortal.getWorkPath() + base.getWorkPath() + "/";
                String value = SWBUtils.IO.getFileFromPath(basepath + "code.xml");

                if (null != value && value.trim().length() > 0) {

                    StringBuilder ret = new StringBuilder(400);
                    SWBResourceURL url = paramRequest.getActionUrl().setAction("saveXMLFile");

                    //Despliegue del editor
                    ret.append("<script type=\"text/javascript\" src=\"");
                    ret.append(SWBPlatform.getContextPath());
                    ret.append("/swbadmin/js/editarea/edit_area/edit_area_full.js\"></script>\n");
                    ret.append("<script type=\"text/javascript\" charset=\"UTF-8\">\n");
                    ret.append("editAreaLoader.init({\n");
                    ret.append("    id : \"resource");
                    ret.append(base.getId());
                    ret.append("\"\n    ,language: \"");
                    ret.append(paramRequest.getUser().getLanguage().toLowerCase());
                    ret.append("\"\n    ,syntax: \"xml\"\n");
                    ret.append("    ,start_highlight: true\n");
                    ret.append("    ,toolbar: \"save, |, search, go_to_line,");
                    ret.append(" |, undo, redo, |, select_font,|, change_smooth_selection,");
                    ret.append(" highlight, reset_highlight, |, help\"\n");
                    ret.append("    ,is_multi_files: false\n");
                    ret.append("    ,save_callback: \"my_save\"\n");
                    ret.append("    ,allow_toggle: false\n");
                    ret.append("});\n");
                    ret.append("\n");
                    ret.append("  function my_save(id, content){\n");
                    ret.append("    var elemento = document.getElementById(\"hiddencode\");\n");
                    ret.append("    elemento.value = content;\n");
                    ret.append("    document.xmledition.submit();\n");
                    ret.append("  }\n");
                    ret.append("</script>\n");
                    ret.append("<form name=\"xmledition\" action=\"");
                    ret.append(url.toString());
                    ret.append("\" method=\"post\">\n");
                    ret.append("<textarea id=\"resource");
                    ret.append(base.getId());
                    ret.append("\" name=\"resource");
                    ret.append(base.getId());
                    ret.append("\" rows=\"25");
                    ret.append("\" cols=\"95\">");
                    ret.append(value);
                    ret.append("</textarea>\n");
                    ret.append("<input type=\"hidden\" id=\"hiddencode\" name=\"hiddencode\" value=\"\"/>\n");

                    out.println(ret.toString());
                }
            }
        }
        out.println("</div>");
    }

    public String getFESelect(String FEsel, SWBParamRequest paramRequest, SemanticProperty sprop) {

        User usr = paramRequest.getUser();
        SemanticOntology ont = SWBPlatform.getSemanticMgr().getSchema();
        SemanticVocabulary sv = SWBPlatform.getSemanticMgr().getVocabulary();
        StringBuilder ret = new StringBuilder();
        ret.append("\n<optgroup label=\"Genérico\">");
        ret.append("\n<option value=\"generico\" selected >GenericFormElement</option>");
        ret.append("\n</optgroup>");

        HashMap<String, SemanticClass> hmscfe = new HashMap<String, SemanticClass>();
        HashMap<String, SemanticObject> hmso = null;

        //Property prop=SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty(SemanticVocabulary.SWB_ANNOT_FORMELEMENTRANGE).getRDFProperty();
        SemanticProperty pro = SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty(SemanticVocabulary.SWB_ANNOT_FORMELEMENTRANGE);
        //System.out.println("prop:" + pro.getName());


        Iterator<SemanticClass> itsub = sv.getSemanticClass(SemanticVocabulary.SWB_SWBFORMELEMENT).listSubClasses();
        while (itsub.hasNext()) {
            SemanticClass scobj = itsub.next();

            if (pro == null || sprop == null) {
                continue;
            }
            NodeIterator it = scobj.getOntClass().listPropertyValues(pro.getRDFProperty());

            while (it.hasNext()) {
                RDFNode node = it.next();

                //System.out.println("node:" + node+" "+sprop.getRange());            
                if (node != null) {
                    if (sprop.getRange().getURI().equals(node.asResource().getURI())) {
                        hmscfe.put(scobj.getDisplayName(usr.getLanguage()), scobj);
                    } else if (sprop.getRangeClass() != null && node.isResource()) {
                        SemanticClass cls = sv.getSemanticClass(node.asResource().getURI());
                        if (cls != null) {
                            if (sprop.getRangeClass().isSubClass(cls)) {
                                hmscfe.put(scobj.getDisplayName(usr.getLanguage()), scobj);
                            }
                        }
                    }
                }
            }
        }

        ArrayList list = new ArrayList(hmscfe.keySet());
        Collections.sort(list);

        Iterator<String> itsc = list.iterator();
        while (itsc.hasNext()) {

            String key = itsc.next();
            SemanticClass scfe = hmscfe.get(key);

            ret.append("\n<optgroup label=\"");
            ret.append(scfe.getDisplayName(paramRequest.getUser().getLanguage()));
            ret.append("\">");
            hmso = new HashMap<String, SemanticObject>();

            Iterator<SemanticObject> itsco = ont.listInstancesOfClass(scfe);
            while (itsco.hasNext()) {
                SemanticObject semObj = itsco.next();
                hmso.put(semObj.getDisplayName(usr.getLanguage()), semObj);
            }

            list = new ArrayList(hmso.keySet());
            Collections.sort(list);

            Iterator<String> itsoo = list.iterator();
            while (itsoo.hasNext()) {
                key = itsoo.next();
                SemanticObject sofe = hmso.get(key);
                ret.append("\n<option value=\"");
                ret.append(sofe.getURI());
                ret.append("\"");
                String stmp = FEsel + "edit|" + sofe.getURI();
                String stmp2 = FEsel + "view|" + sofe.getURI();
                String data = paramRequest.getResourceBase().getData(paramRequest.getWebPage());
                if (FEsel != null && !FEsel.equals("") && (data != null && (data.indexOf(stmp) > -1 || data.indexOf(stmp2) > -1) || FEsel.equals(sofe.getURI()))) {
                    ret.append(" selected ");
                }
                ret.append(">");
                ret.append(sofe.getDisplayName(paramRequest.getUser().getLanguage()));
                ret.append("\n</option>");
            }
            ret.append("\n</optgroup>");
        }

        return ret.toString();
    }

    @Override
    public void processRequest(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        if (paramRequest.getMode().equals(MODE_SIGN)) {
            doSign(request, response, paramRequest);
        } else if (paramRequest.getMode().equals(MODE_ACUSE)) {
            doAcuse(request, response, paramRequest);
        } else{
            super.processRequest(request, response, paramRequest);
        }
    }

    public boolean hasProperty(SWBParameters paramRequest, String varName, SemanticProperty prop) {
        boolean ret = false;
        String data = paramRequest.getResourceBase().getData(paramRequest.getWebPage());
        if (data != null && data.indexOf(varName + "|" + prop.getPropId()) > -1) {
            ret = true;
        }
        return ret;
    }

    public boolean isViewProperty(SWBParameters paramRequest, String varName, SemanticProperty prop, HashMap<String, String> hm) {
        boolean ret = false;
        String data = hm.get(varName + "|" + prop.getPropId());
        if (data != null && data.indexOf(varName + "|" + prop.getPropId() + "|view") > -1) {
            ret = true;
        }
        return ret;
    }

    public boolean isEditProperty(SWBParameters paramRequest, String varName, SemanticProperty prop, HashMap<String, String> hm) {
        boolean ret = false;
        String data = hm.get(varName + "|" + prop.getPropId());
        if (data != null && data.indexOf(varName + "|" + prop.getPropId() + "|edit") > -1) {
            ret = true;
        }
        return ret;
    }
    
    public String htmlwrap(String orig, final int length){
        StringBuilder ret = new StringBuilder(512);
        ret.append("<pre>");
        int linea = 0;
        while(linea*length < orig.length()){
            int inicial=linea*length;
            int ifinal = ++linea * length;
            if (ifinal>orig.length()){
                ret.append(orig.substring(inicial));
            } else {
                ret.append(orig.substring(inicial,ifinal));
                ret.append("\n");
            }
        }
        ret.append("</pre>");
        return ret.toString();
    }
}
